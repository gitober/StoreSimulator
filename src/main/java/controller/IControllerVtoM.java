package controller;

import simu.model.ArrivalPattern;

public interface IControllerVtoM {
	void startSimulation();
	void increaseSpeed();
	void decreaseSpeed();
	void setArrivalPattern(ArrivalPattern pattern);
}