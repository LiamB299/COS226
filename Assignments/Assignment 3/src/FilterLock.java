import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;

public class FilterLock implements Lock {
    volatile private static Logger LOGGER = Logger.getLogger("global");
    volatile int n_levels;
    volatile int levels [];
    volatile int victim [];

    FilterLock(int levels) {
        this.n_levels = levels;
        this.levels = new int[n_levels];
        victim = new int[n_levels];
        for (int i=0; i<n_levels; i++) {
            this.levels[i] =0;
            //victim[i] =-1;
        }
    }

    public void WhoVictim(int j) {
        for(int i=n_levels-1; i>0; i--) {
            //if (victim[i] == -1)
            //    continue;
            if (victim[i] == j)
                continue;
            LOGGER.fine("Thread-" + victim[i] + " is a victim");
        }
    }

    public boolean AllOtherThreads(int i, int L) {
        for(int k=0; k<n_levels; k++) {
            if(k==i)
                continue;
            if (levels[k] >= L && victim[L] == i)
                return false;
        }
        return true;
    }

    public void sl(long time) {
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {}
    }

    public void lock() {
        int i = ThreadID.get();
        //long generatedLong = 1 + (long) (Math.random() * (150 - 1));
        //sl(generatedLong);
        LOGGER.fine("Thread-"+ThreadID.get()+" waiting to perform transaction");
        // L starts at 1 because n-1 levels
        for(int L =1; L<n_levels; L++) {
            levels[i] = L;
            victim[L] = i;
            while(AllOtherThreads(i, L)==false) {
            }
        }
        LOGGER.fine("Thread-"+ThreadID.get()+" performing transaction on the ATM(entering CS)");
        WhoVictim(i);
    }

    public void unlock() {
        int i = ThreadID.get();
        levels[i] =0;
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
