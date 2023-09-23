package generator;

import java.util.Random;
import java.util.stream.Stream;

public class GaussGenerator {
    private final Random random = new Random();

    public double[] generate(double avg, double sig, long count) {
        return Stream.generate(() -> random.nextGaussian()*sig + avg)
                .limit(count)
                .mapToDouble(x -> x)
                .toArray();
    }
}
