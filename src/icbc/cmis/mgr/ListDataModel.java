package icbc.cmis.mgr;

//import com.sun.java.swing.*;
//import com.sun.java.swing.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.util.*;


/**
 * 
 *  @(#)ListDataModel.java	1.0 10/24/99
 *  Copyright (c) 1999 Nantian R&D. All Rights Reserved.
 * 
 * 
 *   @version 1.0 10/24/99
 *   @author  ZhongMingChang
 *  
 */

 
public class ListDataModel extends AbstractListModel {
	private Vector dataList = new Vector(100);
	private Vector listDataListeners = new Vector(10);
	private boolean isSort = false;
/**
 * ListDataModel constructor comment.
 */
public ListDataModel() {
	super();
}
/**
 * ListDataModel constructor comment.
 */
public ListDataModel(String[] data) {
	super();
	for(int i=0; i< data.length; i++)
	{
		dataList.addElement( data[i] );
	}
}
/**
 * ListDataModel constructor comment.
 */
public ListDataModel(boolean isSort) {
	super();
	this.isSort = isSort;
}
/**
 * ListDataModel constructor comment.
 */
public ListDataModel(boolean isSort, String[] items)
{
	super();
	this.isSort = isSort;
	if (isSort)
	{
		icbc.cmis.base.QSortAlgorithm qSort = new icbc.cmis.base.QSortAlgorithm();
		try{
			qSort.sort(items);
		}catch(Exception e)
		{
		}
	}
	for (int i = 0; i < items.length; i++)
	{
		addItem(items[i]);
	}
}
/**
 * This method was created by ZhongMingChang.
 */
public void addItem(String items[])
{

	/*
	int len = dataList.size() - 1;
	if( len < 0 )
	len = 0;
	for(int i=0; i< items.length; i++)
	{
	dataList.addElement( items[i] );
	}	
	fireIntervalAdded(this, len, dataList.size()-1);
	*/
	try
	{
		if (isSort)//sort the items
		{
			icbc.cmis.base.QSortAlgorithm qSort = new icbc.cmis.base.QSortAlgorithm();
			qSort.sort(items);
		}
		for (int i = 0; i < items.length; i++)
		{
			addItem(items[i]);
		}
	}
	catch (Exception e)
	{
	}
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param item java.lang.Object
 */
public void addItem(Object item)
{
	if (item instanceof String)
		addItem((String) item);
	else
	{
		String itemStr = item.toString();
		if (isSort)
		{
			String tmp = itemStr.toLowerCase();
			int i = 0;
			for (i = 0; i < dataList.size(); i++)
			{
				String aItem = dataList.elementAt(i).toString();
				if (tmp.compareTo(aItem.toLowerCase()) <= 0)
					break;
			}
			insertItem(item, i);
		}
		else
		{
			dataList.addElement(item);
			fireIntervalAdded(this, dataList.size() - 1, dataList.size() - 1);
		}
	}
}
/**
 * This method was created by ZhongMingChang.
 */
public void addItem(String item) {

	
	if( isSort )
	{
		String tmp = item.toLowerCase();
		int i=0;
		for( i=0; i<dataList.size(); i++)
		{
			String aItem = (String)dataList.elementAt(i);
			if( tmp.compareTo(aItem.toLowerCase()) <= 0)
				break;
		}

		insertItem( item, i );
	}
	else
	{
		dataList.addElement( item );
		fireIntervalAdded(this, dataList.size()-1, dataList.size()-1);
	}
}
/**
 * This method was created by ZhongMingChang.
 */
public void addListDataListener(ListDataListener l)
{
		listDataListeners.addElement(l);
}
/**
 * This method was created by ZhongMingChang.
 */
public void clearAllItem() {

	fireIntervalRemoved(this, 0, dataList.size()-1);	
	dataList.removeAllElements();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return int
 * @param name java.lang.String
 */
public int findItem(String name) {
	for( int i=0; i<this.dataList.size(); i++)
	{
		String item = (String)dataList.elementAt(i);
		if( item.regionMatches(true, 0, name, 0, name.length()) )
			return i;
	}
	return -1;
}
/**
 * This method was created by ZhongMingChang.
 */
protected void fireContentsChanged(Object source, int index0, int index1)
{
	ListDataEvent e = null;
	for (int i = listDataListeners.size()-1; i >= 0; i -= 1)
	{
		if (e == null)
		{
			e = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index0, index1);
		}
		((ListDataListener) listDataListeners.elementAt(i)).contentsChanged(e);
	}
}
/**
 * This method was created by ZhongMingChang.
 */
protected void fireIntervalAdded(Object source, int index0, int index1)
{
	ListDataEvent e = null;
	for (int i = listDataListeners.size()-1; i >= 0; i -= 1)
	{
		if (e == null)
		{
			e = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index0, index1);
		}
		((ListDataListener) listDataListeners.elementAt(i)).intervalAdded(e);
	}
}
/**
 * This method was created by ZhongMingChang.
 */
protected void fireIntervalRemoved(Object source, int index0, int index1)
{
	ListDataEvent e = null;
	for (int i = listDataListeners.size()-1; i >= 0; i -= 1)
	{
		if (e == null)
		{
			e = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index0, index1);
		}
		((ListDataListener) listDataListeners.elementAt(i)).intervalRemoved(e);
	}
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  02/12/2000
 *  
 * 
 * @return java.lang.String[]
 */
public String[] getAllItems() {
	String[] elements = new String[dataList.size()];
	for( int i=0; i< dataList.size(); i++)
		elements[i] = (String)dataList.elementAt(i);
	return elements;
}
/**
 * getElementAt method comment.
 */
public Object getElementAt(int arg1) {
	try{
		return dataList.elementAt(arg1);
	}catch(Exception e)
	{
		System.out.println(e);
		return null;
	}
}
/**
 * getSize method comment.
 */
public int getSize() {
	return dataList.size();
}
/**
 * This method was created by ZhongMingChang.
 */
public void insertItem(Object item, int idx)
{
	try
	{
		dataList.insertElementAt(item, idx);
		fireIntervalAdded(this, idx, idx + 1);
	} catch (Exception e)
	{
		dataList.addElement(item);
	}
}
/**
 * This method was created by ZhongMingChang.
 */
public void insertItem(String item, int idx)
{
	try
	{
		dataList.insertElementAt(item, idx);
		fireIntervalAdded(this, idx, idx);
	} catch (Exception e)
	{
		dataList.addElement(item);
	}
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 */
public void removeAllItem() {
	try
	{
		//fireIntervalRemoved(this, 0, dataList.size()-1);
		fireIntervalRemoved(this,0,dataList.size());
		dataList.removeAllElements();
	} catch (Exception e)
	{
	}
	
}
/**
 * This method was created by ZhongMingChang.
 */
public void removeItem(int idx)
{
	try
	{
		fireIntervalRemoved(this, idx, idx);
		dataList.removeElementAt(idx );
	} catch (Exception e)
	{
	}
}
/**
 * This method was created by ZhongMingChang.
 */
public void removeItem(String item) {
	int idx = dataList.indexOf( item);
	fireIntervalRemoved(this, idx, idx);
	dataList.removeElement( item );
}
/**
 * This method was created by ZhongMingChang.
 */
public void removeListDataListener(ListDataListener l)
{
	listDataListeners.removeElement(l);
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param isSort boolean
 */
public void setSort( boolean isSort ) {
	this.isSort = isSort;
}
}
