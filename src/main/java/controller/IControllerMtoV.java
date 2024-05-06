package controller;

/**
 * This interface represents the model to view controller in the MVC pattern.
 * It provides methods to visualize a customer at a service point and to show the end time of the simulation.
 */
public interface IControllerMtoV {

 /**
  * Visualizes a new customer at a service point.
  * @param servicePoint The service point where the customer arrives.
  */
 void visualiseCustomer(int servicePoint);

 /**
  * Shows the end time of the simulation.
  * @param time The end time of the simulation.
  */
 void showEndTime(double time);
}