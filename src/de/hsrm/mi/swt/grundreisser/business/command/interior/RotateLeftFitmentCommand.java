package de.hsrm.mi.swt.grundreisser.business.command.interior;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;

/**
 * The class defines a command that rotates a fitment to the left
 * 
 * @author jheba001
 *
 */

public class RotateLeftFitmentCommand implements Command {

	private Fitment fitment;

	public RotateLeftFitmentCommand(Fitment fitment) {
		this.fitment = fitment;
	}

	@Override
	public void execute() {
		fitment.rotateLeft();
	}

	@Override
	public void undo() {
		fitment.rotateRight();
	}

}
