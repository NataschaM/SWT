package de.hsrm.mi.swt.grundreisser.business.floor.groundplan;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.Observer;
import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.exceptions.ValidationException;
import de.hsrm.mi.swt.grundreisser.business.validation.ValidationListener;
import de.hsrm.mi.swt.grundreisser.business.validation.warnings.ValidationWarning;
import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;
import de.hsrm.mi.swt.grundreisser.util.Vector;

/**
 * Class for instances of outer wall Outer wall represents a group of the walls
 * and have the closed structure, that means the first and the last wall in the
 * group have one joint point (corner)
 * 
 * @author nmuel002
 * 
 */
public class OuterWall implements Wall {

	private LinkedList<WallImpl> walls;
	private transient List<Observer> observers;
	private ValidationListener validator;
	private static final int DEFAULT_THICKNESS = 400;
	private Point center;

	/**
	 * Constructor
	 * 
	 * @param walls
	 *            list of the walls
	 */
	public OuterWall(Point center, List<WallImpl> walls) {

		this.center = center;
		this.walls = new LinkedList<>(walls);
		this.observers = new ArrayList<Observer>();
		this.setThickness(DEFAULT_THICKNESS);
		// this.warnings = new ArrayList<ValidationWarning>();
	}

	public void load() {
		this.observers = new ArrayList<Observer>();
		for (int i = 0; i < this.walls.size(); i++) {
			this.walls.get(i).load();
		}
	}

	/**
	 * Create a outer wall with a list of walls with absolute coordinates. The
	 * center point is calculated automatically. Also the wall parts that are
	 * outside of the rectangle bounding box are cut off automatically.
	 * 
	 * @param walls
	 *            a list of walls, where the first and the last wall intersects
	 */
	public static OuterWall createOuterWall(List<WallImpl> walls) {
		// Thickness of the wall has to be set first so the bounding could be
		// calculated correctly
		for (WallImpl wall : walls) {
			wall.setThickness(DEFAULT_THICKNESS);
		}

		WallImpl first = walls.get(0);
		WallImpl last = walls.get(walls.size() - 1);

		int x, y;

		if (first.isHorizontal()) {
			x = last.getPos2().x;
			y = first.getPos1().y;
		} else {
			x = first.getPos1().x;
			y = last.getPos2().y;
		}

		Point p = new Point(x, y);

		first = new WallImpl(p, first.getPos2());
		first.setThickness(DEFAULT_THICKNESS);
		last = new WallImpl(last.getPos1(), p);
		last.setThickness(DEFAULT_THICKNESS);

		walls.set(0, first);
		walls.set(walls.size() - 1, last);

		// Get the upper left point of all walls
		BackendRectangle unionRect = walls.get(0).getRect();
		for (WallImpl wall : walls) {
			unionRect = unionRect.union(wall.getRect());
		}
		Point relativeP = unionRect.getTopLeft();
		Vector vec = new Vector(-relativeP.x, -relativeP.y);
		// Move all walls to relative positions
		for (WallImpl wall : walls) {
			wall.move(vec);
		}

		return new OuterWall(unionRect.getCenter(), walls);
	}

	@Override
	public BackendRectangle getRect() {

		BackendRectangle rect = walls.get(0).getRect();
		for (WallImpl w : walls) {
			BackendRectangle r = w.getRect();
			rect = rect.union(r);
		}

		int width = rect.getWidth();
		int height = rect.getHeight();

		return new BackendRectangle(center, width, height);
	}

	@Override
	public List<PlacedObject> intersects(BackendRectangle rect) {
		List<PlacedObject> intersections = new ArrayList<PlacedObject>();
		Point topLeft = this.getRect().getTopLeft();
		Vector translate = new Vector(-topLeft.x, -topLeft.y);

		for (WallImpl wall : this.walls) {

			Point newCenter = translate.add(rect.getCenter());
			BackendRectangle clRect = new BackendRectangle(newCenter,
					rect.getWidth(), rect.getHeight());
			List<PlacedObject> list = wall.intersects(clRect);
			if (!list.isEmpty()) {
				for (PlacedObject obj : list) {
					intersections.add(obj);
				}
			}
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
	public void validate(PlacedObject po) throws ValidationException {
		this.validator.validate(po);

	}

	@Override
	public void setValidator(ValidationListener validator) {
		this.validator = validator;
		for (WallImpl wall : walls) {
			wall.setValidator(validator);
		}
	}

	@Override
	public boolean addWinDoor(WinDoor windoor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeWinDoor(WinDoor windoor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCenterLocation(Point p) {

		Point oldCenter = center;

		try {
			this.center = p;

			if (this.validator != null) {
				this.validator.validate(this);
			}

			this.notifyObservers();

		} catch (ValidationException e) {
			// or reset the wall list
			this.center = oldCenter;
		}
	}

	@Override
	public void move(Vector v) {

		Point oldCenter = center;

		try {
			this.center = v.add(center);

			if (this.validator != null) {
				this.validator.validate(this);
			}
			this.notifyObservers();

		} catch (ValidationException e) {
			this.center = oldCenter;
		}
	}

	@Override
	public Point getCenterLocation() {
		return center;
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
	public void setThickness(int thickness) {
		for (WallImpl wall : walls) {
			wall.setThickness(thickness);
		}
	}

	@Override
	public List<WinDoor> getWinDoors() {
		List<WinDoor> windoors = new ArrayList<WinDoor>();
		for (int i = 0; i < walls.size(); i++) {
			windoors.addAll(walls.get(i).getWinDoors());
		}
		return windoors;
	}

	/**
	 * Returns the wall list
	 * 
	 * @return the list with the walls
	 */
	public List<WallImpl> getWalls() {
		return this.walls;
	}

	public String toString() {
		return String.format("OuterWall [%s]", this.getRect());
	}

	@Override
	public boolean isHorizontal() {
		return false;
	}

	@Override
	public List<ValidationWarning> getWarnings() {

		List<ValidationWarning> warns = new ArrayList<ValidationWarning>();

		for (Wall w : walls) {
			warns.addAll(w.getWarnings());
		}
		return warns;
	}

	@Override
	public void addWarning(ValidationWarning warning) {

		for (Wall w : walls) {
			w.addWarning(warning);
		}
		this.notifyObservers();
	}

	@Override
	public void removeWarning(ValidationWarning warning) {

		for (Wall w : walls) {
			w.removeWarning(warning);
		}
		this.notifyObservers();

	}

	@Override
	public void clearWarnings() {
		for (Wall w : walls) {
			w.clearWarnings();
		}
		this.notifyObservers();
	}

	@Override
	public void removeWarning(PlacedObject po) {
		for (Wall w : walls) {
			w.removeWarning(po);
		}
		this.notifyObservers();
	}
}
