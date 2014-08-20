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
 
public class KeyedDataCollection extends DataCollection
{
	Hashtable elements = new Hashtable();			//all data elements
	Hashtable refCollections = new Hashtable();		//all reffto elements

	Object[] elementNames = new Object[50];
	int elementCount = 0;
	int bufSize = 50;
/**
 * KeyDataCollection constructor comment.
 */
public KeyedDataCollection() {
	super();
}
/**
 * KeyDataCollection constructor comment.
 */
public KeyedDataCollection(String name) {
	super(name);
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param element com.zmc.chanel.base.DataElement
 */
public void addElement(DataElement element)throws CTEInvalidRequestException,CTEObjectExistingException
{
	if(element == null || element.getName()== null || ((String)element.getName().trim()).length() == 0){
		throw new CTEInvalidRequestException("CTEInvalidRequestException",
			"KeyedDataCollection.addElement(DataElement element)",
			"the DataElement is invalid!");
	}
	
	String name = element.getName();
	
	for(int i = 0;i<elementNames.length;i++){
		
		String tmpDataName = (String)elementNames[i];
		if(tmpDataName != null && tmpDataName.trim().equals(name.trim())){
			
			throw new CTEObjectExistingException("CTEObjectExistingException named "+name,
				"KeyedDataCollection.addElement(DataElement element)",
				"keyedDataCollection "+element.getName()+" aready existed");

		}
		
	}
	
	if( element instanceof DataCollection )
		refCollections.put(element.getName(), element);
	else
		elements.put( element.getName(), element);

	addElementName( element.getName().trim());
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param element com.zmc.chanel.base.DataElement
 */
public void addElement(String dataName,Object dataValue)throws CTEInvalidRequestException,CTEObjectExistingException
{
	if(dataName == null || (dataName.trim()).length() == 0){
		throw new CTEInvalidRequestException("CTEInvalidRequestException",
			"KeyedDataCollection.addElement(String dataName,Object dataValue)",
			"the DataElement is invalid!");
	}
	
	dataName = dataName.trim();
	int idx = dataName.indexOf(".");
	String colName = null;
	String subName = null;
	if(idx == -1) {
		colName = dataName;
	}else{
		if(idx == 0 || idx == dataName.length() -1){
				throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"KeyedDataCollection.addElement(String dataName,Object dataValue)",
					"invalid name: "+dataName);
		}
		colName = dataName.substring(0,idx);
		subName = dataName.substring(idx+1,dataName.length());
	}
	if(idx == -1){
	/*	DataElement element = (DataElement)elements.get(colName);
		if(element == null)
			element = (DataElement)refCollections.get(colName);
		if(element != null){
			throw new CTEObjectExistingException("CTEObjectExistingException",
				"KeyedDataCollection.addElement(String dataName,Object dataValue)",
				"keyedDataCollection "+dataName+" aready existed");

		}
		element = new DataElement();
		element.setName(colName);
		element.setValue(dataValue);
		elements.put(colName,element);
		addElementName( colName);
		*/
		DataElement element = new DataElement();
		element.setName(colName);
		element.setValue(dataValue);
		addElement(element);
		return;
		
	}else{
		DataElement element = (DataElement)elements.get(colName);
		if(element != null){
			throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"KeyedDataCollection.addElement(String dataName,Object dataValue)",
					"invalid name: "+dataName);
		}
		
		DataCollection kColl = (DataCollection)refCollections.get(colName);
		if(kColl == null){
			KeyedDataCollection tmpI = new KeyedDataCollection();
			tmpI.setName(colName);
			addElement(tmpI);
			kColl = tmpI;
		}
		
		if(kColl instanceof IndexedDataCollection){
			throw new CTEInvalidRequestException("CTEInvalidRequestException",
			"KeyedDataCollection.addElement(String dataName,Object dataValue)",
			"the DataElement is invalid!");
		}
		KeyedDataCollection k = (KeyedDataCollection)kColl;
		k.addElement(subName,dataValue);
		return;
		
	}
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param name java.lang.String
 */
private void addElementName(String name)throws CTEInvalidRequestException
{
	if(name == null || name.trim().length() == 0){
		throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"KeyedDataCollection.addElementName(String name)",
					"invalid name;dataName invalid");
	}
	
	elementNames[this.elementCount] = name.trim();
	elementCount++;
	if( elementCount >= bufSize)
	{
		Object[] buf = new Object[bufSize + 50];
		System.arraycopy(elementNames, 0, buf, 0, elementCount);
		elementNames = buf;
		bufSize=bufSize+50;
	}
	
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return com.zmc.chanel.base.DataElement
 * @param name java.lang.String
 */
public DataElement getElement(String name)throws CTEUncausedException,CTEInvalidRequestException,CTEObjectNotFoundException
{
	if(name == null || name.trim().length() == 0){
		throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"KeyedDataCollection.getElement(String name)",
					"invalid name;dataName invalid");
	}
	name = name.trim();
	int idx = name.indexOf(".");
	String dataName = null;
	String subName = null;
	if(idx == -1){
		dataName = name;
	}else{
		dataName = name.substring(0,idx);
		subName = name.substring(idx+1,name.length());
	}
	
	if(!isElementExist(dataName)){
		throw new CTEObjectNotFoundException("CTEObjectNotFoundException named "+name,
		"KeyedDataCollection.getElement(String name)",
		"KeyedDataCollection[ " + this.getName() + " ] doesn't have a dataElement named: " + name);
	}
	if(idx == -1){
		DataElement element = (DataElement) elements.get(name);
		if (element != null)
			return element;

		element = (DataElement)this.refCollections.get(name);

		if( element != null)
			return element;
	}else{
		KeyedDataCollection kColl = (KeyedDataCollection)this.refCollections.get(dataName);
		if(kColl == null){
			throw new CTEUncausedException("CTEUncausedException",
					"KeyedDataCollection.getElement(String name)",
					"uncaused exception in KeyedDataCollection");
		}
		return kColl.getElement(subName);
	}

	throw new CTEObjectNotFoundException("CTEObjectNotFoundException named "+name,
		"KeyedDataCollection.getElement(String name)",
		"KeyedDataCollection[ " + this.getName() + " ] doesn't have a dataElement named: " + name);
		
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.Object
 * @param name java.lang.String
 */
public Object getValueAt(String name)throws CTEObjectNotFoundException,CTEInvalidRequestException
{
	if(name == null || name.trim().length() == 0){
		throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"KeyedDataCollection.getValueAt(String name)",
					"invalid name;dataName invalid");
	}
	
	name = name.trim();

	try{
		int idx = name.indexOf(".");
		if( idx == -1)
		{
			if(!isElementExist(name)){
				throw new CTEObjectNotFoundException("CTEObjectNotFoundException named "+name,
				"KeyedDataCollection.getValueAt(String name)",
				"KeyedDataCollection: " + this.getName() + "does not have a data named: " + name);

			}
			DataElement dataElement = (DataElement)elements.get(name);
			if(dataElement != null){
				return dataElement.getValue( );
			}else{
				throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"KeyedDataCollection.getValueAt(String name)",
					"invalid name:"+name);
			}
			
		}
		else
		{
			if(idx == 0 || idx == name.length() -1){
				throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"KeyedDataCollection.getValueAt(String name)",
					"invalid name:"+name);
			}
			
			String collName = name.substring(0, idx );
			if(!isElementExist(collName)){
				throw new CTEObjectNotFoundException("CTEObjectNotFoundException named "+collName,
				"KeyedDataCollection.getValueAt(String name)",
				"KeyedDataCollection: " + this.getName() + "does not have a data named: " + collName);

			}
			String dataName = name.substring(idx+1, name.length());
			DataCollection aColl = (DataCollection)refCollections.get(collName);
			if(aColl != null){
				return aColl.getValueAt(dataName);
			}else{
				throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"KeyedDataCollection.getValueAt(String name)",
					"invalid name:"+collName);
			}
		}

	}catch(Exception e)
	{
	//	System.out.println("KeyedDataCollection: " + this.getName() + "does not have a data named: " + name );
	//	return null;
	}
	throw new CTEObjectNotFoundException("CTEObjectNotFoundException named "+name,
		"KeyedDataCollection.getValueAt(String name)",
		"KeyedDataCollection: " + this.getName() + "does not have a data named: " + name);
	
	
	
}

