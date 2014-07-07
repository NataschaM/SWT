package de.hsrm.mi.swt.grundreisser.business.factories;

import java.awt.Point;

import de.hsrm.mi.swt.grundreisser.business.catalog.Category;
import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;

/**
 * Factory converts the furniture from catalog into fitment as an object placed
 * on the plan.
 * 
 * It converts also the fitment into furniture if it can be added to the catalog
 * 
 * @author nmuel002
 *
 */
public interface FitmentFactory {

	/**
	 * Converts the furniture with the specified id into the fitment object
	 * 
	 * @param id
	 *            furniture id
	 * @param pos
	 *            central position of the fitment to be created
	 * @return fitment
	 */
	public Fitment createFitmentById(int id, Point pos);

	/**
	 * Converts furniture into fitment
	 * 
	 * @param furn
	 *            furniture
	 * @param pos
	 *            central position of the fitment to be created
	 * @return fitment
	 */
	public Fitment createFitmentFromFurniture(Furniture furn, Point pos);

	/**
	 * Converts fitment into furniture
	 * 
	 * @param fitment
	 *            fitment has to be added to the catalog as furniture
	 * @param name
	 *            name of furniture
	 * @param cat
	 *            category
	 * @return a new furniture which has to be added to the catalog
	 * @throws Exception
	 */
	public Furniture createFurnitureFromFitment(Fitment fitment, String name,
			Category cat) throws Exception;

}
