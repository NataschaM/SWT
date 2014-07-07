package de.hsrm.mi.swt.grundreisser.tests.business.command;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.business.command.wall.AddWallCommand;
import de.hsrm.mi.swt.grundreisser.business.command.wall.DeleteWallCommand;
import de.hsrm.mi.swt.grundreisser.business.command.wall.MoveWallCommand;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.FloorImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WallImpl;

/**
 * JUnit class to test all the wall command implementations
 * 
 * @author jheba001
 *
 */

public class WallCommandTest {

	Floor floor;
	private List<Wall> walls;
	Wall wall;

	/**
	 * Initialization
	 */
	@Before
	public void init() {
		floor = new FloorImpl();
		walls = new ArrayList<Wall>();
		wall = new WallImpl(new Point(0, 0), new Point(0, 1));
	}

	/**
	 * Method to test the add wall command
	 */
	@Test
	public void testAddWall() {
		AddWallCommand aw = new AddWallCommand(floor, wall);

		walls.add(wall);
		aw.execute();

		assertEquals(walls, floor.getGroundPlan().getWalls());

		walls.remove(wall);
		aw.undo();

		assertEquals(walls, floor.getGroundPlan().getWalls());

		walls.add(wall);
		aw.execute();

		assertEquals(walls, floor.getGroundPlan().getWalls());
	}

	/**
	 * Method to test the delete wall command
	 */
	@Test
	public void testDeleteWall() {
		AddWallCommand aw = new AddWallCommand(floor, wall);
		DeleteWallCommand dw = new DeleteWallCommand(floor, wall);

		walls.add(wall);
		aw.execute();
		walls.remove(wall);
		dw.execute();

		assertEquals(walls, floor.getGroundPlan().getWalls());

		walls.add(wall);
		dw.undo();

		assertEquals(walls, floor.getGroundPlan().getWalls());
	}

	/**
	 * Method to test the move wall command
	 */
	@Test
	public void testMoveWall() {
		wall.setCenterLocation(new Point(0, 0));

		AddWallCommand aw = new AddWallCommand(floor, wall);

		walls.add(wall);
		aw.execute();

		MoveWallCommand mw = new MoveWallCommand(wall, new Point(2, 2));

		walls.get(0).setCenterLocation(new Point(2, 2));
		mw.execute();

		assertEquals(walls, floor.getGroundPlan().getWalls());

		walls.get(0).setCenterLocation(new Point(0, 0));
		mw.undo();

		assertEquals(walls, floor.getGroundPlan().getWalls());
	}
}
