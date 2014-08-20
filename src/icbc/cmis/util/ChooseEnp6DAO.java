/*
//updated by ChenJ 20030526 for prepareStmt

*/
package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.service.SQLStatement;
import icbc.cmis.base.TranFailException;
import java.sql.*;
import java.util.Vector;
import java.util.Hashtable;
import icbc.missign.Employee;
import icbc.cmis.util.QueryNormalEnp;

public class ChooseEnp6DAO extends CmisDao {
	//final static int QUERY_LIMIT = 200;

	public ChooseEnp6DAO(icbc.cmis.operation.CmisOperation op) {
	super(op);
	}

//	public String[] genSQL(String queryType,Employee employee,String TA200011001,String TA200011003,String TA200011005,String TA200011010,String TA200011011,String TA200011012,String TA200011014,String TA200011016,String TA200011031, Hashtable paras) throws TranFailException {
//		String sql[] = {"",""};
//		String where = "";
//		String where2 = "";
//
//		if(TA200011001.length() > 0) {
//			where += (" and TA200011001 like '%" + TA200011001 + "%'");
//		}
//		if(TA200011003.length() > 0) {
//			where += (" and TA200011003 like '%" + TA200011003 + "%'");
//		}
//		if(TA200011005.length() > 0) {
//			where += (" and TA200011005 like '" + TA200011005 + "%'");
//		}
//		if(TA200011010.length() > 0) {
//			where += (" and TA200011010 = '" + TA200011010 + "'");
//		}
//		if(TA200011011.length() > 0) {
//			where += (" and TA200011011 = '" + TA200011011 + "'");
//		}
//		if(TA200011012.length() > 0) {
//			where += (" and TA200011012 = '" + TA200011012 + "'");
//		}
//		if(TA200011014.length() > 0) {
//			where += (" and TA200011014 like '" + TA200011014.substring(0,1) + "%'");
//		}
//		if(TA200011016.length() > 0) {
//			where += (" and TA200011016 = '" + TA200011016 + "'");
//		}
//		if(TA200011031.length() > 0) {
//			where += (" and TA200011031 = '" + TA200011031 + "'");
//		}
//		//根据情况加where条件
//		try {
//			QueryNormalEnp qwhere = (QueryNormalEnp)Class.forName("icbc.cmis.util." + queryType).newInstance();
//			where2 = qwhere.getWhere(this.conn,employee,paras);
//			if (where2.startsWith("and ")) {
//				where2 = where2.substring(4);
//			}
//			if (where2.startsWith(" and ")) {
//				where2 = where2.substring(5);
//			}
//		}
//		catch (Exception ex) {
//			throw new TranFailException("cmisutil213","icbc.cmis.util.ChooseEnp3DAO",ex.getMessage(),"产生客户列表查询语句错误！");
//		}
//
//		if(where.length() > 0) {
//			where2 = where.substring(4) + " and " + where2;
//		}
//
//		sql[0] = "select count(*) from ta200011 where " + where2;
//
//		sql[1] = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ("
//					 + "select TA200011001, TA200011003, TA200011005, TA200011037 FROM ta200011 "
//					 + " where " + where2 + ") a  WHERE ";//ROWNUM <= ?) WHERE rnum >= ?";
//
////		System.out.println(sql[0]);
////		System.out.println(sql[1]);
//		return sql;
//	}


//	public String[] genSQL2(String queryType,Employee employee,String TA200011001,String TA200011003,String TA200011005,String TA200011010,String TA200011011,String TA200011012,String TA200011014,String TA200011016,String TA200011031, Hashtable paras) throws TranFailException {
//		String sql[] = {"",""};
//		String where = "";
//		String where2 = "";
//
//		if(TA200011001.length() > 0) {
//			//where += (" and TA200011001 like '%" + TA200011001 + "%'");
//			where += (" and TA200011001 like ? ");
//		}
//		if(TA200011003.length() > 0) {
//			//where += (" and TA200011003 like '%" + TA200011003 + "%'");
//			where += (" and TA200011003 like ? ");
//		}
//		if(TA200011005.length() > 0) {
//			//where += (" and TA200011005 like '" + TA200011005 + "%'");
//			where += (" and TA200011005 like ? ");
//		}
//		if(TA200011010.length() > 0) {
//			//where += (" and TA200011010 = '" + TA200011010 + "'");
//			where += (" and TA200011010 = ? ");
//		}
//		if(TA200011011.length() > 0) {
//			//where += (" and TA200011011 = '" + TA200011011 + "'");
//			where += (" and TA200011011 = ? ");
//		}
//		if(TA200011012.length() > 0) {
//			//where += (" and TA200011012 = '" + TA200011012 + "'");
//			where += (" and TA200011012 = ? ");
//		}
//		if(TA200011014.length() > 0) {
//			//where += (" and TA200011014 like '" + TA200011014.substring(0,1) + "%'");
//			where += (" and TA200011014 like ? ");
//		}
//		if(TA200011016.length() > 0) {
//			//where += (" and TA200011016 = '" + TA200011016 + "'");
//			where += (" and TA200011016 = ? ");
//		}
//		if(TA200011031.length() > 0) {
//			//where += (" and TA200011031 = '" + TA200011031 + "'");
//			where += (" and TA200011031 = ? ");
//		}
//		//根据情况加where条件
//		try {
//			QueryNormalEnp qwhere = (QueryNormalEnp)Class.forName("icbc.cmis.util." + queryType).newInstance();
//			where2 = qwhere.getWhere(this.conn,employee,paras);
//			if (where2.startsWith("and ")) {
//				where2 = where2.substring(4);
//			}
//			if (where2.startsWith(" and ")) {
//				where2 = where2.substring(5);
//			}
//		}
//		catch (Exception ex) {
//			throw new TranFailException("cmisutil213","icbc.cmis.util.ChooseEnp3DAO",ex.getMessage(),"产生客户列表查询语句错误！");
//		}
//
//		if(where.length() > 0) {
//			where2 = where.substring(4) + " and " + where2;
//		}
//
//		sql[0] = "select count(*) from ta200011 where " + where2;
//
//		sql[1] = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ("
//					 + "select TA200011001, TA200011003, TA200011005, TA200011037 FROM ta200011 "
//					 + " where " + where2 + " ) a  WHERE ROWNUM <= ? ) WHERE rnum >= ?";
//
////		System.out.println(sql[0]);
////		System.out.println(sql[1]);
//		return sql;
//	}


