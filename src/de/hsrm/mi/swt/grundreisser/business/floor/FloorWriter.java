package de.hsrm.mi.swt.grundreisser.business.floor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This class represents a writer to save a complete floor
 * 
 * @author jheba001
 *
 */

public class FloorWriter {

	/**
	 * This method saves a Floor-Object into a file with the given filename
	 * 
	 * @param floor
	 *            The floor that should be saved
	 * @param file
	 *            the file, that data should be written in
	 */
	public void write(Floor floor, File file) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(floor);
		out.close();
		fileOut.close();
	}
}
