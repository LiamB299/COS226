//Practical assignment 1
//Thread created by extending the Thread class
//Student Number: 18015001
//Student Name: L.M. Burgess
class TThread extends Thread {
	// holds reference to counter
	private Counter C;

	// class init
	public TThread(Counter C) {
		this.C = C;
	}

	// sleep then output
	public void run() {
		String c_val="";
		for (int i = 0; i < 6; i++) {
			try {
				sleep(550);
			}
			catch (InterruptedException e) {
			}
				// critical section
				c_val = String.valueOf(C.getAndIncrement());
				System.out.print(this.getName()+" "+c_val+" "+String.valueOf(this.getId())+"\n");
		}
	}
}
