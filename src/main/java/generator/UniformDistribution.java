package generator;

import java.util.Random;

public final class UniformDistribution {
    private final double[] array;

    public UniformDistribution(double min, double max, long count) {
        this.array = generate(min, max, count);
    }

    public double[] getArray() {
        return array;
    }

    private static double[] generate(double min, double max, long count) {
        return new Random().doubles()
                .limit(count)
                .map(x -> min + (max - min)*x)
                .toArray();
    }
}
