package de.hsrm.mi.swt.grundreisser.business.factories;

import java.io.FileNotFoundException;
import java.io.IOException;

import de.hsrm.mi.swt.grundreisser.business.catalog.Catalog;

/**
 * Floor factory is responsible for creation and persistence of a catalog
 * 
 * @author jheba001
 *
 */

public interface CatalogFactory {

	/**
	 * Creates a new catalog
	 * 
	 * @return new catalog
	 */
	Catalog createCatalog() throws IOException;

	/**
	 * Loades a catalog from a given file
	 * 
	 * @param fileName
	 *            file name
	 * @return loaded catalog
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	Catalog createCatalog(String filename) throws FileNotFoundException,
			IOException;
}
