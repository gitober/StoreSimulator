package simu.framework;

import eduni.distributions.Distributions;

/**
 * The ArrivalTimeGenerator class generates arrival times based on a specified distribution.
 */
public class ArrivalTimeGenerator {
    private Distributions distributions;

    /**
     * Constructs an ArrivalTimeGenerator object with the specified distribution.
     *
     * @param distributions The distribution to be used for generating arrival times.
     */
    public ArrivalTimeGenerator(Distributions distributions) {
        this.distributions = distributions;
    }

    /**
     * Generates an arrival time based on the specified lambda parameter.
     *
     * @param lambda The rate parameter (λ) of the exponential distribution.
     * @return The generated arrival time.
     */
    public double generateArrivalTime(double lambda) {
        return distributions.negexp(1.0 / lambda);
    }
}
