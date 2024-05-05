package view;

import javafx.scene.layout.Pane;

public abstract class IVisualisation extends Pane {
	public IVisualisation(double width, double height) {
		setPrefSize(width, height);
	}

	public abstract void clearDisplay();

	public abstract void newCustomer(int servicePoint);

	public abstract void visualiseCustomer(int servicePoint);
}