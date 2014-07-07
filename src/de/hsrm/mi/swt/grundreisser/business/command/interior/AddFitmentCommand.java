package de.hsrm.mi.swt.grundreisser.business.command.interior;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;

/**
 * The class defines a command that adds a fitment to the interior
 * 
 * @author jheba001
 * 
 */

public class AddFitmentCommand implements Command {

	private Floor floor;
	private Fitment fitment;

	public AddFitmentCommand(Floor floor, Fitment fitment) {
		this.floor = floor;
		this.fitment = fitment;
	}

	@Override
	public void execute() {
		floor.getInterior().addFitment(fitment);
	}

	@Override
	public void undo() {
		floor.getInterior().removeFitment(fitment);
	}

}
