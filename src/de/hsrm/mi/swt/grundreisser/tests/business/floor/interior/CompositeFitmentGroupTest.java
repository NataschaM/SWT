package de.hsrm.mi.swt.grundreisser.tests.business.floor.interior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.CustomFitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.FitmentGroup;
import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;

public class CompositeFitmentGroupTest {

	private Fitment group;
	private List<Fitment> fitList;
	private Fitment composite;
	private List<Fitment> compList;

	@Before
	public void setUp() {

		fitList = new ArrayList<Fitment>();
		compList = new ArrayList<Fitment>();

		Fitment f1 = new CustomFitment(new Point(9, 5), 6, 4);
		fitList.add(f1);
		Fitment f2 = new CustomFitment(new Point(11, 11), 2, 8);
		fitList.add(f2);

		group = FitmentGroup.createFitmentGroup(fitList);
		compList.add(group);

		Fitment f3 = new CustomFitment(new Point(18, 7), 4, 6);
		compList.add(f3);

		composite = FitmentGroup.createFitmentGroup(compList);
	}

	@Test
	public void testGroupInit() {

		assertEquals(composite.getCenterLocation(), new Point(13, 9));
		assertEquals(composite.getRect(), new BackendRectangle(new Point(6, 3),
				new Point(20, 15)));
		((FitmentGroup) composite).getGlobalList();
	}

	@Test
	public void testMove() {

		composite.move(new Point(5, 19));
		assertEquals(composite.getCenterLocation(), new Point(5, 19));
		assertEquals(composite.getRect(), new BackendRectangle(
				new Point(-2, 13), new Point(12, 25)));

		List<Fitment> fits = ((FitmentGroup) composite).getGlobalList();

		assertEquals(fits.get(0).getRect(), new BackendRectangle(new Point(-2,
				13), new Point(4, 17)));

		assertEquals(fits.get(1).getRect(), new BackendRectangle(new Point(2,
				17), new Point(4, 25)));
	}

	@Test
	public void testCompositeRotateLeft() {

		// start union rect
		BackendRectangle startRect = new BackendRectangle(new Point(6, 3),
				new Point(20, 15));
		// rotated union rect to the left
		BackendRectangle rotRect = new BackendRectangle(new Point(7, 2),
				new Point(19, 16));

		composite.rotateLeft();

		assertEquals(composite.getRect(), rotRect);

		List<Fitment> simpleFits = ((FitmentGroup) composite).getGlobalList();

		assertEquals(simpleFits.get(0).getRect(), new BackendRectangle(
				new Point(9, 13), 4, 6));
		assertEquals(simpleFits.get(1).getRect(), new BackendRectangle(
				new Point(15, 11), 8, 2));
		assertEquals(simpleFits.get(2).getRect(), new BackendRectangle(
				new Point(11, 4), 6, 4));

		composite.rotateLeft();

		assertEquals(composite.getRect(), startRect);

		simpleFits = ((FitmentGroup) composite).getGlobalList();

		assertEquals(simpleFits.get(0).getRect(), new BackendRectangle(
				new Point(17, 13), 6, 4));
		assertEquals(simpleFits.get(1).getRect(), new BackendRectangle(
				new Point(15, 7), 2, 8));
		assertEquals(simpleFits.get(2).getRect(), new BackendRectangle(
				new Point(8, 11), 4, 6));
	}

	@Test
	public void testCompositeRotateRight() {

		// start union rect
		BackendRectangle startRect = new BackendRectangle(new Point(6, 3),
				new Point(20, 15));
		// rotated union rect to the right
		BackendRectangle rotRect = new BackendRectangle(new Point(7, 2),
				new Point(19, 16));

		composite.rotateRight();

		assertEquals(composite.getRect(), rotRect);

		List<Fitment> simpleFits = ((FitmentGroup) composite).getGlobalList();

		assertEquals(simpleFits.get(0).getRect(), new BackendRectangle(
				new Point(17, 5), 4, 6));
		assertEquals(simpleFits.get(1).getRect(), new BackendRectangle(
				new Point(11, 7), 8, 2));
		assertEquals(simpleFits.get(2).getRect(), new BackendRectangle(
				new Point(15, 14), 6, 4));

		composite.rotateRight();

		assertEquals(composite.getRect(), startRect);

		simpleFits = ((FitmentGroup) composite).getGlobalList();

		assertEquals(simpleFits.get(0).getRect(), new BackendRectangle(
				new Point(17, 13), 6, 4));
		assertEquals(simpleFits.get(1).getRect(), new BackendRectangle(
				new Point(15, 7), 2, 8));
		assertEquals(simpleFits.get(2).getRect(), new BackendRectangle(
				new Point(8, 11), 4, 6));
	}

	@Test
	public void globalRectangleTest() {

		List<Fitment> fits = ((FitmentGroup) composite).getFitments();

		BackendRectangle rect = ((FitmentGroup) composite).getGlobalRectangle(
				fits.get(1), composite);
		assertEquals(rect, new BackendRectangle(new Point(18, 7), 4, 6));
		BackendRectangle rect1 = ((FitmentGroup) composite).getGlobalRectangle(
				fits.get(0), composite);
		assertEquals(rect1, new BackendRectangle(new Point(9, 9), 6, 12));

		List<Fitment> smallFits = ((FitmentGroup) fits.get(0)).getFitments();
		BackendRectangle rect2 = ((FitmentGroup) fits.get(0))
				.getGlobalRectangle(smallFits.get(0), composite);
		assertEquals(rect2, new BackendRectangle(new Point(9, 5), 6, 4));

		BackendRectangle rect3 = ((FitmentGroup) fits.get(0))
				.getGlobalRectangle(smallFits.get(1), composite);
		assertEquals(rect3, new BackendRectangle(new Point(11, 11), 2, 8));
	}

	@Test
	public void intersectionTest() {

		BackendRectangle intRect = new BackendRectangle(new Point(3, 12), 4, 2);
		List<PlacedObject> objs = composite.intersects(intRect);
		assertTrue(objs.isEmpty());

		intRect = new BackendRectangle(new Point(7, 7), 8, 4);
		objs = composite.intersects(intRect);
		assertFalse(objs.isEmpty());
		assertEquals(objs.size(), 2);

		List<Fitment> fits = ((FitmentGroup) composite).getFitments();
		List<Fitment> smallFits = ((FitmentGroup) fits.get(0)).getFitments();

		assertEquals(objs.get(0), smallFits.get(0));
		assertEquals(objs.get(1), smallFits.get(1));
	}

}
