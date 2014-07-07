package de.hsrm.mi.swt.grundreisser.business.validation.warnings;

import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;

/**
 * The warning if one object has layer collision to another one.
 * 
 * @author nmuel002
 *
 */
public class LayerValidationWarning implements ValidationWarning {

	private PlacedObject colObj;

	public LayerValidationWarning(PlacedObject colObj) {
		this.colObj = colObj;
	}

	public PlacedObject getCollide() {
		return this.colObj;
	}

	@Override
	public String getMessage() {
		return "Layer validation warning";
	}

}
