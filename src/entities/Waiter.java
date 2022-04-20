package entities;

import genclass.GenericIO;
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
				GenericIO.writelnString("Going to salute a client");
				tbl.saluteTheClient();
				GenericIO.writelnString("Saluted a client");
				bar.returnToTheBar();
				break;
			case 'o':
				tbl.getThePad();
				kit.handTheNoteToTheChef();
				bar.returnToTheBar();
				break;
			case 'p':

				GenericIO.writelnString("Chef has called me");
				while (!tbl.haveAllPortionsBeenServed()) {
					kit.collectPortion();
					tbl.deliverPortion();
				}
				GenericIO.writelnString("All portions served, returning to the bar");
				bar.returnToTheBar();
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

		GenericIO.writelnString("Waiter end");

	}

}
