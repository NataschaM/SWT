package de.hsrm.mi.swt.grundreisser.view.actions.interior;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import de.hsrm.mi.swt.grundreisser.business.ModelManagerImpl;
import de.hsrm.mi.swt.grundreisser.business.catalog.Catalog;
import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture;
import de.hsrm.mi.swt.grundreisser.business.command.Command;
import de.hsrm.mi.swt.grundreisser.business.command.interior.DeleteFitmentCommand;
import de.hsrm.mi.swt.grundreisser.business.command.interior.GroupFitmentCommand;
import de.hsrm.mi.swt.grundreisser.business.command.interior.RotateLeftFitmentCommand;
import de.hsrm.mi.swt.grundreisser.business.command.interior.RotateRightFitmentCommand;
import de.hsrm.mi.swt.grundreisser.business.command.interior.UnGroupFitmentCommand;
import de.hsrm.mi.swt.grundreisser.business.command.wall.CommandManager;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.view.actions.ActionPanel;
import de.hsrm.mi.swt.grundreisser.view.draw.DrawHandler;
import de.hsrm.mi.swt.grundreisser.view.global.View;
import de.hsrm.mi.swt.grundreisser.view.global.ViewStateHolder;
import de.hsrm.mi.swt.grundreisser.view.interior.FitmentView;

/**
 * This action panel show tools, that could be applied to selected fitment
 * views. When this panel is active, the selected view could also be moved.
 * 
 * @author Simon Seyer
 * @see MoveFitmentMouseListener
 */
public class FitmentSelectionActionPanel extends JPanel implements ActionPanel,
		PropertyChangeListener {

	private DrawHandler drawHandler;
	private ViewStateHolder stateHolder;
	private Floor model;
	private MoveFitmentMouseListener listener;
	private JLabel selectionInfoLabel;

	/**
	 * Create a fitment selection action panel
	 * 
	 * @param drawHandler
	 *            a draw handler
	 * @param stateHolder
	 *            a state holder
	 * @param model
	 *            a floor model
	 */
	public FitmentSelectionActionPanel(DrawHandler drawHandler,
			final ViewStateHolder stateHolder, final Floor model) {
		this.drawHandler = drawHandler;
		this.stateHolder = stateHolder;
		this.model = model;

		setLayout(new GridLayout(6, 1, 6, 6));
		setBorder(new EmptyBorder(0, 10, 0, 10));

		selectionInfoLabel = new JLabel();
		selectionInfoLabel.setHorizontalAlignment(JLabel.CENTER);
		selectionInfoLabel.setFont(new FontUIResource(Font.SANS_SERIF,
				Font.BOLD, 14));
		add(selectionInfoLabel);

		JButton deleteButton = new JButton("Löschen");
		add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CommandManager cmdManager = model.getCommandManager();
				for (View<?> view : stateHolder.getSelectedViews()) {
					FitmentView<?> fitmentView = (FitmentView<?>) view;
					cmdManager.execAndPush(new DeleteFitmentCommand(model,
							fitmentView.getModel()));
				}
				stateHolder.setSelectedViews(null);
			}
		});

		JButton rotateLeftButton = new JButton("Links rotieren");
		add(rotateLeftButton);
		rotateLeftButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CommandManager cmdManager = model.getCommandManager();
				for (View<?> view : stateHolder.getSelectedViews()) {
					FitmentView<?> fitmentView = (FitmentView<?>) view;
					cmdManager.execAndPush(new RotateLeftFitmentCommand(
							fitmentView.getModel()));
				}
			}
		});

		JButton rotateRightButton = new JButton("Rechts rotieren");
		add(rotateRightButton);
		rotateRightButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CommandManager cmdManager = model.getCommandManager();
				for (View<?> view : stateHolder.getSelectedViews()) {
					FitmentView<?> fitmentView = (FitmentView<?>) view;
					cmdManager.execAndPush(new RotateRightFitmentCommand(
							fitmentView.getModel()));
				}
			}
		});

		JButton groupButton = new JButton("Gruppieren");
		add(groupButton);
		groupButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CommandManager cmdManager = model.getCommandManager();
				List<Fitment> fitments = new ArrayList<>();
				for (View<?> fitmentView : stateHolder.getSelectedViews()) {
					fitments.add(((FitmentView<?>) fitmentView).getModel());
				}
				Command cmd = new GroupFitmentCommand(model, fitments);
				cmdManager.execAndPush(cmd);
				stateHolder.setSelectedViews(null);
			}
		});

		JButton ungroupButton = new JButton("Auflösen");
		add(ungroupButton);
		ungroupButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CommandManager cmdManager = model.getCommandManager();
				for (View<?> view : stateHolder.getSelectedViews()) {
					FitmentView<?> fitmentView = (FitmentView<?>) view;
					cmdManager.execAndPush(new UnGroupFitmentCommand(model,
							fitmentView.getModel()));
				}
				stateHolder.setSelectedViews(null);
			}
		});

	}

	@Override
	public void setActive(boolean active) {
		if (active) {
			listener = new MoveFitmentMouseListener(drawHandler, model);
			drawHandler.setSelectionDelegate(listener);
			stateHolder.addPropertyChangeListener(this);
			updateInfoLabel();
		} else {
			listener.reset();
			drawHandler.setSelectionDelegate(null);
			stateHolder.removePropertyChangeListener(this);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("selectedViews")) {
			updateInfoLabel();
		}
	}

	/**
	 * Updates the label which displays the selected views
	 */
	private void updateInfoLabel() {
		String info = "";
		Catalog catalog = ModelManagerImpl.getInstance().getCatalog();
		for (View<?> view : stateHolder.getSelectedViews()) {
			FitmentView<?> fitmentView = (FitmentView<?>) view;
			Furniture furniture = catalog.objectById(fitmentView.getModel()
					.getFurnitureId());
			if (furniture != null) {
				info += furniture.getName() + " ";
			} else {
				info += "<?> ";
			}

		}
		selectionInfoLabel.setText(info);
	}

}
