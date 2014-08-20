package icbc.cmis.service;

import java.sql.*;
import java.util.Enumeration;
import oracle.jdbc.driver.*;  
import icbc.cmis.base.*;
import java.util.*;
import icbc.cmis.operation.CmisOperation;
/**
 * Insert the type's description here.
 * Creation date: (2002-1-10 16:28:19)
 * @author: Administrator
 */
public class JDBCProcedureService extends CmisDao {
	CallableStatement call = null;
	ResultSet rset = null;
/**
 * JDBCProcedureService constructor comment.
 */
public JDBCProcedureService(CmisOperation op) {
	super(op);
}
/**
 * JDBCProcedureService constructor comment.
 * @param dbResourceName1 java.lang.String
 */
public JDBCProcedureService(String dbResourceName1,CmisOperation op) {
	super(dbResourceName1,op);
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 16:26:49)
 */
public void closeConn() {
	initCall();
	super.closeConnection();
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


public String executeJournalProcedure2(String procedureName, Vector inParam) throws Exception
{
try
	{
		initCall();
		StringBuffer param = new StringBuffer("{ call " + procedureName + " (");
		for (int i = 0; i < inParam.size() - 1; i++)
			param = param.append("?, ");
		if (inParam.size() > 0)
			param = param.append(" ?, ?,?) }");
		else
			param = param.append(" ?,?) }");

		call = conn.prepareCall(param.toString());

		int outIdx = inParam.size() + 1;
		
		call.registerOutParameter(outIdx, OracleTypes.VARCHAR);
		call.registerOutParameter(outIdx+1, OracleTypes.VARCHAR);
		
		for (int i = 0; i < inParam.size(); i++)
		{
			call.setString(1 + i, ((String) inParam.elementAt(i)).trim());
		}

		call.execute();

		String retCode = call.getString(outIdx+1);
		String retSerialNo = call.getString(outIdx);

		return retSerialNo;
	}
	catch (Exception e)
	{
		System.out.println("Exception from Execute Procedure[ " + procedureName + "] exception: " + e);
		throw e;
	}

}



public int executeJournalProcedure(String procedureName, Vector inParam) throws Exception
{
try
	{
		initCall();
		StringBuffer param = new StringBuffer("{ call " + procedureName + " (");
		for (int i = 0; i < inParam.size() - 1; i++)
			param = param.append("?, ");
		if (inParam.size() > 0)
			param = param.append(" ?, ?) }");
		else
			param = param.append(" ?) }");

		call = conn.prepareCall(param.toString());

		int outIdx = inParam.size() + 1;
		call.registerOutParameter(outIdx, OracleTypes.VARCHAR);

		for (int i = 0; i < inParam.size(); i++)
		{
			call.setString(1 + i, ((String) inParam.elementAt(i)).trim());
		}

		call.execute();

		String retCode = call.getString(outIdx);
		return Integer.valueOf(retCode).intValue();
	}
	catch (Exception e)
	{
		System.out.println("Exception from Execute Procedure[ " + procedureName + "] exception: " + e);
		throw e;
	}

}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-6 16:22:51)
 * @param procedureName java.lang.String
 * @param context com.ibm.dse.base.Context
 * @param iCollName java.lang.String
 * @param inParam com.ibm.dse.base.Vector
 * @param outParam com.ibm.dse.base.Vector

