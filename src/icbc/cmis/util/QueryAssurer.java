package icbc.cmis.util;
import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class QueryAssurer extends QueryNormalEnp {

  public QueryAssurer() {
  }

  public String getWhere(Connection conn,Employee employee, Hashtable paras) {
     String where = "";

     String area = (String)paras.get("assurearea");
     String assuretype = (String)paras.get("assuretype");
     String enpcode = (String)paras.get("enpcode");

     String bankflag = "4";
     if(paras.containsKey("bankFlag"))
       bankflag = (String)paras.get("bankFlag");

     if(bankflag.equals("4"))
       //当所选地区是支行，则所选客户应该在用户所选的地区中
       where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')";
     else if(bankflag.equals("3")||bankflag.equals("2"))
       //如果所选地区是二级行或准一级行，所选客户应该在所选地区的下级行
       where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))";
     //如果所选地区是一级行，所选客户应该在所选地区的所有支行
     else if(bankflag.equals("1"))
       where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))";

     //如果所选地区是总行的，不作地区控制


     /*
       如果是为表外的申请或合同选保证人，则要求信用等级为BBB级以上(含)的(如果信用等级为未评级的则取
       比照信用等级)，且在客户大事记表中无大事的、在黑名单表中无记录的、在客户逃废债表中无记录的
     */
     if(!assuretype.equals("credit")){
        where += " and ta200011001 not in (select ta200016001 from ta200016 ) and ta200011001 not in (select ta200017001 from ta200017)";
        where += " and ta200011001 not in (select ta200018001 from ta200018) ";
        /*and ((ta200011040 <= '40' and ta200011040>'00') ";
        where += " or (ta200011040 = '00' and ta200011070 <='40' and ta200011070 >'00'))";*/
     }

      String hascreditrelation = "";

      if(paras.containsKey("hasCreditRelation"))
         hascreditrelation = (String)paras.get("hasCreditRelation");


      if(hascreditrelation.equals("yes"))
          where += " and ta200011059 in ('1','5') ";
      else
        //客户状态为新增或建立信贷关系，且输入标志是完成的
        where += " and ta200011059 in ('1','2','5')";

      where += " and ta200011083='1' and ta200011001 <> '"+enpcode+"' ";

      if(where.length()>0)
        where = where.substring(4);
      return where;
  }
}