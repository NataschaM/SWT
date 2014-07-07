package de.hsrm.mi.swt.grundreisser.business.validation;

import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;

/**
 * Collision object for validating of the layer concept
 * 
 * @author nmuel002
 *
 */
public class Collision {

	private PlacedObject obj1;
	private PlacedObject obj2;

	/**
	 * Constructor
	 * 
	 * @param obj1
	 *            the 1st collide object
	 * @param obj2
	 *            the 2nd collide object
	 */
	public Collision(PlacedObject obj1, PlacedObject obj2) {
		this.obj1 = obj1;
		this.obj2 = obj2;
	}

	/**
	 * Returns object 1 in the collision
	 * 
	 * @return placed object 1
	 */
	public PlacedObject getObj1() {
		return obj1;
	}

	/**
	 * Returns object 2 in the collision
	 * 
	 * @return placed object 2
	 */
	public PlacedObject getObj2() {
		return obj2;
	}

	/**
	 * Returns intersection area of two collide objects
	 * 
	 * @return intersection rectangle
	 */
	public BackendRectangle getIntersection() {
		return obj1.getRect().intersection(obj2.getRect());
	}

}
