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
 
public class KeyedCollectionFormat extends FormatElement {

	private Vector formatElements = new Vector();
/**
 * KeyedCollectionFormat constructor comment.
 */
public KeyedCollectionFormat() {
	super();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param element com.zmc.chanel.base.FormatElement
 */
public void addFormatElement(FormatElement element)
{
	formatElements.addElement( element );
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param element com.zmc.chanel.base.KeyedCollectionFormat
 */
public void addIndexedCollectionFormat(IndexedCollectionFormat element)
{
	addFormatElement( (FormatElement)element);
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param element com.zmc.chanel.base.KeyedCollectionFormat
 */
public void addKeyedCollectionFormat(KeyedCollectionFormat element)
{
	addFormatElement( (FormatElement)element);
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param element com.zmc.chanel.base.RecordFormat
 */
 
public void addRecordFormat(RecordFormat element)
{
	addFormatElement( (FormatElement)element);

}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 8:42:12)
 * @param element com.icbc.cmis.base.XMLConstantTagFormat
 */
public void addXMLConstantTagFormat(XMLConstantTagFormat element) 
{
	addFormatElement( (FormatElement)element);	
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 8:43:44)
 * @param element com.icbc.cmis.base.XMLEndTagFormat
 */
public void addXMLEndTagFormat(XMLEndTagFormat element) 
{
	addFormatElement( (FormatElement)element);	
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 8:42:54)
 * @param element com.icbc.cmis.base.XMLFullTagFormat
 */
public void addXMLFullTagFormat(XMLFullTagFormat element) 
{
	addFormatElement( (FormatElement)element);	
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 8:41:21)
 * @param element com.icbc.cmis.base.XMLHeadTagFormat
 */
public void addXMLHeadTagFormat(XMLHeadTagFormat element) 
{
	addFormatElement( (FormatElement)element);
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 8:38:57)
 * @param element com.icbc.cmis.base.XMLTagFormat
 */
public void addXMLTagFormat(XMLTagFormat element) 
{
	addFormatElement( (FormatElement)element);		
}
	public String[] extract(String data) throws CTEInvalidRequestException
{
	
        String s1 = data;
        String s2 = new String();
        String as[] = new String[2];
        for(int i = 0;i<getFormatElements().size();i++)
        {
            as = ((FormatElement)getFormatElements().elementAt(i)).extract(s1);
            s2 = s2 + as[0];
            s1 = as[1];
        }

        as[0] = s2;
        return as;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.String
 * @param dataColl com.zmc.chanel.base.KeyedDataCollection
 */
public String format(DataElement dataElement)throws CTEUncausedException, CTEUncausedException,CTEObjectExistingException,CTEInvalidRequestException ,CTEObjectNotFoundException{


	java.io.StringWriter writer = new java.io.StringWriter();
	
	for( int i=0; i<this.formatElements.size(); i++)
	{
		FormatElement formatElement = (FormatElement)formatElements.elementAt( i );
		String dataName = formatElement.getDataName();
		DataElement element = null;
		
		if (dataName != null) {
			element = ((KeyedDataCollection)dataElement).getElement(dataName);
		}
		
		writer.write( formatElement.format(element));
	}

	String tmp = writer.toString();

	if( getDecorator() != null)
		return getDecorator().format(tmp);
	else
		return tmp;

}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.String
 * @param dataColl com.zmc.chanel.base.KeyedDataCollection
 */
public String format(KeyedDataCollection dataColl) throws CTEUncausedException,CTEObjectExistingException,CTEInvalidRequestException,CTEObjectNotFoundException{


	java.io.StringWriter writer = new java.io.StringWriter();
	
	for( int i=0; i<this.formatElements.size(); i++)
	{
		FormatElement formatElement = (FormatElement)formatElements.elementAt( i );
		String dataName = formatElement.getDataName();
		DataElement dataElement = null;
		
		if (dataName != null) {
			dataElement = dataColl.getElement(dataName);
		}
		writer.write( formatElement.format(dataElement));
	}

	String tmp = writer.toString();

	if( getDecorator() != null)
		return getDecorator().format(tmp);
	else
		return tmp;

}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.util.Vector
 */
public Vector getFormatElements() {
	return formatElements;
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

	String tmp= "";

	for( int j=0; j<tabCount; j++)
		tmp = tmp + "\t";

	if( getDataName()!=null && getDataName().length() > 1)
		tmp =tmp +  "<KeyedCollectionFormat dataName = \"" + getDataName() + "\">\n";
	else
		tmp = tmp + "<KeyedCollectionFormat>\n";
	
	tabCount++;
	
	for( int i=0; i<this.formatElements.size(); i++)
	{
		FormatElement element = (FormatElement)formatElements.elementAt(i);
		tmp = tmp + element.toString();
	}
		
	if( getDecorator()!= null)
	{
		for( int j=0; j<tabCount; j++)
			tmp = tmp + "\t";
		tmp = tmp + getDecorator().toString();
	}

	tabCount--;

	for( int j=0; j<tabCount; j++)
		tmp = tmp + "\t";
	
	tmp = tmp + "</KeyedCollectionFormat>\n";	
	
	return tmp;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param dataSource java.lang.String
 * @param dataElement com.zmc.chanel.base.DataElement
 */
public DataElement unformat(String dataSource, DataElement dataElement) throws CTEUncausedException,CTEObjectExistingException,CTEInvalidRequestException,CTEObjectNotFoundException
{
	String tmpString = dataSource;
	for( int i=0; i<this.formatElements.size(); i++)
	{
		FormatElement formatElement = (FormatElement)formatElements.elementAt( i );
		String dataName = formatElement.getDataName();

		String[] extractedData;
		
		if( (formatElement instanceof KeyedCollectionFormat))
		{
			extractedData = formatElement.extract(tmpString);
			if(dataName != null)
			{
				DataElement element = null;
				try {
					element = ((KeyedDataCollection)dataElement).getElement(dataName);
				}
				catch (CTEObjectNotFoundException e) {
					KeyedDataCollection collection = new KeyedDataCollection();
					collection.setName(dataName);
					((KeyedDataCollection)dataElement).addElement( collection );
					element = collection;
				}	
					
				formatElement.unformat(extractedData[0], element);
			}
			else
				formatElement.unformat(extractedData[0], dataElement);

			tmpString = extractedData[1];

		}
		else if( (formatElement instanceof IndexedCollectionFormat))
		{
			extractedData = formatElement.extract(tmpString);
			if(dataName != null)
			{
				DataElement element = null;
				try {
					element = ((KeyedDataCollection)dataElement).getElement(dataName);
				}
				catch (CTEObjectNotFoundException e) {
					IndexedDataCollection collection = new IndexedDataCollection();
					collection.setName(dataName);
					((KeyedDataCollection)dataElement).addElement( collection );
					element = collection;
				}
						
				formatElement.unformat(extractedData[0], element);
			}
			else
				formatElement.unformat(extractedData[0], dataElement);

			tmpString = extractedData[1];

		}		
		else /*formatElement is instanceof FormatField*/
		{
			extractedData = formatElement.extract(tmpString);
			
			tmpString = extractedData[1];
			
			if (dataName == null) {
				continue;	//if no dataName, then no unformat
			}
			else {
				DataElement element = null;
				try {
					element = ((KeyedDataCollection)dataElement).getElement(dataName);
				}
				catch (CTEObjectNotFoundException e) {
					DataElement aElement = new DataElement();
					aElement.setName(dataName);
					((KeyedDataCollection)dataElement).addElement( aElement );
					element = aElement;		
				}
				
				formatElement.unformat(extractedData[0], element);
			}
		}
	}
	return dataElement;
}
}
