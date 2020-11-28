import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;

public class Bakery implements Lock {
    private int num_threads;
    volatile boolean[] flags;
    volatile int label[];
    volatile private static Logger LOGGER = Logger.getLogger("global");

    public Bakery(int num_th) {
        num_threads = num_th;
        flags = new boolean[num_threads];
        label = new int[num_threads];
        for (int i=0; i< num_threads; i++) {
            flags[i] = false;
            label[i] = 0;
        }
    }

    public void lock() {
        int i = ThreadID.get();
        //doorway starts here
        flags[i] = true;
        label[i] = max()+1;
        LOGGER.fine("[LOCK]: (id:"+i+",label:"+ label[i]+") => ticket assigned");
        //doorway ends here
        while (this.go(i) == false) {}
        LOGGER.finer("[LOCK]: (id:"+i+",label:"+label[i]+") => entering CS");
    }

    public void unlock() {
        LOGGER.finer("[LOCK]: (id:"+ThreadID.get()+",label:"+label[ThreadID.get()]+") => left CS");
        flags[ThreadID.get()]=false;
    }

    public int max() {
        int max = label[0];
        for (int i=0; i<num_threads; i++) {
            if (label[i]>max) {
                max = label[i];
            }
        }
        return max;
    }

    public boolean go(int j) {
        for (int i=0; i<num_threads; i++) {
            if (flags[i]==true && (i != j)) {
                if (label[j] > label[i]) {
                    LOGGER.finest("[LOCK]: assess priority => (id:"+i+",label:"+label[i]+") > (id:"+ThreadID.get()+",label:"+ label[ThreadID.get()]+")");
                    return false;
                }
                if (label[j] == label[i]) {
                    if (j > i) {
                        LOGGER.finest("[LOCK]: assess priority => (id:" + i + ",label:" + label[i] + ") > (id:" + ThreadID.get() + ",label:" + label[ThreadID.get()] + ")");
                        return false;
                    }
                }
                LOGGER.finest("[LOCK]: assess priority => (id:"+j+",label:"+label[j]+") > (id:"+i+",label:"+ label[i]+")");
            }
        }
        return true;
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
