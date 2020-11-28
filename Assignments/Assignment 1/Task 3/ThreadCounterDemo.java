//Practical assignment 1
//Two threads can access a shared Counter
//Student Number: 18015001
//Student Name: L.M. Burgess
class ThreadCounterDemo {
	
	public static void main (String args[]) {

		// shared Counter object
		Counter C = new Counter(1);

		// create threads
		TThread t1 = new TThread(C);
		TThread t2 = new TThread(C);
		TThread t3 = new TThread(C);
		TThread t4 = new TThread(C);
		TThread t5 = new TThread(C);
		TThread t6 = new TThread(C);

		// start the threads
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();

		// wait for all threads to complete
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
			t6.join();
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}
}