package sharedRegion;

import commInfra.MemException;
import commInfra.MemFIFO;
import entities.Student;
import entities.StudentStates;
import entities.Waiter;
import entities.WaiterStates;
import genclass.GenericIO;
import main.SimulPar;

/**
 * Table.
 *
 * It is responsible for the the synchronization of the Students and Waiter
 * during the dinner process and is implemented as an implicit monitor.
 * 
 * There is ten internal synchronization points: four blocking point for the
 * Waiter, where he waits for the students to read the menu, describe the order,
 * accept a portion and honor the bill; and 6 blocking points for the Students,
 * where a student waits for the waiter to come salute him, for the waiter to
 * get the pad so he can describe the order, the student that has to organize
 * the order waits for everyone to inform him of their order, waits for an new
 * portion to arrive or for the end of the meal, when there are no more courses
 * to be delivered, waits for everyone to finish eating the current course and
 * student that has to honor the bill waits for the waiter to present the bill
 * to him
 */

public class Table {

	private int first;
	private int portionsDelivered;
	private int coursesDelivered;
	private int nStudents;
	private int eat;
	private int nOrders;

	private boolean menuRead;
	private boolean billPresented;
	private boolean orderDescribed;
	private boolean billHonored;
	private boolean clientSaluted;
	private boolean gotThePad;
	private boolean portionDelivered;
	private boolean allFinishedEating;
	private boolean informed;
	private boolean noMoreCourses;
	private boolean portionAccepted;

