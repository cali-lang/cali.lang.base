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
import com.cali.types.CaliList;
import com.cali.types.CaliMap;
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
		/*
		 * Security manager itself.
		 */
		// instantiate - can new instances be created from this one? This 
		// normally applies to ones instantiated from within cali and are blocked 
		// in CSecurityManager sub-class constructor.
		this.props.put("securitymanager.instantiate", true);
		
		// getProp
		this.props.put("securitymanager.property.get", true);
		
		// keySet/getMap
		this.props.put("securitymanager.property.list", true);
		
		// setProp
		this.props.put("securitymanager.property.set", false);
		
		/*
		 *  System information view. See com.cali.stdlib.CSys.java.
		 */
		this.props.put("os.info.view", false);
		this.props.put("java.info.view", false);
		this.props.put("java.home.view", false);
		this.props.put("java.classpath.view", false);
		this.props.put("cali.info.view", false);
		this.props.put("cali.path.view", false);
		this.props.put("current.path.view", false);
		this.props.put("home.path.view", false);
		this.props.put("user.name.view", false);
		
		/*
		 *  Reflection actions. See com.cali.stdlib.CReflect.java.
		 */
		this.props.put("reflect.eval.string", false);
		this.props.put("reflect.eval.file", false);
		this.props.put("reflect.include.module", false);
	}
	
	/**
	 * Java get property.
	 */
	@Override
	public Object getProperty(String PropName) {
		return this.props.get(PropName);
	}

	/**
	 * Cali getProperty. This method will get the property, match it to a 
	 * standard CaliType and return it. If property 
	 * securitymanager.property.get is set to false this method will 
	 * throw a security exception. 
	 */
	@Override
	public CaliType getProp(Environment env, ArrayList<CaliType> args) {
		if ((Boolean)this.getProperty("securitymanager.property.get")) {
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
		} else {
			return new CaliException("securitymanager.getProp(): Security exception, action 'securitymanager.property.get' not permitted.");
		}
	}
	
	/**
	 * Gets the key set of the properties as a list of strings.
	 */
	@Override
	public CaliType keySet(Environment env, ArrayList<CaliType> args) {
		if ((Boolean)this.getProperty("securitymanager.property.list")) {
			CaliList cl = new CaliList();
			for (String key : this.props.keySet()) {
				cl.add(new CaliString(key));
			}
			return cl;
		} else {
			return new CaliException("securitymanager.keySet(): Security exception, action 'securitymanager.property.list' not permitted.");
		}
	}
	
	/**
	 * Gets a cali map of the security manager properties and their values.
	 */
	@Override
	public CaliType getMap(Environment env, ArrayList<CaliType> args) {
		if ((Boolean)this.getProperty("securitymanager.property.list")) {
			CaliMap cm = new CaliMap();
			for (String key : this.props.keySet()) {
				Object tobj = this.props.get(key);
				if (tobj == null) {
					cm.put(key, new CaliNull());
				} else if (tobj instanceof Boolean) {
					cm.put(key, new CaliBool((Boolean)tobj));
				} else if (tobj instanceof Long) {
					cm.put(key, new CaliInt((Long)tobj));
				} else if (tobj instanceof Double) {
					cm.put(key, new CaliDouble((Double)tobj));
				} else if (tobj instanceof String) {
					cm.put(key, new CaliString((String)tobj));
				} else {
					return new CaliException("securitymanager.getMap(): Expecting simpel type (bool, int, double, string, null) but found '" + tobj.getClass().getName() + "' instead for key '" + key + "'.");
				}
			}
			return cm;
		} else {
			return new CaliException("securitymanager.getMap(): Security exception, action 'securitymanager.property.list' not permitted.");
		}
	}
	
	/**
	 * Cali setProp. This method provides the ability 
	 * to set the property of a security manager property pair. If property 
	 * securitymanager.property.set is set to false this method will 
	 * throw a security exception. 
	 */
	@Override
	public CaliType setProp(Environment env, ArrayList<CaliType> args) {
		if ((Boolean)this.getProperty("securitymanager.property.set")) {
			String key = ((CaliString)args.get(0)).getValueString();
			CaliType ct = args.get(1);
			Object val = null;
			
			if (ct instanceof CaliBool) {
				val = ((CaliBool)ct).getValue();
			} else if (ct instanceof CaliString) {
				val = ((CaliString)ct).getValueString();
			} else if (ct instanceof CaliInt) {
				val = ((CaliInt)ct).getValue();
			} else if (ct instanceof CaliDouble) {
				val = ((CaliDouble)ct).getValue();
			} else if (ct instanceof CaliNull) {
				val = null;
			} else {
				return new CaliException("securitymanager.setProp(): Expecting simpel type (bool, int, double, string, null) but found '" + ct.getClass().getName() + "' instead.");
			}
			
			this.props.put(key, val);
			return env.getClassInstance();
		} else {
			return new CaliException("securitymanager.getProp(): Security exception, action 'securitymanager.property.set' not permitted.");
		}
	}
	
	/**
	 * Cali setMap. This method provides the ability 
	 * to set a whole map of key-val pairs. If property 
	 * securitymanager.property.set is set to false this method will 
	 * throw a security exception. 
	 */
	@Override
	public CaliType setMap(Environment env, ArrayList<CaliType> args) {
		if ((Boolean)this.getProperty("securitymanager.property.set")) {
			CaliMap mp = (CaliMap)args.get(0);
			for (String key : mp.getValue().keySet()) {
				CaliType ct = mp.getValue().get(key);
				Object val = null;
				
				if (ct instanceof CaliBool) {
					val = ((CaliBool)ct).getValue();
				} else if (ct instanceof CaliString) {
					val = ((CaliString)ct).getValueString();
				} else if (ct instanceof CaliInt) {
					val = ((CaliInt)ct).getValue();
				} else if (ct instanceof CaliDouble) {
					val = ((CaliDouble)ct).getValue();
				} else if (ct instanceof CaliNull) {
					val = null;
				} else {
					return new CaliException("securitymanager.setMap(): Expecting simpel type (bool, int, double, string, null) but found '" + ct.getClass().getName() + "' instead.");
				}
				
				this.props.put(key, val);
			}
			return env.getClassInstance();
		} else {
			return new CaliException("securitymanager.getProp(): Security exception, action 'securitymanager.property.set' not permitted.");
		}
	}
}
