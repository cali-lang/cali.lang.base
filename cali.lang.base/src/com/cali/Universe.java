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

package com.cali;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.cali.ast.astClass;
import com.cali.ast.caliException;
import com.cali.stdlib.Lang;

/**
 * Univers singleton object manages program global objects for Cali Engines. Engines 
 * are setup in a manner that allows for multiple Engine instances per program. The Univers 
 * provides a global mechanism for all of the Engines.
 * @author austin
 */
public class Universe {
	/**
	 * The single Universe instance.
	 */
	static Universe instance = null;
	
	/**
	 * Flag defineing if the univers has completed initialization yet or not.
	 */
	private boolean initialized = false;
	
	/**
	 * Defines the Cali version.
	 */
	private static final String version = "1.0";
	
	/**
	 * Map of class definitions. This is used to hold the base lang clases. It allows 
	 * them to be parsed once and coppied to any subsequent Engine object when needed.
	 */
	private Map<String, astClass> classes = new ConcurrentHashMap<String, astClass>();
	
	/**
	 * Default constructor set to private to defeat instantiation. See get to get an 
	 * instance of the object.
	 */
	private Universe() { }
	
	/**
	 * Initializes the Universe object with the provided Engine. This function will use 
	 * the provided engine to Parse the Lang.langSrc code if not already initialized.
	 * @param eng is an Engine object.
	 * @throws Exception 
	 */
	public void init(Engine eng) throws Exception {
		if (!this.initialized) {
			// Load native class definitions here!
			eng.parseString("lang.ca", Lang.langSrc);
			this.classes = eng.getClasses();
			this.initialized = true;
		}
	}
	
	/**
	 * Gets a handle of the Universe object.
	 * @return
	 */
	public static Universe get() {
		if(instance == null) instance = new Universe();
		return instance;
	}
	
	/**
	 * Gets the Map of lang class definitions.
	 * @return a Map object with class definitions.
	 */
	public Map<String, astClass> getClasses() {
		return this.classes;
	}
	
	/**
	 * Gets the class definition of the provided class name.
	 * @param Name is a String with the class name to get.
	 * @return A astClass class definition.
	 * @throws caliException
	 */
	public astClass getClassDef(String Name) throws caliException {
		if (this.classes.containsKey(Name)) {
			return this.classes.get(Name);
		} else {
			throw new caliException("Cali Universe: Freaking out, can't find requested class def '" + Name + "'.");
		}
	}
	
	/**
	 * Gets the Cali version.
	 * @return A String with the Cali version.
	 */
	public static String getCaliVersion() {
		return version;
	}
}
