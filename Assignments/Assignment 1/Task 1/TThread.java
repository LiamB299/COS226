//Practical assignment 1
//Thread created by extending the Thread class
//Student Number: 18015001
//Student Name: L.M. Burgess
class TThread extends Thread {
	// holds reference to counter
	private Counter C;

	// class init, requires a counter object to be passed in
	public TThread(Counter C) {
		this.C = C;
	}

	// sleep then output
	public void run() {
		// loop six times
		for (int i = 0; i < 6; i++) {
			try {
				// sleep 550ms
				sleep(550);
			}

			// catch the collision interrupt
			catch (InterruptedException e) {
			}

			// assign, convert to string and call get increment, not atomic here
			String c_val = String.valueOf(C.getAndIncrement());

			// output as specified
			System.out.print(this.getName()+" "+c_val+" "+String.valueOf(this.getId())+"\n");

		}// end for
	}
}
