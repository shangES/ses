package icbc.cmis.flow.BA;

import java.net.URLEncoder;
import java.sql.*;
import java.util.*;
import oracle.jdbc.*;
import icbc.cmis.service.CmisDao;
import icbc.missign.Employee;
import icbc.cmis.base.*;

public class BA_approvedoDao1 extends CmisDao {

	
	public BA_approvedoDao1(icbc.cmis.operation.CmisOperation op) {
		super(op);	
		
	}

	private String killnull(String strin) {
		if (strin == null) {
			return "";
		} else {
			return strin;
		}
	}

	/**
	 * 生成页面片断
	 * @param ordercode 环节号
	 * @param spflag 细分环节
	 * @param tradetype 申请种类
	 * @return
	 * @throws TranFailException
	 */
	public ArrayList queryfragment(String ordercode, String spflag, String tradetype) throws TranFailException {
		ArrayList vret = new ArrayList();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			this.getConnection("cmis3");
			sql =
				"select fragment_url,fragment_init from mag_approve_content_show ,mag_approve_content_fragment "
					+ "where trade_type = ? and show_spflag = ? and show_code = ? and show_content = fragment_code and show_type = '0' "
					+ "order by show_order";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tradetype);
			pstmt.setString(2, spflag);
			pstmt.setString(3, ordercode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				HashMap hmap = new HashMap();
				String f_url = killnull(rs.getString("fragment_url"));
				String f_init = killnull(rs.getString("fragment_init"));
				if (!f_url.equals("")) {
					hmap.put("fragment_url", f_url);
					hmap.put("fragment_init", f_init);
					vret.add(hmap);
				}
				hmap = null;
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA", "BA_approvedoDao.queryfragment()", e.getMessage(), e.getMessage());
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

	/**
	 * 生成审批页按钮
	 * @param ordercode 环节号
	 * @param spflag 细分环节
	 * @param tradetype 申请种类
	 * @return
	 * @throws TranFailException
	 */
	public ArrayList querybutton(String ordercode, String spflag, String tradetype) throws TranFailException {
		ArrayList vret = new ArrayList();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			this.getConnection("cmis3");
			sql =
				"select button_gif,button_url from mag_approve_content_show ,mag_approve_content_button "
					+ "where trade_type = ? and show_spflag = ? and show_code = ? and show_content = button_code and show_type = '1' "
					+ "order by show_order";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tradetype);
			pstmt.setString(2, spflag);
			pstmt.setString(3, ordercode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				HashMap hmap = new HashMap();
				String b_gif = killnull(rs.getString("button_gif"));
				String b_url = killnull(rs.getString("button_url"));
				if (!b_gif.equals("") && !b_url.equals("")) {
					hmap.put("button_gif", b_gif);
					hmap.put("button_url", b_url);
					vret.add(hmap);
				}
				hmap = null;
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA", "BA_approvedoDao.querybutton()", e.getMessage(), e.getMessage());
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

	/**
	 * 根据流程种类代码，环节代码查询环节名称
	 * @param flowtype  流程种类代码
	 * @param ordercode 环节代码
	 * @return
	 * @throws TranFailException
	 */
	public String getordername(String flowtype, String ordercode) throws TranFailException {
		String str = "";
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			this.getConnection("cmis3");
			sql =
				"select role_name from mag_role_major_new where role_code = ? and role_type = (select kind_type from mag_kind where kind_code = ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ordercode);
			pstmt.setString(2, flowtype);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				str = rs.getString("role_name");
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA", "BA_approvedoDao.getordername()", e.getMessage(), e.getMessage());
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
		return str;
	}

	/**
	 * 生成调查资料页面链接
	 * @param tradetype
	 * @param entcode
	 * @param entname
	 * @param tradecode
	 * @return
	 * @throws TranFailException
	 */
	public String getinfourl(String tradetype, String entcode, String entname, String tradecode) throws TranFailException {
		String infourl = "";
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.CallableStatement stmt_call = null;
		String sql = null;
		try {
			this.getConnection("cmis3");
			sql =
				"select tabname,URLPrefix,entCode,entName,tradeCode,info01 "
					+ "from mag_approve_tab_info "
					+ "where applycode=? and infotype='0' ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tradetype);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String URLPrefix = killnull(rs.getString("URLPrefix"));
				String s_entCode = killnull(rs.getString("entCode"));
				String s_entName = killnull(rs.getString("entName"));
				String s_tradeCode = killnull(rs.getString("tradeCode"));
				String info01 = killnull(rs.getString("info01"));
				infourl =
					URLPrefix
						+ "&"
						+ s_entCode
						+ "="
						+ entcode
						+ "&"
						+ s_entName
						+ "="
						+ java.net.URLEncoder.encode(entname)
						+ "&"
						+ s_tradeCode
						+ "="
						+ java.net.URLEncoder.encode(tradecode)
						+ info01;

			}

			sql = "select tabname,infopro from mag_approve_tab_info where applycode=? and infotype='1' ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tradetype);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String infopro = killnull(rs.getString("infopro"));
				if (!infopro.equals("")) {
					stmt_call = conn.prepareCall("{call " + infopro + "(?,?,?,?)}");
					stmt_call.setString(1, entcode); //客户号
					stmt_call.setString(2, entname); //客户名
					stmt_call.setString(3, tradecode); //申请号
					stmt_call.registerOutParameter(4, OracleTypes.VARCHAR); //返回url
					stmt_call.execute();
					infourl = killnull(stmt_call.getString(4));
				}
			}

		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA", "BA_approvedoDao.getinfourl()", e.getMessage(), e.getMessage());
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
			if (stmt_call != null)
				try {
					stmt_call.close();
				} catch (Exception ex) {
				}
			closeConnection();
		}
		return infourl;
	}

