package de.hsrm.mi.swt.grundreisser.business.validation.warnings;

/**
 * Common interface for the warnings which can be created as a result of
 * validation
 * 
 * @author nmuel002
 * 
 */
public interface ValidationWarning {

	/**
	 * Returns the warning message
	 * 
	 * @return warning message
	 */
	public String getMessage();

}
