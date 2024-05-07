package controller;

import javafx.application.Platform;
import simu.model.ArrivalPattern;
import simu.model.MyEngine;
import view.ISimulatorUI;
import view.IVisualisation;
import db.connections.dao.QueueHistoryDao;
import db.connections.entity.QueueHistory;
import view.Visualisation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Controller implements IControllerVtoM, IControllerMtoV {
	private MyEngine engine;
	private ISimulatorUI ui;
	private IVisualisation visualisation;
	private ArrivalPattern desiredPattern;
	private boolean isSimulationStarted = false;
	private QueueHistoryDao queueHistoryDao;
	private int w;
	private int h;

	public Controller(ISimulatorUI ui, String tableName) {
		this.ui = ui;
		this.w = w; // Add this line
		this.h = h;
		this.visualisation = ui.getVisualisation();
		this.queueHistoryDao = new QueueHistoryDao(tableName);
		this.visualisation = new Visualisation(w, h, this, ui);
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
			setSimulationSpeed((long) (currentDelay * 1.10));
		} else {
			System.out.println("Error: UI is not initialized.");
		}
	}


	@Override
	public void increaseSpeed() {
		if (engine != null) {
			setSimulationSpeed((long) (engine.getDelay() * 0.90));
		}
	}


	@Override
	public void showEndTime(double time) {
		Platform.runLater(() -> ui.setEndingTime(time));
	}

	@Override
	public void setSimulationSpeed(long delay) {
		if (engine != null) {
			engine.setDelay(delay);
			ui.setDelay(delay);
		}
	}

	@Override
	public void setArrivalPattern(ArrivalPattern pattern) {
		desiredPattern = pattern;
		if (engine != null) {
			engine.setArrivalPattern(pattern);
		}
		if (ui != null) {
			ui.setArrivalPattern(pattern);
		}
		System.out.println("Arrival pattern set to: " + describePattern(pattern));
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

	public void setEngine(MyEngine engine) {
		this.engine = engine;
	}

	private void recordQueueHistory(int customerId, String servicePointName, LocalDateTime arrivalTime, LocalDateTime departureTime, double queueTime) {
		QueueHistory history = new QueueHistory(0, customerId, servicePointName, arrivalTime, departureTime, queueTime);
		queueHistoryDao.persist(history);
	}


	private String getServicePointName(int servicePoint) {
		return "Service Point " + servicePoint;
	}

}