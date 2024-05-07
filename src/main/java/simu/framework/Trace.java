package simu.framework;

/**
 * The Trace class provides functionality for logging messages with different levels.
 */
public class Trace {
	/**
	 * Enumeration representing different log levels: INFO, WARNING, ERROR.
	 */
	public enum Level {
		/** Informational level. */
		INFO,
		/** Warning level. */
		WAR,
		/** Error level. */
		ERR
	}

	private static Level traceLevel = Level.INFO; // Initialize traceLevel to a default value

	/**
	 * Sets the trace level for logging.
	 *
	 * @param lvl The trace level to be set.
	 */
	public static void setTraceLevel(Level lvl){
		traceLevel = lvl;
	}

	/**
	 * Logs a message with the specified level if it is equal to or greater than the current trace level.
	 *
	 * @param lvl The level of the log message.
	 * @param txt The text of the log message.
	 */
	public static void out(Level lvl, String txt){
		if (lvl.ordinal() >= traceLevel.ordinal()){
			System.out.println(txt);
		}
	}
}
