package icbc.missign;

import java.sql.*;
import java.util.Vector;
import icbc.cmis.service.CmisDao;
import icbc.missign.Employee;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.SSICTool;
import java.util.ArrayList;
import java.util.HashSet;
import icbc.cmis.base.CmisConstance;
import java.util.StringTokenizer;
import icbc.cmis.base.CmisConstance;

/**
 * updated by yanbo 20040430
 *   add function isEmployeeExisted(String) 
 *   add function getBranchDetail(String)
 * 
 * 查询missign用户的mag_employee表，返回指定用户的用户信息
 * @author 叶晓挺
 * @version 1.0
 *
 */

public class LoginDAO extends CmisDao {
  private Employee employee = new Employee();


  /**
   * 构造函数
   */
  public LoginDAO(icbc.cmis.operation.CmisOperation op) {
  super(op);
  }

  /**
   * 取当前用户
   * @return 返回当前用户
   */
  public Employee getEmployee() {
    return employee;
  }

  /**
   * 取指定用户代码的用户信息。
   * 连接到missign用户，从mag_employee中查询取指定用户代码的记录。
   * @param employeeCode 用户代码
   * @return 返回用户信息
   * @throws Exception
   */
  public Employee getEmployee(String employeeCode) throws Exception {
    String application = (String)CmisConstance.getParameterSettings().get("application");
    PreparedStatement pstmt = null;
    try {
      //String userId = (String)icbc.cmis.base.CmisConstance.getParameterSettings().get("dbUserName");
      //String userPass = (String)icbc.cmis.base.CmisConstance.getParameterSettings().get("dbUserPass");
      //getConnection(userId ,userPass );
	  this.getConnection("missign");
	  String sql = null;
      //取柜员基本信息
	  if(pstmt==null){
        sql = "select EMPLOYEE_CODE, EMPLOYEE_NAME, MDB_SID, EMPLOYEE_PASSWD, EMPLOYEE_USEFUL, MDB_FLAG, MDB_TIME, EMPLOYEE_DELETE_FLAG, PASSWD_TIME, EMPLOYEE_ENABLE_FLAG, EMPLOYEE_NOTESID, EMPLOYEE_EMAIL, AREA_NAME,BANK_FLAG,'0' ZHUJI_FLAG,'0' WORLD_FLAG,'zh_CN' LANG_CODE,'00000000' ZONE_CODE "
                + "from mag_employee,mag_area where AREA_CODE = MDB_SID and EMPLOYEE_CODE = ?";
        pstmt = conn.prepareCall(sql);
	  }
	  
      pstmt.setString(1,employeeCode);
      ResultSet rs = pstmt.executeQuery();
      if(!rs.next()) {
        throw new TranFailException("cmisLoginDAO001","icbc.missign.LoginDAO","柜员号没有在系统中登记!","柜员号没有在系统中登记!");
      }
      employee.setEmployeeCode(employeeCode);
      employee.setEmployeeName(rs.getString("EMPLOYEE_NAME"));
      employee.setMdbSID(rs.getString("MDB_SID"));
      employee.setEmployeePasswd(rs.getString("EMPLOYEE_PASSWD"));
      employee.setEmployeeUseful(rs.getString("EMPLOYEE_USEFUL"));
      employee.setMdbFlag(rs.getString("MDB_FLAG"));
      employee.setMdbTime(rs.getString("MDB_TIME"));
      employee.setEmployeeDeleteFlag(rs.getString("EMPLOYEE_DELETE_FLAG"));
      employee.setPasswdTime(rs.getString("PASSWD_TIME"));
      employee.setEmployeeEnableFlag(rs.getString("EMPLOYEE_ENABLE_FLAG"));
      employee.setEmployeeNotesID(rs.getString("EMPLOYEE_NOTESID"));
      employee.setEmployeeEmail(rs.getString("EMPLOYEE_EMAIL"));
      employee.setAreaName(rs.getString("AREA_NAME"));
      employee.setBankFlag(rs.getString("BANK_FLAG"));
      employee.setZhujiFlag(rs.getString("ZHUJI_FLAG"));
      employee.setWorldFlag(rs.getString("WORLD_FLAG"));
      employee.setLangCode(rs.getString("LANG_CODE"));
	  employee.setZoneCode(rs.getString("ZONE_CODE"));

//      TableBean dict_sys = new TableBean();
//      TableBean mag_sys_major = new TableBean();
//      Hashtable hTable=(Hashtable)CmisConstance.getDictParam();
//      dict_sys = (TableBean)hTable.get("mag_sys");
//      mag_sys_major = (TableBean)hTable.get("mag_sys_major");

      //取柜员角色信息
//      sql = "select employee_area,area_name,employee_major,major_name,employee_class,class_name,employee_func,can_scan,bank_flag,mag_sys.sys_code,mag_sys.sys_name from mag_employee_major,mag_major,MAG_EMPLOYEE_CLASS,mag_area,mag_sys,mag_sys_major where employee_code = ? and employee_delete_flag = '0' and employee_major = mag_sys_major.major_code(+) and mag_sys_major.sys_code = mag_sys.sys_code and employee_major = mag_major.major_code(+) and employee_class = class_code(+) and employee_area = area_code and EMPLOYEE_MAJOR in (select app_major_code from mag_application_new where application = ? ) order by employee_major";
      sql = "select employee_area,area_name,employee_major,major_name,employee_class,class_name,employee_func,can_scan,bank_flag,ms.sys_code,ms.sys_name,isdefault from mag_employee_major,mag_major mm,MAG_EMPLOYEE_CLASS mec,mag_area,mag_sys ms,mag_sys_major where employee_code = ? and employee_delete_flag = '0' and employee_major = mag_sys_major.major_code(+) and mag_sys_major.sys_code = ms.sys_code and employee_major = mm.major_code(+) and employee_class = class_code(+) and employee_area = area_code and employee_major = mec.major_code and mm.lang_code=? and mec.lang_code=? and ms.lang_code=?  order by employee_major";
      pstmt = conn.prepareCall(sql);
      pstmt.setString(1,employeeCode);
	  pstmt.setString(2,employee.getLangCode());
	  pstmt.setString(3,employee.getLangCode());
	  pstmt.setString(4,employee.getLangCode());
//      pstmt.setString(2,application);
      rs = pstmt.executeQuery();
      //为兼容老版本，majors仍采用Vector封装的String数组
      ArrayList roles = new ArrayList();
      Vector majors = new Vector();
      Vector systems = new Vector();
      HashSet set = new HashSet();
      HashSet set2 = new HashSet();
      Role defaultRole = null;
//      HashSet managerMajors = new HashSet();      
      HashSet subApplication = getSubApplication();
      while(rs.next()) {
        Role role = new Role(rs.getString(3),rs.getString(4),rs.getInt(5),
                             rs.getString(6),rs.getInt(8) == 1,rs.getString(7),
                             rs.getString(1),rs.getString(2),rs.getInt(9),rs.getString(10),rs.getString(11),employee.getLangCode());

		if(subApplication.contains(role.getMajorCode())){
	        roles.add(role);
			if(defaultRole==null&&rs.getString(12).equals("1")) defaultRole = role;	        
	        String[] major = {role.getMajorCode(),role.getMajorName()};
	        if(!set.contains(role.getMajorCode())) {
	          majors.add(major);
	          set.add(role.getMajorCode());
	            String[] system = {role.getSysCode(),role.getSysName()};
	            if(!set2.contains(role.getSysCode())){
	              systems.add(system);
	              set2.add(role.getSysCode());
	            }
	        }
	        
//	        if(role.getRoleCode()==5){
//	        	if(!managerMajors.contains(role.getMajorCode()))
//	      			managerMajors.add(role.getMajorCode());
//	        }
		}
      }

//	  if(managerMajors.size()!=0){
//        Role role = new Role("098","其他信息管理",1,"",false,"","00000000","aaa",0,"03","公共信息管理");
//        roles.add(role);
//        String[] major = {"098","其他信息管理"};
//        majors.add(major);
//	  	
//	  }

      employee.setSystems(systems);
//	  employee.setManagerMajors(managerMajors);
      employee.setRoles(roles);
      if(defaultRole!=null){
	      employee.setCurrentRole(defaultRole.getMajorCode(),defaultRole.getRoleCode());	
      }
      else if(roles.size() > 0) {
        Role role = (Role)roles.get(0);
        employee.setCurrentRole(role.getMajorCode(),role.getRoleCode());
      }
      employee.setMajors(majors);
      
      pstmt.close();
      
      
    } catch (Exception ex) {
      throw ex;
    } finally {
      if(pstmt != null) pstmt.close();
      if(conn != null) this.closeConnection();
    }
    return employee;
  }
  
