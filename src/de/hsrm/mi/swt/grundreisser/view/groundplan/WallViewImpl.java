package de.hsrm.mi.swt.grundreisser.view.groundplan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import de.hsrm.mi.swt.grundreisser.business.Observer;
import de.hsrm.mi.swt.grundreisser.business.floor.Observable;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.WinDoor;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Window;
import de.hsrm.mi.swt.grundreisser.view.draw.MainView;
import de.hsrm.mi.swt.grundreisser.view.draw.SelectionDelegate;
import de.hsrm.mi.swt.grundreisser.view.draw.SelectionEventImpl;
import de.hsrm.mi.swt.grundreisser.view.exceptions.InvalidPositionException;
import de.hsrm.mi.swt.grundreisser.view.global.View;
import de.hsrm.mi.swt.grundreisser.view.global.ViewImpl;
import de.hsrm.mi.swt.grundreisser.view.util.PixelConverter;

/**
 * 
 * Default implementation of a wall view
 * 
 * @author Simon Seyer
 * 
 */
public class WallViewImpl extends ViewImpl implements DummyWallView, Observer {

	private Wall model;
	private SelectionDelegate selectionDelegate;
	private boolean selected;
	private boolean dimmed;
	private List<ColoredRectangle> windoorViews;
	private static final Color BG_COLOR = new Color(120, 120, 120);
	private static final Color SELECT_COLOR = new Color(254, 150, 20);;
	private static final Color WARN_COLOR = new Color(200, 0, 0);
	private static final Color WINDOW_COLOR = new Color(123, 188, 232);
	private static final Color DOOR_COLOR = new Color(205, 133, 63);
	private static final Color WINDOOR_COLOR = new Color(200, 200, 200);
	private static final int WINDOOR_WIDTH = 2;
	private static final int DIMMMED_ALPHA = 150;

	/**
	 * Create a wall view without a model. This should only be used by the view
	 * to display a dummy wall while drawing.
	 * 
	 * @param mainView
	 *            a main view
	 */
	public WallViewImpl(MainView mainView) {
		super(mainView);
		setBackground(BG_COLOR);
		renderWindoors();

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
	}

	/**
	 * Create a wall
	 * 
	 * @param mainView
	 *            a main view
	 * @param model
	 *            a wall model
	 */
	public WallViewImpl(MainView mainView, Wall model) {
		this(mainView);
		this.model = model;
		this.model.addObserver(this);
		updateView();
		renderWindoors();
	}

	@Override
	public void updateView() {
		setBounds(getFrame());
		renderWindoors();
		updateColor();
	}

	@Override
	public Rectangle getFrame() {
		PixelConverter pixelConverter = mainView.getPixelConverter();
		return pixelConverter.getPixelForValue(model.getRect()
				.getGeometryRectangle());
	}

	@Override
	public Wall getModel() {
		return this.model;
	}

	@Override
	public void setPosition(Point position) throws InvalidPositionException {
		throw new RuntimeException("Wall movement not supported");
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		for (ColoredRectangle colorRect : windoorViews) {
			Color color = colorRect.color;
			if (dimmed) {
				color = new Color(color.getRed(), color.getGreen(),
						color.getBlue(), DIMMMED_ALPHA);
			}
			g2.setColor(color);
			g2.fill(colorRect.rect);
		}
	}

	/**
	 * Calculates the rectangles for the windoors, so they don't have to be
	 * recalculated each draw cycle.
	 */
	private void renderWindoors() {
		windoorViews = new ArrayList<>();
		if (model != null) {
			PixelConverter pc = mainView.getPixelConverter();
			for (WinDoor windoor : model.getWinDoors()) {
				Rectangle rect;
				if (model.isHorizontal()) {
					int x = (int) (getWidth() * windoor.getPosition());
					int width = pc.getPixelForValue(windoor.getWidth());
					rect = new Rectangle(x, WINDOOR_WIDTH / 2, width,
							getHeight() - WINDOOR_WIDTH);
				} else {
					int y = (int) (getHeight() * windoor.getPosition());
					int height = pc.getPixelForValue(windoor.getWidth());
					rect = new Rectangle(WINDOOR_WIDTH / 2, y, getWidth()
							- WINDOOR_WIDTH, height);
				}

				Color color;
				if (windoor instanceof Window) {
					color = WINDOW_COLOR;
				} else {
					color = DOOR_COLOR;
				}
				windoorViews.add(new ColoredRectangle(rect, color));
			}
		}
		repaint();
	}

	private Point dummyWindoorPoint1;
	private Point dummyWindoorPoint2;
	private Rectangle dummyWindoorRect;

	@Override
	public void setDummyWindoorPoint1(Point p1) throws InvalidPositionException {
		dummyWindoorPoint1 = p1;
		updateDummyWindoor();
	}

