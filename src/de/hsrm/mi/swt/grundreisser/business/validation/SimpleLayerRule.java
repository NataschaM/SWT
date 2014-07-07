package de.hsrm.mi.swt.grundreisser.business.validation;

/**
 * Simple layer rule for our system
 * 
 * @author nmuel002
 *
 */
public class SimpleLayerRule implements LayerRule {

	@Override
	public int[] check(int layer1, int layer2) {

		if (layer1 == 4 && layer2 == 4) {
			return new int[] { layer1, layer2 };
		}
		if (layer1 == 4) {
			return new int[] { 0, layer2 };
		}
		if (layer2 == 4) {
			return new int[] { layer1, 0 };
		}
		if (layer1 == layer2) {
			return new int[] { layer1, layer2 };
		}
		if (layer1 > layer2) {
			return new int[] { 0, layer2 };
		}
		return new int[] { 0, 0 };
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
