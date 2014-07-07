package de.hsrm.mi.swt.grundreisser.business.catalog;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Catalog contains all furniture articles from product range of the furniture
 * house and the furniture which was created and saved by user.
 * 
 * It refers to the whole application and is always to same for different
 * floors.
 * 
 * @author nmuel002
 * 
 */
public interface Catalog extends Serializable {

	/**
	 * Returns all categories of catalog
	 * 
	 * @return the list of categories
	 */
	public Map<String, Category> getCategories();

	/**
	 * Returns the list of category names
	 * 
	 * @return the list with names
	 */
	public List<String> getCategoryNames();

	/**
	 * Returns a category by the specified name
	 * 
	 * @param name
	 *            category name
	 * @return category
	 */
	public Category getCategoryByName(String name);

	/**
	 * Creates and adds a new category to the catalog
	 * 
	 * @param name
	 *            category name
	 */
	public void addCategory(String name);

	/**
	 * Returns the furniture article by id
	 * 
	 * @param id
	 *            id
	 * @return furniture
	 */
	public Furniture objectById(int id);

	/**
	 * Adds a furniture article into the specified category
	 * 
	 * @param furn
	 *            furniture
	 * @param cat
	 *            category
	 */
	public void addArticle(Furniture furn, Category cat);

	/**
	 * Returns the next id free to add the item to the catalog
	 * 
	 * @return the next id which is still not used
	 */
	public int getNextID();

}
