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

import com.cali.CallStack;
import com.cali.Environment;
import com.cali.types.CaliException;
import com.cali.types.CaliException.exType;
import com.cali.types.CaliList;
import com.cali.types.CaliNull;
import com.cali.types.CaliObject;
import com.cali.types.CaliType;

public class astFunctCall extends astNode implements astNodeInt {
	private astFunctDefArgsList args = new astFunctDefArgsList();
	public void addArg(astNode Arg) { this.args.getArgs().add(Arg); }
	
	public astFunctCall() {
		this.setType(astNodeType.FUNCTCALL);
	}
	
	public astFunctCall(String Name) {
		this.setType(astNodeType.FUNCTCALL);
		this.setName(Name);
	}

	public String toString() {
		return this.toString(0);
	}
	
	@Override
	public String toString(int Level) {
		String rstr = "";
		rstr += getTabs(Level) + "{\n";
		rstr += this.getNodeStr(Level + 1) + ",\n";
		if(this.args != null) {
			rstr += getTabs(Level + 1) + "\"argumentList\":\n";
			rstr += this.args.toString(Level + 1) + ",\n";
		}
		if (this.getChild() != null) {
			rstr += getTabs(Level + 1) + "\"child\":\n";
			rstr += ((astNodeInt)this.getChild()).toString(Level + 2) + ",\n";
		}
		rstr += getTabs(Level) + "}";
		return rstr;
	}

	public astFunctDefArgsList getArgs() {
		return args;
	}

	public void setArgs(astFunctDefArgsList args) {
		this.args = args;
	}
	
	public CaliType evalImpl(Environment env, boolean getRef) throws caliException {
		CaliType ret = new CaliNull();

		if (this.functionHasAccess(env, this.getName())) {
		  CaliType cargs;
		  if(this.args != null) {
			  Environment tenv = env.clone(null);
			  cargs = this.args.eval(tenv, getRef);
		  } else {
			  cargs = new CaliList();
		  }

		  if(!cargs.isEx()) {
			  astClass cls = env.getClassInstance().getClassDef();
			  if (env.getCurObj() != null && env.getCurObj() instanceof CaliObject) {
				  cls = ((CaliObject)env.getCurObj()).getClassDef();
			  }
			  if(cls != null) {
				  CallStack cst;
				  synchronized(env.getCallStack()) {
					  cst = new CallStack(this.getFileName(), this.getLineNum(), ((CaliObject)env.getCurObj()).getClassDef().getName(), this.getName(), "Function called.");
					  cst.setParent(env.getCallStack());
				  }
				  
				  Environment tenv = env.clone(env.getCurObj());
				  tenv.setEnvironment(env.getClassInstance(), env.getLocals(), cst);
				  
				  ret = cls.call(tenv, getRef, getName(), (CaliList)cargs);
			  } else {
				  CaliException e = new CaliException(exType.exInternal);
				  e.setException(this.getLineNum(), "CLASS_DEF_NOT_FOUND", "Class '" + env.getClassInstance().getClassDef().getName() + "' has no function definition '" + getName() + "'.", "Class '" + env.getClassInstance().getClassDef().getName() + "' has no function definition '" + this.getName() + "'.", env.getCallStack().getStackTrace());
				  ret = e;
			  }
		  } else {
			  ret = cargs;
		  }
		} else {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(this.getLineNum(), "NO_ACCESS", "aObj.aFunctCall(): No access to function '" + this.getName() + "'.", env.getCallStack().getStackTrace());
			return e;
		}
		
		if (this.getChild() != null && !ret.isEx()) {
			Environment tenv = env.clone(ret);
			ret = this.getChild().eval(tenv, getRef);
		}

		return ret;
	}
	
	public boolean functionHasAccess(Environment env, String functionName) {
		if (env.getClassInstance() != env.getCurObj()) {
			astClass ac = ((CaliObject)env.getCurObj()).getClassDef();
			if (ac.containsFunction(functionName)) {
				if (ac.getFunct(functionName).getAccessType() == AccessType.aPrivate) {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
}
