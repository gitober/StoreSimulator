package viewTests;

import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import view.IVisualisation;

import static org.mockito.Mockito.verify;

class IVisualisationTest {

    IVisualisation visualisation;

    @BeforeEach
    void setUp() {
        visualisation = Mockito.mock(IVisualisation.class);
    }

    @Test
    void clearDisplayShouldInvokeCorrectMethod() {
        visualisation.clearDisplay();
        verify(visualisation).clearDisplay();
    }

    @Test
    void newCustomerShouldInvokeCorrectMethod() {
        int servicePoint = 1;
        visualisation.newCustomer(servicePoint);
        verify(visualisation).newCustomer(servicePoint);
    }

    @Test
    void visualiseCustomerShouldInvokeCorrectMethod() {
        int servicePoint = 1;
        visualisation.visualiseCustomer(servicePoint);
        verify(visualisation).visualiseCustomer(servicePoint);
    }
}