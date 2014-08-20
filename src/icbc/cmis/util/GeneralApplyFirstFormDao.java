package icbc.cmis.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.service.CmisDao;

/*************************************************************
 * 
 * <b>创建日期: </b> 2006-1-26<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * <br>
 * <p>Copyright: Copyright (c)2006</p>
 * <p>Company: </p>
 * 
 * @author Administrator
 * 
 * @version 
 * 
 * @since 
 * 
 * @see 
 * 
 *************************************************************/

public class GeneralApplyFirstFormDao extends GeneralApplyBaseDao {

	/**
	 * <b>构造函数</b><br>
	 * @param op
	 */
	public GeneralApplyFirstFormDao(CmisOperation op) {
		super(op);
	}

	/**
	 * <b>功能描述: 取页面form的信息</b><br>
	 * <p> </p>
	 * @return
	 *  
	 */
	public Vector getFormContent() throws TranFailException {
		String sql = null;
		Vector in = new Vector();
		Hashtable hs = new Hashtable();
		String Apply_sub = "";
		String lang_code="";
		try{
		
		lang_code=(String)this.getOperation().getSessionData("LangCode");
		}
		catch(Exception e){
			lang_code="zh_CN";
		}
		if(lang_code==null)
		lang_code="zh_CN";
		try {
			this.getConnection();
			sql =
				"select * from PA210005 where PA210005001=? and pa210005023=? and lang_code=? order by PA210005017";
				
			if (this.getOperation().isElementExist("approveAction")){
			if(this.getOperation().getStringAt("approveAction").equals("0"))
			sql =
							"select * from PA210005 where PA210005001=? and pa210005023=? and lang_code=? and pa210005015 <> ? order by PA210005017";
						}	
			if (this.getOperation().isElementExist("Apply_sub"))
				Apply_sub = this.getOperation().getStringAt("Apply_sub");
			hs.put(
				"1",
				this.getOperation().getStringAt("Apply_kind") + Apply_sub);
			hs.put("2", this.getOperation().getStringAt("Apply_stage"));
			hs.put("3", lang_code);
			if (this.getOperation().isElementExist("approveAction")){
			if(this.getOperation().getStringAt("approveAction").equals("0"))
			hs.put("4", "1");
			}
			in = this.Query(conn, sql, hs);

		} catch (Exception ex) {
			throw new TranFailException(
				"100301",
				"icbc.cmis.util.GeneralApplyFirstFormDao.getFormContent()");
		} finally {
			this.closeConnection();
		}
		return in;
	}

