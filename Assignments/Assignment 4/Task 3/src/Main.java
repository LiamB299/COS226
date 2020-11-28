import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.io.FileInputStream;

public class Main {
    private static volatile Logger LOGGER = Logger.getLogger("global");
    public static void main(String[] args) {


		configureLogger();
        LOGGER.setLevel(Level.ALL);

        /*
        The scenario will need to run for the amount of runs
        specified in the ConsensusOptions.java file. You can overwrite
        the default ConsensusOptions values in this main method as follows:

            ConsensusOptions.delay = 50;

        This value will then reflect in all classes that access ConsensusOptions.delay.


        HINT - Make sure the thread IDs are 0 and 1 in each run.
        Take a look at the functions offered in ThreadID.java that you
        could use to enforce this.
        */

        CompareAndSwapConsensus stC = new CompareAndSwapConsensus(ConsensusOptions.threads);
        ConsensusThread t[];// = new ConsensusThread(stC);
        t = new ConsensusThread[ConsensusOptions.threads];
        for(int i = 0; i< ConsensusOptions.threads; i++)
            t[i] = new ConsensusThread(stC);

        for(int i = 0; i< ConsensusOptions.threads; i++)
            t[i].start();

        for(int i = 0; i< ConsensusOptions.threads; i++)
            try {
                t[i].join();
            }
            catch (InterruptedException e) {}

    }

    private static void configureLogger() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
