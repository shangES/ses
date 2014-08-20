package icbc.cmis.operation;

import icbc.cmis.operation.*;
import icbc.cmis.base.*;
public class ExampleQueryOp extends CmisOperation{
	
/**
 * LogonOp constructor comment.
 */
public ExampleQueryOp() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-28 14:12:28)
 */
public void execute() throws Exception{
	SqlTool tool = null;
  try{
	  String mark = getStringAt("mark");
	  if(mark != null && mark.trim().equals("true")){
		setOperationDataToSession();
	    setReplyPage("/icbc/cmis/exampleresult.jsp");
	    return;
	  }
	  
	java.util.Vector vColName =new java.util.Vector();
	vColName.addElement("ta200011001");
	vColName.addElement("ta200011003");
	vColName.addElement("ta200011005");
	vColName.addElement("ta200011010");
	vColName.addElement("ta200011011");
	vColName.addElement("ta200011014");
	vColName.addElement("ta200011012");
	vColName.addElement("ta200011016");
	String where ="where rownum<100";
	String table = "ta200011";
	IndexedDataCollection iResult=new IndexedDataCollection();
	iResult.setName("iResult");
	tool = new SqlTool(this);
	tool.getConn();
	tool.executeQueryFix(vColName,table,where,iResult,this);
	tool.closeconn();
	if(isElementExist("iResult"))
		removeDataField("iResult");
	addIndexedDataCollection(iResult);
	setOperationDataToSession();
	//setReplyPage("/icbc/cmis/exampleresult.jsp");
	   String xxx = "/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.missign.TestOp";
      setReplyPage(xxx);
      System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
  }catch(TranFailException e){
	  setReplyPage("/icbc/cmis/error.jsp");
	  if(tool != null) tool.closeconn();
	  throw e;
  }catch(Exception e){
	  setReplyPage("/icbc/cmis/error.jsp");
	  if(tool != null)
	  tool.closeconn();
	  throw new TranFailException("xdtz22126","ExampleQueryOp.execute()",e.getMessage());
  }
  
  
  }
}
