package de.hsrm.mi.swt.grundreisser.business.catalog;

import java.io.Serializable;
import java.util.Map;

/**
 * Category classifies furniture objects by group: for example tables, beds etc.
 * 
 * @author nmuel002
 *
 */
public interface Category extends Serializable {

	/**
	 * Returns the category name
	 * 
	 * @return name
	 */
	public String getName();

	/**
	 * Sets category name
	 * 
	 * @param name
	 *            name
	 */
	public void setName(String name);

	/**
	 * Adds the article to the category
	 * 
	 * @param f
	 *            furniture article
	 */
	public void addArticle(Furniture f);

	/**
	 * Returns furniture article by id
	 * 
	 * @param id
	 *            id
	 * @return furniture
	 */
	public Furniture getArticleById(int id);

	/**
	 * Returns a map of all furniture articles contained in this category
	 * 
	 * @return the map of furniture with ID as key
	 */
	public Map<Integer, Furniture> getFurnitures();

}
