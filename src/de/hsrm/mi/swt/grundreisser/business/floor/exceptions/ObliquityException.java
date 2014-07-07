package de.hsrm.mi.swt.grundreisser.business.floor.exceptions;

/**
 * Exception to be thrown if two points of an object are not horizontally and
 * nor vertically
 * 
 * @author nmuel002
 *
 */
@SuppressWarnings("serial")
public class ObliquityException extends ValidationException {

	public ObliquityException(String msg) {
		super(msg);
	}
}
