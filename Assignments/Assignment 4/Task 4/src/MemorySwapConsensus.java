import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Logger;

public class MemorySwapConsensus extends ConsensusProtocol<Integer> {

    private volatile Integer[] prop_v;
    private static volatile Logger LOGGER = Logger.getLogger("global");
    private volatile int []swap;
    private volatile AtomicIntegerArray r = new AtomicIntegerArray(1);

    public MemorySwapConsensus(int threadCount) {
        super(threadCount);
        swap = new int[threadCount];
        r.set(0, 1);
        for (int i=0; i<threadCount; i++)
            swap[i] = 0;
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

    private int race_swap(int id) {
        int ret = id;
        if(r.compareAndSet(0, 1, 0))
            swap[id] = 1;
        else
            for(int i=0; i<swap.length; i++)
                if(swap[i]==1)
                    ret = i;
        return ret;
    }

    public void decide() {
        int i = ThreadID.get();
        int ans = race_swap(i);
        printRegisterContents();
        String dec = "Participant "+ThreadID.get()+" sees the winning raffle number is "+prop_v[ans];
        LOGGER.info(dec);
    }

    public void printRegisterContents() {
        String line = "Register @ Participant ";
        line += ThreadID.get();
        line += ": [ ";
        for(int i=0; i<swap.length-1; i++)
            line += swap[i]+" ";
        line += swap[swap.length-1]+" ]";
        LOGGER.fine(line);
    }
}
