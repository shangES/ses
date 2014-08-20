package icbc.cmis.util;

import icbc.cmis.base.*;
import icbc.cmis.operation.*;
import java.util.Vector;

/**
 * <p>Title: 柜员选择</p>
 * <p>Description:此模块提供根据条件选择单个或多个柜员的功能。 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company:icbc </p>
 * @author 叶晓挺
 * @version 1.0
 */

public class ChooseEmployee extends CmisOperation {

  public ChooseEmployee() {
  }

  /**
   * 根据传入的参数，显示柜员列表。
   * 传入的参数：<br>
   * eCodeWsA342d：柜员号<br>
   * areaWsA342d：地区号，多个地区用逗号分隔<br>
   * majorWsA342d：专业，多个专业用逗号分隔<br>
   * eClassWsA342d：可选柜员角色，多个角色用逗号分隔<br>
   * includeSelfWsA342d：是否包含自己，true|false<br>
   * multiSelectWsA342d：多选， true|false<br>
   *
   * 显示柜员列表：柜员号、姓名、专业、角色
   * @throws TranFailException
   */
  public void execute() throws TranFailException{
    String eCode = this.getStringAt("eCodeWsA342d");
    String area = this.getStringAt("areaWsA342d");
    String major = this.getStringAt("majorWsA342d");
    String eClass = this.getStringAt("eClassWsA342d");
    String includeSelf = this.getStringAt("includeSelfWsA342d");
    String multiSelect = this.getStringAt("multiSelectWsA342d");
	//修改原因：新增一个是否排除内部柜员的参数 exludeInsideCode342d
	//修改日期: 2004-5-20 
	//修改人：杨光润
    boolean exludeInsideCode = false;
    if(isElementExist("exludeInsideCode342d"))
		exludeInsideCode=new  Boolean( this.getStringAt("exludeInsideCode342d")).booleanValue();
    String distinctIgnor = "00";
    try {
      distinctIgnor = this.getStringAt("distinctIgnoreWsA342d");
    }
    catch (Exception ex) {
    }


    try {
      ChooseEmployeeDAO dao = new ChooseEmployeeDAO(this);
      Vector employee = dao.getEmployee(eCode,area,major,eClass,Boolean.valueOf(includeSelf).booleanValue(),distinctIgnor,exludeInsideCode);
      this.setFieldValue("cmisChooseEmployeeDataWsA342d",employee);
      this.setFieldValue("cmisChooseEmployeeMultiSelectWsA342d",multiSelect);
      this.setOperationDataToSession();
      this.setReplyPage("/icbc/cmis/util/util_ChooseEmployee.jsp");
    } catch (TranFailException ex) {
      throw ex;
    } catch (Exception ex) {
//      throw new TranFailException("cmisutil201","icbc.cmis.util.ChooseEmployee",ex.getMessage(),"生成柜员列表出错！");
			throw new MuiTranFailException("099995", "ChooseEmployee.execute()",(String)this.getSessionData("LangCode"));
    }
  }
}