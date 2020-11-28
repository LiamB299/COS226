import java.util.logging.Logger;

public class GroupMember extends Thread {

    private static volatile Logger LOGGER = Logger.getLogger("global");

    volatile OptimisticToDoList myList;
    volatile Boolean myAdd;
    volatile String myTask;

    public GroupMember(OptimisticToDoList list, Boolean adder, String taskName){
        myList = list;
        myAdd = adder;
        myTask = taskName;
    }

    public void run() {
        if (myAdd) {
            LOGGER.info("Group member "+ThreadID.get()+" is going to try to add a task");
            myList.addTask(myTask);
        } else {
            LOGGER.info("Group member "+ThreadID.get()+" is going to try to remove a task");
            myList.removeTask(myTask);
        }
        //myList.printList();
    }
}
