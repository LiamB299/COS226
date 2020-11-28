//Practical assignment 1
//Counter class
//Student Number: 18015001
//Student Name: L.M. Burgess

class Counter {

	private int value;

	public Counter(int c) {
		value = c;
	}

	public synchronized int getAndIncrement() {
		return value++;
	}

	// Alternate
	//public int getAndIncrement() {
	//	int temp;
	//	synchronized (this) {
	// 		temp = value;
	//		value++;
	//	}
	//	return temp;
	//}

}