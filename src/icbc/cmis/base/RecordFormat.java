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
public class RecordFormat extends KeyedCollectionFormat {
/**
 * RecordFormat constructor comment.
 */
public RecordFormat() {
	super();
}
public String toString()
{
	String tmp = "";
	String dataName = getDataName();

	for( int j=0; j<tabCount; j++)
		tmp = tmp + "\t";
	if(dataName != null && dataName.length() > 1)
		tmp = tmp + "<RecordFormat dataName = \"" + dataName + ">\n";
	else
		tmp = tmp + "<RecordFormat>\n";

	tabCount++;	
	for (int i = 0; i < getFormatElements().size(); i++)
	{
		FormatElement element = (FormatElement) getFormatElements().elementAt(i);
		tmp = tmp + element.toString();
	}

	
	if (getDecorator() != null)
	{
		for( int j=0; j<tabCount; j++)
			tmp = tmp + "\t";	
		tmp = tmp + getDecorator().toString();
	}

	tabCount--;

	for( int j=0; j<tabCount; j++)
		tmp = tmp + "\t";
	tmp = tmp + "</RecordFormatFormat>\n";
	return tmp;
}
}