 */

public String[] executeProcedure(String procedureName, String[] in, int outCount) throws Exception {

	try
	{
		initCall();
	  	StringBuffer param = new StringBuffer("{ call " + procedureName + " (");
		for (int i = 0; i < (in.length + outCount - 1); i++)
			param = param.append("?, ");

		param = param.append(" ?) }");

		call = conn.prepareCall(param.toString());

		for (int i = 1; i <= in.length; i++)
		{
			call.setString(i, in[i - 1]);
		}

		int outIdx = in.length + 1;
		for (int i = 0; i < outCount; i++)
			call.registerOutParameter(outIdx + i, OracleTypes.VARCHAR);

		call.execute();

		String[] retStr = new String[outCount];
		for (int i = 0; i < outCount; i++)
		{
			retStr[i] = (String) call.getString(outIdx + i);
		}

		return retStr;
	}
	catch (Exception e)
	{

		throw e;
	}

}
/**
* Insert the method's description here.
* Creation date: (2001-1-6 16:22:51)
* @param procedureName java.lang.String
* @param context com.ibm.dse.base.Context
* @param iCollName java.lang.String
* @param inParam com.ibm.dse.base.Vector
* @param outParam com.ibm.dse.base.Vector
*/

public int executeProcedure(String procedureName,IndexedDataCollection piColl, Vector inParam, Vector outParam,IndexedDataCollection iResult) throws Exception {
try
	{
		initCall();
		for (int kk = 0; kk < piColl.getSize(); kk++)
		{
			KeyedDataCollection pK = (KeyedDataCollection) piColl.getElement(kk);
			StringBuffer param = new StringBuffer("{ call " + procedureName + " (");
			for (int i = 0; i < inParam.size() - 1; i++)
				param = param.append("?, ");
			if (inParam.size() > 0)
				param = param.append(" ?, ?, ?) }");
			//the last two param was the output, first as retCode and second as a Result set (COURSOR);
			else
				param = param.append(" ?, ?) }");

			call = conn.prepareCall(param.toString());

			int outIdx = inParam.size() + 1;

			call.registerOutParameter(outIdx, OracleTypes.VARCHAR);
			call.registerOutParameter(outIdx + 1, OracleTypes.CURSOR);

			for (int i = 0; i < inParam.size(); i++)
			{
				String paramValue = null;
				paramValue = (String) pK.getValueAt((String) inParam.elementAt(i));
				if(paramValue == null) paramValue = "";
				call.setString(1 + i, paramValue.trim());
			}

			call.execute();

			String retCode = call.getString(outIdx);

			if (retCode.equals("1"))
			{
				call.close();
				continue;
			}

			rset = (ResultSet) call.getObject(outIdx + 1);
			while (rset.next())
			{
				KeyedDataCollection kColl = new KeyedDataCollection();
				for (int j = 0; j < outParam.size(); j++)
				{
					DataElement field = new DataElement();
					field.setName((String) outParam.elementAt(j));
					field.setValue(rset.getString(j + 1));
					kColl.addElement(field);
				}
				iResult.addElement(kColl);
			}
		}
	}
	catch (Exception e)
	{
		throw e;
	}

	return 0;
}
/**
* Insert the method's description here.
* Creation date: (2001-1-6 16:22:51)
* @param procedureName java.lang.String
* @param context com.ibm.dse.base.Context
* @param iCollName java.lang.String
* @param inParam com.ibm.dse.base.Vector
* @param outParam com.ibm.dse.base.Vector
*/
public int executeProcedure(String procedureName, KeyedDataCollection context, Vector inParam, Vector outParam,IndexedDataCollection iResult) throws Exception {
	try
	{
		initCall();
		StringBuffer param = new StringBuffer("{ call " + procedureName + " (");
		for (int i = 0; i < inParam.size() - 1; i++)
			param = param.append("?, ");
		if (inParam.size() > 0)
			param = param.append(" ?, ?, ?) }");
		else
			param = param.append(" ?, ?) }");

		call = conn.prepareCall(param.toString());

		int outIdx = inParam.size() + 1;
		call.registerOutParameter(outIdx, OracleTypes.VARCHAR);
		call.registerOutParameter(outIdx + 1, OracleTypes.CURSOR);
	//	System.out.println(procedureName);
		for (int i = 0; i < inParam.size(); i++)
		{
			String aParam = (String) context.getValueAt((String) inParam.elementAt(i));

			if (aParam != null)
				aParam = aParam.trim();
			else
				aParam = "";
			call.setString(1 + i, aParam);
		//	System.out.println((String) inParam.elementAt(i)+":"+aParam);
		}
		
		call.execute();

		String retCode = call.getString(outIdx);

		rset = (ResultSet) call.getObject(outIdx + 1);
		while (rset.next())
		{
			KeyedDataCollection kColl = new KeyedDataCollection();
			for (int j = 1; j <= outParam.size(); j++)
			{
				DataElement field = new DataElement();
				field.setName((String) outParam.elementAt(j - 1));
				field.setValue(rset.getString(j));
				kColl.addElement(field);
			}
			iResult.addElement(kColl);
			
		}

	return Integer.valueOf(retCode).intValue();
	}
	catch (Exception e)
	{
		throw e;
	}

}
/**
* Insert the method's description here.
* Creation date: (2001-1-6 16:22:51)
* @param procedureName java.lang.String
* @param context com.ibm.dse.base.Context
* @param iCollName java.lang.String
* @param inParam com.ibm.dse.base.Vector
* @param outParam com.ibm.dse.base.Vector
*/

public int executeProcedure(String procedureName, KeyedDataCollection context, Vector inParam, Vector outParam,KeyedDataCollection kResult) throws Exception {

	if (outParam == null)
		outParam = new Vector(1);
	try
	{
		initCall();
		//System.out.println("ProcedureName:" + procedureName);

		StringBuffer param = new StringBuffer("{ call " + procedureName + " (");
		for (int i = 0; i < (inParam.size() + outParam.size() - 1); i++)
			param = param.append("?, ");

		if ((inParam.size() + outParam.size()) > 0)
			param = param.append(" ?) }");
		else
			param = param.append(" ) }");

		call = conn.prepareCall(param.toString());

		int j = 1;
		for (int i = 0; i < inParam.size(); i++)
		{

			String aParam = (String) context.getValueAt((String) inParam.elementAt(i));
			if (aParam != null)
				aParam = aParam.trim();
			else
				aParam = "";

			call.setString(j++, aParam);
			//System.out.println(inParam.elementAt(i)+":"+aParam);
		}

		int outIdx = j;
		for (int i = 0; i < outParam.size(); i++)
			call.registerOutParameter(j++, OracleTypes.VARCHAR);

		call.execute();

		String ret = call.getString(outIdx); //add by xgl
		for (int i = 0; i < outParam.size(); i++)
		{
			DataElement field = new DataElement();
			field.setName((String) outParam.elementAt(i));
			field.setValue(call.getString(outIdx + i));
			kResult.addElement(field);
		}
		return Integer.valueOf(ret).intValue();
	}
	catch (Exception e)
	{
		throw e;
	}

}
/**
* Insert the method's description here.
* Creation date: (2001-1-6 16:22:51)
* @param procedureName java.lang.String
* @param context com.ibm.dse.base.Context
* @param iCollName java.lang.String
* @param inParam com.ibm.dse.base.Vector
* @param outParam com.ibm.dse.base.Vector
*/

public int executeProcedure(String procedureName,Vector outParam,KeyedDataCollection kResult) throws Exception {

	if (outParam == null)
		outParam = new Vector(1);
	try
	{
		initCall();
		StringBuffer param = new StringBuffer("{ call " + procedureName + " (");
		for (int i = 0; i <outParam.size() - 1; i++)
			param = param.append("?, ");

		if (outParam.size() > 0)
			param = param.append(" ?) }");
		else
			param = param.append(" ) }");

		call = conn.prepareCall(param.toString());

		int j = 1;
		
		int outIdx = j;
		for (int i = 0; i < outParam.size(); i++)
			call.registerOutParameter(j++, OracleTypes.VARCHAR);

		call.execute();

		String ret = call.getString(outIdx); //add by xgl
		for (int i = 0; i < outParam.size(); i++)
		{
			DataElement field = new DataElement();
			field.setName((String) outParam.elementAt(i));
			field.setValue(call.getString(outIdx + i));
			kResult.addElement(field);
		}
		return Integer.valueOf(ret).intValue();
	}
	catch (Exception e)
	{
		throw e;
	}

}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 16:25:50)
 */
public void getConn() throws Exception{
	super.getConnection();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 23:51:16)
 */
public void getConn(String userName,String userPass) throws Exception{
	super.getConnection(userName,userPass);
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 23:51:16)
 */
public void getConn(String userName) throws Exception{
	super.getConnection(userName);
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 23:51:16)
 */
public void getConnByVeriStr(String userName,String verifyStr) throws Exception{
	super.getConnByVerify(userName,verifyStr);
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-11 16:11:38)
 */
private void initCall() {
	if(rset != null){
		try{
			rset.close();
			rset = null;
		}catch(Exception ee){
			rset = null;
		}
	}
	
	if(call != null){
		try{
			call.close();
			call = null;
		}catch(Exception ec){
				call = null;
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
}
