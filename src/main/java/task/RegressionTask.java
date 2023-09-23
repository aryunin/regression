package task;

import analysis.LinearRegression;
import analysis.util.LinearRegressionParams;
import com.sun.tools.javac.Main;
import exception.DifferentArraySizesException;
import generator.RandomGenerator;
import generator.impl.NoiseGenerator;
import generator.impl.UniformDistribution;
import org.knowm.xchart.*;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class RegressionTask implements Runnable {
    private final double min;
    private final double max;
    private final long count;
    private final DoubleUnaryOperator func;
    private final RandomGenerator uniformGenerator = new UniformDistribution();
    private final RandomGenerator noiseGenerator = new NoiseGenerator();

    public RegressionTask(double min, double max, long count, DoubleUnaryOperator func) {
        this.min = min;
        this.max = max;
        this.count = count;
        this.func = func;
    }

    @Override
    public void run() {
        // Генерация случайных величин
        double[] xArr = uniformGenerator.generate(min, max, count);
        double[] yArr = Arrays.stream(xArr).map(func).toArray();

        // Добавляем шум к Y
        try {
            yArr = addNoise(yArr);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }

        // Вычисляем параметры линейной регрессии
        LinearRegressionParams params = null;
        try {
            params = LinearRegression.calculate(xArr, yArr);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }

        // Аппроксимируем
        double[] yApprox = approximate(xArr, params);

        // Выводим
        showParams(params);
        showChart(xArr, yArr, yApprox);
    }

    private double[] addNoise(double[] arr) {
        double[] noiseArr = noiseGenerator.generate(-1, 1, count);
        return addArrays(arr, noiseArr);
    }

    private static double[] approximate(double[] xArr, LinearRegressionParams params) {
        return Arrays.stream(xArr).map(x -> params.a * x + params.b).toArray();
    }

    private static double[] addArrays(double[] first, double[] second) {
        if(first.length != second.length)
            throw new DifferentArraySizesException("Разные размеры массивов");

        double[] res = new double[first.length];
        for(int i = 0; i < first.length; i++)
            res[i] = first[i] + second[i];

        return  res;
    }

    private static void showChart(double[] xArr, double[] yArr, double[] yApprox) {
        XYChart chart = new XYChartBuilder().title("Linear Regression").xAxisTitle("X").yAxisTitle("Y").build();

        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setMarkerSize(3);

        chart.addSeries("Initial", xArr, yArr);

        XYSeries approximated = chart.addSeries("Approximated", xArr, yApprox);
        approximated.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        new SwingWrapper<>(chart).displayChart();
    }

    private static void showParams(LinearRegressionParams params) {
        synchronized (Main.class)
        {
            System.out.println("~ Linear Regression ~");
            System.out.println(params);
        }
    }
}
