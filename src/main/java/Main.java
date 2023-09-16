import task.RegressionTask;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread regressionThread = new Thread(new RegressionTask(-1, 1, 3000, x -> 7 * x + 5));
        regressionThread.start();
        regressionThread.join();
    }
}
