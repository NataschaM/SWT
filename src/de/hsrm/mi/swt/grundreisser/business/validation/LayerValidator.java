package de.hsrm.mi.swt.grundreisser.business.validation;

import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.SinglePlacedObject;
import de.hsrm.mi.swt.grundreisser.business.validation.warnings.LayerValidationWarning;

/**
 * Validator for layers of two placed objects
 * 
 * @author nmuel002
 * 
 */
public class LayerValidator {

	private LayerFactory factory;
	private List<LayerRule> rules;

	public LayerValidator() {
		this.factory = new SimpleLayerFactory();
		rules = factory.getLayerRules();
	}

	/**
	 * Validates collision and warns the object it was collide
	 * 
	 * @param collision
	 *            collision object
	 */
	public void validate(Collision collision) {

		PlacedObject obj1 = collision.getObj1();
		PlacedObject obj2 = collision.getObj2();

		//try {
			int layer1 = ((SinglePlacedObject) (obj1)).getLayer();
			int layer2 = ((SinglePlacedObject) (obj2)).getLayer();

			for (LayerRule rule : rules) {
				int[] result = rule.check(layer1, layer2);
				if (result[0] != 0) {
					obj1.addWarning(new LayerValidationWarning(obj2));
				}
				if (result[1] != 0) {
					obj2.addWarning(new LayerValidationWarning(obj1));
				}
			}
//		} catch (Exception e) {
//			
//		}
		
	}

}
