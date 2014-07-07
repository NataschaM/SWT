package de.hsrm.mi.swt.grundreisser.view.interior;

import de.hsrm.mi.swt.grundreisser.view.global.ViewComponent;

/**
 * Displays and manages all the furniture views
 * 
 * @author Simon Seyer
 * 
 */
public interface InteriorView extends ViewComponent {

	/**
	 * Updates the whole with values from the model
	 */
	public void updateView();
}
