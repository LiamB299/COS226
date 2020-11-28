public class Task {

    private String myTask;
    private volatile Task next;

    public Task(String taskName){
        myTask = taskName;
    }

    public void setNextTask(Task nextTask) {
        next = nextTask;
    }

    public Task getNextTask() {
       return next;
    }

    public String getTaskName() {
        return myTask;
    }

}
