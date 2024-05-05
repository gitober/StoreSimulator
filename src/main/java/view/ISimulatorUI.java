package view;

import simu.model.ArrivalPattern;

public interface ISimulatorUI {
	double getTime();
	long getDelay();
	int getCustomerAmount();
	void setEndingTime(double time);
	IVisualisation getVisualisation();
	void appendResults(String text);
	void setDelay(long delay);// Add this method

	void setArrivalPattern(ArrivalPattern arrivalPattern);
}
