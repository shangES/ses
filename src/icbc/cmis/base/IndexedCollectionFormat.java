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
public class IndexedCollectionFormat extends FormatElement
{
	private FormatElement formatElement = null;
	private int times = -1;
/**
 * IndexedFormat constructor comment.
 */
public IndexedCollectionFormat() {
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
	formatElement = element;
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
	formatElement = element;
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
	formatElement = element;	
}
	public String[] extract(String data) throws CTEInvalidRequestException
{
	  int i = times;
        if(i == 0)
            return super.extract(data);
        String s1 = data;
        String s2 = new String();
        String as[] = new String[2];
        boolean flag = false;
        String isOver = null;
        if(i == -1)
        {
            i = 0;
            while(!flag && s1.length() > 0) 
                try
                {
                    as = getFormatElement().extract(s1);
                    if(isOver == null){
	                    int index = as[0].indexOf(">");
	                    if(index != -1){
		                    isOver = as[0].substring(0,index+1).trim();
	                    }
                    }
                    if(as[0].equals(""))
                    {
                        flag = true;
                    } else
                    {
                        s2 = s2 + as[0];
                        if(!as[1].trim().startsWith(isOver.trim()))
                    	{
                       	 	flag = true;
                   		 }
                        s1 = as[1];
                        i++;
                        
                    }
                }
                catch(Exception _ex)
                {
                    flag = true;
                    as[1] = s1 + as[1];
                }
        } else
        {
            for(int j = 1; j <= i; j++)
            {
                as = getFormatElement().extract(s1);
                s2 = s2 + as[0];
                s1 = as[1];
            }

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
public String format(DataElement dataElement)throws CTEUncausedException,CTEObjectExistingException,CTEObjectNotFoundException,CTEInvalidRequestException {


	java.io.StringWriter writer = new java.io.StringWriter();

	int size = ((IndexedDataCollection)dataElement).getSize();
		
	for( int i=0; i<size; i++)
	{
		
		DataElement element = ((IndexedDataCollection)dataElement).getElement(i);
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
 * @param element com.zmc.chanel.base.FormatElement
 */
public FormatElement getFormatElement()
{
	return formatElement;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-3-14 14:02:34)
 * @return int
 */
public int getTimes() {
	return 0;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param times java.lang.String
 */
public void setTimes( String times) {
	try{
		this.times=Integer.valueOf(times).intValue();
	}catch(Exception e){
		this.times = -1;
	}
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
	String dataName = getDataName();

	for( int j=0; j<tabCount; j++)
		tmp = tmp + "\t";
	if(dataName != null && dataName.length() > 1)
		tmp = tmp + "<IndexedCollectionFormat dataName = \"" + dataName + "\">\n";
	else
		tmp = tmp + "<IndexedCollectionFormat>\n";

	tabCount++;
	
	tmp = tmp + formatElement.toString();

	
	if( getDecorator()!= null)
	{
		for( int j=0; j<tabCount; j++)
			tmp = tmp + "\t";
		tmp = tmp + getDecorator().toString();
	}

	tabCount--;
		
	for( int j=0; j<tabCount; j++)
		tmp = tmp + "\t";
	tmp = tmp + "</IndexedCollectionFormat>\n";	
	
	return tmp;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param dataSource java.lang.String
 * @param dataElement com.icbc.cmis.base.DataElement
 */
public DataElement unformat(String dataSource, DataElement dataElement)throws CTEUncausedException, CTEObjectExistingException,CTEObjectNotFoundException,CTEInvalidRequestException
{
	String dataName = formatElement.getDataName();
	String tmpString = dataSource;
	String[] extractedData;

	if( dataName == null)
	{
		//System.out.println("invalid format define: \n" + this.toString());
		//return null;
		throw new CTEInvalidRequestException("CTEInvalidRequestException",
			"IndexedCollectionFormat.unformat(String dataSource, DataElement dataElement)",
			"invalid format define: \n" + this.toString());
	}
	while( true )
	{
		if( tmpString.length() <= 1)
			break;
			
		extractedData = formatElement.extract(tmpString);
		
		if( (formatElement instanceof KeyedCollectionFormat))
		{
			KeyedDataCollection collection = new KeyedDataCollection();
			collection.setName(dataName);
			formatElement.unformat(extractedData[0], collection);

			((IndexedDataCollection)dataElement).addElement( collection );
		}
		else if( (formatElement instanceof IndexedCollectionFormat))
		{
			IndexedDataCollection collection = new IndexedDataCollection();
			collection.setName(dataName);
			formatElement.unformat(extractedData[0], collection);
			((IndexedDataCollection)dataElement).addElement( collection );
		}		
		else
		{
			DataElement aElement = new DataElement();
			aElement.setName(dataName);
			formatElement.unformat(extractedData[0], aElement);
			((IndexedDataCollection)dataElement).addElement( aElement );
			
		}
		
		tmpString = extractedData[1];
	}
	return dataElement;
}
}
