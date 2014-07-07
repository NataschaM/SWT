package de.hsrm.mi.swt.grundreisser.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.view.actions.MainActionPanel;
import de.hsrm.mi.swt.grundreisser.view.draw.DrawPane;
import de.hsrm.mi.swt.grundreisser.view.draw.LegendPane;
import de.hsrm.mi.swt.grundreisser.view.global.ViewStateHolder;
import de.hsrm.mi.swt.grundreisser.view.global.ViewStateHolderImpl;

/**
 * The view to display one floor. This view consits of two view, the draw pane
 * and the action panel.
 * 
 * @author Simon Seyer
 * 
 */
public class FloorView extends JLayeredPane implements AdjustmentListener {

	private static final int SCROLL_SPEED = 4;

	private final DrawPane drawPane;
	private final JScrollPane scrollDrawPane;
	private final MainActionPanel actionPanel;
	private final JPanel contentPanel;
	private final LegendPane legendPane;

	/**
	 * Create a foor view
	 * 
	 * @param floor
	 *            a floor model
	 */
	public FloorView(Floor floor) {
		ViewStateHolder stateHolder = new ViewStateHolderImpl();

		contentPanel = new JPanel(new BorderLayout());

		this.drawPane = new DrawPane(stateHolder, floor);
		this.scrollDrawPane = new JScrollPane(this.drawPane);
		scrollDrawPane.setAutoscrolls(true); // TODO
		scrollDrawPane.getHorizontalScrollBar().setUnitIncrement(SCROLL_SPEED);
		scrollDrawPane.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
		scrollDrawPane.getHorizontalScrollBar().addAdjustmentListener(this);
		scrollDrawPane.getVerticalScrollBar().addAdjustmentListener(this);

		this.actionPanel = new MainActionPanel(drawPane, stateHolder, floor);
		this.actionPanel.setPreferredSize(new Dimension(200, 0));

		contentPanel.add(this.actionPanel, BorderLayout.EAST);
		contentPanel.add(scrollDrawPane, BorderLayout.CENTER);

		this.legendPane = drawPane.getLegendPane();
		add(legendPane, 100, 0);
		add(contentPanel, 1, 0);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateViewPosition();
			}
		});
		updateViewPosition();
	}

	/**
	 * Update the size and location of the sub views
	 */
	private void updateViewPosition() {
		contentPanel.setSize(getSize());
		legendPane.setLocation(10, getHeight() - legendPane.getHeight() - 22);
		invalidate();
		validate();
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		int x = scrollDrawPane.getHorizontalScrollBar().getValue();
		int y = scrollDrawPane.getVerticalScrollBar().getValue();
		drawPane.setOffset(new Point(x, y));
	}
}
