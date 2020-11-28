import java.util.logging.Logger;

public class LazyToDoList {

 private static volatile Logger LOGGER = Logger.getLogger("global");

   private volatile Task head;
   private volatile Task tail;
   private volatile int size;

   LazyToDoList(String[] taskNames) {
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

      }

      public void addTask(String name) {
       Task prev = head;
       Task curr = head.getNextTask();

       while (curr != tail && !curr.getTaskName().equals(name)) {
        prev = curr;
        curr = curr.getNextTask();
       }

       prev.lock();
       curr.lock();
       try {
        if (prev.getMarkedField() == false && curr.getMarkedField() == false && prev.getNextTask() == curr && curr.getTaskName()!=name) {
         prev.setNextTask(new Task(name));
         prev.getNextTask().setNextTask(curr);
         LOGGER.info("Group member " + ThreadID.get() + " added task " + name);
         size++;
         return;
        } else {
         LOGGER.info("Group member " + ThreadID.get() + " could not add task " + name + " as it is already in " +
                 "the list");
         return;
        }
       } finally {
        prev.unlock();
        curr.unlock();
       }
      }



      public void removeTask(String name){
       Task prev = head;
       Task curr = head.getNextTask();

       while(curr!=tail && !curr.getTaskName().equals(name)) {
        prev = curr;
        curr = curr.getNextTask();
       }

       prev.lock();
       curr.lock();

       try {
        if(!curr.getMarkedField() && curr!=tail) {
          curr.setMarkedField(true);
          LOGGER.info("Group member "+ThreadID.get()+" logically removed task "+name);
          prev.setNextTask(curr.getNextTask());
          LOGGER.info("Group member "+ThreadID.get()+" physically removed task "+name);
          size--;
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

}
