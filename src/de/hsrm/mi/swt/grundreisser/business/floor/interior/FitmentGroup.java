package de.hsrm.mi.swt.grundreisser.business.floor.interior;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hsrm.mi.swt.grundreisser.business.Observer;
import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture.FurnitureType;
import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.exceptions.ValidationException;
import de.hsrm.mi.swt.grundreisser.business.validation.Collision;
import de.hsrm.mi.swt.grundreisser.business.validation.ValidationListener;
import de.hsrm.mi.swt.grundreisser.business.validation.warnings.ValidationWarning;
import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;
import de.hsrm.mi.swt.grundreisser.util.Vector;

/**
 * The group of fitments One operation on the group (rotate, move etc.) must be
 * applied on all fitments in the group. In case of rotation it must be executed
 * relatively to the central point of the whole group
 * 
 * @author jheba001
 * 
 */

public class FitmentGroup implements Fitment {

	private List<Fitment> fitments;
	private int furnitureId;
	private Point center;
	private ValidationListener validator;
	private transient List<Observer> observers;

	/**
	 * Dummy constructor for testing
	 */
	public FitmentGroup() {
		this.center = new Point(0, 0);
		this.fitments = new ArrayList<Fitment>();
		this.observers = new ArrayList<Observer>();
	}

	public void load() {
		this.observers = new ArrayList<Observer>();
		for (int i = 0; i < this.fitments.size(); i++) {
			this.fitments.get(i).load();
		}
	}

	/**
	 * Constructor
	 * 
	 * @param fitments
	 *            a list of fitments to group
	 */
	public FitmentGroup(Point center, List<Fitment> fitments) {
		this.center = center;
		this.fitments = fitments;
		this.observers = new ArrayList<Observer>();
	}

	/**
	 * Creates a new fitment group from a global list of fitments. The method
	 * computes a center point of the group and transfers the global coordinates
	 * of the specified fitments into local coordinates with a left top corner
	 * on the point [0,0].
	 * 
	 * @param fits
	 *            the specified fitment list
	 * @return new fitment group
	 */
	public static FitmentGroup createFitmentGroup(List<Fitment> fits) {

		BackendRectangle unionRect = getLocaleRect(fits);
		Point center = unionRect.getCenter();

		List<Fitment> locFits = moveFitmentsToPointOfOrigin(fits);

		return new FitmentGroup(center, locFits);
	}

	/**
	 * The method transfers all fitments in the list to local coordinates so
	 * that the left top corner is on the point of origin in global coord.system
	 * 
	 * @param fits
	 *            list of fitments in global coordinates
	 * @return fitment list with local coordinates
	 */
	private static List<Fitment> moveFitmentsToPointOfOrigin(List<Fitment> fits) {

		List<Fitment> locFits = new ArrayList<Fitment>();

		BackendRectangle unionRect = getLocaleRect(fits);
		Point globalP = unionRect.getTopLeft();
		Vector translation = new Vector(-globalP.x, -globalP.y);

		for (Fitment f : fits) {
			f.move(translation);
			locFits.add(f);
		}

		return locFits;
	}

	/**
	 * Gets recursive a rectangle in global coordinates for the specified
	 * fitment within the group
	 * 
	 * @param fit
	 *            fitment which the global rectangle has to be computed for
	 * @param group
	 *            group that has to be evaluated
	 * @return backend rectangle in the global coordinates of the whole group
	 */
	public BackendRectangle getGlobalRectangle(Fitment fit, Fitment group) {

		List<Fitment> fitList = ((FitmentGroup) group).getFitments();

		// get the left top point of the global rectangle
		Point glTopLeft = group.getRect().getTopLeft();
		// compute translation vector
		Vector translation = new Vector(new Point(0, 0), glTopLeft);

		if (fit == group) {
			return group.getRect();
		}

		if (fitList.contains(fit)) {
			// clone fitment
			Fitment cloneFit = (Fitment) fit.clone();
			// and move it by translation vector
			cloneFit.move(translation);
			return cloneFit.getRect();
		} else {
			for (Fitment f : fitList) {
				if (f.isGroup()) {
					// clone fitment from group list
					Fitment cloneFit = (Fitment) f.clone();
					// and move it by translation vector
					cloneFit.move(translation);
					return getGlobalRectangle(fit, cloneFit);
				}
			}
		}
		return null;
	}

	/**
	 * Returns a map with fitments in global and local coordinates key is local
	 * object, value - object in global coordinates
	 * 
	 * @return a map with fitments in global and local coordinates
	 */
	public Map<Fitment, Fitment> getMap() {

		Map<Fitment, Fitment> global = new HashMap<Fitment, Fitment>();
		global = this.getMapRecursiv(global, this);

		return global;
	}

