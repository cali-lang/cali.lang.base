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


public class caliException extends Exception {
	// Serializable
	private static final long serialVersionUID = 1193764482058983012L;
	
	private String message = "";
	private String trace = "";

	public caliException(String Message) {
        super(Message);
        this.message = Message;
    }
	
    public caliException(astNode node, String Message, String Trace) {
    	super(node.getFileName() + " [" + String.valueOf(node.getLineNum()) + "]: " + Message + "\n" + Trace);
    	this.message = Message;
    	this.trace = Trace;
    }
    
    public caliException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    public String getCaliMessage() { return this.message; }
    public String getCaliTrace() { return this.trace; }
    
    public String getCaliStackTrace() { return this.message + "\n" + this.trace; }
}