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

	private boolean readyToPay;

	private boolean studentCalled;

	private boolean waiterAlerted;

	private int nLeft;
	private int nEntered;
	private int nSaluted;
	private int nSaidGoodbye;

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

	/**
	 * Operation look around
	 *
	 * It is called by a waiter to look around
	 * 
	 */

	public synchronized char lookAround() {
		// GenericIO.writelnString("saluted: " + nSaluted);

		// GenericIO.writelnString("Is looking around");

		if (nLeft == SimulPar.S) {
			setOper('e');
			return oper;
		}

		if (paymentReceived) {
			notifyAll();
		}

		if (nLeft != nSaidGoodbye) {
			setActionNeeded(true);
			setOper('g');
		} else {
			if (nLeft != 0) {

				// GenericIO.writelnString("assssssasaaaaaaaaaaaaaaaa");
				setActionNeeded(false);
			}
		}

		if (nSaluted != nEntered) {
			setOper('c');
			return oper;
		} else {
			setActionNeeded(false);
		}

		if (readyToPay) {
			setOper('b');
			return oper;
		}

		if (studentCalled) {
			setOper('o');
			return oper;
		}

		if (waiterAlerted) {
			setOper('p');
			return oper;
		}

		if (bringNextCourse) {
			setOper('p');
			setActionNeeded(true);
		}

		// Sleep while waiting for something to happen
		while (!actionNeeded) {

			try {
				wait();
			} catch (Exception e) {
			}
			// GenericIO.writelnString("Im hereeeeeeeeeeeee");
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
		// GenericIO.writelnString("Waiter action needed: " + oper);
		return oper;

	}

	/**
	 * Set actionNeeded flag
	 *
	 * @param action
	 */

	public synchronized void setActionNeeded(boolean action) {
		actionNeeded = action;
	}

	/**
	 * Set oper flag
	 *
	 * @param op
	 */
	public synchronized void setOper(char op) {
		oper = op;
	}

	/**
	 * Getter has called waiter
	 *
	 * Method that returns hasCalledWaiter flag
	 * 
	 */
	public synchronized boolean getHasCalledWaiter() {
		return hasCalledWaiter;
	}

	/**
	 * Set hasCalledWaiter flag
	 *
	 * @param hasCalledWaiter
	 */
	public synchronized void setHasCalledWaiter(boolean hasCalledWaiter) {
		this.hasCalledWaiter = hasCalledWaiter;
	}

	/**
	 * Getter wants to pay
	 *
	 * Method that returns wantsToPay flag
	 * 
	 */
	public synchronized boolean getWantsToPay() {
		return wantsToPay;
	}

	/**
	 * Set wantsToPay flag
	 *
	 * @param wantsToPay
	 */
	public synchronized void setWantsToPay(boolean wantsToPay) {
		this.wantsToPay = wantsToPay;
	}

	/**
	 * Getter described order
	 *
	 * Method that returns describedOrder flag
	 * 
	 */
	public synchronized boolean getDescribedOrder() {
		return describedOrder;
	}

	/**
	 * Set describedOrder flag
	 *
	 * @param describedOrder
	 */
	public synchronized void setDescribedOrder(boolean describedOrder) {
		this.describedOrder = describedOrder;
	}

	/**
	 * Getter signal waiter
	 *
	 * Method that returns signalWaiter flag
	 * 
	 */
	public synchronized boolean getSignalWaiter() {
		return signalWaiter;
	}

	/**
	 * Set signalWaiter flag
	 *
	 * @param signalWaiter
	 */
	public synchronized void setSignalWaiter(boolean signalWaiter) {
		this.signalWaiter = signalWaiter;
	}

	/**
	 * Getter bill honored
	 *
	 * Method that returns billHonored flag
	 * 
	 */
	public synchronized boolean getBillHonored() {
		return billHonored;
	}

	/**
	 * Set billHonored flag
	 *
	 * @param billHonored
	 */
	public synchronized void setBillHonored(boolean billHonored) {
		this.billHonored = billHonored;
	}

	/**
	 * Operation say goodbye
	 *
	 * It is called by a waiter to say goodbye to the student
	 * 
	 */
	public synchronized void sayGoodbye() {
		nSaidGoodbye++;
		// GenericIO.writelnString("Goodbye nº: " + nLeft);
	}

	/**
	 * Operation return to the bar
	 *
	 * It is called by a waiter to return to the bar
	 * 
	 */
	public synchronized void returnToTheBar() {

		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.APPST);
		repos.setWaiterState(WaiterStates.APPST);

	}

	/**
	 * Operation return to the bar after salute
	 *
	 * It is called by a waiter to return to the bar after saluting the student
	 * 
	 */
	public synchronized void returnToTheBarAfterSalute() {

		nSaluted++;

		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.APPST);
		repos.setWaiterState(WaiterStates.APPST);

	}

	/**
	 * Operation return to the bar after taking the order
	 *
	 * It is called by a waiter to return to the bar after taking the order
	 * 
	 */
	public synchronized void returnToTheBarAfterTakingTheOrder() {

		studentCalled = false;

		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.APPST);
		repos.setWaiterState(WaiterStates.APPST);

	}

	/**
	 * Operation return to the bar after portions delivered
	 *
	 * It is called by a waiter to the bar after all portions of a course have been
	 * delivered
	 * 
	 */

	public synchronized void returnToTheBarAfterPortionsDelivered() {

		waiterAlerted = false;

		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.APPST);
		repos.setWaiterState(WaiterStates.APPST);

	}

	/**
	 * Operation prepare the bill
	 *
	 * It is called by a waiter to prepare the bill
	 * 
	 */
	public synchronized void prepareBill() {
		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.PRCBL);
		repos.setWaiterState(WaiterStates.PRCBL);

	}

	/**
	 * VER ESTA ----------------------- se é chamada ou nao Operation can bring next
	 * course
	 *
	 * It is called by a student to
	 * 
	 */

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

	/**
	 * Operation «get the pad
	 *
	 * It is called by a waiter to get the pad
	 * 
	 */
	public synchronized void getThePad() {

		// wake up the student
		notifyAll();

	}

	/**
	 * Operation call the waiter
	 *
	 * It is called by a student to call the waiter to describe the order
	 * 
	 */

	public synchronized void callTheWaiter() {
		bringNextCourse = true;
		studentCalled = true;
		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('o');
		notifyAll();

	}

	/**
	 * Operation should have arrived earlier
	 *
	 * It is called by a student to warn the waiter that it is ready to pay the bill
	 * 
	 */
	public synchronized void shouldHaveArrivedEarlier() {

		readyToPay = true;

		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('b');
		notifyAll();

	}

	/**
	 * Operation enter
	 *
	 * It is called by a student to enter the restaurant
	 * 
	 */

	public synchronized void enter() {
		nEntered++;
		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('c');
		notifyAll();
	}

	/**
	 * Operation alert waiter
	 *
	 * It is called by a chef to warn the waiter that a portions is ready to be
	 * delivered
	 * 
	 */

	public synchronized void alertWaiter() {
		waiterAlerted = true;
		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('p');
		notifyAll();

	}

	/**
	 * Operation signal waiter
	 *
	 * It is called by a student to warn the waiter that it can start delivering the
	 * portions of the next course
	 * 
	 */
	public synchronized void signalWaiter() {
		bringNextCourse = true;
		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('p');
		notifyAll();

	}

	/**
	 * Operation go home
	 *
	 * It is called by a student to warn the waiter that its going home
	 * 
	 */
	public synchronized void goHome() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.GGHOM);
		repos.setStudentState(studentID, StudentStates.GGHOM);
		/*
		 * // Sleep while waiting payment not received while (!paymentReceived) { try {
		 * wait(); } catch (Exception e) { } }
		 */

		nLeft++;

		// set action flag and oper and finally wake up the waiter
		setActionNeeded(true);
		setOper('g');
		notifyAll();

	}

	/**
	 * Operation received payment
	 *
	 * It is called by a waiter after the payment has been received
	 * 
	 */

	public synchronized void receivedPayment() {

		paymentReceived = true;
		readyToPay = false;

	}

}
