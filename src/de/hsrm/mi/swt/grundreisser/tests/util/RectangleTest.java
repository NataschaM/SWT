package de.hsrm.mi.swt.grundreisser.tests.util;

import static org.junit.Assert.assertEquals;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;

public class RectangleTest {

	private BackendRectangle rect1;
	private BackendRectangle rect2;
	private BackendRectangle rect3;
	private BackendRectangle firstRect, secondRect;

	@Before
	public void setUp() {
		rect1 = new BackendRectangle(new Point(-1, -1), new Point(3, -3));
		rect2 = new BackendRectangle(new Point(1, 2), 2, 4);
		rect3 = new BackendRectangle(new Point(3, -3), new Point(-1, -1));
		firstRect = new BackendRectangle(new Point(4, 0), 12, 2);
		secondRect = new BackendRectangle(new Point(-2, 1), new Point(10, -1));
	}

	@Test
	public void testInitCorners() {
		assertEquals(rect1.getWidth(), 4);
		assertEquals(rect1.getHeight(), 2);
		assertEquals(rect1.getCenter(), new Point(1, -2));

		assertEquals(rect3.getWidth(), 4);
		assertEquals(rect3.getHeight(), 2);
		assertEquals(rect3.getCenter(), new Point(1, -2));
	}

	@Test
	public void testInitCenter() {
		assertEquals(rect2.getTopLeft(), new Point(0, 0));
		assertEquals(rect2.getBottomRight(), new Point(2, 4));
	}

	@Test
	public void testEquals() {
		assertEquals(firstRect, secondRect);
	}
}
