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
import java.util.Collections;

import com.cali.Environment;
import com.cali.Universe;
import com.cali.Util;
import com.cali.ast.caliException;
import com.cali.stdlib.console;
import com.cali.types.CaliListComparator.SortOrder;

public class CaliList extends CaliObject implements CaliTypeInt, CaliTypeObjectInt {
	private ArrayList<CaliType> value = new ArrayList<CaliType>();
	
	public CaliList() {
		this(true);
	}
	
	public CaliList(boolean LinkClass) {
		// Call parent CaliObject constructor with passed LinkClass arg or end up with 
		// Universe freaking out because it can't find object class reference.
		super(LinkClass);
		
		this.setType(cType.cList);
		
		if (LinkClass) {
			// Setup linkage for string object.
			this.setExternObject(this);
			try {
				this.setClassDef(Universe.get().getClassDef("list"));
			} catch (caliException e) {
				console.get().err("CaliList(): Unexpected exception getting class definition: " + e.getMessage());
			}
		}
	}
	
	public CaliList(ArrayList<CaliType> Value) {
		this();
		this.value = Value;
	}

	public synchronized void add(CaliType Val) {
		this.value.add(Val);
	}
	
	public synchronized int size() {
		return this.value.size();
	}
	
	public synchronized ArrayList<CaliType> getValue() {
		return value;
	}

	public synchronized void setValue(ArrayList<CaliType> value) {
		this.value = value;
	}

	@Override
	public synchronized String toString(int Level) {
		String rstr = "";

		rstr += CaliType.getTabs(Level) + "{\n";
		rstr += CaliType.getTabs(Level + 1) + "\"type\": \"" + this.getType().name() + "\",\n";
		rstr += CaliType.getTabs(Level + 1) + "\"length\": " + this.value.size() + ",\n";
		rstr += CaliType.getTabs(Level + 1) + "\"values\": [\n";
		for (int i = 0; i < this.value.size(); i++) {
		  rstr += ((CaliTypeInt)this.value.get(i)).toString(Level + 2) + ",\n";
		}
		rstr += CaliType.getTabs(Level + 1) + "]\n";
		rstr += CaliType.getTabs(Level) + "}";

		return rstr;
	}

	@Override
	public synchronized String str() {
		return this.str(0);
	}
	
	public synchronized String str(int Level) {
		if (this.value.size() > 0) {
			String rstr = "[\n";
			int count = 0;
			for (CaliType child : this.value) {
				rstr += getTabs(Level + 1) + ((CaliObject)child).str(Level + 1);
				count++;
				if (count < this.value.size()) {
					rstr += ",";
				}
				rstr += "\n";
			}
			rstr += getTabs(Level) + "]";
			return rstr;
		} else {
			return "[]";
		}
	}
	
	public synchronized CaliType add(Environment env, ArrayList<CaliType> args) {
		this.value.add(args.get(0));
		return this;
	}
	
	public synchronized CaliType addAll(Environment env, ArrayList<CaliType> args) {
		this.value.addAll(((CaliList)args.get(0)).getValue());
		return this;
	}
	
	public synchronized CaliType addAllAt(Environment env, ArrayList<CaliType> args) {
		this.value.addAll((int)((CaliInt)args.get(1)).getValue(), ((CaliList)args.get(0)).getValue());
		return this;
	}
	
