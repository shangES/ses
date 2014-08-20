package icbc.cmis.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import oracle.jdbc.internal.OracleTypes;

import icbc.cmis.base.CmisConstance;
import icbc.cmis.base.KeyedDataCollection;
import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.operation.DummyOperation;
import icbc.cmis.service.CmisDao;
import icbc.cmis.tfms.AA.AssureAssociationDAO;
import icbc.cmis.base.genMsg;

import java.util.Collections;


/*************************************************************
 * 
 * <b>创建日期: </b> 2006-5-11<br>
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

public abstract class GeneralApplyAccountDao extends GeneralViewBaseDM {
	AssureAssociationDAO dao = new AssureAssociationDAO(this.getOperation());
	String sys_date = CmisConstance.getWorkDate("yyyyMMdd");
	/**
	 * <b>构造函数</b><br>
	 * @param op
	 */
	protected String baseWebPath =
		(String) CmisConstance.getParameterSettings().get("webBasePath");
	public GeneralApplyAccountDao() {

	}
	/**
	 * 存储转换后的primaryInfo，该参数从首页生成并传入
	 */
	public Hashtable initFormImf = new Hashtable();
	/**
	 * 
	 * <b>功能描述: 在新增前，程序自动调用该函数</b><br>
	 * <p> </p>
	 * @param formID 表单号
	 * @param dataCollection  表单数据集合
	 * @param initFormImf     转换后的primaryInfo
	 * @param conn            数据库连接
	 * @return
	 * @throws TranFailException
	 *
	 */
	abstract public boolean beforeInsert(
		String formID,
		Hashtable dataCollection,
		Hashtable initFormImf,
		Connection conn)
		throws TranFailException;
		
		
	public boolean beforeInsertDue(
		String formID,
		Hashtable dataCollection,
		Hashtable initFormImf,
		String oneJjName,
		Connection conn)
		throws TranFailException
		{return true;};

	abstract public Hashtable queryParameters(Connection conn, Hashtable hs)
		throws TranFailException;
	/**
	 * 
	 * <b>功能描述: 在新增后，程序自动调用该函数</b><br>
	 * <p> </p>
	 * @param formID 表单号
	 * @param dataCollection  表单数据集合
	 * @param initFormImf     转换后的primaryInfo
	 * @param conn            数据库连接
	 * @return
	 * @throws TranFailException
	 *
	 */
	abstract public boolean afterInsert(
		String formID,
		Hashtable dataCollection,
		Hashtable initFormImf,
		Connection conn)
		throws TranFailException;
	/**
			 * 
			 * <b>功能描述: 在查询前，程序自动调用该函数</b><br>
			 * <p> </p>
			 * @param formID 表单号
			 * @param dataCollection  表单数据集合
			 * @param initFormImf     转换后的primaryInfo
			 * @param conn            数据库连接
			 * @return
			 * @throws TranFailException
			 *
			 */
	abstract public boolean beforequery(
		String formID,
		Hashtable initFormImf,
		Connection conn)
		throws TranFailException;
	/**
			 * 
			 * <b>功能描述: 在查询后，程序自动调用该函数</b><br>
			 * <p> </p>
			 * @param formID 表单号
			 * @param dataCollection  表单数据集合
			 * @param initFormImf     转换后的primaryInfo
			 * @param conn            数据库连接
			 * @return
			 * @throws TranFailException
			 *
			 */
	abstract public boolean afterquery(
		String formID,
		Hashtable initFormImf,Hashtable hs,
		Connection conn)
		throws TranFailException;
	/**
			 * 
			 * <b>功能描述: 在修改前，程序自动调用该函数</b><br>
			 * <p> </p>
			 * @param formID 表单号
			 * @param dataCollection  表单数据集合
			 * @param initFormImf     转换后的primaryInfo
			 * @param conn            数据库连接
			 * @return
			 * @throws TranFailException
			 *
			 */
	abstract public boolean beforemodify(
		String formID,
		Hashtable dataCollection,
		Hashtable primaryDataCollection,
		Hashtable initFormImf,
		Connection conn)
		throws TranFailException;
	/**
			 * 
			 * <b>功能描述: 在修改后，程序自动调用该函数</b><br>
			 * <p> </p>
			 * @param formID 表单号
			 * @param dataCollection  表单数据集合
			 * @param initFormImf     转换后的primaryInfo
			 * @param conn            数据库连接
			 * @return
			 * @throws TranFailException
			 *
			 */
	abstract public boolean aftermodify(
		String formID,
		Hashtable dataCollection,
		Hashtable primaryDataCollection,
		Hashtable initFormImf,
		Connection conn)
		throws TranFailException;
	/**
			 * 
			 * <b>功能描述: 在删除前，程序自动调用该函数</b><br>
			 * <p> </p>
			 * @param formID 表单号
			 * @param dataCollection  表单数据集合
			 * @param initFormImf     转换后的primaryInfo
			 * @param conn            数据库连接
			 * @return
			 * @throws TranFailException
			 *
			 */
	abstract public boolean beforedelete(
		String formID,
		Hashtable primaryDataCollection,
		Hashtable initFormImf,
		Connection conn)
		throws TranFailException;
	/**
			 * 
			 * <b>功能描述: 在删除后，程序自动调用该函数</b><br>
			 * <p> </p>
			 * @param formID 表单号
			 * @param dataCollection  表单数据集合
			 * @param initFormImf     转换后的primaryInfo
			 * @param conn            数据库连接
			 * @return
			 * @throws TranFailException
			 *
			 */
	abstract public boolean afterdelete(
		String formID,
		Hashtable primaryDataCollection,
		Hashtable initFormImf,
		Connection conn)
		throws TranFailException;
	/**
	 * 
	 * <b>功能描述:查询时，调用该函数设置主键值，这些值从primaryInfo中读入 </b><br>
	 * <p> </p>
	 * @param ps
	 * @param initFormImf
	 * @param tablename
	 * @throws TranFailException
	 *
	 */

	abstract public void setQueyParameters(
		PreparedStatement ps,
		Hashtable initFormImf,
		String tablename)
		throws TranFailException;
		

	/**
	 *  
	 * <b>功能描述: 查询的对外接口</b><br>
	 * <p>	</p>
	 * @see icbc.cmis.util.GeneralViewBaseDM#query(java.lang.String, java.lang.String)
	 * @param formID
	 * @param primaryInfo
	 * @return
	 * @throws TranFailException
	 *
	 */
	public Hashtable query(String formID, String primaryInfo)
		throws TranFailException {
		Hashtable hs = new Hashtable();   //存放合同和借据的所有信息
		Hashtable ht_hs=new Hashtable();  //存放合同的相关信息
		Hashtable jj_hs=new Hashtable();  //存放借据的相关信息
		try {
			/*
			 * 调用转换函数，转换primaryInfo
			 */
			this.transferToInitForm(primaryInfo);
			this.getConnection();
			//调用查询前函数
			boolean bl =this.beforequery(formID, initFormImf, conn);
			if (bl == true) {
				//调用查询的实现接口函数
				//查询合同相关信息
				ht_hs = this.query_combine(formID, initFormImf, conn);
				//查询借据相关信息
				jj_hs= this.query_combineDue(formID, initFormImf, conn);
				hs.put("ht_hs",ht_hs);
				hs.put("jj_hs",jj_hs);
				//hs.put("jj_hs",new Hashtable());
				//调用查询后函数
				bl = this.afterquery(formID, initFormImf,hs,conn);
				if (bl != true) {
					conn.rollback();
					this.closeConnection();
					throw new TranFailException(
						"100792",
						"icbc.cmis.util.GeneralApplyAccountDao.query()",
						genMsg.getErrMsg("100792", "查询|afterquery"));
				}
				this.closeConnection();
			} else {
				conn.rollback();
				this.closeConnection();
				throw new TranFailException(
					"100792",
					"icbc.cmis.util.GeneralApplyAccountDao.query()",
					genMsg.getErrMsg("100792", "查询|beforequery"));
			}

		} catch (TranFailException e) {
			this.closeConnection();
			throw e;

		} catch (Exception e2) {
			this.closeConnection();
			throw new TranFailException(
				"100792",
				"icbc.cmis.util.GeneralApplyAccountDao.query()",
				genMsg.getErrMsg("100792", "查询|query_combine"));
		}
		finally
		{
			this.closeConnection();
		}

		return hs;
	}

	public int insert(
		String formID,
		Hashtable dataCollection,
		String primaryInfo)
		throws TranFailException {

		try {
			//add by zap
				//存放合同的相关信息
				Hashtable htdataCollection=(Hashtable)dataCollection.get("ht_hs");
				//存放借据相关信息
				Hashtable jiejudataCollection=(Hashtable)dataCollection.get("jj_hs");
			//end by zap
			/*
			 * 调用转换函数，转换primaryInfo
			 */
			this.transferToInitForm(primaryInfo);
			this.getConnection();
			//  调用查询前函数
			boolean bl =this.beforeInsert(formID, htdataCollection, initFormImf, conn);
			
			if (bl == true) {
								//合同新增(modify by zap)
				bl =this.insert_combine(formID,htdataCollection,initFormImf,conn);
				 //end modify by zap
								//add by zap			
				//借据新增	
				if(jiejudataCollection!=null)
				{
					if(jiejudataCollection.size()>0)
					{	
						//存放所有的借据
						Enumeration eu = jiejudataCollection.keys();
						while (eu.hasMoreElements()) 
						{
							//oneJiejuName为一条借据的键值
								 String oneJiejuName = (String) eu.nextElement();
								 if(oneJiejuName.indexOf("#")!=-1)
								 {
							 //oneJiejuInfo为一条借据的信息
							 Hashtable oneJiejuInfo = (Hashtable) jiejudataCollection.get(oneJiejuName);
							 //add by zap(20070413下午)
							 bl =this.beforeInsertDue(formID, jiejudataCollection, htdataCollection, oneJiejuName,conn);
							 bl =this.insert_DueTable(formID,jiejudataCollection,initFormImf,oneJiejuName,conn);
							}
							//end by zap 
						}
					
					}
				}
				 //end by zap
				bl = this.insertRate(conn, initFormImf);	
				if (bl != true) {
					conn.rollback();
					this.closeConnection();
					throw new TranFailException(
						"100792",
						"icbc.cmis.util.GeneralApplyAccountDao.insert()",
						genMsg.getErrMsg("100792", "新增|insert_combine"));
				}
                
				bl = this.insertSychronize(conn, initFormImf);
				if (bl != true) {
					conn.rollback();
					this.closeConnection();
					throw new TranFailException(
						"100792",
						"icbc.cmis.util.GeneralApplyAccountDao.insert()",
						genMsg.getErrMsg("100792", "新增|insertSychronize"));
				}
				bl =
					this.afterInsert(formID, dataCollection, initFormImf, conn);
				if (bl != true) {
					conn.rollback();
					this.closeConnection();
					throw new TranFailException(
						"100792",
						"icbc.cmis.util.GeneralApplyAccountDao.insert()",
						genMsg.getErrMsg("100792", "新增|afterInsert"));
				}
				//借据号同步(重新进行编号)
				//if(((String)initFormImf.get("Apply_stage")).equals("0"))
				//{
					bl=this.updateDueSychronize(conn,initFormImf);
					if (bl != true) 
					{
						conn.rollback();
						this.closeConnection();
						throw new TranFailException(
							"100792",
							"icbc.cmis.util.GeneralApplyAccountDao.insert()",
							genMsg.getErrMsg("100792", "新增|updateDueSychronize"));
						}
				//}
				//借据号同步结束
				conn.commit();
				this.closeConnection();
			} else {
				conn.rollback();
				this.closeConnection();
				throw new TranFailException(
					"100792",
					"icbc.cmis.util.GeneralApplyAccountDao.insert()",
					genMsg.getErrMsg("100792", "新增|beforeInsert"));

			}
		} catch (TranFailException e) {
			try {
				this.daoRollback();
			} catch (Exception e1) {
				
				this.closeConnection();
			}
			this.closeConnection();
			throw e;

		} catch (Exception e) {
			try {
				this.daoRollback();
			} catch (Exception e1) {
				this.closeConnection();
			}
			this.closeConnection();
			throw new TranFailException(
				"100792",
				"icbc.cmis.util.GeneralApplyAccountDao.insert()",
				genMsg.getErrMsg("100792", "新增|insert"));
		}
		return 1;
	}

	public int modify(
		String formID,
		Hashtable dataCollection,
		Hashtable primaryDataCollection,
		String primaryInfo)
		throws TranFailException {
		try {
			this.getConnection();
			this.transferToInitForm(primaryInfo);
			//add by zap
			//存放合同的相关信息
			 Hashtable htdataCollection=(Hashtable)dataCollection.get("ht_hs");
			 //存放借据相关信息
			 Hashtable jiejudataCollection=(Hashtable)dataCollection.get("jj_hs");			 
			//存放合同的相关信息
			 Hashtable htKeyCollection=(Hashtable)primaryDataCollection.get("ht_key");
			//存放借据相关信息
			 Hashtable jiejuKeyCollection=(Hashtable)primaryDataCollection.get("jj_key");
			//end by zap
			boolean bl=false;
			bl =this.beforemodify(
					formID,
					htdataCollection,
					primaryDataCollection,
					initFormImf,
					conn);
								//合同修改
				bl =this.update_combine(formID,htdataCollection,htKeyCollection,initFormImf,conn);
									//借据操作
								if(jiejudataCollection!=null)  	
								{
						if(jiejudataCollection.size()>0)
						{	
							//存放所有的借据
							Enumeration eu = jiejudataCollection.keys();
							while (eu.hasMoreElements()) 
							{
								//oneJiejuName为一条借据的键值
								 String oneJiejuName = (String) eu.nextElement();
								 //oneJiejuInfo为一条借据的信息
								if(oneJiejuName.indexOf("#")!=-1)
								{
								 Hashtable oneJiejuInfo = (Hashtable) jiejudataCollection.get(oneJiejuName);
								 Hashtable oneJiejuKey = (Hashtable) jiejuKeyCollection.get(oneJiejuName);
								
								 String doWhat=(String)oneJiejuInfo.get("dowhat");
								 if("add".equals(doWhat))
										bl =this.insert_DueTable(formID,jiejudataCollection,initFormImf,oneJiejuName,conn);
								 else if("mod".equals(doWhat))
										bl =this.update_combineDue(formID,jiejudataCollection,oneJiejuKey,initFormImf,oneJiejuName,conn);
								 else if("del".equals(doWhat))
								 {  
									bl =this.delete_combineDue(formID,jiejudataCollection,initFormImf,oneJiejuName,conn);
									//增加对借据附属信息删除的支持
									//this.deleteAppertainInfo(conn,initFormImf,oneJiejuName.substring(oneJiejuName.indexOf("#")+1,oneJiejuName.length()));
								 }
										  
								 }  
							}
			
						}
				}
				bl = this.insertRate(conn, initFormImf);		
				if (bl != true) {
					conn.rollback();
					this.closeConnection();
					throw new TranFailException(
						"100792",
						"icbc.cmis.util.GeneralApplyAccountDao.modify()",
						genMsg.getErrMsg("100792", "修改|update_combine"));
				}
				bl = this.updateSychronize(conn, initFormImf);
				
				if (bl != true) {
					conn.rollback();
					this.closeConnection();
					throw new TranFailException(
						"100792",
						"icbc.cmis.util.GeneralApplyAccountDao.modify()",
						genMsg.getErrMsg("100792", "修改|updateSychronize"));
				}
				bl =
					this.aftermodify(
						formID,
						dataCollection,
						primaryDataCollection,
						initFormImf,
						conn);
				if (bl != true) {
					conn.rollback();
					this.closeConnection();
					throw new TranFailException(
						"100792",
						"icbc.cmis.util.GeneralApplyAccountDao.modify()",
						genMsg.getErrMsg("100792", "修改|aftermodify"));
				}
				//借据号同步(重新进行编号)
			//if(((String)initFormImf.get("Apply_stage")).equals("0"))
			//{
				bl=this.updateDueSychronize(conn,initFormImf);
				if (bl != true) 
				{
					conn.rollback();
					this.closeConnection();
					throw new TranFailException(
						"100792",
						"icbc.cmis.util.GeneralApplyAccountDao.modify()",
						genMsg.getErrMsg("100792", "修改|updateDueSychronize"));
					
				}
		//	}
				//借据号同步结束
			conn.commit();
			this.closeConnection();
		} catch (TranFailException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				this.closeConnection();
			}
			this.closeConnection();
			throw e;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				this.closeConnection();
			}
			this.closeConnection();
			throw new TranFailException(
				"100792",
				"icbc.cmis.util.GeneralApplyAccountDao.modify()",
				genMsg.getErrMsg("100792", "修改|modify"));
		}

		return 1;
	}

	public int delete(
		String formID,
		Hashtable primaryDataCollection,
		String primaryInfo)
		throws TranFailException {
		try {
			this.getConnection();
			this.transferToInitForm(primaryInfo);
			//add by zap
		 //存放合同的相关信息
			Hashtable htdataCollection=(Hashtable)primaryDataCollection.get("ht_hs");
			int tempInt=htdataCollection.size();
			//存放借据相关信息
			Hashtable jiejudataCollection=(Hashtable)primaryDataCollection.get("jj_hs");
			 //end by zap
			boolean bl =true;
				this.beforedelete(
					formID,
					primaryDataCollection,
					initFormImf,
					conn);
			if (bl == true) {
				if ("0".equals((String) initFormImf.get("apporapprove"))) {

					bl = this.ObjectionDelete(initFormImf, conn);
					if (bl != true) {
						conn.rollback();
						this.closeConnection();
						throw new TranFailException(
							"100792",
							"icbc.cmis.util.GeneralApplyAccountDao.delete()",
							genMsg.getErrMsg("100792", "删除|ObjectionDelete"));
					}
				}
				bl =
					this.delete_combine(
						formID,
						htdataCollection,
						initFormImf,
						conn);
						 //add by zap			
				//借据新增	
				if(jiejudataCollection!=null)
				{
						if(jiejudataCollection.size()>0)
						{	
							//存放所有的借据
							Enumeration eu = jiejudataCollection.keys();
							while (eu.hasMoreElements()) 
							{
								//oneJiejuName为一条借据的键值
								 String oneJiejuName = (String) eu.nextElement();
								if(oneJiejuName.indexOf("#")!=-1)
									{
									 //oneJiejuInfo为一条借据的信息
									 Hashtable oneJiejuInfo = (Hashtable) jiejudataCollection.get(oneJiejuName);
									 bl =this.delete_combineDue(formID,jiejudataCollection,initFormImf,oneJiejuName,conn);
									 //增加对借据附属信息删除的支持
									//this.deleteAppertainInfo(conn,initFormImf,oneJiejuName.substring(oneJiejuName.indexOf("#")+1,oneJiejuName.length()));
									}
							}
			
						}
				}
				
			//end by zap
				if (bl != true) {
					conn.rollback();
					this.closeConnection();
					throw new TranFailException(
						"100792",
						"icbc.cmis.util.GeneralApplyAccountDao.delete()",
						genMsg.getErrMsg("100792", "删除|delete_combine"));
				}
				//add by zap
				bl=this.deleteImageInfo(conn, initFormImf);
				if (bl != true)
				 {
					conn.rollback();
					this.closeConnection();
					throw new TranFailException(
						"100792",
						"icbc.cmis.util.GeneralApplyAccountDao.delete()",
						genMsg.getErrMsg("100792", "删除|deleteImageInfo"));
								}
				//end by zap
				this.deleteRate(conn, initFormImf);
				bl = this.deleteSychronize(conn, initFormImf);
				if (bl != true) {
					conn.rollback();
					this.closeConnection();
					throw new TranFailException(
						"100792",
						"icbc.cmis.util.GeneralApplyAccountDao.delete()",
						genMsg.getErrMsg("100792", "删除|deleteSychronize"));
				}

				bl =
					this.afterdelete(
						formID,
						primaryDataCollection,
						initFormImf,
						conn);
				if (bl != true) {
					conn.rollback();
					this.closeConnection();
					throw new TranFailException(
						"100792",
						"icbc.cmis.util.GeneralApplyAccountDao.delete()",
						genMsg.getErrMsg("100792", "删除|afterdelete"));
				}
			} else {
				conn.rollback();
				this.closeConnection();
				throw new TranFailException(
					"100792",
					"icbc.cmis.util.GeneralApplyAccountDao.delete()",
					genMsg.getErrMsg("100792", "删除|beforedelete"));
			}
			conn.commit();
			//conn.rollback();
			this.closeConnection();
		} catch (TranFailException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				this.closeConnection();
			}
			this.closeConnection();
			throw e;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				this.closeConnection();
			}
			this.closeConnection();
			throw new TranFailException(
				"100792",
				"icbc.cmis.util.GeneralApplyAccountDao.delete()",
				genMsg.getErrMsg("100792", "删除|delete"));
		}

		return 1;
	}
	/**
	 * 
	 * <b>功能描述: 具体的insert操作（合同）实现类，根据pa210006表的对应关系定义，实现insert操作</b><br>
	 * <p> </p>
	 * @param formID
	 * @param primaryDataCollection
	 * @param initFormImf
	 * @param conn
	 * @return
	 *
	 */
	public boolean insert_combine(
		String formID,
		Hashtable primaryDataCollection,
		Hashtable initFormImf,
		Connection conn)
		throws TranFailException {
		Vector vc = new Vector();
		//从页面数据与业务表数据对应关系表中，取出对应关系存放到hs中
		Hashtable hs =
			this.transfor("1", primaryDataCollection, initFormImf, conn);
		try {

			String sql = "";
			PreparedStatement ps = null;
			int temphs=hs.size();
			Enumeration eu = hs.keys();
			String str = "";
			//以下为根据hs的存放的数据组织insert语句
			//hs的结构是表名为key的多个hashtable集合，每个集合存放多个字段信息集合
			while(eu.hasMoreElements()) {
				//str为表名
				str = (String) eu.nextElement();
				//tt为一张表的信息
				Hashtable tt = (Hashtable) hs.get(str);
				sql = "insert into " + str + " (";
				Enumeration tt_eu = tt.keys(); //tt_eu是表字段名集合
				while (tt_eu.hasMoreElements()) {
					sql += (String) tt_eu.nextElement() + ",";
				}
				sql = sql.substring(0, sql.length() - 1) + ") values(";
				for (int i = 0; i < tt.size(); i++) {
					sql += "?,";
				}
				sql = sql.substring(0, sql.length() - 1) + ")";
				int j = 1;
				Enumeration tt_eu1 = tt.keys();
				//xx为从外部传入的页面数据集合
				Hashtable xx =
					(Hashtable) primaryDataCollection.get(str.toLowerCase());
				ps = conn.prepareStatement(sql);
				System.out.println("insert_combinesql=="+sql);
				while (tt_eu1.hasMoreElements()) {
					Hashtable str_tt = (Hashtable) tt.get(tt_eu1.nextElement());
					System.out.println("insert_combineValue"+nullToEmpty(
					(String) xx.get((String) str_tt.get("formName"))));
					ps.setString(
						j++,
						nullToEmpty(
							(String) xx.get((String) str_tt.get("formName"))));			
				}
				ps.executeUpdate();
			}

		} catch (Exception e) {
			throw new TranFailException(
				"100792",
				"icbc.cmis.util.GeneralApplyAccountDao.insert_combine()",
				genMsg.getErrMsg("100792", "新增|insert_combine()"));
		}
		return true;
	}
	
	//add by zap
	/**
		 * 
		 * <b>功能描述: 具体的insert操作（借据）实现类，根据pa210006表的对应关系定义，实现insert操作</b><br>
		 * <p> </p>
		 * @param formID
		 * @param primaryDataCollection
		 * @param initFormImf
		 * @param conn
		 * @return
		 *
		 */
		public boolean insert_DueTable(
			String formID,
			Hashtable primaryDataCollection,
			Hashtable initFormImf,
			String oneJjName,
			Connection conn)
			throws TranFailException {
			Vector vc = new Vector();
			//从页面数据与业务表数据对应关系表中，取出对应关系存放到hs中
			Hashtable hs =
				 this.transforDue("1", primaryDataCollection, initFormImf, conn);
			try {

				String sql = "";
				PreparedStatement ps = null;
				Enumeration eu = hs.keys();
				String str = "";
				//以下为根据hs的存放的数据组织insert语句
				//hs的结构是表名为key的多个hashtable集合，每个集合存放多个字段信息集合
				while(eu.hasMoreElements()) {
					//str为表名
					str = (String) eu.nextElement();
					//tt为一张表的信息
					Hashtable tt = (Hashtable) hs.get(str);
					sql = "insert into " + str + " (";
					Enumeration tt_eu = tt.keys(); //tt_eu是表字段名集合
					while (tt_eu.hasMoreElements()) {
						sql += (String) tt_eu.nextElement() + ",";
					}
					sql = sql.substring(0, sql.length() - 1) + ") values(";
					for (int i = 0; i < tt.size(); i++) {
						sql += "?,";
					}
					sql = sql.substring(0, sql.length() - 1) + ")";
					int j = 1;
					Enumeration tt_eu1 = tt.keys();
					//xx为从外部传入的页面数据集合
					//增加对借据号不是数字类型的支持
					   oneJjName=oneJjName.substring(0,oneJjName.indexOf("#")).toLowerCase()+oneJjName.substring(oneJjName.indexOf("#"),oneJjName.length());
					Hashtable xx =
						(Hashtable) primaryDataCollection.get(oneJjName);
					ps = conn.prepareStatement(sql);
					System.out.println("insert_DueTableSql=="+sql);
					while (tt_eu1.hasMoreElements()) {
						Hashtable str_tt = (Hashtable) tt.get(tt_eu1.nextElement());
						System.out.println("insert_DueTableVlue="+str_tt.get("formName")+nullToEmpty(
						(String) xx.get((String) str_tt.get("formName"))));
						ps.setString(
							j++,
							nullToEmpty(
								(String) xx.get((String) str_tt.get("formName"))));
								}

					ps.executeUpdate();
				}

			} catch (Exception e) {
				throw new TranFailException(
					"100792",
					"icbc.cmis.util.GeneralApplyAccountDao.insert_DueTable()",
					genMsg.getErrMsg("100792", "新增|insert_DueTable()"));
			}
			return true;
		}
	
	//end by zap
	/**
	 * 
	 * <b>功能描述: 具体的update操作实现类(合同)，根据pa210006表的对应关系定义，实现update操作
	 * </b>组织数据的过程与insert类似<br>
	 * <p> </p>
	 * @param formID
	 * @param primaryDataCollection
	 * @param initFormImf
	 * @param conn
	 * @return
	 *
	 */
	public boolean update_combine(
		String formID,
		Hashtable dataCollection,
		Hashtable primaryDataCollection,
		Hashtable initFormImf,
		Connection conn)
		throws TranFailException {
			Vector vc = new Vector();
					Hashtable hs =
						this.transfor("1", primaryDataCollection, initFormImf, conn);
					try {

						String sql = "";
						PreparedStatement ps = null;
						Enumeration eu = hs.keys();
						String str = "";
						while (eu.hasMoreElements()) {
							String[] pkStr = new String[3];
							int a = 0;
							int b = 0;
							String[] updateStr = new String[100];
							str = (String) eu.nextElement();

							Hashtable tt = (Hashtable) hs.get(str);
							sql = "update  " + str + " set ";
							Enumeration tt_eu = tt.keys();
							while (tt_eu.hasMoreElements()) {
								Hashtable detail_hs =
									(Hashtable) tt.get((String) tt_eu.nextElement());
								if ("1".equals((String) detail_hs.get("isUpdate"))
									&& "0".equals((String) detail_hs.get("isPk"))) {
									sql += (String) detail_hs.get("colName") + "=?,";
									updateStr[b++] = (String) detail_hs.get("colName");

								}
							}
							sql = sql.substring(0, sql.length() - 1) + "  where ";
							Enumeration tt_eu1 = primaryDataCollection.keys();
							while (tt_eu1.hasMoreElements()) {
								String ElementName = (String) tt_eu1.nextElement();
								//System.out.println("ElementName"+ElementName);
								if (primaryDataCollection.get(ElementName)
									instanceof String && ElementName.substring(0,ElementName.length()-3).equals(str)) {
                                   
									String detail_hs =
										(String) primaryDataCollection.get(ElementName);
									sql += ElementName + "=? and ";
									pkStr[a++] = ElementName;
								}
								//}
							}
							int jj = sql.lastIndexOf("and");
							sql = sql.substring(0, jj - 1);

							int jk = 1;
							Enumeration tt_eu3 = tt.keys();
							Hashtable xx1 =
								(Hashtable) dataCollection.get(str.toLowerCase());
							ps = conn.prepareStatement(sql);
							System.out.println("update_combineSQL="+sql);
							while (jk <= 100) {
							if (updateStr[jk - 1] != null
								&& !"".equals(updateStr[jk - 1]))
								{
									
									ps.setString(
												jk++,
												nullToEmpty((String) xx1.get(updateStr[jk - 2])));
									System.out.println("update_combineValue="+nullToEmpty((String) xx1.get(updateStr[jk - 2])));
								}
								
							else
								break;
							}
								
//								while (jk <= 54) {
//								if (updateStr[jk - 1] != null
//									)
//									{
//										String tempStr=nullToEmpty((String) xx1.get(updateStr[jk - 1]));
//										System.out.println(updateStr[jk - 1]+"===="+tempStr);
//										ps.setString(
//													jk,tempStr);
//													jk++;
//									}
//
//								else
//									break;
//								}
								//this.setUpdateParameters(ps, initFormImf, str);
								int j = 1;
							while (j <= 3) {
								if (pkStr[j - 1] != null && !"".equals(pkStr[j - 1]))
								{
									ps.setString(
										jk++,
										nullToEmpty(
											(String) primaryDataCollection.get(
												pkStr[j - 1])));
									System.out.println("update_combinePrimaryValue="+nullToEmpty(
																		(String) primaryDataCollection.get(
																			pkStr[j - 1])));		
								}
								j++;
							}
							

							ps.executeUpdate();
						}

					} catch (Exception e) {
						throw new TranFailException(
							"100792",
							"icbc.cmis.util.GeneralApplyAccountDao.update_combine()",
							genMsg.getErrMsg("100792", "修改|update_combine()"));
					}

					return true;
	}
	/**
		 * 
		 * <b>功能描述: 具体的update操作实现类(借据)，根据pa210006表的对应关系定义，实现update操作
		 * </b>组织数据的过程与insert类似<br>
		 * <p> </p>
		 * @param formID
		 * @param primaryDataCollection
		 * @param initFormImf
		 * @param conn
		 * @return
		 *
		 */
		public boolean update_combineDue(
			String formID,
			Hashtable dataCollection,
			Hashtable primaryDataCollection,
			Hashtable initFormImf,
					String    oneJiejuName,
			Connection conn)
			throws TranFailException {
			Vector vc = new Vector();
			Hashtable hs =
				this.transforDue("1", primaryDataCollection, initFormImf, conn);
			try {
				String sql = "";
				PreparedStatement ps = null;
				Enumeration eu = hs.keys();
				String str = "";
				String[] pkStr = new String[4];
				int a = 0;
				String[] updateStr = new String[100];
				int b = 0;
				while (eu.hasMoreElements()) {
					str = (String) eu.nextElement();

					Hashtable tt = (Hashtable) hs.get(str);
					sql = "update  " + str + " set ";
					Enumeration tt_eu = tt.keys();
					while (tt_eu.hasMoreElements()) {
						Hashtable detail_hs =
							(Hashtable) tt.get((String) tt_eu.nextElement());
						if ("1".equals((String) detail_hs.get("isUpdate"))
							&& "0".equals((String) detail_hs.get("isPk"))) {

							sql += (String) detail_hs.get("colName") + "=?,";
							updateStr[b++] = (String) detail_hs.get("colName");
						}
					}
					sql = sql.substring(0, sql.length() - 1) + "  where ";
					Enumeration tt_eu1 = primaryDataCollection.keys();
					while (tt_eu1.hasMoreElements()) {
						String ElementName = (String) tt_eu1.nextElement();
						if (primaryDataCollection.get(ElementName)
							instanceof String) {
							String detail_hs =
								(String) primaryDataCollection.get(ElementName);
							sql += ElementName + "=? and ";
							pkStr[a++] = ElementName;
						}
					}
					int jj = sql.lastIndexOf("and");
					sql = sql.substring(0, jj - 1);
					int jk = 1;
					Enumeration tt_eu3 = tt.keys();
					//支持借据号为字母的情况
					Hashtable xx1 =
					(Hashtable) dataCollection.get((oneJiejuName.substring(0,oneJiejuName.indexOf('#'))).toLowerCase()+oneJiejuName.substring(oneJiejuName.indexOf('#'),oneJiejuName.length()));
						//(Hashtable) dataCollection.get(oneJiejuName.toLowerCase());
					ps = conn.prepareStatement(sql);
					System.out.println("update_combineDueSQL="+sql);
					while (jk <= 100) {
						if (updateStr[jk - 1] != null
							&& !"".equals(updateStr[jk - 1]))
							{
								
								ps.setString(
										jk++,
										nullToEmpty((String) xx1.get(updateStr[jk - 2])));
								System.out.println("update_combineDueValue="+nullToEmpty((String) xx1.get(updateStr[jk - 2])));
							}
						else
							break;
					}

					int j = 1;
					while (j <= 4) {

						if (pkStr[j - 1] != null && !"".equals(pkStr[j - 1]))
						{
							
							ps.setString(
											jk++,
											nullToEmpty(
												(String) primaryDataCollection.get(
													pkStr[j - 1])));
							System.out.println("update_combineDuePrimaryValue="+nullToEmpty(
														(String) primaryDataCollection.get(
															pkStr[j - 1])));
						}
						j++;
					}

					ps.executeUpdate();
				}

			} catch (Exception e) {
				throw new TranFailException(
					"100792",
					"icbc.cmis.util.GeneralApplyAccountDao.update_combineDue()",
					genMsg.getErrMsg("100792", "修改|update_combineDue()"));
			}

			return true;
		}
	/**
			 * 
			 * <b>功能描述: 具体的delete操作(合同)实现类，根据pa210006表的对应关系定义，实现delete操作
			 * </b>组织数据的过程与insert类似<br>
			 * <p> </p>
			 * @param formID
			 * @param primaryDataCollection
			 * @param initFormImf
			 * @param conn
			 * @return
			 *
			 */
	public boolean delete_combine(
		String formID,
		Hashtable primaryDataCollection,
		Hashtable initFormImf,
		Connection conn)
		throws TranFailException {
		String sql = "";
		PreparedStatement ps = null;
		Hashtable hs =
			this.transfor("1", primaryDataCollection, initFormImf, conn);
		Enumeration eu = hs.keys();
		String str = "";
		
		try {
			while (eu.hasMoreElements()) {
				String[] pkStr = new String[4];
						int a = 0;
				str = (String) eu.nextElement();
				Hashtable tt = (Hashtable) hs.get(str);
				sql = "delete  " + str + " where  ";
				Enumeration tt_eu = tt.keys();
				while (tt_eu.hasMoreElements()) {
					Hashtable detail_hs =
						(Hashtable) tt.get((String) tt_eu.nextElement());
					if ("1".equals((String) detail_hs.get("isPk")) || "2".equals((String) detail_hs.get("isPk")) ) {
						sql += (String) detail_hs.get("colName") + "=?  and  ";
						pkStr[a++] = (String) detail_hs.get("colName");
					}
				}
				int jj = sql.lastIndexOf("and");
				sql = sql.substring(0, jj - 1);
				int j = 1;
				Enumeration tt_eu2 = tt.keys();
				Hashtable xx =
					(Hashtable) primaryDataCollection.get(str.toLowerCase());
				ps = conn.prepareStatement(sql);
				System.out.println("delete_combineSql="+sql);
				while (j <= 4) {
					Hashtable str_tt = (Hashtable) tt.get(tt_eu2.nextElement());
					if (pkStr[j - 1] != null && !"".equals(pkStr[j - 1]))
					{
						
						ps.setString(
									j++,
									nullToEmpty((String) xx.get(pkStr[j - 2])));
						System.out.println("delete_combinePrimaryValue="+nullToEmpty((String) xx.get(pkStr[j - 2])));
					}						
					else
						j++;
				}

				ps.executeUpdate();
			}

		} catch (Exception e) {
			throw new TranFailException(
				"100792",
				"icbc.cmis.util.GeneralApplyAccountDao.delete_combine()",
				genMsg.getErrMsg("100792", "删除|delete_combine()"));
		}

		return true;
	}
	/**
	 * 
	 * <b>功能描述: 具体的delete操作实现类(借据)，根据pa210006表的对应关系定义，实现delete操作
	 * </b>组织数据的过程与insert类似<br>
	 * <p> </p>
	 * @param formID
	 * @param primaryDataCollection
	 * @param initFormImf
	 * @param conn
	 * @return
	 *
	 */
		public boolean delete_combineDue(
			String formID,
			Hashtable primaryDataCollection,
			Hashtable initFormImf,
			String    oneJjName,
			Connection conn)
			throws TranFailException {
			String sql = "";
			PreparedStatement ps = null;
			Hashtable hs =
				this.transforDue("1", primaryDataCollection, initFormImf, conn);
			Enumeration eu = hs.keys();
			String str = "";
			String[] pkStr = new String[3];
			int a = 0;
			try {
				while (eu.hasMoreElements()) {
					str = (String) eu.nextElement();

					Hashtable tt = (Hashtable) hs.get(str);
					sql = "delete  " + str + " where  ";
					Enumeration tt_eu = tt.keys();
					while (tt_eu.hasMoreElements()) {
						Hashtable detail_hs =
							(Hashtable) tt.get((String) tt_eu.nextElement());
						if ("1".equals((String) detail_hs.get("isPk")) || "2".equals((String) detail_hs.get("isPk"))) {
							sql += (String) detail_hs.get("colName") + "=?  and  ";
							pkStr[a++] = (String) detail_hs.get("colName");
						}
					}
					int jj = sql.lastIndexOf("and");
					sql = sql.substring(0, jj - 1);
					int j = 1;
					Enumeration tt_eu2 = tt.keys();
					//支持借据号为字母的情况
					Hashtable xx =
					(Hashtable) primaryDataCollection.get((oneJjName.substring(0,oneJjName.indexOf('#'))).toLowerCase()+oneJjName.substring(oneJjName.indexOf('#'),oneJjName.length()));
						// (Hashtable) primaryDataCollection.get(oneJjName.toLowerCase());
					ps = conn.prepareStatement(sql);
					System.out.println("delete_combineDueSql="+sql);
					while (j <= 3) {
						Hashtable str_tt = (Hashtable) tt.get(tt_eu2.nextElement());
						if (pkStr[j - 1] != null && !"".equals(pkStr[j - 1]))
						{
							
							String  aaa=(String) xx.get(pkStr[j - 1]);
							ps.setString(
								j++,
								nullToEmpty((String)  xx.get(pkStr[j - 2])));
							System.out.println("delete_combineDuePrimaryValue="+nullToEmpty((String)  xx.get(pkStr[j - 2])));
						}
						else
							j++;
					}

					ps.executeUpdate();
				}

			} catch (Exception e) {
				throw new TranFailException(
					"100792",
					"icbc.cmis.util.GeneralApplyAccountDao.delete_combineDue()",
					genMsg.getErrMsg("100792", "删除|delete_combineDue()"));
			}

			return true;
		}
	/**
					 * 
					 * <b>功能描述: 具体的query操作实现类(合同)，根据pa210006表的对应关系定义，实现query操作
					 * </b>组织数据的过程与insert类似<br>
					 * <p> </p>
					 * @param formID
					 * @param primaryDataCollection
					 * @param initFormImf
					 * @param conn
					 * @return
					 *
					 */
	public Hashtable query_combine(
		String formID,
		Hashtable initFormImf,
		Connection conn)
		throws TranFailException {
		Hashtable vc = new Hashtable();
		Hashtable hs = this.transfor("", null, initFormImf, conn);
		try {

			String sql = "";
			PreparedStatement ps = null;
			Enumeration eu = hs.keys();
			String str = "";
			while(eu.hasMoreElements()) {
				str = (String) eu.nextElement();
				Hashtable tt = (Hashtable) hs.get(str);
				sql = "select * from " + str + " where ";
				Enumeration tt_eu = tt.keys();
				while (tt_eu.hasMoreElements()) {
					Hashtable isPrimaryKey_tmp =
						(Hashtable) tt.get(tt_eu.nextElement());
					String isPrimaryKey = (String) isPrimaryKey_tmp.get("isPk");
					if ("1".equals(isPrimaryKey) || "2".equals(isPrimaryKey))
						sql += isPrimaryKey_tmp.get("colName") + "=?  and ";
				}
				int j = sql.lastIndexOf("and");
				sql = sql.substring(0, j - 1);
				ps = conn.prepareStatement(sql);
				//System.out.println(sql);
				this.setQueyParameters(ps, initFormImf, str);
				ResultSet rs = ps.executeQuery();
				Enumeration tt_eu2 = tt.keys();
				Hashtable vc1 = new Hashtable();
				if (rs.next()) {

					while (tt_eu2.hasMoreElements()) {

						Hashtable isPrimaryKey_tmp =
							(Hashtable) tt.get(tt_eu2.nextElement());
						vc1.put(
							(String) isPrimaryKey_tmp.get("formName"),
							nullToEmpty(
								rs.getString(
									(String) isPrimaryKey_tmp.get("colName"))));//把数据库中具体数据放到Hashtable中

					}

				}
				this.queryParameters(conn, vc1);
				vc.put(str.toLowerCase(), vc1);
			}
		} catch (Exception e) {
			throw new TranFailException(
				"100792",
				"icbc.cmis.util.GeneralApplyAccountDao.query_combine()",
				genMsg.getErrMsg("100792", "查询|query_combine()"));
		}

		return vc;
	}
	/**
						 * 
						 * <b>功能描述: 具体的query操作实现类(借据)，根据pa210006表的对应关系定义，实现query操作
						 * </b>组织数据的过程与insert类似<br>
						 * <p> </p>
						 * @param formID
						 * @param primaryDataCollection
						 * @param initFormImf
						 * @param conn
						 * @return
						 *
						 */
		public Hashtable query_combineDue(
			String formID,
			Hashtable initFormImf,
			Connection conn)
			throws TranFailException {
			Hashtable vc = new Hashtable();//存放每一条借据
			Hashtable hs = this.transforDue("", null, initFormImf, conn);
			try {

				String sql = "";
				PreparedStatement ps = null;
				Enumeration eu = hs.keys();
				String str = "";
				if (eu.hasMoreElements()) {
					str = (String) eu.nextElement();
					Hashtable tt = (Hashtable) hs.get(str);
					sql = "select * from " + str + " where ";
					Enumeration tt_eu = tt.keys();
					//add by zap
					String DueCode="";       //用于存放借据号
					//end by zap
					while (tt_eu.hasMoreElements()) {
						Hashtable isPrimaryKey_tmp =
							(Hashtable) tt.get(tt_eu.nextElement());
						String isPrimaryKey = (String) isPrimaryKey_tmp.get("isPk");
						//add by zap
						if("2".equals(isPrimaryKey))
								 DueCode=(String)isPrimaryKey_tmp.get("colName");
						//end by zap     
						if ("1".equals(isPrimaryKey))
							sql += isPrimaryKey_tmp.get("colName") + "=?  and ";
					}
					int j = sql.lastIndexOf("and");
					sql = sql.substring(0, j - 1);
					//add by zap(对借据进行排序)
					sql=sql+"order by "+DueCode+"" ;  
					//end by zap
					ps = conn.prepareStatement(sql);
					this.setQueyParameters(ps, initFormImf, str);
					ResultSet rs = ps.executeQuery();
					String tempDueCode="";
					
					while (rs.next()) {
						tempDueCode="";
						Hashtable vc1 = new Hashtable();
						Enumeration tt_eu2 = tt.keys();
						while (tt_eu2.hasMoreElements()) {
							Hashtable isPrimaryKey_tmp =
								(Hashtable) tt.get(tt_eu2.nextElement());
							vc1.put(
								(String) isPrimaryKey_tmp.get("formName"),
								nullToEmpty(
									rs.getString(
										(String) isPrimaryKey_tmp.get("colName"))));//把数据库中具体数据放到Hashtable中
							//add by zap
							if(DueCode.equals((String) isPrimaryKey_tmp.get("formName")))
							{
								tempDueCode	=rs.getString((String) isPrimaryKey_tmp.get("colName"));
							}
						}
						vc.put(str.toLowerCase()+"#"+tempDueCode, vc1);
						this.queryParameters(conn, vc1);
					    
					}
				}
				
			} catch (Exception e) {
				throw new TranFailException(
					"100792",
					"icbc.cmis.util.GeneralApplyAccountDao.query_combineDue()",
					genMsg.getErrMsg("100792", "查询|query_combineDue()"));
			}

			return vc;
		}

	/**
	 * 
	 * <b>功能描述: 将某个业务下（合同），某个表的信息转换到hashtable中，供其他模块使用</b><br>
	 * <p> </p>
	 * @param flag  对应PA210006006
	 * @param tablename  表名
	 * @param hs       
	 * @param conn
	 * @return
	 *
	 */
	public Hashtable tableTransfor(
		String flag,
		String tablename,
		Hashtable hs,
		Hashtable initFormImf,
		Connection conn)
		throws TranFailException {
		Hashtable tableSector = new Hashtable();
		try {
			String appID = (String) initFormImf.get("appkind");
			String appOrAccount = (String) initFormImf.get("apporapprove");
			String sql = "";
			if ("".equals(flag))
				sql =
					"select * from PA210006 where PA210006001=? and PA210006002=?  and (PA210006010 IS NULL OR PA210006010=?) and PA210006003=? ";
			else
				sql =
					"select * from PA210006 where PA210006001=? and PA210006002=? and (PA210006010 IS NULL OR PA210006010=?) and PA210006006=? and PA210006003=? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, appID);
			ps.setString(2, appOrAccount);
			ps.setString(3, "0");//0：代表合同
			if (!"".equals(flag)) {

				ps.setString(4, flag);
				ps.setString(5, tablename);
			} else {
				ps.setString(4, tablename);
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Hashtable tmp = new Hashtable();
				tmp.put("colName", nullToEmpty(rs.getString("PA210006004")));
				tmp.put("formName", nullToEmpty(rs.getString("PA210006005")));
				tmp.put("isPk", nullToEmpty(rs.getString("PA210006007")));
				//tmp.put("isPrimeTable", rs.getString("PA210006008"));
				tmp.put("isUpdate", nullToEmpty(rs.getString("PA210006006")));
				tmp.put("insert", nullToEmpty(rs.getString("PA210006009")));
				tmp.put("update", nullToEmpty(rs.getString("PA210006010")));
				tmp.put("delete", nullToEmpty(rs.getString("PA210006011")));
				tableSector.put(rs.getString("PA210006004"), tmp);
			}
			rs.close();

		} catch (Exception e) {
			throw new TranFailException(
				"100793",
				"icbc.cmis.util.GeneralApplyAccountDao.tableTransfor()",
				genMsg.getErrMsg("100793", tablename));
		}
		return tableSector;
	}
	
	/**
		 * 
		 * <b>功能描述: 将某个业务下（借据），某个表的信息转换到hashtable中，供其他模块使用</b><br>
		 * <p> </p>
		 * @param flag  对应PA210006006
		 * @param tablename  表名
		 * @param hs       
		 * @param conn
		 * @return
		 *
		 */
		public Hashtable tableTransforDue(
			String flag,
			String tablename,
			Hashtable hs,
			Hashtable initFormImf,
			Connection conn)
			throws TranFailException {
			Hashtable tableSector = new Hashtable();
			try {
				String appID = (String) initFormImf.get("appkind");
				String appOrAccount = (String) initFormImf.get("apporapprove");
				String sql = "";
				if ("".equals(flag))
					sql =
						"select * from PA210006 where PA210006001=? and PA210006002=?  and PA210006010=? and PA210006003=? ";
				else
					sql =
						"select * from PA210006 where PA210006001=? and PA210006002=? and PA210006010=? and PA210006006=? and PA210006003=? ";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, appID);
				ps.setString(2, appOrAccount);
				ps.setString(3, "1");//1：代表借据
				if (!"".equals(flag)) {

					ps.setString(4, flag);
					ps.setString(5, tablename);
				} else {
					ps.setString(4, tablename);
				}
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Hashtable tmp = new Hashtable();
					tmp.put("colName", nullToEmpty(rs.getString("PA210006004")));
					tmp.put("formName", nullToEmpty(rs.getString("PA210006005")));
					tmp.put("isPk", nullToEmpty(rs.getString("PA210006007")));
					//tmp.put("isPrimeTable", rs.getString("PA210006008"));
					tmp.put("isUpdate", nullToEmpty(rs.getString("PA210006006")));
					tmp.put("insert", nullToEmpty(rs.getString("PA210006009")));
					tmp.put("update", nullToEmpty(rs.getString("PA210006010")));
					tmp.put("delete", nullToEmpty(rs.getString("PA210006011")));
					tableSector.put(rs.getString("PA210006004"), tmp);
				}
				rs.close();

			} catch (Exception e) {
				throw new TranFailException(
					"100793",
					"icbc.cmis.util.GeneralApplyAccountDao.tableTransforDue()",
					genMsg.getErrMsg("100793", tablename));
			}
			return tableSector;
		}
	/**
	 * 
	 * <b>功能描述: 将某个业务下（合同），页面字段与业务表字段等信息的对应信息转换到hashtable中，供其他模块使用</b><br>
	 * <p> </p>
	 * @param flag   对应PA210006006
	 * @param hs     转换时需要用到的一些信息
	 * @param conn   数据库连接
	 * @return
	 *
	 */
	public Hashtable transfor(
		String flag,
		Hashtable hs,
		Hashtable initFormImf,
		Connection conn)
		throws TranFailException { //flag 对应PA210006006
		Hashtable tableSector = new Hashtable();
		try {

			String appID = (String) initFormImf.get("appkind");
			String appOrAccount = (String) initFormImf.get("apporapprove");
			String sql = "";
			if ("".equals(flag))
				sql =
					"select distinct PA210006003 from PA210006 where PA210006001=? and PA210006002=?  and (PA210006010 IS NULL OR PA210006010=?)";
			else
				sql =
					"select distinct PA210006003 from PA210006 where PA210006001=? and PA210006002=? and (PA210006010 IS NULL OR PA210006010=?) and PA210006006=? "; 
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, appID);
			ps.setString(2, appOrAccount);
			ps.setString(3, "0");//0：代表合同
			if (!"".equals(flag))
				ps.setString(4, flag);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Hashtable hs_tmp = new Hashtable();
				if (hs != null)
					hs_tmp =
						this.tableTransfor(
							flag,
							rs.getString(1),
							(Hashtable) hs.get(
								((String) rs.getString(1)).toLowerCase()),
							initFormImf,
							conn);
				else
					hs_tmp =
						this.tableTransfor(
							flag,
							rs.getString(1),
							initFormImf,
							initFormImf,
							conn);
				tableSector.put(rs.getString(1).toLowerCase(), hs_tmp);
			}
			rs.close();

		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException(
				"100794",
				"icbc.cmis.util.GeneralApplyAccountDao.transfor()"
					+ e.getMessage());
		}
		return tableSector;
	}
	
	/**
		 * 
		 * <b>功能描述: 将某个业务下(借据)，页面字段与业务表字段等信息的对应信息转换到hashtable中，供其他模块使用</b><br>
		 * <p> </p>
		 * @param flag   对应PA210006006
		 * @param hs     转换时需要用到的一些信息
		 * @param conn   数据库连接
		 * @return
		 * 
		 */
		public Hashtable transforDue(
			String flag,
			Hashtable hs,
			Hashtable initFormImf,
			Connection conn)
			throws TranFailException { //flag 对应PA210006006
			Hashtable tableSector = new Hashtable();
			try {

				String appID = (String) initFormImf.get("appkind");
				String appOrAccount = (String) initFormImf.get("apporapprove");
				String sql = "";
				if ("".equals(flag))
					sql =
						"select distinct PA210006003 from PA210006 where PA210006001=? and PA210006002=? and PA210006010=? ";
				else
					sql =
						"select distinct PA210006003 from PA210006 where PA210006001=? and PA210006002=? and PA210006010=? and PA210006006=? ";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, appID);
				ps.setString(2, appOrAccount);
				ps.setString(3, "1");//1：代表借据。
				if (!"".equals(flag))
					ps.setString(4, flag);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Hashtable hs_tmp = new Hashtable();
					if (hs != null)
						hs_tmp =
							this.tableTransforDue(
								flag,
								rs.getString(1),
								(Hashtable) hs.get(
									((String) rs.getString(1)).toLowerCase()),
								initFormImf,
								conn);
					else
						hs_tmp =
							this.tableTransforDue(
								flag,
								rs.getString(1),
								initFormImf,
								initFormImf,
								conn);
					tableSector.put(rs.getString(1).toLowerCase(), hs_tmp);
				}
				rs.close();

			} catch (TranFailException e) {
				throw e;
			} catch (Exception e) {
				throw new TranFailException(
					"100794",
					"icbc.cmis.util.GeneralApplyAccountDao.transforDue()"
						+ e.getMessage());
			}
			return tableSector;
		}
	/**
	 * 
	 * <b>功能描述:将首页面的信息转换到hashtable中供查询模块使用，其中字段含义和位置有关，合同号及baseID的位置在数据库中已经定义，有位置的限制 </b><br>
	 * <p> </p>
	 * @param str
	 *
	 */
	public void transferToInitForm(String str) {
		StringTokenizer token = new StringTokenizer(str, "|");
		if (token.hasMoreTokens()) {
			initFormImf.put("appkind", token.nextToken()); //业务种类
		}
		if (token.hasMoreTokens()) {
			initFormImf.put("apporapprove", token.nextToken()); //申请还是台账
		}
		if (token.hasMoreTokens()) {
			initFormImf.put("mainbank", token.nextToken()); //业务所属行
		}
		if (token.hasMoreTokens()) {
			initFormImf.put("startbank", token.nextToken()); //业务发起行
		}
		if (token.hasMoreTokens()) {
			initFormImf.put("empcode", token.nextToken()); //操作员
		}
		if (token.hasMoreTokens()) {
			initFormImf.put("enpname", token.nextToken()); //客户名称
		}
		if (token.hasMoreTokens()) {
			initFormImf.put("baseID", token.nextToken()); //相关业务号
		}
		if (token.hasMoreTokens()) {
			initFormImf.put("enpcode", token.nextToken()); //客户号
		}
		if (token.hasMoreTokens()) {
			initFormImf.put("pesudoID", token.nextToken()); //伪键
		}

		int i = 0;
		while (token.hasMoreTokens()) {
			initFormImf.put("parameter" + i, token.nextToken()); //附加的主键信息
			i++;
		}
		if (i >= 1)
			initFormImf.put(
				"contractID",
				(String) initFormImf.get("parameter" + (i - 1)));
		//合同号
	}
	/**
	 * 
	 * <b>功能描述: 将保存primayInfo信息的集合转换回String，集中字段的位置固定</b><br>
	 * <p> </p>
	 * @param initForm
	 * @return
	 *
	 */
	public String InitFormTransferToPrimaryInfo(Hashtable initForm) {

		String str = "";
		str =
			initFormImf.get("appkind")
				+ "|"
				+ initFormImf.get("apporapprove")
				+ "|"
				+ initFormImf.get("mainbank")
				+ "|"
				+ initFormImf.get("startbank")
				+ "|"
				+ initFormImf.get("empcode")
				+ "|"
				+ initFormImf.get("enpname")
				+ "|"
				+ initFormImf.get("baseID")
				+ "|"
				+ initFormImf.get("enpcode")
				+ "|"
				+ initFormImf.get("pesudoID")
				+ "|";
		int i = 0;
		while (initFormImf.containsKey("parameter" + i)) {
			str += initFormImf.get("parameter" + i) + "|";
			i++;
		}
		str += initFormImf.get("contractID") + "|";
		return str;
	}
	/**
	 * 
	 * <b>功能描述: 转换null的string为空</b><br>
	 * <p> </p>
	 * @param str
	 * @return
	 *
	 */
	public String nullToEmpty(String str) {
		if (str == null || str.equals("null"))
			return "";
		else
			return str;
	}
	/**
	 * 
	 * <b>功能描述: 插入同步</b><br>
	 * <p> </p>
	 * @param conn
	 * @param initFormImf
	 * @return
	 * @throws SQLException
	 * @throws TranFailException
	 *
	 */
	public boolean insertSychronize(Connection conn, Hashtable initFormImf)
		throws SQLException, TranFailException {
		CallableStatement cStmt = null;
		String Apply_kind = (String) initFormImf.get("appkind");
		String PRENAME = icbc.cmis.tfms.AA.AssureAssociationOP.PRENAME;
		try {
			cStmt =
				conn.prepareCall(
					"{call pack_assure_relation.proc_assure_interface(?,?,?,?,?,?,?,?,?,?)}");
			cStmt.registerOutParameter(9, OracleTypes.VARCHAR); //error message
			cStmt.registerOutParameter(10, OracleTypes.VARCHAR); //error code
			cStmt.setString(1, (String) initFormImf.get("enpcode")); //企业代码
			cStmt.setString(2, (String) initFormImf.get("mainbank")); //业务所属地区号
			cStmt.setString(3, sys_date); //业务发生时间 yyyymmdd
			cStmt.setString(4, (String) initFormImf.get("apporapprove"));
			//业务所处阶段
			cStmt.setString(5, (String) initFormImf.get("appkind"));
			//业务种类，传入mag_kind
			cStmt.setString(6, (String) initFormImf.get("pesudoID"));
			//原有的或临时的业务合同号
			cStmt.setString(7, (String) initFormImf.get("contractID"));
			//新的业务合同号
			if ("无".equals((String) initFormImf.get("baseID")))
				cStmt.setString(8, ""); //子业务对应的父业务编号
			else
				cStmt.setString(8, (String) initFormImf.get("baseID"));

			cStmt.executeUpdate();
			//deal
			String retmessage = cStmt.getString(9);
			int retflag = cStmt.getInt(10);
			if (retflag != 0) {
				throw new TranFailException(getClass().getName() + "518",
				//错误编码，使用者看
				getClass().getName(), //出错位置,开发者看
				"后台程序提示信息！" + retmessage, //错误内容，开发者看
				"不能执行该项操作！请检查提示信息。"); //错误描述，给使用者看;
			}
		} catch (SQLException ex) {
			throw new TranFailException(getClass().getName() + "525",
			//错误编码，使用者看
			getClass().getName(), //出错位置,开发者看
			"数据库操作错误！" + ex.toString(), //错误内容，开发者看
			"数据库操作错误！请联系系统管理员。"); //错误描述，给使用者看;
		} catch (TranFailException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TranFailException(getClass().getName() + "536",
			//错误编码，使用者看
			getClass().getName(), //出错位置,开发者看
			"数据库操作错误！" + ex.toString(), //错误内容，开发者看
			"数据库操作错误！请联系系统管理员。"); //错误描述，给使用者看;
		}

		return true;
	}
	/**
	 * 
	 * <b>功能描述: 修改同步</b><br>
	 * <p> </p>
	 * @param conn
	 * @param initFormImf
	 * @return
	 * @throws SQLException
	 * @throws TranFailException
	 *
	 */
	public boolean updateSychronize(Connection conn, Hashtable initFormImf)
		throws SQLException, TranFailException {

		CallableStatement cStmt = null;
		String PRENAME = icbc.cmis.tfms.AA.AssureAssociationOP.PRENAME;
		String Apply_kind = (String) initFormImf.get("appkind");

		try {
			cStmt =
				conn.prepareCall(
					"{call pack_assure_relation.proc_assure_interface(?,?,?,?,?,?,?,?,?,?)}");
			cStmt.registerOutParameter(9, OracleTypes.VARCHAR); //error message
			cStmt.registerOutParameter(10, OracleTypes.VARCHAR); //error code
			cStmt.setString(1, (String) initFormImf.get("enpcode")); //企业代码
			cStmt.setString(2, (String) initFormImf.get("mainbank")); //业务所属地区号
			cStmt.setString(3, sys_date); //业务发生时间 yyyymmdd
			cStmt.setString(4, (String) initFormImf.get("apporapprove"));
			//业务所处阶段
			cStmt.setString(5, (String) initFormImf.get("appkind"));
			//业务种类，传入mag_kind
			cStmt.setString(6, (String) initFormImf.get("pesudoID"));
			//原有的或临时的业务合同号
			cStmt.setString(7, (String) initFormImf.get("contractID"));
			//新的业务合同号
			if ("无".equals((String) initFormImf.get("baseID")))
				cStmt.setString(8, ""); //子业务对应的父业务编号
			else
				cStmt.setString(8, (String) initFormImf.get("baseID"));

			cStmt.executeUpdate();
			//deal
			String retmessage = cStmt.getString(9);
			int retflag = cStmt.getInt(10);
			if (retflag != 0) {
				throw new TranFailException(getClass().getName() + "518",
				//错误编码，使用者看
				getClass().getName(), //出错位置,开发者看
				"后台程序提示信息！" + retmessage, //错误内容，开发者看
				"不能执行该项操作！请检查提示信息。"); //错误描述，给使用者看;
			}
		} catch (SQLException ex) {
			throw new TranFailException(getClass().getName() + "525",
			//错误编码，使用者看
			getClass().getName(), //出错位置,开发者看
			"数据库操作错误！" + ex.toString(), //错误内容，开发者看
			"数据库操作错误！请联系系统管理员。"); //错误描述，给使用者看;
		} catch (TranFailException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TranFailException(getClass().getName() + "536",
			//错误编码，使用者看
			getClass().getName(), //出错位置,开发者看
			"数据库操作错误！" + ex.toString(), //错误内容，开发者看
			"数据库操作错误！请联系系统管理员。"); //错误描述，给使用者看;
		}
		return true;
	}
	/**
	 * 
	 * <b>功能描述: 删除同步</b><br>
	 * <p> </p>
	 * @param conn
	 * @param initFormImf
	 * @return
	 * @throws SQLException
	 * @throws TranFailException
	 *
	 */
	public boolean deleteSychronize(Connection conn, Hashtable initFormImf)
		throws SQLException, TranFailException {
		String Apply_kind = (String) initFormImf.get("appkind");
		String PRENAME = icbc.cmis.tfms.AA.AssureAssociationOP.PRENAME;
		CallableStatement cStmt = null;
		cStmt =
			conn.prepareCall(
				"{call pack_public_use.proc_delete_pk(?,?,?,?,?,?,?,?,?)}");
		cStmt.setString(1, (String) initFormImf.get("enpcode"));
		cStmt.setString(2, (String) initFormImf.get("contractID"));
		cStmt.setString(3, "ALL");
		if("0".equals((String) initFormImf.get("apporapprove")))
		cStmt.setString(4, "TA200212");
		else
		cStmt.setString(4, "TA200211");
		cStmt.setString(5, "0");
		cStmt.setString(6, (String) initFormImf.get("appkind"));
		cStmt.setString(7, (String) initFormImf.get("apporapprove"));
		cStmt.registerOutParameter(8, OracleTypes.VARCHAR);
		cStmt.registerOutParameter(9, OracleTypes.VARCHAR);

		cStmt.executeQuery();

		String ret = cStmt.getString(8);
		String retmess = cStmt.getString(9);

		cStmt.close();
		if (ret.equals("1")) {
			throw new TranFailException(getClass().getName() + "519",
			//错误编码，使用者看
			getClass().getName(), //出错位置,开发者看
			"后台程序提示信息！" + retmess, //错误内容，开发者看
			"不能执行该项操作！请检查提示信息。"); //错误描述，给使用者看;
		}

		return true;

	}

	public boolean ObjectionDelete(Hashtable initinfo, Connection conn)
		throws TranFailException,SQLException {
			CallableStatement cStmt = null;
		try {
			cStmt =conn.prepareCall("{call pack_DueCodeSychronize.pro_objectionDelete(?,?,?,?,?,?)}");
			cStmt.setString(1, (String) initinfo.get("appkind"));
			cStmt.setString(2, (String) initinfo.get("enpcode"));
			cStmt.setString(3, (String) initinfo.get("contractID"));
			cStmt.setString(4, (String) initinfo.get("mainbank"));
			cStmt.registerOutParameter(5, OracleTypes.VARCHAR);
			cStmt.registerOutParameter(6, OracleTypes.VARCHAR);
			cStmt.execute();
			String flag = cStmt.getString(5);
			String err_msg=cStmt.getString(6);
			cStmt.close();
			if ("1".equals(flag)) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new TranFailException(
				"100306",
				"icbc.cmis.util.GeneralApplyAccountDao.ObjectionDelete()");
		}
		finally
		{
			if(cStmt!=null)
			   cStmt.close();
		}

	}
	

