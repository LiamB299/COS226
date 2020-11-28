//Practical assignment 2
//Student Number:
//Student Name:

import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;
import java.util.logging.Level;

class Account {

    float balance;
    Lock mylock;
    private volatile static Logger LOGGER = Logger.getLogger("global");

    Account(float amount, Lock mylock) {
        balance = amount;
        this.mylock = mylock;
    }

    boolean withdraw(float amount, String threadName) {
        mylock.lock();
        boolean bs;
            if (balance > amount) {
                bs = true;
                balance -= amount;
                LOGGER.info(threadName+" R"+String.format("%,.2f", amount)+" withdrawn from account, R"+String.format("%,.2f", balance)+" remaining");
            }
            else {
                bs = false;
                LOGGER.info(threadName+" Not enough money, R"+String.format("%,.2f", balance)+" remaining");
            }
            mylock.unlock();
            return bs; //isSuccess;
    }

    void deposit(float amount, String threadName) {
        mylock.lock();
        try {
            balance += amount;
            LOGGER.info(threadName+" R"+String.format("%,.2f", amount)+" deposited into account, R"+String.format("%,.2f", balance)+" remaining");
        }
        finally {
            mylock.unlock();
        }
    }

    float getBalance()
    {
        return balance;
    }
}