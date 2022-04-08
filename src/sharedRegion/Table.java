package sharedRegion;

import commInfra.MemFIFO;
import entities.Student;
import genclass.GenericIO;

public class Table {
	
	private final int sitsNumber;
	
	private boolean organizingOrder;
	private boolean allHaveChosen;
	private boolean orderDone;
	
	private MemFIFO<Integer> sitOrder;
	private MemFIFO<Integer> eatOrder;
	
	private final Student[] students;
	private final GeneralRepo repos;
	
	public Table(GeneralRepo repos, int sitsNumber) {
		this.sitsNumber = sitsNumber;
		students = new Student[this.sitsNumber];
		for (int i = 0; i < students.length; i++) {
			students[i] = null;
		}
		
		try {
			sitOrder = new MemFIFO<> (new Integer [sitsNumber]);
			eatOrder = new MemFIFO<> (new Integer [sitsNumber]);
		} catch (Exception e) {
			GenericIO.writelnString ("Instantiation of waiting FIFO failed: " + e.getMessage ());
			sitOrder = null;
			eatOrder = null;
			System.exit(1);
		}

		this.repos = repos;
	}
	
	

}