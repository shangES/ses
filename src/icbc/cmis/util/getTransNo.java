package icbc.cmis.util;
import icbc.cmis.base.*;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class getTransNo {

  public getTransNo() {
    super();
  }
  public static String getNo() throws TranFailException {
	String strNo = null;
	try{
	    getTransNoDAO dao = new getTransNoDAO(new icbc.cmis.operation.DummyOperation());	
		strNo = dao.getNo();
	} 
	catch(TranFailException ex) {
      throw ex;
    }
    catch(Exception ex) {
      throw new TranFailException("cmisGetTranNo","",ex.getMessage(),ex.getMessage());
    }
	return strNo;	

  }


}