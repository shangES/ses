package icbc.cmis.service.channel;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.CmisConstance;
import java.sql.*;
import java.util.Hashtable;
import oracle.jdbc.driver.*;

public class ServiceHandlerDAO extends CmisDao {

  public ServiceHandlerDAO(icbc.cmis.operation.CmisOperation op) {
  super(op);
  }

  public String logInServerInfo(Hashtable logData) throws TranFailException {
    String urlInfo = (String)logData.get("RvData");
	String SerialNo = (String)logData.get("SerialNo");
	String TransNo = (String)logData.get("TransNo");
	String AuthId = (String)logData.get("AuthId");
	String AreaCode = (String)logData.get("AreaCode");
	String SysID = (String)logData.get("SysID");
	String channelType = (String)logData.get("channelType");
	CallableStatement cStmt = null;
	String retStat = "0";
	String retSeq = "";
    try {
		System.out.print("Begin log dao!");
		this.getConnection("missign");
		 cStmt=
							conn.prepareCall("{call serverintranslogproc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			//
			cStmt.registerOutParameter(14, OracleTypes.VARCHAR);
			cStmt.registerOutParameter(15, OracleTypes.VARCHAR);
			cStmt.setString(1, SerialNo);//序列号
			cStmt.setString(2, CmisConstance.getWorkDate("yyyyMMdd"));
			cStmt.setString(3, CmisConstance.getWorkDate("HHss"));
			cStmt.setString(4, SysID);
			cStmt.setString(5, AuthId);//序列号
			cStmt.setString(6, AreaCode);
			cStmt.setString(7, TransNo);
			cStmt.setString(8, "0");
			cStmt.setString(9, "0");
			cStmt.setString(10, urlInfo);
			cStmt.setString(11,  "01");
			cStmt.setString(12, "0");
			cStmt.setString(13, "I");
			cStmt.executeUpdate();
			retStat = cStmt.getString(14);
			retSeq  = cStmt.getString(15);
			cStmt.close();
		    //System.out.print("Begin log retStat!"+retStat);
    }
    catch (TranFailException ex) {
//     // throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("cmisutil212","icbc.cmis.util.ChooseAreaDAO",ex.getMessage() ,"产生地区列表错误！");
    }
    finally {
     if(cStmt != null) try {cStmt.close();} catch (Exception ex) {};
     this.closeConnection();
    }
    return retSeq;
  }
  
  public String logInServerInfoUpdate(Hashtable logData) throws TranFailException {

	String reqSeq = (String)logData.get("reqSeq");
	String errCode = (String)logData.get("errCode");
	String errMsg = (String)logData.get("errMsg");
	CallableStatement cStmt = null;
	String retStat = "0";
	String retSeq = "";
	try {
		this.getConnection("missign");
		 cStmt=
							conn.prepareCall("{call serverintranslogproc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			//
			cStmt.registerOutParameter(14, OracleTypes.VARCHAR);
			cStmt.registerOutParameter(15, OracleTypes.VARCHAR);
			cStmt.setString(1, reqSeq);//序列号
			cStmt.setString(2, "");
			cStmt.setString(3, "");
			cStmt.setString(4, "");
			cStmt.setString(5, "");//序列号
			cStmt.setString(6, "");
			cStmt.setString(7, "");
			cStmt.setString(8, errCode);
			cStmt.setString(9, errMsg);
			cStmt.setString(10, "");
			cStmt.setString(11,  "01");
			cStmt.setString(12, "0");
			cStmt.setString(13, "U");
			cStmt.executeUpdate();
			retStat = cStmt.getString(14);
			retSeq  = cStmt.getString(15);
			cStmt.close();
	}
	catch (TranFailException ex) {
//	   // throw ex;
	}
	catch (Exception ex) {
	  throw new TranFailException("cmisutil212","icbc.cmis.util.ChooseAreaDAO",ex.getMessage() ,"产生地区列表错误！");
	}
	finally {
	 if(cStmt != null) try {cStmt.close();} catch (Exception ex) {};
	 this.closeConnection();
	}
	return retSeq;
  }

  
}