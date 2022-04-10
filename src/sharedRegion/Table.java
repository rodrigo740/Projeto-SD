package sharedRegion;

import commInfra.MemException;
import commInfra.MemFIFO;
import entities.Student;
import entities.StudentStates;
import entities.Waiter;
import entities.WaiterStates;
import genclass.GenericIO;
import main.SimulPar;

public class Table {

	private int first;
	private int portionsDelivered;

	private boolean organizingOrder;
	private boolean allHaveChosen;
	private boolean orderDescribed;
	private boolean billHonored;
	private boolean clientSaluted;
	private boolean gotThePad;
	private boolean portionDelivered;
	private boolean allFinishedEating;

	private MemFIFO<Integer> sitOrder;
	private MemFIFO<Integer> eatOrder;

	private final Student[] students;
	private final GeneralRepo repos;

	public Table(GeneralRepo repos) {
		first = -1;
		students = new Student[SimulPar.S];
		for (int i = 0; i < students.length; i++) {
			students[i] = null;
		}

		try {
			sitOrder = new MemFIFO<>(new Integer[SimulPar.S]);
			eatOrder = new MemFIFO<>(new Integer[SimulPar.S]);
		} catch (Exception e) {
			GenericIO.writelnString("Instantiation of sit/eat order FIFO failed: " + e.getMessage());
			sitOrder = null;
			eatOrder = null;
			System.exit(1);
		}

		this.repos = repos;
	}

	public synchronized boolean studentHasEntered() {
		// TODO Auto-generated method stub
		return false;
	}

	public synchronized void saluteTheClient() {
		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.PRSMN);
		repos.setWaiterState(WaiterStates.PRSMN);

		// setting clientSaluted flag
		setClientSaluted(true);

		// waking up the student
		notifyAll();
	}

	public synchronized void deliverPortion() {

		portionsDelivered++;
		setPortionDelivered(true);
		notifyAll();

	}

	public synchronized void presentBill() {
		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.RECPM);
		repos.setWaiterState(WaiterStates.RECPM);

		// Sleep while waiting for the student to describe the order
		while (!billHonored) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		// reset orderDescribed flag
		setBillHonored(false);

	}

	public synchronized boolean haveAllPortionsBeenServed() {
		return portionsDelivered == SimulPar.N;
	}

	public synchronized void takeTheOrder() {
		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.TKODR);
		repos.setWaiterState(WaiterStates.TKODR);

		// setting gotThePad flag and wake up the student
		setGotThePad(true);
		notifyAll();

		// Sleep while waiting for the student to describe the order
		while (!orderDescribed) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		// reset orderDescribed flag
		setGotThePad(false);
	}

	private synchronized void setPortionDelivered(boolean b) {
		portionDelivered = b;

	}

	private synchronized void setAllFinishedEating(boolean b) {
		allFinishedEating = b;

	}

	private synchronized void setGotThePad(boolean b) {
		gotThePad = b;

	}

	public synchronized void setOrderDescribed(boolean described) {
		orderDescribed = described;

	}

	public synchronized void setBillHonored(boolean honored) {
		billHonored = honored;

	}

	public synchronized void setClientSaluted(boolean saluted) {
		clientSaluted = saluted;

	}

	public synchronized void walk() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.GGTRT);
		repos.setStudentState(studentID, StudentStates.GGTRT);

		if (first == -1) {
			first = studentID;
		}

		try {
			Thread.sleep((long) (1 + 40 * Math.random()));
		} catch (InterruptedException e) {
		}
	}

	public synchronized void enterRestaurant() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		students[studentID] = ((Student) Thread.currentThread());
		((Student) Thread.currentThread()).setStudentState(StudentStates.TKSTT);
		repos.setStudentState(studentID, StudentStates.TKSTT);

		// adding student to the sit order FIFO
		try {
			sitOrder.write(studentID);
		} catch (MemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Sleep while waiting for the student to describe the order
		while (!clientSaluted) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		// reset orderDescribed flag
		setClientSaluted(false);
	}

	public synchronized void selectingCourse() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.SELCS);
		repos.setStudentState(studentID, StudentStates.SELCS);

	}

	public synchronized boolean amFirst() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		return studentID == first;

	}

	public synchronized void informCompanions() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.CHTWC);
		repos.setStudentState(studentID, StudentStates.CHTWC);

		// waking up the student that takes the order
		notifyAll();

	}

	public synchronized void organizeOrder() {

		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.OGODR);
		repos.setStudentState(studentID, StudentStates.OGODR);

		// Sleep while waiting for the student to describe the order
		while (!gotThePad) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		// reset orderDescribed flag and waking up the waiter
		setGotThePad(false);
		notifyAll();

		// set state of student
		((Student) Thread.currentThread()).setStudentState(StudentStates.CHTWC);
		repos.setStudentState(studentID, StudentStates.CHTWC);

	}

	public synchronized void describeOrder() {
		// setting orderDescribed flag and wake up the waiter
		setOrderDescribed(true);
		notifyAll();

		// reset orderDescribed flag
		setOrderDescribed(false);
	}

	public synchronized void chat() {

		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.CHTWC);
		repos.setStudentState(studentID, StudentStates.CHTWC);

		// Sleep while waiting for a portion to be served or everybody has finished
		// eating
		while (!portionDelivered && !allFinishedEating) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		// reset orderDescribed flag and waking up the waiter
		setPortionDelivered(false);
		setAllFinishedEating(false);

	}

	public synchronized void enjoyMeal() {

		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.EJYML);
		repos.setStudentState(studentID, StudentStates.EJYML);

		try {
			Thread.sleep((long) (1 + 40 * Math.random()));
		} catch (InterruptedException e) {
		}

		try {
			eatOrder.write(studentID);
		} catch (MemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized boolean lastToEat() {
		if (eatOrder.isFull()) {
			try {
				eatOrder = new MemFIFO<>(new Integer[SimulPar.S]);
			} catch (MemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	public synchronized boolean amLast() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		return studentID == sitOrder.getLast();
	}

	public synchronized void honorTheBill() {

		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.PYTBL);
		repos.setStudentState(studentID, StudentStates.PYTBL);

		// set orderDescribed flag and wake up waiter
		setBillHonored(true);
		notifyAll();

	}

	public synchronized void goHome() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.GGHOM);
		repos.setStudentState(studentID, StudentStates.GGHOM);

	}

}