package icbc.cmis.util;

import java.io.IOException;
import java.util.*;
import icbc.cmis.service.CmisDao;
import icbc.cmis.operation.*;
import icbc.cmis.base.TranFailException;

public abstract class GeneralViewBaseDM extends CmisDao {
  protected DBTools database = null;

  public GeneralViewBaseDM() {
    super(new DummyOperation());
  }

  public void setDatabaseTool(CmisOperation op) throws TranFailException, java.io.IOException {
    database = new DBTools(op);
  }

  //查询指定表单的所有字段
  abstract public Hashtable query(String formID, String primaryInfo) throws TranFailException;

  //查询新增前就固定的数据
  abstract public Hashtable queryFix(String formID, String primaryInfo) throws TranFailException;

  //新增数据
  abstract public int insert(String formID, Hashtable dataCollection, String primaryInfo) throws TranFailException;

  //修改数据
  abstract public int modify(String formID, Hashtable dataCollection, Hashtable primaryDataCollection, String primaryInfo) throws TranFailException;

  //删除数据
  abstract public int delete(String formID, Hashtable primaryDataCollection, String primaryInfo) throws TranFailException;

  //新增数据前检查
  //returns String[0] -- success"0" failed"1"
  //        String[1] -- error code
  //        String[2] -- error variable
  public String[] canInsert(String formID, Hashtable dataCollection, String primaryInfo) throws TranFailException {
    return new String[]{"0","",""};
  }

  //修改数据前检查
  public String[] canModify(String formID, Hashtable dataCollection, Hashtable primaryDataCollection, String primaryInfo) throws TranFailException {
    return new String[]{"0","",""};
  }

  //删除数据前检查
  public String[] canDelete(String formID, Hashtable primaryDataCollection, String primaryInfo) throws TranFailException {
    return new String[]{"0","",""};
  }

  //other functions

  //合并数据集合
  public Hashtable mergeData(Hashtable src, String tableID, Hashtable data) {
    Hashtable table = (Hashtable)src.get(tableID);
    if (table == null) {
      src.put(tableID, data);
      return src;
    }
    else {
      table.putAll(data);
      return src;
    }
  }

  //取得返回页面的参数
  //returns String[0] okTitle
  //        String[1] okMsg
  //        String[2] okReturn    
  abstract public String[] getInsertReturn(Hashtable data, String primaryInfo);

  abstract public String[] getModifyReturn(Hashtable data, String primaryInfo);

  abstract public String[] getDeleteReturn(Hashtable data, String primaryInfo);

 //add by zap
 public void getDueData(Hashtable htData,String maxjjcode,String sumDueMoney,String isJieju){
 }
 public String sumDueMoney(String sEnpCode,String contractID)throws Exception{
 
 return null;
 }
 //end by zap
}
