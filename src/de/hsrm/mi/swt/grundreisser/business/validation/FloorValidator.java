package de.hsrm.mi.swt.grundreisser.business.validation;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.exceptions.ValidationException;

/**
 * Validator which validates all actions on the floor
 * 
 * @author nmuel002
 *
 */
public class FloorValidator implements ModellValidator {

	private List<ModellValidator> validators;

	/**
	 * Constructor
	 */
	public FloorValidator() {
		this.validators = new ArrayList<ModellValidator>();
	}

	/**
	 * Adds a validator to the floor validation concept
	 * 
	 * @param val
	 *            validator
	 */
	public void addValidator(ModellValidator val) {
		this.validators.add(val);
	}

	/**
	 * Removes a validator from the floor validation concept
	 * 
	 * @param val
	 *            validator
	 */
	public void deleteValidator(ModellValidator val) {
		if (this.validators.contains(val)) {
			this.validators.remove(val);
		}
	}

	@Override
	public void validate(List<PlacedObject> placedObjs, PlacedObject obj)
			throws ValidationException {

		for (ModellValidator val : this.validators) {
			val.validate(placedObjs, obj);
		}
	}

}
