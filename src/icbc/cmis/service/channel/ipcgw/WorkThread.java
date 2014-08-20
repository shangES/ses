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

import icbc.cmis.operation.DummyOperation;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.net.*;
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

public class WorkThread extends Thread {
  private Receiver receiver;
  private Handler handler;
  private Sender sender;
  private boolean running;
  private Socket socket;
  // private ArrayList hostList;
  private long retryTime;
  private long retryTime1;
  private int socketTimeOut;
  private int maxHandlers;
  public String remoteHost;
  public int remotePort;
  private int Handlers = 0; //处理器计数器，限制线程数
  public int threadNo; //线程号
  /**
   * <b>构造函数</b><br>
   * 
   */
  public WorkThread() {
    receiver = null;
    handler = null;
    sender = null;
    running = false;
    socket = null;
    //hostList = null;
    retryTime = 20L;
    socketTimeOut = 3000;
    remoteHost = "127.0.0.1";
    remotePort = 9999;
  }
  /** 
   * <b>功能描述: </b><br>
   * <p>	</p>
   * @see java.lang.Runnable#run()
   * 
   * 
   */
  public void run() {
    running = true;
    while (running) {
      try {
        if (socket == null) {
          // genHost(); //获得网关的地址和端口
          socket = new Socket(remoteHost, remotePort);
          System.out.println("线程" + threadNo + "建立对应ip通用网关的socket连接成功，地址端口：" + remoteHost + ":" + remotePort);
          retryTime1 = retryTime; //重置创建socket超时睡眠时间
          socket.setSoTimeout(socketTimeOut);
        }
        if (Handlers < maxHandlers) { //线程数不能超过最大线程数
          byte reqMsg[] = receiver.receive(socket);
          handler.set((IPCGWSender)sender, socket, reqMsg, this);
          Thread t_handler = new Thread((IPCGWHandler)handler);
          t_handler.start();
          synchronized (this) {
            Handlers++;
          }
          //以下是监控部分，不影响业务数据
          if (IPCGWClientService.trace_or_not.equals("all")) { //启用全面监控，记录收到的每个包
            ToolsDAO tools = new ToolsDAO(new DummyOperation());
            try {
              tools.trace(
                InetAddress.getLocalHost().getHostAddress(),
                String.valueOf(threadNo),
                new String(reqMsg),
                String.valueOf(Handlers),
                (remoteHost + ":" + remotePort),
                "接收的数据包，工作线程记录",
                "0",
                "0");

            }
            catch (Exception ee) {}
          }
          //以上是监控部分，不影响业务数据
        }
        else {
          try {
            Thread.sleep(retryTime); //线程数超标，睡眠一段时间重试
          }
          catch (Exception e2) {}
        }
      }
      catch (Exception e) {
        System.out.println("线程" + threadNo + "对应ip通用网关的socket连接异常，地址端口：" + remoteHost + ":" + remotePort + "；异常信息：" + e.getMessage());
        try {
          socket.close();
		  socket=null;
          try {
            Thread.sleep(retryTime1); //打开失败，睡眠一段时间重试
            retryTime1 = retryTime1 + retryTime;
            if(retryTime1 >= 10*retryTime){
				retryTime1 = retryTime;
            }
          }
          catch (Exception e2) {}
        }
        catch (Exception e1) {
          socket = null;
          try {
            Thread.sleep(retryTime1); //打开失败，睡眠一段时间重试
            retryTime1 = retryTime1 + retryTime;
			if(retryTime1 >= 10*retryTime){
				retryTime1 = retryTime;
			}
          }
          catch (Exception e2) {}
        }
      }
    }
  }
  /**
   * <b>功能描述: </b><br>
   * <p> </p>
   * 
   *  
   */
  public void shutdown() {
    running = false;
    try {
      socket.close();
    }
    catch (Exception e) {}
  }
  /**
   * <b>功能描述: </b><br>
   * <p> </p>
   * @param handler
   *  
   */
  public void setHandler(Handler handler) {
    this.handler = handler;
  }
  /**
   * <b>功能描述: </b><br>
   * <p> </p>
   * @param receiver
   *  
   */
  public void setReceiver(Receiver receiver) {
    this.receiver = receiver;
  }
  /**
   * <b>功能描述: </b><br>
   * <p> </p>
   * @param sender
   *  
   */
  public void setSender(Sender sender) {
    this.sender = sender;
  }
  /**
   * <b>功能描述: </b><br>
   * <p> 设定重连时间</p>
   * @param retryTime
   *  
   */
  public void setRetryTime(long retryTime) {
    this.retryTime = retryTime;
    this.retryTime1 = retryTime;
  }
  /**
   * <b>功能描述: </b><br>
   * <p> 设定超时时间</p>
   * @param socketTimeout
   *  
   */
  public void setSocketTimeOut(int socketTimeout) {
    this.socketTimeOut = socketTimeout;
  }
  /**
   * <b>功能描述: </b><br>
   * <p> 取得</p>
   * @param hostList
   *  
   */
  public void setHostList(String host, String port) {
    this.remoteHost = host;
    this.remotePort = Integer.parseInt(port);
  }
  /**
   * <b>功能描述: </b><br>
   * <p> 取得</p>
   * @param hostList
   *  
   */
  public void setMaxHandlers(int maxHandlers) {
    this.maxHandlers = maxHandlers;
  }
  /**
   * 
   */
  public synchronized void subHandlers() {
    Handlers--;
  }
  /**
   * 
   */
  public void setThreadNo(int no) {
    threadNo = no;
  }
}
