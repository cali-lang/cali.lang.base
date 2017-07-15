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

public class CaliNull extends CaliObject implements CaliTypeInt, CaliTypeObjectInt {
	public CaliNull() {
		this.setType(cType.cNull);
		
		// Setup linkage for string object.
		this.setExternObject(this);
		try {
			this.setClassDef(Universe.get().getClassDef("cnull"));
		} catch (caliException e) {
			console.get().err("CaliNull(): Unexpected exception getting class definition: " + e.getMessage());
		}
	}

	@Override
	public String toString(int Level) {
		String rstr = "";
		rstr += CaliType.getTabs(Level) + "{\n";
		rstr += CaliType.getTabs(Level + 1) + "\"type\": \"" + this.getType().name() + "\",\n";
		rstr += CaliType.getTabs(Level + 1) + "\"value\": null\n";
		rstr += CaliType.getTabs(Level) + "}";
		return rstr;
	}

	@Override
	public String str() {
		return "null";
	}
	
	public String str(int Level) {
		return this.str();
	}
	
	@Override
	public CaliType toJson(Environment env, ArrayList<CaliType> args) {
		return new CaliString("null");
	}
	
	@Override
	public CaliType pack(Environment env, ArrayList<CaliType> args) {
		ArrayList<String> parts = new ArrayList<String>();
		parts.add("\"type\":\"" + this.getClassDef().getName() + "\"");
		parts.add("\"value\":null");
		return new CaliString("{" + Util.join(parts, ",") + "}");
	}
}
