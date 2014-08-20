/*
 * 创建日期 2007-5-8
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import java.util.*;
import icbc.missign.Employee;
import icbc.cmis.base.*;
import java.sql.*;
import oracle.jdbc.driver.*;

/**
 * @author zjfh-liny
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class EmployeeLanguageSettingDao extends CmisDao {
	public EmployeeLanguageSettingDao(icbc.cmis.operation.CmisOperation op) {
			super(op);
		} 
	public IndexedDataCollection langAvailableList() throws Exception{
		IndexedDataCollection iResult = new IndexedDataCollection();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		
		try{
		
		getConnection("missign");
		sql = "select lang_code, lang_name from mag_language ";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		while(rs.next()){
			KeyedDataCollection kdcoll = new KeyedDataCollection();
			kdcoll.setName("languageavailable");
			DataElement dfield1 = new DataElement();
			DataElement dfield2 = new DataElement();
			
			dfield1.setName("lang_code");
			dfield2.setName("lang_name");
			
			kdcoll.addElement(dfield1);
			kdcoll.addElement(dfield2);
			
			String str1 = rs.getString(1);
			String str2 = rs.getString(2);
			if (str1 == null) {
				str1 = "";
			}
			if (str2 == null) {
				str2 = "";
			}
			kdcoll.setValueAt("lang_code", str1);
			kdcoll.setValueAt("lang_name", str2);
			iResult.addElement(kdcoll);
		}
		
		pstmt.close();
		closeConnection();
	   }catch(Exception e){}
		return iResult;
		
	}
	public String defaultLanguage(String employee_code) throws Exception{
	String empLang = null;
	java.sql.ResultSet rs = null;
	java.sql.PreparedStatement pstmt = null;
	String sql = null;
	try{
		getConnection("missign");
		sql = "select lang_name from mag_language where lang_code=" +
			"(select lang_code from  mag_employee where employee_code=?)";

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, employee_code);
		
		rs = pstmt.executeQuery();
		
		while(rs.next()){
		
		String str1 = rs.getString(1);
		if(str1 == null){ 
			str1="";
		    }
		                 
		empLang = str1;
		}
		pstmt.close();
		closeConnection();
	}catch(Exception e){}
	return empLang;
	}
	
	
}
