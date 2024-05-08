package simu.framework;

/**
 * The Trace class provides a simple logging mechanism with different trace levels.
 */
public class Trace {
	/**
	 * Enum representing the different logging levels.
	 */
	public enum Level { INFO, WAR, ERR }

	private static Level traceLevel = Level.INFO; // Initialize traceLevel to a default value

	/**
	 * Sets the trace level for logging output.
	 * Only messages at or above this level will be displayed.
	 *
	 * @param lvl The trace level to be set.
	 */
	public static void setTraceLevel(Level lvl) {
		traceLevel = lvl;
	}

	/**
	 * Outputs a message to the console if the specified level is at or above the current trace level.
	 *
	 * @param lvl The level of the message.
	 * @param txt The message text to be output.
	 */
	public static void out(Level lvl, String txt) {
		if (lvl.ordinal() >= traceLevel.ordinal()) {
			System.out.println(txt);
		}
	}
}
