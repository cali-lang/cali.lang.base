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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.cali.CallStack;
import com.cali.Environment;
import com.cali.types.CaliBool;
import com.cali.types.CaliException;
import com.cali.types.CaliException.exType;
import com.cali.types.CaliInt;
import com.cali.types.CaliList;
import com.cali.types.CaliNull;
import com.cali.types.CaliType;
import com.cali.types.CaliReturn;
import com.cali.types.CaliObject;
import com.cali.types.CaliDouble;
import com.cali.types.cType;

public class astFunctDef extends astNode implements astNodeInt {
	private astFunctDefArgsList argList = new astFunctDefArgsList();
	private astStatementList instructionList = new astStatementList();
	private boolean isExtern = false;
	
	public astFunctDef() {
		this.setType(astNodeType.FUNCTDEF);
	}
	
	public astFunctDef(String Name) {
		this.setType(astNodeType.FUNCTDEF);
		this.setName(Name);
	}

	public astFunctDefArgsList getArgList() {
		return argList;
	}

	public void setArgList(astFunctDefArgsList argList) {
		this.argList = argList;
	}

	public astStatementList getInstructionList() {
		return instructionList;
	}

	public void setInstructionList(astStatementList InstructionList) {
		this.instructionList = InstructionList;
	}

	public void setExtern(boolean extern) {
		this.isExtern = extern;
	}
	
	public boolean getExtern() {
		return this.isExtern;
	}

	@Override
	public CaliType evalImpl(Environment env, boolean getref) throws caliException {
		throw new caliException(this, "INTERNAL [astFunctDef.evalImpl] Not implemented.", env.stackTraceToString());
	}
	
	@Override
	public String toString(int Level) {
		String rstr = "";
		rstr += getTabs(Level) + "{\n";
		rstr += this.getNodeStr(Level + 1) + ",\n";
		if(this.argList != null) {
			rstr += getTabs(Level + 1) + "\"argumentList\":\n";
			rstr += this.argList.toString(Level + 1) + ",\n";
		}
		if(this.instructionList != null) {
		  rstr += getTabs(Level + 1) + "\"statementList\":\n";
			rstr += this.instructionList.toString(Level + 1) + ",\n";
		}
		rstr += getTabs(Level) + "}";
		return rstr;
	}
	
	public CaliType initArgs(Environment env, CaliList args) throws caliException {
		CaliType ret = new CaliNull();
		
		if (this.argList != null) {
			int i = 0;
			for (astNode adef : this.argList.getArgs()) {
				// Data type in funct def specified, check that passed data 
				// is valid.
				if (i < args.getValue().size()) {
					if (adef.getType() != astNodeType.UNDEF) {
						if (
							(adef.getType() == astNodeType.BOOL && args.getValue().get(i).getType() != cType.cBool) 
							|| (adef.getType() == astNodeType.INT && args.getValue().get(i).getType() != cType.cInt) 
							|| (adef.getType() == astNodeType.DOUBLE && args.getValue().get(i).getType() != cType.cDouble) 
							|| (adef.getType() == astNodeType.STRING && args.getValue().get(i).getType() != cType.cString)
							|| (adef.getType() == astNodeType.LIST && args.getValue().get(i).getType() != cType.cList)
							|| (adef.getType() == astNodeType.MAP && args.getValue().get(i).getType() != cType.cMap)
							|| (adef.getType() == astNodeType.OBJ && args.getValue().get(i).getType() != cType.cObject) 
							) {
								CaliException e = new CaliException(exType.exRuntime);
								e.setException(this.getLineNum(), "INVALID_DATA_TYPE", "Function '" + this.getName() + "' definition at position " + (i + 1) + " is expected to be of type '" + adef.getType().name() + "' but found '" + args.getValue().get(i).getType().name() + "' instead.", env.getCallStack().getStackTrace());
								return e;
						} else if (adef.getType() == astNodeType.VAR && adef.getPrimType() != cType.cUndef && adef.getPrimType() != args.getValue().get(i).getType()) {
							CaliException e = new CaliException(exType.exRuntime);
							e.setException(this.getLineNum(), "INVALID_DATA_TYPE", "Function '" + this.getName() + "' definition at position " + (i + 1) + " is expected to be of type '" + adef.getPrimType().name() + "' but found '" + args.getValue().get(i).getType().name() + "' instead.", env.getCallStack().getStackTrace());
							return e;
						}
					}
					
					if (adef.getType() == astNodeType.ETCETERA) {
						CaliList etcList = new CaliList();
						for (int j = i; j < args.getValue().size(); j++) {
							etcList.add(args.getValue().get(j));
						}
						env.getLocals().add("etc", etcList);
						break;
					} else {
						env.getLocals().add(adef.getName(), args.getValue().get(i));
					}
				} else if (adef.getType() == astNodeType.ETCETERA) {
					CaliList etcList = new CaliList();
					env.getLocals().add("etc", etcList);
					break;
				} else if (adef.getType() != astNodeType.VAR) {
					// Not a var, so it is a defalut value.
					env.getLocals().add(adef.getName(), adef.eval(env, false));
				} else {
					CaliException e = new CaliException(exType.exRuntime);
					e.setException(this.getLineNum(), "ARGUMENT_NUMBER", "Number of arguments provided does not match the number in definition.", "Number of arguments provided does not match the number in definition.", env.getCallStack().getStackTrace());
					ret = e;
				}
				i++;
			}
		} else if (this.argList.getArgs().size() > 0) {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(this.getLineNum(), "ARGUMENT_NUMBER", "Number of arguments provided does not match the number in definition.", "Number of arguments provided does not match the number in definition.", env.getCallStack().getStackTrace());
			ret = e;
		}
		
		if(ret.isNull()) {
			ret = new CaliBool(true);
		}
		
		return ret;
	}
	
