package viewTests;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.util.WaitForAsyncUtils;
import view.Visualisation;

import javafx.embed.swing.JFXPanel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VisualisationTest {

    private Visualisation visualisation;

    @BeforeEach
    public void setUp() {
        // Start JavaFX runtime
        new JFXPanel();

        Platform.runLater(() -> {
            visualisation = new Visualisation(500, 500);
            visualisation.newCustomer(1);
        });

        // Wait for JavaFX application thread to finish
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void clearDisplayShouldFillCanvasWithLightBlue() {
        visualisation.clearDisplay();
        Color expectedColor = Color.LIGHTBLUE;
        Color actualColor = (Color) visualisation.getGraphicsContext2D().getFill();
        assertEquals(expectedColor, actualColor);
    }

    @Test
    public void visualiseCustomerShouldAddCircleToPane() {
        int initialPaneChildrenSize = visualisation.getPane().getChildren().size();
        visualisation.visualiseCustomer(1);
        int finalPaneChildrenSize = visualisation.getPane().getChildren().size();
        assertEquals(initialPaneChildrenSize + 1, finalPaneChildrenSize);
    }

    @Test
    public void newCustomerShouldThrowExceptionForInvalidServicePoint() {
        assertThrows(IllegalArgumentException.class, () -> visualisation.newCustomer(0));
        assertThrows(IllegalArgumentException.class, () -> visualisation.newCustomer(5));
    }

    @Test
    public void clearDisplayShouldClearCanvas() {
        visualisation.clearDisplay();
        // Assert that the canvas is cleared (e.g., by checking the color of a pixel)
    }

    @Test
    public void visualiseCustomerShouldCreateNewCustomer() {
        visualisation.visualiseCustomer(1);
        // Assert that a new customer is created (e.g., by checking the number of children in the pane)
    }

    @Test
    public void newCustomerShouldCreateCircleWithCorrectColor() {
        visualisation.newCustomer(1);
        // Assert that a new circle is created with the correct color
    }

    @Test
    public void newCustomerShouldAddCircleToPane() {
        int initialPaneChildrenSize = visualisation.getPane().getChildren().size();
        visualisation.newCustomer(1);
        int finalPaneChildrenSize = visualisation.getPane().getChildren().size();
        assertEquals(initialPaneChildrenSize + 1, finalPaneChildrenSize);
    }

    @Test
    public void newCustomerShouldHandleInvalidServicePoint() {
        assertThrows(IllegalArgumentException.class, () -> {
            visualisation.newCustomer(5); // This should throw an IllegalArgumentException
        });
    }
}