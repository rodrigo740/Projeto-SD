package entities;

import sharedRegion.Bar;
import sharedRegion.Kitchen;
import sharedRegion.Table;

public class Waiter extends Thread {

	private int waiterID;
	private int waiterState;

	private final Bar bar;
	private final Kitchen kit;
	private final Table tbl;

	public Waiter(String name, int waiterID, int waiterState, Bar bar, Kitchen kit, Table tbl) {
		super(name);
		this.waiterID = waiterID;
		// this.waiterState = waiterState;
		this.bar = bar;
		this.kit = kit;
		this.tbl = tbl;
	}

	public int getWaiterID() {
		return waiterID;
	}

	public void setWaiterID(int waiterID) {
		this.waiterID = waiterID;
	}

	public int getWaiterState() {
		return waiterState;
	}

	public void setWaiterState(int waiterState) {
		this.waiterState = waiterState;
	}

	@Override
	public void run() {
		/*
		 * boolean end = false, startedPreparation = false;
		 * 
		 * while (!end) { if (Table.studentHasEntered()) { // se um deles entrou
		 * Table.saluteTheClient(); // empregado diz ola while (!Table.hasReadMenu) {
		 * Table.presentMenu(); } Table.hasReadMenu = false;
		 * 
		 * } else if (Table.studentHasLeft()) { // se um deles sair Bar.sayGoodbye(); //
		 * o empregado diz goodbye } else if (Table.allHaveLeft()) { // se eles saíram
		 * do restaurante end = true;
		 * 
		 * } else if (this.hasCalledWaiter) { // estudante chama empregado
		 * Bar.getThePad(); // empregado vai buscar o bloco para apontar while
		 * (!this.hasDescribedTheOrder) { // tira o pedido ate o estudante acabar
		 * Table.takeTheOrder(); // empregado vai buscar o pedido } this.describedOrder
		 * = false; while (!Kit.startPreparation()) { Kit.handTheNoteToTheChef(); // dar
		 * a ordem ao chef quanto o chefe // ainda nao comecou a preparar }
		 * startedPreparation = true; } else if (this.shouldHaveArrivedEarlier) { // o
		 * estudante diz olha quero pagar Bar.prepareTheBill(); while
		 * (!this.honorTheBill()) { // o empregado fica aqui ate ele pagar
		 * Table.presentTheBill(); } } else if (this.signalWaiter) { startedPreparation
		 * = true; } if (Kit.getStartedPreparation()) { // o chef comecou a preparar
		 * pratos
		 * 
		 * while (!Kit.getHaveAllPortionsBeenDelivered()) { // enquanto nao tiver
		 * preparado // todas as porcoes if (Kit.getAlertTheWaiter()) { // o chef diz
		 * para ir buscar comida Kit.collectPortion(); // coleta a comida
		 * Table.deliverPortion(); } startedPreparation = false; } } bar.lookAround(); }
		 */
	}

}
