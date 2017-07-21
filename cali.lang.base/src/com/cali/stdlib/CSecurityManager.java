package com.cali.stdlib;

import com.cali.SecurityManagerImpl;
import com.cali.ast.caliException;

/**
 * Cali instance of the security manager extern object. This class 
 * provides an implementation of the SecurityManagerInt that's available 
 * to be used in Cali, instantiated and provided to an Engine object.
 * @author Austin Lehman
 */
public class CSecurityManager extends SecurityManagerImpl {
	
	/**
	 * Default constructor sets the props for the security manager to 
	 * true so they can be managed within cali.
	 * @throws caliException 
	 */
	public CSecurityManager() throws caliException {
		// Check to see if we are allowed to instantiate to begin with.
		if (!(Boolean)this.getProperty("securitymanager.instantiate")) {
			throw new caliException("securitymanager.(): Security exception, action 'securitymanager.instantiate' not permitted.");
		}
		
		// Security manager itself. - Set these to true so that the 
		// cali created security manager can have it's properties 
		// set and listed.
		this.props.put("securitymanager.property.get", true);
		this.props.put("securitymanager.property.list", true);
		this.props.put("securitymanager.property.set", true);
	}
}
