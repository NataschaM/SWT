package de.hsrm.mi.swt.grundreisser.view.draw;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.TooManyListenersException;

import javax.swing.JLayeredPane;
import javax.swing.event.MouseInputListener;

import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.view.exceptions.InvalidPositionException;
import de.hsrm.mi.swt.grundreisser.view.global.View;
import de.hsrm.mi.swt.grundreisser.view.global.ViewState;
import de.hsrm.mi.swt.grundreisser.view.global.ViewStateHolder;
import de.hsrm.mi.swt.grundreisser.view.groundplan.GroundPlanView;
import de.hsrm.mi.swt.grundreisser.view.groundplan.GroundPlanViewImpl;
import de.hsrm.mi.swt.grundreisser.view.groundplan.WallView;
import de.hsrm.mi.swt.grundreisser.view.interior.FitmentView;
import de.hsrm.mi.swt.grundreisser.view.interior.InteriorView;
import de.hsrm.mi.swt.grundreisser.view.interior.InteriorViewImpl;
import de.hsrm.mi.swt.grundreisser.view.util.PixelConverter;
import de.hsrm.mi.swt.grundreisser.view.util.PixelConverterImpl;
import de.hsrm.mi.swt.grundreisser.view.util.ViewFactory;
import de.hsrm.mi.swt.grundreisser.view.util.ViewFactoryImpl;

/**
 * 
 * The pane that holds the complete view hierachy and handles the drawing.
 * 
 * @author Simon Seyer
 * 
 */
