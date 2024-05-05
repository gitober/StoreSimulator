package controllerTests;

import controller.IControllerVtoM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.model.ArrivalPattern;

import static org.mockito.Mockito.*;

class IControllerVtoMTest {

    IControllerVtoM controller;
    ArrivalPattern pattern;

    @BeforeEach
    void setUp() {
        controller = mock(IControllerVtoM.class);
        pattern = ArrivalPattern.CONSTANT; // Replace CONSTANT with a valid constant from ArrivalPattern
    }

    @Test
    void startSimulationShouldInvokeCorrectMethod() {
        controller.startSimulation();
        verify(controller).startSimulation();
    }

    @Test
    void increaseSpeedShouldInvokeCorrectMethod() {
        controller.increaseSpeed();
        verify(controller).increaseSpeed();
    }

    @Test
    void decreaseSpeedShouldInvokeCorrectMethod() {
        controller.decreaseSpeed();
        verify(controller).decreaseSpeed();
    }

    @Test
    void setArrivalPatternShouldInvokeCorrectMethod() {
        controller.setArrivalPattern(pattern);
        verify(controller).setArrivalPattern(pattern);
    }
}