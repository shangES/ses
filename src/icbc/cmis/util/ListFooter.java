package icbc.cmis.util;

import icbc.cmis.operation.*;
import icbc.cmis.util.*;
import icbc.cmis.base.*;
import java.sql.*;
import icbc.cmis.base.TranFailException;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class ListFooter extends CmisOperation {
  icbc.cmis.util.CommonTools c=new icbc.cmis.util.CommonTools( );

  public ListFooter() {
      super();
  }


  public void execute() throws java.lang.Exception {
    /**@todo: implement this icbc.cmis.operation.CmisOperation abstract method*/
/*
    int bufBegIdx;
    int bufEndIdx;
    int startIndex;

    String beginpositionName = null;
    String bufBegName = null;
    String bufEndName = null;
    String indexName = null;

    if( this.isElementExist("indexname")){
        indexName = (String)this.getStringAt("indexname");
    }

    beginpositionName = "ListTagBeginPosition"+indexName;
    bufBegName = "bufBegIdx"+indexName;
    bufEndName = "bufEndIdx"+indexName;

    if( this.isElementExist(beginpositionName)){
        startIndex = Integer.parseInt((String)this.getStringAt(beginpositionName));
    }
    else{
        startIndex = 0;
    }

    //缓冲区首
    if( isElementExist(bufBegName)){
        bufBegIdx = Integer.parseInt((String)this.getStringAt(bufBegName));
    }
    else{
        bufBegIdx = 0;
    }

    //缓冲区尾
    if( isElementExist(bufEndName)){
        bufEndIdx = Integer.parseInt((String)this.getStringAt(bufEndName));
    }
    else{
        bufEndIdx = 999999;
    }

    //若未命中缓冲区，则查询数据库
    if( isHitBuf( startIndex, bufBegIdx, bufEndIdx )){
       this.setFieldValue("okReturn","/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=EB_AuditingDealOP&operationStatus=init");
       return;
    }
*/
    this.setOperationDataToSession();
    c.setDataElement( this.getOperationData(),"opDataUnclear", "true");
    this.setReplyPage(getStringAt("r_replyPage_formtag2509"));
  }

private boolean isHitBuf(int beginPos,int begIdx, int endIdx) throws TranFailException
{
    boolean ret = false;
        if (beginPos >= begIdx
            && beginPos <= endIdx )
            {
            ret = true;
        }
        else
            {
            ret = false;
        }
        return ret;
}


}