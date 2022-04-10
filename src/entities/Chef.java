package entities;

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
		kit.startPreparations();

		do {
			if (!firstCourse) {
				kit.continuePreparation();
			} else {
				firstCourse = false;
			}

			kit.proceedToPresentation();
			// bar.alertWaiter();

			kit.deliverPortion();

			while (!kit.allPortionsDelived()) {
				kit.haveNextPortionReady();
				// bar.alertWaiter();
			}
		} while (!kit.orderBeenCompleted());

		kit.cleanUp();

	}

}
