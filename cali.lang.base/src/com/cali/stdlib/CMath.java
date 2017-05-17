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
import com.cali.ast.caliException;
import com.cali.types.CaliDouble;
import com.cali.types.CaliException;
import com.cali.types.CaliInt;
import com.cali.types.CaliType;
import com.cali.types.cType;

public class CMath {
	public static CaliType e(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.E);
	}
	
	public static CaliType pi(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.PI);
	}
	
	public static CaliType abs(Environment env, ArrayList<CaliType> args) {
		CaliType ret = new CaliInt(0);
		CaliType arg = args.get(0);
		if(arg.getType() == cType.cBool);
		else if(arg.getType() == cType.cInt) ret = new CaliInt(Math.abs((int)((CaliInt)arg).getValue()));
		else if(arg.getType() == cType.cDouble) ret = new CaliDouble(Math.abs(((CaliDouble)arg).getValue()));
		else ret = new CaliException("math.abs(): Expecting object of type bool, int or double but found " + arg.getType().name() + " instead.");
		return ret;
	}
	
	public static CaliType acos(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.acos(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType asin(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.asin(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType atan(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.atan(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType cbrt(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.cbrt(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType ceil(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.ceil(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType copySign(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.copySign(((CaliDouble)args.get(0)).getValue(), ((CaliDouble)args.get(1)).getValue()));
	}
	
	public static CaliType cos(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.cos(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType cosh(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.cosh(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType exp(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.exp(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType expm1(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.expm1(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType floor(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.floor(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType getExponent(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.getExponent(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType hypot(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.hypot(((CaliDouble)args.get(0)).getValue(), ((CaliDouble)args.get(1)).getValue()));
	}
	
	public static CaliType IEEEremainder(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.IEEEremainder(((CaliDouble)args.get(0)).getValue(), ((CaliDouble)args.get(1)).getValue()));
	}
	
	public static CaliType log(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.log(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType log10(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.log10(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType log1p(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.log1p(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType max(Environment env, ArrayList<CaliType> args) throws caliException {
		CaliType ret = new CaliInt(0);
		CaliType arg = args.get(0);
		CaliType arg2 = args.get(1);
		if(arg.isNumericType() && arg2.isNumericType()) ret = new CaliDouble(Math.max(arg.getNumericDouble(), arg2.getNumericDouble()));
		else ret = new CaliException("math.abs(): Expecting object of type bool, int or double but found " + arg.getType().name() + " instead.");
		return ret;
	}
	
	public static CaliType min(Environment env, ArrayList<CaliType> args) throws caliException {
		CaliType ret = new CaliInt(0);
		CaliType arg = args.get(0);
		CaliType arg2 = args.get(1);
		if(arg.isNumericType()&&arg2.isNumericType()) ret = new CaliDouble(Math.min(arg.getNumericDouble(), arg2.getNumericDouble()));
		else ret = new CaliException("math.abs(): Expecting object of type bool, int or double but found " + arg.getType().name() + " instead.");
		return ret;
	}
	
	public static CaliType nextAfter(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.nextAfter(((CaliDouble)args.get(0)).getValue(), ((CaliDouble)args.get(1)).getValue()));
	}
	
	public static CaliType nextUp(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.nextUp(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType pow(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.pow(((CaliDouble)args.get(0)).getValue(), ((CaliDouble)args.get(1)).getValue()));
	}
	
	public static CaliType rand(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.random());
	}
	
	public static CaliType rint(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.rint(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType round(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.round(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType scalb(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.scalb(((CaliDouble)args.get(0)).getValue(), (int)((CaliInt)args.get(1)).getValue()));
	}
	
	public static CaliType signum(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.signum(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType sin(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.sin(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType sinh(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.sinh(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType sqrt(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.sqrt(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType tan(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.tan(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType tanh(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.tanh(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType toDeg(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.toDegrees(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType toRad(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.toRadians(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType ulp(Environment env, ArrayList<CaliType> args) {
		return new CaliDouble(Math.ulp(((CaliDouble)args.get(0)).getValue()));
	}
}
