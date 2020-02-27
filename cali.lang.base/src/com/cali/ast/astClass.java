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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cali.CallStack;
import com.cali.Engine;
import com.cali.Environment;
import com.cali.types.CaliBool;
import com.cali.types.CaliDouble;
import com.cali.types.CaliException;
import com.cali.types.CaliException.exType;
import com.cali.types.CaliInt;
import com.cali.types.CaliList;
import com.cali.types.CaliMap;
import com.cali.types.CaliNull;
import com.cali.types.CaliObject;
import com.cali.types.CaliString;
import com.cali.types.CaliType;
import com.cali.types.Members;

public class astClass extends astNode implements astNodeInt {
	// Has the class init ran yet? Init does things like link to 
	// extended classes ...
	private boolean initRan = false;
	
	// Is the class static.
	private boolean isStatic = false;

	// Is the class external.
	private boolean isExtern = false;
	
	// External class name.
	private String externClassName = "";
	
	// List of extended class names.
	private ArrayList<String> extendedClasses = new ArrayList<String>();
	
	// Constructor definition.
	private astFunctDef constructor = null;
	
	// External class reverence.
	@SuppressWarnings("rawtypes")
	private Class externClass = null;
	
	// Class members.
	private List<String> membList = new ArrayList<String>();
	private Map<String, astNode> membDefs = new ConcurrentHashMap<String, astNode>();
	
	// Class functions.
	private List<String> functList = new ArrayList<String>();
	private Map<String, astNode> functDefs = new ConcurrentHashMap<String, astNode>();
	
	// Inherited members and functions are kept track of so we can 
	// reproduce the AST later.
	private List<String> inheritedMembers = new ArrayList<String>();
	private List<String> inheritedFuncts = new ArrayList<String>();

	/**
	 * Default constructor.
	 */
	public astClass() {
		this.setType(astNodeType.CLASS);
	}
	
	/**
	 * Creates a new class.
	 * @param Name A string with the class name.
	 */
	public astClass(String Name) {
		this.setType(astNodeType.CLASS);
		this.setName(Name);
	}
	
	public void addMember(String Name, astNode Value) {
		this.membList.add(Name);
		this.membDefs.put(Name, Value);
	}
	
	public boolean containsMember(String Name) {
		return this.membDefs.containsKey(Name);
	}
	
	public astNode getMember(String Name) {
		return this.membDefs.get(Name);
	}
	
	public Map<String, astNode> getMembers() {
		return this.membDefs;
	}
	
	public void addFunction(String Name, astNode Value) {
		this.functList.add(Name);
		this.functDefs.put(Name, Value);
	}
	
	public boolean containsFunction(String Name) {
		return this.functDefs.keySet().contains(Name);
	}
	
	public astNode getFunct(String Name) {
		return this.functDefs.get(Name);
	}
	
	public void init(Environment env) throws caliException {
		if (!this.initRan) {
			this.initRan = true;
			
			boolean foundExtern = false;
			if (this.isExtern) { foundExtern = true; }
			
			for(String className : this.extendedClasses) {
				astClass ac = env.getClassByName(className);
				
				if(ac != null) {
					if(ac.getExtern()) {
						if (ac.getName().equals("object")) {
							// Set object extern stuff to begin with if not set already.
							if (this.externClass == null) {
								this.isExtern = true;
								this.externClassName = ac.getExternClassName();
								this.externClass = ac.getExternClass();
							}
						} else {
							if(foundExtern) {
								throw new caliException(this, "Cannot inherit from two external classes. First is '" + this.externClassName + "' and second is '" + className + "'.", env.stackTraceToString());
							}
							
							foundExtern = true;
							this.isExtern = true;
							this.externClassName = ac.getExternClassName();
							this.externClass = ac.getExternClass();
						}
					}					
				} else {
					throw new caliException(this, "Extended class '" + className + "' not found.", env.stackTraceToString());
				}
			}
		}
	}
	
	/*
	 * Run functions
	 */
	public CaliType instantiate(Environment env) throws caliException {
		return this.instantiate(env, true, new CaliList());
	}
	
	public CaliType instantiate(Environment env, boolean getRef, CaliList args) throws caliException {
		// Fisrt run init if it hasn't been ran for the class.
		this.init(env);
		
		CaliObject ci;
		if (this.isExtern) {
			ci = (CaliObject) instantiateExtern();
		} else {
			ci = new CaliObject();
		}
		ci.setClassDef(this);
		
		// Instantiated inherited classes.
		this.instantiateInheritedClasses(env, ci);
		
		// Instantiate members.
		this.instantiateMembers(env, ci);
		
		/*
		 * Call constructor.
		 */
		if(this.constructor != null) {
			CallStack constStack = new CallStack();
			Environment tenv = new Environment(env.getEngine());
			tenv.setEnvironment(ci, env.getLocals(), constStack);
			CaliType ret = this.call(tenv, getRef, getName(), args);
			if (ret.isEx()) {
				return ret;
			}
		}
		
		return ci;
	}
	
