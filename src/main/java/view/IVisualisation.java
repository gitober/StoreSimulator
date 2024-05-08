/**
 * Package containing the user interface components and visualization tools.
 */
package view;

import javafx.scene.layout.Pane;

/**
 * An abstract base class for visualizing the simulation.
 * Extends the JavaFX {@link Pane} class to provide a custom visualization area.
 */
public abstract class IVisualisation extends Pane {

	/**
	 * Constructs a new visualization pane with the specified width and height.
	 *
	 * @param width  The width of the visualization pane.
	 * @param height The height of the visualization pane.
	 */
	public IVisualisation(double width, double height) {
		setPrefSize(width, height);
	}

	/**
	 * Clears the display, removing all visual elements.
	 */
	public abstract void clearDisplay();

	/**
	 * Adds a new customer to the display at the specified service point.
	 *
	 * @param servicePoint The service point number where the new customer is to be displayed.
	 */
	public abstract void newCustomer(int servicePoint);

	/**
	 * Visualizes the customer at the specified service point.
	 *
	 * @param servicePoint The service point number where the customer is to be visualized.
	 */
	public abstract void visualiseCustomer(int servicePoint);
}
