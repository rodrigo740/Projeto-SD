package entities;

public class WaiterStates {

	public static final int APPST = 0; // appraising situation
	public static final int PRSMN = 1; // presenting the menu
	public static final int TKODR = 2; // taking the order
	public static final int PCODR = 3; // placing the order
	public static final int WTFPT = 4; // waiting for portion
	public static final int PRCBL = 5; // processing the bill
	public static final int RECPM = 6; // receiving payment

	private WaiterStates() {
	}

}