public String getConvertString(String name)throws CTEObjectNotFoundException,CTEInvalidRequestException
{

	try{
		String retStr = (String)this.getValueAt(name);
		if (retStr == null)
			return "";
		while(retStr.endsWith("\r\n")){
			retStr=retStr.substring(0,retStr.length()-2);
		}
		icbc.cmis.util.StringBufferR temp = new icbc.cmis.util.StringBufferR(retStr);
	    temp.replace("\r\n","\\r\\n");
	    temp.replace("\'","’");
	    temp.replace("\"","“"); 
	    return temp.toString();
	}catch(CTEObjectNotFoundException e)
	{
		throw e;
	//	System.out.println("KeyedDataCollection: " + this.getName() + "does not have a data named: " + name );
	//	return null;
	}
	catch(CTEInvalidRequestException e)
	{
		throw e;
	//	System.out.println("KeyedDataCollection: " + this.getName() + "does not have a data named: " + name );
	//	return null;
	}	
	catch (Exception e){
	throw new CTEObjectNotFoundException("CTEObjectNotFoundException named "+name,
		"KeyedDataCollection.getValueAt(String name)",
		"KeyedDataCollection: " + this.getName() + "does not have a data named: " + name);
		
		
	}
	
}




/**
 * Insert the method's description here.
 * Creation date: (2002-1-6 21:20:42)
 * @param dataName java.lang.String
 */