	private MemFIFO<Integer> sitOrder;

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
		} catch (Exception e) {
			GenericIO.writelnString("Instantiation of sit order FIFO failed: " + e.getMessage());
			sitOrder = null;
			System.exit(1);
		}

		this.repos = repos;
	}

	/**
	 * Operation salute the client.
	 *
	 * It is called by a waiter to salute the client
	 * 
	 */
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

	/**
	 * Operation deliver portion.
	 *
	 * It is called by a waiter to deliver a portion to a student
	 * 
	 */
	public synchronized void deliverPortion() {

		portionsDelivered++;
		repos.setPortionsDelivered(portionsDelivered);
		setPortionDelivered(true);
		notifyAll();

		// Sleep while waiting for the student to accept the portion
		while (!portionAccepted) {
			try {
				wait();
				// GenericIO.writelnString("\nwaiter waken up in deliver portion\n");
			} catch (Exception e) {
			}
		}
		// GenericIO.writelnString("\nportions delivered\n");
		portionAccepted = false;

	}

	/**
	 * Operation present the bill.
	 *
	 * It is called by a waiter to present the bill to the student
	 * 
	 */
	public synchronized void presentBill() {
		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.RECPM);
		repos.setWaiterState(WaiterStates.RECPM);

		billPresented = true;
		notifyAll();
		// GenericIO.writelnString("Waiting for student to pay the bill");
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

	/**
	 * Operation have all portions been served
	 *
	 * It is called by a waiter to know if all the portions have been served
	 * 
	 */

	public synchronized boolean haveAllPortionsBeenServed() {
		// GenericIO.writelnString("Portions delivered: " + portionsDelivered);
		return portionsDelivered == SimulPar.N;
	}

	/**
	 * Operation get the pad
	 *
	 * It is called by a waiter to get the pad
	 * 
	 */

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

	/**
	 * Operation set portion delivered
	 *
	 * This function sets the value of portionDelivered flag
	 *
	 * @param b
	 */

	private synchronized void setPortionDelivered(boolean b) {
		portionDelivered = b;

	}

	/**
	 * Operation set all finished eating
	 *
	 * This function sets the value of allFinishedEating flag
	 *
	 * @param b
	 */
	private synchronized void setAllFinishedEating(boolean b) {
		allFinishedEating = b;

	}

	/**
	 * Operation set got the pad
	 *
	 * This function sets the value of gotThePad flag
	 *
	 * @param b
	 */
	private synchronized void setGotThePad(boolean b) {
		gotThePad = b;

	}

	/**
	 * Operation set order described
	 *
	 * This function sets the value of orderDescribed flag
	 *
	 * @param b
	 */
	public synchronized void setOrderDescribed(boolean described) {
		orderDescribed = described;

	}

	/**
	 * Operation set bill honored
	 *
	 * This function sets the value of billHonored flag
	 *
	 * @param b
	 */
	public synchronized void setBillHonored(boolean honored) {
		billHonored = honored;

	}

	/**
	 * Operation set client saluted
	 *
	 * This function sets the value of clientSaluted flag
	 *
	 * @param b
	 */

	public synchronized void setClientSaluted(boolean saluted) {
		clientSaluted = saluted;

	}

	/**
	 * Operation set informed
	 *
	 * This function sets the value of informed flag
	 *
	 * @param b
	 */
	public synchronized void setInformed(boolean b) {
		informed = b;
	}

	/**
	 * Operation take a seat
	 *
	 * It is called by a student when it wants to take a seat at the table
	 * 
	 */
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

		if (first == -1) {
			first = studentID;
		}

		((Student) Thread.currentThread()).setSeat(nStudents);
		repos.setStudentSeat(studentID, nStudents);
		nStudents++;
		// GenericIO.writelnString("Student " + studentID + " is waiting for the waiter
		// to salute him");
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

	/**
	 * Operation selecting the course
	 *
	 * It is called by a student to know if all the portions have been served
	 * 
	 */
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

	/**
	 * Operation first to enter
	 *
	 * It is called by a student to know if it was the first to enter in the
	 * restaurant
	 * 
	 */
	public synchronized boolean firstToEnter() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();

		return studentID == first;

	}

	/**
	 * Operation inform companions
	 *
	 * It is called by a student to inform the companion about its order
	 */
	public synchronized void informCompanions() {

		nOrders++;
		setInformed(true);
		// waking up the student that takes the order
		notifyAll();

	}

	/**
	 * Operation organize order
	 *
	 * It is called by a student to start organizing the order
	 * 
	 */

	public synchronized void organizeOrder() {

		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.OGODR);
		repos.setStudentState(studentID, StudentStates.OGODR);

		// for each student wait minus himself
		while (nOrders < SimulPar.S - 1) {
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

	/**
	 * Operation describe order
	 *
	 * It is called by a student to describe the order to the waiter
	 * 
	 */

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

	/**
	 * Operation chat
	 *
	 * It is called by a student to start chatting with the companions
	 * 
	 */

	public synchronized void chat() {

		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.CHTWC);
		repos.setStudentState(studentID, StudentStates.CHTWC);

		// GenericIO.writelnString("Student " + studentID + " is here");

		// Sleep while waiting for a portion to be served or everybody has finished
		// eating

		while (!portionDelivered && !noMoreCourses) {
			try {
				wait();
				// GenericIO.writelnString("\nStudent " + studentID + " was waken up,
				// deliveredPortions: " + portionsDelivered + "\n");
			} catch (Exception e) {
			}
		}

		// reset orderDescribed flag
		setPortionDelivered(false);

		// allFinishedEating = false;

		// GenericIO.writelnString("\nStudent " + studentID + " is leaving\n");

	}

	/**
	 * Operation enjoy the meal
	 *
	 * It is called by a student to start eating the portion
	 * 
	 */

	public synchronized void enjoyMeal() {

		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.EJYML);
		repos.setStudentState(studentID, StudentStates.EJYML);

		portionAccepted = true;
		notifyAll();

		try {
			Thread.sleep((long) (1 + 40 * Math.random()));
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Operation last to eat
	 *
	 * It is called by a student to know if it was the last to eat the portion
	 * 
	 */
	public synchronized boolean lastToEat() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		eat++;

		if (eat == SimulPar.S) {
			// GenericIO.writelnString("Student " + studentID + " was the last to eat");
			coursesDelivered++;
			repos.setCoursesDelivered(coursesDelivered);

			if (coursesDelivered == SimulPar.M) {
				setAllFinishedEating(true);
				noMoreCourses = true;
				notifyAll();
				return false;
			}
			eat = 0;
			portionsDelivered = 0;
			setAllFinishedEating(true);
			notifyAll();

			return true;
		}
		setAllFinishedEating(false);
		return false;
	}

	/**
	 * Operation last to enter restaurant
	 *
	 * It is called by a student to know if it was the last to enter in the
	 * restaurant
	 * 
	 */

	public synchronized boolean lastToEnterRestaurant() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		return studentID == sitOrder.getLast();
	}

	/**
	 * Operation honor the bill
	 *
	 * It is called by a student to honor the bill
	 * 
	 */

	public synchronized void honorTheBill() {

		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.PYTBL);
		repos.setStudentState(studentID, StudentStates.PYTBL);

		// GenericIO.writelnString("Student " + studentID + " is waiting for the waiter
		// to present the bill");

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

	/**
	 * Operation go home
	 *
	 * It is called by a student to leave the restaurant and go home
	 * 
	 */
	public synchronized void goHome() {
		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.GGHOM);
		repos.setStudentState(studentID, StudentStates.GGHOM);

	}

	/**
	 * Operation wait for everyone to finish
	 *
	 * It is called by a student to wait of everyone to finish eating the current
	 * course
	 * 
	 */
	public synchronized void waitForEveryoneToFinish() {

		int studentID;
		// set state of student
		studentID = ((Student) Thread.currentThread()).getStudentID();
		((Student) Thread.currentThread()).setStudentState(StudentStates.CHTWC);
		repos.setStudentState(studentID, StudentStates.CHTWC);

		while (!allFinishedEating) {
			try {
				wait();
				// GenericIO.writelnString("Student " + studentID + " was awaken while waiting
				// for everyone to finish eating");
			} catch (Exception e) {
			}
		}

		// GenericIO.writelnString("Student " + studentID + " finished waiting and now
		// will chat");

	}

}