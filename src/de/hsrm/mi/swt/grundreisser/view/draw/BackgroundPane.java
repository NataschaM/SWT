package de.hsrm.mi.swt.grundreisser.view.draw;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import de.hsrm.mi.swt.grundreisser.view.util.PixelConverter;

/**
 * 
 * This class displays a grid. It is intended to be shown behind the groundplan
 * and interior view. It gives the user a visual guide to estimate distances.
 * 
 * @author Simon Seyer
 *
 */
public class BackgroundPane extends JPanel {

	private PixelConverter pixelConverter;

	/**
	 * Create a background pane
	 * 
	 * @param pixelConverter
	 *            the pixelconverter to convert between view and model
	 *            coordinates
	 */
	public BackgroundPane(PixelConverter pixelConverter) {
		this.pixelConverter = pixelConverter;
		setLayout(null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw dots
		int pixelDist = pixelConverter
				.getPixelForValue(LegendPane.DOT_DISTANCE);
		g.setColor(new Color(180, 180, 180));
		for (int x = (pixelDist / 2); x < getWidth(); x += pixelDist) {
			for (int y = (pixelDist / 2); y < getHeight(); y += pixelDist) {
				g.fillRect(x - 1, y - 1, 2, 2);
			}
		}
	}

}
