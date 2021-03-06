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

public class CaliDouble extends CaliObject implements CaliTypeInt, CaliTypeObjectInt {
	private double value = 0.0;
	
	public CaliDouble() {
		this.setType(cType.cDouble);
		
		// Setup linkage for string object.
		this.setExternObject(this);
		try {
			this.setClassDef(Universe.get().getClassDef("double"));
		} catch (caliException e) {
			console.get().err("CaliDouble(): Unexpected exception getting class definition: " + e.getMessage());
		}
	}
	
	public CaliDouble(double Value) {
		this();
		this.value = Value;
	}
	
	public void setValue(double Value) {
		this.value = Value;
	}
	
	public double getValue() {
		return this.value;
	}

	@Override
	public String toString(int Level) {
		String rstr = "";
		rstr += CaliType.getTabs(Level) + "{\n";
		rstr += CaliType.getTabs(Level + 1) + "\"type\": \"" + this.getType().name() + "\",\n";
		rstr += CaliType.getTabs(Level + 1) + "\"value\": " + this.value + "\n";
		rstr += CaliType.getTabs(Level) + "}";
		return rstr;
	}

	@Override
	public String str() {
		return "" + this.value;
	}
	
	public String str(int Level) {
		return this.str();
	}
	
	public CaliType toInt(Environment env, ArrayList<CaliType> args) {
		return new CaliInt((int)this.value);
	}
	
	public CaliType toBool(Environment env, ArrayList<CaliType> args) {
		if (this.value == 0.0) {
			return new CaliBool(false);
		}
		return new CaliBool(true);
	}
	
	public CaliType toString(Environment env, ArrayList<CaliType> args) {
		return new CaliString("" + this.value);
	}
	
	public CaliType compare(Environment env, ArrayList<CaliType> args) {
		return new CaliInt(Double.compare(this.value, ((CaliDouble)args.get(0)).getValue()));
	}
	
	public CaliType isInfinite(Environment env, ArrayList<CaliType> args) {
		return new CaliBool(Double.isInfinite(this.value));
	}
	
	public CaliType isNan(Environment env, ArrayList<CaliType> args) {
		return new CaliBool(Double.isNaN(this.value));
	}
	
	public CaliType parse(Environment env, ArrayList<CaliType> args) {
		try {
			return new CaliDouble(Double.parseDouble(((CaliString)args.get(0)).getValue()));
		} catch(Exception e) {
			return new CaliException("double.parse(): Double parse exception.");
		}
	}
	
	public CaliType toHex(Environment env, ArrayList<CaliType> args) {
		return new CaliString(Double.toHexString(this.value));
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
