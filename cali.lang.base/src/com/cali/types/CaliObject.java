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

package com.cali.types;

import java.util.ArrayList;

import com.cali.Environment;
import com.cali.Universe;
import com.cali.Util;
import com.cali.ast.astClass;
import com.cali.ast.caliException;
import com.cali.stdlib.console;

public class CaliObject extends CaliType implements CaliTypeInt, CaliTypeObjectInt {
	private astClass classDef;
	private Members members = new Members();
	
	private Object externObject = null;
	
	public CaliObject() {
		this(true);
	}
	
	public CaliObject(boolean LinkClass) {
		this.setType(cType.cObject);
		
		if (LinkClass) {
			// Setup linkage for string object.
			this.setExternObject(this);
			try {
				this.setClassDef(Universe.get().getClassDef("object"));
			} catch (caliException e) {
				console.get().err("CaliObject(): Unexpected exception getting class definition: " + e.getMessage());
			}
		}
	}

	public astClass getClassDef() {
		return classDef;
	}

	public void setClassDef(astClass classDef) {
		this.classDef = classDef;
	}
	
	public Object getExternObject() {
		return externObject;
	}

	public void setExternObject(Object externObject) {
		this.externObject = externObject;
	}

	public void addMember(String Key, CaliType Value) {
		this.members.add(Key, Value);
	}
	
	public Members getMembers() {
		return this.members;
	}
	
	@Override
	public String toString(int Level) {
		String rstr = "";

		rstr += getTabs(Level);
		rstr += "line ";
		rstr += this.getClassDef().getLineNum();
		rstr += ": ";
		rstr += "[";
		rstr += this.getType().name();
		rstr += "] classDef='";
		if(this.classDef != null)
			rstr += this.getClassDef().getName();
		else
			rstr += "undef";
		rstr += "'";
		if(this.getClassDef().getName() != "")
			rstr += " name='" + this.getClassDef().getName() + "'";
		rstr += "\n";

		if(this.members != null)
			rstr += this.members.toString(Level);

		return rstr;
	}

	@Override
	public String str() {
		return this.str(0);
	}
	
	public String str(int Level) {
		if (this.members.getMap().size() > 0) {
			String rstr = "{\n";
			int count = 0;
			for (String name : this.members.getMap().keySet()) {
				rstr += getTabs(Level + 1) + "'" + name + "': ";
				CaliType child = this.members.get(name);
				rstr += ((CaliObject)child).str(Level + 1);
				count++;
				if (count < this.members.getMap().size()) {
					rstr += ",";
				}
				rstr += "\n";
			}
			rstr += getTabs(Level) + "}";
			return rstr;
		} else {
			return "{}";
		}
	}
	
	public String str(Environment env) throws caliException {
		if (this.getClassDef().containsFunction("toString")) {
			astClass ac = this.getClassDef();
			Environment tenv = env.clone(this);
			CaliType ret = ac.call(tenv, false, "toString", new CaliList());
			if (ret.getType() == cType.cString) {
				return ((CaliString)ret).getValue();
			} else if (ret.isEx()) {
			  System.out.println(((CaliException)ret).stackTraceToString());
			}
		  }
		return "cObject@" + Integer.toHexString(System.identityHashCode(this));
	}
	
	public CaliType toJson(Environment env, ArrayList<CaliType> args) {
		ArrayList<String> parts = new ArrayList<String>();
		for (String key : this.members.getMap().keySet()) {
			CaliType ct = this.members.get(key);
			if (
					ct instanceof CaliBool
					|| ct instanceof CaliNull
					|| ct instanceof CaliInt
					|| ct instanceof CaliDouble
					|| ct instanceof CaliString
					|| ct instanceof CaliList
					|| ct instanceof CaliMap
					|| ct instanceof CaliObject
				) {
				parts.add("\"" + key + "\":" + ((CaliTypeObjectInt)ct).toJson(env, new ArrayList<CaliType>()).getValueString());
				
			} else {
				return new CaliException("Unexpected type found '" + ct.getType().name() + "' when converting to JSON.");
			}
		}
		return new CaliString("{" + Util.join(parts, ",") + "}");
	}
	
	public CaliType pack(Environment env, ArrayList<CaliType> args) {
		ArrayList<String> parts = new ArrayList<String>();
		
		// Object metadata.
		parts.add("\"type\":\"" + this.getClassDef().getName() + "\"");
		
		ArrayList<String> mparts = new ArrayList<String>();
		for (String key : this.members.getMap().keySet()) {
			CaliType ct = this.members.get(key);
			if (
					ct instanceof CaliBool
					|| ct instanceof CaliNull
					|| ct instanceof CaliInt
					|| ct instanceof CaliDouble
					|| ct instanceof CaliString
					|| ct instanceof CaliList
					|| ct instanceof CaliMap
					|| ct instanceof CaliObject
				) {
				mparts.add("\"" + key + "\":" + ((CaliTypeObjectInt)ct).pack(env, new ArrayList<CaliType>()).getValueString());
				
			} else {
				return new CaliException("Unexpected type found '" + ct.getType().name() + "' when packing object.");
			}
		}
		parts.add("\"members\":{" + Util.join(mparts, ",") + "}");
		
		return new CaliString("{" + Util.join(parts, ",") + "}");
	}
}
