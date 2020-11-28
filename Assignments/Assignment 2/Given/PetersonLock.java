//Practical assignment 2
//Student Number:
//Student Name:
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import TheadID;

class PetersonLock implements Lock {
	volatile private boolean[] flags = new boolean[2];
	volatile private int victim;

	public PetersonLock() {
		flags[0]=flags[1]=false;
		victim = -1;
	}

	public void lock() {
		int i = ThreadID.get()// thread id?
		int j = 1-i;
		flag[i] = true;
		victim = i;
		while(flag[j] && (victim==i)) {}
	}

	public void unlock() {
		int i = ThreadID.get(); // thread id?
		flag[i] = false;
	}

	// Any class implementing Lock must provide these methods
	public Condition newCondition() {
		throw new java.lang.UnsupportedOperationException();
	}

	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		throw new java.lang.UnsupportedOperationException();
	}

	public boolean tryLock() {
		throw new java.lang.UnsupportedOperationException();
	}

	public void lockInterruptibly() throws InterruptedException {
		throw new java.lang.UnsupportedOperationException();
	}
}
