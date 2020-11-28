import java.util.concurrent.locks.ReentrantLock;

// task is node

public class Task {

    private final String name;
    public volatile Task next;
    private volatile ReentrantLock lock;

    public Task(String taskName){
        name = taskName;
        next = null;
        lock = new ReentrantLock();
    }

    public void lock(){
        lock.lock();
    }

    public void unlock(){
        lock.unlock();
    }

    public String getTaskName(){
        return name;
    }

    public Task getNextTask(){
        return next;
    }

    public void setNextTask(Task nextTask){
        next = nextTask;
    }

}
