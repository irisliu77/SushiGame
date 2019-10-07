package sushigame.model;


public class InsufficientBalanceException extends Exception {

	public InsufficientBalanceException() {
		super("Insufficient balance");
	}
}
