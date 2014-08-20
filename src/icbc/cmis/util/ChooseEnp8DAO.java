package icbc.cmis.util;
import icbc.cmis.base.*;
import java.sql.*;
import icbc.cmis.util.*;
import java.util.*;
import java.io.*;
import icbc.cmis.operation.*; 
import icbc.cmis.second.pkg.*;
import java.io.IOException;
import oracle.jdbc.driver.*;
import oracle.jdbc.internal.OracleTypes;
import icbc.cmis.service.*;


public class ChooseEnp8DAO extends CmisDao {
	public ChooseEnp8DAO(CmisOperation op) {
		super(op);
	}

//	public String query(String customercode,String AreaCode, String EmployeeCode) throws TranFailException {
//						try {
//							
//							Vector v_parameter = new Vector(); //参数
//							Vector v_result = new Vector(); //返回结构
//							DBTools dbutil = new DBTools(this.getOperation());	
//							v_parameter.add(customercode);
//							v_parameter.add(EmployeeCode);
//							String sql =
//								"SELECT  1  FROM ta200012 WHERE ta200012001 = ? and ta200012003=? and rownum=1" ;
//							v_result = dbutil.executeSQLResult(sql, v_parameter);							
//						  if (v_result.size()==1){
//							Vector v_result1 = new Vector(); 
//							Vector v_parameter1 = new Vector(); //参数					      																											
//							v_parameter1.add(EmployeeCode);
//							v_parameter1.add(AreaCode);
//							v_parameter1.add(CmisConstance.getWorkDate("yyyyMMdd"));
//							v_parameter1.add(CmisConstance.getWorkDate("yyyyMMdd"));
//							 sql =
//								"SELECT  1  FROM pa200045  WHERE pa200045001 = ? and pa200045002=? and pa200045008=0 and pa200045004<=? and pa200045005>=? and  rownum=1" ;
//							v_result1 = dbutil.executeSQLResult(sql, v_parameter1);
//							if (v_result1.size()==1){
//							Vector v_result2 = new Vector(); 
//							Vector v_parameter2 = new Vector(); //参数
//							v_parameter2.add(EmployeeCode);
//							v_parameter2.add(customercode);
//							v_parameter2.add(CmisConstance.getWorkDate("yyyyMMdd"));
//							v_parameter2.add(CmisConstance.getWorkDate("yyyyMMdd"));
//							 sql =
//								"SELECT  1  FROM pa200045  WHERE pa200045001 = ? and pa200045003=? and pa200045008=1 and pa200045004<=? and pa200045005>=? and rownum=1" ;
//							 v_result2 = dbutil.executeSQLResult(sql, v_parameter2);
//							 if (v_result2.size() == 0) { 
//								   return  "0";  //是否有查询权限
//								 } else {
//								   return	"1";
//							  }
//                             
//							}else{
//							  return "1";
//							}
//						  }else{
//							  return "1";
//						  }
//						} catch (TranFailException ex) {
//							throw ex;
//						} catch (Exception ex) {
//							throw new TranFailException("ChooseEnp8DAO",
//							//错误编码，使用者看
//							getClass().getName() + ".query()", //出错位置,开发者看
//							"通用数据库工具调用失败！" + ex.toString(), //错误内容，开发者看
//							"无法进行查询！"); //错误描述，给使用者看;
//						}
//					}
	

	public String query(String customercode,String EmployeeCode,String AreaCode)throws TranFailException {
		 try {
			this.getConnection();
			String rs = "";
			String err_txt = "";
			CallableStatement cStmt =
				conn.prepareCall(
					"{call pack_employee_right.proc_employee_rihgt(?,?,?,?,?)}");
			cStmt.setString(1, customercode);
			cStmt.setString(2, EmployeeCode);
			cStmt.setString(3, AreaCode);
			cStmt.registerOutParameter(4, OracleTypes.VARCHAR);
			cStmt.registerOutParameter(5, OracleTypes.VARCHAR);
			cStmt.execute();
			rs = cStmt.getString(4);
			err_txt = cStmt.getString(5);
			conn.commit();
			cStmt.close();
			this.closeConnection();
			if (rs.equals("2"))
//				throw new TranFailException(
//					"ChooseEnp8DAO",
//					"ChooseEnp8DAO.query()",
//					err_txt,
//					"查询柜员权限失败");
					throw new MuiTranFailException("099995", "ChooseEnp8DAO.query(String customercode,String EmployeeCode,String AreaCode)",(String)this.getOperation().getSessionData("LangCode"));
                   
			return rs;
		} catch (TranFailException eee) {
			closeConnection();
			throw eee;
		} catch (Exception ee) {
			if (conn != null)
				closeConnection();
//			throw new TranFailException(
//				"ChooseEnp8DAO",
//				"ChooseEnp8DAO.query()",
//				ee.getMessage(),
//				"查询柜员权限失败");
		}
			throw new MuiTranFailException("099995", "ChooseEnp8DAO.query(String customercode,String EmployeeCode,String AreaCode)",(String)this.getOperation().getSessionData("LangCode"));
	}	
		
}