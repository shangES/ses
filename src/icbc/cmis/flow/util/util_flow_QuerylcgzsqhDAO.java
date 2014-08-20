
package icbc.cmis.flow.util;

import java.sql.*;
import icbc.missign.*;
import java.util.*;
import icbc.cmis.util.Decode;
import icbc.cmis.util.Util_MuiQueryDAO;


/*************************************************************
 * 
 * <b>创建日期: </b> 2005-10-25<br>
 * <b>标题: </b>拨备风险审批流程跟踪<br>
 * <b>类描述: </b>得到业务编号类<br>
 * <br>
 * <p>Copyright: Copyright (c)2005</p>
 * <p>Company: </p>
 * 
 * @author DongMiaoying
 * 
 * @version 
 * 
 * @since 
 * 
 * @see 
 * 
 *************************************************************/

public class util_flow_QuerylcgzsqhDAO extends Util_MuiQueryDAO {
	/**
	 * <b>构造函数</b><br>
	 * 
	 */
	public util_flow_QuerylcgzsqhDAO() {
		
	}
	/** 
			 * <b>功能描述: </b>设置缓冲页数<br>
			 * <p>  </p>
			 * @see icbc.cmis.rams.util.util_QueryDAO#getBufferPages()
			 * @return int 缓冲页数
			 * 
			 */
			public int getBufferPages() {
				return 10;
			}

			/** 
			 * <b>功能描述: </b>设置每页显示行数<br>
			 * <p>  </p>
			 * @see icbc.cmis.rams.util.util_QueryDAO#getPageLines()
			 * @return int 每页显示行数
			 * 
			 */
			public int getPageLines() {
				return 20;
			}

			/** 
			 * <b>功能描述: </b>返回查询结果字段个数<br>
			 * <p>  </p>
			 * @see icbc.cmis.rams.util.util_QueryDAO#getFieldNumber()
			 * @return int 字段个数
			 * 
			 */
			public int getFieldNumber() {
				return 3;
			}

			/** 
			 * <b>功能描述: </b>设置显示的字段<br>
			 * <p>  </p>
			 * @see icbc.cmis.rams.util.util_QueryDAO#getFieldNames()
			 * @return String[] 返回查询结果的中文字段名称
			 * 
			 */
			public String[] getFieldNames() {
				//String[] ret = { "序号", "申请号","流程种类" };
				String[] ret = { super.propertyResourceReader.getPublicStr("P000019"), super.propertyResourceReader.getPublicStr("P000027"),super.propertyResourceReader.getPublicStr("P000058") };
				return ret;
			}

			/** 
			 * <b>功能描述: </b>返回各字段显示宽度<br>
			 * <p>  -1表示不指定宽度,0表示不显示</p>
			 * @see icbc.cmis.rams.util.util_QueryDAO#getFieldsWidth()
			 * @return int[] 各字段显示宽度 或 null
			 * 
			 */
			public int[] getFieldsWidth() {
				int[] ret = { -1, -1, -1 };
				//int[] ret = {50,100,400,100,200};
				return ret;
			}

			/** 
			 * <b>功能描述: </b>产生查询结果条数的SQL语句<br>
			 * <p>语句必须已 select count(*) from 开始，可利用传入的数据库连接、人员、自定义参数组织SQL语句 </p>
			 * @see icbc.cmis.rams.util.util_QueryDAO#getCountSQL(java.sql.Connection, icbc.missign.Employee, java.util.Hashtable)
			 * @param conn 当前可用的数据库连接
			 * @param employee 当前人员
			 * @param paras 用户调用自定义参数
			 * @return 记数SQL语句
			 * @throws Exception
			 * 
			 */
			public String getCountSQL(
				Connection conn,
				Employee employee,
				Hashtable paras)
				throws Exception {

				String kh_code = (String) paras.get("kh_code"); //客户代码

				String employeecode = (String)paras.get("employeecode");//柜员代码
				String kind_type = (String)paras.get("kind_type");//流程大类
				String queryStr =
					"select count(distinct process002 ) from mprocess_new,mag_kind " +
					" where process001 = ? and process008=? and process003=kind_code and kind_type=?"  ;

				return queryStr;

			}

