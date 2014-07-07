package de.hsrm.mi.swt.grundreisser.view.actions.interior;

import java.awt.CardLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.view.actions.ActionPanel;
import de.hsrm.mi.swt.grundreisser.view.draw.DrawHandler;
import de.hsrm.mi.swt.grundreisser.view.global.ViewStateHolder;

/**
 * Action panel that is displayed, when the view is in interior state. Shows the
 * CatalogActionPanel or the FitmentSelectionActionPanel depending on the
 * selected views.
 * 
 * @author Simon Seyer
 *
 */
public class InteriorActionPanel extends JPanel implements ActionPanel,
		PropertyChangeListener {

	private ViewStateHolder stateHolder;

	private static String CATALOG_PANEL = "CATALOG_PANEL";
	private static String SELECTION_PANEL = "SELECTION_PANEL";
	private CardLayout layout;
	private CatalogActionPanel catalogActionPanel;
	private FitmentSelectionActionPanel fitmentSelectionActionPanel;
	private ActionPanel visibleActionPanel;

	/**
	 * Create an interior action panel
	 * 
	 * @param drawHandler
	 *            a draw handler
	 * @param stateHolder
	 *            a state holder
	 * @param model
	 *            a floor model
	 */
	public InteriorActionPanel(DrawHandler drawHandler,
			ViewStateHolder stateHolder, Floor model) {
		this.stateHolder = stateHolder;

		layout = new CardLayout();
		setLayout(layout);

		catalogActionPanel = new CatalogActionPanel(drawHandler, model);
		visibleActionPanel = catalogActionPanel;
		add(catalogActionPanel, CATALOG_PANEL);

		fitmentSelectionActionPanel = new FitmentSelectionActionPanel(
				drawHandler, stateHolder, model);
		add(fitmentSelectionActionPanel, SELECTION_PANEL);

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
		boolean showCatalogPanel = stateHolder.getSelectedViews().isEmpty();
		if (showCatalogPanel != (visibleActionPanel == catalogActionPanel)) {
			visibleActionPanel.setActive(false);
			if (showCatalogPanel) {
				layout.show(this, CATALOG_PANEL);
				visibleActionPanel = catalogActionPanel;
			} else {
				layout.show(this, SELECTION_PANEL);
				visibleActionPanel = fitmentSelectionActionPanel;
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
