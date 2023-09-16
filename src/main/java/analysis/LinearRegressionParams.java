package analysis;

public final class LinearRegressionParams {
    public final double a;
    public final double b;
    public final double k;

    public LinearRegressionParams(double a, double b, double k) {
        this.a = a;
        this.b = b;
        this.k = k;
    }

    @Override
    public String toString() {
        return "LinearRegressionParams{" +
                "a=" + a +
                ", b=" + b +
                ", k=" + k +
                '}';
    }
}
