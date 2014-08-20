package icbc.cmis.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import oracle.jdbc.OracleTypes;

import icbc.cmis.TC.model.TC_CargoInsData;
import icbc.cmis.TG.model.TG_LGAlterApplyData;
import icbc.cmis.base.CmisConstance;
import icbc.cmis.base.DictBean;
import icbc.cmis.base.*;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.service.CmisDao;
import icbc.cmis.tfms.AA.AssureAssociationDAO;

/*************************************************************
 * 
 * <b>创建日期: </b> 2006-2-7<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * <br>
 * <p>Copyright: Copyright (c)2006</p>
 * <p>Company: </p>
 * 
 * @author zjfh-huxz
 * 
 * @version 
 * 
 * @since 
 * 
 * @see 
 * 
 *************************************************************/

public abstract class GeneralApplyBaseDao extends CmisDao {
	String sys_date = CmisConstance.getWorkDate("yyyyMMdd");
	String langCode="zh_CN";
	
	AssureAssociationDAO dao = new AssureAssociationDAO(this.getOperation());
	/**
	 * <b>构造函数</b><br>
	 * @param op
	 */
	public GeneralApplyBaseDao(CmisOperation op) {
		super(op);
		try
		{
				langCode=(String)op.getSessionData("LangCode");
		}
		catch (Exception ex)
		{
			langCode="zh_CN";
		}
	}

