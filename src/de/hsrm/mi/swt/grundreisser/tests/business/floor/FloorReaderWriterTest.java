package de.hsrm.mi.swt.grundreisser.tests.business.floor;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.FloorImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.FloorReader;
import de.hsrm.mi.swt.grundreisser.business.floor.FloorWriter;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WallImpl;

public class FloorReaderWriterTest {
	Floor floor;
	FloorWriter writer;
	FloorReader reader;
	String name;
	Wall wall;

	/**
	 * Initialization
	 */
	@Before
	public void init() {
		wall = new WallImpl(new Point(0, 0), new Point(0, 1));
		floor = new FloorImpl();
		floor.getGroundPlan().addWall(wall);
		writer = new FloorWriter();
		reader = new FloorReader();
		name = "test";
	}

	/**
	 * Method to test the reader and writer funktionality
	 */
	@Test
	public void testReadWrite() {
		Floor nfloor = null;

		try {
			writer.write(floor, new File(name));
			nfloor = reader.read(new File(name));
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(wall.getCenterLocation().x, nfloor.getGroundPlan()
				.getWalls().get(0).getCenterLocation().x);
		assertEquals(wall.getCenterLocation().y, nfloor.getGroundPlan()
				.getWalls().get(0).getCenterLocation().y);
	}

	@Test(expected = FileNotFoundException.class)
	public void testFileNotFoundExceptionReader() throws FileNotFoundException,
			IOException {
		reader.read(new File("none"));
	}
}
