package frameworkTests;

import eduni.distributions.Distributions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import simu.framework.ArrivalTimeGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ArrivalTimeGeneratorTest {

    private ArrivalTimeGenerator arrivalTimeGenerator;
    private Distributions distributions;

    @BeforeEach
    void setUp() {
        distributions = Mockito.mock(Distributions.class);
        arrivalTimeGenerator = new ArrivalTimeGenerator(distributions);
    }

    @Test
    void generateArrivalTimeReturnsExpectedValue() {
        double lambda = 5.0;
        double expectedArrivalTime = 2.0;
        when(distributions.negexp(1.0 / lambda)).thenReturn(expectedArrivalTime);

        double actualArrivalTime = arrivalTimeGenerator.generateArrivalTime(lambda);

        assertEquals(expectedArrivalTime, actualArrivalTime);
    }

    @Test
    void generateArrivalTimeReturnsZeroWhenLambdaIsInfinity() {
        double lambda = Double.POSITIVE_INFINITY;
        double expectedArrivalTime = 0.0;
        when(distributions.negexp(1.0 / lambda)).thenReturn(expectedArrivalTime);

        double actualArrivalTime = arrivalTimeGenerator.generateArrivalTime(lambda);

        assertEquals(expectedArrivalTime, actualArrivalTime);
    }

    @Test
    void generateArrivalTimeReturnsInfinityWhenLambdaIsZero() {
        double lambda = 0.0;
        double expectedArrivalTime = Double.POSITIVE_INFINITY;
        when(distributions.negexp(1.0 / lambda)).thenReturn(expectedArrivalTime);

        double actualArrivalTime = arrivalTimeGenerator.generateArrivalTime(lambda);

        assertEquals(expectedArrivalTime, actualArrivalTime);
    }
}