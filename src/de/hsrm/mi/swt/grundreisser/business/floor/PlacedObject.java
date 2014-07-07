package de.hsrm.mi.swt.grundreisser.business.floor;

import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.validation.warnings.ValidationWarning;
import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;

/**
 * Interface for all objects which are placed on the plan. It makes possible to
 * manage objects of various types inside of one structure
 * 
 * @author nmuel002
 *
 */
public interface PlacedObject {

	/**
	 * Getter for Rectangle (the area of the object)
	 * 
	 * @return Rectangle
	 */
	public BackendRectangle getRect();

	/**
	 * Returns a list of objects which area are intersecting the area of this
	 * object
	 * 
	 * @return a list of all intersecting objects
	 */
	public List<PlacedObject> intersects(BackendRectangle rect);

	/**
	 * Returns a list of warnings
	 * 
	 * @return a warning list
	 */
	public List<ValidationWarning> getWarnings();

	/**
	 * Adds a new warning to the warning list
	 * 
	 * @param warning
	 *            validation warning
	 */
	public void addWarning(ValidationWarning warning);

	/**
	 * Removes a validation warning from a list
	 * 
	 * @param warning
	 *            validation warning that it is no more actually
	 */
	public void removeWarning(ValidationWarning warning);

	/**
	 * Removes all validation warnings for the specified object
	 * 
	 * @param po
	 *            specified object, which warnings have to be removed
	 */
	public void removeWarning(PlacedObject po);

	/**
	 * Removes all validation warnings
	 */
	public void clearWarnings();

}
