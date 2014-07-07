package de.hsrm.mi.swt.grundreisser.business.catalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogImpl implements Catalog {

	private Map<String, Category> categories;

	public CatalogImpl() {
		this.categories = new HashMap<String, Category>();
	}

	@Override
	public Map<String, Category> getCategories() {
		return this.categories;
	}

	@Override
	public List<String> getCategoryNames() {
		List<String> cats = new ArrayList<String>();
		for (String name : this.categories.keySet()) {
			cats.add(name);
		}
		return cats;
	}

	@Override
	public Category getCategoryByName(String name) {
		return this.categories.get(name);
	}

	@Override
	public void addCategory(String name) {
		Category cat = new CategoryImpl(name);
		this.categories.put(name, cat);
	}

	@Override
	public Furniture objectById(int id) {

		for (Category cat : this.categories.values()) {
			Furniture furn = cat.getArticleById(id);
			if (furn != null)
				return furn;
		}
		return null;
	}

	@Override
	public void addArticle(Furniture furn, Category cat) {

		if (this.categories.containsValue(cat)) {
			cat.addArticle(furn);
		}

	}

	@Override
	public int getNextID() {

		int maxID = 0;

		for (Category cat : this.categories.values()) {

			Map<Integer, Furniture> map = cat.getFurnitures();
			for (int i : map.keySet()) {
				if (i > maxID) {
					maxID = i;
				}
			}
		}

		return maxID + 1;
	}

	/**
	 * Returns the names of layers
	 * 
	 * @return array of layer names
	 */
	public static String[] getLayerNames() {
		return new String[] { null, "Bodenhohe Objekte", "Mittelhohe Objekte",
				"Deckenhohe Objekte", "Raumhohe Objekte" };
	}

}
