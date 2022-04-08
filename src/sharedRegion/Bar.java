package sharedRegion;

import commInfra.MemFIFO;
import entities.Waiter;
import genclass.GenericIO;

public class Bar {
	
	private final Waiter waiter;
	private final GeneralRepo repos;
	
	private boolean hasCalledWaiter;
	private boolean wantsToPay;
	private boolean describedOrder;
	private boolean signalWaiter;
	private boolean billHonored;
	
	
	public Bar(GeneralRepo repos) {
		
		this.waiter = null;
		this.repos = repos;
	}
	
	


}
