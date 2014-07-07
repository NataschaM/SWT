package de.hsrm.mi.swt.grundreisser.business.factories;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.catalog.Catalog;
import de.hsrm.mi.swt.grundreisser.business.catalog.CatalogFurniture;
import de.hsrm.mi.swt.grundreisser.business.catalog.Category;
import de.hsrm.mi.swt.grundreisser.business.catalog.CustomFurniture;
import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture;
import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture.FurnitureType;
import de.hsrm.mi.swt.grundreisser.business.catalog.FurnitureGroup;
import de.hsrm.mi.swt.grundreisser.business.catalog.FurnitureToGroup;
import de.hsrm.mi.swt.grundreisser.business.factories.exceptions.CreateCatalogFurnitureException;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.CatalogFitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.CustomFitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.FitmentGroup;

/**
 * The implementation of FitmentFactory
 * 
 * @author nmuel002
 * 
 */
public class FitmentFactoryImpl implements FitmentFactory {

	private Catalog catalog;

	public FitmentFactoryImpl() {
		// this.catalog = ModelManagerImpl.getInstance().getCatalog();
	}

	@Override
	public Fitment createFitmentById(int id, Point pos) {

		Furniture furn = catalog.objectById(id);

		if (furn.getFurnitureType() == FurnitureType.CATALOG) {
			return createCatalogFitment((CatalogFurniture) furn, pos);
		}

		if (furn.getFurnitureType() == FurnitureType.CUSTOM) {
			return createCustomFitment((CustomFurniture) furn, pos);
		}

		return createFitmentGroup((FurnitureGroup) furn, pos);
	}

	/**
	 * Returns a created fitment from CatalogFurniture
	 * 
	 * @param furn
	 *            specified furniture
	 * @param pos
	 *            position where the object have been placed
	 * @return CatalogFitment
	 */
	private Fitment createCatalogFitment(CatalogFurniture furn, Point pos) {

		Dimension size = furn.getSize();
		int width = (int) size.getWidth();
		int height = (int) size.getHeight();

		return new CatalogFitment(pos, width, height, furn.getId(),
				furn.getLayer());
	}

	/**
	 * Returns a custom fitment which was created from CustomFurniture object
	 * 
	 * @param furn
	 *            custom furniture
	 * @param pos
	 *            position on the plan
	 * @return CustomFitment
	 */
	private Fitment createCustomFitment(CustomFurniture furn, Point pos) {

		return new CustomFitment(pos);
	}

	/**
	 * Returns a fitment group from furniture group
	 * 
	 * @param furn
	 *            furniture group
	 * @param pos
	 *            position on the plan
	 * @return FitmentGroup
	 */
	private Fitment createFitmentGroup(FurnitureGroup furn, Point pos) {

		List<Fitment> fitments = createFitmentGroupRecursive(furn);

		Fitment newFit = FitmentGroup.createFitmentGroup(fitments);
		newFit.move(pos);

		return newFit;
	}

	/**
	 * Helper method for building of fitment group from furniture group
	 * 
	 * @param group
	 *            furniture group
	 * @return a list of the fitments that represent objects in the furniture
	 *         group
	 */
	private List<Fitment> createFitmentGroupRecursive(Furniture group) {

		List<Fitment> groupList = new ArrayList<Fitment>();

		for (FurnitureToGroup ele : ((FurnitureGroup) group).getFurnitureList()) {

			Furniture actFurn = ele.getFurnitureObject();
			if (actFurn.getFurnitureType() != FurnitureType.GROUP) {
				Fitment fit = null;
				if (actFurn.getFurnitureType() == FurnitureType.CATALOG) {
					CatalogFurniture catFurn = (CatalogFurniture) actFurn;
					fit = createCatalogFitment(catFurn, ele.getPosition());
				} else {
					CustomFurniture custFurn = (CustomFurniture) actFurn;
					fit = createCustomFitment(custFurn, ele.getPosition());
				}

				groupList.add(fit);
			} else {
				List<Fitment> upperList = createFitmentGroupRecursive(actFurn);
				Fitment fitmGroup = FitmentGroup.createFitmentGroup(upperList);
				groupList.add(fitmGroup);
			}
		}

		return groupList;
	}

