//Practical assignment 1
//Note the different ways in which the threads are initialised
//Student Number:
//Student Name:
class ThreadDemo {
	
	public static void main (String args[]) {
		TThread t1 = new TThread(); 
		t1.start();
		
		Thread t2 = new Thread(new TRunnable()); 
		t2.start();
	}
}