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
import com.cali.ast.caliException;
import com.cali.stdlib.console;

public class CaliBool extends CaliObject implements CaliTypeInt, CaliTypeObjectInt {
	private boolean value = false;
	
	public CaliBool() {
		this.setType(cType.cBool);
		
		// Setup linkage for string object.
		this.setExternObject(this);
		try {
			this.setClassDef(Universe.get().getClassDef("bool"));
		} catch (caliException e) {
			console.get().err("CaliBool(): Unexpected exception getting class definition: " + e.getMessage());
		}
	}
	
	public CaliBool(boolean Value) {
		this();
		this.setValue(Value);
	}

	public boolean getValue() {
		return this.value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
	
	@Override
	public String toString(int Level) {
		String rstr = "";
		rstr += CaliType.getTabs(Level) + "{\n";
		rstr += CaliType.getTabs(Level + 1) + "\"type\": \"" + this.getType().name() + "\",\n";
		rstr += CaliType.getTabs(Level + 1) + "\"value\": ";
		if (this.value) rstr += "true";
		else rstr += "false";
		rstr += "\n";
		rstr += CaliType.getTabs(Level) + "}";
		return rstr;
	}

	@Override
	public String str() {
		if (this.value) return new String("true");
		return new String("false");
	}
	
	public String str(int Level) {
		return this.str();
	}
	
	public CaliType toInt(Environment env, ArrayList<CaliType> args) {
		if (this.value) return new CaliInt(1);
		return new CaliInt(0);
	}
	
	public CaliType toDouble(Environment env, ArrayList<CaliType> args) {
		if (this.value) return new CaliDouble(1.0);
		return new CaliDouble(0.0);
	}
	
	public CaliType toString(Environment env, ArrayList<CaliType> args) {
		if (this.value) return new CaliString("true");
		return new CaliString("false");
	}
	
	public CaliType compare(Environment env, ArrayList<CaliType> args) {
		return new CaliInt(Boolean.compare(this.value, ((CaliBool)args.get(0)).getValue()));
	}
	
	public CaliType parse(Environment env, ArrayList<CaliType> args) {
		this.value = Boolean.parseBoolean(((CaliString)args.get(0)).getValue());
		return this;
	}
	
	@Override
	public CaliType toJson(Environment env, ArrayList<CaliType> args) {
		return this.toString(env, args);
	}
	
	@Override
	public CaliType pack(Environment env, ArrayList<CaliType> args) {
		ArrayList<String> parts = new ArrayList<String>();
		parts.add("\"type\":\"" + this.getClassDef().getName() + "\"");
		parts.add("\"value\":" + this.toString(env, args) + "");
		return new CaliString("{" + Util.join(parts, ",") + "}");
	}
}
