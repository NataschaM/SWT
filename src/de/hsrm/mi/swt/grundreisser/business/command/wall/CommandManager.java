package de.hsrm.mi.swt.grundreisser.business.command.wall;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.command.exceptions.UndoRedoException;

/**
 * This interface describes the command manager, which manage the commands.
 * 
 * @author jheba001
 *
 */

public interface CommandManager {

	/**
	 * Undoes a command
	 * 
	 * @throws UndoRedoException
	 *             is thrown if there are no commands to undo
	 */
	public void undo() throws UndoRedoException;

	/**
	 * Redoes a command
	 * 
	 * @throws UndoRedoException
	 *             is thrown if there are no commands to redo
	 */
	public void redo() throws UndoRedoException;

	/**
	 * Execute a command and add it to the list
	 * 
	 * @param cmd
	 *            command that shall be executed
	 */
	public void execAndPush(Command cmd);
}
