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
	private int coursesDelivered;
	private int nStudents;
	private int eat;

	private boolean menuRead;
	private boolean billPresented;
	private boolean organizingOrder;
	private boolean allHaveChosen;
	private boolean orderDescribed;
	private boolean billHonored;
	private boolean clientSaluted;
	private boolean gotThePad;
	private boolean portionDelivered;
	private boolean allFinishedEating;
	private boolean informed;

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

	public synchronized void saluteTheClient() {
		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.PRSMN);
		repos.setWaiterState(WaiterStates.PRSMN);

		// setting clientSaluted flag and waking up the student
		setClientSaluted(true);
		notifyAll();

		// Sleep while waiting for the student to read the menu
		while (!menuRead) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		// reset menuRead flag
		menuRead = false;

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

		billPresented = true;
		notifyAll();
		GenericIO.writelnString("Waiting for student to pay the bill");
		// Sleep while waiting for the student to honor the bill
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

	public synchronized void getThePad() {
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
		setOrderDescribed(false);
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

	public synchronized void setInformed(boolean b) {
		informed = b;

	}

	public synchronized void walk() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.GGTRT);
		repos.setStudentState(studentID, StudentStates.GGTRT);

		try {
			long v = (long) (1 + 40 * Math.random());
			Thread.sleep(v);

		} catch (InterruptedException e) {
		}

		if (first == -1) {
			first = studentID;
		}

	}

	public synchronized void takeASeat() {
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
			e1.printStackTrace();
		}

		((Student) Thread.currentThread()).setSeat(nStudents);
		nStudents++;
		GenericIO.writelnString("Student " + studentID + " is waiting for the waiter to salute him");
		// Sleep while waiting for the waiter to salute the student
		while (!clientSaluted) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		// reset clientSaluted flag
		setClientSaluted(false);

	}

	public synchronized void selectingCourse() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.SELCS);
		repos.setStudentState(studentID, StudentStates.SELCS);

		// set menuRead flag and waking up the waiter
		menuRead = true;
		notifyAll();

	}

	public synchronized boolean amFirst() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();

		return studentID == first;

	}

	public synchronized void informCompanions() {

		setInformed(true);

		// waking up the student that takes the order
		notifyAll();

	}

	public synchronized void organizeOrder() {

		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.OGODR);
		repos.setStudentState(studentID, StudentStates.OGODR);

		// for each student wait
		for (int i = 0; i < SimulPar.S - 1; i++) {
			// Sleep while waiting for all of the students to describes their orders
			while (!informed) {
				try {
					wait();
				} catch (Exception e) {
				}
			}
			setInformed(false);
		}

	}

	public synchronized void describeOrder() {

		// Sleep while waiting for the waiter to get the pad
		while (!gotThePad) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.CHTWC);
		repos.setStudentState(studentID, StudentStates.CHTWC);

		// reset gotThePad flag and set orderDescribed flag and waking up the waiter
		setGotThePad(false);
		setOrderDescribed(true);
		notifyAll();

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

		// reset orderDescribed flag
		setPortionDelivered(false);

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

	}

	public synchronized boolean lastToEat() {
		eat++;
		if (eat == SimulPar.S) {
			coursesDelivered++;
			eat = 0;
			portionsDelivered = 0;
			if (coursesDelivered == SimulPar.M) {
				setAllFinishedEating(true);
				notifyAll();
				return false;
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

		while (!billPresented) {
			try {
				wait();
			} catch (Exception e) {
			}
		}
		billPresented = false;
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