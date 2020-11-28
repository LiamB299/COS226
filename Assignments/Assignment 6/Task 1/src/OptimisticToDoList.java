import java.util.logging.Logger;

// linked list of tasks

public class OptimisticToDoList {

    private Task head, tail;
    private volatile int size;
    private static volatile Logger LOGGER = Logger.getLogger("global");

    OptimisticToDoList(String[] taskNames) {
    	head = new Task("HEAD");
    	tail = new Task("TAIL");
    	head.setNextTask(tail);

    	Task iter = head;
    	for(int i=0; i<taskNames.length; i++) {
    	    iter.setNextTask(new Task(taskNames[i]));
    	    iter = iter.getNextTask();
        }
    	iter.setNextTask(tail);
    	size = taskNames.length;
    	//LOGGER.fine("Size: "+size);
    }

    // iterate to end of list or unless found, not alphabetical
    public void addTask(String name) {
    	Task prev = head;
    	Task curr = head.next;

    	while(curr!=tail && !curr.getTaskName().equals(name)) {
    	    prev = curr;
    	    curr = curr.next;
        }

    	prev.lock();
    	curr.lock();
    	try {
            if (validate(prev, curr))
                if (curr.getTaskName().equals(name)) {
                    LOGGER.info("Group member "+ThreadID.get()+" could not add task "+name+" as it is already in " +
                            "the list");
                    return;
                }
                else {
                    Task hold = new Task(name);
                    hold.setNextTask(curr);
                    prev.setNextTask(hold);
                    LOGGER.info("Group member "+ThreadID.get()+" added task "+name);
                    size = size+1;
                    //LOGGER.fine("Size: "+ThreadID.get()+" "+size);
                    return;
                }
        } finally {
    	    prev.unlock();
    	    curr.unlock();
        }
    }

    public void removeTask(String name){
        Task prev = head;
        Task curr = head.next;

        while(curr!=tail && !curr.getTaskName().equals(name)) {
            prev = curr;
            curr = curr.next;
        }

        prev.lock();
        curr.lock();
        try {
            if (validate(prev, curr))
                if (curr.getTaskName().equals(name)) {
                    prev.setNextTask(curr.getNextTask());
                    LOGGER.info("Group member "+ThreadID.get()+" removed task "+name);
                    size = size-1;
                    //LOGGER.fine("Size: "+ThreadID.get()+" "+size);
                    return;
                }
                else {
                    LOGGER.info("Group member "+ThreadID.get()+" could not remove task "+name+" as it was not in the list");
                    return;
                }
        } finally {
            prev.unlock();
            curr.unlock();
        }
	}

    public void printList(){
        Task hold = head;
        String sline = "";

        while(hold.getNextTask()!=tail) {
            hold = hold.getNextTask();
            sline += hold.getTaskName();
            if(hold.getNextTask()!=tail)
                sline+= ", ";
        }

        LOGGER.fine("Todo List Size: "+size);
        LOGGER.fine(sline);
    }

    private boolean validate(Task predecessor, Task current) {
    	Task iter = head;
    	while(iter!=predecessor && iter!=tail) {
    	    iter = iter.getNextTask();
        }

    	if(iter==tail)
    	    return false;
    	else {
    	    if(iter.getNextTask()==current)
    	        return true;
    	    else return false;
        }

    }
 }
