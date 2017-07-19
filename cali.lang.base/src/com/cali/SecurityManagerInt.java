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

package com.cali;

import java.util.ArrayList;
import com.cali.types.CaliType;

/**
 * Security manager interface. The security manager defines the Java and Cali 
 * interfaces for setting and getting properties that manage security settings 
 * for the Cali Engine.
 * @author Austin Lehman
 */
public interface SecurityManagerInt {
	// Java get property value.
	public Object getProperty(String PropName);
	
	/*
	 * Cali set or get property. Either one of these may throw not permitted or 
	 * not implemented exceptions.
	 */
	public CaliType setProp(Environment env, ArrayList<CaliType> args);
	public CaliType getProp(Environment env, ArrayList<CaliType> args);
}
