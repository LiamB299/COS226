//Practical assignment 2
//Student Number: 18015001
//Student Name:L.M. Burgess
class TThread extends Thread {
    volatile Account account;

    TThread(Account acc) {
        account = acc;
    }

    public void run() {
        //String name = "Thread-"+ThreadID.get();
        for (int i = 0; i < TThreadOptions.numRuns; i++) {
            account.withdraw(TThreadOptions.withdrawAmount/TThreadOptions.numRuns, this.getName());
            try {
                sleep(TThreadOptions.sleepTime);
            }
            catch (InterruptedException e) {
            }
        }
        account.deposit(TThreadOptions.depositAmount, this.getName());
    }
}