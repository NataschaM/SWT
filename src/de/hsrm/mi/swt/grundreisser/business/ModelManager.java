package de.hsrm.mi.swt.grundreisser.business;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import de.hsrm.mi.swt.grundreisser.business.catalog.Catalog;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;

/**
 * Interface ModelManager describes the structure for managing all floors opened
 * in the system. The class that implements that interface must be singleton and
 * have only one instance of the class
 * 
 * @author nmuel002
 *
 */
public interface ModelManager {

	/**
	 * Getter for the actual floor which have to be showed to the user
	 * 
	 * @return the actual floor
	 */
	public Floor getActFloor();

	/**
	 * Setter for actual floor
	 * 
	 * @param floor
	 *            floor
	 */
	public void setActFloor(Floor floor);

	/**
	 * Getter for the actual file name if the floor were loaded from a file
	 * 
	 * @return actual file name
	 */
	public String getActFileName();

	/**
	 * Setter for actual file name
	 * 
	 * @param fileName
	 *            file name
	 */
	public void setActFileName(String fileName);

	/**
	 * Getter for all floors
	 * 
	 * @return a map of the floors
	 */
	public Map<String, Floor> getFloors();

	/**
	 * Method for loading of a floor from a file
	 * 
	 * @param file
	 *            a file is to be loaded
	 * @return floor
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void loadFile(File file) throws FileNotFoundException, IOException;

	/**
	 * Saves a floor into a file.
	 * 
	 * @param floor
	 *            the floor to save
	 * @param file
	 *            the file to save the data into
	 * @throws IOException
	 *             is thrown, when there the file could not be successfully
	 *             written
	 */
	public void saveFile(Floor floor, File file) throws IOException;

	/**
	 * Persistence method
	 */
	public void close();

	/**
	 * Returns the catalog
	 * 
	 * @return catalog
	 */
	public Catalog getCatalog();

	/**
	 * Add a property change listener to get notified when something changed in
	 * the model manager.
	 * 
	 * @param l
	 *            the property change listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener l);

	/**
	 * Remove a property change listener.
	 * 
	 * @param l
	 *            the property change listener, that was added before
	 */
	public void removePropertyChangeListener(PropertyChangeListener l);

}