	public void instantiateMembers(Environment env, CaliObject ci) throws caliException {
		for (int i = 0; i < this.membList.size(); i++) {
			astNode cur = this.membDefs.get(this.membList.get(i));
			switch(cur.getType()) {
			case VAR:
				ci.addMember(cur.getName(), cur.eval(env));
				break;
			case BOOL:
				ci.addMember(cur.getName(), cur.eval(env));
				break;
			case INT:
				ci.addMember(cur.getName(), cur.eval(env));
				break;
			case DOUBLE:
				ci.addMember(cur.getName(), cur.eval(env));
				break;
			case STRING:
				ci.addMember(cur.getName(), cur.eval(env));
				break;
			case OBJ:
				ci.addMember(cur.getName(), cur.eval(env));
				break;
			case LIST:
				ci.addMember(cur.getName(), cur.eval(env));
				break;
			case MAP:
				ci.addMember(cur.getName(), cur.eval(env));
				break;
			case NULL:
				ci.addMember(cur.getName(), cur.eval(env));
				break;
			default:
				throw new caliException("astClass.instantiateMembers(): Unexpected node type '" + cur.getType().name() + "' found.");
			}
		}
	}
	
	public CaliType call (Environment env, boolean getRef, String functName, CaliList args) throws caliException {
		CaliType ret = new CaliNull();
		
		if (this.functDefs.containsKey(functName)) {
			astFunctDef fdef = (astFunctDef) this.functDefs.get(functName);
			// Local function variables and objects
			Members locals = new Members();
			Environment tenv = new Environment(env.getEngine());
			CaliObject ci = env.getClassInstance();
			
			
			if (env.getCurObj() != null && env.getCurObj() instanceof CaliObject) {
				ci = (CaliObject) env.getCurObj();
			}
			tenv.setEnvironment(ci, locals, env.getCallStack());
			if(fdef.getExtern()) {
				CaliType tmp = ((astFunctDef)this.functDefs.get(functName)).initArgs(tenv, args);
				if(!tmp.isEx()) {
					//ret = env.getClassInstance().externMod.call(functName, args);
					
					ret = fdef.callExtern(tenv, args);
					/*
					 * Check ret for exception now.
					 */
				 } else {
					 ret = tmp;
				 }
			} else {
				ret = ((astFunctDef)this.functDefs.get(functName)).call(tenv, getRef, args, this.getFileName());
			}
		} else {
			CaliException ce = new CaliException(exType.exRuntime);
			ce.setException(this.getLineNum(), "FUNCT_NOT_FOUND", "Object '" + this.getName() + "' doesn't have function '" + functName + "'.", env.getCallStack().getStackTrace());
			ret = ce;
		}
		
		return ret;
	}
	
	private void instantiateInheritedClasses(Environment env, CaliObject cobj) throws caliException {
		for(String className : this.extendedClasses) {
			astClass ac = env.getClassByName(className);
			
			if(ac != null) {
				if(ac.getExtern()) {
					if (!ac.getName().equals("object")) {
						CaliObject ao = (CaliObject) ac.instantiate(env);
						if(ao != null) {
							cobj.setExternObject(ao.getExternObject());
						} else {
							throw new caliException(this, "Failed to instantiate class '" + className + "', object is null.", env.stackTraceToString());
						}
					}
				}
			
				ac.instantiateMembers(env, cobj);
				
				for(String key : ac.getFuncts().keySet()) {
					// If the child most class doesn't contain the function, add it. (This allows overwrite functionality.)
					if(!this.functDefs.containsKey(key)) {
						this.functDefs.put(key, ac.getFuncts().get(key));
						this.inheritedFuncts.add(key);
					}
				}
				
			} else {
				throw new caliException(this, "Extended class '" + className + "' not found.", env.stackTraceToString());
			}
		}
	}
	
	private CaliType instantiateExtern() throws caliException {
		boolean primType = true;
		CaliObject obj;
		
		// Instantiate native type, or generic object.
		if (this.getName().equals("bool")) {
			obj = new CaliBool();
		} else if (this.getName().equals("int")) {
			obj = new CaliInt();
		} else if (this.getName().equals("double")) {
			obj = new CaliDouble();
		} else if (this.getName().equals("string")) {
			obj = new CaliString();
		} else if (this.getName().equals("list")) {
			obj = new CaliList();
		} else if (this.getName().equals("map")) {
			obj = new CaliMap();
		} else {
			primType = false;
			obj = new CaliObject();
		}
		
		obj.setClassDef(this);
		if (primType || this.externClass.getName().equals("com.cali.types.CaliObject")) {
			obj.setExternObject(obj);
		} else {
			try {
		        obj.setExternObject(this.externClass.newInstance());
		    } catch (SecurityException e) {
				throw new caliException("Instantiate extern security exception: " + e.getMessage());
			} catch (InstantiationException e) {
				throw new caliException("Instantiate extern instantiation exception: " + e.getMessage());
			} catch (IllegalAccessException e) {
				throw new caliException("Instantiate extern illegal access exception: " + e.getMessage());
			} catch (IllegalArgumentException e) {
				throw new caliException("Instantiate extern illegal argument exception: " + e.getMessage());
			}
		}
		
		return obj;
	}
	
