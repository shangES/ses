package icbc.cmis.util;

import java.util.Iterator;
import java.util.Vector;

import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.second.pkg.DBProcedureAccessService;
import icbc.cmis.second.pkg.DBProcedureParamsDef;

/*************************************************************
 * 
 * <b>创建日期: </b> 2005-5-31<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * <br>
 * <p>Copyright: Copyright (c)2005</p>
 * <p>Company: </p>
 * 
 * @author  胡晓忠
 * 
 * @version 
 * 
 * @since 
 * 
 * @see 
 * 
 *************************************************************/

public class util_xdyw_ctrlOp extends CmisOperation {

	public util_xdyw_ctrlOp() {
	}
	public void execute() throws  TranFailException {
		try {
			String opAction = getStringAt("opAction");
            //String Userid			          
//            java.lang.String unique1=this.getStringAt("mainFlag1");
//            int tip=unique1.substring(0,unique1.length()-1).lastIndexOf("|");
//            String changeUnique1=unique1.substring(0,tip+1);
//            this.setFieldValue("mainFlag1",changeUnique1);
            
			if (opAction.equals("20001")) {
				performsxctrl();
			}
			else{
				throw new TranFailException(
					"xdtz0FFE6232",
					"util_xdyw_ctrlOp.execute()",
					"opAction出错！",
					"");}
		} catch (TranFailException e) {
			throw e;
		}catch (Exception e) {

			throw new TranFailException(
				"xdtz0FFE6233",
				"util_xdyw_ctrlOp.execute()",
				e.getMessage(),
				e.getMessage());
		}finally{
		this.setOperationDataToSession();
	    }

	
    }
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param RateTypeCode
	 * @param rateTime
	 *  
	 */
	private void performsxctrl()
		throws TranFailException {
		try {
			DBProcedureParamsDef def = 
				new DBProcedureParamsDef("Pack_Sx_Computer.get_dbyw_dbratio");
            //new DBProcedureParamsDef("kk");			
			Object a = this.getOperationData();
			def.addInParam("Userid"); //
            def.addInParam("mainFlag1");
            def.addInParam("mainFlag2");            
			def.addInParam("YWDate"); //
			def.addInParam("YWType"); //
            def.addInParam("FlowFlag"); //
			def.addInParam("FeeValue"); //
			def.addInParam("MoneyKind"); //
            def.addInParam("EnsureValue"); //
            def.addInParam("EnsureMoneyType"); //
			def.addOutParam("flag");
			def.addOutParam("msg");
			def.addOutParam("ratio");
			def.addOutParam("balance");
			def.addOutParam("balance_total");
            def.addOutParam("balancerisk");
            def.addOutParam("relativerisk");
            def.addOutParam("applycode");
            def.addOutParam("applyoper");
            def.addOutParam("approveoper");
            def.addOutParam("tradetype");
            def.addOutParam("customertype");
            
			//调用存储过程
			DBProcedureAccessService proceSrv =
				new DBProcedureAccessService(this);
			int returnCode =
				proceSrv.executeProcedure(this.getOperationData(), def);
			proceSrv = null;

			if (returnCode == 1) {
				throw new TranFailException(
					"xdtzEEB778",
					"util_xdyw_ctrlOp.performsxctrl()",
					getStringAt("msg"),
					"查询业务风险量出错!" + getStringAt("msg"));
			}
			else {

				String xmlPack =
					"DirectOutput<?xml version=\"1.0\" encoding=\"GB2312\"?>";
				xmlPack += "<datas>";
				String flag = this.getStringAt("flag");
				String ratio =this.getStringAt("ratio");
                String balance=this.getStringAt("balance");
                String balance_total = this.getStringAt("balance_total");
                String balancerisk =this.getStringAt("balancerisk");
                String relativerisk=this.getStringAt("relativerisk");
                String applycode = this.getStringAt("applycode");
                String applyoper = this.getStringAt("applyoper");
                String approveoper =this.getStringAt("approveoper");
                String tradetype=this.getStringAt("tradetype");
                String customertype =this.getStringAt("customertype");
                String msg=this.getStringAt("msg");    
                String mainFlag2=null;
                String mainFlag3=null;                
                if(flag==null){
                    flag="";
                }
                if(ratio==null){
                    ratio="";
                                }
                if(balance==null){
                    balance="";
                                }
                if(balance_total==null){
                    balance_total="";
                                }
                if(balancerisk==null){
                    balancerisk="";
                }
                if(relativerisk==null){
                    relativerisk="";
                                }
                if(applyoper==null){
                    applyoper="";
                                }
                if(approveoper==null){
                    approveoper="";
                                }
                if(tradetype==null){
                    tradetype="";
                                }
                if(customertype==null){
                    customertype="";
                                }
                if(msg==null){
                    msg="";
                            }
                mainFlag2=this.getStringAt("mainFlag2")+balance+"|"+"01|";
                mainFlag3=balance_total+"|"+balancerisk+"|"+relativerisk+"|"+ratio+"|"+applyoper+"|"+approveoper+"|";
				xmlPack += "<en tradetype='"+ tradetype+ "'  customertype='"+ customertype+ "' applycode='"+applycode+"'   mainFlag2='"+ mainFlag2 + "'  mainFlag3='"+ mainFlag3 +"'  flag='"+flag+"'   msg='"+msg+"'  />";
				xmlPack += "</datas>";
                           this.setOperationDataToSession();
				setReplyPage(xmlPack);
			
				
			}
		} catch (TranFailException ee) {
			throw ee;
		} catch (Exception e) {

			throw new TranFailException(
				"xdtz0FFE6234",
				"util_xdyw_ctrlOp.performsxctrl()",
				e.getMessage(),
				e.getMessage());
		}

	}
	

}
