//Practical assignment 2
//Student Number: 18015001
//Student Name:
class TThread extends Thread {
	
	Account account;
	
	TThread(Account acc) {
		account = acc;
	}

	public void run() {
		System.out.println("Thread running...");
	}
}