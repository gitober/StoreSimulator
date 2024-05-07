package viewTests;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;
import view.Visualisation;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class VisualisationTest {

    private Visualisation visualisation;

    @BeforeEach
    public void setUp() {
        new JFXPanel(); // initializes JavaFX environment

        Platform.runLater(() -> {
            visualisation = new Visualisation(500, 500);
        });

        // Wait for JavaFX application thread to finish
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void clearDisplayShouldFillCanvasWithLightBlue() {
        Platform.runLater(() -> visualisation.clearDisplay());
        WaitForAsyncUtils.waitForFxEvents();

        Canvas canvas = (Canvas) visualisation.getPane().getChildren().get(0);
        assertEquals(Color.LIGHTBLUE, canvas.getGraphicsContext2D().getFill());
    }

    @Test
    public void newCustomerShouldThrowExceptionForInvalidServicePoint() {
        assertThrows(IllegalArgumentException.class, () -> {
            Platform.runLater(() -> visualisation.newCustomer(-1));
            WaitForAsyncUtils.waitForFxEvents();
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Platform.runLater(() -> visualisation.newCustomer(6));
            WaitForAsyncUtils.waitForFxEvents();
        });
    }

    @Test
    public void visualiseCustomerShouldAddCircleToPane() {
        Platform.runLater(() -> visualisation.visualiseCustomer(1));
        WaitForAsyncUtils.waitForFxEvents();

        long circleCount = visualisation.getPane().getChildren().stream()
                .filter(node -> node instanceof Circle)
                .count();
        assertEquals(1, circleCount);
    }

    @Test
    public void newCustomerShouldCreateCircleWithCorrectColor() {
        Platform.runLater(() -> visualisation.newCustomer(1));
        WaitForAsyncUtils.waitForFxEvents();

        Optional<Circle> circleOpt = visualisation.getPane().getChildren().stream()
                .filter(node -> node instanceof Circle)
                .map(node -> (Circle) node)
                .findFirst();

        Circle circle = circleOpt.orElseThrow(() -> new AssertionError("No Circle found"));
        assertEquals(Color.BLUE, circle.getFill());
    }

    @Test
    public void clearDisplayShouldClearCanvas() {
        Platform.runLater(() -> visualisation.clearDisplay());
        WaitForAsyncUtils.waitForFxEvents();

        Canvas canvas = (Canvas) visualisation.getPane().getChildren().get(0);
        assertEquals(Color.LIGHTBLUE, canvas.getGraphicsContext2D().getFill());
    }

    @Test
    public void newCustomerShouldHandleInvalidServicePoint() {
        assertThrows(IllegalArgumentException.class, () -> {
            Platform.runLater(() -> visualisation.newCustomer(-1));
            WaitForAsyncUtils.waitForFxEvents();
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Platform.runLater(() -> visualisation.newCustomer(6));
            WaitForAsyncUtils.waitForFxEvents();
        });
    }
}
