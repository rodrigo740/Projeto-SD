package entities;

import genclass.GenericIO;
import sharedRegion.Bar;
import sharedRegion.Kitchen;
import sharedRegion.Table;

/**
 * Chef thread.
 *
 * Used to simulate the Chef life cycle.
 */
public class Chef extends Thread {
	/**
	 * Chef identification
	 */
	private int chefID;
	/**
	 * Chef state
	 */
	private int chefState;
	/**
	 * Reference to the Bar
	 */
	private final Bar bar;
	/**
	 * Reference to the Kitchen
	 */
	private final Kitchen kit;
	/**
	 * Reference to the Table
	 */
	private final Table tbl;

	/**
	 * Instantiation of a Chef Thread
	 * 
	 * @param name      thread name
	 * @param chefID    ID of the chef
	 * @param chefState state of the chef
	 * @param bar       reference of the Bar
	 * @param kit       reference of the Kitchen
	 * @param tbl       reference of the Table
	 */
	public Chef(String name, int chefID, int chefState, Bar bar, Kitchen kit, Table tbl) {
		super(name);
		this.chefID = chefID;
		this.chefState = ChefStates.WAFOR;
		this.bar = bar;
		this.kit = kit;
		this.tbl = tbl;
	}

	/**
	 * Get Chef ID
	 * 
	 * @return chefID
	 */
	public int getChefID() {
		return chefID;
	}

	/**
	 * Set Chef ID
	 * 
	 * @param chefID
	 */
	public void setChefID(int chefID) {
		this.chefID = chefID;
	}

	/**
	 * Get Chef state
	 * 
	 * @return chefState
	 */
	public int getChefState() {
		return chefState;
	}

	/**
	 * Set Chef state
	 * 
	 * @param chefState
	 */
	public void setChefState(int chefState) {
		this.chefState = chefState;
	}

	/**
	 * Regulates the life cycle of the Chef
	 */

	@Override
	public void run() {
		boolean firstCourse = true;
		kit.watchTheNews();
		GenericIO.writelnString("Got an order");
		kit.startPreparations();

		do {
			if (!firstCourse) {
				GenericIO.writelnString("Continue Preparations");
				kit.continuePreparation();
			} else {
				firstCourse = false;
			}

			kit.proceedToPresentation();
			GenericIO.writelnString("Waiter alerted");
			bar.alertWaiter();
			// kit.alertWaiter();
			GenericIO.writelnString("Portion ready");
			kit.deliverPortion();

			while (!kit.allPortionsDelived()) {
				GenericIO.writelnString("Preparing next portion");
				kit.haveNextPortionReady();
				GenericIO.writelnString("Next portion ready");
				kit.alertWaiter();
				GenericIO.writelnString("Waiter called to take next portion");
				kit.deliverPortion();
				GenericIO.writelnString("Portion delivered");
			}
		} while (!kit.orderBeenCompleted());

		kit.cleanUp();
		GenericIO.writelnString("Chef end");
	}

}
