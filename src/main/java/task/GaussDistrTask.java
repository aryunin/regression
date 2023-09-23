package task;

import analysis.util.CommonMethods;
import analysis.util.DistributionParams;
import com.sun.tools.javac.Main;
import generator.GaussGenerator;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class GaussDistrTask implements Runnable {
    final private double avg;
    final private double sig;
    final private long count;
    final private double cellWidth;
    final private GaussGenerator gaussGenerator = new GaussGenerator();

    public GaussDistrTask(double avg, double sig, long count, double cellWidth) {
        this.avg = avg;
        this.sig = sig;
        this.count = count;
        this.cellWidth = cellWidth;
    }

    @Override
    public void run() {
        // Генерируем СВ
        double[] xArr = gaussGenerator.generate(avg, sig, count);

        // Находим карманы
        double[] intervals = CommonMethods.getIntervals(xArr, cellWidth);

        // Находим частоты
        double[] freqs = CommonMethods.getFrequencies(xArr, intervals);

        // Находим параметры распределения
        DistributionParams params = new DistributionParams(xArr);

        // Выводим информацию
        showParams(params);
        freqs = Arrays.copyOfRange(freqs, 0, intervals.length);
        showChart(freqs, intervals);
    }


    private static void showParams(DistributionParams params) {
        synchronized (Main.class)
        {
            System.out.println("~ Gauss Distribution ~");
            System.out.println(params);
        }
    }

    private static void showChart(double[] yArr, double[] xArr) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        List<String> xArrFmt = Arrays.stream(xArr).mapToObj(decimalFormat::format).toList();
        List<Double> yArrFmt = Arrays.stream(yArr).boxed().toList();

        var chart = new CategoryChartBuilder().title("Gauss Distribution").xAxisTitle("X").yAxisTitle("Y").build();
        chart.addSeries("Initial", xArrFmt, yArrFmt);
        new SwingWrapper<>(chart).displayChart();
    }
}
