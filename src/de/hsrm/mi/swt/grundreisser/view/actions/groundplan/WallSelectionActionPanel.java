package de.hsrm.mi.swt.grundreisser.view.actions.groundplan;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.hsrm.mi.swt.grundreisser.business.command.wall.CommandManager;
import de.hsrm.mi.swt.grundreisser.business.command.wall.DeleteWallCommand;
import de.hsrm.mi.swt.grundreisser.business.command.windoor.ClearWinDoorsCommand;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.view.actions.ActionPanel;
import de.hsrm.mi.swt.grundreisser.view.global.View;
import de.hsrm.mi.swt.grundreisser.view.global.ViewStateHolder;
import de.hsrm.mi.swt.grundreisser.view.groundplan.WallView;

/**
 * This panel is shown, when the user selected a wall. It gives the user the
 * ability to manipulate the wall, for example delete it.
 * 
 * @author Simon Seyer
 *
 */
public class WallSelectionActionPanel extends JPanel implements ActionPanel {

	/**
	 * Create a wall selection listener
	 * 
	 * @param stateHolder
	 *            a state holder
	 * @param floor
	 *            a floor model
	 */
	public WallSelectionActionPanel(final ViewStateHolder stateHolder,
			final Floor floor) {

		setLayout(new GridLayout(5, 1, 6, 6));
		setBorder(new EmptyBorder(0, 10, 0, 10));

		JButton deleteButton = new JButton("Löschen");
		add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CommandManager cmdManager = floor.getCommandManager();
				for (View<?> view : stateHolder.getSelectedViews()) {
					@SuppressWarnings("unchecked")
					WallView<Wall> wallView = (WallView<Wall>) view;
					cmdManager.execAndPush(new DeleteWallCommand(floor,
							wallView.getModel()));
				}
				stateHolder.setSelectedViews(null);
			}
		});

		JButton clearWindoorButton = new JButton("Fenster und Türen löschen");
		add(clearWindoorButton);
		clearWindoorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CommandManager cmdManager = floor.getCommandManager();
				for (View<?> view : stateHolder.getSelectedViews()) {
					@SuppressWarnings("unchecked")
					WallView<Wall> wallView = (WallView<Wall>) view;
					cmdManager.execAndPush(new ClearWinDoorsCommand(wallView
							.getModel()));
				}
			}
		});
	}

	@Override
	public void setActive(boolean active) {

	}
}
