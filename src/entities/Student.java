package entities;

import genclass.GenericIO;
import main.SimulPar;
import sharedRegion.Bar;
import sharedRegion.Kitchen;
import sharedRegion.Table;

public class Student extends Thread {

	private int studentID;
	private int studentState;

	private int seat;

	private final Bar bar;
	private final Kitchen kit;
	private final Table tbl;

	public Student(String name, int studentID, Bar bar, Kitchen kit, Table tbl) {
		super(name);
		this.studentID = studentID;
		this.studentState = StudentStates.GGTRT;
		this.bar = bar;
		this.kit = kit;
		this.tbl = tbl;
		this.seat = -1;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public int getStudentState() {
		return studentState;
	}

	public int getSeat() {
		return seat;
	}

	public void setStudentState(int studentState) {
		this.studentState = studentState;
	}

	@Override
	public void run() {

		tbl.walk();

		bar.enter();
		// GenericIO.writelnString("Student " + studentID + " has entered the
		// restaurant");

		tbl.takeASeat();
		// GenericIO.writelnString("Student " + studentID + " has took a seat");

		tbl.selectingCourse();
		// GenericIO.writelnString("Student " + studentID + " has selected a course");

		if (!tbl.amFirst()) {
			tbl.informCompanions();
			GenericIO.writelnString("Student " + studentID + " has informed the companion");
		} else {
			GenericIO.writelnString("Student " + studentID + " has described the order");
			tbl.organizeOrder();
			GenericIO.writelnString("Student " + studentID + " is going to call the waiter");
			bar.callTheWaiter();
			tbl.describeOrder();

		}
		for (int i = 0; i < SimulPar.M; i++) {
			tbl.chat();

			tbl.enjoyMeal();
			GenericIO.writelnString("Student " + studentID + " has eaten");
			if (tbl.lastToEat()) {
				GenericIO.writelnString("Student " + studentID + " was the last ot eat");
				bar.signalWaiter();
			}
			GenericIO.writelnString("Student " + studentID + " has stopped chatting");
		}

		tbl.chat();
		if (tbl.amLast()) {
			GenericIO.writelnString("Student " + studentID + " must honor the bill");
			bar.shouldHaveArrivedEarlier();
			tbl.honorTheBill();
			GenericIO.writelnString("Student " + studentID + " has honored the bill");
		}
		bar.goHome();

	}

}
