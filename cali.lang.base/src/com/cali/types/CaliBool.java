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

import com.cali.Universe;
import com.cali.ast.caliException;

public class CaliBool extends CaliObject implements CaliTypeInt {
	private boolean value = false;
	
	public CaliBool() {
		this.setType(cType.cBool);
		
		// Setup linkage for string object.
		this.setExternObject(this);
		try {
			this.setClassDef(Universe.get().getClassDef("bool"));
		} catch (caliException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public CaliType toInt(ArrayList<CaliType> args) {
		if (this.value) return new CaliInt(1);
		return new CaliInt(0);
	}
	
	public CaliType toDouble(ArrayList<CaliType> args) {
		if (this.value) return new CaliDouble(1.0);
		return new CaliDouble(0.0);
	}
	
	public CaliType toString(ArrayList<CaliType> args) {
		if (this.value) return new CaliString("true");
		return new CaliString("false");
	}
	
	public CaliType compare(ArrayList<CaliType> args) {
		return new CaliInt(Boolean.compare(this.value, ((CaliBool)args.get(0)).getValue()));
	}
	
	public CaliType parse(ArrayList<CaliType> args) {
		this.value = Boolean.parseBoolean(((CaliString)args.get(0)).getValue());
		return this;
	}
}
