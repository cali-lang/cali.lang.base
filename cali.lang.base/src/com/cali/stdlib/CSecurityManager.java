/*
 * Copyright 2017 Austin Lehman
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
	 * @throws caliException if there's an attempt to instantiate the security manager.
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
