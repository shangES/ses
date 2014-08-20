package icbc.cmis.base;
import icbc.cmis.util.*;
import icbc.cmis.operation.*;


import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (2002-1-9 13:03:57)
 * @author: Administrator
 */
public class DataBean {
//	private int colCount = 0;
//	private int rowCount = 0;
//	protected Vector columns = new Vector();
//	protected Vector values = new Vector();
/**
 * TableBean constructor comment.
 */
public DataBean() {
	super();
}

/**
 * Insert the method's description here.
 * Creation date: (2002-1-10 10:36:30)
 * @return java.lang.String
 * @param code java.lang.String
 */
public String getNameByCode(String userName , String tableName , String nameCollName , String codeColName , String codeValue ) {
	try{
	
		DBTools srv = new DBTools(new DummyOperation(),userName);
		String querySql = "SELECT "+nameCollName+" FROM "+tableName+" WHERE " +codeColName+ " = ? " ;
		Vector params = new Vector(1);
		params.add(codeValue);
		
		String strVal = srv.executeSQL(querySql,params);
		return strVal;

	}
	catch(Exception e)
	{
		return "";
	}
}

public String getNameByCodeLang(String userName , String tableName , String nameCollName , String codeColName , String codeValue ,String langCode ) {
	try{
	
		DBTools srv = new DBTools(new DummyOperation(),userName);
		String querySql = "SELECT "+nameCollName+" FROM "+tableName+" WHERE " +codeColName+ " = ? and lang_code = ?" ;
		Vector params = new Vector(2);
		params.add(codeValue);
		params.add(langCode);
		String strVal = srv.executeSQL(querySql,params);
		return strVal;

	}
	catch(Exception e)
	{
		return "";
	}
}


}