	/**
	 * 
	 * <b>功能描述: 取页面包含的js信息</b><br>
	 * <p> </p>
	 * @param firstordetail
	 * @return
	 * @throws TranFailException
	 *
	 */
	public Vector getIncludejs(String firstordetail) throws TranFailException {
		String sql = null;
		Vector in = new Vector();
		Hashtable hs = new Hashtable();
		String Apply_sub = "";
		String lang_code="";
				try{
		
				lang_code=(String)this.getOperation().getSessionData("LangCode");
				}
				catch(Exception e){
					lang_code="zh_CN";
				}
		if(lang_code==null)
				lang_code="zh_CN";
		try {
			this.getConnection();
			if (firstordetail.equals("0"))
			sql =
							"select * from PA210002 where PA210002001=? and PA210002003=? and PA210002005=? and lang_code=? ";
			else
			sql =
				"select * from PA210002 where PA210002001=? and PA210002003=? and PA210002005=?";
			if (this.getOperation().isElementExist("Apply_sub"))
				Apply_sub = this.getOperation().getStringAt("Apply_sub");
			hs.put(
				"1",
				this.getOperation().getStringAt("Apply_kind") + Apply_sub);
			hs.put("2", firstordetail);
			hs.put("3", this.getOperation().getStringAt("Apply_stage"));
			if (firstordetail.equals("0"))
			hs.put("4", lang_code);
			in = this.Query(conn, sql, hs);

		} catch (Exception ex) {
			throw new TranFailException(
				"100302",
				"icbc.cmis.util.GeneralApplyFirstFormDao.getIncludejs()");
		} finally {
			this.closeConnection();
		}
		return in;
	}
	/**
	 * 
	 * <b>功能描述: 去页面的title</b><br>
	 * <p> </p>
	 * @throws TranFailException
	 *
	 */
	public void getTitle() throws TranFailException {
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String Apply_sub = "";
		String lang_code="";
						try{
		
						lang_code=(String)this.getOperation().getSessionData("LangCode");
						}
						catch(Exception e){
							lang_code="zh_CN";
						}
		if(lang_code==null)
				lang_code="zh_CN";
		try {
			this.getConnection();
			sql = "select * from PA210001 where PA210001001=? and lang_code=? ";
			stmt = conn.prepareStatement(sql);
			if (this.getOperation().isElementExist("Apply_sub"))
				Apply_sub = this.getOperation().getStringAt("Apply_sub");
			stmt.setString(
				1,
				this.getOperation().getStringAt("Apply_kind") + Apply_sub);
			stmt.setString(
							2,
			lang_code);	
			rs = stmt.executeQuery();

			while (rs.next()) {
				this.getOperation().setFieldValue(
					"Apply_title",
					rs.getString(2));
				this.getOperation().setFieldValue(
					"Apply_da200211010",
					rs.getString(3));
			}

		} catch (Exception ex) {
			throw new TranFailException(
				"100303",
				"icbc.cmis.util.GeneralApplyFirstFormDao.getTitle()");
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception ex) {
				};
			this.closeConnection();
		}

	}
	/**
	 * 
	 * <b>功能描述:查询页面button信息 </b><br>
	 * <p> </p>
	 * @param firstordetail  首页面还是内容页面的标志
	 * @param flag           申请还是台账
	 * @return
	 * @throws TranFailException
	 *
	 */
	public Vector getButton(String firstordetail, String flag)
		throws TranFailException {
		String sql = null;
		Vector in = new Vector();
		Hashtable hs = new Hashtable();
		String Apply_sub = "";
		String lang_code="";
								try{
		
								lang_code=(String)this.getOperation().getSessionData("LangCode");
								}
								catch(Exception e){
									lang_code="zh_CN";
								}
			if(lang_code==null)
					lang_code="zh_CN";
		try {
			this.getConnection();
			sql = "";
			if ("".equals(flag))
				sql =
					"select * from PA210003 where PA210003001=? and PA210003005=? and PA210003008=? order by pa210003004";
			else{
			  if (!firstordetail.equals("0"))
				sql =
					"select * from PA210003 where PA210003001=? and PA210003005=? and  PA210003008=? and PA210003006=? order by pa210003004";
			else
			sql =
				  "select * from PA210003 where PA210003001=? and PA210003005=? and  PA210003008=? and PA210003006=? and lang_code=? order by pa210003004";
						
			}
			if (this.getOperation().isElementExist("Apply_sub"))
				Apply_sub = this.getOperation().getStringAt("Apply_sub");
			hs.put(
				"1",
				this.getOperation().getStringAt("Apply_kind") + Apply_sub);
			hs.put("2", firstordetail);
			hs.put("3", this.getOperation().getStringAt("Apply_stage"));
			if (!"".equals(flag)) {
				hs.put("4", flag);
				if (firstordetail.equals("0"))  //如果是首页
				
				hs.put("5", lang_code);		
			}
       
			in = this.Query(conn, sql, hs);

		} catch (Exception ex) {
			throw new TranFailException(
				"100304",
				"icbc.cmis.util.GeneralApplyFirstFormDao.getButton()");
		} finally {
			this.closeConnection();
		}
		return in;
	}
	/**
	 * 
	 * <b>功能描述: 取hidden项</b><br>
	 * <p> </p>
	 * @param firstordetail  首页还是内容页面
	 * @param flag          申请还是台账
	 * @return
	 * @throws TranFailException
	 *
	 */
	public Vector getHidden(String firstordetail, String flag)
		throws TranFailException {
		String sql = null;
		Vector in = new Vector();
		Hashtable hs = new Hashtable();
		String Apply_sub = "";
		try {
			this.getConnection();
			if ("".equals(flag))
				sql =
					"select * from PA210004 where PA210004001=? and PA210004005=? and PA210004007=?";
			else
				sql =
					"select * from PA210004 where PA210004001=? and PA210004005=? and PA210004007=? and PA210004006=?";
			if (this.getOperation().isElementExist("Apply_sub"))
				Apply_sub = this.getOperation().getStringAt("Apply_sub");
			hs.put(
				"1",
				this.getOperation().getStringAt("Apply_kind") + Apply_sub);
			hs.put("2", firstordetail);
			hs.put("3", this.getOperation().getStringAt("Apply_stage"));
			if (!"".equals(flag))
				hs.put("4", flag);
			in = this.Query(conn, sql, hs);

		} catch (Exception ex) {
			throw new TranFailException(
				"100305",
				"icbc.cmis.util.GeneralApplyFirstFormDao.getHidden()");
		} finally {
			this.closeConnection();
		}
		return in;
	}

	/** 
	 * <b>功能描述: </b><br>
	 * <p>	</p>
	 * @see icbc.cmis.util.GeneralApplyBaseDao#insertE(java.sql.Connection)
	 * @param conn
	 * @throws Exception
	 * @throws TranFailException
	 * 
	 */
	public boolean abstractInsert(Connection conn)
		throws Exception, TranFailException {
		return true;

	}

	/** 
	 * <b>功能描述: </b><br>
	 * <p>	</p>
	 * @see icbc.cmis.util.GeneralApplyBaseDao#modifyE(java.sql.Connection)
	 * @param conn
	 * @throws Exception
	 * @throws TranFailException
	 * 
	 */
	public boolean abstractModify(Connection conn)
		throws Exception, TranFailException {
		return true;

	}

	/** 
	 * <b>功能描述: </b><br>
	 * <p>	</p>
	 * @see icbc.cmis.util.GeneralApplyBaseDao#deleteE(java.sql.Connection)
	 * @param conn
	 * @throws Exception
	 * @throws TranFailException
	 * 
	 */
	public boolean abstractDelete(Connection conn)
		throws Exception, TranFailException {
		return true;

	}
	/**
	 * 
	 * <b>功能描述: 根据areacode查询地区名</b><br>
	 * <p> </p>
	 * @param areacode
	 * @return
	 * @throws Exception
	 *
	 */
	public String getAreaName(String areacode) throws Exception {
		try {

			this.getConnection();
			Hashtable hs = new Hashtable();
			hs.put("1", areacode);
			Vector vc =
				this.Query(
					conn,
					"select AREA_NAME from mag_area where AREA_CODE = ?",
					hs);
			this.closeConnection();
			hs = (Hashtable) vc.get(0);
			return (String) hs.get("1");
		} catch (Exception e) {
			this.closeConnection();
			throw new TranFailException(
				"100301",
				"icbc.cmis.util.GeneralApplyFirstFormDao.getFormContent()");
		}
	}

	/**
	* 
	* <b>功能描述: 页面包含的tab页定义</b><br>
	* <p> </p>
	* @param apporapprove    申请or台账
	* @param pagetype        查询or新增页面
	* @return
	* @throws TranFailException
	*
	*/
	public Vector getContent(String apporapprove, String pagetype)
		throws TranFailException {
		String sql = null;
		Vector in = new Vector();
		Hashtable hs = new Hashtable();
		String Apply_sub = "";
        
		try {
			this.getConnection();
			
			if (this.getOperation().getStringAt("Apply_kind").equals("24")||this.getOperation().getStringAt("Apply_kind").equals("25")||this.getOperation().getStringAt("Apply_kind").equals("26")||this.getOperation().getStringAt("Apply_kind").equals("31")
			||this.getOperation().getStringAt("Apply_kind").equals("32")||this.getOperation().getStringAt("Apply_kind").equals("33")){
			
			sql =
				"select * from PA210007 where PA210007001=? and PA210007008=?  and (PA210007006=? or pa210007006=4) order by PA210007007";

			hs.put("1", this.getOperation().getStringAt("Apply_kind"));
			hs.put("2", this.getOperation().getStringAt("Apply_stage"));//申请或台帐
			hs.put("3", pagetype);
			

			}else{
			
				sql =
								"select * from PA210007 where PA210007001=? and PA210007008=? and PA210007002=? and (PA210007006=? or pa210007006=4) order by PA210007007";

							hs.put("1", this.getOperation().getStringAt("Apply_kind"));
							hs.put("2", this.getOperation().getStringAt("Apply_stage"));//申请或台帐
				            hs.put("3", apporapprove);
							hs.put("4", pagetype);
			
			}
			
			in = this.Query(conn, sql, hs);
            
            
		} catch (Exception ex) {
			throw new TranFailException(
				"100305",
				"icbc.cmis.util.GeneralApplyFirstFormDao.getHidden()");
		} finally {
			this.closeConnection();
		}
		return in;
	}

}
