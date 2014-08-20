package icbc.cmis.util;

import java.util.Hashtable;

import icbc.cmis.base.TranFailException;
import icbc.cmis.base.genMsg;
import icbc.cmis.tags.PropertyResourceReader;
import icbc.missign.Employee;

/**
 * ***********************************************************
 * 
 * <b>创建日期: </b>2007-5-31 8:46:30<br>
 * <b>标题:  Util_MuiQueryDAO.java</b><br>
 * <b>类描述: Util_MuiQueryDAO.java</b><br>
 * <br>
 * <p>Copyright:(c)2007</p>
 * <p>Company: ICBC</p>
 * 
 * @author 魏洪波
 * 
 * @version 1.0
 * 
 * @since 
 * 
 * @see
 */
public abstract class Util_MuiQueryDAO extends util_QueryDAO {

  /**语言标志*/
  protected String langCode = null;
  /**属性阅读器*/
	protected PropertyResourceReader propertyResourceReader = null;

  public void genParameters(Employee employee, Hashtable paras) throws TranFailException {
    langCode = "zh_CN";
    try {
      langCode = employee.getLangCode();
    }
    catch (Exception e) {}

    propertyResourceReader = new PropertyResourceReader(langCode, this.getMuiDef());

    super.genParameters(employee, paras);

  }

  /**
   *  
   * Description  : 设置私用properties的路径
   * CreationDate : 2007-5-31 8:47:20
   * @author     : 
   *   
   * @return
   */
  protected abstract String getMuiDef();

  /**
   *  
   * Description  : 取公用properties的文本
   * CreationDate : 2007-5-31 8:47:15
   * @author     : 
   *   
   * @param id
   * @return
   */
  protected String getMuiPubStr(String id) {
    return propertyResourceReader.getPublicStr(id);
  }

  /**
   *  
   * Description  : 取私用properties的文本
   * CreationDate : 2007-5-31 8:47:30
   * @author     : 
   *   
   * @param id
   * @return
   */
  protected String getMuiPriStr(String id) {
    return propertyResourceReader.getPrivateStr(id);
  }

  /**
   *  
   * Description  : 取错误码,无参数
   * CreationDate : 2007-5-31 10:06:59
   * @author     : 
   *   
   * @param errCode
   * @return
   */
  protected String getMuiErrMsg(String errCode) {
    return genMsg.getErrMsgByLang(langCode, errCode);
  }

  /**
   *  
   * Description  : 取错误码,有参数
   * CreationDate : 2007-5-31 10:07:03
   * @author     : 
   *   
   * @param errCode
   * @param paras
   * @return
   */

  protected String getMuiErrMsg(String errCode, String paras) {
    return genMsg.getErrMsgByLang(langCode, errCode, paras);
  }

}