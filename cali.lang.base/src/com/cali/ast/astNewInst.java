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

package com.cali.ast;

import java.util.ArrayList;

import com.cali.Environment;
import com.cali.types.CaliList;
import com.cali.types.CaliNull;
import com.cali.types.CaliObject;
import com.cali.types.CaliType;

public class astNewInst extends astNode implements astNodeInt {
	private astFunctDefArgsList args = new astFunctDefArgsList();
	
	public astNewInst() {
		this.setType(astNodeType.NEWINST);
	}
	
	public astNewInst(String Name) {
		this.setType(astNodeType.NEWINST);
		this.setName(Name);
	}
	
	public void addArg(astNode Arg) { this.args.getArgs().add(Arg); }
	
	@Override
	public String toString() {
		return this.toString(0);
	}
	
	@Override
	public String toString(int Level) {
		String rstr = "";
		rstr += getTabs(Level) + "{\n";
		rstr += this.getNodeStr(Level + 1) + ",\n";
		rstr += getTabs(Level + 1) + "\"constructor\": \"" + this.getName() + "\"\n";
		if(this.args != null) {
			rstr += getTabs(Level + 1) + "\"argumentList\":\n";
			rstr += this.args.toString(Level + 1) + ",\n";
		}
		if(this.getChild() != null) {
			rstr += getTabs(Level + 1) + "\"child\":\n";
			rstr += ((astNodeInt)this.getChild()).toString(Level + 1) + ",\n";
		}
		rstr += getTabs(Level) + "}";
		return rstr;
	}

	@Override
	public CaliType evalImpl(Environment env, boolean getref) throws caliException {
		CaliType ret = new CaliNull();
		
		if(env.getEngine().containsClass(this.getName())) {
			CaliList cargs = (CaliList) this.args.eval(env, getref);
			CaliObject cobj = (CaliObject) env.getEngine().getClassByName(this.getName()).instantiate(env, getref, cargs);
			
			if(cobj != null) {
				ret = cobj;
			} else {
				throw new caliException(this, "Cannot instantiate object of type '" + this.getName() + "', class definition not found.", env.stackTraceToString());
			}
		} else {
			throw new caliException(this, "Cannot instantiate class '" + this.getName() + "'.", env.stackTraceToString());
		}
		
		if (this.getChild() != null) {
			Environment tenv = env.clone(ret);
			return this.getChild().eval(tenv, getref);
		}
		
		return ret;
	}

	public astFunctDefArgsList getArgs() {
		return args;
	}

	public void setArgs(astFunctDefArgsList args) {
		this.args = args;
	}
	
	public ArrayList<CaliType> getEvaledArgs(Environment env) throws caliException {
		ArrayList<CaliType> eargs = new ArrayList<CaliType>();
		for(astNode tn : this.args.getArgs()) {
			CaliType res = tn.eval(env);
			eargs.add(res);
		}
		return eargs;
	}
}
