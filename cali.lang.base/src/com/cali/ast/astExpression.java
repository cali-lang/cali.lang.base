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
import com.cali.types.CaliBool;
import com.cali.types.CaliDouble;
import com.cali.types.CaliException;
import com.cali.types.CaliException.exType;
import com.cali.types.CaliInt;
import com.cali.types.CaliList;
import com.cali.types.CaliMap;
import com.cali.types.CaliNull;
import com.cali.types.CaliObject;
import com.cali.types.CaliRef;
import com.cali.types.CaliString;
import com.cali.types.CaliType;
import com.cali.types.CaliTypeInt;
import com.cali.types.cType;

public class astExpression extends astNode implements astNodeInt {
	private astNode left = null;
	private astNode right = null;
	private expType eType = expType.UNDEF;
	
	public astExpression() {
		this.setType(astNodeType.EXP);
	}
	
	public astExpression(astNode Left) {
		this.setType(astNodeType.EXP);
		this.setLeft(Left);
	}

	@Override
	public String toString() {
		return this.toString(0);
	}
	
	@Override
	public String toString(int Level) {
		String rstr = "";
		rstr += getTabs(Level) + "{\n";
		rstr += this.getNodeStr(Level + 1) + ",\n";
		rstr += getTabs(Level + 1) + "\"eType\": \"" + this.eType.name() + "\"\n";
		if(this.left != null) {
			rstr += getTabs(Level + 1) + "\"left\":\n";
			rstr += ((astNodeInt)this.left).toString(Level + 1) + ",\n";
		}
		if(this.right != null) {
			rstr += getTabs(Level + 1) + "\"right\":\n";
			rstr += ((astNodeInt)this.right).toString(Level + 1) + ",\n";
		}
		if (this.getChild() != null) {
			rstr += getTabs(Level + 1) + "\"child\":\n";
			rstr += ((astNodeInt)this.getChild()).toString(Level + 1) + ",\n";
		}
		rstr += getTabs(Level) + "}";
		return rstr;
	}

	public astNode getLeft() {
		return left;
	}

	public void setLeft(astNode left) {
		this.left = left;
	}

	public astNode getRight() {
		return right;
	}

	public void setRight(astNode right) {
		this.right = right;
	}

	public expType geteType() {
		return eType;
	}

	public void seteType(expType eType) {
		this.eType = eType;
	}

	@Override
	public CaliType evalImpl(Environment env, boolean getRef) throws caliException {
		CaliType ret = new CaliNull();

		if((this.left != null)&&(this.right != null)) {
			switch(this.eType) {
				case ASSIGNMENT: {
					ret = this.assignment(env, getRef);
					break;
				} case ADD: {
					ret = this.oper(env, getRef);
					break;
				} case SUBTRACT: {
					ret = this.oper(env, getRef);
					break;
				} case MULTIPLY: {
					ret = this.oper(env, getRef);
					break;
				} case DIVIDE: {
					ret = this.oper(env, getRef);
					break;
				} case MODULUS: {
					ret = this.oper(env, getRef);
					break;
				} case EQEQ: {
					ret = this.oper(env, getRef);
					break;
				} case NOTEQ: {
					ret = this.oper(env, getRef);
					break;
				} case LT: {
					ret = this.oper(env, getRef);
					break;
				} case GT: {
					ret = this.oper(env, getRef);
					break;
				} case LTEQ: {
					ret = this.oper(env, getRef);
					break;
				} case GTEQ: {
					ret = this.oper(env, getRef);
					break;
				} case AND: {
					ret = this.oper(env, getRef);
					break;
				} case OR: {
					ret = this.oper(env, getRef);
					break;
				} case INSERT: {
					ret = this.oper(env, getRef);
					break;
				} case INSTANCEOF: {
					ret = this.oper(env, getRef);
					break;
				} case PLEQ: {
					ret = this.operEquals(env, getRef);
					break;
				} case MIEQ: {
					ret = this.operEquals(env, getRef);
					break;
				} case MUEQ: {
					ret = this.operEquals(env, getRef);
					break;
				} case DIEQ: {
					ret = this.operEquals(env, getRef);
					break;
				} default: {
					CaliException e = new CaliException(exType.exInternal);
					e.setException(this.getLineNum(), "UNDEFINED_EXPRESSION", "Undefined expression '" + this.eType.name() + "' found. (aExp::call)", "Undefined expression '" + this.eType.name() + "' found. (aExp::call)", env.getCallStack().getStackTrace());
					ret = e;
					break;
				}
			}
		} else if(this.left != null) {
			switch(this.eType) {
				case PLPL: {
					ret = this.operIncDec(env, getRef);
					break;
				} case MIMI: {
					ret = this.operIncDec(env, getRef);
					break;
				} case NOT: {
					ret = this.operLeft(env, getRef);
					break;
				} case COUNT: {
					ret = this.operLeft(env, getRef);
					break;
				} case INCLUDE: {
					ret = this.operLeft(env, getRef);
					break;
				} default: {
					CaliException e = new CaliException(exType.exInternal);
					e.setException(this.getLineNum(), "UNDEFINED_EXPRESSION", "Undefined expression '" + this.eType.name() + "' found. (aExp::call)", "Undefined expression '" + this.eType.name() + "' found. (aExp::call)", env.getCallStack().getStackTrace());
					ret = e;
					break;
				}
			}
		}
		
		// If child is defined, evaluate it as well.
		if (this.getChild() != null) {
		  Environment tenv = env;
		  if (ret instanceof CaliObject || ret.getType() == cType.cMap || ret.getType() == cType.cList) {
			tenv = env.clone(ret);
		  }
		  ret = this.getChild().eval(tenv, getRef);
		}

		
		return ret;
	}
	
