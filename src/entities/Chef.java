package entities;

import sharedRegion.Bar;
import sharedRegion.Kitchen;

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
	 * Instantiation of a Chef Thread
	 * 
	 * @param name      thread name
	 * @param chefID    ID of the chef
	 * @param chefState state of the chef
	 * @param bar       reference of the Bar
	 * @param kit       reference of the Kitchen
	 */
	public Chef(String name, int chefID, int chefState, Bar bar, Kitchen kit) {
		super(name);
		this.chefID = chefID;
		this.chefState = ChefStates.WAFOR;
		this.bar = bar;
		this.kit = kit;
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
		// Transition to 'WAFOR'
		kit.watchTheNews();
		// Transition to 'PRPCS'
		kit.startPreparations();
		do {
			if (!firstCourse) {
				// Transition to 'PRPCS'
				kit.continuePreparation();
			} else {
				firstCourse = false;
			}
			// Transition to 'DSHPT'
			kit.proceedToPresentation();
			bar.alertWaiter();
			// Transition to 'DLVPT'
			kit.deliverPortion();
			while (!kit.allPortionsDelived()) {
				// Transition to 'DSHPT'
				kit.haveNextPortionReady();
				kit.alertWaiter();
				// Transition to 'DLVPT'
				kit.deliverPortion();
			}
		} while (!kit.orderBeenCompleted());
		// Transition to 'CLSSV'
		kit.cleanUp();
	}

}
