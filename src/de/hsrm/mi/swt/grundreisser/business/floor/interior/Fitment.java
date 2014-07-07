package de.hsrm.mi.swt.grundreisser.business.floor.interior;

import java.awt.Point;
import java.io.Serializable;

import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture.FurnitureType;
import de.hsrm.mi.swt.grundreisser.business.floor.Observable;
import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.validation.ValidationListener;
import de.hsrm.mi.swt.grundreisser.util.Vector;

/**
 * Fitment represents the furniture objects which are placed on the plan
 * 
 * @author nmuel002
 * 
 */
public interface Fitment extends Observable, PlacedObject, ValidationListener,
		Cloneable, Serializable {

	/**
	 * Returns the id of the furniture which this fitment is in accordance with.
	 * 
	 * @return furniture id
	 */
	public int getFurnitureId();

	public void load();

	/**
	 * Moves the fitment to the new center
	 * 
	 * @param newPoint
	 */
	public void move(Point newPoint);

	/**
	 * Moves the fitment by the specified vector
	 * 
	 * @param translation
	 *            translation vector
	 */
	public void move(Vector translation);

	/**
	 * Rotates the fitment to the left
	 */
	public void rotateLeft();

	/**
	 * Rotates the fitment to the right
	 */
	public void rotateRight();

	/**
	 * Returns the center location of fitment
	 * 
	 * @return center point
	 */
	public Point getCenterLocation();

	/**
	 * Returns a boolean value for information whether the object belongs to the
	 * group of objects or not
	 * 
	 * @return true - if it is the grouped fitment, false - for simple fitment
	 */
	public boolean isGroup();

	/**
	 * Gets the furniture type (CATALOG, CUSTOM or GROUP)
	 * 
	 * @return furniture type
	 */
	public FurnitureType getType();

	/**
	 * Gets the name of the referred furniture
	 * 
	 * @return name of furniture
	 */
	public String getName();

	public Object clone();
}
