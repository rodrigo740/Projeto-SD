package entities;

/**
 * Definition of the internal states of the chef during his life cycle.
 */

public class ChefStates {

	/**
	 * waiting for an order
	 */

	public static final int WAFOR = 0;

	/**
	 * preparing a course
	 */

	public static final int PRPCS = 1;

	/**
	 * dishing the portions
	 */

	public static final int DSHPT = 2;

	/**
	 * delivering the portions
	 */

	public static final int DLVPT = 3;

	/**
	 * closing service
	 */

	public static final int CLSSV = 4;

	/**
	 * It can't be instantiated
	 */

	private ChefStates() {
	}

}