	public Vector Query(Connection conn, String sql, Hashtable hs) throws TranFailException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Vector in = new Vector();
		try {
			stmt = conn.prepareStatement(sql);
			for (int i = 1; i <= hs.size(); i++) {
				stmt.setString(i, (String) (hs.get(i + "")));
			}
			rs = stmt.executeQuery();

			while (rs.next()) {
				Hashtable name = new Hashtable();
				for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
					name.put(j + "", NullTo(rs.getString(j)));
				}
				in.addElement(name);
			}

		} catch (Exception ex) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException e) {
				throw new MuiTranFailException("100307", "icbc.cmis.util.GeneralApplyBaseDao.Query()",langCode);
			}
			throw new MuiTranFailException("100307", "icbc.cmis.util.GeneralApplyBaseDao.Query()",langCode);
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception ex) {
				};

		}
		return in;
	}

	public boolean ModifyDelete(Connection conn, String sql, Hashtable hs) throws TranFailException {

		PreparedStatement stmt = null;
		Vector in = new Vector();
		try {
			stmt = conn.prepareStatement(sql);
			for (int i = 1; i <= hs.size(); i++) {
				stmt.setString(i, (String) (hs.get(i + "")));
			}
			int i = stmt.executeUpdate();
			if (i != 1) {
				if (conn != null)
					conn.rollback();
				if (stmt != null)
					stmt.close();
				return false;
			}

		} catch (Exception ex) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException e) {
				throw new MuiTranFailException("100308", "icbc.cmis.util.GeneralApplyBaseDao.ModifyDelete()",langCode);
			}
			throw new MuiTranFailException("100308", "icbc.cmis.util.GeneralApplyBaseDao.ModifyDelete()",langCode);
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception ex) {
				};

		}
		return true;
	}

	private String NullTo(String str) {
		if (str == null)
			str = "";
		return str;
	}
	//	public Connection getConn() throws TranFailException {
	//		Connection conn = this.getConn();
	//		return conn;
	//	}
	//
	//	public void commit(Connection conn) throws TranFailException {
	//		try {
	//			conn.commit();
	//		} catch (SQLException e) {
	//			throw new MuiTranFailException(
	//				"GeneralApplyBaseDao007",
	//				"icbc.cmis.util.GeneralApplyBaseDao.commit()",
	//				e.getMessage(),
	//				"commit()出错！");
	//		}
	//
	//	}

	public boolean insertSychronize(Connection conn) throws SQLException, TranFailException {

		CallableStatement cStmt = null;
		String Apply_kind = this.getOperation().getStringAt("Apply_kind");
		String Apply_sub = "";
		if (this.getOperation().isElementExist("Apply_sub"))
			Apply_sub = this.getOperation().getStringAt("Apply_sub");
		String PRENAME = icbc.cmis.tfms.AA.AssureAssociationOP.PRENAME;
		try {
			this.getOperation().setFieldValue(PRENAME + "i_customercode", this.getOperation().getStringAt("Apply_customerCode"));
			this.getOperation().setFieldValue(PRENAME + "i_areacode", (String)this.getOperation().getSessionData("AreaCode"));
			this.getOperation().setFieldValue(PRENAME + "i_currdate", sys_date);
			this.getOperation().setFieldValue(PRENAME + "i_oldcontract", this.getOperation().getStringAt("Apply_pesudoID"));
			this.getOperation().setFieldValue(PRENAME + "i_newcontract", this.getOperation().getStringAt("Apply_contractID"));
			if (this.getOperation().isElementExist("Apply_baseID"))
				this.getOperation().setFieldValue(PRENAME + "i_basecontract", this.getOperation().getStringAt("Apply_baseID"));
			else
				this.getOperation().setFieldValue(PRENAME + "i_basecontract", "");
			this.getOperation().setFieldValue(PRENAME + "i_applystage", "0");
			this.getOperation().setFieldValue(PRENAME + "i_checktype", this.getOperation().getStringAt("Apply_kind"));

			if ("01".equals(Apply_kind) && Apply_sub.equals("1")) {
				cStmt = conn.prepareCall("{call Pack_assure_relation.proc_others_sync(?,?,?,?,?,?,?,?,?,?)}");
				cStmt.setString(1, this.getOperation().getStringAt("Apply_customerCode"));
				cStmt.setString(2, (String)this.getOperation().getSessionData("AreaCode"));
				cStmt.setString(3, sys_date);
				cStmt.setString(4, "0");
				cStmt.setString(5, "01");
				cStmt.setString(6, this.getOperation().getStringAt("Apply_pesudoID"));
				cStmt.setString(7, this.getOperation().getStringAt("Apply_contractID"));
				cStmt.setString(8, "");
				cStmt.registerOutParameter(9, OracleTypes.VARCHAR);
				cStmt.registerOutParameter(10, OracleTypes.VARCHAR);

				cStmt.executeQuery();

				String ret = cStmt.getString(10);
				String retmess = cStmt.getString(9);

				cStmt.close();
				if (ret.equals("1")) {
					if (conn != null)
						conn.rollback();

					if (cStmt != null)
						cStmt.close();
					closeConnection();
					return false;
				}

			}
			if (!("01".equals(Apply_kind) && "1".equals(Apply_sub)))
				dao.interfaceAssureRelation(this.getOperation().getOperationData(), conn);
			if ("01".equals(Apply_kind)) {
				cStmt = conn.prepareCall("{call pack_apply_use.proc_adjust_billno(?,?,?,?)}");
				cStmt.setString(1, this.getOperation().getStringAt("Apply_customerCode"));
				cStmt.setString(2, this.getOperation().getStringAt("Apply_contractID"));

				cStmt.registerOutParameter(3, OracleTypes.VARCHAR);
				cStmt.registerOutParameter(4, OracleTypes.VARCHAR);

				cStmt.execute();

				String ret = cStmt.getString(3);
				String retmess = cStmt.getString(4);
				cStmt.close();
				if (ret.equals("1")) {
					if (conn != null)
						conn.rollback();

					if (cStmt != null)
						cStmt.close();
					closeConnection();
					return false;
				}
			}

		} catch (TranFailException e) {
			if (conn != null)
				conn.rollback();
			this.closeConnection();
			throw e;
		} catch (Exception e) {
			if (conn != null)
				conn.rollback();
			this.closeConnection();
			throw new MuiTranFailException("100309", "icbc.cmis.util.GeneralApplyBaseDao.insertSychronize()",langCode);
		}

		return true;
	}

	public boolean updateSychronize(Connection conn) throws SQLException, TranFailException {

		CallableStatement cStmt = null;
		String PRENAME = icbc.cmis.tfms.AA.AssureAssociationOP.PRENAME;
		String Apply_kind = this.getOperation().getStringAt("Apply_kind");
		String Apply_sub = "";
		if (this.getOperation().isElementExist("Apply_sub"))
			Apply_sub = this.getOperation().getStringAt("Apply_sub");
		try {

			this.getOperation().setFieldValue(PRENAME + "i_customercode", this.getOperation().getStringAt("Apply_customerCode"));
			this.getOperation().setFieldValue(PRENAME + "i_areacode", (String)this.getOperation().getSessionData("AreaCode"));
			this.getOperation().setFieldValue(PRENAME + "i_currdate", sys_date);
			this.getOperation().setFieldValue(PRENAME + "i_oldcontract", this.getOperation().getStringAt("Apply_pesudoID"));
			this.getOperation().setFieldValue(PRENAME + "i_newcontract", this.getOperation().getStringAt("Apply_contractID"));
			if (this.getOperation().isElementExist("Apply_baseID"))
				this.getOperation().setFieldValue(PRENAME + "i_basecontract", this.getOperation().getStringAt("Apply_baseID"));
			else
				this.getOperation().setFieldValue(PRENAME + "i_basecontract", "");
			this.getOperation().setFieldValue(PRENAME + "i_applystage", "0");
			this.getOperation().setFieldValue(PRENAME + "i_checktype", this.getOperation().getStringAt("Apply_kind"));
			if ("01".equals(Apply_kind) && Apply_sub.equals("1")) {
				cStmt = conn.prepareCall("{call Pack_assure_relation.proc_others_sync(?,?,?,?,?,?,?,?,?,?)}");
				cStmt.setString(1, this.getOperation().getStringAt("Apply_customerCode"));
				cStmt.setString(2, (String)this.getOperation().getSessionData("AreaCode"));
				cStmt.setString(3, sys_date);
				cStmt.setString(4, "0");
				cStmt.setString(5, "01");
				cStmt.setString(6, this.getOperation().getStringAt("Apply_pesudoID"));
				cStmt.setString(7, this.getOperation().getStringAt("Apply_contractID"));
				cStmt.setString(8, "");
				cStmt.registerOutParameter(9, OracleTypes.VARCHAR);
				cStmt.registerOutParameter(10, OracleTypes.VARCHAR);

				cStmt.executeQuery();

				String ret = cStmt.getString(10);
				String retmess = cStmt.getString(9);

				cStmt.close();
				if (ret.equals("1")) {
					if (conn != null)
						conn.rollback();

					if (cStmt != null)
						cStmt.close();
					closeConnection();
					return false;
				}

			}
			if (!("01".equals(Apply_kind) && "1".equals(Apply_sub)))
				dao.interfaceAssureRelation(this.getOperation().getOperationData(), conn);
			if ("01".equals(Apply_kind)) {
				cStmt = conn.prepareCall("{call pack_apply_use.proc_adjust_billno(?,?,?,?)}");
				cStmt.setString(1, this.getOperation().getStringAt("Apply_customerCode"));
				cStmt.setString(2, this.getOperation().getStringAt("Apply_contractID"));

				cStmt.registerOutParameter(3, OracleTypes.VARCHAR);
				cStmt.registerOutParameter(4, OracleTypes.VARCHAR);

				cStmt.execute();

				String ret = cStmt.getString(3);
				String retmess = cStmt.getString(4);
				cStmt.close();
				if (ret.equals("1")) {
					if (conn != null)
						conn.rollback();

					if (cStmt != null)
						cStmt.close();
					closeConnection();
					return false;
				}
			}

		} catch (TranFailException e) {
			if (conn != null)
				conn.rollback();
			this.closeConnection();
			throw e;
		} catch (Exception e) {
			if (conn != null)
				conn.rollback();
			this.closeConnection();
			throw new MuiTranFailException("100310", "icbc.cmis.util.GeneralApplyBaseDao.updateSychronize()",langCode);
		}

		return true;
	}

	public boolean deleteSychronize(Connection conn) throws SQLException, TranFailException {
		String Apply_kind = this.getOperation().getStringAt("Apply_kind");
		String PRENAME = icbc.cmis.tfms.AA.AssureAssociationOP.PRENAME;
		CallableStatement cStmt = null;
		cStmt = conn.prepareCall("{call pack_public_use.proc_delete_pk(?,?,?,?,?,?,?,?,?)}");
		cStmt.setString(1, this.getOperation().getStringAt("Apply_customerCode"));
		cStmt.setString(2, this.getOperation().getStringAt("Apply_contractID"));
		cStmt.setString(3, "ALL");
		cStmt.setString(4, "TA200212");
		cStmt.setString(5, "0");
		cStmt.setString(6, this.getOperation().getStringAt("Apply_kind"));
		cStmt.setString(7, "0");
		cStmt.registerOutParameter(8, OracleTypes.VARCHAR);
		cStmt.registerOutParameter(9, OracleTypes.VARCHAR);

		if (this.getOperation().getStringAt("Apply_kind").equals("01")) {
			return true;
		} else if (this.getOperation().getStringAt("Apply_kind").equals("06")) {
			return true;
		} else if (this.getOperation().getStringAt("Apply_kind").equals("04")) {
			return true;
		} else if (Apply_kind.equals("15") || Apply_kind.equals("18") || Apply_kind.equals("19") || Apply_kind.equals("20")) {

			cStmt.executeQuery();

			String ret = cStmt.getString(8);
			String retmess = cStmt.getString(9);

			cStmt.close();
			if (ret.equals("1")) {
				if (conn != null)
					conn.rollback();

				if (cStmt != null)
					cStmt.close();
				closeConnection();
				return false;
			}
		} else if (this.getOperation().getStringAt("Apply_kind").equals("17")) {

			try {

				cStmt.executeQuery();

				String ret = cStmt.getString(8);
				String retmess = cStmt.getString(9);

				cStmt.close();
				if (ret.equals("1")) {
					conn.rollback();

					if (cStmt != null)
						cStmt.close();
					closeConnection();
					return false;
				}
				cStmt = conn.prepareCall("call pack_direction.dodelete(?,?,?,?,?,?)");
				cStmt.setString(1, this.getOperation().getStringAt("TA270019001"));
				cStmt.setString(2, this.getOperation().getStringAt("TA270019002"));
				cStmt.setString(3, "01");
				cStmt.setString(4, "0");
				cStmt.registerOutParameter(5, OracleTypes.NUMBER);
				cStmt.registerOutParameter(6, OracleTypes.VARCHAR);
				cStmt.execute();
				String err_txt = cStmt.getString(6);
				if (cStmt.getInt(5) != 0) {
					throw new MuiTranFailException("100311", "icbc.cmis.TB.TB_importHTApplyOp.deleteDataDueBill()",langCode);
				}

			} catch (TranFailException e) {
				if (conn != null)
					conn.rollback();
				this.closeConnection();
				throw e;
			} catch (Exception e) {
				if (conn != null)
					conn.rollback();
				this.closeConnection();
				throw new MuiTranFailException("100311", "icbc.cmis.TB.TB_importHTApplyOp.deleteDataDueBill()",langCode);
			}
		}
		return true;

	}

	public String getSequence(
		String enpcode,
		String areacode,
		String tablecode,
		String creditmethod,
		String seq,
		Connection conn) {
		String err_txt = "";
		try {
			String sequence = "";

			CallableStatement cStmt = conn.prepareCall("{?=call pack_tools.func_get_sequence(?,?,?,?,?)}");
			cStmt.registerOutParameter(1, OracleTypes.VARCHAR);
			cStmt.registerOutParameter(6, OracleTypes.VARCHAR);
			cStmt.setString(2, enpcode);
			cStmt.setString(3, areacode);
			cStmt.setString(4, tablecode);
			cStmt.setString(5, creditmethod);
			cStmt.execute();
			sequence = cStmt.getString(1);
			err_txt = cStmt.getString(6);
			cStmt.close();
			this.getOperation().setFieldValue(seq, sequence);

			return sequence;
		} catch (Exception ee) {
			if (conn != null)
				closeConnection();
			err_txt = ee.getMessage();
			return "wrong";
		}
	}

	public final String insert() throws Exception, TranFailException {
		try {

			this.getConnection();
			boolean bl = this.abstractInsert(conn);

			if (bl == true) {
				boolean isInsertRate = this.insertRate(conn);
				if (isInsertRate == true) {
					boolean insertView = this.insertView(conn);
					//					if (insertView == false) {
					//						if (conn != null)
					//							conn.rollback();
					//						this.closeConnection();
					//						return "failure";
					//					}
					boolean tmp = this.insertSychronize(conn);
					if (tmp == true) {
						conn.commit();
						this.closeConnection();
						return "ok";
					} else {
						if (conn != null)
							conn.rollback();
						this.closeConnection();
						throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.insert()",langCode);
					}

				} else {
					if (conn != null)
						conn.rollback();
					this.closeConnection();
					throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.insert()",langCode);
				}
			} else {
				if (conn != null)
					conn.rollback();
				this.closeConnection();
				throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.insert()",langCode);
			}
		} catch (TranFailException e) {
			if (conn != null)
				conn.rollback();
			this.closeConnection();
			throw e;
		} catch (Exception e) {
			if (conn != null)
				conn.rollback();
			this.closeConnection();
			throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.insert()",langCode);
		} finally {
			this.getOperation().setOperationDataToSession();
		}

	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param conn
	 * @return
	 *  
	 */
	private boolean insertRate(Connection conn) throws TranFailException, SQLException {
		String Apply_kind = this.getOperation().getStringAt("Apply_kind");
		String Apply_customerCode = this.getOperation().getStringAt("Apply_customerCode");
		String Apply_sub = "";
		if (this.getOperation().isElementExist("Apply_sub")) {
			Apply_sub = this.getOperation().getStringAt("Apply_sub");
		}
		String Apply_contractID = this.getOperation().getStringAt("Apply_contractID");
		if ("01".equals(Apply_kind) && "2".equals(Apply_sub) || "17".equals(Apply_kind) && "1".equals(Apply_sub))
			return true;

		CallableStatement cStmt = conn.prepareCall("{call Pack_insertApplyRate.insertRate(?,?,?,?,?)}");
		cStmt.setString(1, Apply_customerCode);
		cStmt.setString(2, Apply_contractID);
		cStmt.setString(3, Apply_kind);
		cStmt.registerOutParameter(4, OracleTypes.VARCHAR);
		cStmt.registerOutParameter(5, OracleTypes.VARCHAR);
		cStmt.execute();
		String flag = cStmt.getString(4);
		String err_txt = cStmt.getString(5);
		cStmt.close();
		if ("1".equals(flag)) {
			throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.insertRate()",langCode);
		}
		if ("2".equals(flag)) {

			throw new MuiTranFailException("100315", "icbc.cmis.util.GeneralApplyBaseDao.insertRate()",langCode);

		}

		return true;

	}

	private boolean insertView(Connection conn) throws TranFailException, SQLException {
		String Apply_kind = this.getOperation().getStringAt("Apply_kind");
		String Apply_customerCode = this.getOperation().getStringAt("Apply_customerCode");
		String Apply_sub = "";
		if (this.getOperation().isElementExist("Apply_sub")) {
			Apply_sub = this.getOperation().getStringAt("Apply_sub");
		}
		String Apply_contractID = this.getOperation().getStringAt("Apply_contractID");

		if ("01".equals(Apply_kind)
			&& !"1".equals(Apply_sub)
			|| "04".equals(Apply_kind)
			|| "06".equals(Apply_kind)
			|| "15".equals(Apply_kind)
			|| "17".equals(Apply_kind)
			&& !"1".equals(Apply_sub)
			|| "18".equals(Apply_kind)
			|| "19".equals(Apply_kind)) {

			CallableStatement cStmt = conn.prepareCall("{?=call PACK_IMAGE_TOOL.func_getYWKind(?,?,?,?)}");
			cStmt.registerOutParameter(1, OracleTypes.VARCHAR);
			cStmt.setString(2, Apply_customerCode);
			cStmt.setString(3, Apply_contractID);
			cStmt.setString(4, Apply_kind);
			cStmt.registerOutParameter(5, OracleTypes.VARCHAR);
			cStmt.execute();
			String flag1 = cStmt.getString(1);
			String err_txt = cStmt.getString(5);
			cStmt.close();
			if ("-1".equals(flag1)) {
				throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.insertView()",langCode);
			}
			if (!"0".equals(flag1)) {
				cStmt = conn.prepareCall("{?=call PACK_IMAGE_TOOL.func_insertRequisition(?,?,?,?,?)}");
				cStmt.registerOutParameter(1, OracleTypes.NUMBER);
				cStmt.setString(2, Apply_customerCode);
				cStmt.setString(3, flag1);
				cStmt.setString(4, Apply_contractID);
				cStmt.setString(5, this.getOperation().getStringAt("Apply_sponsorBank"));
				cStmt.registerOutParameter(6, OracleTypes.VARCHAR);
				cStmt.execute();
				int flag = cStmt.getInt(1);
				String err_txt1 = cStmt.getString(6);
				cStmt.close();
				if (flag != 1) {
					throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.insertView()",langCode);
				}
			}
		}
		return true;

	}

	private boolean deleteView(Connection conn) throws TranFailException, SQLException {
		String Apply_kind = this.getOperation().getStringAt("Apply_kind");
		String Apply_customerCode = this.getOperation().getStringAt("Apply_customerCode");
		String Apply_sub = "";
		if (this.getOperation().isElementExist("Apply_sub")) {
			Apply_sub = this.getOperation().getStringAt("Apply_sub");
		}
		String Apply_contractID = this.getOperation().getStringAt("Apply_contractID");
		if ("01".equals(Apply_kind)
			&& !"1".equals(Apply_sub)
			|| "04".equals(Apply_kind)
			|| "06".equals(Apply_kind)
			|| "15".equals(Apply_kind)
			|| "17".equals(Apply_kind)
			&& !"1".equals(Apply_sub)
			|| "18".equals(Apply_kind)
			|| "19".equals(Apply_kind)) {
			CallableStatement cStmt = conn.prepareCall("{?=call PACK_IMAGE_TOOL.func_deleteRequisition(?,?,?)}");
			cStmt.registerOutParameter(1, OracleTypes.NUMBER);
			cStmt.setString(2, Apply_customerCode);
			cStmt.setString(3, Apply_contractID);
			cStmt.registerOutParameter(4, OracleTypes.VARCHAR);
			cStmt.execute();
			int flag = cStmt.getInt(1);
			String err_txt = cStmt.getString(4);
			cStmt.close();
			if (flag != 1) {
				throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.deleteView()",langCode);
			}
		}
		return true;

	}

	public final String modify() throws Exception, TranFailException {
		try {

			this.getConnection();
			boolean bl = this.abstractModify(conn);
			if (bl == true) {
				boolean isInsertRate = this.insertRate(conn);
				if (isInsertRate == true) {
					conn.commit();
					boolean tmp = this.updateSychronize(conn);
					if (tmp == true) {
						conn.commit();
						this.closeConnection();
						return "ok";
					} else {
						if (conn != null)
							conn.rollback();
						this.closeConnection();
						throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.modify()",langCode);
					}

				} else {
					if (conn != null)
						conn.rollback();
					this.closeConnection();
					throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.modify()",langCode);
				}
			} else {
				if (conn != null)
					conn.rollback();
				this.closeConnection();
				throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.modify()",langCode);
			}
		} catch (TranFailException e) {
			if (conn != null)
				conn.rollback();
			this.closeConnection();
			throw e;
		} catch (Exception e) {
			if (conn != null)
				conn.rollback();
			this.closeConnection();
			throw new MuiTranFailException("100313", "icbc.cmis.util.GeneralApplyBaseDao.modify()",langCode);
		} finally {
			this.getOperation().setOperationDataToSession();
		}

	}

	public final String delete() throws Exception, TranFailException {
		try {

			this.getConnection();
			boolean objectionDelete = this.ObjectionDelete(conn);
			if (objectionDelete == true) {
				boolean bl = this.abstractDelete(conn);
				if (bl == true) {
					this.deleteRate(conn);
					bl = this.deleteView(conn);
					if (bl != true) {
						if (conn != null)
							conn.rollback();
						this.closeConnection();
						throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.modify()",langCode);
					}
					boolean tmp = this.deleteSychronize(conn);
					if (tmp == true) {
						conn.commit();
						this.closeConnection();
						return "ok";
					} else {
						if (conn != null)
							conn.rollback();
						this.closeConnection();
						throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.modify()",langCode);
					}

				} else {
					if (conn != null)
						conn.rollback();
					this.closeConnection();
					throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.modify()",langCode);
				}
			} else {
				if (conn != null)
					conn.rollback();
				this.closeConnection();
				throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.modify()",langCode);
			}
		} catch (TranFailException e) {
			if (conn != null)
				conn.rollback();
			this.closeConnection();
			throw e;
		} catch (Exception e) {
			if (conn != null)
				conn.rollback();
			this.closeConnection();
			throw new MuiTranFailException("100314", "icbc.cmis.util.GeneralApplyBaseDao.delete()",langCode);
		} finally {
			this.getOperation().setOperationDataToSession();
		}

	}
	/**
	 * 
	 * <b>功能描述: 删除申请时，如果该申请的状态是否决或退回调查，则调用张远征的接口</b><br>
	 * <p> </p>
	 * @return 返回布尔值 true成功 false失败
	 * @throws TranFailException
	 *
	 */
	public boolean ObjectionDelete(Connection conn) throws TranFailException {
		try {

			String Apply_kind = this.getOperation().getStringAt("Apply_kind");
			String Apply_customerCode = this.getOperation().getStringAt("Apply_customerCode");
			String Apply_sub = "";
			if (this.getOperation().isElementExist("Apply_sub")) {
				Apply_sub = this.getOperation().getStringAt("Apply_sub");
			}
			String Apply_contractID = this.getOperation().getStringAt("Apply_contractID");
			String sql = "";
			Hashtable hs = new Hashtable();
			if ("01".equals(Apply_kind)) {
				sql = "select TA200061029 from TA200061 where ta200061001=? and TA200061002=?";

			} else if ("04".equals(Apply_kind)) {
				sql = "select TA200161018 from TA200161 where TA200161001=? and TA200161002=?";
			} else if ("06".equals(Apply_kind)) {
				sql = "select TA250051024 from TA250051 where TA250051001=? and TA250051002=?";
			} else if ("15".equals(Apply_kind)) {
				sql = "select ta270011023 from ta270011 where ta270011001=? and ta270011003=?";
			} else if ("17".equals(Apply_kind)) {
				sql = "select ta270019016 from ta270019 where TA270019001=? and TA270019002=?";
			} else if ("18".equals(Apply_kind)) {
				sql = "select ta270015030 from ta270015 where TA270015001=? and TA270015003=?";
			} else if ("19".equals(Apply_kind)) {
				sql = "select ta270018011 from ta270018 where TA270018001=? and TA270018003=?";
			} else if ("20".equals(Apply_kind)) {
				sql = "select ta250081018 from ta250081 where TA250081001=? and TA250081004=?";
			}
			hs.put("1", Apply_customerCode);
			hs.put("2", Apply_contractID);
			Vector vc = this.Query(conn, sql, hs);
			Hashtable rr = (Hashtable)vc.get(0);
			if (!"1".equals((String)rr.get("1"))) {
				CallableStatement cStmt = null;
				if ("20".equals(Apply_kind))
					cStmt = conn.prepareCall("{call pack_approvepub.deleteprocess(?,?,?,?)}");
				else
					cStmt = conn.prepareCall("{call pack_approvepub.migrateprocess(?,?,?,?,?,?,?)}");
				if (!"20".equals(Apply_kind)) {

					cStmt.setString(1, Apply_customerCode);
					cStmt.setString(2, Apply_contractID);
					cStmt.setString(3, "1");
					cStmt.setString(4, "");
					cStmt.setString(5, this.getOperation().getStringAt("Apply_sponsorBank"));
					cStmt.registerOutParameter(6, OracleTypes.VARCHAR);
					cStmt.registerOutParameter(7, OracleTypes.VARCHAR);
				} else {
					cStmt.setString(1, Apply_customerCode);
					cStmt.setString(2, Apply_contractID);
					cStmt.registerOutParameter(3, OracleTypes.VARCHAR);
					cStmt.registerOutParameter(4, OracleTypes.VARCHAR);
				}
				cStmt.execute();
				String flag = "";
				if (!"20".equals(Apply_kind))
					flag = cStmt.getString(6);
				else
					flag = cStmt.getString(3);
				String err_txt = "";
				if (!"20".equals(Apply_kind))
					err_txt = cStmt.getString(7);
				else
					err_txt = cStmt.getString(4);
				cStmt.close();
				if ("1".equals(flag)) {
					throw new MuiTranFailException("100312", "icbc.cmis.util.GeneralApplyBaseDao.ObjectionDelete()",langCode);
				}
			}
			return true;
		} catch (Exception e) {
			throw new MuiTranFailException("100306", "icbc.cmis.util.GeneralApplyBaseDao.ObjectionDelete()",langCode);
		}

	}

	/**
		 * @param conn
		 * @param initFormImf
		 * @return
		 */
	private void deleteRate(Connection conn) throws TranFailException {
		String sql = "";
		PreparedStatement stmt = null;

		try {

			sql = "delete from ta200213 where ta200213001=? and ta200213002=?  ";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, this.getOperation().getStringAt("Apply_customerCode"));
			stmt.setString(2, this.getOperation().getStringAt("Apply_contractID"));
			stmt.executeUpdate();

			stmt.close();

		} catch (Exception e) {
			if (conn != null) {
				try {
					conn.rollback();
					stmt.close();
				} catch (Exception eee) {
				}
				closeConnection();
			}
			throw new MuiTranFailException(
				"xdtz0FFE102",
				"GeneralApplyBaseDao.deleteRate(String)",
				e.getMessage(),
				"删除ta200213表发生异常");
		}
	}

	public abstract boolean abstractInsert(Connection conn) throws Exception, TranFailException;
	public abstract boolean abstractModify(Connection conn) throws Exception, TranFailException;
	public abstract boolean abstractDelete(Connection conn) throws Exception, TranFailException;
}
