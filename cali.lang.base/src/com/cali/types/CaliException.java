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
import com.cali.ast.caliException;
import com.cali.stdlib.console;

public class CaliException extends CaliObject implements CaliTypeInt {
	public enum exType {
		exUndef,
		exInternal,
		exRuntime
	};
	
	private exType et = exType.exUndef;
	private int lineNumber = -1;
	private String id = "";
	private String text = "";
	private String details = "";
	private String stackTrace = "";
	
	public CaliException() {
		this.setType(cType.cException);
		
		// Setup linkage for string object.
		this.setExternObject(this);
		try {
			this.setClassDef(Universe.get().getClassDef("exception"));
		} catch (caliException e) {
			console.get().err("CaliException(): Unexpected exception getting class definition: " + e.getMessage());
		}
	}
	
	public CaliException(exType ExType) {
		this();
		this.et = ExType;
	}
	
	public CaliException(String Text) {
		this(exType.exRuntime);
		this.text = Text;
		this.details = Text;
	}

	public void setException(int LineNum, String Id, String Text, String Details, String StackTrace) {
		this.lineNumber = LineNum;
		this.id = Id;
		this.text = Text;
		this.details = Details;
		this.stackTrace = StackTrace;
	}
	
	public void setException(int LineNum, String Id, String Text, String StackTrace) {
		this.lineNumber = LineNum;
		this.id = Id;
		this.text = Text;
		this.details = Text;
		this.stackTrace = StackTrace;
	}
	
	public exType getEt() {
		return et;
	}

	public void setEt(exType et) {
		this.et = et;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String getExceptionTypeString() {
		return this.et.name();
	}
	
	public String stackTraceToString() {
		String rstr = "line ";
		rstr += this.lineNumber;
		rstr += ": ";
		rstr += this.et.name();
		rstr += " Exception. [" + this.id + "] :: " + text + "\n";
		rstr += this.stackTrace;
		return rstr;
	}
	
	@Override
	public String toString(int Level) {
		String rstr = "";

		rstr += CaliType.getTabs(Level);
		rstr += "line ";
		rstr += this.lineNumber;
		rstr += ": ";
		rstr += "[";
		rstr += this.getType().name();
		rstr += "] ";
		rstr += this.et.name();
		rstr += " Exception\n";

		rstr += CaliType.getTabs(Level + 1);
		rstr += "id: " + this.id + "\n";

		rstr += CaliType.getTabs(Level + 1);
		rstr += "text: " + this.text + "\n";

		rstr += CaliType.getTabs(Level + 1);
		rstr += "details: " + this.details + "\n";

		rstr += CaliType.getTabs(Level + 1);
		rstr += "stackTrace: " + this.stackTrace + "\n";

		return rstr;
	}

	@Override
	public String str() {
		return this.stackTraceToString();
	}
	
	public String str(int Level) {
		return this.str();
	}
	
	public CaliType getLineNumber(Environment env, ArrayList<CaliType> args) {
		return new CaliInt(this.lineNumber);
	}
	
	public CaliType getExceptionType(Environment env, ArrayList<CaliType> args) {
		return new CaliString(this.et.name());
	}
	
	public CaliType getId(Environment env, ArrayList<CaliType> args) {
		return new CaliString(this.id);
	}
	
	public CaliType getText(Environment env, ArrayList<CaliType> args) {
		return new CaliString(this.text);
	}
	
	public CaliType getDetails(Environment env, ArrayList<CaliType> args) {
		return new CaliString(this.details);
	}
	
	public CaliType getStackTrace(Environment env, ArrayList<CaliType> args) {
		return new CaliString(this.stackTrace);
	}
}
