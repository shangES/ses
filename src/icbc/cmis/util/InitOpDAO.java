package icbc.cmis.util;

import icbc.cmis.base.*;
import icbc.cmis.service.CmisDao;
import java.sql.*;
import java.util.*;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class InitOpDAO extends CmisDao{

  public InitOpDAO(icbc.cmis.operation.CmisOperation op) {
    super(op);
  }
  
  public Vector getTablesByType(String tableType) throws TranFailException {
  	Vector ret = new Vector();
  	PreparedStatement pstmt = null;
  	ResultSet rs = null;
  	String sql = "select tab_owner,tab_code,tab_name from mag_cache_tables where tab_type=?";
  	try{
  	  this.getConnection("missign");
  	  pstmt = conn.prepareStatement(sql);
  	  pstmt.setString(1,tableType);
  	  rs = pstmt.executeQuery();
  	  while(rs.next()){
  	    String[] tab = new String[3];
  	    tab[0] = rs.getString(1);
  	    tab[1] = rs.getString(2);
  	    tab[2] = rs.getString(3);
  	    ret.add(tab);
  	  }
  	}
  	catch(Exception e){
  	  throw new TranFailException("cmisInit","icbc.cmis.util.InitOpDAO",e.getMessage(),e.getMessage());
  	}
  	finally {
      if(rs != null) try {rs.close();} catch (Exception ex) {}
      if(pstmt != null) try {pstmt.close();} catch (Exception ex) {}
      this.closeConnection();
    }
  	return ret;
  }	
  

}
