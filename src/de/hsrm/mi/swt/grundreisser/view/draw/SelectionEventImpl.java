package de.hsrm.mi.swt.grundreisser.view.draw;

import java.awt.event.MouseEvent;

import de.hsrm.mi.swt.grundreisser.view.global.View;

/**
 * Default implementation of a selection event.
 * 
 * @author Simon Seyer
 *
 */
public class SelectionEventImpl implements SelectionEvent {

	private View<?> selectedView;
	private View<?> clickedView;
	private MouseEvent mouseEvent;

	/**
	 * Create a selection event with a view and a mouseEvent. The clicked view
	 * is assumed as selected view by default.
	 * 
	 * @param clickedView
	 *            the view, the user clicked on
	 * @param mouseEvent
	 *            the mouse event that was triggered
	 */
	public SelectionEventImpl(View<?> clickedView, MouseEvent mouseEvent) {
		this.selectedView = clickedView;
		this.clickedView = clickedView;
		this.mouseEvent = mouseEvent;
	}

	@Override
	public View<?> getSelectedView() {
		return selectedView;
	}

	@Override
	public View<?> getClickedView() {
		return clickedView;
	}

	@Override
	public MouseEvent getMouseEvent() {
		return mouseEvent;
	}

	@Override
	public void updateSelectedView(View<?> view) {
		this.selectedView = view;
	}

}
