package icbc.cmis.operation;

import java.util.*;
import java.sql.*;

import icbc.cmis.base.*;
import icbc.cmis.service.SQLAnalyser;
import oracle.jdbc.*;

/**
 * Modify History:
 * 	20030528		金智伟把executeQueryFix改造成变量绑定方式；
 */

/**
 * Insert the type's description here.
 * Creation date: (2002-1-7 17:42:19)
 * @author: Administrator
 */
public class SqlTool extends icbc.cmis.service.CmisDao {
	Statement stmt= null;
	CallableStatement stmt_call= null;
	PreparedStatement pre_stmt = null;

        
/**
 * MyExampleSqlSrv constructor comment.
 */
public SqlTool(CmisOperation op) throws Exception{
	super(op);
	
}
/**
 * MyExampleSqlSrv constructor comment.
 * @param dbResourceName1 java.lang.String
 */
public SqlTool(String dbResourceName1,CmisOperation op) throws Exception{
	super(dbResourceName1,op);
}
 public java.sql.CallableStatement callProc(String procname, Vector in, int nString) throws Exception
 {
	initSql();
 	int i = 0, j;
 	int nTotal = in.size() + nString ;
 	String fmt = "";
 	for (i = 1; i < nTotal; i++) {
  		fmt = fmt + "?,";
 	}
 	
 	fmt = fmt + '?';
 	try {
 		 stmt_call = conn.prepareCall("{call " + procname + " (" + fmt + ")}");
  		for (i = 1; i <= in.size(); i++) {
   			stmt_call.setString(i, (String) in.elementAt(i - 1));
  		}
  	
  		for (j = 0; j < nString; j++) {
   			stmt_call.registerOutParameter(i, OracleTypes.VARCHAR);
   			i++;
  		}
  		stmt_call.execute();
		return stmt_call;
 	}catch(Exception ex){
	 	throw ex;
	 	}

 }  
/**
 * Insert the method's description here.
 * Creation date: (2002-1-7 18:52:25)
 * @return java.util.Hashtable
 * @param acctCode java.lang.String
 * @param areaCode java.lang.String
 * @param major java.lang.String
 */ 
public void closeconn(){
	try{
		initSql();
	    closeConnection();	
	}catch(Exception e){
		CmisConstance.pringMsg("SqlTool.closeconn() Exception:"+e.getMessage());
		e.printStackTrace();
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 1:39:16)
 */
public void commit()throws Exception {
	try{
		conn.commit();
	}catch(Exception e){
		throw e;
	}
}
/**
 * 执行存储过程
 * @param sql 存储过程名及参数
 * @return 返回ResultSet
 * @exception 抛出SQLException表示执行失败, message中包含传入的参数sql,
 * 可用SQLException的三个方法得到有关信息
 */
public ResultSet executeProc(String sql) throws Exception {
	ResultSet rs= null;
	try
	{
		initSql();
		stmt_call= conn.prepareCall("{ ? = call " + sql + "}");
		stmt_call.registerOutParameter(1, OracleTypes.CURSOR);
		stmt_call.execute();
		rs= (ResultSet) stmt_call.getObject(1);
	}
	catch (Exception ex)
	{
		throw ex;
	}
	return rs;
}
/**
 * 执行存储过程
 * @param sql 存储过程名及参数
 * @return 返回double值
 * @exception 抛出SQLException表示执行失败, message中包含传入的参数sql,
 * 可用SQLException的三个方法得到有关信息
 */
public double executeProcD(String sql) throws Exception {
	double i= 0;
	try
	{
		initSql();
		stmt_call= conn.prepareCall("{ ? = call " + sql + "}");
		stmt_call.registerOutParameter(1, OracleTypes.NUMBER);
		stmt_call.execute();
		i= stmt_call.getDouble(1);
	}
	catch (SQLException ex)
	{
		throw ex;
	}
	return i;
}
/**
 * 执行存储过程
 * @param sql 存储过程名及参数
 * @return 返回int值
 * @exception 抛出SQLException表示执行失败, message中包含传入的参数sql,
 * 可用SQLException的三个方法得到有关信息
 */
public int executeProcI(String sql) throws Exception {
	int i= 0;
	try
	{
		initSql();
		stmt_call= conn.prepareCall("{ ? = call " + sql + "}");
		stmt_call.registerOutParameter(1, OracleTypes.NUMBER);
		stmt_call.execute();
		i= stmt_call.getInt(1);
	}
	catch (SQLException ex)
	{
		throw ex;
	}
	return i;
}
/**
 * 执行存储过程
 * @param sql 存储过程名及参数
 * @return 返回String值
 * @exception 抛出SQLException表示执行失败, message中包含传入的参数sql,
 * 可用SQLException的三个方法得到有关信息
 */
public String executeProcS(String sql) throws Exception {
	String Str= null;
	try
	{
		initSql();
		stmt_call= conn.prepareCall("{ ? = call " + sql + "}");
		stmt_call.registerOutParameter(1, OracleTypes.VARCHAR);
		stmt_call.execute();
		Str= stmt_call.getString(1);
	}
	catch (Exception ex)
	{
		throw ex;
	}
	return Str;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-7 18:52:25)
 * @return java.util.Hashtable
 * @param acctCode java.lang.String
 * @param areaCode java.lang.String
 * @param major java.lang.String
 */ 
public ResultSet executeQuery(String sql) throws Exception{
	ResultSet rs= null;
	try{
		initSql();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(sql);
	}catch(Exception e){
		throw e;
	}
	return rs;
}
public void executeQueryFix(Vector sql,String from,String where,IndexedDataCollection icoll) throws Exception{
	executeQueryFix(sql,from,where,icoll,super.getOperation());
}

/**
 * @author aking
 * @param boolean bValueOrName, 标识Vector参数是值Vector还是名称Vector
 * @param Vector vStrArr, Vector中的每个元素是长度为2的字符串数组，第一个字符串表示类型，
 * 					第二个字符串表示值(bValueOrName==true)，表示Op中的名称(bValueOrName==false)
 */
public void executeQueryFix(Vector sql,String from,String where,IndexedDataCollection icoll, boolean bValueOrName,  Vector vStrArr) throws Exception{
	executeQueryFix(sql,from,where,icoll,super.getOperation(), bValueOrName, vStrArr);
}


public void executeQueryFix(Vector sql,String from,String where,IndexedDataCollection icoll,CmisOperation operation) throws Exception{
	
	ResultSet rs = null;
	try{
		initSql();
		String strselect="select ";
	
		for(int i=0;i<sql.size();i++){
			String str0=(String)sql.elementAt(i);
			strselect+=str0+",";
		}
		strselect=strselect.substring(0,strselect.length()-1);
		if(where == null || where.trim().length() == 0)
			strselect=strselect+" from "+from;
			else 
				strselect=strselect+" from "+from+" "+where;
				
		stmt=conn.createStatement();
		rs=stmt.executeQuery(strselect);

		int maxRow = operation.getMaxRowNum();
		while(rs.next()){
			if(maxRow != -1 && icoll.getSize()>=maxRow){
				operation.setFieldValue(icoll.getName()+"IsOverflow","true");
				operation.setFieldValue(icoll.getName()+"OverflowMsg","交易结果记录数过大,当前返回记录数为 ["+maxRow+"],请调整查询条件缩小数据量.");
				break;
			}
		    KeyedDataCollection kcoll=new KeyedDataCollection();
	        kcoll.setName("kcollqueryresult");
	        for(int i=0;i<sql.size();i++){
		        String str1=(String)sql.elementAt(i);
		        DataElement dfield=new DataElement();
		        str1 = str1.trim();
		        int mark = str1.indexOf(" ");
		        while (mark != -1){
			        str1 = str1.substring(mark,str1.length());
			        str1 = str1.trim();
			        mark = str1.indexOf(" ");
		        }
		        
		        int index = str1.indexOf(".");
		        if (index != -1){
			        str1 = str1.replace('.','_');
		        }
		        
		        dfield.setName(str1);
		        String str2=rs.getString(i+1);
		        if (str2==null) str2="";
		        dfield.setValue(str2);
		        kcoll.addElement(dfield);
	        }
			icoll.addElement(kcoll);
		}
		rs.close();
		stmt.close();
	}catch(Exception e){
		try{
			rs.close();
		}catch(Exception ee){
		}
		try{
			stmt.close();
		}catch(Exception ee){
		}
		throw e;
	}
	return;
}

/**
 * @author aking
 * @param boolean bValueOrName, 标识Vector参数是值Vector还是名称Vector
 * @param Vector vStrArr, Vector中的每个元素是长度为2的字符串数组，第一个字符串表示类型，
 * 					第二个字符串表示值(bValueOrName==true)或表示Op中的名称(bValueOrName==false)
 */
public void executeQueryFix(Vector sql,String from,String where,IndexedDataCollection icoll,CmisOperation operation, boolean bValueOrName,  Vector vStrArr) throws Exception{
	
	ResultSet rs = null;
	try{
		initSql();
		String strselect="select ";
	
		for(int i=0;i<sql.size();i++){
			String str0=(String)sql.elementAt(i);
			strselect+=str0+",";
		}
		strselect=strselect.substring(0,strselect.length()-1);
		if(where == null || where.trim().length() == 0)
			strselect=strselect+" from "+from;
			else 
				strselect=strselect+" from "+from+" "+where;
				
		pre_stmt=conn.prepareStatement(strselect);
		if (bValueOrName) {
			varbd(pre_stmt, vStrArr, true, null);
		} else {
			varbd(pre_stmt, vStrArr, false, operation.getOperationData());
		}
		rs = pre_stmt.executeQuery();

		int maxRow = operation.getMaxRowNum();
		while(rs.next()){
			if(maxRow != -1 && icoll.getSize()>=maxRow){
				operation.setFieldValue(icoll.getName()+"IsOverflow","true");
				operation.setFieldValue(icoll.getName()+"OverflowMsg","交易结果记录数过大,当前返回记录数为 ["+maxRow+"],请调整查询条件缩小数据量.");
				break;
			}
		    KeyedDataCollection kcoll=new KeyedDataCollection();
	        kcoll.setName("kcollqueryresult");
	        for(int i=0;i<sql.size();i++){
		        String str1=(String)sql.elementAt(i);
		        DataElement dfield=new DataElement();
		        str1 = str1.trim();
		        int mark = str1.indexOf(" ");
		        while (mark != -1){
			        str1 = str1.substring(mark,str1.length());
			        str1 = str1.trim();
			        mark = str1.indexOf(" ");
		        }
		        
		        int index = str1.indexOf(".");
		        if (index != -1){
			        str1 = str1.replace('.','_');
		        }
		        
		        dfield.setName(str1);
		        String str2=rs.getString(i+1);
		        if (str2==null) str2="";
		        dfield.setValue(str2);
		        kcoll.addElement(dfield);
	        }
			icoll.addElement(kcoll);
		}
		rs.close();
		pre_stmt.close();
	}catch(Exception e){
		try{
			rs.close();
		}catch(Exception ee){
		}
		try{
			pre_stmt.close();
		}catch(Exception ee){
		}
		throw e;
	}
	return;
}

public void executeQueryFix_tmp(Vector sql,String from,String where,IndexedDataCollection icoll) throws Exception{
	
	ResultSet rs = null;
	try{
		initSql();
		String strselect="select ";
	
		for(int i=0;i<sql.size();i++){
			String str0=(String)sql.elementAt(i);
			strselect+=str0+",";
		}
		strselect=strselect.substring(0,strselect.length()-1);
		if(where == null || where.trim().length() == 0)
			strselect=strselect+" from "+from;
			else 
				strselect=strselect+" from "+from+" "+where;
				
		stmt=conn.createStatement();
		rs=stmt.executeQuery(strselect);
		
		while(rs.next()){
		    KeyedDataCollection kcoll=new KeyedDataCollection();
	        kcoll.setName("kcollqueryresult");
	        for(int i=0;i<sql.size();i++){
		        String str1=(String)sql.elementAt(i);
		        DataElement dfield=new DataElement();
		        str1 = str1.trim();
		        int mark = str1.indexOf(" ");
		        while (mark != -1){
			        str1 = str1.substring(mark,str1.length());
			        str1 = str1.trim();
			        mark = str1.indexOf(" ");
		        }
		        
		        int index = str1.indexOf(".");
		        if (index != -1){
			        str1 = str1.replace('.','_');
		        }
		        
		        dfield.setName(str1);
		        String str2=rs.getString(i+1);
		        if (str2==null) str2="";
		        dfield.setValue(str2);
		        kcoll.addElement(dfield);
	        }
			icoll.addElement(kcoll);
		}
	}catch(Exception e){
		throw e;
	}
	return;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-7 18:52:25)
 * @return java.util.Hashtable
 * @param acctCode java.lang.String
 * @param areaCode java.lang.String
 * @param major java.lang.String
 */ 
public int executeUpdate(String sql) throws Exception{
	
	int ret=-1;
	try{
		initSql();
		stmt=conn.createStatement();
		ret=stmt.executeUpdate(sql);
	}catch(Exception e){
		throw e;
	}
	return ret;
}
private void varbd(PreparedStatement pre_stmt,Vector v,boolean isValue,KeyedDataCollection opData)throws Exception,SQLException{
	if(v != null && !v.isEmpty()){
		for(int i = 0;i<v.size();i++){
				String[] tmpStr = null;
				if(v.elementAt(i) instanceof String[]){
					tmpStr = (String[])v.elementAt(i);
				}else{
					tmpStr =  new String[2];
					tmpStr[0] = "s";
					tmpStr[1] = (String)v.elementAt(i);
				}
				
				String value  = null;
				if(isValue){
					value = tmpStr[1];
				}else{
					value = (String)opData.getValueAt(tmpStr[1]);
					
				}
				if(value == null) value = "";
				if(tmpStr[0].equalsIgnoreCase("s")){
					pre_stmt.setString(i+1,value);
				}else if(tmpStr[0].equalsIgnoreCase("i")){
					pre_stmt.setInt(i+1,Integer.parseInt(value));
				}else if(tmpStr[0].equalsIgnoreCase("f")){
					pre_stmt.setFloat(i+1,Float.parseFloat(value));
				}else if(tmpStr[0].equalsIgnoreCase("d")){
					pre_stmt.setDouble(i+1,Double.parseDouble(value));
				}else{
					if(isValue){
						throw new Exception("不支持的数据类型["+tmpStr[0]+"] 值["+value+"] in icbc.cmis.operaiton.SqlTool.varbd(.....)");
					}else{
						throw new Exception("不支持的数据类型["+tmpStr[0]+"] 数据名称["+tmpStr[1]+"] 值["+value+"] in icbc.cmis.operaiton.SqlTool.varbd(......)");
					}
				}
				CmisConstance.pringMsg("type:"+tmpStr[0]+",value:"+value);
		}
	}
} 

public int executeUpdate(String sql,Vector vValueDataName,KeyedDataCollection opData) throws Exception{
	
	int ret=-1;
	try{
		initSql();
		pre_stmt=conn.prepareStatement(sql);
		varbd(pre_stmt,vValueDataName,false,opData);
		ret = pre_stmt.executeUpdate();
	}catch(Exception e){
		throw e;
	}
	return ret;
}

public int executeUpdate(String sql,Vector vDataValue) throws Exception{
	
	int ret=-1;
	try{
		initSql();
		pre_stmt=conn.prepareStatement(sql);
		varbd(pre_stmt,vDataValue,true,null);
		ret = pre_stmt.executeUpdate();
	}catch(Exception e){
		throw e;
	}
	return ret;
}

public ResultSet executeQuery(String sql,Vector vValueDataName,KeyedDataCollection opData) throws Exception{
	ResultSet rs= null;
	try{
		initSql();
		pre_stmt = conn.prepareStatement(sql);
		varbd(pre_stmt,vValueDataName,false,opData);
		rs=pre_stmt.executeQuery();
	}catch(Exception e){
		throw e;
	}
	return rs;
}

public ResultSet executeQuery(String sql,Vector vDataValue) throws Exception{
	ResultSet rs= null;
	try{
		initSql();
		pre_stmt = conn.prepareStatement(sql);
		varbd(pre_stmt,vDataValue,true,null);
		rs=pre_stmt.executeQuery();
	}catch(Exception e){
		throw e;
	}
	return rs;
}

public int executeUpdateI(String tableName,String columnStr,KeyedDataCollection context) throws Exception{
	
	int ret=-1;
	try{
		initSql();
		StringBuffer buffer = new StringBuffer();
		StringTokenizer toke = new StringTokenizer(columnStr,",");
		Vector tmpV = new Vector();
		while(toke.hasMoreElements()){
			buffer = buffer.append("?,");
			String name = (String)toke.nextElement();
			String value = null;
			try{
				value = (String)context.getValueAt(name.trim());
			}catch(Exception ee){}
			if(value == null) value = "";
			String[] va = new String[2];
			va[1] = value;
			va[0]="s";
			
			tmpV.addElement(va);
		}
		String tmpStr = new String(buffer);
		tmpStr = tmpStr.substring(0,tmpStr.length()-1);
				
		String sql = "insert into "+tableName+" ( "+columnStr+" ) values ( "+tmpStr+" )";
		pre_stmt=conn.prepareStatement(sql);
		CmisConstance.pringMsg(sql);
		varbd(pre_stmt,tmpV,true,null);
		ret= pre_stmt.executeUpdate();
	}catch(Exception e){
		throw e;
	}
	return ret;
}
public int executeUpdateU(String tableName,String columnStr,String where,KeyedDataCollection context) throws Exception{
	
	int ret=-1;
	try{
		initSql();
		StringBuffer buffer = new StringBuffer();
		StringTokenizer toke = new StringTokenizer(columnStr,",");
		Vector tmpV = new Vector();
		while(toke.hasMoreElements()){
			String name = (String)toke.nextElement();
			buffer = buffer.append(name+"=?,");
			String value = null;
			try{
				value = (String)context.getValueAt(name.trim());
			}catch(Exception ee){}
			if(value == null) value = "";
			String[] va = new String[2];
			va[1] = value;
			va[0]="s";
			
			tmpV.addElement(va);
			
		}
		String tmpStr = new String(buffer);
		tmpStr = tmpStr.substring(0,tmpStr.length()-1);
		if(where == null)
			 where = "";
		else{
			Hashtable ht = new Hashtable(10);
			where = SQLAnalyser.getAnalyserSQL(where,ht);
			if(!ht.isEmpty()){
				Enumeration keys = ht.keys();
				int i = 0;
				for(;keys.hasMoreElements();keys.nextElement()){
					i++;
					String[] tmpArray = (String[])ht.get(new Integer(i));
					tmpV.addElement(tmpArray);
				}
			}
		}
		String sql = "update "+tableName+" set "+tmpStr +" "+where;
		CmisConstance.pringMsg(sql);
		pre_stmt = conn.prepareStatement(sql);
		varbd(pre_stmt,tmpV,true,null);
		ret=pre_stmt.executeUpdate();
	}catch(Exception e){
		throw e;
	}
	return ret;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 23:51:16)
 */
public void getConn() throws Exception{
	super.getConnection();
}
public void getConn(String userName,String userPass) throws Exception{
	super.getConnection(userName,userPass);
}
public void getConn(String userName) throws Exception{
	super.getConnection(userName);
}


public void getConnByVeriStr(String userName,String verifyStr) throws Exception{
	super.getConnByVerify(userName,verifyStr);
} 
  public int getDays(String firstDay,String secondDay) throws Exception
  {
	ResultSet rs = null;
	int days = 0;
	try
	{
	  initSql();
	  stmt = conn.createStatement();
	  rs = stmt.executeQuery("select to_date('"+secondDay+"','YYYYMMDD')-to_date('"+firstDay+"','YYYYMMDD') from dual");
	  while (rs.next())
	  {
		days = rs.getInt(1);
	  }
	}catch(Exception ex){
	  throw ex;
	}
	  return days;
  }      
/**
 * Insert the method's description here.
 * Creation date: (2002-1-30 16:07:51)
 * @return icbc.cmis.base.DictBean
 * @param sql java.lang.String
 */
public DictBean getDictBean(String sql)throws Exception {
	try{
		ResultSet rs = null;
		initSql();
		DictBean bean = new DictBean();
		stmt=conn.createStatement();
		rs=stmt.executeQuery(sql);
		while(rs.next()){
			String key = rs.getString(1);
			String value = rs.getString(2);
			if(key == null || key.trim().length() == 0) key ="keyIsNull";
			if(value == null) value="";
			bean.setNullMark(false);
			bean.addData(key,value);
		}
		return bean;
	}catch(Exception e){
		throw e;
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 23:47:08)
 */
private void initSql() {
	if(stmt != null){
		try{
			stmt.close();
			stmt = null;
		}catch(Exception e){
			stmt = null;
		}
	}
	if(stmt_call != null){
		try{
			stmt_call.close();
			stmt_call = null;
		}catch(Exception e){
			stmt_call = null;
		}
	}
	if(pre_stmt != null){
		try{
			pre_stmt.close();
			pre_stmt = null;
		}catch(Exception e){
			pre_stmt = null;
		}
	}
	
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 1:40:41)
 */
public void rollback()throws Exception {
	try{
		conn.rollback();
	}catch(Exception e){
		throw e;
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 1:42:14)
 * @param tf boolean
 */
public void setAutoCommit(boolean tf) throws Exception{
	try{
		conn.setAutoCommit(tf);
	}catch(Exception e){
		throw e;
	}
	
}
  public String SysDate() throws Exception
  {
	ResultSet rs = null;
	String SysDate=null;
	try
	{
	  initSql();
	  stmt = conn.createStatement();
	  rs = stmt.executeQuery("select to_char(cmisdate,'YYYYMMDD') from dual");
	  while (rs.next())
	  {
		SysDate = rs.getString(1);
	  }
	  rs.close();
	}
	catch(Exception ex)
	{
	 throw ex;
	}
	return SysDate;
  }
  
public CallableStatement callFunc(
	String funcName, //Function的名字
       Vector in,       //传入的参数
       int nString)     //输出值的个数
    throws Exception {

    initSql();

    int i = 0, j;
    int nTotal = in.size() + nString;
    String fmt = "";

    for (i = 1; i < nTotal; i++) {
        fmt = fmt + "?,";
    }

    fmt = fmt + '?';

    try {
        stmt_call = conn.prepareCall("{? = call " + funcName + " (" + fmt + ")}");

        stmt_call.registerOutParameter(1, OracleTypes.VARCHAR);

        for (i = 1; i <= in.size(); i++) {
            stmt_call.setString(i + 1, (String) in.elementAt(i - 1));
        }

        for (j = 0; j < nString; j++) {
            stmt_call.registerOutParameter(i + 1, OracleTypes.VARCHAR);
            i++;
        }

        stmt_call.execute();
        return stmt_call;
    } catch (Exception ex) {
    	try{
    		stmt_call.close();
    	}catch(Exception ee){
    	}
        throw ex;
    }
}
}
