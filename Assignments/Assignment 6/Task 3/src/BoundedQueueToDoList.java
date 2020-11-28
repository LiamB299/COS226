import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class BoundedQueueToDoList {

    private static volatile Logger LOGGER = Logger.getLogger("global");
    private volatile AtomicInteger size;
    private volatile Integer capacity;
    private volatile Task head;//, tail;
    private volatile Lock enqLock, deqLock;
    private volatile Condition NotEmpty, NotFull;

    public BoundedQueueToDoList(int capacity, String[] tasks) {
        this.capacity = capacity;
        size = new AtomicInteger(tasks.length);
        head = new Task("HEAD");

        enqLock = new ReentrantLock();
        deqLock = new ReentrantLock();

        NotEmpty = deqLock.newCondition();
        NotFull = enqLock.newCondition();

        Task iter = head;
        for(int i=0; i<tasks.length; i++) {
            iter.setNextTask(new Task(tasks[i]));
            iter = iter.getNextTask();
        }
        //tail = iter;
    }

    public void enq(String taskName) {
        boolean mustwake = false;
        enqLock.lock();
        try {
            while(size.get() == capacity)
                try {
                    NotFull.await();
                } catch (InterruptedException e) {}
            Task T = new Task(taskName);
            //tail.setNextTask(T);
            //tail = T;
            Task iter = head;
            while(iter.getNextTask()!=null) {
                iter = iter.getNextTask();
                if(iter.getTaskName().equals(taskName)) {
                    LOGGER.info("Group member "+ThreadID.get()+" could not add task "+taskName+" as it is already in the list");
                    return;
                }
            }
            iter.setNextTask(T);
            LOGGER.info("Group member "+ThreadID.get()+" added task "+taskName);
            if(size.getAndIncrement() == 0)
                mustwake = true;
        } finally {
            enqLock.unlock();
        }
        if(mustwake) {
            deqLock.lock();
            try {
                NotEmpty.signalAll();
            } finally {
                deqLock.unlock();
            }
        }
    }

    public void deq() {
        boolean mustwake = false;
        deqLock.lock();
        try {
            while(size.get() == 0)
                try {
                    NotEmpty.await();
                } catch(InterruptedException e) {}
            LOGGER.info("Group member "+ThreadID.get()+" removed task "+head.getNextTask().getTaskName());
            head.setNextTask(head.getNextTask().getNextTask());
            if(size.getAndDecrement() == capacity)
                mustwake = true;
        } finally {
            deqLock.unlock();
        }
        if(mustwake) {
            enqLock.lock();
            try {
                    NotFull.signalAll();
            } finally {
                enqLock.unlock();
            }
        }
    }

    public void printList(){
        Task hold = head;
        String sline = "";

        for(int i=0; i<size.get(); i++) {
            hold = hold.getNextTask();
            sline += hold.getTaskName();
            if(hold.getNextTask()!=null)
                sline+= ", ";
        }

        LOGGER.fine("Todo List Size: "+size.get());
        LOGGER.fine(sline);

    }

}