package de.hsrm.mi.swt.grundreisser.tests.business.catalog;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.business.catalog.CatalogFurniture;
import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture;
import de.hsrm.mi.swt.grundreisser.business.catalog.FurnitureGroup;
import de.hsrm.mi.swt.grundreisser.business.catalog.FurnitureToGroup;

public class FurnitureGroupTest {

	private List<FurnitureToGroup> furnList;
	private FurnitureGroup group;

	@Before
	public void setup() {

		furnList = new ArrayList<FurnitureToGroup>();

		Furniture f1 = new CatalogFurniture(1, "Sofa Lilie", 2, new Dimension(
				2, 4));
		FurnitureToGroup fg1 = new FurnitureToGroup(f1, new Point(1, 4));
		furnList.add(fg1);

		Furniture f2 = new CatalogFurniture(2, "Bett Schlafie", 2,
				new Dimension(8, 4));
		FurnitureToGroup fg2 = new FurnitureToGroup(f2, new Point(4, 10));
		furnList.add(fg2);

		Furniture f3 = new CatalogFurniture(3, "Kommode Ecke", 2,
				new Dimension(6, 2));
		FurnitureToGroup fg3 = new FurnitureToGroup(f3, new Point(15, 13));
		furnList.add(fg3);

		Furniture f4 = new CatalogFurniture(4, "Commode Quadrat", 2,
				new Dimension(4, 4));
		FurnitureToGroup fg4 = new FurnitureToGroup(f4, new Point(16, 2));
		furnList.add(fg4);

		Furniture f5 = new CatalogFurniture(5, "Carpet Large", 1,
				new Dimension(8, 10));
		FurnitureToGroup fg5 = new FurnitureToGroup(f5, new Point(8, 7));
		furnList.add(fg5);

		group = new FurnitureGroup(furnList);
	}

	@Test
	public void testGroupSize() {

		Dimension dim = group.getSize();

		assertEquals(dim, new Dimension(18, 14));
	}

}
