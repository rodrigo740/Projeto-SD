package entities;

import main.SimulPar;
import sharedRegion.Bar;
import sharedRegion.Kitchen;
import sharedRegion.Table;

public class Student extends Thread {

	private int studentID;
	private int studentState;

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
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public int getStudentState() {
		return studentState;
	}

	public void setStudentState(int studentState) {
		this.studentState = studentState;
	}

	@Override
	public void run() {

		boolean first = false, organizingOrder = true, allHaveChosen = false, lastToEat = false, orderDone = true;

		int m = 1;

		tbl.walk();

		tbl.enterRestaurant();

		tbl.selectingCourse();

		if (tbl.amFirst()) {
			tbl.informCompanions();
		} else {

			tbl.organizeOrder();
			bar.callTheWaiter();
			tbl.describeOrder();
		}

		tbl.chat();

		for (int i = 0; i < SimulPar.M; i++) {
			tbl.enjoyMeal();
			if (tbl.lastToEat()) {
				bar.getSignalWaiter();
			}
			tbl.chat();
		}
		if (tbl.amLast()) {
			bar.shouldHaveArrivedEarlier();
			tbl.honorTheBill();
		}

		tbl.goHome();

	}

}
