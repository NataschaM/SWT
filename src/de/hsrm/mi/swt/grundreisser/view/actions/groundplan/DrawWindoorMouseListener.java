package de.hsrm.mi.swt.grundreisser.view.actions.groundplan;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import de.hsrm.mi.swt.grundreisser.business.command.wall.CommandManager;
import de.hsrm.mi.swt.grundreisser.business.command.windoor.AddWinDoorCommand;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Door;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WinDoor;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Window;
import de.hsrm.mi.swt.grundreisser.view.draw.DrawHandler;
import de.hsrm.mi.swt.grundreisser.view.draw.SelectionDelegate;
import de.hsrm.mi.swt.grundreisser.view.draw.SelectionEvent;
import de.hsrm.mi.swt.grundreisser.view.exceptions.InvalidPositionException;
import de.hsrm.mi.swt.grundreisser.view.global.ViewStateHolder;
import de.hsrm.mi.swt.grundreisser.view.groundplan.DummyWallView;

public class DrawWindoorMouseListener extends MouseInputAdapter implements
		SelectionDelegate {

	public static final int WINDOW = 0;
	public static final int DOOR = 1;
	private int type;

	private DrawHandler drawHandler;
	private Floor floor;
	private DummyWallView wallView;
	private Point p1;
	private MouseEvent processedMouseEvent;

	public DrawWindoorMouseListener(ViewStateHolder stateHolder, Floor floor,
			DrawHandler drawHandler, int type) {
		assert type >= 0 && type <= 1;

		this.drawHandler = drawHandler;
		this.floor = floor;
		this.type = type;
	}

	@Override
	public void viewSelected(SelectionEvent selectionEvent) {
		if (wallView == null) {
			// The click that finishes the windoor also shows up here after it
			// was processed by the mouseClicked method. When this is the case,
			// we have to ignore the click.
			if (processedMouseEvent != null
					&& processedMouseEvent.getWhen() == selectionEvent
							.getMouseEvent().getWhen()) {
				processedMouseEvent = null;
				return;
			}
			if (selectionEvent.getClickedView() instanceof DummyWallView) {
				wallView = (DummyWallView) selectionEvent.getClickedView();
				try {
					p1 = selectionEvent.getMouseEvent().getPoint();
					wallView.setDummyWindoorPoint1(p1);
				} catch (InvalidPositionException e) {

				}
			}
		}
	}

	@Override
	public void viewDeselected(SelectionEvent selectionEvent) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (wallView != null) {
			try {
				Point p = e.getLocationOnScreen();
				Point origin = wallView.getComponent().getLocationOnScreen();
				p.translate(-origin.x, -origin.y);
				wallView.setDummyWindoorPoint2(p);
			} catch (InvalidPositionException e1) {
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (wallView != null) {
			processedMouseEvent = e;

			Point p2 = e.getLocationOnScreen();
			Point origin = wallView.getComponent().getLocationOnScreen();
			p2.translate(-origin.x, -origin.y);

			int width;
			if (wallView.getModel().isHorizontal()) {
				width = Math.max(p1.x, p2.x) - Math.min(p1.x, p2.x);
			} else {
				width = Math.max(p1.y, p2.y) - Math.min(p1.y, p2.y);
			}
			if (width <= 0) {
				reset();
				return;
			}
			width = drawHandler.getPixelConverter().getValueForPixel(width);

			double pos;
			if (wallView.getModel().isHorizontal()) {
				pos = Math.min(p1.x, p2.x) / (double) wallView.getFrame().width;
			} else {
				pos = Math.min(p1.y, p2.y)
						/ (double) wallView.getFrame().height;
			}

			CommandManager cm = floor.getCommandManager();
			WinDoor windoor;
			if (type == WINDOW) {
				windoor = new Window(pos, width);
			} else {
				windoor = new Door(pos, width);
			}
			cm.execAndPush(new AddWinDoorCommand(wallView.getModel(), windoor));

			reset();
		}
	}

	private void reset() {
		this.wallView.clearWindoorDummy();
		this.wallView = null;
		this.p1 = null;
	}

}
