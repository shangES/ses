package icbc.cmis.base;

/**
 * 
 *   @(#) *.java	1.0 05/13/2000
 *   Copyright (c) 2000 ZhongMC. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/13/2000
 *   @author  ZhongMingChang
 *   
 */

import java.util.*;

public class IndexedDataCollection extends DataCollection
{
	Vector elements = new Vector();
	
/**
 * IndexedDataCollection constructor comment.
 */
public IndexedDataCollection() {
	super();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param element com.zmc.chanel.base.DataElement
 */
public void addElement(DataElement element)
{
	elements.addElement(element);
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param idx int
 */
public DataElement getElement(int idx)throws CTEInvalidRequestException
{
	try{
		return (DataElement)elements.elementAt(idx);
	}catch(Exception e)
	{
		//return null;
		throw new CTEInvalidRequestException("CTEInvalidRequestException",
			"IndexedDataCollection.getElement(int idx)",
			e.getMessage());
	}
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 */
public int getSize()
{
	return elements.size();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.String
 */
public String toString() {

	String tmp = "";
	for( int i=0; i<tabCount; i++)
		tmp = tmp + "\t";

	tmp = tmp +"IndexedDataCollection ID= " + this.getName() + "\n";
	tabCount++;
	
	for( int i = 0; i<this.elements.size(); i++)
	{
		DataElement element = (DataElement)elements.elementAt( i );
		tmp = tmp + element.toString();
	}
	tabCount--;
	return tmp;
}

/**
 * Insert the method's description here.
 * Creation date: (2002-2-4 14:28:45)
 * @param index int
 * @param dataElement icbc.cmis.base.DataElement
 */
public void insertElement(int index, DataElement dataElement) throws CTEInvalidRequestException{
	int idx = elements.size();
	if(index >= idx || index < 0){
		throw new CTEInvalidRequestException("CTEInvalidRequestException in IndexedDataCollection "+getName()+" at "+idx,
			"IndexedDataCollection.insertElement(int index, DataElement dataElement)",
			"no element found");
			
	}
	elements.insertElementAt(dataElement,index);
}

/**
 * Insert the method's description here.
 * Creation date: (2002-1-25 16:49:08)
 * @param idx int
 */
public void removeElement(int idx) throws CTEObjectNotFoundException{
	try{
		elements.removeElementAt(idx);
	}catch(Exception e){
		throw new CTEObjectNotFoundException("CTEObjectNotFoundException",
			"IndexedDataCollection.removeElement(int idx)",
			e.getMessage());
	}
}

/**
 * Insert the method's description here.
 * Creation date: (2002-2-4 14:17:01)
 * @param idx int
 */
public void replaceElement(int index,DataElement dataElement) throws CTEObjectNotFoundException {
	int idx = elements.size();
	if(index >= idx || index < 0){
		throw new CTEObjectNotFoundException("CTEObjectNotFoundException in IndexedDataCollection "+getName()+" at "+idx,
			"IndexedDataCollection.updateElement(int index,DataElement dataElement)",
			"no element found");
			
	}
	elements.setElementAt(dataElement,index);
}
}
