package de.hsrm.mi.swt.grundreisser.business.validation;

import java.util.List;

/**
 * Factory, which creates layer objects (if necessary) and layer rules
 * 
 * @author nmuel002
 *
 */
public interface LayerFactory {

	/**
	 * Returns a list with the rules
	 * 
	 * @return list with the rules
	 */
	public List<LayerRule> getLayerRules();

}
