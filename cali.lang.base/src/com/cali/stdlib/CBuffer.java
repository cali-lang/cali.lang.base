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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import com.cali.Environment;
import com.cali.ast.caliException;
import com.cali.types.CaliType;
import com.cali.types.CaliNull;
import com.cali.types.CaliObject;
import com.cali.types.CaliInt;
import com.cali.types.CaliDouble;
import com.cali.types.CaliString;
import com.cali.types.CaliException;


public class CBuffer {
	private int writeCursor = 0;
	private int readCursor = 0;
	
	public enum byteOrder {
		BIG,
		LITTLE
	}
	
	protected byte[] buff = null;
	
	public CBuffer() { this.buff = new byte[1024]; }
	
	public void newBuffer(int Size) { this.buff = new byte[Size]; }
	
	public byte[] getBuffer() { return this.buff; }
	public void setBuffer(byte[] Buff) { this.buff = Buff; }
	
	public CaliType newBuffer(Environment env, ArrayList<CaliType> args) {
		synchronized(this) {
			this.buff = new byte[(int)((CaliInt)args.get(0)).getValue()];
			return new CaliNull();
		}
	}
	
	public CaliType size(Environment env, ArrayList<CaliType> args) {
		return new CaliInt(this.buff.length);
	}
	
	public CaliType _clear(Environment env, ArrayList<CaliType> args) {
		synchronized(this) {
			byte b = 0;
			Arrays.fill(this.buff, b);
			this.readCursor = 0;
			this.writeCursor = 0;
			return new CaliNull();
		}
	}
	
	public CaliType _writeSeek(Environment env, ArrayList<CaliType> args) {
		synchronized(this) {
			int index = (int)((CaliInt)args.get(0)).getValue();
			if((index >= 0)&&(index < buff.length)) {
				this.writeCursor = index;
			} else {
				return new CaliException("buffer.writeSeek(): Index out of bounds.");
			}
			return new CaliNull();
		}
	}
	
	public CaliType _readSeek(Environment env, ArrayList<CaliType> args) {
		synchronized(this) {
			int index = (int)((CaliInt)args.get(0)).getValue();
			if((index >= 0)&&(index < buff.length)) {
				this.readCursor = index;
			} else {
				return new CaliException("buffer.readSeek(): Index out of bounds.");
			}
			return new CaliNull();
		}
	}
	
