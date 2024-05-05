package simu.framework;

import simu.model.ArrivalPattern;

public interface IEngine extends Runnable {
	void setSimulationTime(double time);
	void setDelay(long delay);
	long getDelay();
	void setArrivalPattern(ArrivalPattern pattern);
}