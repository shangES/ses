package icbc.cmis.util;

import java.sql.*;

import oracle.jdbc.driver.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.CmisOperation;
import java.util.*;

import icbc.cmis.service.CmisDao;

/*************************************************************
 * 
 * <b>创建日期: </b> 2004-8-31><br>
 * <b>标题: </b>数据访问工具类<br>
 * <b>类描述: </b>主要解决数据库连接得不到释放的问题。原则要接口简单。<br>
 * <br>
 * <p>Copyright: Copyright (c)2004</p>
 * <p>Company: </p>
 * 
 * @author zhouxj
 * 
 * @version 1.25
 * 
 * @since 
 * 
 * @see 
 * 
 *************************************************************/

public class DBTools extends CmisDao {
	private String OpName="";	//调用的Op名	
	private boolean b_DBUtilLog=true;		//是否要保存DBUtilLog的统计信息
	private String s_DBStartTime="";		//调用DBUtil开始时间
	private String s_DBEndTime="";			//调用DBUtil结束时间
	private Vector result; //返回的结果集存放状态
	private String s_out_Msg; //返回存储过程信息,一般存贮返回信息
	private String s_out_sign; //返回存储过程信息,一般存储返回标志
	private Hashtable H_table; //返回的一条结果集存放
	private String dbUser;
	/**
	 * <b>构造函数</b><br>
	 * @param arg1
	 * @throws java.io.IOException
	 */
	public DBTools(CmisOperation arg1) throws java.io.IOException {
		super(arg1);
		OpName=arg1.getOperationName();
		this.dbUser = "cmis3";
	}

