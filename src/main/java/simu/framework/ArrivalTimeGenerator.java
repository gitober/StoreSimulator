package simu.framework;

import eduni.distributions.Distributions;

/**
 * The ArrivalTimeGenerator class is responsible for generating arrival times
 * based on an exponential distribution.
 */
public class ArrivalTimeGenerator {
    private Distributions distributions;

    /**
     * Constructs an ArrivalTimeGenerator object.
     *
     * @param distributions The distribution utility used to generate arrival times.
     */
    public ArrivalTimeGenerator(Distributions distributions) {
        this.distributions = distributions;
    }

    /**
     * Generates the next arrival time using an exponential distribution with the specified lambda.
     *
     * @param lambda The rate parameter (lambda) for the exponential distribution.
     * @return The generated arrival time.
     */
    public double generateArrivalTime(double lambda) {
        return distributions.negexp(1.0 / lambda);
    }
}
