package de.hsrm.mi.swt.grundreisser.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.hsrm.mi.swt.grundreisser.business.ModelManagerImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;

/**
 * The main frame of the application that displays one or more floor views. At
 * the moment, there is only one FloorView supported.
 * 
 * @author Simon Seyer
 * 
 */
public class MainFrame extends JFrame {

	private List<FloorView> floorViews;

	/**
	 * Create a main frame
	 */
	public MainFrame() {
		floorViews = new ArrayList<>();

		setPreferredSize(new Dimension(800, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		updateFloor();
		ModelManagerImpl.getInstance().addPropertyChangeListener(
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (evt.getPropertyName().equals("actFloor")) {
							updateFloor();
						}
					}
				});
	}

	/**
	 * Update the visible floor
	 */
	private void updateFloor() {
		Floor floor = ModelManagerImpl.getInstance().getActFloor();
		addFloorView(new FloorView(floor));
	}

	/**
	 * Adds and displays a floor view.
	 * 
	 * @param floorView
	 *            the floor view
	 */
	public void addFloorView(final FloorView floorView) {
		floorViews.clear();
		this.floorViews.add(floorView);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				getContentPane().removeAll();
				getContentPane().add(floorView, BorderLayout.CENTER);
				pack();
			}
		});
	}

	/**
	 * Start point for the programm
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		new MainFrame();
	}
}
