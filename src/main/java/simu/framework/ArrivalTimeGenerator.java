package simu.framework;

import eduni.distributions.Distributions;

public class ArrivalTimeGenerator {
    private Distributions distributions;

    public ArrivalTimeGenerator(Distributions distributions) {
        this.distributions = distributions;
    }

    public double generateArrivalTime(double lambda) {
        return distributions.negexp(1.0 / lambda);
    }
}