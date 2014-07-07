package de.hsrm.mi.swt.grundreisser.business.floor.groundplan;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.Observer;
import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.exceptions.ValidationException;
import de.hsrm.mi.swt.grundreisser.business.validation.ValidationListener;

public class GroundPlanImpl implements GroundPlan {

	private List<Wall> walls;
	private transient List<Observer> observers;
	private ValidationListener validator;

	public GroundPlanImpl() {
		this.walls = new ArrayList<Wall>();
		this.observers = new ArrayList<Observer>();
	}

	public void load() {
		this.observers = new ArrayList<>();
		for (int i = 0; i < this.walls.size(); i++) {
			this.walls.get(i).load();
		}
	}

	@Override
	public void validate(PlacedObject po) throws ValidationException {
		this.validator.validate(po);
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
			obs.update(this, walls);
		}
	}

	@Override
	public List<Wall> getWalls() {
		return walls;
	}

	@Override
	public boolean addWall(Wall wall) {
		
		try {
			wall.setValidator(this);
			walls.add(wall);
			this.validate(wall);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.notifyObservers();
		return true;
	}

	@Override
	public boolean addWall(List<Point> wallPoints) {
		// TODO validation
		if (wallPoints.size() == 2) {
			Wall w = new WallImpl(wallPoints.get(0), wallPoints.get(1));
			this.addWall(w);
			this.notifyObservers();
			return true;
		}
		if (wallPoints.size() > 2) {
			// TODO outer wall implementation
			this.notifyObservers();
			return true;
		}
		return false;
	}

	@Override
	public boolean removeWall(Wall wall) {
		boolean res = false;
		try {
			res = this.walls.remove(wall);
			if (res == false) {
				return res;
			}
			this.validate(wall);
			this.notifyObservers();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public boolean addWinDoor(WinDoor wd, Wall wall) {
		for (Wall w : walls) {
			if (w instanceof OuterWall) {
				for (Wall inWall : ((OuterWall) w).getWalls()) {
					if (inWall == wall) {
						return inWall.addWinDoor(wd);
					}
				}
			}
			if (wall == w) {
				return w.addWinDoor(wd);
			}
		}
		return false;
	}

	@Override
	public boolean removeWinDoor(WinDoor wd, Wall wall) {
		for (Wall w : walls) {
			if (w instanceof OuterWall) {
				for (Wall inWall : ((OuterWall) w).getWalls()) {
					if (inWall == wall) {
						return inWall.removeWinDoor(wd);
					}
				}
			}
			if (wall == w) {
				return w.removeWinDoor(wd);
			}
		}
		return false;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValidator(ValidationListener validator) {
		this.validator = validator;
	}

}
