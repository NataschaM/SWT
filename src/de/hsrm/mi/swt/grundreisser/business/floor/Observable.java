package de.hsrm.mi.swt.grundreisser.business.floor;

import de.hsrm.mi.swt.grundreisser.business.Observer;

/**
 * Interface for observable objects
 * 
 * @author nmuel002
 *
 */
public interface Observable {

	/**
	 * Adds an observer to the list of objects listening for changes in this
	 * object
	 * 
	 * @param obs
	 *            observer
	 */
	public void addObserver(Observer obs);

	/**
	 * Removes an observer from the observer list
	 * 
	 * @param obs
	 *            observer
	 */
	public void deleteObserver(Observer obs);

	/**
	 * Method for notifying all observers about the changes
	 */
	public void notifyObservers();

}