	@Override
	public Fitment createFitmentFromFurniture(Furniture furn, Point pos) {

		if (furn.getFurnitureType() == FurnitureType.CATALOG) {
			return createCatalogFitment((CatalogFurniture) furn, pos);
		}

		if (furn.getFurnitureType() == FurnitureType.CUSTOM) {
			return createCustomFitment((CustomFurniture) furn, pos);
		}

		return createFitmentGroup((FurnitureGroup) furn, pos);
	}

	@Override
	public Furniture createFurnitureFromFitment(Fitment fitment, String name,
			Category cat) throws Exception {

		if (fitment.getType() == FurnitureType.CUSTOM) {
			return createCustomFurniture((CustomFitment) fitment, name);
		}
		if (fitment.getType() == FurnitureType.GROUP) {
			return createFurnitureGroup((FitmentGroup) fitment, name);
		}

		throw new CreateCatalogFurnitureException(
				"CatalogFurniture cannot be created by user");
	}

	/**
	 * Returns a custom furniture which was created from custom fitment and have
	 * to be added to the catalog
	 * 
	 * @param fit
	 *            custom fitment
	 * @param name
	 *            furniture name
	 * @return custom furniture
	 */
	private Furniture createCustomFurniture(CustomFitment fit, String name) {

		int id = catalog.getNextID();
		Dimension size = new Dimension(fit.getRect().getWidth(), fit.getRect()
				.getHeight());

		return new CustomFurniture(id, name, fit.getLayer(), size);
	}

	/**
	 * Returns a furniture group created from the specified fitment group
	 * 
	 * @param group
	 *            fitment group
	 * @param name
	 *            furniture name
	 * @return furniture group
	 */
	private Furniture createFurnitureGroup(FitmentGroup group, String name) {

		List<FurnitureToGroup> furnitures = createFurnitureGroupRecursive(group);
		int id = catalog.getNextID();

		return new FurnitureGroup(id, name, furnitures);
	}

	/**
	 * Helper method to create furniture group
	 * 
	 * @param group
	 *            fitment group
	 * @return a list of furnitures with their local positions which belong to
	 *         one furniture group
	 */
	private List<FurnitureToGroup> createFurnitureGroupRecursive(Fitment group) {

		List<FurnitureToGroup> groupList = new ArrayList<FurnitureToGroup>();

		for (Fitment actFit : ((FitmentGroup) group).getFitments()) {

			if (actFit.getType() != FurnitureType.GROUP) {
				Furniture furn = null;

				if (actFit.getType() == FurnitureType.CUSTOM) {
					if (actFit.getFurnitureId() == 0) {
						furn = new CustomFurniture();
						((CustomFurniture) furn)
								.setLayer(((CustomFitment) actFit).getLayer());
						Dimension size = new Dimension(actFit.getRect()
								.getWidth(), actFit.getRect().getHeight());
						((CustomFurniture) furn).setSize(size);

					} else {
						furn = (CustomFurniture) catalog.objectById(
								actFit.getFurnitureId()).clone();
					}
				} else {
					furn = (CatalogFurniture) catalog.objectById(
							actFit.getFurnitureId()).clone();
				}

				FurnitureToGroup fg = new FurnitureToGroup(furn,
						actFit.getCenterLocation());
				groupList.add(fg);

			} else {
				List<FurnitureToGroup> upperList = createFurnitureGroupRecursive(actFit);

				Furniture furnGroup = null;
				if (actFit.getFurnitureId() == 0) {
					furnGroup = new FurnitureGroup(upperList);
				} else {
					furnGroup = (FurnitureGroup) catalog.objectById(
							actFit.getFurnitureId()).clone();
				}

				FurnitureToGroup fg = new FurnitureToGroup(furnGroup,
						actFit.getCenterLocation());
				groupList.add(fg);
			}
		}

		return groupList;
	}

}