  private HashSet getSubApplication(){
	HashSet set = new HashSet();
	String subApplication = (String)CmisConstance.getParameterSettings().get("subApplication");
	StringTokenizer token = new StringTokenizer(subApplication,"|");
	while (token.hasMoreTokens()) {
		set.add(token.nextToken());
     	}
	return set;
  }

  //该柜员的外部柜员是否存在,返回柜员号；停用的返回“stop”；不存在的返回“false”
  public String isEmployeeExisted(String accountCode) throws TranFailException{
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql ="select count(*) from mag_employee_comparison WHERE outside_employee_code = ? and rownum=1";
    String sql2 = "select outside_employee_code from mag_employee_comparison where inside_employee_code = ?";
    String sql3 = "select EMPLOYEE_DELETE_FLAG from mag_employee where employee_code=?";
    int n =0;
    try {
        this.getConnection("missign");
        pstmt = conn.prepareCall(sql);
        pstmt.setString(1,accountCode);
        rs = pstmt.executeQuery();
        if(rs.next())
          n = rs.getInt(1);
        if(pstmt != null) 
          pstmt.close();
        
        if(n>=1){
          pstmt = conn.prepareStatement(sql3);
          pstmt.setString(1,accountCode);
          rs = pstmt.executeQuery();
          String flag = null;
          if(rs.next())
            flag = rs.getString(1);
          closeConnection();
          if(flag == null || flag.equals("1"))
            return "stop";
          else
            return "true";
        }
        else{
          pstmt = conn.prepareStatement(sql2);
          pstmt.setString(1,accountCode);
          rs = pstmt.executeQuery();
          String ret = "false";
          if(rs.next())
            ret = rs.getString(1);
          if(pstmt != null) 
            pstmt.close();
          closeConnection();
          return ret;
        }
        
    }catch(Exception e){
        try{
            if(pstmt != null) pstmt.close();
        }catch(Exception e1){}
        closeConnection();
        throw new TranFailException("isEmployeeExisted","LoginDAO.isEmployeeExisted()","该柜员在内部系统中不存在","该柜员在内部系统中不存在");
    }
  }
  
