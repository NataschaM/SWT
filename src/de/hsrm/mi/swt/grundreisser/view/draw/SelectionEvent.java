package de.hsrm.mi.swt.grundreisser.view.draw;

import java.awt.event.MouseEvent;

import de.hsrm.mi.swt.grundreisser.view.global.View;

/**
 * This class encapsulates all information of a selection event the user
 * initiated with a click on a view in the draw pane.
 * 
 * @author Simon Seyer
 *
 */
public interface SelectionEvent {

	/**
	 * Get the view, that was selected with this event. This view has not be
	 * equal with the clicked view. For example view groups would change the
	 * selected view so the whole group is selected when clicking on one
	 * part-view.
	 * 
	 * @return the selected view
	 */
	public View<?> getSelectedView();

	/**
	 * Get the clicked view. This view is always the view with the mouse
	 * listener, that received the click event.
	 * 
	 * @return the clicked view
	 */
	public View<?> getClickedView();

	/**
	 * Get the mouse event that triggered the selection.
	 * 
	 * @return the mouse event
	 */
	public MouseEvent getMouseEvent();

	/**
	 * Changes the selected view. This is for example used by view groups. They
	 * would change the selected view so the whole group is selected when
	 * clicking on one part-view.
	 * 
	 * @param view
	 *            the new selected view
	 */
	public void updateSelectedView(View<?> view);
}