	public int getCount(String queryType,Employee employee,String TA200011001,String TA200011003,String TA200011005,String TA200011010,String TA200011011,String TA200011012,String TA200011014,String TA200011016,String TA200011031, Hashtable paras) throws TranFailException {
		String sql[] = {"",""};
		String where = "";
		String where2 = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int ret = 0;
		if(TA200011001.length() > 0) {
			//where += (" and TA200011001 like '%" + TA200011001 + "%'");
			where += (" and TA200011001 like ? ");
		}
		if(TA200011003.length() > 0) {
			//where += (" and TA200011003 like '%" + TA200011003 + "%'");
			where += (" and TA200011003 like ? ");
		}
		if(TA200011005.length() > 0) {
			//where += (" and TA200011005 like '" + TA200011005 + "%'");
			where += (" and TA200011005 like ? ");
		}
		if(TA200011010.length() > 0) {
			//where += (" and TA200011010 = '" + TA200011010 + "'");
			where += (" and TA200011010 = ? ");
		}
		if(TA200011011.length() > 0) {
			//where += (" and TA200011011 = '" + TA200011011 + "'");
			where += (" and TA200011011 = ? ");
		}
		if(TA200011012.length() > 0) {
			//where += (" and TA200011012 = '" + TA200011012 + "'");
			where += (" and TA200011012 = ? ");
		}
		if(TA200011014.length() > 0) {
			//where += (" and TA200011014 like '" + TA200011014.substring(0,1) + "%'");
			where += (" and TA200011014 like ? ");
		}
		if(TA200011016.length() > 0) {
			//where += (" and TA200011016 = '" + TA200011016 + "'");
			where += (" and TA200011016 = ? ");
		}
		if(TA200011031.length() > 0) {
			//where += (" and TA200011031 = '" + TA200011031 + "'");
			where += (" and TA200011031 = ? ");
		}

		if(queryType.equals("QueryNormalEnp")||queryType.equals("")){
		      int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
	    	  String ecode = employee.getEmployeeCode();
	      	  int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
	      	  String area = employee.getMdbSID();
	          switch (bankFlag) {
	        	case 0:
	          		break;
	        	case 4:
	          		//where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')");
	          		where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = ? )");
	          //根据柜员加where
	          		if(eclass == 8) {
	            		where2 += (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = ? union all select ta200012001 from ta200012 where ta200012006 = ? )");
	          		}
	          		break;
	        	default:
	          		where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = ? connect by belong_bank = prior area_code))");
	          	break;
		      }
	    //输入已完成
	    	  where2 += (" and TA200011059 = '1' and TA200011083 = 1");
	    	  if(where2.length() > 4 ) where2 = where2.substring(4);
				if (where2.startsWith("and ")) {
					where2 = where2.substring(4);
				}
				if (where2.startsWith(" and ")) {
					where2 = where2.substring(5);
				}

		}
		else if(queryType.equals("QueryAssurer")){
			
	     String area = (String)paras.get("assurearea");
	     String assuretype = (String)paras.get("assuretype");
	     String enpcode = (String)paras.get("enpcode");

	     String bankflag = "4";
	     if(paras.containsKey("bankFlag"))
	       bankflag = (String)paras.get("bankFlag");

	     if(bankflag.equals("4"))
	       //当所选地区是支行，则所选客户应该在用户所选的地区中
	       //where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')";
	       where2 += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = ? )";
    	 else if(bankflag.equals("3")||bankflag.equals("2"))
	       //如果所选地区是二级行或准一级行，所选客户应该在所选地区的下级行