  //取指定外部柜员的下的所有内部柜员的柜员号，地区，默认业务
  public Vector getBranchDetail(String accountCode) throws TranFailException{
    PreparedStatement pstmt = null;
    /*
    String isOverSea = null;
    String isOverSeaFlag = null;
    try{
      isOverSea = (String)CmisConstance.getParameterSettings().get("OverSeaFlag");
      if(isOverSea!=null&&isOverSea.equals("true"))
		isOverSeaFlag = "1";
	  else
		isOverSeaFlag = "0";
    }
    catch(Exception e){
	  isOverSeaFlag = "0";
    }
    */
    //ResultSet rs = null;
    //String sql = "select me.outside_employee_code from mag_employee_comparison me where me.outside_employee_code = (select m.outside_employee_code from mag_employee_comparison m where m.inside_employee_code=?)";
    try{
      this.getConnection("missign");
      //pstmt = conn.prepareStatement(sql);
//      pstmt.setString(1,accountCode);
//      ResultSet rs2 = pstmt.executeQuery();
      Vector branch = new Vector();
//      if(rs2.next()){
//        String outsideCode = rs2.getString(1);
        employee.setOutsideEmpCode(accountCode);
//        if(rs2.next()){
//          pstmt.close();    
          String sql = //"select * from (select me.employee_code,ma.area_code,decode(cm.local_name,null,ma.area_name,cm.local_name),msm.sys_code,mem.employee_major,mem.employee_class,rank() over(partition by mem.employee_code order by mem.isdefault desc,mem.employee_major,mem.employee_class) cnt from mag_employee_major mem,mag_employee_comparison mec,mag_area ma,mag_employee me,mag_sys_major msm,cmis3.minit cm where mec.inside_employee_code=me.employee_code and mec.inside_employee_code=mem.employee_code and me.employee_delete_flag='0' and me.mdb_sid=ma.area_code and ma.area_code=cm.local_code(+) and mem.employee_major=msm.major_code and mec.outside_employee_code =?) where cnt=1 ";
			"select *"
			+"  from (select me.employee_code,"
			+"               ma.area_code,"
			+"               decode(cm.local_name, null, ma.area_name, cm.local_name),"
			+"               msm.sys_code,"
			+"               mem.employee_major,"
			+"               mem.employee_class,"
			+"               rank() over(partition by mem.employee_code order by mem.isdefault desc, mem.employee_major, mem.employee_class) cnt"
			+"          from mag_employee_major      mem,"
			+"               mag_employee_comparison mec,"
			+"               mag_area                ma,"
			+"               mag_employee            me,"
			+"               mag_sys_major           msm,"
			+"               cmis3.minit             cm"
			+"         where mec.inside_employee_code = me.employee_code"
			+"           and mec.inside_employee_code = mem.employee_code"
			+"           and me.employee_delete_flag = '0'"
			+"           and me.mdb_sid = ma.area_code"
			+"           and ma.area_code = cm.local_code(+)"
			+"           and mem.employee_major = msm.major_code"
			+"           and mec.outside_employee_code = ?)"
			+" where cnt = 1";
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1,accountCode);
		  //pstmt.setString(2,isOverSeaFlag);
          ResultSet rs3 = pstmt.executeQuery();
          while(rs3.next()){
            String[] tempStr= new String[6];
            tempStr[0] = rs3.getString(1); //employee_code
            tempStr[1] = rs3.getString(2);//area_code
            tempStr[2] = rs3.getString(3);//area_name
            tempStr[3] = rs3.getString(4);//sys_code
            tempStr[4] = rs3.getString(5);//employee_major
            tempStr[5] = rs3.getString(6);//employee_class
			//tempStr[6] = rs3.getString(7);//lang_code
            branch.add(tempStr);
          }
//        }
//      }
      if(pstmt != null) 
          pstmt.close();
      closeConnection();
      return branch;
    }catch(Exception e){
        try{
            if(pstmt != null) pstmt.close();
        }catch(Exception e1){}
        closeConnection();
        throw new TranFailException("getBranchDetail","LoginDAO.getBranchDetail()","查询柜员在内部系统中内部柜员信息失败","查询柜员在内部系统中内部柜员信息失败");
    }
  }
  
