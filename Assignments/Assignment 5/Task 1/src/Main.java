import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.io.FileInputStream;

public class Main {
    private static volatile Logger LOGGER = Logger.getLogger("global");
    public static void main(String[] args) {
        configureLogger();
        LOGGER.setLevel(Level.ALL);

        TTASLock lock = new TTASLock();
        Customer []cs = new Customer[CustomerOptions.customers];

        for(int i = 0; i< CustomerOptions.runs; i++) {
            for(int j=0; j<CustomerOptions.customers; j++)
                cs[j] = new Customer(lock);
            for(int j=0; j<CustomerOptions.customers; j++)
                cs[j].start();
            for(int j=0; j<CustomerOptions.customers; j++)
                try {
                    cs[j].join();
                }
                catch (InterruptedException e) {}
            ThreadID.reset();
        }
    }

    private static void configureLogger() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

