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
import de.hsrm.mi.swt.grundreisser.business.factories.FitmentFactory;
import de.hsrm.mi.swt.grundreisser.business.factories.FitmentFactoryImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.FitmentGroup;
import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;

public class FitmentFactoryTest {

	private Furniture simple;
	private Furniture composite;
	private FitmentFactory ff;

	@Before
	public void setUp() {

		ff = new FitmentFactoryImpl();

		List<FurnitureToGroup> toSimple = new ArrayList<FurnitureToGroup>();

		FurnitureToGroup tg1 = new FurnitureToGroup(new CatalogFurniture(1,
				"Sofa1", 2, new Dimension(6, 4)), new Point(3, 2));
		toSimple.add(tg1);

		FurnitureToGroup tg2 = new FurnitureToGroup(new CatalogFurniture(2,
				"Sofa3", 2, new Dimension(2, 8)), new Point(5, 8));
		toSimple.add(tg2);

		simple = new FurnitureGroup(3, "Gruppe 1", toSimple);

		List<FurnitureToGroup> toComposite = new ArrayList<FurnitureToGroup>();

		toComposite.add(new FurnitureToGroup(simple, new Point(3, 6)));

		FurnitureToGroup tg3 = new FurnitureToGroup(new CatalogFurniture(4,
				"Bett", 2, new Dimension(4, 6)), new Point(12, 4));
		toComposite.add(tg3);

		composite = new FurnitureGroup(5, "grooße Gruppe", toComposite);
	}

	@Test
	public void testFurnitureToFitment() {

		Fitment result = ff.createFitmentFromFurniture(composite, new Point(13,
				9));
		assertEquals(result.getCenterLocation(), new Point(13, 9));
		assertEquals(result.getRect(), new BackendRectangle(new Point(6, 3),
				new Point(20, 15)));

		BackendRectangle glRect0 = new BackendRectangle(new Point(9, 5), 6, 4);
		BackendRectangle glRect1 = new BackendRectangle(new Point(11, 11), 2, 8);
		BackendRectangle glRect2 = new BackendRectangle(new Point(18, 7), 4, 6);

		List<Fitment> glList = ((FitmentGroup) result).getGlobalList();

		assertEquals(glList.get(0).getRect(), glRect0);
		assertEquals(glList.get(1).getRect(), glRect1);
		assertEquals(glList.get(2).getRect(), glRect2);

	}

}
