package de.hsrm.mi.swt.grundreisser.business.command.interior;

import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;

/**
 * The class defines a command that groups fitments
 * 
 * @author jheba001
 * 
 */

public class UnGroupFitmentCommand implements Command {

	private Floor floor;
	private List<Fitment> fitments;
	private Fitment group;

	public UnGroupFitmentCommand(Floor floor, Fitment group) {
		this.floor = floor;
		this.group = group;
	}

	@Override
	public void execute() {
		floor.getInterior().ungroupFitments(group);
	}

	@Override
	public void undo() {
		group = floor.getInterior().groupFitments(fitments);
	}

}
