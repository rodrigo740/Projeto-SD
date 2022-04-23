package entities;

import main.SimulPar;
import sharedRegion.Bar;
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
	 * Reference to the Table
	 */
	private final Table tbl;

	/**
	 * Instantiation of a Student Thread
	 * 
	 * @param name      thread main
	 * @param studentID ID of the student
	 * @param bar       reference to the Bar
	 * @param tbl       reference to the Table
	 */
	public Student(String name, int studentID, Bar bar, Table tbl) {
		super(name);
		this.studentID = studentID;
		this.studentState = StudentStates.GGTRT;
		this.bar = bar;
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
		// Transition to 'GGTRT'
		walk();
		bar.enter();
		// Transition to 'TKSTT'
		tbl.takeASeat();
		// Transition to 'SELCS'
		tbl.selectingCourse();
		if (!tbl.firstToEnter()) {
			tbl.informCompanions();
		} else {
			// Transition to 'OGODR'
			tbl.organizeOrder();
			bar.callTheWaiter();
			tbl.describeOrder();
		}
		for (int i = 0; i < SimulPar.M; i++) {
			// Transition to 'CHTWC'
			tbl.chat();
			// Transition to 'EJYML'
			tbl.enjoyMeal();
			if (tbl.lastToEat()) {
				if (i != 2) {
					bar.signalWaiter();
				}
				tbl.chatAgain();
			} else {
				// Transition to 'CHTWC'
				tbl.waitForEveryoneToFinish();
			}
		}
		if (tbl.lastToEnterRestaurant()) {
			bar.shouldHaveArrivedEarlier();
			// Transition to 'PYTBL'
			tbl.honorTheBill();
		}
		// Transition to 'GGHOM'
		bar.goHome();
	}

	/**
	 * Operation walk
	 *
	 * It is called by a student to wander before entering the restaurant
	 * 
	 */

	public void walk() {
		long v = (long) (1 + 40 * Math.random());
		try {
			Thread.sleep(v);
		} catch (InterruptedException e) {
		}
	}
}
