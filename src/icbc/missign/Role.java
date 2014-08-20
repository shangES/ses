package icbc.missign;

import java.io.Serializable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Role implements Serializable {
  private String majorCode;
  private String majorName;
  private String sysCode;
  private String sysName;
  private int roleCode = 0;
  private String roleName;
  private boolean canScan = false;
  private String forbidFunctions;
  private String areaCode;
  private String areaName;
  private int bankFlag;
  private boolean multiRole = false;
  private String langCode;   //add by yanbo @20070413


  public Role() {
  }

  public Role(String majorCode,String majorName,int roleCode,String roleName,
              boolean canScan,String forbidFunctions,String areaCode,
              String areaName,int bankFlag,String sysCode,String sysName,String langCode) {

    this.majorCode = majorCode;
    this.majorName = majorName;
    this.sysCode = sysCode;
    this.sysName = sysName;

    this.roleCode =  roleCode;
    this.roleName =  roleName;
    this.canScan =   canScan;
    this.forbidFunctions = forbidFunctions;
    this.areaCode =  areaCode;
    this.areaName =  areaName;
    this.bankFlag = bankFlag;
    this.langCode = langCode;
  }

  public String getLangCode(){
  	return langCode;
  }
  
  public void setLangCode(String langCode){
    this.langCode = langCode;
  }
  
  public String getAreaCode() {
    return areaCode;
  }
  public String getAreaName() {
    return areaName;
  }
  public boolean isCanScan() {
    return canScan;
  }
  public String getForbidFunctions() {
    return forbidFunctions;
  }
  public String getMajorCode() {
    return majorCode;
  }
  public String getMajorName() {
    return majorName;
  }

  public String getSysCode() {
    return sysCode;
  }
  public String getSysName() {
    return sysName;
  }
  public int getRoleCode() {
    return roleCode;
  }
  public String getRoleName() {
    return roleName;
  }
  public int getBankFlag() {
    return bankFlag;
  }
  public boolean isMultiRole() {
    return multiRole;
  }
  public void setMultiRole(boolean multiRole) {
    this.multiRole = multiRole;
  }
  public int hashCode() {
    return this.majorCode.hashCode() * this.roleCode;
  }
  public boolean equals(Object that) {
    return (that instanceof Role) && (this.majorCode.equals(((Role)that).majorCode))
        && (this.roleCode == ((Role)that).roleCode);
  }
}