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

import com.cali.Environment;
import com.cali.types.CaliException;
import com.cali.types.CaliType;
import com.cali.types.CaliTypeInt;

public class astThrow extends astNode implements astNodeInt
{
	private astNode expression = new astNull();
	
	public astThrow() { this.setType(astNodeType.THROW); }
	
	public void setExpression(astNode Expression) { this.expression = Expression; }
	public astNode getExpression() { return this.expression; }

	public String toString() {
		return this.toString(0);
	}
	
	@Override
	public String toString(int Level) {
		String rstr = "";
		rstr += getTabs(Level) + "{\n";
		rstr += this.getNodeStr(Level + 1) + ",\n";
		rstr += getTabs(Level + 1) + "\"expression\":\n";
		rstr += ((astNodeInt)this.expression).toString(Level + 2);
		rstr += getTabs(Level) + "}";
		return rstr;
	}

	@Override
	public CaliType evalImpl(Environment env, boolean getref) throws caliException {
		CaliType ret = this.expression.eval(env, getref);
		return new CaliException(((CaliTypeInt)ret).str());
	}
}
