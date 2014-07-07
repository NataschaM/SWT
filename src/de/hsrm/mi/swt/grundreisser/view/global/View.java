package de.hsrm.mi.swt.grundreisser.view.global;

import java.awt.Point;
import java.awt.Rectangle;

import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.view.draw.SelectionDelegate;
import de.hsrm.mi.swt.grundreisser.view.exceptions.InvalidPositionException;

/**
 * 
 * This class represents one object, that is drawn on the draw pane
 * 
 * @author Simon Seyer
 * 
 */
public interface View<T extends PlacedObject> extends ViewComponent {

	/**
	 * Get the enclosing frame of the object to check for valid position
	 * 
	 * @return the frame
	 */
	public Rectangle getFrame();

	/**
	 * Get the corresponding model for the view
	 * 
	 * @return the model
	 */
	public T getModel();

	/**
	 * Move the upper left point of the object to a different location.
	 * 
	 * @param position
	 *            position the new uppper left point
	 * @throws InvalidPositionException
	 *             is thrown, when the new position is not valid
	 */
	public void setPosition(Point position) throws InvalidPositionException;

	/**
	 * Set's the delegate, that is used to handle view selection.
	 * 
	 * @param selectionDelegate
	 *            the delegate
	 */
	public void setSelectionDelegate(SelectionDelegate selectionDelegate);

	/**
	 * Set's the view in selected mode. The view should respond with a changed
	 * view representation.
	 * 
	 * @param selected
	 *            true, if the view should be selected
	 */
	public void setSelected(boolean selected);

	/**
	 * Updates the whole with values from the model
	 */
	public void updateView();
}
