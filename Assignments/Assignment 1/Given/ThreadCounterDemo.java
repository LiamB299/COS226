//Practical assignment 1
//Two threads can access a shared Counter
//Student Number:
//Student Name:
class ThreadCounterDemo {
	
	public static void main (String args[]) {

		// shared Counter object
		Counter C = new Counter(1);

		// create threads
		TThread t1 = new TThread();
		TThread t2 = new TThread();

		// start the threads
		t1.start();
		t2.start();

		// wait for all threads to complete
		try {
			t1.join();
			t2.join();
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}
}