package icbc.cmis.operation;

import java.util.*;
import icbc.cmis.base.*;
public class ExampleLogin_cn extends CmisOperation{
	
                                                              
                                                              /**
 * LogonOp constructor comment.
 */
public ExampleLogin_cn() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-5-9 10:28:07)
 */
private void db() throws Exception{
	System.out.println("db*");
	SqlTool tool = null;
	IndexedDataCollection icoll = new IndexedDataCollection();
	icoll.setName("iResult");
	try{
		tool = new SqlTool(this);
		  String userId = (String)CmisConstance.getParameterSettings().get("dbUserName");
	        String userPass = (String)CmisConstance.getParameterSettings().get("dbUserPass");
	   
		
		tool.getConn(userId,userPass);
		tool.executeUpdate("delete from xgl_test");
		tool.executeUpdate("insert into xgl_test values('appSrv:34fg朱镕鎔基')");
		tool.executeUpdate("insert into xgl_test values('appSrv:fdh郭芃gh345')");
		tool.executeUpdate("insert into xgl_test values('appSrv:4aa啊4aa丂aa')");
		tool.executeUpdate("insert into xgl_test values('appSrv:张旻')");
		tool.executeUpdate("insert into xgl_test values('appSrv:中国人民解放军。')");
		tool.executeUpdate("insert into xgl_test values('appSrv:asdfas23432dsf')");
		tool.executeUpdate("insert into xgl_test values('data1 from jsp:"+getStringAt("data1")+"')");
		tool.executeUpdate("insert into xgl_test values('data2 from jsp:"+getStringAt("data2")+"')");
		tool.executeUpdate("insert into xgl_test values('data3 from jsp:"+getStringAt("data3")+"')");
		tool.executeUpdate("insert into xgl_test values('data4 from jsp:"+getStringAt("data4")+"')");
/*
		tool.executeUpdate("insert into xgl_test values('GBK->CP1383 encode appSrv:"+new String("appSrv:34fg朱镕鎔基".getBytes("GBK"),"CP1383")+"')");
		tool.executeUpdate("insert into xgl_test values('GBK->CP1383 encode appSrv:"+new String("appSrv:34fg朱镕鎔基".getBytes("GBK"),"CP1383")+"')");
		tool.executeUpdate("insert into xgl_test values('GBK->8859_1 encode appSrv:"+new String("appSrv:34fg朱镕鎔基".getBytes("GBK"),"8859_1")+"')");
		tool.executeUpdate("insert into xgl_test values('GBK->GB2312 encode appSrv:"+new String("appSrv:34fg朱镕鎔基".getBytes("GBK"),"GB2312")+"')");
		tool.executeUpdate("insert into xgl_test values('GBK->GB2312 encode appSrv:"+new String("appSrv:34fg朱镕鎔基".getBytes("GBK"),"GB2312")+"')");
		tool.executeUpdate("insert into xgl_test values('GBK->GB2312 encode appSrv:"+new String("appSrv:34fg朱镕鎔基".getBytes("GBK"),"GB2312")+"')");
		tool.executeUpdate("insert into xgl_test values('GBK->CP1383 encode data1 from jsp:"+new String(getStringAt("data1").getBytes("GBK"),"CP1383")+"')");
		tool.executeUpdate("insert into xgl_test values('GBK->8859_1 encode data2 from jsp:"+new String(getStringAt("data2").getBytes("GBK"),"8859_1")+"')");
		tool.executeUpdate("insert into xgl_test values('GBK->GB2312 encode data3 from jsp:"+new String(getStringAt("data3").getBytes("GBK"),"GB2312")+"')");
		tool.executeUpdate("insert into xgl_test values('GBK->GB2312 encode data4 from jsp:"+new String(getStringAt("data4").getBytes("GBK"),"GB2312")+"')");
	
		*/
		tool.commit();
		
		Vector v = new Vector();
		v.add(0,"msg");
		tool.executeQueryFix(v,"xgl_test","",icoll);
		tool.closeconn();
		for(int i = 0;i<icoll.getSize();i++){
			KeyedDataCollection kcoll = (KeyedDataCollection)icoll.getElement(i);
			String msg = (String)kcoll.getValueAt("msg");
			//kcoll.addElement("enmsg",new String(msg.getBytes("GB2312"),"GBK"));
			System.out.println("data from db:"+msg);
			//System.out.println("enc data from db:"+new String(msg.getBytes("GB2312"),"GBK"));
			
		}
		
		if(isElementExist("iResult")){
			removeDataField("iResult");
		}
		addIndexedDataCollection(icoll);
	}catch(Exception e){
		addIndexedDataCollection(icoll);
		tool.closeconn();
		e.printStackTrace();
		throw e;
	}
	
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-28 14:12:28)
 */
public void execute()throws Exception
{
	try
		{
			//业务逻辑处理
			String acct = getStringAt("accountCode");
			addSessionData("Login","true");
			if(acct == null)
				throw new Exception("该柜员无效！");
			addSessionData("accountCode",acct);
		//	setReplyPage("/icbc/cmis/examplequery.jsp");
		sss();
		db();
		setReplyPage("/test/result.jsp");
		
		
		}catch (Exception e){
			new TranFailException("xdtz22125","LogonOp.execute()",e.getMessage());
			setReplyPage("/icbc/cmis/error.jsp");
		}


}
/**
 * Insert the method's description here.
 * Creation date: (2002-5-9 10:10:08)
 */
private void sss() throws Exception{
		System.out.println("file.encoding:"+System.getProperty("file.encoding"));
	    System.out.println("Cp1381->GBK:"+new String(getStringAt("data1").getBytes("Cp1381"),"GBK") );
		System.out.println("iso-8859-1->GBK:"+new String(getStringAt("data1").getBytes("iso-8859-1"),"GBK") );
		System.out.println("gb2312->GBK:"+new String(getStringAt("data1").getBytes("gb2312"),"GBK") );
		System.out.println("GBK->gb2312:"+new String(getStringAt("data1").getBytes("GBK"),"gb2312") );
		System.out.println("GBK->GBK:"+new String(getStringAt("data1").getBytes("GBK"),"GBK") );
		System.out.println("data2:"+getStringAt("data2"));
		System.out.println("data3:"+getStringAt("data3"));
		System.out.println("data4:"+getStringAt("data4"));
		System.out.println("sssssssssssssssss");
}
}
