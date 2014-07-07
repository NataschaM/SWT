package de.hsrm.mi.swt.grundreisser.tests.business.floor.interior;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.business.floor.interior.CustomFitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.FitmentGroup;
import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;
import de.hsrm.mi.swt.grundreisser.util.Vector;

public class SimpleFitmentGroupTest {

	private Fitment group;
	private List<Fitment> fitList;

	@Before
	public void setUp() {

		fitList = new ArrayList<Fitment>();

		Fitment f1 = new CustomFitment(new Point(9, 5), 6, 4);
		fitList.add(f1);
		Fitment f2 = new CustomFitment(new Point(11, 11), 2, 8);
		fitList.add(f2);

		group = FitmentGroup.createFitmentGroup(fitList);
	}

	@Test
	public void testGroupInit() {

		Point center = new Point(9, 9);
		assertEquals(group.getCenterLocation(), center);

		BackendRectangle rect1 = new BackendRectangle(new Point(6, 3),
				new Point(12, 15));
		assertEquals(group.getRect(), rect1);

		BackendRectangle rect2 = FitmentGroup
				.getLocaleRect(((FitmentGroup) group).getFitments());
		assertEquals(new Point(0, 0), rect2.getTopLeft());

	}

	@Test
	public void testMove() {

		group.move(new Point(17, 16));
		assertEquals(group.getCenterLocation(), new Point(17, 16));

		List<Fitment> fits = ((FitmentGroup) group).getGlobalList();

		assertEquals(fits.get(0).getRect(), new BackendRectangle(new Point(14,
				10), new Point(20, 14)));

		assertEquals(fits.get(1).getRect(), new BackendRectangle(new Point(18,
				14), new Point(20, 22)));
	}

	@Test
	public void testSimpleRotateLeft() {

		// rotated left union rect
		BackendRectangle rect1 = new BackendRectangle(new Point(3, 6),
				new Point(15, 12));
		// union rect before rotation
		BackendRectangle rect2 = new BackendRectangle(new Point(6, 3),
				new Point(12, 15));

		// rotated left small rect
		BackendRectangle fitR1_2 = new BackendRectangle(new Point(3, 6),
				new Point(7, 12));
		BackendRectangle fitR2_2 = new BackendRectangle(new Point(7, 6),
				new Point(15, 8));

		group.rotateLeft();
		// assertEquals(group.getRect(), rect1);

		List<Fitment> fits = ((FitmentGroup) group).getGlobalList();

		Fitment fit1 = fits.get(0);
		Fitment fit2 = fits.get(1);

		Vector v1 = new Vector(0, -4);
		Vector v2 = v1.rotateLeft();
		assertEquals(v2.getX(), -4);
		assertEquals(v2.getY(), 0);

		assertEquals(fit1.getRect(), fitR1_2);
		assertEquals(fit2.getRect(), fitR2_2);

		group.rotateLeft();
		assertEquals(group.getRect(), rect2);

		fits = ((FitmentGroup) group).getGlobalList();

		fit1 = fits.get(0);
		fit2 = fits.get(1);

		assertEquals(fit1.getRect(), new BackendRectangle(new Point(9, 13), 6,
				4));
		assertEquals(fit2.getRect(),
				new BackendRectangle(new Point(7, 7), 2, 8));
	}

	@Test
	public void testSimpleRotateRight() {

		// rotated right union rect
		BackendRectangle rect1 = new BackendRectangle(new Point(3, 6),
				new Point(15, 12));
		// union rect before rotation
		BackendRectangle rect2 = new BackendRectangle(new Point(6, 3),
				new Point(12, 15));

		// small rects of fitmentImpl objects
		BackendRectangle fitR1_1 = new BackendRectangle(new Point(6, 3),
				new Point(12, 7));
		BackendRectangle fitR2_1 = new BackendRectangle(new Point(10, 7),
				new Point(12, 15));

		// rotated right small rect
		BackendRectangle fitR1_2 = new BackendRectangle(new Point(11, 6),
				new Point(15, 12));
		BackendRectangle fitR2_2 = new BackendRectangle(new Point(3, 10),
				new Point(11, 12));

		group.rotateRight();
		assertEquals(group.getRect(), rect1);

		List<Fitment> fits = ((FitmentGroup) group).getGlobalList();

		Fitment fit1 = fits.get(0);
		Fitment fit2 = fits.get(1);

		Vector v1 = new Vector(0, -4);
		Vector v2 = v1.rotateRight();
		assertEquals(v2.getX(), 4);
		assertEquals(v2.getY(), 0);

		assertEquals(fit1.getRect(), fitR1_2);
		assertEquals(fit2.getRect(), fitR2_2);

		group.rotateRight();
		assertEquals(group.getRect(), rect2);

		fits = ((FitmentGroup) group).getGlobalList();

		fit1 = fits.get(0);
		fit2 = fits.get(1);

		assertEquals(fit1.getRect(), new BackendRectangle(new Point(9, 13), 6,
				4));
		assertEquals(fit2.getRect(),
				new BackendRectangle(new Point(7, 7), 2, 8));

		group.rotateRight();
		group.rotateRight();

		assertEquals(group.getRect(), rect2);

		fits = ((FitmentGroup) group).getGlobalList();

		fit1 = fits.get(0);
		fit2 = fits.get(1);

		assertEquals(fit1.getRect(), fitR1_1);
		assertEquals(fit2.getRect(), fitR2_1);
	}
}
