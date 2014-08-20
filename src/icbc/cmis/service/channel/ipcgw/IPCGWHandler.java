////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2006 ICBC
//
//ALL RIGHTS RESERVED BY ICBC CORPORATION, THIS PROGRAM
//MUST BE USED SOLELY FOR THE PURPOSE FOR WHICH IT WAS  
//FURNISHED BY ICBC CORPORATION, NO PART OF THIS PROGRAM
//MAY BE REPRODUCED OR DISCLOSED TO OTHERS, IN ANY FORM
//WITHOUT THE PRIOR WRITTEN PERMISSION OF ICBC CORPORATION.
//USE OF COPYRIGHT NOTICE DOES NOT EVIDENCE PUBLICATION
//OF THE PROGRAM
//
//			ICBC CONFIDENTIAL AND PROPRIETARY
//
////////////////////////////////////////////////////////////////////////////
//

package icbc.cmis.service.channel.ipcgw;

import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import icbc.cmis.base.KeyedDataCollection;
import icbc.cmis.operation.*;
/*************************************************************
 * 
 * <b>创建日期: </b> 2006-7-12<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * <br>
 * <p>Copyright: Copyright (c)2006</p>
 * <p>Company: </p>
 * 
 * @author ZJFH-wanglx
 * 
 * @version 
 * 
 * @since 
 * 
 * @see  
 * 
 *************************************************************/

public class IPCGWHandler implements Runnable, Handler {
  private String defaultOperation;
  private HashMap operationMap;
  private IPCGWSender sender;
  private Socket socket;
  private byte reqMsg1[];
  private WorkThread workth;
  /**
   * <b>构造函数</b><br>
   * 
   */
  public IPCGWHandler() {
    defaultOperation = null;
    operationMap = new HashMap();
  }
  /**
   * <b>构造函数</b><br>
   * 
   */
  public IPCGWHandler(HashMap operationMap, String defaultOperation, IPCGWSender sender1, Socket socket1, byte reqMsg[], WorkThread workth1) {
    defaultOperation = null;
    operationMap = new HashMap();
    sender = sender1;
    socket = socket1;
    reqMsg1 = reqMsg;
    workth = workth1;
  }

