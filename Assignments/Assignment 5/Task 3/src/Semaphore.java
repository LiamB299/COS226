import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Semaphore {

    private static volatile Logger LOGGER = Logger.getLogger("global");
    private final int cap;
    private volatile int in_store; //state
    private volatile Lock mylock;
    private volatile Condition cond;

    Semaphore(int capacity) {
        cap = capacity;
        in_store=0;
        mylock = new ReentrantLock();
        cond = mylock.newCondition();
    }

    public void acquire() {
        mylock.lock();
        LOGGER.info("Customer "+ThreadID.get()+" is trying to enter the store");
        LOGGER.fine("Available spaces in the store: "+(cap-in_store));
        try {
            while(in_store==cap) {
                LOGGER.info("Customer " + ThreadID.get() + " tried to enter the store but sees the store is at its capacity");
                    try {
                        cond.await();
                    }
                    catch (InterruptedException e) {}
            }
            //LOGGER.fine("Available spaces in the store: "+(cap-in_store));
            in_store++;
            LOGGER.info("Customer "+ThreadID.get()+" has entered the store");
        }
        finally {
            mylock.unlock();
        }
    }

    public void release() {
        mylock.lock();
        LOGGER.info("Customer "+ThreadID.get()+" has left the store");
        try {
            in_store--;
            cond.signalAll();
        }
        finally {
            mylock.unlock();
        }
    }
}