public class DrawPane extends JLayeredPane implements MainView, DrawHandler,
		AWTEventListener, SelectionDelegate {

	/**
	 * Size of the draw pane in model value
	 */
	private static final int PANEL_SIZE = 20000;
	private static final int ZOOM_STEP = 2;

	private PixelConverter pixelConverter;
	private ViewFactory viewFactory;
	private BackgroundPane backgroundPane;
	private LegendPane legendPane;
	private GroundPlanView groundPlanView;
	private InteriorView interiorView;
	private ViewStateHolder stateHolder;
	private MouseInputListener externalMouseListener;
	private SelectionDelegate externalSelectionDelegate;
	private MouseListener deselectionMouseListener;
	private Point offset;
	private DropTarget dropTarget;
	private DropTargetListener dropTargetListener;

	/**
	 * Create a draw pane
	 * 
	 * @param stateHolder
	 *            a state holder
	 * @param model
	 *            a floor model
	 */
	public DrawPane(final ViewStateHolder stateHolder, Floor model) {
		this.stateHolder = stateHolder;
		this.pixelConverter = new PixelConverterImpl();

		this.viewFactory = new ViewFactoryImpl(this);

		this.interiorView = new InteriorViewImpl(this, viewFactory,
				model.getInterior());
		add(interiorView.getComponent());

		this.groundPlanView = new GroundPlanViewImpl(this, viewFactory,
				model.getGroundPlan());

		add(groundPlanView.getComponent());

		this.backgroundPane = new BackgroundPane(pixelConverter);
		add(this.backgroundPane);
		this.legendPane = new LegendPane(pixelConverter);

		// Update the size of the sub panels when the DrawPane size changes
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resize();
			}
		});
		this.stateHolder
				.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (evt.getPropertyName().equals("viewState")) {
							updateLayerVisiblity();
						}
					}
				});
		updateLayerVisiblity();
		initDeselectionListener();

		dropTarget = new DropTarget(this, DnDConstants.ACTION_LINK, null, true,
				null);

		Toolkit.getDefaultToolkit().addAWTEventListener(this,
				AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);

		updateView();
	}

	@Override
	public void validate(Rectangle2D rect) throws InvalidPositionException {
		if (!getBounds().contains(rect)) {
			throw new InvalidPositionException();
		}
	}

	@Override
	public PixelConverter getPixelConverter() {
		return this.pixelConverter;
	}

	@Override
	public void setMouseListener(MouseInputListener mouseListener) {
		this.externalMouseListener = mouseListener;
	}

	@Override
	public GroundPlanView getGroundPlanView() {
		return groundPlanView;
	}

	@Override
	public InteriorView getInteriorView() {
		return interiorView;
	}

	@Override
	public ViewFactory getViewFactory() {
		return viewFactory;
	}

	/**
	 * Get a pane that displays a legend
	 * 
	 * @return a legend pane
	 */
	public LegendPane getLegendPane() {
		return legendPane;
	}

	private void updateView() {
		int size = pixelConverter.getPixelForValue(PANEL_SIZE);
		setPreferredSize(new Dimension(size, size));
		groundPlanView.updateView();
		interiorView.updateView();
		backgroundPane.repaint();
		legendPane.updateView();
	}

	/**
	 * Resizes all subviews to the size of the draw pane
	 */
	private void resize() {
		for (int i = 0; i < getComponentCount(); i++) {
			getComponent(i).setSize(getSize());
		}
	}

	@Override
	public SelectionDelegate getSelectionDelegate() {
		return this;
	}

	@Override
	public void viewSelected(SelectionEvent selectionEvent) {
		ViewState state = stateHolder.getViewState();
		if (state.equals(ViewState.INTERIOR_SELECT)
				&& selectionEvent.getClickedView() instanceof WallView<?>) {
			return;
		}
		if ((state.equals(ViewState.GROUND_PLAN_SELECT) || state
				.equals(ViewState.GROUND_PLAN_DRAW))
				&& selectionEvent.getClickedView() instanceof FitmentView) {
			return;
		}
		if (state.equals(ViewState.GROUND_PLAN_SELECT)
				|| state.equals(ViewState.INTERIOR_SELECT)) {
			View<?> selectedView = selectionEvent.getSelectedView();
			if (!selectionEvent.getMouseEvent().isShiftDown()) {
				if (stateHolder.getSelectedViews().contains(selectedView)) {
					if (stateHolder.getSelectedViews().size() > 1) {
						// More than one view is selected and the user clicked
						// on one of the selected views -> only select the
						// clicked view
						for (View<?> view : stateHolder.getSelectedViews()) {
							if (view != selectedView) {
								viewDeselected(new SelectionEventImpl(view,
										selectionEvent.getMouseEvent()));
							}
						}
					} else {
						// User clicked on the selected view -> deselect the
						// view
						viewDeselected(new SelectionEventImpl(selectedView,
								selectionEvent.getMouseEvent()));
					}
				} else {
					// User clicked on an unselected view -> deselect all views
					// that were selected before and select the clicked view
					for (View<?> view : stateHolder.getSelectedViews()) {
						viewDeselected(new SelectionEventImpl(view,
								selectionEvent.getMouseEvent()));
					}
					stateHolder.addSelectedView(selectedView);
				}
			} else {
				if (stateHolder.getSelectedViews().contains(selectedView)) {
					// User shift-clicked on a already selected view -> deselect
					// this view
					viewDeselected(new SelectionEventImpl(selectedView,
							selectionEvent.getMouseEvent()));
				} else {
					// User shift-clicked on an unselected view -> select this
					// view
					stateHolder.addSelectedView(selectedView);
				}
			}

		}
		if (externalSelectionDelegate != null) {
			externalSelectionDelegate.viewSelected(selectionEvent);
		}
	}

	@Override
	public void viewDeselected(SelectionEvent selectionEvent) {
		stateHolder.removeSelectedView(selectionEvent.getSelectedView());
		if (externalSelectionDelegate != null) {
			externalSelectionDelegate.viewDeselected(selectionEvent);
		}
	}

	/**
	 * Initializes the listener to deselect views
	 */
	private void initDeselectionListener() {
		this.deselectionMouseListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (View<?> view : stateHolder.getSelectedViews()) {
					viewDeselected(new SelectionEventImpl(view, e));
				}
			}
		};
		addMouseListener(this.deselectionMouseListener);
	}

	@Override
	public void setSelectionDelegate(SelectionDelegate selectionDelegate) {
		this.externalSelectionDelegate = selectionDelegate;
	}

	@Override
	public void eventDispatched(AWTEvent event) {
		// A global listener is used, because we also want get events when
		// another component, for example a wall view, is laying over ther draw
		// pane.
		if (externalMouseListener != null) {
			MouseEvent me = (MouseEvent) event;
			Point screenLocation = me.getLocationOnScreen();
			Point relativeLocation = getDrawPoint(screenLocation);
			if (relativeLocation == null) {
				return;
			}

			// Create a mouse event in the draw pane
			me = new MouseEvent(this, me.getID(), me.getWhen(),
					me.getModifiers(), relativeLocation.x, relativeLocation.y,
					screenLocation.x, screenLocation.y, me.getClickCount(),
					me.isPopupTrigger(), me.getButton());

			// call the appropriate mouse listener method

			switch (me.getID()) {
			case MouseEvent.MOUSE_CLICKED:
				externalMouseListener.mouseClicked(me);
				break;
			case MouseEvent.MOUSE_PRESSED:
				externalMouseListener.mousePressed(me);
				break;
			case MouseEvent.MOUSE_RELEASED:
				externalMouseListener.mouseReleased(me);
				break;
			case MouseEvent.MOUSE_MOVED:
				externalMouseListener.mouseMoved(me);
				break;
			case MouseEvent.MOUSE_ENTERED:
				externalMouseListener.mouseEntered(me);
				break;
			case MouseEvent.MOUSE_EXITED:
				externalMouseListener.mouseExited(me);
				break;
			case MouseEvent.MOUSE_DRAGGED:
				externalMouseListener.mouseDragged(me);
				break;
			}
		}
	}

	/**
	 * Set the scroll offset of the view
	 * 
	 * @param offset
	 *            the scroll offset
	 */
	public void setOffset(Point offset) {
		this.offset = offset;
	}

	@Override
	public Point getDrawPoint(Point absolutePoint) {
		// Get a point in the draw pane
		Point relativeLocation = (Point) absolutePoint.clone();
		Point location = getParent().getLocationOnScreen();
		relativeLocation.translate(-location.x, -location.y);

		// Check if screen lays in the draw pane
		if (relativeLocation.x < 0 || relativeLocation.y < 0
				|| relativeLocation.x > getParent().getWidth()
				|| relativeLocation.y > getParent().getHeight()) {
			return null;
		}

		relativeLocation.translate(offset.x, offset.y);

		return relativeLocation;
	}

	/**
	 * Updates the visiblity of ground plan view and interior view
	 */
	private void updateLayerVisiblity() {
		boolean visible = stateHolder.getViewState() == ViewState.INTERIOR_SELECT;
		interiorView.getComponent().setVisible(visible);
		groundPlanView.setDimmed(visible);
	}

	@Override
	public void setDropTargetListener(DropTargetListener listener)
			throws TooManyListenersException {
		if (dropTargetListener != null) {
			dropTarget.removeDropTargetListener(dropTargetListener);
		}
		if (listener != null) {
			dropTarget.addDropTargetListener(listener);
			dropTargetListener = listener;
		}
	}

	@Override
	public void zoomIn() {
		pixelConverter.setScaleFactor(pixelConverter.getScaleFactor()
				+ ZOOM_STEP);
		updateView();
	}

	@Override
	public void zoomOut() {
		pixelConverter.setScaleFactor(pixelConverter.getScaleFactor()
				- ZOOM_STEP);
		updateView();
	}

}