	private CaliType assignment(Environment env, boolean getRef) throws caliException {
		CaliType ret = new CaliNull();
		
		CaliType lres = this.left.eval(env, true);
		if (!lres.isEx()) {
		  if (lres.getType() == cType.cRef) {
			CaliRef ref = (CaliRef)lres;
			CaliType rval = this.right.eval(env, getRef);
			if (!rval.isEx()) {
			  ref.assign(rval);
			  ret = rval;
			} else {
			  ret = rval;
			}
		  } else {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(this.getLineNum(), "ASSIGN_NOT_POSSIBLE", "astExpression.assignment(): Left side of assignment expression returned an object of type '" + lres.getType().name() + "' which can't be assigned.", env.getCallStack().getStackTrace());
			return e;
		  }
		} else {
		  ret = lres;
		}
		return ret;
	}
	
	private CaliType oper(Environment env, boolean getRef) throws caliException {
		CaliType ret = new CaliNull();

		CaliType r_left  = left.eval(env, getRef);
		if(!r_left.isEx()) {
			CaliType r_right = right.eval(env, getRef);
			if(!r_right.isEx()) {
				switch(this.eType) {
					case ADD: {
						ret = evalPlus(env, r_left, r_right);
						break;
					} case SUBTRACT: {
						ret = evalMinus(env, r_left, r_right);
						break;
					} case MULTIPLY: {
						ret = evalMult(env, r_left, r_right);
						break;
					} case DIVIDE: {
						ret = evalDiv(env, r_left, r_right);
						break;
					} case MODULUS: {
						ret = evalModulus(env, r_left, r_right);
						break;
					} case EQEQ: {
						ret = evalEqualsEquals(env, r_left, r_right);
						break;
					} case NOTEQ: {
						ret = evalNotEquals(env, r_left, r_right);
						break;
					} case LT: {
						ret = evalLessThan(env, r_left, r_right);
						break;
					} case GT: {
						ret = evalGreaterThan(env, r_left, r_right);
						break;
					} case LTEQ: {
						ret = evalLessThanEquals(env, r_left, r_right);
						break;
					} case GTEQ: {
						ret = evalGreaterThanEquals(env, r_left, r_right);
						break;
					} case AND: {
						ret = evalAnd(env, r_left, r_right);
						break;
					} case OR: {
						ret = evalOr(env, r_left, r_right);
						break;
					} case INSERT: {
						ret = evalInsert(env, r_left, r_right);
						break;
					} case INSTANCEOF: {
						ret = evalInstanceOf(env, r_left, r_right);
						break;
					} default: {
						CaliException e = new CaliException(exType.exInternal);
						e.setException(this.getLineNum(), "UNDEFINED_EXPRESSION", "Undefined operator expression '" + this.eType.name() + "' found. (aExp::call)", "Undefined operator expression '" + this.eType.name() + "' found. (aExp::call)", env.getCallStack().getStackTrace());
						ret = e;
						break;
					}
				}
			} else {
				ret = r_right;
			}
		} else {
			ret = r_left;
		}

		return ret;
	}
	
