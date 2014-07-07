package de.hsrm.mi.swt.grundreisser.view.actions.interior;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.hsrm.mi.swt.grundreisser.business.catalog.CatalogImpl;

/**
 * Prompts a dialog that asks the user for information about the fitment he
 * would like to add.
 * 
 * @author Simon Seyer
 *
 */
public class CustomFitmentDialog {

	private JPanel inputPanel;
	private JTextField nameField;
	private JTextField widthField;
	private JTextField heightField;
	private JComboBox<String> layerList;
	private Component currentParent;

	/**
	 * Create a custom fitment dialog
	 */
	public CustomFitmentDialog() {
		inputPanel = new JPanel(new GridLayout(4, 2));

		inputPanel.add(new JLabel("Name:"));
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(200, 30));
		inputPanel.add(nameField);

		inputPanel.add(new JLabel("Breite (in cm):"));
		widthField = new JTextField();
		inputPanel.add(widthField);

		inputPanel.add(new JLabel("Höhe (in cm):"));
		heightField = new JTextField();
		inputPanel.add(heightField);

		inputPanel.add(new JLabel("Typ:"));
		String[] layer = CatalogImpl.getLayerNames();
		layerList = new JComboBox<>(Arrays.copyOfRange(layer, 1, layer.length));
		layerList.setSelectedIndex(0);
		inputPanel.add(layerList);
	}

	/**
	 * Display the dialog to the user and get the result
	 * 
	 * @return the result status of JOptionPane
	 * @see JOptionPane
	 */
	public int show(Component parent) {
		currentParent = parent;
		int result = -2;
		while (result < -1) {
			result = JOptionPane.showConfirmDialog(parent, inputPanel,
					"Eigenes Möbelstück", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null);
			if (result == JOptionPane.OK_OPTION) {
				if (!validate()) {
					result = -2;
				}
			}
		}
		currentParent = null;
		return result;
	}

	/**
	 * Check if the input values are valid
	 * 
	 * @return true, if the input is valid
	 */
	private boolean validate() {
		if (getName().equals("")) {
			showValidationError("Bitte einen Namen eingeben");
			return false;
		}
		try {
			if (getWidth() <= 0) {
				showValidationError("Bitte eine Breite größer 0 eingeben");
				return false;
			}
		} catch (Exception e) {
			showValidationError("Bitte eine Breite in Millimetern eingeben");
			return false;
		}
		try {
			if (getHeight() <= 0) {
				showValidationError("Bitte eine Höhe größer 0 eingeben");
				return false;
			}
		} catch (Exception e) {
			showValidationError("Bitte eine Höhe in Millimetern eingeben");
			return false;
		}

		return true;
	}

	private void showValidationError(String msg) {
		JOptionPane.showMessageDialog(currentParent, msg, "Eingabefehler",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Get the entered name
	 * 
	 * @return the name
	 */
	public String getName() {
		return nameField.getText();
	}

	/**
	 * Get the entered width in mm
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return Integer.valueOf(widthField.getText()) * 10;
	}

	/**
	 * Get the entered height in mm
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return Integer.valueOf(heightField.getText()) * 10;
	}

	/**
	 * Get the selected layer
	 * 
	 * @return the layer
	 */
	public int getLayer() {
		return layerList.getSelectedIndex() + 1;
	}
}
