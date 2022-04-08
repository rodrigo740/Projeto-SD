package entities;

import sharedRegion.Bar;
import sharedRegion.Kitchen;
import sharedRegion.Table;

public class Chef {
	
	private int chefID;
	private int chefState;
	
	private final Bar bar;
	private final Kitchen kit;
	private final Table tbl;
	
	public Chef(int chefID, int chefState, Bar bar, Kitchen kit, Table tbl) {
		super();
		this.chefID = chefID;
		this.chefState = chefState;
		this.bar = bar;
		this.kit = kit;
		this.tbl = tbl;
	}
	
	public int getChefID() {
		return chefID;
	}
	public void setChefID(int chefID) {
		this.chefID = chefID;
	}
	public int getChefState() {
		return chefState;
	}
	public void setChefState(int chefState) {
		this.chefState = chefState;
	}

}
