package de.hsrm.mi.swt.grundreisser.business.catalog;

import java.awt.Dimension;

/**
 * Class for furniture which belongs to the furniture house catalog
 * 
 * @author nmuel002
 * 
 */
public class CatalogFurniture implements Furniture {

	private Dimension size;
	private String name;
	private int id;
	private int layer;
	private FurnitureType type = FurnitureType.CATALOG;

	public CatalogFurniture(int id, String name, int layer, Dimension dim) {
		this.id = id;
		this.name = name;
		this.layer = layer;
		this.size = dim;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Dimension getSize() {
		return size;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Returns the layer of the furniture
	 * 
	 * @return layer
	 */
	public int getLayer() {
		return layer;
	}

	public String toString() {
		return String.format(
				"Catalog furniture [id: %d, name: %s, layer: %d, size: %s]",
				id, name, layer, size);
	}

	@Override
	public FurnitureType getFurnitureType() {
		return type;
	}

	public Object clone() {
		return new CatalogFurniture(id, name, layer, size);
	}
}
