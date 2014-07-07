package de.hsrm.mi.swt.grundreisser.business.command.interior;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;

/**
 * The class defines a command that deletes a fitment from the interior
 * 
 * @author jheba001
 *
 */

public class DeleteFitmentCommand implements Command {

	private Floor floor;
	private Fitment fitment;

	public DeleteFitmentCommand(Floor floor, Fitment fitment) {
		this.fitment = fitment;
		this.floor = floor;
	}

	@Override
	public void execute() {
		floor.getInterior().removeFitment(fitment);
	}

	@Override
	public void undo() {
		floor.getInterior().addFitment(fitment);
	}

}
