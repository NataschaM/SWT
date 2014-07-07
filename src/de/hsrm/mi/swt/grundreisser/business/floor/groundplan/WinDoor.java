package de.hsrm.mi.swt.grundreisser.business.floor.groundplan;

import java.io.Serializable;

/**
 * Interface for such objects as windows and doors
 * 
 * @author nmuel002
 *
 */
public interface WinDoor extends Serializable {

	/**
	 * Getter for width
	 * 
	 * @return width
	 */
	public int getWidth();

	/**
	 * Getter for center position on the wall
	 * 
	 * @return position
	 */
	public double getPosition();

	/**
	 * Setter for the width
	 * 
	 * @param width
	 *            width
	 */
	public void setWidth(int width);

	/**
	 * Setter for center position on the wall
	 * 
	 * @param p
	 *            center position
	 */
	public void setPosition(double p);

}
