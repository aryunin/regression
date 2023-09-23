package analysis;

import analysis.util.LinearRegressionParams;
import exception.DifferentArraySizesException;
import exception.EmptyArrayException;

import java.util.Arrays;

public class LinearRegression {
    public static LinearRegressionParams calculate(double[] xArr, double[] yArr) {
        if(xArr.length != yArr.length)
            throw new DifferentArraySizesException("Разные размеры массивов");

        double mx = Arrays.stream(xArr).average().orElseThrow(() -> new EmptyArrayException("Нет данных"));
        double my = Arrays.stream(yArr).average().orElseThrow(() -> new EmptyArrayException("Нет данных"));

        double[] tmp = new double[xArr.length];

        // коэффициент корреляции
        for(int i = 0; i < xArr.length; i++)
            tmp[i] = (xArr[i] - mx) * (yArr[i] - my);
        double k = Arrays.stream(tmp).sum() / xArr.length;

        // коэффициент a
        for(int i = 0; i < xArr.length; i++)
            tmp[i] = (xArr[i] - mx) * (xArr[i] - mx);
        double d = Arrays.stream(tmp).sum() / xArr.length;
        double a = k / d;

        // коэффициент b
        double b = my - a * mx;

        return new LinearRegressionParams(a, b, k);
    }
}
