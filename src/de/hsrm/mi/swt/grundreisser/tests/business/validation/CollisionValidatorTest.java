package de.hsrm.mi.swt.grundreisser.tests.business.validation;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.hsrm.mi.swt.grundreisser.business.floor.PlacedObject;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.CustomFitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.FitmentGroup;
import de.hsrm.mi.swt.grundreisser.business.validation.Collision;
import de.hsrm.mi.swt.grundreisser.business.validation.CollisionValidator;

public class CollisionValidatorTest {

	private Fitment group, intGroup;
	private List<Fitment> fitList;
	private Fitment composite;
	private List<Fitment> compList, intGroupList;
	private List<PlacedObject> totalList;
	private Fitment intersectFit, intFit1, intFit2;
	private CollisionValidator val;
	private Fitment f1, f2, f3;

	@Before
	public void setUp() {

		fitList = new ArrayList<Fitment>();
		compList = new ArrayList<Fitment>();
		intGroupList = new ArrayList<Fitment>();
		totalList = new ArrayList<PlacedObject>();

		f1 = new CustomFitment(new Point(9, 5), 6, 4);
		fitList.add(f1);
		f2 = new CustomFitment(new Point(11, 11), 2, 8);
		fitList.add(f2);

		group = FitmentGroup.createFitmentGroup(fitList);
		compList.add(group);

		f3 = new CustomFitment(new Point(18, 7), 4, 6);
		compList.add(f3);

		composite = FitmentGroup.createFitmentGroup(compList);
		totalList.add(composite);

		intersectFit = new CustomFitment(new Point(7, 7), 8, 4);
		totalList.add(intersectFit);

		intFit1 = new CustomFitment(new Point(20, 10), 4, 4);
		intFit2 = new CustomFitment(new Point(11, 14), 6, 2);
		intGroupList.add(intFit1);
		intGroupList.add(intFit2);

		intGroup = FitmentGroup.createFitmentGroup(intGroupList);

		totalList.add(intGroup);

		val = new CollisionValidator();
	}

	@Test
	public void testSimpleCollisionByGroup() {

		List<Collision> collisions = val
				.findCollisions(totalList, intersectFit);
		assertEquals(collisions.size(), 2);
		PlacedObject obj1 = collisions.get(0).getObj1();
		assertEquals(obj1, f1);
		PlacedObject obj2 = collisions.get(0).getObj2();
		assertEquals(obj2, intersectFit);

		obj1 = collisions.get(1).getObj1();
		assertEquals(obj1, f2);
		obj2 = collisions.get(1).getObj2();
		assertEquals(obj2, intersectFit);
	}

	@Test
	public void testCompositeCollisionByGroup() {

		List<Collision> collisions = val.findCollisions(totalList, intGroup);
		assertEquals(collisions.size(), 2);

		PlacedObject obj1 = collisions.get(0).getObj1();
		assertEquals(obj1, intFit1);
		PlacedObject obj2 = collisions.get(0).getObj2();
		assertEquals(obj2, intFit1);
	}

}
