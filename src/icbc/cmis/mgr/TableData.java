package icbc.cmis.mgr;

/*
 * @(#)TableData.java	1.0 10/18/99
 *
 * Copyright (c) 1999 ZhongMingChang. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of 
 * ZhongMingChang.("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 *
 * ZhongMingChang MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */

/**
 *
 * @version 1.0 10/18/99
 * @author  ZhongMingChang
 */
 
 
public class TableData {
	TreeData parent= null;
	String[] tableData = null;
	private Object objectData = null;		//the object this tableData refer to
/**
 * TableData constructor comment.
 */
public TableData() {
	super();
}
/**
 * TableData constructor comment.
 */
public TableData(Object obj, String[] data) {
	super();
	this.objectData = obj;
	this.tableData = data;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  09/08/2000
 *  
 * 
 * @return java.lang.Object
 */
public Object getObjectData() {
	return objectData;
}
/**
 * TableData constructor comment.
 */
public TreeData getParent() {
	return parent;
}
/**
 * TableData constructor comment.
 */
public String[] getTableData() {
	return tableData;
}
/**
 * TableData constructor comment.
 */
public void setParent(TreeData parent) {
	this.parent = parent;
}
/**
 * TableData constructor comment.
 */
public void setTableData(String[] tableData) {
	this.tableData = tableData;
}
}
