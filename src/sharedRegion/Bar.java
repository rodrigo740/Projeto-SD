package sharedRegion;

import commInfra.MemFIFO;
import entities.Student;
import entities.StudentStates;
import entities.Waiter;
import entities.WaiterStates;
import genclass.GenericIO;
import main.SimulPar;

public class Bar {

	private final Waiter waiter;
	private final GeneralRepo repos;

	private char oper;

	private boolean paymentReceived;
	private boolean wantsToPay;
	private boolean describedOrder;
	private boolean signalWaiter;
	private boolean billHonored;
	private boolean hasCalledWaiter;

	private boolean actionNeeded;

	private boolean bringNextCourse;

	private int nLeft;
	private int nEntered;
	private int nSaluted;

	private MemFIFO<Integer> enterOrder;

	public Bar(GeneralRepo repos) {

		try {
			enterOrder = new MemFIFO<>(new Integer[SimulPar.S]);
		} catch (Exception e) {
			GenericIO.writelnString("Instantiation of enter order FIFO failed: " + e.getMessage());
			enterOrder = null;
			System.exit(1);
		}

		this.waiter = null;
		this.repos = repos;
	}

	public synchronized char lookArround() {

		// GenericIO.writelnString("Is looking around");

		if (nLeft == SimulPar.S) {
			setOper('e');
			return oper;
		}

		if (paymentReceived) {
			notifyAll();
		}

		if (nSaluted != nEntered) {
			setOper('c');
			return oper;
		} else {
			setActionNeeded(false);
		}

		// Sleep while waiting for something to happen
		while (!actionNeeded) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		if (oper == 'p') {
			// Sleep while waiting for the student to signal it needs the next course
			while (!bringNextCourse) {
				try {
					wait();
				} catch (Exception e) {
				}
			}

			// reset bringNextCourse flag
			bringNextCourse = false;
		}

		// reseting actionNeeded flag
		setActionNeeded(false);
		GenericIO.writelnString("Waiter action needed: " + oper);
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
		nLeft++;
		GenericIO.writelnString("Goodbye nº: " + nLeft);
	}

	public synchronized void returnToTheBar() {

		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.APPST);
		repos.setWaiterState(WaiterStates.APPST);

	}

	public synchronized void returnToTheBarAfterSalute() {

		nSaluted++;

		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.APPST);
		repos.setWaiterState(WaiterStates.APPST);

	}

	public synchronized void prepareBill() {
		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.PRCBL);
		repos.setWaiterState(WaiterStates.PRCBL);

	}

	public synchronized void canBringNextCourse() {
		// Sleep while waiting for the student to signal it needs the next course
		while (!bringNextCourse) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		// reset bringNextCourse flag
		bringNextCourse = false;

	}

	public synchronized void getThePad() {

		// wake up the student
		notifyAll();

	}

	public synchronized void callTheWaiter() {
		bringNextCourse = true;
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

	public synchronized void enter() {
		nEntered++;
		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('c');
		notifyAll();
	}

	public synchronized void alertWaiter() {
		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('p');
		notifyAll();

	}

	public synchronized void signalWaiter() {
		bringNextCourse = true;
		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('p');
		notifyAll();

	}

	public synchronized void goHome() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.GGHOM);
		repos.setStudentState(studentID, StudentStates.GGHOM);

		// Sleep while waiting payment not received
		while (!paymentReceived) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('g');
		notifyAll();

	}

	public synchronized void receivedPayment() {

		paymentReceived = true;

	}

}
