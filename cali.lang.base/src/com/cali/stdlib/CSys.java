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

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;

import com.cali.Environment;
import com.cali.Universe;
import com.cali.types.CaliInt;
import com.cali.types.CaliNull;
import com.cali.types.CaliString;
import com.cali.types.CaliType;

public class CSys {
	public static String getSysInfo(Environment env) throws Exception {
		String ret = "";
		
		ArrayList<CaliType> args = new ArrayList<CaliType>();
		ret += "OS Information\n";
		ret += "\tOS Arch: " + ((CaliString)getOsArch(env, args)).getValueString() + "\n";
		ret += "\tOS Name: " + ((CaliString)getOsName(env, args)).getValueString() + "\n";
		ret += "\tOS Version: " + ((CaliString)getOsVersion(env, args)).getValueString() + "\n";
		ret += "\tOS File Separator: " + ((CaliString)getFileSeparator(env, args)).getValueString() + "\n";
		
		ret += "\n";
		ret += "Java Information\n";
		ret += "\tJava Version: " + ((CaliString)getJavaVersion(env, args)).getValueString() + "\n";
		ret += "\tJava Vendor: " + ((CaliString)getJavaVendor(env, args)).getValueString() + "\n";
		ret += "\tJava Vendor Url: " + ((CaliString)getJavaVendorUrl(env, args)).getValueString() + "\n";
		ret += "\tJava Class Path: " + ((CaliString)getJavaClassPath(env, args)).getValueString() + "\n";
		
		ret += "\n";
		ret += "Cali Information\n";
		ret += "\tCali Version: " + ((CaliString)getCaliVersion(env, args)).getValueString() + "\n";
		ret += "\tCali Assembly: " + ((CaliString)getAssembly(env, args)).getValueString() + "\n";
		ret += "\tCali Assembly Path: " + ((CaliString)getAssemblyPath(env, args)).getValueString() + "\n";
		
		ret += "\n";
		ret += "User Information\n";
		ret += "\tCurrent Path: " + ((CaliString)getCurrentPath(env, args)).getValueString() + "\n";
		ret += "\tHome Path: " + ((CaliString)getHomePath(env, args)).getValueString() + "\n";
		ret += "\tUser Name: " + ((CaliString)getUserName(env, args)).getValueString() + "\n";
		
		ret += "\n";
		
		return ret;
	}
	
	/*
	 * Environment functions
	 */
	public static CaliType getSysInfo(Environment env, ArrayList<CaliType> args) throws Exception {
		String ret = CSys.getSysInfo(env);
		return new CaliString(ret);
	}
	
	@SuppressWarnings("deprecation")
	public static String _getAssembly() throws Exception {
		String path = "";
		try {
			path = (new File(URLDecoder.decode(Universe.class.getProtectionDomain().getCodeSource().getLocation().getPath())).getAbsolutePath());
		} catch(Exception e) {
			/*
			if(runtime.getInstance().getOsType() == osType.MOBILE) {
				path = android.getInstance().getAssemblyPath();
			}
			*/
			throw e;
		}
		return path;
	}
	
	@SuppressWarnings("deprecation")
	public static String _getAssemblyPath() throws Exception {
		String path = "";
		try {
			path = (new File(URLDecoder.decode(Universe.class.getProtectionDomain().getCodeSource().getLocation().getPath())).getParent()) + System.getProperty("file.separator");
		} catch(Exception e) {
			/*
			if(runtime.getInstance().getOsType() == osType.MOBILE) {
				path = android.getInstance().getAssemblyPath();
			}
			*/
			throw e;
		}
		return path;
	}
	
	public static CaliType getAssembly(Environment env, ArrayList<CaliType> args) throws Exception {
		return new CaliString(_getAssembly());
	}
	
	public static CaliType getAssemblyPath(Environment env, ArrayList<CaliType> args) throws Exception {
		return new CaliString(_getAssemblyPath());
	}
	
	public static CaliType getCurrentPath(Environment env, ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("user.dir"));
	}
	
	/*
	public static CaliType getMainFilePath(Environment env, ArrayList<CaliType> args) {
		if(runtime.getInstance().getOsType() == osType.MOBILE)
			return new CaliString(runtime.getInstance().getMobilePath());
		else
			return sys.getCurrentPath(args);
	}
	*/
	
	public static CaliType getHomePath(Environment env, ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("user.home"));
	}
	
	public static CaliType getUserName(Environment env, ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("user.name"));
	}
	
	public static CaliType getOsArch(Environment env, ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("os.arch"));
	}
	
	public static CaliType getOsName(Environment env, ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("os.name"));
	}
	
	public static CaliType getOsVersion(Environment env, ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("os.version"));
	}
	
	public static CaliType getJavaVersion(Environment env, ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("java.version"));
	}
	
	public static CaliType getJavaVendor(Environment env, ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("java.vendor"));
	}
	
	public static CaliType getJavaVendorUrl(Environment env, ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("java.vendor.url"));
	}
	
	public static CaliType getFileSeparator(Environment env, ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("file.separator"));
	}
	
	public static CaliType getLineSeparator(Environment env, ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("line.separator"));
	}
	
	public static CaliType getJavaClassPath(Environment env, ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("java.class.path"));
	}
	
	public static CaliType getCaliVersion(Environment env, ArrayList<CaliType> args) {
		return new CaliString(Universe.getCaliVersion());
	}
	
	public static CaliType getJavaHome(Environment env, ArrayList<CaliType> args) {
		return new CaliString(System.getenv("JAVA_HOME"));
	}
	
	/*
	 * Time functions
	 */
	public static CaliType getMills(Environment env, ArrayList<CaliType> args) {
		return new CaliInt(System.currentTimeMillis());
	}
	
	public static CaliType _sleep(Environment env, ArrayList<CaliType> args) throws InterruptedException {
		CaliType ret = new CaliNull();
		Thread.sleep(((CaliInt)args.get(0)).getValue());
		return ret;
	}
}
