package de.hsrm.mi.swt.grundreisser.view.actions.groundplan;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import de.hsrm.mi.swt.grundreisser.business.command.wall.AddWallCommand;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WallImpl;
import de.hsrm.mi.swt.grundreisser.view.draw.DrawHandler;
import de.hsrm.mi.swt.grundreisser.view.exceptions.InvalidPositionException;
import de.hsrm.mi.swt.grundreisser.view.groundplan.DummyWallView;
import de.hsrm.mi.swt.grundreisser.view.groundplan.GroundPlanView;
import de.hsrm.mi.swt.grundreisser.view.util.PixelConverter;

/**
 * A action listener for the draw inner wall tool in the ground plan. It
 * responses to clicks the user does on the draw pane and shows live, how the
 * wall would be drawn.
 * 
 * @author Simon Seyer
 *
 */
public class DrawInnerWallMouseListener extends MouseInputAdapter {

	protected GroundPlanView groundPlanView;
	protected DrawHandler drawHandler;
	protected Floor floor;
	protected DummyWallView wallView;
	protected Point p1;
	protected Point p2;

	/**
	 * Create a draw inner wall listener
	 * 
	 * @param groundPlanView
	 *            a ground plan view
	 * @param floor
	 *            a floor model
	 * @param drawHandler
	 *            a draw handler
	 */
	public DrawInnerWallMouseListener(GroundPlanView groundPlanView,
			Floor floor, DrawHandler drawHandler) {
		this.groundPlanView = groundPlanView;
		this.drawHandler = drawHandler;
		this.floor = floor;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (wallView == null) {
			setP1(drawHandler.getDrawPoint(e.getLocationOnScreen()));
		} else {
			setP2(drawHandler.getDrawPoint(e.getLocationOnScreen()));
			PixelConverter pc = drawHandler.getPixelConverter();
			finishWall(new WallImpl(pc.getValueForPixel(p1),
					pc.getValueForPixel(p2)));
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (wallView != null) {
			setP2(e.getPoint());
		}
	}

	/**
	 * Set the first point, where the wall should begin.
	 * 
	 * @param p1
	 *            the point, the user clicked
	 */
	protected void setP1(Point p1) {
		this.p1 = p1;
		wallView = drawHandler.getViewFactory().createDummyWallView();
		try {
			wallView.setDummyPoint1(p1);
			groundPlanView.addDummyWallView(wallView);
		} catch (InvalidPositionException e1) {
			wallView = null;
		}
	}

	/**
	 * Set the second point to display the wall. It is drawn live between the
	 * first and the second point. The wall is automaticly normalized to be
	 * straight.
	 * 
	 * @param p2
	 *            the second point
	 */
	protected void setP2(Point p2) {
		this.p2 = p2;
		if (Math.abs(p2.x - p1.x) > Math.abs(p2.y - p1.y)) {
			// horizontal: remove y delta
			p2.y = p1.y;
		} else {
			// vertical: remove x delta
			p2.x = p1.x;
		}
		try {
			wallView.setDummyPoint2(p2);
		} catch (InvalidPositionException e) {

		}
	}

	/**
	 * Finishes the wall and sends it down to the model.
	 * 
	 * @param wall
	 *            the drawn wall
	 */
	protected void finishWall(WallImpl wall) {
		groundPlanView.removeDummyWallView(wallView);
		floor.getCommandManager().execAndPush(new AddWallCommand(floor, wall));
		reset();
	}

	/**
	 * Resets the state of the listener to start with drawing again.
	 */
	protected void reset() {
		wallView = null;
		p1 = null;
		p2 = null;
	}
}
