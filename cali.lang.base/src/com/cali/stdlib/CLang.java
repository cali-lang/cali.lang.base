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

import com.cali.Engine;
import com.cali.Environment;
import com.cali.Universe;
import com.cali.ast.astClass;
import com.cali.types.*;

public class CLang {
	public static CaliType type(Environment env, ArrayList<CaliType> args) {
		CaliType ct = args.get(0);
		if (ct.getType() == cType.cObject) {
			return new CaliString(((CaliObject)ct).getClassDef().getName());
		} else {
			return new CaliString(args.get(0).getType().name().toLowerCase().substring(1));
		}
	}

	public static CaliType getClassCalidoc(Environment env, ArrayList<CaliType> args) {
		if ((Boolean)env.getEngine().getSecurityManager().getProperty("calidoc.class.getJson")) {
			String className = ((CaliString)args.get(0)).getValue();
			Engine eng = env.getEngine();
			astClass cls = eng.getClassByName(className);
			return cls.getCalidoc();
		}
		return new CaliNull();
	}
}