	public CaliType call (Environment env, boolean getRef, CaliList args, String FileName) throws caliException {
		CaliType ret = new CaliNull();
		boolean retFound = false;
		
		CaliType tnode = this.initArgs(env, args);
		
		CallStack cst = new CallStack(FileName, this.getLineNum(), env.getClassInstance().getClassDef().getName(), this.getName(), "Defined.");
		cst.setParent(env.getCallStack());
		
		// Itterate statement list of function
		if(!tnode.isEx()) {
			if(this.instructionList != null) {
				for(int i = 0; (i < this.instructionList.getStatements().size())&&(!ret.isEx()); i++) {
					astNode statement = this.instructionList.getStatements().get(i);
					CaliType tmp = null;
					if(statement != null) {
						Environment tenv = new Environment(env.getEngine());
						tenv.setEnvironment(env.getClassInstance(), env.getLocals(), cst);
						tmp = statement.eval(tenv, getRef);
						if(tmp.isReturn()) {
							ret = ((CaliReturn)tmp).getValue();
							retFound = true;
							break;
						} else if(tmp.isEx()) {
							ret = tmp;
							break;
						}
					} else {
						CaliException e = new CaliException(exType.exInternal);
						e.setException(this.getLineNum(), "NULL_PTR", "Null pointer for statement. (aFunctDef::call)", "Null pointer for statement.", env.getCallStack().getStackTrace());
						ret = e;
						break;
					}
				}
			} else {
				CaliException e = new CaliException(exType.exInternal);
				e.setException(this.getLineNum(), "NULL_PTR", "Null pointer for slist. (aFunctDef::call)", "Null pointer for slist.", env.getCallStack().getStackTrace());
				ret = e;
			}
		}
		else
			ret = tnode;
		
		if(ret == null) {
			if(retFound) {
				CaliException e = new CaliException(exType.exInternal);
				e.setException(this.getLineNum(), "NULL_PTR", "Null pointer returned. (aFunctDef::call)", "Null pointer returned.", env.getCallStack().getStackTrace());
				ret = e;
			} else {
				// No return statement, create new astNode of aUndef and return.
				ret = new CaliNull();
			}
		}

		
		return ret;
	}
	
