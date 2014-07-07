package de.hsrm.mi.swt.grundreisser.business.command.wall;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.command.exceptions.UndoRedoException;

/**
 * This is the implementation of the command manager. It implements the
 * CommandManager Interface.
 * 
 * @author jheba001
 *
 */

public class CommandManagerImpl implements CommandManager {
	private List<Command> commands;
	private int index;

	/**
	 * Constructor of the CommandManager class
	 */
	public CommandManagerImpl() {
		commands = new ArrayList<Command>();
		index = 0;
	}

	@Override
	public void undo() throws UndoRedoException {
		if (index > 0) {
			index--;
			commands.get(index).undo();
		} else
			throw new UndoRedoException("Undo: no commands available");
	}

	@Override
	public void redo() throws UndoRedoException {
		if (index < commands.size()) {
			commands.get(index).execute();
			index++;
		} else
			throw new UndoRedoException("Redo: no commands available");
	}

	@Override
	public void execAndPush(Command cmd) {
		if (index < commands.size())
			commands = commands.subList(0, index);
		cmd.execute();
		commands.add(cmd);
		index++;
	}

	/**
	 * @return The list of commands
	 */
	public List<Command> getList() {
		return commands.subList(0, index);
	}
}
