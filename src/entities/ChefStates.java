package entities;

public class ChefStates {

	public static final int WAFOR = 0; // waiting for an order
	public static final int PRPCS = 1; // preparing a course
	public static final int DSHPT = 2; // dishing the portions
	public static final int DLVPT = 3; // delivering the portions
	public static final int CLSSV = 4; // closing service

	private ChefStates() {
	}

}
