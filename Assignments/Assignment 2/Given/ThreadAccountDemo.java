//Practical assignment 2
//Student Number:
//Student Name:

import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.io.FileInputStream;


class ThreadAccountDemo {
	private static final Logger LOGGER = Logger.getLogger("global");
	
	public static void main (String args[]) {

		// set up logger properties
		configureLogger();
		// set the types of log messages to be displayed
		// options (for this practical): OFF, INFO, FINE, FINER, FINEST, ALL
		LOGGER.setLevel(Level.FINEST);

		// examples of logging with different log levels
		// 	  (make sure to remove these lines before submitting your assignment)
		LOGGER.fine("This is a fine message");
		LOGGER.finer(String.format("(sample id:%d, sample name: %s)", 3, "SomeThread"));

		// set up shared Account
		Lock lock = new PetersonLock();
		float startingBalance = 2260;
		Account acc = new Account(startingBalance, lock);

		// specify your own option values for thread runs here
		TThreadOptions.numRuns = 1;
		TThreadOptions.withdrawAmount = 800;

		TThread t1 = new TThread(acc);
		t1.start();
		
		TThread t2 = new TThread(acc);
		t2.start();

	}

	private static void configureLogger() {
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}