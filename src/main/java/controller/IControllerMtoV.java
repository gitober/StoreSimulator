/**
 * The IControllerMtoV interface represents methods for communication from the controller to the view.
 */
package controller;

public interface IControllerMtoV {
 /**
  * Visualizes the arrival of a customer at a service point.
  *
  * @param servicePoint The service point where the customer arrives.
  */
 void visualiseCustomer(int servicePoint);

 /**
  * Shows the end time of the simulation.
  *
  * @param time The end time of the simulation.
  */
 void showEndTime(double time);
}
