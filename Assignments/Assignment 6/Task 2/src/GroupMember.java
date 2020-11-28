import java.util.logging.Logger;

public class GroupMember extends Thread {

    LazyToDoList myList;
    boolean myAdd;
    String myTask;
    private static volatile Logger LOGGER = Logger.getLogger("global");

    public GroupMember(LazyToDoList toDoList, boolean adder, String taskName){
        myList = toDoList;
        myAdd = adder;
        myTask = taskName;
    }

    public void run(){
        if(myAdd) {
            LOGGER.info("Group member "+ThreadID.get()+" is going to try to add a task");
            myList.addTask(myTask);
        }
        else {
            LOGGER.info("Group member "+ThreadID.get()+" is going to try to remove a task");
            myList.removeTask(myTask);
        }
    }
}