	private Map<Fitment, Fitment> getMapRecursiv(Map<Fitment, Fitment> global,
			Fitment fit) {

		List<Fitment> local = ((FitmentGroup) fit).getFitments();

		// get the left top point of the global rectangle
		Point glTopLeft = fit.getRect().getTopLeft();
		// compute translation vector
		Vector translation = new Vector(new Point(0, 0), glTopLeft);

		for (Fitment f : local) {
			// clone fitment from group list
			Fitment cloneFit = (Fitment) f.clone();
			// and move it by translation vector
			cloneFit.move(translation);

			if (!f.isGroup()) {
				// add simple fitment to the list
				global.put(cloneFit, f);
			} else {
				getMapRecursiv(global, cloneFit);
			}
		}
		return global;
	}

	/**
	 * Returns a list of fitments of the group which positions are in the global
	 * coordinates
	 * 
	 * @return the list of fitments with global coordinates
	 */
	public List<Fitment> getGlobalList() {

		List<Fitment> global = new ArrayList<Fitment>();
		global = this.getGlobalListRecursiv(global, this);

		return global;
	}

	/**
	 * Recursive method that returns a list of all simple objects within a
	 * fitment group
	 * 
	 * @param global
	 *            a list to which the objects with their global coordinates will
	 *            be added
	 * @param fit
	 *            the fitment group that is evaluated
	 * @return the list of simple objects in global coordinates
	 */
	public List<Fitment> getGlobalListRecursiv(List<Fitment> global, Fitment fit) {

		List<Fitment> local = ((FitmentGroup) fit).getFitments();

		// get the left top point of the global rectangle
		Point glTopLeft = fit.getRect().getTopLeft();
		// compute translation vector
		Vector translation = new Vector(new Point(0, 0), glTopLeft);

		for (Fitment f : local) {
			// clone fitment from group list
			Fitment cloneFit = (Fitment) f.clone();
			// and move it by translation vector
			cloneFit.move(translation);

			if (!f.isGroup()) {
				// add simple fitment to the list
				global.add(cloneFit);
			} else {
				getGlobalListRecursiv(global, cloneFit);
			}
		}
		return global;
	}

	/**
	 * Returns the locale rectangle where all objects have locale coordinates.
	 * Normally its top left corner must be always at the point [0, 0].
	 * 
	 * This method is interesting for intern usage, but was made public for
	 * testing.
	 * 
	 * @param fits
	 *            fitment list in the local coordinates
	 * @return locale rectangle
	 */
	public static BackendRectangle getLocaleRect(List<Fitment> fits) {
		BackendRectangle rec = fits.get(0).getRect();
		for (Fitment f : fits) {
			rec = rec.union(f.getRect());
		}
		return rec;
	}

	/**
	 * Recursive method that rotates all objects in the group to the left on the
	 * specified point
	 * 
	 * @param fits
	 *            the list of fitments in the group
	 * @param centerPoint
	 *            the point of rotation
	 */
	private void rotateLeft(List<Fitment> fits, Point centerPoint) {

		for (Fitment f : fits) {

			Vector vect = new Vector(centerPoint, f.getCenterLocation());

			Vector rotated = vect.rotateLeft();
			Point newCenter = rotated.add(centerPoint);

			if (!f.isGroup()) {
				f.move(newCenter);
				f.rotateLeft();
			} else {
				f.move(newCenter);
				BackendRectangle rec = getLocaleRect(((FitmentGroup) f)
						.getFitments());
				Point localCenter = rec.getCenter();

				rotateLeft(((FitmentGroup) f).getFitments(), localCenter);
				moveFitmentsToPointOfOrigin(((FitmentGroup) f).getFitments());
			}
		}
	}

