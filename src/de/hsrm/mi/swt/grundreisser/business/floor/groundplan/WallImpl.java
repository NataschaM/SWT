package de.hsrm.mi.swt.grundreisser.business.floor.groundplan;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hsrm.mi.swt.grundreisser.business.Observer;
import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.SinglePlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.exceptions.ValidationException;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.FitmentGroup;
import de.hsrm.mi.swt.grundreisser.business.validation.ValidationListener;
import de.hsrm.mi.swt.grundreisser.business.validation.warnings.LayerValidationWarning;
import de.hsrm.mi.swt.grundreisser.business.validation.warnings.ValidationWarning;
import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;
import de.hsrm.mi.swt.grundreisser.util.Vector;

/**
 * Implementation of the wall
 * 
 * @author nmuel002
 * 
 */
public class WallImpl implements Wall, SinglePlacedObject {

	private Point pos1;
	private Point pos2;
	private Point center;
	private int thickness = 200;
	private boolean collide = false;
	private transient List<Observer> observers;
	private List<WinDoor> windoors;
	private ValidationListener validator;
	private transient List<ValidationWarning> warnings;

	/**
	 * Constructor
	 * 
	 * @param p1
	 *            point 1
	 * @param p2
	 *            point 2
	 */
	public WallImpl(Point p1, Point p2) {
		checkPoints(p1, p2);
		center = new Point((this.pos1.x + this.pos2.x) / 2,
				(this.pos1.y + this.pos2.y) / 2);
		this.windoors = new ArrayList<WinDoor>();
		this.observers = new ArrayList<Observer>();
		this.warnings = new ArrayList<ValidationWarning>();
	}

	public void load() {
		this.observers = new ArrayList<Observer>();
		this.warnings = new ArrayList<ValidationWarning>();
	}

	/**
	 * Checks if the wall is horizontal or vertical by initialization
	 * 
	 * @param p1
	 *            start point
	 * @param p2
	 *            end point
	 */
	private void checkPoints(Point p1, Point p2) {

		this.pos1 = p1;

		if (p1.x == p2.x || p1.y == p2.y) {
			this.pos2 = p2;
			return;
		}

		Vector horiz = new Vector(p1, new Point(p1.x + 1, p1.y));
		Vector actVector = new Vector(p1, p2);

		double cos = horiz.computeCosinus(actVector);
		cos = Math.abs(cos);

		if (cos >= 0.5 && cos <= 1) {
			this.pos2 = new Point(p2.x, p1.y);
		} else {
			this.pos2 = new Point(p1.x, p2.y);
		}

	}

	/**
	 * Calculates the width of the wall
	 * 
	 * @return width
	 */
	private int calcWidth() {

		if (isHorizontal()) {
			return Math.abs(pos1.x - pos2.x);
		}
		return Math.abs(pos1.y - pos2.y);
	}

	@Override
	public boolean isHorizontal() {
		if (pos1.x == pos2.x)
			return false;
		return true;
	}

	@Override
	public BackendRectangle getRect() {
		if (isHorizontal()) {
			return new BackendRectangle(this.getCenterLocation(),
					this.calcWidth(), thickness);
		}
		return new BackendRectangle(this.getCenterLocation(), thickness,
				this.calcWidth());
	}

	@Override
	public List<PlacedObject> intersects(BackendRectangle rect) {
		List<PlacedObject> intersections = new ArrayList<PlacedObject>();
		if (this.getRect().intersects(rect)) {
			intersections.add(this);
		}
		return intersections;
	}

	@Override
	public void addObserver(Observer obs) {
		this.observers.add(obs);
	}

	@Override
	public void deleteObserver(Observer obs) {
		this.observers.remove(obs);
	}

	@Override
	public void notifyObservers() {
		for (Observer obs : this.observers) {
			obs.update(this, this);
		}
	}

	@Override
	public boolean addWinDoor(WinDoor windoor) {

		try {
			this.windoors.add(windoor);

			if (this.validator != null)
				validate(this);

			this.notifyObservers();
		} catch (ValidationException e) {
			this.windoors.remove(windoor);
			return false;
		}

		return true;
	}

