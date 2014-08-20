package icbc.cmis.base;

import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (2002-1-9 13:03:57)
 * @author: Administrator
 */
public class TableBean {
	private int colCount = 0;
	private int rowCount = 0;
	protected Vector columns = new Vector();
	protected Vector values = new Vector();
/**
 * TableBean constructor comment.
 */
public TableBean() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 13:07:18)
 * @param colName java.lang.String
 */
public void addColumn(String colName) {
	colName = colName.toLowerCase();
	columns.add(colCount,colName);
	colCount++;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 13:09:46)
 * @param row java.util.Vector
 */
public void addRow(Vector row) {
	values.add(rowCount,row);
	rowCount++;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 14:19:05)
 * @return java.util.Hashtable[]
 * @param bankId java.lang.String
 */
public KeyedDataCollection getAllSubBankInfoByBankID(String bankId) {
	try{
		KeyedDataCollection bankInfo = getASubBankInfoByBankID(bankId);
		IndexedDataCollection iSubBankInfo = (IndexedDataCollection)bankInfo.getElement("iSubBankInfo");
		if(iSubBankInfo != null){
			for(int i = 0;i<iSubBankInfo.getSize();i++){
				KeyedDataCollection kSubBankInfo = (KeyedDataCollection)iSubBankInfo.getElement(i);
				String subBankId = (String)kSubBankInfo.getValueAt("area_code");
				IndexedDataCollection tmpI = (IndexedDataCollection)getSubBankInfo(subBankId);
				kSubBankInfo.addElement(tmpI);
			/*	if(tmpI != null){
					for(int j = 0;j<tmpI.getSize();j++){
						KeyedDataCollection kSubBankInfo1 = (KeyedDataCollection)iSubBankInfo.getElement(i);
						String subBankId1 = (String)kSubBankInfo.getValueAt("area_code");
						getAllSubBankInfoByBankID(subBankId1);
				
					}
				}
				*/
				
			}
		}

		return bankInfo;
		
	}catch(Exception e){
		System.out.println("Exception in TableBean. getAllSubBankInfoByBankID(String bankId): \n"+e.toString());
		return null;
	}	
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 14:19:05)
 * @return java.util.Hashtable[]
 * @param bankId java.lang.String
 */
public KeyedDataCollection getASubBankInfoByBankID(String bankId) {
try{
	if(bankId == null) 
		return null;
	bankId = bankId.trim();
	int index = indexOfColumn("area_code");
	int subIndex = indexOfColumn("belong_bank");
	int subIndex1 = indexOfColumn("second_bank");
	KeyedDataCollection kBankInfo = new KeyedDataCollection();
	kBankInfo.setName("kBankInfo");
	IndexedDataCollection iBankInfo = new IndexedDataCollection();
	iBankInfo.setName("iSubBankInfo");
	boolean isFound = false;
	for(int i = 0;i<values.size();i++){
		Vector tmpV = (Vector)values.elementAt(i);
		String tmpCode = (String)tmpV.elementAt(index);
		if(tmpCode.trim().equals(bankId)){
			isFound = true;
			for(int j =0;j<columns.size();j++){
				String keyStr = (String)columns.elementAt(j);
				kBankInfo.addElement(keyStr.toLowerCase(),tmpV.elementAt(j));
				
			}
			
		}else{
			String subCode = (String)tmpV.elementAt(subIndex);
			String subCode1 = (String)tmpV.elementAt(subIndex1);
			
			if(subCode.trim().equals(bankId) || subCode1.trim().equals(bankId)){
				KeyedDataCollection kColl = new KeyedDataCollection();
				kColl.setName("kSubBankInfo");
				
				for(int j =0;j<columns.size();j++){
					String keyStr = (String)columns.elementAt(j);
					kColl.addElement(keyStr.toLowerCase(),tmpV.elementAt(j));
				
				}
				iBankInfo.addElement(kColl);
			}
		}
	}
	if(isFound){
		kBankInfo.addElement(iBankInfo);
		return kBankInfo;
	}else
		return null;
}catch(Exception e){
	System.out.println("******Exception in TableBean.getASubbankInfoByBankID(String bankId): \n"+e.toString());
	return null;
}
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 13:56:26)
 * @return java.util.Hashtable
 * @param areaCode java.lang.String
 */
public Hashtable getBankInfoByAreaCode(String areaCode) {
	
	int index = indexOfColumn("area_code");
	Vector vRow = null;
	for(int i = 0;i<values.size();i++){
		Vector v = (Vector)values.elementAt(i);
		String aCode = (String)v.elementAt(index);
		if(aCode.trim().equals(areaCode.trim())){
			vRow = v;
			break;
		}
	}
	
	if(vRow != null){
		Hashtable hTable = new Hashtable();
		for(int j = 0;j<columns.size();j++){
			String tmpValue = (String)vRow.elementAt(j);
			if(tmpValue == null) tmpValue = "";
			hTable.put(((String)columns.elementAt(j)).toLowerCase(),tmpValue);
		}
		return hTable;
	}else
		return null;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 23:49:49)
 * @return java.util.Vector
 */
