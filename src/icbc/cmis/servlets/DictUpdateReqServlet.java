package icbc.cmis.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import icbc.cmis.base.*;
import java.util.Hashtable;
/**
 * Insert the type's description here.
 * Creation date: (2002-1-15 15:18:12)
 * @author: Administrator
 */
public class DictUpdateReqServlet extends HttpServlet {
	String userName = null;
	String userPass = null;
/**
 * DictUpdateReqServlet constructor comment.
 */
public DictUpdateReqServlet() {
	super();
}
/**
 * service method comment.
 */
public void doGet(HttpServletRequest req, HttpServletResponse rep) throws ServletException, java.io.IOException {
	icbc.cmis.operation.SqlTool sqlTool = null;
	try{
		String password1 = (String) req.getParameter("password");
		String userName1 = (String) req.getParameter("userName");

		if (userName1 == null || !userName.trim().equals(userName))
		{
			tipMsg(rep,"ERROR in Server: 非法用户!");
			return;
		}

		if (password1 == null || !password1.trim().equals(userPass))
		{
			tipMsg(rep,"ERROR in Server: 非法用户!");
			return ;
		}
		
		
		String mark = (String)req.getParameter("queryStr");
		if(mark.trim().equals("error")){
			CmisConstance.initializeErrorMessageTable();
			tipMsg(rep,"initialization error message file ok!");
		}else if(mark.trim().equals("dbuser")){
			CmisConstance.initializePassVerifyTable();
			tipMsg(rep,"initialization database info file ok!");
		}else if(mark.trim().equals("dict")){
//			String userId = (String)CmisConstance.getParameterSettings().get("dbUserName");
//	   		String userPass = (String)CmisConstance.getParameterSettings().get("dbUserPass");
			sqlTool = new icbc.cmis.operation.SqlTool(new icbc.cmis.operation.DummyOperation());
//			sqlTool.getConn(userId,userPass);
			sqlTool.getConn("missign");
			java.util.Vector v = new java.util.Vector(3);
			v.add(0,"tab_code");
			v.add(1,"tab_type");
			v.add(2,"tab_owner");
			IndexedDataCollection result = new IndexedDataCollection();
			java.util.Enumeration e = CmisConstance.getDBUsers();
			for(;e.hasMoreElements();){
				String sysType = (String)e.nextElement();
				sqlTool.executeQueryFix(v,"mag_cache_tables","where TAB_OWNER='"+sysType.trim().toUpperCase()+"'",result);
			}
			sqlTool.closeconn();
			int count = 0;
			String str = "<tr>\n";
			for(int i = 0;i<result.getSize();i++){
				KeyedDataCollection kColl = (KeyedDataCollection)result.getElement(i);
				String tableName = (String)kColl.getValueAt("tab_code");
				String tableType = (String)kColl.getValueAt("tab_type");
				String tableOwer = (String)kColl.getValueAt("tab_owner");
				String optionValue = tableName+"|"+tableType+"|"+tableOwer;
				if(count != 3){
					str = str+"<td><input type=\"checkbox\" name=\"dictCheckBox\" value=\""+optionValue+"\"> "+tableName+" </td>\n";
					count++;
				}else{
					str = str+"</tr>\n<tr>\n";
					str = str+"<td><input type=\"checkbox\" name=\"dictCheckBox\" value=\""+optionValue+"\"> "+tableName+" </td>\n";
					count = 1;
				}
			}
			str = str+"</tr>";
			req.setAttribute("dictCheckBox",java.net.URLEncoder.encode(str));

			ServletContext servletcontext = getServletContext();
			RequestDispatcher requestdispatcher = servletcontext.getRequestDispatcher("/icbc/cmis/dictupdate.jsp");
			requestdispatcher.forward(req, rep);
		}
	}catch(Exception e){
		if(sqlTool != null){
			try{
				sqlTool.closeconn();
			}catch(Exception ee){}
		}
		String msg = e.getMessage();
		if(msg == null) msg = e.toString();
		tipMsg(rep,msg);
	}
}
/**
 * service method comment.
 */
public void doPost(HttpServletRequest req, HttpServletResponse rep) throws ServletException, java.io.IOException {
	try{
		String message = null;
		String password1 = (String) req.getParameter("password");
		String userName1 = (String) req.getParameter("userName");

		if (userName1 == null || !userName.trim().equals(userName))
		{
			tipMsg(rep,"ERROR in Server: 非法用户!");
			return;
		}

		if (password1 == null || !password1.trim().equals(userPass))
		{
			tipMsg(rep,"ERROR in Server: 非法用户!");
			return ;
		}
		
		String[] tables = (String[])req.getParameterValues("dictCheckBox");
		if(tables != null){
			icbc.cmis.service.GeneralSQLService sql = new icbc.cmis.service.GeneralSQLService();
			for(int i = 0;i<tables.length;i++){
				String optionValue = tables[i].trim();
				int idx = optionValue.indexOf("|");
				String tableName = optionValue.substring(0,idx);
				optionValue = optionValue.substring(idx+1,optionValue.length());
				idx = optionValue.indexOf("|");
				int tableType = Integer.valueOf(optionValue.substring(0,idx)).intValue();
				String tableOwner = optionValue.substring(idx+1,optionValue.length());
				try{
					sql.updateDictTable(tableName,tableOwner,tableType);
				}catch(Exception e){
					if(message == null) message = "Message:\nupdate tables[ "+tableName;
						else message = message+","+tableName;
				}
				
			}
		}
		if(message == null)
			message = "update dictionary tables OK!";
			
		tipMsg(rep,message);
			
	}catch(Exception e){
		tipMsg(rep,"ERROR: exception is:\n"+e.getMessage());
	}
}
/**
   @roseuid 3A51E04500FA
   */
public void init(ServletConfig sc)
{
	try
	{
		super.init(sc);
		userName = sc.getInitParameter("userName");
		userPass = sc.getInitParameter("userPass");
	}
	catch (Exception e)
	{
		log("Exception in init: " + e);
	}
	log("End of init");
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-3 19:51:52)
 * @param o javax.servlet.ServletOutputStream
 */
private void tipMsg( HttpServletResponse rep,String message)throws java.io.IOException   {
	ServletOutputStream o = rep.getOutputStream();
	o.println("<HEAD><H1><B>DictUpdateReqServlet</B></H1><HR></HEAD>");
	o.println("<BODY>");
	o.write(message.getBytes());	
	o.println("<BR><HR>");
	o.println("</BODY></HTML>");
	o.close();
}
}
