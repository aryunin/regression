package analysis;

import exception.DifferentArraySizesException;
import exception.EmptyArrayException;

import java.util.Arrays;

public class LinearRegression {
    public double a = 0.0;
    public double b = 0.0;
    public double k = 0.0;

    private double[] xArr;
    private double[] yArr;

    public LinearRegression(double[] xArr, double[] yArr) {
        setInitials(xArr, yArr);
    }

    public void setInitials(double[] xArr, double[] yArr) {
        if(xArr.length != yArr.length)
            throw new DifferentArraySizesException("Разные размеры массивов");

        this.xArr = xArr;
        this.yArr = yArr;
    }

    public void calculate() {
        double mx = Arrays.stream(xArr).average().orElseThrow(EmptyArrayException::new);
        double my = Arrays.stream(yArr).average().orElseThrow(EmptyArrayException::new);

        double[] tmp = new double[xArr.length];

        // коэффициент корреляции
        for(int i = 0; i < xArr.length; i++)
            tmp[i] = (xArr[i] - mx) * (yArr[i] - my);
        k = Arrays.stream(tmp).sum() / xArr.length;

        // коэффициент a
        for(int i = 0; i < xArr.length; i++)
            tmp[i] = (xArr[i] - mx) * (xArr[i] - mx);
        double d = Arrays.stream(tmp).sum() / xArr.length;
        a = k / d;

        // коэффициент b
        b = my - a * mx;
    }
}
