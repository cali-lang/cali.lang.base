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

package com.cali.stdlib;

import java.util.ArrayList;

import com.cali.types.CaliNull;
import com.cali.types.CaliType;
import com.cali.types.CaliTypeInt;

public class console {
	static console instance = null;
	
	public console() {
		this.init();
	}
	
	public void init() {
		// Do any initialization here!
	}
	
	public static console get() {
		if(instance == null) instance = new console();
		return instance;
	}
	
	public console log(String Str) { return this.println(Str); }
	public console info(String Str) { return this.println("[info] " + Str); }
	public console warn(String Str) { return this.println("[warn] " + Str); }
	public console err(String Str) { return this.println("[error] " + Str); }
	
	public synchronized console print(String Text) {
		System.out.print(Text);
		System.out.flush();
		return this;
	}
	
	public console println(String Text) { this.print(Text + "\n"); return this; }
	
	public CaliType _log(ArrayList<CaliType> args) {
		this.log(((CaliTypeInt)args.get(0)).str());
		return new CaliNull();
	}
	
	public CaliType _info(ArrayList<CaliType> args) {
		this.info(((CaliTypeInt)args.get(0)).str());
		return new CaliNull();
	}
	
	public CaliType _warn(ArrayList<CaliType> args) {
		this.warn(((CaliTypeInt)args.get(0)).str());
		return new CaliNull();
	}
	
	public CaliType _err(ArrayList<CaliType> args) {
		this.err(((CaliTypeInt)args.get(0)).str());
		return new CaliNull();
	}
	
	public CaliType _print(ArrayList<CaliType> args) {
		this.print(((CaliTypeInt)args.get(0)).str());
		return new CaliNull();
	}
	
	public CaliType _println(ArrayList<CaliType> args) {
		CaliType ret = this._print(args);
		System.out.println();
		return ret;
	}
}
