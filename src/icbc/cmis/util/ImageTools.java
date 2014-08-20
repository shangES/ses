/*
 * 创建日期 2006-7-26
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.util;
import icbc.cmis.base.*;
import icbc.missign.*;

/**
 * @author ZJFH-yanb
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class ImageTools {
  public String getImageOutEmp(CMisSessionMgr sm) {
    String ret = "";
    try {
      Employee employee = (Employee)sm.getSessionData("Employee");
      String outEmp = employee.getOutsideEmpCode();
      outEmp = outEmp.substring(4);
      int test = Integer.parseInt(outEmp);
      ret = outEmp;
    }
    catch (Exception e) {
      ret = "00000";
    }
    return ret;
  }

}
