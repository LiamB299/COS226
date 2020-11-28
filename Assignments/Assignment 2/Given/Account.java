//Practical assignment 2
//Student Number:
//Student Name:

import java.util.concurrent.locks.Lock;

class Account {

	float balance;
	volatile Lock lock;
	volatile ThreadID id;
	volatile TThread th;
	private volatile static final Logger LOGGER = Logger.getLogger("global");
	cofigureLogger();
	LOGGER.setLevel(Level.INFO);

	Account(float amount, Lock mylock) {
		balance = amount;
		this.mylock = mylock;
	}

	boolean withdraw(float amount, String threadName) {
		boolean isSuccess = false;	// indicates if withdrawal transaction was successful
			if balance > amount {
				balance -= amount;
				isSuccess = true;
				LOGGER.info()
				return isSuccess
		}
			// Print...
		return isSuccess;
	}

	void deposit(float amount, String threadName) {
		balance += amount;
		// Print...
	}

	float getBalance()
	{
		return balance;
	}
}