//	       where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))";
	       where2 += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = ? connect by belong_bank = prior area_code))";	       
	     //如果所选地区是一级行，所选客户应该在所选地区的所有支行
	     else if(bankflag.equals("1"))
//	       where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))";
	       where2 += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = ? connect by belong_bank = prior area_code))";
	
	     //如果所选地区是总行的，不作地区控制
	     /*
	       如果是为表外的申请或合同选保证人，则要求信用等级为BBB级以上(含)的(如果信用等级为未评级的则取
	       比照信用等级)，且在客户大事记表中无大事的、在黑名单表中无记录的、在客户逃废债表中无记录的
	     */
	     if(!assuretype.equals("credit")){
	        where2 += " and ta200011001 not in (select ta200016001 from ta200016 ) and ta200011001 not in (select ta200017001 from ta200017)";
	        where2 += " and ta200011001 not in (select ta200018001 from ta200018) ";
	        /*and ((ta200011040 <= '40' and ta200011040>'00') ";
	        where2 += " or (ta200011040 = '00' and ta200011070 <='40' and ta200011070 >'00'))";*/
	     }
	
	      String hascreditrelation = "";
	
	      if(paras.containsKey("hasCreditRelation"))
	         hascreditrelation = (String)paras.get("hasCreditRelation");
	
	
	      if(hascreditrelation.equals("yes"))
	          where2 += " and ta200011059 = '1' ";
	      else
	        //客户状态为新增或建立信贷关系，且输入标志是完成的
	        where2 += " and ta200011059 in ('1','2')";
	

