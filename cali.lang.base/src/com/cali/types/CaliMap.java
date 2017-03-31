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
import java.util.concurrent.ConcurrentHashMap;

import com.cali.Universe;
import com.cali.ast.caliException;
import com.cali.stdlib.console;

public class CaliMap extends CaliObject implements CaliTypeInt {
	private ConcurrentHashMap<String, CaliType> value = new ConcurrentHashMap<String, CaliType>();
	
	public CaliMap() {
		this(true);
	}
	
	public CaliMap(boolean LinkClass) {
		this.setType(cType.cMap);
		
		if (LinkClass) {
			// Setup linkage for string object.
			this.setExternObject(this);
			try {
				this.setClassDef(Universe.get().getClassDef("map"));
			} catch (caliException e) {
				console.get().err("CaliMap(): Unexpected exception getting class definition: " + e.getMessage());
			}
		}
	}
	
	public CaliMap(ConcurrentHashMap<String, CaliType> Value) {
		this(true);
		this.value = Value;
	}

	public ConcurrentHashMap<String, CaliType> getValue() {
		return value;
	}

	public void setValue(ConcurrentHashMap<String, CaliType> value) {
		this.value = value;
	}

	public int size() {
		return this.value.size();
	}
	
	public boolean contains(String Key) {
		return this.value.containsKey(Key);
	}
	
	public void put(String Key, CaliType Val) {
		this.value.put(Key, Val);
	}
	
	@Override
	public String toString(int Level) {
		String rstr = "";

		rstr += CaliType.getTabs(Level) + "{\n";
		rstr += CaliType.getTabs(Level + 1) + "\"type\": \"" + this.getType().name() + "\",\n";
		rstr += CaliType.getTabs(Level + 1) + "\"values\": {\n";
		for (String Key : this.value.keySet()) {
			CaliType Val = this.value.get(Key);
			rstr += CaliType.getTabs(Level + 2) + Key + ":\n";
			rstr += ((CaliTypeInt)Val).toString(Level + 2) + ",\n";
		}
		rstr += CaliType.getTabs(Level + 1) + "}\n";
		rstr += CaliType.getTabs(Level) + "}";

		return rstr;
	}

	@Override
	public String str() {
		return this.str(0);
	}
	
	public String str(int Level) {
		if (this.value.size() > 0) {
			String rstr = "{\n";
			int count = 0;
			for (String name : this.value.keySet()) {
				rstr += getTabs(Level + 1) + "\"" + name + "\": ";
				CaliType child = this.value.get(name);
				rstr += ((CaliObject)child).str(Level + 1);
				count++;
				if (count < this.value.size()) {
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
	
	public CaliType clear(ArrayList<CaliType> args) {
		this.value.clear();
		return this;
	}
	
	public CaliType containsKey(ArrayList<CaliType> args) {
		return new CaliBool(this.value.containsKey(((CaliString)args.get(0)).getValue()));
	}
	
	public CaliType containsVal(ArrayList<CaliType> args) {
		return new CaliBool(this.value.containsValue(args.get(0)));
	}
	
	public CaliType get(ArrayList<CaliType> args) {
		return this.value.get(((CaliString)args.get(0)).getValue());
	}
	
	public CaliType isEmpty(ArrayList<CaliType> args) {
		return new CaliBool(this.value.isEmpty());
	}
	
	public CaliType keySet(ArrayList<CaliType> args) {
		CaliList lst = new CaliList();
		for (String str : this.value.keySet()) {
			lst.add(new CaliString(str));
		}
		return lst;
	}
	
	public CaliType put(ArrayList<CaliType> args) {
		this.value.put(((CaliString)args.get(0)).getValue(), args.get(1));
		return this;
	}
	
	public CaliType putAll(ArrayList<CaliType> args) {
		CaliMap mp = (CaliMap)args.get(0);
		this.value.putAll(mp.getValue());
		return this;
	}
	
	public CaliType putIfAbsent(ArrayList<CaliType> args) {
		this.value.putIfAbsent(((CaliString)args.get(0)).getValue(), args.get(1));
		return this;
	}
	
	public CaliType remove(ArrayList<CaliType> args) {
		Object ret = this.value.remove(((CaliString)args.get(0)).getValue());
		if (ret == null) {
			return new CaliNull();
		} else {
			return (CaliType) ret;
		}
	}
	
	public CaliType size(ArrayList<CaliType> args) {
		return new CaliInt(this.value.size());
	}
	
	public CaliType values(ArrayList<CaliType> args) {
		CaliList lst = new CaliList();
		for (String str : this.value.keySet()) {
			lst.add(this.value.get(str));
		}
		return lst;
	}
}
