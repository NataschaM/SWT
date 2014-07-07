/**
 * 
 */
package de.hsrm.mi.swt.grundreisser.view.groundplan;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import de.hsrm.mi.swt.grundreisser.business.Observer;
import de.hsrm.mi.swt.grundreisser.business.floor.Observable;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.GroundPlan;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.view.draw.MainView;
import de.hsrm.mi.swt.grundreisser.view.global.ViewImpl;
import de.hsrm.mi.swt.grundreisser.view.util.ViewFactory;

/**
 * Default implemenation of the GroundPlanView
 * 
 * @author Simon Seyer
 * 
 */
public class GroundPlanViewImpl extends ViewImpl implements GroundPlanView {

	private ViewFactory viewFactory;
	private GroundPlan model;
	private List<WallView<?>> wallViews;
	private boolean dimmed;

	/**
	 * Create a ground plan view
	 * 
	 * @param mainView
	 *            a main view
	 * @param viewFactory
	 *            a view factory
	 * @param model
	 *            a ground plan model
	 */
	public GroundPlanViewImpl(MainView mainView, ViewFactory viewFactory,
			GroundPlan model) {
		super(mainView);
		this.mainView = mainView;
		this.viewFactory = viewFactory;
		this.model = model;

		setLayout(null);
		setOpaque(false);

		this.wallViews = new ArrayList<>();
		model.addObserver(new Observer() {

			@Override
			public void update(Observable obs, Object obj) {
				// TODO: not performant
				updateView();
			}
		});
		updateView();
	}

	@Override
	public void updateView() {
		removeAll();
		wallViews.clear();
		for (Wall wall : this.model.getWalls()) {

			WallView<?> wallView = viewFactory.createWallView(wall);
			wallView.setDimmed(dimmed);
			wallViews.add(wallView);
			add(wallView.getComponent());
		}
		invalidate();
		validate();
		repaint();
	}

	@Override
	public JComponent getComponent() {
		return this;
	}

	@Override
	public void addDummyWallView(DummyWallView wallView) {
		add(wallView.getComponent());
	}

	@Override
	public void removeDummyWallView(DummyWallView wallView) {
		remove(wallView.getComponent());
		repaint();
	}

	@Override
	public void setDimmed(boolean dimmed) {
		this.dimmed = dimmed;
		for (WallView<?> wallView : wallViews) {
			wallView.setDimmed(dimmed);
		}
	}
}
