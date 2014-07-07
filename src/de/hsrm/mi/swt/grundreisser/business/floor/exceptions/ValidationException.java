package de.hsrm.mi.swt.grundreisser.business.floor.exceptions;

/**
 * Validation exception must be thrown if the action cannot be executed
 * 
 * @author nmuel002
 *
 */
@SuppressWarnings("serial")
public class ValidationException extends Exception {

	/**
	 * Initializing constructor
	 * 
	 * @param msg
	 */
	public ValidationException(String msg) {
		super(msg);
	}
}
