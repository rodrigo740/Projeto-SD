package main;

import entities.Chef;
import entities.Student;
import entities.Waiter;
import genclass.FileOp;
import genclass.GenericIO;
import sharedRegion.Bar;
import sharedRegion.GeneralRepo;
import sharedRegion.Kitchen;
import sharedRegion.Table;

/**
 * Simulation of the Assignment 1 - TheRestaurant. (number of threads controlled
 * by global constants - SimulPar)
 */

public class TheRestaurant {

	/**
	 * Main method.
	 *
	 * @param args runtime arguments
	 */

	public static void main(String[] args) {

		Student students[] = new Student[SimulPar.S]; // reference to the student threads
		Waiter waiter; // reference to the waiter thread
		Chef chef; // reference to the chef thread

		Table table; // reference to the table
		Bar bar; // reference to the bar
		Kitchen kitchen; // reference to the kitchen
		GeneralRepo repos; // reference to the GeneralRepo

		String fileName; // name of the logger file
		char opt;
		boolean success;

		/* problem initialization */

		GenericIO.writelnString("\n" + "\tThe Restaurant Problem\n");

		do {
			GenericIO.writeString("Logging file name? ");
			fileName = GenericIO.readlnString();
			if (FileOp.exists(".", fileName)) {
				do {
					GenericIO.writeString("There is already a file with this name. Delete it (y - yes; n - no)? ");
					opt = GenericIO.readlnChar();
				} while ((opt != 'y') && (opt != 'n'));
				if (opt == 'y')
					success = true;
				else
					success = false;
			} else
				success = true;
		} while (!success);

		/* Initialize threads */

		repos = new GeneralRepo(fileName);

		table = new Table(repos);
		bar = new Bar(repos);
		kitchen = new Kitchen(repos);

		waiter = new Waiter("Waiter", 1, 0, bar, kitchen, table);
		chef = new Chef("Chef", 1, 0, bar, kitchen);

		for (int i = 0; i < SimulPar.S; i++) {
			students[i] = new Student("Student_" + (i + 1), i, bar, table);
		}

		/* start the simulation */

		chef.start();
		waiter.start();

		for (int i = 0; i < SimulPar.S; i++) {
			students[i].start();
		}

		/* waiting for the simulation to finish */

		GenericIO.writelnString();

		try {
			chef.join();
		} catch (InterruptedException e) {
			GenericIO.writelnString("The chef has terminated.");
		}

		try {
			waiter.join();
		} catch (InterruptedException e) {
			GenericIO.writelnString("The waiter has terminated.");
		}

		for (int i = 0; i < SimulPar.S; i++) {
			try {
				students[i].join();
			} catch (InterruptedException e) {
				GenericIO.writelnString("The student " + (i + 1) + " has terminated.");
			}
		}

		GenericIO.writelnString();

	}

}
