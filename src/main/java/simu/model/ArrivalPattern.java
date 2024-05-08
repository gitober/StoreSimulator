package simu.model;

/**
 * The ArrivalPattern enum represents different patterns of customer arrivals during a simulation.
 */
public enum ArrivalPattern {
    /**
     * High frequency of customers arriving in the morning.
     */
    MORNINGRUSH,

    /**
     * Low frequency of customers arriving around midday.
     */
    MIDDAYLULL,

    /**
     * High frequency of customers arriving in the afternoon.
     */
    AFTERNOONRUSH,

    /**
     * Constant rate of customer arrivals.
     */
    CONSTANT;
}
