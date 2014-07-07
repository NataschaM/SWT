package de.hsrm.mi.swt.grundreisser.business.catalog;

import java.util.HashMap;
import java.util.Map;

public class CategoryImpl implements Category {

	private String name;
	private Map<Integer, Furniture> furnitureMap;

	public CategoryImpl(String name) {
		this.name = name;
		this.furnitureMap = new HashMap<Integer, Furniture>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void addArticle(Furniture f) {
		this.furnitureMap.put(f.getId(), f);
	}

	@Override
	public Furniture getArticleById(int id) {
		return this.furnitureMap.get(id);
	}

	@Override
	public Map<Integer, Furniture> getFurnitures() {
		return this.furnitureMap;
	}

	public String toString() {
		return String.format("Category [name: %s]", name);
	}

}
