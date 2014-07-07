package de.hsrm.mi.swt.grundreisser.view.groundplan;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.OuterWall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WallImpl;
import de.hsrm.mi.swt.grundreisser.view.draw.MainView;
import de.hsrm.mi.swt.grundreisser.view.draw.SelectionDelegate;
import de.hsrm.mi.swt.grundreisser.view.draw.SelectionEvent;
import de.hsrm.mi.swt.grundreisser.view.exceptions.InvalidPositionException;
import de.hsrm.mi.swt.grundreisser.view.global.ViewImpl;
import de.hsrm.mi.swt.grundreisser.view.util.PixelConverter;
import de.hsrm.mi.swt.grundreisser.view.util.ViewFactory;

/**
 * This compoment displays an outer wall. It handles the wall views that are
 * part of this outer wall and selection on one of the wall views.
 * 
 * @author Simon Seyer
 *
 */
public class OuterWallView extends ViewImpl implements WallView<OuterWall>,
		SelectionDelegate {

	private OuterWall model;
	private ViewFactory viewFactory;
	private List<WallView<?>> wallViews;
	private SelectionDelegate selectionDelegate;

	/**
	 * Create an outer wall view
	 * 
	 * @param mainView
	 *            a main view
	 * @param viewFactory
	 *            a view factory
	 * @param model
	 *            a outer wall model
	 */
	public OuterWallView(MainView mainView, ViewFactory viewFactory,
			OuterWall model) {
		super(mainView);
		this.viewFactory = viewFactory;
		this.model = model;

		wallViews = new ArrayList<>();
		setLayout(null);
		updateView();
		setBounds(getFrame());
		setOpaque(false);
	}

	@Override
	public void updateView() {
		wallViews.clear();
		removeAll();
		for (WallImpl wall : model.getWalls()) {
			WallView<?> wallView = viewFactory.createWallView(wall);
			wallView.setSelectionDelegate(this);
			wallViews.add(wallView);
			add(wallView.getComponent());
		}
		invalidate();
		validate();
		repaint();
	}

	@Override
	public void setSelectionDelegate(SelectionDelegate selectionDelegate) {
		this.selectionDelegate = selectionDelegate;
	}

	@Override
	public Rectangle getFrame() {
		PixelConverter pc = mainView.getPixelConverter();
		return pc.getPixelForValue(model.getRect().getGeometryRectangle());
	}

	@Override
	public OuterWall getModel() {
		return model;
	}

	@Override
	public void setPosition(Point position) throws InvalidPositionException {
		throw new RuntimeException("Wall movement not supported");
	}

	@Override
	public JComponent getComponent() {
		return this;
	}

	@Override
	public void viewSelected(SelectionEvent selectionEvent) {
		selectionEvent.updateSelectedView(this);
		selectionDelegate.viewSelected(selectionEvent);
	}

	@Override
	public void viewDeselected(SelectionEvent selectionEvent) {
		selectionDelegate.viewDeselected(selectionEvent);
	}

	@Override
	public void setSelected(boolean selected) {
		for (WallView<?> wallView : wallViews) {
			wallView.setSelected(selected);
		}
	}

	@Override
	public void setDimmed(boolean dimmed) {
		for (WallView<?> wallView : wallViews) {
			wallView.setDimmed(dimmed);
		}
	}
}
