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
		boolean end = false;

		while (!end) {
			oper = bar.lookArround();
			switch (oper) {
			case 'c':
				tbl.saluteTheClient();
				// GenericIO.writelnString("Waiter saluted a client");
				bar.returnToTheBarAfterSalute();
				break;
			case 'o':
				tbl.getThePad();
				kit.handTheNoteToTheChef();
				bar.returnToTheBarAfterTakingTheOrder();
				break;
			case 'p':

				// GenericIO.writelnString("Chef has called me");
				while (!tbl.haveAllPortionsBeenServed()) {
					// GenericIO.writelnString("more portions need to be delivered!");
					kit.collectPortion();
					// GenericIO.writelnString("Portions collected");
					tbl.deliverPortion();
				}
				// GenericIO.writelnString("All portions served, returning to the bar");
				bar.returnToTheBarAfterPortionsDelivered();
				break;
			case 'b':
				bar.prepareBill();
				tbl.presentBill();
				bar.receivedPayment();
				bar.returnToTheBar();
				break;
			case 'g':
				bar.sayGoodbye();
				bar.returnToTheBar();
				break;
			case 'e':
				end = true;
				break;
			}

		}

		// GenericIO.writelnString("Waiter end");

	}

}
