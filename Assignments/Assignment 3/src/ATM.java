import java.util.Random;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class ATM {
    volatile private static Logger LOGGER = Logger.getLogger("global");
    volatile private float balance;
    int nTh;
    volatile FilterLock myLock;
    volatile char [] group;

    public ATM(float amount, int numThreads) {
        group = new char[numThreads];
        for (int i=0; i<numThreads; i++)
            group[i] = 'x';
        balance = amount;
        nTh = numThreads;
        myLock = new FilterLock(numThreads);
        printLine();
    }

    public void sl(long time) {
        try {
            sleep(time);
        }
        catch (InterruptedException e) {}
    }

    private void printLine() {
        LOGGER.fine("Lines that use the ATM:");
        for(int i=0; i<nTh; i++) {
            LOGGER.fine("["+group[i]+"]");
        }
    }

    public void updateGroup(int threadID) {
        // set line
        group[threadID]='o';
        // check another line still needs to go
        for(int i=0; i<nTh; i++)
            if(group[i]=='x') {
                printLine();
                return;
            }
        // reset the array if all lines have gone
        printLine();
        LOGGER.fine("All lines used the ATM, resetting:");
        for(int i=0; i<nTh; i++) {
            group[i] = 'x';
            LOGGER.fine("[" + group[i] + "]");
        }
    }

    public boolean withdraw(float amount, int threadID) {
        while(group[threadID]=='o') {}
        myLock.lock();
        try {
            if (balance < amount) {
                LOGGER.info("Thread-" + threadID + " withdrawal of R" + String.format("%.2f", amount) + " not possible, insufficient funds. R" + String.format("%.2f", balance) + " remaining");
                return false;
            }
            else {
                balance -= amount;
                LOGGER.info("Thread-"+threadID+" withdrawing R"+String.format("%.2f", amount)+" from ATM, R"+String.format("%.2f", balance)+" remaining");
                return true;
            }
        }
        finally {
            LOGGER.fine("Thread-"+threadID+" exiting");
            updateGroup(threadID);
            myLock.unlock();
        }
    }

    public void deposit(float amount, int threadID) {
        while(group[threadID]=='o') {}
        myLock.lock();
        try {
            balance += amount;
            LOGGER.info("Thread-"+threadID+" depositing R"+String.format("%.2f", amount)+" to ATM, R"+String.format("%.2f", balance)+" remaining");
        }
        finally {
            LOGGER.fine("Thread-"+ThreadID.get()+" exiting");
            updateGroup(threadID);
            myLock.unlock();
        }
    }

    public float getATMBalance() {
        return balance;
    }
}
