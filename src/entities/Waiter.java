package entities;

import genclass.GenericIO;
import sharedRegion.Bar;
import sharedRegion.Kitchen;
import sharedRegion.Table;

/**
 * Waiter thread.
 *
 * Used to simulate the Waiter life cycle.
 */

public class Waiter extends Thread {

	/**
	 * Waiter identification.
	 */

	private int waiterID;

	/**
	 * Waiter state.
	 */

	private int waiterState;

	/**
	 * Reference to the Bar.
	 */

	private final Bar bar;

	/**
	 * Reference to the Kitchen.
	 */

	private final Kitchen kit;

	/**
	 * Reference to the Table.
	 */

	private final Table tbl;

	/**
	 * Instantiation of a Waiter thread.
	 * 
	 * @param name        thread name
	 * @param waiterID    id of the waiter
	 * @param waiterState state of the waiter
	 * @param bar         reference to the bar
	 * @param kit         reference to the kitchen
	 * @param tbl         reference to the table
	 */
	public Waiter(String name, int waiterID, int waiterState, Bar bar, Kitchen kit, Table tbl) {
		super(name);
		this.waiterID = waiterID;
		this.waiterState = WaiterStates.APPST;
		this.bar = bar;
		this.kit = kit;
		this.tbl = tbl;
	}

	/**
	 * Get waiter id
	 * 
	 * @return waiterID
	 */
	public int getWaiterID() {
		return waiterID;
	}

	/**
	 * Set waiter id
	 * 
	 */

	public void setWaiterID(int waiterID) {
		this.waiterID = waiterID;
	}

	/**
	 * Get waiter state
	 * 
	 * @return waiterState
	 */

	public int getWaiterState() {
		return waiterState;
	}

	/**
	 * Set waiter state
	 * 
	 */

	public void setWaiterState(int waiterState) {
		this.waiterState = waiterState;
	}

	/**
	 * Regulates the life cycle of the Waiter.
	 */

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

	}

}
