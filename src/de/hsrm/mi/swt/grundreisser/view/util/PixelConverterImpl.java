package de.hsrm.mi.swt.grundreisser.view.util;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * 
 * The default imlementation for a pixel converter
 * 
 * @author Simon Seyer
 * 
 */
public class PixelConverterImpl implements PixelConverter {

	private int scaleFactor;

	/**
	 * Create a pixel converter with the default scale factor
	 */
	public PixelConverterImpl() {
		scaleFactor = 20;
	}

	@Override
	public void setScaleFactor(int scaleFactor) {
		if (scaleFactor >= 1) {
			this.scaleFactor = scaleFactor;
		}
	}

	@Override
	public int getScaleFactor() {
		return scaleFactor;
	}

	@Override
	public int getPixelForValue(int value) {
		return value / scaleFactor;
	}

	@Override
	public int getValueForPixel(int pixel) {
		return pixel * scaleFactor;
	}

	@Override
	public Point getPixelForValue(Point value) {
		Point p = (Point) value.clone();
		p.x = getPixelForValue(p.x);
		p.y = getPixelForValue(p.y);
		return p;
	}

	@Override
	public Point getValueForPixel(Point pixel) {
		Point p = (Point) pixel.clone();
		p.x = getValueForPixel(p.x);
		p.y = getValueForPixel(p.y);
		return p;
	}

	@Override
	public Rectangle getPixelForValue(Rectangle value) {
		Point location = getPixelForValue(value.getLocation());
		int width = getPixelForValue((int) value.getWidth());
		int height = getPixelForValue((int) value.getHeight());
		return new Rectangle(location.x, location.y, width, height);
	}

	@Override
	public Rectangle getValueForPixel(Rectangle value) {
		Point location = getValueForPixel(value.getLocation());
		int width = getValueForPixel((int) value.getWidth());
		int height = getValueForPixel((int) value.getHeight());
		return new Rectangle(location.x, location.y, width, height);
	}
}
