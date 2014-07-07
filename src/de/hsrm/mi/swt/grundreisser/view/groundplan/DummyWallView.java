package de.hsrm.mi.swt.grundreisser.view.groundplan;

import java.awt.Point;

import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.view.exceptions.InvalidPositionException;

public interface DummyWallView extends WallView<Wall> {

	/**
	 * Set the first point of the wall for live drawing of the wall.
	 * 
	 * @param point
	 *            the first point the user specified
	 * @throws InvalidPositionException
	 */
	public void setDummyPoint1(Point point) throws InvalidPositionException;

	/**
	 * Set the second point of the wall for live drawing of the wall.
	 * 
	 * @param point
	 *            the second point the user specified.
	 * @throws InvalidPositionException
	 */
	public void setDummyPoint2(Point point) throws InvalidPositionException;

	/**
	 * Set the first point of a windoor for live drawing.
	 * 
	 * @param p1
	 *            the first point the user specified
	 * @throws InvalidPositionException
	 */
	public void setDummyWindoorPoint1(Point p1) throws InvalidPositionException;

	/**
	 * Set the second point of a windoor for live drawing.
	 * 
	 * @param p2
	 *            the second point the user specified
	 * @throws InvalidPositionException
	 */
	public void setDummyWindoorPoint2(Point p2) throws InvalidPositionException;

	/**
	 * Clear the dummy windoor view
	 */
	public void clearWindoorDummy();
}
