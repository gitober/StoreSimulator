package simu.framework;

import eduni.distributions.Distributions;

/**
 * This class represents a generator for arrival times in the simulation.
 * It provides a method to generate an arrival time based on a given lambda value.
 */
public class ArrivalTimeGenerator {
    private Distributions distributions;

    /**
     * Constructs a new ArrivalTimeGenerator with the given distributions.
     * @param distributions The distributions for the arrival time generator.
     */
    public ArrivalTimeGenerator(Distributions distributions) {
        this.distributions = distributions;
    }

    /**
     * Generates an arrival time based on the given lambda value.
     * @param lambda The lambda value for the negative exponential distribution.
     * @return The generated arrival time.
     */
    public double generateArrivalTime(double lambda) {
        return distributions.negexp(1.0 / lambda);
    }
}