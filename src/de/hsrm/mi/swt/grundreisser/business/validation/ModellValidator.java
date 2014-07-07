package de.hsrm.mi.swt.grundreisser.business.validation;

import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.exceptions.ValidationException;

/**
 * Interface for validation of an user interaction
 * 
 * @author nmuel002
 * 
 */
public interface ModellValidator {

	/**
	 * Validates the correctness of user's changes
	 * 
	 * @param placedObjs
	 *            list of all objects placed on the plan
	 * @param obj
	 *            object which the changes were done on
	 * @throws ValidationException
	 */
	public void validate(List<PlacedObject> placedObjs, PlacedObject obj)
			throws ValidationException;

}
