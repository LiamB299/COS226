//Practical assignment 1
//Shared counter object
//Student Number:
//Student Name:

class Counter {

	private int value;

	public Counter(int c) {
		value = c;
	}

	public int getAndIncrement() {
		return value++;
	}
}