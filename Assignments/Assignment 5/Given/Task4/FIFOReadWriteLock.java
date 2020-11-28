// Practical 5
// Name and surname:
// Student number:

public class FIFOReadWriteLock {

	public FIFOReadWriteLock() {
		
	}

	public Lock readLock() {
	    // @todo: return appropriate object
		return null;
	}

	public Lock writeLock() {
	    // @todo: return appropriate object
		return null;
	}


    /* -------------------------------------------------- *
     *      Nested ReadLock Class
     * -------------------------------------------------- */
    private class ReadLock extends AbstractLock {

        public void lock() {

        }

        public void unlock() {

        }

    }

    /* -------------------------------------------------- *
     *      Nested WriteLock Class
     * -------------------------------------------------- */
    private class WriteLock extends AbstractLock {

        public void lock() {

        }

        public void unlock() {

        }

    }

}
