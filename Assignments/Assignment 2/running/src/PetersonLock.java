//Practical assignment 2
//Student Number:18015001
//Student Name:L.M. Burgess
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;
import java.util.logging.Level;

class PetersonLock implements Lock {

    volatile private boolean[] flags = new boolean[2];
    volatile private int victim;
    volatile private static Logger LOGGER = Logger.getLogger("global");

    public PetersonLock() {
        flags[0]=flags[1]=false;
        victim = -1;
    }

    public void lock() {
        int i = ThreadID.get();
        int j = 1-i;
        flags[i] = true;
        victim = i;
        LOGGER.fine("[Lock] Thread "+ i +" is victim");
        while(flags[j] && (victim==i)) {}
        LOGGER.fine("[Lock] Thread "+toString().valueOf(i)+" entering CS (thread "+victim+" is victim)");
    }

    public void unlock() {
        int i = ThreadID.get();
        LOGGER.fine("[Lock] Thread "+toString().valueOf(i)+" leaving CS");
        flags[i] = false;
    }

    // Any class implementing Lock must provide these methods
    public Condition newCondition() {
        throw new java.lang.UnsupportedOperationException();
    }

    public boolean tryLock(long time, TimeUnit unit)
            throws InterruptedException {
        throw new java.lang.UnsupportedOperationException();
    }

    public boolean tryLock() {
        throw new java.lang.UnsupportedOperationException();
    }

    public void lockInterruptibly() throws InterruptedException {
        throw new java.lang.UnsupportedOperationException();
    }
}