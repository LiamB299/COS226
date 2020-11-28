import java.util.Stack;
import java.util.logging.Logger;

public class StackConsensus extends ConsensusProtocol<Integer> {

    private volatile Integer[] prop_v;
    private static volatile Logger LOGGER = Logger.getLogger("global");
    private volatile Stack<Integer> stack;

    public StackConsensus(int threadCount) {
        super(threadCount);
        stack = new Stack<Integer>();
        prop_v = new Integer[threadCount];
    }

    private void prop_value(Integer v) {
        prop_v[ThreadID.get()] = v;
    }

    public void propose(Integer value) {
        prop_value(value);
        stack.push(ThreadID.get());
        String prop = "Participant "+ThreadID.get()+" buys a raffle ticket with random number "+value;
        LOGGER.info(prop);
    }

    private int raffle() {
        return prop_v[stack.peek()];
    }

    public void decide() {
        int ans = raffle();
        String dec = "Participant "+ThreadID.get()+" sees the winning raffle number is "+ans;
        LOGGER.info(dec);
    }

    public void displayStackContents() {
        String line = "Stack : ";
        Stack<Integer> hold = new Stack<Integer>();
        for(int i=prop_v.length-1; i>-1; i--)
            hold.push(stack.pop());
        for(int i=prop_v.length-1; i>0; i--)
            line +=hold.pop()+" ";
        line+=hold.pop();
        LOGGER.fine(line);
    }
}
