package de.hsrm.mi.swt.grundreisser.business.command.windoor;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WinDoor;

/**
 * The class defines a command that adds a windoor to a wall
 * 
 * @author jheba001
 *
 */

public class AddWinDoorCommand implements Command {

	private Wall wall;
	private WinDoor windoor;

	public AddWinDoorCommand(Wall wall, WinDoor windoor) {
		this.wall = wall;
		this.windoor = windoor;
	}

	@Override
	public void execute() {
		wall.addWinDoor(windoor);
	}

	@Override
	public void undo() {
		wall.removeWinDoor(windoor);
	}

}
