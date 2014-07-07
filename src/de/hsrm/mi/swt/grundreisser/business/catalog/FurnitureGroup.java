package de.hsrm.mi.swt.grundreisser.business.catalog;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.util.BackendRectangle;

/**
 * This class represents the group of furniture that was created and saved by
 * user
 * 
 * @author nmuel002
 * 
 */
public class FurnitureGroup implements Furniture {

	private int id;
	private String name;
	private List<FurnitureToGroup> group;
	private FurnitureType type = FurnitureType.GROUP;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            id
	 * @param name
	 *            name
	 * @param group
	 *            the list of furniture objects with their positions in the
	 *            group
	 */
	public FurnitureGroup(int id, String name, List<FurnitureToGroup> group) {
		this.group = group;
		this.id = id;
		this.name = name;
	}

	/**
	 * Constructor
	 * 
	 * @param group
	 *            the list of furniture objects with their positions in the
	 *            group
	 */
	public FurnitureGroup(List<FurnitureToGroup> group) {
		this.group = group;
	}

	@Override
	public int getId() {
		return id;
	}

	/**
	 * Sets the specified furniture id
	 * 
	 * @param id
	 *            id
	 */
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public Dimension getSize() {

		BackendRectangle rect = new BackendRectangle(new Rectangle());
		for (FurnitureToGroup f : group) {
			Dimension dim = f.getFurnitureObject().getSize();
			Point p = f.getPosition();
			BackendRectangle r = new BackendRectangle(p, dim.width, dim.height);
			rect = rect.union(r);
		}

		return new Dimension(rect.getWidth(), rect.getHeight());
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of furniture group
	 * 
	 * @param name
	 *            name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public List<FurnitureToGroup> getFurnitureList() {
		return this.group;
	}

	public String toString() {
		return String.format("Furniture group [id: %d, name: %s, size: %s]",
				id, name, this.getSize());
	}

	@Override
	public FurnitureType getFurnitureType() {
		return type;
	}

	public Object clone() {
		List<FurnitureToGroup> newGroup = new ArrayList<FurnitureToGroup>();

		for (FurnitureToGroup fg : this.group) {
			FurnitureToGroup newFG = (FurnitureToGroup) fg.clone();
			newGroup.add(newFG);
		}
		return new FurnitureGroup(id, name, newGroup);
	}
}