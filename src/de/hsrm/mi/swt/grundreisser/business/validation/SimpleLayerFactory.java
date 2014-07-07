package de.hsrm.mi.swt.grundreisser.business.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple factory for the rules
 * 
 * @author nmuel002
 *
 */
public class SimpleLayerFactory implements LayerFactory {

	@Override
	public List<LayerRule> getLayerRules() {
		List<LayerRule> rules = new ArrayList<LayerRule>();
		rules.add(new SimpleLayerRule());
		return rules;
	}

}
