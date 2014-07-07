package de.hsrm.mi.swt.grundreisser.view.groundplan;

import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.view.global.View;

/**
 * 
 * Represents the view of the wall.
 * 
 * @author Simon Seyer
 * 
 */
public interface WallView<T extends Wall> extends View<T> {

	/**
	 * Sets the wall view in a dimmed mode, where all views are drawn less opque
	 * 
	 * @param dimmed
	 *            true, for dimmed mode, false else
	 */
	public void setDimmed(boolean dimmed);
}