//add by zap
/**
		 * 
		 * <b>功能描述:同步借据号</b><br>
		 * <p> </p>
		 * @param conn
		 * @param initFormImf
		 * @return
		 * @throws SQLException
		 * @throws TranFailException
		 *
		 */
public boolean updateDueSychronize
								(Connection conn,
								Hashtable initFormImf
				) throws TranFailException {
			int ret=0;
		boolean bl=false;
		String Apply_kind = (String) initFormImf.get("appkind");
		String Apply_customerCode = (String) initFormImf.get("enpcode");
		String Apply_contractID = (String) initFormImf.get("contractID");	
		CallableStatement cStmt=null;					    	
		try{
			cStmt = conn.prepareCall("{ ?=call pack_DueCodeSychronize.func_updateDueCode(?,?,?,?)}");
			cStmt.registerOutParameter(1, OracleTypes.INTEGER);      //返回的标志
			cStmt.setString(2, Apply_kind);                          //业务种类
			cStmt.setString(3, Apply_customerCode);                  //客户号
			cStmt.setString(4, Apply_contractID);                    //业务号
			cStmt.registerOutParameter(5, OracleTypes.VARCHAR);      //返回的信息
			
			cStmt.executeQuery();
			ret=cStmt.getInt(1);
			if(ret==0)
				bl=true;		
		 cStmt.close();			
		} 
		catch(Exception e)
		{
			throw new TranFailException(
						"100792",
						"icbc.cmis.util.GeneralApplyAccountDao.updateDueSychronize()",
						genMsg.getErrMsg("100792", "借据号同步|query_combineDueCount()"));
		}
		finally
		{
			 try{
			 if(cStmt!=null)
				cStmt.close();
			 }
			 catch(Exception e)
			 {}
		}
		return bl;

}


		/**删除影像相关信息
		 * @param conn
		 * @param initFormImf
		 * @return
		 */
		private boolean deleteImageInfo(Connection conn, Hashtable initFormImf) throws TranFailException ,SQLException{
			CallableStatement cStmt=null;
			try{
			cStmt = conn.prepareCall("{?= call pack_image_tool.func_deleteRequisition(?,?,?)}");
			cStmt.registerOutParameter(1, OracleTypes.INTEGER);
			cStmt.registerOutParameter(4, OracleTypes.VARCHAR);
			cStmt.setString(2, (String)initFormImf.get("enpcode"));
			cStmt.setString(3, (String)initFormImf.get("contractID"));
			cStmt.execute();
			int retValue = cStmt.getInt(1);
			String err_txt = cStmt.getString(4);
			cStmt.close();
			if (retValue!=1) {
				throw new TranFailException("100312", "icbc.cmis.util.GeneralApplyAccountDao.deleteImageInfo()");
			}
			}
			catch(Exception ex)
			{
				if(cStmt!=null)
				cStmt.close();
			}
			return true;
		}
