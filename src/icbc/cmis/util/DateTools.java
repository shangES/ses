package icbc.cmis.util;

import java.util.Calendar;
import icbc.cmis.base.*;
/*
	日期格式化工具类
	code writen by zhouxj
	ver 1.02
	2003/08/06
	code modify by zhengzezhou
        for 资金集中配置项目
        2004-6-22
java.util.Calendar rightNow = java.util.Calendar.getInstance();
rightNow.set(1999,12,23);
String s_i= rightNow.toString();
System.out.println(s_i);
java.util.Date D_date=new java.util.Date(1993-1900,3,9);

//String s_d=D_date.toLocaleString();
String s_d=D_date.toString();
System.out.println(s_d);

输出：
java.util.GregorianCalendar[time=?,areFieldsSet=false,areAllFieldsSet=true,lenient=true,zone=java.util.SimpleTimeZone[id=Asia/Shanghai,offset=28800000,dstSavings=3600000,useDaylight=false,startYear=0,startMode=0,startMonth=0,startDay=0,startDayOfWeek=0,startTime=0,endMode=0,endMonth=0,endDay=0,endDayOfWeek=0,endTime=0],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=1999,MONTH=12,WEEK_OF_YEAR=21,WEEK_OF_MONTH=4,DAY_OF_MONTH=23,DAY_OF_YEAR=?,DAY_OF_WEEK=4,DAY_OF_WEEK_IN_MONTH=3,AM_PM=0,HOUR=11,HOUR_OF_DAY=11,MINUTE=34,SECOND=46,MILLISECOND=980,ZONE_OFFSET=28800000,DST_OFFSET=0]
Fri Apr 09 00:00:00 CST 1993

测试：
icbc.cmis.util.DateTools D1=new icbc.cmis.util.DateTools();

System.out.println(D1.formatYMD("19990908"));
System.out.println(D1.cStr(-90887));
System.out.println(D1.getStrDate(0030405));
System.out.println(D1.cInt("03.5433"));
*/
public class DateTools {
	private java.util.Calendar Cal_temp= java.util.Calendar.getInstance();
	private java.util.Date D_date=new java.util.Date();

/**
 * <p>计算贷款期限</p>
 * <p>用于资金集中配置项目</p>
 * <p>算法：
 * 003：三个月
 * 006：半年
 * 012：一年
 * 024：二年
 * 036：三年
 * 060：五年
 * 099：五年以上
 * 注：区间是前开后闭
 * modify by zhengzezhou 20051012
 * @param String beginDate 发放日
 * @param String endDate 到期日
 * @return String 贷款期限
 * @throws TranFailException
 */
public String calcLoanTerm(String beginDate, String endDate)
    throws TranFailException,Exception{
  //校验输入参数
  final String exceptionWord = "000";
  if (this.isEmpty(beginDate)) {
    return exceptionWord;
  }
  if (this.isEmpty(endDate)) {
    return exceptionWord;
  }
  if (!this.isDate(beginDate)) {
    return exceptionWord;
  }
  if (!this.isDate(endDate)) {
    return exceptionWord;
  }
  //计算
  java.util.Calendar begindate =
          new java.util.GregorianCalendar(
                  Integer.parseInt(beginDate.substring(0, 4)),
                  Integer.parseInt(beginDate.substring(4, 6)),
                  Integer.parseInt(beginDate.substring(6, 8)));
  java.util.Calendar enddate =
          new java.util.GregorianCalendar(
                  Integer.parseInt(endDate.substring(0, 4)),
                  Integer.parseInt(endDate.substring(4, 6)),
                  Integer.parseInt(endDate.substring(6, 8)));
  boolean i_bool = false;
  java.util.Calendar temp1 = (java.util.Calendar)begindate.clone();
  temp1.add(java.util.Calendar.MONTH, 3);//加上间隔
  i_bool = enddate.after(temp1);//比较先后
  if (!i_bool) {
    return "003"; //3个月
  }else {
    temp1 = (java.util.Calendar)begindate.clone();
    temp1.add(java.util.Calendar.MONTH, 6);//加上间隔
    i_bool = enddate.after(temp1);//比较先后
    if (!i_bool) {
      return "006"; //6个月
    } else {
      temp1 = (java.util.Calendar)begindate.clone();
      temp1.add(java.util.Calendar.MONTH, 12);//加上间隔
      i_bool = enddate.after(temp1);//比较先后
      if (!i_bool) {
        return "012"; //1年
      } else {
        temp1 = (java.util.Calendar)begindate.clone();
        temp1.add(java.util.Calendar.MONTH, 24);//加上间隔
        i_bool = enddate.after(temp1);//比较先后
        if (!i_bool) {
          return "024"; //2年
        } else {
          temp1 = (java.util.Calendar)begindate.clone();
          temp1.add(java.util.Calendar.MONTH, 36);//加上间隔
          i_bool = enddate.after(temp1);//比较先后
          if (!i_bool) {
            return "036"; //3年
          } else {
            temp1 = (java.util.Calendar)begindate.clone();
            temp1.add(java.util.Calendar.MONTH, 60);//加上间隔
            i_bool = enddate.after(temp1);//比较先后
            if (!i_bool) {
              return "060"; //5年
            } else {
              return "099"; //5年以上
            }
          }
        }
      }
    }
  }
}

/**
 * Insert the method's description here.
 * Creation date: (2003-5-21 16:15:00)
 * @return java.lang.String
 * 对日期变量(YYYYMMDD)进行加减以日期计算
 * D1.addDate("19990908",-30)) 结果为 19990809
 */
public String addDate(String s_Date,int i_num)throws Exception {
//	java.util.Calendar Cal_temp2= java.util.Calendar.getInstance();
	if(!isDate(s_Date)){ return "Not a valid date";}

    String y = s_Date.substring(0, 4);		//year
    int i_y = cInt(y);
    String m = s_Date.substring(4, 6);		//month
    int i_m = cInt(m)-1;					//month is from 00.
    String d = s_Date.substring(6, 8);		//day
    int i_d=cInt(d);

	Cal_temp.clear();
	Cal_temp.set(i_y,i_m,i_d);				//set(int year, int month, int date) Sets the values for the fields year, month, and date.
/*
	Cal_temp.set(Cal_temp.YEAR,i_y);
	Cal_temp.set(Cal_temp.MONTH,i_m);
	Cal_temp.set(Cal_temp.DATE,i_d);
*/
    Cal_temp.add(Calendar.DATE,i_num);     //add(Calendar.DATE, -5).

//    long l_k= Cal_temp.get(Cal_temp.time);
    i_y=Cal_temp.get(Calendar.YEAR);
    i_m=Cal_temp.get(Calendar.MONTH)+1;		//add a month
    i_d=Cal_temp.get(Calendar.DATE);

    int i_len;

    y="0000"+cStr(i_y);
    i_len=y.length();
    y=y.substring(i_len-4,i_len);			//取最右边4个字符

    m="00"+cStr(i_m);
    i_len=m.length();
    m=m.substring(i_len-2,i_len);			//取最右边2个字符

    d="00"+cStr(i_d);
    i_len=d.length();
    d=d.substring(i_len-2,i_len);			//取最右边2个字符


    String s_ret=y+m+d;

    return s_ret;
}
/**
 * Insert the method's description here.
 * Creation date: (2003-5-21 19:18:41)
 * @return java.lang.String
 * 对日期变量(YYYYMMDD)进行加减以月份计算
 * D1.addMonth("19990908",6) 结果为 20000308
 */
public String addMonth(String s_Date,int i_num)throws Exception {
//	java.util.Calendar Cal_temp2= java.util.Calendar.getInstance();
	if(!isDate(s_Date)){ return "Not a valid date";}

    String y = s_Date.substring(0, 4);		//year
    int i_y = cInt(y);
    String m = s_Date.substring(4, 6);		//month
    int i_m = cInt(m)-1;					//month is from 00.
    String d = s_Date.substring(6, 8);		//day
    int i_d=cInt(d);

	Cal_temp.clear();
	Cal_temp.set(i_y,i_m,i_d);				//set(int year, int month, int date) Sets the values for the fields year, month, and date.
/*
	Cal_temp.set(Cal_temp.YEAR,i_y);
	Cal_temp.set(Cal_temp.MONTH,i_m);
	Cal_temp.set(Cal_temp.DATE,i_d);
*/
    Cal_temp.add(Calendar.MONTH,i_num);     //add(Calendar.DATE, -5).

    i_y=Cal_temp.get(Calendar.YEAR);
    i_m=Cal_temp.get(Calendar.MONTH)+1;		//add a month
    i_d=Cal_temp.get(Calendar.DATE);

    int i_len;

    y="0000"+cStr(i_y);
    i_len=y.length();
    y=y.substring(i_len-4,i_len);			//取最右边4个字符

    m="00"+cStr(i_m);
    i_len=m.length();
    m=m.substring(i_len-2,i_len);			//取最右边2个字符

    d="00"+cStr(i_d);
    i_len=d.length();
    d=d.substring(i_len-2,i_len);			//取最右边2个字符


    String s_ret=y+m+d;

    return s_ret;
}
/**
 * Insert the method's description here.
 * Creation date: (2003-5-21 14:02:04)
 * @return int
 */
public int cInt(String s_temp) throws Exception {
    int i_temp;
    if (isInt(s_temp)) {
        i_temp = Integer.parseInt(s_temp);
    } else {
	     Exception ee=new Exception("Not an int number!");
        throw ee;
    }

    //doule d_num=Double.parseDouble((Sting)S_num);
/*
    //String toString(int i)
        String s_Ins=String.valueOf(this.getDoubleAt("InsAmount"));
        java.text.DecimalFormat df = new java.text.DecimalFormat();
		df.applyLocalizedPattern("#,##0.00");
		String ss = df.format(this.getDoubleAt("InsAmount"));
*/
    return i_temp;
}
/**
 * Insert the method's description here.
 * Creation date: (2003-5-21 14:48:35)
 * @return java.lang.String
 */
public String cStr(int i_temp) {
	String s_temp=Integer.toString(i_temp);
	return s_temp;
}
/**
 * Insert the method's description here.
 * Creation date: (2003-5-28 17:20:10)
 * 计算两个日期的间隔天数和比较两个日期的大小
 * @return long
 * DayBetween("19990830","19990901") 结果为2.
 *
 */
public long DayBetween(String s_from,String s_end) throws Exception{
	long l_fromDay;
	long l_endDay;

	String s_Date=s_from;

	if(!isDate(s_Date)){  Exception ee=new Exception("Not a valid Date!"); throw ee;}

    String y = s_Date.substring(0, 4);		//year
    int i_y = cInt(y);
    String m = s_Date.substring(4, 6);		//month
    int i_m = cInt(m)-1;					//month is from 00.
    String d = s_Date.substring(6, 8);		//day
    int i_d=cInt(d);

	Cal_temp.clear();
	Cal_temp.set(i_y,i_m,i_d);
	D_date=Cal_temp.getTime();
	l_fromDay=D_date.getTime()/(24*60*60*1000);		//将开始时间转成从since January 1, 1970, 00:00:00 GMT 算起的天数。

	s_Date=s_end;
	if(!isDate(s_Date)){ throw new Exception();}

    y = s_Date.substring(0, 4);		//year
    i_y = cInt(y);
    m = s_Date.substring(4, 6);		//month
    i_m = cInt(m)-1;					//month is from 00.
    d = s_Date.substring(6, 8);		//day
    i_d=cInt(d);

	Cal_temp.clear();
	Cal_temp.set(i_y,i_m,i_d);
	D_date=Cal_temp.getTime();
	l_endDay=D_date.getTime()/(24*60*60*1000);		//将开始时间转成从since January 1, 1970, 00:00:00 GMT 算起的天数。

	return l_endDay-l_fromDay;

}
/**
 * Insert the method's description here.
 * Creation date: (2003-5-21 14:58:33)
 * @return java.lang.String
 * 将日期YYYYMMDD转换为加"/"
 */
public String formatYMD(String s_Date)throws Exception {
	if(isDate(s_Date)){
		String s_retrun= s_Date.substring(0,4)+"/"+s_Date.substring(4,6)+"/"+s_Date.substring(6,8);
		return s_retrun;
	}else{
		return "Not a valid date!";
	}
	/*
	java.text.SimpleDateFormat dateFmt1 = new java.text.SimpleDateFormat(format);	// format:"yyyy.MM.dd G 'at' hh:mm:ss z"    ->>  1996.07.10 AD at 15:08:56 PDT

	java.util.Calendar c1 = java.util.Calendar.getInstance();
	c1.set(year,month-1,date,hour,minute,second);
	tmpStr1 = dateFmt1.format(c1.getTime());
	*/
}
/**
 * Insert the method's description here.
 * Creation date: (2003-5-21 14:58:33)
 * @return java.lang.String
 * 将日期YYYYMMDD转换为加"-"
 */
public String formatYMD2(String s_Date)throws Exception {
	if(isDate(s_Date)){
		String s_retrun= s_Date.substring(0,4)+"-"+s_Date.substring(4,6)+"-"+s_Date.substring(6,8);
		return s_retrun;
	}else{
		return "Not a valid date!";
	}

}
public String getStrDate(int i_date) throws Exception {
    String s_Date = cStr(i_date);
    if (isDate(s_Date)) {
        return s_Date;
    } else {
        return "Not a valid Date!";
    }

}
public String getStrDate(String s_date){

	return s_date;
}
public boolean isDate(String theStr) throws Exception {
    if (theStr.length() != 8)
        return false;
    String y = theStr.substring(0, 4);
    int i_y = cInt(y);
    String m = theStr.substring(4, 6);

    int i_m = cInt(m);
    if (i_m < 1 || i_m > 12)
        return false;

    String d = theStr.substring(6, 8);
    int i_d = cInt(d);

    int maxDays = 31;
    if (isInt(m) == false || isInt(d) == false || isInt(y) == false) {
        return (false);
    } else
        if (i_y < 1900 || i_y > 3000) {
            return (false);
        } else
            if (i_m == 4 || i_m == 6 || i_m == 9 || i_m == 11)
                maxDays = 30;
            else
                if (i_m == 2) {
                    if ((i_y % 4) > 0)
                        maxDays = 28;
                    else
                        if ((i_y % 100) == 0 && (i_y % 400) > 0)
                            maxDays = 28;
                        else
                            maxDays = 29;
                }
    if (i_d < 1 && i_d > maxDays) {
        return (false);
    }
    //         if (isBetween(d,1,maxDays) == false) { return(false);}

    else {
        return (true);
    }

}
public boolean isDigit(String theNum) {
    String theMask = "0123456789";
    if (isEmpty(theNum))
        return false;
    else
        if (theMask.indexOf(theNum) == -1)
            return false;
    return true;
}
public boolean isEmpty(String str) {
    //      str = trim(str);
    if ((str == null) || (str.length() == 0))
        return true;
    else
        return (false);
}
/**
 * Insert the method's description here.
 * Creation date: (2003-5-21 14:03:36)
 * @return boolean
 */
public boolean isInt(String theStr) throws Exception {
    boolean flag = true;
    theStr = trim(theStr);
    if (isEmpty(theStr))
        flag = true;
    else {
        if (theStr.substring(0, 1).equals("-")) //负数
            theStr = theStr.substring(1);
        for (int i = 0; i < theStr.length(); i++) {
            if (isDigit(theStr.substring(i, i + 1)) == false) {
                flag = false;
                break;
            }
        }
    }
    return flag;
}
/**
 * Insert the method's description here.
 * Creation date: (2003-5-21 14:05:11)
 * @return java.lang.String
 */
public String trim(String str) throws Exception {
   String returnstr="";
    if(str == "")
       return "";
    int i = 0;
    for(i = 0;i<str.length();i++)
    {
        if(str.charAt(i) == ' ')
        {
            continue;
         }
        break;
    }
    str = str.substring(i,str.length());
    if(str =="")
       return "";
    for(i=str.length()-1;i>=0;i--)
    {
        if(str.charAt(i)==' ')
        {
            continue;
         }
         break;
     }
     returnstr = str.substring(0,i+1);
     return returnstr;
}
/**
 * Insert the method's description here.
 * Creation date: (2003-5-21 15:48:47)
 * @return java.lang.String
 * 1999/01/09->19990109
 */
public String unformatYMD(String s_Date)throws Exception {

	if(isEmpty(s_Date)){
		return "Not a valid date!";
	}
	if(s_Date.length()==10){
		String s_retrun= s_Date.substring(0,4)+s_Date.substring(5,7)+s_Date.substring(8,10);
		return s_retrun;
	}
	return "Not a valid date!";
}
}
