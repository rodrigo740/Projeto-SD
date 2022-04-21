package entities;

import genclass.GenericIO;
import sharedRegion.Bar;
import sharedRegion.Kitchen;
import sharedRegion.Table;

public class Chef extends Thread {

	private int chefID;
	private int chefState;

	private final Bar bar;
	private final Kitchen kit;
	private final Table tbl;

	public Chef(String name, int chefID, int chefState, Bar bar, Kitchen kit, Table tbl) {
		super(name);
		this.chefID = chefID;
		this.chefState = ChefStates.WAFOR;
		this.bar = bar;
		this.kit = kit;
		this.tbl = tbl;
	}

	public int getChefID() {
		return chefID;
	}

	public void setChefID(int chefID) {
		this.chefID = chefID;
	}

	public int getChefState() {
		return chefState;
	}

	public void setChefState(int chefState) {
		this.chefState = chefState;
	}

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
