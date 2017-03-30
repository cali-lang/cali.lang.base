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
import com.cali.types.CaliBool;
import com.cali.types.CaliList;
import com.cali.types.CaliMap;
import com.cali.types.CaliNull;
import com.cali.types.CaliString;
import com.cali.types.CaliType;
import com.cali.types.cType;

public class astFor extends astNode implements astNodeInt {
	private astNode exprInit = null;
	private astNode exprCond = null;
	private astNode exprInc = null;
	
	private astNode eachVar = null;
	private astNode eachExpr = null;
	
	private boolean isForEach = false;
	
	private astStatementList instructions = new astStatementList();
	
	public astFor() {
		this.setType(astNodeType.FOR);
	}
	
	public void addToInstructionList(astNode Node) {
		this.instructions.getStatements().add(Node);
	}

	public String toString() {
		return this.toString(0);
	}
	
	@Override
	public String toString(int Level) {
		String rstr = "";
		
		rstr += getTabs(Level) + "{\n";
		rstr += this.getNodeStr(Level + 1) + ",\n";

		if(this.exprCond != null) {
			rstr += getTabs(Level + 1) + "\"condition\":\n";
			rstr += ((astNodeInt)this.exprCond).toString(Level + 1) + ",\n";
		}

		if(this.instructions != null) {
			rstr += this.instructions.toString(Level + 1);
		}
		
		rstr += getTabs(Level) + "}";
		return rstr;
	}

	@Override
	public CaliType evalImpl(Environment env, boolean getref) throws caliException {
		if(this.isForEach) {
			return this.evalForEachImpl(env, getref);
		} else {
			return this.evalForImpl(env, getref);
		}
	}

	private CaliType evalForEachImpl(Environment env, boolean getref) throws caliException {
		CaliType ret = new CaliNull();
		
		CaliType titems = this.eachExpr.eval(env, getref);
		
		if(titems.getType() == cType.cList) {
			CaliList al = (CaliList)titems;
			for(int i = 0; i < al.getValue().size(); i++) {
				CaliType item = al.getValue().get(i);
				env.getLocals().add(this.eachVar.getName(), item);
				
				for(astNode inst : this.instructions.getStatements()) {
					ret = inst.eval(env, getref);
					if(astNode.isBreakReturnEvent(ret))
						break;
				}
				
				if(astNode.isBreakReturnEvent(ret))
					break;
			}
		}
		else if(titems.getType() == cType.cMap) {
			CaliMap am = (CaliMap)titems;
			
			for(String key : am.getValue().keySet()) {
				CaliString tmp = new CaliString(key);
				env.getLocals().add(this.eachVar.getName(), tmp);
				
				for(astNode inst : this.instructions.getStatements()) {
					ret = inst.eval(env, getref);
					if(astNode.isBreakReturnEvent(ret))
						break;
				}
				
				if(astNode.isBreakReturnEvent(ret))
					break;
			}
		}
		else {
			throw new caliException(this, "For loop expecting of type LIST or MAP, found '" + titems.getType().name() + "' instead.", env.stackTraceToString());
		}
		
		return ret;
	}
	
	private CaliType evalForImpl(Environment env, boolean getref) throws caliException
	{
		CaliType ret = new CaliNull();
		
		@SuppressWarnings("unused")
		CaliType einit = this.exprInit.eval(env, getref);
		
		CaliType etmp = this.exprCond.eval(env, getref);
		CaliBool cond = etmp.evalExpressionBool();
		while(cond.getValue()) {
			for(astNode inst : this.instructions.getStatements()) {
				ret = inst.eval(env, getref);
				if(astNode.isBreakReturnEvent(ret))
					break;
			}
			
			if(astNode.isBreakReturnEvent(ret))
				break;
			else {
				// Increment step
				@SuppressWarnings("unused")
				CaliType einc = this.exprInc.eval(env, getref);
				
				// Recheck condition
				etmp = this.exprCond.eval(env, getref);
				cond = etmp.evalExpressionBool();
			}
		}
		
		return ret;
	}

	public astNode getExprInit() {
		return exprInit;
	}

	public void setExprInit(astNode exprInit) {
		this.exprInit = exprInit;
	}

	public astNode getExprCond() {
		return exprCond;
	}

	public void setExprCond(astNode exprCond) {
		this.exprCond = exprCond;
	}

	public astNode getExprInc() {
		return exprInc;
	}

	public void setExprInc(astNode exprInc) {
		this.exprInc = exprInc;
	}

	public boolean getIsForEach() {
		return isForEach;
	}

	public void setIsForEach(boolean isForEach) {
		this.isForEach = isForEach;
	}

	public astStatementList getInstructions() {
		return instructions;
	}

	public void setInstructions(astStatementList instructions) {
		this.instructions = instructions;
	}

	public astNode getEachVar() {
		return eachVar;
	}

	public void setEachVar(astNode eachVar) {
		this.eachVar = eachVar;
	}

	public astNode getEachExpr() {
		return eachExpr;
	}

	public void setEachExpr(astNode eachExpr) {
		this.eachExpr = eachExpr;
	}
}
