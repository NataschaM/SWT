package de.hsrm.mi.swt.grundreisser.business.floor.groundplan;

/**
 * The class for window representation
 * 
 * @author nmuel002
 *
 */
public class Window implements WinDoor {

	private double pos;
	private int width;

	public Window(double pos, int width) {
		this.pos = pos;
		this.width = width;
	}

	public Window() {
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
	 * Method to check if two windows are equal
	 * 
	 * @param anotherWindow
	 *            another window
	 * @return true if two windows are equal, otherwise false
	 */
	public boolean equals(Object anotherWindow) {

		if (this == anotherWindow) {
			return true;
		}
		if (anotherWindow == null) {
			return false;
		}
		if (!(anotherWindow instanceof Window)) {
			return false;
		}

		Window other = (Window) anotherWindow;

		if (this.pos == other.pos && this.width == other.width) {
			return true;
		}

		return false;
	}

	public String toString() {
		return String.format("Window: position %d, width%s cm", pos, width);
	}
}
