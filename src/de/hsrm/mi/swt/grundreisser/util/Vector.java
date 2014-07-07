package de.hsrm.mi.swt.grundreisser.util;

import java.awt.Point;

/**
 * Util class for definition of a two-dimentional vector
 * 
 * @author nmuel002
 * 
 */
public class Vector {

	private int x;
	private int y;

	/**
	 * Initializing constructor if the vector is to be defined by two points:
	 * start and end
	 * 
	 * @param start
	 *            start point
	 * @param end
	 *            end point
	 */
	public Vector(Point start, Point end) {
		this.x = end.x - start.x;
		this.y = end.y - start.y;
	}

	/**
	 * Constructor by two coordinates x and y as parameter
	 * 
	 * @param x
	 * @param y
	 */
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x-coordinate
	 * 
	 * @return x-coordinate
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Returns the y-coordinate
	 * 
	 * @return y-coordinate
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Addition to a second vector
	 * 
	 * @param v
	 *            another vector
	 * @return the sum of two vectors as vector
	 */
	public Vector add(Vector v) {
		return new Vector(this.x + v.getX(), this.y + v.getY());
	}

	/**
	 * Addition to a point This operation represents a point translation on this
	 * vector
	 * 
	 * @param p
	 *            the point to be translated
	 * @return a point as a result of the translation
	 */
	public Point add(Point p) {
		return new Point(p.x + this.x, p.y + this.y);
	}

	/**
	 * Subtraction of another vector from this vector
	 * 
	 * @param v
	 *            another vector
	 * @return a vector as the result of two vectors subtraction
	 */
	public Vector sub(Vector v) {
		return new Vector(this.x - v.getX(), this.y - v.getY());
	}

	/**
	 * Scaling
	 * 
	 * @param scalar
	 *            scale factor
	 * @return a scaled vector
	 */
	public Vector scale(int scalar) {
		return new Vector(this.x * scalar, this.y * scalar);
	}

	/**
	 * Negation: finding of a vector, which has inverse direction to this one
	 * 
	 * @return a negated vector
	 */
	public Vector negate() {
		return this.scale(-1);
	}

	/**
	 * Returns the length of the vector
	 * 
	 * @return length
	 */
	public double getLength() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Method to check if two vectors are equal
	 * 
	 * @param anotherVector
	 *            another vector
	 * @return true if two vectors are equal, otherwise false
	 */
	public boolean equals(Object anotherVector) {

		if (this == anotherVector) {
			return true;
		}
		if (anotherVector == null) {
			return false;
		}
		if (!(anotherVector instanceof Vector)) {
			return false;
		}

		Vector other = (Vector) anotherVector;

		if (this.x == other.x && this.y == other.y) {
			return true;
		}

		return false;
	}

	/**
	 * Returns the scale product of two vectors
	 * 
	 * @param vect
	 *            another vector
	 * @return scale product
	 */
	public int scale_product(Vector vect) {
		return this.x * vect.x + this.y * vect.y;
	}

	/**
	 * Computes cosinus of two vectors angle
	 * 
	 * @param vect
	 *            another vector
	 * @return cosinus of the angle
	 */
	public double computeCosinus(Vector vect) {
		return ((double) this.scale_product(vect))
				/ (this.getLength() * vect.getLength());
	}

	/**
	 * Returns a vector as a result of rotation of this vector to the left
	 * 
	 * @return the rotated vector to the left
	 */
	public Vector rotateLeft() {
		return new Vector(y, -x);
	}

	/**
	 * Returns a vector as a result of rotation of this vector to the left
	 * 
	 * @return the rotated vector to the left
	 */
	public Vector rotateRight() {
		return new Vector(-y, x);
	}

	public String toString() {
		return String.format("Vector [x: %d, y: %d]", x, y);
	}

}