	public CaliType _addString(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			String str = ((CaliString)args.get(0)).getValueString();
			String cset = ((CaliString)args.get(1)).getValueString();
			this._setStringAt(this.writeCursor, str, cset);
			this.writeCursor += str.getBytes().length;
			return new CaliNull();
		}
	}
	
	public CaliType _addByte(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = this.writeCursor;
			int ival = (int)((CaliInt)args.get(0)).getValue();
			this.setByte(index, ival); this.writeCursor++;
			return new CaliNull();
		}
	}
	
	public CaliType _addUByte(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = this.writeCursor;
			int ival = (int)((CaliInt)args.get(0)).getValue();
			this.setUByte(index, ival); this.writeCursor++;
			return new CaliNull();
		}
	}
	
	public CaliType _addShort(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = this.writeCursor;
			short ival = (short)((CaliInt)args.get(0)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
			this.setShort(index, ival, bo); this.writeCursor += 2;
			return new CaliNull();
		}
	}
	
	public CaliType _addUShort(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = this.writeCursor;
			int ival = (short)((CaliInt)args.get(0)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
			this.setUShort(index, ival, bo); this.writeCursor += 2;
			return new CaliNull();
		}
	}
	
	public CaliType _addInt(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = this.writeCursor;
			int ival = (int)((CaliInt)args.get(0)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
			this.setInt(index, ival, bo); this.writeCursor += 4;
			return new CaliNull();
		}
	}
	
	public CaliType _addUInt(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = this.writeCursor;
			long ival = ((CaliInt)args.get(0)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
			this.setUInt(index, ival, bo); this.writeCursor += 4;
			return new CaliNull();
		}
	}
	
	public CaliType _addLong(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = this.writeCursor;
			long ival = ((CaliInt)args.get(0)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
			this.setLong(index, ival, bo); this.writeCursor += 8;
			return new CaliNull();
		}
	}
	
	public CaliType _addFloat(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = this.writeCursor;
			double tval = ((CaliDouble)args.get(0)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
			this.setFloat(index, tval, bo); this.writeCursor += 4;
			return new CaliNull();
		}
	}
	
	public CaliType _addDouble(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = this.writeCursor;
			double tval = ((CaliDouble)args.get(0)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
			this.setDouble(index, tval, bo); this.writeCursor += 8;
			return new CaliNull();
		}
	}
	
	public CaliType getWriteCursor(Environment env, ArrayList<CaliType> args) {
		return new CaliInt(this.writeCursor);
	}
	
	public CaliType getReadCursor(Environment env, ArrayList<CaliType> args) {
		return new CaliInt(this.readCursor);
	}
	
	public CaliType getString(Environment env, ArrayList<CaliType> args) throws caliException {
		return new CaliString(this._getString(((CaliString)args.get(0)).getValueString()));
	}
	
	public CaliType getStringAt(Environment env, ArrayList<CaliType> args) throws caliException {
		int index = (int) ((CaliInt)args.get(1)).getValue();
		int length = (int) ((CaliInt)args.get(0)).getValue();
		String cset = ((CaliString)args.get(2)).getValueString();
		boolean increment = false;
		if(index < 0) { index = this.readCursor; increment = true; }
		String val = this._getStringAt(index, length, cset);
		if(increment) this.readCursor += length;
		return new CaliString(val);
	}
	
	public CaliType getByte(Environment env, ArrayList<CaliType> args) throws caliException {
		int index = (int)((CaliInt)args.get(0)).getValue();
		boolean increment = false;
		if(index < 0) { index = this.readCursor; increment = true; }
		int val = this._getByte(index);
		if(increment) this.readCursor++;
		return new CaliInt(val);
	}
	
	public CaliType getUByte(Environment env, ArrayList<CaliType> args) throws caliException {
		int index = (int)((CaliInt)args.get(0)).getValue();
		boolean increment = false;
		if(index < 0) { index = this.readCursor; increment = true; }
		int val = this._getUByte(index);
		if(increment) this.readCursor++;
		return new CaliInt(val);
	}
	
	public CaliType getShort(Environment env, ArrayList<CaliType> args) throws caliException {
		int index = (int)((CaliInt)args.get(0)).getValue();
		byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
		boolean increment = false;
		if(index < 0) { index = this.readCursor; increment = true; }
		int val = this._getShort(index, bo);
		if(increment) this.readCursor += 2;
		return new CaliInt(val);
	}
	
	public CaliType getUShort(Environment env, ArrayList<CaliType> args) throws caliException {
		int index = (int)((CaliInt)args.get(0)).getValue();
		byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
		boolean increment = false;
		if(index < 0) { index = this.readCursor; increment = true; }
		int val = this._getUShort(index, bo);
		if(increment) this.readCursor += 2;
		return new CaliInt(val);
	}
	
	public CaliType getInt(Environment env, ArrayList<CaliType> args) throws caliException {
		int index = (int)((CaliInt)args.get(0)).getValue();
		byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
		boolean increment = false;
		if(index < 0) { index = this.readCursor; increment = true; }
		int val = this._getInt(index, bo);
		if(increment) this.readCursor += 4;
		return new CaliInt(val);
	}
	
	public CaliType getUInt(Environment env, ArrayList<CaliType> args) throws caliException {
		int index = (int)((CaliInt)args.get(0)).getValue();
		byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
		boolean increment = false;
		if(index < 0) { index = this.readCursor; increment = true; }
		long val = this._getUInt(index, bo);
		if(increment) this.readCursor += 4;
		return new CaliInt(val);
	}
	
	public CaliType getLong(Environment env, ArrayList<CaliType> args) throws caliException {
		int index = (int)((CaliInt)args.get(0)).getValue();
		byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
		boolean increment = false;
		if(index < 0) { index = this.readCursor; increment = true; }
		long val = this._getLong(index, bo);
		if(increment) this.readCursor += 8;
		return new CaliInt(val);
	}
	
	public CaliType getFloat(Environment env, ArrayList<CaliType> args) throws caliException {
		int index = (int)((CaliInt)args.get(0)).getValue();
		byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
		boolean increment = false;
		if(index < 0) { index = this.readCursor; increment = true; }
		double val = this._getFloat(index, bo);
		if(increment) this.readCursor += 4;
		return new CaliDouble(val);
	}
	
	public CaliType getDouble(Environment env, ArrayList<CaliType> args) throws caliException {
		int index = (int)((CaliInt)args.get(0)).getValue();
		byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
		boolean increment = false;
		if(index < 0) { index = this.readCursor; increment = true; }
		double val = this._getDouble(index, bo);
		if(increment) this.readCursor += 4;
		return new CaliDouble(val);
	}
	
	public CaliType setString(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			String str = ((CaliString)args.get(0)).getValueString();
			String cset = ((CaliString)args.get(1)).getValueString();
			return new CaliInt(this._setString(str, cset));
		}
	}
	
	public CaliType setStringAt(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = (int) ((CaliInt)args.get(0)).getValue();
			String str = ((CaliString)args.get(1)).getValueString();
			String cset = ((CaliString)args.get(2)).getValueString();
			return new CaliInt(this._setStringAt(index, str, cset));
		}
	}
	
	public CaliType _setByte(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = (int)((CaliInt)args.get(0)).getValue();
			int ival = (int)((CaliInt)args.get(1)).getValue();
			this.setByte(index, ival);
			return new CaliNull();
		}
	}
	
	public CaliType _setUByte(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = (int)((CaliInt)args.get(0)).getValue();
			int ival = (int)((CaliInt)args.get(1)).getValue();
			this.setUByte(index, ival);
			return new CaliNull();
		}
	}
	
	public CaliType _setShort(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = (int)((CaliInt)args.get(0)).getValue();
			short ival = (short)((CaliInt)args.get(1)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(2)).getValueString());
			this.setShort(index, ival, bo);
			return new CaliNull();
		}
	}
	
	public CaliType _setUShort(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = (int)((CaliInt)args.get(0)).getValue();
			int ival = (short)((CaliInt)args.get(1)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(2)).getValueString());
			this.setUShort(index, ival, bo);
			return new CaliNull();
		}
	}
	
	public CaliType _setInt(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = (int)((CaliInt)args.get(0)).getValue();
			int ival = (int)((CaliInt)args.get(1)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(2)).getValueString());
			this.setInt(index, ival, bo);
			return new CaliNull();
		}
	}
	
	public CaliType _setUInt(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = (int)((CaliInt)args.get(0)).getValue();
			long ival = ((CaliInt)args.get(1)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(2)).getValueString());
			this.setUInt(index, ival, bo);
			return new CaliNull();
		}
	}
	
	public CaliType _setLong(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = (int)((CaliInt)args.get(0)).getValue();
			long ival = ((CaliInt)args.get(1)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(2)).getValueString());
			this.setLong(index, ival, bo);
			return new CaliNull();
		}
	}
	
	public CaliType _setFloat(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = (int)((CaliInt)args.get(0)).getValue();
			double tval = ((CaliDouble)args.get(1)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(2)).getValueString());
			this.setFloat(index, tval, bo);
			return new CaliNull();
		}
	}
	
	public CaliType _setDouble(Environment env, ArrayList<CaliType> args) throws caliException {
		synchronized(this) {
			int index = (int)((CaliInt)args.get(0)).getValue();
			double tval = ((CaliDouble)args.get(1)).getValue();
			byteOrder bo = this.getByteOrder(((CaliString)args.get(2)).getValueString());
			this.setDouble(index, tval, bo);
			return new CaliNull();
		}
	}
	
	public CaliType byteToBinary(Environment env, ArrayList<CaliType> args) {
		int index = (int)((CaliInt)args.get(0)).getValue();
		if((index >= 0)&&(index < buff.length)) {
			String bin = Integer.toBinaryString((this.buff[index] + 256) % 256);
			while(bin.length() < 8) bin = "0" + bin;
			return new CaliString(bin);
		} else {
			return new CaliException("buffer.byteToBinary(): Index out of bounds.");
		}
	}
	
	public CaliType shortToBinary(Environment env, ArrayList<CaliType> args) {
		int index = (int)((CaliInt)args.get(0)).getValue();
		byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
		
		if((index >= 0)&&(index < buff.length + 1)) {
			String bin1 = Integer.toBinaryString((this.buff[index] + 256) % 256);
			while(bin1.length() < 8) bin1 = "0" + bin1;
			String bin2 = Integer.toBinaryString((this.buff[index + 1] + 256) % 256);
			while(bin2.length() < 8) bin2 = "0" + bin2;
			
			if(bo == byteOrder.BIG) {
				return new CaliString(bin1 + bin2);
			} else {
				return new CaliString(bin2 + bin1);
			}
		} else {
			return new CaliException("buffer.shortToBinary(): Index out of bounds.");
		}
	}
	
	public CaliType intToBinary(Environment env, ArrayList<CaliType> args) {
		int index = (int)((CaliInt)args.get(0)).getValue();
		byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
		
		if((index >= 0)&&(index < buff.length + 3)) {
			String bin1 = Integer.toBinaryString((this.buff[index] + 256) % 256);
			while(bin1.length() < 8) bin1 = "0" + bin1;
			String bin2 = Integer.toBinaryString((this.buff[index + 1] + 256) % 256);
			while(bin2.length() < 8) bin2 = "0" + bin2;
			String bin3 = Integer.toBinaryString((this.buff[index + 2] + 256) % 256);
			while(bin3.length() < 8) bin3 = "0" + bin3;
			String bin4 = Integer.toBinaryString((this.buff[index + 3] + 256) % 256);
			while(bin4.length() < 8) bin4 = "0" + bin4;
			
			if(bo == byteOrder.BIG) {
				return new CaliString(bin1 + bin2 + bin3 + bin4);
			} else {
				return new CaliString(bin4 + bin3 + bin2 + bin1);
			}
		} else {
			return new CaliException("buffer.intToBinary(): Index out of bounds.");
		}
	}
	
	public CaliType longToBinary(Environment env, ArrayList<CaliType> args) {
		int index = (int)((CaliInt)args.get(0)).getValue();
		byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
		
		if((index >= 0)&&(index < buff.length + 7)) {
			String bin1 = Integer.toBinaryString((this.buff[index] + 256) % 256);
			while(bin1.length() < 8) {
				bin1 = "0" + bin1;
			}
			String bin2 = Integer.toBinaryString((this.buff[index + 1] + 256) % 256);
			while(bin2.length() < 8) {
				bin2 = "0" + bin2;
			}
			String bin3 = Integer.toBinaryString((this.buff[index + 2] + 256) % 256);
			while(bin3.length() < 8) {
				bin3 = "0" + bin3;
			}
			String bin4 = Integer.toBinaryString((this.buff[index + 3] + 256) % 256);
			while(bin4.length() < 8) {
				bin4 = "0" + bin4;
			}
			String bin5 = Integer.toBinaryString((this.buff[index + 4] + 256) % 256);
			while(bin5.length() < 8) {
				bin5 = "0" + bin5;
			}
			String bin6 = Integer.toBinaryString((this.buff[index + 5] + 256) % 256);
			while(bin6.length() < 8) {
				bin6 = "0" + bin6;
			}
			String bin7 = Integer.toBinaryString((this.buff[index + 6] + 256) % 256);
			while(bin7.length() < 8) {
				bin7 = "0" + bin7;
			}
			String bin8 = Integer.toBinaryString((this.buff[index + 7] + 256) % 256);
			while(bin8.length() < 8) {
				bin8 = "0" + bin8;
			}
			
			if(bo == byteOrder.BIG) {
				return new CaliString(bin1 + bin2 + bin3 + bin4 + bin5 + bin6 + bin7 + bin8);
			} else {
				return new CaliString(bin8 + bin7 + bin6 + bin5 + bin4 + bin3 + bin2 + bin1);
			}
		} else {
			return new CaliException("buffer.longToBinary(): Index out of bounds.");
		}
	}
	
	public CaliType floatToBinary(Environment env, ArrayList<CaliType> args) {
		int index = (int)((CaliInt)args.get(0)).getValue();
		byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
		
		if((index >= 0)&&(index < buff.length + 3)) {
			String bin1 = Integer.toBinaryString((this.buff[index] + 256) % 256);
			while(bin1.length() < 8) {
				bin1 = "0" + bin1;
			}
			String bin2 = Integer.toBinaryString((this.buff[index + 1] + 256) % 256);
			while(bin2.length() < 8) {
				bin2 = "0" + bin2;
			}
			String bin3 = Integer.toBinaryString((this.buff[index + 2] + 256) % 256);
			while(bin3.length() < 8) {
				bin3 = "0" + bin3;
			}
			String bin4 = Integer.toBinaryString((this.buff[index + 3] + 256) % 256);
			while(bin4.length() < 8) {
				bin4 = "0" + bin4;
			}
			
			if(bo == byteOrder.BIG) {
				return new CaliString(bin1 + bin2 + bin3 + bin4);
			} else {
				return new CaliString(bin4 + bin3 + bin2 + bin1);
			}
		} else {
			return new CaliException("buffer.intToBinary(): Index out of bounds.");
		}
	}
	
	public CaliType doubleToBinary(Environment env, ArrayList<CaliType> args) {
		int index = (int)((CaliInt)args.get(0)).getValue();
		byteOrder bo = this.getByteOrder(((CaliString)args.get(1)).getValueString());
		
		if((index >= 0)&&(index < buff.length + 7)) {
			String bin1 = Integer.toBinaryString((this.buff[index] + 256) % 256);
			while(bin1.length() < 8) {
				bin1 = "0" + bin1;
			}
			String bin2 = Integer.toBinaryString((this.buff[index + 1] + 256) % 256);
			while(bin2.length() < 8) {
				bin2 = "0" + bin2;
			}
			String bin3 = Integer.toBinaryString((this.buff[index + 2] + 256) % 256);
			while(bin3.length() < 8) {
				bin3 = "0" + bin3;
			}
			String bin4 = Integer.toBinaryString((this.buff[index + 3] + 256) % 256);
			while(bin4.length() < 8) {
				bin4 = "0" + bin4;
			}
			String bin5 = Integer.toBinaryString((this.buff[index + 4] + 256) % 256);
			while(bin5.length() < 8) {
				bin5 = "0" + bin5;
			}
			String bin6 = Integer.toBinaryString((this.buff[index + 5] + 256) % 256);
			while(bin6.length() < 8) {
				bin6 = "0" + bin6;
			}
			String bin7 = Integer.toBinaryString((this.buff[index + 6] + 256) % 256);
			while(bin7.length() < 8) {
				bin7 = "0" + bin7;
			}
			String bin8 = Integer.toBinaryString((this.buff[index + 7] + 256) % 256);
			while(bin8.length() < 8) {
				bin8 = "0" + bin8;
			}
			
			if(bo == byteOrder.BIG) {
				return new CaliString(bin1 + bin2 + bin3 + bin4 + bin5 + bin6 + bin7 + bin8);
			} else {
				return new CaliString(bin8 + bin7 + bin6 + bin5 + bin4 + bin3 + bin2 + bin1);
			}
		} else {
			return new CaliException("buffer.doubleToBinary(): Index out of bounds.");
		}
	}
	
	public CaliType _copyFrom(Environment env, ArrayList<CaliType> args) {
		synchronized(this) {
			int dindex = (int)((CaliInt)args.get(0)).getValue();
			CaliObject obj = (CaliObject)args.get(1);
			int sindex = (int)((CaliInt)args.get(2)).getValue();
			int length = (int)((CaliInt)args.get(3)).getValue();
			byte[] obuff = null;
			
			if((obj.getExternObject() != null)&&(obj.getExternObject() instanceof CBuffer)) {
				obuff = ((CBuffer)obj.getExternObject()).buff;
			} else {
				return new CaliException("buffer.copyFrom(): External object is null or not of type buffer.");
			}
			
			if((dindex + length) < this.buff.length) {
				if((sindex + length) < obuff.length) {
					System.arraycopy(obuff, sindex, this.buff, dindex, length);
				} else {
					return new CaliException("buffer.copyFrom(): Source buffer overflow.");
				}
			} else {
				return new CaliException("buffer.copyFrom(): Destination buffer overflow.");
			}
			
			return new CaliNull();
		}
	}
	
	public CaliType _copyTo(Environment env, ArrayList<CaliType> args) {
		synchronized(this) {
			int sindex = (int)((CaliInt)args.get(0)).getValue();
			CaliObject obj = (CaliObject)args.get(1);
			int dindex = (int)((CaliInt)args.get(2)).getValue();
			int length = (int)((CaliInt)args.get(3)).getValue();
			byte[] obuff = null;
			
			if((obj.getExternObject() != null)&&(obj.getExternObject() instanceof CBuffer)) {
				obuff = ((CBuffer)obj.getExternObject()).buff;
			} else {
				return new CaliException("buffer.copyTo(): External object is null or not of type buffer.");
			}
			
			if((dindex + length) < this.buff.length) {
				if((sindex + length) < obuff.length) {
					System.arraycopy(this.buff, sindex, obuff, dindex, length);
				} else {
					return new CaliException("buffer.copyTo(): Source buffer overflow.");
				}
			} else {
				return new CaliException("buffer.copyTo(): Destination buffer overflow.");
			}
			
			return new CaliNull();
		}
	}
	
	public byteOrder getByteOrder(String str) {
		if(str.equals("big")) {
			return byteOrder.BIG;
		} else if(str.equals("little")) {
			return byteOrder.LITTLE;
		} else {
			return byteOrder.BIG;
		}
	}
	
	public String _getString(String charSet) throws caliException {
		String cset = charSet.toUpperCase().replace("_", "-");
		try {
			return new String(this.buff, cset);
		} catch (UnsupportedEncodingException e) {
			throw new caliException("buffer.getString(): Unsopported encoding exception. (" + e.getMessage() + ")");
		} catch(NullPointerException npe) {
			throw new caliException("buffer.getString(): Buffer object is null.");
		}
	}
	
	public String _getStringAt(int index, int length, String charSet) throws caliException {
		String cset = charSet.toUpperCase().replace("_", "-");
		try {
			byte[] dest = new byte[length];
			System.arraycopy(this.buff, index, dest, 0, length);
			return new String(dest, cset);
		} catch (UnsupportedEncodingException e) {
			throw new caliException("buffer.getStringAt(): Unsopported encoding exception. (" + e.getMessage() + ")");
		}
	}
	
	public int _getByte(int index) throws caliException {
		if((index >= 0)&&(index < buff.length)) {
			return (int)this.buff[index];
		} else {
			throw new caliException("buffer.getByte(): Index out of bounds.");
		}
	}
	
	public int _getUByte(int index) throws caliException {
		if((index >= 0)&&(index < buff.length)) {
			int val = (int) this.buff[index] & 0xff;
			return val;
		} else {
			throw new caliException("buffer.getUByte(): Index out of bounds.");
		}
	}
	
	public int _getShort(int index, byteOrder bo) throws caliException {
		if((index >= 0)&&(index < buff.length + 1)) {
			int val = 0;
			if(bo == byteOrder.BIG) {
				val = (
						((((int)this.buff[index]) << 8) & 0x0000ff00) + 
						((int)(this.buff[index + 1] & 0x000000ff))
					);
			} else {
				val = (
						((((int)this.buff[index + 1]) << 8) & 0x0000ff00) + 
						((int)(this.buff[index] & 0x000000ff))
					);
			}
			return val;
		} else {
			throw new caliException("buffer.getShort(): Index out of bounds.");
		}
	}
	
	public int _getUShort(int index, byteOrder bo) throws caliException {
		if((index >= 0)&&(index < buff.length + 1)) {
			int val = 0;
			
			if(bo == byteOrder.BIG) {
				val = (
						((((int)this.buff[index]) << 8) & 0x0000ff00) + 
						((int)(this.buff[index + 1] & 0x000000ff))
					);
			} else {
				val = (
						((((int)this.buff[index + 1]) << 8) & 0x0000ff00) + 
						((int)(this.buff[index] & 0x000000ff))
					);
			}
			return val;
		} else {
			throw new caliException("buffer.getShort(): Index out of bounds.");
		}
	}
	
	public int _getInt(int index, byteOrder bo) throws caliException {
		if((index >= 0)&&(index < buff.length + 3)) {
			int val = 0;
			if(bo == byteOrder.BIG) {
				val = (
						((((int)this.buff[index]) << 24) & 0xff000000) +
						((((int)this.buff[index + 1]) << 16) & 0x00ff0000) +
						((((int)this.buff[index + 2]) << 8) & 0x0000ff00)  + 
						(((int)this.buff[index + 3]) & 0x000000ff)
					);
			} else {
				val = (
						((((int)this.buff[index + 3]) << 24) & 0xff000000) +
						((((int)this.buff[index + 2]) << 16) & 0x00ff0000) +
						((((int)this.buff[index + 1]) << 8) & 0x0000ff00)  + 
						(((int)this.buff[index]) & 0x000000ff)
					);
			}
			return val;
		} else {
			throw new caliException("buffer.getInt(): Index out of bounds.");
		}
	}
	
	public long _getUInt(int index, byteOrder bo) throws caliException {
		if((index >= 0)&&(index < buff.length + 3)) {
			long val = 0l;
			if(bo == byteOrder.BIG) {
				val = (
						((((long)this.buff[index]) << 24) & 0xff000000l) +
						((((long)this.buff[index + 1]) << 16) & 0x00ff0000l) +
						((((long)this.buff[index + 2]) << 8) & 0x0000ff00l)  + 
						(((long)this.buff[index + 3]) & 0x000000ffl)
					);
			} else {
				val = (
						((((long)this.buff[index + 3]) << 24) & 0xff000000l) +
						((((long)this.buff[index + 2]) << 16) & 0x00ff0000l) +
						((((long)this.buff[index + 1]) << 8) & 0x0000ff00l)  + 
						(((long)this.buff[index]) & 0x000000ffl)
					);
			}
			return val;
		} else {
			throw new caliException("buffer.getUInt(): Index out of bounds.");
		}
	}
	
	public long _getLong(int index, byteOrder bo) throws caliException {
		if((index >= 0)&&(index < buff.length + 7)) {
			long val = 0;
			if(bo == byteOrder.BIG) {
				val = (
						((((long)this.buff[index]) << 56) & 0xff00000000000000l) +
						((((long)this.buff[index + 1]) << 48) & 0x00ff000000000000l) +
						((((long)this.buff[index + 2]) << 40) & 0x0000ff0000000000l)  + 
						((((long)this.buff[index + 3]) << 32) & 0x000000ff00000000l)  + 
						((((long)this.buff[index + 4]) << 24) & 0x00000000ff000000l)  + 
						((((long)this.buff[index + 5]) << 16) & 0x0000000000ff0000l)  + 
						((((long)this.buff[index + 6]) << 8) & 0x000000000000ff00l)  + 
						(((long)this.buff[index + 7]) & 0x00000000000000ffl)
					);
			} else {
				val = (
						((((long)this.buff[index + 7]) << 56) & 0xff00000000000000l) +
						((((long)this.buff[index + 6]) << 48) & 0x00ff000000000000l) +
						((((long)this.buff[index + 5]) << 40) & 0x0000ff0000000000l) +
						((((long)this.buff[index + 4]) << 32) & 0x000000ff00000000l) +
						((((long)this.buff[index + 3]) << 24) & 0x00000000ff000000l) +
						((((long)this.buff[index + 2]) << 16) & 0x0000000000ff0000l) +
						((((long)this.buff[index + 1]) << 8) & 0x000000000000ff00l)  + 
						(((long)this.buff[index]) & 0x00000000000000ffl)
					);
			}
			return val;
		} else {
			throw new caliException("buffer.getLong(): Index out of bounds.");
		}
	}
	
	public float _getFloat(int index, byteOrder bo) throws caliException {
		if((index >= 0)&&(index < buff.length + 3)) {
			float val = 0;
			if(bo == byteOrder.BIG) {
				val = ByteBuffer.wrap(this.buff, index, 4).order(ByteOrder.BIG_ENDIAN).getFloat();
			} else {
				val = ByteBuffer.wrap(this.buff, index, 4).order(ByteOrder.LITTLE_ENDIAN).getFloat();
			}
			return val;
		} else {
			throw new caliException("buffer.getInt(): Index out of bounds.");
		}
	}
	
	public double _getDouble(int index, byteOrder bo) throws caliException {
		if((index >= 0)&&(index < buff.length + 7)) {
			double val = 0;
			if(bo == byteOrder.BIG) {
				val = ByteBuffer.wrap(this.buff, index, 8).order(ByteOrder.BIG_ENDIAN).getDouble();
			} else {
				val = ByteBuffer.wrap(this.buff, index, 8).order(ByteOrder.LITTLE_ENDIAN).getDouble();
			}
			return val;
		} else {
			throw new caliException("buffer.getDouble(): Index out of bounds.");
		}
	}
	
	public int _setString(String str, String charSet) throws caliException {
		synchronized(this) {
			String cset = charSet.toUpperCase().replace("_", "-");
			try {
				this.buff = str.getBytes(cset);
				return this.buff.length;
			} catch (UnsupportedEncodingException e) {
				throw new caliException("buffer._setString(): Unsupported encoding exception. " + e.getMessage());
			}
		}
	}
	
	public int _setStringAt(int index, String str, String charSet) throws caliException {
		synchronized(this) {
			String cset = charSet.toUpperCase().replace("_", "-");
			int len = str.getBytes(Charset.forName(cset)).length;
			if((index + len) <= this.buff.length) {
				System.arraycopy(str.getBytes(Charset.forName(cset)), 0, this.buff, index, len);
				return str.getBytes(Charset.forName(cset)).length;
			} else {
				throw new caliException("buffer.setStringAt(): Index " + (index + len) + "/" + this.buff.length + " out of bounds.");
			}
		}
	}
	
	public void setByte(int index, int ival) throws caliException {
		synchronized(this) {
			if((index >= 0)&&(index < this.buff.length)) {
				this.buff[index] = (byte)ival;
			} else {
				throw new caliException("buffer.setByte(): Index out of bounds.");
			}
		}
	}
	
	public void setUByte(int index, int ival) throws caliException {
		synchronized(this) {
			if((index >= 0)&&(index < this.buff.length)) {
				this.buff[index] = (byte)(ival & (0xff));
			} else {
				throw new caliException("buffer.setUByte(): Index out of bounds.");
			}
		}
	}
	
	public void setShort(int index, int ival, byteOrder bo) throws caliException {
		synchronized(this) {
			if((index >= 0)&&(index < this.buff.length + 1)) {
				if(bo == byteOrder.BIG) {
					this.buff[index + 1] = (byte)(ival & 0xff);
					this.buff[index] = (byte)((ival >> 8) & 0xff);
				} else {
					this.buff[index] = (byte)(ival & 0xff);
					this.buff[index + 1] = (byte)((ival >> 8) & 0xff);
				}
			} else {
				throw new caliException("buffer.setShort(): Index out of bounds.");
			}
		}
	}
	
	public void setUShort(int index, int ival, byteOrder bo) throws caliException {
		synchronized(this) {
			if((index >= 0)&&(index < this.buff.length + 1)) {
				if(bo == byteOrder.BIG) {
					this.buff[index + 1] = (byte)(ival & 0xff);
					this.buff[index] = (byte)((ival >> 8) & 0xff);
				} else {
					this.buff[index] = (byte)(ival & 0xff);
					this.buff[index + 1] = (byte)((ival >> 8) & 0xff);
				}
			} else {
				throw new caliException("buffer.setUShort(): Index out of bounds.");
			}
		}
	}
	
	public void setInt(int index, int ival, byteOrder bo) throws caliException {
		synchronized(this) {
			if((index >= 0)&&(index < this.buff.length + 3)) {
				if(bo == byteOrder.BIG) {
					this.buff[index + 3] = (byte)(ival & 0xff);
					this.buff[index + 2] = (byte)((ival >> 8) & 0xff);
					this.buff[index + 1] = (byte)((ival >> 16) & 0xff);
					this.buff[index] = (byte)((ival >> 24) & 0xff);
				} else {
					this.buff[index] = (byte)(ival & 0xff);
					this.buff[index + 1] = (byte)((ival >> 8) & 0xff);
					this.buff[index + 2] = (byte)((ival >> 16) & 0xff);
					this.buff[index + 3] = (byte)((ival >> 24) & 0xff);
				}
			} else {
				throw new caliException("buffer.setInt(): Index out of bounds.");
			}
		}
	}
	
	public void setUInt(int index, long ival, byteOrder bo) throws caliException {
		synchronized(this) {
			if((index >= 0)&&(index < this.buff.length + 3)) {
				if(bo == byteOrder.BIG) {
					this.buff[index + 3] = (byte)(ival & 0xff);
					this.buff[index + 2] = (byte)((ival >> 8) & 0xff);
					this.buff[index + 1] = (byte)((ival >> 16) & 0xff);
					this.buff[index] = (byte)((ival >> 24) & 0xff);
				} else {
					this.buff[index] = (byte)(ival & 0xff);
					this.buff[index + 1] = (byte)((ival >> 8) & 0xff);
					this.buff[index + 2] = (byte)((ival >> 16) & 0xff);
					this.buff[index + 3] = (byte)((ival >> 24) & 0xff);
				}
			} else {
				throw new caliException("buffer.setUInt(): Index out of bounds.");
			}
		}
	}
	
	public void setLong(int index, long ival, byteOrder bo) throws caliException {
		synchronized(this) {
			if((index >= 0)&&(index < this.buff.length + 7)) {
				if(bo == byteOrder.BIG) {
					this.buff[index + 7] = (byte)(ival & 0xff);
					this.buff[index + 6] = (byte)((ival >> 8) & 0xff);
					this.buff[index + 5] = (byte)((ival >> 16) & 0xff);
					this.buff[index + 4] = (byte)((ival >> 24) & 0xff);
					this.buff[index + 3] = (byte)((ival >> 32) & 0xff);
					this.buff[index + 2] = (byte)((ival >> 40) & 0xff);
					this.buff[index + 1] = (byte)((ival >> 48) & 0xff);
					this.buff[index] = (byte)((ival >> 56) & 0xff);
				} else {
					this.buff[index] = (byte)(ival & 0xff);
					this.buff[index + 1] = (byte)((ival >> 8) & 0xff);
					this.buff[index + 2] = (byte)((ival >> 16) & 0xff);
					this.buff[index + 3] = (byte)((ival >> 24) & 0xff);
					this.buff[index + 4] = (byte)((ival >> 32) & 0xff);
					this.buff[index + 5] = (byte)((ival >> 40) & 0xff);
					this.buff[index + 6] = (byte)((ival >> 48) & 0xff);
					this.buff[index + 7] = (byte)((ival >> 56) & 0xff);
				}
			} else {
				throw new caliException("buffer.setLong(): Index out of bounds.");
			}
		}
	}
	
	public void setFloat(int index, double tval, byteOrder bo) throws caliException {
		synchronized(this) {
			int ival = Float.floatToIntBits((float) tval);	
			if((index >= 0)&&(index < this.buff.length + 3)) {
				if(bo == byteOrder.BIG) {
					this.buff[index + 3] = (byte)(ival & 0xff);
					this.buff[index + 2] = (byte)((ival >> 8) & 0xff);
					this.buff[index + 1] = (byte)((ival >> 16) & 0xff);
					this.buff[index] = (byte)((ival >> 24) & 0xff);
				} else {
					this.buff[index] = (byte)(ival & 0xff);
					this.buff[index + 1] = (byte)((ival >> 8) & 0xff);
					this.buff[index + 2] = (byte)((ival >> 16) & 0xff);
					this.buff[index + 3] = (byte)((ival >> 24) & 0xff);
				}
			} else {
				throw new caliException("buffer.setFloat(): Index out of bounds.");
			}
		}
	}
	
	public void setDouble(int index, double tval, byteOrder bo) throws caliException {
		synchronized(this) {
			long ival = Double.doubleToRawLongBits(tval);	
			if((index >= 0)&&(index < this.buff.length + 7)) {
				if(bo == byteOrder.BIG) {
					this.buff[index + 7] = (byte)(ival & 0xff);
					this.buff[index + 6] = (byte)((ival >> 8) & 0xff);
					this.buff[index + 5] = (byte)((ival >> 16) & 0xff);
					this.buff[index + 4] = (byte)((ival >> 24) & 0xff);
					this.buff[index + 3] = (byte)((ival >> 32) & 0xff);
					this.buff[index + 2] = (byte)((ival >> 40) & 0xff);
					this.buff[index + 1] = (byte)((ival >> 48) & 0xff);
					this.buff[index] = (byte)((ival >> 56) & 0xff);
				} else {
					this.buff[index] = (byte)(ival & 0xff);
					this.buff[index + 1] = (byte)((ival >> 8) & 0xff);
					this.buff[index + 2] = (byte)((ival >> 16) & 0xff);
					this.buff[index + 3] = (byte)((ival >> 24) & 0xff);
					this.buff[index + 4] = (byte)((ival >> 32) & 0xff);
					this.buff[index + 5] = (byte)((ival >> 40) & 0xff);
					this.buff[index + 6] = (byte)((ival >> 48) & 0xff);
					this.buff[index + 7] = (byte)((ival >> 56) & 0xff);
				}
			} else {
				throw new caliException("buffer.setDouble(): Index out of bounds.");
			}
		}
	}
}
