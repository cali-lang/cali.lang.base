/* The following code was generated by JFlex 1.4.3 on 8/27/17 8:15 AM */

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

import java.math.BigDecimal;
import java.math.BigInteger;

import java_cup.runtime.Symbol;
import com.cali.stdlib.console;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 8/27/17 8:15 AM from the specification file
 * <tt>/home/austin/git-public/cali.lang.base/cali.lang.base/src/main/jflex/Scanner.jflex</tt>
 */
class Lexer extends sym implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int C_COMMENT = 2;
  public static final int STRING = 4;
  public static final int YYINITIAL = 0;
  public static final int STRING_LIT = 8;
  public static final int STRING_FRMT = 6;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3,  3,  4, 4
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\66\1\6\1\0\1\1\1\5\22\0\1\1\1\54\1\63"+
    "\1\61\1\0\1\53\1\57\1\64\1\41\1\42\1\52\1\50\1\40"+
    "\1\51\1\3\1\10\12\2\1\37\1\7\1\55\1\47\1\56\1\0"+
    "\1\62\32\4\1\45\1\65\1\46\1\0\1\4\1\0\1\13\1\24"+
    "\1\11\1\22\1\15\1\35\1\4\1\33\1\21\1\4\1\36\1\12"+
    "\1\20\1\16\1\26\1\23\1\4\1\25\1\14\1\27\1\17\1\30"+
    "\1\34\1\31\1\32\1\4\1\43\1\60\1\44\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\1\1\2\3\0\1\3\1\2\1\4\1\5\1\1"+
    "\1\3\1\6\1\7\14\1\1\10\1\11\1\12\1\13"+
    "\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23"+
    "\1\24\1\25\1\26\1\27\2\3\1\30\1\3\1\31"+
    "\1\32\2\2\1\33\1\34\1\35\1\3\2\33\1\36"+
    "\1\37\2\33\1\40\1\33\1\41\1\3\1\33\2\0"+
    "\1\2\1\42\1\43\12\1\1\44\12\1\1\45\1\46"+
    "\1\47\1\50\1\51\1\52\1\53\1\54\1\55\1\56"+
    "\1\57\1\60\1\61\1\62\1\0\1\2\1\63\1\64"+
    "\1\65\2\0\1\66\1\4\1\67\10\1\1\70\12\1"+
    "\1\71\3\1\1\72\1\73\1\0\1\1\1\74\3\1"+
    "\1\75\1\76\1\1\1\77\10\1\1\100\3\1\1\0"+
    "\1\101\1\102\11\1\1\103\1\1\1\104\1\105\1\106"+
    "\1\0\1\107\1\110\1\111\3\1\1\112\2\1\1\113"+
    "\1\114\1\115\1\1\1\116\1\117\4\1\1\120\1\121";

  private static int [] zzUnpackAction() {
    int [] result = new int[200];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\67\0\156\0\245\0\334\0\u0113\0\u0113\0\u014a"+
    "\0\u0181\0\u01b8\0\u01ef\0\u0113\0\u0226\0\u025d\0\u0294\0\u02cb"+
    "\0\u0302\0\u0339\0\u0370\0\u03a7\0\u03de\0\u0415\0\u044c\0\u0483"+
    "\0\u04ba\0\u04f1\0\u0113\0\u0113\0\u0113\0\u0113\0\u0113\0\u0113"+
    "\0\u0113\0\u0528\0\u055f\0\u0596\0\u05cd\0\u0604\0\u063b\0\u0672"+
    "\0\u06a9\0\u06e0\0\u0717\0\u0113\0\u074e\0\u0785\0\u0113\0\u07bc"+
    "\0\u07f3\0\u082a\0\u0861\0\u0113\0\u0898\0\u08cf\0\u0113\0\u0113"+
    "\0\u0113\0\u0906\0\u093d\0\u0113\0\u0974\0\u0113\0\u09ab\0\u09e2"+
    "\0\u0a19\0\u0a50\0\u0a87\0\u0113\0\u0113\0\u0abe\0\u0af5\0\u0b2c"+
    "\0\u0b63\0\u0b9a\0\u0bd1\0\u0c08\0\u0c3f\0\u0c76\0\u0cad\0\u01b8"+
    "\0\u0ce4\0\u0d1b\0\u0d52\0\u0d89\0\u0dc0\0\u0df7\0\u0e2e\0\u0e65"+
    "\0\u0e9c\0\u0ed3\0\u0113\0\u0113\0\u0113\0\u0113\0\u0113\0\u0113"+
    "\0\u0113\0\u0113\0\u0113\0\u0113\0\u0113\0\u0113\0\u0113\0\u0113"+
    "\0\u0f0a\0\u0f41\0\u0113\0\u0113\0\u0113\0\u0f78\0\u0faf\0\u0113"+
    "\0\u0a19\0\u0113\0\u0fe6\0\u101d\0\u1054\0\u108b\0\u10c2\0\u10f9"+
    "\0\u1130\0\u1167\0\u01b8\0\u119e\0\u11d5\0\u120c\0\u1243\0\u127a"+
    "\0\u12b1\0\u12e8\0\u131f\0\u1356\0\u138d\0\u01b8\0\u13c4\0\u13fb"+
    "\0\u1432\0\u01b8\0\u0113\0\u1469\0\u14a0\0\u01b8\0\u14d7\0\u150e"+
    "\0\u1545\0\u01b8\0\u01b8\0\u157c\0\u01b8\0\u15b3\0\u15ea\0\u1621"+
    "\0\u1658\0\u168f\0\u16c6\0\u16fd\0\u1734\0\u01b8\0\u176b\0\u17a2"+
    "\0\u17d9\0\u1810\0\u01b8\0\u01b8\0\u1847\0\u187e\0\u18b5\0\u18ec"+
    "\0\u1923\0\u195a\0\u1991\0\u19c8\0\u19ff\0\u01b8\0\u1a36\0\u01b8"+
    "\0\u01b8\0\u01b8\0\u1a6d\0\u01b8\0\u01b8\0\u01b8\0\u1aa4\0\u1adb"+
    "\0\u1b12\0\u01b8\0\u1b49\0\u1b80\0\u01b8\0\u0113\0\u01b8\0\u1bb7"+
    "\0\u01b8\0\u01b8\0\u1bee\0\u1c25\0\u1c5c\0\u1c93\0\u01b8\0\u01b8";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[200];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\6\1\7\1\10\1\11\1\12\1\13\1\7\1\14"+
    "\1\15\1\16\2\12\1\17\1\20\1\21\2\12\1\22"+
    "\1\23\1\24\1\25\1\26\1\12\1\27\4\12\1\30"+
    "\1\31\1\12\1\32\1\33\1\34\1\35\1\36\1\37"+
    "\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\47"+
    "\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57"+
    "\1\6\1\7\6\60\1\7\43\60\1\61\14\60\5\62"+
    "\2\63\54\62\1\64\1\62\1\65\1\66\5\67\1\70"+
    "\1\71\54\67\1\72\1\67\1\73\1\74\5\75\2\63"+
    "\55\75\1\76\1\77\1\100\71\0\1\10\1\101\1\12"+
    "\4\0\26\12\33\0\1\102\65\0\1\12\1\0\1\12"+
    "\4\0\26\12\36\0\1\7\70\0\1\103\36\0\1\104"+
    "\2\0\1\105\16\0\1\12\1\0\1\12\4\0\1\12"+
    "\1\106\1\107\23\12\32\0\1\12\1\0\1\12\4\0"+
    "\16\12\1\110\4\12\1\111\2\12\32\0\1\12\1\0"+
    "\1\12\4\0\1\12\1\112\3\12\1\113\12\12\1\114"+
    "\5\12\32\0\1\12\1\0\1\12\4\0\4\12\1\115"+
    "\1\12\1\116\17\12\32\0\1\12\1\0\1\12\4\0"+
    "\5\12\1\117\16\12\1\120\1\12\32\0\1\12\1\0"+
    "\1\12\4\0\4\12\1\121\21\12\32\0\1\12\1\0"+
    "\1\12\4\0\6\12\1\122\5\12\1\123\11\12\32\0"+
    "\1\12\1\0\1\12\4\0\14\12\1\124\11\12\32\0"+
    "\1\12\1\0\1\12\4\0\4\12\1\125\21\12\32\0"+
    "\1\12\1\0\1\12\4\0\14\12\1\126\5\12\1\127"+
    "\3\12\32\0\1\12\1\0\1\12\4\0\22\12\1\130"+
    "\3\12\32\0\1\12\1\0\1\12\4\0\2\12\1\131"+
    "\12\12\1\132\10\12\67\0\1\133\76\0\1\134\66\0"+
    "\1\135\1\136\65\0\1\137\1\0\1\140\64\0\1\141"+
    "\66\0\1\142\66\0\1\143\66\0\1\144\66\0\1\145"+
    "\76\0\1\146\67\0\1\147\55\0\1\150\102\0\1\151"+
    "\3\0\6\60\1\0\43\60\1\0\14\60\6\152\1\0"+
    "\1\152\1\153\41\152\1\61\14\152\5\62\2\0\54\62"+
    "\1\0\1\62\1\0\1\62\5\0\2\63\57\0\1\63"+
    "\16\0\1\71\6\0\1\70\1\0\1\74\33\0\1\154"+
    "\1\0\1\155\1\0\5\62\2\63\54\62\1\0\1\62"+
    "\1\0\1\66\63\0\1\156\66\0\1\157\1\0\1\155"+
    "\1\0\5\75\2\0\55\75\2\0\1\75\16\0\1\71"+
    "\6\0\1\70\1\0\1\74\34\0\1\160\1\155\1\0"+
    "\5\75\2\63\55\75\2\0\1\100\2\0\1\161\67\0"+
    "\1\162\63\0\5\103\2\0\60\103\2\0\1\12\1\0"+
    "\1\12\4\0\2\12\1\163\23\12\32\0\1\12\1\0"+
    "\1\12\4\0\3\12\1\164\12\12\1\165\7\12\32\0"+
    "\1\12\1\0\1\12\4\0\2\12\1\166\23\12\32\0"+
    "\1\12\1\0\1\12\4\0\10\12\1\167\15\12\32\0"+
    "\1\12\1\0\1\12\4\0\3\12\1\170\22\12\32\0"+
    "\1\12\1\0\1\12\4\0\6\12\1\171\17\12\32\0"+
    "\1\12\1\0\1\12\4\0\16\12\1\172\7\12\32\0"+
    "\1\12\1\0\1\12\4\0\23\12\1\173\2\12\32\0"+
    "\1\12\1\0\1\12\4\0\1\12\1\174\24\12\32\0"+
    "\1\12\1\0\1\12\4\0\1\175\2\12\1\176\22\12"+
    "\32\0\1\12\1\0\1\12\4\0\24\12\1\177\1\12"+
    "\32\0\1\12\1\0\1\12\4\0\13\12\1\200\12\12"+
    "\32\0\1\12\1\0\1\12\4\0\10\12\1\201\4\12"+
    "\1\202\10\12\32\0\1\12\1\0\1\12\4\0\4\12"+
    "\1\203\21\12\32\0\1\12\1\0\1\12\4\0\16\12"+
    "\1\204\7\12\32\0\1\12\1\0\1\12\4\0\6\12"+
    "\1\205\12\12\1\206\4\12\32\0\1\12\1\0\1\12"+
    "\4\0\14\12\1\207\11\12\32\0\1\12\1\0\1\12"+
    "\4\0\10\12\1\210\15\12\32\0\1\12\1\0\1\12"+
    "\4\0\1\12\1\211\24\12\32\0\1\12\1\0\1\12"+
    "\4\0\14\12\1\212\11\12\113\0\1\213\3\0\6\152"+
    "\1\0\1\152\1\0\41\152\1\0\14\152\63\0\1\76"+
    "\70\0\1\214\3\0\1\12\1\0\1\12\4\0\3\12"+
    "\1\215\22\12\32\0\1\12\1\0\1\12\4\0\4\12"+
    "\1\216\21\12\32\0\1\12\1\0\1\12\4\0\1\217"+
    "\25\12\32\0\1\12\1\0\1\12\4\0\16\12\1\220"+
    "\7\12\32\0\1\12\1\0\1\12\4\0\16\12\1\221"+
    "\7\12\32\0\1\12\1\0\1\12\4\0\4\12\1\222"+
    "\21\12\32\0\1\12\1\0\1\12\4\0\7\12\1\223"+
    "\16\12\32\0\1\12\1\0\1\12\4\0\4\12\1\224"+
    "\21\12\32\0\1\12\1\0\1\12\4\0\1\12\1\225"+
    "\24\12\32\0\1\12\1\0\1\12\4\0\1\12\1\226"+
    "\24\12\32\0\1\12\1\0\1\12\4\0\16\12\1\227"+
    "\7\12\32\0\1\12\1\0\1\12\4\0\2\12\1\230"+
    "\23\12\32\0\1\12\1\0\1\12\4\0\1\12\1\231"+
    "\24\12\32\0\1\12\1\0\1\12\4\0\17\12\1\232"+
    "\6\12\32\0\1\12\1\0\1\12\4\0\16\12\1\233"+
    "\7\12\32\0\1\12\1\0\1\12\4\0\2\12\1\234"+
    "\23\12\32\0\1\12\1\0\1\12\4\0\6\12\1\235"+
    "\17\12\32\0\1\12\1\0\1\12\4\0\4\12\1\236"+
    "\21\12\32\0\1\12\1\0\1\12\4\0\15\12\1\237"+
    "\10\12\32\0\1\12\1\0\1\12\4\0\1\12\1\240"+
    "\24\12\32\0\1\12\1\0\1\12\4\0\3\12\1\241"+
    "\22\12\113\0\1\242\5\0\1\12\1\0\1\12\4\0"+
    "\3\12\1\243\22\12\32\0\1\12\1\0\1\12\4\0"+
    "\22\12\1\244\3\12\32\0\1\12\1\0\1\12\4\0"+
    "\10\12\1\245\15\12\32\0\1\12\1\0\1\12\4\0"+
    "\1\246\25\12\32\0\1\12\1\0\1\12\4\0\14\12"+
    "\1\247\11\12\32\0\1\12\1\0\1\12\4\0\6\12"+
    "\1\250\17\12\32\0\1\12\1\0\1\12\4\0\2\12"+
    "\1\251\23\12\32\0\1\12\1\0\1\12\4\0\6\12"+
    "\1\252\17\12\32\0\1\12\1\0\1\12\4\0\10\12"+
    "\1\253\15\12\32\0\1\12\1\0\1\12\4\0\2\12"+
    "\1\254\23\12\32\0\1\12\1\0\1\12\4\0\4\12"+
    "\1\255\21\12\32\0\1\12\1\0\1\12\4\0\25\12"+
    "\1\256\32\0\1\12\1\0\1\12\4\0\14\12\1\257"+
    "\11\12\32\0\1\12\1\0\1\12\4\0\23\12\1\260"+
    "\2\12\32\0\1\12\1\0\1\12\4\0\4\12\1\261"+
    "\21\12\32\0\1\12\1\0\1\12\4\0\4\12\1\262"+
    "\21\12\115\0\1\263\3\0\1\12\1\0\1\12\4\0"+
    "\1\264\25\12\32\0\1\12\1\0\1\12\4\0\22\12"+
    "\1\265\3\12\32\0\1\12\1\0\1\12\4\0\5\12"+
    "\1\266\20\12\32\0\1\12\1\0\1\12\4\0\11\12"+
    "\1\267\14\12\32\0\1\12\1\0\1\12\4\0\5\12"+
    "\1\270\20\12\32\0\1\12\1\0\1\12\4\0\1\12"+
    "\1\271\24\12\32\0\1\12\1\0\1\12\4\0\1\272"+
    "\25\12\32\0\1\12\1\0\1\12\4\0\16\12\1\273"+
    "\7\12\32\0\1\12\1\0\1\12\4\0\1\274\25\12"+
    "\32\0\1\12\1\0\1\12\4\0\5\12\1\275\20\12"+
    "\113\0\1\276\5\0\1\12\1\0\1\12\4\0\4\12"+
    "\1\277\21\12\32\0\1\12\1\0\1\12\4\0\1\300"+
    "\25\12\32\0\1\12\1\0\1\12\4\0\16\12\1\301"+
    "\7\12\32\0\1\12\1\0\1\12\4\0\4\12\1\302"+
    "\21\12\32\0\1\12\1\0\1\12\4\0\16\12\1\303"+
    "\7\12\32\0\1\12\1\0\1\12\4\0\4\12\1\304"+
    "\21\12\32\0\1\12\1\0\1\12\4\0\4\12\1\305"+
    "\21\12\32\0\1\12\1\0\1\12\4\0\15\12\1\306"+
    "\10\12\32\0\1\12\1\0\1\12\4\0\11\12\1\307"+
    "\14\12\32\0\1\12\1\0\1\12\4\0\24\12\1\310"+
    "\1\12\30\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[7370];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\1\3\0\2\11\4\1\1\11\16\1\7\11\12\1"+
    "\1\11\2\1\1\11\4\1\1\11\2\1\3\11\2\1"+
    "\1\11\1\1\1\11\2\1\2\0\1\1\2\11\25\1"+
    "\16\11\1\0\1\1\3\11\2\0\1\11\1\1\1\11"+
    "\30\1\1\11\1\0\25\1\1\0\20\1\1\0\12\1"+
    "\1\11\12\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[200];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  StringBuffer string = new StringBuffer();

  private Symbol symbol(int sym) {
    return new Symbol(sym, yyline+1, yycolumn+1);
  }
  
  private Symbol symbol(int sym, Object val) {
    return new Symbol(sym, yyline+1, yycolumn+1, val);
  }
  
  private void error(String message) {
    console.get().err("Error at line "+(yyline+1)+", column "+(yycolumn+1)+" : "+message);
  }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 136) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 46: 
          { return symbol(sym.LTEQ);
          }
        case 82: break;
        case 72: 
          { return symbol(sym.SWITCH);
          }
        case 83: break;
        case 20: 
          { return symbol(sym.MODULUS);
          }
        case 84: break;
        case 53: 
          { string.append("\\");
          }
        case 85: break;
        case 7: 
          { return symbol(sym.DI);
          }
        case 86: break;
        case 49: 
          { return symbol(sym.OR);
          }
        case 87: break;
        case 10: 
          { return symbol(sym.LPAREN);
          }
        case 88: break;
        case 57: 
          { return symbol(sym.TRY);
          }
        case 89: break;
        case 73: 
          { return symbol(sym.EXTERN);
          }
        case 90: break;
        case 44: 
          { return symbol(sym.MODULUSEQ);
          }
        case 91: break;
        case 39: 
          { return symbol(sym.PLEQ);
          }
        case 92: break;
        case 61: 
          { return symbol(sym.ELSE);
          }
        case 93: break;
        case 31: 
          { string.append('\n');
          }
        case 94: break;
        case 52: 
          { string.append('\"');
          }
        case 95: break;
        case 5: 
          { return symbol(sym.DOT);
          }
        case 96: break;
        case 47: 
          { return symbol(sym.GTEQ);
          }
        case 97: break;
        case 8: 
          { return symbol(sym.COLON);
          }
        case 98: break;
        case 41: 
          { return symbol(sym.MIEQ);
          }
        case 99: break;
        case 48: 
          { return symbol(sym.AND);
          }
        case 100: break;
        case 80: 
          { return symbol(sym.PROTECTED);
          }
        case 101: break;
        case 30: 
          { string.append('\r');
          }
        case 102: break;
        case 25: 
          { string.setLength(0); yybegin(STRING);
          }
        case 103: break;
        case 17: 
          { return symbol(sym.PL);
          }
        case 104: break;
        case 36: 
          { return symbol(sym.IF);
          }
        case 105: break;
        case 18: 
          { return symbol(sym.MI);
          }
        case 106: break;
        case 54: 
          { string.append('\'');
          }
        case 107: break;
        case 21: 
          { return symbol(sym.NOT);
          }
        case 108: break;
        case 24: 
          { return symbol(sym.COUNT);
          }
        case 109: break;
        case 32: 
          { string.append('\t');
          }
        case 110: break;
        case 45: 
          { return symbol(sym.NOTEQ);
          }
        case 111: break;
        case 69: 
          { return symbol(sym.WHILE);
          }
        case 112: break;
        case 6: 
          { return symbol(sym.SEMI);
          }
        case 113: break;
        case 9: 
          { return symbol(sym.COMMA);
          }
        case 114: break;
        case 66: 
          { return symbol(sym.CATCH);
          }
        case 115: break;
        case 55: 
          { return symbol(sym.ETCETERA);
          }
        case 116: break;
        case 26: 
          { string.setLength(0); yybegin(STRING_LIT);
          }
        case 117: break;
        case 76: 
          { string.append("\"\"\"");
          }
        case 118: break;
        case 50: 
          { return symbol(sym.INSERT);
          }
        case 119: break;
        case 81: 
          { return symbol(sym.INSTANCEOF);
          }
        case 120: break;
        case 70: 
          { return symbol(sym.BOOL, false);
          }
        case 121: break;
        case 38: 
          { return symbol(sym.EQEQ);
          }
        case 122: break;
        case 63: 
          { return symbol(sym.NULL);
          }
        case 123: break;
        case 33: 
          { yybegin(YYINITIAL); 
								return symbol(sym.STRING, string.toString());
          }
        case 124: break;
        case 15: 
          { return symbol(sym.RBRACE);
          }
        case 125: break;
        case 62: 
          { return symbol(sym.ENUM);
          }
        case 126: break;
        case 59: 
          { string.setLength(0); yybegin(STRING_FRMT);
          }
        case 127: break;
        case 40: 
          { return symbol(sym.PLPL);
          }
        case 128: break;
        case 4: 
          { if(yytext().contains("."))
								{
									BigDecimal val = null;
									try{ val = new BigDecimal(yytext()); }
									catch(NumberFormatException e)
									{
										error("Number format exception <" + yytext() + ">");
									}
									return symbol(sym.DOUBLE, val);
								}
								else
								{
									BigInteger val = null;
									try{ val = new BigInteger(yytext()); }
									catch(NumberFormatException e)
									{
										error("Number format exception <" + yytext() + ">");
									}
									return symbol(sym.INT, val);
								}
          }
        case 129: break;
        case 79: 
          { return symbol(sym.PRIVATE);
          }
        case 130: break;
        case 75: 
          { return symbol(sym.RETURN);
          }
        case 131: break;
        case 12: 
          { return symbol(sym.LBRACKET);
          }
        case 132: break;
        case 37: 
          { return symbol(sym.CALLBACK);
          }
        case 133: break;
        case 28: 
          { /* Do nothing. */
          }
        case 134: break;
        case 51: 
          { yybegin(YYINITIAL);
          }
        case 135: break;
        case 68: 
          { return symbol(sym.THROW);
          }
        case 136: break;
        case 42: 
          { return symbol(sym.MIMI);
          }
        case 137: break;
        case 14: 
          { return symbol(sym.LBRACE);
          }
        case 138: break;
        case 65: 
          { return symbol(sym.CLASSDEF);
          }
        case 139: break;
        case 71: 
          { return symbol(sym.STATIC);
          }
        case 140: break;
        case 78: 
          { return symbol(sym.DEFAULT);
          }
        case 141: break;
        case 13: 
          { return symbol(sym.RBRACKET);
          }
        case 142: break;
        case 64: 
          { return symbol(sym.BOOL, true);
          }
        case 143: break;
        case 23: 
          { return symbol(sym.GT);
          }
        case 144: break;
        case 77: 
          { return symbol(sym.INCLUDE);
          }
        case 145: break;
        case 74: 
          { return symbol(sym.PUBLIC);
          }
        case 146: break;
        case 34: 
          { return symbol(sym.DIEQ);
          }
        case 147: break;
        case 2: 
          { /* ignore */
          }
        case 148: break;
        case 29: 
          { yybegin(YYINITIAL); 
								return symbol(sym.STRING, 
								string.toString());
          }
        case 149: break;
        case 16: 
          { return symbol(sym.EQ);
          }
        case 150: break;
        case 11: 
          { return symbol(sym.RPAREN);
          }
        case 151: break;
        case 3: 
          { /* throw new Error("Illegal character <" + String.ValueOf((int)yytext()) + ">");*/
							error("Illegal character <" + String.valueOf((int)yytext().charAt(0)) + ">");
          }
        case 152: break;
        case 27: 
          { string.append( yytext() );
          }
        case 153: break;
        case 67: 
          { return symbol(sym.BREAK);
          }
        case 154: break;
        case 58: 
          { return symbol(sym.FOR);
          }
        case 155: break;
        case 35: 
          { yybegin(C_COMMENT);
          }
        case 156: break;
        case 60: 
          { return symbol(sym.CASE);
          }
        case 157: break;
        case 19: 
          { return symbol(sym.MU);
          }
        case 158: break;
        case 22: 
          { return symbol(sym.LT);
          }
        case 159: break;
        case 1: 
          { return symbol(sym.IDENT, yytext());
          }
        case 160: break;
        case 56: 
          { return symbol(sym.NEW);
          }
        case 161: break;
        case 43: 
          { return symbol(sym.MUEQ);
          }
        case 162: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              {     return symbol( sym.EOF );
 }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
