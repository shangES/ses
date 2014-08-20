package icbc.cmis.base;

import java.util.Hashtable;

import java.io.*;
import org.w3c.dom.*;
import javax.servlet.http.*;
import icbc.cmis.service.*;
import icbc.cmis.service.channel.CM2002Service;
import icbc.cmis.util.DSRCommService;
import icbc.cmis.operation.*;
import java.util.*;
import java.sql.*;
/**
	 * Insert the method's description here.
	 * Creation date: (2001-12-31 15:35:54)
	 * @param userName java.lang.String
	 * @param userPass java.lang.String
	 * 
	 * update by chenjun 20031009
	 * 修改setWorkDate()，新增票据的工作日期
	 * 
	 * 
	 */
public class CmisConstance {
  private static Hashtable errorMsgTable = null;
  public static JDBCConnectionPool JDBCPool = null;
  private static Hashtable settingsTable = new Hashtable();
  private static MQConnectionPoolService MQPool = null;

  private static Hashtable dict_tables = new Hashtable();
  private static Hashtable dbUserTable = new Hashtable();
  //add by chenj 20031119
  private static Hashtable dbUserPool = new Hashtable();
  private static Hashtable userAndPass = new Hashtable();
  private static XMLReader xmlReader = null;
  private static Hashtable opPool = new Hashtable();
  public static Thread cmisSystemInfo = null;
  public static DA200251031Bean da200251031 = null;
  public static icbc.cmis.mgr.MonitorClientInterface monitor = null;
  public static int onLineNums = 0;
  public static int logonNums = 0;
  public static boolean isServerStarted = false;
  public static DA200251012Bean da200251012 = null;
  public static DA220091006Bean da220091006 = null;
  private static String cmisWorkDate = null;

  public static Hashtable limitedOpList = null;
  //public static Hashtable opList = new Hashtable();
  private static Integer limitedOpSynLock = new Integer(0);
  public static String appServerID = null;
  public static String appCode = null;
  public static Hashtable funDbMap = null;

  //add by yanbo 20040415
  private static Hashtable sysCodeWithEntry = new Hashtable();
  private static boolean switchAppEnabled = false;
  private static String localAppEntry = null;
  //end 20040415

  //add by zap 20060607
  //存放所有的服务参数信息
  public static ArrayList controledList = null;
  //存放所有需要监控的opName及opActionName
  public static ArrayList opDataList = null;
  //end zap 20060607

  public static Hashtable controledFunc = null; //add by yanbo 20060531
  public static boolean isFuncControled = false; //add by yanbo 20060706
  
  public static Vector allZone = null; //add by chenj 20070417
  public static Vector allLang = null; //add by chenj 20070417
  private static Hashtable ml_dict_tables = new Hashtable();//多语言字典表 add by chenj 20070417
  
  /**
   * CmisConstance constructor comment.
   */
  public CmisConstance() {
    super();
  }
  /**
   * Insert the method's description here.
   * Creation date: (2002-1-31 14:58:02)
   * @param op icbc.cmis.operation.CmisOperation
   */
  public static synchronized void addCmisOperation(String opName, Class c) {

    String opName1 = opName.trim();
    Object o = opPool.get(opName1);
    if (o == null) {
      opPool.put(opName1, c);
    }
  }
  public static void addDBUserInfo(String userName, String userPass) {
    dbUserTable.put(userName.toLowerCase(), userPass);
  }

  public static String getDBPoolName(String functionCode) {
    String tmp = null;
    try {
      tmp = (String)funDbMap.get(functionCode.trim());
    }
    catch (Exception e) {
      tmp = null;
    }
    return tmp;
  }

  public static String getDBUserPass(String userName, String verifyStr) {
    return (String)dbUserTable.get((userName.trim() + "." + verifyStr.trim()).toLowerCase());
  }

  public static String getDBPool(String userName) {
    return (String)dbUserPool.get(userName.trim());
  }

  /**
  * Insert the method's description here.
  * Creation date: (2002-1-6 23:34:21)
  * @return java.lang.String
  * @param errorCode java.lang.String
  */
  /**
   * Insert the method's description here.
   * Creation date: (2002-4-15 15:24:25)
   * @return java.util.Enumeration
   */
  public static Enumeration getDBUsers() {
    return userAndPass.keys();
  }
  public static Hashtable getDictParam() {
    return dict_tables;
  }
  //chenj20070423
  public static Hashtable getDictParam(String lang_code) {
	
	try{
		if(lang_code!=null&&lang_code!="")
			return (Hashtable)ml_dict_tables.get(lang_code);
		else
			return (Hashtable)ml_dict_tables.get("zh_CN");
	}
	catch (Exception e) {
		return getDictParam();
	}
  }
  //chenj
  
