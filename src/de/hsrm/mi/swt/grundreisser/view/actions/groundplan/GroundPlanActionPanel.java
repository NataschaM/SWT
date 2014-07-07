package de.hsrm.mi.swt.grundreisser.view.actions.groundplan;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.view.actions.ActionPanel;
import de.hsrm.mi.swt.grundreisser.view.draw.DrawHandler;
import de.hsrm.mi.swt.grundreisser.view.global.ViewStateHolder;

/**
 * This panel is a container for the ConstructionActionPanel and the
 * WallSelectionActionPanel. It swaps the two panel based on the selected views
 * of the ViewStateHolder.
 * 
 * @author Simon Seyer
 * @see ConstructionActionPanel
 * @see WallSelectionActionPanel
 */
public class GroundPlanActionPanel extends JPanel implements
		PropertyChangeListener, ActionPanel {

	private static String CONSTRUCTION_PANEL = "CONSTRUCTION_PANEL";
	private static String SELECTION_PANEL = "SELECTION_PANEL";

	private final CardLayout layout;
	private final ViewStateHolder stateHolder;
	private ConstructionActionPanel constructionActionPanel;
	private WallSelectionActionPanel wallSelectionActionPanel;
	private ActionPanel visibleActionPanel;

	/**
	 * Create a ground plan action panel.
	 * 
	 * @param drawHandler
	 *            a draw handler
	 * @param stateHolder
	 *            a state holder
	 * @param floor
	 *            a foor model
	 */
	public GroundPlanActionPanel(final DrawHandler drawHandler,
			final ViewStateHolder stateHolder, final Floor floor) {
		this.stateHolder = stateHolder;
		layout = new CardLayout();
		setLayout(layout);

		constructionActionPanel = new ConstructionActionPanel(drawHandler,
				stateHolder, floor);
		visibleActionPanel = constructionActionPanel;
		add(constructionActionPanel, CONSTRUCTION_PANEL);

		wallSelectionActionPanel = new WallSelectionActionPanel(stateHolder,
				floor);
		add(wallSelectionActionPanel, SELECTION_PANEL);

		updateVisiblePanel();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("selectedViews")) {
			updateVisiblePanel();
		}
	}

	/**
	 * Shows the correct action panel, when a change is needed.
	 */
	private void updateVisiblePanel() {
		boolean showConstructionPanel = stateHolder.getSelectedViews()
				.isEmpty();
		if (showConstructionPanel != (visibleActionPanel == constructionActionPanel)) {
			visibleActionPanel.setActive(false);
			if (showConstructionPanel) {
				layout.show(this, CONSTRUCTION_PANEL);
				visibleActionPanel = constructionActionPanel;
			} else {
				layout.show(this, SELECTION_PANEL);
				visibleActionPanel = wallSelectionActionPanel;
			}
			visibleActionPanel.setActive(true);
		}
	}

	@Override
	public void setActive(boolean active) {
		if (active) {
			stateHolder.addPropertyChangeListener(this);
		} else {
			stateHolder.removePropertyChangeListener(this);
		}
		visibleActionPanel.setActive(active);
	}

}
