package de.hsrm.mi.swt.grundreisser.business.catalog;

import java.awt.Dimension;
import java.io.Serializable;

/**
 * Interface for furniture saved and loaded in the catalog. It can be products
 * of a furniture house or the items created by user
 * 
 * @author nmuel002
 *
 */
public interface Furniture extends Cloneable, Serializable {

	/**
	 * enum to distinguish different types of furniture
	 * 
	 * @author nmuel002
	 *
	 */
	public enum FurnitureType {
		CATALOG, CUSTOM, GROUP
	};

	/**
	 * Returns the id of the item in catalog
	 * 
	 * @return id
	 */
	public int getId();

	/**
	 * Returns the size of the furniture
	 * 
	 * @return dimension: width and height
	 */
	public Dimension getSize();

	/**
	 * Returns the name of the furniture
	 * 
	 * @return name
	 */
	public String getName();

	/**
	 * Returns the type of the furniture
	 * 
	 * @return furniture type
	 */
	public FurnitureType getFurnitureType();

	public Object clone();

}
