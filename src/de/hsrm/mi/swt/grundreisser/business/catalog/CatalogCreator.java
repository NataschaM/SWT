package de.hsrm.mi.swt.grundreisser.business.catalog;

import java.awt.Dimension;

/**
 * Creates a catalog with categories and furniture
 * 
 * @author jheba001
 *
 */

public class CatalogCreator {
	static private CatalogCreator instance = null;
	private Catalog catalog = null;

	private CatalogCreator() {
	}

	static public CatalogCreator getInstance() {
		if (instance == null)
			instance = new CatalogCreator();
		return instance;
	}

	public Catalog getCatalog() {
		if (catalog != null)
			return catalog;

		catalog = new CatalogImpl();

		int id = 12345;

		catalog.addCategory("Sofas");
		catalog.addArticle(new CatalogFurniture(id++, "Antje", 2,
				new Dimension(2900, 900)), catalog.getCategoryByName("Sofas"));
		catalog.addArticle(new CatalogFurniture(id++, "Bertus", 2,
				new Dimension(1500, 750)), catalog.getCategoryByName("Sofas"));
		catalog.addArticle(new CatalogFurniture(id++, "Claas", 2,
				new Dimension(1000, 800)), catalog.getCategoryByName("Sofas"));

		catalog.addCategory("Sessel");
		catalog.addArticle(new CatalogFurniture(id++, "Dirksje", 2,
				new Dimension(1000, 900)), catalog.getCategoryByName("Sessel"));
		catalog.addArticle(new CatalogFurniture(id++, "Elvina", 2,
				new Dimension(800, 800)), catalog.getCategoryByName("Sessel"));

		catalog.addCategory("Betten");
		catalog.addArticle(new CatalogFurniture(id++, "Frerik", 2,
				new Dimension(2090, 1950)), catalog.getCategoryByName("Betten"));
		catalog.addArticle(new CatalogFurniture(id++, "Gijsbert", 2,
				new Dimension(2090, 1060)), catalog.getCategoryByName("Betten"));

		catalog.addCategory("Tische");
		catalog.addArticle(new CatalogFurniture(id++, "Hendrikje", 2,
				new Dimension(1100, 900)), catalog.getCategoryByName("Tische"));
		catalog.addArticle(new CatalogFurniture(id++, "Iza", 2, new Dimension(
				2000, 1700)), catalog.getCategoryByName("Tische"));

		catalog.addCategory("Stühle");
		catalog.addArticle(new CatalogFurniture(id++, "Janneke", 2,
				new Dimension(500, 500)), catalog.getCategoryByName("Stühle"));
		catalog.addArticle(new CatalogFurniture(id++, "Koenraad", 2,
				new Dimension(800, 500)), catalog.getCategoryByName("Stühle"));

		catalog.addCategory("Schränke");
		catalog.addArticle(new CatalogFurniture(id++, "Lodewijk", 2,
				new Dimension(1500, 800)), catalog
				.getCategoryByName("Schränke"));
		catalog.addArticle(new CatalogFurniture(id++, "Marijke", 4,
				new Dimension(1100, 800)), catalog
				.getCategoryByName("Schränke"));

		catalog.addCategory("Teppiche");
		catalog.addArticle(new CatalogFurniture(id++, "Neptunus", 1,
				new Dimension(2000, 1500)), catalog
				.getCategoryByName("Teppiche"));
		catalog.addArticle(new CatalogFurniture(id++, "Olaf", 1, new Dimension(
				1200, 800)), catalog.getCategoryByName("Teppiche"));

		catalog.addCategory("Waschbecken");
		catalog.addArticle(new CatalogFurniture(id++, "Piet", 2, new Dimension(
				800, 800)), catalog.getCategoryByName("Waschbecken"));

		catalog.addCategory("Toiletten");
		catalog.addArticle(new CatalogFurniture(id++, "Quael", 2,
				new Dimension(600, 400)), catalog
				.getCategoryByName("Toiletten"));

		catalog.addCategory("Duschen");
		catalog.addArticle(new CatalogFurniture(id++, "Robbin", 4,
				new Dimension(700, 800)), catalog.getCategoryByName("Duschen"));

		catalog.addCategory("Badewanne");
		catalog.addArticle(new CatalogFurniture(id++, "Stejn", 2,
				new Dimension(1750, 750)), catalog
				.getCategoryByName("Badewanne"));

		catalog.addCategory("Aquarium");
		catalog.addArticle(new CatalogFurniture(id++, "Teelke", 2,
				new Dimension(1200, 500)), catalog
				.getCategoryByName("Aquarium"));
		catalog.addArticle(new CatalogFurniture(id++, "Ulrijke", 2,
				new Dimension(2300, 800)), catalog
				.getCategoryByName("Aquarium"));

		catalog.addCategory("Fernseher");
		catalog.addArticle(new CatalogFurniture(id++, "Vaast", 2,
				new Dimension(1900, 50)), catalog
				.getCategoryByName("Fernseher"));
		catalog.addArticle(new CatalogFurniture(id++, "Wubbo", 2,
				new Dimension(800, 500)), catalog
				.getCategoryByName("Fernseher"));

		catalog.addCategory("Stehlampen");
		catalog.addArticle(new CatalogFurniture(id++, "Xanten", 2,
				new Dimension(300, 300)), catalog
				.getCategoryByName("Stehlampen"));

		catalog.addCategory("Deckenlampen");
		catalog.addArticle(new CatalogFurniture(id++, "Yuna", 3, new Dimension(
				200, 200)), catalog.getCategoryByName("Deckenlampe"));
		catalog.addArticle(new CatalogFurniture(id++, "Zwanette", 3,
				new Dimension(1200, 50)), catalog
				.getCategoryByName("Deckenlampe"));

		return catalog;
	}
}