	public DBTools(CmisOperation arg1, String dbUser) throws java.io.IOException {
		super(arg1);
		OpName=arg1.getOperationName();
		this.dbUser = dbUser;
	}
	/**
	 * <b>功能描述: </b>提供执行函数型存储过程的简单接口<br>
	 * <p>输入参数只支持STRING,输出参数在后面.，只返回函数返回值,只有输入参数,支持两个输出参数.函数型存储过程返回值为VARCHAR2型
	 * o_flag       OUT   VARCHAR2,               --返回成功信息,可以用作返回标志值,0 成功 非0表示失败
	 * o_mesg    	OUT   VARCHAR2,               --第二个返回信息的参数
 	 * 调用语句例如:{?=call pack_templet.func_get_result( ?, ?, ?, ?, ?)}</p>
	 * 
 	 * @param Sqlstate	调用存储过程的语句
	 * @param inParamValue 输入参数值列表
	 * @return 存储过程返回值
	 * @throws Exception
	 *  
	 */
	public String executeFunctionMsg(String Sqlstate, Vector inParamValue)
		throws TranFailException {

		CallableStatement call = null;
		Hashtable ht;
		try {
            long D_start = System.currentTimeMillis();	//取开始时刻
			if (inParamValue == null)
				throw new Exception("请提供存储过程调用的输入参数信息");

			getConnection(this.dbUser);

			StringBuffer param = new StringBuffer(Sqlstate);

			call = conn.prepareCall(param.toString());
			
			int outIdx = inParamValue.size() +2;

			call.registerOutParameter(1, Types.VARCHAR);
			
			//now set the procedure input paramaters    
			for (int i = 0; i < inParamValue.size(); i++) {
				call.setString(2 + i, (String) inParamValue.get(i));
			}
	
			call.registerOutParameter(outIdx, Types.VARCHAR);
			call.registerOutParameter(outIdx+1, Types.VARCHAR);

			call.execute();

			String s_out_ProcSign = call.getString(1);
			this.s_out_sign= call.getString(outIdx);
			this.s_out_Msg = call.getString(outIdx+1);	 //返回存储过程信息,一般存贮返回信息

			this.H_table = null; //将第一条记录存入H_table中
			this.result = null;

			call.close();
			conn.commit();
			closeConnection();
			long D_end = System.currentTimeMillis();	//取结束时刻
			//writelog(Sqlstate,D_start,D_end);	//记录时间信息表
			return s_out_ProcSign;
		} catch (TranFailException e) {

			util_logTools.error(e);
			throw e;
		} catch (SQLException sqlex) {
			util_logTools.error(sqlex);
			throw new TranFailException("DBUtil067", //错误编码，使用者看
			getClass().getName() + ".executeFunction()", //出错位置,开发者看
			sqlex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + Sqlstate);
		} catch (Exception ex) {

			util_logTools.error(ex);
			throw new TranFailException("DBUtil068", //错误编码，使用者看
			getClass().getName() + ".executeFunction()", //出错位置,开发者看
			ex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + Sqlstate); //错误描述，给使用者看
		} finally {
			try {
				conn.rollback();
				if (call != null)
					call.close();
			} catch (Exception ee) {
			};
			closeConnection();
		}

	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p>输出sql语句的统计信息 </p>
	 * @param Sqlstate SQL语句的字符串
	 * @param D_start 开始时间
	 * @param D_end 结束时间
	 *  
	 */
/*
	private void writelog(String Sqlstate, long D_start, long D_end) throws SQLException, TranFailException {
		PreparedStatement stmt = null;
		try {
			if (this.b_DBUtilLog) {
				String s_start= DateTools.getDateTime(D_start,"yyyy-MM-dd HH:mm");
				String s_end= DateTools.getDateTime(D_end,"yyyy-MM-dd HH:mm");
				
				long l_time = DateTools.TimeBetween(D_start, D_end);
				
				System.out.print("调用Op："+this.OpName+"\nSQL语句" + Sqlstate +"\n开始时间:" +s_start+"\n结束时间："+s_end+"\n耗时毫秒:"+l_time);
				
				getConnection(this.dbUser);*/

//create table cmis3.ta440901 (		/*数据库资源监控表*/
//	OPNAME	varchar2(50),        	/*调用Op名*/
//	SQLSTATE	varchar2(1000),		/*SQL语句或者存储过程名称*/
//	STARTTIME	varchar2(16) ,	   	/*开始时间*/
//	ENDTIME	varchar2(16),	   	/*结束时间*/
//	ELAPSE		number(16)		/*耗时毫秒*/
//);                
/*	
				String s_sql ="insert into ta440901(OPNAME,SQLSTATE,STARTTIME,ENDTIME,ELAPSE) values(?,?,?,?,?)";
				stmt = conn.prepareStatement(s_sql);
				stmt.setString(1, this.OpName);
				stmt.setString(2, Sqlstate);
				stmt.setString(3, s_start);
				stmt.setString(4, s_end);
				stmt.setLong(5, l_time);
				
				int i_ret=stmt.executeUpdate();
				
				conn.commit();
				stmt.close();
				closeConnection();
					
			}
		} catch (SQLException sqlex) {
			conn.rollback();
			util_logTools.error(sqlex);
			throw new TranFailException("DBUtil077", //错误编码，使用者看
			getClass().getName() + ".writelog()", //出错位置,开发者看
			sqlex.getMessage(), //错误内容，开发者看
			"写log错误，请检查参数是否正确：" );
		} catch (Exception ex) {
			conn.rollback();
			util_logTools.error(ex);
			throw new TranFailException("DBUtil078", //错误编码，使用者看
			getClass().getName() + ".writelog()", //出错位置,开发者看
			ex.getMessage(), //错误内容，开发者看
			"写log错误，请检查参数是否正确：" ); //错误描述，给使用者看
		} finally {
			try {
		
			if (stmt != null)
				stmt.close();
			} catch (Exception ee) {
			};
			closeConnection();
		}
			
	}*/


	/**
	 * <b>功能描述: </b>提供执行函数型存储过程的简单接口<br>
	 * <p>输入参数只支持STRING,输出参数在后面.，只返回函数返回值,只有输入参数,没有输出参数.函数型存储过程返回值为VARCHAR2型
	 * 调用语句例如:{?=call pack_templet.func_get_result( ?, ?, ?, ?, ?)}</p>
	 * 
	 * @param Sqlstate	调用存储过程的语句
	 * @param inParamValue 输入参数值列表
	 * @return 存储过程返回的第二个参数，第二个返回信息的参数
	 * @throws Exception
	 *  
	 */
	public String executeFunction(String Sqlstate, Vector inParamValue)
		throws TranFailException {

		CallableStatement call = null;
		Hashtable ht;
		try {
			long D_start=System.currentTimeMillis();	//取开始时刻

			if (inParamValue == null)
				throw new Exception("请提供存储过程调用的输入参数信息");

			getConnection(this.dbUser);

			StringBuffer param = new StringBuffer(Sqlstate);

			call = conn.prepareCall(param.toString());

			call.registerOutParameter(1, Types.VARCHAR);
			
			//now set the procedure input paramaters    
			for (int i = 0; i < inParamValue.size(); i++) {
				call.setString(2 + i, (String) inParamValue.get(i));
			}
	
			call.execute();

			String s_out_ProcSign = call.getString(1);

			this.H_table = null; //将第一条记录存入H_table中
			this.result = null;

			call.close();
			conn.commit();
			closeConnection();
			this.s_out_sign = s_out_ProcSign; //返回存储过程信息,一般存贮返回信息
			this.s_out_Msg = s_out_ProcSign;

			long D_end=System.currentTimeMillis();	//取结束时刻
			//writelog(Sqlstate,D_start,D_end);	//记录时间信息表

			return s_out_ProcSign;
		} catch (TranFailException e) {
			util_logTools.error(e);
			throw e;
		} catch (SQLException sqlex) {

			util_logTools.error(sqlex);
			throw new TranFailException("DBUtil067", //错误编码，使用者看
			getClass().getName() + ".executeFunction()", //出错位置,开发者看
			sqlex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + Sqlstate);
		} catch (Exception ex) {
			util_logTools.error(ex);
			throw new TranFailException("DBUtil068", //错误编码，使用者看
			getClass().getName() + ".executeFunction()", //出错位置,开发者看
			ex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + Sqlstate); //错误描述，给使用者看
		} finally {
			try {
				conn.rollback();
				if (call != null)
					call.close();
			} catch (Exception ee) {
			};
			closeConnection();
		}

	}
	
	/**
	 * <b>功能描述: </b>提供执行存储过程的简单接口<br>
	 * <p>输入参数只支持STRING,输出参数在后面.不返回游标，只返回成功信息和整数的标志位</p>
	 * o_flag       OUT   VARCHAR2,               --返回成功信息,可以用作返回标志值,0 成功 非0表示失败
	 * o_msg		OUT   VARCHAR2,               --第二个返回信息的参数
	 * 
	 * @param Sqlstate	调用存储过程的语句
	 * @param inParamValue 输入参数值列表
	 * @return 存储过程返回的第二个参数，第二个返回信息的参数
	 * @throws Exception
	 *  
	 */
	public String executeProcedure(String Sqlstate, Vector inParamValue)
		throws TranFailException{

		CallableStatement call = null;
		Hashtable ht;
		try {
			long D_start=System.currentTimeMillis();	//取开始时刻

			if (inParamValue == null)
				throw new Exception("请提供存储过程调用的参数信息");

			getConnection(this.dbUser);

			StringBuffer param = new StringBuffer(Sqlstate);

			call = conn.prepareCall(param.toString());

			int outIdx = inParamValue.size() + 1;

			String outType = "";
			//now set the procedure input paramaters    
			for (int i = 0; i < inParamValue.size(); i++) {

				call.setString(1 + i, (String) inParamValue.get(i));

			}

			call.registerOutParameter(outIdx, Types.VARCHAR);
			call.registerOutParameter(outIdx + 1, Types.VARCHAR);

			call.execute();

			this.s_out_sign = call.getString(outIdx);
			this.s_out_Msg = call.getString(outIdx + 1);

			this.H_table = null; //将第一条记录存入H_table中
			result = null;

			call.close();
			conn.commit();
			closeConnection();

			long D_end=System.currentTimeMillis();	//取结束时刻
			//writelog(Sqlstate,D_start,D_end);	//记录时间信息表

			return s_out_Msg;
		} catch (TranFailException e) {

			util_logTools.error(e);
			throw e;
		} catch (SQLException sqlex) {

			util_logTools.error(sqlex);
			throw new TranFailException("DBUtil057", //错误编码，使用者看
			getClass().getName() + ".executeProcedure()", //出错位置,开发者看
			sqlex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + Sqlstate);
		} catch (Exception ex) {

			util_logTools.error(ex);
			throw new TranFailException("DBUtil058", //错误编码，使用者看
			getClass().getName() + ".executeProcedure()", //出错位置,开发者看
			ex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + Sqlstate); //错误描述，给使用者看
		} finally {
			try {
				conn.rollback();
				if (call != null)
					call.close();
			} catch (Exception ee) {
			};
			closeConnection();
		}

	}

	/**
	 * <b>功能描述: </b>提供执行存储过程的接口<br>
	 * <p>输入参数只支持STRING,输出参数在后面,最后一个参数为CURSOR类型,存放结果集 .</p>
	 * o_mesg       OUT   VARCHAR2,               --出错详细信息,,可以用作返回标志值,0 成功 非0表示失败
	 * o_retcount   OUT   VARCHAR2,               --返回记录数
	 * o_result   OUT   ref_dbtest                --返回结果集合
	 * 
	 * 
	 * @param Sqlstate	调用存储过程的语句
	 * @param inParamValue 输入参数值列表
	 * @param outParamType	输出参数
	 * @return 存储过程返回的第二个参数，一般是指记录的条数
	 * @throws Exception
	 *  
	 */
	public int executeProcedure(
		String Sqlstate,
		Vector inParamValue,
		Vector outParamName)
		throws TranFailException {

		CallableStatement call = null;
		Hashtable ht;
		try {
			long D_start=System.currentTimeMillis();	//取开始时刻

			if (inParamValue == null)
				throw new Exception("请提供存储过程调用的参数信息");

			getConnection(this.dbUser);

			StringBuffer param = new StringBuffer(Sqlstate);

			call = conn.prepareCall(param.toString());

			int outIdx = inParamValue.size() + 1;

			String outType = "";
			//now set the procedure input paramaters    
			for (int i = 0; i < inParamValue.size(); i++) {

				call.setString(1 + i, (String) inParamValue.get(i));

			}

			call.registerOutParameter(outIdx, Types.VARCHAR);
			call.registerOutParameter(outIdx + 1, Types.VARCHAR);
			call.registerOutParameter(outIdx + 2, OracleTypes.CURSOR);

			call.execute();
			result = new Vector();
			ResultSet rset = null;

			String s_out_ProcSign = call.getString(outIdx);
			String s_out_rowCount = call.getString(outIdx + 1);

			try {
				rset = (ResultSet) call.getObject(outIdx + 2);

				int i_count = 0;
				int maxRow = getOperation().getMaxRowNum(); // 取的记录最大限制值
				while (rset.next()) {
					i_count++;
					ht = new Hashtable();
					for (int i = 0; i < outParamName.size(); i++) {
						ht.put(
							(String) outParamName.get(i),
							this.replaceNullString(rset.getString(1 + i)));
					}
					if (i_count == 1) {
						this.H_table = ht; //将第一条记录存入H_table中
					}
					result.add(ht);

					if (maxRow != -1 && i_count >= maxRow) {
						util_logTools.warn(
							"数据记录大于maxRow:" + maxRow + "的设置!请调整条件缩小查找范围!");
						break;
					}
				}
			} catch (SQLException esql) {
				throw esql;
			}

			call.close();
			conn.commit();
			closeConnection();
			s_out_sign = s_out_ProcSign;
			s_out_Msg = s_out_ProcSign;

			long D_end=System.currentTimeMillis();	//取结束时刻
			//writelog(Sqlstate,D_start,D_end);	//记录时间信息表

			return Integer.valueOf(s_out_rowCount).intValue();
		} catch (TranFailException e) {

			util_logTools.error(e);
			throw e;
		} catch (SQLException sqlex) {

			util_logTools.error(sqlex);
			throw new TranFailException("DBUtil047", //错误编码，使用者看
			getClass().getName() + ".executeProcedure()", //出错位置,开发者看
			sqlex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + Sqlstate);
		} catch (Exception ex) {

			util_logTools.error(ex);
			throw new TranFailException("DBUtil048", //错误编码，使用者看
			getClass().getName() + ".executeProcedure()", //出错位置,开发者看
			ex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + Sqlstate); //错误描述，给使用者看
		} finally {
			try {
				conn.rollback();
				if (call != null)
					call.close();
			} catch (Exception ee) {
			};
			closeConnection();
		}

	}

	/**
	 * <b>功能描述: </b>提供执行存储过程的接口<br>
	 * <p>输入参数只支持STRING,输出参数在后面,最后一个参数为CURSOR类型,存放结果集 .</p>
	 * out_ProcSign	out varchar2,	可以用作返回标志值,0 成功 非0表示失败
	 * out_rowCount	out varchar2,
	 * out_ProcMsg  out varchar2,
	 * ret_dbtest 	out ref_dbtest,
	 * 
	 * 
	 * @param Sqlstate	调用存储过程的语句
	 * @param inParamValue 输入参数值列表
	
	 * @param outParamType	输出参数
	 * @return 存储过程返回的第二个参数，一般是指记录的条数
	 * @throws Exception
	 *  
	 */
	public int executeProcedureMsg(
		String Sqlstate,
		Vector inParamValue,
		Vector outParamName)
		throws TranFailException{

		CallableStatement call = null;
		Hashtable ht;
		try {
			long D_start=System.currentTimeMillis();	//取开始时刻

			if (inParamValue == null)
				throw new Exception("请提供存储过程调用的参数信息");

			getConnection(this.dbUser);

			StringBuffer param = new StringBuffer(Sqlstate);

			call = conn.prepareCall(param.toString());

			int outIdx = inParamValue.size() + 1;

			String outType = "";
			//now set the procedure input paramaters    
			for (int i = 0; i < inParamValue.size(); i++) {

				call.setString(1 + i, (String) inParamValue.get(i));

			}

			call.registerOutParameter(outIdx, Types.VARCHAR);
			call.registerOutParameter(outIdx + 1, Types.VARCHAR);
			call.registerOutParameter(outIdx + 2, Types.VARCHAR);
			call.registerOutParameter(outIdx + 3, OracleTypes.CURSOR);

			call.execute();
			result = new Vector();
			ResultSet rset = null;

			String s_out_ProcSign = call.getString(outIdx);
			String s_out_rowCount = call.getString(outIdx + 1);
			String s_out_ProcMsg = call.getString(outIdx + 2);

			try {
				rset = (ResultSet) call.getObject(outIdx + 3);

				int i_count = 0;
				int maxRow = getOperation().getMaxRowNum(); // 取的记录最大限制值
				while (rset.next()) {
					i_count++;
					ht = new Hashtable();
					for (int i = 0; i < outParamName.size(); i++) {
						ht.put(
							(String) outParamName.get(i),
							this.replaceNullString(rset.getString(1 + i)));
					}
					if (i_count == 1) {
						this.H_table = ht; //将第一条记录存入H_table中
					}
					result.add(ht);

					if (maxRow != -1 && i_count >= maxRow) {
						util_logTools.warn(
							"数据记录大于maxRow:" + maxRow + "的设置!请调整条件缩小查找范围!");
						break;
					}
				}
			} catch (SQLException esql) {
				throw esql;
			}

			call.close();
			conn.commit();
			closeConnection();
			s_out_Msg = s_out_ProcMsg;
			s_out_sign = s_out_ProcSign;

			long D_end=System.currentTimeMillis();	//取结束时刻
			//writelog(Sqlstate,D_start,D_end);	//记录时间信息表

			return Integer.valueOf(s_out_rowCount).intValue();
		} catch (TranFailException e) {
		
			util_logTools.error(e);
			throw e;
		} catch (SQLException sqlex) {
			util_logTools.error(sqlex);
			throw new TranFailException("DBUtil037", //错误编码，使用者看
			getClass().getName() + ".executeProcedureMsg()", //出错位置,开发者看
			sqlex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + Sqlstate);
		} catch (Exception ex) {

			util_logTools.error(ex);
			throw new TranFailException("DBUtil038", //错误编码，使用者看
			getClass().getName() + ".executeProcedureMsg()", //出错位置,开发者看
			ex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + Sqlstate); //错误描述，给使用者看
		} finally {
			try {
				conn.rollback();
				if (call != null)
					call.close();
			} catch (Exception ee) {
			};
			closeConnection();
		}

	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> 取结果集合</p>
	 * @return
	 *  
	 */
	public Vector getResult() {
		return result;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p>设置结果集合 </p>
	 * @param vector
	 *  
	 */
	private void setResult(Vector vector) {
		result = vector;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p>执行单一SQL语句的接口，只支持简单查询的SQL，只支持STRING类型的参数，
	 * 直接返回单一值的语句,可以从result中取后几个字段的值. </p>
	 * @param SQL 绑定变量的SQL
	 * @param vParam 参数值 
	 * @return 第一字段的值
	 *  
	 */
	public String executeSQL(String SQL, Vector vParam)
		throws TranFailException {
		String s_ret = "";
		//		String s_sql = SQL.toUpperCase();
		String s_sql = SQL;
		Vector v_field;
		PreparedStatement stmt = null;
		Hashtable ht;
		try {
			long D_start=System.currentTimeMillis();	//取开始时刻

			v_field = findField(s_sql);

			if (v_field == null) {
				util_logTools.error("找不到正确的字段集合，请检查SQL是否正确：" + SQL);
				throw new TranFailException("DBUtil005", //错误编码，使用者看
				getClass().getName() + ".executeSQL()", //出错位置,开发者看
				"找不到正确的字段集合，请检查SQL是否正确：" + SQL, //错误内容，开发者看
				"找不到正确的字段集合，请检查SQL是否正确：" + SQL);
			}

			getConnection(this.dbUser);

			ResultSet rs = null;
			stmt = conn.prepareStatement(s_sql);

			for (int i = 0; i < vParam.size(); i++) {
				String s_param = (String) vParam.get(i);
				stmt.setString(1 + i, s_param);
			}

			rs = stmt.executeQuery();
			result = new Vector();
			this.H_table = new Hashtable();
			int i_count = 0;
			while (rs.next()) {
				i_count++;
				s_ret = rs.getString(1);
				ht = new Hashtable();
				for (int i = 0; i < v_field.size(); i++) {

					ht.put(
						(String) v_field.get(i),
						this.replaceNullString(rs.getString(1 + i)));
				}
				if (i_count == 1) {
					this.H_table = ht; //将第一条记录存入H_table中
				}
				result.add(ht);

				break; //只添加一条记录
			}
			rs.close();
			stmt.close();
			closeConnection();
	
			long D_end=System.currentTimeMillis();	//取结束时刻
			//writelog(SQL,D_start,D_end);	//记录时间信息表

			return s_ret; //返回第一字段的值
		} catch (TranFailException e) {
			util_logTools.error(e);
			throw e;
		} catch (SQLException sqlex) {
			util_logTools.error(sqlex);
			throw new TranFailException("DBUtil007", //错误编码，使用者看
			getClass().getName() + ".executeSQL()", //出错位置,开发者看
			sqlex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + SQL);
		} catch (Exception ex) {
			util_logTools.error(ex);
			throw new TranFailException("DBUtil008", //错误编码，使用者看
			getClass().getName() + ".executeSQL()", //出错位置,开发者看
			ex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + SQL); //错误描述，给使用者看
		} finally {
			try {
//				conn.rollback();
				if (stmt != null)
					stmt.close();
			} catch (Exception ee) {
			};
			closeConnection();
		}

	}

	/**
	 * <b>功能描述: </b><br>
	 * <p>执行单一SQL语句的接口，只支持简单查询的SQL，只支持STRING类型的参数，
	 * 返回一个结果集合 .</p>
	 * @param SQL	绑定变量的SQL
	 * @param vParam 
	 * @return 返回的结果集
	 * @throws TranFailException
	 *  
	 */
	public Vector executeSQLResult(String SQL, Vector vParam)
		throws TranFailException {
		String s_ret = "";
		//		String s_sql = SQL.toUpperCase();
		String s_sql = SQL;
		Vector v_field;
		PreparedStatement stmt = null;
		Hashtable ht;
		try {
			long D_start=System.currentTimeMillis();	//取开始时刻
			
			v_field = findField(s_sql);

			if (v_field == null) {
				util_logTools.error("找不到正确的字段集合，请检查SQL是否正确：" + SQL);
				throw new TranFailException("DBUtil015", //错误编码，使用者看
				getClass().getName() + ".executeSQLResult()", //出错位置,开发者看
				"找不到正确的字段集合，请检查SQL是否正确：" + SQL, //错误内容，开发者看
				"找不到正确的字段集合，请检查SQL是否正确：" + SQL);
			}

			getConnection(this.dbUser);

			ResultSet rs = null;
			stmt = conn.prepareStatement(s_sql);

			for (int i = 0; i < vParam.size(); i++) {
				String s_param = (String) vParam.get(i);
				stmt.setString(1 + i, s_param);
			}

			rs = stmt.executeQuery();
			result = new Vector();
			int maxRow = getOperation().getMaxRowNum();
			int i_count = 0;
			while (rs.next()) {
				i_count++;
				ht = new Hashtable();
				for (int i = 0; i < v_field.size(); i++) {
					ht.put(
						(String) v_field.get(i),
						this.replaceNullString(rs.getString(1 + i)));
				}
				if (i_count == 1) {
					this.H_table = ht; //将第一条记录存入H_table中
				}
				result.add(ht);
				if (i_count > maxRow) {
					util_logTools.warn("数据记录大于maxRow的设置!");
					break;
				}
			}

			rs.close();
			stmt.close();
			closeConnection();

			long D_end=System.currentTimeMillis();	//取结束时刻
			//writelog(SQL,D_start,D_end);	//记录时间信息表

			return result;
		} catch (TranFailException e) {
			util_logTools.error(e);
			throw e;
		} catch (SQLException sqlex) {
			util_logTools.error(sqlex);
			throw new TranFailException("DBUtil017", //错误编码，使用者看
			getClass().getName() + ".executeSQLResult()", //出错位置,开发者看
			sqlex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + SQL);
		} catch (Exception ex) {
			util_logTools.error(ex);
			throw new TranFailException("DBUtil018", //错误编码，使用者看
			getClass().getName() + ".executeSQLResult()", //出错位置,开发者看
			ex.getMessage(), //错误内容，开发者看
			"语句错误，请检查SQL是否正确：" + SQL); //错误描述，给使用者看
		} finally {
			try {
//				conn.rollback();
				if (stmt != null)
					stmt.close();
			} catch (Exception ee) {
			};
			closeConnection();
		}

	}

	/**
		 * <b>功能描述: </b><br>
		 * <p>执行单一SQL语句的接口，只支持简单查询的SQL，只支持STRING类型的参数，
		 * 返回一个结果集合 .</p>
		 * @param SQL	绑定变量的SQL
		 * @param vParam 
		 * @return 返回的结果集
		 * @throws TranFailException
		 *  
		 */
		public Vector executeSQLResultNoLimit(String SQL, Vector vParam)
			throws TranFailException {
			String s_ret = "";
			//		String s_sql = SQL.toUpperCase();
			String s_sql = SQL;
			Vector v_field;
			PreparedStatement stmt = null;
			Hashtable ht;
			try {
				long D_start=System.currentTimeMillis();	//取开始时刻
			
				v_field = findField(s_sql);

				if (v_field == null) {
					util_logTools.error("找不到正确的字段集合，请检查SQL是否正确：" + SQL);
					throw new TranFailException("DBUtil015", //错误编码，使用者看
					getClass().getName() + ".executeSQLResult()", //出错位置,开发者看
					"找不到正确的字段集合，请检查SQL是否正确：" + SQL, //错误内容，开发者看
					"找不到正确的字段集合，请检查SQL是否正确：" + SQL);
				}

				getConnection(this.dbUser);

				ResultSet rs = null;
				stmt = conn.prepareStatement(s_sql);

				for (int i = 0; i < vParam.size(); i++) {
					String s_param = (String) vParam.get(i);
					stmt.setString(1 + i, s_param);
				}

				rs = stmt.executeQuery();
				result = new Vector();
				int maxRow = 500;
				int i_count = 0;
				while (rs.next()) {
					i_count++;
					ht = new Hashtable();
					for (int i = 0; i < v_field.size(); i++) {
						ht.put(
							(String) v_field.get(i),
							this.replaceNullString(rs.getString(1 + i)));
					}
					if (i_count == 1) {
						this.H_table = ht; //将第一条记录存入H_table中
					}
					result.add(ht);
					if (i_count > maxRow) {
						util_logTools.warn("数据记录大于maxRow的设置!");
						break;
					}
				}

				rs.close();
				stmt.close();
				closeConnection();

				long D_end=System.currentTimeMillis();	//取结束时刻
				//writelog(SQL,D_start,D_end);	//记录时间信息表

				return result;
			} catch (TranFailException e) {
				util_logTools.error(e);
				throw e;
			} catch (SQLException sqlex) {
				util_logTools.error(sqlex);
				throw new TranFailException("DBUtil017", //错误编码，使用者看
				getClass().getName() + ".executeSQLResult()", //出错位置,开发者看
				sqlex.getMessage(), //错误内容，开发者看
				"语句错误，请检查SQL是否正确：" + SQL);
			} catch (Exception ex) {
				util_logTools.error(ex);
				throw new TranFailException("DBUtil018", //错误编码，使用者看
				getClass().getName() + ".executeSQLResult()", //出错位置,开发者看
				ex.getMessage(), //错误内容，开发者看
				"语句错误，请检查SQL是否正确：" + SQL); //错误描述，给使用者看
			} finally {
				try {
//					conn.rollback();
					if (stmt != null)
						stmt.close();
				} catch (Exception ee) {
				};
				closeConnection();
			}

		}
		/**
		 * <b>功能描述: </b><br>
		 * <p>执行SQL语句的接口，支持多select查询的SQL，只支持STRING类型的参数，
		 * 返回一个结果集合 .</p>
		 * @param SQL	绑定变量的SQL
		 * @param vParam,vselect
		 * @return 返回的结果集
		 * @throws TranFailException
		 *  
		 */
		public Vector executeSQLResult(String SQL, Vector vParam,Vector vselect)
			throws TranFailException {
			String s_ret = "";
			String s_sql = SQL;
			Vector v_field;
			PreparedStatement stmt = null;
			Hashtable ht;
			try {
				long D_start=System.currentTimeMillis();	//取开始时刻
			
				v_field = vselect;

				if (v_field == null) {
					util_logTools.error("找不到正确的字段集合，请检查SQL是否正确：" + SQL);
					throw new TranFailException("DBUtil015", //错误编码，使用者看
					getClass().getName() + ".executeSQLResult()", //出错位置,开发者看
					"找不到正确的字段集合，请检查SQL是否正确：" + SQL, //错误内容，开发者看
					"找不到正确的字段集合，请检查SQL是否正确：" + SQL);
				}

				getConnection(this.dbUser);

				ResultSet rs = null;
				stmt = conn.prepareStatement(s_sql);

				for (int i = 0; i < vParam.size(); i++) {
					String s_param = (String) vParam.get(i);
					stmt.setString(1 + i, s_param);
				}

				rs = stmt.executeQuery();
				result = new Vector();
				int maxRow = getOperation().getMaxRowNum();
				int i_count = 0;
				while (rs.next()) {
					i_count++;
					ht = new Hashtable();
					for (int i = 0; i < v_field.size(); i++) {
						ht.put(
							(String) v_field.get(i),
							this.replaceNullString(rs.getString(1 + i)));
					}
					if (i_count == 1) {
						this.H_table = ht; //将第一条记录存入H_table中
					}
					result.add(ht);
					if (i_count > maxRow) {
						util_logTools.warn("数据记录大于maxRow的设置!");
						break;
					}
				}

				rs.close();
				stmt.close();
				closeConnection();

				long D_end=System.currentTimeMillis();	//取结束时刻
				//writelog(SQL,D_start,D_end);	//记录时间信息表

				return result;
			} catch (TranFailException e) {
				util_logTools.error(e);
				throw e;
			} catch (SQLException sqlex) {
				util_logTools.error(sqlex);
				throw new TranFailException("DBUtil017", //错误编码，使用者看
				getClass().getName() + ".executeSQLResult()", //出错位置,开发者看
				sqlex.getMessage(), //错误内容，开发者看
				"语句错误，请检查SQL是否正确：" + SQL);
			} catch (Exception ex) {
				util_logTools.error(ex);
				throw new TranFailException("DBUtil018", //错误编码，使用者看
				getClass().getName() + ".executeSQLResult()", //出错位置,开发者看
				ex.getMessage(), //错误内容，开发者看
				"语句错误，请检查SQL是否正确：" + SQL); //错误描述，给使用者看
			} finally {
				try {
					if (stmt != null)
						stmt.close();
				} catch (Exception ee) {
				};
				closeConnection();
			}

		}
	/**
	 * <b>功能描述: </b><br>
	 * <p>查找SQL中的在SELECT FROM之间的字段，然后存放到数组中返回 </p>
	 * @param s_sql
	 * @return
	 *  
	 */
	private Vector findField(String s_sql) throws Exception {
		Vector V_ret = new Vector();
		int i_sp = s_sql.toUpperCase().indexOf("SELECT");
		int i_wp = s_sql.toUpperCase().indexOf("FROM");
		if (i_sp == -1 || i_wp == -1)
			return null;
		String s_field = s_sql.substring(i_sp + 7, i_wp);

		int i_cp = -2;

		i_cp = s_field.indexOf(",");
		while (i_cp != -1) {

			String s_temp = s_field.substring(0, i_cp);
			V_ret.add(s_temp.trim());
			s_field = s_field.substring(i_cp + 1);
			i_cp = s_field.indexOf(",");
		}
		V_ret.add(s_field.trim());

		if (V_ret.size() == 0) {
			return null;
		} else {
			return V_ret;
		}

	}
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @return
	 *  
	 */
	public String getS_out_sign() {
		return s_out_sign;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param string
	 *  
	 */
	private void setS_out_sign(String string) {
		s_out_sign = string;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @return
	 *  
	 */
	public Hashtable getH_table() {
		return H_table;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param hashtable
	 *  
	 */
	public void setH_table(Hashtable hashtable) {
		H_table = hashtable;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @return
	 *  
	 */
	public String getS_out_Msg() {
		return s_out_Msg;
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param string
	 *  
	 */
	private void setS_out_Msg(String string) {
		s_out_Msg = string;
	}

    /**
     * <b>功能描述: </b><br>
     * <p>若字符串为空，替换为"" </p>
     * @param tmpStr
     * @return
     *  
     */
    
    private String replaceNullString(String tmpStr) {
        try {
            String a = (tmpStr == null) ? "" : tmpStr;
            return a;
        } catch (Exception ex) {
            return "";
        }
    }
}
