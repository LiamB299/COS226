//Practical assignment 1
//Shared counter object
//Student Number: 18015001
//Student Name: L.M. Burgess

import java.util.concurrent.locks.ReentrantLock;
class Counter {

	private int value;
	// Lock, garbage collector to remove instance
	private ReentrantLock MyLock;

	public Counter(int c) {
		value = c;
		MyLock = new ReentrantLock();
	}

	public int getAndIncrement() {
		int temp;
		MyLock.lock();
		// attempt to make instructions atomic
		try {
			temp = value;
			++value;
		}
		finally {
			MyLock.unlock();
		}
		return temp;
	}
}
