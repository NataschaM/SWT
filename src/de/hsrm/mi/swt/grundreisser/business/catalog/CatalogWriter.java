package de.hsrm.mi.swt.grundreisser.business.catalog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This class represents a writer to save a complete catalog
 * 
 * @author jheba001
 *
 */

public class CatalogWriter {

	/**
	 * This method saves a Catalog-Object into a file with the given filename
	 * 
	 * @param floor
	 *            The floor that should be saved
	 * @param filename
	 *            The file name of the created file
	 */
	public void write(Catalog catalog, String filename) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(filename + ".ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(catalog);
		out.close();
		fileOut.close();
	}
}
