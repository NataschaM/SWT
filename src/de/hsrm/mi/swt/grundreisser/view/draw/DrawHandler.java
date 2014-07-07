package de.hsrm.mi.swt.grundreisser.view.draw;

import java.awt.Point;
import java.awt.dnd.DropTargetListener;
import java.util.TooManyListenersException;

import javax.swing.event.MouseInputListener;

import de.hsrm.mi.swt.grundreisser.view.groundplan.GroundPlanView;
import de.hsrm.mi.swt.grundreisser.view.interior.InteriorView;
import de.hsrm.mi.swt.grundreisser.view.util.PixelConverter;
import de.hsrm.mi.swt.grundreisser.view.util.ViewFactory;

/**
 * 
 * An interface to get access to the panel that does the drawing and holds the
 * view hierarchy.
 * 
 * @author Simon Seyer
 * 
 */
public interface DrawHandler {

	/**
	 * Set a mouse listener, that is used when the ui is in the ViewState
	 * GROUND_PLAN_DRAW.
	 * 
	 * @param mouseListener
	 *            the mouse listener
	 * @see de.hsrm.mi.swt.grundreisser.view.global.ViewState
	 */
	public void setMouseListener(MouseInputListener mouseListener);

	/**
	 * This selection delegate is used, when the view is in the GROUND_PLAN_DRAW
	 * mode and is called, when a view on the draw panel is selected.
	 * 
	 * @param selectionDelegate
	 *            the selection delegate
	 */
	public void setSelectionDelegate(SelectionDelegate selectionDelegate);

	/**
	 * Adds a drop target listener, so dropped events on the draw pane could be
	 * received.
	 * 
	 * @param listener
	 *            the listenter to set
	 */
	public void setDropTargetListener(DropTargetListener listener)
			throws TooManyListenersException;

	/**
	 * Get the ground plan view
	 * 
	 * @return the ground plan view
	 */
	public GroundPlanView getGroundPlanView();

	/**
	 * Get the interior view
	 * 
	 * @return the interior view
	 */
	public InteriorView getInteriorView();

	/**
	 * Get the factory to create views
	 * 
	 * @return the view factory
	 */
	public ViewFactory getViewFactory();

	/**
	 * Get a converter to convert model and view coordinates
	 * 
	 * @return a pixel converter
	 */
	public PixelConverter getPixelConverter();

	/**
	 * Get a point on the draw pane for a absolute position on screen. Warning:
	 * this could only be used directly after a mouse event occured, because the
	 * window could be moved and then the calculation will fail.
	 * 
	 * @param absolutePoint
	 *            the absolute point on screen
	 * @return the relative point in the draw pane or null, if the point lays
	 *         not in the draw pane
	 */
	public Point getDrawPoint(Point absolutePoint);

	/**
	 * Scales the whole view up
	 */
	public void zoomIn();

	/**
	 * Scales the whole view down
	 */
	public void zoomOut();

}
