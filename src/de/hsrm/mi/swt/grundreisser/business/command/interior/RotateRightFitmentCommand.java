package de.hsrm.mi.swt.grundreisser.business.command.interior;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;

/**
 * The class defines a command that rotates a fitment to the left
 * 
 * @author jheba001
 *
 */

public class RotateRightFitmentCommand implements Command {

	private Fitment fitment;

	public RotateRightFitmentCommand(Fitment fitment) {
		this.fitment = fitment;
	}

	@Override
	public void execute() {
		fitment.rotateRight();
	}

	@Override
	public void undo() {
		fitment.rotateLeft();
	}

}
