package view;

import javafx.scene.layout.Pane;

/**
 * This abstract class represents a visualisation in the user interface.
 * It extends the Pane class from JavaFX and provides methods to clear the display, add a new customer, and visualise a customer at a service point.
 */
public abstract class IVisualisation extends Pane {

	/**
	 * Constructs a new IVisualisation with the given width and height.
	 * @param width The width of the visualisation.
	 * @param height The height of the visualisation.
	 */
	public IVisualisation(double width, double height) {
		setPrefSize(width, height);
	}

	/**
	 * Clears the display of the visualisation.
	 */
	public abstract void clearDisplay();

	/**
	 * Adds a new customer to the visualisation at a service point.
	 * @param servicePoint The service point where the customer is added.
	 */
	public abstract void newCustomer(int servicePoint);

	/**
	 * Visualises a customer at a service point in the visualisation.
	 * @param servicePoint The service point where the customer is visualised.
	 */
	public abstract void visualiseCustomer(int servicePoint);
}