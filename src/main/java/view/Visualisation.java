package view;

import javafx.animation.PathTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;

public class Visualisation extends IVisualisation {

	private Canvas canvas;
	private GraphicsContext gc;
	private final int canvasWidth;
	private final int canvasHeight;
	private Pane pane;
	private double[] startPositions;

	public Visualisation(int w, int h) {
		super(w, h);
		canvas = new Canvas(w, h);
		gc = canvas.getGraphicsContext2D();
		canvasWidth = w;
		canvasHeight = h;
		pane = new Pane(canvas);
		startPositions = new double[]{
				0, 0, // Top-left corner (service point 1)
				canvasWidth - 10, 0, // Top-right corner (service point 2)
				0, canvasHeight - 10, // Bottom-left corner (service point 3)
				canvasWidth - 10, canvasHeight - 10 // Bottom-right corner (service point 4)
		};
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
		gc.setFill(Color.LIGHTBLUE);
		gc.fillRect(0, 0, canvasWidth, canvasHeight);
	}

	@Override
	public void visualiseCustomer(int servicePoint) {
		newCustomer(servicePoint);
	}

	@Override
	public void newCustomer(int servicePoint) {
		if (servicePoint < 1 || servicePoint > 4) {
			throw new IllegalArgumentException("Service point must be between 1 and 4");
		}
		Color color;
		switch (servicePoint) {
			case 1:
				color = Color.BLUEVIOLET;
				break;
			case 2:
				color = Color.DARKVIOLET;
				break;
			case 3:
				color = Color.DARKOLIVEGREEN;
				break;
			case 4:
			default:
				color = Color.INDIANRED;
				break;
		}

		// Create a circle to represent the customer
		Circle circle = new Circle(5, color);
		circle.setCenterX(startPositions[(servicePoint - 1) * 2]);
		circle.setCenterY(startPositions[(servicePoint - 1) * 2 + 1]);
		pane.getChildren().add(circle);

		// Define the path based on the service point
		Path path = new Path();
		switch (servicePoint) {
			case 1: // Top-left corner
				path.getElements().add(new MoveTo(0, 0));
				path.getElements().add(new HLineTo(canvasWidth / 2));
				path.getElements().add(new VLineTo(canvasHeight / 2));
				path.getElements().add(new HLineTo(canvasWidth - 10));
				path.getElements().add(new VLineTo(canvasHeight - 10));
				break;
			case 2: // Top-right corner
				path.getElements().add(new MoveTo(canvasWidth, 0));
				path.getElements().add(new HLineTo(canvasWidth / 2));
				path.getElements().add(new VLineTo(canvasHeight / 2));
				path.getElements().add(new HLineTo(10));
				path.getElements().add(new VLineTo(canvasHeight - 10));
				break;
			case 3: // Bottom-left corner
				path.getElements().add(new MoveTo(0, canvasHeight));
				path.getElements().add(new HLineTo(canvasWidth / 2));
				path.getElements().add(new VLineTo(canvasHeight / 2));
				path.getElements().add(new HLineTo(canvasWidth - 10));
				path.getElements().add(new VLineTo(10));
				break;
			case 4: // Bottom-right corner
				path.getElements().add(new MoveTo(canvasWidth, canvasHeight));
				path.getElements().add(new HLineTo(canvasWidth / 2));
				path.getElements().add(new VLineTo(canvasHeight / 2));
				path.getElements().add(new HLineTo(10));
				path.getElements().add(new VLineTo(10));
				break;
		}

		// Animate the circle along the path
		PathTransition transition = new PathTransition();
		transition.setNode(circle);
		transition.setPath(path);
		transition.setDuration(Duration.seconds(2));
		transition.setCycleCount(1);
		transition.setOnFinished(event -> pane.getChildren().remove(circle));
		transition.play();
	}

}