//	      where += " and ta200011083='1' and ta200011001 <> '"+enpcode+"' ";
	      where2 += " and ta200011083='1' and ta200011001 <> ? ";
	
	      if(where2.length()>0)
	        where2 = where2.substring(4);

			
			
			
			
		}
		
			if(where.length() > 0) {
				where2 = where.substring(4) + " and " + where2;
			}		
		
		sql[0] = "select count(*) from ta200011 where " + where2;
		
		try{
			this.getConnection();
			pstmt = conn.prepareStatement(sql[0]);
			int index_paras = 1;
			if(TA200011001.length() > 0) {
				//where += (" and TA200011001 like '%" + TA200011001 + "%'");
				pstmt.setString(index_paras++,"%"+ TA200011001 + "%");
			}
			if(TA200011003.length() > 0) {
				//where += (" and TA200011003 like '%" + TA200011003 + "%'");
				pstmt.setString(index_paras++,"%" + TA200011003 + "%");
			}
			if(TA200011005.length() > 0) {
				//where += (" and TA200011005 like '" + TA200011005 + "%'");
				pstmt.setString(index_paras++,TA200011005 + "%");
			}
			if(TA200011010.length() > 0) {
				//where += (" and TA200011010 = '" + TA200011010 + "'");
				pstmt.setString(index_paras++,TA200011010);
			}
			if(TA200011011.length() > 0) {
				//where += (" and TA200011011 = '" + TA200011011 + "'");
  			    pstmt.setString(index_paras++,TA200011011);
			}
			if(TA200011012.length() > 0) {
				//where += (" and TA200011012 = '" + TA200011012 + "'");
				pstmt.setString(index_paras++,TA200011012);
			}
			if(TA200011014.length() > 0) {
				//where += (" and TA200011014 like '" + TA200011014.substring(0,1) + "%'");
				pstmt.setString(index_paras++,TA200011014.substring(0,1) + "%");
			}
			if(TA200011016.length() > 0) {
				//where += (" and TA200011016 = '" + TA200011016 + "'");
				pstmt.setString(index_paras++,TA200011016);
			}
			if(TA200011031.length() > 0) {
				//where += (" and TA200011031 = '" + TA200011031 + "'");
				pstmt.setString(index_paras++,TA200011031 );
			}
			if(queryType.equals("QueryNormalEnp")||queryType.equals("")){
		      int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
	    	  String ecode = employee.getEmployeeCode();
	      	  int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
	      	  String area = employee.getMdbSID();			
	            switch (bankFlag) {
	        	case 0:
	          		break;
	        	case 4:
	          		//where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')");
					pstmt.setString(index_paras++,area);          		
	          		if(eclass == 8) {
	            		//where2 += (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = ? union all select ta200012001 from ta200012 where ta200012006 = ? )");
	            		pstmt.setString(index_paras++,ecode);          		
	            		pstmt.setString(index_paras++,ecode);          		
	          		}
	          		break;
	        	default:
	          		//where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = ? connect by belong_bank = prior area_code))");
	          		pstmt.setString(index_paras++,area); 
	          	break;
		      }
			}
		else if(queryType.equals("QueryAssurer")){
		
	     String area = (String)paras.get("assurearea");
	     String assuretype = (String)paras.get("assuretype");
	     String enpcode = (String)paras.get("enpcode");
	     String bankflag = "4";
	     if(paras.containsKey("bankFlag"))
	       bankflag = (String)paras.get("bankFlag");
	     if(bankflag.equals("4"))
	       //当所选地区是支行，则所选客户应该在用户所选的地区中
	       //where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')";
	          		pstmt.setString(index_paras++,area);
    	 else if(bankflag.equals("3")||bankflag.equals("2"))
	       //如果所选地区是二级行或准一级行，所选客户应该在所选地区的下级行
//	       where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))";
	          		pstmt.setString(index_paras++,area);
	     //如果所选地区是一级行，所选客户应该在所选地区的所有支行
	     else if(bankflag.equals("1"))
//	       where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))";
	          		pstmt.setString(index_paras++,area);
		 if(enpcode==null||enpcode.equals("null"))
		 	enpcode="00000";
      	  pstmt.setString(index_paras++,enpcode);	
		}
		 rs = pstmt.executeQuery();
  		 rs.next();
		 ret = rs.getInt(1);
			
		}
		catch (Exception ex) {
			throw new TranFailException("cmisutil214","icbc.cmis.util.ChooseEnp3DAO",ex.getMessage() + sql,"查询记录条数错误！");
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




//	public int getRecordCount (String sql) throws TranFailException {
//		Statement stmt = null;
//		ResultSet rs = null;
//		int ret = 0;
//		try {
//			this.getConnection();
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(sql);
//			icbc.cmis.FJ.CommonTools.cmisPrintln(sql);
//			rs.next();
//			ret = rs.getInt(1);
//		}
//		catch (TranFailException ex) {
//			throw ex;
//		}
//		catch (Exception ex) {
//			throw new TranFailException("cmisutil214","icbc.cmis.util.ChooseEnp3DAO",ex.getMessage() + sql,"查询记录条数错误！");
//		}
//		finally {
//			if(rs != null) try {rs.close();} catch (Exception ex) {};
//			if(stmt != null) try {stmt.close();} catch (Exception ex) {};
//			this.closeConnection();
//		}
//		return ret;
//	}

//	public Vector query(String sql,int begin, int end) throws TranFailException  {
//		Vector ret = new Vector();
//		//PreparedStatement pstmt = null;
//                Statement stmt = null;
//		ResultSet rs = null;
//		try {
//			this.getConnection();
//
////			pstmt = conn.prepareStatement(sql);
//			stmt = conn.createStatement();
////			pstmt.setInt(2,begin);
////			pstmt.setInt(1,end);
//			rs = stmt.executeQuery(sql);
//			int i = 0;
//			while (rs.next()) {
//				String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)};
////        String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),String.valueOf(begin + i)};
//				ret.add(row);
//				i++;
//			}
//		}
//		catch (TranFailException ex) {
//			throw ex;
//		}
//		catch (Exception ex) {
//			throw new TranFailException("cmisutil215","icbc.cmis.util.ChooseEnp3DAO",ex.getMessage() + sql,"产生客户列表错误！");
//		}
//		finally {
//			if(rs != null) try {rs.close();} catch (Exception ex) {};
////			if(pstmt != null) try {pstmt.close();} catch (Exception ex) {};
//			if(stmt != null) try {stmt.close();} catch (Exception ex) {};
//			this.closeConnection();
//		}
//		return ret;
//	}
	
	public Vector getData(String queryType,Employee employee,String TA200011001,String TA200011003,String TA200011005,String TA200011010,String TA200011011,String TA200011012,String TA200011014,String TA200011016,String TA200011031, Hashtable paras,int begin, int end) throws TranFailException {
		String sql[] = {"",""};
		String where = "";
		String where2 = "";
		Vector ret = new Vector();		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if(TA200011001.length() > 0) {
			//where += (" and TA200011001 like '%" + TA200011001 + "%'");
			where += (" and TA200011001 like ? ");
		}
		if(TA200011003.length() > 0) {
			//where += (" and TA200011003 like '%" + TA200011003 + "%'");
			where += (" and TA200011003 like ? ");
		}
		if(TA200011005.length() > 0) {
			//where += (" and TA200011005 like '" + TA200011005 + "%'");
			where += (" and TA200011005 like ? ");
		}
		if(TA200011010.length() > 0) {
			//where += (" and TA200011010 = '" + TA200011010 + "'");
			where += (" and TA200011010 = ? ");
		}
		if(TA200011011.length() > 0) {
			//where += (" and TA200011011 = '" + TA200011011 + "'");
			where += (" and TA200011011 = ? ");
		}
		if(TA200011012.length() > 0) {
			//where += (" and TA200011012 = '" + TA200011012 + "'");
			where += (" and TA200011012 = ? ");
		}
		if(TA200011014.length() > 0) {
			//where += (" and TA200011014 like '" + TA200011014.substring(0,1) + "%'");
			where += (" and TA200011014 like ? ");
		}
		if(TA200011016.length() > 0) {
			//where += (" and TA200011016 = '" + TA200011016 + "'");
			where += (" and TA200011016 = ? ");
		}
		if(TA200011031.length() > 0) {
			//where += (" and TA200011031 = '" + TA200011031 + "'");
			where += (" and TA200011031 = ? ");
		}

		if(queryType.equals("QueryNormalEnp")||queryType.equals("")){
		      int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
	    	  String ecode = employee.getEmployeeCode();
	      	  int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
	      	  String area = employee.getMdbSID();
	          switch (bankFlag) {
	        	case 0:
	          		break;
	        	case 4:
	          		//where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')");
	          		where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = ? )");
	          //根据柜员加where
	          		if(eclass == 8) {
	            		where2 += (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = ? union all select ta200012001 from ta200012 where ta200012006 = ? )");
	          		}
	          		break;
	        	default:
	          		where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = ? connect by belong_bank = prior area_code))");
	          	break;
		      }
	    //输入已完成
	    	  where2 += (" and TA200011059 = '1' and TA200011083 = 1");
	    	  if(where2.length() > 4 ) where2 = where2.substring(4);
				if (where2.startsWith("and ")) {
					where2 = where2.substring(4);
				}
				if (where2.startsWith(" and ")) {
					where2 = where2.substring(5);
				}
		}
		else if(queryType.equals("QueryAssurer")){
			
	     String area = (String)paras.get("assurearea");
	     String assuretype = (String)paras.get("assuretype");
	     String enpcode = (String)paras.get("enpcode");

	     String bankflag = "4";
	     if(paras.containsKey("bankFlag"))
	       bankflag = (String)paras.get("bankFlag");

	     if(bankflag.equals("4"))
	       //当所选地区是支行，则所选客户应该在用户所选的地区中
	       //where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')";
	       where2 += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = ? )";
    	 else if(bankflag.equals("3")||bankflag.equals("2"))
	       //如果所选地区是二级行或准一级行，所选客户应该在所选地区的下级行
