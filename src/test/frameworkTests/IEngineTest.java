package frameworkTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.framework.IEngine;
import simu.model.ArrivalPattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IEngineTest {

    private IEngine engine;
    private ArrivalPattern pattern;

    @BeforeEach
    void setUp() {
        engine = mock(IEngine.class); // Create a mock IEngine
        pattern = ArrivalPattern.CONSTANT; // Replace CONSTANT with a valid constant from ArrivalPattern
    }

    @Test
    void setAndGetSimulationTime() {
        engine.setSimulationTime(10.0);
        verify(engine).setSimulationTime(10.0); // Verify that setSimulationTime was called with 10.0
    }

    @Test
    void setAndGetDelay() {
        engine.setDelay(1000);
        verify(engine).setDelay(1000); // Verify that setDelay was called with 1000
    }

    @Test
    void setAndGetArrivalPattern() {
        engine.setArrivalPattern(pattern);
        verify(engine).setArrivalPattern(pattern); // Verify that setArrivalPattern was called with pattern
    }
}