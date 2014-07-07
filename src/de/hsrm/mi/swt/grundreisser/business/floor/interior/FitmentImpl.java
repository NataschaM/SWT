package de.hsrm.mi.swt.grundreisser.business.floor.interior;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hsrm.mi.swt.grundreisser.business.Observer;
import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.SinglePlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.exceptions.ValidationException;
import de.hsrm.mi.swt.grundreisser.business.validation.ValidationListener;
import de.hsrm.mi.swt.grundreisser.business.validation.warnings.LayerValidationWarning;
import de.hsrm.mi.swt.grundreisser.business.validation.warnings.ValidationWarning;
import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;
import de.hsrm.mi.swt.grundreisser.util.Vector;

/**
 * The class for fitment implements SinglePlacedObject. This kind of fitment is
 * represented by the simple rectangle form.
 * 
 * This class ist abstract, because we must distinguish between catalog and
 * customer fitment
 * 
 * @author jheba001
 * 
 */

public abstract class FitmentImpl implements Fitment, SinglePlacedObject {

	protected int width;
	protected int height;
	protected Point center;
	protected boolean collide;
	protected int furnitureId;
	protected int layer;
	protected ValidationListener validator;
	protected transient List<Observer> observers;
	protected transient List<ValidationWarning> warnings;

	/**
	 * Default constructor
	 */
	public FitmentImpl() {
		this.center = new Point(0, 0);
		this.width = 10;
		this.height = 10;
		this.observers = new ArrayList<Observer>();
		this.warnings = new ArrayList<ValidationWarning>();
	}

	public void load() {
		this.observers = new ArrayList<Observer>();
		this.warnings = new ArrayList<ValidationWarning>();
	}

	/**
	 * Constructor
	 * 
	 * @param center
	 *            center position
	 * @param width
	 *            width
	 * @param height
	 *            height
	 * @param furnitureId
	 *            id in catalog
	 */
	public FitmentImpl(Point center, int width, int height) {
		this.center = center;
		this.width = width;
		this.height = height;
		this.observers = new ArrayList<Observer>();
		this.warnings = new ArrayList<ValidationWarning>();
	}

	@Override
	public void rotateLeft() {

		int tmpWidth = width, tmpHeight = height;
		try {
			width = tmpHeight;
			height = tmpWidth;
			this.validate(this);
			this.notifyObservers();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void rotateRight() {
		this.rotateLeft();
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
		return new BackendRectangle(center, width, height);
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
		return center;
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

	@Override
	public int getLayer() {
		return layer;
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
	public boolean isGroup() {
		return false;
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

	public String toString() {
		return String
				.format("FitmentImpl [furniture id: %s, layer: %d, center: %s, width: %d, height: %d]",
						this.furnitureId, this.layer, this.center, this.width,
						this.height);
	}

	public abstract Object clone();
}
