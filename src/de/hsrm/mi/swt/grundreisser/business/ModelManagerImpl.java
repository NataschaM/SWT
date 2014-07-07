package de.hsrm.mi.swt.grundreisser.business;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hsrm.mi.swt.grundreisser.business.catalog.Catalog;
import de.hsrm.mi.swt.grundreisser.business.factories.CatalogFactory;
import de.hsrm.mi.swt.grundreisser.business.factories.CatalogFactoryImpl;
import de.hsrm.mi.swt.grundreisser.business.factories.FloorFactory;
import de.hsrm.mi.swt.grundreisser.business.factories.FloorFactoryImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;

/**
 * This class implements the ModelManager-interface and fulfills the managing of
 * the floors in the system
 * 
 * @author nmuel002
 * 
 */
public class ModelManagerImpl implements ModelManager {

	private static ModelManager instance = null;
	private Floor actFloor;
	private Map<String, Floor> floors;
	private FloorFactory floorFactory;
	private Catalog catalog;
	private String actFileName;
	private PropertyChangeSupport changeSupport;

	/**
	 * Private constructor to guarantee only one instance of the class
	 * 
	 */
	private ModelManagerImpl() {
		this.floors = new HashMap<String, Floor>();
		floorFactory = new FloorFactoryImpl();
		changeSupport = new PropertyChangeSupport(this);

		this.actFloor = floorFactory.createFloor();
		CatalogFactory catFact = new CatalogFactoryImpl();
		try {
			this.catalog = catFact.createCatalog(actFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns the singleton-instance of the class
	 * 
	 * @return instance
	 * @throws IOException
	 */
	public static ModelManager getInstance() {
		if (instance == null) {
			instance = new ModelManagerImpl();
		}
		return instance;
	}

	@Override
	public Floor getActFloor() {
		return this.actFloor;
	}

	@Override
	public Map<String, Floor> getFloors() {
		return this.floors;
	}

	@Override
	public String getActFileName() {
		return actFileName;
	}

	@Override
	public void loadFile(File file) throws FileNotFoundException, IOException {
		actFileName = file.getName();
		Floor oldFloor = this.actFloor;
		this.actFloor = this.floorFactory.createFloor(file);
		this.actFloor.load();
		this.floors.put(file.getName(), actFloor);
		changeSupport.firePropertyChange("actFloor", oldFloor, this.actFloor);
	}

	@Override
	public void saveFile(Floor floor, File file) throws IOException {
		this.floorFactory.saveFloor(floor, file);
	}

	@Override
	public void close() {
		for (Floor floor : floors.values()) {
			floor.close();
		}
	}

	@Override
	public void setActFloor(Floor floor) {
		this.actFloor = floor;
	}

	@Override
	public void setActFileName(String fileName) {
		this.actFileName = fileName;
	}

	@Override
	public Catalog getCatalog() {
		return this.catalog;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		changeSupport.addPropertyChangeListener(l);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		changeSupport.removePropertyChangeListener(l);
	}
}
