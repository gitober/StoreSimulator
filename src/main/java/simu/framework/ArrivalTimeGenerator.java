package simu.framework;

import eduni.distributions.Distributions;

public class ArrivalTimeGenerator {
    private Distributions distributions;

    public ArrivalTimeGenerator() {
        distributions = new Distributions();
    }

    public double generateArrivalTime(double lambda) {
        return distributions.negexp(1.0 / lambda);
    }
}