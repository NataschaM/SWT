package de.hsrm.mi.swt.grundreisser.business.floor;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.command.wall.CommandManager;
import de.hsrm.mi.swt.grundreisser.business.command.wall.CommandManagerImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.exceptions.ValidationException;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.GroundPlan;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.GroundPlanImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Interior;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.InteriorImpl;
import de.hsrm.mi.swt.grundreisser.business.validation.CollisionValidator;
import de.hsrm.mi.swt.grundreisser.business.validation.FloorValidator;
import de.hsrm.mi.swt.grundreisser.business.validation.ModellValidator;
import de.hsrm.mi.swt.grundreisser.business.validation.ValidationListener;

/**
 * This is an implementation of the Floor interface for the managing of all
 * ground plan and interior objects on the outline
 * 
 * @author nmuel002
 *
 */
public class FloorImpl implements Floor {

	private GroundPlan groundPlan;
	private transient CommandManager cm;
	private transient ModellValidator validator;
	private Interior interior;

	public FloorImpl() {
		this.groundPlan = new GroundPlanImpl();
		this.groundPlan.setValidator(this);
		this.cm = new CommandManagerImpl();
		this.validator = new FloorValidator();
		((FloorValidator) this.validator)
				.addValidator(new CollisionValidator());
		this.interior = new InteriorImpl();
		this.interior.setValidator(this);
	}

	@Override
	public void validate(PlacedObject po) throws ValidationException {
		this.validator.validate(getPlacedObjects(), po);
	}

	public void load() {
		this.interior.load();
		this.groundPlan.load();
		this.cm = new CommandManagerImpl();
		this.validator = new FloorValidator();
		((FloorValidator) this.validator)
		.addValidator(new CollisionValidator());
	}

	@Override
	public List<PlacedObject> getPlacedObjects() {
		List<PlacedObject> placedObjects = new ArrayList<PlacedObject>();
		List<Wall> walls = this.groundPlan.getWalls();

		for (Wall wall : walls) {
			placedObjects.add(wall);
		}
		for (Fitment fit : this.interior.getFitments()) {
			placedObjects.add(fit);
		}

		return placedObjects;
	}

	@Override
	public GroundPlan getGroundPlan() {
		return this.groundPlan;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public CommandManager getCommandManager() {
		return cm;
	}

	@Override
	public void setValidator(ValidationListener validator) {
		// TODO Auto-generated method stub
	}

	@Override
	public Interior getInterior() {
		return this.interior;
	}

}