//  public boolean configIDAndType(String accountCode) throws TranFailException{
//    PreparedStatement pstmt = null;
//    ResultSet rs = null;
//    String sql ="select id,id_type from mag_employee WHERE EMPLOYEE_CODE = ?";
//    
//    String id = null;
//    String idType = null;
//    boolean ret = false;
//    try {
//        this.getConnection("missign");
//        pstmt = conn.prepareCall(sql);
//        pstmt.setString(1,accountCode);
//        rs = pstmt.executeQuery();
//        if(rs.next()){
//          id = rs.getString(1);
//          idType = rs.getString(2);
//          if(id != null && idType != null){
//            ret = true;
//          }
//        }
//        else
//          throw new TranFailException("LoginDAO","LoginDAO.configIDAndType()","该柜员号不存在");
//        if(pstmt != null) 
//          pstmt.close();
//        closeConnection();
//        return ret;
//    }catch(Exception e){
//        try{
//            if(pstmt != null) pstmt.close();
//        }catch(Exception e1){}
//        closeConnection();
//        throw new TranFailException("LoginDAO","LoginDAO.configIDAndType()","查找ID和ID类型失败");
//    }
//  }
  
  //取密码密文
  public boolean configPwdCrypto(String accountCode, String crypto) throws TranFailException{
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql ="select EMPLOYEE_PASSWD from MAG_EMPLOYEE_PASS WHERE EMPLOYEE_CODE = ?";
    String update_sql ="update MAG_EMPLOYEE_PASS set EMPLOYEE_PASSWD = ? WHERE EMPLOYEE_CODE = ?";
    //String insert_sql ="insert into MAG_EMPLOYEE_PASS (EMPLOYEE_CODE,EMPLOYEE_PASSWD) values(?,?)";
    
    //String passEncode = null;
    String crypto2 = null;
    try {
        this.getConnection("missign");
        pstmt = conn.prepareCall(sql);
        pstmt.setString(1,accountCode);
        rs = pstmt.executeQuery();
        if(rs.next()){
          crypto2 = rs.getString(1);
          //passEncode2 = SSICTool.genSSICpass(pass);
          if(!crypto.equals(crypto2)){
            pstmt.close();
            pstmt = conn.prepareStatement(update_sql);
            pstmt.setString(1,crypto);
            pstmt.setString(2,accountCode);
            int n = pstmt.executeUpdate();
            if(n == 0){
              throw new TranFailException("LoginDAO","LoginDAO.storePass()","根新密码密文失败");
            }
          }
        }
        else{
          pstmt.close();
          closeConnection();
          return false;
//          passEncode2 = SSICTool.genSSICpass(pass);
//          pstmt = conn.prepareStatement(insert_sql);
//          pstmt.setString(1,accountCode);
//          pstmt.setString(2,passEncode2);
//          int n = pstmt.executeUpdate();
//          if(n == 0){
//            throw new TranFailException("LoginDAO","LoginDAO.storePass()","插入密码密文失败");
//          }
        }
        if(pstmt != null) 
          pstmt.close();
        conn.commit();
        closeConnection();
        return true;
    }catch(Exception e){
        try{
            if(pstmt != null) pstmt.close();
        }catch(Exception e1){}
        closeConnection();
        throw new TranFailException("LoginDAO","LoginDAO.storePass()","存入密码密文失败");
    }
  }

  /**
   * @param employee
   */
  public void setEmployeeRole(Employee employee) {
    // TODO 自动生成方法存根
    this.employee=employee;
  }
}