package icbc.cmis.base;

import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (2002-2-5 21:37:12)
 * @author: Administrator
 */
public class SelfDefineTableBean {
    private Hashtable hTable = new Hashtable();
/**
 * SelfDefineDictBean constructor comment.
 */
public SelfDefineTableBean() {
    super();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-2-5 21:41:12)
 * @param areaCode java.lang.String
 * @param key java.lang.String
 * @param value java.lang.String
 */
public void addRow(String areaCode, Vector values) {
    TableBean bean = (TableBean)hTable.get(areaCode.trim());
    if(bean == null){
        bean = new TableBean();
        bean.addColumn("currtype");
        bean.addColumn("ratecode");
        bean.addColumn("rate");
        bean.addColumn("notes");
        //bean.setNullMark(false);
        bean.addRow(values);
        hTable.put(areaCode.trim(),bean);
    }else{
        bean.addRow(values);
    }
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-8 13:24:20)
 * @return java.util.Vector
 */
public Enumeration getAreaCodes() {
    return hTable.keys();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
//public String getOptionTags(String areaCode) {
//    DictBean bean = (DictBean)hTable.get(areaCode.trim());
//    if(bean == null) return "";
//    StringBuffer buffer = new StringBuffer();
//    Vector keys =(Vector) bean.getKeys();
//    if(keys == null) return "";
//    for(int i = 0;i<keys.size();i++)
//      {
//       String key = (String)keys.elementAt(i);
//       String value = (String)bean.getValue(key);
//       buffer =  buffer.append("<option value=" + key + ">" + value + "</option>\n" ); 
//      }
//    return new String(buffer);
//}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
//public String getOptionTags(String areaCode,String tfKey) {
//    DictBean bean = (DictBean)hTable.get(areaCode.trim());
//    if(bean == null) return "";
//    
//    StringBuffer buffer = new StringBuffer();
//    Vector keys = (Vector)bean.getKeys();
//    if(keys == null) return "";
//    for(int i = 0;i<keys.size();i++)
//      {
//       String key = (String)keys.elementAt(i);
//       String value = (String)bean.getValue(key);
//       if(tfKey != null && key.trim().equals(tfKey.trim()))
//        buffer =  buffer.append("<option value=" + key + "  selected>" + value + "</option>\n" );
//        else
//                buffer =  buffer.append("<option value=" + key + ">" + value + "</option>\n" );
//      }
//    return new String(buffer);
//}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
//public String getOptionTagsEmty(String areaCode) {
//    StringBuffer buffer = new StringBuffer();
//    buffer =  buffer.append("<option value=\"\"> </option>\n" );
//    buffer = buffer.append(getOptionTags(areaCode));
//    return new String(buffer);
//}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
//public String getOptionTagsEmty(String areaCode,String tfKey) {
//    StringBuffer buffer = new StringBuffer();
//    buffer =  buffer.append("<option value=\"\"> </option>\n" );
//    buffer = buffer.append(getOptionTags(areaCode,tfKey));
//    return new String(buffer);
//}
//public String getOptionTagsWithKeyName(String areaCode,String tfKey) {
//    if(areaCode == null) return ""; 
//    DictBean bean = (DictBean)hTable.get(areaCode.trim());
//    if(bean == null) return "";
//
//    StringBuffer buffer = new StringBuffer();
//    Vector keys = (Vector)bean.getKeys();
//    if(keys == null) return "";
//    for(int i = 0;i<keys.size();i++)
//      {
//       String key = (String)keys.elementAt(i);
//       String value = (String)bean.getValue(key);
//       if(tfKey != null && key.trim().equals(tfKey.trim()))
//        buffer =  buffer.append("<option value=" + key + "  selected>" + key + "&nbsp;&nbsp" +value + "</option>\n" );
//        else
//                buffer =  buffer.append("<option value=" + key + ">" + key + "&nbsp;&nbsp" +value + "</option>\n" );
//      }
//    return new String(buffer);
//}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-8 13:23:24)
 * @return java.lang.String
 * @param key java.lang.String
 */
//public String getValue(String areaCode,String key) {
//    if(areaCode == null || areaCode.trim().length() == 0) return null;
//    DictBean bean = (DictBean)hTable.get(areaCode.trim());
//    if(bean  == null) return "";
//    String tmpStr = (String)bean.getValue(key.trim());
//    if(tmpStr == null) tmpStr = "";
//    return tmpStr;
//    
//}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-8 13:23:24)
 * @return java.lang.String
 * @param key java.lang.String
 */
public TableBean getValues(String areaCode) {
    if(areaCode == null || areaCode.trim().length() == 0) return null;
    return (TableBean)hTable.get(areaCode.trim());
    
}
//public boolean isNull(String areaCode) {
//    if(areaCode == null) return true;
//    TableBean bean = (TableBean)hTable.get(areaCode.trim());
//    if(bean  == null) return true;
//    return bean.isNull();
//}
}
