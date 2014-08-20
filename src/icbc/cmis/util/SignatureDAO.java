package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import java.sql.*;
import icbc.cmis.base.TranFailException;

public class SignatureDAO extends CmisDao {

  public SignatureDAO(icbc.cmis.operation.CmisOperation op) {
    super(op);
  }

  public String getPrivateKey() throws TranFailException {
    Statement stmt = null;
    ResultSet rs = null;
    String sql = null;
    String ret = null;
    try {
      this.getConnection();
      stmt = conn.createStatement();
      //取得柜员信息
      sql = "select PRIVATEKEY from IMAGE_KEY where USEFLAG = '0'";
      rs = stmt.executeQuery(sql);
      if(!rs.next()) {throw new TranFailException("signature001","icbc.cmis.util.SignatureDAO","取加密密钥出错!","取加密密钥出错!");}

      ret = rs.getString(1);
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("signature002","icbc.cmis.util.SignatureDAO",ex.getMessage(),"");
    }
    finally {
      if(rs != null) try {rs.close();} catch (Exception ex) {};
      if(stmt != null) try {stmt.close();} catch (Exception ex) {};
      this.closeConnection();
    }
    return ret;
  }
}