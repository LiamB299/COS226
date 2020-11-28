import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;

public abstract class AbstractLock implements Lock {

	private static volatile Logger LOGGER = Logger.getLogger("global");
	
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
