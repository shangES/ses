package icbc.cmis.util;


import java.util.*;
import icbc.cmis.base.*;
/**
 * Insert the type's description here.
 * Creation date: (2001-12-28 14:08:27)
 * @author: Administrator
 */
public class util_EnpQueryOper extends icbc.cmis.operation.CmisOperation{
	java.util.Vector branchs=new java.util.Vector();
/**
 * LogonOp constructor comment.
 */
public util_EnpQueryOper() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-28 14:12:28)
 */
public void execute() throws TranFailException {
	try {
		try {
			String mark = getStringAt("mark");
			if (mark != null && mark.trim().equals("true")) {
				setOperationDataToSession();
				setReplyPage("/icbc/cmis/util/util_EnpList.jsp");
				return;
			}
		} catch (Exception eee) {
			return;
		}
		String where = "";
		try {
			if (!getStringAt("TA200011001").equals("")) {
				where += " and TA200011001 like '" + getStringAt("TA200011001") + "%'";
			}
			if (!getStringAt("TA200011003").equals("")) {
				where += " and TA200011003 like '%" + getStringAt("TA200011003") + "%'";
			}
			if (!getStringAt("TA200011005").equals("")) {
				where += " and TA200011005 like '" + getStringAt("TA200011005") + "%'";
			}
			if (!getStringAt("TA200011010").equals("")) {
				where += " and TA200011010 = '" + getStringAt("TA200011010") + "'";
			}
			if (!getStringAt("TA200011011").equals("")) {
				where += " and TA200011011 = '" + getStringAt("TA200011011") + "'";
			}
			if (!getStringAt("TA200011012").equals("")) {
				where += " and TA200011012 = '" + getStringAt("TA200011012") + "'";
			}
			if (!getStringAt("TA200011014").equals("")) {
				where += " and substr(TA200011014,1,1) = substr('"
					+ getStringAt("TA200011014")
					+ "',1,1)";
			}
			if (!getStringAt("TA200011016").equals("")) {
				where += " and TA200011016 = '" + getStringAt("TA200011016") + "'";
			}
			if (!getStringAt("TA200011031").equals("")) {
				where += " and TA200011031 = '" + getStringAt("TA200011031") + "'";
			}
			String ts = (String) getSessionData("BankFlag");
			if (ts.equals("4")) { //支行
				ts = (String) getSessionData("EmployeeClass");
				if (Integer.valueOf(ts).intValue() == 8) { //操作员
					where
						+= (" and TA200011001 in (select TA200012001 from ta200012 where ta200012003 = '"
							+ (String) getSessionData("EmployeeCode")
							+ "' )");
				} else {
					where
						+= (" and TA200011001 in (select TA20001L001 from TA20001L where TA20001L002 = '"
							+ (String) getSessionData("AreaCode")
							+ "')");
				}
			} else
				if (!ts.equals("0")) { //except 总行
					FindAllSubBranch((String) getSessionData("AreaCode"));
					String ts1 = "";
					int j = branchs.size();
					for (int i = 0; i < j; i++) {
						ts1 += ("'" + (String) branchs.get(i) + "'");
						if (i < (j - 1))
							ts1 += ",";
					}
					where
						+= (" and TA200011001 in (select TA20001L001 from TA20001L where TA20001L002 in ("
							+ ts1
							+ "))");
				}
			where += (" order by ta200011001");
			where = where.substring(4);
			java.util.Hashtable selehash = new java.util.Hashtable();
			selehash.put("select", "ta200011001,ta200011003,ta200011005");
			selehash.put("from", "ta200011");
			selehash.put("where", where);
			selehash.put("key", "ta200011001");
			GetQueryResult(where, selehash); //取到查询条件后进入查询
		} catch (Exception ex) {
			throw ex;
		}

               setReplyPage("/icbc/cmis/util/util_EnpList.jsp");

	} catch (TranFailException e) {
		setErrorCode(e.getErrorCode());
		setErrorCode(((TranFailException) e).getErrorCode());
		setErrorDispMsg(e.getDisplayMessage(e.getErrorCode()));
		setErrorLocation(e.getErrorLocation());
		setErrorMessage(e.getErrorMsg());
		setReplyPage("/icbc/cmis/error.jsp");
	} catch (Exception e) {
		setErrorCode("xdtz22126");
		setErrorDispMsg("交易平台错误：客户登录失败");
		setErrorLocation("FACustomerQueryOp.execute()");
		setReplyPage("/icbc/cmis/error.jsp");
		setErrorMessage(e.getMessage());
		setReplyPage("/icbc/cmis/error.jsp");
	}
}


/**
 * Insert the method's description here.
 * Creation date: (2001-12-30 9:29:26)
 * @param sbranch java.lang.String
 */
private void FindAllSubBranch(String sBranch) throws TranFailException{
	try{
		java.util.Vector vBranchArea = new EnpQuerySqlSrv(this).getBranchArea(sBranch);

	for(int i= 0;i<vBranchArea.size();i++){
		String subBranchArea = (String)vBranchArea.elementAt(i);
		branchs.add(subBranchArea);
		FindAllSubBranch(subBranchArea);
	  }
	}catch(TranFailException e){
		throw e;
	}
	catch(Exception e){
		new TranFailException("xdtz22125","FACustomerQueryOp.FindAllSubBranch(String)",e.getMessage());
	}
	return;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-30 9:29:26)
 * @param sbranch java.lang.String
 */
private void GetQueryResult(String swhere, java.util.Hashtable h_tmp)
	throws TranFailException {
	IndexedDataCollection idcoll= new IndexedDataCollection();
	idcoll.setName("icollqueryenp");
	try
	{
		Vector vTable = new EnpQuerySqlSrv(this).queryResult(swhere,h_tmp);
		for(int i = 0;i<vTable.size();i++)
		{
			Hashtable hTable = (Hashtable)vTable.elementAt(i);
			KeyedDataCollection kdcoll= new KeyedDataCollection();
			kdcoll.setName("kcollqueryenp");
			DataElement dfield1= new DataElement();
			DataElement dfield2= new DataElement();
			DataElement dfield3= new DataElement();
			dfield1.setName("ta200011001");
			dfield2.setName("ta200011003");
			dfield3.setName("ta200011005");
			kdcoll.addElement(dfield1);
			kdcoll.addElement(dfield2);
			kdcoll.addElement(dfield3);
			String str1= (String)hTable.get("1");
			String str2= (String)hTable.get("2");
			String str3= (String)hTable.get("3");
			kdcoll.setValueAt("ta200011001", str1);
			kdcoll.setValueAt("ta200011003", str2);
			kdcoll.setValueAt("ta200011005", str3);
			idcoll.addElement(kdcoll);
		}
	try{
		getOperationData().addElement(idcoll);
	}catch(CTEObjectExistingException exx){
		getOperationData().setValueAt("icollqueryenp",idcoll);
	}
		setOperationDataToSession();
	}
	catch(Exception ee)
	{
		new TranFailException("xdtz22125","FACustomerQueryOp.GetQueryResult(String,Hashtable)",ee.getMessage());
	}
	return;
   }
}
