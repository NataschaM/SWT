package de.hsrm.mi.swt.grundreisser.business.floor.groundplan;

import java.awt.Point;
import java.io.Serializable;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.floor.Observable;
import de.hsrm.mi.swt.grundreisser.business.validation.ValidationListener;

/**
 * Interface for the structure which manages all walls on the plan
 * 
 * @author nmuel002
 *
 */
public interface GroundPlan extends ValidationListener, Observable,
		Serializable {

	/**
	 * Getter for all walls of the ground plan
	 * 
	 * @return a list of walls
	 */
	public List<Wall> getWalls();

	public void load();

	/**
	 * Method that adds a new wall to the ground plan
	 * 
	 * @param wall
	 *            a new wall
	 * @return true if the wall was added, false otherwise
	 */
	public boolean addWall(Wall wall);

	/**
	 * Method that adds a new wall to the ground plan
	 * 
	 * @param wallPoints
	 *            the points of the new wall
	 * @return true if the wall was added, false otherwise
	 */
	public boolean addWall(List<Point> wallPoints);

	/**
	 * Removes the wall from ground plan
	 * 
	 * @param wall
	 *            the wall to be removed
	 */
	public boolean removeWall(Wall wall);

	/**
	 * Adds a new windoor-object to a wall on ground plan
	 * 
	 * @param wd
	 *            the windoor to be added
	 * @param wall
	 *            the wall where the windoor to be added
	 * @return true if the wall was added, false otherwise
	 */
	public boolean addWinDoor(WinDoor wd, Wall wall);

	/**
	 * Removes a windoor from a wall on ground plan
	 * 
	 * @param wd
	 *            the windoor to be removed
	 * @param wall
	 *            the wall where the windoor to be removed
	 */
	public boolean removeWinDoor(WinDoor wd, Wall wall);

	/**
	 * Method must insure the persistence of all objects in ground plan
	 */
	public void close();

}
