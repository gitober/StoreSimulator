package controller;

/**
 * Interface defining methods that the simulation engine uses to communicate results and updates back to the UI.
 */
public interface IControllerMtoV {
	/**
	 * Notifies the UI about the simulation end time.
	 * @param time the end time of the simulation
	 */
	void showEndTime(double time);

	/**
	 * Notifies the UI to visualize a new customer event.
	 */
	void visualiseCustomer();
}
