package de.hsrm.mi.swt.grundreisser.view.global;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * 
 * Holds the current state of the ui to be accessible by all different ui parts.
 * 
 * @author Simon Seyer
 * 
 */
public interface ViewStateHolder {

	/**
	 * Get the current state of the view
	 * 
	 * @return the current state
	 */
	public ViewState getViewState();

	/**
	 * Set a new view state
	 * 
	 * @param viewState
	 *            the new state
	 */
	public void setViewState(ViewState viewState);

	/**
	 * Get a list of all selected views
	 * 
	 * @return a list of selected views
	 */
	public List<View<?>> getSelectedViews();

	/**
	 * Set the currently selected views
	 * 
	 * @param selectedViews
	 *            a list of selected views
	 */
	public void setSelectedViews(List<View<?>> selectedViews);

	/**
	 * Adds a view to the list of selected views
	 * 
	 * @param view
	 *            a view, that was selected
	 */
	public void addSelectedView(View<?> view);

	/**
	 * Removes a view from the list of selected view
	 * 
	 * @param view
	 *            a view, taht was unselected
	 */
	public void removeSelectedView(View<?> view);

	/**
	 * Add a property change listener to get notified when something changed in
	 * the ViewStateHolder.
	 * 
	 * @param l
	 *            the property change listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener l);

	/**
	 * Remove a property change listener.
	 * 
	 * @param l
	 *            the property change listener, that was added before
	 */
	public void removePropertyChangeListener(PropertyChangeListener l);

}
