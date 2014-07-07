package de.hsrm.mi.swt.grundreisser.view.draw;

import java.awt.geom.Rectangle2D;

import de.hsrm.mi.swt.grundreisser.view.exceptions.InvalidPositionException;
import de.hsrm.mi.swt.grundreisser.view.util.PixelConverter;

/**
 * 
 * This interface allows to interact with main draw view to do updates,
 * validation and conversion
 * 
 * @author Simon Seyer
 * 
 */
public interface MainView {

	/**
	 * Checks for a valid position of a rectangle
	 * 
	 * @param rect
	 *            the rect to validate
	 * @throws InvalidPositionException
	 *             is thrown, when the position is not valid
	 */
	public void validate(Rectangle2D rect) throws InvalidPositionException;

	/**
	 * Get a converter to convert model and view coordinates
	 * 
	 * @return a pixel converter
	 */
	public PixelConverter getPixelConverter();

	/**
	 * Get the delegate that is used to handle object selection
	 * 
	 * @return the selection delegate
	 */
	public SelectionDelegate getSelectionDelegate();
}
