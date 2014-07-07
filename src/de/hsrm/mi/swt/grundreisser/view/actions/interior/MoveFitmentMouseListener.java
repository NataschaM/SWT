package de.hsrm.mi.swt.grundreisser.view.actions.interior;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.MouseInputAdapter;

import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.command.interior.MoveFitmentCommand;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.view.draw.DrawHandler;
import de.hsrm.mi.swt.grundreisser.view.draw.SelectionDelegate;
import de.hsrm.mi.swt.grundreisser.view.draw.SelectionEvent;
import de.hsrm.mi.swt.grundreisser.view.exceptions.InvalidPositionException;
import de.hsrm.mi.swt.grundreisser.view.interior.FitmentView;

/**
 * This handler is used to move fitments.
 * 
 * @author Simon Seyer
 * 
 */
public class MoveFitmentMouseListener extends MouseInputAdapter implements
		SelectionDelegate {

	private List<FitmentView<?>> selectedViews;
	private List<Point> relativeStartPoints;
	private DrawHandler drawHandler;
	private Floor model;

	/**
	 * Create a move fitment mouse listener
	 * 
	 * @param drawHandler
	 *            a draw handler
	 * @param model
	 *            a floor model
	 */
	public MoveFitmentMouseListener(DrawHandler drawHandler, Floor model) {
		this.drawHandler = drawHandler;
		this.model = model;
		selectedViews = new LinkedList<>();
	}

	@Override
	public void viewSelected(SelectionEvent selectionEvent) {
		FitmentView<?> view = (FitmentView<?>) selectionEvent.getSelectedView();
		selectedViews.add(view);
		view.getComponent().addMouseMotionListener(this);
		view.getComponent().addMouseListener(this);
	}

	@Override
	public void viewDeselected(SelectionEvent selectionEvent) {
		if (selectedViews.contains(selectionEvent.getSelectedView())) {
			FitmentView<?> view = (FitmentView<?>) selectionEvent
					.getSelectedView();
			view.getComponent().removeMouseMotionListener(this);
			view.getComponent().removeMouseListener(this);
			selectedViews.remove(view);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		relativeStartPoints = new LinkedList<>();
		for (FitmentView<?> fitmentView : selectedViews) {
			Point relativeStartPoint = drawHandler.getDrawPoint(e
					.getLocationOnScreen());
			Point viewLocation = fitmentView.getComponent().getLocation();
			relativeStartPoint.translate(-viewLocation.x, -viewLocation.y);
			relativeStartPoints.add(relativeStartPoint);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!selectedViews.isEmpty()) {
			try {
				Point p = drawHandler.getDrawPoint(e.getLocationOnScreen());
				if (p != null) {
					for (int i = 0; i < selectedViews.size(); i++) {
						Point pCopy = new Point(p);
						pCopy.translate(-relativeStartPoints.get(i).x,
								-relativeStartPoints.get(i).y);
						selectedViews.get(i).setPosition(pCopy);
					}
				}
			} catch (InvalidPositionException e1) {

			}
		}
	};

	@Override
	public void mouseReleased(MouseEvent e) {
		for (FitmentView<?> fitmentView : selectedViews) {
			Rectangle bounds = fitmentView.getComponent().getBounds();
			int centerX = bounds.x + bounds.width / 2;
			int centerY = bounds.y + bounds.height / 2;
			Point modelCenter = drawHandler.getPixelConverter()
					.getValueForPixel(new Point(centerX, centerY));
			Command cmd = new MoveFitmentCommand(fitmentView.getModel(),
					modelCenter);
			model.getCommandManager().execAndPush(cmd);
		}
	}

	/**
	 * Remove all listeners, that were added to the selected view
	 */
	public void reset() {
		for (FitmentView<?> fitmentView : selectedViews) {
			fitmentView.getComponent().removeMouseMotionListener(this);
			fitmentView.getComponent().removeMouseListener(this);
		}
		selectedViews.clear();
	}
}
