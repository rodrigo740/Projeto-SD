package entities;

/**
 * Definition of the internal states of the student during his life cycle.
 */

public class StudentStates {

	/**
	 * going to the restaurant
	 */
	public static final int GGTRT = 0;
	/**
	 * taking a seat at the table
	 */
	public static final int TKSTT = 1;
	/**
	 * selecting the courses
	 */
	public static final int SELCS = 2;
	/**
	 * organizing the order
	 */
	public static final int OGODR = 3;
	/**
	 * chatting with companions
	 */
	public static final int CHTWC = 4;
	/**
	 * enjoying the meal
	 */
	public static final int EJYML = 5;
	/**
	 * paying the bill
	 */
	public static final int PYTBL = 6;
	/**
	 * going home
	 */
	public static final int GGHOM = 7;

	/**
	 * It can't be instantiated.
	 */
	private StudentStates() {
	}

}
