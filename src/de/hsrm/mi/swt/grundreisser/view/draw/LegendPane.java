package de.hsrm.mi.swt.grundreisser.view.draw;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import de.hsrm.mi.swt.grundreisser.view.util.PixelConverter;

/**
 * 
 * This class displays a legend. It is intended to be shown above the groundplan
 * and interior view. It gives the user a visual guide to estimate distances.
 * 
 * @author Simon Seyer
 */
public class LegendPane extends JPanel {

	private PixelConverter pixelConverter;

	/**
	 * Defines the distance of the dots in the model unit
	 */
	public static final int DOT_DISTANCE = 500;

	/**
	 * Defines over how many dots the legend should be shown
	 */
	private static final int LEGEND_LENGTH_FACTOR = 2;

	/**
	 * Create a legend pane
	 * 
	 * @param pixelConverter
	 *            the pixelconverter to convert between view and model
	 *            coordinates
	 */
	public LegendPane(PixelConverter pixelConverter) {
		this.pixelConverter = pixelConverter;
		setLayout(null);
		setOpaque(false);
		updateView();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int pixelDist = pixelConverter.getPixelForValue(DOT_DISTANCE);

		// Draw legend
		int legendX = 0;
		int legendY = 8;
		int legendWidth = LEGEND_LENGTH_FACTOR * pixelDist + 2;

		g.setColor(new Color(30, 30, 30));
		g.fillRect(legendX, legendY - 6, 2, 6);
		g.fillRect(legendX, legendY, legendWidth, 2);
		g.fillRect(legendX + legendWidth - 2, legendY - 6, 2, 6);

		// Draw legend text
		g.setColor(new Color(10, 10, 10));
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		g.drawString(LEGEND_LENGTH_FACTOR + " m", legendX + legendWidth + 7,
				legendY + 2);
	}

	/**
	 * Updates the legend
	 */
	public void updateView() {
		int pixelDist = pixelConverter.getPixelForValue(DOT_DISTANCE);
		setSize(LEGEND_LENGTH_FACTOR * pixelDist + 100, 10);
		repaint();
	}
}
