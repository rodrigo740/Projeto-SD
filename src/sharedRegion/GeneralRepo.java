package sharedRegion;

import java.util.Objects;

import genclass.GenericIO;
import genclass.TextFile;

public class GeneralRepo {

	private final String logFileName;

	public GeneralRepo(String logFileName) {

		if ((logFileName == null) || Objects.equals(logFileName, "")) {
			this.logFileName = "logger";
		} else {
			this.logFileName = logFileName;
		}

		reportInitialStatus();

	}

	private void reportInitialStatus() {
		TextFile log = new TextFile(); // instantiation of a text file handler

		if (!log.openForWriting(".", logFileName)) {
			GenericIO.writelnString("The operation of creating the file " + logFileName + " failed!");
			System.exit(1);
		}
		log.writelnString("                The Restaurant Problem");

		log.writelnString(" Waiter 1  Chef 1  Student 1  Student 2  Student 3  Student 4  Student 5");
		if (!log.close()) {
			GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
			System.exit(1);
		}
		reportStatus();

	}

	private void reportStatus() {
		TextFile log = new TextFile(); // instantiation of a text file handler

		String lineStatus = ""; // state line to be printed

		if (!log.openForAppending(".", logFileName)) {
			GenericIO.writelnString("The operation of opening for appending the file " + logFileName + " failed!");
			System.exit(1);
		}

		/*
		 * 
		 * for (int i = 0; i < SimulPar.M; i++) switch (barberState[i]) { case
		 * BarberStates.SLEEPING: lineStatus += " SLEEPING "; break; case
		 * BarberStates.INACTIVITY: lineStatus += " ACTIVICT "; break; } for (int i = 0;
		 * i < SimulPar.N; i++) switch (customerState[i]) { case
		 * CustomerStates.DAYBYDAYLIFE: lineStatus += " DAYBYDAY "; break; case
		 * CustomerStates.WANTTOCUTHAIR: lineStatus += " WANTCUTH "; break; case
		 * CustomerStates.WAITTURN: lineStatus += " WAITTURN "; break; case
		 * CustomerStates.CUTTHEHAIR: lineStatus += " CUTTHAIR "; break; }
		 */
		log.writelnString(lineStatus);
		if (!log.close()) {
			GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
			System.exit(1);
		}
	}

}
