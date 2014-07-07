package de.hsrm.mi.swt.grundreisser.business.floor.interior;

import java.awt.Point;

import de.hsrm.mi.swt.grundreisser.business.ModelManagerImpl;
import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture;
import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture.FurnitureType;

/**
 * This is a class for the simple fitment which was defined in catalog
 * 
 * @author nmuel002
 *
 */
public class CatalogFitment extends FitmentImpl {

	/**
	 * Catalog fitment can be only instanciated if the values of following
	 * parameters are specified
	 * 
	 * @param center
	 *            center position
	 * @param width
	 *            width
	 * @param height
	 *            height
	 * @param furnitureId
	 *            furnitureId
	 * @param layer
	 *            layer
	 */
	public CatalogFitment(Point center, int width, int height, int furnitureId,
			int layer) {
		super(center, width, height);
		this.furnitureId = furnitureId;
		this.layer = layer;
	}

	public Object clone() {
		Fitment newFitment = new CatalogFitment(this.center, this.width,
				this.height, this.furnitureId, this.layer);
		return newFitment;
	}

	public String toString() {
		return String
				.format("CatalogFitment [furniture id: %s, layer: %d, center: %s, width: %d, height: %d]",
						this.furnitureId, this.layer, this.center, this.width,
						this.height);
	}

	@Override
	public FurnitureType getType() {
		return FurnitureType.CATALOG;
	}

	@Override
	public String getName() {

		Furniture furn = ModelManagerImpl.getInstance().getCatalog()
				.objectById(this.furnitureId);
		return furn.getName();
	}

}
