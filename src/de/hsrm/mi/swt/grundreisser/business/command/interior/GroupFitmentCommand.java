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

public class GroupFitmentCommand implements Command {

	private Floor floor;
	private List<Fitment> fitments;
	private Fitment group;

	public GroupFitmentCommand(Floor floor, List<Fitment> fitments) {
		this.floor = floor;
		this.fitments = fitments;
	}

	@Override
	public void execute() {
		group = floor.getInterior().groupFitments(fitments);
	}

	@Override
	public void undo() {
		floor.getInterior().ungroupFitments(group);
	}

}
