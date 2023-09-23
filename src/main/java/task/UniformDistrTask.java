package task;

import analysis.util.CommonMethods;
import analysis.util.DistributionParams;
import com.sun.tools.javac.Main;
import generator.RandomGenerator;
import generator.impl.UniformDistribution;
import org.knowm.xchart.*;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class UniformDistrTask implements Runnable {
    final private double min;
    final private double max;
    final private long count;
    final private double cellWidth;
    final private RandomGenerator uniformGenerator = new UniformDistribution();

    public UniformDistrTask(double min, double max, long count, double cellWidth) {
        this.min = min;
        this.max = max;
        this.count = count;
        this.cellWidth = cellWidth;
    }

    @Override
    public void run() {
        double[] xArr;
        double[] intervals = null;
        double[] freqs = null;
        DistributionParams params = null;

        try {
            // Генерируем СВ
            xArr = uniformGenerator.generate(min, max, count);

            // Находим карманы
            intervals = CommonMethods.getIntervals(xArr, cellWidth);

            // Находим частоты
            freqs = CommonMethods.getFrequencies(xArr, intervals);

            // Находим параметры распределения
            params = new DistributionParams(xArr);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }

        // Выводим информацию
        showParams(params);
        freqs = Arrays.copyOfRange(freqs, 0, intervals.length);
        showChart(freqs, intervals);
    }

    private static void showParams(DistributionParams params) {
        synchronized (Main.class)
        {
            System.out.println("~ Uniform Distribution ~");
            System.out.println(params);
        }
    }

    private static void showChart(double[] yArr, double[] xArr) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        List<String> xArrFmt = Arrays.stream(xArr).mapToObj(decimalFormat::format).toList();
        List<Double> yArrFmt = Arrays.stream(yArr).boxed().toList();

        var chart = new CategoryChartBuilder().title("Uniform Distribution").xAxisTitle("X").yAxisTitle("Y").build();
        chart.addSeries("Initial", xArrFmt, yArrFmt);
        new SwingWrapper<>(chart).displayChart();
    }
}
