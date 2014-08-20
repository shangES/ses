package icbc.cmis.operation;

import icbc.cmis.second.pkg.DBProcedureAccessService;
import icbc.cmis.second.pkg.DBProcedureParamsDef;
import icbc.cmis.service.*;
import icbc.cmis.util.*;
import icbc.cmis.base.*;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;


/**
 * Insert the type's description here.
 * Creation date: (2001-12-25 10:57:44)
 * @author: xgl
 * @version 1.02
 *  
 */
public abstract class CmisOperation {
	private KeyedDataCollection data = null;
	private String operationName = null;
	private CMisSessionMgr sessionMgr = null;
	private Vector vIcollName = new Vector();
	protected int maxRow = 200;
	private String[] logMsg = null;
	private Vector vCmisDao = new Vector();
	private String serverAddr = null;
	private String userAddr = null;
	public int connectCount = 0;
	/**
	 * CmisOperations constructor comment.
	 */
	public CmisOperation() {
		super();

	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-5-23 12:38:48)
	 * @param dao icbc.cmis.service.CmisDao
	 */
	public void addCmisDao(CmisDao dao) {
		if (dao != null) {
			vCmisDao.add(dao);
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 11:47:05)
	 * @param fieldName java.lang.String
	 * @param fieldValue java.lang.Object
	 */
	public void addDataElement(DataElement dataElement)
		throws TranFailException {
		try {
			if (dataElement instanceof IndexedDataCollection) {
				addIndexedDataCollection((IndexedDataCollection) dataElement);
			} else if (dataElement instanceof KeyedDataCollection) {
				addKeyedDataCollection((KeyedDataCollection) dataElement);
			} else {
				data.addElement(dataElement);
			}
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22100",
				getOperationName(),
				e.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 11:47:05)
	 * @param fieldName java.lang.String
	 * @param fieldValue java.lang.Object
	 */
	public void addDataField(String fieldName, Object fieldValue)
		throws TranFailException {
		try {
			if (fieldValue instanceof DataCollection) {
				throw new Exception("Invalid request in addDataField(String fieldName, Object fieldValue)");
			} else {
				data.addElement(fieldName, fieldValue);
			}
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22100",
				getOperationName(),
				e.getMessage());
		}
	}
	public void addIndexedDataCollection(IndexedDataCollection dataElement)
		throws TranFailException {
		try {
			vIcollName.add(dataElement.getName());
			data.addElement(dataElement);
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22100",
				getOperationName(),
				e.getMessage());
		}
	} /**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 11:47:05)
	 * @param fieldName java.lang.String
	 * @param fieldValue java.lang.Object
	 */
	public void addKeyedDataCollection(KeyedDataCollection dataElement)
		throws TranFailException {
		try {
			data.addElement(dataElement);
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22100",
				getOperationName(),
				e.getMessage());
		}
	} /**
	 * Insert the method's description here.
	 * Creation date: (2002-1-16 9:39:26)
	 * @return icbc.cmis.base.DataElement
	 * @param dataName java.lang.String
	 */
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-24 11:16:06)
	 * @param dataName java.lang.String
	 * @param dataValue java.lang.String
	 */
	public void addSessionData(String dataName, Object dataValue)
		throws TranFailException {
		if (dataValue instanceof IndexedDataCollection) {
			IndexedDataCollection tmpI = (IndexedDataCollection) dataValue;
			if (tmpI.getSize() > maxRow) {
				throw new TranFailException(
					"xdtz22127",
					getOperationName()
						+ ".addSessionData(String dataName, Object dataValue)",
					"在session中添加大容量数据失败,；请调整查询条件，缩小数据量",
					"在session中添加大容量数据失败,；请调整查询条件，缩小数据量");
			}
		}
		try {
			sessionMgr.addSessionData(dataName, dataValue);
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22100",
				getOperationName(),
				e.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-29 10:38:15)
	 */
	public void clearSession() throws TranFailException {
		sessionMgr.removeSession();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-5-23 12:40:35)
	 */
	private void closeCmisDao() {
		for (int i = 0; i < vCmisDao.size(); i++) {
			CmisDao dao = (CmisDao) vCmisDao.elementAt(i);
			if (dao != null) {
				try {
					dao.cmisOperationRun();
				} catch (Exception e) {
				}
				dao = null;
			} else {
				System.out.println(
					"==**Notice**==: in class "
						+ this.getClass().toString()
						+ ", some CmisDao is null!the DB connection is ???");
			}

		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 10:57:44)
	 */
	public abstract void execute() throws Exception, TranFailException;

	/**
	* Insert the method's description here.
	* Creation date: (2002-1-22 10:25:54)
	*/
	public String getCurrentDateTime(String format) {
		try {
			return CmisConstance.getWorkDate(format);
		} catch (Exception e) {
			return null;
		}
	}
	public DataElement getDataElement(String dataName)
		throws TranFailException {
		try {
			return (DataElement) data.getElement(dataName);
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22102",
				getOperationName(),
				e.getMessage());
		}
	} /**
	 * Insert the method's description here.
	 * Creation date: (2002-1-16 9:39:26)
	 * @return icbc.cmis.base.DataElement
	 * @param dataName java.lang.String
	 */
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 14:58:48)
	 * @return java.lang.Object
	 * @param dataName java.lang.String
	 */
	public double getDoubleAt(String dataName) throws TranFailException {
		try {
			return Double
				.valueOf((String) data.getValueAt(dataName))
				.doubleValue();
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22102",
				getOperationName(),
				e.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-1-23 8:59:56)
	 * @return java.lang.String[]
	 */
	public java.util.Vector getElementNames() {
		return (java.util.Vector) data.getElementNames();
	}

	public IndexedDataCollection getIndexedDataCollection(String dataName)
		throws TranFailException {
		try {
			return (IndexedDataCollection) data.getElement(dataName);
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22102",
				getOperationName(),
				e.getMessage());
		}
	} /**
	 * Insert the method's description here.
	 * Creation date: (2002-1-16 9:39:26)
	 * @return icbc.cmis.base.DataElement
	 * @param dataName java.lang.String
	 */
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 14:58:48)
	 * @return java.lang.Object
	 * @param dataName java.lang.String
	 */
	public int getIntAt(String dataName) throws TranFailException {
		try {
			return Integer
				.valueOf((String) data.getValueAt(dataName))
				.intValue();
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22102",
				getOperationName(),
				e.getMessage());
		}
	}
	public KeyedDataCollection getKeyedDataCollection(String dataName)
		throws TranFailException {
		try {
			return (KeyedDataCollection) data.getElement(dataName);
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22102",
				getOperationName(),
				e.getMessage());
		}
	} /**
	 * Insert the method's description here.
	 * Creation date: (2002-1-4 11:21:48)
	 * @return byte[]
	 * @param formatName java.lang.String
	 */
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 14:58:48)
	 * @return java.lang.Object
	 * @param dataName java.lang.String
	 */
	public long getLongAt(String dataName) throws TranFailException {
		try {
			return Long.valueOf((String) data.getValueAt(dataName)).longValue();
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22102",
				getOperationName(),
				e.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-3-20 10:19:10)
	 * @return int
	 */
	public int getMaxRowNum() {
		return maxRow;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 14:58:48)
	 * @return java.lang.Object
	 * @param dataName java.lang.String
	 */
	public Object getObjectAt(String dataName) throws TranFailException {
		try {
			return data.getValueAt(dataName);
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22102",
				getOperationName(),
				e.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 10:57:44)
	 */
	public KeyedDataCollection getOperationData() {
		return data;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 10:57:44)
	 */
	public String getOperationName() {
		return operationName;
	}
	
	/**
	 * 
	 * Creation date: (2005-07-09)
	 * chenj 
	 */
	public String[] getOperationActValue() {
		
		String para[] = { "","" };
		String actionValue="";
		
		try{
		  actionValue =this.getStringAt("opAction"); 
		}catch(Exception e){}
		
		if(actionValue==null||actionValue.equals("")){
			Hashtable hTable=(Hashtable)CmisConstance.getDictParam();
			//TableBean dict_op = (TableBean)hTable.get("vopnameact");
			DictBean dict_op = (DictBean)hTable.get("vopnameact");
			//String actValue = dict_op.getNameByCode("actname","opname",getOperationName());
			String actValue = dict_op.getValue(getOperationName());
			if(actValue!=null&&actValue.equals("NULL")){
				para[0] = actValue;
				para[1] = actValue;
				return para;
			}
			try{
			  actionValue =this.getStringAt(actValue);
			  para[0] =  actValue;
			}catch(Exception e){}				
		}
		else{
			para[0] =  "opAction";
		}
		
		para[1] =  actionValue;
		return para;
	}
	
	
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 11:41:28)
	 * @param pageValue java.lang.String
	 */
	public String getReplyPage() throws TranFailException {
		try {
			return (String) data.getValueAt("replyPage");
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22104",
				getOperationName(),
				e.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-24 11:25:50)
	 * @return java.lang.Object
	 * @param dataName java.lang.String
	 */
	public Object getSessionData(String dataName) throws TranFailException {
		try {
			return sessionMgr.getSessionData(dataName);
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22107",
				getOperationName(),
				e.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 14:58:48)
	 * @return java.lang.Object
	 * @param dataName java.lang.String
	 */
	public String getStringAt(String dataName) throws TranFailException {
		try {
			return (String) data.getValueAt(dataName);
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22102",
				getOperationName(),
				e.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-2-1 14:27:42)
	 * @param tranName java.lang.String
	 * @param operation java.lang.String
	 * @param enpCode java.lang.String
	 * @param enpName java.lang.String
	 * @param comment java.lang.String
	 */
	public void initLog(
		String tranName,
		String operation,
		String enpCode,
		String enpName,
		String opType,
		String comment) {
		logMsg = new String[6];
		if (tranName == null)
			logMsg[0] = "";
		else
			logMsg[0] = tranName;
		if (operation == null)
			logMsg[1] = "";
		else
			logMsg[1] = operation;
		if (enpCode == null)
			logMsg[2] = "";
		else
			logMsg[2] = enpCode;
		if (enpName == null)
			logMsg[3] = "";
		else
			logMsg[3] = enpName;
		if (opType == null)
			logMsg[4] = "";
		else
			logMsg[4] = opType;
		if (comment == null)
			logMsg[5] = "";
		else
			logMsg[5] = comment;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-1-23 9:01:12)
	 * @return boolean
	 * @param dataName java.lang.String
	 */
	public boolean isElementExist(String dataName) {
		return data.isElementExist(dataName);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-2-1 14:39:48)
	 */
	private void log(TranFailException et) {
		JDBCProcedureService proc = null;
		try {
			int  logEnable =Integer.parseInt(
				(String) CmisConstance.getParameterSettings().get("logEnable"));
			if (logEnable!=0 && logEnable!=4 && logEnable!=5 && logEnable!=6 && logEnable!=7)
				return;
			Vector vData = new Vector(17);
			vData.add(0, getCurrentDateTime("yyyyMMdd"));
			vData.add(1, getCurrentDateTime("HHmm"));
			String eCode = null;
			try {
				eCode = (String) getSessionData("EmployeeCode");
			} catch (Exception es) {
				try {
					eCode = getStringAt("EmployeeCode");
				} catch (Exception ess) {
				}
			}
			if (eCode == null)
				eCode = "";
			vData.add(2, eCode);
			String eName = null;
			try {
				eName = (String) getSessionData("EmployeeName");
			} catch (Exception es) {
				try {
					eName = getStringAt("EmployeeName");
				} catch (Exception ess) {
				}
			}
			if (eName == null)
				eName = "";
			vData.add(3, eName);
			String areaCode = null;
			try {
				areaCode = (String) getSessionData("AreaCode");
			} catch (Exception es) {
				try {
					areaCode = getStringAt("AreaCode");
				} catch (Exception ess) {
				}
			}
			if (areaCode == null)
				areaCode = "";
			vData.add(4, areaCode);
			vData.add(5, getOperationName());
			if (logMsg == null) {
				initLog("", "", "", "", "", "");
			}

			vData.add(6, logMsg[0]);
			vData.add(7, logMsg[1]);
			vData.add(8, logMsg[2]);
			vData.add(9, logMsg[3]);
			vData.add(10, logMsg[4]);

			String errCode = "";
			String errLocal = "";
			String errMsg = "";
			if (et != null) {
				errCode = et.getErrorCode();
				errLocal = et.getErrorLocation();
				errMsg = et.getErrorMsg();
				if (errCode == null)
					errCode = "未知错误";
				if (errLocal == null)
					errLocal = "未知错误";
				if (errMsg == null)
					errMsg = "未知错误";
			} else {
				try {
					errCode = getStringAt("tranErrorCode");
				} catch (Exception ed) {
				}
				try {
					errLocal = getStringAt("tranErrorLocation");
				} catch (Exception ed) {
				}
				try {
					errMsg = getStringAt("tranErrorMsg");
				} catch (Exception ed) {
				}
				if (errCode == null)
					errCode = "";
				if (errLocal == null)
					errLocal = "";
				if (errMsg == null)
					errMsg = "";
			}
			vData.add(11, errCode);
			vData.add(12, errLocal);
			vData.add(13, errMsg);
			vData.add(14, logMsg[5]);
			vData.add(15, serverAddr);
			vData.add(16, userAddr);

			String appAddr = "";
    		try
        	{
        		appAddr = java.net.InetAddress.getLocalHost().getHostAddress();
    		}
    		catch (Exception e)
        	{
        		appAddr = "unknown";
    		}
    		appAddr = appAddr + " clone " + CmisConstance.appServerID;

			vData.add(17, appAddr);

//			String userId =
//				(String) CmisConstance.getParameterSettings().get("dbUserName");
//			String userPass =
//				(String) CmisConstance.getParameterSettings().get("dbUserPass");
			proc = new JDBCProcedureService(this);
//			proc.getConn(userId, userPass);
			proc.getConn("missign");
			String logProc =
				(String) CmisConstance.getParameterSettings().get(
					"transLogProcName");
			if (logProc == null || logProc.trim().length() == 0) {
				if (proc != null)
					try {
						proc.closeConn();
					} catch (Exception em) {
					}
				proc = null;
				return;
			}
			proc.executeJournalProcedure(logProc, vData);
			proc.commit();
			proc.closeConn();
		} catch (Exception e) {
			if (proc != null) {
				try {
					proc.rollback();
					proc.closeConn();
				} catch (Exception ee) {
				}
			}
			CmisConstance.pringMsg("CmisOperation write log failure!");
			proc = null;
		} finally {
			if (proc != null) {
				try {
					proc.closeConn();
				} catch (Exception ee) {
				}
			}
			proc = null;
		}
	}




	/**
	 * Insert the method's description here.
	 * Creation date: (2002-2-1 14:39:48)
	 * 20060304 update by yanbo
	 * 20050715 日志的修改 chenj
	 * logState = -1(I)   (logEnable = 4) 或 (logEnable=6且在excute之前记录)
	 * logState = 1(U)   logEnable ＝6且在excute之后记录
	 */
	private String log2(int logState, String logSeq, long beginTime, TranFailException et) {
		JDBCProcedureService proc = null;
		String serialno="0";
		java.text.SimpleDateFormat timeFmt =
					new java.text.SimpleDateFormat("HHmmssSSS");
		java.text.SimpleDateFormat dateFmt =
					new java.text.SimpleDateFormat("yyyyMMdd");
		//System.out.println("log2->" + logState + " " + (logSeq==null?"null":logSeq) + " " + beginTime + " "+ et);
		try {
			int  logEnable =Integer.parseInt(
				(String) CmisConstance.getParameterSettings().get("logEnable"));
//			if (logEnable!=0 && logEnable!=4 && logEnable!=5 && logEnable!=6 && logEnable!=7)
//				return "0";
            if (logEnable!=4 && logEnable!=6)
			  return "0";
			if(logEnable==4 && logState==-1)
			  return "";
//			if(logEnable!=6 && logState==1)
//			  return logSeq;
			Vector vData = new Vector(17);
			java.util.Calendar c = java.util.Calendar.getInstance();
			//vData.add(0, dateFmt.format(c.getTime()));
			vData.add(0, CmisConstance.getWorkDate("yyyyMMdd"));
			vData.add(1, timeFmt.format(c.getTime()));
			String eCode = null;
			try {
				eCode = (String) getSessionData("EmployeeCode");
			} catch (Exception es) {
				try {
					eCode = getStringAt("EmployeeCode");
				} catch (Exception ess) {
				}
			}
			if (eCode == null)
				eCode = "";
			vData.add(2, eCode);
			String eName = null;
			try {
				eName = (String) getSessionData("EmployeeName");
			} catch (Exception es) {
				try {
					eName = getStringAt("EmployeeName");
				} catch (Exception ess) {
				}
			}
			if (eName == null)
				eName = "";
			vData.add(3, eName);
			String areaCode = null;
			try {
				areaCode = (String) getSessionData("AreaCode");
			} catch (Exception es) {
				try {
					areaCode = getStringAt("AreaCode");
				} catch (Exception ess) {
				}
			}
			if (areaCode == null)
				areaCode = "";
			vData.add(4, areaCode);
			vData.add(5, getOperationName());
			if (logMsg == null) {
				initLog("", "", "", "", "", "");
			}
			//vData.add(6, logMsg[0]);
			//vData.add(6, logMsg[0]);
			String ddd[]=getOperationActValue();
			vData.add(6, ddd[0]);//chenj modify 20050709 记录交易日志的action值
			vData.add(7, ddd[1]);//chenj modify 20050709 记录交易日志的action值
			vData.add(8, logMsg[2]);
			vData.add(9, logMsg[3]);
			vData.add(10, logMsg[4]);

			String errCode = "";
			String errLocal = "";
			String errMsg = "";
			if (et != null) {
				errCode = et.getErrorCode();
				errLocal = et.getErrorLocation();
				errMsg = et.getErrorMsg();
				if (errCode == null)
					errCode = "未知错误";
				if (errLocal == null)
					errLocal = "未知错误";
				if (errMsg == null)
					errMsg = "未知错误";
			} else {
				try {
					errCode = getStringAt("tranErrorCode");
				} catch (Exception ed) {
				}
				try {
					errLocal = getStringAt("tranErrorLocation");
				} catch (Exception ed) {
				}
				try {
					errMsg = getStringAt("tranErrorMsg");
				} catch (Exception ed) {
				}
				if (errCode == null)
					errCode = "";
				if (errLocal == null)
					errLocal = "";
				if (errMsg == null)
					errMsg = "";
			}
			vData.add(11, errCode);
			vData.add(12, errLocal);
			vData.add(13, errMsg);
			vData.add(14, logMsg[5]);
			vData.add(15, serverAddr);
			vData.add(16, userAddr);

			String appAddr = "";
    		try
        	{
        		appAddr = java.net.InetAddress.getLocalHost().getHostAddress();
    		}
    		catch (Exception e)
        	{
        		appAddr = "unknown";
    		}
			vData.add(17, appAddr);
			vData.add(18, CmisConstance.appServerID);			

			Hashtable hTable=(Hashtable)CmisConstance.getDictParam();

			TableBean dict_mag_log_ctrl = (TableBean)hTable.get("mag_log_ctrl");



			vData.add(19, (String)dict_mag_log_ctrl.getNameByCode("log_curr_partition","log_id","MAG_TRANS_LOG"));

			vData.add(20, String.valueOf(logState));
			vData.add(21, logSeq);
			if (logSeq.equals("") && logEnable ==6)
			  vData.add(22, "");
			else
			  vData.add(22, String.valueOf(System.currentTimeMillis() - beginTime));
//    		appAddr = appAddr + " clone " + CmisConstance.appServerID;


			System.out.println("log2->" + getOperationName() + "@" + ddd[0] + "=" + ddd[1]);
//			String userId =
//				(String) CmisConstance.getParameterSettings().get("dbUserName");
//			String userPass =
//				(String) CmisConstance.getParameterSettings().get("dbUserPass");
			proc = new JDBCProcedureService(this);
//			proc.getConn(userId, userPass);
			proc.getConn("missign");
			String logProc =
				(String) CmisConstance.getParameterSettings().get(
					"transLogProcName");
			if (logProc == null || logProc.trim().length() == 0) {
				if (proc != null)
					try {
						proc.closeConn();
					} catch (Exception em) {
					}
				proc = null;
				return "0";
			}
			 serialno = proc.executeJournalProcedure2(logProc, vData);
			
			
			proc.commit();
			proc.closeConn();

		} catch (Exception e) {
			if (proc != null) {
				try {
					proc.rollback();
					proc.closeConn();
				} catch (Exception ee) {
				}
			}
			CmisConstance.pringMsg("CmisOperation write log failure!");
			proc = null;
		} finally {
			if (proc != null) {
				try {
					proc.closeConn();
				} catch (Exception ee) {
				}
			}
			proc = null;
		}
		return serialno;
	}





	/**
	 * Insert the method's description here.
	 * Creation date: (2002-2-1 14:39:48)
	 */
	private void logDsr(String transName,byte[] bContent,String transstatus){
		try{
			int  logEnable =Integer.parseInt(
				(String) CmisConstance.getParameterSettings().get("logEnable"));
			if (logEnable!=1 && logEnable!=4 && logEnable!=5 && logEnable!=6 && logEnable!=7)
				return;

			 setFieldValue("DLTranDate",CmisConstance.getWorkDate("yyyyMMdd"));
			 setFieldValue("DLTranTime",CmisConstance.getWorkDate("HHss"));
			 setFieldValue("DLEmployee",(String)getSessionData("EmployeeCode"));
			 setFieldValue("DLEmployeeName",checkNilStr((String)getSessionData("EmployeeName")));
			 setFieldValue("DLAreaCode",checkNilStr((String)getSessionData("AreaCode")));
			 setFieldValue("DLTransName",transName);
			 try{
			 	setFieldValue("DLTfStat",checkNilStr(getStringAt("TSF_STAT")));
			 }catch(Exception e){
			 	setFieldValue("DLTfStat","e");
			 }
			 try{
			 	setFieldValue("DLTransOk",checkNilStr(getStringAt("TRANSOK")));
			 }catch(Exception e){
			 	setFieldValue("DLTransOk","e");
			 }
			 try{
			 	setFieldValue("DLErrCode",checkNilStr(getStringAt("ERR_NO")));
			 }catch(Exception e){
			 	setFieldValue("DLErrCode","未知");
			 }
		 	 String dsrType = (String) DSRCommService.tranTyoeMap.get(transName);			 
			 //dsrTranName
			 if(dsrType.equals("1")){
			 	String sendPkg = new String(bContent);//getString(bContent);
//			 	sendPkg = new String(bContent,"Cp1252");
//			 	sendPkg = new String(bContent,"ISO8859_1");
//			 	sendPkg = new String(bContent,"UTF8");
//			 	sendPkg = new String(bContent,"UTF-16");			 	
			 	setFieldValue("DLSendPkg",sendPkg);			 	
			 }else{
				 setFieldValue("DLSendPkg","");
			 }
			 setFieldValue("DLSrvAddr",this.serverAddr);
			 setFieldValue("DLUsrAddr",this.userAddr);
			 try{
				String transno = this.getStringAt("dsrTransNo8");
				setFieldValue("DLTransNo",transno);
			 }
			 catch(Exception e){
			 	setFieldValue("DLTransNo","");	
			 }
			 setFieldValue("DLTransType",dsrType);			 
			 setFieldValue("DLTransStatus",transstatus);			 			 

 			 Hashtable hTable=(Hashtable)CmisConstance.getDictParam();
			 TableBean dict_mag_log_ctrl = (TableBean)hTable.get("mag_log_ctrl");
			 setFieldValue("DLCurrPart",(String)dict_mag_log_ctrl.getNameByCode("log_curr_partition","log_id","MAG_ONLINE_TRANS_LOG"));
			 
//			 vData.add(19, (String)dict_mag_log_ctrl.getNameByCode("log_curr_partition","log_id","MAG_TRANS_LOG"));





			 DBProcedureParamsDef def1 = new DBProcedureParamsDef("onlinetranslogproc");
//			 def1.setDBUserName((String)CmisConstance.getParameterSettings().get("dbUserName"));
			 def1.setDBUserName("missign");
			 String veryStr = CmisConstance.getPassVerify("missign");
//    		 userPass = getDBUserPass(userName,veryStr);			 
//			 def1.setDBUserPass((String)CmisConstance.getParameterSettings().get("dbUserPass"));
			 def1.setDBUserPass((String)CmisConstance.getDBUserPass("missign",veryStr));
			 def1.addInParam("DLTranDate");
			 def1.addInParam("DLTranTime");
			 def1.addInParam("DLEmployee");
			 def1.addInParam("DLEmployeeName");
			 def1.addInParam("DLAreaCode");
			 def1.addInParam("DLTransName");
			 def1.addInParam("DLTfStat");
			 def1.addInParam("DLTransOk");
			 def1.addInParam("DLErrCode");
			 def1.addInParam("DLSendPkg");
			 def1.addInParam("DLSrvAddr");
			 def1.addInParam("DLUsrAddr");
			 def1.addInParam("DLTransNo");
			 def1.addInParam("DLTransType");
			 def1.addInParam("DLTransStatus");			 			 			 
			 def1.addInParam("DLCurrPart");

			 def1.addOutParam("out_procsign");

			 DBProcedureAccessService srv = new DBProcedureAccessService(this);
			 srv.executeProcedure(this.getOperationData(),def1);

		}catch(Exception e){
			System.out.println("写DSR日志失败，错误信息：\n"+e.getMessage());
		}
	}

	public static String checkNilStr(String src){
		if(src == null) return "";
		return src;
	}

	private String logCycleOp(String serialNo,boolean isFirst,long beginTime) {
		JDBCProcedureService proc = null;
		try {

			int  logEnable =Integer.parseInt(
				(String) CmisConstance.getParameterSettings().get("logEnable"));
			if (logEnable!=3 && logEnable!=6 && logEnable!=7)
				return "";

//			String userId =
//				(String) CmisConstance.getParameterSettings().get("dbUserName");
//			String userPass =
//				(String) CmisConstance.getParameterSettings().get("dbUserPass");
			proc = new JDBCProcedureService(this);
			Vector vData = new Vector(5);
			String model = null;
			long l =0l;
			if(isFirst){
				model = "I";
			}else{
				int inteval = 15000;
				try{
					inteval = Integer.parseInt(
					(String) CmisConstance.getParameterSettings().get("cycleTime"));
				}catch(Exception ei){}
				if((System.currentTimeMillis() - beginTime)<inteval){
					model = "D";
				}else{
					model = "U";
				}
		    }
			KeyedDataCollection tmpK = new KeyedDataCollection();
			tmpK.addElement("model",model);
			tmpK.addElement("serialNo",serialNo);
			tmpK.addElement("tranName",getOperationName().toUpperCase());
			String appAddr = "";
    		 try
        	 {
        	 		appAddr = java.net.InetAddress.getLocalHost().getHostAddress();
    		 }
    		 catch (Exception e)
        	 {
        			appAddr = "unknown";
    		 }
    		if(isFirst){
    			tmpK.addElement("tranTime","");
    		}else{
    			tmpK.addElement("tranTime",String.valueOf(System.currentTimeMillis() - beginTime));
    		}
			tmpK.addElement("appAddrr",appAddr);
			tmpK.addElement("cloneName",CmisConstance.appServerID);
			vData.addElement("model");
			vData.addElement("serialNo");
			vData.addElement("tranName");
			vData.addElement("tranTime");
    		vData.addElement("appAddrr");
    		vData.addElement("cloneName");
			Vector outParam = new Vector(1);
			outParam.addElement("retMark");
			outParam.addElement("serialNo1");
//			proc.getConn(userId, userPass);
			proc.getConn("missign");
			if(proc.executeProcedure("proctransaudit",tmpK,vData,outParam,tmpK)==0){
				proc.commit();
			}else{
				proc.rollback();
			}
			proc.closeConn();
			return (String)tmpK.getValueAt("serialNo1");
		} catch (Exception e) {
			if (proc != null) {
				try {
					proc.rollback();
					proc.closeConn();
				} catch (Exception ee) {
				}
			}
			CmisConstance.pringMsg("CmisOperation write logCycleOp failure!");
			proc = null;
		} finally {
			if (proc != null) {
				try {
					proc.closeConn();
				} catch (Exception ee) {
				}
			}
			proc = null;
		}
		return "";
	}

	private void logHandlerTime(long beginTime) {
		JDBCProcedureService proc = null;
		try {
			int  logEnable =Integer.parseInt(
				(String) CmisConstance.getParameterSettings().get("logEnable"));
			if (logEnable!=2 && logEnable!=5 && logEnable!=7)
				return;

//			String userId =
//				(String) CmisConstance.getParameterSettings().get("dbUserName");
//			String userPass =
//				(String) CmisConstance.getParameterSettings().get("dbUserPass");
			proc = new JDBCProcedureService(this);
			Vector vData = new Vector();
			vData.add(getOperationName().toUpperCase());
			vData.add(String.valueOf(System.currentTimeMillis() - beginTime));
//			proc.getConn(userId, userPass);
			proc.getConn("missign");
			proc.executeJournalProcedure("operationHandlerTimeLogProc", vData);
			proc.commit();
			proc.closeConn();
		} catch (Exception e) {
			if (proc != null) {
				try {
					proc.rollback();
					proc.closeConn();
				} catch (Exception ee) {
				}
			}
			CmisConstance.pringMsg("CmisOperation write timeLog failure!");
			proc = null;
		} finally {
			if (proc != null) {
				try {
					proc.closeConn();
				} catch (Exception ee) {
				}
			}
			proc = null;
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-28 9:51:15)
	 */
	public void removeAllOperationDataFromSession() throws TranFailException {
		try {
			KeyedDataCollection opK =
				(KeyedDataCollection) sessionMgr.getSessionData(
					"operationSessionData");
			if (opK != null) {
				sessionMgr.removeSessionData("operationSessionData");
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22107",
				getOperationName(),
				e.getMessage());
		}
	}
	public void removeAllSessionData() throws TranFailException {
		sessionMgr.removeAllSessionData();
	}
	public void removeDataField(String dataName) throws TranFailException {
		try {
			data.removeElement(dataName);
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22124",
				getOperationName(),
				e.getMessage());

		}
	} /**
	 * Insert the method's description here.
	 * Creation date: (2002-1-4 11:21:48)
	 * @return byte[]
	 * @param formatName java.lang.String
	 */
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-24 11:16:31)
	 * @param dataName java.lang.String
	 */
	public void removeSessionData(String dataName) throws TranFailException {
		sessionMgr.removeSessionData(dataName);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-28 10:52:20)
	 */
	public void run() throws Exception, TranFailException {
		String serialNo = null;
		
		//add by zap 20060613
			KeyedDataCollection kColl = getOperationData();
			WriteLog(kColl);
			//end zap 20060613
		
		long beginTime = System.currentTimeMillis();
		try {
			try{
				//edit by chenj 20040429
//				serialNo = logCycleOp("-1",true,-1);
				serialNo = log2(-1,"",0,null);
				//edit by chenj 20040429 end
			}catch(Exception eq){}
			//edit by chenj yanbo 20050201
			String authEnabled = null;
			try{
				authEnabled = (String)CmisConstance.getParameterSettings().get("OpAuthEnabled");
			}catch(Exception eq){
				authEnabled = "false";
			}
			if (authEnabled!=null&&authEnabled.equals("true")) {
			  //KeyedDataCollection context = this.getOperationData();
			  String opName = this.getClass().getName();
			  OpAuthService opAuthService = new OpAuthService(opName, this.getOperationData());
			  try {
				if (!opAuthService.isOpAuthed()) {
				  //mgr.updateSessionData("opAuthAlert",opAuthService.getErrorMsg());
				  System.out.println("*!*!*!opAuthInfo:" + opAuthService.getErrorMsg());
				  throw new TranFailException(
					"icbc.cmis.operation.CmisOperation",
					"CmisOperation.run",
					"该op操作未被授权",
					"请检查op操作名及操作类型是否已经登记\n" + opAuthService.getErrorMsg());
				}
			  }
			  catch (TranFailException te) {
				throw te;
			  }
			  catch (Exception e) {
				throw new TranFailException("icbc.cmis.operation.CmisOperation", "CmisOperation.run", "该op操作未被授权", "请检查op操作名及操作类型是否已经登记");
			  }
			}
			//edit by chenj yanbo end
			
			icbc.cmis.base.Trace.trace(
				getOperationName(),
				0,
				0,
				"",
				getOperationName() + ".run() begin");
			CmisConstance.pringMsg(
				"*****Operation******:" + getOperationName());
			String rowUnlimited =
				(String) CmisConstance.getParameterSettings().get(
					"rowUnlimited");
			if (rowUnlimited != null && rowUnlimited.trim().equals("true")) {
				int size = -1;
				try {
					String sizeStr = getStringAt("displayMaxRow");
					removeDataField("displayMaxRow");
					if (sizeStr.trim().equals("unlimited")) {
						size = -1;
					} else {
						size = Integer.valueOf(sizeStr.trim()).intValue();
					}
					maxRow = size;
				} catch (Exception e1) {
					try {
						maxRow =
							Integer
								.valueOf(
									(String) CmisConstance
										.getParameterSettings()
										.get(
										"displayMaxRow"))
								.intValue();
					} catch (Exception e) {
						maxRow = 100;
					}
				}
			} else {
				try {
					maxRow =
						Integer
							.valueOf(
								(String) CmisConstance
									.getParameterSettings()
									.get(
									"displayMaxRow"))
							.intValue();
				} catch (Exception e) {
					maxRow = 200;
				}
			}

			Vector vv = getElementNames();
			if (vv != null) {
				for (int jj = 0; jj < vv.size(); jj++) {
					String tmpName = (String) vv.elementAt(jj);
					if (tmpName != null
						&& (tmpName.indexOf("IsOverflow") != -1
							|| tmpName.indexOf("OverflowMsg") != -1)) {
						try {
							removeDataField(tmpName);
						} catch (Exception eee) {
						}
					}

				}
			}

			execute();

			if (this.connectCount != 0) {
				System.out.println(
					"$$Waring$$:operation["
						+ this.getClass().getName()
						+ "] may be matter,["
						+ this.connectCount
						+ "] Database connection need be Closed!");
			}
			try{
				if(serialNo != null){
					//logCycleOp(serialNo,false,beginTime);
					log2(1,serialNo,beginTime,null);
				}
			}catch(Exception eq){}
			try {
				closeCmisDao();
			} catch (Exception edo) {
			}

			try {
				/////////////////////////////////////////////////////////////////////
				//
				//	修改原因：为了满足对于CM2002 服务交易的运行我们添加了sesessionMgr
				//				不等于空的判断条件
				//	修改人：YangGuangRun
				//	修改时间：2004-10-25
				/*if (!(isElementExist("logoutMark")
									&& getStringAt("logoutMark").equals("true"))) {
									sessionMgr.sessionCommit();
				}*/
				if (!(isElementExist("logoutMark")
					&& getStringAt("logoutMark").equals("true")) && sessionMgr!=null) {
					//sessionMgr.sessionCommit();
				}
				/////////////////////////////////////////////////////////////////////
			} catch (Exception se) {
				throw se;
			}

			icbc.cmis.base.Trace.trace(
				getOperationName(),
				0,
				0,
				"",
				getOperationName() + ".run() success!");
//remove by chenj 20040429
//			try {
//				log(null);
//			} catch (Exception eee) {
//			}
//remove by chenj 20040429 end			
			try {
				logHandlerTime(beginTime);
			} catch (Exception eee) {
			}
		} catch (Exception e) {
			if (this.connectCount != 0) {
				System.out.println(
					"$$Waring$$:operation["
						+ this.getClass().getName()
						+ "] may be matter,["
						+ this.connectCount
						+ "] Database connection need be Closed!");
			}

			try {
				closeCmisDao();
			} catch (Exception edo) {
			}
//			try{
//				if(serialNo != null && serialNo.trim().length() != 0){
//					//logCycleOp(serialNo,false,beginTime);
//				}
//			}catch(Exception eq){}
			if (e instanceof TranFailException) {
				try {
					//log2((TranFailException) e);
					log2(1,serialNo,beginTime,(TranFailException) e);
				} catch (Exception eee) {
				}
				TranFailException ee = (TranFailException) e;
				icbc.cmis.base.Trace.trace(
					getOperationName(),
					0,
					0,
					"",
					getOperationName() + ".run() exception," + ee.toMsg());
				throw e;
			}

			TranFailException ee =
				new TranFailException(
					"xdtz22111",
					getOperationName() + ".execute()",
					e.getMessage());
			icbc.cmis.base.Trace.trace(
				getOperationName(),
				0,
				0,
				"",
				getOperationName() + ".run() exception," + ee.toMsg());
			try {
				//log2(ee);
				log2(1,serialNo,beginTime,ee);
			} catch (Exception eee) {
			}
			throw ee;
		}
	}
	
	public void sessionCommit() throws TranFailException {
		sessionMgr.sessionCommit();
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (2002-4-11 18:08:47)
	 * @param tableNames java.lang.String
	 */
	public void setDictUpdatetMark(
		String tableName,
		String tableType,
		String tableOwner)
		throws TranFailException {
		JDBCProcedureService proc = null;
		try {
			String appSrvs =
				(String) CmisConstance.getParameterSettings().get(
					"balanceAppSrvIPAddrs");
			proc = new JDBCProcedureService(this);
			Vector inParam = new Vector(5);
			inParam.add(0, appSrvs.trim());
			inParam.add(1, tableName.trim());
			inParam.add(2, tableType.trim());
			inParam.add(3, tableOwner.trim());
			inParam.add(4, getCurrentDateTime("yyyyMMdd"));
//			String userId =
//				(String) CmisConstance.getParameterSettings().get("dbUserName");
//			String userPass =
//				(String) CmisConstance.getParameterSettings().get("dbUserPass");
//			proc.getConn(userId, userPass);
			proc.getConn("missign");
			proc.executeJournalProcedure(
				"insertDICT_TEMPORARY_TABLEProc",
				inParam);
			proc.closeConn();
			proc = null;
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22126",
				"CmisOperation.setDictUpdatetMark()",
				"Exception:" + e.toString());
		} finally {
			if (proc != null) {
				try {
					proc.closeConn();
				} catch (Exception e) {
				}
				proc = null;
			}
		}
	}
	public void setErrorCode(String errCode) throws TranFailException {
		try {
			DataElement element = new DataElement();
			element.setName("tranErrorCode");
			element.setValue(errCode);
			data.addElement(element);
		} catch (CTEObjectExistingException e) {

			try {
				data.setValueAt("tranErrorCode", errCode);
			} catch (Exception e1) {
				throw new TranFailException(
					"xdtz22107",
					getOperationName(),
					e1.getMessage());
			}

		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22107",
				getOperationName(),
				e.getMessage());
		}

		setErrorDispMsg(CmisConstance.getDispErrorMsg(errCode));

	} /**
	 * Insert the method's description here.
	 * Creation date: (2002-1-3 19:33:17)
	 * @param errCode java.lang.String
	 */
	public void setErrorDispMsg(String dispMsg) throws TranFailException {
		try {
			DataElement element = new DataElement();
			element.setName("tranErrorDispMsg");
			element.setValue(dispMsg);
			data.addElement(element);

		} catch (CTEObjectExistingException e) {

			try {
				data.setValueAt("tranErrorDispMsg", dispMsg);
			} catch (Exception e1) {
				throw new TranFailException(
					"xdtz22107",
					getOperationName(),
					e1.getMessage());
			}

		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22107",
				getOperationName(),
				e.getMessage());
		}
	} /**
	 * Insert the method's description here.
	 * Creation date: (2002-1-3 19:33:17)
	 * @param errCode java.lang.String
	 */
	public void setErrorLocation(String errLocation) throws TranFailException {
		try {
			DataElement element = new DataElement();
			element.setName("tranErrorLocation");
			element.setValue(errLocation);
			data.addElement(element);

		} catch (CTEObjectExistingException e) {
			try {
				data.setValueAt("tranErrorLocation", errLocation);
			} catch (Exception e1) {
				throw new TranFailException(
					"xdtz22107",
					getOperationName(),
					e1.getMessage());
			}

		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22107",
				getOperationName(),
				e.getMessage());
		}
	} /**
	 * Insert the method's description here.
	 * Creation date: (2002-1-3 19:33:17)
	 * @param errCode java.lang.String
	 */
	public void setErrorMessage(String errMsg) throws TranFailException {
		try {
			DataElement element = new DataElement();
			element.setName("tranErrorMsg");
			element.setValue(errMsg);
			data.addElement(element);
		} catch (CTEObjectExistingException e) {

			try {
				data.setValueAt("tranErrorMsg", errMsg);
			} catch (Exception e1) {
				throw new TranFailException(
					"xdtz22107",
					getOperationName(),
					e1.getMessage());
			}

		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22107",
				getOperationName(),
				e.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 15:06:00)
	 * @param dataName java.lang.String
	 * @param fieldValue java.lang.String
	 */
	public void setFieldValue(String dataName, Object fieldValue)
		throws TranFailException {
		try {
			try {
				if (fieldValue instanceof DataCollection) {
					throw new Exception("Invalid request in setFieldValue(String dataName, Object fieldValue)");
				} else {
					data.setValueAt(dataName, fieldValue);
				}
			} catch (CTEObjectNotFoundException e) {
				data.addElement(dataName, fieldValue);
			}
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22107",
				getOperationName(),
				e.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 10:57:44)
	 */
	public void setOperationData(KeyedDataCollection opData) {
		data = opData;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-28 9:49:39)
	 * @param opData java.lang.Object
	 */
	public void setOperationDataToSession() throws TranFailException {

		try {
			String oldOpName = (String) getStringAt("oldOperationName");
			if (oldOpName != null)
				setFieldValue("operationName", oldOpName);
		} catch (Exception ee) {
		}

		try {
			if (maxRow != -1) {
				for (int i = 0; i < vIcollName.size(); i++) {
					String iName = (String) vIcollName.elementAt(i);
					IndexedDataCollection ic = getIndexedDataCollection(iName);
					int len = ic.getSize();
					if (ic != null && len > maxRow) {

						this.setFieldValue(iName + "IsOverflow", "true");
						this.setFieldValue(
							iName + "OverflowMsg",
							"交易结果记录数过大["
								+ len
								+ "]，当前返回记录数为 ["
								+ maxRow
								+ "],请调整查询条件缩小数据量.");
						for (int j = len - 1; j >= maxRow; j--) {
							ic.removeElement(j);

						}
					}
				}
			}

			if (sessionMgr.getSessionData("operationSessionData") != null) {
				sessionMgr.removeSessionData("operationSessionData");
			}
			sessionMgr.addSessionData("operationSessionData", data);
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {

			throw new TranFailException(
				"xdtz22108",
				getOperationName(),
				e.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 10:57:44)
	 */
	public void setOperationName(String opClassName) {
		operationName = opClassName;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 11:41:28)
	 * @param pageValue java.lang.String
	 */
	public void setReplyPage(String pageValue) throws TranFailException {
		try {
			try {
				String baseWebPath = null;
				try {
					baseWebPath =
						(String) CmisConstance.getParameterSettings().get(
							"webBasePath");
				} catch (Exception e) {
					baseWebPath = null;
				}
				if (baseWebPath != null && baseWebPath.trim().length() > 1) {
					baseWebPath = baseWebPath.trim();
					pageValue = pageValue.trim();
					if (pageValue.startsWith(baseWebPath)) {
						pageValue = pageValue.substring(baseWebPath.length());
					}
					if (pageValue.startsWith("/icbc/cmis")){
						pageValue = pageValue.substring(10);
					}
				}
				DataElement field = new DataElement();
				field.setName("replyPage");
				field.setValue(pageValue);
				data.addElement(field);
			} catch (CTEObjectExistingException e) {
				data.setValueAt("replyPage", pageValue);
			}
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22109",
				getOperationName(),
				e.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-7-11 22:03:55)
	 * @param serverIp java.lang.String
	 */
	public void setServerAddr(String serverIp, String userAd) {
		if (serverIp == null)
			serverIp = "";
		if (userAd == null)
			userAd = "";
		serverAddr = serverIp;
		userAddr = userAd;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-25 11:22:34)
	 * @param mgr com.icbc.cmis.base.CMisSessionMgr
	 */
	public void setSessionMgr(CMisSessionMgr mgr) {
		sessionMgr = mgr;
	}
	public String substringb(String ts, int len) {
		byte[] tb = ts.getBytes();
		if (tb.length <= len)
			return ts;

		float k = 0;
		for (int i = 0; i < len; i++) {
			if (tb[i] < 0) {
				k += 0.5;
			} else {
				k++;
			}
		}
		return ts.substring(0, (int) k);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-3-30 14:32:31)
	 * @param dataName java.lang.String
	 */
	public void trimValueAt(String dataName) throws TranFailException {
		try {
			String dataValue = (String) data.getValueAt(dataName);
			if (dataValue != null) {
				int realLen = dataValue.trim().length();
				String tmpStr = "";
				for (int i = 0; i < realLen; i++) {
					tmpStr = tmpStr + "@";
				}
				if (dataValue.indexOf(tmpStr) != -1) {
					dataValue = "";
				}
				data.setValueAt(dataName, dataValue.trim());
			}
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22102",
				getOperationName(),
				e.getMessage());
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-3-30 14:32:31)
	 * @param dataName java.lang.String
	 */
	public void trimValueAt(String dataName, int len)
		throws TranFailException {
		try {
			String dataValue = (String) data.getValueAt(dataName);
			if (dataValue != null) {
				int realLen = dataValue.trim().length();
				String tmpStr = "";
				for (int i = 0; i < realLen; i++) {
					tmpStr = tmpStr + "@";
				}
				if (dataValue.indexOf(tmpStr) != -1) {
					dataValue = "";
				}
				dataValue = substringb(dataValue.trim(), len);
				data.setValueAt(dataName, dataValue.trim());
			}
		} catch (Exception e) {
			throw new TranFailException(
				"xdtz22102",
				getOperationName(),
				e.getMessage());
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-24 11:17:17)
	 * @param dataName java.lang.String
	 * @param newValue java.lang.String
	 */
	public void updateSessionData(String dataName, Object newValue)
		throws TranFailException {
		if (newValue instanceof IndexedDataCollection) {
			IndexedDataCollection tmpI = (IndexedDataCollection) newValue;
			if (tmpI.getSize() > maxRow) {
				throw new TranFailException(
					"xdtz22127",
					getOperationName()
						+ ".updateSessionData(String dataName, Object newValue)",
					"在session中添加大容量数据失败,；请调整查询条件，缩小数据量",
					"在session中添加大容量数据失败,；请调整查询条件，缩小数据量");
			}
		}
		sessionMgr.updateSessionData(dataName, newValue);
	}

	public static String getHostArea(String areaCode){
		if(areaCode == null) return areaCode;
		String areaTrans = (String)CmisConstance.getParameterSettings().get("areaTransEnable");
		if(areaTrans != null && areaTrans.equalsIgnoreCase("true")){
			TableBean tb = (TableBean)CmisConstance.getDictParam().get("mag_area_comparison2");
			if(tb != null){
				String hostArea = (String)tb.getNameByCode("dj_area_code","area_code",areaCode);
				if(hostArea != null && hostArea.trim().length() != 0){
					return hostArea;
				}
			}

		}
		return areaCode;

	}


	private void dsrInit()throws TranFailException{
		if(isElementExist("INIT_FLAG")){
			if(isElementExist("ifSelfContinue")){//2007年2月份版本修改
				if(!getStringAt("ifSelfContinue").equals("1")){//2007年2月份版本修改
					this.removeDataField("INIT_FLAG");
				}
			}else{
				this.removeDataField("INIT_FLAG");
			}
		}
		if(isElementExist("ROW_REQ")){
			this.removeDataField("ROW_REQ");
		}
		if(isElementExist("INQ_ITEMS")){
			this.removeDataField("INQ_ITEMS");
		}
		if(this.isElementExist("MRSD_PastRows")){//20070130 update by wlx 
			this.removeDataField("MRSD_PastRows");
		}
	}

	private void dsrResultHandler() throws TranFailException {
		String tranOk = getStringAt("TRANSOK"); //交易成功标志
		String errNo = getStringAt("ERR_NO"); //错误编号
		String tableName = getStringAt("TABLE_NAME"); //出错表名
		String tsfStat = getStringAt("TSF_STAT"); //交易整体执行状态标志
		String errMsg = "";
		if (tranOk == null)
			tranOk = "";
		if (errNo == null)
			errNo = "";
		if (tableName == null)
			tableName = "";
		if (tsfStat == null)
			tsfStat = "";
		if (errNo.trim() != "0"){
			if(errNo.trim().length() == 4) errNo = "0"+errNo.trim();
			errMsg = (String) CmisConstance.getDispErrorMsg(errNo);
		}

		if (!tsfStat.trim().equals("0") || !tranOk.trim().equals("0")) {
			if(!tsfStat.trim().equals("0")){
				throw new TranFailException(
					"CmisOperation010",
					"CmisOperation.dsrResultHandler()",
					"没有成功调用到主机程序！主机错误码["
						+ errNo
						+ "] "
						+ tableName
						+ "，错误信息："
						+ errMsg,
					errMsg);
			}else if(!errNo.trim().equals("0")&&!errNo.trim().equals("100")){
				throw new TranFailException(
					"CmisOperation013",
					"CmisOperation.dsrResultHandler()",
					"调用主机程序失败！主机错误码["
						+ errNo
						+ "] "
						+ tableName
						+ "，错误信息："
						+ errMsg,
					errMsg);

			}else{
				throw new TranFailException(
					"CmisOperation012",
					"CmisOperation.dsrResultHandler()",
					"调用主机程序失败！主机错误码[" + errNo + "] " + tableName,
					"调用主机程序失败！主机错误码[" + errNo + "]");
			}

		}

	}

	

	private void writeFile(byte[] b, String fileName) {
		String debug =
			(String) CmisConstance.getParameterSettings().get("isDebugModel");
		if (debug == null || !debug.equals("true"))
			return;

		try {
			String rootPath =
				(String) CmisConstance.getParameterSettings().get(
					"fileRootPath");
			if (rootPath.endsWith(System.getProperty("file.separator")))
				fileName = rootPath + fileName;
			else
				fileName =
					rootPath + System.getProperty("file.separator") + fileName;

			DataOutputStream outfile =
				new DataOutputStream(new FileOutputStream(fileName));

			outfile.write(b, 0, b.length);

			outfile.flush();
			outfile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private DSRPackMsg packModul(byte[] b, String modulName, int size, int idx)throws TranFailException {
		if (idx + 10 >= b.length) {
			int buf = 512;
			byte[] tmp = new byte[idx + buf];
			System.arraycopy(b, 0, tmp, 0, b.length);
			b = tmp;
		}
		byte[] bx = new byte[8];
		if(modulName.length()>8) modulName = modulName.substring(0,8);
		byte[] tTmp = getBytes(modulName);
		System.arraycopy(
			tTmp,
			0,
			bx,
			0,
			tTmp.length);
		System.arraycopy(bx, 0, b, idx, 8);
		idx += 8;
		b[idx++] = (byte) ((size & 0xff00) >>> 8);
		b[idx++] = (byte) (size & 0x00ff);
		return new DSRPackMsg(idx,b);
	}

	private byte[] getBytes(String src)throws TranFailException{
		if(src == null){
			return null;
		}
		String encoding = (String)CmisConstance.getParameterSettings().get("dsrEndoding");

		if(encoding == null || encoding.trim().length() == 0){
			throw new TranFailException("CmisOperation031","CmisOperation.getBytes()","不支持的字符编码格式["+encoding+"]","不支持的字符编码格式["+encoding+"],请在文件icbccmis.xml中指定正确的编码格式！");
		}
		try{
			return src.getBytes(encoding);
		}catch(UnsupportedEncodingException ex){
			throw new TranFailException("CmisOperation032","CmisOperation.getBytes()","不支持的字符编码格式["+encoding+"]"+ex.getMessage(),"不支持的字符编码格式["+encoding+"],编码格式为空;请在文件icbccmis.xml中指定正确的编码格式！");
		}
	}

	private String getString(byte[] bSrc)throws TranFailException{
		if(bSrc == null){
			return null;
		}
		String encoding = (String)CmisConstance.getParameterSettings().get("dsrEndoding");
		if(encoding == null || encoding.trim().length() == 0){
			throw new TranFailException("CmisOperation033","CmisOperation.getBytes()","不支持的字符编码格式["+encoding+"]","不支持的字符编码格式["+encoding+"],编码格式为空;请在文件icbccmis.xml中指定正确的编码格式！");
		}
		try{
			return new String(bSrc,encoding);
		}catch(UnsupportedEncodingException ex){
			throw new TranFailException("CmisOperation034","CmisOperation.getBytes()","不支持的字符编码格式["+encoding+"]","不支持的字符编码格式["+encoding+"],请在文件icbccmis.xml中指定正确的编码格式！");
		}
	}

	private DSRPackMsg pack(byte[] b, String desc, int idx)throws TranFailException {
		int len = 0;
		byte[] bTmp = null;
		bTmp = getBytes(desc);
		if (desc != null)
			len = bTmp.length;
		if (idx + len + 2 >= b.length) {
			int buf = 512;
			if (len + 2 > 512)
				buf = len + 2;
			byte[] tmp = new byte[idx + buf];
			System.arraycopy(b, 0, tmp, 0, b.length);
			b = tmp;
		}
		b[idx++] = (byte) ((len & 0xff00) >>> 8);
		b[idx++] = (byte) (len & 0x00ff);
		if (len > 0) {
			System.arraycopy(bTmp, 0, b, idx, len);
			idx += len;
		}
		return new DSRPackMsg(idx,b);
	}


		private void WriteLog(KeyedDataCollection kColl) throws TranFailException {

			ArrayList logopList = CmisConstance.opDataList;
			try {
				String sKcoll = kColl.toString();
				if (sKcoll.length() > 1000) {
					sKcoll = sKcoll.substring(0, 1000);
				}
				String opName =""; 
				String opAction = "";
				try {
				 opName=(String)kColl.getValueAt("operationName");
					opAction = (String)kColl.getValueAt("opAction");
				}
				catch(Exception ex)
				{
      	
				}
				if (logopList!=null) {
					for (int i = 0; i < logopList.size(); i++) {
						ArrayList logopSubList = (ArrayList)logopList.get(i);
						//从内存中查找logopList，判断是否需要记录
						if (logopSubList.get(0).equals(opName) && logopSubList.get(1).equals(opAction)) {
							//String sKcoll=kColl.toString();
							insertMagOpdataLog(opName, opAction, sKcoll);
						}
	
					}
				}
			}
			catch (Exception e) {
				throw new TranFailException("CmisOperation035", "CmisOperation.WriteLog(KeyedDataCollection kColl)", "获取KeyedDataCollection失败！");
			}

		}

		/**
			 * <b>功能描述: 把交易记录保存记录到mag_opdata_log表中</b><br>
			 * <p> </p>
			 * @param 
			 *  
			 */
		public void insertMagOpdataLog(String opName, String opAction, String logContent) throws Exception {
			SqlTool sqlTool = null;
			String logDate = (String)CmisConstance.getWorkDate("yyyyMMdd");
			String logTime = (String)CmisConstance.getWorkDate("hh:mm:ss");
			try {

				sqlTool = new SqlTool(new DummyOperation());

				sqlTool.getConn("missign");
				String insertSql = "insert into mag_opdata_log values(?,?,?,?,?)";
				Vector vData = new Vector(5);
				vData.add(opName);
				vData.add(opAction);
				vData.add(logDate);
				vData.add(logTime);
				vData.add(logContent);

				int mark = sqlTool.executeUpdate(insertSql, vData);
				sqlTool.commit();
				if (mark < 0)
					throw new TranFailException(
						"CmisOperation036",
						"CmisOperation.insertMagOpdataLog(String opName, String opAction, String logContent)",
						"操作失败！",
						"增加监控记录失败！");

			}
			catch (Exception e) {
				throw e;
			}
			finally {
				try {
					if (sqlTool != null)
						sqlTool.closeconn();
				}
				catch (Exception e) {}
			}
		}
}
