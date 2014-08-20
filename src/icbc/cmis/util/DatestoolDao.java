////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2005 ICBC
//
//ALL RIGHTS RESERVED BY ICBC CORPORATION, THIS PROGRAM
//MUST BE USED SOLELY FOR THE PURPOSE FOR WHICH IT WAS  
//FURNISHED BY ICBC CORPORATION, NO PART OF THIS PROGRAM
//MAY BE REPRODUCED OR DISCLOSED TO OTHERS, IN ANY FORM
//WITHOUT THE PRIOR WRITTEN PERMISSION OF ICBC CORPORATION.
//USE OF COPYRIGHT NOTICE DOES NOT EVIDENCE PUBLICATION
//OF THE PROGRAM
//
//			ICBC CONFIDENTIAL AND PROPRIETARY
//
////////////////////////////////////////////////////////////////////////////
//

package icbc.cmis.util;

import icbc.cmis.operation.CmisOperation;
import icbc.cmis.service.CmisDao;

import java.sql.CallableStatement;

import oracle.jdbc.OracleTypes;

/*************************************************************
 * 
 * <b>创建日期: </b> 2005-10-13<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * <br>
 * <p>Copyright: Copyright (c)2005</p>
 * <p>Company: </p>
 * 
 * @author zjfh-guop
 * 
 * @version 
 * 
 * @since 
 * 
 * @see 
 * 
 *************************************************************/

public class DatestoolDao extends CmisDao {

	/**
	 * <b>构造函数</b><br>
	 * @param op
	 */
	public DatestoolDao(CmisOperation op) {
		super(op);
		// TODO Auto-generated constructor stub
	}

	public String calc_term(String startdate,String enddate){
       String term = "";
       try{		
       	  getConnection();
       	  
		CallableStatement cstmt =
							conn.prepareCall(
								"{call pack_control_fund.proc_cal_term (?,?,?,?,?)}");
       	cstmt.setString(1,startdate);
       	cstmt.setString(2,enddate);
		cstmt.registerOutParameter(3, OracleTypes.VARCHAR);
		cstmt.registerOutParameter(4, OracleTypes.VARCHAR); 
		cstmt.registerOutParameter(5, OracleTypes.VARCHAR);
       	
       	cstmt.execute();
       	
       	term = cstmt.getString(3);
       	if(term.equals("-1")){
       		term = "000";
       	}
       	  
       	  closeConnection();
       	  
       }
       catch(Exception e){
       	  if(conn!=null)
       	     closeConnection();
       	  term = "000";
       }
       return term;
       
	}

}