//	       where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))";
	       where2 += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = ? connect by belong_bank = prior area_code))";	       
	     //如果所选地区是一级行，所选客户应该在所选地区的所有支行
	     else if(bankflag.equals("1"))
//	       where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))";
	       where2 += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = ? connect by belong_bank = prior area_code))";
	
	     //如果所选地区是总行的，不作地区控制
	     /*
	       如果是为表外的申请或合同选保证人，则要求信用等级为BBB级以上(含)的(如果信用等级为未评级的则取
	       比照信用等级)，且在客户大事记表中无大事的、在黑名单表中无记录的、在客户逃废债表中无记录的
	     */
	     if(!assuretype.equals("credit")){
	        where2 += " and ta200011001 not in (select ta200016001 from ta200016 ) and ta200011001 not in (select ta200017001 from ta200017)";
	        where2 += " and ta200011001 not in (select ta200018001 from ta200018) ";
	        /*and ((ta200011040 <= '40' and ta200011040>'00') ";
	        where2 += " or (ta200011040 = '00' and ta200011070 <='40' and ta200011070 >'00'))";*/
	     }
	
	      String hascreditrelation = "";
	
	      if(paras.containsKey("hasCreditRelation"))
	         hascreditrelation = (String)paras.get("hasCreditRelation");
	
	
	      if(hascreditrelation.equals("yes"))
	          where2 += " and ta200011059 = '1' ";
	      else
	        //客户状态为新增或建立信贷关系，且输入标志是完成的
	        where2 += " and ta200011059 in ('1','2')";
	

