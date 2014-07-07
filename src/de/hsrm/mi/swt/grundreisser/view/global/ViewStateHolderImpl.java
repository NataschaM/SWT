package de.hsrm.mi.swt.grundreisser.view.global;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The default implementation of the ViewStateHolder
 * 
 * @author Simon Seyer
 * 
 */
public class ViewStateHolderImpl implements ViewStateHolder {

	private ViewState viewState;
	private List<View<?>> selectedViews;

	private PropertyChangeSupport changes = new PropertyChangeSupport(this);

	/**
	 * Create a new state holder
	 */
	public ViewStateHolderImpl() {
		viewState = ViewState.GROUND_PLAN_SELECT;
		selectedViews = new ArrayList<View<?>>();
	}

	@Override
	public ViewState getViewState() {
		return viewState;
	}

	@Override
	public void setViewState(ViewState viewState) {
		if (this.viewState == viewState) {
			return;
		}
		ViewState oldValue = this.viewState;
		this.viewState = viewState;
		setSelectedViews(null);
		changes.firePropertyChange("viewState", oldValue, viewState);
	}

	@Override
	public List<View<?>> getSelectedViews() {
		return new LinkedList<>(selectedViews);
	}

	@Override
	public void addSelectedView(View<?> view) {
		if (!selectedViews.contains(view)) {
			selectedViews.add(view);
			view.setSelected(true);
			changes.firePropertyChange("selectedViews", null, view);
		}
	}

	@Override
	public void removeSelectedView(View<?> view) {
		if (selectedViews.contains(view)) {
			selectedViews.remove(view);
			view.setSelected(false);
			changes.firePropertyChange("selectedViews", view, null);
		}
	}

	@Override
	public void setSelectedViews(List<View<?>> selectedViews) {
		List<View<?>> oldValue = this.selectedViews;
		List<View<?>> newValue = selectedViews;
		if (newValue == null) {
			newValue = new ArrayList<>(0);
		}

		for (View<?> view : oldValue) {
			view.setSelected(false);
		}
		for (View<?> view : newValue) {
			view.setSelected(true);
		}
		this.selectedViews = newValue;
		changes.firePropertyChange("selectedViews", oldValue, newValue);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}

}
