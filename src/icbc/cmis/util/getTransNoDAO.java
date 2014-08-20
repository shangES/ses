package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.TranFailException;
import java.sql.*;
import java.util.Vector;
import oracle.jdbc.driver.OracleTypes;;

public class getTransNoDAO extends CmisDao {

  public getTransNoDAO(icbc.cmis.operation.CmisOperation op) {
  super(op);
  }

  public String getNo() throws TranFailException {
	CallableStatement cStmt= null;
	String  ret_TransNo=null;
    try {
      this.getConnection("missign");

	    cStmt=
	      conn.prepareCall("{?=call 	getTransNo()}");
	    cStmt.registerOutParameter(1, OracleTypes.VARCHAR);
	    cStmt.executeQuery();
	    
    	
	    ret_TransNo= cStmt.getString(1);
    	cStmt.close();

    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("cmisutil212","icbc.cmis.util.getTransNoDAO",ex.getMessage(),"产生联机交易序列号时报错！");
    }
    finally {
      if(cStmt != null) try {cStmt.close();} catch (Exception ex) {};
      this.closeConnection();
    }
	return ret_TransNo;
  }
}