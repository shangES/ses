package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.TranFailException;
import java.sql.*;
import java.util.*;

/**
 * <p>Title: F-CM2002资产管理系统</p>
 * <p>Description: DAO类，从客户台帐提取一个客户的数据</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ICBC HZ </p>
 * @author zheng ze zhou
 * @version 1.0
 */

public class QueryEnpCharacterDAO extends CmisDao {
  /**
   * constructor
   */
  public QueryEnpCharacterDAO(icbc.cmis.operation.CmisOperation op) {
    super(op);
  }

  /**
   * <b>功能描述: </b>此函数用于从客户台帐提取一个客户的数据<br>
   * <p>注：	</p>
   * @see
   * @param String 客户号
   * @return Hashtable 一条客户记录的全部字段
   * @throws TranFailException
   */
  public Hashtable queryEnpRecord(String cis) throws TranFailException {
    //define
    String sql = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;
    Hashtable retRecord = null;
    //query
    try {
      StringBuffer tmpSbuffer = new StringBuffer(1200);
      this.getConnection();
      tmpSbuffer.append("select ");
      tmpSbuffer.append("TA200011001,");
      tmpSbuffer.append("TA200011002,");
      tmpSbuffer.append("TA200011003,");
      tmpSbuffer.append("TA200011004,");
      tmpSbuffer.append("TA200011077,");
      tmpSbuffer.append("TA200011005,");
      tmpSbuffer.append("TA200011006,");
      tmpSbuffer.append("TA200011007,");
      tmpSbuffer.append("TA200011008,");
      tmpSbuffer.append("TA200011009,");
      tmpSbuffer.append("TA200011010,");
      tmpSbuffer.append("TA200011011,");
      tmpSbuffer.append("TA200011012,");
      tmpSbuffer.append("TA200011013,");
      tmpSbuffer.append("TA200011014,");
      tmpSbuffer.append("TA200011015,");
      tmpSbuffer.append("TA200011016,");
      tmpSbuffer.append("TA200011017,");
      tmpSbuffer.append("TA200011018,");
      tmpSbuffer.append("TA200011019,");
      tmpSbuffer.append("TA200011020,");
      tmpSbuffer.append("TA200011021,");
      tmpSbuffer.append("TA200011022,");
      tmpSbuffer.append("TA200011023,");
      tmpSbuffer.append("TA200011024,");
      tmpSbuffer.append("TA200011025,");
      tmpSbuffer.append("TA200011026,");
      tmpSbuffer.append("TA200011027,");
      tmpSbuffer.append("TA200011028,");
      tmpSbuffer.append("TA200011029,");
      tmpSbuffer.append("TA200011030,");
      tmpSbuffer.append("TA200011031,");
      tmpSbuffer.append("TA200011032,");
      tmpSbuffer.append("TA200011033,");
      tmpSbuffer.append("TA200011034,");
      tmpSbuffer.append("TA200011035,");
      tmpSbuffer.append("TA200011036,");
      tmpSbuffer.append("TA200011037,");
      tmpSbuffer.append("TA200011038,");
      tmpSbuffer.append("TA200011039,");
      tmpSbuffer.append("TA200011040,");
      tmpSbuffer.append("TA200011041,");
      tmpSbuffer.append("TA200011042,");
      tmpSbuffer.append("TA200011043,");
      tmpSbuffer.append("TA200011044,");
      tmpSbuffer.append("TA200011045,");
      tmpSbuffer.append("TA200011046,");
      tmpSbuffer.append("TA200011047,");
      tmpSbuffer.append("TA200011048,");
      tmpSbuffer.append("TA200011049,");
      tmpSbuffer.append("TA200011050,");
      tmpSbuffer.append("TA200011051,");
      tmpSbuffer.append("TA200011052,");
      tmpSbuffer.append("TA200011053,");
      tmpSbuffer.append("TA200011054,");
      tmpSbuffer.append("TA200011055,");
      tmpSbuffer.append("TA200011056,");
      tmpSbuffer.append("TA200011057,");
      tmpSbuffer.append("TA200011058,");
      tmpSbuffer.append("TA200011059,");
      tmpSbuffer.append("TA200011060,");
      tmpSbuffer.append("TA200011061,");
      tmpSbuffer.append("TA200011062,");
      tmpSbuffer.append("TA200011063,");
      tmpSbuffer.append("TA200011064,");
      tmpSbuffer.append("TA200011065,");
      tmpSbuffer.append("TA200011066,");
      tmpSbuffer.append("TA200011067,");
      tmpSbuffer.append("TA200011068,");
      tmpSbuffer.append("TA200011069,");
      tmpSbuffer.append("TA200011070,");
      tmpSbuffer.append("TA200011071,");
      tmpSbuffer.append("TA200011072,");
      tmpSbuffer.append("TA200011073,");
      tmpSbuffer.append("TA200011074,");
      tmpSbuffer.append("TA200011075,");
      tmpSbuffer.append("TA200011076,");
      tmpSbuffer.append("TA200011078,");
      tmpSbuffer.append("TA200011079,");
      tmpSbuffer.append("TA200011080,");
      tmpSbuffer.append("TA200011081,");
      tmpSbuffer.append("TA200011900,");
      tmpSbuffer.append("TA200011082,");
      tmpSbuffer.append("TA200011083,");
      tmpSbuffer.append("TA200011084,");
      tmpSbuffer.append("TA200011085,");
      tmpSbuffer.append("TA200011086,");
      tmpSbuffer.append("TA200011087,");
      tmpSbuffer.append("TA200011088,");
      tmpSbuffer.append("TA200011089,");
      tmpSbuffer.append("TA200011090,");
      tmpSbuffer.append("TA200011091,");
      tmpSbuffer.append("TA200011092,");
      tmpSbuffer.append("TA200011093,");
      tmpSbuffer.append("TA200011094,");
      tmpSbuffer.append("TA200011095 ");
      tmpSbuffer.append("from ta200011 ");
      tmpSbuffer.append("where ta200011001 = ?");
      sql = tmpSbuffer.toString();
      pStmt= conn.prepareStatement(sql);
      pStmt.setString(1,cis); //客户号
      rs = pStmt.executeQuery();
      retRecord = new Hashtable();
      if (rs.next()) {
        retRecord.put("TA200011001", rs.getString("TA200011001")==null?"":rs.getString("TA200011001"));
        retRecord.put("TA200011002", rs.getString("TA200011002")==null?"":rs.getString("TA200011002"));
        retRecord.put("TA200011003", rs.getString("TA200011003")==null?"":rs.getString("TA200011003"));
        retRecord.put("TA200011004", rs.getString("TA200011004")==null?"":rs.getString("TA200011004"));
        retRecord.put("TA200011077", rs.getString("TA200011077")==null?"":rs.getString("TA200011077"));
        retRecord.put("TA200011005", rs.getString("TA200011005")==null?"":rs.getString("TA200011005"));
        retRecord.put("TA200011006", rs.getString("TA200011006")==null?"":rs.getString("TA200011006"));
        retRecord.put("TA200011007", rs.getString("TA200011007")==null?"":rs.getString("TA200011007"));
        retRecord.put("TA200011008", rs.getString("TA200011008")==null?"":rs.getString("TA200011008"));
        retRecord.put("TA200011009", rs.getString("TA200011009")==null?"":rs.getString("TA200011009"));
        retRecord.put("TA200011010", rs.getString("TA200011010")==null?"":rs.getString("TA200011010"));
        retRecord.put("TA200011011", rs.getString("TA200011011")==null?"":rs.getString("TA200011011"));
        retRecord.put("TA200011012", rs.getString("TA200011012")==null?"":rs.getString("TA200011012"));
        retRecord.put("TA200011013", rs.getString("TA200011013")==null?"":rs.getString("TA200011013"));
        retRecord.put("TA200011014", rs.getString("TA200011014")==null?"":rs.getString("TA200011014"));
        retRecord.put("TA200011015", rs.getString("TA200011015")==null?"":rs.getString("TA200011015"));
        retRecord.put("TA200011016", rs.getString("TA200011016")==null?"":rs.getString("TA200011016"));
        retRecord.put("TA200011017", rs.getString("TA200011017")==null?"":rs.getString("TA200011017"));
        retRecord.put("TA200011018", rs.getString("TA200011018")==null?"":rs.getString("TA200011018"));
        retRecord.put("TA200011019", rs.getString("TA200011019")==null?"":rs.getString("TA200011019"));
        retRecord.put("TA200011020", rs.getString("TA200011020")==null?"":rs.getString("TA200011020"));
        retRecord.put("TA200011021", rs.getString("TA200011021")==null?"":rs.getString("TA200011021"));
        retRecord.put("TA200011022", rs.getString("TA200011022")==null?"":rs.getString("TA200011022"));
        retRecord.put("TA200011023", rs.getString("TA200011023")==null?"":rs.getString("TA200011023"));
        retRecord.put("TA200011024", rs.getString("TA200011024")==null?"":rs.getString("TA200011024"));
        retRecord.put("TA200011025", rs.getString("TA200011025")==null?"":rs.getString("TA200011025"));
        retRecord.put("TA200011026", rs.getString("TA200011026")==null?"":rs.getString("TA200011026"));
        retRecord.put("TA200011027", rs.getString("TA200011027")==null?"":rs.getString("TA200011027"));
        retRecord.put("TA200011028", rs.getString("TA200011028")==null?"":rs.getString("TA200011028"));
        retRecord.put("TA200011029", rs.getString("TA200011029")==null?"":rs.getString("TA200011029"));
        retRecord.put("TA200011030", rs.getString("TA200011030")==null?"":rs.getString("TA200011030"));
        retRecord.put("TA200011031", rs.getString("TA200011031")==null?"":rs.getString("TA200011031"));
        retRecord.put("TA200011032", rs.getString("TA200011032")==null?"":rs.getString("TA200011032"));
        retRecord.put("TA200011033", rs.getString("TA200011033")==null?"":rs.getString("TA200011033"));
        retRecord.put("TA200011034", rs.getString("TA200011034")==null?"":rs.getString("TA200011034"));
        retRecord.put("TA200011035", rs.getString("TA200011035")==null?"":rs.getString("TA200011035"));
        retRecord.put("TA200011036", rs.getString("TA200011036")==null?"":rs.getString("TA200011036"));
        retRecord.put("TA200011037", rs.getString("TA200011037")==null?"":rs.getString("TA200011037"));
        retRecord.put("TA200011038", rs.getString("TA200011038")==null?"":rs.getString("TA200011038"));
        retRecord.put("TA200011039", rs.getString("TA200011039")==null?"":rs.getString("TA200011039"));
        retRecord.put("TA200011040", rs.getString("TA200011040")==null?"":rs.getString("TA200011040"));
        retRecord.put("TA200011041", rs.getString("TA200011041")==null?"":rs.getString("TA200011041"));
        retRecord.put("TA200011042", rs.getString("TA200011042")==null?"":rs.getString("TA200011042"));
        retRecord.put("TA200011043", rs.getString("TA200011043")==null?"":rs.getString("TA200011043"));
        retRecord.put("TA200011044", rs.getString("TA200011044")==null?"":rs.getString("TA200011044"));
        retRecord.put("TA200011045", rs.getString("TA200011045")==null?"":rs.getString("TA200011045"));
        retRecord.put("TA200011046", rs.getString("TA200011046")==null?"":rs.getString("TA200011046"));
        retRecord.put("TA200011047", rs.getString("TA200011047")==null?"":rs.getString("TA200011047"));
        retRecord.put("TA200011048", rs.getString("TA200011048")==null?"":rs.getString("TA200011048"));
        retRecord.put("TA200011049", rs.getString("TA200011049")==null?"":rs.getString("TA200011049"));
        retRecord.put("TA200011050", rs.getString("TA200011050")==null?"":rs.getString("TA200011050"));
        retRecord.put("TA200011051", rs.getString("TA200011051")==null?"":rs.getString("TA200011051"));
        retRecord.put("TA200011052", rs.getString("TA200011052")==null?"":rs.getString("TA200011052"));
        retRecord.put("TA200011053", rs.getString("TA200011053")==null?"":rs.getString("TA200011053"));
        retRecord.put("TA200011054", rs.getString("TA200011054")==null?"":rs.getString("TA200011054"));
        retRecord.put("TA200011055", rs.getString("TA200011055")==null?"":rs.getString("TA200011055"));
        retRecord.put("TA200011056", rs.getString("TA200011056")==null?"":rs.getString("TA200011056"));
        retRecord.put("TA200011057", rs.getString("TA200011057")==null?"":rs.getString("TA200011057"));
        retRecord.put("TA200011058", rs.getString("TA200011058")==null?"":rs.getString("TA200011058"));
        retRecord.put("TA200011059", rs.getString("TA200011059")==null?"":rs.getString("TA200011059"));
        retRecord.put("TA200011060", rs.getString("TA200011060")==null?"":rs.getString("TA200011060"));
        retRecord.put("TA200011061", rs.getString("TA200011061")==null?"":rs.getString("TA200011061"));
        retRecord.put("TA200011062", rs.getString("TA200011062")==null?"":rs.getString("TA200011062"));
        retRecord.put("TA200011063", rs.getString("TA200011063")==null?"":rs.getString("TA200011063"));
        retRecord.put("TA200011064", rs.getString("TA200011064")==null?"":rs.getString("TA200011064"));
        retRecord.put("TA200011065", rs.getString("TA200011065")==null?"":rs.getString("TA200011065"));
        retRecord.put("TA200011066", rs.getString("TA200011066")==null?"":rs.getString("TA200011066"));
        retRecord.put("TA200011067", rs.getString("TA200011067")==null?"":rs.getString("TA200011067"));
        retRecord.put("TA200011068", rs.getString("TA200011068")==null?"":rs.getString("TA200011068"));
        retRecord.put("TA200011069", rs.getString("TA200011069")==null?"":rs.getString("TA200011069"));
        retRecord.put("TA200011070", rs.getString("TA200011070")==null?"":rs.getString("TA200011070"));
        retRecord.put("TA200011071", rs.getString("TA200011071")==null?"":rs.getString("TA200011071"));
        retRecord.put("TA200011072", rs.getString("TA200011072")==null?"":rs.getString("TA200011072"));
        retRecord.put("TA200011073", rs.getString("TA200011073")==null?"":rs.getString("TA200011073"));
        retRecord.put("TA200011074", rs.getString("TA200011074")==null?"":rs.getString("TA200011074"));
        retRecord.put("TA200011075", rs.getString("TA200011075")==null?"":rs.getString("TA200011075"));
        retRecord.put("TA200011076", rs.getString("TA200011076")==null?"":rs.getString("TA200011076"));
        retRecord.put("TA200011078", rs.getString("TA200011078")==null?"":rs.getString("TA200011078"));
        retRecord.put("TA200011079", rs.getString("TA200011079")==null?"":rs.getString("TA200011079"));
        retRecord.put("TA200011080", rs.getString("TA200011080")==null?"":rs.getString("TA200011080"));
        retRecord.put("TA200011081", rs.getString("TA200011081")==null?"":rs.getString("TA200011081"));
        retRecord.put("TA200011900", rs.getString("TA200011900")==null?"":rs.getString("TA200011900"));
        retRecord.put("TA200011082", rs.getString("TA200011082")==null?"":rs.getString("TA200011082"));
        retRecord.put("TA200011083", rs.getString("TA200011083")==null?"":rs.getString("TA200011083"));
        retRecord.put("TA200011084", rs.getString("TA200011084")==null?"":rs.getString("TA200011084"));
        retRecord.put("TA200011085", rs.getString("TA200011085")==null?"":rs.getString("TA200011085"));
        retRecord.put("TA200011086", rs.getString("TA200011086")==null?"":rs.getString("TA200011086"));
        retRecord.put("TA200011087", rs.getString("TA200011087")==null?"":rs.getString("TA200011087"));
        retRecord.put("TA200011088", rs.getString("TA200011088")==null?"":rs.getString("TA200011088"));
        retRecord.put("TA200011089", rs.getString("TA200011089")==null?"":rs.getString("TA200011089"));
        retRecord.put("TA200011090", rs.getString("TA200011090")==null?"":rs.getString("TA200011090"));
        retRecord.put("TA200011091", rs.getString("TA200011091")==null?"":rs.getString("TA200011091"));
        retRecord.put("TA200011092", rs.getString("TA200011092")==null?"":rs.getString("TA200011092"));
        retRecord.put("TA200011093", rs.getString("TA200011093")==null?"":rs.getString("TA200011093"));
        retRecord.put("TA200011094", rs.getString("TA200011094")==null?"":rs.getString("TA200011094"));
        retRecord.put("TA200011095", rs.getString("TA200011095")==null?"":rs.getString("TA200011095"));
      }
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("QueryEnpCharacterDAO001",getClass().getName()+".queryEnpRecord()",
                                  ex.toString(),"查询客户信息时报错！");
    }
    finally {
      if(rs != null) try {rs.close();} catch (Exception ex) {};
      if(pStmt != null) try {pStmt.close();} catch (Exception ex) {};
      this.closeConnection();
    }
    return retRecord;
  }

}