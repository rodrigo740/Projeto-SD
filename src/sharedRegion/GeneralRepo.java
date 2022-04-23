package sharedRegion;

import java.util.Objects;

import entities.ChefStates;
import entities.StudentStates;
import entities.WaiterStates;
import genclass.GenericIO;
import genclass.TextFile;
import main.SimulPar;

public class GeneralRepo {

	private final String logFileName;

	private int chefState;
	private int waiterState;
	private int studentsState[];
	private int seat[];
	private int portionsDelivered;
	private int coursesDelivered;

	public GeneralRepo(String logFileName) {

		if ((logFileName == null) || Objects.equals(logFileName, "")) {
			this.logFileName = "logger";
		} else {
			this.logFileName = logFileName;
		}

		chefState = ChefStates.WAFOR;
		waiterState = WaiterStates.APPST;
		studentsState = new int[SimulPar.S];
		seat = new int[SimulPar.S];
		for (int i = 0; i < SimulPar.S; i++) {
			studentsState[i] = StudentStates.GGTRT;
			seat[i] = -1;
		}

		reportInitialStatus();

	}

	public synchronized void setChefState(int state) {
		chefState = state;
		reportStatus();
	}

	public synchronized void setWaiterState(int state) {
		waiterState = state;
		reportStatus();
	}

	public synchronized void setStudentState(int id, int state) {
		studentsState[id] = state;
		reportStatus();

	}

	public synchronized void setStudentSeat(int studentID, int n) {
		seat[n] = studentID;
	}

	public synchronized void setPortionsDelivered(int n) {
		portionsDelivered = n;
	}

	public synchronized void setCoursesDelivered(int n) {
		coursesDelivered = n;
	}

	private void reportInitialStatus() {
		TextFile log = new TextFile(); // instantiation of a text file handler

		if (!log.openForWriting(".", logFileName)) {
			GenericIO.writelnString("The operation of creating the file " + logFileName + " failed!");
			System.exit(1);
		}

		log.writelnString(" \t\t\t\t\t\t  The Restaurant - Description of the internal state\n");
		String s = "  Chef  Waiter   Stu0   Stu1   Stu2   Stu3   Stu4   Stu5   Stu6   NCourse  NPortion\t\t\tTable\n State  State   State  State  State  State  State  State  State\t\t\t      Seat0 Seat1 Seat2 Seat3 Seat4 Seat5 Seat6";
		log.writelnString(s);

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

		switch (chefState) {
		case ChefStates.WAFOR:
			lineStatus += " WAFOR ";
			break;
		case ChefStates.PRPCS:
			lineStatus += " PRPCS ";
			break;
		case ChefStates.DSHPT:
			lineStatus += " DSHPT ";
			break;
		case ChefStates.DLVPT:
			lineStatus += " DLVPT ";
			break;
		case ChefStates.CLSSV:
			lineStatus += " CLSSV ";
			break;
		}

		switch (waiterState) {
		case WaiterStates.APPST:
			lineStatus += " APPST  ";
			break;
		case WaiterStates.PRSMN:
			lineStatus += " PRSMN  ";
			break;
		case WaiterStates.TKODR:
			lineStatus += " TKODR  ";
			break;
		case WaiterStates.PCODR:
			lineStatus += " PCODR  ";
			break;
		case WaiterStates.WTFPT:
			lineStatus += " WTFPT  ";
			break;
		case WaiterStates.PRCBL:
			lineStatus += " PRCBL  ";
			break;
		case WaiterStates.RECPM:
			lineStatus += " RECPM  ";
			break;
		}

		for (int i = 0; i < SimulPar.S; i++) {

			switch (studentsState[i]) {
			case StudentStates.GGTRT:
				lineStatus += " GGTRT ";
				break;
			case StudentStates.TKSTT:
				lineStatus += " TKSTT ";
				break;
			case StudentStates.SELCS:
				lineStatus += " SELCS ";
				break;
			case StudentStates.OGODR:
				lineStatus += " OGODR ";
				break;
			case StudentStates.CHTWC:
				lineStatus += " CHTWC ";
				break;
			case StudentStates.EJYML:
				lineStatus += " EJYML ";
				break;
			case StudentStates.PYTBL:
				lineStatus += " PYTBL ";
				break;
			case StudentStates.GGHOM:
				lineStatus += " GGHOM ";
				break;
			}

		}

		lineStatus += "     " + coursesDelivered + " \t      " + portionsDelivered + "        ";

		for (int i = 0; i < SimulPar.S; i++) {
			if (seat[i] == -1) {
				lineStatus += " #    ";
			} else {
				lineStatus += " " + seat[i] + "    ";
			}

		}

		log.writelnString(lineStatus);
		if (!log.close()) {
			GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
			System.exit(1);
		}
	}

}
