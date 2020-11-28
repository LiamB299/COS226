import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class CardUser extends Thread {
    volatile private static Logger LOGGER = Logger.getLogger("global");

    volatile ATM atm;
    volatile Random ran = new Random();
    public CardUser(ATM atm) {
        this.atm = atm;
    }

    public void setATM(ATM atm) {
        this.atm = atm;
    }

    public ATM getATM() {
        return atm;
    }

    public void sl(long time) {
        try {
            sleep(time);
        }
        catch (InterruptedException e) {}
    }

    // thread method
    public void run() {
        for(int i=0; i<TransactionOptions.cardUsers; i++) {
            int meth = ThreadLocalRandom.current().nextInt(0, 1 + 1);

            // withdraw
            if (meth == 0) {
                float amount = 200 + ran.nextFloat() * (1000 - 200);
                atm.withdraw(amount, ThreadID.get());
                sl(TransactionOptions.withdrawalSleepTime);
            } else {
                float amount = 50 + ran.nextFloat() * (500 - 50);
                atm.deposit(amount, ThreadID.get());
                sl(TransactionOptions.depositSleepTime);
            }
        }
    }
}