	/**
	 * 调查资料tab名称
	 * @param tradetype
	 * @return
	 * @throws TranFailException
	 */
	public String getinfoname(String tradetype) throws TranFailException {
		String infotabname = "";
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			this.getConnection("cmis3");
			sql = "select tabname from mag_approve_tab_info where applycode=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tradetype);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				infotabname = killnull(rs.getString("tabname"));
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA", "BA_approvedoDao.getinfoname()", e.getMessage(), e.getMessage());
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
		return infotabname;
	}

	/**
	 * 判断是不是发起人
	 * @param entcode
	 * @param tradecode
	 * @param ordercode
	 * @param empareacode
	 * @param employeecode
	 * @return
	 * @throws TranFailException
	 */
	public String checkfirst(String entcode, String tradecode, String ordercode, String empareacode, String employeecode)
		throws TranFailException {
		String str = "0";
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			this.getConnection("cmis3");
			sql = "select process006,process007,process008 from mprocess_new where process001 = ? and process002=? and process005=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, entcode);
			pstmt.setString(2, tradecode);
			pstmt.setString(3, "1");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String process006 = killnull(rs.getString("process006"));
				String process007 = killnull(rs.getString("process007"));
				String process008 = killnull(rs.getString("process008"));
				if (process006.equals(ordercode) && process007.equals(empareacode) && process008.equals(employeecode)) {
					str = "1";
				}
			} else {
				str = "1";
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA", "BA_approvedoDao.checkfirst()", e.getMessage(), e.getMessage());
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
		return str;
	}

	/**
	 * 生成台帐信息页面链接
	 * @param approve_flowtype
	 * @param reurnurl
	 * @param webBasePath
	 * @param entcode_value
	 * @param entname_value
	 * @return
	 * @throws TranFailException
	 */
	public ArrayList getcrediturl(
		String approve_tradetype,
		String reurnurl,
		String webBasePath,
		String entcode_value,
		String entname_value)
		throws TranFailException {
		ArrayList vret = new ArrayList();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			this.getConnection("cmis3");
			sql =
				"select urlname,URLPrefix,entcode,entname,returnurl,credit001 from mag_approve_credit where creditcode in (select creditcode from mag_approve_credit_tab where flowcode = ? )";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, approve_tradetype);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String s_urlname = killnull(rs.getString("urlname"));
				String s_URLPrefix = killnull(rs.getString("URLPrefix"));
				String s_entcode = killnull(rs.getString("entcode"));
				String s_entname = killnull(rs.getString("entname"));
				String s_returnurl = killnull(rs.getString("returnurl"));
				String s_credit001 = killnull(rs.getString("credit001"));

