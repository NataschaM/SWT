package de.hsrm.mi.swt.grundreisser.view.global;

import javax.swing.JComponent;

/**
 * This interface is used to get the swing component of a view. This is needed,
 * because we would like to work with interfaces and there are no interfaces for
 * swing components we could extend.
 * 
 * @author Simon Seyer
 * 
 */
public interface ViewComponent {

	/**
	 * Get the swing component of this view component. Should normally return
	 * this.
	 * 
	 * @return the component
	 */
	public JComponent getComponent();

}
