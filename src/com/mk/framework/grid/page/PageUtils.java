package com.mk.framework.grid.page;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mk.framework.grid.GridServerHandler;

public class PageUtils {

	/**
	 * 获取PagedResult一个实例
	 * 
	 * @return
	 */
	public static PagedResult getDefaultPageResult() {
		return new PagedResult();
	}

	/**
	 * 设置当前页码
	 * 
	 * @param pagedResult
	 * @param pageno
	 */
	public static void setPageNo(PagedResult pagedResult, int pageno) {
		pagedResult.getPageInfo().setPageno(pageno);
	}

	/**
	 * 在Page设置总行数
	 * 
	 * @param pagedResult
	 * @param totalRows
	 */
	public static void setTotalRows(PagedResult pagedResult, int totalRows) {
		pagedResult.getPageInfo().setTotalrows(totalRows);
		pagedResult.getPageInfo().init();
		pagedResult.setRequirtSearchCount(false);
	}

	/**
	 * 在Page设置总行数，只在查询的时候生成记录数
	 * 
	 * @param pagedResult
	 * @param conn
	 * @param countSql
	 * @throws SQLException
	 */
	public static void setTotalRows(PagedResult pagedResult, JdbcTemplate jt, String countSql) {
		if (pagedResult.isRequirtSearchCount()) {
			setTotalRows(pagedResult, jt.queryForInt(countSql));
		} else {
			pagedResult.getPageInfo().init();
		}
	}

	/**
	 * 设置结果集
	 * 
	 * @param pagedResult
	 * @param dataList
	 */
	public static void setDataList(PagedResult pagedResult, List dataList) {
		pagedResult.setDataList(dataList);
	}

	/**
	 * 获取结果集的起始位置
	 * 
	 * @param pagedResult
	 * @param dataList
	 */
	public static int getFirstPostion(PagedResult pagedResult) {
		return pagedResult.getPageInfo().getStart();
	}

	/**
	 * 获取结果集的结束位置
	 * 
	 * @param pagedResult
	 * @param dataList
	 */
	public static int getEndPostion(PagedResult pagedResult) {
		return pagedResult.getPageInfo().getEnd();
	}

	/**
	 * 获取每页记录数
	 * 
	 * @param pagedResult
	 * @param dataList
	 */
	public static int getPageSize(PagedResult pagedResult) {
		return pagedResult.getPageInfo().getPagesize();
	}

	/**
	 * 获取增加分页功能的SQL语句
	 * 
	 * @param pagedResult
	 * @param query
	 * @return
	 */
	public static String getPagedSql(PagedResult pagedResult, String sql) {
		StringBuffer newSql = new StringBuffer();

		newSql.append(" SELECT ROWALL.*,ROWNUM FROM ");
		newSql.append(" (SELECT ROW_.*, ROWNUM AS NUM_  FROM ( ");
		newSql.append(sql);
		newSql.append(" ) ROW_ WHERE ROWNUM <=" + getEndPostion(pagedResult) + ") ROWALL ");
		newSql.append(" WHERE NUM_ > " + getFirstPostion(pagedResult));

		return newSql.toString();

	}

	/**
	 * 获取增加分页功能的SQL语句
	 * 
	 * @param pagedResult
	 * @param query
	 * @return
	 */
	public static String getPagedSql(String sql, int start, int end) {
		StringBuffer newSql = new StringBuffer();

		newSql.append(" SELECT ROWALL.*,ROWNUM FROM ");
		newSql.append(" (SELECT ROW_.*, ROWNUM AS NUM_  FROM ( ");
		newSql.append(sql);
		newSql.append(" ) ROW_ WHERE ROWNUM <=" + end + ") ROWALL ");
		newSql.append(" WHERE NUM_ > " + start);

		return newSql.toString();

	}

	public static void setTotalRows(GridServerHandler grid, int totalRows) {
		grid.setTotalRowNum(totalRows);
		grid.getPageInfo().init();

	}

	public static String getOraclePagedSql(String sql, GridServerHandler grid) {
		StringBuffer newSql = new StringBuffer();

		newSql.append(" SELECT ROWALL.*,ROWNUM FROM ");
		newSql.append(" (SELECT ROW_.*, ROWNUM AS NUM_  FROM ( ");
		newSql.append(sql);
		newSql.append(" ) ROW_ WHERE ROWNUM <=" + grid.getEndRowNum() + ") ROWALL ");
		newSql.append(" WHERE NUM_ > " + grid.getStartRowNum());

		return newSql.toString();
	}

	public static String getMySqlPagedSql(GridServerHandler grid) {
		String sql = " LIMIT " + grid.getStartRowNum() + ", " + grid.getEndRowNum();
		return sql;
	}
}
