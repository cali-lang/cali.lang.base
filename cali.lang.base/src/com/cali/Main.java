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

package com.cali;

/**
 * Main class implements program entry point for command line 
 * usage.
 * @author austin
 */
public class Main {
	/**
	 * Command line program entry point.
	 * @param args is an array of Strings with the command line arguments.
	 * @throws Exception on failure.
	 */
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			printUsage();
			System.out.println("\nError, incorrect number of arguments. Expecting script file name.");
		} else {
			// Create a new Cali engine.
			Engine eng = new Engine();
			
			// Sets debug output to true.
			//eng.setDebug(true);
			
			// Add resource include path for testing.
			eng.addResourceIncludePath("/com/cali/stdlib/ca/");
			
			// Parse the provided file name.
			eng.parseFile(args[0]);
			
			// Prints the full parsed abastract syntax tree of all the sources.
			//System.out.println(eng.toString());
			
			// Attempt to run the code.
			eng.run();
		}
	}
	
	/**
	 * Prints the application usage to standard output.
	 */
	public static void printUsage() {
		String rstr = "";
		rstr += "Cali-Lang Version " + Universe.getCaliVersion()  + "\n";
		rstr += "Copyright 2017 Austin Lehman\n";
		rstr += "Apache License Version 2\n";
		System.out.println(rstr);
	}
}