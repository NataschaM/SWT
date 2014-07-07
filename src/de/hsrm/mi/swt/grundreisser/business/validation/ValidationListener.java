package de.hsrm.mi.swt.grundreisser.business.validation;

import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.exceptions.ValidationException;

/**
 * Interface for the objects which have to estimate user interactions (addition
 * of objects etc.)
 * 
 * @author nmuel002
 *
 */
public interface ValidationListener {

	/**
	 * Method validates if the object can be placed on the plan
	 * 
	 * @param po
	 *            placed object
	 * @throws ValidationException
	 */
	public void validate(PlacedObject po) throws ValidationException;

	/**
	 * Setter for superior validator, which has to give throw the further
	 * validation
	 * 
	 * @param validator
	 *            superior validator
	 */
	public void setValidator(ValidationListener validator);
}
