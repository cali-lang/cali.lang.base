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

import com.cali.Environment;
import com.cali.ast.astClass;
import com.cali.ast.astFunctDef;
import com.cali.ast.astNode;
import com.cali.ast.astNodeType;
import com.cali.ast.caliException;
import com.cali.types.CaliBool;
import com.cali.types.CaliList;
import com.cali.types.CaliMap;
import com.cali.types.CaliString;
import com.cali.types.CaliType;
import com.cali.types.CaliTypeInt;
import com.cali.types.cType;

public class CClass {
	private astClass classDef = null;
	
	public CClass() { }
	
	public void setClass(astClass ClassDef) { this.classDef = ClassDef; }
	
	public CaliType getName(Environment env, ArrayList<CaliType> args) {
		return new CaliString(this.classDef.getName());
	}
	
	public CaliType isStatic(Environment env, ArrayList<CaliType> args) {
		return new CaliBool(this.classDef.getStatic());
	}
	
	public CaliType isExtern(Environment env, ArrayList<CaliType> args) {
		return new CaliBool(this.classDef.getExtern());
	}
	
	public CaliType getExternClassName(Environment env, ArrayList<CaliType> args) {
		return new CaliString(this.classDef.getExternClassName());
	}
	
	public CaliType getMembers(Environment env, ArrayList<CaliType> args) {
		CaliMap mp = new CaliMap();
		
		for(String mname : this.classDef.getMembers().keySet()) {
			mp.put(mname, new CaliString(this.classDef.getMembers().get(mname).getType().name()));
		}
		
		return mp;
	}
	
	public CaliType getMethods(Environment env, ArrayList<CaliType> args) throws caliException {
		CaliMap map = new CaliMap();
		
		for(String mname : this.classDef.getFuncts().keySet()) {
			astFunctDef afd = (astFunctDef) this.classDef.getFunct(mname);
			CaliMap fm = new CaliMap();
			fm.put("isExtern", new CaliBool(afd.getExtern()));
			
			CaliList alist = new CaliList();
			for(astNode tn : afd.getArgList().getArgs()) {
				CaliMap am = new CaliMap();
				
				if(tn.getType() == astNodeType.ETCETERA) {
					am.put("name", new CaliString("..."));
				} else {
					am.put("name", new CaliString(tn.getName()));
					
					
					
					String primType = "";
					if(tn.getPrimType() != cType.cUndef) {
						primType = tn.getPrimType().name().toLowerCase().substring(1);
					}
					am.put("requiredType", new CaliString(primType));
					
					if (tn.getType() == astNodeType.VAR) {
						am.put("hasDefaultValue", new CaliBool(false));
					} else {
						am.put("hasDefaultValue", new CaliBool(true));
						CaliType defVal = tn.eval(env);
						am.put("defaultValueType", new CaliString(defVal.getType().name().toLowerCase().substring(1)));
						am.put("defaultValue", new CaliString(((CaliTypeInt)defVal).str()));
					}
				}
				
				alist.add(am);
			}
			fm.put("arguments", alist);
			map.put(mname, fm);
		}
		
		return map;
	}
}