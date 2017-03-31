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
import com.cali.ast.astClass;
import com.cali.ast.caliException;
import com.cali.stdlib.console;

public class CaliCallback extends CaliObject implements CaliTypeInt {
	private String functName = "";
	private CaliObject obj = null;
	private Environment tenv = null;
	
	public CaliCallback() {
		this.setType(cType.cCallback);
		
		// Setup linkage for string object.
		this.setExternObject(this);
		try {
			this.setClassDef(Universe.get().getClassDef("callback"));
		} catch (caliException e) {
			console.get().err("CaliCallback(): Unexpected exception getting class definition: " + e.getMessage());
		}
	}
	
	public CaliCallback(Environment Env, CaliObject Obj, String FunctName) {
		this();
		this.tenv = Env;
		this.obj = Obj;
		this.functName = FunctName;
	}
	
	public CaliType call(CaliList args) {
		return this.call(this.tenv, args);
	}
	
	public CaliType call(Environment env, CaliList args) {
		CaliType ret = new CaliNull();
		
		try {
			ret = this.callWithException(env, args);
		} catch(caliException e) {
			console.get().err("\n" + e.getCaliStackTrace());
			return new CaliException(e.getMessage());
		}
		
		return ret;
	}
	
	public CaliType callWithException(Environment env, CaliList args) throws caliException {
		CaliType ret = new CaliNull();
		
		CaliObject tobj = (CaliObject) env.getCurObj();
		env.setCurObj(this.getObj());
		try {
			astClass ac = this.obj.getClassDef();
			ret = ac.call(env, false, this.getFunctName(), args);
		} catch(caliException e) {
			env.setCurObj(tobj);
			throw e;
		}
		env.setCurObj(tobj);
		
		return ret;
	}
	
	public String getFunctName() {
		return functName;
	}

	public void setFunctName(String functName) {
		this.functName = functName;
	}

	public CaliObject getObj() {
		return obj;
	}

	public void setObj(CaliObject obj) {
		this.obj = obj;
	}

	public Environment getEnv() {
		return tenv;
	}

	public void setEnv(Environment env) {
		this.tenv = env;
	}
	
	public CaliType _call(ArrayList<CaliType> args) {
		return this.call((CaliList)args.get(0));
	}
}
