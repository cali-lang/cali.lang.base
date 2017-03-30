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

package com.cali.types;

import java.util.Comparator;

import com.cali.ast.caliException;

@SuppressWarnings("rawtypes")
public class CaliListComparator implements Comparator {
	public enum SortOrder { ASCENDING, DESCENDING, CUSTOM }
	
	private SortOrder order = SortOrder.DESCENDING;
	public void setSortOrder(SortOrder Order) { this.order = Order; }
	
	private CaliCallback onCompare = null;
	public void setCallback(CaliCallback OnCompare) { this.onCompare = OnCompare; }
	
	@Override
	public int compare(Object o1, Object o2) {
		Integer res = 0;
		CaliType one = (CaliType)o1;
		CaliType two = (CaliType)o2;
		
		if(this.order == SortOrder.CUSTOM) {
			try {
				return this.customCompare(one, two);
			} catch (caliException e) {
				throw new RuntimeException(e.getMessage());
			}
		} else {
			// Comparing same types
			if(one.getType() == two.getType()) {
				if(one instanceof CaliString) {
					String vone = ((CaliString)one).getValue();
					String vtwo = ((CaliString)two).getValue();
					res = vone.compareTo(vtwo);
				} else if(one instanceof CaliBool) {
					Boolean vone = ((CaliBool)one).getValue();
					Boolean vtwo = ((CaliBool)two).getValue();
					res = vone.compareTo(vtwo);
				} else if(one instanceof CaliInt) {
					Long vone = ((CaliInt)one).getValue();
					Long vtwo = ((CaliInt)two).getValue();
					res = vone.compareTo(vtwo);
				} else if(one instanceof CaliDouble) {
					Double vone = ((CaliDouble)one).getValue();
					Double vtwo = ((CaliDouble)two).getValue();
					res = vone.compareTo(vtwo);
				} else if(
						one instanceof CaliList 
						|| one instanceof CaliMap
						|| one.getType() == cType.cCallback
					) {
					String vone = ((CaliTypeInt)one).str();
					String vtwo = ((CaliTypeInt)two).str();
					res = vone.compareTo(vtwo);
				} else if(one.getType() == cType.cObject) {
					String vone = ((CaliObject)one).getClassDef().getName();
					String vtwo = ((CaliObject)two).getClassDef().getName();
					res = vone.compareTo(vtwo);
				} else if(one.isNull()) {
					res = 0;
				}
			} else if(one instanceof CaliInt && two instanceof CaliDouble) {
				Double vone = (double)((CaliInt)one).getValue();
				Double vtwo = ((CaliDouble)two).getValue();
				res = vone.compareTo(vtwo);
			} else if(one instanceof CaliDouble && two instanceof CaliInt) {
				Double vone = ((CaliDouble)one).getValue();
				Double vtwo = (double)((CaliInt)two).getValue();
				res = vone.compareTo(vtwo);
			} else if(one.isNull()) {
				res = -1;
			} else if(two.isNull()) {
				res = 1;
			} else {
				String vone = ((CaliTypeInt)one).str();
				String vtwo = ((CaliTypeInt)two).str();
				res = vone.compareTo(vtwo);
			}
			
			if(this.order == SortOrder.DESCENDING) return res;
			else return res * -1;
		}
	}
	
	private int customCompare(CaliType one, CaliType two) throws caliException
	{
		int ret = 0;
		if(this.onCompare != null) {
			CaliList args = new CaliList();
			args.add(one);
			args.add(two);
			CaliType retInt = this.onCompare.call(args);
			if(retInt instanceof CaliInt)
				ret = (int) ((CaliInt)retInt).getValue();
			else
				throw new caliException("CaliListComparator.compareCustom(): Result from callback expects type of int, but found " + retInt.getType().name() + " instead.");
		}
		else
			throw new caliException("CaliListComparator.compareCustom(): Callback OnCompare is null.");
		
		return ret;
	}
}
