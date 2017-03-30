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

import com.cali.ast.caliException;
import com.cali.types.CaliDouble;
import com.cali.types.CaliException;
import com.cali.types.CaliInt;
import com.cali.types.CaliType;
import com.cali.types.cType;

public class CMath {
	public static CaliType e(ArrayList<CaliType> args) {
		return new CaliDouble(Math.E);
	}
	
	public static CaliType pi(ArrayList<CaliType> args) {
		return new CaliDouble(Math.PI);
	}
	
	public static CaliType abs(ArrayList<CaliType> args) {
		CaliType ret = new CaliInt(0);
		CaliType arg = args.get(0);
		if(arg.getType() == cType.cBool);
		else if(arg.getType() == cType.cInt) ret = new CaliInt(Math.abs((int)((CaliInt)arg).getValue()));
		else if(arg.getType() == cType.cDouble) ret = new CaliDouble(Math.abs(((CaliDouble)arg).getValue()));
		else ret = new CaliException("math.abs(): Expecting object of type bool, int or double but found " + arg.getType().name() + " instead.");
		return ret;
	}
	
	public static CaliType acos(ArrayList<CaliType> args) {
		return new CaliDouble(Math.acos(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType asin(ArrayList<CaliType> args) {
		return new CaliDouble(Math.asin(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType atan(ArrayList<CaliType> args) {
		return new CaliDouble(Math.atan(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType cbrt(ArrayList<CaliType> args) {
		return new CaliDouble(Math.cbrt(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType ceil(ArrayList<CaliType> args) {
		return new CaliDouble(Math.ceil(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType copySign(ArrayList<CaliType> args) {
		return new CaliDouble(Math.copySign(((CaliDouble)args.get(0)).getValue(), ((CaliDouble)args.get(1)).getValue()));
	}
	
	public static CaliType cos(ArrayList<CaliType> args) {
		return new CaliDouble(Math.cos(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType cosh(ArrayList<CaliType> args) {
		return new CaliDouble(Math.cosh(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType exp(ArrayList<CaliType> args) {
		return new CaliDouble(Math.exp(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType expm1(ArrayList<CaliType> args) {
		return new CaliDouble(Math.expm1(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType floor(ArrayList<CaliType> args) {
		return new CaliDouble(Math.floor(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType getExponent(ArrayList<CaliType> args) {
		return new CaliDouble(Math.getExponent(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType hypot(ArrayList<CaliType> args) {
		return new CaliDouble(Math.hypot(((CaliDouble)args.get(0)).getValue(), ((CaliDouble)args.get(1)).getValue()));
	}
	
	public static CaliType IEEEremainder(ArrayList<CaliType> args) {
		return new CaliDouble(Math.IEEEremainder(((CaliDouble)args.get(0)).getValue(), ((CaliDouble)args.get(1)).getValue()));
	}
	
	public static CaliType log(ArrayList<CaliType> args) {
		return new CaliDouble(Math.log(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType log10(ArrayList<CaliType> args) {
		return new CaliDouble(Math.log10(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType log1p(ArrayList<CaliType> args) {
		return new CaliDouble(Math.log1p(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType max(ArrayList<CaliType> args) throws caliException {
		CaliType ret = new CaliInt(0);
		CaliType arg = args.get(0);
		CaliType arg2 = args.get(1);
		if(arg.isNumericType() && arg2.isNumericType()) ret = new CaliDouble(Math.max(arg.getNumericDouble(), arg2.getNumericDouble()));
		else ret = new CaliException("math.abs(): Expecting object of type bool, int or double but found " + arg.getType().name() + " instead.");
		return ret;
	}
	
	public static CaliType min(ArrayList<CaliType> args) throws caliException {
		CaliType ret = new CaliInt(0);
		CaliType arg = args.get(0);
		CaliType arg2 = args.get(1);
		if(arg.isNumericType()&&arg2.isNumericType()) ret = new CaliDouble(Math.min(arg.getNumericDouble(), arg2.getNumericDouble()));
		else ret = new CaliException("math.abs(): Expecting object of type bool, int or double but found " + arg.getType().name() + " instead.");
		return ret;
	}
	
	public static CaliType nextAfter(ArrayList<CaliType> args) {
		return new CaliDouble(Math.nextAfter(((CaliDouble)args.get(0)).getValue(), ((CaliDouble)args.get(1)).getValue()));
	}
	
	public static CaliType nextUp(ArrayList<CaliType> args) {
		return new CaliDouble(Math.nextUp(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType pow(ArrayList<CaliType> args) {
		return new CaliDouble(Math.pow(((CaliDouble)args.get(0)).getValue(), ((CaliDouble)args.get(1)).getValue()));
	}
	
	public static CaliType rand(ArrayList<CaliType> args) {
		return new CaliDouble(Math.random());
	}
	
	public static CaliType rint(ArrayList<CaliType> args) {
		return new CaliDouble(Math.rint(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType round(ArrayList<CaliType> args) {
		return new CaliDouble(Math.round(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType scalb(ArrayList<CaliType> args) {
		return new CaliDouble(Math.scalb(((CaliDouble)args.get(0)).getValue(), (int)((CaliInt)args.get(1)).getValue()));
	}
	
	public static CaliType signum(ArrayList<CaliType> args) {
		return new CaliDouble(Math.signum(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType sin(ArrayList<CaliType> args) {
		return new CaliDouble(Math.sin(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType sinh(ArrayList<CaliType> args) {
		return new CaliDouble(Math.sinh(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType sqrt(ArrayList<CaliType> args) {
		return new CaliDouble(Math.sqrt(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType tan(ArrayList<CaliType> args) {
		return new CaliDouble(Math.tan(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType tanh(ArrayList<CaliType> args) {
		return new CaliDouble(Math.tanh(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType toDeg(ArrayList<CaliType> args) {
		return new CaliDouble(Math.toDegrees(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType toRad(ArrayList<CaliType> args) {
		return new CaliDouble(Math.toRadians(((CaliDouble)args.get(0)).getValue()));
	}
	
	public static CaliType ulp(ArrayList<CaliType> args) {
		return new CaliDouble(Math.ulp(((CaliDouble)args.get(0)).getValue()));
	}
}
