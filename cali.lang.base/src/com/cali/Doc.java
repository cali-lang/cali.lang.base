/*
 * Copyright 2021 Austin Lehman
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

import com.cali.ast.astClass;
import com.cali.ast.caliException;
import com.cali.types.*;

import java.io.File;

/**
 * Class that provides support for Cali-Doc. Specifically this class
 * will generate documents in different formats. It also supports doc calls
 * from within Cali code.
 */
public class Doc {
    /**
	 * Builds the calidoc for the provied script
	 * file provided and returns it as a Markdown
	 * string.
	 * @return A String with the calidoc in markdown format.
	 */
	public static String getCalidocMarkdown(Engine Eng, String ScriptFile) throws caliException {
		File sf = new File(ScriptFile);
		String ret = "# file: " + sf.getName() + "\n\n";

		for (String className : Eng.getClasses().keySet()) {
			astClass ac = Eng.getClasses().get(className);
			if (ac.getFileName().equals(ScriptFile)) {
				CaliMap cdoc = (CaliMap)ac.getCalidoc();

				String staticStr = "";
				if (cdoc.getValue().get("isStatic").getNumericBool()) {
					staticStr = "`static` ";
				}
				String externStr = "";
				if (cdoc.getValue().get("isExtern").getNumericBool()) {
					externStr = "(extern: " + cdoc.getValue().get("externClassName").getValueString() + ") ";
				}
				String extendedStr = "";
				CaliList elist = (CaliList)cdoc.getValue().get("extendedClasses");
				if (elist.size() > 0) {
					extendedStr = "**extends: " + elist.getValue().get(0).getValueString();
					for (int i = 1; i < elist.getValue().size(); i++) {
						extendedStr += ", " + elist.getValue().get(i).getValueString();
					}
					extendedStr += "** ";
				}
				ret += "## class: " + cdoc.getValue().get("className").getValueString() + "\n";
				ret += "[" + cdoc.getValue().get("lineNumber").getNumericInt() + ":" + cdoc.getValue().get("colNumber").getNumericInt() + "] " + staticStr + externStr + extendedStr + "\n";
				ret += Doc.getCalidocMarkdownMembers(cdoc);
				ret += Doc.getCalidocMarkdownMethods(cdoc);


				ret += "\n\n";
			}
		}

		return ret;
	}

	/**
	 * Builds the calidoc members in markdown format and returns
	 * as a string.
	 * @param cdoc is a CaliMap object with the members.
	 * @return A String with the generated doc.
	 */
	private static String getCalidocMarkdownMembers(CaliMap cdoc) {
		String ret = "";
		CaliList lst = (CaliList) cdoc.getValue().get("members");
		if (lst.getValue().size() > 0) {
			ret += "#### Members\n";
			for (int i = 0; i < lst.getValue().size(); i++) {
				CaliMap memb = (CaliMap)lst.getValue().get(i);
				ret += "- **" + memb.getValue().get("name").getValueString() + "**\n";

				if (memb.getValue().get("value").getType() != cType.cNull) {
					CaliMap docMap = (CaliMap)memb.getValue().get("value");
					CaliList docList = (CaliList)docMap.getValue().get("docList");
					ret += Doc.getCalidocMarkdownDoclist(docList);
				}
			}
			ret += "\n";
		}
		return ret;
	}

	/**
	 * Builds the calidoc methods in markdown format and returns
	 * as a string.
	 * @param cdoc is a CaliMap object with the methods.
	 * @return A String with the generated doc.
	 */
	private static String getCalidocMarkdownMethods(CaliMap cdoc) {
		String ret = "";
		CaliList lst = (CaliList) cdoc.getValue().get("methods");
		if (lst.getValue().size() > 0) {
			ret += "#### Methods\n";
			for (int i = 0; i < lst.getValue().size(); i++) {
				CaliMap methb = (CaliMap)lst.getValue().get(i);
				ret += "- **" + methb.getValue().get("name").getValueString() + "** (";

				CaliList margs = (CaliList)methb.getValue().get("args");
				ret += getCalidocMarkdownMethodArgs(margs);

				ret += ")\n";
				if (methb.getValue().containsKey("caliDoc")) {
					CaliMap docMap = (CaliMap)methb.getValue().get("caliDoc");
					CaliList docList = (CaliList)docMap.getValue().get("docList");
					ret += Doc.getCalidocMarkdownDoclist(docList);
				} else {
					ret += "\n";
				}
			}
			ret += "\n";
		}
		return ret;
	}

	/**
	 * Builds the calidoc function in markdown format and returns
	 * as a string.
	 * @param docList is a CaliList object with items from the function comment.
	 * @return A String with the generated doc.
	 */
	private static String getCalidocMarkdownDoclist(CaliList docList) {
		String ret = "";
		for (CaliType cobj : docList.getValue()) {
			CaliMap docItem = (CaliMap) cobj;
			if (docItem.getValue().get("type").getValueString().toLowerCase().equals("annotation")) {
				ret += "\t- **@" + docItem.getValue().get("tagName").getValueString() + "** `" + docItem.getValue().get("tagValue").getValueString() + "` " + docItem.getValue().get("tagDescription").getValueString() + "\n";
			} else {
				// Text node
				ret += "\t> " + docItem.getValue().get("text").getValueString() + "\n";
			}
		}
		return ret;
	}

	/**
	 * Builds the calidoc method args in markdown format and returns
	 * as a string.
	 * @param margs is a CaliList object with the method args.
	 * @return A String with the generated doc.
	 */
	private static String getCalidocMarkdownMethodArgs(CaliList margs) {
		String ret = "";
		if (margs.size() > 0) {
			String argsStr = "";
			for (int j = 0; j < margs.getValue().size(); j++) {
				CaliMap arg = (CaliMap) margs.getValue().get(j);
				String argStr = "";
				if (arg.contains("specifiedType")) {
					String type = arg.getValue().get("specifiedType").getValueString();
					if (!type.equals("undef")) {
						argStr += " " + type;
					}
				}
				if (arg.contains("name")) {
					argStr += " " + arg.getValue().get("name").getValueString();
				}
				if (arg.contains("type")) {
					String type = arg.getValue().get("type").getValueString();
					if (type.equals("etcetera")) {
						argStr += "...";
					}
				}

				if (arg.contains("valueType")) {
					CaliType valType = arg.getValue().get("value");
					String val = arg.getValue().get("value").getValueString();

					if (valType instanceof CaliString) {
						argStr += " = \"" + val + "\"";
					} else {
						argStr += " = " + val;
					}
				}

				if (!argStr.trim().equals("")) {
					if (j > 0) argsStr += ", ";
					argsStr += argStr.trim();
				}
			}
			ret += "`" + argsStr + "`";
		}
		return ret;
	}
}
