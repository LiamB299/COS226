import java.util.logging.Logger;

public class GroupMember extends Thread{

    private volatile BoundedQueueToDoList myQueue;
    private volatile Boolean myEnq;
    private volatile String myTask;
    private static volatile Logger LOGGER = Logger.getLogger("global");

   public GroupMember(BoundedQueueToDoList queue, boolean enqueue, String taskName){
       myQueue = queue;
       myEnq = enqueue;
       myTask = taskName;
    }

    public void run(){
        if(myEnq) {
            //sleep
            LOGGER.info("Group member "+ThreadID.get()+" is going to try to add a task");
            myQueue.enq(myTask);
        }
        else {
            //sleep
            LOGGER.info("Group member "+ThreadID.get()+" is going to try to remove a task");
            myQueue.deq();
        }
    }
}
