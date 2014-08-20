package icbc.cmis.mgr;

/*
 * @(#)TableDataModel.java	1.0 10/18/99
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


// import com.sun.java.swing.event.*;

// import com.sun.java.swing.table.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

 
  public class TableDataModel extends AbstractTableModel {
	protected String[] names = null;
	protected int columns = 0;
	protected int currentRow = 0;
	private int rows = 0;
	private  java.util.Vector rowData = new java.util.Vector(100);

	private TreeData treeData;
/**
 * TableDataModel constructor comment.
 */
public TableDataModel() {
	super();
}
/**
 * TableDataModel constructor comment.
 */
public TableDataModel(String[] names) {
	super();
	this.names = names;
	columns = names.length;
}
/**
 * This method was created in VisualAge.
 */
public void addRow(String[] value) {
	try{
		rowData.addElement(value);
		rows++;

		TableModelEvent event = new TableModelEvent(this, rows-1, rows-1,
							 TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		fireTableChanged(event);
		
	}catch (Exception e)
	{
		System.out.println( "Exception in TableDataModel.addRow(String[]): " + e );
	}
	
}
/**
 * This method was created in VisualAge.
 */
public void addRow(TableData value) {
	try{
		rowData.addElement(value);
		rows++;

		TableModelEvent event = new TableModelEvent(this, rows-1, rows-1,
							 TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
		fireTableChanged(event);


	}catch (Exception e)
	{
		System.out.println( e );
	}
	
}
/**
 * This method was created in VisualAge.
 */
public void deleteRow(int idx) {
	try{
		rowData.removeElementAt(idx);
		rows--;
		
		fireTableChanged(new TableModelEvent(this, idx, idx,
							 TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE)); 

		
	}catch (Exception e)
	{
		System.out.println( e );
	}
	
}
/**
 * This method was created in VisualAge.
 */
public void deleteRow(Object o) {
	try{
		int idx = rowData.indexOf(o);
		fireTableChanged(new TableModelEvent(this, idx, idx,
							 TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE)); 

		rowData.removeElement(o);
		rows--;
	}catch (Exception e)
	{
		System.out.println( e );
	}
	
}
public Class getColumnClass(int c)
{
	return String.class;
}
/**
 * getColumnCount method comment.
 */
public int getColumnCount() {
	return columns;
}
/**
 * getColumnCount method comment.
 */
public String getColumnName(int col) {
	
	return names[col];
}
/**
 * getRowCount method comment.
 */
public int getRowCount() {
	return rows;
}
/**
 * getRowCount method comment.
 */
public Object getRowObject(int idx) {

	try{
		return rowData.elementAt( idx );
	}catch ( Exception e )
	{
		System.out.println("Exception in TableDataModel.getRowObject(int): " +  e );
		return null;
	}	
}
/**
 * This method was created in VisualAge.
 */
public TreeData getTreeData() {
	return treeData;
}
/**
 * getValueAt method comment.
 */
public Object getValueAt(int arg1, int arg2) {
	try{
		Object obj = rowData.elementAt( arg1 );
		String[] rowValue;
		if( obj instanceof TableData)
		{
			TableData td = (TableData)obj;
			rowValue = (String[] ) td.getTableData();
		}
		else
			rowValue = (String[])obj;
		return rowValue[arg2];
	}catch ( Exception e )
	{
		System.out.println("Exception in TableDataModel.getValueAt(int, int): [ "+ arg1 +", " + arg2 + " ]: "  +  e );
		return null;
	}
}
public boolean isCellEditable(int row, int col)
{
		return true;
}
/**
 * This method was created in VisualAge.
 */
void newMethod() {
}
/**
 * This method was created in VisualAge.
 */
public void removeAllRow()
{
	if (rows > 0)
		fireTableChanged(new TableModelEvent(this, 0, rows - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
	rows = 0;
	rowData.removeAllElements();
}
public void setNames(String[] names) {
	this.names = names;
	columns = names.length;
}
/**
 * This method was created in VisualAge.
 */
public void setTreeData(TreeData treeData) {
	this.treeData = treeData;
}
/**
 * This method was created in VisualAge.
 */
public void updateRow(TableData value) {
		int idx = rowData.indexOf(value);
		fireTableChanged(new TableModelEvent(this, idx, idx,
							 TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE)); 
	
}
/**
 * This method was created in VisualAge.
 */
public void updateRow(Object value) {
		int idx = rowData.indexOf(value);
		fireTableChanged(new TableModelEvent(this, idx, idx,
							 TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE)); 
	
}
}
