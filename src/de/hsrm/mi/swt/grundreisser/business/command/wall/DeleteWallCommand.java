package de.hsrm.mi.swt.grundreisser.business.command.wall;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;

/**
 * The class defines a command that deletes a wall from the ground-plan
 * 
 * @author jheba001
 *
 */

public class DeleteWallCommand implements Command {

	private Floor floor;
	private Wall wall;

	public DeleteWallCommand(Floor floor, Wall wall) {
		this.floor = floor;
		this.wall = wall;
	}

	@Override
	public void execute() {
		floor.getGroundPlan().removeWall(wall);
	}

	@Override
	public void undo() {
		floor.getGroundPlan().addWall(wall);
	}

}
