package de.hsrm.mi.swt.grundreisser.business.command.interior;

import java.awt.Point;

import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture;
import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;

/**
 * The class defines a command that adds a furniture to the interior
 * 
 * @author jheba001
 * 
 */

public class AddFurnitureCommand implements Command {

	private Floor floor;
	private Fitment fitment;
	private Furniture furn;
	private Point pos;

	public AddFurnitureCommand(Floor floor, Furniture furn, Point pos) {
		this.furn = furn;
		this.floor = floor;
		this.pos = pos;
	}

	@Override
	public void execute() {
		this.fitment = floor.getInterior().addFurnitureToInterior(furn, pos);
	}

	@Override
	public void undo() {
		floor.getInterior().removeFitment(fitment);
	}

}
