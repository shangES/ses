package icbc.cmis.util;

import icbc.cmis.operation.*;
import icbc.cmis.base.*;
import java.util.*;
import java.io.*;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class InitOp extends CmisOperation {
	
	public InitOp() {
	}
	public void execute() throws java.lang.Exception {
		/**@todo: implement this icbc.cmis.operation.CmisOperation abstract method*/
		try{

			String action = getStringAt("act");
			if(action.equals("init")){
				initSys();
			}
			else if(action.equals("login")){
				initLogin();
			}
			else {
			  throw new Exception();
			}
			/* else if(action.equals("reupdateFunc")){
				reupdateFunc();
			}*/
		}
		catch(Exception e){
			throw e;
		}


	}
	

  private void initSys() throws Exception {
  	String initSysDate = null;
  	String initLimitedOp = null;
  	String tabType0 = null;
  	String tabType1 = null;
  	String tabType2 = null;
	String tabType10 = null;
	String tabType11 = null;
	String tabType12 = null;
  	String genPara = null;
    Vector res = new Vector(1);
  	try{
  	  initSysDate = getStringAt("sysdate");
  	}
  	catch(Exception e){}
  	try{
  	  initLimitedOp = getStringAt("limitedOp");
  	}
  	catch(Exception e){}
  	try{
  	  tabType0 = getStringAt("dictType0");
  	}
  	catch(Exception e){}
  	try{
  	  tabType1 = getStringAt("dictType1");
  	}
  	catch(Exception e){}
  	try{
  	  tabType2 = getStringAt("dictType2");
  	}
  	catch(Exception e){}
	try{
		  tabType10 = getStringAt("dictType10");
		}
		catch(Exception e){}
	try{
		  tabType11 = getStringAt("dictType11");
		}
		catch(Exception e){}
	try{
		  tabType12 = getStringAt("dictType12");
		}
		catch(Exception e){}
  	try{
  	  genPara = getStringAt("genpara");
  	}
  	catch(Exception e){}
  	
  	if(initSysDate!=null){
  	  if(initSysDate.equals("1")){
  	    this.setDictUpdatetMark("INITWORKDATE","1","cmis3");
  	    res.add("系统日期初始化已经准备进行");
  	  }
  	}
  	if(initLimitedOp!=null){
  	  if(initLimitedOp.equals("1")){
  	    this.setDictUpdatetMark("INITLIMITEDOP","1","cmis3");
  	    res.add("最大操作数初始化已经准备进行");
  	  }
  	}
  	if(tabType0!=null && !tabType0.equals("")){ 
  	  int pos = tabType0.indexOf("|");	  
  	  String tabOwner = tabType0.substring(0,pos);
  	  String tabName = tabType0.substring(pos+1);
  	  this.setDictUpdatetMark(tabName,"0",tabOwner.toLowerCase());
  	  res.add(tabName+"初始化已经准备进行");
  	}
  	if(tabType1!=null && !tabType1.equals("")){ 
  	  int pos = tabType1.indexOf("|");	  
  	  String tabOwner = tabType1.substring(0,pos);
  	  String tabName = tabType1.substring(pos+1);
  	  this.setDictUpdatetMark(tabName,"1",tabOwner.toLowerCase());
  	  res.add(tabName+"初始化已经准备进行");
  	}
  	if(tabType2!=null && !tabType2.equals("")){ 
  	  int pos = tabType2.indexOf("|");	  
  	  String tabOwner = tabType2.substring(0,pos);
  	  String tabName = tabType2.substring(pos+1);
  	  this.setDictUpdatetMark(tabName,"2",tabOwner.toLowerCase());
  	  res.add(tabName+"初始化已经准备进行");
  	}
	if(tabType10!=null && !tabType10.equals("")){ 
		  int pos = tabType10.indexOf("|");	  
		  String tabOwner = tabType10.substring(0,pos);
		  String tabName = tabType10.substring(pos+1);
		  this.setDictUpdatetMark(tabName,"10",tabOwner.toLowerCase());
		  res.add(tabName+"初始化已经准备进行");
		}
	if(tabType11!=null && !tabType11.equals("")){ 
			  int pos = tabType11.indexOf("|");	  
			  String tabOwner = tabType11.substring(0,pos);
			  String tabName = tabType11.substring(pos+1);
			  this.setDictUpdatetMark(tabName,"11",tabOwner.toLowerCase());
			  res.add(tabName+"初始化已经准备进行");
			}
	if(tabType12!=null && !tabType12.equals("")){ 
			  int pos = tabType12.indexOf("|");	  
			  String tabOwner = tabType12.substring(0,pos);
			  String tabName = tabType12.substring(pos+1);
			  this.setDictUpdatetMark(tabName,"12",tabOwner.toLowerCase());
			  res.add(tabName+"初始化已经准备进行");
			}
  	if(genPara!=null){
  	  if(genPara.equals("1")){
  	    this.setDictUpdatetMark("GENPARA","3","missign");
  	    res.add("票据和债券字典初始化已经准备进行");
  	  }
  	}
  	try{
          this.removeDataField("initResult");
    }
    catch(Exception e){}
        this.addDataField("initResult",res);
        this.setOperationDataToSession();
        this.setReplyPage("/util/util_InitRes.jsp");
  }
  
  
  private void initLogin() throws Exception {
    Vector tab0 = new Vector();
    Vector tab1 = new Vector();
    Vector tab2 = new Vector();
	Vector tab10 = new Vector();
	Vector tab11 = new Vector();
	Vector tab12 = new Vector();
  	InitOpDAO dao = new InitOpDAO(this);
  	try{
  	  tab0 = dao.getTablesByType("0");
  	  tab1 = dao.getTablesByType("1");
  	  tab2 = dao.getTablesByType("2");
	  tab10 = dao.getTablesByType("10");
	  tab11 = dao.getTablesByType("11");
	  tab12 = dao.getTablesByType("12");	
  	}
  	catch(Exception e){
  	  throw e;
  	}
  	try{
      this.removeDataField("mag_cache_tab0");
    }
    catch(Exception e){}
    this.addDataField("mag_cache_tab0",tab0);
    
    try{
      this.removeDataField("mag_cache_tab1");
    }
    catch(Exception e){}
    this.addDataField("mag_cache_tab1",tab1);
    
    try{
      this.removeDataField("mag_cache_tab2");
    }
    catch(Exception e){}
    this.addDataField("mag_cache_tab2",tab2);
    
	try{
		  this.removeDataField("mag_cache_tab10");
		}
	catch(Exception e){}
	this.addDataField("mag_cache_tab10",tab10);
	
	try{
		  this.removeDataField("mag_cache_tab11");
		}
	catch(Exception e){}
	this.addDataField("mag_cache_tab11",tab11);
		
	try{
		  this.removeDataField("mag_cache_tab12");
		}
	catch(Exception e){}
	this.addDataField("mag_cache_tab12",tab12);
				
    this.setOperationDataToSession();
    this.setReplyPage("/util/util_InitLogin.jsp");
  }
}
