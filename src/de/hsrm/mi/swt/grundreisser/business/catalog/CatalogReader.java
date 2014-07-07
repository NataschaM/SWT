package de.hsrm.mi.swt.grundreisser.business.catalog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This class represents a reader to create a catalog from a given file
 * 
 * @author jheba001
 *
 */

public class CatalogReader {

	/**
	 * This method reads floor information from a file and creates a
	 * Floor-object
	 * 
	 * @param filename
	 *            The filename of the file
	 * @return The created floor
	 * @throws FileNotFoundException
	 */
	public Catalog read(String filename) throws FileNotFoundException {
		Catalog catalog = new CatalogImpl();
		try {
			FileInputStream fileIn = new FileInputStream(filename + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			catalog = (Catalog) in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException ne) {
			throw ne;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return catalog;
	}

}
