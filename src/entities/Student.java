package entities;

import sharedRegion.Bar;
import sharedRegion.Kitchen;
import sharedRegion.Table;

public class Student extends Thread{
	
	private int studentID;
	private int studentState;
	
	private final Bar bar;
	private final Kitchen kit;
	private final Table tbl;
	
	public Student(String name, int studentID, Bar bar, Kitchen kit, Table tbl) {
		super(name);
		this.studentID = studentID;
		//this.studentState = studentState;
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
	
	

}
