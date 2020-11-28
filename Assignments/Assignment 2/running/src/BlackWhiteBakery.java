import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;

public class BlackWhiteBakery implements Lock {
    volatile int numThreads;
    enum colors_set {
        Black,
        White
    }
    volatile private colors_set mycolor[];
    volatile private colors_set color;
    boolean flags[];
    volatile private int numbers[];
    private volatile static Logger LOGGER = Logger.getLogger("global");

    public BlackWhiteBakery(int n) {
        numThreads = n;
        mycolor = new colors_set[n];
        flags = new boolean[n];
        numbers = new int[n];
        color = colors_set.Black;
        for (int i=0; i<n; i++) {
            flags[i] = false;
            numbers[i] = 0;
            //mycolor[i] = color;
        }
    }

    private int max() {
        int max = 0;
        for (int i=0; i<numThreads; i++) {
            if (mycolor[i] == mycolor[ThreadID.get()]) {
                if (max < numbers[i]) max = numbers[i];
            }
        }
        return max;
    }

    private boolean lexigraph(int i, int j) {
        if(numbers[j]<numbers[i])
            return false;
        else if(numbers[i]==numbers[j])
            if(j < i)
                return false;
        return true;
    }

    private boolean go(int i) {
        for (int j=0; j<numThreads; j++) {

            if (i == j || numbers[j] == 0)
                continue;

            if (mycolor[i] != mycolor[j] && mycolor[i] == color) {
                LOGGER.finest("[LOCK]: assess priority => (id:" + j + ",number:" + numbers[j] + ",color:" + mycolor[j] + ") > (id:" + i + ",number:" + numbers[i] + ",color:" + mycolor[i] + ")");
                return false;
            } else if (mycolor[i] == mycolor[j]) {
                if (numbers[j] < numbers[i]) {
                    LOGGER.finest("[LOCK]: assess priority => (id:" + j + ",number:" + numbers[j] + ",color:" + mycolor[j] + ") > (id:" + i + ",number:" + numbers[i] + ",color:" + mycolor[i] + ")");
                    return false;
                } else if (numbers[j] == numbers[i]) {
                    if (j > i) {
                        LOGGER.finest("[LOCK]: assess priority => (id:" + j + ",number:" + numbers[j] + ",color:" + mycolor[j] + ") > (id:" + i + ",number:" + numbers[i] + ",color:" + mycolor[i] + ")");
                        return false;
                    }
                }
            }
            LOGGER.finest("3[LOCK]: assess priority => (id:" + i + ",number:" + numbers[i] + ",color:" + mycolor[i] + ") > (id:" + j + ",number:" + numbers[j] + ",color:" + mycolor[j] + ")");
        }
        return true;
    }

    @Override
    public void lock() {
        // doorway
        int i = ThreadID.get();
        flags[i] = true;
        mycolor[i] = color;
        numbers[i] = 1 + max();
        LOGGER.fine("[LOCK]: (id:"+i+",number:"+numbers[i]+",color:"+mycolor[i]+") => ticket assigned");
        flags[i] = false;
        // end doorway
        while (go(i)==false) {}
        LOGGER.finer("[LOCK]: (id:"+i+",number:"+numbers[i]+",color:"+mycolor[i]+") => entering CS");
    }

    @Override
    public void unlock() {
        if (mycolor[ThreadID.get()] == colors_set.Black) {
            LOGGER.finer("[LOCK]: (id:" + ThreadID.get() + ",number:" + numbers[ThreadID.get()] + ",color:" + mycolor[ThreadID.get()] + ") => left CS, shared color:"+colors_set.White);
            color = colors_set.White;
        }
        else {
            LOGGER.finer("[LOCK]: (id:" + ThreadID.get() + ",number:" + numbers[ThreadID.get()] + ",color:" + mycolor[ThreadID.get()] + ") => left CS, shared color:"+colors_set.Black);
            color = colors_set.Black;
        }
        numbers[ThreadID.get()] = 0;
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
