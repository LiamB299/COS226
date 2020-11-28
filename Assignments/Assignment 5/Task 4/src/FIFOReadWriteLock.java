// Practical 5
// Name and surname:
// Student number:
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class FIFOReadWriteLock {

    private static volatile Logger LOGGER = Logger.getLogger("global");
    public volatile boolean writer;
    private volatile Lock rl, wl;
    public volatile Lock lock;
    public volatile Condition cond;
    public volatile int readAcquires, readReleases;

	public FIFOReadWriteLock() {
		writer = false;
		readAcquires = readReleases = 0;
		lock = new ReentrantLock(true);
        cond = lock.newCondition();
        rl = new ReadLock();
        wl = new WriteLock();
	}

	public Lock readLock() {
	    // @todo: return appropriate object
		return rl;
	}

	public Lock writeLock() {
	    // @todo: return appropriate object
		return wl;
	}


    /* -------------------------------------------------- *
     *      Nested ReadLock Class
     * -------------------------------------------------- */
    private class ReadLock extends AbstractLock {

        public void lock() {
            LOGGER.info("Thread "+ThreadID.get()+" wants to acquire the read lock");
            lock.lock();
            try {
                while(writer)
                    try {
                        cond.await();
                    }
                    catch (InterruptedException e) {}
                readAcquires++;
                LOGGER.info("Thread "+ThreadID.get()+" acquired the read lock");
            } finally {
                lock.unlock();
            }
        }

        public void unlock() {
            lock.lock();
            LOGGER.info("Thread "+ ThreadID.get()+" released the read lock");
            try {
                readReleases++;
                if(readAcquires == readReleases)
                    cond.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    /* -------------------------------------------------- *
     *      Nested WriteLock Class
     * -------------------------------------------------- */
    private class WriteLock extends AbstractLock {
        public void lock() {
            LOGGER.info("Thread "+ThreadID.get()+" wants to acquire the write lock");
            lock.lock();
            try {
                while(writer) {
                    try {
                        cond.await();
                    }
                    catch (InterruptedException e) {}
                }
                writer = true;
                while(readAcquires != readReleases)
                    try {
                        cond.await();
                    }
                    catch (InterruptedException e) {}
                LOGGER.info("Thread "+ThreadID.get()+" acquired the write lock");
            } finally {
                lock.unlock();
            }
        }

        public void unlock() {
            LOGGER.info("Thread "+ ThreadID.get()+" released the write lock");
            lock.lock();
            try {
                writer = false;
                cond.signalAll();
            }
            finally {
                lock.unlock();
            }
        }
    }
}
