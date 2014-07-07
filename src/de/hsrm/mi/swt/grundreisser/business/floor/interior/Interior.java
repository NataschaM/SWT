package de.hsrm.mi.swt.grundreisser.business.floor.interior;

import java.awt.Point;
import java.io.Serializable;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.catalog.Category;
import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture;
import de.hsrm.mi.swt.grundreisser.business.floor.Observable;
import de.hsrm.mi.swt.grundreisser.business.validation.ValidationListener;

/**
 * This interface represents an interior
 * 
 * @author jheba001
 * 
 */

public interface Interior extends Observable, ValidationListener, Serializable {

	/**
	 * Makes a fitment group
	 * 
	 * @param fitments
	 *            the list of fitments to be grouped
	 * @return
	 */
	public Fitment groupFitments(List<Fitment> fitments);

	public void load();

	/**
	 * Ungroups a grouped fitment
	 * 
	 * @param fitment
	 *            fitment group to be ungrouped
	 */
	public void ungroupFitments(Fitment fitment);

	/**
	 * Adds a new fitment to the interior
	 * 
	 * @param fitment
	 *            the specified fitment
	 */
	public void addFitment(Fitment fitment);

	/**
	 * Removes a fitment form the interior
	 * 
	 * @param fitment
	 *            the fitment to be removed
	 */
	public void removeFitment(Fitment fitment);

	/**
	 * Getter to get a List of fitments in the interior
	 * 
	 * @return list of fitments
	 */
	public List<Fitment> getFitments();

	/**
	 * Persists all interior objects before system closing
	 */
	public void close();

	/**
	 * Adds the created fitment to the catalog as furniture
	 * 
	 * @param fitment
	 *            fitment to be added to the furniture catalog
	 * @param name
	 *            name
	 * @param cat
	 *            category in which the furniture has to be added
	 * @throws Exception
	 */
	public void addToCatalog(Fitment fitment, String name, Category cat)
			throws Exception;

	/**
	 * Creates and adds the fitment to interior from the furniture added by user
	 * 
	 * @param furn
	 *            furniture
	 * @param pos
	 *            central position of the fitment to be added
	 * @return fitment which was created
	 */
	public Fitment addFurnitureToInterior(Furniture furn, Point pos);

}
