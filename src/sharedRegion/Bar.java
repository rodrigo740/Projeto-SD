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
	
	public boolean isHasCalledWaiter() {
		return hasCalledWaiter;
	}

	public void setHasCalledWaiter(boolean hasCalledWaiter) {
		this.hasCalledWaiter = hasCalledWaiter;
	}


	public boolean isWantsToPay() {
		return wantsToPay;
	}


	public void setWantsToPay(boolean wantsToPay) {
		this.wantsToPay = wantsToPay;
	}


	public boolean isDescribedOrder() {
		return describedOrder;
	}


	public void setDescribedOrder(boolean describedOrder) {
		this.describedOrder = describedOrder;
	}


	public boolean isSignalWaiter() {
		return signalWaiter;
	}


	public void setSignalWaiter(boolean signalWaiter) {
		this.signalWaiter = signalWaiter;
	}


	public boolean isBillHonored() {
		return billHonored;
	}


	public void setBillHonored(boolean billHonored) {
		this.billHonored = billHonored;
	}
	
}