  public static String getDispErrorMsg(String errorCode) {
    try {
      errorCode = errorCode.trim();
      String msg = (String)errorMsgTable.get(errorCode);
      /*	if(msg == null || msg.trim().length() == 0){
      		msg = (String)((DictBean)dict_tables.get("errTable")).get(errorCode);
      	}
      */
      if (msg == null || msg.trim().length() == 0) {
        msg = "错误码: " + errorCode + ",未知错误";
      }
      return msg;
    }
    catch (Exception e) {
      return "错误码: " + errorCode + ",未知错误";
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (2001-12-27 17:34:34)
   * @return com.icbc.cmis.service.MQConnectionPoolService
   */
  public static MQConnectionPoolService getMQPool() {
    return MQPool;
  }
  /**
   * Insert the method's description here.
   * Creation date: (2002-1-31 14:56:22)
   * @return icbc.cmis.operation.CmisOperation
   * @param opName java.lang.String
   */
  public static Class getOperationClass(String opName) throws Exception {
    return (Class)opPool.get(opName.trim());
  }
  /**
   * Insert the method's description here.
   * Creation date: (2001-12-26 15:59:05)
   * @return java.util.Hashtable
   */
  public static Hashtable getParameterSettings() {
    return settingsTable;
  }
  public static String getPassVerify(String userName) {
    return (String)userAndPass.get(userName.toLowerCase());
  } /**
              	 * Insert the method's description here.
              	 * Creation date: (2002-1-4 11:30:48)
              	 * @return com.icbc.cmis.base.XMLReader
              	 */
  /**
   * Insert the method's description here.
   * Creation date: (2002-7-31 11:12:11)
   * @return java.lang.String
   * @param format java.lang.String
   */
  public static String getSystemData(String format) {
    java.text.SimpleDateFormat dateFmt1 = new java.text.SimpleDateFormat(format);
    java.util.Calendar c1 = java.util.Calendar.getInstance();
    return dateFmt1.format(c1.getTime());
  }
  /**
   * Insert the method's description here.
   * Creation date: (2002-7-31 11:12:11)
   * @return java.lang.String
   * @param format java.lang.String
   */
  public static String getWorkDate(String format) {
    String tmpStr = null;
    tmpStr = CmisConstance.cmisWorkDate;
    java.text.SimpleDateFormat dateFmt = new java.text.SimpleDateFormat("HHmmss");
    java.util.Calendar c = java.util.Calendar.getInstance();
    tmpStr = tmpStr.trim() + dateFmt.format(c.getTime());

    String tmpStr1 = "";
    int year = Integer.valueOf(tmpStr.substring(0, 4)).intValue();
    int month = Integer.valueOf(tmpStr.substring(4, 6)).intValue();
    int date = Integer.valueOf(tmpStr.substring(6, 8)).intValue();
    int hour = Integer.valueOf(tmpStr.substring(8, 10)).intValue();
    int minute = Integer.valueOf(tmpStr.substring(10, 12)).intValue();
    int second = Integer.valueOf(tmpStr.substring(12, 14)).intValue();
    java.text.SimpleDateFormat dateFmt1 = new java.text.SimpleDateFormat(format);
    java.util.Calendar c1 = java.util.Calendar.getInstance();
    c1.set(year, month - 1, date, hour, minute, second);
    tmpStr1 = dateFmt1.format(c1.getTime());
    return tmpStr1;
  }

  public static XMLReader getXMLReader() {
    return xmlReader;
  } /**
              	 * Insert the method's description here.
              	 * Creation date: (2002-1-4 11:28:48)
              	 */
  /**
   * Insert the method's description here.
   * Creation date: (2001-12-26 17:05:53)
   * @param filePath java.lang.String
   */
  public static void initCTEFile(String filePath) throws Exception {
    try {
      opPool = null;
      opPool = new Hashtable();
    }
    catch (Exception ex) {}

    XMLReader xmlReader = new XMLReader();
    xmlReader.loadXMLFile(filePath);
    Node node = xmlReader.getNode("CTESettings");
    NodeList list = node.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node tmpNode = list.item(i);
      String nodeName = tmpNode.getNodeName();

      if (nodeName.trim().equals("JDBCConnectionPool")) {

        manageJDBCNode(tmpNode);

      }
      else if (nodeName.trim().equals("DBUserVerifyPool")) {
        manageDBVerity(tmpNode);
      }
      else if (nodeName.trim().equals("ECCMQConnectionPool")) {

        manageMQ(tmpNode);

      }
      else if (nodeName.trim().equals("Monitor")) {
        manageMonitor(tmpNode);
      }
      else if (nodeName.trim().equals("AppEntryPool")) {
        manageAppEntry(tmpNode);
      }
      ///////////////////////////////////////////////////////////
      //
      //	修改原因：增加CM2002对外服务接口
      //	修改人：YangGuangRun
      //	修改时间：2004-10-22
      ///////////////////////////////////////////////////////////
      else if (nodeName.trim().equals("CM2002Service")) {
        initCM2002ChannelService(tmpNode);
        ///////////////////////////////////////////////////////////
      }
      else {

        if (isElementNode(tmpNode)) {

          NamedNodeMap map = tmpNode.getAttributes();

          CmisConstance.getParameterSettings().put(map.getNamedItem("id").getNodeValue(), map.getNamedItem("value").getNodeValue());

        }
      }
    }

    //		String userId =
    //			(String) CmisConstance.getParameterSettings().get("dbUserName");
    //		String userPass =
    //			(String) CmisConstance.getParameterSettings().get("dbUserPass");
    //		addDBUserInfo(userId + "." + userPass, userPass);
    String userId = (String)CmisConstance.getParameterSettings().get("dbUserInName");
    String userPass = (String)CmisConstance.getParameterSettings().get("dbUserInPass");
    addDBUserInfo(userId + "." + userPass, userPass);

  }
  public static void initializationFmt() throws Exception {
    try {
      String mqEnable = (String)CmisConstance.getParameterSettings().get("enableMQ");
      if (mqEnable == null)
        mqEnable = "";
      if (mqEnable.trim().equalsIgnoreCase("true")) {
        xmlReader = new XMLReader();
        String fmtDefFile = (String)CmisConstance.getParameterSettings().get("formatDefFileName");

        String rootPath = (String)CmisConstance.getParameterSettings().get("fileRootPath");
        if (rootPath.endsWith(System.getProperty("file.separator")))
          fmtDefFile = rootPath + fmtDefFile;
        else
          fmtDefFile = rootPath + System.getProperty("file.separator") + fmtDefFile;

        xmlReader.loadXMLFile(fmtDefFile);
      }
      else if (mqEnable.trim().equalsIgnoreCase("false")) {
        DSRCommService srv = new DSRCommService();
        srv.initialization();
      }
      else if (mqEnable.trim().equalsIgnoreCase("tf")) {
        xmlReader = new XMLReader();
        String fmtDefFile = (String)CmisConstance.getParameterSettings().get("formatDefFileName");

        String rootPath = (String)CmisConstance.getParameterSettings().get("fileRootPath");
        if (rootPath.endsWith(System.getProperty("file.separator")))
          fmtDefFile = rootPath + fmtDefFile;
        else
          fmtDefFile = rootPath + System.getProperty("file.separator") + fmtDefFile;

        xmlReader.loadXMLFile(fmtDefFile);
        DSRCommService srv = new DSRCommService();
        srv.initialization();
      }
      else {
        throw new Exception("在icbccmis.xml中的配置项[enableMQ]的值无效");
      }

    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }

  }
  public static void initializeErrorMessageTable() throws Exception {

    errorMsgTable = new java.util.Hashtable();

    String line = "";
    int index = 0;
    String fileName = (String)CmisConstance.getParameterSettings().get("errTableFileName");

    String rootPath = (String)CmisConstance.getParameterSettings().get("fileRootPath");
    if (rootPath.endsWith(System.getProperty("file.separator")))
      fileName = rootPath + fileName;
    else
      fileName = rootPath + System.getProperty("file.separator") + fileName;

    BufferedReader in = new BufferedReader(new FileReader(fileName));

    while ((line = in.readLine()) != null) {
      if (line.startsWith("#"))
        continue;
      else if (line.trim().length() == 0)
        continue;

      index = line.indexOf("=");
      if (index > 0 && index < line.length() - 1) {

        errorMsgTable.put(line.substring(0, index), line.substring(index + 1));

      }
      if (index == line.length() - 1)
        errorMsgTable.put(line.substring(0, index), "");
    }
    in.close();

  }

  public static void initializeLimitedOpList() throws Exception {

    limitedOpList = new java.util.Hashtable();
    String sql = "select transename from parellel_control";
    SqlTool sqlTool = null;
    try {
      sqlTool = new SqlTool(new DummyOperation());
      sqlTool.getConn("missign");

      ResultSet rs = sqlTool.executeQuery(sql);
      while (rs.next()) {
        limitedOpList.put(rs.getString(1), "");
      }
      sqlTool.closeconn();
    }
    catch (Exception e) {
      try {
        if (sqlTool != null)
          sqlTool.closeconn();
      }
      catch (Exception ee) {}
      throw new TranFailException("icbc.cmis.base.CmisConstance", "CmisConstance.initializeLimitedOpList", "初始化受限制Op列表失败", e.getMessage());
    }
  }

  //add by yanbo 20060531
  public static void initializeControledFunc() throws Exception {

    controledFunc = new java.util.Hashtable();
    String sql2 = "select para_max from cmis3.mag_comm_parameters where para_id='308' ";
    String sql =
      "select func_code,func_address from mag_function where func_sub_node = '0' and func_address not like '%.jsp%' and func_address not like '%.html%' and func_address not like '%.htm%'";
    SqlTool sqlTool = null;
    try {
      sqlTool = new SqlTool(new DummyOperation());
      sqlTool.getConn("missign");

      ResultSet cond = sqlTool.executeQuery(sql2);
      if (cond.next()) {
        int isCtl = cond.getInt(1);
        if (isCtl == 1) {
          ResultSet rs = sqlTool.executeQuery(sql);
          while (rs.next()) {
            isFuncControled = true;
            String rawFunc = rs.getString(2);
            Hashtable item = new Hashtable();
            String operationName = "";
            int pos = rawFunc.indexOf("?");
            if (pos > 0) {
              rawFunc = rawFunc.substring(pos + 1);
            }
            else {
              int opNamePos = rawFunc.indexOf("&");
              if (opNamePos > 0) {
                operationName = rawFunc.substring(0, opNamePos);
                rawFunc = rawFunc.substring(opNamePos + 1);
              }
              else {
                //item.put("operationName", rawFunc);
                Vector vitem = new Vector(1);
                vitem.add(item);
                controledFunc.put(rawFunc, vitem);
                continue;
              }
            }

            StringTokenizer tokens = new StringTokenizer(rawFunc, "&");
            while (tokens.hasMoreTokens()) {
              String token = tokens.nextToken();
              int tokenPos = token.indexOf("=");
              if (tokenPos > 0) {
                String tokenName = token.substring(0, tokenPos);
                String tokenValue = token.substring(tokenPos + 1);
                if (tokenName.equals("operationName")) {
                  operationName = tokenValue;
                }
                else
                  item.put(tokenName, tokenValue);
              }
            }
            Vector vitem = (Vector)controledFunc.get(operationName);
            if (vitem == null) {
              vitem = new Vector();
              controledFunc.put(operationName, vitem);
            }
            vitem.add(item);
          }
        }
        else
          isFuncControled = false;
      }
      else {
        isFuncControled = false;
      }
      sqlTool.closeconn();
    }
    catch (Exception e) {
      try {
        if (sqlTool != null)
          sqlTool.closeconn();
      }
      catch (Exception ee) {}
      throw new TranFailException("icbc.cmis.base.CmisConstance", "CmisConstance.initializeControledFunc", "初始化监控func列表失败", e.getMessage());
    }
  }

  public static Hashtable getControledFunc() {
    return controledFunc;
  }

  public static void initializePassVerifyTable() throws Exception {

    userAndPass = new java.util.Hashtable();

    String line = "";
    int index = 0;
    String fileName = (String)getParameterSettings().get("dbUserInfoFileName");

    String rootPath = (String)CmisConstance.getParameterSettings().get("fileRootPath");
    if (rootPath.endsWith(System.getProperty("file.separator")))
      fileName = rootPath + fileName;
    else
      fileName = rootPath + System.getProperty("file.separator") + fileName;

    BufferedReader in = new BufferedReader(new FileReader(fileName));

    while ((line = in.readLine()) != null) {
      if (line.startsWith("#"))
        continue;
      else if (line.trim().length() == 0)
        continue;
      line = line.trim();
      index = line.indexOf("=");
      if (index > 0 && index < line.length() - 1) {
        String dbName = line.substring(0, index);
        String dbVerify = line.substring(index + 1, line.length());
        userAndPass.put(dbName.toLowerCase(), dbVerify);
      }
      if (index == line.length() - 1)
        continue;
    }
    in.close();

  }
  /**
   * Insert the method's description here.
   * Creation date: (2001-12-27 18:19:30)
   * @return boolean
   * @param aNode org.w3c.dom.Node
   */
  private static boolean isElementNode(Node aNode) {
    if (aNode.getNodeType() == Node.ELEMENT_NODE)
      return true;
    else
      return false;

  }
  /**
   * Insert the method's description here.
   * Creation date: (2002-7-31 11:33:33)
   * @return boolean
   * @param dateStr java.lang.String
   */
  public static boolean isNormalDate(String dateStr) {
    try {
      if (dateStr == null || dateStr.trim().length() != 8)
        return false;
      dateStr = dateStr.trim();
      int year = Integer.valueOf(dateStr.substring(0, 4)).intValue();
      int month = Integer.valueOf(dateStr.substring(4, 6)).intValue();
      int date = Integer.valueOf(dateStr.substring(6, 8)).intValue();

      if (year <= 1900)
        return false;
      if (month <= 0 || month > 12)
        return false;
      if (date <= 0 || date > 31)
        return false;

      if (month == 4 || month == 6 || month == 9 || month == 11) {
        if (date > 30)
          return false;
      }
      if (month == 2) {
        if (year % 4 > 0 || (year % 100 == 0 && year % 400 > 0)) {
          if (date > 28)
            return false;
        }
        else {
          if (date > 29)
            return false;
        }
      }
    }
    catch (Exception e) {
      return false;
    }
    return true;
  }
  /**
   * Insert the method's description here.
   * Creation date: (2002-10-10 9:49:58)
   * @return boolean
   * @param opName java.lang.String
   */
  public static boolean isValidRuningStatus(String opName, HttpServletRequest req) throws TranFailException {
    SqlTool sqlTool = null;
    try {
      //开关正常开启，且被控清单非空
      String isEnable = (String)CmisConstance.getParameterSettings().get("enableLimitedOpCtl");
      if (isEnable == null || !isEnable.equalsIgnoreCase("true"))
        return true;
      if (limitedOpList == null || limitedOpList.isEmpty())
        return true;

      //update by yanbo 20060302, 整合了cmis、bms的op名和动作的控制
      //bms Op
      String isOpSceneCtl = (String)CmisConstance.getParameterSettings().get("enableLimitedOpSceneCtl");
      if (isOpSceneCtl == null || !isOpSceneCtl.equalsIgnoreCase("true"))
        isOpSceneCtl = "false";
      //标示scene的tag名称
      String opscenetag = (String)CmisConstance.getParameterSettings().get("enableLimitedOpSceneTag");
      if (opscenetag == null)
        opscenetag = "scene";
      String scene = (String)req.getParameter(opscenetag);
      if (scene == null) {
        scene = "";
      }
      String opNameBms = opName + scene;
      //cmis Op
      String opAction = (String)req.getParameter("opAction");
      if (opAction == null) {
        opAction = "";
      }
      String opNameCmis = opName + opAction;

      //确认使用哪个opName
      if (!limitedOpList.containsKey(opName) && !limitedOpList.containsKey(opNameBms) && !limitedOpList.containsKey(opNameCmis))
        return true;

      if (limitedOpList.containsKey(opNameCmis)) {
        opName = opNameCmis;
      }
      else if (limitedOpList.containsKey(opNameBms)) {
        opName = opNameBms;
      }
      //end update 20060302	
      int retryTimes = Integer.parseInt((String)CmisConstance.getParameterSettings().get("limitedOpRetryTimes"));
      int interval = Integer.parseInt((String)CmisConstance.getParameterSettings().get("limitedOpRetryInterval"));

      sqlTool = new SqlTool(new DummyOperation());
      sqlTool.getConn("missign");
      Vector params = new Vector(1);
      params.add(opName);
      synchronized (limitedOpSynLock) {
        CallableStatement cs = sqlTool.callFunc("pack_parellel_control.func_check_session", params, 0);
        if (cs.getInt(1) == 0)
          return true;
      }

      //			int times = 0;
      //			while (times < 0) {
      //			//while (times < retryTimes) {
      //				try {
      //					Thread.currentThread().sleep(interval);
      //				} catch (Exception e) {
      //				}
      //	
      //				times++;
      //
      //				synchronized (limitedOpSynLock) {
      //					CallableStatement cs = sqlTool.callFunc("pack_parellel_control.func_check_session",params,0);
      //					if(cs.getInt(1)==0)
      //					return true;
      //				}
      //
      //			}
    }
    catch (Exception ee) {
      System.out.println("Waring:CmisConstance.isValidRuningStatus(String operation)[" + opName + "] exception,Exception is:" + ee.toString());
      return true; //ignore
    }
    finally {
      try {
        if (sqlTool != null)
          sqlTool.closeconn();
      }
      catch (Exception e) {}
    }
    throw new TranFailException(
      "xdtz22131",
      "CmisConstance.isValidRuningStatus(String opName)",
      "为保证系统能够正常运行，该模块启用了并发控制，现并发使用人数已达到最大值，请稍后重试！",
      "为保证系统能够正常运行，该模块启用了并发控制，现并发使用人数已达到最大值，请稍后重试！");

  }

  /**
  	 * Insert the method's description here.
  	 * Creation date: (2002-10-10 9:49:58)
  	 * @return boolean
  	 * @param opName java.lang.String
  	 */
  //for ctp 
  public static boolean isValidRuningStatus(String opName, KeyedDataCollection opData) throws TranFailException {
    SqlTool sqlTool = null;
    try {
      //开关正常开启，且被控清单非空
      String isEnable = (String)CmisConstance.getParameterSettings().get("enableLimitedOpCtl");
      if (isEnable == null || !isEnable.equalsIgnoreCase("true"))
        return true;
      if (limitedOpList == null || limitedOpList.isEmpty())
        return true;
      //update by yanbo 20060302, 整合了cmis、bms的op名和动作的控制
      //bms Op
      String isOpSceneCtl = (String)CmisConstance.getParameterSettings().get("enableLimitedOpSceneCtl");
      if (isOpSceneCtl == null || !isOpSceneCtl.equalsIgnoreCase("true"))
        isOpSceneCtl = "false";
      //标示scene的tag名称
      String opscenetag = (String)CmisConstance.getParameterSettings().get("enableLimitedOpSceneTag");
      if (opscenetag == null)
        opscenetag = "scene";
      String scene = null;
      try {
        scene = (String)opData.getValueAt(opscenetag);
      }
      catch (Exception e) {}
      if (scene == null) {
        scene = "";
      }
      String opNameBms = opName + scene;
      //cmis Op
      String opAction = null;
      try {
        opAction = (String)opData.getValueAt("opAction");
      }
      catch (Exception e) {}
      if (opAction == null) {
        opAction = "";
      }
      String opNameCmis = opName + opAction;
      //end update 20060302

      if (!limitedOpList.containsKey(opName) && !limitedOpList.containsKey(opNameBms) && !limitedOpList.containsKey(opNameCmis))
        return true;

      if (limitedOpList.containsKey(opNameCmis)) {
        opName = opNameCmis;
      }
      else if (limitedOpList.containsKey(opNameBms)) {
        opName = opNameBms;
      }

      int retryTimes = Integer.parseInt((String)CmisConstance.getParameterSettings().get("limitedOpRetryTimes"));
      int interval = Integer.parseInt((String)CmisConstance.getParameterSettings().get("limitedOpRetryInterval"));

      sqlTool = new SqlTool(new DummyOperation());
      sqlTool.getConn("missign");
      Vector params = new Vector(1);
      params.add(opName);
      synchronized (limitedOpSynLock) {
        CallableStatement cs = sqlTool.callFunc("pack_parellel_control.func_check_session", params, 0);
        if (cs.getInt(1) == 0)
          return true;
      }

      int times = 0;
      while (times < 0) {
        //while (times < retryTimes) {
        try {
          Thread.currentThread().sleep(interval);
        }
        catch (Exception e) {}

        times++;

        synchronized (limitedOpSynLock) {
          CallableStatement cs = sqlTool.callFunc("pack_parellel_control.func_check_session", params, 0);
          if (cs.getInt(1) == 0)
            return true;
        }

      }
    }
    catch (Exception ee) {
      System.out.println("Waring:CmisConstance.isValidRuningStatus(String operation)[" + opName + "] exception,Exception is:" + ee.toString());
      return true; //ignore
    }
    finally {
      try {
        if (sqlTool != null)
          sqlTool.closeconn();
      }
      catch (Exception e) {}
    }
    throw new TranFailException(
      "xdtz22131",
      "CmisConstance.isValidRuningStatus(String opName)",
      "为保证系统能够正常运行，该模块启用了并发控制，现并发使用人数已达到最大值，请稍后重试！",
      "为保证系统能够正常运行，该模块启用了并发控制，现并发使用人数已达到最大值，请稍后重试！");

  }

  /**
   * Insert the method's description here.
   * Creation date: (2002-4-22 16:27:14)
   */
  public static synchronized void logonNumsPlus() {
    logonNums++;
  }
  /**
   * Insert the method's description here.
   * Creation date: (2002-4-22 16:26:36)
   */
  public static synchronized void logonNumsReduce() {
    logonNums--;
  }
  /**
   * Insert the method's description here.
   * Creation date: (2001-12-27 17:21:48)
   * @param aNode org.w3c.dom.Node
   */
  private static void manageJDBCNode(Node aNode) throws Exception {

    NodeList list = aNode.getChildNodes();
    for (int i = 0; i < list.getLength(); i++) {
      Node tmpNode = list.item(i);
      if (isElementNode(tmpNode)) {
        String className = tmpNode.getNodeName();
        Class c = Class.forName("icbc.cmis.service." + className);
        JDBCConnectionPool pool = (JDBCConnectionPool)c.newInstance();
        if (pool instanceof WasJDBCConnectionPoolService) {
          WasJDBCConnectionPoolService wasPool = (WasJDBCConnectionPoolService)pool;
          NodeList childList = tmpNode.getChildNodes();
          for (int j = 0; j < childList.getLength(); j++) {
            Node childNode = childList.item(j);
            if (isElementNode(childNode)) {
              DBResource dbr = new DBResource();
              NamedNodeMap map = childNode.getAttributes();
              //	String pass  = map.getNamedItem("dbPassword").getNodeValue();
              //	String userName =  map.getNamedItem("dbUserName").getNodeValue();
              String sourceId = map.getNamedItem("resourceID").getNodeValue();
              //dbr.dbUserName = dbUserName;
              //dbr.dbPassword = dbUserPass;
              dbr.resourceName = sourceId.trim();
              wasPool.addJDBCResource(dbr);
            }
          }
          CmisConstance.JDBCPool = wasPool;
          //	return;
        }
        else if (pool instanceof JDBCConnectionPoolService) {
          JDBCConnectionPoolService jdbcPool = (JDBCConnectionPoolService)pool;
          NodeList childList = tmpNode.getChildNodes();
          for (int j = 0; j < childList.getLength(); j++) {
            Node childNode = childList.item(j);
            if (isElementNode(childNode)) {
              DBResource rs = new DBResource();
              NamedNodeMap map = childNode.getAttributes();
              rs.resourceName = map.getNamedItem("resourceID").getNodeValue();
              rs.driverName = map.getNamedItem("driverName").getNodeValue();
              rs.dbURL = map.getNamedItem("dbURL").getNodeValue();
              //	rs.dbUserName = map.getNamedItem("dbUserName").getNodeValue();
              //	rs.dbPassword = map.getNamedItem("dbPassword").getNodeValue();
              rs.maxConnection = Integer.valueOf(map.getNamedItem("maxConnection").getNodeValue()).intValue();
              jdbcPool.addJDBCResource(rs);
            }
          }
          CmisConstance.JDBCPool = jdbcPool;
          //	return;
        }
      }
    }
  }

  /**
   * Insert the method's description here.
   * Creation date: (2001-12-27 17:21:48)
   * @param aNode org.w3c.dom.Node
   */
  private static void manageDBVerity(Node aNode) throws Exception {

    userAndPass = new java.util.Hashtable();
    dbUserPool = new java.util.Hashtable();
    NodeList childList = aNode.getChildNodes();
    for (int j = 0; j < childList.getLength(); j++) {
      Node childNode = childList.item(j);
      if (isElementNode(childNode)) {
        NamedNodeMap map = childNode.getAttributes();
        String dbName = map.getNamedItem("userID").getNodeValue();
        String dbVerify = map.getNamedItem("verify").getNodeValue();
        userAndPass.put(dbName.toLowerCase(), dbVerify);

        String dbPool = map.getNamedItem("pool").getNodeValue();
        dbUserPool.put(dbName.toLowerCase(), dbPool);

      }
    }

  }

  /**
   * Insert the method's description here.
   * Creation date: (2001-12-27 17:20:17)
   * @param aNode org.w3c.dom.Node
   */
  /*
  			listenPort="1880"
  			checkInterval="1800"
  			server="127.0.0.1"
  			retryInterval="10000"
  			mgrName="icbc"
  			mgrPass="icbc"
  			serverStart="false"
  */
  private static void manageMonitor(Node aNode) {
    try {
      NamedNodeMap map = aNode.getAttributes();
      int listerPort = Integer.valueOf(map.getNamedItem("listenPort").getNodeValue()).intValue();
      int checkInterval = Integer.valueOf(map.getNamedItem("checkInterval").getNodeValue()).intValue();
      String server = (String)map.getNamedItem("server").getNodeValue();
      int retryInterval = Integer.valueOf(map.getNamedItem("retryInterval").getNodeValue()).intValue();
      String mgrName = (String)map.getNamedItem("mgrName").getNodeValue();
      String mgrPass = (String)map.getNamedItem("mgrPass").getNodeValue();
      boolean serverStart = ((String)map.getNamedItem("serverStart").getNodeValue()).trim().equals("true") ? true : false;

      if (serverStart) {
        icbc.cmis.mgr.MonitorServer srvMonitor = new icbc.cmis.mgr.MonitorServer(checkInterval, listerPort);
      }

      icbc.cmis.mgr.MonitorClient client = new icbc.cmis.mgr.MonitorClient(server, listerPort, retryInterval, mgrName, mgrPass);

    }
    catch (Exception e) {
      System.out.println("Warning:init Monitor failure!Exception:\n" + e.getMessage());
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (2001-12-27 17:20:17)
   * @param aNode org.w3c.dom.Node
   */
  private static void manageMQ(Node aNode) {

    String mqEnable = (String)CmisConstance.getParameterSettings().get("enableMQ");
    if (mqEnable != null && (mqEnable.trim().equalsIgnoreCase("true") || mqEnable.trim().equalsIgnoreCase("tf"))) {

      MQConnectionPoolService mqPool = new MQConnectionPoolService();
      NodeList list = aNode.getChildNodes();
      int maxCon = 10;
      for (int i = 0; i < list.getLength(); i++) {
        Node tmpNode = list.item(i);
        if (isElementNode(tmpNode)) {
          if (tmpNode.getNodeName().equals("QManager")) {
            QManagerConnectionManager qMgr = new QManagerConnectionManager();
            NamedNodeMap map = tmpNode.getAttributes();
            qMgr.qmanagerName = (String)map.getNamedItem("QMgrName").getNodeValue();
            qMgr.channelName = (String)map.getNamedItem("channel").getNodeValue();
            qMgr.charSet = Integer.valueOf(map.getNamedItem("charSet").getNodeValue()).intValue();
            qMgr.hostName = (String)map.getNamedItem("hostName").getNodeValue();
            qMgr.port = Integer.valueOf(map.getNamedItem("port").getNodeValue()).intValue();
            maxCon = Integer.valueOf(map.getNamedItem("maxConnections").getNodeValue()).intValue();
            qMgr.maxConnections = maxCon;
            mqPool.addQManager(qMgr);
          }
          else if (tmpNode.getNodeName().equals("MQResource")) {
            MQResource rs = new MQResource();
            NamedNodeMap map = tmpNode.getAttributes();
            rs.setQManagerName(map.getNamedItem("QMgrName").getNodeValue());
            rs.setSendToQ(map.getNamedItem("sendToQ").getNodeValue());
            rs.setReplyToQ(map.getNamedItem("replyToQ").getNodeValue());
            rs.setPutMessageOptions(map.getNamedItem("PutMessageOptions").getNodeValue());
            rs.setGetMessageOptions(map.getNamedItem("getMessageOptions").getNodeValue());
            rs.setSendToQOpenOptions(map.getNamedItem("sendToQOpenOptions").getNodeValue());
            rs.setReplyToQOpenOptions(map.getNamedItem("replyToQOpenOptions").getNodeValue());
            rs.setTimeOut(map.getNamedItem("timeout").getNodeValue());
            rs.setResourceID(map.getNamedItem("resourceID").getNodeValue());
            rs.setMaxConnections(maxCon);
            mqPool.addMQResource(rs);
          }
        }
      }
      CmisConstance.setMQPool(mqPool);
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (2002-4-22 16:27:14)
   */
  public static synchronized void onLineNumsPlus() {
    onLineNums++;
  }
  /**
   * Insert the method's description here.
   * Creation date: (2002-4-22 16:27:14)
   */
  public static synchronized void onLineNumsReduce() {
    onLineNums--;
  }
  /**
   * Insert the method's description here.
   * Creation date: (2002-7-25 13:42:07)
   * @param msg java.lang.String
   */
  public static void pringMsg(String msg) {
    String debug = (String)CmisConstance.getParameterSettings().get("isDebugModel");
    if (debug == null)
      return;
    if (debug.equals("true")) {
      System.out.println("AppServer:" + appServerID + " -> " + msg);
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (2002-10-10 9:49:58)
   * @return boolean
   * @param opName java.lang.String
   */
  public static void resetRuningStatus(String opName, HttpServletRequest req) throws Exception {
    //	开关正常开启，且被控清单非空
    String isEnable = (String)CmisConstance.getParameterSettings().get("enableLimitedOpCtl");
    if (isEnable == null || !isEnable.equalsIgnoreCase("true"))
      return;
    if (limitedOpList == null || limitedOpList.isEmpty())
      return;
    //	update by yanbo 20060302, 整合了cmis、bms的op名和动作的控制
    //bms Op
    String isOpSceneCtl = (String)CmisConstance.getParameterSettings().get("enableLimitedOpSceneCtl");
    if (isOpSceneCtl == null || !isOpSceneCtl.equalsIgnoreCase("true"))
      isOpSceneCtl = "false";
    //标示scene的tag名称
    String opscenetag = (String)CmisConstance.getParameterSettings().get("enableLimitedOpSceneTag");
    if (opscenetag == null)
      opscenetag = "scene";
    String scene = (String)req.getParameter(opscenetag);
    if (scene == null) {
      scene = "";
    }
    String opNameBms = opName + scene;
    //cmis Op
    String opAction = (String)req.getParameter("opAction");
    if (opAction == null) {
      opAction = "";
    }
    String opNameCmis = opName + opAction;

    if (!limitedOpList.containsKey(opName) && !limitedOpList.containsKey(opNameBms) && !limitedOpList.containsKey(opNameCmis))
      return;

    if (limitedOpList.containsKey(opNameCmis)) {
      opName = opNameCmis;
    }
    else if (limitedOpList.containsKey(opNameBms)) {
      opName = opNameBms;
    }
    //	end update 20060302

    SqlTool sqlTool = null;
    try {
      sqlTool = new SqlTool(new DummyOperation());
      sqlTool.getConn("missign");
      Vector params = new Vector(1);
      params.add(opName);
      synchronized (limitedOpSynLock) {
        sqlTool.callFunc("pack_parellel_control.func_del_session", params, 0);
        return;
      }
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      try {
        if (sqlTool != null)
          sqlTool.closeconn();
      }
      catch (Exception e) {}
    }

  }

  /**
  	 * Insert the method's description here.
  	 * Creation date: (2002-10-10 9:49:58)
  	 * @return boolean
  	 * @param opName java.lang.String
  	 */
  //for ctp
  public static void resetRuningStatus(String opName, KeyedDataCollection opData) throws Exception {
    //	开关正常开启，且被控清单非空
    String isEnable = (String)CmisConstance.getParameterSettings().get("enableLimitedOpCtl");
    if (isEnable == null || !isEnable.equalsIgnoreCase("true"))
      return;
    if (limitedOpList == null || limitedOpList.isEmpty())
      return;

    //bms Op
    String isOpSceneCtl = (String)CmisConstance.getParameterSettings().get("enableLimitedOpSceneCtl");
    if (isOpSceneCtl == null || !isOpSceneCtl.equalsIgnoreCase("true"))
      isOpSceneCtl = "false";
    //标示scene的tag名称
    String opscenetag = (String)CmisConstance.getParameterSettings().get("enableLimitedOpSceneTag");
    if (opscenetag == null)
      opscenetag = "scene";
    String scene = null;
    try {
      scene = (String)opData.getValueAt(opscenetag);
    }
    catch (Exception e) {}
    if (scene == null) {
      scene = "";
    }
    String opNameBms = opName + scene;
    //cmis Op
    String opAction = null;
    try {
      opAction = (String)opData.getValueAt("opAction");
    }
    catch (Exception e) {}
    if (opAction == null) {
      opAction = "";
    }
    String opNameCmis = opName + opAction;

    if (!limitedOpList.containsKey(opName) && !limitedOpList.containsKey(opNameBms) && !limitedOpList.containsKey(opNameCmis))
      return;

    if (limitedOpList.containsKey(opNameCmis)) {
      opName = opNameCmis;
    }
    else if (limitedOpList.containsKey(opNameBms)) {
      opName = opNameBms;
    }
    //	end update 20060302

    SqlTool sqlTool = null;
    try {
      sqlTool = new SqlTool(new DummyOperation());
      sqlTool.getConn("missign");
      Vector params = new Vector(1);
      params.add(opName);
      synchronized (limitedOpSynLock) {
        sqlTool.callFunc("pack_parellel_control.func_del_session", params, 0);
        return;
      }
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      try {
        if (sqlTool != null)
          sqlTool.closeconn();
      }
      catch (Exception e) {}
    }

  }
  /**
   * Insert the method's description here.
   * Creation date: (2001-12-27 17:33:39)
   * @param mqs com.icbc.cmis.service.MQConnectionPoolService
   */
  public static void setMQPool(MQConnectionPoolService mqs) {
    MQPool = mqs;
  }
  /**
   * Insert the method's description here.
   * Creation date: (2001-12-26 15:59:05)
   * @return java.util.Hashtable
   */
  public static void setParameterSettings(Hashtable settings) {
    settingsTable = settings;
  }
  public static void setWorkDate() throws Exception {
    SqlTool tool = null;
    //try {
     /* tool = new SqlTool(new DummyOperation());
      tool.getConn();
      String tmpDate = null;
      java.sql.ResultSet set = tool.executeQuery("select to_char(cmisdate,'yyyymmdd') from dual");

      if (set.next()) {
        tmpDate = set.getString(1);
      }
      set.close();
      tool.closeconn();
      if (tmpDate == null || tmpDate.trim().length() != 8) {
        throw new Exception("更新工作日期失败[调用cmisdate取工作日期失败]！");
      }*/
      CmisConstance.cmisWorkDate = "20140731";
   /* }
    catch (Exception e) {
      /*if (tool != null) {
        try {
          tool.closeconn();
        }
        catch (Exception ee) {}
      }
      throw e;
    }*/
  }

  /**
   * Insert the method's description here.
   * Creation date: (2002-2-5 20:06:07)
   */
  public static void startCmisSystemInfoThread() {
    try {
      CmisSystemInfoThread sysInfo = new CmisSystemInfoThread();
      if (cmisSystemInfo != null) {
        try {
          cmisSystemInfo.interrupt();
        }
        catch (Exception ee) {}
      }
      cmisSystemInfo = null;
      Thread thread = new Thread(sysInfo);
      thread.start();
      cmisSystemInfo = thread;
    }
    catch (Exception e) {
      CmisConstance.pringMsg("Start CmisOperationThrea is fail in CmisCanstance.startCmisOperationMgr()");
    }
  }
  /**
   * Insert the method's description here.
   * Creation date: (2002-10-29 15:47:46)
   */
  public static void systemReset() {
    try {
      //opList = new Hashtable();
      limitedOpSynLock = new Integer(0);
      errorMsgTable = null;
      JDBCPool = null;
      settingsTable = new Hashtable();
      MQPool = null;
      dict_tables = new Hashtable();
      dbUserTable = new Hashtable();
      //add by chenj 20031119
      dbUserPool = new Hashtable();
      userAndPass = new Hashtable();
      xmlReader = null;
      opPool = new Hashtable();
      cmisSystemInfo = null;
      da200251031 = null;
      monitor = null;
      onLineNums = 0;
      logonNums = 0;
      isServerStarted = false;
      da200251012 = null;
      da220091006 = null;
      cmisWorkDate = null;
      limitedOpList = null;
      icbc.cmis.service.MQConnectionPoolService.inUsedMqConnCount = 0;
      icbc.cmis.service.MQConnectionPoolService.mqConnCount = 0;
      icbc.cmis.service.WasJDBCConnectionPoolService.cons = 0;
      System.out.println("系统内存变量初始化完成!");

    }
    catch (Exception e) {
      System.out.println("系统内存变量初始化异常in CmisConstance.systemReset(),Exception:" + e.getMessage());
    }

  }

  //add by yanbo 20040415
  public static Enumeration getAppEntryURLs() {
    return sysCodeWithEntry.elements();
  }

  public static String getAppEntry(String sysCode) {
    return (String)sysCodeWithEntry.get(sysCode);
  }

  public static boolean getSwitchAppEnabled() {
    return switchAppEnabled;
  }

  public static String getLocalAppEntry() {
    return localAppEntry;
  }

  /**
   * Insert the method's description here.
   * Creation date: (2002-10-10 9:49:58)
   * @return boolean
   * @param opName java.lang.String
   * @param scene  java.lang.String
   * 20060227 by 严波 废弃
   * add by 2004-05-11<br>
   * add scene,对于opscene里的操作进行并发控制<br>
   */
  //public static boolean isValidRuningStatus(String opName, String scene)
  //		throws TranFailException {
  //		//try {
  //				//分隔符 
  //				String deli =
  //						(String) CmisConstance.getParameterSettings().get(
  //								"enableLimitedopSceneCtlDeli");
  //				if (deli == null) {
  //						deli = "";
  //				}
  //		String opscene = opName + deli + scene;
  //		return isValidRuningStatus(opscene);
  //        
  //
  //}

  /**
   * Insert the method's description here.
   * Creation date: (2002-10-10 9:49:58)
   * @return boolean
   * @param opName java.lang.String
   * 废弃 by 严波 20060227
   * add by 2004-05-11<br>
   * add scene,对于opscene里的操作进行并发控制<br>
   */
  //	public static void resetRuningStatus(String opName, String scene) throws Exception {
  //		//分隔符 
  //		String deli =
  //				(String) CmisConstance.getParameterSettings().get(
  //						"enableLimitedopSceneCtlDeli");
  //		if (deli == null) {
  //				deli = "";
  //		}
  //		String opscene = opName + deli + scene;
  //    
  //		resetRuningStatus(opscene);
  //
  //	}

  //update by yanbo 20040430 for switch apps
  private static void manageAppEntry(Node aNode) throws Exception {

    sysCodeWithEntry = new java.util.Hashtable();

    NodeList childList = aNode.getChildNodes();

    for (int j = 0; j < childList.getLength(); j++) {
      Node childNode = childList.item(j);
      if (isElementNode(childNode)) {
        if (childNode.getNodeName().trim().equals("Status")) {
          NamedNodeMap map = childNode.getAttributes();
          String enabled = map.getNamedItem("enabled").getNodeValue();
          if (enabled.equalsIgnoreCase("true")) {
            switchAppEnabled = true;
          }
          else
            //return;
            switchAppEnabled = false;
        }
        else {

          NamedNodeMap map2 = childNode.getAttributes();
          String sysCode = map2.getNamedItem("sysCode").getNodeValue();
          String url = map2.getNamedItem("url").getNodeValue();
          sysCodeWithEntry.put(sysCode, url);
        }
      }
    }

  }

  //end 20040415

  /////////////////////////////////////////////////////////////////
  //
  //	修改原因：增加CM2002对外服务接口
  //	修改人:YangGuangRun
  //	修改时间：2004-10-22
  /////////////////////////////////////////////////////////////////

  /**
   * <b>功能描述: </b><br>
   * <p> </p>
   * @param node
   *  
   */
  private static void initCM2002ChannelService(Node node) throws Exception {
    NamedNodeMap nameMap = node.getAttributes();
    String channelEnabled = nameMap.getNamedItem("enabled").getNodeValue();
    if (channelEnabled != null && channelEnabled.equalsIgnoreCase("true")) {
      CM2002Service.setEnabled(true);
      CM2002Service.initCM2002Service(node);
    }

  }
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  //
  //	修改原因：增加服务参数控制
  //	修改人:ZhengAiping
  //	修改时间：2006-06-06
  /////////////////////////////////////////////////////////////////

  /**
  	* <b>功能描述: 查找出服务参数列表中所有的信息，并cach到内存中</b><br>
  	* <p> </p>
  	* @param 
  	*  
  	*/
  public static void initControledList() throws Exception {
    SqlTool sqlTool = null;
    try {
      controledList = new ArrayList();
      sqlTool = new SqlTool(new DummyOperation());

      sqlTool.getConn("missign");
      String selectSql = "select * from mag_appvid_ctl order by area_code,major_code";
      ResultSet rs = sqlTool.executeQuery(selectSql);
      int iColumnCount = rs.getMetaData().getColumnCount();
      while (rs.next()) {
        ArrayList controledSubList = new ArrayList();
        for (int i = 0; i < iColumnCount; i++) {
          controledSubList.add(rs.getString(i + 1));
        }

        controledList.add(controledSubList);
      }

    }
    catch (Exception e) {
      throw e;
    }
    finally {
      try {
        if (sqlTool != null)
          sqlTool.closeconn();
      }
      catch (Exception e) {}
    }

  }

  /////////////////////////////////////////////////////////////////
  //
  //	修改原因：增加服务参数控制
  //	修改人:ZhengAiping
  //	修改时间：2006-06-06
  /////////////////////////////////////////////////////////////////

  /**
   * <b>功能描述:根据地区代码查找对应的地区名称</b><br>
   * <p> </p>
   * @param 
   *  
   */

  public static String getAreaName(String areaCode) throws Exception {
    SqlTool sqlTool = null;
    ResultSet rs = null;
    String areaName = "";
    try {
      sqlTool = new SqlTool(new DummyOperation());
      sqlTool.getConn("missign");
      String selectSql = "select area_name from mag_area where area_code='" + areaCode + "'";
      rs = sqlTool.executeQuery(selectSql);
      while (rs.next()) {
        areaName = rs.getString(1);
      }
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      try {
        if (rs != null)
          rs.close();
        if (sqlTool != null)
          sqlTool.closeconn();
      }
      catch (Exception e) {}
    }
    return areaName;

  }
  /**
   * <b>功能描述:根据业务代码查找对应的业务种类名称</b><br>
   * <p> </p>
   * @param 
   *  
   */
  public static String getMajorName(String majorCode) throws Exception {
    SqlTool sqlTool = null;
    ResultSet rs = null;
    String majorName = "";
    try {
      sqlTool = new SqlTool(new DummyOperation());
      sqlTool.getConn("missign");
      String selectSql = "select major_name from mag_major where major_code='" + majorCode + "'";
      rs = sqlTool.executeQuery(selectSql);
      while (rs.next()) {
        majorName = rs.getString(1);
      }
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      try {
        if (rs != null)
          rs.close();
        if (sqlTool != null)
          sqlTool.closeconn();
      }
      catch (Exception e) {}
    }
    return majorName;

  }

  /**
   * <b>功能描述: 从内存中取所有服务参数列表,对登录的用户进行判断(是内部调用还是外部调用没有进行判断)</b><br>
   * <p> </p>
   * @param 
   *  
   */

  public static String popedomInfo(String areaCode, String majorCode, String channelMode, String tran_code) throws Exception {
    String infoStr = "";
    String areaName = getAreaName(areaCode);
    String majorName = getMajorName(majorCode);
    ArrayList alist = controledList;
    for (int i = 0; i < alist.size(); i++) {
      ArrayList bList = (ArrayList)alist.get(i);
      String iBeginTime = (String)bList.get(3);
      String iEndTime = (String)bList.get(5);
      if (bList.get(0).equals("*")) {
        if (bList.get(1).equals("*")) //代表在某一时间段所有地区所有业务都必须控制
          {
          if (bList.get(2).equals("2")) //外部接口调用
            {
            if (bList.get(2).equals(channelMode) && bList.get(4).equals(tran_code)) {
              infoStr = areaName + "地区" + majorName + "业务，在" + iBeginTime + "至" + iEndTime + "时间段已经被控制!";
            }
          }
          else {
            if (bList.get(2).equals(channelMode)) {
              infoStr = areaName + "地区" + majorName + "业务，在" + iBeginTime + "至" + iEndTime + "时间段已经被控制";
            }
          }
        }
        else {
          if (bList.get(2).equals("2")) //外部接口调用
            {
            if (bList.get(1).equals(majorCode) && bList.get(2).equals(channelMode) && bList.get(4).equals(tran_code)) {
              infoStr = areaName + "地区" + majorName + "业务，在" + iBeginTime + "至" + iEndTime + "时间段已经被控制!";
            }
          }
          else {
            if (bList.get(1).equals(majorCode) && bList.get(2).equals(channelMode)) {
              infoStr = areaName + "地区" + majorName + "业务，在" + iBeginTime + "至" + iEndTime + "时间段已经被控制!";
            }
          }

        }

      }
      else {

        if (bList.get(1).equals("*")) //代表在某一时间段某一地区所有业务都必须控制
          {
          if (bList.get(2).equals("2")) //外部接口调用
            {
            if (bList.get(0).equals(areaCode) && bList.get(2).equals(channelMode) && bList.get(4).equals(tran_code)) {
              infoStr = areaName + "地区" + majorName + "业务，在" + iBeginTime + "至" + iEndTime + "时间段已经被控制!";
            }
          }
          else {
            if (bList.get(0).equals(areaCode) && bList.get(2).equals(channelMode)) {
              infoStr = areaName + "地区" + majorName + "业务，在" + iBeginTime + "至" + iEndTime + "时间段已经被控制!";
            }
          }
        }
        else {
          if (bList.get(2).equals("2")) //外部接口调用
            {
            if (bList.get(0).equals(areaCode) && bList.get(1).equals(majorCode) && bList.get(2).equals(channelMode) && bList.get(4).equals(tran_code)) {
              infoStr = areaName + "地区" + majorName + "业务，在" + iBeginTime + "至" + iEndTime + "时间段已经被控制!";
            }
          }
          else {
            if (bList.get(0).equals(areaCode) && bList.get(1).equals(majorCode) && bList.get(2).equals(channelMode)) {
              infoStr = areaName + "地区" + majorName + "业务，在" + iBeginTime + "至" + iEndTime + "时间段已经被控制!";
            }
          }

        }

      }
    }

    return infoStr;

  }

  public static boolean isPopedom(String areaCode, String majorCode, String channelMode, String tran_code) throws Exception {
    boolean flag = false;
    //		取用户登录时间(日期)
    String sLoginTime = (String)getWorkDate("HHmmss");
    int iLoginTime = Integer.parseInt(sLoginTime);
    //	add by zap (20060814)   当业务种类为运行管理（100）时，不做控制
    if (majorCode != null) {
      if (majorCode.equals("100")) {
        return false;
      }
    }
    //end by zap (20060814)  
    ArrayList alist = controledList;
    if (alist!=null) {
    for (int i = 0; i < alist.size(); i++) {
      ArrayList bList = (ArrayList)alist.get(i);
      //把控制开始时间转换为int类型，以便于同系统时间进行对比
      int iBeginTime = Integer.parseInt((String)bList.get(3));

      //把控制结束时间转换为int类型，以便于同系统时间进行对比
      int iEndTime = Integer.parseInt((String)bList.get(5));

      if (!bList.get(0).equals("*")) {
        if (!bList.get(1).equals("*")) //代表在某一时间段所有地区所有业务都必须控制
          {
          if (bList.get(0).equals(areaCode)
            && bList.get(1).equals(majorCode)
            && bList.get(2).equals(channelMode)
            && iLoginTime >= iBeginTime
            && iLoginTime <= iEndTime) {
            flag = true;
          } //end if
        } //end for bList.get(0)
        else {
          if (bList.get(0).equals(areaCode)
            && bList.get(2).equals(channelMode)
            && iLoginTime >= iBeginTime
            && iLoginTime <= iEndTime) {
            flag = true;
          }
        }
      } //end for bList.get(1)
      else {
        if (!bList.get(1).equals("*")) //代表在某一时间段所有地区所有业务都必须控制
          {
          if ( bList.get(1).equals(majorCode)
            && bList.get(2).equals(channelMode)
            && iLoginTime >= iBeginTime
            && iLoginTime <= iEndTime) {
            flag = true;
          } //end if
        } //end for bList.get(0)
        else {
          if ( bList.get(2).equals(channelMode)
            && iLoginTime >= iBeginTime
            && iLoginTime <= iEndTime) {
            flag = true;
          }
        }
      }
    } 
    }
    //end for for
    /*if (bList.get(2).equals("2")) //外部接口调用
      {
      if (bList.get(2).equals(channelMode) && bList.get(4).equals(tran_code) && iLoginTime >= iBeginTime && iLoginTime <= iEndTime) {
        flag = true;
      }
    }
    else {
      if (bList.get(0).equals(areaCode)
    	&& bList.get(1).equals(majorCode) && bList.get(2).equals(channelMode) && iLoginTime >= iBeginTime && iLoginTime <= iEndTime) {
        flag = true;
      }
    }
    }
    else {
    if (bList.get(2).equals("2")) //外部接口调用
      {
      if ( bList.get(2).equals(channelMode)
        && bList.get(4).equals(tran_code)
        && iLoginTime >= iBeginTime
        && iLoginTime <= iEndTime) {
        flag = true;
      }
    }
    else {
      if ( bList.get(2).equals(channelMode) && iLoginTime >= iBeginTime && iLoginTime <= iEndTime) {
        flag = true;
      }
    }
    
    }
    
    }
    else {
    
    if (!bList.get(1).equals("*")) //代表在某一时间段某一地区所有业务都必须控制
    {
    if (bList.get(2).equals("2")) //外部接口调用
      {
      if ( bList.get(2).equals(channelMode)
        && bList.get(4).equals(tran_code)
        && iLoginTime >= iBeginTime
        && iLoginTime <= iEndTime) {
        flag = true;
      }
    }
    else {
      if (bList.get(0).equals(areaCode) && bList.get(2).equals(channelMode) && iLoginTime >= iBeginTime && iLoginTime <= iEndTime) {
        flag = true;
      }
    }
    }
    else {
    if (bList.get(2).equals("2")) //外部接口调用
      {
      if (bList.get(0).equals(areaCode)
        && bList.get(1).equals(majorCode)
        && bList.get(2).equals(channelMode)
        && bList.get(4).equals(tran_code)
        && iLoginTime >= iBeginTime
        && iLoginTime <= iEndTime) {
        flag = true;
      }
    }
    else {
      if (bList.get(0).equals(areaCode)
        && bList.get(1).equals(majorCode)
        && bList.get(2).equals(channelMode)
        && iLoginTime >= iBeginTime
        && iLoginTime <= iEndTime) {
        flag = true;
      }
    }
    
    }
    
    }
    }*/

    return flag;

  }

  //外部接口调用判断
  public static boolean isOutPopedom(String tran_code) throws Exception {
    boolean flag = false;
    //		取用户登录时间(日期)
    String sLoginTime = (String)getWorkDate("HHmmss");
    int iLoginTime = Integer.parseInt(sLoginTime);
    ArrayList alist = controledList;
    for (int i = 0; i < alist.size(); i++) {
      ArrayList bList = (ArrayList)alist.get(i);
      //把控制开始时间转换为int类型，以便于同系统时间进行对比
      int iBeginTime = Integer.parseInt((String)bList.get(3));
      //把控制结束时间转换为int类型，以便于同系统时间进行对比
      int iEndTime = Integer.parseInt((String)bList.get(5));

      if (bList.get(4).equals(tran_code) && iLoginTime >= iBeginTime && iLoginTime <= iEndTime) {
        flag = true;
        break;
      }
    }
    return flag;

  }

  /*public static String[] isPopedom(String areaCode, String majorCode, String channelMode, String tran_code) throws Exception {
    String[] flag = { "false", "" };
    //		取用户登录时间(日期)
    String sLoginTime = (String)getWorkDate("HHmmss");
    int iLoginTime = Integer.parseInt(sLoginTime);
    //	add by zap (20060814)   当业务种类为运行管理（100）时，不做控制
    if (majorCode != null) {
      if (majorCode.equals("100")) {
        flag[0] = "false";
        flag[1] = "true";
        return flag;
      }
    }
    //end by zap (20060814)  
    ArrayList alist = controledList;
    for (int i = 0; i < alist.size(); i++) {
      ArrayList bList = (ArrayList)alist.get(i);
      //把控制开始时间转换为int类型，以便于同系统时间进行对比
      int iBeginTime = Integer.parseInt((String)bList.get(3));
      //把控制结束时间转换为int类型，以便于同系统时间进行对比
      int iEndTime = Integer.parseInt((String)bList.get(5));
  
      if (bList.get(0).equals("*")) {
        if (bList.get(1).equals("*")) //代表在某一时间段所有地区所有业务都必须控制
          {
          if (bList.get(2).equals("2")) //外部接口调用
            {
            if (bList.get(2).equals(channelMode) && bList.get(4).equals(tran_code) && iLoginTime >= iBeginTime && iLoginTime <= iEndTime) {
              flag[0] = "true";
              flag[1] = "所有地区所有业务种类在" + iBeginTime + "至" + iEndTime + "时间段已经被控制";
              return flag;
            }
          }
          else {
            if (bList.get(2).equals(channelMode) && iLoginTime >= iBeginTime && iLoginTime <= iEndTime) {
              flag[0] = "true";
              flag[1] = "所有地区所有业务种类在" + iBeginTime + "至" + iEndTime + "时间段已经被控制";
              return flag;
            }
          }
        }
        else {
          if (bList.get(2).equals("2")) //外部接口调用
            {
            if (bList.get(1).equals(majorCode)
              && bList.get(2).equals(channelMode)
              && bList.get(4).equals(tran_code)
              && iLoginTime >= iBeginTime
              && iLoginTime <= iEndTime) {
              flag[0] = "true";
              flag[1] = "所有地区"+majorCode+"业务种类在" + iBeginTime + "至" + iEndTime + "时间段已经被控制";
              return flag;
            }
          }
          else {
            if (bList.get(1).equals(majorCode) && bList.get(2).equals(channelMode) && iLoginTime >= iBeginTime && iLoginTime <= iEndTime) {
              flag[0] = "true";
  						flag[1] = "所有地区"+majorCode+"业务种类在" + iBeginTime + "至" + iEndTime + "时间段已经被控制";
              return flag;
            }
          }
  
        }
  
      }
      else {
  
        if (bList.get(1).equals("*")) //代表在某一时间段某一地区所有业务都必须控制
          {
          if (bList.get(2).equals("2")) //外部接口调用
            {
            if (bList.get(0).equals(areaCode)
              && bList.get(2).equals(channelMode)
              && bList.get(4).equals(tran_code)
              && iLoginTime >= iBeginTime
              && iLoginTime <= iEndTime) {
              flag[0] = "true";
              flag[1] = areaCode + "地区所有业务种类在" + iBeginTime + "至" + iEndTime + "时间段已经被控制";
              return flag;
            }
          }
          else {
            if (bList.get(0).equals(areaCode) && bList.get(2).equals(channelMode) && iLoginTime >= iBeginTime && iLoginTime <= iEndTime) {
              flag[0] = "true";
              flag[1] = areaCode + "地区所有业务种类在" + iBeginTime + "至" + iEndTime + "时间段已经被控制";
              return flag;
            }
          }
        }
        else {
          if (bList.get(2).equals("2")) //外部接口调用
            {
            if (bList.get(0).equals(areaCode)
              && bList.get(1).equals(majorCode)
              && bList.get(2).equals(channelMode)
              && bList.get(4).equals(tran_code)
              && iLoginTime >= iBeginTime
              && iLoginTime <= iEndTime) {
              flag[0] = "true";
              flag[1] = areaCode + "地区"+majorCode+"业务种类在" + iBeginTime + "至" + iEndTime + "时间段已经被控制";
              return flag;
            }
          }
          else {
            if (bList.get(0).equals(areaCode)
              && bList.get(1).equals(majorCode)
              && bList.get(2).equals(channelMode)
              && iLoginTime >= iBeginTime
              && iLoginTime <= iEndTime) {
              flag[0] = "true";
  		  flag[1] = areaCode + "地区"+majorCode+"业务种类在" + iBeginTime + "至" + iEndTime + "时间段已经被控制";
              return flag;
            }
          }
  
        }
  
      }
    }
  
    return flag;
  
  }
  */
  /**
  	 * <b>功能描述: 查找出服务参数列表中所有的信息，并cach到内存中</b><br>
  	 * <p> </p>
  	 * @param 
  	 *  
  	 */
  public static void initOpdataList() throws Exception {
    SqlTool sqlTool = null;
    try {
      opDataList = new ArrayList();
      sqlTool = new SqlTool(new DummyOperation());

      sqlTool.getConn("missign");
      String selectSql = "select opname,actionname from mag_opdata_logop where valid='1'";
      ResultSet rs = sqlTool.executeQuery(selectSql);
      int iColumnCount = rs.getMetaData().getColumnCount();
      while (rs.next()) {
        ArrayList opDataSubList = new ArrayList();
        for (int i = 0; i < iColumnCount; i++) {
          opDataSubList.add(rs.getString(i + 1));
        }

        opDataList.add(opDataSubList);
      }
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      try {
        if (sqlTool != null)
          sqlTool.closeconn();
      }
      catch (Exception e) {}
    }

  }
  
/*
* 修改原因：英文版改造,
* 修改时间：2007-04-17
* 修改人：chenj
* 初始化系统目前的时区表和语言代码表
*/
public static void initZoneLang() throws Exception {
  SqlTool tool = null;
  try {
	tool = new SqlTool(new DummyOperation());
	tool.getConn("missign");
	allZone = new Vector();
	allLang = new Vector();


	java.sql.ResultSet rs = tool.executeQuery("select lang_code,lang_name from mag_language ");
	while (rs.next()) {
		allLang.add(rs.getString(1));
	}

	rs.close();

//	String tmpDate = null;
//	java.sql.ResultSet rs2 = tool.executeQuery("select zone_code,zone_name from time_zone   ");
//	while (rs.next()) {
//		allZone.add(rs.getString(1));
//	}
//	rs.close();
	
	tool.closeconn();
	
	for(Iterator i = allLang.iterator(); i.hasNext();){
		String lang_code = (String)i.next();
		ml_dict_tables.put(lang_code,new Hashtable());
	}

	
	
	
	
	
//	if (tmpDate == null || tmpDate.trim().length() != 8) {
//	  throw new Exception("更新工作日期失败[调用cmisdate取工作日期失败]！");
//	}
//	CmisConstance.cmisWorkDate = tmpDate.trim();
  }
  catch (Exception e) {
	if (tool != null) {
	  try {
		tool.closeconn();
	  }
	  catch (Exception ee) {}
	}
	throw e;
  }
}
  
  

}
