package icbc.cmis.util;

import icbc.cmis.base.*;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.tfms.AA.AssureAssociationDAO;

import java.sql.Connection;
import java.util.*;

public  class GeneralApplyOp extends icbc.cmis.operation.CmisOperation {

	CmisOperation operation ;
    
	private String baseWebPath =
		(String) CmisConstance.getParameterSettings().get("webBasePath");

	/**
	 * constructor function
	 */
	public GeneralApplyOp() {
		super();
	}

	

	/** 
	 * <b>π¶ƒ‹√Ë ˆ: </b><br>
	 * <p>	</p>
	 * @see icbc.cmis.operation.CmisOperation#execute()
	 * @throws Exception
	 * @throws TranFailException
	 * 
	 */
	public void execute() throws Exception, TranFailException {
		// TODO Auto-generated method stub
		
	}
    
     
}
