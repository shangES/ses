package icbc.cmis.util;

import java.util.*;
import icbc.cmis.operation.*;
import icbc.cmis.base.*;

/**
 * <p>Title: F-CM2002资产管理系统</p>
 * <p>Description: 查询客户的年初信用等级等要素</p>
 * <p>create for 资金集中配置项目</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ICBC HZ </p>
 * @author zheng ze zhou
 * @version 1.0
 * modify by zheng ze zhou
 * modify date 2004-7-16
 * modify reason:为了方便框架的调用，去掉了继承CmisOperation的方式
 */

public class QueryEnpCharacter  {

  public QueryEnpCharacter() {

  }
  /**
   * <p>查询客户的年初信用等级(如果年初信用等级为未评级则取比照信用等级)</p>
   * <p>年初信用等级 TA200011040；比照信用等级 TA200011070 </p>
   * <p>写成静态方法便于调用</p>
   * <p>Example:
   * @param String enpcode 客户代码
   * @return String creditLevel 信用等级代码
   * @throws TranFailException
   */
  public static String getCreditLevel(String enpcode) throws icbc.cmis.base.TranFailException{
    try {
      //define
      String retlevel = "?";
      Hashtable tmph;
      QueryEnpCharacterDAO dao;
      //values : 赋值 dao里已经控制了不会赋null
      dao = new QueryEnpCharacterDAO(new icbc.cmis.operation.DummyOperation());
      tmph = dao.queryEnpRecord(enpcode);
      retlevel = (String)tmph.get("TA200011040"); //企业年初信用等级
      if (retlevel!=null&&retlevel.equals("00")) { //未评级
        retlevel = (String)tmph.get("TA200011070"); //比照信用等级
      }
      if (retlevel.equals("")||retlevel.equals("00")) { //比照信用等级为空或00时，就送90 Date 2004-9-3
        retlevel = "90";
      }
      return retlevel;
    } catch (TranFailException e) {
      throw e;
    } catch (Exception e) {
      //handler your exception
      throw new TranFailException("QueryEnpCharacter001", "icbc.cmis.util.QueryEnpCharacter.getCreditLevel()",
                                   e.getMessage(),"无法获得企业信用等级");
    }
  }
}
