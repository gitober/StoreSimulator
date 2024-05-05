package controller;

import javafx.application.Platform;
import simu.model.ArrivalPattern;
import simu.model.MyEngine;
import view.ISimulatorUI;
import view.IVisualisation;

public class Controller implements IControllerVtoM, IControllerMtoV {
	private MyEngine engine;
	private ISimulatorUI ui;
	private IVisualisation visualisation;
	private ArrivalPattern desiredPattern;
	private boolean isSimulationStarted = false;

	public Controller(ISimulatorUI ui) {
		this.ui = ui;
		this.visualisation = ui.getVisualisation();
	}

	@Override
	public void visualiseCustomer(int servicePoint) {
		if (visualisation != null) {
			Platform.runLater(() -> visualisation.newCustomer(servicePoint));
		} else {
			System.out.println("Error: Visualisation is not initialized.");
		}
	}

	@Override
	public void startSimulation() {
		isSimulationStarted = true;

		int maxCustomers;
		try {
			maxCustomers = ui.getCustomerAmount();
		} catch (NumberFormatException e) {
			maxCustomers = 150;
			System.out.println("Invalid input. Setting max customers to 150 by default.");
		}

		engine = new MyEngine(this, maxCustomers);
		engine.setSimulationTime(ui.getTime());
		engine.setDelay(ui.getDelay());

		if (desiredPattern != null) {
			engine.setArrivalPattern(desiredPattern);
			System.out.println("Arrival pattern set to: " + describePattern(desiredPattern));
		}

		if (visualisation != null) {
			visualisation.clearDisplay();
		} else {
			System.out.println("Error: Visualisation is not initialized.");
		}

		new Thread(engine).start();
	}

	@Override
	public void decreaseSpeed() {
		if (ui != null) {
			long currentDelay = ui.getDelay();
			ui.setDelay((long) (currentDelay * 1.10));
		} else {
			System.out.println("Error: UI is not initialized.");
		}
	}

	@Override
	public void increaseSpeed() {
		if (engine != null) {
			engine.setDelay((long) (engine.getDelay() * 0.90));
		}
		if (ui != null) {
			ui.setDelay(engine != null ? engine.getDelay() : 0);
		} else {
			System.out.println("Error: UI is not initialized.");
		}
	}

	@Override
	public void showEndTime(double time) {
		Platform.runLater(() -> ui.setEndingTime(time));
	}

	@Override
	public void setArrivalPattern(ArrivalPattern pattern) {
		if (ui != null) {
			ui.setArrivalPattern(pattern);
			System.out.println("Arrival pattern set to: " + describePattern(pattern));
		} else {
			desiredPattern = pattern;
			System.out.println("Setting desired pattern to: " + describePattern(pattern));
		}
	}

	private String describePattern(ArrivalPattern pattern) {
		switch (pattern) {
			case MORNINGRUSH:
				return "Morning Rush (High frequency of customers)";
			case MIDDAYLULL:
				return "Midday Lull (Low frequency of customers)";
			case AFTERNOONRUSH:
				return "Afternoon Rush (High frequency of customers)";
			default:
				return "Unknown pattern";
		}
	}

	public void setMorningRush() {
		setArrivalPattern(ArrivalPattern.MORNINGRUSH);
	}

	public void setMiddayLull() {
		setArrivalPattern(ArrivalPattern.MIDDAYLULL);
	}

	public void setAfternoonRush() {
		setArrivalPattern(ArrivalPattern.AFTERNOONRUSH);
	}

	public boolean getIsSimulationStarted() {
		return isSimulationStarted;
	}
}