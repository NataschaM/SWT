package de.hsrm.mi.swt.grundreisser.business.factories;

import java.io.FileNotFoundException;
import java.io.IOException;

import de.hsrm.mi.swt.grundreisser.business.catalog.Catalog;
import de.hsrm.mi.swt.grundreisser.business.catalog.CatalogCreator;
import de.hsrm.mi.swt.grundreisser.business.catalog.CatalogReader;
import de.hsrm.mi.swt.grundreisser.business.catalog.CatalogWriter;

/**
 * This is the implementation of the catalog factory. It implements the
 * CatalogFactory interface.
 * 
 * @author jheba001
 *
 */

public class CatalogFactoryImpl implements CatalogFactory {

	private CatalogReader reader = new CatalogReader();
	private CatalogWriter writer = new CatalogWriter();
	private CatalogCreator catCreator;

	@Override
	public Catalog createCatalog() throws IOException {
		catCreator = CatalogCreator.getInstance();
		Catalog catalog = catCreator.getCatalog();
		writer.write(catalog, "catalog");
		return catalog;
	}

	@Override
	public Catalog createCatalog(String filename) throws FileNotFoundException,
			IOException {
		if (filename != null)
			return reader.read(filename);
		return createCatalog();
	}

	/**
	 * Persists the catalog objects
	 * 
	 * @param catalog
	 *            catalog
	 */
	public void saveCatalog(Catalog catalog, String filename)
			throws IOException {
		writer.write(catalog, filename);
	}

}
