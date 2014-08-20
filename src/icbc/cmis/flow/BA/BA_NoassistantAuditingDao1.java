package icbc.cmis.flow.BA;

import java.sql.*;
import java.util.*;
import oracle.jdbc.driver.*;
import icbc.cmis.service.CmisDao;
import icbc.missign.Employee;
import icbc.cmis.base.*;

/**
 * 待辅助审批列表查询
 * @author zjfh-zhangyz
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class BA_NoassistantAuditingDao1 extends CmisDao {
	String langCode = "zh_CN";
	public BA_NoassistantAuditingDao1(icbc.cmis.operation.CmisOperation op) {
		super(op);
		try {
			langCode = (String)op.getSessionData("LangCode");
		}
		catch (Exception ex) {langCode = "zh_CN";}
	}

	private String killnull(String strin) {
		if (strin == null) {
			return "";
		} else {
			return strin;
		}
	}

	/**
	 * 查询待辅助审批记录
	 * @param areacode
	 * @param employeecode
	 * @return
	 * @throws TranFailException
	 */
	public ArrayList querylist(String areacode, String employeecode) throws TranFailException {
		ArrayList vret = new ArrayList();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			this.getConnection("cmis3");
			sql =
				"select rownum rnum, advice001,(select ta200011003 from ta200011 where ta200011001 = advice001) advice001_name,"
					+ "advice002,advice003,"
					+ "process003,(select kind_name from imag_kind where kind_code = process003 and lang_code='"+langCode+"') process003_name ,"
					+ "process004,(select pa002 from pa200021 where pa001 = process004 and lang_code='"+langCode+"') process004_name,"
					+ "advice006 "
					+ "from madvice_ass,mprocess_new "
					+ "where advice001=process001 "
					+ "and advice002 = process002 "
					+ "and advice003 = process005 "
					+ "and advice004 = ? "
					+ "and advice005 = ? "
					+ "and process010 = '1'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, areacode);
			pstmt.setString(2, employeecode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				HashMap hmap = new HashMap();
				hmap.put("rnum", killnull(rs.getString("rnum")));
				hmap.put("advice001", killnull(rs.getString("advice001"))); //客户号
				hmap.put("advice001_name", killnull(rs.getString("advice001_name"))); //客户名称
				hmap.put("advice002", killnull(rs.getString("advice002"))); //申请号
				hmap.put("advice003", killnull(rs.getString("advice003"))); //序号
				hmap.put("process003", killnull(rs.getString("process003"))); //流程种类代码
				hmap.put("process003_name", killnull(rs.getString("process003_name"))); //流程种类
				hmap.put("process004", killnull(rs.getString("process004"))); //申请种类代码
				hmap.put("process004_name", killnull(rs.getString("process004_name"))); //申请种类
				hmap.put("advice006", killnull(rs.getString("advice006"))); //序号
				vret.add(hmap);
				hmap = null;
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA", "BA_NoassistantAuditingDao.querylist()", e.getMessage(), e.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			closeConnection();
		}
		return vret;
	}

}