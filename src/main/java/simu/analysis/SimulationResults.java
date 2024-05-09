package simu.analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchart.*;

/**
 * The SimulationResults class provides methods to read simulation results from a file,
 * save the results to another file, create a graph based on the results, and save the graph as an image.
 */
public class SimulationResults {

    /**
     * The main method reads simulation results from a file and creates a graph based on the results.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Example: Read simulation results from a file and create a graph
        List<Integer> times = new ArrayList<>();
        List<Integer> queueLengths = readResultsFromFile("queue_results.txt", times);
        saveResultsAndCreateGraph(times, queueLengths);
    }

    /**
     * Saves the simulation results to a file and creates a graph based on the results.
     *
     * @param times        The list of times at which the queue lengths were recorded.
     * @param queueLengths The list of queue lengths corresponding to the times.
     */
    public static void saveResultsAndCreateGraph(List<Integer> times, List<Integer> queueLengths) {
        if (!times.isEmpty()) { // Check if times list is not empty
            saveResultsToFile(times, queueLengths);
            // Assuming you want to create a graph based on queue lengths, not times
            createGraph(queueLengths);
        } else {
            System.err.println("No simulation results available to save.");
        }
    }

    /**
     * Saves the simulation results to a file.
     *
     * @param times        The list of times at which the queue lengths were recorded.
     * @param queueLengths The list of queue lengths corresponding to the times.
     */
    private static void saveResultsToFile(List<Integer> times, List<Integer> queueLengths) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"))) {
            for (int i = 0; i < times.size(); i++) {
                // Format the time with two decimal places
                String formattedTime = String.format("%.2f", times.get(i) / 100.0); // Assuming times are in milliseconds, divide by 100 to convert to seconds
                writer.write("Time " + formattedTime + ": Queue length = " + queueLengths.get(i) + "\n");
            }
            // Add a separator between the results and the graph data
            writer.write("\n\n");
            writer.write("Graph data:"); // Add a label for the graph data
            writer.newLine();
            for (int i = 0; i < times.size(); i++) {
                writer.write(times.get(i) + "," + queueLengths.get(i) + "\n");
            }
            System.out.println("Results and graph data saved to file results.txt");
        } catch (IOException e) {
            System.err.println("Error saving results: " + e.getMessage());
        }
    }

    /**
     * Creates a graph based on the simulation results.
     *
     * @param data The list of queue lengths over time.
     */
    private static void createGraph(List<Integer> data) {
        // Prepare data for XChart
        List<Integer> xData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            xData.add(i);
        }

        // Create line chart
        XYChart chart = new XYChartBuilder().width(800).height(600)
                .title("Queue Behavior Over Time")
                .xAxisTitle("Time")
                .yAxisTitle("Queue Length")
                .build();

        // Add data to the chart
        chart.addSeries("Queue Length", xData, data);

        // Save graph to a file
        try {
            BitmapEncoder.saveBitmap(chart, "queue_behavior", BitmapEncoder.BitmapFormat.PNG);
            System.out.println("Chart saved to file queue_behavior.png");
        } catch (IOException e) {
            System.err.println("Error saving chart: " + e.getMessage());
        }
    }

    /**
     * Reads simulation results from a file.
     *
     * @param filename The name of the file containing simulation results.
     * @param times    The list to store the times at which the queue lengths were recorded.
     * @return The list of queue lengths corresponding to the recorded times.
     */
    private static List<Integer> readResultsFromFile(String filename, List<Integer> times) {
        List<Integer> queueLengths = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the format is "Time {time}: Queue length = {length}"
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String timePart = parts[0].trim();
                    // Extract time value and convert to Integer
                    int time = Integer.parseInt(timePart.substring(5).trim()); // Extract time value and remove leading whitespace
                    times.add(time);
                    String queueLengthPart = parts[1].trim();
                    // Extract queue length value and convert to Integer
                    int queueLength = Integer.parseInt(queueLengthPart.substring(queueLengthPart.indexOf("=") + 1).trim()); // Extract queue length value and remove leading whitespace
                    queueLengths.add(queueLength);
                }
            }
            System.out.println("Results read from file " + filename);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return queueLengths;
    }
}
