// Practical 5
// Name and surname:
// Student number:

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class TTASLock extends AbstractLock {
	private static volatile Logger LOGGER = Logger.getLogger("global");
	AtomicBoolean state = new AtomicBoolean(false);
	AtomicInteger hold = new AtomicInteger(-1);

	void thread_sleep(long time) {
		try {
			Thread.sleep(time);
		}
		catch (InterruptedException e) {}
	}

	public void lock() {
		LOGGER.info("Customer "+ThreadID.get()+" is trying to enter the store");
		while(true) {
			while(state.get()) {}
			if(!state.getAndSet(true)) {
				hold.set(ThreadID.get());
				LOGGER.info("Customer "+hold+" has entered the store");
				return;
			}
			else {
				LOGGER.info("Customer "+ThreadID.get()+" tried to enter the store but sees customer "+hold.get()
						+" is already in the store");
				thread_sleep(CustomerOptions.wait);
			}
		}


	}
	
 	public void unlock() {
 		LOGGER.info("Customer "+ThreadID.get()+" has left the store");
 		state.set(false);
 	}
}
