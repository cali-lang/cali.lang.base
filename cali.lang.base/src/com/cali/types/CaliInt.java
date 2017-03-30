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

public class CaliInt extends CaliObject implements CaliTypeInt {
	private long value = 0L;
	
	public CaliInt() {
		this.setType(cType.cInt);
		
		// Setup linkage for string object.
		this.setExternObject(this);
		try {
			this.setClassDef(Universe.get().getClassDef("int"));
		} catch (caliException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CaliInt(long Value) {
		this();
		this.value = Value;
	}
	
	public void setValue(long Value) {
		this.value = Value;
	}
	
	public long getValue() {
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
	
	public CaliType toDouble(ArrayList<CaliType> args) {
		return new CaliDouble((double)this.value);
	}
	
	public CaliType toBool(ArrayList<CaliType> args) {
		if (this.value == 0) {
			return new CaliBool(false);
		}
		return new CaliBool(true);
	}
	
	public CaliType toString(ArrayList<CaliType> args) {
		return new CaliString("" + this.value);
	}
	
	public CaliType compare(ArrayList<CaliType> args) {
		return new CaliInt(Long.compare(this.value, ((CaliInt)args.get(0)).getValue()));
	}
	
	public CaliType numLeadingZeros(ArrayList<CaliType> args) {
		return new CaliInt(Long.numberOfLeadingZeros(this.value));
	}
	
	public CaliType numTrailingZeros(ArrayList<CaliType> args) {
		return new CaliInt(Long.numberOfTrailingZeros(this.value));
	}
	
	public CaliType reverse(ArrayList<CaliType> args) {
		return new CaliInt(Long.reverse(this.value));
	}
	
	public CaliType reverseBytes(ArrayList<CaliType> args) {
		return new CaliInt(Long.reverseBytes(this.value));
	}
	
	public CaliType rotateLeft(ArrayList<CaliType> args) {
		return new CaliInt(Long.rotateLeft(this.value, (int)((CaliInt)args.get(0)).getValue()));
	}
	
	public CaliType rotateRight(ArrayList<CaliType> args) {
		return new CaliInt(Long.rotateRight(this.value, (int)((CaliInt)args.get(0)).getValue()));
	}
	
	public CaliType signum(ArrayList<CaliType> args) {
		return new CaliInt(Long.signum(this.value));
	}
	
	public CaliType toBinary(ArrayList<CaliType> args) {
		return new CaliString(Long.toBinaryString(this.value));
	}
	
	public CaliType toHex(ArrayList<CaliType> args) {
		return new CaliString(Long.toHexString(this.value));
	}
	
	public CaliType toOctal(ArrayList<CaliType> args) {
		return new CaliString(Long.toOctalString(this.value));
	}
	
	public CaliType parse(ArrayList<CaliType> args) {
		try {
			if(args.get(1).isNull()) {
				return new CaliInt(Long.parseLong(((CaliString)args.get(0)).getValue()));
			} else {
				return new CaliInt(Long.parseLong(((CaliString)args.get(0)).getValue(), (int)((CaliInt)args.get(1)).getValue()));
			}
		} catch(Exception e) {
			return new CaliException("int.parse(): Integer parse exception.");
		}
	}
}
