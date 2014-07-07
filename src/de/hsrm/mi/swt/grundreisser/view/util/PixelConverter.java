package de.hsrm.mi.swt.grundreisser.view.util;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * 
 * This interface provides the conversion between model coordinates and view
 * coordinates
 * 
 * @author Simon Seyer
 * 
 */
public interface PixelConverter {

	/**
	 * Set the scale factor to transform between view and model coordinates. It
	 * is defined as viewValue * scaleFactor = modelValue.
	 * 
	 * @param scaleFactor
	 *            the factor to scale with
	 */
	public void setScaleFactor(int scaleFactor);

	/**
	 * Get the scale factor that is used to transform between view and model
	 * coordinates. It is defined as viewValue * scaleFactor = modelValue.
	 * 
	 * @return the scale factor
	 */
	public int getScaleFactor();

	/**
	 * Converts a value from the model into a pixel coordinate of the view
	 * 
	 * @param value
	 *            the model value
	 * @return the pixel value
	 */
	public int getPixelForValue(int value);

	/**
	 * Converts a pixel coordinate to a model value
	 * 
	 * @param pixel
	 *            the pixel value
	 * @return the model value
	 */
	public int getValueForPixel(int pixel);

	/**
	 * Converts a point from the model into a point of the view
	 * 
	 * @param value
	 *            the model point
	 * @return the view point
	 */
	public Point getPixelForValue(Point value);

	/**
	 * Converts a view point to a model point
	 * 
	 * @param pixel
	 *            the view point
	 * @return the model point
	 */
	public Point getValueForPixel(Point pixel);

	/**
	 * Converts a model rect to a view rect
	 * 
	 * @param value
	 *            the model rect
	 * @return the view rect
	 */
	public Rectangle getPixelForValue(Rectangle value);

	/**
	 * Converts a view rect to a model rect
	 * 
	 * @param value
	 *            the view rect
	 * @return the model rect
	 */
	public Rectangle getValueForPixel(Rectangle value);

}
