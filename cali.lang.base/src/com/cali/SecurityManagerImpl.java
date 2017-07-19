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

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.cali.types.CaliBool;
import com.cali.types.CaliDouble;
import com.cali.types.CaliException;
import com.cali.types.CaliInt;
import com.cali.types.CaliNull;
import com.cali.types.CaliString;
import com.cali.types.CaliType;

/**
 * Default implementation of the security manager. This can be extended
 * to implement custom security manager functionality or properties. This 
 * object is provided in the cali environment as secman object.
 * @author Austin Lehman
 */
public class SecurityManagerImpl implements SecurityManagerInt {
	/**
	 * Holds the properties for the security manager.
	 */
	protected ConcurrentHashMap<String, Object> props = new ConcurrentHashMap<String, Object>();

	/**
	 * Default constructor adds the standard properties.
	 */
	public SecurityManagerImpl() {
		// System information view. See com.cali.stdlib.CSys.java.
		this.props.put("os.info.view", false);
		this.props.put("java.info.view", false);
		this.props.put("java.home.view", false);
		this.props.put("java.classpath.view", false);
		this.props.put("cali.info.view", false);
		this.props.put("cali.path.view", false);
		this.props.put("current.path.view", false);
		this.props.put("home.path.view", false);
		this.props.put("user.name.view", false);
	}
	
	/**
	 * Java get property.
	 */
	@Override
	public Object getProperty(String PropName) {
		return this.props.get(PropName);
	}

	/**
	 * Cali setProperty. This method by default returns an exception 
	 * because we don't normally want the application code modifying the 
	 * contents of the security manager. This can be overridden if other 
	 * functionality is desired.
	 */
	@Override
	public CaliType setProp(Environment env, ArrayList<CaliType> args) {
		return new CaliException("secman.setProp(): Setting property not allowed.");
	}

	/**
	 * Cali getProperty. This method will get the property, match it to a 
	 * standard CaliType and return it.
	 */
	@Override
	public CaliType getProp(Environment env, ArrayList<CaliType> args) {
		String PropName = ((CaliString)args.get(0)).getValueString();
		Object obj = this.props.get(PropName);
		if (obj == null) {
			return new CaliNull();
		} else if (obj instanceof Boolean) {
			return new CaliBool((Boolean)obj);
		} else if (obj instanceof Long) {
			return new CaliInt((Long)obj);
		} else if (obj instanceof Double) {
			return new CaliDouble((Double)obj);
		} else if (obj instanceof String) {
			return new CaliString((String)obj);
		} else {
			return new CaliString(obj.toString());
		}
	}
	
}
