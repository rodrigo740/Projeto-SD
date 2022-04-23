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
			oper = bar.lookAround();
			switch (oper) {
			case 'c':
				GenericIO.writelnString("Going to salute a client");
				tbl.saluteTheClient();
				GenericIO.writelnString("Returning to the bar after saluting");
				bar.returnToTheBarAfterSalute();
				break;
			case 'o':
				GenericIO.writelnString("Going to take the order");
				tbl.getThePad();
				kit.handTheNoteToTheChef();
				GenericIO.writelnString("Returning to the bar after receiving an order and delivering it to the chef");
				bar.returnToTheBarAfterTakingTheOrder();
				break;
			case 'p':

				GenericIO.writelnString("Going to collect a portion");
				while (!tbl.haveAllPortionsBeenServed()) {
					GenericIO.writelnString("more portions need to be delivered!");
					kit.collectPortion();
					// GenericIO.writelnString("Portions collected");
					tbl.deliverPortion();
				}
				GenericIO.writelnString("All portions served, returning to the bar");
				bar.returnToTheBarAfterPortionsDelivered();
				break;
			case 'b':
				GenericIO.writelnString("Preparing the bill");
				bar.prepareBill();
				tbl.presentBill();
				bar.receivedPayment();
				GenericIO.writelnString("Got the payment, returning to the bar");
				bar.returnToTheBar();
				break;
			case 'g':
				GenericIO.writelnString("Goodbye");
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
