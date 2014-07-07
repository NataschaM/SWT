package de.hsrm.mi.swt.grundreisser.util;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Util class for a rectangle which is represented by its two point: top left
 * corner and upper right corner
 * 
 * @author nmuel002
 * 
 */
public class BackendRectangle {

	private Rectangle rect;

	/**
	 * Constructor
	 * 
	 * @param p1
	 *            top left corner
	 * @param p2
	 *            bottom right corner
	 * @throws BadRectangleException
	 */
	public BackendRectangle(Point p1, Point p2) {

		Point topLeft = null, bottomRight = null;

		int minX = Math.min(p1.x, p2.x);
		int maxX = Math.max(p1.x, p2.x);
		int minY = Math.min(p1.y, p2.y);
		int maxY = Math.max(p1.y, p2.y);

		topLeft = new Point(minX, minY);
		bottomRight = new Point(maxX, maxY);

		int width = bottomRight.x - topLeft.x;
		int height = bottomRight.y - topLeft.y;

		this.rect = new Rectangle(topLeft.x, topLeft.y, width, height);
	}

	/**
	 * Constructor
	 * 
	 * @param center
	 *            center point
	 * @param width
	 *            width
	 * @param height
	 *            height
	 */
	public BackendRectangle(Point center, int width, int height) {

		int x1 = 0, y1 = 0;
		x1 = center.x - width / 2;
		y1 = center.y - height / 2;

		Point topLeft = new Point(x1, y1);
		this.rect = new Rectangle(topLeft.x, topLeft.y, width, height);
	}

	/**
	 * Constructor
	 * 
	 * @param r
	 *            java rectangle
	 */
	public BackendRectangle(Rectangle r) {
		this.rect = r;
	}

	/**
	 * Getter for the top left point
	 * 
	 * @return top left point
	 */
	public Point getTopLeft() {
		return this.rect.getLocation();
	}

	/**
	 * Getter for the upper right point
	 * 
	 * @return upper right point
	 */
	public Point getBottomRight() {
		return new Point(this.rect.x + this.rect.width, this.rect.y
				+ this.rect.height);
	}

	/**
	 * Getter for the center point
	 * 
	 * @return center point
	 */
	public Point getCenter() {
		int centerX = this.rect.x + this.rect.width / 2;
		int centerY = this.rect.y + this.rect.height / 2;
		return new Point(centerX, centerY);
	}

	/**
	 * Getter for width
	 * 
	 * @return width
	 */
	public int getWidth() {
		return this.rect.width;
	}

	/**
	 * Getter for height
	 * 
	 * @return height
	 */
	public int getHeight() {
		return this.rect.height;
	}

	/**
	 * Returns java rectangle
	 * 
	 * @return rectangle
	 */
	public Rectangle getGeometryRectangle() {
		return this.rect;
	}

	/**
	 * Translates the rectangle
	 * 
	 * @param dx
	 *            difference x-coordinate
	 * @param dy
	 *            difference y-coordinate
	 */
	public void translate(int dx, int dy) {
		this.rect.translate(dx, dy);
	}

	/**
	 * Checks if the rectangle contains a point
	 * 
	 * @param p
	 *            point
	 * @return true - if the point is inside the rectangle, false otherwise
	 */
	public boolean contains(Point p) {
		return this.rect.contains(p);
	}

	/**
	 * Checks if the rectangle is intersecting with one other
	 * 
	 * @param r
	 *            another rectangle
	 * @return true - if two rectangles are intersecting, false otherwise
	 */
	public boolean intersects(BackendRectangle r) {
		return this.rect.intersects(r.rect);
	}

	/**
	 * Returns the intersection of this rectangle with one another.
	 * 
	 * @param r
	 *            another rectangle
	 * @return intersection rectangle
	 */
	public BackendRectangle intersection(BackendRectangle r) {
		Rectangle inersectRect = this.rect.intersection(r.rect);
		return new BackendRectangle(inersectRect);
	}

	/**
	 * Returns a new rectangle that represents the union of the two rectangles.
	 * 
	 * @param r
	 *            another rectangle
	 * @return the smallest Rectangle containing both the specified Rectangle
	 *         and this Rectangle.
	 */
	public BackendRectangle union(BackendRectangle r) {
		Rectangle unionRect = this.rect.union(r.rect);
		return new BackendRectangle(unionRect);
	}

	/**
	 * Method to check if two rectangles are equal
	 * 
	 * @param anotherRect
	 *            another rectangle
	 * @return true if two rectangles are equal, otherwise false
	 */
	public boolean equals(Object anotherRect) {

		if (this == anotherRect) {
			return true;
		}
		if (anotherRect == null) {
			return false;
		}
		if (!(anotherRect instanceof BackendRectangle)) {
			return false;
		}

		BackendRectangle other = (BackendRectangle) anotherRect;
		return this.rect.equals(other.rect);
	}

	public String toString() {
		return String.format(
				"Rectangle [center point: %s, width: %d height: %d mm]",
				this.getCenter(), this.rect.width, this.rect.height);
	}

}
