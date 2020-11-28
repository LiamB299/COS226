import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public abstract class AbstractLock implements Lock {
	
	public abstract void lock();
	
 	public abstract void unlock();
	
 	public boolean tryLock() {
		throw new java.lang.UnsupportedOperationException();
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		throw new java.lang.UnsupportedOperationException();
	}

	public Condition newCondition() {
		throw new java.lang.UnsupportedOperationException();
	}

	public void lockInterruptibly() throws InterruptedException {
		throw new java.lang.UnsupportedOperationException();
	}
}
