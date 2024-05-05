package viewTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.model.ArrivalPattern;
import view.ISimulatorUI;
import view.IVisualisation;

import static org.mockito.Mockito.*;

class ISimulatorUITest {

    ISimulatorUI simulatorUI;
    IVisualisation visualisation;

    @BeforeEach
    void setUp() {
        simulatorUI = mock(ISimulatorUI.class);
        visualisation = mock(IVisualisation.class);
    }

    @Test
    void getTimeShouldInvokeCorrectMethod() {
        simulatorUI.getTime();
        verify(simulatorUI).getTime();
    }

    @Test
    void getDelayShouldInvokeCorrectMethod() {
        simulatorUI.getDelay();
        verify(simulatorUI).getDelay();
    }

    @Test
    void getCustomerAmountShouldInvokeCorrectMethod() {
        simulatorUI.getCustomerAmount();
        verify(simulatorUI).getCustomerAmount();
    }

    @Test
    void setEndingTimeShouldInvokeCorrectMethod() {
        double time = 10.0;
        simulatorUI.setEndingTime(time);
        verify(simulatorUI).setEndingTime(time);
    }

    @Test
    void getVisualisationShouldInvokeCorrectMethod() {
        simulatorUI.getVisualisation();
        verify(simulatorUI).getVisualisation();
    }

    @Test
    void appendResultsShouldInvokeCorrectMethod() {
        String text = "Test";
        simulatorUI.appendResults(text);
        verify(simulatorUI).appendResults(text);
    }

    @Test
    void setDelayShouldInvokeCorrectMethod() {
        long delay = 1000L;
        simulatorUI.setDelay(delay);
        verify(simulatorUI).setDelay(delay);
    }

    @Test
    void setArrivalPatternShouldInvokeCorrectMethod() {
        ArrivalPattern pattern = ArrivalPattern.MORNINGRUSH;
        simulatorUI.setArrivalPattern(pattern);
        verify(simulatorUI).setArrivalPattern(pattern);
    }
}