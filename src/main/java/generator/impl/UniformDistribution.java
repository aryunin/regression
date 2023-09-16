package generator.impl;

import generator.RandomGenerator;

import java.util.Random;

public final class UniformDistribution implements RandomGenerator {
    private final Random generator = new Random();

    @Override
    public double[] generate(double min, double max, long count) {
        return generator.doubles()
                .limit(count)
                .map(x -> min + (max - min)*x)
                .toArray();
    }
}
