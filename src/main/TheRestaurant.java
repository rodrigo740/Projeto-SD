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

public class TheRestaurant {

	public static void main(String[] args) {

		Student students[] = new Student[SimulPar.S];
		Waiter waiter;
		Chef chef;

		Table table;
		Bar bar;
		Kitchen kitchen;
		GeneralRepo repos;

		String fileName;
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

		table = new Table(repos, SimulPar.S);
		bar = new Bar(repos);
		kitchen = new Kitchen(repos);

		waiter = new Waiter("Waiter", 1, 0, bar, kitchen, table);
		chef = new Chef("Chef", 1, 0, bar, kitchen, table);

		for (int i = 0; i < SimulPar.S; i++) {
			students[i] = new Student("Student_" + (i + 1), i, bar, kitchen, table);
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