public void removeElement(String dataName) throws CTEUncausedException, CTEInvalidRequestException,CTEObjectNotFoundException{

	if(dataName == null || dataName.trim().length() == 0){
		throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"KeyedDataCollection.removeElement(String dataName)",
					"invalid dataName;dataName invalid");
	}
	dataName = dataName.trim();
	int idx = dataName.indexOf(".");
	boolean isExist = false;
	String name = null;
	String subName = null;
	if(idx == -1){
		name = dataName;
	}else {
		if(idx == 0 || idx == dataName.length() -1){
				throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"KeyedDataCollection.removeElement(String dataName)",
					"invalid name: "+dataName);
		}
		
		name = dataName.substring(0,idx);
		subName = dataName.substring(idx+1,dataName.length());
	}

	
	if(!isElementExist(name)){
		throw new CTEObjectNotFoundException("CTEObjectNotFoundException named " +dataName,
					"KeyedDataCollection.removeElement(String dataName)",
					"DataElement named "+name+" is not exist");
		
	}
	
	if(idx == -1){
		if(elements.containsKey(dataName)){
			elements.remove(dataName);
			removeElementName(dataName);
			elementCount--;
			return;
		}else if(refCollections.containsKey(dataName)){
			refCollections.remove(dataName);
			removeElementName(dataName);
			elementCount--;
			return;
		}else{
			throw new CTEUncausedException("CTEUncausedException",
					"KeyedDataCollection.removeElement(String dataName)",
					"uncaused exception in KeyedDataCollection");
		}
		
	}else{
		KeyedDataCollection kColl = (KeyedDataCollection)refCollections.get(name);
		if(kColl == null){
			throw new CTEUncausedException("CTEUncausedException",
					"KeyedDataCollection.removeElement(String dataName)",
					"uncaused exception in KeyedDataCollection");
		}
		kColl.removeElement(subName);
		return;
		
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-6 21:41:59)
 * @param name java.lang.String
 */
private void removeElementName(String name) throws CTEObjectNotFoundException,CTEInvalidRequestException{
	if(name == null || name.trim().length() == 0){
		throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"KeyedDataCollection.removeElementName(String name)",
					"invalid name;dataName invalid");
	}
	boolean isFound = false;
	Object[] tmp = new Object[bufSize];
	int j = 0;
	for(int i = 0;i<elementNames.length;i++){
		if(!isFound && elementNames[i]!=null && ((String)elementNames[i]).trim().equals(name.trim())){
			isFound = true;
			continue;
			
		}
		tmp[j] = elementNames[i];
		j++;
		
	}
	if(!isFound){
		throw new CTEObjectNotFoundException("CTEObjectNotFoundException "+name,
					"KeyedDataCollection.removeElementName(String name)",
					"DataElement named "+name+" not found");
	}
	elementNames = tmp;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param name java.lang.String
 * @param value java.lang.Object


 you can set the collection value as aColl.bColl.Name, value
 
 */
