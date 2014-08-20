package icbc.cmis.util;

import icbc.cmis.operation.*;
import icbc.cmis.base.TranFailException;
import icbc.missign.Employee;
import java.util.Vector;
import java.util.Hashtable;


public class ChooseEnp2 extends CmisOperation {
  private static final int PAGE_LINES = 15;

  public ChooseEnp2() {
  }
  public void execute() throws TranFailException {
    try{
      String flag = this.getStringAt("flag");
      String page = this.getStringAt("page");
      //判断标志，根据标志转向不同处理
      if(flag.equals("newQuery")) {
        newQuery();
      } else if(flag.equals("query")) {
        query(page);
      } else if(flag.equals("done")) {
        clear();
      }
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("cmisUTIL800","",ex.getMessage(),"数据提取失败！" + ex.getMessage());
    }
  }

  public void newQuery() throws TranFailException {
    //新查询，取出查询参数，调用数据查询模块得到结果，将结果放入SESSION
    //返回结果集的第一页
    try {
      //取参数
      Employee employee = (Employee)this.getSessionData("Employee");
      String TA200011001 = this.getStringAt("TA200011001");
      String TA200011003 = this.getStringAt("TA200011003");
      String TA200011005 = this.getStringAt("TA200011005");
      String TA200011010 = this.getStringAt("TA200011010");
      String TA200011011 = this.getStringAt("TA200011011");
      String TA200011012 = this.getStringAt("TA200011012");
      String TA200011014 = this.getStringAt("TA200011014");
      String TA200011016 = this.getStringAt("TA200011016");
      String TA200011031 = this.getStringAt("TA200011031");
      String queryType = this.getStringAt("queryType");

      Hashtable paras = new Hashtable();
      Vector pnames = this.getElementNames();
      for (int i = 0; i < pnames.size(); i++) {
        String tname = (String)pnames.get(i);
        try {
          paras.put(tname,this.getStringAt(tname));
        }
        catch (Exception ex) {
        }
      }

      //调用数据查询模块得到结果，将结果放入SESSION
      ChooseEnpDAO dao = new ChooseEnpDAO(this);
      Vector datas = dao.getEnterprises(queryType,employee,TA200011001,TA200011003,TA200011005,TA200011010,TA200011011,TA200011012,TA200011014,TA200011016,TA200011031,paras);
      this.setFieldValue("enterprise3GB2U",datas);
      this.setOperationDataToSession();
      //返回结果集的第一页
      query("1");
    } catch(TranFailException ex) {
      throw ex;
    } catch(Exception ex) {
      throw new TranFailException("cmisUTIL801","",ex.getMessage(),ex.getMessage());
    }

  }

  public void query(String spage) throws TranFailException{
    //从SESSION中取回结果集，根据页号返回结果
    try {
      //从SESSION中取回结果集
      Vector datas = (Vector)this.getObjectAt("enterprise3GB2U");
      String warning = (String)datas.get(0);
      //计算应取回的记录起始和终止编号
      int page = Integer.valueOf(spage).intValue();
      int begin = (page - 1) * PAGE_LINES + 1;
      int end   = (page) * PAGE_LINES;
      //取记录集大小
      int last = datas.size() - 1;
      if(datas.isEmpty()) {
        last = 0;
      }
      int pages = (last - 1)/ PAGE_LINES + 1;
      //返回结果
      String ret = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
      ret += ("<datas page='" + page + "' pages='" + pages + "' lines='" + last + "' warning='" + warning + "'>");
      for(int i = begin; i <= end && i <= last; i++) {
        String[] row = (String[])datas.get(i);
        ret += ("<en o='" + i + "' c='" + row[0] + "' n='" + row[1] + "' j='" + row[2] + "' d='" + row[3] + "' />");
      }
      ret += "</datas>";
      this.setOperationDataToSession();
      this.setReplyPage("DirectOutput" + ret);
    } catch(Exception ex) {
      throw new TranFailException("cmisUTIL802","",ex.getMessage(),"数据提取失败！" + ex.getMessage());
    }

  }

  public void clear() {
    //清除SESSION中的相关数据
    try {
      this.removeDataField("enterprise3GB2U");
      String ret = "<?xml version=\"1.0\" encoding=\"GBK\"?><ok />";
      this.setOperationDataToSession();
      this.setReplyPage("DirectOutput" + ret);
    }
    catch (Exception ex) {};
  }
}