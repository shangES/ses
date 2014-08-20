package icbc.cmis.util;

import java.sql.*;
import icbc.missign.*;
import java.util.*;
import icbc.cmis.util.Decode;

public class PagesQuery extends util_QueryDAO {

  public PagesQuery() {
  }

  /**
   * 设置缓冲页数
   * @return int缓冲页数
   */
  public int getBufferPages() {
    return 5;
  }

  /**
   * 设置每页显示行数
   * @return int每页显示行数
   */
  public int getPageLines() {
    return 20;
  }

  /**
   * 返回查询结果字段个数
   * @return int 字段个数
   */
  public int getFieldNumber() {
    return 4;
  }

  /**
   * 返回查询结果的中文字段名称, ""表示不显示
   * @return String[]
   */
  public String[] getFieldNames() {
    String[] ret = {"编号","客户代码","客户全称",""};
    return ret;
  }

  /**
   * 返回各字段显示宽度，-1表示不指定宽度, 0表示不显示
   * @return 各字段显示宽度 或 null
   */
  public int[] getFieldsWidth() {
//    int[] ret = {-1,-1,-1,-1};
    int[] ret = {50,100,400,0};
    return ret;
  }

  /**
   * 指定用来提示确认的字段，1表示提示中用到的字段
   * @return 提示确认的字段
   */
  public int[] getAskFields(){
    int[] ret = {0,1,0,1};
    return ret;
  }

  /**
   * 设置对齐方式，0左对齐 1右对齐
   * @return 对齐方式
   */
  public int[] getFieldsAlign() {
    int[] ret = {0,1,0,0};
    return ret;
  }

  /**
   * 产生查询结果条数的SQL语句
   * 语句必须已 select count(*) from 开始
   * 可利用传入的数据库连接、人员、自定义参数组织SQL语句
   * @param conn  当前可用的数据库连接
   * @param employee 当前人员
   * @param paras 用户调用自定义参数
   * @return SQL语句
   * @throws Exception
   */
  public String getCountSQL(Connection conn, Employee employee, Hashtable paras) throws Exception {
    return "select count(*) from TA200011 where TA200011004 like ?";
  }

  /**
   * 产生查询SQL语句，
   * 语句建议符合以下结构之一：<br>
   * SELECT * FROM ( SELECT ROWNUM rnum,a.* FROM ( YOUR_QUERY_GOES_HERE -- including the order by ) a WHERE ROWNUM &lt;= ?) WHERE rnum &gt;= ?
   * select * from (select RANK() OVER (ORDER BY ……) rnk,…… FROM ……) WHERE rnk &lt;= ? AND rnk &gt;= ? <br>
   * 可利用传入的数据库连接、人员、自定义参数组织SQL语句
   * @param conn  当前可用的数据库连接
   * @param employee 当前人员
   * @param paras 用户调用自定义参数
   * @throws Exception
   * @return SQL语句
   */
  public String getQuerySQL(Connection conn, Employee employee, Hashtable paras) throws Exception {
    return "SELECT * FROM ( SELECT ROWNUM rnum,a.* FROM (select TA200011001,TA200011003,TA200011004 FROM TA200011 where TA200011004 like ?) a WHERE ROWNUM <= ?) WHERE rnum >= ?";
//    return "select * from (select RANK() OVER (ORDER BY TA200011001) rnk,TA200011001,TA200011003,TA200011004 FROM TA200011 where TA200011004 like ?) WHERE rnk <= ? AND rnk >= ?";
  }

  /**
   * 绑定查询变量，注意与SQL的配合
   * @param notCountQuery 计数查询时为true,数据查询时为false
   * @param pstmt PreparedStatement
   * @param paras 自定义参数表
   * @param begin 起始顺序号
   * @param end 中止顺序号
   * @throws Exception
   */
  public void setParameters(boolean notCountQuery,PreparedStatement pstmt,Hashtable paras,int begin, int end)  throws Exception {
    String ts = "%"+Decode.decode((String)paras.get("cname"))+"%";
    pstmt.setString(1,ts);
    if(notCountQuery) {
      pstmt.setInt(2,end);
      pstmt.setInt(3,begin);
    }
  }
}