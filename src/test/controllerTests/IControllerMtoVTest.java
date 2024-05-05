package controllerTests;

import controller.IControllerMtoV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class IControllerMtoVTest {

    IControllerMtoV controller;
    int servicePoint;
    double time;

    @BeforeEach
    void setUp() {
        controller = mock(IControllerMtoV.class);
        servicePoint = 1;
        time = 100.0;
    }

    @Test
    void visualiseCustomerShouldInvokeCorrectMethod() {
        controller.visualiseCustomer(servicePoint);
        verify(controller).visualiseCustomer(servicePoint);
    }

    @Test
    void showEndTimeShouldInvokeCorrectMethod() {
        controller.showEndTime(time);
        verify(controller).showEndTime(time);
    }
}