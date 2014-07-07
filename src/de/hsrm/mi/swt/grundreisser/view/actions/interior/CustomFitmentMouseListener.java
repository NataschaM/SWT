package de.hsrm.mi.swt.grundreisser.view.actions.interior;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.command.interior.AddFitmentCommand;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.CustomFitment;
import de.hsrm.mi.swt.grundreisser.view.draw.DrawHandler;

/**
 * Shows a dialog to create and add a custom fitment, when the user clicked on
 * the draw pane.
 * 
 * @author Simon Seyer
 *
 */
public class CustomFitmentMouseListener extends MouseInputAdapter {

	private DrawHandler drawHandler;
	private Floor model;
	private Component parent;
	private ActionListener actionListener;

	/**
	 * Create a custom fitment mouse listener
	 * 
	 * @param drawHandler
	 *            a draw handler
	 * @param model
	 *            a floor model
	 */
	public CustomFitmentMouseListener(DrawHandler drawHandler, Floor model,
			Component parent, ActionListener actionListener) {
		this.drawHandler = drawHandler;
		this.model = model;
		this.parent = parent;
		this.actionListener = actionListener;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = drawHandler.getDrawPoint(e.getLocationOnScreen());
		p = drawHandler.getPixelConverter().getValueForPixel(p);

		CustomFitmentDialog dialog = new CustomFitmentDialog();
		int result = dialog.show(parent);
		if (result == JOptionPane.OK_OPTION) {
			CustomFitment customFitment = new CustomFitment(p,
					dialog.getWidth(), dialog.getHeight());
			customFitment.setLayer(dialog.getLayer());
			Command cmd = new AddFitmentCommand(model, customFitment);
			model.getCommandManager().execAndPush(cmd);
		}
		actionListener.actionPerformed(new ActionEvent(this, 0,
				"customFitmentMouseListener"));
	}
}
