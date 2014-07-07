package de.hsrm.mi.swt.grundreisser.view.actions;

/**
 * An action panel is panel that displays meta information of the objects on the
 * draw pane and allows to manipulate them.
 * 
 * @author Simon Seyer
 *
 */
public interface ActionPanel {

	/**
	 * Sets the action panel active, so it could enable/disable it's listener
	 * and reset state.
	 * 
	 * @param active
	 *            true, if the panel shold be active, false otherwise
	 */
	public void setActive(boolean active);
}
