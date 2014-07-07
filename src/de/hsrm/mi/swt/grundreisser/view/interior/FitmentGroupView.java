package de.hsrm.mi.swt.grundreisser.view.interior;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import de.hsrm.mi.swt.grundreisser.business.Observer;
import de.hsrm.mi.swt.grundreisser.business.floor.Observable;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.FitmentGroup;
import de.hsrm.mi.swt.grundreisser.view.draw.MainView;
import de.hsrm.mi.swt.grundreisser.view.draw.SelectionDelegate;
import de.hsrm.mi.swt.grundreisser.view.draw.SelectionEvent;
import de.hsrm.mi.swt.grundreisser.view.exceptions.InvalidPositionException;
import de.hsrm.mi.swt.grundreisser.view.global.ViewImpl;
import de.hsrm.mi.swt.grundreisser.view.util.PixelConverter;
import de.hsrm.mi.swt.grundreisser.view.util.ViewFactory;

/**
 * This view displays a group of fitment views and allows to interact with them
 * as one object.
 * 
 * @author Simon Seyer
 *
 */
public class FitmentGroupView extends ViewImpl implements
		FitmentView<FitmentGroup>, SelectionDelegate {

	private FitmentGroup model;
	private ViewFactory viewFactory;
	private List<FitmentView<?>> fitmentViews;
	private SelectionDelegate selectionDelegate;

	public FitmentGroupView(MainView mainView, FitmentGroup model,
			ViewFactory viewFactory) {
		super(mainView);
		this.model = model;
		this.viewFactory = viewFactory;

		fitmentViews = new ArrayList<>();
		setLayout(null);
		setBounds(getFrame());
		setOpaque(false);
		updateView();

		model.addObserver(new Observer() {

			@Override
			public void update(Observable obs, Object obj) {
				setBounds(getFrame());
			}
		});
	}

	@Override
	public Rectangle getFrame() {
		PixelConverter pc = mainView.getPixelConverter();
		return pc.getPixelForValue(model.getRect().getGeometryRectangle());
	}

	@Override
	public void setPosition(Point position) throws InvalidPositionException {
		Point oldP = getLocation();
		setLocation(position);
		try {
			mainView.validate(getBounds());
		} catch (InvalidPositionException e) {
			setLocation(oldP);
			throw e;
		}
	}

	@Override
	public void setSelectionDelegate(SelectionDelegate selectionDelegate) {
		this.selectionDelegate = selectionDelegate;

	}

	@Override
	public void setSelected(boolean selected) {
		for (FitmentView<?> view : fitmentViews) {
			view.setSelected(selected);
		}
	}

	@Override
	public void updateView() {
		setBounds(getFrame());
		fitmentViews.clear();
		removeAll();
		for (Fitment fitment : model.getFitments()) {
			FitmentView<?> view = viewFactory.createFitmentView(fitment);
			view.setSelectionDelegate(this);
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

	@Override
	public FitmentGroup getModel() {
		return model;
	}

	@Override
	public void viewSelected(SelectionEvent selectionEvent) {
		selectionEvent.updateSelectedView(this);
		selectionDelegate.viewSelected(selectionEvent);
	}

	@Override
	public void viewDeselected(SelectionEvent selectionEvent) {
		selectionEvent.updateSelectedView(this);
		selectionDelegate.viewDeselected(selectionEvent);
	}

	@Override
	public synchronized void addMouseListener(MouseListener l) {
		for (FitmentView<?> fitmentView : fitmentViews) {
			fitmentView.getComponent().addMouseListener(l);
		}
	}

	@Override
	public synchronized void addMouseMotionListener(MouseMotionListener l) {
		for (FitmentView<?> fitmentView : fitmentViews) {
			fitmentView.getComponent().addMouseMotionListener(l);
		}
	}

	@Override
	public synchronized void removeMouseListener(MouseListener l) {
		for (FitmentView<?> fitmentView : fitmentViews) {
			fitmentView.getComponent().removeMouseListener(l);
		}
	}

	@Override
	public synchronized void removeMouseMotionListener(MouseMotionListener l) {
		for (FitmentView<?> fitmentView : fitmentViews) {
			fitmentView.getComponent().removeMouseMotionListener(l);
		}
	}

}
