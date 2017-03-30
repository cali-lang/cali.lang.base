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

import com.cali.Environment;
import com.cali.types.CaliException;
import com.cali.types.CaliException.exType;
import com.cali.types.CaliInt;
import com.cali.types.CaliList;
import com.cali.types.CaliNull;
import com.cali.types.CaliObject;
import com.cali.types.CaliString;
import com.cali.types.CaliType;

public class astTryCatch extends astNode implements astNodeInt
{
	private astStatementList tryInstList = new astStatementList();
	private astStatementList catchInstList = new astStatementList();

	public astTryCatch() {
		this.setType(astNodeType.TRYCATCH);
	}

	@Override
	public String toString() {
		return this.toString(0);
	}
	
	@Override
	public String toString(int Level) {
		String rstr = "";
		rstr += getTabs(Level) + "{\n";
		rstr += this.getNodeStr(Level + 1) + ",\n";

		if(this.tryInstList != null) {
			rstr += getTabs(Level + 1) + "\"try\":\n";
			rstr += this.tryInstList.toString(Level + 1) + ",\n";
		}
		
		rstr += getTabs(Level + 1) + "\"catchObject\": \"" + this.getName() + "\"\n";

		if(this.catchInstList != null) {
			rstr += getTabs(Level + 1) + "\"catch\":\n";
			rstr += this.catchInstList.toString(Level + 1) + "\n";
		}
		
		rstr += getTabs(Level) + "}";
		return rstr;
	}

	@Override
	public CaliType evalImpl(Environment env, boolean getRef) throws caliException {
		CaliType ret = new CaliNull();
		CaliType except = new CaliNull();
		
		for(int i = 0; (i < this.tryInstList.getStatements().size())&&(!ret.isEx()); i++) {
			astNode statement = this.tryInstList.getStatements().get(i);
			CaliType tmp = statement.eval(env, getRef);
			if(tmp.isBreak() || tmp.isReturn()) {
				ret = tmp;
				break;
			} else if(tmp.isEx()) {
				except = tmp;
				break;
			}
		}
		
		if(!except.isNull()) {
			CaliType tnode = this.initArgs(env, getRef, (CaliException)except);
			if(tnode.isEx()) {
				ret = tnode;
			} else {
				env.getLocals().add(this.getName(), tnode);
				for(int i = 0; (i < this.catchInstList.getStatements().size())&&(!ret.isEx()); i++) {
					astNode statement = this.catchInstList.getStatements().get(i);
					CaliType tmp = statement.eval(env, getRef);
					if(tmp.isBreak() || tmp.isReturn()) {
						ret = tmp;
						break;
					} else if(tmp.isEx()) {
						ret = tmp;
						break;
					}
				}
			}
		}
		
		return ret;
	}
	
	private CaliType initArgs(Environment env, boolean getRef, CaliException ae) throws caliException {
		CaliType ret = new CaliNull();

		if(this.getName().equals("")) {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "MALFORMED_CATCH_DEFINITION", "Malformed catch definition.", "Malformed catch definition.", env.getCallStack().getStackTrace());
			ret = e;
		} else {
			 // Instantiate a new exception object and return it.
			 
			 if(env.getEngine().containsClass("exception")) {
				 astClass exClass = env.getEngine().getClassByName("exception");
				 ret = exClass.instantiate(env, getRef, new CaliList());
				 
				 CaliObject aci = (CaliObject)ret;
				 aci.addMember("lineNumber", new CaliInt(ae.getLineNumber()));
				 aci.addMember("exceptionType", new CaliString(ae.getExceptionTypeString()));
				 aci.addMember("id", new CaliString(ae.getId()));
				 aci.addMember("text", new CaliString(ae.getText()));
				 aci.addMember("details", new CaliString(ae.getDetails()));
				 aci.addMember("stackTrace", new CaliString(ae.getStackTrace()));
			 } else {
				 CaliException e = new CaliException(exType.exRuntime);
				 e.setException(getLineNum(), "EXCEPTION_CLASS_NOT_FOUND", "Exception class definition not found.", "Exception class definition not found.", env.getCallStack().getStackTrace());
				 ret = e;
			 }
		}	

		return ret;
	}
	
	public void setTryInstList(astStatementList TryInstList) { this.tryInstList = TryInstList; }
	public astStatementList getTryInstList() { return this.tryInstList; }
	
	public void setCatchInstList(astStatementList CatchInstList) { this.catchInstList = CatchInstList; }
	public astStatementList getCatchInstList() { return this.catchInstList; }
}
