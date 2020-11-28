public class Customer extends Thread {

    Semaphore mySemaphore;

    Customer(Semaphore sema) {
        mySemaphore = sema;
    }

    void thread_sleep(long time) {
        try {
            sleep(time);
        }
        catch (InterruptedException e) {}
    }

    public void run() {
        thread_sleep((int) (Math.random() * ((50 - 10) + 1)) + 10);
        mySemaphore.acquire();
        // critical section
        thread_sleep((int) (Math.random() * ((50 - 10) + 1)) + 10);
        mySemaphore.release();
    }
}
