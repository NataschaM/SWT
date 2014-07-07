package de.hsrm.mi.swt.grundreisser.view.util;

import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.view.groundplan.DummyWallView;
import de.hsrm.mi.swt.grundreisser.view.groundplan.WallView;
import de.hsrm.mi.swt.grundreisser.view.interior.FitmentView;

/**
 * 
 * A factory to create the different views that are drawn to the draw pane
 * 
 * @author Simon Seyer
 * 
 */
public interface ViewFactory {

	/**
	 * Create a fitment view for a fitment model object
	 * 
	 * @param fitment
	 *            the fitment model
	 * @return a fitment view
	 */
	public FitmentView<?> createFitmentView(Fitment fitment);

	/**
	 * Create a wall view for a wall model object
	 * 
	 * @param wall
	 *            the wall model
	 * @return a wall view
	 */
	public WallView<?> createWallView(Wall wall);

	/**
	 * Create a wall view that is not asocciated to a wall model. This view
	 * should be only used to display temporary data, before a valid model
	 * object could be created.
	 * 
	 * @return a dummy wall view
	 */
	public DummyWallView createDummyWallView();

}
