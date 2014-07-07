package de.hsrm.mi.swt.grundreisser.tests.business.command;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.command.exceptions.UndoRedoException;
import de.hsrm.mi.swt.grundreisser.business.command.wall.AddWallCommand;
import de.hsrm.mi.swt.grundreisser.business.command.wall.CommandManagerImpl;
import de.hsrm.mi.swt.grundreisser.business.command.wall.DeleteWallCommand;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.FloorImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WallImpl;

/**
 * JUnit class to test the command manager implementation
 * 
 * @author jheba001
 *
 */

public class CommandManagerTest {

	CommandManagerImpl cm;
	List<Command> commands;
	Command ac;
	Command dc;

	/**
	 * Initialization
	 */
	@Before
	public void init() {
		cm = new CommandManagerImpl();
		commands = new ArrayList<Command>();
		ac = new AddWallCommand(new FloorImpl(), new WallImpl(new Point(0, 0),
				new Point(0, 1)));
		dc = new DeleteWallCommand(new FloorImpl(), new WallImpl(
				new Point(0, 0), new Point(0, 1)));
	}

	/**
	 * Method to test the execute and push functionality
	 */
	@Test
	public void testExecAndPush() {
		Floor floor = new FloorImpl();
		Wall wall = new WallImpl(new Point(0, 0), new Point(0, 1));
		Command nc = new AddWallCommand(floor, wall);
		cm.execAndPush(nc);

		List<Wall> walls = new ArrayList<Wall>();
		walls.add(wall);

		assertEquals(floor.getGroundPlan().getWalls(), walls);
	}

	/**
	 * Method to test the undo functionality
	 */
	@Test
	public void testUndo() {
		cm.execAndPush(ac);
		cm.execAndPush(dc);
		commands.add(ac);
		commands.add(dc);
		try {
			cm.undo();
			cm.undo();
		} catch (UndoRedoException e) {
			e.printStackTrace();
		}
		commands.remove(dc);
		commands.remove(ac);
		assertEquals(cm.getList(), commands);
	}

	/**
	 * Method to test the redo functionality
	 */
	@Test
	public void testRedo() {
		cm.execAndPush(ac);
		cm.execAndPush(dc);
		commands.add(ac);
		commands.add(dc);
		try {
			cm.undo();
			cm.undo();
			cm.redo();
		} catch (UndoRedoException e) {
			e.printStackTrace();
		}
		commands.remove(dc);
		assertEquals(cm.getList(), commands);
	}

	/**
	 * Method to test the undo redo functionality
	 */
	@Test
	public void testUndoRedo() {
		Command nc = new AddWallCommand(new FloorImpl(), new WallImpl(
				new Point(0, 0), new Point(0, 1)));
		cm.execAndPush(ac);
		cm.execAndPush(dc);
		commands.add(ac);
		commands.add(dc);
		try {
			cm.undo();
			cm.undo();
			cm.redo();
		} catch (UndoRedoException e) {
			e.printStackTrace();
		}
		commands.remove(dc);

		cm.execAndPush(nc);
		commands.add(nc);
		assertEquals(cm.getList(), commands);
	}

	/**
	 * Method to test the undo functionality and expected exception
	 */
	@Test(expected = UndoRedoException.class)
	public void testUndoException() throws UndoRedoException {
		cm.undo();
	}

	/**
	 * Method to test the redo functionality and expected exception
	 */
	@Test(expected = UndoRedoException.class)
	public void testRedoException() throws UndoRedoException {
		cm.redo();
	}
}