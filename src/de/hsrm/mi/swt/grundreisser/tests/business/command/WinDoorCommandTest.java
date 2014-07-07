package de.hsrm.mi.swt.grundreisser.tests.business.command;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.business.command.windoor.AddWinDoorCommand;
import de.hsrm.mi.swt.grundreisser.business.command.windoor.ClearWinDoorsCommand;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.FloorImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Door;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.GroundPlan;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.OuterWall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WallImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WinDoor;

/**
 * JUnit class to test all the windoor command implementations
 * 
 * @author jheba001
 *
 */

public class WinDoorCommandTest {

	Wall wall;
	OuterWall outerwall;
	List<WinDoor> windoors;
	WinDoor windoor;

	/**
	 * Initialization
	 */
	@Before
	public void init() {
		Floor floor = new FloorImpl();
		GroundPlan gp = floor.getGroundPlan();
		LinkedList<WallImpl> l = new LinkedList<WallImpl>();

		wall = new WallImpl(new Point(0, 0), new Point(0, 1));
		l.add((WallImpl) wall);
		outerwall = new OuterWall(new Point(0, 0), l);

		gp.addWall(outerwall);

		windoor = new Door();
		windoors = new ArrayList<WinDoor>();
	}

	/**
	 * Method to test the add windoor command
	 */
	@Test
	public void testAddWinDoor() {
		AddWinDoorCommand awd = new AddWinDoorCommand(wall, windoor);
		windoors.add(windoor);
		awd.execute();

		assertEquals(wall.getWinDoors(), windoors);
		assertEquals(outerwall.getWinDoors(), windoors);

		windoors.remove(windoor);
		awd.undo();

		assertEquals(wall.getWinDoors(), windoors);
		assertEquals(outerwall.getWinDoors(), windoors);
	}

	/**
	 * Method to test the delete windoor command
	 */
	@Test
	public void testDeleteWinDoor() {
		AddWinDoorCommand awd = new AddWinDoorCommand(wall, windoor);
		ClearWinDoorsCommand dwd = new ClearWinDoorsCommand(wall);

		windoors.add(windoor);
		awd.execute();
		windoors.remove(windoor);
		dwd.execute();

		assertEquals(wall.getWinDoors(), windoors);

		windoors.add(windoor);
		dwd.undo();

		assertEquals(wall.getWinDoors(), windoors);
	}
}
