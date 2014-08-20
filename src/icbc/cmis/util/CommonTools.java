package icbc.cmis.util;

import java.util.*;
import java.sql.*;

import icbc.cmis.base.*;
import oracle.jdbc.driver.*;

/**
 * Insert the type's description here.
 * Creation date: (2002-1-7 17:42:19)
 * @author: Administrator
 */
public class CommonTools {
  private String iYearInitMonth;
  private String iYearLastMonth;

  public CommonTools() {
      super();
  }

public String patchWhere( String patchName, Object patchValue ){
String retString=null;
   retString = patchString( patchValue );
   if( retString.trim().length() == 0 )
      return "";
   else
      return " and "+patchName+"='"+patchString(patchValue)+"' ";
}

public String patchString( Object patchValue ){
        if (patchValue instanceof java.lang.String) {
            return (String)patchValue;
        } else if (patchValue instanceof java.lang.Integer) {
            return ( (Integer)patchValue).toString();
        } else if (patchValue instanceof java.lang.Double){
            return ((Double)patchValue).toString();
        } else if (patchValue instanceof java.lang.Float) {
            return ((Float)patchValue).toString();
        } else {
            return patchValue + "";
        }
      }

public String getStringAsConvert(  String sourceString, int i ){
   return(String.valueOf( Integer.parseInt( sourceString ) + i ));
}

public void setDataElement( KeyedDataCollection kdcoll, String elementName, Object o )throws Exception{
try{
   DataElement dfield= createDataElement( elementName );
   try {
      kdcoll.removeElement( elementName );
   } catch (Exception e) {}

   kdcoll.addElement(dfield);
   kdcoll.setValueAt(elementName, o );
}
   catch (Exception ee)
   {
      throw new TranFailException("cmisutil602","CommonTools","创建数据元出错"+elementName);
   }
}

public DataElement createDataElement( String elementName ){
   DataElement dfield= new DataElement();
   dfield.setName(elementName);
   return dfield;
}

  public int getMonthsBetween(String date1, String date2 ){
          int Year1= Integer.parseInt(date1.substring(0,4));
          int Year2= Integer.parseInt(date2.substring(0,4));
          int Month1= Integer.parseInt( date1.substring(4,6));
          int Month2= Integer.parseInt( date2.substring(4,6));

          return( Year1*12+Month1 - Year2*12 - Month2 );
  }

  public String getiYear(String date, int i ){
          String thisYear=date.substring(0,4);
          int iYear=Integer.parseInt(thisYear)+i;
          return( String.valueOf(iYear));
  }

  public String getiYearLastMonth( String date, int i ){
          String iYear="";
          iYear=getiYear(date, i);
          iYearLastMonth=iYear+"12";
          return iYearLastMonth;
  }

  public String getiYearInitMonth( String date, int i ){
          String iYear="";
          iYear=getiYear(date, i);
          iYearInitMonth=iYear+"00";
          return iYearInitMonth;
  }

  public boolean isYearHeadEnd( String date ){
     String month=date.substring(4,6);
     if( month.equals("00") || month.equals("12"))
          return true;
     else
          return false;
}

public void setVectorElement( Vector invector, KeyedDataCollection kdcoll, String elementName )throws Exception{
try{
   if( kdcoll.isElementExist( elementName ))
      kdcoll.removeElement( elementName );
   invector.add( elementName );
}
   catch (Exception ee)
   {
      throw new TranFailException("cmisutil602","CommonTools","创建数据元出错"+elementName);
   }
}

  public boolean isYearEnd( String date ){
     String month=date.substring(4,6);
     if( month.equals("12"))
          return true;
     else
          return false;
  }

  public boolean isYearBegin( String date ){
     String month=date.substring(4,6);
     if( month.equals("00"))
          return true;
     else
          return false;
}

public String getClientAdmin( String ClientCode, String AreaCode ) throws java.lang.Exception {
  int retFlag ;
  icbc.cmis.service.JDBCProcedureService srv=null;
  KeyedDataCollection keyed = new KeyedDataCollection();
  KeyedDataCollection kResult = new KeyedDataCollection();
  kResult.setName("procResult");


  Vector inParam = new Vector();
  Vector outParam = new Vector();
  inParam.add( "ClientCode");
  inParam.add( "AreaCode");
  setDataElement( keyed, "ClientCode", ClientCode);
  setDataElement( keyed, "AreaCode", AreaCode);
outParam.add("ret_flag");
outParam.add("ret_errMsg");
outParam.add("clientAdmin");

try{
srv = new icbc.cmis.service.JDBCProcedureService(new icbc.cmis.operation.DummyOperation());
  srv.getConn();

  retFlag = srv.executeProcedure("pack_commontools.proc_readAdministrator",keyed,inParam,outParam,kResult);
  if(retFlag != 0){
         srv.closeConn();
         throw new TranFailException("cmisutil602","CommonTools","调用读主管信贷员存贮过程出错"+kResult.getValueAt("ret_errMsg"));
  }else{
         srv.closeConn();
         }
}
   catch (TranFailException ee)
   {
      srv.closeConn();
      throw ee;
   }
   catch (Exception ee)
   {
         srv.closeConn();
      throw new TranFailException("cmisutil602","CommonTools","调用读主管信贷员存贮过程出错");
   }

  return (String)kResult.getValueAt("clientAdmin");
}

public String convertDate( String sourceDate, int changeValue ) throws java.lang.Exception {
  int retFlag ;
  icbc.cmis.service.JDBCProcedureService srv=null;
  KeyedDataCollection keyed = new KeyedDataCollection();
  KeyedDataCollection kResult = new KeyedDataCollection();
  kResult.setName("procResult");


  Vector inParam = new Vector();
  Vector outParam = new Vector();
  inParam.add( "sourceDate");
  inParam.add( "changeValue");
  setDataElement( keyed, "sourceDate", sourceDate);
  setDataElement( keyed, "changeValue", String.valueOf( changeValue ));
outParam.add("ret_flag");
outParam.add("ret_errMsg");
outParam.add("destDate");

try{
srv = new icbc.cmis.service.JDBCProcedureService(new icbc.cmis.operation.DummyOperation());
  srv.getConn();

  retFlag = srv.executeProcedure("pack_commontools.proc_convertdate",keyed,inParam,outParam,kResult);
  if(retFlag != 0){
         srv.closeConn();
         throw new TranFailException("cmisutil602","CommonTools","调用转换日期存贮过程出错"+kResult.getValueAt("ret_errMsg"));
  }else{
         srv.closeConn();
         }
}
   catch (TranFailException ee)
   {
      srv.closeConn();
      throw ee;
   }
   catch (Exception ee)
   {
         srv.closeConn();
         throw new TranFailException("cmisutil602","CommonTools","调用转换日期存贮过程出错");
   }

  return (String)kResult.getValueAt("destDate");
}

}