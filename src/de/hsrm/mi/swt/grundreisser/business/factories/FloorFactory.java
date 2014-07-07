package de.hsrm.mi.swt.grundreisser.business.factories;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hsrm.mi.swt.grundreisser.business.floor.Floor;

/**
 * Floor factory is responsible for creation and persistence of a floor
 * 
 * @author nmuel002
 *
 */
public interface FloorFactory {

	public static final String FILE_EXTENSION = "reisser";

	/**
	 * Creates a new floor
	 * 
	 * @return new floor
	 */
	public Floor createFloor();

	/**
	 * Loads a floor from a given file
	 * 
	 * @param file
	 *            the file, the data should be read from
	 * @return loaded floor
	 */
	public Floor createFloor(File file) throws FileNotFoundException,
			IOException;

	/**
	 * Persists the floor objects
	 * 
	 * @param floor
	 *            floor
	 * @param file
	 *            the file, that should be written in
	 * @throws IOException
	 */
	public void saveFloor(Floor floor, File file) throws IOException;
}
