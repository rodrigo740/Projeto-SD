package entities;

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
			// Transition to 'APPST'
			oper = bar.lookAround();
			switch (oper) {
			case 'c':
				// Transition to 'PRSMN'
				tbl.saluteTheClient();
				// Transition to 'APPST'
				bar.returnToTheBarAfterSalute();
				break;
			case 'o':
				// Transition to 'TKODR'
				tbl.getThePad();
				// Transition to 'PCODR'
				kit.handTheNoteToTheChef();
				// Transition to 'APPST'
				bar.returnToTheBarAfterTakingTheOrder();
				break;
			case 'p':
				while (!tbl.haveAllPortionsBeenServed()) {
					// Transition to 'WTFPT'
					kit.collectPortion();
					tbl.deliverPortion();
				}
				// Transition to 'APPST'
				bar.returnToTheBarAfterPortionsDelivered();
				break;
			case 'b':
				// Transition to 'PRCBL'
				bar.prepareBill();
				// Transition to 'RECPM'
				tbl.presentBill();
				bar.receivedPayment();
				// Transition to 'APPST'
				bar.returnToTheBar();
				break;
			case 'g':
				bar.sayGoodbye();
				// Transition to 'APPST'
				bar.returnToTheBar();
				break;
			case 'e':
				end = true;
				break;
			}
		}
	}
}