public Vector getColumns() {
	return columns;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-10 10:36:30)
 * @return java.lang.String
 * @param code java.lang.String
 */
public String getNameByCode(String nameCollName,String codeColName,String codeValue) {
	int index = indexOfColumn(codeColName.trim());
	int indexN = indexOfColumn(nameCollName.trim());
	for(int i = 0;i<values.size();i++){
		Vector tmp = (Vector)values.elementAt(i);
		String key = (String)tmp.elementAt(index);
		if(key.trim().equals(codeValue.trim())){
			return (String)tmp.elementAt(indexN);
		}
	}
	return null;
}

/**
 * Insert the method's description here.
 * Creation date: (2003-9-15 10:36:30)
 * @return java.lang.String
 * @param code java.lang.String
 */
public String getNameByTwoCode(String nameCollName,String codeColName1,String codeColName2,String codeValue1,String codeValue2) {
	int index1 = indexOfColumn(codeColName1.trim());
	int index2 = indexOfColumn(codeColName2.trim());	
	int indexN = indexOfColumn(nameCollName.trim());
	for(int i = 0;i<values.size();i++){
		Vector tmp = (Vector)values.elementAt(i);
		String key1 = (String)tmp.elementAt(index1);
		String key2 = (String)tmp.elementAt(index2);
		if(key1.trim().equals(codeValue1.trim())&&key2.trim().equals(codeValue2.trim())){
			return (String)tmp.elementAt(indexN);
		}
	}
	return null;
}




/**
 * Insert the method's description here.
 * Creation date: (2003-8-20 10:36:30)
 * @return java.lang.String
 * @param code java.lang.String
 */
public Vector getAllNameByCodes(String nameCollName,String codeColName,Vector codeValues) {
	int index = indexOfColumn(codeColName.trim());
	int indexN = indexOfColumn(nameCollName.trim());
        Vector allNames = new Vector();
        String  codeValue = null;
        for(int j = 0;j<codeValues.size();j++){
          codeValue = (String)codeValues.elementAt(j);
          for(int i = 0;i<values.size();i++){
            Vector tmp = (Vector)values.elementAt(i);
            String key = (String)tmp.elementAt(index);
            if(key.trim().equals(codeValue.trim())){
              if(!allNames.contains((String)tmp.elementAt(indexN)))
                    allNames.add((String)tmp.elementAt(indexN));
            }
          }
        }
	return allNames;
}

/**
 * Insert the method's description here.
 * Creation date: (2003-8-20 10:36:30)
 * @return java.lang.String
 * @param code java.lang.String
 */
public Vector getAllNameByCode(String nameCollName,String codeColName,String codeValues) {
	int index = indexOfColumn(codeColName.trim());
	int indexN = indexOfColumn(nameCollName.trim());
        Vector allNames = new Vector();
//        String  codeValue = null;
          for(int i = 0;i<values.size();i++){
            Vector tmp = (Vector)values.elementAt(i);
            String key = (String)tmp.elementAt(index);
            if(key.trim().equals(codeValues.trim())){
              if(!allNames.contains((String)tmp.elementAt(indexN)))
                    allNames.add((String)tmp.elementAt(indexN));
            }
          }
	return allNames;
}




