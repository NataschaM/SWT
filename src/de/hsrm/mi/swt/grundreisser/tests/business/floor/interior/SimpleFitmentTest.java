package de.hsrm.mi.swt.grundreisser.tests.business.floor.interior;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.business.floor.interior.CustomFitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;
import de.hsrm.mi.swt.grundreisser.util.Vector;

public class SimpleFitmentTest {

	private Fitment fitment1;
	private Fitment fitment2;

	@Before
	public void setUp() {
		fitment1 = new CustomFitment(new Point(5, 4), 6, 4);
		fitment2 = new CustomFitment(new Point(11, 8), 2, 10);
	}

	@Test
	public void testRotate() {

		BackendRectangle rect1 = new BackendRectangle(new Point(3, 1),
				new Point(7, 7));
		BackendRectangle rect2 = new BackendRectangle(new Point(2, 2),
				new Point(8, 6));

		fitment1.rotateLeft();
		assertEquals(fitment1.getRect(), rect1);
		fitment1.rotateLeft();
		fitment1.rotateLeft();
		fitment1.rotateLeft();
		assertEquals(fitment1.getRect(), rect2);

		fitment1.rotateRight();
		assertEquals(fitment1.getRect(), rect1);
		fitment1.rotateRight();
		assertEquals(fitment1.getRect(), rect2);

		fitment1.rotateLeft();
		assertEquals(fitment1.getRect(), rect1);

		rect1 = new BackendRectangle(new Point(10, 3), new Point(12, 13));
		rect2 = new BackendRectangle(new Point(6, 7), new Point(16, 9));

		fitment2.rotateLeft();
		assertEquals(fitment2.getRect(), rect2);
		fitment2.rotateRight();
		assertEquals(fitment2.getRect(), rect1);
		fitment2.rotateRight();
		fitment2.rotateRight();
		assertEquals(fitment2.getRect(), rect1);
		fitment2.rotateLeft();
		assertEquals(fitment2.getRect(), rect2);
	}

	@Test
	public void testMove() {

		fitment1.move(new Point(3, 10));
		assertEquals(fitment1.getRect(), new BackendRectangle(new Point(0, 8),
				new Point(6, 12)));

		fitment2.move(new Vector(4, -3));
		assertEquals(fitment2.getRect(), new BackendRectangle(new Point(14, 0),
				new Point(16, 10)));
	}

}