//	end by zap

	private boolean insertRate(Connection conn, Hashtable initFormImf) throws TranFailException, SQLException {

			if("0".equals(initFormImf.get("apporapprove"))){		
			CallableStatement cStmt = conn.prepareCall("{call Pack_insertApplyRate.insertRate(?,?,?,?,?)}");
			cStmt.setString(1, (String)initFormImf.get("enpcode"));
			cStmt.setString(2, (String)initFormImf.get("contractID"));
			cStmt.setString(3, (String)initFormImf.get("appkind"));
			cStmt.registerOutParameter(4, OracleTypes.VARCHAR);
			cStmt.registerOutParameter(5, OracleTypes.VARCHAR);
			cStmt.execute();
			String flag = cStmt.getString(4);
			String err_txt = cStmt.getString(5);
			cStmt.close();
			if ("1".equals(flag)) {
				throw new TranFailException("100312", "icbc.cmis.util.GeneralApplyAccountDao.insertRate()");
			}
			if ("2".equals(flag)) {

				throw new TranFailException("100315", "icbc.cmis.util.GeneralApplyAccountDao.insertRate()");

			}
			}
			return true;

		}
	/**
		 * @param conn
		 * @param initFormImf
		 * @return
		 */
		private void deleteRate(Connection conn, Hashtable initFormImf) throws TranFailException {
			String sql = "";
			PreparedStatement stmt = null;				
			try {
				sql = "delete from ta200213 where ta200213001=? and ta200213002=?  ";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, (String)initFormImf.get("enpcode"));
				stmt.setString(2, (String)initFormImf.get("contractID"));
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
				throw new TranFailException(
					"xdtz0FFE102",
					"GeneralApplyAccountDao.deleteRate(String)",
					e.getMessage(),
					"删除ta200213表发生异常");
			}
		
		}
	/**删除借据附属信息
			 * @param conn
			 * @param initFormImf
			 * @return
			 */
			private void deleteAppertainInfo(Connection conn, Hashtable initFormImf,String dueCode) throws Exception {
				String sql = "";
				PreparedStatement stmt = null;
				try {
					sql = "delete from ta200268 where ta200268001=? and ta200268002=?  ";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, (String)initFormImf.get("enpcode"));
					stmt.setString(2, (String)initFormImf.get("contractID")+"-"+dueCode);
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
					throw new TranFailException(
						"xdtz0FFE102",
						"GeneralApplyAccountDao.deleteAppertainInfo(String)",
						e.getMessage(),
						"删除借据附属信息发生异常");
				}
				finally
				{
					if(stmt!=null)
					   stmt.close();
				}
		
			}
}
