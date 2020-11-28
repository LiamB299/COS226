import java.util.logging.Logger;

public class Customer extends Thread {

    private static volatile Logger LOGGER = Logger.getLogger("global");
    FIFOReadWriteLock RWLock;
    private volatile Boolean isWrite;

    Customer(FIFOReadWriteLock lock, Boolean isWriter) {
        RWLock = lock;
        isWrite = isWriter;
    }

    void thread_sleep(long time) {
        try {
            sleep(time);
        }
        catch (InterruptedException e) {}
    }

    public void run() {
        if(isWrite) {
            LOGGER.fine("Thread "+ThreadID.get()+" is a writer");
            RWLock.writeLock().lock();
        }
        else {
            LOGGER.fine("Thread "+ThreadID.get()+" is a reader");
            RWLock.readLock().lock();
        }

        thread_sleep((int) (Math.random() * ((50 - 10) + 1)) + 10);
        // critical section

        if(isWrite)
            RWLock.writeLock().unlock();
        else
            RWLock.readLock().unlock();
    }
}
