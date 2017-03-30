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

import com.cali.Universe;
import com.cali.types.CaliInt;
import com.cali.types.CaliNull;
import com.cali.types.CaliString;
import com.cali.types.CaliType;

public class CSys {
	public static String getSysInfo()
	{
		String ret = "";
		
		ArrayList<CaliType> args = new ArrayList<CaliType>();
		ret += "OS Information\n";
		ret += "\tOS Arch: " + ((CaliString)getOsArch(args)).getValueString() + "\n";
		ret += "\tOS Name: " + ((CaliString)getOsName(args)).getValueString() + "\n";
		ret += "\tOS Version: " + ((CaliString)getOsVersion(args)).getValueString() + "\n";
		ret += "\tOS File Separator: " + ((CaliString)getFileSeparator(args)).getValueString() + "\n";
		
		ret += "\n";
		ret += "Java Information\n";
		ret += "\tJava Version: " + ((CaliString)getJavaVersion(args)).getValueString() + "\n";
		ret += "\tJava Vendor: " + ((CaliString)getJavaVendor(args)).getValueString() + "\n";
		ret += "\tJava Vendor Url: " + ((CaliString)getJavaVendorUrl(args)).getValueString() + "\n";
		ret += "\tJava Class Path: " + ((CaliString)getJavaClassPath(args)).getValueString() + "\n";
		
		ret += "\n";
		ret += "Cali Information\n";
		ret += "\tCali Version: " + ((CaliString)getCaliVersion(args)).getValueString() + "\n";
		ret += "\tCali Assembly: " + ((CaliString)getAssembly(args)).getValueString() + "\n";
		ret += "\tCali Assembly Path: " + ((CaliString)getAssemblyPath(args)).getValueString() + "\n";
		
		ret += "\n";
		ret += "User Information\n";
		ret += "\tCurrent Path: " + ((CaliString)getCurrentPath(args)).getValueString() + "\n";
		ret += "\tHome Path: " + ((CaliString)getHomePath(args)).getValueString() + "\n";
		ret += "\tUser Name: " + ((CaliString)getUserName(args)).getValueString() + "\n";
		
		ret += "\n";
		
		return ret;
	}
	
	/*
	 * Environment functions
	 */
	public static CaliType getSysInfo(ArrayList<CaliType> args)
	{
		String ret = CSys.getSysInfo();
		return new CaliString(ret);
	}
	
	public static String _getAssembly() {
		String path = "";
		try {
			path = (new File(URLDecoder.decode(Universe.class.getProtectionDomain().getCodeSource().getLocation().getPath())).getAbsolutePath());
		} catch(Exception e) {
			/*
			if(runtime.getInstance().getOsType() == osType.MOBILE)
			{
				path = android.getInstance().getAssemblyPath();
			}
			*/
			e.printStackTrace();
		}
		return path;
	}
	
	public static String _getAssemblyPath()
	{
		String path = "";
		try {
			path = (new File(URLDecoder.decode(Universe.class.getProtectionDomain().getCodeSource().getLocation().getPath())).getParent()) + System.getProperty("file.separator");
		} catch(Exception e) {
			/*
			if(runtime.getInstance().getOsType() == osType.MOBILE)
			{
				path = android.getInstance().getAssemblyPath();
			}
			*/
			e.printStackTrace();
		}
		return path;
	}
	
	public static CaliType getAssembly(ArrayList<CaliType> args) {
		return new CaliString(_getAssembly());
	}
	
	public static CaliType getAssemblyPath(ArrayList<CaliType> args) {
		return new CaliString(_getAssemblyPath());
	}
	
	public static CaliType getCurrentPath(ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("user.dir"));
	}
	
	/*
	public static CaliType getMainFilePath(ArrayList<CaliType> args) {
		if(runtime.getInstance().getOsType() == osType.MOBILE)
			return new CaliString(runtime.getInstance().getMobilePath());
		else
			return sys.getCurrentPath(args);
	}
	*/
	
	public static CaliType getHomePath(ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("user.home"));
	}
	
	public static CaliType getUserName(ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("user.name"));
	}
	
	public static CaliType getOsArch(ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("os.arch"));
	}
	
	public static CaliType getOsName(ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("os.name"));
	}
	
	public static CaliType getOsVersion(ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("os.version"));
	}
	
	public static CaliType getJavaVersion(ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("java.version"));
	}
	
	public static CaliType getJavaVendor(ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("java.vendor"));
	}
	
	public static CaliType getJavaVendorUrl(ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("java.vendor.url"));
	}
	
	public static CaliType getFileSeparator(ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("file.separator"));
	}
	
	public static CaliType getLineSeparator(ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("line.separator"));
	}
	
	public static CaliType getJavaClassPath(ArrayList<CaliType> args) {
		return new CaliString(System.getProperty("java.class.path"));
	}
	
	public static CaliType getCaliVersion(ArrayList<CaliType> args) {
		return new CaliString(Universe.getCaliVersion());
	}
	
	public static CaliType getJavaHome(ArrayList<CaliType> args) {
		return new CaliString(System.getenv("JAVA_HOME"));
	}
	
	/*
	 * Time functions
	 */
	public static CaliType getMills(ArrayList<CaliType> args) {
		return new CaliInt(System.currentTimeMillis());
	}
	
	public static CaliType _sleep(ArrayList<CaliType> args) {
		CaliType ret = new CaliNull();
		try {
			Thread.sleep(((CaliInt)args.get(0)).getValue());
		} catch (InterruptedException e) {
			// TODO Return exception object.
			e.printStackTrace();
		}
		return ret;
	}
}
