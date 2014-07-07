package de.hsrm.mi.swt.grundreisser.business.catalog;

import java.awt.Dimension;

public class CustomFurniture implements Furniture {

	private int id;
	private String name;
	private int layer;
	private Dimension size;
	private FurnitureType type = FurnitureType.CUSTOM;

	/**
	 * Constructor for unspecified furniture
	 */
	public CustomFurniture() {
		this.size = new Dimension(40, 40);
	}

	/**
	 * Constructor for the specified furniture that was written to the catalog
	 * 
	 * @param id
	 *            id
	 * @param name
	 *            name
	 * @param layer
	 *            layer
	 * @param size
	 *            size
	 */
	public CustomFurniture(int id, String name, int layer, Dimension size) {
		this.id = id;
		this.name = name;
		this.layer = layer;
		this.size = size;
	}

	@Override
	public int getId() {
		return id;
	}

	/**
	 * Sets the id value
	 * 
	 * @param id
	 *            id
	 */
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public Dimension getSize() {
		return size;
	}

	/**
	 * Sets the size of the furniture
	 * 
	 * @param size
	 *            Dimension size
	 */
	public void setSize(Dimension size) {
		this.size = size;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the furniture
	 * 
	 * @param name
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the layer
	 * 
	 * @return layer
	 */
	public int getLayer() {
		return layer;
	}

	/**
	 * Sets the layer
	 * 
	 * @param layer
	 *            layer
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String toString() {
		return String.format(
				"Custom furniture [id: %d, name: %s, layer: %d, size: %s]", id,
				name, layer, size);
	}

	@Override
	public FurnitureType getFurnitureType() {
		return type;
	}

	public Object clone() {
		return new CustomFurniture(id, name, layer, size);
	}

}
