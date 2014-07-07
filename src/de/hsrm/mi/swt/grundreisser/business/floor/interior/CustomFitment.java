package de.hsrm.mi.swt.grundreisser.business.floor.interior;

import java.awt.Point;

import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture.FurnitureType;

/**
 * This is a modification of FitmentImpl that is defined by a user.
 * 
 * Unlike by CatalogFitment here the user can specify size and layer of the
 * fitment
 * 
 * @author nmuel002
 * 
 */
public class CustomFitment extends FitmentImpl {

	private String name;

	/**
	 * Default constructor
	 */
	public CustomFitment() {
		super();
	}

	/**
	 * Constructor for a fitment object with the specified position. The fitment
	 * gets default values for its size.
	 * 
	 * @param center
	 *            center position
	 */
	public CustomFitment(Point center) {
		super();
		this.center = center;
	}

	/**
	 * Constructor for a fitment with specified position and size
	 * 
	 * @param center
	 *            center position
	 * @param width
	 *            width
	 * @param height
	 *            height
	 */
	public CustomFitment(Point center, int width, int height) {
		super(center, width, height);
	}

	/**
	 * The method gives the possibility to define or redefine the size of this
	 * fitment
	 * 
	 * @param width
	 *            width
	 * @param height
	 *            height
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Sets the id from catalog
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(int id) {
		this.furnitureId = id;
	}

	/**
	 * Sets the layer, because the user can specify the layer for this kind of
	 * fitment
	 * 
	 * @param layer
	 *            layer
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}

	public Object clone() {
		CustomFitment newFitment = new CustomFitment();
		newFitment.move(center);
		newFitment.setSize(width, height);
		newFitment.setId(this.furnitureId);
		newFitment.setLayer(layer);
		return newFitment;
	}

	public String toString() {
		return String
				.format("CustomFitment [furniture id: %s, layer: %d, center: %s, width: %d, height: %d]",
						this.furnitureId, this.layer, this.center, this.width,
						this.height);
	}

	@Override
	public FurnitureType getType() {
		return FurnitureType.CUSTOM;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
