package sharedRegion;

import entities.Waiter;
import entities.WaiterStates;

public class Bar {

	private final Waiter waiter;
	private final GeneralRepo repos;

	private char oper;

	private boolean wantsToPay;
	private boolean describedOrder;
	private boolean signalWaiter;
	private boolean billHonored;
	private boolean hasCalledWaiter;

	private boolean actionNeeded;

	public Bar(GeneralRepo repos) {

		this.waiter = null;
		this.repos = repos;
	}

	public synchronized char lookArround() {
		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.APPST);
		repos.setWaiterState(WaiterStates.APPST);

		// Sleep while waiting for something to happen
		while (!actionNeeded) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		// reseting actionNeeded flag
		setActionNeeded(false);

		return oper;

	}

	public synchronized void setActionNeeded(boolean action) {
		actionNeeded = action;
	}

	public synchronized void setOper(char op) {
		oper = op;
	}

	public synchronized boolean getHasCalledWaiter() {
		return hasCalledWaiter;
	}

	public synchronized void setHasCalledWaiter(boolean hasCalledWaiter) {
		this.hasCalledWaiter = hasCalledWaiter;
	}

	public synchronized boolean getWantsToPay() {
		return wantsToPay;
	}

	public synchronized void setWantsToPay(boolean wantsToPay) {
		this.wantsToPay = wantsToPay;
	}

	public synchronized boolean getDescribedOrder() {
		return describedOrder;
	}

	public synchronized void setDescribedOrder(boolean describedOrder) {
		this.describedOrder = describedOrder;
	}

	public synchronized boolean getSignalWaiter() {
		return signalWaiter;
	}

	public synchronized void setSignalWaiter(boolean signalWaiter) {
		this.signalWaiter = signalWaiter;
	}

	public synchronized boolean getBillHonored() {
		return billHonored;
	}

	public synchronized void setBillHonored(boolean billHonored) {
		this.billHonored = billHonored;
	}

	public synchronized void sayGoodbye() {
		// TODO Auto-generated method stub

	}

	public synchronized void returnToTheBar() {
		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.APPST);
		repos.setWaiterState(WaiterStates.APPST);

	}

	public synchronized void prepareBill() {
		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.PRCBL);
		repos.setWaiterState(WaiterStates.PRCBL);

	}

	public synchronized void getThePad() {

		// wake up the student
		notifyAll();

	}

	public synchronized void callTheWaiter() {

		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('o');
		notifyAll();

	}

	public synchronized void shouldHaveArrivedEarlier() {

		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('b');
		notifyAll();

	}

}
