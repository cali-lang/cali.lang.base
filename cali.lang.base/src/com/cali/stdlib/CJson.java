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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.cali.Environment;
import com.cali.Universe;
import com.cali.ast.caliException;
import com.cali.types.CaliBool;
import com.cali.types.CaliDouble;
import com.cali.types.CaliInt;
import com.cali.types.CaliList;
import com.cali.types.CaliMap;
import com.cali.types.CaliNull;
import com.cali.types.CaliObject;
import com.cali.types.CaliString;
import com.cali.types.CaliType;

public class CJson {
	public static CaliType parse(Environment env, ArrayList<CaliType> args) throws Exception {
		String jstr = ((CaliString)args.get(0)).getValueString();
		
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject) parser.parse(jstr);
		
		return CJson.parseJsonObject(jobj);
	}
	
	/**
	 * Takes the provided JSON obejct and converts it into a cali 
	 * map which is then returned.
	 * @param jobj is a JSONObject with the parsed object to convert.
	 * @return A CaliMap object with the converted data.
	 * @throws caliException 
	 */
	private static CaliType parseJsonObject(JSONObject jobj) throws caliException {
		CaliMap mp = new CaliMap();
		
		for (Object key : jobj.keySet()) {
			Object obj = jobj.get(key);
			try {
				mp.put((String)key, CJson.parseJsonDataType(obj));
			} catch (caliException e) {
				throw new caliException("json.parse(): Exception for key '" + (String)key + "': " + e.getMessage());
			}
		}
		
		return mp;
	}
	
	/**
	 * Takes the provided JSON array and converts it into a cali 
	 * list which is then returned.
	 * @param jobj is a JSONArray with the parsed array to convert.
	 * @return A CaliArray with the converted data.
	 * @throws caliException 
	 */
	private static CaliType parseJsonArray(JSONArray jobj) throws caliException {
		CaliList lst = new CaliList();
		
		for (int i = 0; i < jobj.size(); i++) {
			Object obj = jobj.get(i);
			try {
				lst.add(CJson.parseJsonDataType(obj));
			} catch (caliException e) {
				throw new caliException("json.parse(): Exception for index '" + i + "': " + e.getMessage());
			}
		}
		
		return lst;
	}
	
	/**
	 * Takes the provided Object argument, checks it's type and converts and 
	 * returns its proper cali type.
	 * @param obj is an Object to convert.
	 * @return A CaliType with the converted value.
	 * @throws caliException
	 */
	private static CaliType parseJsonDataType(Object obj) throws caliException {
		CaliType cobj = null;
		
		if (obj instanceof String) {
			cobj = new CaliString((String)obj);
		} else if (obj instanceof Double) {
			cobj = new CaliDouble((Double)obj);
		} else if (obj instanceof Long) {
			cobj = new CaliInt((Long)obj);
		} else if (obj instanceof Boolean) {
			cobj = new CaliBool((Boolean)obj);
		} else if (obj == null) {
			cobj = new CaliNull();
		} else if (obj instanceof JSONObject) {
			cobj = CJson.parseJsonObject((JSONObject)obj);
		} else if (obj instanceof JSONArray) {
			cobj = CJson.parseJsonArray((JSONArray)obj);
		} else {
			throw new caliException("Unxpected data type '" + obj.getClass().getName() + "' found.");
		}
		
		return cobj;
	}
	
	public static CaliType unpack(Environment env, ArrayList<CaliType> args) throws Exception {
		String jstr = ((CaliString)args.get(0)).getValueString();
		
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject) parser.parse(jstr);
		
		return CJson.unpackJsonData(env, jobj);
	}
	
	private static CaliType unpackJsonData(Environment env, JSONObject obj) throws caliException {
		CaliType cobj = null;
		
		if (obj.containsKey("type")) {
			String type = CJson.getJsonString(env, obj, "type");
			// BOOL
			if (type.equals("bool")) {
				if (obj.containsKey("value")) {
					cobj = new CaliBool(CJson.getJsonBoolean(env, obj, "value"));
				} else {
					throw new caliException("json.unpack(): Malformed JSON, missing value.");
				}
			}
			// INT
			else if (type.equals("int")) {
				if (obj.containsKey("value")) {
					cobj = new CaliInt(CJson.getJsonInt(env, obj, "value"));
				} else {
					throw new caliException("json.unpack(): Malformed JSON, missing value.");
				}
			}
			// DOUBLE
			else if (type.equals("double")) {
				if (obj.containsKey("value")) {
					cobj = new CaliDouble(CJson.getJsonDouble(env, obj, "value"));
				} else {
					throw new caliException("json.unpack(): Malformed JSON, missing value.");
				}
			}
			// STRING
			else if (type.equals("string")) {
				if (obj.containsKey("value")) {
					cobj = new CaliString(CJson.getJsonString(env, obj, "value"));
				} else {
					throw new caliException("json.unpack(): Malformed JSON, missing value.");
				}
			}
			// NULL
			else if (type.equals("null")) {
				if (obj.containsKey("value")) {
					cobj = new CaliNull();
				} else {
					throw new caliException("json.unpack(): Malformed JSON, missing value.");
				}
			}
			// LIST
			else if (type.equals("list")) {
				if (obj.containsKey("value")) {
					cobj = CJson.getJsonList(env, obj, "value");
				} else {
					throw new caliException("json.unpack(): Malformed JSON, missing value.");
				}
			}
			// OBJECT
			else if (Universe.get().getClassDef(type) != null) {
				if (obj.containsKey("members")) {
					cobj = CJson.getJsonObject(env, type, obj);
				} else {
					throw new caliException("json.unpack(): Malformed JSON, missing value.");
				}
			}
			// UNKNOWN?
			else {
				throw new caliException("json.unpack(): Malformed JSON, type '" + type + "' unknown.");
			}
		} else {
			throw new caliException("json.unpack(): Malformed JSON, 'type' not found.");
		}
		
		return cobj;
	}
	
	private static Boolean getJsonBoolean(Environment env, JSONObject obj, String key) throws caliException {
		Object tobj = obj.get(key);
		if (tobj instanceof Boolean) {
			return (Boolean)tobj;
		} else {
			throw new caliException("json.unpack(): Getting value for key '" + key + "'. Expecting object of type Boolean but found '" + tobj.getClass().getName() + "' instead.");
		}
	}
	
	private static Long getJsonInt(Environment env, JSONObject obj, String key) throws caliException {
		Object tobj = obj.get(key);
		if (tobj instanceof Long) {
			return (Long)tobj;
		} else {
			throw new caliException("json.unpack(): Getting value for key '" + key + "'. Expecting object of type Long but found '" + tobj.getClass().getName() + "' instead.");
		}
	}
	
	private static Double getJsonDouble(Environment env, JSONObject obj, String key) throws caliException {
		Object tobj = obj.get(key);
		if (tobj instanceof Double) {
			return (Double)tobj;
		} else {
			throw new caliException("json.unpack(): Getting value for key '" + key + "'. Expecting object of type Double but found '" + tobj.getClass().getName() + "' instead.");
		}
	}
	
	private static String getJsonString(Environment env, JSONObject obj, String key) throws caliException {
		Object tobj = obj.get(key);
		if (tobj instanceof String) {
			return (String)tobj;
		} else {
			throw new caliException("json.unpack(): Getting value for key '" + key + "'. Expecting object of type String but found '" + tobj.getClass().getName() + "' instead.");
		}
	}
	
	private static CaliList getJsonList(Environment env, JSONObject obj, String key) throws caliException {
		Object tobj = obj.get(key);
		if (tobj instanceof JSONArray) {
			JSONArray ja = (JSONArray) tobj;
			CaliList cl = new CaliList();
			for (int i = 0; i < ja.size(); i++) {
				Object aobj = ja.get(i);
				if (aobj instanceof JSONObject) {
					cl.add(CJson.unpackJsonData(env, (JSONObject)aobj));
				} else {
					throw new caliException("json.unpack(): Malformed JSON structure found for array '" + key + "' at index " + i + ", expecting JSON object but found '\" + tobj.getClass().getName() + \"' instead.");
				}
			}
			return cl;
		} else {
			throw new caliException("json.unpack(): Getting value for key '" + key + "'. Expecting object of type JSONArray but found '" + tobj.getClass().getName() + "' instead.");
		}
	}
	
	private static CaliType getJsonObject(Environment env, String type, JSONObject obj) throws caliException {
		Object tobj = obj.get("members");
		if (tobj instanceof JSONObject) {
			JSONObject jmemb = (JSONObject)tobj;
			CaliObject co = env.getEngine().instantiateObject(type);
			
			for (String key : co.getMembers().getMap().keySet()) {
				if (jmemb.containsKey(key)) {
					if (jmemb.get(key) instanceof JSONObject) {
						co.addMember(key, CJson.unpackJsonData(env, (JSONObject)jmemb.get(key)));
					} else {
						throw new caliException("json.unpack(): Malformed JSON, expecting '" + key + "' to be a JSON object but found '" + tobj.getClass().getName() + "' instead.");
					}
				} else {
					throw new caliException("json.unpack(): Malformed JSON, object '" + type + "' missing member '" + key + "'.");
				}
			}
			
			return co;
		} else {
			throw new caliException("json.unpack(): Getting value for key 'members'. Expecting object of type JSONObject but found '" + tobj.getClass().getName() + "' instead.");
		}
	}
}
