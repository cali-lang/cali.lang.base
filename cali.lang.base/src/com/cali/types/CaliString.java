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
import com.cali.stdlib.console;

public class CaliString extends CaliObject implements CaliTypeInt {
	private String value = "";
	
	public CaliString() {
		this.setType(cType.cString);
		
		// Setup linkage for string object.
		this.setExternObject(this);
		try {
			this.setClassDef(Universe.get().getClassDef("string"));
		} catch (caliException e) {
			console.get().err("CaliString(): Unexpected exception getting class definition: " + e.getMessage());
		}
	}
	
	public CaliString(String Value) {
		this();
		this.value = Value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString(int Level) {
		String rstr = "";
		rstr += CaliType.getTabs(Level) + "{\n";
		rstr += CaliType.getTabs(Level + 1) + "\"type\": \"" + this.getType().name() + "\",\n";
		rstr += CaliType.getTabs(Level + 1) + "\"value\": \"" + this.value + "\"\n";
		rstr += CaliType.getTabs(Level) + "}";
		return rstr;
	}

	@Override
	public String str() {
		return this.value;
	}
	
	public String str(int Level) {
		return "\"" + this.str() + "\"";
	}
	
	public CaliType charAt(ArrayList<CaliType> args) throws caliException {
		int index = (int) ((CaliInt)args.get(0)).getValue();
		if (index >= 0 && index < this.value.length()) {
			return new CaliString("" + this.value.charAt(index));
		} else {
			throw new caliException("Index " + index + " out of bounds." );
		}
	}
	
	public CaliType compare(ArrayList<CaliType> args) {
		return new CaliInt(this.value.compareTo(((CaliString)args.get(0)).getValue()));
	}
	
	public CaliType compareICase(ArrayList<CaliType> args) {
		return new CaliInt(this.value.compareToIgnoreCase(((CaliString)args.get(0)).getValue()));
	}
	
	public CaliType concat(ArrayList<CaliType> args) {
		this.value += ((CaliString)args.get(0)).getValue();
		return this;
	}
	
	public CaliType contains(ArrayList<CaliType> args) {
		return new CaliBool(this.value.contains(((CaliString)args.get(0)).getValue()));
	}
	
	public CaliType endsWith(ArrayList<CaliType> args) {
		return new CaliBool(this.value.endsWith(((CaliString)args.get(0)).getValue()));
	}
	
	public CaliType equals(ArrayList<CaliType> args) {
		return new CaliBool(this.value.equals(((CaliString)args.get(0)).getValue()));
	}
	
	public CaliType equalsICase(ArrayList<CaliType> args) {
		return new CaliBool(this.value.equalsIgnoreCase(((CaliString)args.get(0)).getValue()));
	}
	
	public CaliType indexOf(ArrayList<CaliType> args) {
		return new CaliInt(this.value.indexOf(((CaliString)args.get(0)).getValue()));
	}
	
	public CaliType indexOfStart(ArrayList<CaliType> args) {
		return new CaliInt(this.value.indexOf(((CaliString)args.get(0)).getValue(), (int)((CaliInt)args.get(1)).getValue()));
	}
	
	public CaliType isEmpty(ArrayList<CaliType> args) {
		return new CaliBool(this.value.isEmpty());
	}
	
	public CaliType lastIndexOf(ArrayList<CaliType> args) {
		return new CaliInt(this.value.lastIndexOf(((CaliString)args.get(0)).getValue()));
	}
	
	public CaliType lastIndexOfStart(ArrayList<CaliType> args) {
		return new CaliInt(this.value.lastIndexOf(((CaliString)args.get(0)).getValue(), (int)((CaliInt)args.get(1)).getValue()));
	}
	
	public CaliType length(ArrayList<CaliType> args) {
		return new CaliInt(this.value.length());
	}
	
	public CaliType matches(ArrayList<CaliType> args) {
		return new CaliBool(this.value.matches(((CaliString)args.get(0)).getValue()));
	}
	
	public CaliType replace(ArrayList<CaliType> args) {
		return new CaliString(this.value.replace(((CaliString)args.get(0)).getValue(), ((CaliString)args.get(1)).getValue()));
	}
	
	public CaliType replaceFirstRegex(ArrayList<CaliType> args) {
		return new CaliString(this.value.replaceFirst(((CaliString)args.get(0)).getValue(), ((CaliString)args.get(1)).getValue()));
	}
	
	public CaliType replaceRegex(ArrayList<CaliType> args) {
		return new CaliString(this.value.replaceAll(((CaliString)args.get(0)).getValue(), ((CaliString)args.get(1)).getValue()));
	}
	
	public CaliType split(ArrayList<CaliType> args) {
		CaliList ret = new CaliList();
		boolean allowBlanks = ((CaliBool)args.get(1)).getValue();
		String parts[] = this.value.split(((CaliString)args.get(0)).getValue());
		for (String part : parts) {
			if (allowBlanks || !part.trim().equals("")) {
				ret.add(new CaliString(part));
			}
		}
		return ret;
	}
	
	public CaliType startsWith(ArrayList<CaliType> args) {
		if (args.size() > 1) {
			return new CaliBool(this.value.startsWith(((CaliString)args.get(0)).getValue(), (int)((CaliInt)args.get(1)).getValue()));
		} else {
			return new CaliBool(this.value.startsWith(((CaliString)args.get(0)).getValue()));
		}
	}
	
	public CaliType substr(ArrayList<CaliType> args) {
		if (args.get(1).isNull()) {
			return new CaliString(this.value.substring((int)((CaliInt)args.get(0)).getValue()));
		} else {
			return new CaliString(this.value.substring((int)((CaliInt)args.get(0)).getValue(), (int)((CaliInt)args.get(1)).getValue()));
		}
	}
	
	public CaliType toLower(ArrayList<CaliType> args) {
		return new CaliString(this.value.toLowerCase());
	}
	
	public CaliType toUpper(ArrayList<CaliType> args) {
		return new CaliString(this.value.toUpperCase());
	}
	
	public CaliType trim(ArrayList<CaliType> args) {
		return new CaliString(this.value.trim());
	}
}
