package de.hsrm.mi.swt.grundreisser.business.floor.groundplan;

/**
 * The class represents a door
 * 
 * @author nmuel002
 *
 */
public class Door implements WinDoor {

	private double pos;
	private int width;

	public Door(double pos, int width) {
		this.pos = pos;
		this.width = width;
	}

	public Door() {
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public double getPosition() {
		return pos;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void setPosition(double p) {
		this.pos = p;
	}

	/**
	 * Method to check if two doors are equal
	 * 
	 * @param anotherDoor
	 *            another door
	 * @return true if two doors are equal, otherwise false
	 */
	public boolean equals(Object anotherDoor) {

		if (this == anotherDoor) {
			return true;
		}
		if (anotherDoor == null) {
			return false;
		}
		if (!(anotherDoor instanceof Door)) {
			return false;
		}

		Door other = (Door) anotherDoor;

		if (this.pos == other.pos && this.width == other.width) {
			return true;
		}

		return false;
	}

	public String toString() {
		return String.format("Door: position %d, width%s cm", pos, width);
	}
}
