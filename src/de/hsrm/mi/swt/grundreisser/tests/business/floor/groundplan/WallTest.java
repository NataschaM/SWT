package de.hsrm.mi.swt.grundreisser.tests.business.floor.groundplan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.FloorImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.GroundPlan;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.OuterWall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WallImpl;
import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;
import de.hsrm.mi.swt.grundreisser.util.Vector;

public class WallTest {

	private Wall outerWall;
	private WallImpl innerWallHoriz;
	private WallImpl innerWallVert;
	private WallImpl inWall1;
	private WallImpl inWall2;
	private GroundPlan gp;
	private Floor floor;
	private Point center;

	@Before
	public void setUp() {

		floor = new FloorImpl();
		gp = floor.getGroundPlan();

		innerWallHoriz = new WallImpl(new Point(-2, 0), new Point(10, 0));
		innerWallVert = new WallImpl(new Point(2, 2), new Point(6, 16));

		inWall1 = new WallImpl(new Point(2, 3), new Point(8, 3));
		inWall2 = new WallImpl(new Point(5, 6), new Point(5, 14));

		gp.addWall(innerWallHoriz);
		gp.addWall(innerWallVert);

		LinkedList<WallImpl> walls = new LinkedList<WallImpl>();
		Point p1 = new Point(0, 5);
		Point p2 = new Point(0, 14);
		Point p3 = new Point(4, 14);
		Point p4 = new Point(4, 10);
		Point p5 = new Point(9, 10);
		Point p6 = new Point(9, 13);
		Point p7 = new Point(14, 13);
		Point p8 = new Point(14, 0);
		Point p9 = new Point(8, 0);
		Point p10 = new Point(8, 5);

		walls.add(new WallImpl(p1, p2));
		walls.add(new WallImpl(p2, p3));
		walls.add(new WallImpl(p3, p4));
		walls.add(new WallImpl(p4, p5));
		walls.add(new WallImpl(p5, p6));
		walls.add(new WallImpl(p6, p7));
		walls.add(new WallImpl(p7, p8));
		walls.add(new WallImpl(p8, p9));
		walls.add(new WallImpl(p9, p10));
		walls.add(new WallImpl(p10, p1));

		center = new Point(7, 7);
		outerWall = new OuterWall(center, walls);
		outerWall.setThickness(2);

		gp.addWall(outerWall);
	}

	@Test
	public void testInnerWallInit() {

		Point p1 = new Point(4, 20), p2 = new Point(8, 4);
		WallImpl w = new WallImpl(p1, p2);

		assertEquals(w.getPos1(), p1);
		assertEquals(w.getPos2(), new Point(4, 4));

		p2 = new Point(16, 18);
		w = new WallImpl(p1, p2);

		assertEquals(w.getPos1(), p1);
		assertEquals(w.getPos2(), new Point(16, 20));

		p1 = new Point(2, 2);
		p2 = new Point(10, 4);
		w = new WallImpl(p1, p2);

		assertEquals(w.getPos1(), p1);
		assertEquals(w.getPos2(), new Point(10, 2));

		p2 = new Point(6, 16);
		w = new WallImpl(p1, p2);

		assertEquals(w.getPos1(), p1);
		assertEquals(w.getPos2(), new Point(2, 16));

		assertEquals(innerWallVert.getPos1(), p1);
		assertEquals(innerWallVert.getPos2(), new Point(2, 16));
	}

	@Test
	public void testInnerWall() {

		// test horizontal wall
		innerWallHoriz.setThickness(2);
		BackendRectangle newRect = null;
		Point rectCenter = null;

		newRect = new BackendRectangle(new Point(4, 0), 12, 2);
		rectCenter = newRect.getCenter();

		assertEquals(innerWallHoriz.getCenterLocation(), rectCenter);
		assertEquals(innerWallHoriz.getRect(), newRect);

		newRect = new BackendRectangle(new Point(-2, 1), new Point(10, -1));
		rectCenter = newRect.getCenter();
		assertEquals(innerWallHoriz.getCenterLocation(), rectCenter);
		assertEquals(innerWallHoriz.getRect(), newRect);

		// test vertical wall
		innerWallVert.setThickness(2);

		newRect = new BackendRectangle(new Point(2, 9), 2, 14);
		rectCenter = newRect.getCenter();
		assertEquals(innerWallVert.getCenterLocation(), rectCenter);
		assertEquals(innerWallVert.getRect(), newRect);

		newRect = new BackendRectangle(new Point(1, 2), new Point(3, 16));
		rectCenter = newRect.getCenter();
		assertEquals(innerWallVert.getCenterLocation(), rectCenter);
		assertEquals(innerWallVert.getRect(), newRect);
	}

	@Test
	public void testOuterWall() {

		BackendRectangle rect = outerWall.getRect();
		BackendRectangle rect1 = new BackendRectangle(new Point(-1, -1),
				new Point(15, 15));

		BackendRectangle rect2 = new BackendRectangle(center, 16, 16);

		assertTrue(rect.equals(rect1));
		assertTrue(rect.equals(rect2));

		Point wallCenter = outerWall.getCenterLocation();

		assertTrue(wallCenter.equals(center));
	}

	@Test
	public void testInnerWallSetCenter() {

		innerWallHoriz.setCenterLocation(new Point(8, -4));

		assertEquals(innerWallHoriz.getPos1(), new Point(2, -4));
		assertEquals(innerWallHoriz.getPos2(), new Point(14, -4));
	}

	@Test
	public void testInnerWallMovement() {

		inWall1.setThickness(2);
		inWall2.setThickness(2);

		Vector v1 = new Vector(-2, -2);
		Point top1 = inWall1.getRect().getTopLeft();
		Vector v1_1 = new Vector(top1, new Point(0, 0));
		assertEquals(v1, v1_1);

		inWall1.move(v1_1);
		assertEquals(inWall1.getRect(), new BackendRectangle(new Point(0, 0),
				new Point(6, 2)));

		Vector v2 = new Vector(-4, -6);
		Point top2 = inWall2.getRect().getTopLeft();
		Vector v2_1 = new Vector(top2, new Point(0, 0));
		assertEquals(v2, v2_1);

		inWall2.move(v2_1);
		assertEquals(inWall2.getRect(), new BackendRectangle(new Point(0, 0),
				new Point(2, 8)));
	}

	@Test
	public void testOuterWallSetCenter() {

		Point newCenter = new Point(0, 0);
		outerWall.setCenterLocation(newCenter);

		BackendRectangle rect = outerWall.getRect();
		BackendRectangle rect1 = new BackendRectangle(new Point(-8, -8),
				new Point(8, 8));
		BackendRectangle rect2 = new BackendRectangle(newCenter, 16, 16);

		assertTrue(rect.equals(rect1));
		assertTrue(rect.equals(rect2));
	}

	@Test
	public void testOuterWallMovement() {

		Point newTop = new Point(4, 2);

		Vector v = new Vector(outerWall.getRect().getTopLeft(), newTop);

		outerWall.move(v);

		BackendRectangle rect = outerWall.getRect();
		BackendRectangle r1 = new BackendRectangle(newTop, new Point(20, 18));

		assertEquals(rect, r1);

		assertEquals(outerWall.getCenterLocation(), new Point(12, 10));
	}

}
