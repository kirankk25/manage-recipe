/**
 * 
 */
package com.assignment.food.managerecipe.commonexceptions;

/**
 * @author Kiran Kumar Karedla
 * Manages Not Valid Value Exceptions
 *
 */
public class NotValidValueException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9189881359279657467L;
	
	public NotValidValueException() {
		super();
	}
	public NotValidValueException(final String message) {
		super(message,null,false,false);
	}
	public NotValidValueException(final String message,final Throwable cause) {
		super(message,null,false,false);
	}
	public NotValidValueException(final String message,final Throwable cause,final boolean enableSupression,final boolean writableStackTrace ) {
		super(message,cause,enableSupression,writableStackTrace);
	}

}
