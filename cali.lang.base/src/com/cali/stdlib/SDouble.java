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

import com.cali.types.CaliDouble;
import com.cali.types.CaliException;
import com.cali.types.CaliInt;
import com.cali.types.CaliString;
import com.cali.types.CaliType;

public class SDouble {
	public static CaliType maxExp(ArrayList<CaliType> args) {
		return new CaliInt(Double.MAX_EXPONENT);
	}
	
	public static CaliType maxVal(ArrayList<CaliType> args) {
		return new CaliDouble(Double.MAX_VALUE);
	}
	
	public static CaliType minExp(ArrayList<CaliType> args) {
		return new CaliInt(Double.MIN_EXPONENT);
	}
	
	public static CaliType minNormal(ArrayList<CaliType> args) {
		return new CaliDouble(Double.MIN_NORMAL);
	}
	
	public static CaliType minVal(ArrayList<CaliType> args) {
		return new CaliDouble(Double.MIN_VALUE);
	}
	
	public static CaliType nanVal(ArrayList<CaliType> args) {
		return new CaliDouble(Double.NaN);
	}
	
	public static CaliType negInfinity(ArrayList<CaliType> args) {
		return new CaliDouble(Double.NEGATIVE_INFINITY);
	}
	
	public static CaliType posInfinity(ArrayList<CaliType> args) {
		return new CaliDouble(Double.POSITIVE_INFINITY);
	}
	
	public static CaliType size(ArrayList<CaliType> args) {
		return new CaliInt(Double.SIZE);
	}
	
	public static CaliType parse(ArrayList<CaliType> args) {
		try {
			return new CaliDouble(Double.parseDouble(((CaliString)args.get(0)).getValue()));
		} catch(Exception e) {
			return new CaliException("Double.parse(): Double parse exception.");
		}
	}
}