			/** 
			 * <b>功能描述: </b>产生查询SQL语句<br>
			 * <p>语句建议符合以下结构之一：<br>
			 * select * from (select RANK() OVER (ORDER BY ……) rnk,…… FROM ……) WHERE rnk &lt;= ? AND rnk &gt;= ? <br>
			 * select * from (select rownum rnk,…… FROM …… where …… and rownum &lt;= ?) WHERE rnk &gt;= ?
			 * 可利用传入的数据库连接、人员、自定义参数组织SQL语句
			 * </p>
			 * @see icbc.cmis.rams.util.util_QueryDAO#getQuerySQL(java.sql.Connection, icbc.missign.Employee, java.util.Hashtable)
			 * @param conn 当前可用的数据库连接
			 * @param employee  当前人员,在查询与柜员身份有关时有用
			 * @param paras 用户调用自定义参数
			 * @return 查询SQL语句
			 * @throws Exception
			 * 
			 */
			public String getQuerySQL(
				Connection conn,
				Employee employee,
				Hashtable paras)
				throws Exception {
					
					String kh_code = (String) paras.get("kh_code"); //客户代码
		
					String employeecode = (String)paras.get("employeecode");//柜员代码
					String kind_type = (String)paras.get("kind_type");//流程大类
				String queryStr = ""; //查询子句

				queryStr ="select distinct process002 ,kind_name" +
					"  from mprocess_new ,imag_kind" +
					"  where process001 = ?  and process008=?  and process003=kind_code and kind_type=? and lang_code='"+langCode+"'";
				
				String sql ="select *  from ( select   rownum rnk,  temp_tab.* from ( "
						+ queryStr
						+ ") temp_tab where rownum <= ? ) where  rnk >= ?";

				return sql;
			}

			/** 
			 * <b>功能描述: </b>绑定查询变量<br>
			 * <p>注意与SQL的配合 </p>
			 * @see icbc.cmis.rams.util.util_QueryDAO#setParameters(boolean, java.sql.PreparedStatement, java.util.Hashtable, int, int)
			 * @param notCountQuery 计数查询时为true,数据查询时为false
			 * @param pstmt PreparedStatement
			 * @param paras 自定义参数表
			 * @param begin 开始记录序号
			 * @param end 结束记录序号
			 * @throws Exception
			 * 
			 */
			public void setParameters(
				boolean notCountQuery,
				PreparedStatement pstmt,
				Hashtable paras,
				int begin,
				int end)
				throws Exception {
					String kh_code = (String) paras.get("kh_code"); //客户代码
	
					String employeecode = (String)paras.get("employeecode");//柜员代码
					String kind_type = (String)paras.get("kind_type");//流程大类
				int i = 0;
        
				pstmt.setString(++i, kh_code);
				pstmt.setString(++i, employeecode);
				pstmt.setString(++i, kind_type);
				if (notCountQuery) {
					pstmt.setInt(++i, end);
					pstmt.setInt(++i, begin);
				}
			}

			/** 
			 * <b>功能描述: </b>设置提示字段<br>
			 * <p>0表示不要提示，1表示这个字段要提示    </p>
			 * @see icbc.cmis.rams.util.util_QueryDAO#getAskFields()
			 * @return
			 * 
			 */
			public int[] getAskFields() {
				int[] ret = { 0, 0, 0 };

				return ret;
			}

			/** 
			 * <b>功能描述: </b>设置对齐方式，0左对齐 1右对齐<br>
			 * <p>  </p>
			 * @see icbc.cmis.rams.util.util_QueryDAO#getFieldsAlign()
			 * @return 对齐方式
			 * 
			 */
			public int[] getFieldsAlign() {
				int[] ret = { 0, 0, 0 };

				return ret;
			}

			/** 
			 * <b>功能描述: </b>设置需要从通用参数表取值的字段<br>
			 * <p>0代表不用从参数表取值，其他为对应的PARATYPE    </p>
			 * @see icbc.cmis.rams.util.util_QueryDAO#getFieldsGenPara()
			 * @return 通用参数表字段
			 * 
			 */
			public int[] getFieldsGenPara() {
				int[] ret = { 0, 0, 0};

				return ret;
			}
      /* （非 Javadoc）
       * @see icbc.cmis.util.Util_MuiQueryDAO#getMuiDef()
       */
      protected String getMuiDef() {
        // TODO 自动生成方法存根
        return "icbc.cmis.flow.FLOW_UTILE";
      }

}