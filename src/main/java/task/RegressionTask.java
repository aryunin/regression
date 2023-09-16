package task;

import analysis.LinearRegression;
import exception.DifferentArraySizesException;
import generator.UniformDistribution;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class RegressionTask implements Runnable {
    private double min;
    private double max;
    private double count;
    private DoubleUnaryOperator func;

    public RegressionTask(double min, double max, double count, DoubleUnaryOperator func) {
        this.min = min;
        this.max = max;
        this.count = count;
        this.func = func;
    }

    @Override
    public void run() {
        double[] xArr = new UniformDistribution(-1, 1, 3000).getArray();
        double[] noiseArr = new UniformDistribution(-1, 1, 3000).getArray();
        double[] yArr = Arrays.stream(xArr).map(func).toArray();
        yArr = addArrays(yArr, noiseArr);

        XYChart chart = QuickChart.getChart("Chart", "X", "Y", "Y = 5X + 7 + noise", xArr, yArr);

        LinearRegression regression = new LinearRegression(xArr, yArr);
        regression.calculate();
        logRegressionParams(regression);

        double[] approx = Arrays.stream(xArr).map(x -> regression.a * x + regression.b).toArray();

        chart.addSeries("Approximated", xArr, approx);

        new SwingWrapper<>(chart).displayChart();
    }

    private static double[] addArrays(double[] first, double[] second) {
        if(first.length != second.length)
            throw new DifferentArraySizesException("Разные размеры массивов");

        double[] res = new double[first.length];
        for(int i = 0; i < first.length; i++)
            res[i] = first[i] + second[i];

        return  res;
    }

    private static void logRegressionParams(LinearRegression regression) {
        System.out.println(
                "a = " + regression.a + "\n"
                        + "b = " + regression.b + "\n"
                        + "k = " + regression.k
        );
    }
}