	@Override
	public void rotateLeft() {

		try {
			BackendRectangle rec = getLocaleRect(this.getFitments());
			Point localCenter = rec.getCenter();
			rotateLeft(this.getFitments(), localCenter);
			moveFitmentsToPointOfOrigin(this.getFitments());

			this.validate(this);
			this.notifyObservers();

		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Recursive method that rotates all objects in the group to the right on
	 * specified point
	 * 
	 * @param fits
	 *            the list of fitments in the group
	 * @param centerPoint
	 *            the point of rotation
	 */
	private void rotateRight(List<Fitment> fits, Point centerPoint) {

		for (Fitment f : fits) {

			Vector vect = new Vector(centerPoint, f.getCenterLocation());

			Vector rotated = vect.rotateRight();
			Point newCenter = rotated.add(centerPoint);

			if (!f.isGroup()) {
				f.move(newCenter);
				f.rotateRight();
			} else {
				f.move(newCenter);
				BackendRectangle rec = getLocaleRect(((FitmentGroup) f)
						.getFitments());
				Point localCenter = rec.getCenter();

				rotateRight(((FitmentGroup) f).getFitments(), localCenter);
				moveFitmentsToPointOfOrigin(((FitmentGroup) f).getFitments());
			}
		}
	}

	@Override
	public void rotateRight() {
		try {
			BackendRectangle rec = getLocaleRect(this.getFitments());
			Point localCenter = rec.getCenter();
			rotateRight(this.getFitments(), localCenter);
			moveFitmentsToPointOfOrigin(this.getFitments());

			this.validate(this);
			this.notifyObservers();

		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getFurnitureId() {
		return this.furnitureId;
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
		for (Observer o : this.observers) {
			o.update(this, this);
		}
	}

	@Override
	public BackendRectangle getRect() {

		BackendRectangle rect = getLocaleRect(this.fitments);
		return new BackendRectangle(center, rect.getWidth(), rect.getHeight());
	}

	@Override
	public List<PlacedObject> intersects(BackendRectangle rect) {

		List<PlacedObject> intersections = new ArrayList<PlacedObject>();

		Map<Fitment, Fitment> map = this.getMap();

		for (Fitment fit : map.keySet()) {
			if (fit.getRect().intersects(rect)) {
				intersections.add(map.get(fit));
			}
		}

		// recursiveIntersects(rect, this, intersections);
		return intersections;
	}

	/**
	 * Runs throw the structure and adds to the list all objects in the group
	 * which intersect the specified rectangle
	 * 
	 * @param rect
	 *            the specified rectangle
	 * @param fit
	 *            the fitment which the evaluating is executed for
	 * @param intersections
	 *            the list for all intersecting objects
	 */
	private void recursiveIntersects(BackendRectangle rect, Fitment fit,
			List<PlacedObject> intersections) {

		BackendRectangle fitRect = null;

		if (fit == this)
			fitRect = this.getRect();
		else
			fitRect = getGlobalRectangle(fit, this);
		boolean intersects = fitRect.intersects(rect);

		if (intersects) {
			if (!fit.isGroup()) {
				intersections.add(fit);
			} else {
				for (Fitment f : ((FitmentGroup) fit).getFitments()) {
					recursiveIntersects(rect, f, intersections);
				}
			}
		}
	}

	@Override
	public void move(Point newPoint) {
		try {
			this.center = newPoint;
			this.validate(this);
			this.notifyObservers();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Point getCenterLocation() {
		return this.center;
	}

	@Override
	public void validate(PlacedObject po) throws ValidationException {
		if (this.validator != null) {
			this.validator.validate(po);
		}
	}

	@Override
	public void setValidator(ValidationListener validator) {
		this.validator = validator;
	}

	@Override
	public void move(Vector translation) {
		try {
			this.center = translation.add(center);
			this.validate(this);
			this.notifyObservers();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns fitment list
	 * 
	 * @return fitment list
	 */
	public List<Fitment> getFitments() {
		return this.fitments;
	}

	/**
	 * Setter for furniture id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.furnitureId = id;
	}

	public String toString() {
		return String.format("FitmentGroup [furniture id: %s, Rectangle: %s]",
				this.furnitureId, this.getRect());
	}

	public Object clone() {
		return new FitmentGroup(this.center, this.fitments);
	}

	@Override
	public boolean isGroup() {
		return true;
	}

	@Override
	public FurnitureType getType() {
		return FurnitureType.GROUP;
	}

	@Override
	public List<ValidationWarning> getWarnings() {
		List<ValidationWarning> warns = new ArrayList<ValidationWarning>();

		for (Fitment fit : this.getFitments()) {
			warns.addAll(fit.getWarnings());
		}
		return warns;
	}

	@Override
	public void addWarning(ValidationWarning warning) {
		for (Fitment fit : this.getFitments()) {
			fit.addWarning(warning);
		}
		this.notifyObservers();
	}

	@Override
	public void removeWarning(ValidationWarning warning) {
		for (Fitment fit : this.getFitments()) {
			fit.removeWarning(warning);
		}
		this.notifyObservers();
	}

	@Override
	public void clearWarnings() {
		Map<Fitment, Fitment> fitMap = this.getMap();
		for (Fitment fit : fitMap.keySet()) {
			fitMap.get(fit).clearWarnings();
		}
		this.notifyObservers();
	}

	@Override
	public void removeWarning(PlacedObject po) {
		Map<Fitment, Fitment> fitMap = this.getMap();
		for (Fitment fit : fitMap.keySet()) {
			fitMap.get(fit).removeWarning(po);
		}
		this.notifyObservers();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
