package generator.impl;

import generator.RandomGenerator;

public class NoiseGenerator implements RandomGenerator {
    private final RandomGenerator generator = new UniformDistribution();

    @Override
    public double[] generate(double min, double max, long count) {
        return generator.generate(min, max, count);
    }
}
