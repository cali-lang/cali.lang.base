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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.cali.types.CaliNull;
import com.cali.types.CaliString;
import com.cali.types.CaliType;

import com.cali.types.CaliBool;
import com.cali.types.CaliException;
import com.cali.types.CaliInt;

public class CDate extends Date {
	private static final long serialVersionUID = 1579993228939943395L;
	
	public CDate() { }
	
	public static LocalDate dateToLocalDate(Date Dt) {
		return Dt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static Date localDateToDate(LocalDate Ld) {
		return new Date(Ld.toEpochDay());
	}
	
	public CaliType newDate(ArrayList<CaliType> args) {
		if(!args.get(0).isNull()) {
			this.setTime(((CaliInt)args.get(0)).getValue());
		}
		return new CaliNull();
	}
	
	@SuppressWarnings("deprecation")
	public CaliType getHours(ArrayList<CaliType> args) {
		return new CaliInt(this.getHours());
	}
	
	@SuppressWarnings("deprecation")
	public CaliType getMinutes(ArrayList<CaliType> args) {
		return new CaliInt(this.getMinutes());
	}
	
	@SuppressWarnings("deprecation")
	public CaliType getSeconds(ArrayList<CaliType> args) {
		return new CaliInt(this.getSeconds());
	}
	
	public CaliType getTime(ArrayList<CaliType> args) {
		return new CaliInt(this.getTime());
	}
	
	@SuppressWarnings("deprecation")
	public CaliType _setHours(ArrayList<CaliType> args) {
		this.setHours((int)((CaliInt)args.get(0)).getValue());
		return new CaliNull();
	}
	
	@SuppressWarnings("deprecation")
	public CaliType _setMinutes(ArrayList<CaliType> args) {
		this.setMinutes((int)((CaliInt)args.get(0)).getValue());
		return new CaliNull();
	}
	
	@SuppressWarnings("deprecation")
	public CaliType _setSeconds(ArrayList<CaliType> args) {
		this.setSeconds((int)((CaliInt)args.get(0)).getValue());
		return new CaliNull();
	}
	
	public CaliType _setTime(ArrayList<CaliType> args) {
		this.setTime(((CaliInt)args.get(0)).getValue());
		return new CaliNull();
	}
	
	public CaliType toString(ArrayList<CaliType> args) {
		return new CaliString(this.toString());
	}
	
	public CaliType parse(ArrayList<CaliType> args) {
		SimpleDateFormat sdf = new SimpleDateFormat(((CaliString)args.get(1)).getValueString());
		try {
			Date td = sdf.parse(((CaliString)args.get(0)).getValueString());
			this.setTime(td.getTime());
			return new CaliNull();
		} catch (ParseException e) {
			return new CaliException("Date.parse(): Parse exception. (" + e.getMessage() + ")");
		}
	}
	
	public CaliType format(ArrayList<CaliType> args) {
		SimpleDateFormat sdf = new SimpleDateFormat(((CaliString)args.get(0)).getValueString());
		return new CaliString(sdf.format(this));
	}

	public CaliType isEpoch(ArrayList<CaliType> args) {
		if(this.getTime() == 0) {
			return new CaliBool(true);
		}
		return new CaliBool(false);
	}
	
	/*
	 * Helper functions
	 */
	public static Date addDays(Date dt, int numDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, numDays);
		return c.getTime();
	}
}