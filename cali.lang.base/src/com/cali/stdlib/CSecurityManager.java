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

import java.util.ArrayList;

import com.cali.Environment;
import com.cali.types.CaliType;

/**
 * Cali security manager. This class is the implementation within Cali that gives access 
 * to the set/get prop functions from the security manager within the current Engine.
 * @author Austin Lehman
 */
public class CSecurityManager {
	/**
	 * Cali setProperty. This method by default returns an exception 
	 * because we don't normally want the application code modifying the 
	 * contents of the security manager. This can be overridden if other 
	 * functionality is desired.
	 */
	public CaliType setProp(Environment env, ArrayList<CaliType> args) {
		return env.getEngine().getSecurityManager().setProp(env, args);
	}
	
	/**
	 * Cali getProperty. This method will get the property, match it to a 
	 * standard CaliType and return it.
	 */
	public CaliType getProp(Environment env, ArrayList<CaliType> args) {
		return env.getEngine().getSecurityManager().getProp(env, args);
	}
}
