package de.hsrm.mi.swt.grundreisser.view.global;

/**
 * 
 * The different states the view could be in
 * 
 * @author Simon Seyer
 * 
 */
public enum ViewState {

	/**
	 * The ground pLan is visible and editable and the user could select objects
	 * on the draw pane
	 */
	GROUND_PLAN_SELECT,

	/**
	 * The ground plan is visible and editable and the user could draw on the
	 * draw pane
	 */
	GROUND_PLAN_DRAW,

	/**
	 * The interior is visible and editable and the user could select objects on
	 * the draw pane
	 */
	INTERIOR_SELECT
}
