package de.hsrm.mi.swt.grundreisser.business.floor;

import java.util.List;

import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;

/**
 * Interface for placed objects which do not contain any other placed objects,
 * but can be built together in groups
 * 
 * @author nmuel002
 *
 */
public interface SinglePlacedObject extends PlacedObject {

	/**
	 * Returns the layer of the object
	 * 
	 * @return layer
	 */
	public int getLayer();

	/**
	 * Returns the intersection with a rectangle
	 * 
	 * @param rect
	 *            rectangle
	 * @return true - if intersects, otherwise false
	 */
	public List<PlacedObject> intersects(BackendRectangle rect);

	/**
	 * Returns the boolean value if this placed object is colliding to other
	 * objects
	 * 
	 * @return true if collide, false otherwise
	 */
	public boolean getCollide();

	/**
	 * Sets collide value
	 * 
	 * @param collide
	 *            collide value
	 */
	public void setCollide(boolean collide);

}
