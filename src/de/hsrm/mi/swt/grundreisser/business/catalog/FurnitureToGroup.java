package de.hsrm.mi.swt.grundreisser.business.catalog;

import java.awt.Point;
import java.io.Serializable;

/**
 * This class represents furniture object in the group with the local
 * coordinates system. Furniture objects have to have local coordinates to be
 * placed into a group.
 * 
 * @author nmuel002
 * 
 */
public class FurnitureToGroup implements Serializable, Cloneable {

	private Furniture furniture;
	private Point position;

	/**
	 * Constructor
	 * 
	 * @param furniture
	 *            furniture object
	 * @param position
	 *            center position in the group
	 */
	public FurnitureToGroup(Furniture furniture, Point position) {
		this.furniture = furniture;
		this.position = position;
	}

	/**
	 * Returns furniture object
	 * 
	 * @return furniture object
	 */
	public Furniture getFurnitureObject() {
		return this.furniture;
	}

	/**
	 * Returns center position in the group
	 * 
	 * @return center position
	 */
	public Point getPosition() {
		return this.position;
	}

	public String toString() {
		return String.format("Furniture to group [%s, position: %s]",
				furniture, position);
	}

	public Object clone() {
		return new FurnitureToGroup((Furniture) furniture.clone(), position);
	}

}
