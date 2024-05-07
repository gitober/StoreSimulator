package controllerTests;

import controller.Controller;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import simu.framework.Trace;
import simu.model.ArrivalPattern;
import simu.model.MyEngine;
import view.ISimulatorUI;
import view.Visualisation;

import java.util.concurrent.CountDownLatch;
import org.junitpioneer.jupiter.RetryingTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ControllerTest extends ApplicationTest {
    private Controller controller;
    private ISimulatorUI mockUi;
    private Visualisation mockVisualisation;

    @BeforeEach
    void setUp() {
        new JFXPanel(); // initializes JavaFX environment
        mockUi = mock(ISimulatorUI.class);
        mockVisualisation = mock(Visualisation.class);
        when(mockUi.getVisualisation()).thenReturn(mockVisualisation);

        String controllerName = "TestController";
        controller = new Controller(mockUi, controllerName); // Pass the name as the second parameter

        Trace.setTraceLevel(Trace.Level.INFO);
    }


    @RetryingTest(3)
    void visualiseCustomer_WithValidServicePoint() throws InterruptedException {
        int servicePoint = 1;
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                Thread.sleep(1000); // wait for 1 second before calling visualiseCustomer
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controller.visualiseCustomer(servicePoint);
            latch.countDown();
        });
        latch.await();
        verify(mockVisualisation).newCustomer(servicePoint);
    }

    @Test
    void startSimulation_WithValidInputs() {
        when(mockUi.getCustomerAmount()).thenReturn(3);
        when(mockUi.getTime()).thenReturn(10.0);
        when(mockUi.getDelay()).thenReturn(100L);
        controller.startSimulation();
        assertTrue(controller.getIsSimulationStarted());
    }

    @Test
    void startSimulation_WithInvalidCustomerAmount() {
        when(mockUi.getCustomerAmount()).thenThrow(NumberFormatException.class);
        controller.startSimulation();
        assertTrue(controller.getIsSimulationStarted());
    }

    @Test
    void startSimulation_WithZeroDelay() {
        when(mockUi.getCustomerAmount()).thenReturn(3);
        when(mockUi.getTime()).thenReturn(10.0);
        when(mockUi.getDelay()).thenReturn(0L);
        controller.startSimulation();
        assertTrue(controller.getIsSimulationStarted());
    }

    @Test
    void decreaseSpeed_WithValidEngine() {
        // Arrange
        MyEngine mockEngine = mock(MyEngine.class);
        when(mockEngine.getDelay()).thenReturn(100L); // Set the initial delay to 100
        controller.setEngine(mockEngine); // Set the mock engine in the controller
        when(mockUi.getDelay()).thenReturn(100L); // Set the initial delay in UI to 100

        // Act
        controller.decreaseSpeed();

        // Assert
        verify(mockUi).setDelay((long) (100 * 1.10)); // Verify that setDelay was called with 110% of the initial delay
    }

    @Test
    void increaseSpeed_WithValidEngine() {
        // Arrange
        MyEngine mockEngine = mock(MyEngine.class);
        when(mockEngine.getDelay()).thenReturn(100L); // Set the initial delay to 100
        controller.setEngine(mockEngine); // Set the mock engine in the controller

        // Act
        controller.increaseSpeed();

        // Assert
        verify(mockUi).setDelay((long) (100 * 0.90)); // Verify that setDelay was called with 90% of the initial delay
    }

    @RetryingTest(3)
    void showEndTime_WithValidTime() throws InterruptedException {
        double endTime = 20.0;
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                Thread.sleep(1000); // wait for 1 second before calling showEndTime
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controller.showEndTime(endTime);
            latch.countDown();
        });
        latch.await();
        verify(mockUi).setEndingTime(endTime);
    }


    @Test
    void setArrivalPattern_WithValidPattern() {
        controller.setArrivalPattern(ArrivalPattern.MORNINGRUSH);
        verify(mockUi).setArrivalPattern(ArrivalPattern.MORNINGRUSH);
    }
}