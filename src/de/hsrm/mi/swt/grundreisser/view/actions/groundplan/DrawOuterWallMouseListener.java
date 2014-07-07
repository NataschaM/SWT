package de.hsrm.mi.swt.grundreisser.view.actions.groundplan;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.command.wall.AddWallCommand;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.OuterWall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WallImpl;
import de.hsrm.mi.swt.grundreisser.view.draw.DrawHandler;
import de.hsrm.mi.swt.grundreisser.view.groundplan.DummyWallView;
import de.hsrm.mi.swt.grundreisser.view.groundplan.GroundPlanView;

/**
 * A action listener for the draw outer wall tool in the ground plan. It
 * responses to clicks the user does on the draw pane and shows live, how the
 * wall would be drawn. It uses the DrawInnerWallMouseListener to handle the
 * drawing of the wall parts.
 * 
 * @author Simon Seyer
 * @see DrawInnerWallMouseListener
 */
public class DrawOuterWallMouseListener extends DrawInnerWallMouseListener {

	private List<WallImpl> walls;
	private List<DummyWallView> wallViews;

	/**
	 * Create a draw outer wall listener
	 * 
	 * @param groundPlanView
	 *            a ground plan view
	 * @param floor
	 *            a floor model
	 * @param drawHandler
	 *            a draw handler
	 */
	public DrawOuterWallMouseListener(GroundPlanView groundPlanView,
			Floor floor, DrawHandler drawHandler) {
		super(groundPlanView, floor, drawHandler);
		reset();
	}

	@Override
	protected void finishWall(WallImpl wall) {
		walls.add(wall);
		wallViews.add(wallView);
		// A minimum of four wall is needed to create a closed outer wall
		if (walls.size() > 3) {
			if (wall.getRect().intersects(walls.get(0).getRect())) {
				// Remove all dummy views
				for (DummyWallView dummyWallView : wallViews) {
					groundPlanView.removeDummyWallView(dummyWallView);
				}
				floor.getCommandManager().execAndPush(
						new AddWallCommand(floor, OuterWall
								.createOuterWall(walls)));
				reset();
				return;
			}
		}
		Point p2 = this.p2;
		super.reset();
		// Set the first point of the next wall to the second point of the
		// previous wall
		setP1(p2);
	}

	@Override
	protected void reset() {
		super.reset();
		walls = new ArrayList<>();
		wallViews = new ArrayList<>();
	}
}
