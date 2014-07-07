package de.hsrm.mi.swt.grundreisser.business.command.exceptions;

/**
 * Exception for undo/redo operations
 * 
 * @author jheba001
 *
 */

@SuppressWarnings("serial")
public class UndoRedoException extends Exception {

	/**
	 * Constructor of the Exception
	 * 
	 * @param msg
	 *            the failure message
	 */
	public UndoRedoException(String msg) {
		super(msg);
	}
}