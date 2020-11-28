import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task {

    private String mytask;
    private volatile Task next;
    private volatile Boolean marked;
    private volatile Lock mylock;

    public Task(String taskName){
       mytask = taskName;
       marked = false;
       mylock = new ReentrantLock();
       next = null;
    }

    public void lock(){
        mylock.lock();
    }

    public void unlock(){
        mylock.unlock();
    }

    public String getTaskName(){
        return mytask;
    }

    public Task getNextTask(){
        return next;
    }

    public void setNextTask(Task nextTask){
        next = nextTask;
    }

    public void setMarkedField(boolean marked){
        this.marked = marked;
    }

    public boolean getMarkedField(){
        return marked;
    }
}
