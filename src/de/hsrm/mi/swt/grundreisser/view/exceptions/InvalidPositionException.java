/**
 * 
 */
package de.hsrm.mi.swt.grundreisser.view.exceptions;

/**
 * 
 * An Exception to report invalid view positions
 * 
 * @author Simon Seyer
 * 
 */
public class InvalidPositionException extends Exception {

	/**
	 * Instantiate exception with a message
	 * 
	 * @param msg
	 *            the message
	 */
	public InvalidPositionException(String msg) {
		super(msg);
	}

	/**
	 * Instantiate a new exception
	 */
	public InvalidPositionException() {
		super();
	}

}
