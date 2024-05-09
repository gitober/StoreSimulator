package simu.analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchart.*;

public class SimulationResults {

    public static void main(String[] args) {
        // Example: Read simulation results from a file and create a graph
        List<Integer> queueLengths = readResultsFromFile("queue_results.txt");
        saveResultsAndCreateGraph(queueLengths);
    }

    public static void saveResultsAndCreateGraph(List<Integer> data) {
        saveResultsToFile(data);
        createGraph(data);
    }

    private static void saveResultsToFile(List<Integer> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"))) {
            for (int i = 0; i < data.size(); i++) {
                writer.write("Time " + i + ": Queue length = " + data.get(i) + "\n");
            }
            System.out.println("Results saved to file results.txt");
        } catch (IOException e) {
            System.err.println("Error saving results: " + e.getMessage());
        }
    }

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

    private static List<Integer> readResultsFromFile(String filename) {
        List<Integer> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the format is "Time {i}: Queue length = {length}"
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    data.add(Integer.parseInt(parts[1].trim()));
                }
            }
            System.out.println("Results read from file " + filename);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return data;
    }
}
