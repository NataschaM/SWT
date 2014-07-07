package de.hsrm.mi.swt.grundreisser.view.interior;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import de.hsrm.mi.swt.grundreisser.business.Observer;
import de.hsrm.mi.swt.grundreisser.business.floor.Observable;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Interior;
import de.hsrm.mi.swt.grundreisser.view.draw.MainView;
import de.hsrm.mi.swt.grundreisser.view.global.ViewImpl;
import de.hsrm.mi.swt.grundreisser.view.util.ViewFactory;

public class InteriorViewImpl extends ViewImpl implements InteriorView {

	private Interior model;
	private ViewFactory viewFactory;
	private List<FitmentView<?>> fitmentViews;

	/**
	 * Create an interior view
	 * 
	 * @param mainView
	 *            a main view
	 * @param model
	 *            a interior model
	 */
	public InteriorViewImpl(MainView mainView, ViewFactory viewFactory,
			Interior model) {
		super(mainView);
		this.model = model;
		this.viewFactory = viewFactory;

		setLayout(null);
		setOpaque(false);

		this.fitmentViews = new ArrayList<>();
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
		fitmentViews.clear();
		for (Fitment fitment : model.getFitments()) {
			FitmentView<?> view = viewFactory.createFitmentView(fitment);
			fitmentViews.add(view);
			add(view.getComponent());
		}
		invalidate();
		validate();
		repaint();
	}

	@Override
	public JComponent getComponent() {
		return this;
	}

}
