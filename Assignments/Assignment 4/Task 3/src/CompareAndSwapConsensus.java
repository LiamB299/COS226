import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Logger;

public class CompareAndSwapConsensus extends ConsensusProtocol<Integer> {

    private final int First = -1;
    private volatile AtomicInteger r = new AtomicInteger(First);

    private volatile Integer[] prop_v;
    private static volatile Logger LOGGER = Logger.getLogger("global");

    public CompareAndSwapConsensus(int threadCount) {
        super(threadCount);
        prop_v = new Integer[threadCount];
    }

    private void prop_value(Integer v) {
        prop_v[ThreadID.get()] = v;
    }

    public void propose(Integer value) {
        prop_value(value);
        String prop = "Participant "+ThreadID.get()+" buys a raffle ticket with random number "+value;
        LOGGER.info(prop);
    }

    public void decide() {
        int i = ThreadID.get();
        int ans;
        int prev = r.get();
        if(r.compareAndSet(First, i))
            ans = prop_v[i];
        else
            ans = prop_v[r.get()];
        String see = "Thread "+i+" - register: "+prev+" => "+r.get();
        LOGGER.fine(see);
        String dec = "Participant "+ThreadID.get()+" sees the winning raffle number is "+ans;
        LOGGER.info(dec);
    }
}