	@Override
	public void setDummyWindoorPoint2(Point p2) throws InvalidPositionException {
		dummyWindoorPoint2 = p2;
		updateDummyWindoor();
	}

	/**
	 * Updates the location of the dummy windoor based on the two dummy windoor
	 * points.
	 */
	private void updateDummyWindoor() {
		if (dummyWindoorRect == null) {
			dummyWindoorRect = new Rectangle();
			windoorViews.add(new ColoredRectangle(dummyWindoorRect,
					WINDOOR_COLOR));
		}
		dummyWindoorRect.setBounds(0, 0, 0, 0);
		if (dummyWindoorPoint1 != null && dummyWindoorPoint2 != null) {
			if (model.isHorizontal()) {
				dummyWindoorRect.x = Math.min(dummyWindoorPoint1.x,
						dummyWindoorPoint2.x);
				dummyWindoorRect.y = WINDOOR_WIDTH / 2;
				dummyWindoorRect.width = Math.max(dummyWindoorPoint1.x,
						dummyWindoorPoint2.x) - dummyWindoorRect.x;
				dummyWindoorRect.height = getHeight() - WINDOOR_WIDTH;
			} else {
				dummyWindoorRect.x = WINDOOR_WIDTH / 2;
				dummyWindoorRect.y = Math.min(dummyWindoorPoint1.y,
						dummyWindoorPoint2.y);
				dummyWindoorRect.width = getWidth() - WINDOOR_WIDTH;
				dummyWindoorRect.height = Math.max(dummyWindoorPoint1.y,
						dummyWindoorPoint2.y) - dummyWindoorRect.y;
			}
		}
		repaint();
	}

	@Override
	public void clearWindoorDummy() {
		if (dummyWindoorRect != null) {
			windoorViews.remove(dummyWindoorRect);
			dummyWindoorRect = null;
			dummyWindoorPoint1 = null;
			dummyWindoorPoint2 = null;
		}
		repaint();
	}

	@Override
	public JComponent getComponent() {
		return this;
	}

	@Override
	public void update(Observable obs, Object obj) {
		updateView();
	}

	private Point dummyPoint1;
	private Point dummyPoint2;
	private static final int DUMMY_WIDTH = 2;

	@Override
	public void setDummyPoint1(Point point) throws InvalidPositionException {
		dummyPoint1 = point;
		dummyPoint2 = point;
		updateDummyLocation();
	}

	@Override
	public void setDummyPoint2(Point point) throws InvalidPositionException {
		dummyPoint2 = point;
		updateDummyLocation();
	}

	/**
	 * Update the location and the size of the rect to adapt changes of the
	 * dummy points. The position of the dummy is normalized, so that wall is
	 * straight.
	 */
	private void updateDummyLocation() {
		Point p1 = new Point(Math.min(dummyPoint1.x, dummyPoint2.x), Math.min(
				dummyPoint1.y, dummyPoint2.y));
		Point p2 = new Point(Math.max(dummyPoint1.x, dummyPoint2.x), Math.max(
				dummyPoint1.y, dummyPoint2.y));

		if (Math.abs(p2.x - p1.x) > Math.abs(p2.y - p1.y)) {
			p1.y -= DUMMY_WIDTH / 2;
			p2.y += DUMMY_WIDTH / 2;
		} else {
			p1.x -= DUMMY_WIDTH / 2;
			p2.x += DUMMY_WIDTH / 2;
		}
		setLocation(p1.x, p1.y);
		// calculate delta's for width and height
		setSize(p2.x - p1.x, p2.y - p1.y);
	}

	@Override
	public void setSelectionDelegate(SelectionDelegate selectionDelegate) {
		this.selectionDelegate = selectionDelegate;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
		updateColor();
	}

	@Override
	public void setDimmed(boolean dimmed) {
		this.dimmed = dimmed;
		updateColor();
	}

	/**
	 * Updates the backround color based on selection and dim state
	 */
	private void updateColor() {
		Color color;
		if (selected) {
			color = SELECT_COLOR;
		} else if (model != null && !model.getWarnings().isEmpty()) {
			color = WARN_COLOR;
		} else {
			color = BG_COLOR;
		}
		if (dimmed) {
			color = new Color(color.getRed(), color.getGreen(),
					color.getBlue(), DIMMMED_ALPHA);
		}
		setBackground(color);
		repaint();
	}

	/**
	 * This class is used to store prerendered windoors.
	 * 
	 * @author Simon Seyer
	 *
	 */
	private class ColoredRectangle {
		private Rectangle rect;
		private Color color;

		/**
		 * Create a colored rectanle
		 * 
		 * @param rect
		 *            the rectangle
		 * @param color
		 *            the color
		 */
		public ColoredRectangle(Rectangle rect, Color color) {
			super();
			this.rect = rect;
			this.color = color;
		}

	}
}
