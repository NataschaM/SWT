package de.hsrm.mi.swt.grundreisser.business.factories;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.FloorImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.FloorReader;
import de.hsrm.mi.swt.grundreisser.business.floor.FloorWriter;

/**
 * This is the implementation of the floor factory. It implements the
 * FloorFactory interface.
 * 
 * @author jheba001
 *
 */

public class FloorFactoryImpl implements FloorFactory {

	private FloorReader reader = new FloorReader();
	private FloorWriter writer = new FloorWriter();

	@Override
	public Floor createFloor() {
		return new FloorImpl();
	}

	@Override
	public Floor createFloor(File file) throws FileNotFoundException,
			IOException {
		if (file != null)
			return reader.read(file);
		return createFloor();
	}

	@Override
	public void saveFloor(Floor floor, File file) throws IOException {
		writer.write(floor, file);
	}

}
