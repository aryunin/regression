package analysis.util;

import exception.EmptyArrayException;

import java.util.Arrays;

public final class DistributionParams {
    public double u1;
    public double u2;
    public double u3;
    public double u4;
    public double avg;
    public double sig;

    public DistributionParams(double[] arr) {
        if (arr == null || arr.length == 0)
            throw new EmptyArrayException("Empty data");

        calculate(arr);
    }

    private void calculate(double[] arr) {
        avg = Arrays.stream(arr).sum() / arr.length;
        u1 = Arrays.stream(arr).map(x -> x - avg).sum() / arr.length;
        u2 = Arrays.stream(arr).map(x -> Math.pow(x - avg, 2)).sum() / arr.length;
        u3 = Arrays.stream(arr).map(x -> Math.pow(x - avg, 3)).sum() / arr.length;
        u4 = Arrays.stream(arr).map(x -> Math.pow(x - avg, 4)).sum() / arr.length;
        sig = Math.sqrt(u2);
    }

    @Override
    public String toString() {
        return "DistributionParams{" +
                "u1=" + u1 +
                ", u2=" + u2 +
                ", u3=" + u3 +
                ", u4=" + u4 +
                ", avg=" + avg +
                ", sig=" + sig +
                '}';
    }
}
