/*
 * 创建日期 2005-9-5
 * 进行押品种类的查询
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.util;
import icbc.cmis.service.CmisDao;
import icbc.cmis.base.TranFailException;
import java.sql.*;
import java.util.Vector;

public class ChoosePawnTypeDAO extends CmisDao {

	public ChoosePawnTypeDAO(icbc.cmis.operation.CmisOperation op) {
	super(op);
	}

	public String getPawnType(String pawn_code) throws TranFailException {
		int disable = pawn_code.length();
		String selecttable="";
		if(disable==1)
				selecttable="da470901004";
		if(disable==3)
				selecttable="da470901001";
		if(disable==5)
				selecttable="da470901002";
		if(disable==7)
				selecttable="da470901003";
		
		StringBuffer xml = new StringBuffer(1000);
		String sql = null;
		
		java.sql.PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			this.getConnection();
		    xml.append("<pawns ");
			//取得押品级别
			sql = "select dict_code,dict_name from "+selecttable+" where dict_code = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pawn_code);
			rs = pstmt.executeQuery();
			rs.next();
			xml.append(" currentCode='"+rs.getString(1)+"'");
			xml.append(" currentName='"+rs.getString(2)+"'");
			xml.append(" currentLevel='"+(rs.getString(1).length()/2+1)+"'>");
			//查询上下级押品类型
     		pstmt.close();
     		
     		if(disable==1)
			sql = "select dict_code,dict_name from da470901004 union "				+"select dict_code,dict_name from da470901001 where substr(dict_code,0,1)=?";
			if(disable==3)
			sql = "select dict_code,dict_name from da470901004 union  "
				+"select dict_code,dict_name from da470901001 union  "
				+"select dict_code,dict_name from da470901002 where substr(dict_code,0,3)=?";
			if(disable==5)
			sql = "select dict_code,dict_name from da470901004 union  "
				+"select dict_code,dict_name from da470901001 union  "
				+"select dict_code,dict_name from da470901002 union  "
				+"select dict_code,dict_name from da470901003 where substr(dict_code,0,5)=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pawn_code);
			rs = pstmt.executeQuery();

			int level = 9;
			xml.append("<level9>");
			while (rs.next()) {
				int tlevel = ((rs.getString(1).length())/2)+1;
				if(tlevel == 0) {
					if (!pawn_code.equals("00000000")) {
						continue;
					} else {
						tlevel = 8;
					}
				}
				if(tlevel != level) {
					xml.append("</level" + level + ">");
					level = tlevel;
					xml.append("<level" + level + ">");
				}
				xml.append("<pawn c='" + rs.getString(1) + "' n='" + rs.getString(2)+"' />");
			}
			xml.append("</level" + level + ">");
			xml.append("</pawns>");
		}
		catch (TranFailException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new TranFailException("cmisutil212","icbc.cmis.util.ChoosePawnTypeDAO",ex.getMessage() + sql,"产生押品类型列表错误！");
		}
		finally {
			if(rs != null) try {rs.close();} catch (Exception ex) {};
			if(pstmt != null) try {pstmt.close();} catch (Exception ex) {};
			this.closeConnection();
		}
		return xml.toString();
	}
	
	
}