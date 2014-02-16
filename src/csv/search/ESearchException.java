package csv.search;

public class ESearchException extends Exception{

	/**
	 *
	 */
	public ESearchException() {
		super();
	}

	/**
	 *
	 * @param s
	 */
	public ESearchException(String s) {
		super(s);
	}

	/**
	 *
	 */
	public void printLocalizedMessage() {
		System.err.println(this.getLocalizedMessage());
	}
}
