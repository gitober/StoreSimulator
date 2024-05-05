package frameworkTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import simu.framework.Trace;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TraceTest {

    @BeforeEach
    void setUp() {
        Trace.setTraceLevel(Trace.Level.INFO);
    }

    @Test
    void shouldPrintWhenLevelIsEqualToTraceLevel() {
        // Capture the System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Trace.out(Trace.Level.INFO, "Test message");

        assertTrue(outContent.toString().contains("Test message"));
    }

    @Test
    void shouldPrintWhenLevelIsHigherThanTraceLevel() {
        // Capture the System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Trace.setTraceLevel(Trace.Level.INFO);
        Trace.out(Trace.Level.WAR, "Test message");

        assertTrue(outContent.toString().contains("Test message"));
    }

    @Test
    void shouldNotPrintWhenLevelIsLowerThanTraceLevel() {
        // Capture the System.out content
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Trace.setTraceLevel(Trace.Level.ERR);
        Trace.out(Trace.Level.INFO, "Test message");

        assertFalse(outContent.toString().contains("Test message"));
    }
}