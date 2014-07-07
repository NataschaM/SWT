package de.hsrm.mi.swt.grundreisser.business.command.windoor;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WinDoor;

/**
 * The class defines a command that deletes a windoor from a wall
 * 
 * @author jheba001
 *
 */

public class ClearWinDoorsCommand implements Command {

	private Wall wall;
	private List<WinDoor> windoors;

	public ClearWinDoorsCommand(Wall wall) {
		this.wall = wall;
	}

	@Override
	public void execute() {
		windoors = new ArrayList<>(wall.getWinDoors());
		for (WinDoor winDoor : windoors) {
			wall.removeWinDoor(winDoor);
		}
	}

	@Override
	public void undo() {
		for (WinDoor winDoor : windoors) {
			wall.addWinDoor(winDoor);
		}
		windoors.clear();
	}

}
