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

	public GeneralRepo(String logFileName) {

		if ((logFileName == null) || Objects.equals(logFileName, "")) {
			this.logFileName = "logger";
		} else {
			this.logFileName = logFileName;
		}

		chefState = ChefStates.WAFOR;
		waiterState = WaiterStates.APPST;
		studentsState = new int[SimulPar.S];

		for (int i = 0; i < SimulPar.S; i++) {
			studentsState[i] = StudentStates.GGTRT;
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

	private void reportInitialStatus() {
		TextFile log = new TextFile(); // instantiation of a text file handler

		if (!log.openForWriting(".", logFileName)) {
			GenericIO.writelnString("The operation of creating the file " + logFileName + " failed!");
			System.exit(1);
		}
		log.writelnString("                The Restaurant - Description of the internal state");

		log.writelnString(
				" Chef  Waiter  Stu 0  Stu 1  Stu 2  Stu 3  Stu 4  Stu 5  Stu 6  NCourse  NPortion  \t  \t  \t  Table\nState State   State  State  State  State  State  State  State \t \t             Seat0  Seat1  Seat2  Seat3  Seat4  Seat5  Seat6");
		if (!log.close()) {
			GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
			System.exit(1);
		}
		reportStatus();

	}

	private synchronized void reportStatus() {
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
			lineStatus += " APPST ";
			break;
		case WaiterStates.PRSMN:
			lineStatus += " PRSMN ";
			break;
		case WaiterStates.TKODR:
			lineStatus += " TKODR ";
			break;
		case WaiterStates.PCODR:
			lineStatus += " PCODR ";
			break;
		case WaiterStates.WTFPT:
			lineStatus += " WTFPT ";
			break;
		case WaiterStates.PRCBL:
			lineStatus += " PRCBL ";
			break;
		case WaiterStates.RECPM:
			lineStatus += " RECPM ";
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

		lineStatus += SimulPar.M + "	" + SimulPar.N;

		for (int i = 0; i < SimulPar.S; i++) {

			lineStatus += " " + studentsState[i];

		}

		log.writelnString(lineStatus);
		if (!log.close()) {
			GenericIO.writelnString("The operation of closing the file " + logFileName + " failed!");
			System.exit(1);
		}
	}

}
