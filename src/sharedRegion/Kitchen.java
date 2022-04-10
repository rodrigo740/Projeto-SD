package sharedRegion;

import entities.Chef;
import entities.ChefStates;
import entities.Waiter;
import entities.WaiterStates;

public class Kitchen {

	private final Chef chef;
	private final GeneralRepo repos;

	private boolean orderArrived;
	private boolean portionCollected;

	private int courses;
	private int portions;
	private int deliveredCourses;
	private int deliveredPortions;

	public Kitchen(GeneralRepo repos) {
		this.chef = null;
		this.repos = repos;
	}

	/**
	 * Set Portion Collected Flag.
	 *
	 * @param collected portion collected
	 */

	public synchronized void setPortionCollected(boolean collected) {
		portionCollected = collected;
	}

	/**
	 * Set Order Arrived Flag.
	 *
	 * @param arrived order has arrived
	 */

	public synchronized void setOrderArrived(boolean arrived) {
		orderArrived = arrived;
	}

	/**
	 * Operation hand the note to the Chef.
	 *
	 * It is called by a waiter to deliver the order to the chef
	 * 
	 * @param nCourses  number of courses
	 * @param nPortions number of portions
	 */

	public synchronized void handTheNoteToTheChef(int nCourses, int nPortions) {

		// set state of waiter
		((Waiter) Thread.currentThread()).setWaiterState(WaiterStates.PCODR);
		repos.setWaiterState(WaiterStates.PCODR);

		// set number of courses and portions per course
		courses = nCourses;
		portions = nPortions;

		// set orderArrived flag and wake chef
		setOrderArrived(true);
		notifyAll();

	}

	/**
	 * Operation watch the news.
	 *
	 * It is called by a chef while waiting for and order to be delivered by the
	 * waiter.
	 *
	 */

	public synchronized void watchTheNews() {

		// set state of chef
		((Chef) Thread.currentThread()).setChefState(ChefStates.WAFOR);
		repos.setChefState(ChefStates.WAFOR);

		// Sleep while waiting for order to arrive
		while (!orderArrived) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		// order has arrived reseting flag
		setOrderArrived(false);
	}

	/**
	 * Operation start preparations.
	 *
	 * It is called by a chef after receiving and order
	 *
	 */

	public synchronized void startPreparations() {

		// set state of chef
		((Chef) Thread.currentThread()).setChefState(ChefStates.PRPCS);
		repos.setChefState(ChefStates.PRPCS);
	}

	/**
	 * Operation continue preparations.
	 *
	 * It is called by a chef after the chef delivered a portion
	 *
	 */

	public synchronized void continuePreparation() {

		// set state of chef
		((Chef) Thread.currentThread()).setChefState(ChefStates.PRPCS);
		repos.setChefState(ChefStates.PRPCS);
	}

	/**
	 * Operation proceed to presentation.
	 *
	 * It is called by a chef after preparing a portion
	 *
	 */

	public synchronized void proceedToPresentation() {

		// set state of chef
		((Chef) Thread.currentThread()).setChefState(ChefStates.DSHPT);
		repos.setChefState(ChefStates.DSHPT);
	}

	/**
	 * Operation all portions delivered.
	 *
	 * It is called by a chef to know if all portion have been delivered
	 *
	 */

	public synchronized boolean allPortionsDelived() {

		return portions == deliveredPortions;
	}

	/**
	 * Operation deliver portion.
	 *
	 * It is called by a chef to deliver a portion to the waiter
	 *
	 */

	public synchronized void deliverPortion() {

		// set state of chef
		((Chef) Thread.currentThread()).setChefState(ChefStates.DLVPT);
		repos.setChefState(ChefStates.DLVPT);

		// Sleep while waiting for order to arrive
		while (!portionCollected) {
			try {
				wait();
			} catch (Exception e) {
			}
		}

		// reseting portionCollected flag
		setPortionCollected(false);
	}

	/**
	 * Operation collect portion.
	 *
	 * It is called by a waiter to collect a portion
	 *
	 */

	public synchronized void collectPortion() {

		// set state of chef
		((Chef) Thread.currentThread()).setChefState(ChefStates.DSHPT);
		repos.setChefState(ChefStates.DSHPT);

		// Set portionCollected flag and wake the chef
		setPortionCollected(true);
		notifyAll();
	}

	/**
	 * Operation have next portion ready.
	 *
	 * It is called by a chef in order to start dishing another portion
	 *
	 */

	public synchronized void haveNextPortionReady() {

		// set state of chef
		((Chef) Thread.currentThread()).setChefState(ChefStates.DSHPT);
		repos.setChefState(ChefStates.DSHPT);

	}

	/**
	 * Operation order been completed.
	 *
	 * It is called by a chef in order to know if the order has been completed
	 *
	 */

	public synchronized boolean orderBeenCompleted() {

		return courses == deliveredCourses;

	}

	/**
	 * Operation clean up.
	 *
	 * It is called by a chef to finish its service
	 *
	 */

	public synchronized void cleanUp() {

		// set state of chef
		((Chef) Thread.currentThread()).setChefState(ChefStates.CLSSV);
		repos.setChefState(ChefStates.CLSSV);

	}

}
