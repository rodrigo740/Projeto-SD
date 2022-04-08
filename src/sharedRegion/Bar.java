package sharedRegion;

import commInfra.MemFIFO;
import entities.Waiter;
import genclass.GenericIO;

public class Bar {
	
	private final Waiter waiter;
	private final GeneralRepo repos;
	
	private boolean wantsToPay;
	private boolean describedOrder;
	private boolean signalWaiter;
	private boolean billHonored;
	private boolean hasCalledWaiter;
	
	
	public Bar(GeneralRepo repos) {
		
		this.waiter = null;
		this.repos = repos;
	}
	
	public boolean getHasCalledWaiter() {
		return hasCalledWaiter;
	}

	public void setHasCalledWaiter(boolean hasCalledWaiter) {
		this.hasCalledWaiter = hasCalledWaiter;
	}


	public boolean getWantsToPay() {
		return wantsToPay;
	}


	public void setWantsToPay(boolean wantsToPay) {
		this.wantsToPay = wantsToPay;
	}


	public boolean getDescribedOrder() {
		return describedOrder;
	}


	public void setDescribedOrder(boolean describedOrder) {
		this.describedOrder = describedOrder;
	}


	public boolean getSignalWaiter() {
		return signalWaiter;
	}


	public void setSignalWaiter(boolean signalWaiter) {
		this.signalWaiter = signalWaiter;
	}


	public boolean getBillHonored() {
		return billHonored;
	}


	public void setBillHonored(boolean billHonored) {
		this.billHonored = billHonored;
	}
	
}
