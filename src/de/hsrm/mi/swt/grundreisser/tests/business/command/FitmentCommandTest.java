package de.hsrm.mi.swt.grundreisser.tests.business.command;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.business.catalog.CustomFurniture;
import de.hsrm.mi.swt.grundreisser.business.catalog.Furniture;
import de.hsrm.mi.swt.grundreisser.business.command.interior.AddFitmentCommand;
import de.hsrm.mi.swt.grundreisser.business.command.interior.DeleteFitmentCommand;
import de.hsrm.mi.swt.grundreisser.business.command.interior.MoveFitmentCommand;
import de.hsrm.mi.swt.grundreisser.business.command.interior.RotateLeftFitmentCommand;
import de.hsrm.mi.swt.grundreisser.business.command.interior.RotateRightFitmentCommand;
import de.hsrm.mi.swt.grundreisser.business.floor.Floor;
import de.hsrm.mi.swt.grundreisser.business.floor.FloorImpl;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.CustomFitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;

/**
 * JUnit class to test all the wall command implementations
 * 
 * @author jheba001
 *
 */

public class FitmentCommandTest {

	Floor floor;
	List<Fitment> fitments;
	Fitment fitment;
	Furniture furn;
	Point pos;

	/**
	 * Initialization
	 */
	@Before
	public void init() {
		floor = new FloorImpl();
		fitments = new ArrayList<Fitment>();
		furn = new CustomFurniture();
		pos = new Point();
		fitment = new CustomFitment();
	}

	/**
	 * Method to test the add fitment command
	 */
	@Test
	public void testAddFitment() {
		AddFitmentCommand af = new AddFitmentCommand(floor, fitment);

		fitments.add(fitment);
		af.execute();

		assertEquals(fitments, floor.getInterior().getFitments());

		fitments.remove(fitment);
		af.undo();

		assertEquals(fitments, floor.getInterior().getFitments());

		fitments.add(fitment);
		af.execute();

		assertEquals(fitments, floor.getInterior().getFitments());
	}

	/**
	 * Method to test the delete fitment command
	 */
	@Test
	public void testDeleteFitment() {
		AddFitmentCommand af = new AddFitmentCommand(floor, fitment);
		DeleteFitmentCommand df = new DeleteFitmentCommand(floor, fitment);

		fitments.add(fitment);
		af.execute();
		fitments.remove(fitment);
		df.execute();

		assertEquals(fitments, floor.getInterior().getFitments());

		fitments.add(fitment);
		df.undo();

		assertEquals(fitments.get(0).getCenterLocation(), floor.getInterior()
				.getFitments().get(0).getCenterLocation());
	}

	/**
	 * Method to test the move wall command
	 */
	@Test
	public void testMoveFitment() {
		fitment.move(new Point(0, 0));
		AddFitmentCommand af = new AddFitmentCommand(floor, fitment);

		fitments.add(fitment);
		af.execute();

		MoveFitmentCommand mf = new MoveFitmentCommand(fitment, new Point(2, 2));

		fitments.get(0).move(new Point(2, 2));
		mf.execute();

		assertEquals(fitments, floor.getInterior().getFitments());

		fitments.get(0).move(new Point(0, 0));
		mf.undo();

		assertEquals(fitments, floor.getInterior().getFitments());
	}

	/**
	 * Method to test the rotate right command
	 */
	@Test
	public void testRotateRightFitment() {
		RotateRightFitmentCommand rrf = new RotateRightFitmentCommand(fitment);
		Fitment nfitment = new CustomFitment();

		rrf.execute();
		nfitment.rotateRight();

		assertEquals(fitment.getCenterLocation(), nfitment.getCenterLocation());
	}

	/**
	 * Method to test the rotate left command
	 */
	@Test
	public void testRotateLeftFitment() {
		RotateLeftFitmentCommand rlf = new RotateLeftFitmentCommand(fitment);
		Fitment nfitment = new CustomFitment();

		rlf.execute();
		nfitment.rotateRight();

		assertEquals(fitment.getCenterLocation(), nfitment.getCenterLocation());
	}
}
