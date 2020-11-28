import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static volatile Logger LOGGER = Logger.getLogger("global");
    public static void main(String[] args) {
        configureLogger();
        LOGGER.setLevel(Level.ALL);

        String []tasks = {"L", "S", "D", "I"};
        String []add = {"A", "L", "S", "X"};
        String []remove = {"D", "H", "D", "X"};
        BoundedQueueToDoList op = new BoundedQueueToDoList(4, tasks);

        GroupMember []gm = new GroupMember[MemberOptions.enqueuers+MemberOptions.dequeuers];
        for(int i=0; i<MemberOptions.enqueuers; i++)
            gm[i] = new GroupMember(op, true, add[i]);
        for(int i=MemberOptions.enqueuers, j=0; i<(MemberOptions.enqueuers+MemberOptions.dequeuers); i++, j++)
            gm[i] = new GroupMember(op, false, remove[j]);
        for(int i=0; i<(MemberOptions.enqueuers+MemberOptions.dequeuers); i++) {
            gm[i].start();
        }
        for(int i=0; i<(MemberOptions.enqueuers+MemberOptions.dequeuers); i++) {
            try {
                gm[i].join();
            }
            catch(InterruptedException e) {}
        }
        op.printList();
    }
    private static void configureLogger() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("logging.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
