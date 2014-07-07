package de.hsrm.mi.swt.grundreisser.business.floor;

import java.io.Serializable;
import java.util.List;

import de.hsrm.mi.swt.grundreisser.business.command.wall.CommandManager;
import de.hsrm.mi.swt.grundreisser.business.floor.groundplan.GroundPlan;
import de.hsrm.mi.swt.grundreisser.business.floor.interior.Interior;
import de.hsrm.mi.swt.grundreisser.business.validation.ValidationListener;

/**
 * The Floor-interface describes the structure of objects that are placed on the
 * outline and manages the ground plan and interior
 * 
 * @author nmuel002
 *
 */
public interface Floor extends ValidationListener, Serializable {

	/**
	 * Returns all objects that are placed on the floor
	 * 
	 * @return a list of placed objects
	 */
	public List<PlacedObject> getPlacedObjects();

	public void load();

	/**
	 * Returns the interior
	 * 
	 * @return interior
	 */
	public Interior getInterior();

	/**
	 * Returns a ground plan
	 * 
	 * @return ground plan
	 */
	public GroundPlan getGroundPlan();

	/**
	 * Returns the command manager of the floor
	 * 
	 * @return command manager
	 */
	public CommandManager getCommandManager();

	/**
	 * Persists all objects on the floor
	 */
	public void close();
}