				HashMap hmap = new HashMap();
				hmap.put("url_name", s_urlname);
				String url =
					webBasePath
						+ s_URLPrefix
						+ "&"
						+ s_entcode
						+ "="
						+ entcode_value
						+ "&"
						+ s_entname
						+ "="
						+ java.net.URLEncoder.encode(entname_value)
						+ "&"
						+ s_returnurl
						+ "="
						+ java.net.URLEncoder.encode(reurnurl)
						+ s_credit001;
				hmap.put("url_link", url);
				vret.add(hmap);
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA", "BA_approvedoDao.querylist_me()", e.getMessage(), e.getMessage());
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

	/**
	 * 生成扩展tab页以及其链接
	 * @param entcode 客户号
	 * @param entname 客户名
	 * @param tradecode 申请号
	 * @param tradetype 申请种类
	 * @param webBasePath
	 * @return
	 * @throws TranFailException
	 */
	public ArrayList makeextratab(String entcode, String entname, String tradecode, String tradetype, String webBasePath)
		throws TranFailException {
		ArrayList vret = new ArrayList();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.CallableStatement stmt_call = null;
		String sql = null;
		try {
			this.getConnection("cmis3");
			//生成定义的链接
			sql =
				"select taborder,tabname,urlprefix,entcode,entname,tradecode,extra001 "
					+ "from mag_approve_tab_extra "
					+ "where tradetype=? and proshow='0' order by taborder";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tradetype);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String s_taborder = killnull(rs.getString("taborder"));
				String s_tabname = killnull(rs.getString("tabname"));
				String s_urlprefix = killnull(rs.getString("urlprefix"));
				String s_entcode = killnull(rs.getString("entcode"));
				String s_entname = killnull(rs.getString("entname"));
				String s_tradecode = killnull(rs.getString("tradecode"));
				String s_extra001 = killnull(rs.getString("extra001"));

				HashMap hmap = new HashMap();
				hmap.put("taborder", s_taborder);
				hmap.put("tabname", s_tabname);
				String taburl =
					webBasePath
						+ s_urlprefix
						+ "&"
						+ s_entcode
						+ "="
						+ entcode
						+ "&"
						+ s_entname
						+ "="
						+ java.net.URLEncoder.encode(entname)
						+ "&"
						+ s_tradecode
						+ "="
						+ java.net.URLEncoder.encode(tradecode)
						+ s_extra001;
				hmap.put("taburl", taburl);
				vret.add(hmap);
			}

			//生成需要存储过程来生成的链接
			sql = "select taborder,tabname,prourl from mag_approve_tab_extra where tradetype=? and proshow='1' order by taborder";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tradetype);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				HashMap hmap = new HashMap();

				String s_taborder = killnull(rs.getString("taborder"));
				String s_tabname = killnull(rs.getString("tabname"));
				String s_prourl = killnull(rs.getString("prourl"));

				if (s_prourl.equals("")) {
					hmap.put("taborder", s_taborder);
					hmap.put("tabname", s_tabname);
					hmap.put("taburl", "");
					vret.add(hmap);
				} else {
					stmt_call = conn.prepareCall("{call " + s_prourl + "(?,?,?,?)}");
					stmt_call.setString(1, entcode); //客户号
					stmt_call.setString(2, entname); //客户名
					stmt_call.setString(3, tradecode); //申请号
					stmt_call.registerOutParameter(4, OracleTypes.VARCHAR); //返回url
					stmt_call.execute();
					String taburl = killnull(stmt_call.getString(4));
					hmap.put("taborder", s_taborder);
					hmap.put("tabname", s_tabname);
					if (!taburl.equals("")) {
						hmap.put("taburl", webBasePath + taburl);
						vret.add(hmap);
					} else {
						hmap.put("taburl", "");
					}

				}
			}

		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA", "BA_approvedoDao.makeextratab()", e.getMessage(), e.getMessage());
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
			if (stmt_call != null)
				try {
					stmt_call.close();
				} catch (Exception ex) {
				}
			closeConnection();
		}

		return vret;

	}

	/**
	 * 是否显示台帐信息页
	 * @param tradetype 申请种类
	 * @return
	 * @throws TranFailException
	 */
	public String isshowtab2(String tradetype) throws TranFailException {
		String strout = "0";
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			this.getConnection("cmis3");
			sql = "select SHOWTAB2 from mag_approve_tab_info where applycode = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tradetype);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				strout = killnull(rs.getString("SHOWTAB2"));
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA", "BA_approvedoDao.showtab2()", e.getMessage(), e.getMessage());
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
		return strout;
	}

}