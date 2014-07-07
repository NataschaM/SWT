package de.hsrm.mi.swt.grundreisser.view.actions.interior;

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture;
import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.command.interior.AddFurnitureCommand;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.view.draw.DrawHandler;

/**
 * This class handles the drop event on the draw pane for furnitures.
 * 
 * @author Simon Seyer
 *
 */
public class FurnitureDropTargetListener extends DropTargetAdapter {

	private Floor model;
	private DrawHandler drawHandler;

	/**
	 * Create a furniture drop target listener
	 * 
	 * @param drawHandler
	 *            a draw handler
	 * @param model
	 *            a floor model
	 */
	public FurnitureDropTargetListener(DrawHandler drawHandler, Floor model) {
		this.drawHandler = drawHandler;
		this.model = model;
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		try {
			Transferable tr = dtde.getTransferable();
			Furniture furniture = (Furniture) tr
					.getTransferData(FurnitureTransferable.furnitureFlavor);
			if (dtde.isDataFlavorSupported(FurnitureTransferable.furnitureFlavor)) {
				dtde.acceptDrop(DnDConstants.ACTION_LINK);
				Point location = drawHandler.getPixelConverter()
						.getValueForPixel(dtde.getLocation());
				Command cmd = new AddFurnitureCommand(model, furniture,
						location);
				model.getCommandManager().execAndPush(cmd);

				dtde.dropComplete(true);
				return;
			}
			dtde.rejectDrop();
		} catch (Exception e) {
			e.printStackTrace();
			dtde.rejectDrop();
		}
	}
}