/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTags(String keyColName,String valueColName) {
	StringBuffer buffer = new StringBuffer();
	int indexKey = indexOfColumn(keyColName.toLowerCase());
	int indexValue = indexOfColumn(valueColName.toLowerCase());
	for(int i = 0;i<values.size();i++)
	  {
	   Vector vv = (Vector)values.elementAt(i);
	   String key = (String)vv.elementAt(indexKey);
	   String value = (String)vv.elementAt(indexValue);
	   if(key == null) key = "";
	   if(value == null) value = "";
	   buffer =  buffer.append("<option value=" + key + ">" + value + "</option>\n" ); 
	  }
	return new String(buffer);
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTags(String keyColName,String valueColName,String selectedValue) {
	
	StringBuffer buffer = new StringBuffer();
	int indexKey = indexOfColumn(keyColName.toLowerCase());
	int indexValue = indexOfColumn(valueColName.toLowerCase());
	for(int i = 0;i<values.size();i++)
	  {
	   Vector vv = (Vector)values.elementAt(i);
	   String key = (String)vv.elementAt(indexKey);
	   String value = (String)vv.elementAt(indexValue);
	   if(key == null) key = "";
	   if(value == null) value = "";
	   if(selectedValue != null && key.trim().equals(selectedValue.trim()))
	   	buffer =  buffer.append("<option value=" + key + "  selected>" + value + "</option>\n" );
	   	else
	   		buffer =  buffer.append("<option value=" + key + ">" + value + "</option>\n" );
	  }
	return new String(buffer);
	
}
	/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTagsEmty(String keyColName,String valueColName) {
	StringBuffer buffer = new StringBuffer();
	buffer =  buffer.append("<option value=\"\"> </option>\n" );
	buffer = buffer.append(getOptionTags(keyColName,valueColName));
	return new String(buffer);
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTagsEmty(String keyColName,String valueColName,String selectedValue) {
	StringBuffer buffer = new StringBuffer();
	buffer =  buffer.append("<option value=\"\"> </option>\n" );
	buffer =  buffer.append(getOptionTags(keyColName,valueColName,selectedValue));
	return new String(buffer);
}

/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 14:19:05)
 * @return java.util.Hashtable[]
 * @param bankId java.lang.String
 */
private IndexedDataCollection getSubBankInfo(String bankId) {
try{
	if(bankId == null) 
		return null;
	bankId = bankId.trim();
	int subIndex = indexOfColumn("belong_bank");
	int subIndex1 = indexOfColumn("second_bank");
	IndexedDataCollection iBankInfo = new IndexedDataCollection();
	iBankInfo.setName("iSubBankInfo");
	for(int i = 0;i<values.size();i++){
		Vector tmpV = (Vector)values.elementAt(i);
		String subCode = (String)tmpV.elementAt(subIndex);
		String subCode1 = (String)tmpV.elementAt(subIndex1);
			
		if(subCode.trim().equals(bankId) || subCode1.trim().equals(bankId)){
			KeyedDataCollection kColl = new KeyedDataCollection();
			kColl.setName("kSubBankInfo");
				
			for(int j =0;j<columns.size();j++){
				String keyStr = (String)columns.elementAt(j);
				kColl.addElement(keyStr.toLowerCase(),tmpV.elementAt(j));
				
			}
			
			iBankInfo.addElement(kColl);
			kColl.addElement(getSubBankInfo((String)kColl.getValueAt("area_code")));
		}
	}
	return iBankInfo;
}catch(Exception e){
	System.out.println("******Exception in TableBean.getASubbankInfoByBankID(String bankId): \n"+e.toString());
	return null;
}
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 23:50:45)
 * @return java.util.Vector
 */
public Vector getTableValus() {
	return values;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 23:51:50)
 * @return int
 * @param columnName java.lang.String
 */
public int indexOfColumn(String columnName) {
	columnName = columnName.toLowerCase();
	/*
	if(columns != null) return columns.indexOf(columnName);
		else return -1;
		*/

	if(columns != null){
		for(int i = 0;i<columns.size();i++){
			if(((String)columns.elementAt(i)).equalsIgnoreCase(columnName))
				return i;
		}
	}
	return -1;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTagsByFilter(String filterColName,String filterColValue,String keyColName,String valueColName,String selectedValue) {
	StringBuffer buffer = new StringBuffer("<option value=\"\"></option>");
	int indexFilter = indexOfColumn(filterColName.toLowerCase());
	int indexKey = indexOfColumn(keyColName.toLowerCase());
	int indexValue = indexOfColumn(valueColName.toLowerCase());
	if(indexFilter == -1 || indexKey == -1 || indexValue == -1){
		return "<option value=\"\"></option>";
	}
	if(selectedValue == null)selectedValue = "";
	for(int i = 0;i<values.size();i++)
	  {
	   Vector vv = (Vector)values.elementAt(i);
	   String key = (String)vv.elementAt(indexKey);
	   String value = (String)vv.elementAt(indexValue);
	   String filter = (String)vv.elementAt(indexFilter);
	   if(key == null) key = "";
	   if(value == null) value = "";
	   if(filter == null) filter = "";
	   if(filter.trim().equals(filterColValue.trim())){
	   	if(selectedValue != null && key.trim().equals(selectedValue.trim()))
	   		buffer =  buffer.append("<option value=" + key + "  selected>" + value + "</option>\n" );
	   		else
	   			buffer =  buffer.append("<option value=" + key + ">" + value + "</option>\n" );
	  	}
	  }
	return new String(buffer);
}

public String getNameByFilter(
    String filterColName,
    String filterColValue,
    String keyColName,
    String valueColName,
    String keyValue) {
    String returnValue = "";
    int indexFilter = indexOfColumn(filterColName.toLowerCase());
    int indexKey = indexOfColumn(keyColName.toLowerCase());
    int indexValue = indexOfColumn(valueColName.toLowerCase());
    if (indexFilter == -1 || indexKey == -1 || indexValue == -1) {
        return returnValue;
    }
    for (int i = 0; i < values.size(); i++) {
        Vector vv = (Vector) values.elementAt(i);
        String key = (String) vv.elementAt(indexKey);
        String value = (String) vv.elementAt(indexValue);
        String filter = (String) vv.elementAt(indexFilter);
        if (key == null)
            key = "";
        if (value == null)
            value = "";
        if (filter == null)
            filter = "";
        if (filter.trim().equals(filterColValue.trim())) {
            if (keyValue != null && key.trim().equals(keyValue.trim())) {
                returnValue = value;
                break;
            }
        }
    }
    return returnValue;
}

}
