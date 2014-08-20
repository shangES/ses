		package icbc.cmis.util;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.*;
import java.util.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.*;
/**
 * Title: 跨行业、跨地区授权
 * Description: Submitted from source jsp
 *              Sumnitted to /icbc/cmis/util/util_MagGrantDetail.jsp
 *              get
 *              String EmployeeCode,String AreaCode
 *              from session
 *              put vector vMagGrant to OperationData
 *
 *              在jsp发起交易时可以这么写
 *              var ts = window.showModalDialog("/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=MagGrant&ask=true&beginPos=0&grant001=0&columnName1=代码&columnName2=名称&time="+new(Date),window,"dialogWidth:630px;dialogHeight:360px;center:yes;help:no;status:no;resizable:no");
 * Creation date: (01-26-2002)
 * @para String grant001 授权对象种类,
 * @return
 *
 *
 * update date: 2003-03-04
 * update content: 动态显示 名称和代码
 * updated by WuQQ
 */
public class MagGrant extends CmisOperation {
private String sReplyPage = null;
public MagGrant() {
	super();
}
public void execute() throws TranFailException {
	try {
								String sGrant001 = getStringAt("grant001");
								GetQueryResult(sGrant001);
								//updated by WuQQ,20030304
								this.setOperationDataToSession(); //保留 需要在util_MagGrantDetail.jsp中显示的名称和代码
								setReplyPage("/icbc/cmis/util/util_MagGrantDetail.jsp");
								return;

	} catch (TranFailException e) {
		setErrorCode(e.getErrorCode());
		setErrorCode(((TranFailException) e).getErrorCode());
		setErrorDispMsg(e.getDisplayMessage(e.getErrorCode()));
		setErrorLocation(e.getErrorLocation());
		setErrorMessage(e.getErrorMsg());
		setReplyPage("/icbc/cmis/error.jsp");
	} catch (Exception e) {
		setErrorCode("xdtzUTIl501");
		setErrorDispMsg("跨行授权失败");
		setErrorLocation("MagGrant.execute()");
		setReplyPage("/icbc/cmis/error.jsp");
		setErrorMessage(e.getMessage());
		setReplyPage("/icbc/cmis/error.jsp");
	}
}

private void GetQueryResult(String sGrant001)
	throws TranFailException {
	try
	{
					String AreaCode = (String) getSessionData("AreaCode");
					String EmployeeCode = (String) getSessionData("EmployeeCode");
					//Vector vTable = new icbc.cmis.PB.YearQueryResult().GetQueryResult(swhere,h_tmp);
					/*String sql = "select grant002,";
					if( sGrant001.equals("0")){
						sql += " (select ta200011003,ta200011004 from ta200011 where ta200011001=grant002)";
			//sql += ",(select ta200011004 from ta200011 where ta200011001=grant002)";
					}else{
						sql += " (select area_name,bank_flag from mag_area where area_code=grant002)";
				}

		 sql += " from mag_grant ";
					sql += " where grant001='"+sGrant001+"' and grant003='"+AreaCode+"' and grant004='"+EmployeeCode+"'";
			*/
			String sql="";
			if( sGrant001.equals("0")){
				sql += "select  grant002,ta200011003,ta200011004 from mag_grant ,ta200011 where grant002=ta200011001 and grant001='"+sGrant001+"' and grant003='"+AreaCode+"' and grant004='"+EmployeeCode+"'";
			}else{
				sql += "select  grant002,area_name,bank_flag from mag_grant ,mag_area where grant002=area_code and grant001='"+sGrant001+"' and grant003='"+AreaCode+"' and grant004='"+EmployeeCode+"'";
			}
			//系统日期在 授权起始日 与 授权终止日 之间
			sql += " and to_char(cmisdate,'yyyymmdd') >= grant008 and to_char(cmisdate,'yyyymmdd') <= grant009";
					icbc.cmis.FJ.CommonTools.cmisPrintln(sql);
					SqlTool tool = new SqlTool(this);
					java.sql.ResultSet rs = null;
					java.util.Vector aVector = null;

					try{
					tool.getConn();
					rs = tool.executeQuery(sql);
					if (rs != null)
					{
									aVector = new java.util.Vector();
									int i = 0;
									while (rs.next()) {
													Hashtable hTable = new Hashtable();
				hTable.put("code",rs.getString(1));
				hTable.put("name",rs.getString(2));
				/*
				if( sGrant001.equals("0")){
					hTable.put("bank_flag",rs.getString(3));
				}else if( sGrant001.equals("1")){
					hTable.put("short_name",rs.getString(3));
				}
				*/
				hTable.put("third_col",rs.getString(3));

													//aVector.add(i*2, rs.getString(1));
													//aVector.add(i*2+1, rs.getString(2));
													aVector.add(i,hTable);
													i++;
									}
					}
					tool.closeconn();
					setFieldValue("vMagGrant",aVector);
					setOperationDataToSession();
					} catch (TranFailException te) {
									tool.closeconn();
									throw te;
					} catch (Exception e) {
									tool.closeconn();
									throw new TranFailException("xddzUTILl502", "MagGrant.getQueryResult()", "跨行业授权失败！","错误描述");
					}
					}catch (TranFailException te) {
									throw te;
					} catch (Exception e) {
									throw new TranFailException("xddtUTIL503", "MagGrant.getQueryResult()", "跨行业授权失败！","错误描述");
					}

	return;
}
	/**Clean up resources*/
	public void destroy() {
	}
}