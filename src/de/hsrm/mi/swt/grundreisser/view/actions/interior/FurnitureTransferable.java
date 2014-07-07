package de.hsrm.mi.swt.grundreisser.view.actions.interior;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture;

/**
 * This transferable is used to transfer a furniture from the catalog list to
 * the draw pane by drag and drop.
 * 
 * @author Simon Seyer
 *
 */
public class FurnitureTransferable implements Transferable {

	public static final DataFlavor furnitureFlavor = new DataFlavor(
			Furniture.class, "A fitment object");
	private static DataFlavor[] supportedFlavors = { furnitureFlavor };
	private Furniture furniture;

	/**
	 * Create a transferable for a furntiure
	 * 
	 * @param furniture
	 *            the furniture to transfer
	 */
	public FurnitureTransferable(Furniture furniture) {
		this.furniture = furniture;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return supportedFlavors;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(furnitureFlavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (flavor.equals(furnitureFlavor)) {
			return furniture;
		}
		throw new UnsupportedFlavorException(flavor);
	}
}
