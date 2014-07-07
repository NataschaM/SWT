package de.hsrm.mi.swt.grundreisser.business.validation;

/**
 * For definition of the rules of intersection
 * 
 * @author nmuel002
 * 
 */
public interface LayerRule {

	/**
	 * Checks the incoming layers
	 * 
	 * @param layer1
	 * @param layer2
	 * @return array with layers which has to be marked as collide
	 */
	public int[] check(int layer1, int layer2);

	public String getDescription();

}
