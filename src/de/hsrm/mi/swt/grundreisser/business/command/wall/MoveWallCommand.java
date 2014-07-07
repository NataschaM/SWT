package de.hsrm.mi.swt.grundreisser.business.command.wall;

import java.awt.Point;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;

/**
 * The class defines a command that moves a wall to a given destination on the
 * ground-plan
 * 
 * @author jheba001
 *
 */

public class MoveWallCommand implements Command {

	private Wall wall;
	private Point oldPosition;
	private Point newPosition;

	public MoveWallCommand(Wall wall, Point newPosition) {
		this.wall = wall;
		this.oldPosition = wall.getCenterLocation();
		this.newPosition = newPosition;
	}

	@Override
	public void execute() {
		wall.setCenterLocation(newPosition);
	}

	@Override
	public void undo() {
		wall.setCenterLocation(oldPosition);
	}

}
