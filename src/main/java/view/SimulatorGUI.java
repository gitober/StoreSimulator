package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.ArrivalPattern;
import view.IVisualisation;
import view.Visualisation;
import controller.Controller;
import controller.IControllerVtoM;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimulatorGUI extends Application implements ISimulatorUI {

	private IControllerVtoM controller;
	private TextField customerAmount;
	private TextArea resultsTextArea;
	private IVisualisation display;
	private ComboBox<Double> timeDropdown;
	private ComboBox<String> delayDropdown;
	private Button startButton;

	private static final double[] exampleTimes = {30.0, 60.0, 120.0}; // Predefined example times

	@Override
	public void init() {
		Trace.setTraceLevel(Level.INFO);
	}

	@Override
	public void start(Stage primaryStage) {
		// Initialize display
		display = new Visualisation(500, 500);
		controller = new Controller(this, "Store Simulation");

		primaryStage.setOnCloseRequest(t -> {
			Platform.exit();
			System.exit(0);
		});

		// Create a PrintStream that appends text to the resultsTextArea
		PrintStream printStream = new PrintStream(new ByteArrayOutputStream()) {
			@Override
			public void println(String x) {
				Platform.runLater(() -> resultsTextArea.appendText(x + "\n\n"));
			}
		};
		System.setOut(printStream);

		// Set up the stage
		primaryStage.setTitle("Simulator");

		// Instantiate customerAmount TextField
		customerAmount = new TextField("Enter amount");
		customerAmount.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		customerAmount.setPrefWidth(150);

		// Allow only numeric characters
		customerAmount.addEventFilter(KeyEvent.KEY_TYPED, event -> {
			String character = event.getCharacter();
			// Allow only numeric characters or the backspace key
			if (!character.matches("[0-9\b]")) {
				System.out.println("Only numbers are allowed.");
				event.consume();
			}
		});

		// Set up the startButton
		startButton = new Button("Start simulation");
		startButton.setOnAction(event -> {
			int numberOfCustomers = getCustomerAmount();
			for (int i = 0; i < numberOfCustomers; i++) {
				display.visualiseCustomer(5); // Start new customers at ARRIVAL
			}
			controller.startSimulation();
			startButton.setDisable(true);
		});


		// Set up other buttons
		Button slowButton = new Button("Slow down");
		slowButton.setOnAction(e -> controller.decreaseSpeed());

		Button speedUpButton = new Button("Speed up");
		speedUpButton.setOnAction(e -> controller.increaseSpeed());

		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> {
			Platform.exit();
			System.exit(0);
		});

		// Set up labels and dropdowns
		Label customerLabel = new Label("Customer amount:");
		customerLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		Label patternLabel = new Label("Customer traffic:");
		patternLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		ComboBox<ArrivalPattern> arrivalPatternDropdown = new ComboBox<>();
		arrivalPatternDropdown.getItems().setAll(
				Arrays.stream(ArrivalPattern.values())
						.filter(pattern -> pattern != ArrivalPattern.CONSTANT)
						.toList()
		);
		arrivalPatternDropdown.setValue(ArrivalPattern.MORNINGRUSH);
		arrivalPatternDropdown.setOnAction(e -> controller.setArrivalPattern(arrivalPatternDropdown.getValue()));


		Label delayLabel = new Label("Simulation speed:");
		delayLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		delayDropdown = new ComboBox<>();
		delayDropdown.getItems().addAll("SLOW", "NORMAL", "FAST");
		delayDropdown.setValue("NORMAL");
		delayDropdown.setOnAction(e -> setDelay(getDelay()));

		List<Double> exampleTimesList = new ArrayList<>();
		for (double time : exampleTimes) {
			exampleTimesList.add(time);
		}
		timeDropdown = new ComboBox<>();
		timeDropdown.getItems().addAll(exampleTimesList);
		timeDropdown.setValue(exampleTimesList.get(0));

		// Set up the grid layout
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setVgap(10);
		grid.setHgap(5);

		grid.add(customerLabel, 0, 0);
		grid.add(customerAmount, 1, 0);
		grid.add(patternLabel, 0, 1);
		grid.add(arrivalPatternDropdown, 1, 1);
		grid.add(delayLabel, 0, 2);
		grid.add(delayDropdown, 1, 2);
		grid.add(startButton, 0, 3);
		grid.add(slowButton, 1, 3);
		grid.add(speedUpButton, 2, 3);
		grid.add(exitButton, 0, 6);

		// TextArea to display simulation results
		resultsTextArea = new TextArea();
		resultsTextArea.setEditable(false);
		resultsTextArea.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		resultsTextArea.setWrapText(true);
		resultsTextArea.setPrefHeight(500);
		resultsTextArea.setPrefWidth(300);

		// Add padding to the TextArea
		resultsTextArea.setPadding(new Insets(5));

		ScrollPane scrollPane = new ScrollPane(resultsTextArea);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		scrollPane.setPadding(new Insets(10));

		// Initialize display correctly
		VBox leftVBox = new VBox(10);
		leftVBox.setPadding(new Insets(15));
		leftVBox.getChildren().addAll(grid, (Visualisation) display);

		// Set up for the layout
		BorderPane borderPane = new BorderPane();
		borderPane.setLeft(leftVBox);
		borderPane.setCenter(scrollPane);

		primaryStage.setMinWidth(1300);
		primaryStage.setMaxWidth(1400);
		primaryStage.setMinHeight(900);
		primaryStage.setMaxHeight(900);

		// Set up for the scene
		Scene scene = new Scene(borderPane, 1200, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public double getTime() {
		return timeDropdown.getValue();
	}

	// Speed for the simulation
	@Override
	public long getDelay() {
		switch (delayDropdown.getValue()) {
			case "SLOW":
				return 1000;
			case "NORMAL":
				return 500;
			case "FAST":
				return 10;
			default:
				return 500;
		}
	}

	@Override
	public int getCustomerAmount() {
		try {
			return Integer.parseInt(customerAmount.getText());
		} catch (NumberFormatException e) {
			appendResults("Invalid customer amount, defaulting to 150.");
			return 150;
		}
	}

	@Override
	public void setEndingTime(double time) {
		Platform.runLater(() -> {
			DecimalFormat formatter = new DecimalFormat("#0.00");
			resultsTextArea.appendText("Ending time: " + formatter.format(time) + "\n\n");
			startButton.setDisable(false);
		});
	}

	@Override
	public IVisualisation getVisualisation() {
		return display;
	}

	@Override
	public void appendResults(String result) {
		Platform.runLater(() -> resultsTextArea.appendText(result + "\n\n"));
	}

	@Override
	public void setDelay(long delay) {
		controller.setSimulationSpeed(delay);
	}

	@Override
	public void setArrivalPattern(ArrivalPattern pattern) {
		controller.setArrivalPattern(pattern);
	}

	public void startSimulation() {
		int numberOfCustomers = getCustomerAmount();
		for (int i = 0; i < numberOfCustomers; i++) {
			display.visualiseCustomer(5); // Start new customers at ARRIVAL
		}
		controller.startSimulation();
	}


	public TextArea getResultsTextArea() {
		return resultsTextArea;
	}

	public ComboBox<Double> getTimeDropdown() {
		return timeDropdown;
	}

	public ComboBox<String> getDelayDropdown() {
		return delayDropdown;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
