package icbc.missign;

/**
 * 用于存放柜员信息，与missign用户的mag_employee表一致。
 * 提供isValid方法确认当前用户是否有效及口令是否正确。
 * @author 叶晓挺
 * @version 1.0
 */
import icbc.cmis.util.Encode;

public class Employee2 implements java.io.Serializable {
  private String employeeCode = "";
  private String employeeName = "";
  private String mdbSID = "";
  private String employeePasswd = "";
  private String employeeUseful = "";
  private String mdbFlag = "";
  private String mdbTime = "";
  private String employeeDeleteFlag = "";
  private String passwdTime = "";
  private String employeeEnableFlag = "";
  private String employeeNotesID = "";
  private String employeeEmail = "";
  private String invaildInfo = "";
  private String areaName = "";
  private String bankFlag = "";
  private String employeeMajor = "";
  private String employeeMajorName = "";
  private String employeeClass = "";
  private String employeeClassName = "";
  private java.util.Vector majors;
  private String canScan;

  public Employee2() {
  }
  public String getEmployeeCode() {
    return employeeCode;
  }
  public void setEmployeeCode(String employeeCode) {
    this.employeeCode = employeeCode;
  }
  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }
  public String getEmployeeName() {
    return employeeName;
  }
  public void setMdbSID(String mdbSID) {
    this.mdbSID = mdbSID;
  }
  public String getMdbSID() {
    return mdbSID;
  }
  public void setEmployeePasswd(String employeePasswd) {
    this.employeePasswd = employeePasswd;
  }
  public String getEmployeePasswd() {
    return employeePasswd;
  }
  public void setEmployeeUseful(String employeeUseful) {
    this.employeeUseful = employeeUseful;
  }
  public String getEmployeeUseful() {
    return employeeUseful;
  }
  public void setMdbFlag(String mdbFlag) {
    this.mdbFlag = mdbFlag;
  }
  public String getMdbFlag() {
    return mdbFlag;
  }
  public void setMdbTime(String mdbTime) {
    this.mdbTime = mdbTime;
  }
  public String getMdbTime() {
    return mdbTime;
  }
  public void setEmployeeDeleteFlag(String employeeDeleteFlag) {
    this.employeeDeleteFlag = employeeDeleteFlag;
  }
  public String getEmployeeDeleteFlag() {
    return employeeDeleteFlag;
  }
  public void setPasswdTime(String passwdTime) {
    this.passwdTime = passwdTime;
  }
  public String getPasswdTime() {
    return passwdTime;
  }
  public void setEmployeeEnableFlag(String employeeEnableFlag) {
    this.employeeEnableFlag = employeeEnableFlag;
  }
  public String getEmployeeEnableFlag() {
    return employeeEnableFlag;
  }
  public void setEmployeeNotesID(String employeeNotesID) {
    this.employeeNotesID = employeeNotesID;
  }
  public String getEmployeeNotesID() {
    return employeeNotesID;
  }
  public void setEmployeeEmail(String employeeEmail) {
    this.employeeEmail = employeeEmail;
  }
  public String getEmployeeEmail() {
    return employeeEmail;
  }
  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }
  public String getAreaName() {
    return areaName;
  }
  public void setBankFlag(String bankFlag) {
    this.bankFlag = bankFlag;
  }
  public String getBankFlag() {
    return bankFlag;
  }
  public void setEmployeeClassName(String employeeClassName) {
    this.employeeClassName = employeeClassName;
  }
  public String getEmployeeClassName() {
    return employeeClassName;
  }
  public void setEmployeeMajor(String employeeMajor) {
    this.employeeMajor = employeeMajor;
  }
  public String getEmployeeMajor() {
    return employeeMajor;
  }
  public void setEmployeeMajorName(String employeeMajorName) {
    this.employeeMajorName = employeeMajorName;
  }
  public String getEmployeeMajorName() {
    return employeeMajorName;
  }
  public void setEmployeeClass(String employeeClass) {
    this.employeeClass = employeeClass;
  }
  public String getEmployeeClass() {
    return employeeClass;
  }
  public void setMajors(java.util.Vector majors) {
    this.majors = majors;
  }
  public java.util.Vector getMajors() {
    return majors;
  }
  /**
   * 判断当前用户是否有效，判断条件：
   * <li>employeeCode为空，无效
   * <li>employeeName为空，无效
   * <li>employeePasswd与传入的口令不一致，口令错
   * <li>employeeEnableFlag为空或等于0，该账号没有激活
   * <li>employeeDeleteFlag等于1，该账号已作废
   * 若用户有效，则取
   * @param passwd 用户口令
   * @return 如果该用户有效且口令正确，则返回true；否则，返回false
   */
  public boolean isValid(String passwd) {
    if(employeeCode == null) {
      invaildInfo = "账号并没有在系统中登记！";
      return false;
    }
    if(employeeName == null) {
      invaildInfo = "账号并没有在系统中登记！";
      return false;
    }
    if(employeeEnableFlag == null || employeeEnableFlag.equals("0")) {
      invaildInfo = "该账号没有激活！";
      return false;
    }
    if(employeeDeleteFlag.equals("1")) {
      invaildInfo = "该账号已作废！";
      return false;
    }
    if(!Encode.checkPassword(employeeCode,passwd,employeePasswd)) {
      invaildInfo = "口令错误，请返回主页重试。";
      return false;
    }
    return true;
  }
  /**
   * 取用户无效的原因
   * @return 无效原因
   */
  public String getInvaildInfo() {
    return invaildInfo;
  }
  public void setCanScan(String canScan) {
    this.canScan = canScan;
  }
  public String getCanScan() {
    return canScan;
  }

}