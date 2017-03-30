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

import java.util.concurrent.ConcurrentHashMap;

public class Members {
	private ConcurrentHashMap<String, CaliType> impa = new ConcurrentHashMap<String, CaliType>();
	
	public void add(String Key, CaliType Value) {
		this.impa.put(Key, Value);
	}
	
	public boolean contains(String Key) {
		return this.impa.containsKey(Key);
	}
	
	public CaliType get(String Key) {
		return this.impa.get(Key);
	}
	
	public ConcurrentHashMap<String, CaliType> getMap() {
		return this.impa;
	}
	
	public String toString() {
		return this.toString(0);
	}
	
	public String toString(int Level) {
		String rstr = "";

		rstr += CaliType.getTabs(Level) + "{\n";

		for (String key : this.impa.keySet()) {
			rstr += CaliType.getTabs(Level + 1) + "\"" + key + "\":\n";
			rstr += ((CaliTypeInt)this.impa.get(key)).toString(Level + 1);
			rstr += CaliType.getTabs(Level + 1) + ",\n";
		}
		rstr += CaliType.getTabs(Level) + "}\n";

		return rstr;
	}
}
