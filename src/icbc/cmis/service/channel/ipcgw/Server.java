/*
 * Created on 2004-7-7
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package icbc.cmis.service.channel.ipcgw;

import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ouwh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Server extends Thread {
	private String opNum = "0023";
//	private String area4 = "000000000000000000000000000000001" +//		                   "12006-12-3101:01:010000000000000001" +//		                   "00200002601111111111111111122222222222222222000111222222006-01-0102:02:02" +//		"00000111112222006-12-31000000100122220121001000000000000000001000000000000000020000001000000003000000200000000400000000511" +//		"00000000001";
//	private String area4 = "00000000000000000000000000000000022006-12-3101:01:010000000000000000020000260" +
//		"001000000000000000000000000000000000100100000000000000001000000000000000011100000000000000000"+
//		"0000000000000000000000000000000000000000000" +//		"0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +//		"000000000000000000000000000000000000000000";
    private String area4 ="1002090010痧痧痧瘗0001痧痧@@痧痧痧痧2006-12-26-16.31.50.649914000068632006-12-26-16.31.50.647567       100200002600200026010015019602009900000142704001001012712013-04-0116:31:5110329000010002013-04-011304012041200000000000000000000000000000000000000000000000000000000000000000000000000000000000000001900-01-010";
	private String area3 = "0001" + opNum + "00000000" + "00000000";
	private static int port = 7777;
	private int counter=0;
	public static void main(String[] args) {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
			System.out.println("Listen: " + port);
		} catch (Exception e) {
			System.exit(0);
		}
		while (true) {
			Socket s = null;
			try {
				System.out.println("begin to accept one.");
				s = ss.accept();
				s.setSoTimeout(3000);
				new Server(s);
				System.out.println("accept one.");
			} catch (Exception e) {
			}
		}
	}
	private Socket s = null;
	private String reqMsg ="1002090010痧痧痧瘗0001痧痧@@痧痧痧痧2006-12-26-16.26.28.086216000063612006-12-26-16.26.28.084504       100200002600200026010015019602009900000142704001001012712013-04-0116:26:2810328000010002013-04-011304041041200000000000000000000000000000000000000000000000000000000000000000000000000000000000000001900-01-010";
			//"10" + "02" + "03"+ "0000" + "0000" + area3 + area4;
	public Server(Socket s) {
		this.s = s;
		this.start();
	}
	public void run() {
		try {
			sleep(10000);
			while (true) {
				send();
				
				receice();
				sleep(2000);
			}
		} catch (Exception e) {
		}
		System.out.println("stop one.");
	}
	private void send() throws Exception {
		OutputStream out = s.getOutputStream();
		String strHead = reqMsg.getBytes().length + "00000000";
		strHead = "0000".substring(0, (12 - strHead.length())) + strHead;
		byte[] head = strHead.getBytes();
		counter += 1;
		System.out.println("send:  "+counter+": "+new String(head)+reqMsg);
		out.write(head);
		out.write(reqMsg.getBytes());
	}
	private void receice() throws Exception {
		InputStream in = s.getInputStream();
		OutputStream out = s.getOutputStream();
		// 先接收数据包头
		byte[] head = new byte[12];
		int len = 0;
		long begin = System.currentTimeMillis();
		while (len < 12) {
			try {
				len += in.read(head, len, 12 - len);
				if(len == -1)len=0;
			} catch (InterruptedIOException e) {
			}
			// 如果在检查时间间隔内未能接收到一个完整的包头，则检查
			if (System.currentTimeMillis() - begin > 60000) {
				//throw new Exception();
			}
		}

		String strHead = new String(head);
		int dataLen = Integer.parseInt(strHead.substring(0, 4));

		len = 0;
		byte[] body = new byte[dataLen];
		begin = System.currentTimeMillis();
		while (len < dataLen) {
			try {
				len += in.read(body, len, dataLen - len);
			} catch (InterruptedIOException e) {
			}
			if (System.currentTimeMillis() - begin > 60000) {
				//throw new Exception();
			}
		}
		System.out.println(new String(body));
		if ((new String(body)).substring(2, 4).equals("03")) {
			out.write("00140000000010030000000000".getBytes());
		}
	}
}
