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
import com.cali.types.CaliException;
import com.cali.types.CaliInt;
import com.cali.types.CaliString;
import com.cali.types.CaliType;

public class SInt {
	public static CaliType parse(Environment env, ArrayList<CaliType> args) {
		try {
			if(args.get(1).isNull()) {
				return new CaliInt(Long.parseLong(((CaliString)args.get(0)).getValue()));
			} else {
				return new CaliInt(Long.parseLong(((CaliString)args.get(0)).getValue(), (int)((CaliInt)args.get(1)).getValue()));
			}
		} catch(Exception e) {
			return new CaliException("Int.parse(): Integer parse exception.");
		}
	}
	
	public static CaliType maxVal(Environment env, ArrayList<CaliType> args) {
		return new CaliInt(Long.MAX_VALUE);
	}
	
	public static CaliType minVal(Environment env, ArrayList<CaliType> args) {
		return new CaliInt(Long.MIN_VALUE);
	}
}