	@Override
	public boolean removeWinDoor(WinDoor windoor) {

		if (windoors.contains(windoor)) {
			windoors.remove(windoor);
			this.notifyObservers();
			return true;
		}
		return false;
	}

	@Override
	public void move(Vector v) {
		// store old values
		Point oldCenter = this.center;
		Point oldPos1 = this.pos1;
		Point oldPos2 = this.pos2;

		try {
			// make translation
			this.pos1 = v.add(pos1);
			this.pos2 = v.add(pos2);

			this.center = v.add(center);
			if (this.validator != null) {
				this.validator.validate(this);
			}
			this.notifyObservers();

		} catch (ValidationException e) {
			// or reset values
			this.pos1 = oldPos1;
			this.pos2 = oldPos2;
			this.center = oldCenter;
		}
	}

	@Override
	public void setCenterLocation(Point p) {

		// store old values
		Point oldCenter = this.center;
		Point oldPos1 = this.pos1;
		Point oldPos2 = this.pos2;

		// calculate translation vector
		int diffX = p.x - center.x;
		int diffY = p.y - center.y;

		Vector translation = new Vector(diffX, diffY);

		try {
			// make translation
			this.pos1 = translation.add(pos1);
			this.pos2 = translation.add(pos2);

			this.center = p;
			if (this.validator != null) {
				this.validator.validate(this);
			}
			this.notifyObservers();

		} catch (ValidationException e) {
			// or reset values
			this.pos1 = oldPos1;
			this.pos2 = oldPos2;
			this.center = oldCenter;
		}
	}

	@Override
	public Point getCenterLocation() {
		return this.center;
	}

	@Override
	public boolean getCollide() {
		return collide;
	}

	@Override
	public void setCollide(boolean collide) {
		this.collide = collide;
	}

	@Override
	public List<Point> getPositions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPositions(List<Point> points) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate(PlacedObject po) throws ValidationException {
		this.validator.validate(po);
	}

	@Override
	public void setValidator(ValidationListener validator) {
		this.validator = validator;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 4;
	}

	/**
	 * Getter for position 1
	 * 
	 * @return 1st position
	 */
	public Point getPos1() {
		return this.pos1;
	}

	/**
	 * Getter for position 2
	 * 
	 * @return 2nd position
	 */
	public Point getPos2() {
		return this.pos2;
	}

	@Override
	public void setThickness(int thickness) {
		this.thickness = thickness;
	}

	@Override
	public List<WinDoor> getWinDoors() {
		return windoors;
	}

	public String toString() {
		return String.format("WallImpl [%s]", this.getRect());
	}

	@Override
	public List<ValidationWarning> getWarnings() {
		return this.warnings;
	}

	@Override
	public void addWarning(ValidationWarning warning) {
		this.warnings.add(warning);
		this.notifyObservers();
	}

	@Override
	public void removeWarning(ValidationWarning warning) {
		if (this.warnings.contains(warning)) {
			this.warnings.remove(warning);
			this.notifyObservers();
		}
	}

	@Override
	public void clearWarnings() {
		if (!this.warnings.isEmpty()) {
			this.warnings.clear();
			this.notifyObservers();
		}
	}

	@Override
	public void removeWarning(PlacedObject po) {
		if (po instanceof FitmentGroup) {

			Map<Fitment, Fitment> fitMap = ((FitmentGroup) po).getMap();
			for (Fitment f : fitMap.values()) {
				for (ValidationWarning warn : new ArrayList<>(warnings)) {
					if (((LayerValidationWarning) warn).getCollide() == f) {
						this.warnings.remove(warn);
					}
				}
			}

		} else {
			for (ValidationWarning warn : new ArrayList<>(warnings)) {
				if (((LayerValidationWarning) warn).getCollide() == po) {
					this.warnings.remove(warn);
				}
			}
		}
		this.notifyObservers();
	}

}
