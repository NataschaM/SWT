package de.hsrm.mi.swt.grundreisser.tests.business.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.business.catalog.Catalog;
import de.hsrm.mi.swt.grundreisser.business.catalog.CatalogImpl;
import de.hsrm.mi.swt.grundreisser.business.catalog.CatalogReader;
import de.hsrm.mi.swt.grundreisser.business.catalog.CatalogWriter;

/**
 * 
 * @author jheba001
 *
 */

public class CatalogReaderWriterTest {
	Catalog catalog;
	CatalogWriter writer;
	CatalogReader reader;
	String name;

	// TODO Here 's a furniture needed

	/**
	 * Initialization
	 */
	@Before
	public void init() {
		// TODO initialize furniture and add it to the catalog
		catalog = new CatalogImpl();
		writer = new CatalogWriter();
		reader = new CatalogReader();
		name = "test";
	}

	/**
	 * Method to test the reader and writer funktionality
	 */
	@Test
	public void testReadWrite() {
		Catalog ncata = null;

		try {
			writer.write(catalog, name);
			ncata = reader.read(name);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// assertEquals(wall.getCenterLocation().x,nfloor.getGroundPlan().getWalls().get(0).getCenterLocation().x);
		// assertEquals(wall.getCenterLocation().y,nfloor.getGroundPlan().getWalls().get(0).getCenterLocation().y);
	}

	@Test(expected = FileNotFoundException.class)
	public void testFileNotFoundExceptionReader() throws FileNotFoundException {
		reader.read("none");
	}
}
