public class Customer extends Thread {

    private volatile TTASLock mylock;

    Customer(TTASLock lock) {
        mylock = lock;
    }

    void thread_sleep(long time) {
        try {
            sleep(time);
        }
        catch (InterruptedException e) {}
    }


    public void run() {
        Integer value = (int) (Math.random() * ((50 - 10) + 1)) + 10;
        thread_sleep(value);
        mylock.lock();
        value = (int) (Math.random() * ((50 - 10) + 1)) + 10;
        thread_sleep(value);
        mylock.unlock();
    }
}
