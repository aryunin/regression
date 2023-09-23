package analysis.util;

import exception.EmptyArrayException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonMethods {
    public static double[] getIntervals(double[] data, double width) {
        double min = Arrays.stream(data).min().orElseThrow(()->new EmptyArrayException("Empty data"));
        double max = Arrays.stream(data).max().orElseThrow(()->new EmptyArrayException("Empty data"));

        List<Double> intervals = new ArrayList<>();
        double currentCell = min + width;
        while(currentCell < max) {
            intervals.add(currentCell);
            currentCell += width;
        }

        return intervals.stream().mapToDouble(i -> i).toArray();
    }

    public static double[] getFrequencies(double[] data, double[] intervals) {
        if (data == null || data.length == 0)
            throw new EmptyArrayException("Empty data");
        if (intervals == null || intervals.length == 0)
            throw new EmptyArrayException("Empty intervals array");

        double[] res = new double[intervals.length + 1];

        for (double x : data) {
            int pos = 0;
            while (pos < intervals.length) {
                if (x <= intervals[pos])
                    break;
                pos++;
            }
            res[pos]++;
        }

        return res;
    }
}
