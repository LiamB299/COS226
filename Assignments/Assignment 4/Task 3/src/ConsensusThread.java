public class ConsensusThread extends Thread {

    private volatile Consensus<Integer> cObj;

    public ConsensusThread(Consensus<Integer> consensusObject) {
        cObj = consensusObject;
    }

    void thread_sleep(long time) {
        try {
            sleep(time);
        }
        catch (InterruptedException e) {}
    }

    public void run() {
        //for (int i=0; i< ConsensusOptions.runs; i++) {
            Integer value = (int) (Math.random() * (49 - 0 + 1) + 0);
            cObj.propose(value);
            thread_sleep(ConsensusOptions.delay);
            cObj.decide();
        //}
    }

}

