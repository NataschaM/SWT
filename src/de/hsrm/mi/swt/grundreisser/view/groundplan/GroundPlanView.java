package de.hsrm.mi.swt.grundreisser.view.groundplan;

import de.hsrm.mi.swt.grundreisser.view.global.ViewComponent;

/**
 * Displays and manages all the views of the ground plan
 * 
 * @author Simon Seyer
 * 
 */
public interface GroundPlanView extends ViewComponent {

	/**
	 * Adds a dummy wall view, that is not intended to stay longer than the
	 * current user action in the ground plan view.
	 * 
	 * @param wallView
	 *            the dummy wall view
	 */
	public void addDummyWallView(DummyWallView wallView);

	/**
	 * Removes the added dummy wall view. If the dummy view is not present in
	 * the ground plan view, nothing happens.
	 * 
	 * @param wallView
	 *            the added dummy wall view
	 */
	public void removeDummyWallView(DummyWallView wallView);

	/**
	 * Sets the ground plan view in a dimmed mode, where all sub views are drawn
	 * less opque
	 * 
	 * @param dimmed
	 *            true, for dimmed mode, false else
	 */
	public void setDimmed(boolean dimmed);

	/**
	 * Updates the whole with values from the model
	 */
	public void updateView();
}