	public CaliType callExtern(Environment env, CaliList args) throws caliException {
		CaliType ret = new CaliNull();
		
		CaliObject callingObj = env.getClassInstance();
		Object o = callingObj.getExternObject();
		
		if(o != null) {
			ArrayList<CaliType> fargs = this.getExternArgs(env, args);
			try {
				Class<?> aclass = callingObj.getClassDef().getExternClass();
				if (aclass == null) {
					System.out.println("aclass null");
				}
				Method meth = aclass.getMethod(this.getName(), Environment.class, ArrayList.class);
				CaliType tmp = (CaliType)meth.invoke(o, env, fargs);
				if((tmp != null)&&(tmp instanceof CaliType)) ret = tmp;
				else
					throw new caliException(this, "Return value found from calling '" + this.getName() + "' is null or not of type CaliType.", env.stackTraceToString());
			} catch (NoSuchMethodException e) {
				CaliException ex = new CaliException(exType.exRuntime);
				ex.setException(this.getLineNum(), "EXTERN_NO_SUCH_METHOD", "External call, no such method '" + this.getName() + "'.", env.getCallStack().getStackTrace());
				return ex;
			} catch (SecurityException e) {
				CaliException ex = new CaliException(exType.exRuntime);
				ex.setException(this.getLineNum(), "EXTERN_SECURITY_EXCEPTION", "External call, security exception for method '" + this.getName() + "'.", env.getCallStack().getStackTrace());
				return ex;
			} catch (IllegalAccessException e) {
				CaliException ex = new CaliException(exType.exRuntime);
				ex.setException(this.getLineNum(), "EXTERN_ILLEGAL_ACCESS", "External call, illegal access exception for method '" + this.getName() + "'.", env.getCallStack().getStackTrace());
				return ex;
			} catch (IllegalArgumentException e) {
				CaliException ex = new CaliException(exType.exRuntime);
				ex.setException(this.getLineNum(), "EXTERN_ILLEGAL_ARGUMENT", "External call, illegal argument exception for method '" + this.getName() + "'.", env.getCallStack().getStackTrace());
				return ex;
			} catch(StackOverflowError e) {
				CaliException ex = new CaliException(exType.exRuntime);
				ex.setException(this.getLineNum(), "EXTERN_STACK_OVERFLOW", "External call, stack overflow exception for method '" + this.getName() + "'. Infinite recursion perhaps?", env.getCallStack().getStackTrace());
				return ex;
			} catch (InvocationTargetException e) {
				CaliException ex = new CaliException(exType.exRuntime);
				ex.setException(this.getLineNum(), "EXTERN_INVOCATION_TARGET_EXCEPTION", "External call, invocation target exception for method '" + this.getName() + "', the external method threw an uncaught exception: " + e.getTargetException().toString(), env.getCallStack().getStackTrace());
				return ex;
			} catch (caliException e) {
				CaliException ex = new CaliException(exType.exRuntime);
				ex.setException(this.getLineNum(), "EXTERN_EXCEPTION", e.getMessage(), env.getCallStack().getStackTrace());
				return ex;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			CaliException ex = new CaliException(exType.exRuntime);
			ex.setException(this.getLineNum(), "EXTERN_OBJECT_NOT_FOUND", "External object not found when calling '" + this.getName() + "'.", env.getCallStack().getStackTrace());
			return ex;
		}
		
		return ret;
	}
	
	private ArrayList<CaliType> getExternArgs(Environment env, CaliList eargs) throws caliException
	{
		ArrayList<CaliType> args = new ArrayList<CaliType>();
		
		boolean etcFound = false;
		CaliList etcList = new CaliList();
		cType etype = cType.cUndef;
		
		for(int i = 0; (i < this.argList.getArgs().size())||(i < eargs.size()); i++) {
			if((i < this.argList.getArgs().size())&&(this.argList.getArgs().get(i).getType() == astNodeType.ETCETERA)) {
				if(!etcFound) {
					etype = ((astEtcetera)this.argList.getArgs().get(i)).getPrimType();
					etcFound = true;
				}
				else
					throw new caliException(this, "Already found a etcetera (...) definition in this function definition.", env.stackTraceToString());
			}
			
			if(etcFound) {
				if(i < eargs.size()) {
					if(etype != cType.cUndef) {
						if(eargs.getValue().get(i).getType() == etype) {
							etcList.add(eargs.getValue().get(i));
						}
						else
							throw new caliException(this, "Etcetera (...) list is expecting type '" + etype.name() + " but found type '" + eargs.getValue().get(i).getType().name() + "'.", env.stackTraceToString());
					}
					else
						etcList.add(eargs.getValue().get(i));
				}
			} else {
				if(etcFound) {
					throw new caliException(this, "Cannot have arguments in function definition after etcetera (...).", env.stackTraceToString());
				} else {
					if(this.argList.getArgs().size() > i) {
						astNode v = this.argList.getArgs().get(i);

						if(i < eargs.size()) {
							if((v.getPrimType() == cType.cUndef)||(v.getPrimType() == eargs.getValue().get(i).getType())||(eargs.getValue().get(i).isNull())) {
								args.add(eargs.getValue().get(i));
							} else if((v.getPrimType() == cType.cInt)&&(eargs.getValue().get(i).getType() == cType.cDouble)){
								CaliInt ai = new CaliInt((int)((CaliDouble)eargs.getValue().get(i)).getValue());
								args.add(ai);
							} else if((v.getPrimType() == cType.cDouble)&&(eargs.getValue().get(i).getType() == cType.cInt)) {
								CaliDouble ad = new CaliDouble((double)((CaliInt)eargs.getValue().get(i)).getValue());
								args.add(ad);
							} else {
								throw new caliException(this, "Expecting type '" + v.getPrimType().name() + " but found type '" + eargs.getValue().get(i).getType().name() + "'.", env.stackTraceToString());
							}
						} else {
							args.add(this.argList.getArgs().get(i).eval(env));
						}
					} else {
						throw new caliException(this, "Incorrect number of arguments provided to function '" + this.getName() + "'. Provided " + String.valueOf(eargs.size()) + " but expecting " + String.valueOf(this.argList.getArgs().size()) + ".", env.stackTraceToString());
					}
				}
			}
		}
		
		if(etcFound) {
			args.add(etcList);
		}
		
		return args;
	}
}
