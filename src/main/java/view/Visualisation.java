package view;

import controller.Controller;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;

import java.util.*;

public class Visualisation extends IVisualisation {

	private Canvas canvas;
	private GraphicsContext gc;
	private final int canvasWidth;
	private final int canvasHeight;
	private Pane pane;
	private double[][] servicePoints;
	private List<Queue<Circle>> queues;
	private boolean[] serving;
	private Image backgroundImage;
	private final double queueSpacing = 15.0;
	private final double queueHorizontalOffset = 20.0;
	private boolean simulationStarted = false;
	private int createdCustomers = 0;
	private Controller controller;
	private ISimulatorUI ui;


	// Define the service points
	private static final int SERVICE_DESK = 0;
	private static final int DELI_COUNTER = 1;
	private static final int VEGETABLE_SECTION = 2;
	private static final int CASHIER = 3;
	private static final int EXIT = 4;

	public Visualisation(int w, int h, Controller controller, ISimulatorUI ui) {
		super(w, h);
		this.controller = controller;
		this.ui = ui;
		canvas = new Canvas(w, h);
		gc = canvas.getGraphicsContext2D();
		canvasWidth = w;
		canvasHeight = h;
		pane = new Pane(canvas);
		servicePoints = new double[][]{
				{50, 50}, // Top-left corner (Service Desk)
				{canvasWidth - 50, 50}, // Top-right corner (Deli Counter)
				{50, canvasHeight - 50}, // Bottom-left corner (Vegetable Section)
				{canvasWidth - 50, canvasHeight - 50}, // Bottom-right corner (Cashier)
				{canvasWidth / 2, canvasHeight - 10} // Bottom-middle (Exit)
		};
		queues = new ArrayList<>();
		serving = new boolean[5];
		for (int i = 0; i < 5; i++) {
			queues.add(new LinkedList<>());
			serving[i] = false;
		}

		// Load the background image
		backgroundImage = new Image(getClass().getResource("/kauppa.png").toExternalForm());

		clearDisplay();
		this.getChildren().add(pane);
	}

	public GraphicsContext getGraphicsContext2D() {
		return gc;
	}

	public Pane getPane() {
		return pane;
	}

	@Override
	public void clearDisplay() {
		if (backgroundImage != null) {
			gc.drawImage(backgroundImage, 0, 0, canvasWidth, canvasHeight);
		} else {
			gc.setFill(Color.LIGHTBLUE);
			gc.fillRect(0, 0, canvasWidth, canvasHeight);
		}
	}

	@Override
	public void visualiseCustomer(int servicePoint) {
		newCustomer(servicePoint);
	}

	@Override
	public void newCustomer(int servicePoint) {
		// Only create a new customer if the simulation has just started and the number of created customers is less than the user's input
		if (!simulationStarted && createdCustomers < ui.getCustomerAmount()) {
			// Check if a customer is already being served at the service point
			if (serving[servicePoint]) {
				return; // If so, do not add a new customer
			}
			Circle customer = new Circle(10, Color.DARKVIOLET);
			Platform.runLater(() -> {
				customer.setCenterX(canvasWidth / 2);
				customer.setCenterY(0);
				pane.getChildren().add(customer);
				List<Integer> servicePointsOrder = generateRandomOrder();
				servicePointsOrder.add(CASHIER); // Last stop is always the cashier
				servicePointsOrder.add(EXIT); // Then exit
				customer.setUserData(servicePointsOrder);
				if (queues.get(servicePoint).isEmpty()) { // Only add to queue if it's empty
					queues.get(servicePoint).add(customer);
				}
				if (!serving[servicePoint]) { // Only start serving if not already serving
					serveCustomer(servicePoint);
				}
			});
			createdCustomers++;
			if (createdCustomers >= ui.getCustomerAmount()) {
				simulationStarted = true;
			}
		}
	}


	private List<Integer> generateRandomOrder() {
		List<Integer> order = new ArrayList<>(Arrays.asList(SERVICE_DESK, DELI_COUNTER, VEGETABLE_SECTION));
		Collections.shuffle(order);
		return order;
	}

	private void serveCustomer(int servicePoint) {
		Queue<Circle> queue = queues.get(servicePoint);
		if (!queue.isEmpty()) {
			serving[servicePoint] = true;
			Circle nextCustomer = queue.poll();
			moveToNextServicePoint(nextCustomer, servicePoint);
		} else {
			serving[servicePoint] = false; // No customers to serve
		}
	}

	private void moveToNextServicePoint(Circle customer, int currentServicePoint) {
		List<Integer> servicePointsOrder = (List<Integer>) customer.getUserData();
		if (servicePointsOrder.isEmpty()) {
			return;
		}
		int nextServicePoint = servicePointsOrder.remove(0);
		Path path = createPathToServicePoint(customer, nextServicePoint);
		PathTransition transition = new PathTransition();
		transition.setNode(customer);
		transition.setPath(path);
		transition.setDuration(Duration.seconds(2));
		transition.setCycleCount(1);
		transition.setOnFinished(event -> {
			if (nextServicePoint == EXIT) {
				pane.getChildren().remove(customer);
			} else {
				queues.get(nextServicePoint).add(customer);
				serveCustomer(nextServicePoint);
			}
			serving[currentServicePoint] = false;
			serveCustomer(currentServicePoint);
		});
		transition.play();
	}


	private Path createPathToServicePoint(Circle customer, int servicePoint) {
		Path path = new Path();
		path.getElements().add(new MoveTo(customer.getCenterX(), customer.getCenterY()));
		double[] coordinates = Arrays.copyOf(servicePoints[servicePoint], 2);

		// Handle queuing
		int queuePosition = queues.get(servicePoint).size();
		coordinates[0] += queuePosition * queueHorizontalOffset;
		coordinates[1] += queuePosition * queueSpacing;

		// Limit the bounds
		coordinates[0] = Math.max(0, Math.min(canvasWidth - customer.getRadius(), coordinates[0]));
		coordinates[1] = Math.max(0, Math.min(canvasHeight - customer.getRadius(), coordinates[1]));

		// Add some spacing to prevent customers from overlapping
		coordinates[0] += queuePosition * 20; // Adjust this value as needed
		coordinates[1] += queuePosition * 20; // Adjust this value as needed
		path.getElements().add(new HLineTo(coordinates[0]));
		path.getElements().add(new VLineTo(coordinates[1]));

		customer.setCenterX(coordinates[0]);
		customer.setCenterY(coordinates[1]);

		return path;
	}
}