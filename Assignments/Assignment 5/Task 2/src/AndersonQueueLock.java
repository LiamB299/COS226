// Practical 5
// Name and surname:
// Student number:

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class AndersonQueueLock extends AbstractLock {

    private volatile boolean flags[];
    private volatile AtomicInteger tail;
    private volatile ThreadLocal<Integer> slot = new ThreadLocal<Integer>() {
        protected Integer initialValue() {
            return 0;
        }
    };

    private static volatile Logger LOGGER = Logger.getLogger("global");
    private int numThreads;

    public AndersonQueueLock(int numThreads) {
        tail = new AtomicInteger(0);
        this.numThreads = numThreads;
        flags = new boolean[numThreads];
        flags[0] = true;
        for(int i=1; i<numThreads; i++)
            flags[i] = false;
    }

    public void lock() {
        int slt = tail.getAndIncrement() % numThreads;
        slot.set(slt);
        LOGGER.fine("Thread "+ThreadID.get()+" has slot "+slt);
        while(!flags[slt]) {}
        LOGGER.info("Customer "+ThreadID.get()+" enters the store");
    }


    public void unlock() {
        LOGGER.info("Customer "+ThreadID.get()+" leaves the store");
        flags[slot.get()] = false;
        flags[(slot.get()+1)%numThreads] = true;
    }

}
