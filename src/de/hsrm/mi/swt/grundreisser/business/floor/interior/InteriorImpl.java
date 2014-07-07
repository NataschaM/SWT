package de.hsrm.mi.swt.grundreisser.business.floor.interior;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.ModelManagerImpl;
import de.hsrm.mi.swt.grundreisser.business.ModelManager;
import de.hsrm.mi.swt.grundreisser.business.Observer;
import de.hsrm.mi.swt.grundreisser.business.catalog.Catalog;
import de.hsrm.mi.swt.grundreisser.business.catalog.Category;
import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture;
import de.hsrm.mi.swt.grundreisser.business.factories.FitmentFactory;
import de.hsrm.mi.swt.grundreisser.business.factories.FitmentFactoryImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.exceptions.ValidationException;
import de.hsrm.mi.swt.grundreisser.business.validation.ValidationListener;
import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;
import de.hsrm.mi.swt.grundreisser.util.Vector;

/**
 * This is the implementation of the interior. It implements the Interior
 * interface.
 * 
 * @author jheba001
 * 
 */

public class InteriorImpl implements Interior {

	private List<Fitment> interiorFitments;
	private transient ValidationListener validator;
	private transient Catalog catalog;
	private transient FitmentFactory fitmentFactory;
	private transient List<Observer> observers;

	public InteriorImpl() {
		this.interiorFitments = new ArrayList<Fitment>();
		this.observers = new ArrayList<Observer>();
		this.fitmentFactory = new FitmentFactoryImpl();
		// this.catalog = ModelManagerImpl.getInstance().getCatalog();
	}

	public void load() {
		this.observers = new ArrayList<Observer>();
		for (int i = 0; i < this.interiorFitments.size(); i++) {
			this.interiorFitments.get(i).load();
		}
		catalog = ModelManagerImpl.getInstance().getCatalog();
		this.fitmentFactory = new FitmentFactoryImpl();
	}

	@Override
	public Fitment groupFitments(List<Fitment> fitments) {

		List<Fitment> fitsToGroup = new ArrayList<Fitment>();

		for (Fitment f : fitments) {
			if (this.interiorFitments.contains(f)) {
				fitsToGroup.add(f);
				this.removeFitment(f);
			}
		}

		Fitment group = FitmentGroup.createFitmentGroup(fitsToGroup);
		this.addFitment(group);
		return group;
	}

	@Override
	public void ungroupFitments(Fitment fitment) {

		if (this.interiorFitments.contains(fitment)) {

			BackendRectangle rect = fitment.getRect();
			Point top = rect.getTopLeft();
			Vector translation = new Vector(top.x, top.y);
			List<Fitment> fitments = ((FitmentGroup) fitment).getFitments();
			this.removeFitment(fitment);
			for (Fitment f : fitments) {
				f.move(translation);
				this.addFitment(f);
			}
		}
	}

	@Override
	public void addFitment(Fitment fitment) {

		try {
			fitment.setValidator(this);
			this.interiorFitments.add(fitment);
			this.validate(fitment);
			this.notifyObservers();
		} catch (ValidationException e) {
			this.interiorFitments.remove(fitment);
		}
	}

	@Override
	public void removeFitment(Fitment fitment) {
		if (this.interiorFitments.contains(fitment)) {
			
			try {
				this.interiorFitments.remove(fitment);
				this.validate(fitment);
				this.notifyObservers();
			} catch (ValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addToCatalog(Fitment fitment, String name, Category cat)
			throws Exception {
		Furniture furn = this.fitmentFactory.createFurnitureFromFitment(
				fitment, name, cat);
		this.catalog.addArticle(furn, cat);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

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
			obs.update(this, this.interiorFitments);
		}
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
	public List<Fitment> getFitments() {
		return this.interiorFitments;
	}

	@Override
	public Fitment addFurnitureToInterior(Furniture furn, Point pos) {
		Fitment newFitment = this.fitmentFactory.createFitmentFromFurniture(
				furn, pos);

		this.addFitment(newFitment);

		return newFitment;
	}
}
