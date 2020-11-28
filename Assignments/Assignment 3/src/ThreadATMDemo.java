import java.io.FileInputStream;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class ThreadATMDemo {
    private static volatile Logger LOGGER = Logger.getLogger("global");
    public static void main(String args[]) {
        LOGGER.setLevel(Level.ALL);
        configureLogger();

        int threads = TransactionOptions.lines;
        ATM atm = new ATM(1000, threads);
        Line line1 = new Line(threads, atm);
        //Line line2 = new Line(4, atm);
        //Line line3 = new Line(4, atm);
        line1.run();
        //line2.run();
        //line3.run();
    }

    private static void configureLogger() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
