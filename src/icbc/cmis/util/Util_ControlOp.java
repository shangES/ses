package icbc.cmis.util;

import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;

/*************************************************************
 * 
 * <b>创建日期: </b> 2005-11-29<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * <br>
 * <p>Copyright: Copyright (c)2005</p>
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

public class Util_ControlOp extends CmisOperation {

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

			String opAction = this.getStringAt("opAction");
			String customercode = this.getStringAt("customercode");
			String areacode = this.getStringAt("areacode");
			String tradecode = this.getStringAt("tradecode");
			String formercode = this.getStringAt("formercode");
			String opflag = this.getStringAt("opflag");
			String yewukind = this.getStringAt("yewukind");
			
			if ("20001".equals(opAction)) {
				Util_ControlDao dao = new Util_ControlDao(this);
				String[] result =
					dao.check(
						customercode,
						areacode,
						tradecode,
						formercode,
						opflag,
						yewukind
						);
				String xmlPack =
					"DirectOutput<?xml version=\"1.0\" encoding=\"GB2312\"?>";
				xmlPack += "<datas>";

				xmlPack += "<en result='" + result[0] +"' message='"+result[1] +" '/>";
				xmlPack += "</datas>";
				setReplyPage(xmlPack);
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException(
				"Util_ControlOp",
				"icbc.cmis.util.Util_ControlOp",
				e.getMessage(),
				e.getMessage());
		} finally {
			this.setOperationDataToSession();
		}

	}

}