	public synchronized CaliType clear(Environment env, ArrayList<CaliType> args) {
		this.value.clear();
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized CaliType clone(Environment env, ArrayList<CaliType> args) {
		CaliList nl = new CaliList();
		nl.setValue((ArrayList<CaliType>) this.value.clone());
		return nl;
	}
	
	public synchronized CaliType contains(Environment env, ArrayList<CaliType> args) {
		CaliType mn = args.get(0);
		
		for(CaliType n : this.value) {
			if(mn.isNull()) {
				if(n.isNull()) {
					return new CaliBool(true);
				}
			} else if(mn instanceof CaliBool) { 
				if((n instanceof CaliBool)&&(((CaliBool)n).getValue() == ((CaliBool)mn).getValue())) {
					return new CaliBool(true);
				}
			} else if(mn instanceof CaliInt) {
				if((n instanceof CaliInt)&&((CaliInt)n).getValue() == ((CaliInt)mn).getValue()) {
					return new CaliBool(true);
				}
			} else if(mn instanceof CaliDouble) {
				if((n instanceof CaliDouble)&&(((CaliDouble)n).getValue() == ((CaliDouble)mn).getValue())) {
					return new CaliBool(true);
				}
			} else if(mn instanceof CaliString) {
				if((n instanceof CaliString)&&(((CaliString)n).getValue().equals(((CaliString)mn).getValue()))) {
					return new CaliBool(true);
				}
			} else if(mn instanceof CaliCallback) {
				if((n instanceof CaliCallback)&&(((CaliCallback)n).getFunctName().equals(((CaliCallback)mn).getFunctName()))) {
					return new CaliBool(true);
				}
			}
		}
		
		return new CaliBool(false);
	}
	
	public synchronized CaliType containsObjRef(Environment env, ArrayList<CaliType> args) {
		return new CaliBool(this.value.contains(args.get(0)));
	}
	
	public synchronized CaliType get(Environment env, ArrayList<CaliType> args) {
		int index = (int) ((CaliInt)args.get(0)).getValue();
		if(index >= 0 && index < this.value.size()) {
			return this.value.get(index);
		} else {
			return new CaliException("list.get(): Index out of bounds.");
		}
	}
	
	public synchronized CaliType indexOf(Environment env, ArrayList<CaliType> args) {
		return new CaliInt(this.value.indexOf(args.get(0)));
	}
	
	public synchronized CaliType isEmpty(Environment env, ArrayList<CaliType> args) {
		return new CaliBool(this.value.isEmpty());
	}
	
	public synchronized CaliType remove(Environment env, ArrayList<CaliType> args) {
		this.value.remove(args.get(0));
		return this;
	}
	
	public synchronized CaliType removeAt(Environment env, ArrayList<CaliType> args) {
		int index = (int) ((CaliInt)args.get(0)).getValue();
		if(index >= 0 && index < this.value.size()) {
			return (CaliType)this.value.remove(index);
		} else {
			return new CaliException("list.removeAt(): Index out of bounds.");
		}
	}
	
	public synchronized CaliType removeAll(Environment env, ArrayList<CaliType> args) {
		return new CaliBool(this.value.removeAll(((CaliList)args.get(0)).getValue()));
	}
	
	public synchronized CaliType retainAll(Environment env, ArrayList<CaliType> args) {
		return new CaliBool(this.value.retainAll(((CaliList)args.get(0)).getValue()));
	}
	
	public synchronized CaliType set(Environment env, ArrayList<CaliType> args) {
		int index = (int) ((CaliInt)args.get(0)).getValue();
		if(index >= 0 && index < this.value.size()) {
			return this.value.set(index, args.get(1));
		} else {
			return new CaliException("list.set(): Index out of bounds.");
		}
	}
	
	public synchronized CaliType size(Environment env, ArrayList<CaliType> args) {
		return new CaliInt(this.value.size());
	}
	
	public synchronized CaliType subList(Environment env, ArrayList<CaliType> args) {
		int bindex = (int)((CaliInt)args.get(0)).getValue();
		int eindex = (int)((CaliInt)args.get(1)).getValue();
		if(eindex >= bindex && bindex >= 0 && eindex <= this.value.size()) {
			CaliList ret = new CaliList();
			ret.setValue(new ArrayList<CaliType>(this.value.subList(bindex, eindex)));
			return ret;
		} else {
			return new CaliException("list.subList(): Index out of bounds.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public synchronized CaliType sort(Environment env, ArrayList<CaliType> args) {
		CaliListComparator lc = new CaliListComparator();
		Collections.sort(this.value, lc);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized CaliType sortAsc(Environment env, ArrayList<CaliType> args) {
		CaliListComparator lc = new CaliListComparator();
		lc.setSortOrder(SortOrder.ASCENDING);
		Collections.sort(this.value, lc);
		return this;
	}
	
	public synchronized CaliType join(Environment env, ArrayList<CaliType> args) {
		String glue = ((CaliString)args.get(0)).getValue();
		ArrayList<String> parts = new ArrayList<String>();
		
		for (CaliType itm : this.value) {
			parts.add(((CaliTypeInt)itm).str());
		}
		return new CaliString(Util.join(parts, glue));
	}
	
	@SuppressWarnings("unchecked")
	public synchronized CaliType sortCustom(Environment env, ArrayList<CaliType> args) {
		CaliCallback onCompare = (CaliCallback)args.get(0);
		
		CaliListComparator lc = new CaliListComparator();
		lc.setSortOrder(SortOrder.CUSTOM);
		lc.setCallback(onCompare);
		
		try {
			Collections.sort(this.value, lc); 
		} catch(Exception e) {
			return new CaliException(e.getMessage());
		}
		
		return this;
	}
	
	@Override
	public CaliType toJson(Environment env, ArrayList<CaliType> args) {
		ArrayList<String> parts = new ArrayList<String>();
		for (CaliType ct : this.value) {
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
				parts.add(((CaliTypeObjectInt)ct).toJson(env, new ArrayList<CaliType>()).getValueString());
			} else {
				return new CaliException("Unexpected type found '" + ct.getType().name() + "' when converting to JSON.");
			}
		}
		return new CaliString("[" + Util.join(parts, ",") + "]");
	}
	
	@Override
	public CaliType pack(Environment env, ArrayList<CaliType> args) {
		ArrayList<String> parts = new ArrayList<String>();
		
		// Object metadata.
		parts.add("\"type\":\"" + this.getClassDef().getName() + "\"");
		
		ArrayList<String> mparts = new ArrayList<String>();
		for (CaliType ct : this.value) {
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
				mparts.add(((CaliTypeObjectInt)ct).toJson(env, new ArrayList<CaliType>()).getValueString());
			} else {
				return new CaliException("Unexpected type found '" + ct.getType().name() + "' when converting to JSON.");
			}
		}
		parts.add("\"value\":[" + Util.join(mparts, ",") + "]");
		
		return new CaliString("{" + Util.join(parts, ",") + "}");
	}
}
