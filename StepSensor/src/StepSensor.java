import Plot.PlotWindow;
import Plot.ScatterPlot;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StepSensor
{
    public static void main(String[] args) throws IOException
    {
        String data = readFile("step sensor data/4-100-step-running.csv");

        String [] lines = data.split("\n");

        ArrayList<Double> accX = getColumnAsList(lines, 0);
        ArrayList<Double> accY = getColumnAsList(lines, 1);
        ArrayList<Double> accZ = getColumnAsList(lines, 2);

        ScatterPlot plt = new ScatterPlot(100, 100, 1100, 700);

        for (int i = 0; i < accX.size(); i++)
        {
            double x = i;				// x-value is index of the value
            double y = accX.get(i);		// y-value is the value itself
            plt.plot(0, x, y).strokeColor("red").strokeWeight(2).style("-");
        }

        PlotWindow window = PlotWindow.getWindowFor(plt, 1200,800);
        window.show();

        ArrayList<Double> magnitudes = get3dMagnitude(accX, accY, accZ);
        ArrayList<Integer> peakIndexes = getPeakIndexes(magnitudes);
        ArrayList<Double> peakValues = getPeaks(magnitudes);

        ScatterPlot plt2 = new ScatterPlot(100, 100, 1100, 700);

        // Plot magnitudes as data set 0
        for (int i = 0; i < magnitudes.size(); i++)
        {
            double index = i;
            double value = magnitudes.get(i);
            plt2.plot(0, index, value).strokeColor("red").strokeWeight(2).style("-");
        }

        // Plot the peaks as data set 1
        for (int i = 0; i < peakIndexes.size(); i++)
        {
            double index = peakIndexes.get(i);
            double value = peakValues.get(i);
            plt2.plot(1, index, value).strokeColor("blue").strokeWeight(5).style(".");
        }

        PlotWindow window2 = PlotWindow.getWindowFor(plt2, 1200,800);
        window2.show();

        System.out.println(peakValues.size());
    }

    public static String readFile(String fileName) throws IOException
    {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static ArrayList<Double> getColumnAsList(String [] lines, int columnNumber)
    {
        ArrayList<Double> columnNumbers = new ArrayList<>();

        for(int i = 1; i < lines.length; i++)
        {
            String line = lines[i];

            String [] words = line.split(",");

            Double num = Double.parseDouble(words[columnNumber].trim());

            columnNumbers.add(num);
        }

        return columnNumbers;
    }

    public static ArrayList<Double> get3dMagnitude(ArrayList<Double> x, ArrayList<Double> y, ArrayList<Double> z)
    {
        ArrayList<Double> magnitudes = new ArrayList<>();

        for(int i = 0; i < x.size(); i++)
        {
            double mag = Math.sqrt(Math.pow(x.get(i), 2) + Math.pow(y.get(i), 2) + Math.pow(z.get(i), 2));
            magnitudes.add(mag);
        }

        return magnitudes;
    }

    public static ArrayList<Double> getPeaks(ArrayList<Double> magnitudes)
    {
        ArrayList<Double> peaks = new ArrayList<>();

        for(int i = 1; i < magnitudes.size() - 1; i++)
        {
            if((magnitudes.get(i) > magnitudes.get(i - 1)) && (magnitudes.get(i) > magnitudes.get(i + 1)) && (magnitudes.get(i) >= 50))
            {
                peaks.add(magnitudes.get(i));
            }
        }

        return peaks;
    }

    public static ArrayList<Integer> getPeakIndexes(ArrayList<Double> data)
    {
        ArrayList<Integer> indexes = new ArrayList<>();
        int index = 0;

        for(int i = 1; i < data.size() - 1; i++)
        {
            if((data.get(i) > data.get(i - 1)) && (data.get(i) > data.get(i + 1)) && (data.get(i) >= 50))
            {
                indexes.add(i);
            }
        }

        return indexes;
    }

    public static ArrayList<Double> getValuesAt(ArrayList<Integer> peakLocations, ArrayList<Double> data)
    {
        ArrayList<Double> yValues = new ArrayList<>();
        double val = 0;

        for(int i = 0; i < peakLocations.size(); i++)
        {
            val = data.get(i);
            yValues.add(val);
        }

        return yValues;
    }
}
