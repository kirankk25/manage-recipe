/**
 * 
 */
package com.assignment.food.managerecipe.commonexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Kiran Kumar Karedla
 * Manages Not Found Exceptions
 *
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5806161747130309344L;
	
	public NotFoundException() {
		super();
	}
	public NotFoundException(final String message) {
		super(message,null,false,false);
	}
	public NotFoundException(final String message,final Throwable cause) {
		super(message,cause,false,false);
	}
	

}
