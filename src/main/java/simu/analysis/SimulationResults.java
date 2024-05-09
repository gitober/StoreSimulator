package simu.analysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.knowm.xchart.*;

public class SimulationResults {

    public static void main(String[] args) {
        // Test the graphing functionality with dummy data
        List<Integer> queueLengths = List.of(10, 20, 30, 40, 20, 30, 40, 50, 30, 20, 10);
        saveResultsAndCreateGraph(queueLengths);
    }

    public static void saveResultsAndCreateGraph(List<Integer> data) {
        saveResultsToFile(data);
        createGraph(data);
    }

    private static void saveResultsToFile(List<Integer> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"))) {
            for (int i = 0; i < data.size(); i++) {
                writer.write("Aika " + i + ": Jonon pituus = " + data.get(i) + "\n");
            }
            System.out.println("Tulokset tallennettu tiedostoon results.txt");
        } catch (IOException e) {
            System.err.println("Virhe tallennettaessa tuloksia: " + e.getMessage());
        }
    }

    private static void createGraph(List<Integer> data) {
        // Valmistellaan data XChart-kirjastoa varten
        List<Integer> xData = new java.util.ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            xData.add(i);
        }

        // Luodaan viivakaavio (Line Chart)
        XYChart chart = new XYChartBuilder().width(800).height(600)
                .title("Jonojen käyttäytyminen ajan suhteen")
                .xAxisTitle("Aika")
                .yAxisTitle("Jonon pituus")
                .build();

        // Lisätään data kaavioon
        chart.addSeries("Jonon pituus", xData, data);

        // Tallennetaan kuva tiedostoon
        try {
            BitmapEncoder.saveBitmap(chart, "queue_behavior", BitmapEncoder.BitmapFormat.PNG);
            System.out.println("Kaavio tallennettu tiedostoon queue_behavior.png");
        } catch (IOException e) {
            System.err.println("Virhe tallennettaessa kaaviota: " + e.getMessage());
        }
    }
}
