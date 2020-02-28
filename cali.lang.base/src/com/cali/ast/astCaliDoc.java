package com.cali.ast;

import com.cali.Environment;
import com.cali.ast.doc.docAnnotation;
import com.cali.ast.doc.docText;
import com.cali.ast.doc.docType;
import com.cali.types.CaliList;
import com.cali.types.CaliMap;
import com.cali.types.CaliString;
import com.cali.types.CaliType;

import java.util.ArrayList;
import java.util.List;

public class astCaliDoc extends astNode implements astNodeInt {
    // The full doc comment text.
    private String caliDocText = "";

    // The parsed doc comment broken into a list of text or annotation nodes.
    private List<docText> docList = new ArrayList<docText>();


    public astCaliDoc() {
        this.setType(astNodeType.CALI_DOC);
    }

    public astCaliDoc(String Text) {
        this();
        this.setCaliDocText(Text);
    }

    private void setCaliDocText(String Text) {
        // Set the text
        this.caliDocText = Text;

        // Strip the formatting
        this.stripFormatting();

        // Parse text
        this.parseText();
    }

    private void stripFormatting() {
        String ret = "";
        String lines[] = this.caliDocText.split("\n");
        for (String line : lines) {
            String tline = line.trim();
            if (tline.startsWith("*"))
                tline = tline.replaceFirst("^[*]+(.*)", "$1").trim();
            if (!tline.equals(""))
                ret += tline + "\n";
        }
        this.caliDocText = ret.trim();
    }

    private void parseText() {
        List<docText> lst = new ArrayList<docText>();

        String lines[] = this.parseProcessLines();
        for(String line : lines) {
            if (line.matches("@[A-Za-z0-9_]+.*")) {
                // Annoation found.
                docAnnotation an = new docAnnotation();
                an.setText(line);
                String parts[] = line.split("\\s+");
                an.setTagName(parts[0].substring(1));
                if (parts.length > 1) {
                    an.setValue(parts[1]);
                }
                if (parts.length > 2) {
                    String desc = parts[2];
                    for (int i = 3; i < parts.length; i++) {
                        desc += " " + parts[i];
                    }
                    an.setDescription(desc);
                }
                lst.add(an);
            } else {
                lst.add(new docText(line));
            }
        }

        this.docList = lst;
    }

    /**
     * Processes all the individual lines and groups up plain text and
     * annotation nodes into individual strings.
     * @return An array of Strings with each logical line.
     */
    private String[] parseProcessLines() {
        ArrayList<String> ret = new ArrayList();

        String cur = "";
        String lines[] = this.caliDocText.split("\n");
        for (String line : lines) {
            if (line.matches("@[A-Za-z0-9_]+.*")) {
                if (!cur.trim().equals("")) {
                    ret.add(cur.trim());
                    cur = "";
                }
                cur += line + " ";
            } else if (line.trim().equals("")) {
                if (!cur.trim().equals("")) {
                    ret.add(cur.trim());
                    cur = "";
                }
            } else {
                cur += line + " ";
            }
        }

        // If there's any left not added already.
        if (!cur.trim().equals("")) {
            ret.add(cur.trim());
        }

        return ret.toArray(new String[ret.size()]);
    }

    @Override
    public String toString(int Level) {
        String rstr = "";
        rstr += getTabs(Level) + "{\n";
        rstr += this.getNodeStr(Level + 1) + ",\n";
        rstr += getTabs(Level + 1) + "\"fileName\": \"" + this.getFileName() + "\",\n";
        rstr += getTabs(Level + 1) + "\"caliDocText\": \"" + this.caliDocText + "\",\n";
        return rstr;
    }

    @Override
    public CaliType evalImpl(Environment env, boolean getref) throws caliException {
        return null;
    }

    public CaliType getCalidoc() {
        CaliMap ret = new CaliMap();

        ret.put("caliDocText", new CaliString(this.caliDocText));
        CaliList lst = new CaliList();
        for (docText dt : this.docList) {
            CaliMap dobj = new CaliMap();
            dobj.put("type", new CaliString(dt.getType().name()));
            dobj.put("text", new CaliString(dt.getText()));
            if (dt.getType() == docType.ANNOTATION) {
                docAnnotation da = (docAnnotation)dt;
                dobj.put("tagName", new CaliString(da.getTagName()));
                dobj.put("tagValue", new CaliString(da.getValue()));
                dobj.put("tagDescription", new CaliString(da.getDescription()));
            }
            lst.add(dobj);
        }
        ret.put("docList", lst);

        return ret;
    }
}
