package de.hsrm.mi.swt.grundreisser.business.validation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.exceptions.ValidationException;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.OuterWall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WallImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.FitmentGroup;
import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;
import de.hsrm.mi.swt.grundreisser.util.Vector;

/**
 * Validator for collision detection for the specified object on the plan
 * 
 * @author nmuel002
 * 
 */
public class CollisionValidator implements ModellValidator {

	private LayerValidator validator;

	public CollisionValidator() {
		this.validator = new LayerValidator();
	}

	@Override
	public void validate(List<PlacedObject> placedObjs, PlacedObject obj)
			throws ValidationException {

		obj.clearWarnings();

		for (PlacedObject po : placedObjs) {
			po.removeWarning(obj);
		}
		
		if(!placedObjs.contains(obj))
			return;

		List<Collision> collisions = findCollisions(placedObjs, obj);

		for (Collision col : collisions) {
			this.validator.validate(col);
		}

	}

	public List<Collision> findCollisions(List<PlacedObject> placedObjs,
			PlacedObject obj) {

		List<Collision> collisions = new ArrayList<Collision>();

		for (PlacedObject po : placedObjs) {
			if (po != obj) {

				if (obj instanceof OuterWall) {
					Point topLeft = obj.getRect().getTopLeft();
					Vector translation = new Vector(topLeft.x, topLeft.y);
					for (WallImpl w : ((OuterWall) obj).getWalls()) {
						WallImpl clWall = new WallImpl(w.getPos1(), w.getPos2());
						clWall.move(translation);
						List<PlacedObject> list = po.intersects(clWall.getRect());
						for (PlacedObject o : list) {
							System.out.println(o);
								Collision col = new Collision(o, w);
								collisions.add(col);
						}
					}
				} else if (obj instanceof FitmentGroup) {
					Map<Fitment, Fitment> fitMap = ((FitmentGroup) obj)
							.getMap();
					for (Fitment fit : fitMap.keySet()) {
						List<PlacedObject> list = po.intersects(fit.getRect());
						for (PlacedObject o : list) {
							Collision col = new Collision(o, fitMap.get(fit));
							collisions.add(col);
						}
					}
				} else {
					List<PlacedObject> list = po.intersects(obj.getRect());
					for (PlacedObject o : list) {
						Collision col = new Collision(o, obj);
						collisions.add(col);
					}
				}
			}
		}

		return collisions;

	}

}
