package icbc.cmis.base;

import javax.servlet.jsp.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;
import com.icbc.ssic.base.*;
import javax.servlet.http.*;
import icbc.cmis.operation.*;
/**
   * Insert the method's description here.
   * Creation date: (2001-12-31 15:35:54)
   * @param userName java.lang.String
   * @param userPass java.lang.String
   */
public class SSICTool {
	/*所有的XML参数保存在静态变量中*/
	//密钥对保存
	private static java.security.PublicKey SSICPubKey;//用于保存统一认证服务器公钥
	private static java.security.PrivateKey XMISPriKey;//用于保存本地专业系统私钥

    private static boolean SSICEnabled=false;
    private static boolean SSICStopped=false;
    private static String SSICServerIP;
    private static String SSICFilePath;
    private static String SSICVersion;
    private static String LocalSystemName;
    private static String LocalSystemURL;
    private static String LocalSystemPrivateKeyPassword;

	
	/**
	 * cmisConstance constructor comment.
	 */
	public SSICTool() {
		super();
	}
public static String getLocalSystemURL() {
    return LocalSystemURL;
}
public static String getSSICEmployeeCode(CmisOperation op) throws Exception {
    String userName = "";
    if (!SSICEnabled)
        return userName;
    String ssiAuth = op.getStringAt("SSIAuth");
    String ssiSign = op.getStringAt("SSISign");
    Credentials cred = new Credentials(ssiAuth, ssiSign);
    if (cred.isvalidate(SSICPubKey,XMISPriKey,"1")) {
        SSICUser user = cred.getSSICUser();
        userName = user.getUserName();
    }
    return userName;
}
public static KeyedDataCollection getUserInfo(PageContext pageContext)
    throws Exception {

    KeyedDataCollection kdcoll = new KeyedDataCollection("userInfo");
    kdcoll.addElement("employee_code", "");
    kdcoll.addElement("employee_name", "");
    kdcoll.addElement("gender", "");
    kdcoll.addElement("birthday", "");
    kdcoll.addElement("id_no", "");
    kdcoll.addElement("department", "");
    kdcoll.addElement("result", "false");
    if (!SSICEnabled)
        return kdcoll;

    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

    String SSIAuth = request.getParameter("SSIAuth");
    String SSISign = request.getParameter("SSISign");

    Credentials cred = new Credentials(SSIAuth, SSISign);
    if (cred.isvalidate(SSICPubKey)) {
        SSICUserInfo userinfo = cred.getSSICUserInfo();
        kdcoll.setValueAt("employee_code", userinfo.getUsername());
        String s_name = userinfo.getName();
        kdcoll.setValueAt(
            "employee_name",
            s_name.substring(0, s_name.length() > 4 ? 4 : s_name.length()));
        kdcoll.setValueAt("gender", userinfo.getGender());
        kdcoll.setValueAt("birthday", userinfo.getBirthday());
        kdcoll.setValueAt("id_no", userinfo.getEmpcardno());
        kdcoll.setValueAt("department", userinfo.getDepartment());
        kdcoll.setValueAt("result", "true");
    } else
        return kdcoll;

    return kdcoll;
}
public static void initializeSSIC() throws Exception {

    String enableSSIC =
        (String) CmisConstance.getParameterSettings().get("enableSSIC");
    if (enableSSIC == null)
        enableSSIC = "";
    if (enableSSIC.trim().equalsIgnoreCase("true")){
        SSICEnabled = true;
        SSICStopped = false;
    }
    else if(enableSSIC.trim().equalsIgnoreCase("false")) {
        SSICEnabled = false;
        SSICStopped = false;
        return;
    }else {
        SSICEnabled = false;
        SSICStopped = true;
        return;
    }

    SSICServerIP =
        (String) CmisConstance.getParameterSettings().get("SSICServerIP");
    SSICFilePath =
        (String) CmisConstance.getParameterSettings().get("SSICFilePath");
    SSICVersion = (String) CmisConstance.getParameterSettings().get("SSICVersion");
    LocalSystemName =
        (String) CmisConstance.getParameterSettings().get("LocalSystemName");
    LocalSystemURL =
        (String) CmisConstance.getParameterSettings().get("LocalSystemURL");
    LocalSystemPrivateKeyPassword =
        (String) CmisConstance.getParameterSettings().get(
            "LocalSystemPrivateKeyPassword");
    SSICService ssic = new SSICService("SSIC");
    SSICService mis = new SSICService(LocalSystemName);

    mis.initialize(LocalSystemPrivateKeyPassword, SSICFilePath);
    ssic.initialize(SSICFilePath);
    XMISPriKey = mis.getPrivateKey();
    SSICPubKey = ssic.getPublicKey();
    /*	
        String rootPath =
            (String) CmisConstance.getParameterSettings().get("fileRootPath");
        if (rootPath.endsWith(System.getProperty("file.separator")))
            fileName = rootPath + fileName;
        else
            fileName = rootPath + System.getProperty("file.separator") + fileName;
    */
}
public static boolean isSSICEnabled() {
    return SSICEnabled;
}

public static boolean isSSICStopped() {
    return SSICStopped;
}

public static boolean login(PageContext pageContext) throws Exception {

    if (!SSICEnabled)
        return false;

    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
    
    String SSIAuth = request.getParameter("SSIAuth");
    String SSISign = request.getParameter("SSISign");

    ServerSideAuthenticator logonsign = new ServerSideAuthenticator();

    logonsign.setServerName(SSICServerIP);
    logonsign.setVersion(SSICVersion);
    logonsign.setServiceName(LocalSystemName);
    logonsign.setServiceURL(LocalSystemURL+"ssic_start_url.jsp");
    logonsign.setOperation("signIn");
    logonsign.setSSIPublicKey(SSICPubKey);
    logonsign.setPrivateKey(XMISPriKey);
    if (!logonsign.execute(request, response, SSIAuth, SSISign))
        return true;
    return true;
}
public static boolean logout(PageContext pageContext) throws Exception {

    if (!SSICEnabled)
        return false;

    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

    String s_scene = "";
    try {
        KeyedDataCollection context =
            (KeyedDataCollection) request.getAttribute("operationData");
        s_scene = (String) context.getValueAt("scene");
    } catch (Exception e) {
    }

    String SSIAuth = request.getParameter("SSIAuth");
    String SSISign = request.getParameter("SSISign");
    ServerSideAuthenticator logonsign = new ServerSideAuthenticator();
    logonsign.setServerName(SSICServerIP);
    logonsign.setVersion(SSICVersion);
    logonsign.setServiceName(LocalSystemName);

    if (s_scene != null && s_scene.equals("quitSSIC"))
        logonsign.setOperation("signOutSSI");
    else
        logonsign.setOperation("signOut");

    if (!logonsign.execute(request, response, SSIAuth, SSISign))
        return true;
    return true;
}
public static boolean prepareUserInfo(
    PageContext pageContext,
    String returnpage)
    throws Exception {

    if (!SSICEnabled)
        return false;

    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

    String serviceURL = LocalSystemURL + returnpage;
    //    String serviceURL = LocalSystemURL + "ssic/tellerinfo.jsp";
    String tellerno = request.getParameter("emp_code");
    SSICUser user = new SSICUser(tellerno);
    Credentials cred = new Credentials(user, "600000");
    cred.generate(SSICPubKey, XMISPriKey);
    response.sendRedirect(
        "http://"
            + SSICServerIP
            + "/servlet/com.icbc.ssic.base.QueryTellerInfo?"
            + cred.getAuthStringURL()
            + "&SERVICENAME="
            + LocalSystemName
            + "&serviceURL="
            + serviceURL
            + "&TELLERNO="
            + tellerno);
    return true;
}
public static String genSSICpass(String plainPwd) throws Exception{
    String encPwd = SecurityTool.getMessageDigestBySHA1(plainPwd);
    return encPwd;
}

public static java.security.PublicKey getPublicKey(){
    return SSICPubKey;
}

public static String getLocalSystemName(){
    return LocalSystemName;
}


public static java.security.PrivateKey getPrivateKey(){
    return  XMISPriKey ;
}
}
