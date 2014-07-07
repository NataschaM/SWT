package de.hsrm.mi.swt.grundreisser.business.command.wall;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;

/**
 * The class defines a command that adds a wall to the ground-plan
 * 
 * @author jheba001
 *
 */

public class AddWallCommand implements Command {

	private Floor floor;
	private Wall wall;

	public AddWallCommand(Floor floor, Wall wall) {
		this.floor = floor;
		this.wall = wall;
	}

	@Override
	public void execute() {
		floor.getGroundPlan().addWall(wall);
	}

	@Override
	public void undo() {
		floor.getGroundPlan().removeWall(wall);
	}

}
