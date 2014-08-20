package icbc.cmis.service;

import icbc.cmis.base.*;
import java.sql.*;
import java.util.HashMap;
/**
 * Insert the type's description here.
 * Creation date: (2002-1-7 9:44:19)
 * @author: Administrator
 */
public class ParaDictService extends CmisDao {
/**
 * GeneralSQLService constructor comment.
 */
public ParaDictService() {
	// super();
	super(new icbc.cmis.operation.DummyOperation());
}
/**
 * GeneralSQLService constructor comment.
 * @param dbResourceName1 java.lang.String
 */
public ParaDictService(String dbResourceName1) {
	// super(dbResourceName1);
	super(dbResourceName1,new icbc.cmis.operation.DummyOperation());
}
public void initialDictTables() throws java.lang.Exception {

   Statement stmt = null;
    ResultSet rs = null;
    try {

        getConnection("missign");
        stmt = conn.createStatement();
		String sql_get_allapp = "select distinct app from genpara ";
		rs = stmt.executeQuery(sql_get_allapp);
		HashMap apps = new HashMap();
		int count = 0;
		while (rs.next()){
			count++;
			apps.put(Integer.toString(count),rs.getString(1));
		}
		
		for(int i=1;i<=count;i++){

	        String sql ="select paratype , paracode , paravalue from genpara where app = '" + apps.get(Integer.toString(i)) + "' order by paratype,paracode ";
	        rs = stmt.executeQuery(sql);
 	        ParaBean selfBean = new ParaBean();
    	    while (rs.next()) {
	            String paraType = rs.getString(1);
	            String key = rs.getString(2);
	            String value = rs.getString(3);
	            if (paraType == null)
	                paraType = "nullparaType";
	            if (key == null)
	                key = "nullkey";
	            if (value == null)
	                value = "";
	            selfBean.addRow(paraType, key, value);
	        }
	            
			synchronized(this){
				try{
					CmisConstance.getDictParam().remove(("genpara"+apps.get(Integer.toString(i))).toLowerCase());
				}catch(Exception e){}
		        CmisConstance.getDictParam().put(("genpara"+apps.get(Integer.toString(i))).toLowerCase(), selfBean);
			}	        
		}

        rs.close();
        stmt.close();
        closeConnection();

} catch (Exception e) {
    if (rs != null) {
        try {
            rs.close();
        } catch (Exception ez) {
        }
    }
    if (stmt != null) {
        try {
            stmt.close();
        } catch (Exception ez) {
        }
    }
    try {
        closeConnection();
    } catch (Exception ez) {
    }
    throw e;
}
}
}
