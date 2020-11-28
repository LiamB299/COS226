import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static volatile Logger LOGGER = Logger.getLogger("global");
    public static void main(String[] args) {
        configureLogger();
        LOGGER.setLevel(Level.ALL);

        String []tasks = {"L", "S", "D"};
        String []add = {"A", "L", "S"};
        String []remove = {"D", "H", "D"};
        OptimisticToDoList op = new OptimisticToDoList(tasks);

        GroupMember []gm = new GroupMember[MemberOptions.adders+MemberOptions.removers];
        for(int i=0; i<MemberOptions.adders; i++)
            gm[i] = new GroupMember(op, true, add[i]);
        for(int i=MemberOptions.adders, j=0; i<(MemberOptions.adders+MemberOptions.removers); i++, j++)
            gm[i] = new GroupMember(op, false, remove[j]);
        for(int i=0; i<(MemberOptions.adders+MemberOptions.removers); i++) {
            gm[i].start();
        }
        for(int i=0; i<(MemberOptions.adders+MemberOptions.removers); i++) {
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
