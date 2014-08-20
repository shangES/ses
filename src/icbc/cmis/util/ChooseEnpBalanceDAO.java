/*
//updated by ChenJ 20030526 for prepareStmt

*/
package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.service.SQLStatement;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.genMsg;

import java.sql.*;
import java.util.Vector;
import java.util.Hashtable;
import icbc.missign.Employee;

public class ChooseEnpBalanceDAO extends CmisDao {
	//final static int QUERY_LIMIT = 200;

	public ChooseEnpBalanceDAO(icbc.cmis.operation.CmisOperation op) {
	super(op);
	}

	public int getCount(String queryType,Employee employee,String TA200361001,String TA200361008,String cond,Hashtable paras) throws TranFailException {
		String sql[] = {"",""};
		//String where = "";
		String where2 = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int ret = 0;
		String langCode = employee.getLangCode();	     //得到语言类型
			
		if(queryType.equals("QueryBalance")||queryType.equals("")){
		      int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
	    	  //String ecode = employee.getEmployeeCode();
	      	  int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
	      	  //String area = employee.getMdbSID();
	          switch (bankFlag) {
	        	case 4:
	          		where2 = where2 + "ta200011001 = ta200361002 and ta200361003 = '99' and ta200361001 = ? and ta200361118 = ? and ta200361008 "+ cond +" ? and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = ?) and TA200011059 = '1' ";
	          		//根据柜员加where
	          		//where2 = where2 + "and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = ?) and TA200011059 = '1' ";
	          		
	          		if(eclass == 8) {
	            		//where2 += (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = ? union all select ta200012001 from ta200012 where ta200012006 = ? )");
	          		  where2 = where2 + "and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = ? union all select ta200012001 from ta200012 where ta200012006 = ? ) ";

	          		}
	          		break;
	        	default:
	          		where2 = where2 + "ta200011001 = ta200361002 and ta200361003 = '99' and ta200361001 = ? and ta200361118 = ? and ta200361008 "+ cond +" ? and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = ?) and TA200011059 = '1' ";
	          	    break;
		      }
	    //输入已完成
		}
		
		sql[0] = "select count(*) from ta200011,ta200361 where " + where2;
		
		try{
			this.getConnection();
			//this.getConnection("cmis","cmis_connection");
			pstmt = conn.prepareStatement(sql[0]);
			int index_paras = 1;
					
			if(queryType.equals("QueryBalance")||queryType.equals("")){
		      int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
	    	  String ecode = employee.getEmployeeCode();
	      	  int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
	      	  String empArea = employee.getMdbSID();
	      	  String area = (String)paras.get("ChooseArea");			
	          switch (bankFlag) {
	        	case 4:
	          		//where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')");					          		
	              if(TA200361001.length() > 0) {
				     pstmt.setString(index_paras++,TA200361001);
			      } 
			      pstmt.setString(index_paras++,area);
			      if(TA200361008.length() > 0) {
				    pstmt.setString(index_paras++,TA200361008);
			      }
	              pstmt.setString(index_paras++,empArea);
	              if(eclass == 8) {
	            	pstmt.setString(index_paras++,ecode);
	            	pstmt.setString(index_paras++,ecode);
	            		//where2 += (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = ? union all select ta200012001 from ta200012 where ta200012006 = ? )");
	            		//pstmt.setString(index_paras++,ecode);          		
	            		//pstmt.setString(index_paras++,ecode);          		
	          	  }
	          	  break;
	        	default:
	          		//where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = ? connect by belong_bank = prior area_code))");
	          	  if(TA200361001.length() > 0) {
				     pstmt.setString(index_paras++,TA200361001);
			      }
			      //area = (String)paras.get("ChooseArea");
			      pstmt.setString(index_paras++,area);
			      if(TA200361008.length() > 0) {
				    pstmt.setString(index_paras++,TA200361008);
			      }
	              pstmt.setString(index_paras++,area);
	          	  break;
		      }
		   }		
		 rs = pstmt.executeQuery();
  		 rs.next();
		 ret = rs.getInt(1);
			
		}
		catch (Exception ex) {
			throw new TranFailException("cmisutil214","icbc.cmis.util.ChooseEnp3DAO",ex.getMessage() + sql,genMsg.getErrMsgByLang(langCode,"099993"));
		}
		finally {
			if(rs != null) try {rs.close();} catch (Exception ex) {};
			if(pstmt != null) try {pstmt.close();} catch (Exception ex) {};
			this.closeConnection();
		}
//		sql[1] = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ("
//					 + "select TA200011001, TA200011003, TA200011005, TA200011037 FROM ta200011 "
//					 + " where " + where2 + " ) a  WHERE ROWNUM <= ? ) WHERE rnum >= ?";

		return ret;
	}

	
	public Vector getData(String queryType,Employee employee,String TA200361001,String TA200361008,String cond,Hashtable paras,int begin, int end) throws TranFailException {
		String sql[] = {"",""};
		String where = "";
		String where2 = "";
		Vector ret = new Vector();		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String langCode = employee.getLangCode();	     //得到语言类型	

		if(queryType.equals("QueryBalance")||queryType.equals("")){
		      int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
	    	  //String ecode = employee.getEmployeeCode();
	      	  int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
	      	  //String area = employee.getMdbSID();
	      	  
	          switch (bankFlag) {
	        	case 4:
	          		where2 = where2 + "ta200011001 = ta200361002 and ta200361003 = '99' and ta200361001 = ? and ta200361118 = ? and ta200361008 "+ cond +" ?  and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = ?) and TA200011059 = '1' ";
	          		//根据柜员加where
	          		//where2 = where2 + "and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = ?) and TA200011059 = '1' ";
	          		
	          		if(eclass == 8) {
	            		//where2 += (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = ? union all select ta200012001 from ta200012 where ta200012006 = ? )");
	          		  where2 = where2 + "and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = ? )";	          		  //	"union all select ta200012001 from ta200012 where ta200012006 = ? ) ";

	          		}
	          		break;
	        	default:
	          		where2 = where2 + "ta200011001 = ta200361002 and ta200361003 = '99' and ta200361001 = ? and ta200361118 = ? and ta200361008 "+ cond +" ? and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = ?) and TA200011059 = '1' ";
	          		break;
		      }
	    //输入已完成
	    	  
		}
		sql[0] = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ("
					 + "select ta200011001,ta200011003,ta200011005,ta200361008,ta200011037 from ta200011,ta200361 "
					 + " where " + where2 + " ) a  WHERE ROWNUM <= ? ) WHERE rnum >= ?";

		
		try{
			this.getConnection();
			//this.getConnection("cmis","cmis_connection");
			pstmt = conn.prepareStatement(sql[0]);
			int index_paras = 1;
			
			if(queryType.equals("QueryBalance")||queryType.equals("")){		  
			  
			  
		      int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
	    	  String ecode = employee.getEmployeeCode();
	      	  int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
	      	  String empArea = employee.getMdbSID();
	      	  String area = (String)paras.get("ChooseArea");			
	          switch (bankFlag) {
	        	case 4:  
	          		//where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')");		          		
	          		pstmt.setString(index_paras++,TA200361001);
			        pstmt.setString(index_paras++,area);
			        pstmt.setString(index_paras++,TA200361008);
	          		pstmt.setString(index_paras++,area);
	          		if(eclass == 8) {
	          			
	            		//where2 += (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = ? union all select ta200012001 from ta200012 where ta200012006 = ? )");
	            		pstmt.setString(index_paras++,ecode);          		
	            		//pstmt.setString(index_paras++,ecode);          		
	          		}
	          		break;
	        	default:
	          		//where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = ? connect by belong_bank = prior area_code))");
	          		pstmt.setString(index_paras++,TA200361001);
			        pstmt.setString(index_paras++,area);
			        pstmt.setString(index_paras++,TA200361008);
	          		pstmt.setString(index_paras++,area); 
	          	break;
		      }
			}
		 pstmt.setInt(index_paras++,end);
  		 pstmt.setInt(index_paras++,begin);	      
	      
		 rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)};
				ret.add(row);
				i++;
			}
			
		}
		catch (Exception ex) {
			throw new TranFailException("cmisutil214","icbc.cmis.util.ChooseEnpBalanceDAO",ex.getMessage() + sql,genMsg.getErrMsgByLang(langCode,"099993"));
		}
		finally {
			if(rs != null) try {rs.close();} catch (Exception ex) {};
			if(pstmt != null) try {pstmt.close();} catch (Exception ex) {};
			this.closeConnection();
		}

		return ret;
	}
	
	
	
	
	
	
	
	
	
}