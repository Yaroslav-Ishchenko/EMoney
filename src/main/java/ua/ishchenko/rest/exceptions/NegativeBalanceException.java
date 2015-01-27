package ua.ishchenko.rest.exceptions;

public class NegativeBalanceException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7242522768745675801L;
	String message;
	public NegativeBalanceException() {
		message = "The operation can not be finished because of lack of money";
	}
	
	public String toString()
	{
		return message;
	}
}
