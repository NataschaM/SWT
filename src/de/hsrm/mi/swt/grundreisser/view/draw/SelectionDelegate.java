package de.hsrm.mi.swt.grundreisser.view.draw;

/**
 * Delegate that handles selection of a object on screen. A delegate is a
 * listener, but one object could only have on delegate.
 * 
 * @author Simon Seyer
 *
 */
public interface SelectionDelegate {

	/**
	 * Called when a view is selected.
	 * 
	 * @param selectionEvent
	 *            the event that encapsulates the selection
	 */
	public void viewSelected(SelectionEvent selectionEvent);

	/**
	 * Called when a view is unselected
	 * 
	 * @param selectionEvent
	 *            the event that encapsulates the unselection
	 */
	public void viewDeselected(SelectionEvent selectionEvent);
}
