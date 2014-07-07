package de.hsrm.mi.swt.grundreisser.view.actions.groundplan;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.view.actions.ActionPanel;
import de.hsrm.mi.swt.grundreisser.view.draw.DrawHandler;
import de.hsrm.mi.swt.grundreisser.view.global.ViewState;
import de.hsrm.mi.swt.grundreisser.view.global.ViewStateHolder;

/**
 * Displays all tools that could be used in the ground plan mode. It is
 * responsible for setting the correct action handlers, when the user choosed a
 * tool.
 * 
 * @author Simon Seyer
 *
 */
public class ConstructionActionPanel extends JPanel implements ActionPanel,
		PropertyChangeListener {

	private final ButtonGroup buttonGroup;
	private final DrawHandler drawHandler;
	private final JToggleButton selectionButton;
	private final ViewStateHolder stateHolder;
	private final Floor floor;

	/**
	 * Create a action panel. Initializes all view components and action
	 * listeners.
	 * 
	 * @param drawHandler
	 *            a draw handler
	 * @param stateHolder
	 *            a state holder
	 * @param floor
	 *            a floor model
	 */
	public ConstructionActionPanel(final DrawHandler drawHandler,
			final ViewStateHolder stateHolder, final Floor floor) {
		this.drawHandler = drawHandler;
		this.stateHolder = stateHolder;
		this.floor = floor;

		setLayout(new GridLayout(5, 1, 6, 6));
		setBorder(new EmptyBorder(0, 10, 0, 10));

		selectionButton = new JToggleButton("Auswählen", true);
		add(selectionButton);
		selectionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setInSelectionMode();
			}
		});

		JToggleButton drawInnerWallButton = new JToggleButton(
				"Innenwand zeichnen");
		add(drawInnerWallButton);
		drawInnerWallButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawHandler.setMouseListener(new DrawInnerWallMouseListener(
						drawHandler.getGroundPlanView(), floor, drawHandler));
				stateHolder.setViewState(ViewState.GROUND_PLAN_DRAW);
			}
		});

		JToggleButton drawOuterWallButton = new JToggleButton(
				"Außenwand zeichnen");
		add(drawOuterWallButton);
		drawOuterWallButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawHandler.setMouseListener(new DrawOuterWallMouseListener(
						drawHandler.getGroundPlanView(), floor, drawHandler));
				stateHolder.setViewState(ViewState.GROUND_PLAN_DRAW);
			}
		});

		JToggleButton drawWindowButton = new JToggleButton("Fenster zeichnen");
		add(drawWindowButton);
		drawWindowButton.addActionListener(new WindoorActionListener(
				DrawWindoorMouseListener.WINDOW));

		JToggleButton drawDoorButton = new JToggleButton("Tür zeichnen");
		add(drawDoorButton);
		drawDoorButton.addActionListener(new WindoorActionListener(
				DrawWindoorMouseListener.DOOR));

		// Add the buttons to a group, so only one button could be active at the
		// same time
		buttonGroup = new ButtonGroup();
		buttonGroup.add(selectionButton);
		buttonGroup.add(drawInnerWallButton);
		buttonGroup.add(drawOuterWallButton);
		buttonGroup.add(drawWindowButton);
		buttonGroup.add(drawDoorButton);

		setInSelectionMode();
	}

	/**
	 * A action listener for the two actions drawing a door and drawing a
	 * window.
	 * 
	 * @author Simon Seyer
	 *
	 */
	private class WindoorActionListener implements ActionListener {

		private int type;

		/**
		 * Create a action listener for drawing a door or for drawing a window
		 * 
		 * @param type
		 *            either DOOR or WINDOW
		 * @see DrawWindoorMouseListener
		 */
		public WindoorActionListener(int type) {
			assert type >= 0 && type <= 1;
			this.type = type;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			DrawWindoorMouseListener drawWindoorML = new DrawWindoorMouseListener(
					stateHolder, floor, drawHandler, type);
			drawHandler.setSelectionDelegate(drawWindoorML);
			drawHandler.setMouseListener(drawWindoorML);
			stateHolder.setViewState(ViewState.GROUND_PLAN_DRAW);

		}

	}

	/**
	 * Applies the selection mode. Resets listener.
	 */
	private void setInSelectionMode() {
		selectionButton.setSelected(true);
		stateHolder.setViewState(ViewState.GROUND_PLAN_SELECT);
		// Has to be done because the action listener would otherwise
		// not be freed until a new action listener is set
		drawHandler.setMouseListener(null);
		drawHandler.setSelectionDelegate(null);
	}

	@Override
	public void setActive(boolean active) {
		if (active) {
			setInSelectionMode();
			stateHolder.addPropertyChangeListener(this);
		} else {
			drawHandler.setMouseListener(null);
			drawHandler.setSelectionDelegate(null);
			stateHolder.removePropertyChangeListener(this);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("viewState")) {
			if (stateHolder.getViewState() == ViewState.GROUND_PLAN_SELECT
					&& !selectionButton.isSelected()) {
				setInSelectionMode();
			}
		}
	}

}
