package de.hsrm.mi.swt.grundreisser.view.actions.interior;

import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import de.hsrm.mi.swt.grundreisser.view.actions.interior.CatalogActionPanel.FurnitureItem;

/**
 * A transfer handler to transfer a furniture from a JList of Furnitures.
 * 
 * @author Simon Seyer
 *
 */
public class FurnitureTransferHandler extends TransferHandler {

	@Override
	protected Transferable createTransferable(JComponent c) {
		JList<?> list = (JList<?>) c;
		FurnitureItem furnitureItem = (FurnitureItem) list.getSelectedValue();
		return new FurnitureTransferable(furnitureItem.getValue());
	}

	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.LINK;
	}
}
