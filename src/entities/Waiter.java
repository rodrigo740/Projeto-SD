package entities;

import sharedRegion.Bar;
import sharedRegion.Kitchen;
import sharedRegion.Table;

public class Waiter extends Thread {

	private int waiterID;
	private int waiterState;

	private final Bar bar;
	private final Kitchen kit;
	private final Table tbl;

	public Waiter(String name, int waiterID, int waiterState, Bar bar, Kitchen kit, Table tbl) {
		super(name);
		this.waiterID = waiterID;
		this.waiterState = WaiterStates.APPST;
		this.bar = bar;
		this.kit = kit;
		this.tbl = tbl;
	}

	public int getWaiterID() {
		return waiterID;
	}

	public void setWaiterID(int waiterID) {
		this.waiterID = waiterID;
	}

	public int getWaiterState() {
		return waiterState;
	}

	public void setWaiterState(int waiterState) {
		this.waiterState = waiterState;
	}

	@Override
	public void run() {

		char oper;
		boolean end = false, startedPreparation = false;

		while (!end) {
			oper = bar.lookArround();
			switch (oper) {
			case 'c':
				tbl.saluteTheClient();
				bar.returnToTheBar();
				break;
			case 'o':
				bar.getThePad();
				tbl.takeTheOrder();
				kit.handTheNoteToTheChef();
				bar.returnToTheBar();
				break;
			case 'p':
				while (!tbl.haveAllPortionsBeenServed()) {
					kit.collectPortion();
					tbl.deliverPortion();
				}
				bar.returnToTheBar();
				break;
			case 'b':
				bar.prepareBill();
				tbl.presentBill();
				bar.returnToTheBar();
				break;
			case 'g':
				bar.sayGoodbye();
				break;

			default:
				bar.lookArround();
			}

		}

		/*
		 * while (!end) { if (tbl.studentHasEntered()) { tbl.saluteTheClient(); while
		 * (!tbl.hasReadMenu()) { tbl.presentMenu(); } tbl.setHasReadMenu(false);
		 * 
		 * } else if (tbl.studentHasLeft()) { bar.sayGoodbye(); } else if
		 * (tbl.allHaveLeft()) { end = true;
		 * 
		 * } else if (this.hasCalledWaiter) { bar.getThePad(); while
		 * (!this.hasDescribedTheOrder) { tbl.takeTheOrder(); } this.describedOrder =
		 * false; while (!Kit.startPreparation()) { Kit.handTheNoteToTheChef(); }
		 * startedPreparation = true; } else if (this.shouldHaveArrivedEarlier) {
		 * bar.prepareTheBill(); while (!this.honorTheBill()) { tbl.presentTheBill(); }
		 * } else if (this.signalWaiter) { startedPreparation = true; } if
		 * (kit.getStartedPreparation()) {
		 * 
		 * while (!kit.getHaveAllPortionsBeenDelivered()) {
		 * 
		 * if (kit.getAlertTheWaiter()) { kit.collectPortion(); tbl.deliverPortion(); }
		 * startedPreparation = false; } } bar.lookAround(); }
		 */

	}

}
