package de.hsrm.mi.swt.grundreisser.view.interior;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import de.hsrm.mi.swt.grundreisser.business.Observer;
import de.hsrm.mi.swt.grundreisser.business.floor.Observable;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.view.draw.MainView;
import de.hsrm.mi.swt.grundreisser.view.draw.SelectionDelegate;
import de.hsrm.mi.swt.grundreisser.view.draw.SelectionEventImpl;
import de.hsrm.mi.swt.grundreisser.view.exceptions.InvalidPositionException;
import de.hsrm.mi.swt.grundreisser.view.global.View;
import de.hsrm.mi.swt.grundreisser.view.global.ViewImpl;
import de.hsrm.mi.swt.grundreisser.view.util.PixelConverter;

/**
 * Default implemenation of a fitment view
 * 
 * @author Simon Seyer
 *
 */
public class FitmentViewImpl extends ViewImpl implements FitmentView<Fitment> {

	private Fitment model;
	private boolean selected;
	private SelectionDelegate selectionDelegate;
	private static final Color DEFAULT_COLOR = new Color(100, 150, 20);
	private static final Color SELECTION_COLOR = new Color(254, 150, 20);
	private static final Color WARN_COLOR = new Color(200, 0, 0);

	/**
	 * Create a fitment view
	 * 
	 * @param mainView
	 *            a main view
	 * @param model
	 *            a fitment model
	 */
	public FitmentViewImpl(MainView mainView, Fitment model) {
		super(mainView);
		this.model = model;

		final View<?> thisView = this;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectionDelegate != null) {
					selectionDelegate.viewSelected(new SelectionEventImpl(
							thisView, e));
				}
			}
		});
		model.addObserver(new Observer() {

			@Override
			public void update(Observable obs, Object obj) {
				updateView();
			}
		});
		updateView();
	}

	@Override
	public void updateView() {
		setBounds(getFrame());
		repaint();
	}

	@Override
	public Rectangle getFrame() {
		PixelConverter pc = mainView.getPixelConverter();
		return pc.getPixelForValue(model.getRect().getGeometryRectangle());
	}

	@Override
	public Fitment getModel() {
		return model;
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
		this.selected = selected;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (selected) {
			g.setColor(SELECTION_COLOR);
		} else {
			g.setColor(Color.WHITE);
		}
		g2.fillRect(0, 0, getWidth(), getHeight());

		if (!model.getWarnings().isEmpty()) {
			g.setColor(WARN_COLOR);
		} else {
			g.setColor(DEFAULT_COLOR);
		}
		g2.fillRect(4, 4, getWidth() - 8, getHeight() - 8);
	}

	@Override
	public JComponent getComponent() {
		return this;
	}

}
