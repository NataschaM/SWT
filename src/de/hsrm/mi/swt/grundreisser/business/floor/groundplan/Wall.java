package de.hsrm.mi.swt.grundreisser.business.floor.groundplan;

import java.awt.Point;
import java.io.Serializable;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.floor.Observable;
import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.validation.ValidationListener;
import de.hsrm.mi.swt.grundreisser.util.Vector;

/**
 * Interface for wall
 * 
 * @author nmuel002
 *
 */
public interface Wall extends PlacedObject, Observable, ValidationListener,
		Serializable {

	/**
	 * Adds a window or a door on the wall
	 * 
	 * @param windoor
	 *            a window or a door
	 * @return true if the windoor was added, false otherwise
	 */
	public boolean addWinDoor(WinDoor windoor);

	public void load();

	/**
	 * Removes a window or a door from the wall
	 * 
	 * @param windoor
	 *            a window or a door
	 * @return true if removed, false otherwise
	 */
	public boolean removeWinDoor(WinDoor windoor);

	/**
	 * Setter for center point of the wall
	 * 
	 * @param p
	 *            center point
	 */
	public void setCenterLocation(Point p);

	/**
	 * Returns the center location of the wall
	 * 
	 * @return center point
	 */
	public Point getCenterLocation();

	/**
	 * Returns positions of the wall. It can be the end points of the inner wall
	 * or the corner points of the outer wall
	 * 
	 * @return list of points
	 */
	public List<Point> getPositions();

	/**
	 * Setter for positions of the wall
	 * 
	 * @param points
	 *            list of points
	 */
	public void setPositions(List<Point> points);

	/**
	 * Setter for thickness of the wall
	 * 
	 * @param thickness
	 *            thickness
	 */
	public void setThickness(int thickness);

	/**
	 * Getter for the windoors
	 * 
	 * @return list with windoors on a wall
	 */
	public List<WinDoor> getWinDoors();

	/**
	 * Moves the wall with the specified vector
	 * 
	 * @param v
	 *            vector
	 */
	public void move(Vector v);

	/**
	 * Checks the orientation of the wall
	 * 
	 * @return true if horizontal, false otherwise
	 */
	public boolean isHorizontal();
}
