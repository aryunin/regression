import task.GaussDistrTask;
import task.RegressionTask;
import task.UniformDistrTask;

// TODO по-хорошему, все нужно зарефакторить и добавить адекватную обработку исключений
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread uniformThread = new Thread(new UniformDistrTask(0, 1, 3000, 0.05));
        Thread gaussThread = new Thread(new GaussDistrTask(1, 1, 3000, 0.25));
        Thread regressionThread = new Thread(new RegressionTask(-1, 1, 3000, x -> 7 * x + 5));

        uniformThread.start();
        gaussThread.start();
        regressionThread.start();

        uniformThread.join();
        gaussThread.join();
        regressionThread.join();
    }
}
