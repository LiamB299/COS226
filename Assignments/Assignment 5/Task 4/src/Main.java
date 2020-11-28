import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.io.FileInputStream;

public class Main {
    private static volatile Logger LOGGER = Logger.getLogger("global");
    public static void main(String[] args) {
        configureLogger();
        LOGGER.setLevel(Level.ALL);

        int total = CustomerOptions.writers + CustomerOptions.readers;

        FIFOReadWriteLock RW = new FIFOReadWriteLock();
        Customer cs[] = new Customer[total];

        for(int i=0; i<CustomerOptions.runs; i++) {
            for(int j=0; j<CustomerOptions.readers; j++) {
                cs[j] = new Customer(RW, false);
            }
            for(int j = CustomerOptions.readers; j<(CustomerOptions.writers+CustomerOptions.readers); j++) {
                cs[j] = new Customer(RW, true);
            }
            for(int j=0; j<total; j++) {
                cs[j].start();
            }
            for(int j=0; j<total; j++) {
                try {
                    cs[j].join();
                }
                catch (InterruptedException e) {}
            }
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