public void setValueAt(String name, Object value)throws CTEInvalidRequestException, CTEObjectNotFoundException
{
	try{
		name = name.trim();
		int idx = name.indexOf(".");
		if( idx == -1)
		{
			if(!isElementExist(name)){
				throw new CTEObjectNotFoundException("CTEObjectNotFoundException named "+name,
				"KeyedDataCollection.setValueAt(String name, Object value)",
				"KeyedDataCollection: " + this.getName() + "does not have a data named: " + name);
			}
			DataElement dataElement = (DataElement)elements.get(name);
			if(dataElement != null){
				dataElement.setValue(value);
				return;
				
			}else{
				throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"setValueAt(String name, Object value)",
					"invalid name: "+name);
			}
		}
		
		else
		{
			if(idx == 0 || idx == name.length() -1){
				throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"setValueAt(String name, Object value)",
					"invalid name: "+name);
			}
			
			String collName = name.substring(0, idx );
			if(!isElementExist(collName)){
				throw new CTEObjectNotFoundException("CTEObjectNotFoundException named "+name,
				"KeyedDataCollection.setValueAt(String name, Object value)",
				"KeyedDataCollection: " + this.getName() + "does not have a data named: " + name);
			}
			String dataName = name.substring(idx+1, name.length());
			DataCollection aColl = (DataCollection)refCollections.get(collName);
			if(aColl != null){
				aColl.setValueAt(dataName, value);
				return;
			}else{
				throw new CTEInvalidRequestException("CTEInvalidRequestException",
					"setValueAt(String name, Object value)",
					"invalid name: "+dataName);
			}
		}

	}catch(Exception e)
	{
		//System.out.println("KeyedDataCollection: " + this.getName() + "does not have a data named: " + name );
	}
	throw new CTEObjectNotFoundException("CTEObjectNotFoundException named "+name,
		"KeyedDataCollection.setValueAt(String name, Object value)",
		"KeyedDataCollection: " + this.getName() + "does not have a data named: " + name);

	
}
public String toString()
{
	String tmp = "";
	for (int i = 0; i < tabCount; i++)
		tmp = tmp + "\t";
	tmp = tmp + "KeyedDataCollection ID= " + this.getName() + "\n";
	tabCount++;
	for (int i = 0; i < this.elementCount; i++)
	{
		DataElement element = null;
		try
		{
			element = (DataElement) elements.get(elementNames[i]);
		}
		catch (Exception e)
		{
		}
		
		try
		{
			if (element == null)
				element = (DataElement) refCollections.get(elementNames[i]);
		}
		catch (Exception e)
		{
		}
		tmp = tmp + element.toString();
	}
	tabCount--;
	return tmp;
}

/**
 * Insert the method's description here.
 * Creation date: (2002-1-23 8:47:54)
 * @return java.lang.String[]
 */
public Vector getElementNames() {
	int len = elementNames.length;
	Vector v = new Vector();
	for(int i = 0;i<len;i++){
		String key = (String)elementNames[i];
		if(key != null && key.trim().length() >0)
			v.add(key);
	}
	return v;
}

/**
 * Insert the method's description here.
 * Creation date: (2002-1-23 8:55:48)
 * @return boolean
 * @param dataName java.lang.String
 */
public boolean isElementExist(String dataName) {
	if(dataName == null || dataName.trim().length() == 0) return false;
	for(int i = 0;i<elementNames.length;i++){
		String x = (String)elementNames[i];
		if(x!= null && x.trim().equals(dataName.trim())) return true;
	}
	return false;
}

////////////////////////////////////////////////////////////////////////////////////////////////
/*
 * 修改原因：为了防止CM2002与CTP之间Session数据的不同步，为KeyedDataCollection提供清空数据的方法.
 * 修改时间: 2004-08-28
 * 修改人:YangGuangRun
 * 
 */
/**
 * <b>功能描述: </b><br>
 * <p>为KeyedDataCollection提供清空数据的方法 </p>
 * 
 *  
 */
public void clear(){
	Vector namesVector=new Vector();
	for(int i=0;i<elementNames.length;i++){
		if(elementNames[i]!=null) 
			namesVector.add(elementNames[i]);
	}
	for(Iterator i=namesVector.iterator();i.hasNext();){
		try{
			String name=(String)i.next();
			if(name!=null)
				removeElement(name);
		}catch(CTEObjectNotFoundException e){
			
		}catch(CTEInvalidRequestException e){
			
		}catch(CTEUncausedException e){
			
		}
	}
}
/////////////////////////////////////////////////////////////////////////////////////////////
}
