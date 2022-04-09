package sharedRegion;

import entities.*;

public class Kitchen {

	private final Chef chef;
	private final GeneralRepo repos;

	private boolean orderArrived;

	public Kitchen(GeneralRepo repos) {
		this.chef = null;
		this.repos = repos;
		orderArrived = false;
	}

	/**
	 * Operation hand the note to the Chef.
	 *
	 * It is called by a waiter to deliver the order to the chef
	 *
	 */

	public synchronized void handTheNoteToTheChef() {
		
		(Waiter) Thread.currentThread();
		
		

	}

	/**
	 * Operation watch the news.
	 *
	 * It is called by a chef while waiting for and order to be delivered by the
	 * waiter.
	 *
	 */

	public synchronized void watchTheNews() {
		while (!orderArrived) {
			try {
				wait();
			} catch (Exception e) {
			}
		}
	}

}
