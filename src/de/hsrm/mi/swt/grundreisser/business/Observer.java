package de.hsrm.mi.swt.grundreisser.business;

import de.hsrm.mi.swt.grundreisser.business.floor.Observable;

/**
 * Interface Observer is similar to java-Observer but is makes possible to
 * communicate with objects which have implemented the Observable interface. So
 * it will be avoid to work with Observable class in favor of the interface
 * 
 * @author nmuel002
 *
 */
public interface Observer {

	/**
	 * The method for updating the informations of an observable object
	 * 
	 * @param obs
	 *            observable object
	 * @param obj
	 *            the object where the changes were happened
	 */
	public void update(Observable obs, Object obj);

}
