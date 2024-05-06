package simu.model;

/**
 * This enum represents the different arrival patterns for the simulator.
 * It includes MORNINGRUSH, MIDDAYLULL, AFTERNOONRUSH, and CONSTANT.
 */
public enum ArrivalPattern {
    /**
     * Represents the morning rush arrival pattern.
     */
    MORNINGRUSH,

    /**
     * Represents the midday lull arrival pattern.
     */
    MIDDAYLULL,

    /**
     * Represents the afternoon rush arrival pattern.
     */
    AFTERNOONRUSH,

    /**
     * Represents a constant arrival pattern.
     */
    CONSTANT;
}