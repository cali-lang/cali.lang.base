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

public class CaliReturn extends CaliType implements CaliTypeInt {
	private CaliType value = null;
	
	public CaliReturn() {
		this.setType(cType.cReturn);
	}
	
	public CaliReturn(CaliType Value) {
		this();
		this.value = Value;
	}

	public CaliType getValue() {
		return value;
	}

	public void setValue(CaliType value) {
		this.value = value;
	}
	
	@Override
	public String toString(int Level) {
		String rstr = "";
		rstr += getTabs(Level) + "{\n";
		rstr += getTabs(Level + 1) + "\"type\": \"" + this.getType().name() + "\",\n";
		rstr += getTabs(Level + 1) + "\"value\": ";
		rstr += ((CaliTypeInt)this.value).toString(Level + 2);
		rstr += getTabs(Level) + "}";
		return rstr;
	}

	@Override
	public String str() {
		return "return";
	}
}
