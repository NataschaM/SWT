package de.hsrm.mi.swt.grundreisser.view.util;

import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.OuterWall;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.Wall;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Fitment;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.FitmentGroup;
import de.hsrm.mi.swt.grundreisser.view.draw.MainView;
import de.hsrm.mi.swt.grundreisser.view.groundplan.DummyWallView;
import de.hsrm.mi.swt.grundreisser.view.groundplan.OuterWallView;
import de.hsrm.mi.swt.grundreisser.view.groundplan.WallView;
import de.hsrm.mi.swt.grundreisser.view.groundplan.WallViewImpl;
import de.hsrm.mi.swt.grundreisser.view.interior.FitmentGroupView;
import de.hsrm.mi.swt.grundreisser.view.interior.FitmentView;
import de.hsrm.mi.swt.grundreisser.view.interior.FitmentViewImpl;

/**
 * @author Simon Seyer
 * 
 */
public class ViewFactoryImpl implements ViewFactory {

	private MainView mainView;

	/**
	 * Create a view factory
	 * 
	 * @param mainView
	 *            a main view
	 */
	public ViewFactoryImpl(MainView mainView) {
		this.mainView = mainView;
	}

	@Override
	public FitmentView<?> createFitmentView(Fitment fitment) {
		FitmentView<?> view;
		if (fitment instanceof FitmentGroup) {
			view = new FitmentGroupView(mainView, (FitmentGroup) fitment, this);
		} else {
			view = new FitmentViewImpl(mainView, fitment);
		}
		view.setSelectionDelegate(mainView.getSelectionDelegate());
		return view;
	}

	@Override
	public WallView<?> createWallView(Wall wall) {
		WallView<?> wallView;
		if (wall instanceof OuterWall) {
			wallView = new OuterWallView(mainView, this, (OuterWall) wall);
		} else {
			wallView = new WallViewImpl(mainView, wall);
		}
		wallView.setSelectionDelegate(mainView.getSelectionDelegate());
		return wallView;
	}

	@Override
	public DummyWallView createDummyWallView() {
		return new WallViewImpl(mainView);
	}

}
