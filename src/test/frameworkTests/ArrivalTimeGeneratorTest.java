package frameworkTests;

import eduni.distributions.ContinuousGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import simu.framework.ArrivalTimeGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ArrivalTimeGeneratorTest {

    private ArrivalTimeGenerator arrivalTimeGenerator;
    private ContinuousGenerator continuousGenerator;

    @BeforeEach
    void setUp() {
        continuousGenerator = Mockito.mock(ContinuousGenerator.class);
        arrivalTimeGenerator = new ArrivalTimeGenerator(continuousGenerator);
    }

    @Test
    void generateArrivalTimeReturnsExpectedValue() {
        int index = 0; // Index is not used, but required as parameter
        double expectedArrivalTime = 2.0;
        when(continuousGenerator.sample()).thenReturn(expectedArrivalTime);

        double actualArrivalTime = arrivalTimeGenerator.generateArrivalTime(index);

        assertEquals(expectedArrivalTime, actualArrivalTime);
    }
}
