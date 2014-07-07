package de.hsrm.mi.swt.grundreisser.view.global;

import javax.swing.JPanel;

import de.hsrm.mi.swt.grundreisser.view.draw.MainView;

/**
 * 
 * The default implementation for a view
 * 
 * @author Simon Seyer
 * 
 */
public abstract class ViewImpl extends JPanel {

	protected MainView mainView;

	/**
	 * Init a view that holds a main view
	 * 
	 * @param mainView
	 *            a main view
	 */
	public ViewImpl(MainView mainView) {
		this.mainView = mainView;
	}

}