//	      where += " and ta200011083='1' and ta200011001 <> '"+enpcode+"' ";
	      where2 += " and ta200011083='1' and ta200011001 <> ? ";
	
	      if(where2.length()>0)
	        where2 = where2.substring(4);

			
			
			
			
		}

			if(where.length() > 0) {
				where2 = where.substring(4) + " and " + where2;
			}
		sql[0] = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ("
					 + "select TA200011001, TA200011003, TA200011005, TA200011037 FROM ta200011 "
					 + " where " + where2 + " ) a  WHERE ROWNUM <= ? ) WHERE rnum >= ?";

		
		try{
			this.getConnection();
			pstmt = conn.prepareStatement(sql[0]);
			int index_paras = 1;
			if(TA200011001.length() > 0) {
				//where += (" and TA200011001 like '%" + TA200011001 + "%'");
				pstmt.setString(index_paras++,"%"+ TA200011001 + "%");
			}
			if(TA200011003.length() > 0) {
				//where += (" and TA200011003 like '%" + TA200011003 + "%'");
				pstmt.setString(index_paras++,"%" + TA200011003 + "%");
			}
			if(TA200011005.length() > 0) {
				//where += (" and TA200011005 like '" + TA200011005 + "%'");
				pstmt.setString(index_paras++,TA200011005 + "%");
			}
			if(TA200011010.length() > 0) {
				//where += (" and TA200011010 = '" + TA200011010 + "'");
				pstmt.setString(index_paras++,TA200011010);
			}
			if(TA200011011.length() > 0) {
				//where += (" and TA200011011 = '" + TA200011011 + "'");
  			    pstmt.setString(index_paras++,TA200011011);
			}
			if(TA200011012.length() > 0) {
				//where += (" and TA200011012 = '" + TA200011012 + "'");
				pstmt.setString(index_paras++,TA200011012);
			}
			if(TA200011014.length() > 0) {
				//where += (" and TA200011014 like '" + TA200011014.substring(0,1) + "%'");
				pstmt.setString(index_paras++,TA200011014.substring(0,1) + "%");
			}
			if(TA200011016.length() > 0) {
				//where += (" and TA200011016 = '" + TA200011016 + "'");
				pstmt.setString(index_paras++,TA200011016);
			}
			if(TA200011031.length() > 0) {
				//where += (" and TA200011031 = '" + TA200011031 + "'");
				pstmt.setString(index_paras++,TA200011031 );
			}
			if(queryType.equals("QueryNormalEnp")||queryType.equals("")){
		      int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
	    	  String ecode = employee.getEmployeeCode();
	      	  int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
	      	  String area = employee.getMdbSID();			
	            switch (bankFlag) {
	        	case 0:
	          		break;
	        	case 4:
	          		//where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')");
					pstmt.setString(index_paras++,area);          		
	          		if(eclass == 8) {
	            		//where2 += (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = ? union all select ta200012001 from ta200012 where ta200012006 = ? )");
	            		pstmt.setString(index_paras++,ecode);          		
	            		pstmt.setString(index_paras++,ecode);          		
	          		}
	          		break;
	        	default:
	          		//where2 += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = ? connect by belong_bank = prior area_code))");
	          		pstmt.setString(index_paras++,area); 
	          	break;
		      }
			}
		else if(queryType.equals("QueryAssurer")){
		
	     String area = (String)paras.get("assurearea");
	     String assuretype = (String)paras.get("assuretype");
	     String enpcode = (String)paras.get("enpcode");
	     String bankflag = "4";
	     if(paras.containsKey("bankFlag"))
	       bankflag = (String)paras.get("bankFlag");
	     if(bankflag.equals("4"))
	       //当所选地区是支行，则所选客户应该在用户所选的地区中
	       //where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')";
	          		pstmt.setString(index_paras++,area);
    	 else if(bankflag.equals("3")||bankflag.equals("2"))
	       //如果所选地区是二级行或准一级行，所选客户应该在所选地区的下级行
//	       where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))";
	          		pstmt.setString(index_paras++,area);
	     //如果所选地区是一级行，所选客户应该在所选地区的所有支行
	     else if(bankflag.equals("1"))
//	       where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))";
	          		pstmt.setString(index_paras++,area);
		 if(enpcode==null||enpcode.equals("null"))
		 	enpcode="00000";
      	  pstmt.setString(index_paras++,enpcode);	
		}


		 pstmt.setInt(index_paras++,end);
  		 pstmt.setInt(index_paras++,begin);	      
	      
		 rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)};
//        String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),String.valueOf(begin + i)};
				ret.add(row);
				i++;
			}
			
		}
		catch (Exception ex) {
			throw new TranFailException("cmisutil214","icbc.cmis.util.ChooseEnp3DAO",ex.getMessage() + sql,"查询记录条数错误！");
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
	
	
	
	
	
	
	
	
	
}