package de.hsrm.mi.swt.grundreisser.tests.view;

import static org.junit.Assert.assertEquals;

import java.awt.Rectangle;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.view.util.PixelConverter;
import de.hsrm.mi.swt.grundreisser.view.util.PixelConverterImpl;

public class PixelConverterTest {

	private PixelConverter pixelConverter;

	@Before
	public void setUp() throws Exception {
		pixelConverter = new PixelConverterImpl();
		pixelConverter.setScaleFactor(50);
	}

	@Test
	public void testGetValueForPixel() {
		Rectangle r = new Rectangle(10, 10, 10, 10);
		Rectangle newR = pixelConverter.getValueForPixel(r);
		Rectangle checkR = new Rectangle(500, 500, 500, 500);
		assertEquals(checkR, newR);
	}

	@Test
	public void testGetPixelForValue() {
		Rectangle r = new Rectangle(500, 500, 500, 500);
		Rectangle newR = pixelConverter.getPixelForValue(r);
		Rectangle checkR = new Rectangle(10, 10, 10, 10);
		assertEquals(checkR, newR);
	}

}
