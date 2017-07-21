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

import com.cali.types.CaliType;
import com.cali.Environment;
import com.cali.ast.astClass;
import com.cali.ast.caliException;
import com.cali.types.CaliBool;
import com.cali.types.CaliException;
import com.cali.types.CaliList;
import com.cali.types.CaliNull;
import com.cali.types.CaliObject;
import com.cali.types.CaliString;

public class CReflect {
	public static CaliType _evalStr(Environment env, ArrayList<CaliType> args) {
		if ((Boolean)env.getEngine().getSecurityManager().getProperty("reflect.eval.string")) {
			String code = ((CaliString)args.get(0)).getValue();
			String name = ((CaliString)args.get(1)).getValue();
			try {
				env.getEngine().parseString(name, code);
				return new CaliNull();
			} catch (caliException e) {
				return new CaliException(e.getMessage());
			} catch (Exception e) {
				return new CaliException(e.getMessage());
			}
		} else {
			return new CaliException("reflect.evalStr(): Security exception, action 'reflect.eval.string' not permitted.");
		}
	}
	
	public static CaliType _evalFile(Environment env, ArrayList<CaliType> args) {
		if ((Boolean)env.getEngine().getSecurityManager().getProperty("reflect.eval.file")) {
			String FileName = ((CaliString)args.get(0)).getValue();
			try {
				env.getEngine().parseFile(FileName);
			} catch (Exception e) {
				return new CaliException(e.getMessage());
			}
			return new CaliNull();
		} else {
			return new CaliException("reflect.evalFile(): Security exception, action 'reflect.eval.file' not permitted.");
		}
	}
	
	
	
	public static CaliType _includeModule(Environment env, ArrayList<CaliType> args) {
		if ((Boolean)env.getEngine().getSecurityManager().getProperty("reflect.include.module")) {
			String incFile = ((CaliString)args.get(0)).getValue().replace(".", System.getProperty("file.separator")) + ".ca";
			try {
				env.getEngine().parseFile(incFile);
			} catch (Exception e) {
				return new CaliException(e.getMessage());
			}
			return new CaliNull();
		} else {
			return new CaliException("reflect.includeModule(): Security exception, action 'reflect.include.module' not permitted.");
		}
	}
	
	public static CaliType loadedModules(Environment env, ArrayList<CaliType> args) {
		CaliList list = new CaliList();
		for (String mod : env.getEngine().getIncludes()) {
			list.add(new CaliString(mod));
		}
		return list;
	}
	
	public static CaliType loadedClasses(Environment env, ArrayList<CaliType> args) {
		CaliList list = new CaliList();
		for (String cls : env.getEngine().getClasses().keySet()) {
			list.add(new CaliString(cls));
		}
		return list;
	}
	
	public static CaliType isModuleLoaded(Environment env, ArrayList<CaliType> args) {
		String incFile = ((CaliString)args.get(0)).getValue().replace(".", System.getProperty("file.separator")) + ".ca";
		return new CaliBool(env.getEngine().getIncludes().contains(incFile));
	}
	
	public static CaliType classExists(Environment env, ArrayList<CaliType> args) {
		CaliString name = (CaliString)args.get(0);
		return new CaliBool(env.getEngine().getClasses().containsKey(name.getValue()));
	}
	
	public static CaliType getClassDef(Environment env, ArrayList<CaliType> args) {
		String ClassName = ((CaliString)args.get(0)).getValue();
		if(env.getEngine().getClasses().containsKey(ClassName)) {
			astClass tc = env.getEngine().getClasses().get(ClassName);
			astClass cc = env.getEngine().getClasses().get("rclass");
			try {
				CaliObject co = (CaliObject) cc.instantiate(env);
				CClass ccobj = (CClass)co.getExternObject();
				ccobj.setClass(tc);
				return co;
			} catch (caliException e) {
				return new CaliException("reflect.getClassDef(): Class '" + ClassName + "'.\n");
			}
		} else {
			return new CaliException("reflect.getClassDef(): Class '" + ClassName + "' not found.");
		}
	}
	
	public static CaliType instantiate(Environment env, ArrayList<CaliType> args) {
		String ClassName = ((CaliString)args.get(0)).getValue();
		try {
			CaliObject co = env.getEngine().instantiateObject(ClassName);
			return co;
		} catch (caliException e) {
			return new CaliException("reflect.instantiate(): Failed to instantiate class  '" + ClassName + "'.\n");
		}
	}
	
	public static CaliType invoke(Environment env, ArrayList<CaliType> args) {
		CaliObject obj = (CaliObject)args.get(0);
		String fname = ((CaliString)args.get(1)).getValue();
		CaliList alist = (CaliList) args.get(2);
		
		try {
			astClass ac = obj.getClassDef();
			Environment tenv = env.clone(obj);
			return ac.call(tenv, false, fname, alist);
		} catch (caliException e) {
			return new CaliException(e.getCaliMessage());
		} catch (Exception e) {
			return new CaliException("reflect.invoke(): Unhandled exception occurred while calling '" + fname + "'. " + e.getMessage());
		}
	}
}
