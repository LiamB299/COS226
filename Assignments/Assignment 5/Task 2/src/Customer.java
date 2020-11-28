public class Customer extends Thread {

    private volatile AndersonQueueLock mylock;

    Customer(AndersonQueueLock lock) {
        mylock = lock;
    }

    void thread_sleep(long time) {
        try {
            sleep(time);
        }
        catch (InterruptedException e) {}
    }

    public void run() {
        thread_sleep((ThreadID.get()+1) * 100 % 75);
        mylock.lock();
        thread_sleep((int) (Math.random() * ((50 - 10) + 1)) + 10);
        mylock.unlock();
    }
}
