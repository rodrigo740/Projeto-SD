package entities;

import genclass.GenericIO;
import main.SimulPar;
import sharedRegion.Bar;
import sharedRegion.Kitchen;
import sharedRegion.Table;

/**
 * Student thread.
 *
 * Used to simulate the Student life cycle.
 */
public class Student extends Thread {

	/**
	 * Student identification
	 */
	private int studentID;
	/**
	 * Student state
	 */
	private int studentState;

	/*
	 * Number of the seat at the table
	 */
	private int seat;

	/**
	 * Reference to the Bar
	 */
	private final Bar bar;
	/**
	 * Reference to the Kitchen
	 */
	private final Kitchen kit;
	/**
	 * Reference to the Table
	 */
	private final Table tbl;

	/**
	 * Instantiation of a Student Thread
	 * 
	 * @param name      thread main
	 * @param studentID ID of the student
	 * @param bar       reference to the Bar
	 * @param kit       reference to the Kitchen
	 * @param tbl       reference to the Table
	 */
	public Student(String name, int studentID, Bar bar, Kitchen kit, Table tbl) {
		super(name);
		this.studentID = studentID;
		this.studentState = StudentStates.GGTRT;
		this.bar = bar;
		this.kit = kit;
		this.tbl = tbl;
		this.seat = -1;
	}

	/**
	 * Get Student ID
	 * 
	 * @return studentID
	 */
	public int getStudentID() {
		return studentID;
	}

	/**
	 * Set Student ID
	 * 
	 * @return studentID
	 */
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	/**
	 * Set number of the seat at the table
	 * 
	 * @param seat
	 */
	public void setSeat(int seat) {
		this.seat = seat;
	}

	/**
	 * Get Student state
	 * 
	 * @return studentState
	 */
	public int getStudentState() {
		return studentState;
	}

	/**
	 * Get number of the seat at the table
	 * 
	 * @return seat
	 */
	public int getSeat() {
		return seat;
	}

	/**
	 * Set Student state
	 * 
	 * @param studentState
	 */
	public void setStudentState(int studentState) {
		this.studentState = studentState;
	}

	/**
	 * Regulates the life cycle of the Student
	 */
	@Override
	public void run() {

		tbl.walk();

		bar.enter();
		GenericIO.writelnString("Student " + studentID + " has entered the restaurant");

		tbl.takeASeat();
		GenericIO.writelnString("Student " + studentID + " has took a seat");

		tbl.selectingCourse();
		GenericIO.writelnString("Student " + studentID + " has selected a course");

		if (!tbl.firstToEnter()) {
			tbl.informCompanions();
			GenericIO.writelnString("Student " + studentID + " has informed the companion");
		} else {
			GenericIO.writelnString("Student " + studentID + " will organize the order");
			tbl.organizeOrder();
			GenericIO.writelnString("Student " + studentID + " is going to call the waiter");
			bar.callTheWaiter();
			GenericIO.writelnString("Student " + studentID + " waiter has been called");
			tbl.describeOrder();
			GenericIO.writelnString("Student " + studentID + " has described the order");

		}
		for (int i = 0; i < SimulPar.M; i++) {
			tbl.chat();
			GenericIO.writelnString("Student " + studentID + " received a portion");
			tbl.enjoyMeal();
			GenericIO.writelnString("Student " + studentID + " has eaten");
			if (tbl.lastToEat()) {
				GenericIO.writelnString("Student " + studentID + " was the last ot eat");
				bar.signalWaiter();
			} else {
				GenericIO.writelnString(
						"Student " + studentID + " wasn't the last to eat, waiting for other students.....");
				tbl.waitForEveryoneToFinish();
			}
			GenericIO.writelnString("Student " + studentID + " has stopped chatting");
		}

		GenericIO.writelnString("Student " + studentID + " has no more courses");

		tbl.chat();
		if (tbl.lastToEnterRestaurant()) {
			GenericIO.writelnString("Student " + studentID + " must honor the bill");
			bar.shouldHaveArrivedEarlier();
			tbl.honorTheBill();
			GenericIO.writelnString("Student " + studentID + " has honored the bill");
		}
		bar.goHome();

	}

}
