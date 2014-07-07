package de.hsrm.mi.swt.grundreisser.view.actions.interior;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;

import de.hsrm.mi.swt.grundreisser.business.ModelManagerImpl;
import de.hsrm.mi.swt.grundreisser.business.catalog.Catalog;
import de.hsrm.mi.swt.grundreisser.business.catalog.Category;
import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.view.actions.ActionPanel;
import de.hsrm.mi.swt.grundreisser.view.draw.DrawHandler;

/**
 * This panel displays the catalog with all available fitments. It allows to
 * select a category and move a a fitment on the draw pane.
 * 
 * @author Simon Seyer
 *
 */
public class CatalogActionPanel extends JPanel implements ActionPanel {

	private Floor floor;
	private DrawHandler drawHandler;
	private boolean inCategory;
	private Catalog catalog;
	private JLabel categoryLabel;
	private JButton backButton;
	private DefaultListModel<Object> listModel;
	private JList<Object> list;
	private List<Furniture> furnitures;

	/**
	 * Create a catalog action panel
	 * 
	 * @param drawHandler
	 *            a draw handler
	 * @param floor
	 *            a floor model
	 */
	public CatalogActionPanel(final DrawHandler drawHandler, final Floor floor) {
		setLayout(new BorderLayout());
		this.drawHandler = drawHandler;
		this.floor = floor;
		catalog = ModelManagerImpl.getInstance().getCatalog();

		JPanel topPanel = new JPanel(new GridLayout(1, 2));
		topPanel.setBorder(new EmptyBorder(0, 5, 0, 0));
		add(topPanel, BorderLayout.NORTH);

		categoryLabel = new JLabel();
		categoryLabel
				.setFont(new FontUIResource(Font.SANS_SERIF, Font.BOLD, 14));
		topPanel.add(categoryLabel);

		backButton = new JButton("Zurück");
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				leaveCategory();
			}
		});
		topPanel.add(backButton);

		listModel = new DefaultListModel<>();

		list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setTransferHandler(new FurnitureTransferHandler());
		list.setBorder(new EmptyBorder(5, 5, 5, 5));
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!inCategory && !e.getValueIsAdjusting()) {
					String sec = (String) ((JList<?>) e.getSource())
							.getSelectedValue();
					enterCategory(sec);
				}
			}
		});
		add(new JScrollPane(list), BorderLayout.CENTER);

		leaveCategory();

		final JToggleButton customFitmentButton = new JToggleButton(
				"Eigenes Objekt erstellen");
		// customFitmentButton.setBorder(new EmptyBorder(5,0,0,0));
		final JPanel catalogPanel = this;
		customFitmentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (customFitmentButton.isSelected()) {
					drawHandler
							.setMouseListener(new CustomFitmentMouseListener(
									drawHandler, floor, catalogPanel,
									new ActionListener() {

										@Override
										public void actionPerformed(
												ActionEvent e) {
											customFitmentButton
													.setSelected(false);
											drawHandler.setMouseListener(null);
										}
									}));
				} else {
					drawHandler.setMouseListener(null);
				}
			}
		});
		add(customFitmentButton, BorderLayout.SOUTH);
	}

	/**
	 * Changes the view so that the list of furnitures in the category is
	 * listed.
	 * 
	 * @param name
	 *            the name of the category
	 */
	private void enterCategory(String name) {
		Category category = catalog.getCategoryByName(name);
		if (category != null) {
			categoryLabel.setText(name);

			furnitures = new LinkedList<>(category.getFurnitures().values());

			listModel.clear();
			for (Furniture furniture : furnitures) {
				listModel.addElement(new FurnitureItem(furniture));
			}

			inCategory = true;
			backButton.setEnabled(true);
			list.setDragEnabled(true);
		}
	}

	/**
	 * Shows the list of categories
	 */
	private void leaveCategory() {
		categoryLabel.setText("");
		inCategory = false;
		backButton.setEnabled(false);
		list.setDragEnabled(false);

		listModel.clear();
		for (String category : catalog.getCategoryNames()) {
			listModel.addElement(category);
		}
	}

	@Override
	public void setActive(boolean active) {
		try {
			if (active) {
				drawHandler
						.setDropTargetListener(new FurnitureDropTargetListener(
								drawHandler, floor));
			} else {
				drawHandler.setDropTargetListener(null);
			}
		} catch (TooManyListenersException e) {
		}
	}

	/**
	 * This classed is used to display the name of the furniture in the list.
	 * The default implementation of the toString method shows more meta data.
	 * 
	 * @author Simon Seyer
	 *
	 */
	public class FurnitureItem {

		private Furniture furniture;

		/**
		 * Create a furniture item
		 * 
		 * @param furniture
		 *            the furniture to store
		 */
		public FurnitureItem(Furniture furniture) {
			this.furniture = furniture;
		}

		/**
		 * Get the value of the item
		 * 
		 * @return the furniture
		 */
		public Furniture getValue() {
			return furniture;
		}

		@Override
		public String toString() {
			return furniture.getName();
		}
	}

}
