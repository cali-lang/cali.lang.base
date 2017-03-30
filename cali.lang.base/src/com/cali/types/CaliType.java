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

import com.cali.Environment;
import com.cali.ast.caliException;

public class CaliType {
	
	private cType type = cType.cUndef;
	
	public CaliType() { }

	public cType getType() {
		return type;
	}

	public void setType(cType type) {
		this.type = type;
	}
	
	public boolean isEx() {
		if (this.type == cType.cException) { return true; }
		return false;
	}
	
	public boolean isNull() {
		if (this.type == cType.cNull) { return true; }
		return false;
	}
	
	public boolean isReturn() {
		if (this.type == cType.cReturn) { return true; }
		return false;
	}
	
	public boolean isBreak() {
		if (this.type == cType.cBreak) { return true; }
		return false;
	}
	
	@Override
	public String toString() {
		return ((CaliTypeInt)this).toString(0);
	}
	
	public static String getTabs(int Level) {
		String rstr = "";
		for (int i = 0; i < Level; i++) { rstr += "\t"; }
		return rstr;
	}
	
	public boolean isNumericType() {
		if(this.type == cType.cInt)
			return true;
		else if(this.type == cType.cDouble)
			return true;
		else if(this.type == cType.cBool)
			return true;
		return false;
	}
	
	public boolean getNumericBool() throws caliException {
		if(this.type == cType.cBool)
			return ((CaliBool)this).getValue();
		else if(this.type == cType.cInt) {
			if(((CaliInt)this).getValue() != 0)
				return true;
			return false;
		}
		else if(this.type == cType.cDouble) {
			if(((CaliDouble)this).getValue() != 0.0)
				return true;
			return false;
		} else {
			throw new caliException("INTERNAL [CaliType.getNumericBool] Not expecting type '" + this.type.name() + "'.");
		}
	}
	
	public long getNumericInt() throws caliException {
		if(this.type == cType.cInt)
			return ((CaliInt)this).getValue();
		else if(this.type == cType.cBool) {
			if(((CaliBool)this).getValue() == true)
				return 1;
			return 0;
		} else if(this.type == cType.cDouble) {
			return (int)((CaliDouble)this).getValue();
		} else {
			throw new caliException("INTERNAL [CaliType.getNumericInt] Not expecting type '" + this.type.name() + "'.");
		}
	}
	
	public double getNumericDouble() throws caliException {
		if(this.type == cType.cDouble)
			return ((CaliDouble)this).getValue();
		else if(this.type == cType.cInt) {
			return (double)((CaliInt)this).getValue();
		} else if(this.type == cType.cBool) {
			if(((CaliBool)this).getValue() == true)
				return 1.0;
			return 0.0;
		} else {
			throw new caliException("INTERNAL [CaliType.getNumericDouble] Not expecting type '" + this.type.name() + "'.");
		}
	}
	
	public String getValueString() {
		return ((CaliTypeInt)this).str();
	}
	
	public CaliBool compareEqual(Environment env, CaliType To) throws caliException {
		CaliBool ret = new CaliBool(false);
		
		if((this.type == cType.cNull)||(To.getType() == cType.cNull)) {
			if((this.type == cType.cNull)&&(To.getType() == cType.cNull))
				ret = new CaliBool(true);
			else
				ret = new CaliBool(false);
		} else {
			switch(this.type){
				case cBool:
					if(To.isNumericType()) {
						if(this.getNumericDouble() == To.getNumericDouble())
							ret = new CaliBool(true);
						else
							ret = new CaliBool(false);
					}
					else {
						throw new caliException("Attempting to compare objects of type '" + this.getType() + "' and '" + To.getType() + "'.");
					}
					break;
				case cInt:
					if(To.isNumericType()) {
						if(this.getNumericDouble() == To.getNumericDouble())
							ret = new CaliBool(true);
						else
							ret = new CaliBool(false);
					} else {
						throw new caliException("Attempting to compare objects of type '" + this.getType() + "' and '" + To.getType() + "'.");
					}
					break;
				case cDouble:
					if(To.isNumericType()) {
						if(this.getNumericDouble() == To.getNumericDouble())
							ret = new CaliBool(true);
						else
							ret = new CaliBool(false);
					} else {
						throw new caliException("Attempting to compare objects of type '" + this.getType() + "' and '" + To.getType() + "'.");
					}
					break;
				case cString:
					if(To.getType() == cType.cString) {
						if(((CaliString)this).getValueString().equals(((CaliString)To).getValueString()))
							ret = new CaliBool(true);
						else
							ret = new CaliBool(false);
					} else {
						throw new caliException("Attempting to compare objects of type '" + this.getType() + "' and '" + To.getType() + "'.");
					}
					break;
				case cObject:
					if(To.getType() == cType.cObject) {
						if(this == To)
							ret = new CaliBool(true);
						else
							ret = new CaliBool(false);
					} else {
						throw new caliException("Attempting to compare objects of type '" + this.getType() + "' and '" + To.getType() + "'.");
					}
					break;
				case cList:
					if(To.getType() == cType.cList) {
						if(this == To)
							ret = new CaliBool(true);
						else
							ret = new CaliBool(false);
					} else {
						throw new caliException("Attempting to compare objects of type '" + this.getType() + "' and '" + To.getType() + "'.");
					}
					break;
				case cMap:
					if(To.getType() == cType.cMap) {
						if(this == To)
							ret = new CaliBool(true);
						else
							ret = new CaliBool(false);
					} else {
						throw new caliException("Attempting to compare objects of type '" + this.getType() + "' and '" + To.getType() + "'.");
					}
					break;
				default:
					throw new caliException("Attempting to compare objects of type '" + this.getType() + "' and '" + To.getType() + "'.");
			}
		}
		
		return ret;
	}
	
	public CaliBool evalExpressionBool() throws caliException {
		CaliBool ret = new CaliBool();
		
		switch(this.type) {
			case cBool:
				ret = (CaliBool) this;
				break;
			case cInt:
				if(((CaliInt)this).getValue() != 0)
					ret = new CaliBool(true);
				else
					ret = new CaliBool(false);
				break;
			case cDouble:
				if(((CaliDouble)this).getValue() != 0.0)
					ret = new CaliBool(true);
				else
					ret = new CaliBool(false);
				break;
			case cString:
				if(!((CaliString)this).getValue().equals(""))
					ret = new CaliBool(true);
				else
					ret = new CaliBool(false);
				break;
			case cList:
				if(((CaliList)this).size() != 0)
					ret = new CaliBool(true);
				else
					ret = new CaliBool(false);
				break;
			case cMap:
				if(((CaliMap)this).size() != 0)
					ret = new CaliBool(true);
				else
					ret = new CaliBool(false);
				break;
			case cObject:
				ret = new CaliBool(true);
				break;
			case cNull:
				ret = new CaliBool(false);
				break;
			default:
				throw new caliException("Unexpected result from condition of type '" + this.type.name() + "'.");
		}
		return ret;
	}
}