  public void set(IPCGWSender sender1, Socket socket1, byte reqMsg[], WorkThread workth1) throws Exception {
    sender = sender1;
    socket = socket1;
    reqMsg1 = reqMsg;
    workth = workth1;
  }
  /* （非 Javadoc）
   * @see java.lang.Runnable#run()
   */
  public void run() {
    try {
      if (socket != null) {
        byte repMsg[] = Process(reqMsg1);
        sender.set(socket, repMsg,workth);
        Thread t_sender = new Thread((IPCGWSender)sender);
        t_sender.start();
		workth.subHandlers();
      }
    }
    catch (Exception e) {}
  }
  /**
   * <b>功能描述: </b><br>
   * <p> 处理的参数是二三四三个通讯区的数据</p>
   * @param repMsg
   * @return
   * @throws Exception
   *  
   */
  public byte[] Process(byte reqMsg[]) throws Exception {
    String operationName = null;
    byte buf[] = new byte[1];
    System.arraycopy(reqMsg, 38+33, buf, 0, 1); //数据区的前1个字节表示交易类型
    String operationFlag = new String(buf); //取到交易类型，从而确定调用的op
    Vector v_trade_in = (Vector)IPCGWClientService.ht_trades.get(operationFlag + "_in");
    Vector v_trade_out = (Vector)IPCGWClientService.ht_trades.get(operationFlag + "_out");
    byte tmpReqMsg[] = new byte[reqMsg.length - 38]; //单取第四通讯区数据
    System.arraycopy(reqMsg, 38, tmpReqMsg, 0, tmpReqMsg.length);
    byte repMsg[] = new byte[38 + 4]; //返回数据包，返回给统一消息平台
    System.arraycopy(reqMsg, 0, repMsg, 0, 38); //获取接收包的二区和三区的数据
    //下面是判断收到的数据项和配置文件是否一致
    int allLen = 0;
    for (int k = 0; k < v_trade_in.size(); k++) {
      allLen += Integer.parseInt(((String[])v_trade_in.elementAt(k))[1]);
    }
    if (allLen != tmpReqMsg.length) { //长度不等，就返回错误1表示接收异常
      //以下是监控部分，不影响业务数据
      if (!IPCGWClientService.trace_or_not.equals("false")) { //启用监控，记录异常包
        ToolsDAO tools = new ToolsDAO(new DummyOperation());
        try {
          tools.trace(InetAddress.getLocalHost().getHostAddress(), String.valueOf(workth.threadNo), new String(reqMsg),String.valueOf(workth.threadNo), workth.remoteHost+":"+String.valueOf(workth.remotePort), "数据包长度和配置文件不一致，handler记录", "1", "0");
        }
        catch (Exception ee) {}
      }
      //以上是监控部分，不影响业务数据
      System.arraycopy("0001".getBytes(), 0, repMsg, 38, "0001".length()); //表示接收异常
      return repMsg;//返回数据给工作线程
    }
    operationName = (String)operationMap.get(String.valueOf(Integer.parseInt(operationFlag)));
    if (operationName == null) {
      //operationName = defaultOperation;
//		以下是监控部分，不影响业务数据
		if (!IPCGWClientService.trace_or_not.equals("false")) { //启用监控，记录异常包
			ToolsDAO tools = new ToolsDAO(new DummyOperation());
			try {
				tools.trace(InetAddress.getLocalHost().getHostAddress(), String.valueOf(workth.threadNo), new String(reqMsg), String.valueOf(workth.threadNo),  workth.remoteHost+":"+String.valueOf(workth.remotePort), "没有对应该类数据包的交易处理类，handler记录", "1", "0");
			}catch (Exception ee) {}
		}
		//以上是监控部分，不影响业务数据
		System.arraycopy("0002".getBytes(), 0, repMsg, 38, "0002".length()); //表示处理异常
		return repMsg;//返回数据给工作线程
    }
    try {
      Class c = (Class)Class.forName(operationName);
      CmisOperation operation = (CmisOperation)c.newInstance();
      operation.setOperationName(operationName);
      KeyedDataCollection opData = new KeyedDataCollection();
      operation.setOperationData(opData);
      //此处添加解包程序

      String fieldName = ""; //数据域的名称
      String fieldLen = ""; //数据域的长度
      String fieldType="";//数据的类型
      int idx = 0; //此变量标志当前解串的位置
      String[] strTmp = new String[5];
      for (int i = 0; i < v_trade_in.size(); i++) {
        strTmp = (String[])v_trade_in.elementAt(i);
        fieldName = strTmp[0];
        fieldLen = strTmp[1];
        byte tmpBuf[] = new byte[Integer.parseInt(fieldLen)];
        System.arraycopy(tmpReqMsg, idx, tmpBuf, 0, Integer.parseInt(fieldLen)); //取数据的值
        idx = idx + Integer.parseInt(fieldLen);
        operation.setFieldValue(fieldName, new String(tmpBuf));
      }
      //交给op执行主机返传的数据
      operation.execute();
      //执行完毕把结果返回，根据配置文件对结果进行打包	
      //根据要求，有标志的，则根据返回数据格式打包，没有标志的，直接返回成功
      if (v_trade_out != null && v_trade_out.size() > 0) {
        String retStr = "";
        for (int i = 0; i < v_trade_out.size(); i++) {
          strTmp = (String[])v_trade_out.elementAt(i);
          fieldName = strTmp[0];
		  fieldLen = strTmp[1];
		  fieldType = strTmp[2];
          String fieldValue = operation.getStringAt(fieldName);
          if (fieldValue.length() <= Integer.parseInt(fieldLen)) { 
          	if(fieldType.equals("NUM")){//数字类型前面补充0
          		for(;fieldValue.length()<Integer.parseInt(fieldLen);){
					fieldValue = "0"+fieldValue;
          		}
          	}else{//字符类型前面补充空格
				for(;fieldValue.length()<Integer.parseInt(fieldLen);){
					fieldValue = " "+fieldValue;
				}          		
          	}
            retStr +=  fieldValue ;
          }
          else { //长度超过3位，则该字段不赋值，长度0
            throw new Exception(fieldName + "字段数据长度超长！");
          }
          if (retStr.length() > 4000) {
            throw new Exception(operationFlag + "总字段数据长度超长！");
          }
        }
        byte tmpRep[] = new byte[38 + retStr.length()];
        System.arraycopy(repMsg, 0, tmpRep, 0, 38);
        System.arraycopy(retStr.getBytes(), 0, tmpRep, 38, retStr.length());
        return tmpRep;
      }
      else {
        System.arraycopy("0000".getBytes(), 0, repMsg, 38, "0000".length());
        return repMsg;
      }
    }
    catch (Exception e) {
      //以下是监控部分，不影响业务数据
      if (!IPCGWClientService.trace_or_not.equals("false")) { //启用监控，记录异常包
        ToolsDAO tools = new ToolsDAO(new DummyOperation());
        try {
          tools.trace(InetAddress.getLocalHost().getHostAddress(), String.valueOf(workth.threadNo), new String(reqMsg), String.valueOf(workth.threadNo),  workth.remoteHost+":"+String.valueOf(workth.remotePort), e.getMessage().length()>200?e.getMessage().substring(0, 200):e.getMessage(), "1", "2");
        }
        catch (Exception ee) {
        System.out.print("");
        }
      }
      //以上是监控部分，不影响业务数据
      System.arraycopy("0002".getBytes(), 0, repMsg, 38, "0002".length()); //返回0002表示处理异常
      return repMsg;
    }
//    finally {
//      workth.subHandlers();
//    }
  }
  /** 
   * <b>功能描述: </b><br>
   * <p>	</p>
   * @see com.icbc.cmis.services.ipcgw.Handler#initForm(org.w3c.dom.Node)
   * @param node
   * @throws Exception
   * 
   */
  public void initForm(Node node) throws Exception {
    NamedNodeMap attrMap = node.getAttributes();
    try {
      defaultOperation = attrMap.getNamedItem("defaultOperation").getNodeValue().trim();
    }
    catch (Exception e) {
      throw new Exception("xml文件格式错误，node－－Handler&defaultOperation");
    }
    NodeList childNodes = node.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node nextNode = childNodes.item(i);
      NamedNodeMap switchMap = nextNode.getAttributes();
      try {
        operationMap.put(switchMap.getNamedItem("case").getNodeValue().trim(), switchMap.getNamedItem("operation").getNodeValue().trim());
      }
      catch (Exception e) {}
    }
  }
}
