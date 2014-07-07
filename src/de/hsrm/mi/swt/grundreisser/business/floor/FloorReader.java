package de.hsrm.mi.swt.grundreisser.business.floor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This class represents a reader to create a floor from a given file
 * 
 * @author jheba001
 *
 */

public class FloorReader {

	/**
	 * This method reads floor information from a file and creates a
	 * Floor-object
	 * 
	 * @param file
	 *            the file, the data should be read from
	 * @return The created floor
	 * @throws FileNotFoundException
	 */
	public Floor read(File file) throws FileNotFoundException, IOException {
		Floor floor = new FloorImpl();
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			floor = (Floor) in.readObject();
			in.close();
			fileIn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return floor;
	}

}
