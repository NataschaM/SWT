package de.hsrm.mi.swt.grundreisser.business.command.interior;

import java.awt.Point;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;

/**
 * The class defines a command that moves a fitment to a given destination
 * 
 * @author jheba001
 *
 */

public class MoveFitmentCommand implements Command {

	private Fitment fitment;
	private Point oldPosition;
	private Point newPosition;

	public MoveFitmentCommand(Fitment fitment, Point newPosition) {
		this.fitment = fitment;
		this.oldPosition = fitment.getCenterLocation();
		this.newPosition = newPosition;
	}

	@Override
	public void execute() {
		fitment.move(newPosition);
	}

	@Override
	public void undo() {
		fitment.move(oldPosition);
	}

}
