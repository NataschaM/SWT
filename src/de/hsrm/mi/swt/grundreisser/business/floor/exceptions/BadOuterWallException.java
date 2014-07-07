package de.hsrm.mi.swt.grundreisser.business.floor.exceptions;

/**
 * This exception has to thrown if the outer wall has no closed structure
 * 
 * @author nmuel002
 *
 */
public class BadOuterWallException extends Exception {

	public BadOuterWallException(String msg) {
		super(msg);
	}

}
