package entities;

import sharedRegion.Bar;
import sharedRegion.Kitchen;
import sharedRegion.Table;

public class Waiter {
	
	private int waiterID;
	private int waiterState;
	
	private final Bar bar;
	private final Kitchen kit;
	private final Table tbl;
	
	public Waiter(int waiterID, int waiterState, Bar bar, Kitchen kit, Table tbl) {
		super();
		this.waiterID = waiterID;
		this.waiterState = waiterState;
		this.bar = bar;
		this.kit = kit;
		this.tbl = tbl;
	}
	
	public int getWaiterID() {
		return waiterID;
	}
	public void setWaiterID(int waiterID) {
		this.waiterID = waiterID;
	}
	public int getWaiterState() {
		return waiterState;
	}
	public void setWaiterState(int waiterState) {
		this.waiterState = waiterState;
	}
	
	

}