	private CaliType evalPlus(Environment env, CaliType r_left, CaliType r_right) {
		CaliType ret = new CaliNull();

		// Let's do actual addition.
		if (this.isNumber(r_left) && this.isNumber(r_right)) {
		  if (this.isInt(r_left) && this.isInt(r_right)) {
			ret = new CaliInt(this.getValueInt(r_left) + this.getValueInt(r_right));
		  } else if (this.isInt(r_left)) {
			ret = new CaliDouble((double)this.getValueInt(r_left) + ((CaliDouble)r_right).getValue());
		  } else if (this.isInt(r_right)) {
			ret = new CaliDouble(((CaliDouble)r_left).getValue() + (double)this.getValueInt(r_right));
		  } else {
			  ret = new CaliDouble(((CaliDouble)r_left).getValue() + ((CaliDouble)r_right).getValue());
		  }
		}
		
		// Otherwise, we are going to treat as concatenation.
		else {
		  ret = new CaliString(((CaliTypeInt)r_left).str() + ((CaliTypeInt)r_right).str());
		}

		return ret;
	}
	
	private CaliType evalMinus(Environment env, CaliType r_left, CaliType r_right) {
		CaliType ret = new CaliNull();

		if (this.isNumber(r_left) && this.isNumber(r_right)) {
		  if (this.isInt(r_left) && this.isInt(r_right)) {
			ret = new CaliInt(this.getValueInt(r_left) - this.getValueInt(r_right));
		  } else if (this.isInt(r_left)) {
			ret = new CaliDouble((double)this.getValueInt(r_left) - ((CaliDouble)r_right).getValue());
		  } else if (this.isInt(r_right)) {
			ret = new CaliDouble(((CaliDouble)r_left).getValue() - (double)this.getValueInt(r_right));
		  } else {
			  ret = new CaliDouble(((CaliDouble)r_left).getValue() - ((CaliDouble)r_right).getValue());
		  }
		} else {
		  if (!this.isNumber(r_left)) {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "INVALID_EXPRESSION", "astExpression.evalMinus(): Left side of expression isn't a number.", env.getCallStack().getStackTrace());
			return e;
		  } else {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "INVALID_EXPRESSION", "astExpression.evalMinus(): Right side of expression isn't a number.", env.getCallStack().getStackTrace());
			return e;
		  }
		}

		return ret;
	}
	
	private CaliType evalMult(Environment env, CaliType r_left, CaliType r_right) {
		CaliType ret = new CaliNull();

		if (this.isNumber(r_left) && this.isNumber(r_right)) {
		  if (this.isInt(r_left) && this.isInt(r_right)) {
			ret = new CaliInt(this.getValueInt(r_left) * this.getValueInt(r_right));
		  } else if (this.isInt(r_left)) {
			ret = new CaliDouble((double)this.getValueInt(r_left) * ((CaliDouble)r_right).getValue());
		  } else if (this.isInt(r_right)) {
			ret = new CaliDouble(((CaliDouble)r_left).getValue() * (double)this.getValueInt(r_right));
		  } else {
			  ret = new CaliDouble(((CaliDouble)r_left).getValue() * ((CaliDouble)r_right).getValue());
		  }
		} else {
		  if (!this.isNumber(r_left)) {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "INVALID_EXPRESSION", "astExpression.evalMult(): Left side of expression isn't a number.", env.getCallStack().getStackTrace());
			return e;
		  } else {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "INVALID_EXPRESSION", "astExpression.evalMult(): Right side of expression isn't a number.", env.getCallStack().getStackTrace());
			return e;
		  }
		}

		return ret;
	}
	
	private CaliType evalDiv(Environment env, CaliType r_left, CaliType r_right) {
		CaliType ret = new CaliNull();

		if (this.isNumber(r_left) && this.isNumber(r_right)) {
		  if (r_right.getType() == cType.cInt && this.getValueInt(r_right) == 0) {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "DIV_BY_0", "astExpression.evailDiv(): Division by 0 exception.", env.getCallStack().getStackTrace());
			return e;
		  } else if (r_right.getType() == cType.cDouble && ((CaliDouble)r_right).getValue() == 0.0) {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "DIV_BY_0", "astExpression.evailDiv(): Division by 0 exception.", env.getCallStack().getStackTrace());
			return e;
		  } else {
			if (this.isInt(r_left) && this.isInt(r_right)) {
			  ret = new CaliDouble((double)this.getValueInt(r_left) / (double)this.getValueInt(r_right));
			} else if (this.isInt(r_left)) {
			  ret = new CaliDouble((double)this.getValueInt(r_left) / ((CaliDouble)r_right).getValue());
			} else if (this.isInt(r_right)) {
			  ret = new CaliDouble(((CaliDouble)r_left).getValue() / (double)this.getValueInt(r_right));
			} else {
				ret = new CaliDouble(((CaliDouble)r_left).getValue() / ((CaliDouble)r_right).getValue());
			}
		  }
		} else {
		  if (!this.isNumber(r_left)) {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "INVALID_EXPRESSION", "astExpression.evailDiv(): Left side of expression isn't a number.", env.getCallStack().getStackTrace());
			return e;
		  } else {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "INVALID_EXPRESSION", "astExpression.evailDiv(): Right side of expression isn't a number.", env.getCallStack().getStackTrace());
			return e;
		  }
		}

		return ret;
	}
	
	private CaliType evalModulus(Environment env, CaliType r_left, CaliType r_right) {
		CaliType ret = new CaliNull();

		if (this.isNumber(r_left) && this.isNumber(r_right)) {
		  if (r_right.getType() == cType.cInt && this.getValueInt(r_right) == 0) {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "DIV_BY_0", "astExpression.evailModulus(): Division by 0 exception.", env.getCallStack().getStackTrace());
			return e;
		  } else if (r_right.getType() == cType.cDouble && ((CaliDouble)r_right).getValue() == 0.0) {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "DIV_BY_0", "astExpression.evailModulus(): Division by 0 exception.", env.getCallStack().getStackTrace());
			return e;
		  } else {
			if (this.isInt(r_left) && this.isInt(r_right)) {
			  ret = new CaliDouble((double)this.getValueInt(r_left) % (double)this.getValueInt(r_right));
			} else if (this.isInt(r_left)) {
			  ret = new CaliDouble((double)this.getValueInt(r_left) % ((CaliDouble)r_right).getValue());
			} else if (this.isInt(r_right)) {
			  ret = new CaliDouble(((CaliDouble)r_left).getValue() % (double)this.getValueInt(r_right));
			} else {
				ret = new CaliDouble(((CaliDouble)r_left).getValue() % ((CaliDouble)r_right).getValue());
			}
		  }
		} else {
		  if (!this.isNumber(r_left)) {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "INVALID_EXPRESSION", "astExpression.evailModulus(): Left side of expression isn't a number.", env.getCallStack().getStackTrace());
			return e;
		  } else {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "INVALID_EXPRESSION", "astExpression.evailModulus(): Right side of expression isn't a number.", env.getCallStack().getStackTrace());
			return e;
		  }
		}

		return ret;
	}
	
	private CaliType evalEqualsEquals(Environment env, CaliType r_left, CaliType r_right) {
		CaliType ret = new CaliNull();

		if (this.isNumber(r_left) && this.isNumber(r_right)) {
		  if (this.isInt(r_left) && this.isInt(r_right)) {
			if (this.getValueInt(r_left) == this.getValueInt(r_right)) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  } else if (this.isInt(r_left)) {
			if ((double)this.getValueInt(r_left) == ((CaliDouble)r_right).getValue()) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  } else if (this.isInt(r_right)) {
			if (((CaliDouble)r_left).getValue() == (double)this.getValueInt(r_right)) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  } else {
			if (((CaliDouble)r_left).getValue() == ((CaliDouble)r_right).getValue()) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  }
		} else if (r_left.getType() == cType.cString || r_right.getType() == cType.cString) {
		  if (((CaliTypeInt)r_left).str().equals(((CaliTypeInt)r_right).str())) ret = new CaliBool(true);
		  else ret = new CaliBool(false);
		} else if (r_left.isNull() && r_right.isNull()) {
		  ret = new CaliBool(true);
		} else if (r_left == r_right) ret = new CaliBool(true);
		else ret = new CaliBool(false);

		return ret;
	}
	
	private CaliType evalLessThan(Environment env, CaliType r_left, CaliType r_right) {
		CaliType ret = new CaliNull();

		if (this.isNumber(r_left) && this.isNumber(r_right)) {
		  if (this.isInt(r_left) && this.isInt(r_right)) {
			if ((int)((CaliInt)r_left).getValue() < (int)((CaliInt)r_right).getValue()) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  } else if (this.isInt(r_left)) {
			if ((double)((CaliInt)r_left).getValue() < ((CaliDouble)r_right).getValue()) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  } else {
			if (((CaliDouble)r_left).getValue() < (double)((CaliInt)r_right).getValue()) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  }
		}
		else {
		  CaliException e = new CaliException(exType.exRuntime);
		  e.setException(getLineNum(), "INVALID_COMPARISON", "astExpression.evalLessThan(): Less than comparison of non-number.", env.getCallStack().getStackTrace());
		  return e;
		}

		return ret;
	}
	
	private CaliType evalGreaterThan(Environment env, CaliType r_left, CaliType r_right) {
		CaliType ret = new CaliNull();

		if (this.isNumber(r_left) && this.isNumber(r_right)) {
		  if (this.isInt(r_left) && this.isInt(r_right)) {
			if ((int)((CaliInt)r_left).getValue() > (int)((CaliInt)r_right).getValue()) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  } else if (this.isInt(r_left)) {
			if ((double)((CaliInt)r_left).getValue() > ((CaliDouble)r_right).getValue()) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  } else {
			if (((CaliDouble)r_left).getValue() > (double)((CaliInt)r_right).getValue()) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  }
		}
		else {
		  CaliException e = new CaliException(exType.exRuntime);
		  e.setException(getLineNum(), "INVALID_COMPARISON", "astExpression.evalGreaterThan(): Greater than comparison of non-number.", env.getCallStack().getStackTrace());
		  return e;
		}

		return ret;
	}
	
	private CaliType evalLessThanEquals(Environment env, CaliType r_left, CaliType r_right) {
		CaliType ret = new CaliNull();

		if (this.isNumber(r_left) && this.isNumber(r_right)) {
		  if (this.isInt(r_left) && this.isInt(r_right)) {
			if ((int)((CaliInt)r_left).getValue() <= (int)((CaliInt)r_right).getValue()) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  } else if (this.isInt(r_left)) {
			if ((double)((CaliInt)r_left).getValue() <= ((CaliDouble)r_right).getValue()) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  } else {
			if (((CaliDouble)r_left).getValue() <= (double)((CaliInt)r_right).getValue()) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  }
		} else {
		  CaliException e = new CaliException(exType.exRuntime);
		  e.setException(getLineNum(), "INVALID_COMPARISON", "astExpression.evalLessThanEquals(): Less than equals comparison of non-number.", env.getCallStack().getStackTrace());
		  return e;
		}

		return ret;
	}
	
	private CaliType evalGreaterThanEquals(Environment env, CaliType r_left, CaliType r_right) {
		CaliType ret = new CaliNull();

		if (this.isNumber(r_left) && this.isNumber(r_right)) {
		  if (this.isInt(r_left) && this.isInt(r_right)) {
			if ((int)((CaliInt)r_left).getValue() >= (int)((CaliInt)r_right).getValue()) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  } else if (this.isInt(r_left)) {
			if ((double)((CaliInt)r_left).getValue() >= ((CaliDouble)r_right).getValue()) ret = new CaliBool(true);
			else ret = new CaliBool(false);
		  } else {
			if (((CaliDouble)r_left).getValue() >= (double)((CaliInt)r_right).getValue()) {
				ret = new CaliBool(true);
			} else {
				ret = new CaliBool(false);
			}
		  }
		}
		else {
		  CaliException e = new CaliException(exType.exRuntime);
		  e.setException(getLineNum(), "INVALID_COMPARISON", "astExpression.evalGreaterThanEquals(): Greater than equals comparison of non-number.", env.getCallStack().getStackTrace());
		  return e;
		}

		return ret;
	}
	
	private CaliType evalAnd(Environment env, CaliType r_left, CaliType r_right) {
		if (r_left.isEx()) {
			return r_left;
		} else if (r_right.isEx()) {
			return r_right;
		} else {
		  if (this.boolVal(r_left) && this.boolVal(r_right)) {
			  return new CaliBool(true);
		  } else {
			  return new CaliBool(false);
		  }
		}
	}
	
	private CaliType evalOr(Environment env, CaliType r_left, CaliType r_right) {
		if (r_left.isEx()) {
			return r_left;
		} else if (r_right.isEx()) {
			return r_right;
		} else {
		  if (this.boolVal(r_left) || this.boolVal(r_right)) {
			  return new CaliBool(true);
		  } else {
			  return new CaliBool(false);
		  }
		}
	}
	
	private CaliType evalInsert(Environment env, CaliType r_left, CaliType r_right) {
		if (r_left.getType() == cType.cList) {
		  ((CaliList)r_left).add(r_right);
		  return r_left;
		} else {
		  CaliException e = new CaliException(exType.exRuntime);
		  e.setException(getLineNum(), "INSERT_NOT_POSSIBLE", "astExpression.evalInsert(): Left side of insert expression not a list.", env.getCallStack().getStackTrace());
		  return e;
		}
	}
	
	private CaliType evalInstanceOf(Environment env, CaliType r_left, CaliType r_right) {
		if (r_left instanceof CaliObject) {
			if (r_right instanceof CaliString) {
				  if (((CaliObject)r_left).getClassDef().instanceOf(((CaliString)r_right).getValue())) {
					  return new CaliBool(true);
				  } else {
					  return new CaliBool(false);
				  }
			} else {
				CaliException e = new CaliException(exType.exRuntime);
				e.setException(getLineNum(), "UNEXPECTED_TYPE_FOUND", "astExpression.evalInstanceOf(): Left side of expression is not an instance of CaliObject.", env.getCallStack().getStackTrace());
				return e;
			}
		} else {
		  CaliException e = new CaliException(exType.exRuntime);
		  e.setException(getLineNum(), "UNEXPECTED_TYPE_FOUND", "astExpression.evalInstanceOf(): Left side of expression is not an instance of CaliObject.", env.getCallStack().getStackTrace());
		  return e;
		}
	}
	
	private CaliType evalNotEquals(Environment env, CaliType r_left, CaliType r_right) {
		CaliType ret = this.evalEqualsEquals(env, r_left, r_right);
		return new CaliBool(!((CaliBool)ret).getValue());
	}
	
	private CaliType operEquals(Environment env, boolean getRef) throws caliException {
		CaliType ret = new CaliNull();

		CaliType r_left  = left.eval(env, true);
		if(!r_left.isEx()) {
			if (r_left.getType() == cType.cRef) {
			  CaliRef ref = (CaliRef)r_left;
			  CaliType r_right = right.eval(env, getRef);
			  if(!r_right.isEx()) {
				  switch(this.eType) {
					  case PLEQ: {
						  ret = evalPlusEquals(env, ref, r_right);
						  break;
					  } case MIEQ: {
						  ret = evalMinusEquals(env, ref, r_right);
						  break;
					  } case MUEQ: {
						  ret = evalMultEquals(env, ref, r_right);
						  break;
					  } case DIEQ: {
						  ret = evalDivEquals(env, ref, r_right);
						  break;
					  } default: {
							CaliException e = new CaliException(exType.exInternal);
							e.setException(this.getLineNum(), "UNDEFINED_EXPRESSION", "Undefined oper-equals expression '" + this.eType.name() + "' found. (aExp::call)", "Undefined oper-equals expression '" + this.eType.name() + "' found. (aExp::call)", env.getCallStack().getStackTrace());
							ret = e;
							break;
						}
				  }
			  } else {
				  ret = r_right;
			  }
			} else {
			  CaliException e = new CaliException(exType.exRuntime);
			  e.setException(getLineNum(), "ASSIGN_NOT_POSSIBLE", "astExpression.operEquals(): Left side of assignment expression returned an object of type '" + r_left.getType().name() + "' which can't be assigned.", env.getCallStack().getStackTrace());
			  return e;
			}
		}
		else {
			ret = r_left;
		}

		return ret;
	}
	
	private CaliType evalPlusEquals(Environment env, CaliType r_left, CaliType r_right) {
		CaliRef ref = (CaliRef)r_left;
		try {
		  CaliType lval = ref.getValue();
		  CaliType res = this.evalPlus(env, lval, r_right);
		  if (!res.isEx()) {
			ref.assign(res);
			return res;
		  } else {
			return res;
		  }
		} catch (Exception ex) {
		  CaliException e = new CaliException(exType.exRuntime);
		  e.setException(getLineNum(), "ASSIGN_NOT_POSSIBLE", "astExpression.evalPlusEquals(): " + ex.getMessage(), env.getCallStack().getStackTrace());
		  return e;
		}
	}
	
	private CaliType evalMinusEquals(Environment env, CaliType r_left, CaliType r_right) {
		CaliRef ref = (CaliRef)r_left;
		try {
		  CaliType lval = ref.getValue();
		  CaliType res = this.evalMinus(env, lval, r_right);
		  if (!res.isEx()) {
			ref.assign(res);
			return res;
		  } else {
			return res;
		  }
		} catch (Exception ex) {
		  CaliException e = new CaliException(exType.exRuntime);
		  e.setException(getLineNum(), "ASSIGN_NOT_POSSIBLE", "astExpression.evalMinusEquals(): " + ex.getMessage(), env.getCallStack().getStackTrace());
		  return e;
		}
	}
	
	private CaliType evalMultEquals(Environment env, CaliType r_left, CaliType r_right) {
		CaliRef ref = (CaliRef)r_left;
		try {
		  CaliType lval = ref.getValue();
		  CaliType res = this.evalMult(env, lval, r_right);
		  if (!res.isEx()) {
			ref.assign(res);
			return res;
		  } else {
			return res;
		  }
		} catch (Exception ex) {
		  CaliException e = new CaliException(exType.exRuntime);
		  e.setException(getLineNum(), "ASSIGN_NOT_POSSIBLE", "astExpression.evalMultEquals(): " + ex.getMessage(), env.getCallStack().getStackTrace());
		  return e;
		}
	}
	
	private CaliType evalDivEquals(Environment env, CaliType r_left, CaliType r_right) {
		CaliRef ref = (CaliRef)r_left;
		try {
		  CaliType lval = ref.getValue();
		  CaliType res = this.evalDiv(env, lval, r_right);
		  if (!res.isEx()) {
			ref.assign(res);
			return res;
		  } else {
			return res;
		  }
		} catch (Exception ex) {
		  CaliException e = new CaliException(exType.exRuntime);
		  e.setException(getLineNum(), "ASSIGN_NOT_POSSIBLE", "astExpression.evalDivEquals(): " + ex.getMessage(), env.getCallStack().getStackTrace());
		  return e;
		}
	}
	
	private CaliType operIncDec(Environment env, boolean getRef) throws caliException {
		CaliType ret = new CaliNull();

		CaliType r_left  = left.eval(env, true);
		if(!r_left.isEx()) {
			if (r_left.getType() == cType.cRef) {
			  CaliRef ref = (CaliRef)r_left;
			  switch(this.eType) {
				  case PLPL: {
					  ret = evalPlusPlus(env, ref);
					  break;
				  } case MIMI: {
					  ret = evalMinusMinus(env, ref);
					  break;
				  } default: {
						CaliException e = new CaliException(exType.exInternal);
						e.setException(this.getLineNum(), "UNDEFINED_EXPRESSION", "Undefined oper inc/dec expression '" + this.eType.name() + "' found. (aExp::call)", "Undefined oper inc/dec expression '" + this.eType.name() + "' found. (aExp::call)", env.getCallStack().getStackTrace());
						ret = e;
						break;
					}
			  }
			  
			} else {
			  CaliException e = new CaliException(exType.exRuntime);
			  e.setException(getLineNum(), "ASSIGN_NOT_POSSIBLE", "astExpression.operIncDec(): Left side of assignment expression returned an object of type '" + r_left.getType().name() + "' which can't be assigned.", env.getCallStack().getStackTrace());
			  return e;
			}
		} else {
			ret = r_left;
		}

		return ret;
	}
	
	private CaliType evalPlusPlus(Environment env, CaliType r_left) {
		CaliRef ref = (CaliRef)r_left;
		try {
		  CaliType lval = ref.getValue();
		  if (this.isNumber(lval)) {
			  if (this.isInt(lval)) {
				CaliInt val = new CaliInt(((CaliInt)lval).getValue() + 1);
				ref.assign(val);
				return val;
			  } else {
				CaliDouble val = new CaliDouble(((CaliDouble)lval).getValue() + 1.0);
				ref.assign(val);
				return val;
			  }
		  } else {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "ASSIGN_NOT_POSSIBLE", "astExpression.evalDivEquals(): Attempt to increment data type '" + lval.getType().name() + "'.", env.getCallStack().getStackTrace());
			return e;
		  }
		} catch (Exception ex) {
		  CaliException e = new CaliException(exType.exRuntime);
		  e.setException(getLineNum(), "ASSIGN_NOT_POSSIBLE", "astExpression.evalDivEquals(): " + ex.getMessage(), env.getCallStack().getStackTrace());
		  return e;
		}
	}
	
	private CaliType evalMinusMinus(Environment env, CaliType r_left) {
		CaliRef ref = (CaliRef)r_left;
		try {
		  CaliType lval = ref.getValue();
		  if (this.isNumber(lval)) {
			  if (this.isInt(lval)) {
				CaliInt val = new CaliInt(((CaliInt)lval).getValue() - 1);
				ref.assign(val);
				return val;
			  } else {
				CaliDouble val = new CaliDouble(((CaliDouble)lval).getValue() - 1.0);
				ref.assign(val);
				return val;
			  }
		  } else {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "ASSIGN_NOT_POSSIBLE", "astExpression.evalMinusMinus(): Attempt to increment data type '" + lval.getType().name() + "'.", env.getCallStack().getStackTrace());
			return e;
		  }
		} catch (Exception ex) {
		  CaliException e = new CaliException(exType.exRuntime);
		  e.setException(getLineNum(), "ASSIGN_NOT_POSSIBLE", "astExpression.evalMinusMinus(): " + ex.getMessage(), env.getCallStack().getStackTrace());
		  return e;
		}
	}
	
	private CaliType operLeft(Environment env, boolean getRef) throws caliException
	{
		CaliType ret = new CaliNull();

		CaliType r_left  = left.eval(env, getRef);
		if(!r_left.isEx()) {
			switch(this.eType) {
				case NOT: {
					ret = evalNot(env, r_left);
					break;
				} case COUNT: {
					ret = evalCount(env, r_left);
					break;
				} case INCLUDE: {
					ret = evalInclude(env, r_left);
					break;
				} default: {
					CaliException e = new CaliException(exType.exInternal);
					e.setException(this.getLineNum(), "UNDEFINED_EXPRESSION", "Undefined oper left expression '" + this.eType.name() + "' found. (aExp::call)", "Undefined oper left expression '" + this.eType.name() + "' found. (aExp::call)", env.getCallStack().getStackTrace());
					ret = e;
					break;
				}
			}
		}
		else {
			ret = r_left;
		}

		return ret;
	}
	
	private CaliType evalNot(Environment env, CaliType r_left) {
		return new CaliBool(!this.boolVal(r_left));
	}
	
	private CaliType evalCount(Environment env, CaliType r_left) {
		if (r_left.getType() == cType.cList) {
		  return new CaliInt(((CaliList)r_left).getValue().size());
		} else if (r_left.getType() == cType.cMap) {
		  return new CaliInt(((CaliMap)r_left).size());
		} else if (r_left.getType() == cType.cString) {
		  return new CaliInt(((CaliString)r_left).getValue().length());
		} else {
		  CaliException e = new CaliException(exType.exRuntime);
		  e.setException(getLineNum(), "ASSIGN_NOT_POSSIBLE", "astExpression.evalCount(): Count operator (#) cannot be used for data type '" + r_left.getType().name() + "'.", env.getCallStack().getStackTrace());
		  return e;
		}
	}
	
	private CaliType evalInclude(Environment env, CaliType r_left) {
		if (r_left instanceof CaliString) {
			try {
				env.getEngine().addInclude(((CaliString)r_left).getValue());
			} catch (Exception ex) {
				CaliException e = new CaliException(exType.exRuntime);
				e.setException(getLineNum(), "INCLUDE_FAILED", "astExpression.evalInclude(): Failed to include '" + ((CaliString)r_left).getValue() + "'. " + ex.getMessage(), env.getCallStack().getStackTrace());
				return e;
			}
			return new CaliBool(true);
		} else {
			CaliException e = new CaliException(exType.exRuntime);
			e.setException(getLineNum(), "UNEXPECTED_DATA_TYPE", "astExpression.evalInclude(): Expecting string data type but found '" + r_left.getType().name() + "' instead.", env.getCallStack().getStackTrace());
			return e;
		}
	}
	
	
	private boolean isNumber(CaliType Item) {
		if (Item.getType() == cType.cInt || Item.getType() == cType.cDouble || Item.getType() == cType.cBool) return true;
		return false;
	}
	
	private boolean isInt(CaliType Item) {
		if (Item.getType() == cType.cInt || Item.getType() == cType.cBool) return true;
		return false;
	}
	
	private long getValueInt(CaliType Item) {
		if (Item.getType() == cType.cInt) {
			return (int) ((CaliInt)Item).getValue();
		} else if (((CaliBool)Item).getValue()) {
			return 1;
		}
		return 0;
	}
	
	private boolean boolVal(CaliType Item) {
		if (Item.getType() == cType.cNull) {
			return false;
		} else if (Item.getType() == cType.cBool) {
			if (((CaliBool)Item).getValue() == false) return false;
			else return true;
		} else if (Item.getType() == cType.cInt) {
			if (((CaliInt)Item).getValue() == 0) return false;
			else return true;
		} else if (Item.getType() == cType.cDouble) {
			if (((CaliDouble)Item).getValue() == 0.0) return false;
			else return true;
		} else if (Item.getType() == cType.cString) {
			if (((CaliString)Item).getValue() == "") return false;
			else return true;
		}
		else if (Item instanceof CaliObject) return true;
		else if (Item.getType() == cType.cList) return true;
		else if (Item.getType() == cType.cMap) return true;
		else return false;
	}
}
