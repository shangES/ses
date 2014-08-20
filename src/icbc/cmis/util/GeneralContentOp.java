package icbc.cmis.util;

import java.util.Vector;

import icbc.cmis.base.CmisConstance;
import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;

/*************************************************************
 * 
 * <b>创建日期: </b> 2006-5-29<br>
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

public class GeneralContentOp extends CmisOperation {
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
            //查询页面的各项内容
			getFormInfo();
			String jjapply="";
			this.setFieldValue("Apply_kind", this.getStringAt("Apply_kind"));
			this.setFieldValue("Apply_stage", this.getStringAt("Apply_stage"));
			if (this.isElementExist("Apply_sub"))
				this.setFieldValue("Apply_sub", this.getStringAt("Apply_sub"));
			if (this.isElementExist("Apply_sponsorBank"))
				this.setFieldValue(
					"Apply_sponsorBank",
					this.getStringAt("Apply_sponsorBank"));
			if (this.isElementExist("Apply_customerCode"))
				this.setFieldValue(
					"Apply_customerCode",
					this.getStringAt("Apply_customerCode"));
			if (this.isElementExist("Apply_customerName"))
				this.setFieldValue(
					"Apply_customerName",
					this.getStringAt("Apply_customerName"));
			if (this.isElementExist("Apply_contractID"))
				this.setFieldValue(
					"Apply_contractID",
					this.getStringAt("Apply_contractID"));
			if (this.isElementExist("Apply_pesudoID"))
				this.setFieldValue(
					"Apply_pesudoID",
					this.getStringAt("Apply_pesudoID"));
			if (this.isElementExist("Apply_da200211010"))
				this.setFieldValue(
					"Apply_da200211010",
					this.getStringAt("Apply_da200211010"));
			
			this.updateSessionData("Apply_stage",this.getStringAt("Apply_stage"));
			
			this.updateSessionData("Apply_customerCode",this.getStringAt("Apply_customerCode"));
			this.updateSessionData("Apply_contractID",this.getStringAt("Apply_contractID"));		
			this.setOperationDataToSession();
			this.setReplyPage(baseWebPath + "/util/GeneralContent.jsp");

		} catch (TranFailException e) {
            throw new TranFailException(
                "100791",
                "icbc.cmis.util.GeneralContentOp");
		}

	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * 
	 *  
	 */
	private void getFormInfo() throws TranFailException {
		GeneralApplyFirstFormDao dao = new GeneralApplyFirstFormDao(this);
		
        String Isquery="";
		String oper = "";
		String pageMode = "";
        String operMode="";
        String isJieju="0";  //判断是新增进来还是查询进来
        String querytj="";
		
        if(this.isElementExist("querytj"))
		querytj=this.getStringAt("querytj");
        if (this.isElementExist("approveAction"))
        operMode = this.getStringAt("approveAction");
        
		if (this.isElementExist("oper"))
			oper = this.getStringAt("oper");
		//用于跳转到那个页面
		if ("showInsertView".equals(oper))
			pageMode = "0";
		else if ("showQueryView".equals(oper))
			pageMode = "1";
		else
			pageMode = "2";
            
        if (this.isElementExist("approveAction")&&"2".equals(operMode)&&"1".equals(pageMode))//如果是台账，首页面是查询进入，且是维护状态的，operMode为3
        operMode="3";
        
        if("".equals(operMode))
        operMode=pageMode;
		Vector v_TabContent=null;
		Vector v_path = null;
		Vector v_hidden = null;
		Vector v_button = null;
		Vector v_TabContentjj=new Vector();
		if (this.isElementExist("isJieju"))
				isJieju=this.getStringAt("isJieju");
		if (this.isElementExist("Isquery"))   //判断是否由查询进来
		Isquery=this.getStringAt("Isquery");		
			if("0".equals(isJieju))  //合同进入
			{ 
				v_TabContent=dao.getContent(this.getStringAt("Apply_stage"), pageMode);
				if ("1".equals(this.getStringAt("Apply_stage"))) //台帐进入
				v_TabContent=dao.getContent("0", pageMode);
				if (!oper.equals("showInsertView")&&!this.getStringAt("Apply_kind").equals("24")&&!this.getStringAt("Apply_kind").equals("25")&&!this.getStringAt("Apply_kind").equals("26")&&!this.getStringAt("Apply_kind").equals("27"))
				v_TabContent=dao.getContent("5", pageMode);
				v_path = dao.getIncludejs("1");
				v_hidden = dao.getHidden("1", pageMode);
				v_button = dao.getButton("1", operMode);
			}
			else if("2".equals(isJieju))  //借据列表
			{
	  
				 v_TabContent=dao.getContent("2", pageMode);
				 v_path = dao.getIncludejs("1");
				 v_hidden = dao.getHidden("1", pageMode);
				 v_button = dao.getButton("2", operMode);
				}else if ("3".equals(isJieju)){     //借据新增
				
				v_TabContent=dao.getContent("3", pageMode);
				v_path = dao.getIncludejs("1");
				v_hidden = dao.getHidden("1", pageMode);
				v_button = dao.getButton("3", operMode);	
				v_TabContentjj=dao.getContent("2", pageMode);  //双层tab页
				
				}
				else if("4".equals(isJieju)){
				
					v_TabContent=dao.getContent("4", pageMode);
					v_path = dao.getIncludejs("1");
					v_hidden = dao.getHidden("1", pageMode);
					v_button = dao.getButton("4", operMode);
					v_TabContentjj=dao.getContent("2", pageMode);  //双层tab页	
				
				}
		else if("6".equals(isJieju)){
				
							v_TabContent=dao.getContent("6", pageMode);
							v_path = dao.getIncludejs("1");
							v_hidden = dao.getHidden("1", pageMode);
							v_button = dao.getButton("6", operMode);	
				
						}	
		else if("7".equals(isJieju)){
				
									v_TabContent=dao.getContent("7", pageMode);
									v_path = dao.getIncludejs("1");
									v_hidden = dao.getHidden("1", pageMode);
									v_button = dao.getButton("7", operMode);
			                        v_TabContentjj=dao.getContent("6", pageMode);  //双层tab页	
				
								}						
		this.updateSessionData("querytj",querytj);
		this.updateSessionData("Isquery",Isquery);		//保存查询状态，新增还是查询
		this.updateSessionData("isJieju",isJieju);
		
		dao.getTitle();
		this.setFieldValue("Apply_content", v_TabContent);
		this.setFieldValue("v_TabContentjj", v_TabContentjj);
		this.setFieldValue("Apply_path", v_path);
		this.setFieldValue("Apply_button", v_button);
		this.setFieldValue("Apply_TabContent", v_TabContent);
		this.setFieldValue("Apply_hidden", v_hidden);

	}

}