	private void loadExternClass() throws caliException {
		ClassLoader cl = Engine.class.getClassLoader();
	    try {
	        this.externClass = cl.loadClass(this.externClassName);
	    } catch (ClassNotFoundException e) {
	    	throw new caliException("Extern class '" + this.externClassName + "' not found.");
	    } catch (SecurityException e) {
			throw new caliException("Extern class security exception: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new caliException("Extern class illegal argument excetpion: " + e.getMessage());
		}
	}

	
	@Override
	public String toString() {
		return this.toString(0);
	}
	
	public String toString(int Level) {
		String rstr = "";
		rstr += getTabs(Level) + "{\n";
		rstr += this.getNodeStr(Level + 1) + ",\n";
		rstr += getTabs(Level + 1) + "\"fileName\": \"" + this.getFileName() + "\",\n";
		rstr += getTabs(Level + 1) + "\"modRef\": \"" + this.externClassName + "\",\n";
		rstr += getTabs(Level + 1) + "\"static\": \"" + this.isStatic + "\",\n";
		rstr += getTabs(Level + 1) + "\"extern\": \"" + this.isExtern + "\",\n";

		rstr += getTabs(Level + 1) + "\"definitions\": [\n";
		for(int i = 0; i < this.membList.size(); i++)
			rstr += ((astNodeInt)this.membDefs.get(this.membList.get(i))).toString(Level + 2) + ",\n";
		rstr += getTabs(Level + 1) + "],\n";

		if(this.constructor != null)
		{
			rstr += getTabs(Level + 1) + "\"constructor\":\n";
			rstr += this.constructor.toString(Level + 1) + ",\n";
		}

		rstr += getTabs(Level + 1) + "\"functionDefinitions\": [\n";
		for(int i = 0; i < this.functList.size(); i++)
			rstr += ((astNodeInt)this.functDefs.get(this.functList.get(i))).toString(Level + 2) + ",\n";
		rstr += getTabs(Level + 1) + "],\n";

		rstr += getTabs(Level) + "}";
		return rstr;
	}

	public boolean hasMain() {
		if(this.functDefs.containsKey("main"))
			return true;
		return false;
	}
	
	public void setInheritedMembers(ArrayList<String> InheritedMembers) { this.inheritedMembers = InheritedMembers; }
	public List<String> getInheritedMembers() { return this.inheritedMembers; }
	
	public void setInheritedFuncts(ArrayList<String> InheritedFuncts) { this.inheritedFuncts = InheritedFuncts; }
	public List<String> getInheritedFuncts() { return this.inheritedFuncts; }

	public boolean getStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public boolean getExtern() {
		return isExtern;
	}

	public void setExtern(boolean isExtern) {
		this.isExtern = isExtern;
	}
	
	public String getExternClassName() {
		return externClassName;
	}

	public void setExternClassName(String externClass) throws caliException {
		this.externClassName = externClass;
		this.loadExternClass();
	}
	
	@SuppressWarnings("rawtypes")
	public void setExternClass(Class C) { this.externClass = C; }
	
	@SuppressWarnings("rawtypes")
	public Class getExternClass() { return this.externClass; }

	@Override
	public CaliType evalImpl(Environment env, boolean getref) {
		return new CaliNull();
	}

	public ArrayList<String> getExtendedClasses() {
		return extendedClasses;
	}

	public void setExtendedClasses(ArrayList<String> extendedClasses) {
		this.extendedClasses = extendedClasses;
	}

	public astFunctDef getConstructor() {
		return constructor;
	}

	public void setConstructor(astFunctDef constructor) {
		this.constructor = constructor;
	}

	public boolean instanceOf(String Name) {
		if (this.getName().equals(Name)) {
			return true;
		} else if (this.extendedClasses.contains(Name)) {
			return true;
		} else if (this.getName().equals("cnull") && Name.equals("null")) {
			return true;
		}
		return false;
	}
	
	public Map<String, astNode> getFuncts() {
		return this.functDefs;
	}
	
	@Override
	public void setName(String Name) {
		// Don't add object as extend class to object.
		if (!Name.equals("object") && !this.extendedClasses.contains("object")) {
			this.extendedClasses.add("object");
		}
		// Actually set the name.
		super.setName(Name);
	}
}
