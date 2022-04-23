package entities;

/**
 * Definition of the internal states of the waiter during his life cycle.
 */

public class WaiterStates {

	/**
	 * appraising situation
	 */
	public static final int APPST = 0;
	/**
	 * presenting the menu
	 */
	public static final int PRSMN = 1;
	/**
	 * taking the order
	 */
	public static final int TKODR = 2;
	/**
	 * placing the order
	 */
	public static final int PCODR = 3;
	/**
	 * waiting for portion
	 */
	public static final int WTFPT = 4;
	/**
	 * processing the bill
	 */
	public static final int PRCBL = 5;
	/**
	 * receiving payment
	 */
	public static final int RECPM = 6;

	/**
	 * It can't be instantiated.
	 */
	private WaiterStates() {
	}

}
