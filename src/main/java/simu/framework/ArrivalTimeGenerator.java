package simu.framework;

import eduni.distributions.ContinuousGenerator;

/**
 * Generates arrival times for simulation events.
 */
public class ArrivalTimeGenerator {
    private final ContinuousGenerator generator;

    /**
     * Constructs an ArrivalTimeGenerator with a given continuous generator.
     *
     * @param generator The continuous generator to use for generating arrival times.
     */
    public ArrivalTimeGenerator(ContinuousGenerator generator) {
        this.generator = generator;
    }

    /**
     * Generates the next arrival time based on the given index.
     *
     * @param index The index of the current event (not used here).
     * @return The next arrival time.
     */
    public double generateArrivalTime(int index) {
        return generator.sample();
    }
}
