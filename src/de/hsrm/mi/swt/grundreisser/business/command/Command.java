package de.hsrm.mi.swt.grundreisser.business.command;

/**
 * This interface describes a command
 * 
 * @author jheba001
 *
 */

public interface Command {

	/**
	 * Executs the command
	 */
	public void execute();

	/**
	 * Undoes the command
	 */
	public void undo();
}
