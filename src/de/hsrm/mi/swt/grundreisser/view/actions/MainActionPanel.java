package de.hsrm.mi.swt.grundreisser.view.actions;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.hsrm.mi.swt.grundreisser.business.ModelManagerImpl;
import de.hsrm.mi.swt.grundreisser.business.command.exceptions.UndoRedoException;
import de.hsrm.mi.swt.grundreisser.business.factories.FloorFactory;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.view.actions.groundplan.GroundPlanActionPanel;
import de.hsrm.mi.swt.grundreisser.view.actions.interior.InteriorActionPanel;
import de.hsrm.mi.swt.grundreisser.view.draw.DrawHandler;
import de.hsrm.mi.swt.grundreisser.view.global.ViewState;
import de.hsrm.mi.swt.grundreisser.view.global.ViewStateHolder;

/**
 * This panel shows contextual information and tools for the content on the draw
 * pane. It automatically updates the visible views depending on the values of
 * the ViewStateHolder
 * 
 * @author Simon Seyer
 * @see ViewStateHolder
 */
public class MainActionPanel extends JPanel implements ActionListener,
		PropertyChangeListener {

	private static String GROUNDPLAN_PANEL = "GROUNDPLAN_PANEL";
	private static String INTERIOR_PANEL = "INTERIOR_PANEL";

	private ViewStateHolder stateHolder;
	private Floor model;
	private CardLayout layout;
	private JToggleButton groundPlanModeButton;
	private JToggleButton interiorModeButton;
	private ButtonGroup switchButtonGroup;
	private JPanel contextualActionPanel;
	private GroundPlanActionPanel groundPlanActionPanel;
	private InteriorActionPanel interiorActionPanel;
	private ActionPanel visisbleActionPanel;

	/**
	 * Create a action panel and intialize all views.
	 * 
	 * @param drawHandler
	 *            a draw handler
	 * @param stateHolder
	 *            a state holder
	 * @param floor
	 *            a floor model
	 */
	public MainActionPanel(final DrawHandler drawHandler,
			final ViewStateHolder stateHolder, final Floor floor) {
		this.stateHolder = stateHolder;
		this.model = floor;

		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 3, 3));
		buttonPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
		topPanel.add(buttonPanel, BorderLayout.CENTER);

		JButton loadButton = new JButton("Laden");
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Disable drawing when opening a dialog
				if (stateHolder.getViewState() == ViewState.GROUND_PLAN_DRAW) {
					stateHolder.setViewState(ViewState.GROUND_PLAN_SELECT);
				}
				loadFile();
			}
		});
		buttonPanel.add(loadButton);

		JButton saveButton = new JButton("Speichern");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Disable drawing when opening a dialog
				if (stateHolder.getViewState() == ViewState.GROUND_PLAN_DRAW) {
					stateHolder.setViewState(ViewState.GROUND_PLAN_SELECT);
				}
				saveFile();
			}
		});
		buttonPanel.add(saveButton);

		JButton zoomOut = new JButton("-");
		zoomOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawHandler.zoomIn();
			}
		});
		buttonPanel.add(zoomOut);

		JButton zoomIn = new JButton("+");
		zoomIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawHandler.zoomOut();
			}
		});
		buttonPanel.add(zoomIn);

		JButton undoButton = new JButton("<-");
		undoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					floor.getCommandManager().undo();
				} catch (UndoRedoException e1) {
				}
			}
		});
		buttonPanel.add(undoButton);

		JButton redoButton = new JButton("->");
		redoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					floor.getCommandManager().redo();
				} catch (UndoRedoException e1) {
				}
			}
		});
		buttonPanel.add(redoButton);

		topPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.SOUTH);
		// topPanel.add(new JSeparator(JSeparator.HORIZONTAL));

		contextualActionPanel = new JPanel();
		add(contextualActionPanel, BorderLayout.CENTER);

		layout = new CardLayout();
		contextualActionPanel.setLayout(layout);

		groundPlanActionPanel = new GroundPlanActionPanel(drawHandler,
				stateHolder, floor);
		visisbleActionPanel = groundPlanActionPanel;
		visisbleActionPanel.setActive(true);
		contextualActionPanel.add(groundPlanActionPanel, GROUNDPLAN_PANEL);

		interiorActionPanel = new InteriorActionPanel(drawHandler, stateHolder,
				floor);
		contextualActionPanel.add(interiorActionPanel, INTERIOR_PANEL);

		JPanel switchWrapper = new JPanel(new BorderLayout());
		add(switchWrapper, BorderLayout.SOUTH);

		switchWrapper.add(new JSeparator(JSeparator.HORIZONTAL),
				BorderLayout.NORTH);

		JPanel switchPanel = new JPanel(new GridLayout(2, 1));
		switchWrapper.add(switchPanel, BorderLayout.CENTER);

		groundPlanModeButton = new JToggleButton("Groundplan", true);
		groundPlanModeButton.addActionListener(this);
		switchPanel.add(groundPlanModeButton);

		interiorModeButton = new JToggleButton("Interior");
		interiorModeButton.addActionListener(this);
		switchPanel.add(interiorModeButton);

		// Add the buttons to a group, so only one button could be selected at
		// the same time
		switchButtonGroup = new ButtonGroup();
		switchButtonGroup.add(groundPlanModeButton);
		switchButtonGroup.add(interiorModeButton);

		stateHolder.addPropertyChangeListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == groundPlanModeButton) {
			stateHolder.setViewState(ViewState.GROUND_PLAN_SELECT);
		} else {
			stateHolder.setViewState(ViewState.INTERIOR_SELECT);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("viewState")) {
			boolean groundPlan = stateHolder.getViewState() == ViewState.GROUND_PLAN_SELECT
					|| stateHolder.getViewState() == ViewState.GROUND_PLAN_DRAW;
			if (groundPlan != (visisbleActionPanel == groundPlanActionPanel)) {
				visisbleActionPanel.setActive(false);
				if (groundPlan) {
					layout.show(contextualActionPanel, GROUNDPLAN_PANEL);
					visisbleActionPanel = groundPlanActionPanel;
				} else {
					layout.show(contextualActionPanel, INTERIOR_PANEL);
					visisbleActionPanel = interiorActionPanel;
				}
				visisbleActionPanel.setActive(true);
			}

		}
	}

	/**
	 * Show a file chooser and save the floor to the selected location
	 */
	private void saveFile() {
		JFileChooser fileChooser = createFileChooser();
		int returnVal = fileChooser.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File saveFile = fileChooser.getSelectedFile();
			if (!saveFile.getAbsolutePath().endsWith(
					FloorFactory.FILE_EXTENSION)) {
				saveFile = new File(fileChooser.getSelectedFile() + "."
						+ FloorFactory.FILE_EXTENSION);
			}

			try {
				ModelManagerImpl.getInstance().saveFile(model, saveFile);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane
						.showMessageDialog(this,
								"Datei konnte an dem gewählten Ort nicht gespeichert werden");
			}
		}
	}

	/**
	 * Show a file chooser and load the selected file
	 */
	private void loadFile() {
		JFileChooser fileChooser = createFileChooser();
		int returnVal = fileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File loadFile = fileChooser.getSelectedFile();

			try {
				ModelManagerImpl.getInstance().loadFile(loadFile);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this,
						"Datei konnte nicht geladen werden");
			}
		}
	}

	/**
	 * Get a configured file chooser
	 * 
	 * @return a file chooser
	 */
	private JFileChooser createFileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Grundreisser Datei", FloorFactory.FILE_EXTENSION);
		fileChooser.setFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		return fileChooser;
	}
}
