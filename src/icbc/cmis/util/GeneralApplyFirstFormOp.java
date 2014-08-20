package icbc.cmis.util;

import java.util.Vector;

import icbc.cmis.base.CmisConstance;
import icbc.cmis.base.KeyedDataCollection;
import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;

/*************************************************************
 * 
 * <b>创建日期: </b> 2006-1-24<br>
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

public class GeneralApplyFirstFormOp extends CmisOperation {

	private String baseWebPath =
		(String) CmisConstance.getParameterSettings().get("webBasePath");
	/** 
	 * <b>功能描述: </b><br>
	 * <p>	</p>
	 * @see icbc.cmis.operation.CmisOperation#execute()
	 * @throws Exception
	 * @throws TranFailException
	 * 
	 */
	public void execute() throws Exception, TranFailException {

		try {
            
            String bankflag=(String)this.getSessionData("BankFlag");
            if("4".equals(bankflag)){
                this.setFieldValue("Apply_sponsorBank",this.getSessionData("AreaCode"));
                GeneralApplyFirstFormDao dao=new GeneralApplyFirstFormDao(this);
                this.setFieldValue("Apply_sponsorBankName",dao.getAreaName((String)this.getSessionData("AreaCode")));               
            }
            
			getInitFormInfo();
            this.setFieldValue("Apply_kind",this.getStringAt("Apply_kind"));
         //   this.setFieldValue("lang_code",this.getStringAt("lang_code"));
            this.setFieldValue("Apply_stage",this.getStringAt("Apply_stage"));
            if(this.isElementExist("Apply_sub"))
            this.setFieldValue("Apply_sub",this.getStringAt("Apply_sub"));
			if(this.isElementExist("jjapply"))
			this.updateSessionData("jjapply",this.getStringAt("jjapply"));
			          
			this.setReplyPage(baseWebPath + "/util/GeneralApplyFirstForm.jsp");

		} catch (TranFailException e) {
			throw e;
		} 

	}

	/**
	 * <b>功能描述: 查询首页面信息，放入集合</b><br>
	 * <p> </p>
	 * 
	 *  
	 */
	private void getInitFormInfo() throws TranFailException {
		GeneralApplyFirstFormDao dao = new GeneralApplyFirstFormDao(this);
        Vector v_button=null;
		Vector v_content = dao.getFormContent();
		Vector v_path = dao.getIncludejs("0");
		Vector v_hidden = dao.getHidden("0","");
        if("1".equals(this.getStringAt("Apply_stage"))){         
		 v_button = dao.getButton("0",this.getStringAt("approveAction"));
        }else
         v_button = dao.getButton("0","");
		dao.getTitle();
		this.setFieldValue("Apply_content", v_content);
		this.setFieldValue("Apply_path", v_path);
		this.setFieldValue("Apply_button", v_button);
		this.setFieldValue("Apply_hidden", v_hidden);

	}

}
