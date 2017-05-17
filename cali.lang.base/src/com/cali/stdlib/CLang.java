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
import com.cali.types.CaliObject;
import com.cali.types.CaliString;
import com.cali.types.CaliType;
import com.cali.types.cType;

public class CLang {
	public static CaliType type(Environment env, ArrayList<CaliType> args) {
		CaliType ct = args.get(0);
		if (ct.getType() == cType.cObject) {
			return new CaliString(((CaliObject)ct).getClassDef().getName());
		} else {
			return new CaliString(args.get(0).getType().name().toLowerCase().substring(1));
		}
	}
}
