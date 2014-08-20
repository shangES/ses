package icbc.cmis.flow.BA;

import java.sql.*;
import java.util.*;

import oracle.jdbc.*;
import icbc.cmis.service.CmisDao;
import icbc.cmis.base.*;

/**
 * 审批的待处理列表查询
 * 
 * @author zjfh-zhangyz
 * 
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

public class BA_approvelistDao extends CmisDao {

	public static String sLangCode = "zh_CN";

	public BA_approvelistDao(icbc.cmis.operation.CmisOperation op) {
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
	 * 查询待本人处理列表
	 * 
	 * @param employeecode
	 *            柜员号
	 * @param empareacode
	 *            柜员所属地区
	 * @param ordercode
	 *            当前环节代码
	 * @param runproc
	 *            是否调用存储过程查询未进流程业务，0否；1是
	 * @return 查询结果列表
	 * @throws TranFailException
	 */
	public ArrayList querylist_me(String employeecode, String empareacode,
			String ordercode, String runproc, String busitype)
			throws TranFailException {
		ArrayList vret = new ArrayList();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			this.getConnection("cmis3");
            ArrayList alist = new ArrayList();
            sql = "select def01,def02 from mag_approve_def where def_type =? order by to_number(def_order)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, busitype);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	HashMap hmap = new HashMap();
            	hmap.put("runsql", rs.getString(1));
            	hmap.put("runsqlbundlestr", rs.getString(2));
            	alist.add(hmap);
            }
            rs.close();
            if (alist.size()!=0) {
				//查询流程表
				sql = "select rownum rnum,process001,ta200011003 process001_name,process002,"
						+ "process003,kind_name process003_name, "
						+ "process004,pa002 process004_name, "
						+ "process005,process009 "
						+ "from mprocess_new, ta200011, imag_kind, ipa200021 "
						+ "where ta200011001 = process001 and kind_code = process003 and pa001 = process004 "
						+ "and process004 in(select distinct pa001 from ipa200021 where pa004 = ? ) "
						+ "and process006 = ? and process007 = ? and process008 = ? and ( process009 = '0' or process009 = '1' or process009 = '2') and imag_kind.LANG_CODE=? and ipa200021.LANG_CODE=? "
						;
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, busitype);
				pstmt.setString(2, ordercode);
				pstmt.setString(3, empareacode);
				pstmt.setString(4, employeecode);
				pstmt.setString(5, this.sLangCode);
				pstmt.setString(6, this.sLangCode);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					HashMap hmap = new HashMap();
					hmap.put("rnum", killnull(rs.getString("rnum")));
					hmap.put("process001", killnull(rs.getString("process001"))); //客户号
					hmap.put("process001_name", killnull(rs
							.getString("process001_name"))); //客户名称
					hmap.put("process002", killnull(rs.getString("process002"))); //申请号
					hmap.put("process003", killnull(rs.getString("process003"))); //流程种类代码
					hmap.put("process003_name", killnull(rs
							.getString("process003_name"))); //流程种类
					hmap.put("process004", killnull(rs.getString("process004"))); //申请种类代码
					hmap.put("process004_name", killnull(rs
							.getString("process004_name"))); //申请种类
					hmap.put("process005", killnull(rs.getString("process005"))); //序号
					hmap.put("process009", killnull(rs.getString("process009"))); //处理状态
					vret.add(hmap);
				}
            } else {
            	HashMap hmapvar = new HashMap();
            	hmapvar.put("busitype", busitype);
            	hmapvar.put("ordercode", ordercode);
            	hmapvar.put("empareacode", empareacode);
            	hmapvar.put("langcode", this.sLangCode);
            	hmapvar.put("employeecode", employeecode);
            	for(int i=0;i<alist.size();i++){
            		HashMap hmapsql = (HashMap)alist.get(i);
            		String runsql = (String)hmapsql.get("runsql");
            		String runsqlbundlestr = (String)hmapsql.get("runsqlbundlestr");
            		if (runsql.equals("")) {
            			continue;
            		}
            		pstmt = conn.prepareStatement(runsql);
            		int j=1;
            		StringTokenizer token = new StringTokenizer(runsqlbundlestr,"|");
            		while (token.hasMoreTokens()) {
            			pstmt.setString(j++, (String)hmapvar.get(token.nextToken()));
            		}
            		rs = pstmt.executeQuery();
            		while (rs.next()) {
    					HashMap<String,String> hmap = new HashMap<String,String>();
    					hmap.put("rnum", killnull(rs.getString("rnum")));
    					hmap.put("process001", killnull(rs.getString("process001"))); //客户号
    					hmap.put("process001_name", killnull(rs
    							.getString("process001_name"))); //客户名称
    					hmap.put("process002", killnull(rs.getString("process002"))); //申请号
    					hmap.put("process003", killnull(rs.getString("process003"))); //流程种类代码
    					hmap.put("process003_name", killnull(rs
    							.getString("process003_name"))); //流程种类
    					hmap.put("process004", killnull(rs.getString("process004"))); //申请种类代码
    					hmap.put("process004_name", killnull(rs
    							.getString("process004_name"))); //申请种类
    					hmap.put("process005", killnull(rs.getString("process005"))); //序号
    					hmap.put("process009", killnull(rs.getString("process009"))); //处理状态
    					vret.add(hmap);
            		}
            		rs.close();
            	}
            	
            }
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approvelistDao.querylist_me()", e.getMessage(), e
							.getMessage());
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
	 * 查询待本行处理的审批列表
	 * 
	 * @param empareacode
	 *            柜员所属地区
	 * @param employeecode
	 *            柜员号码
	 * @param ordercode
	 *            当前环节代码
	 * @return
	 * @throws TranFailException
	 */
	public ArrayList querylist_us(String empareacode, String employeecode,
			String ordercode, String runproc, String busitype)
			throws TranFailException {
		ArrayList vret = new ArrayList();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.CallableStatement stmt_call = null;
		String sql = null;
		try {
			this.getConnection("cmis3");
            ArrayList alist = new ArrayList();
            sql = "select def03,def04 from mag_approve_def where def_type =? order by to_number(def_order)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, busitype);
            rs = pstmt.executeQuery();
            while (rs.next()) {
            	HashMap hmap = new HashMap();
            	hmap.put("runsql", rs.getString(1));
            	hmap.put("runsqlbundlestr", rs.getString(2));
            	alist.add(hmap);
            }
            rs.close();

            if (alist.size()==0) {
				//查询流程表
				sql = "select rownum rnum,process001,ta200011003 process001_name,process002,"
						+ "process003,kind_name process003_name, "
						+ "process004,pa002 process004_name, "
						+ "process005,process009 "
						+ "from mprocess_new, ta200011, imag_kind, ipa200021 "
						+ "where ta200011001 = process001 and kind_code = process003 and pa001 = process004 "
						+ "and process004 in(select distinct pa001 from ipa200021 where pa004 = ? ) "
						+ "and process006 = ? and process007 = ? and (process008 is null or process008='') and process009 = '0' and imag_kind.LANG_CODE=? and ipa200021.LANG_CODE=? "
						;
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, busitype);
				pstmt.setString(2, ordercode);
				pstmt.setString(3, empareacode);
				pstmt.setString(4, this.sLangCode);
				pstmt.setString(5, this.sLangCode);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					HashMap hmap = new HashMap();
					hmap.put("rnum", killnull(rs.getString("rnum")));
					hmap.put("process001", killnull(rs.getString("process001"))); //客户号
					hmap.put("process001_name", killnull(rs
							.getString("process001_name"))); //客户名称
					hmap.put("process002", killnull(rs.getString("process002"))); //申请号
					hmap.put("process003", killnull(rs.getString("process003"))); //流程种类代码
					hmap.put("process003_name", killnull(rs
							.getString("process003_name"))); //流程种类
					hmap.put("process004", killnull(rs.getString("process004"))); //申请种类代码
					hmap.put("process004_name", killnull(rs
							.getString("process004_name"))); //申请种类
					hmap.put("process005", killnull(rs.getString("process005"))); //序号
					hmap.put("process009", killnull(rs.getString("process009"))); //处理状态
					vret.add(hmap);
				}
            }else {
            	HashMap hmapvar = new HashMap();
            	hmapvar.put("busitype", busitype);
            	hmapvar.put("ordercode", ordercode);
            	hmapvar.put("empareacode", empareacode);
            	hmapvar.put("langcode", this.sLangCode);
            	hmapvar.put("employeecode", employeecode);
            	for(int i=0;i<alist.size();i++){
            		HashMap hmapsql = (HashMap)alist.get(i);
            		String runsql = (String)hmapsql.get("runsql");
            		String runsqlbundlestr = (String)hmapsql.get("runsqlbundlestr");
            		if (runsql.equals("")) {
            			continue;
            		}
            		pstmt = conn.prepareStatement(runsql);
            		int j=1;
            		StringTokenizer token = new StringTokenizer(runsqlbundlestr,"|");
            		while (token.hasMoreTokens()) {
            			pstmt.setString(j++, (String)hmapvar.get(token.nextToken()));
            		}
            		rs = pstmt.executeQuery();
            		while (rs.next()) {
    					HashMap<String,String> hmap = new HashMap<String,String>();
    					hmap.put("rnum", killnull(rs.getString("rnum")));
    					hmap.put("process001", killnull(rs.getString("process001"))); //客户号
    					hmap.put("process001_name", killnull(rs
    							.getString("process001_name"))); //客户名称
    					hmap.put("process002", killnull(rs.getString("process002"))); //申请号
    					hmap.put("process003", killnull(rs.getString("process003"))); //流程种类代码
    					hmap.put("process003_name", killnull(rs
    							.getString("process003_name"))); //流程种类
    					hmap.put("process004", killnull(rs.getString("process004"))); //申请种类代码
    					hmap.put("process004_name", killnull(rs
    							.getString("process004_name"))); //申请种类
    					hmap.put("process005", killnull(rs.getString("process005"))); //序号
    					hmap.put("process009", killnull(rs.getString("process009"))); //处理状态
    					vret.add(hmap);
            		}
            		rs.close();
            	}
            }
			//调用存储过程查询
			if (runproc.equals("1")) {
				//查询接口
				sql = "select interface001 from mag_approve_interface "
						+ "where tradecode in(select distinct pa001 from ipa200021 where pa004 = ? ) "
						+ "and interface001 is not null";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, busitype);
				rs = pstmt.executeQuery();

				HashMap interfacemap = new HashMap();
				int icount = 1;
				while (rs.next()) {
					interfacemap.put(icount++ + "", rs
							.getString("interface001"));
				}
				int isize = interfacemap.size();
				int countbegin = vret.size() + 1;

				//逐一调用接口
				for (int i = 1; i <= isize; i++) {
					stmt_call = conn.prepareCall("{call "
							+ (String) interfacemap.get(i + "")
							+ "(?,?,?,?,?)}");
					String temo = (String) interfacemap.get(i + "");
					stmt_call.setString(1, empareacode); //地区号
					stmt_call.setString(2, employeecode); //柜员号
					stmt_call.registerOutParameter(3, OracleTypes.CURSOR); //返回游标
					stmt_call.registerOutParameter(4, OracleTypes.VARCHAR); //返回标志,0正确，－1失败
					stmt_call.registerOutParameter(5, OracleTypes.VARCHAR); //返回信息
					stmt_call.execute();
					rs = (ResultSet) stmt_call.getObject(3);
					String str_flag = stmt_call.getString(4);
					String str_msg = stmt_call.getString(5);
					while (rs.next()) {
						HashMap hmap = new HashMap();
						hmap.put("rnum", countbegin++ + "");
						hmap.put("process001", killnull(rs.getString("a1"))); //客户号
						hmap.put("process001_name",
								killnull(rs.getString("a2"))); //客户名称
						hmap.put("process002", killnull(rs.getString("a3"))); //申请号
						hmap.put("process003", killnull(rs.getString("a4"))); //流程种类代码
						hmap.put("process003_name",
								killnull(rs.getString("a5"))); //流程种类
						hmap.put("process004", killnull(rs.getString("a6"))); //申请种类代码
						hmap.put("process004_name",
								killnull(rs.getString("a7"))); //申请种类
						hmap.put("process005", "1"); //序号
						hmap.put("process009", "-1"); //处理状态，默认置为未进入流程
						vret.add(hmap);
					}
					rs.close();
				}
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.BA",
					"BA_approvelistDao.querylist_us()", e.getMessage(), e
							.getMessage());
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
	 * 申请书状态检查＝非写机评结果校验
	 * 
	 * @param i_cuscode
	 *            客户号
	 * @param i_yewucode
	 *            业务号
	 * @param i_areacode
	 *            当前地区
	 * @param i_employee
	 *            当前处理人
	 * @param i_nowphase
	 *            当前环节
	 * @param i_yewuflow
	 *            当前申请种类
	 * @return
	 * @throws TranFailException
	 */
	public HashMap checkapply(String i_cuscode, String i_yewucode,
			String i_areacode, String i_employee, String i_nowphase,
			String i_yewuflow) throws TranFailException {
		HashMap hmap = new HashMap();
		java.sql.CallableStatement stmt_call = null;
		String sql = null;
		try {
			this.getConnection("cmis3");
			//生成
			stmt_call = conn
					.prepareCall("{call pack_ctrl_public.proc_ctrl_nowrite"
							+ "(?,?,?,?,?,?,?,?,?,?,?,?)}");
			stmt_call.setString(1, i_cuscode);
			stmt_call.setString(2, i_yewucode);
			stmt_call.setString(3, i_areacode);
			stmt_call.setString(4, i_employee);
			stmt_call.setString(5, i_nowphase);
			stmt_call.setString(6, i_yewuflow);
			stmt_call.registerOutParameter(7, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(8, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(9, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(10, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(11, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(12, OracleTypes.VARCHAR);
			stmt_call.execute();
			String o_opinion = stmt_call.getString(7); //机评意见0不控制1控制2提示-1程序出错
			String o_firmctl = stmt_call.getString(8); //刚性控制说明
			String o_softctl = stmt_call.getString(9); //提示信息说明
			String o_firmold = stmt_call.getString(10); //刚性控制说明
			String o_softold = stmt_call.getString(11); //提示信息说明
			String o_stoparea = stmt_call.getString(12); //停办设置行

			//hmap.put("o_opinion", o_opinion);

			stmt_call = conn.prepareCall("{call pack_explaincode.proc_analyze"
					+ "(?,?,?,?,?,?,?,?)}");
			stmt_call.setString(1, o_firmctl);
			stmt_call.setString(2, o_softctl);
			stmt_call.setString(3, o_firmold);
			stmt_call.setString(4, o_softold);
			stmt_call.registerOutParameter(5, Types.VARCHAR);
			stmt_call.registerOutParameter(6, Types.VARCHAR);
			stmt_call.registerOutParameter(7, OracleTypes.CURSOR);
			stmt_call.registerOutParameter(8, OracleTypes.CURSOR);
			stmt_call.execute();
			String o_flag = stmt_call.getString(5); //返回标志-1解析错误，0成功
			String o_msg = stmt_call.getString(6);
			java.sql.ResultSet o_ret1 = (ResultSet) stmt_call.getObject(7);
			java.sql.ResultSet o_ret2 = (ResultSet) stmt_call.getObject(8);
			StringBuffer sbf_msg = new StringBuffer();
			if (o_flag.equals("0")) {
				while (o_ret1.next()) {
					sbf_msg.append(o_ret1.getString(1));
					sbf_msg.append(";");
				}
				while (o_ret2.next()) {
					sbf_msg.append(o_ret2.getString(1));
					sbf_msg.append(";");
				}
				hmap.put("o_opinion", o_opinion);
				hmap.put("o_message", sbf_msg.toString());
			} else {
				hmap.put("o_opinion", "-1");
				hmap.put("o_message", o_msg);
			}
			o_ret1.close();
			o_ret2.close();

		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			hmap.put("o_opinion", "-1");
			hmap.put("o_message", e.getMessage());
		} finally {
			if (stmt_call != null)
				try {
					stmt_call.close();
				} catch (Exception ex) {
				}
			closeConnection();
		}
		return hmap;
	}

}