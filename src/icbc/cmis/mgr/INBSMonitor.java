package icbc.cmis.mgr;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.event.MouseEvent;
import javax.swing.tree.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

/**
 * Insert the type's description here.
 * Creation date: (2001-4-1 20:44:25)
 * @author: Administrator
 */
public class INBSMonitor extends JFrame implements ServerListener, ServerProcessor, java.awt.event.MouseListener, java.awt.event.WindowListener, Runnable {
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private JMenuBar ivjINBSMonitorJMenuBar = null;
	private JMenuItem ivjJClearMenuItem = null;
	private JPanel ivjJFrameContentPane = null;
	private JMenu ivjJOptionMenu = null;
	private JScrollPane ivjJScrollPane1 = null;
	private JScrollPane ivjJScrollPane2 = null;
	private JSplitPane ivjJSplitPane1 = null;
	private JTextArea ivjMessageArea = null;
	private JTable ivjServerTable = null;
	private Thread monitorThread = null;
	private boolean isStop = false;
	private int checkInterval = 30000; //reflash every 30 sec.
	private int lines = 0;
	private int maxLines = 100;
	private int listenPort = 1800;
	private Vector serverProcessors = new Vector();
	private Hashtable readThreads = new Hashtable();
	private ServerListenThread listenThread = null;
	private TableDataModel ivjTableDataModel = null;
	private JMenuItem ivjExitMenuItem = null;
	private JMenu ivjJMenu1 = null;
	private JMenuItem ivjReloadMenuItem = null;
	private JMenuItem ivjRestartServerMenuItem = null;
	private JMenuItem ivjSettingsMenuItem = null;
	private JMenuItem ivjStopMenuItem = null;
	private JSeparator ivjJSeparator2 = null;
	private JMenuItem ivjStartServerMenuItem = null;
	private JSeparator ivjJSeparator11 = null;
	private JSeparator ivjJSeparator111 = null;
	private JMenuItem ivjJGCMenuItem = null;
	private JMenuItem ivjJMonitMemoryMenuItem = null;
	private JMenu ivjJMonitMenu = null;
	private JMenuItem ivjJMonitSysPropMenuItem = null;
	private JSeparator ivjJSeparator1 = null;
	private JSeparator ivjJSeparator3 = null;
	private JMenu ivjJUserMgrMenu = null;
	private JMenuItem ivjJMgrLogonMenuItem = null;
	private JSeparator ivjJSeparator4 = null;
	private JSeparator ivjJSeparator5 = null;
	private JMenuItem ivjJUserSignOffMenuItem = null;
	private JMenuItem ivjJMonitStartMenuItem = null;
	private JSeparator ivjJSeparator6 = null;
	private java.util.Hashtable userInfo = new Hashtable();
	private MonitorUserInfo curUserInfo = null;
	private JScrollPane ivjJScrollPane3 = null;
	private JLabel ivjAvailableMemLabel = null;
	private JLabel ivjFreeMemLabel = null;
	private JLabel ivjJLabel1 = null;
	private JLabel ivjJLabel2 = null;
	private JLabel ivjJLabel3 = null;
	private JLabel ivjJLabel4 = null;
	private JPanel ivjJPanel1 = null;
	private JPanel ivjJPanel2 = null;
	private JSplitPane ivjJSplitPane2 = null;
	private JTextArea ivjSysPropTextArea = null;
	private JLabel ivjTotalMemLabel = null;
	private JLabel ivjMem_srvNameLabel = null;
	private JButton ivjMemFresh_btn = null;
	private JRadioButton ivjMemNoTime_btn = null;
	private JRadioButton ivjMemTime_btn = null;
	private JSeparator ivjJSeparator7 = null;
	private JLabel ivjJLabel6 = null;
	private JPanel ivjJPanel3 = null;
	private JButton ivjSysProp_btn = null;
	private JButton ivjJButton1 = null;

class IvjEventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == INBSMonitor.this.getJClearMenuItem()) 
				connEtoM1(e);
			if (e.getSource() == INBSMonitor.this.getRestartServerMenuItem()) 
				connEtoC2(e);
			if (e.getSource() == INBSMonitor.this.getReloadMenuItem()) 
				connEtoC3(e);
			if (e.getSource() == INBSMonitor.this.getSettingsMenuItem()) 
				connEtoC1(e);
			if (e.getSource() == INBSMonitor.this.getStopMenuItem()) 
				connEtoC5(e);
			if (e.getSource() == INBSMonitor.this.getStartServerMenuItem()) 
				connEtoC6(e);
			if (e.getSource() == INBSMonitor.this.getExitMenuItem()) 
				connEtoM2(e);
			if (e.getSource() == INBSMonitor.this.getJMonitMemoryMenuItem()) 
				connEtoC8(e);
			if (e.getSource() == INBSMonitor.this.getJMonitSysPropMenuItem()) 
				connEtoC9(e);
			if (e.getSource() == INBSMonitor.this.getJGCMenuItem()) 
				connEtoC10(e);
			if (e.getSource() == INBSMonitor.this.getJMgrLogonMenuItem()) 
				connEtoC12(e);
			if (e.getSource() == INBSMonitor.this.getJUserSignOffMenuItem()) 
				connEtoC14(e);
			if (e.getSource() == INBSMonitor.this.getJMonitStartMenuItem()) 
				connEtoC17(e);
			if (e.getSource() == INBSMonitor.this.getMemTime_btn()) 
				connEtoC18(e);
			if (e.getSource() == INBSMonitor.this.getMemNoTime_btn()) 
				connEtoC19(e);
			if (e.getSource() == INBSMonitor.this.getMemFresh_btn()) 
				connEtoC20(e);
			if (e.getSource() == INBSMonitor.this.getSysProp_btn()) 
				connEtoC21(e);
			if (e.getSource() == INBSMonitor.this.getJButton1()) 
				connEtoC22(e);
		};
	};
/**
 * INBSMonitor constructor comment.
 */
public INBSMonitor() {
	super();
	initialize();
}
/**
 * INBSMonitor constructor comment.
 */
public INBSMonitor(int checkInterval, int listenPort ) {
	super();
	
	this.listenPort = listenPort;
	this.checkInterval = checkInterval;
	initialize();
	try{
		monitorStart(listenPort);
	}catch(Exception e){
		System.out.println("CMISMonitor thread start failure!");
	}
	
	this.show();
}
/**
 * INBSMonitor constructor comment.
 * @param title java.lang.String
 */
public INBSMonitor(String title) {
	super(title);
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-1 21:56:58)
 * @param serverName java.lang.String
 */
private synchronized void addServer(int sessionID, String serverName)
{

	int columnCount = getTableDataModel().getColumnCount();
	String[] aRow = new String[columnCount];

	aRow[0] = String.valueOf(sessionID);
	aRow[1] = serverName;
	aRow[2] = "NORMAL";
	aRow[3] = "0";
	aRow[4] = "0";
		
	this.getTableDataModel().addRow(aRow);
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-22 9:30:23)
 * @param userInfo com.ecc.echannels.util.MonitorUserInfo
 */
public synchronized void addUserInfo(MonitorUserInfo userInfo1) {
	MonitorUserInfo info = null;
	if(this.userInfo.containsKey(userInfo1.getServerName())){
		this.userInfo.remove(userInfo1.getServerName());
	}
	this.userInfo.put(userInfo1.getServerName(),userInfo1);
	
}
public void closeSocket(int sessionID, java.net.Socket socket )
{

	removeServer(sessionID);
	ServerReadThread readThread = (ServerReadThread)readThreads.get(String.valueOf(sessionID));
	readThreads.remove(String.valueOf(sessionID));
	serverProcessors.remove(readThread);

	showMessage("Server["+ sessionID +":" + readThread.getClientHostName() + "]was down!");
}
/**
 * connEtoC1:  (IntervalMenuItem.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.intervalMenuItem_ActionPerformed()V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.settingsMenuItem_ActionPerformed();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC10:  (JGCMenuItem.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.jGCMenuItem_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC10(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.jGCMenuItem_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC12:  (JUserMgrMenu.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.jUserMgrMenu_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC12(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.jMgrLogonMenuItem_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC14:  (JUserChangeMenuItem.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.jUserChangeMenuItem_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC14(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.jUserSignOffMenuItem_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC17:  (JMonitStartMenuItem.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.jMonitStartMenuItem_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC17(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.jMonitStartMenuItem_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC18:  (MemTime_btn.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.memTime_btn_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC18(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.memTime_btn_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC19:  (MemNoTime_btn.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.memNoTime_btn_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC19(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.memNoTime_btn_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC2:  (RestartServerMenuItem.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.restartServerMenuItem_ActionPerformed()V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC2(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.restartServerMenuItem_ActionPerformed();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC20:  (MemFresh_btn.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.memFresh_btn_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC20(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.memFresh_btn_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC21:  (SysProp_btn.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.sysProp_btn_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC21(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.sysProp_btn_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC22:  (JButton1.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.jButton1_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC22(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.jButton1_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC3:  (ReloadMenuItem.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.reloadMenuItem_ActionPerformed()V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC3(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.reloadMenuItem_ActionPerformed();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC5:  (StopMenuItem.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.stopMenuItem_ActionPerformed()V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC5(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.stopMenuItem_ActionPerformed();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC6:  (StartServerMenuItem.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.startServerMenuItem_ActionPerformed()V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC6(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.startServerMenuItem_ActionPerformed();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC8:  (JMonitMemoryMenuItem.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.jMonitMemoryMenuItem_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC8(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.jMonitMemoryMenuItem_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoC9:  (JMonitSysPropMenuItem.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.jMonitSysPropMenuItem_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC9(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.jMonitSysPropMenuItem_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoM1:  (JClearMenuItem.action.actionPerformed(java.awt.event.ActionEvent) --> JTextArea1.text)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoM1(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		getMessageArea().setText(" ");
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoM2:  (ExitMenuItem.action.actionPerformed(java.awt.event.ActionEvent) --> INBSMonitor.dispose()V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoM2(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.dispose();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}

/**
 * Insert the method's description here.
 * Creation date: (2001-4-1 23:41:56)
 */
public void dispose()
{
	super.dispose();

	try
	{
		this.isStop = true;
		this.monitorThread.interrupt();

		for (int i = 0; i < serverProcessors.size(); i++)
		{
			ServerReadThread readThread = (ServerReadThread) serverProcessors.elementAt(i);
			readThread.close();
		}
		
		listenThread.close();

	}
	catch (Exception e)
	{
	}

}
/**
 * Return the AvailableMemLabel property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getAvailableMemLabel() {
	if (ivjAvailableMemLabel == null) {
		try {
			ivjAvailableMemLabel = new javax.swing.JLabel();
			ivjAvailableMemLabel.setName("AvailableMemLabel");
			ivjAvailableMemLabel.setBorder(new javax.swing.border.EtchedBorder());
			ivjAvailableMemLabel.setText("服务处于关闭状态...");
			ivjAvailableMemLabel.setBounds(121, 115, 153, 21);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjAvailableMemLabel;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-1 21:17:31)
 * @return java.lang.String
 */
private String getCurrentDateTime(String format)
{
	try
	{
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		java.util.Date date = calendar.getTime();
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
		
		return formatter.format(date);
	}
	catch (Exception e)
	{
		return null;
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-22 10:12:44)
 * @return com.ecc.echannels.util.MonitorUserInfo
 */
public MonitorUserInfo getCurUserInfo() {
	return this.curUserInfo;
}
/**
 * Return the ExitMenuItem property value.
 * @return javax.swing.JMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuItem getExitMenuItem() {
	if (ivjExitMenuItem == null) {
		try {
			ivjExitMenuItem = new javax.swing.JMenuItem();
			ivjExitMenuItem.setName("ExitMenuItem");
			ivjExitMenuItem.setText("退出");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjExitMenuItem;
}
/**
 * Return the FreeMemLabel property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getFreeMemLabel() {
	if (ivjFreeMemLabel == null) {
		try {
			ivjFreeMemLabel = new javax.swing.JLabel();
			ivjFreeMemLabel.setName("FreeMemLabel");
			ivjFreeMemLabel.setBorder(new javax.swing.border.EtchedBorder());
			ivjFreeMemLabel.setText("服务处于关闭状态...");
			ivjFreeMemLabel.setBounds(121, 142, 153, 21);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjFreeMemLabel;
}
/**
 * Return the INBSMonitorJMenuBar property value.
 * @return javax.swing.JMenuBar
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuBar getINBSMonitorJMenuBar() {
	if (ivjINBSMonitorJMenuBar == null) {
		try {
			ivjINBSMonitorJMenuBar = new javax.swing.JMenuBar();
			ivjINBSMonitorJMenuBar.setName("INBSMonitorJMenuBar");
			ivjINBSMonitorJMenuBar.add(getJUserMgrMenu());
			ivjINBSMonitorJMenuBar.add(getJOptionMenu());
			ivjINBSMonitorJMenuBar.add(getJMenu1());
			ivjINBSMonitorJMenuBar.add(getJMonitMenu());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjINBSMonitorJMenuBar;
}
/**
 * Return the JButton1 property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getJButton1() {
	if (ivjJButton1 == null) {
		try {
			ivjJButton1 = new javax.swing.JButton();
			ivjJButton1.setName("JButton1");
			ivjJButton1.setText("清    空");
			ivjJButton1.setBounds(318, 3, 85, 27);
			ivjJButton1.setForeground(java.awt.SystemColor.desktop);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJButton1;
}
/**
 * Return the JClearMenuItem property value.
 * @return javax.swing.JMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuItem getJClearMenuItem() {
	if (ivjJClearMenuItem == null) {
		try {
			ivjJClearMenuItem = new javax.swing.JMenuItem();
			ivjJClearMenuItem.setName("JClearMenuItem");
			ivjJClearMenuItem.setText("清空窗口");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJClearMenuItem;
}
/**
 * Return the JFrameContentPane property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getJFrameContentPane() {
	if (ivjJFrameContentPane == null) {
		try {
			ivjJFrameContentPane = new javax.swing.JPanel();
			ivjJFrameContentPane.setName("JFrameContentPane");
			ivjJFrameContentPane.setLayout(new java.awt.BorderLayout());
			ivjJFrameContentPane.setBackground(java.awt.Color.white);
			getJFrameContentPane().add(getJSplitPane1(), "Center");
			getJFrameContentPane().add(getJSplitPane2(), "South");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJFrameContentPane;
}
/**
 * Return the JGCMenuItem property value.
 * @return javax.swing.JMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuItem getJGCMenuItem() {
	if (ivjJGCMenuItem == null) {
		try {
			ivjJGCMenuItem = new javax.swing.JMenuItem();
			ivjJGCMenuItem.setName("JGCMenuItem");
			ivjJGCMenuItem.setText("系统资源回收");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJGCMenuItem;
}
/**
 * Return the JLabel1 property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getJLabel1() {
	if (ivjJLabel1 == null) {
		try {
			ivjJLabel1 = new javax.swing.JLabel();
			ivjJLabel1.setName("JLabel1");
			ivjJLabel1.setText("当前系统内存使用情况：");
			ivjJLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
			ivjJLabel1.setBounds(122, 56, 159, 23);
			ivjJLabel1.setForeground(java.awt.SystemColor.desktop);
			ivjJLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJLabel1;
}
/**
 * Return the JLabel2 property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getJLabel2() {
	if (ivjJLabel2 == null) {
		try {
			ivjJLabel2 = new javax.swing.JLabel();
			ivjJLabel2.setName("JLabel2");
			ivjJLabel2.setText("总计数:");
			ivjJLabel2.setBounds(41, 85, 45, 21);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJLabel2;
}
/**
 * Return the JLabel3 property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getJLabel3() {
	if (ivjJLabel3 == null) {
		try {
			ivjJLabel3 = new javax.swing.JLabel();
			ivjJLabel3.setName("JLabel3");
			ivjJLabel3.setText("剩余数:");
			ivjJLabel3.setBounds(42, 141, 45, 21);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJLabel3;
}
/**
 * Return the JLabel4 property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getJLabel4() {
	if (ivjJLabel4 == null) {
		try {
			ivjJLabel4 = new javax.swing.JLabel();
			ivjJLabel4.setName("JLabel4");
			ivjJLabel4.setText("已用数:");
			ivjJLabel4.setBounds(42, 116, 45, 21);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJLabel4;
}
/**
 * Return the JLabel6 property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getJLabel6() {
	if (ivjJLabel6 == null) {
		try {
			ivjJLabel6 = new javax.swing.JLabel();
			ivjJLabel6.setName("JLabel6");
			ivjJLabel6.setBorder(new javax.swing.border.CompoundBorder());
			ivjJLabel6.setText("服务器内存资源监控方式：");
			ivjJLabel6.setBounds(9, 6, 172, 23);
			ivjJLabel6.setForeground(java.awt.SystemColor.desktop);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJLabel6;
}
/**
 * Return the JMenu1 property value.
 * @return javax.swing.JMenu
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenu getJMenu1() {
	if (ivjJMenu1 == null) {
		try {
			ivjJMenu1 = new javax.swing.JMenu();
			ivjJMenu1.setName("JMenu1");
			ivjJMenu1.setText("操作");
			ivjJMenu1.add(getJMonitStartMenuItem());
			ivjJMenu1.add(getJSeparator6());
			ivjJMenu1.add(getStartServerMenuItem());
			ivjJMenu1.add(getRestartServerMenuItem());
			ivjJMenu1.add(getJSeparator2());
			ivjJMenu1.add(getReloadMenuItem());
			ivjJMenu1.add(getJSeparator3());
			ivjJMenu1.add(getJSeparator1());
			ivjJMenu1.add(getJGCMenuItem());
			ivjJMenu1.add(getJSeparator11());
			ivjJMenu1.add(getJSeparator111());
			ivjJMenu1.add(getStopMenuItem());
			ivjJMenu1.add(getExitMenuItem());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJMenu1;
}
/**
 * Return the JMgrLogonMenuItem property value.
 * @return javax.swing.JMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuItem getJMgrLogonMenuItem() {
	if (ivjJMgrLogonMenuItem == null) {
		try {
			ivjJMgrLogonMenuItem = new javax.swing.JMenuItem();
			ivjJMgrLogonMenuItem.setName("JMgrLogonMenuItem");
			ivjJMgrLogonMenuItem.setText("管理员登录");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJMgrLogonMenuItem;
}
/**
 * Return the JMonitMemoryMenuItem property value.
 * @return javax.swing.JMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuItem getJMonitMemoryMenuItem() {
	if (ivjJMonitMemoryMenuItem == null) {
		try {
			ivjJMonitMemoryMenuItem = new javax.swing.JMenuItem();
			ivjJMonitMemoryMenuItem.setName("JMonitMemoryMenuItem");
			ivjJMonitMemoryMenuItem.setText("系统内存");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJMonitMemoryMenuItem;
}
/**
 * Return the JMonitMenu property value.
 * @return javax.swing.JMenu
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenu getJMonitMenu() {
	if (ivjJMonitMenu == null) {
		try {
			ivjJMonitMenu = new javax.swing.JMenu();
			ivjJMonitMenu.setName("JMonitMenu");
			ivjJMonitMenu.setText("资源监控");
			ivjJMonitMenu.add(getJMonitMemoryMenuItem());
			ivjJMonitMenu.add(getJMonitSysPropMenuItem());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJMonitMenu;
}
/**
 * Return the JMonitStartMenuItem property value.
 * @return javax.swing.JMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuItem getJMonitStartMenuItem() {
	if (ivjJMonitStartMenuItem == null) {
		try {
			ivjJMonitStartMenuItem = new javax.swing.JMenuItem();
			ivjJMonitStartMenuItem.setName("JMonitStartMenuItem");
			ivjJMonitStartMenuItem.setText("启动Monitor监控");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJMonitStartMenuItem;
}
/**
 * Return the JMonitSysPropMenuItem property value.
 * @return javax.swing.JMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuItem getJMonitSysPropMenuItem() {
	if (ivjJMonitSysPropMenuItem == null) {
		try {
			ivjJMonitSysPropMenuItem = new javax.swing.JMenuItem();
			ivjJMonitSysPropMenuItem.setName("JMonitSysPropMenuItem");
			ivjJMonitSysPropMenuItem.setText("系统属性");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJMonitSysPropMenuItem;
}
/**
 * Return the JOptionMenu property value.
 * @return javax.swing.JMenu
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenu getJOptionMenu() {
	if (ivjJOptionMenu == null) {
		try {
			ivjJOptionMenu = new javax.swing.JMenu();
			ivjJOptionMenu.setName("JOptionMenu");
			ivjJOptionMenu.setText("选项");
			ivjJOptionMenu.add(getSettingsMenuItem());
			ivjJOptionMenu.add(getJClearMenuItem());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJOptionMenu;
}
/**
 * Return the JPanel1 property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getJPanel1() {
	if (ivjJPanel1 == null) {
		try {
			ivjJPanel1 = new javax.swing.JPanel();
			ivjJPanel1.setName("JPanel1");
			ivjJPanel1.setPreferredSize(new java.awt.Dimension(300, 0));
			ivjJPanel1.setLayout(null);
			ivjJPanel1.setEnabled(false);
			getJPanel1().add(getJLabel1(), getJLabel1().getName());
			getJPanel1().add(getJLabel2(), getJLabel2().getName());
			getJPanel1().add(getJLabel3(), getJLabel3().getName());
			getJPanel1().add(getJLabel4(), getJLabel4().getName());
			getJPanel1().add(getTotalMemLabel(), getTotalMemLabel().getName());
			getJPanel1().add(getFreeMemLabel(), getFreeMemLabel().getName());
			getJPanel1().add(getAvailableMemLabel(), getAvailableMemLabel().getName());
			getJPanel1().add(getMem_srvNameLabel(), getMem_srvNameLabel().getName());
			getJPanel1().add(getMemTime_btn(), getMemTime_btn().getName());
			getJPanel1().add(getMemNoTime_btn(), getMemNoTime_btn().getName());
			getJPanel1().add(getMemFresh_btn(), getMemFresh_btn().getName());
			getJPanel1().add(getJSeparator7(), getJSeparator7().getName());
			getJPanel1().add(getJLabel6(), getJLabel6().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJPanel1;
}
/**
 * Return the JPanel2 property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getJPanel2() {
	if (ivjJPanel2 == null) {
		try {
			ivjJPanel2 = new javax.swing.JPanel();
			ivjJPanel2.setName("JPanel2");
			ivjJPanel2.setLayout(new java.awt.BorderLayout());
			getJPanel2().add(getJScrollPane3(), "Center");
			getJPanel2().add(getJPanel3(), "North");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJPanel2;
}
/**
 * Return the JPanel3 property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getJPanel3() {
	if (ivjJPanel3 == null) {
		try {
			ivjJPanel3 = new javax.swing.JPanel();
			ivjJPanel3.setName("JPanel3");
			ivjJPanel3.setPreferredSize(new java.awt.Dimension(0, 35));
			ivjJPanel3.setLayout(null);
			getJPanel3().add(getSysProp_btn(), getSysProp_btn().getName());
			getJPanel3().add(getJButton1(), getJButton1().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJPanel3;
}
/**
 * Return the JScrollPane1 property value.
 * @return javax.swing.JScrollPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JScrollPane getJScrollPane1() {
	if (ivjJScrollPane1 == null) {
		try {
			ivjJScrollPane1 = new javax.swing.JScrollPane();
			ivjJScrollPane1.setName("JScrollPane1");
			ivjJScrollPane1.setPreferredSize(new java.awt.Dimension(3, 300));
			getJScrollPane1().setViewportView(getMessageArea());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJScrollPane1;
}
/**
 * Return the JScrollPane2 property value.
 * @return javax.swing.JScrollPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JScrollPane getJScrollPane2() {
	if (ivjJScrollPane2 == null) {
		try {
			ivjJScrollPane2 = new javax.swing.JScrollPane();
			ivjJScrollPane2.setName("JScrollPane2");
			ivjJScrollPane2.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			ivjJScrollPane2.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			ivjJScrollPane2.setOpaque(false);
			ivjJScrollPane2.setBackground(java.awt.Color.lightGray);
			ivjJScrollPane2.setPreferredSize(new java.awt.Dimension(468, 150));
			getJScrollPane2().setViewportView(getServerTable());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJScrollPane2;
}
/**
 * Return the JScrollPane3 property value.
 * @return javax.swing.JScrollPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JScrollPane getJScrollPane3() {
	if (ivjJScrollPane3 == null) {
		try {
			ivjJScrollPane3 = new javax.swing.JScrollPane();
			ivjJScrollPane3.setName("JScrollPane3");
			ivjJScrollPane3.setPreferredSize(new java.awt.Dimension(3, 10));
			getJScrollPane3().setViewportView(getSysPropTextArea());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJScrollPane3;
}
/**
 * Return the JSeparator1 property value.
 * @return javax.swing.JSeparator
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JSeparator getJSeparator1() {
	if (ivjJSeparator1 == null) {
		try {
			ivjJSeparator1 = new javax.swing.JSeparator();
			ivjJSeparator1.setName("JSeparator1");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJSeparator1;
}
/**
 * Return the JSeparator11 property value.
 * @return javax.swing.JSeparator
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JSeparator getJSeparator11() {
	if (ivjJSeparator11 == null) {
		try {
			ivjJSeparator11 = new javax.swing.JSeparator();
			ivjJSeparator11.setName("JSeparator11");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJSeparator11;
}
/**
 * Return the JSeparator111 property value.
 * @return javax.swing.JSeparator
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JSeparator getJSeparator111() {
	if (ivjJSeparator111 == null) {
		try {
			ivjJSeparator111 = new javax.swing.JSeparator();
			ivjJSeparator111.setName("JSeparator111");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJSeparator111;
}
/**
 * Return the JSeparator2 property value.
 * @return javax.swing.JSeparator
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JSeparator getJSeparator2() {
	if (ivjJSeparator2 == null) {
		try {
			ivjJSeparator2 = new javax.swing.JSeparator();
			ivjJSeparator2.setName("JSeparator2");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJSeparator2;
}
/**
 * Return the JSeparator3 property value.
 * @return javax.swing.JSeparator
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JSeparator getJSeparator3() {
	if (ivjJSeparator3 == null) {
		try {
			ivjJSeparator3 = new javax.swing.JSeparator();
			ivjJSeparator3.setName("JSeparator3");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJSeparator3;
}
/**
 * Return the JSeparator4 property value.
 * @return javax.swing.JSeparator
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JSeparator getJSeparator4() {
	if (ivjJSeparator4 == null) {
		try {
			ivjJSeparator4 = new javax.swing.JSeparator();
			ivjJSeparator4.setName("JSeparator4");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJSeparator4;
}
/**
 * Return the JSeparator5 property value.
 * @return javax.swing.JSeparator
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JSeparator getJSeparator5() {
	if (ivjJSeparator5 == null) {
		try {
			ivjJSeparator5 = new javax.swing.JSeparator();
			ivjJSeparator5.setName("JSeparator5");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJSeparator5;
}
/**
 * Return the JSeparator6 property value.
 * @return javax.swing.JSeparator
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JSeparator getJSeparator6() {
	if (ivjJSeparator6 == null) {
		try {
			ivjJSeparator6 = new javax.swing.JSeparator();
			ivjJSeparator6.setName("JSeparator6");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJSeparator6;
}
/**
 * Return the JSeparator7 property value.
 * @return javax.swing.JSeparator
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JSeparator getJSeparator7() {
	if (ivjJSeparator7 == null) {
		try {
			ivjJSeparator7 = new javax.swing.JSeparator();
			ivjJSeparator7.setName("JSeparator7");
			ivjJSeparator7.setBounds(32, 81, 222, 3);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJSeparator7;
}
/**
 * Return the JSplitPane1 property value.
 * @return javax.swing.JSplitPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JSplitPane getJSplitPane1() {
	if (ivjJSplitPane1 == null) {
		try {
			ivjJSplitPane1 = new javax.swing.JSplitPane(javax.swing.JSplitPane.VERTICAL_SPLIT);
			ivjJSplitPane1.setName("JSplitPane1");
			ivjJSplitPane1.setDividerLocation(320);
			ivjJSplitPane1.setDividerSize(4);
			ivjJSplitPane1.setBackground(java.awt.Color.lightGray);
			getJSplitPane1().add(getJScrollPane1(), "bottom");
			getJSplitPane1().add(getJScrollPane2(), "top");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJSplitPane1;
}
/**
 * Return the JSplitPane2 property value.
 * @return javax.swing.JSplitPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JSplitPane getJSplitPane2() {
	if (ivjJSplitPane2 == null) {
		try {
			ivjJSplitPane2 = new javax.swing.JSplitPane(javax.swing.JSplitPane.HORIZONTAL_SPLIT);
			ivjJSplitPane2.setName("JSplitPane2");
			ivjJSplitPane2.setPreferredSize(new java.awt.Dimension(10, 180));
			ivjJSplitPane2.setDividerLocation(300);
			getJSplitPane2().add(getJPanel1(), "left");
			getJSplitPane2().add(getJPanel2(), "right");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJSplitPane2;
}
/**
 * Return the JUserMgrMenu property value.
 * @return javax.swing.JMenu
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenu getJUserMgrMenu() {
	if (ivjJUserMgrMenu == null) {
		try {
			ivjJUserMgrMenu = new javax.swing.JMenu();
			ivjJUserMgrMenu.setName("JUserMgrMenu");
			ivjJUserMgrMenu.setText("用户管理");
			ivjJUserMgrMenu.add(getJMgrLogonMenuItem());
			ivjJUserMgrMenu.add(getJSeparator4());
			ivjJUserMgrMenu.add(getJSeparator5());
			ivjJUserMgrMenu.add(getJUserSignOffMenuItem());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJUserMgrMenu;
}
/**
 * Return the JUserChangeMenuItem property value.
 * @return javax.swing.JMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuItem getJUserSignOffMenuItem() {
	if (ivjJUserSignOffMenuItem == null) {
		try {
			ivjJUserSignOffMenuItem = new javax.swing.JMenuItem();
			ivjJUserSignOffMenuItem.setName("JUserSignOffMenuItem");
			ivjJUserSignOffMenuItem.setText("签退");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJUserSignOffMenuItem;
}
/**
 * Return the Mem_srvNameLabel property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getMem_srvNameLabel() {
	if (ivjMem_srvNameLabel == null) {
		try {
			ivjMem_srvNameLabel = new javax.swing.JLabel();
			ivjMem_srvNameLabel.setName("Mem_srvNameLabel");
			ivjMem_srvNameLabel.setText("服务器");
			ivjMem_srvNameLabel.setForeground(java.awt.SystemColor.desktop);
			ivjMem_srvNameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			ivjMem_srvNameLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
			ivjMem_srvNameLabel.setBounds(23, 58, 96, 22);
			ivjMem_srvNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMem_srvNameLabel;
}
/**
 * Return the MemFresh_btn property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getMemFresh_btn() {
	if (ivjMemFresh_btn == null) {
		try {
			ivjMemFresh_btn = new javax.swing.JButton();
			ivjMemFresh_btn.setName("MemFresh_btn");
			ivjMemFresh_btn.setText("刷新");
			ivjMemFresh_btn.setBounds(198, 29, 68, 20);
			ivjMemFresh_btn.setEnabled(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMemFresh_btn;
}
/**
 * Return the MemNoTime_btn property value.
 * @return javax.swing.JRadioButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JRadioButton getMemNoTime_btn() {
	if (ivjMemNoTime_btn == null) {
		try {
			ivjMemNoTime_btn = new javax.swing.JRadioButton();
			ivjMemNoTime_btn.setName("MemNoTime_btn");
			ivjMemNoTime_btn.setSelected(true);
			ivjMemNoTime_btn.setText("手工");
			ivjMemNoTime_btn.setBounds(119, 28, 54, 24);
			ivjMemNoTime_btn.setEnabled(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMemNoTime_btn;
}
/**
 * Return the MemTime_btn property value.
 * @return javax.swing.JRadioButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JRadioButton getMemTime_btn() {
	if (ivjMemTime_btn == null) {
		try {
			ivjMemTime_btn = new javax.swing.JRadioButton();
			ivjMemTime_btn.setName("MemTime_btn");
			ivjMemTime_btn.setText("实时");
			ivjMemTime_btn.setBounds(41, 29, 54, 24);
			ivjMemTime_btn.setEnabled(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMemTime_btn;
}
/**
 * Return the JTextArea1 property value.
 * @return javax.swing.JTextArea
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextArea getMessageArea() {
	if (ivjMessageArea == null) {
		try {
			ivjMessageArea = new javax.swing.JTextArea();
			ivjMessageArea.setName("MessageArea");
			ivjMessageArea.setBounds(0, 0, 160, 120);
			ivjMessageArea.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMessageArea;
}
/**
 * Return the ReloadMenuItem property value.
 * @return javax.swing.JMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuItem getReloadMenuItem() {
	if (ivjReloadMenuItem == null) {
		try {
			ivjReloadMenuItem = new javax.swing.JMenuItem();
			ivjReloadMenuItem.setName("ReloadMenuItem");
			ivjReloadMenuItem.setText("重载服务器配置");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjReloadMenuItem;
}
/**
 * Return the RestartServerMenuItem property value.
 * @return javax.swing.JMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuItem getRestartServerMenuItem() {
	if (ivjRestartServerMenuItem == null) {
		try {
			ivjRestartServerMenuItem = new javax.swing.JMenuItem();
			ivjRestartServerMenuItem.setName("RestartServerMenuItem");
			ivjRestartServerMenuItem.setText("重启服务器");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjRestartServerMenuItem;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-4 16:53:04)
 * @return java.lang.String
 */
public String getSellectedServer() {

	int idx = getServerTable().getSelectedRow();
	if( idx == -1)
		return null;
	else
	{
		String[] row = (String[])getTableDataModel().getRowObject(idx);
		return row[1];
		
	}
		
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-1 22:41:38)
 */
private void getServerMessage()
{
	//first set all state to unnormal
	TableDataModel dataModel = getTableDataModel();
	for (int i = 0; i < dataModel.getRowCount(); i++)
	{
		String[] rowData = (String[]) dataModel.getRowObject(i);
		rowData[2] = "UNNORMAL";
		dataModel.updateRow(rowData);
	}

	Vector processors = (Vector)this.serverProcessors.clone();
	
	for (int i = 0; i < processors.size(); i++)
	{
		ServerReadThread readThread = (ServerReadThread) processors.elementAt(i);
		try
		{
			readThread.sentdata("1001"); //get the server's information
		}
		catch (Exception e)
		{
		}
	}

}
/**
 * Return the ScrollPaneTable property value.
 * @return javax.swing.JTable
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTable getServerTable() {
	if (ivjServerTable == null) {
		try {
			ivjServerTable = new javax.swing.JTable();
			ivjServerTable.setName("ServerTable");
			getJScrollPane2().setColumnHeaderView(ivjServerTable.getTableHeader());
			getJScrollPane2().getViewport().setBackingStoreEnabled(true);
			ivjServerTable.setOpaque(true);
			ivjServerTable.setBackground(java.awt.Color.white);
			ivjServerTable.setBounds(0, 0, 200, 200);
			// user code begin {1}

			ivjServerTable.setModel(getTableDataModel());			
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjServerTable;
}
/**
 * Return the IntervalMenuItem property value.
 * @return javax.swing.JMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuItem getSettingsMenuItem() {
	if (ivjSettingsMenuItem == null) {
		try {
			ivjSettingsMenuItem = new javax.swing.JMenuItem();
			ivjSettingsMenuItem.setName("SettingsMenuItem");
			ivjSettingsMenuItem.setText("设置");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSettingsMenuItem;
}
/**
 * Return the StartServerMenuItem property value.
 * @return javax.swing.JMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuItem getStartServerMenuItem() {
	if (ivjStartServerMenuItem == null) {
		try {
			ivjStartServerMenuItem = new javax.swing.JMenuItem();
			ivjStartServerMenuItem.setName("StartServerMenuItem");
			ivjStartServerMenuItem.setText("启动服务器");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjStartServerMenuItem;
}
/**
 * Return the StopMenuItem property value.
 * @return javax.swing.JMenuItem
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JMenuItem getStopMenuItem() {
	if (ivjStopMenuItem == null) {
		try {
			ivjStopMenuItem = new javax.swing.JMenuItem();
			ivjStopMenuItem.setName("StopMenuItem");
			ivjStopMenuItem.setText("停止");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjStopMenuItem;
}
/**
 * Return the SysProp_btn property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getSysProp_btn() {
	if (ivjSysProp_btn == null) {
		try {
			ivjSysProp_btn = new javax.swing.JButton();
			ivjSysProp_btn.setName("SysProp_btn");
			ivjSysProp_btn.setText("服务器系统资源配置");
			ivjSysProp_btn.setForeground(java.awt.SystemColor.desktop);
			ivjSysProp_btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			ivjSysProp_btn.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
			ivjSysProp_btn.setBounds(91, 4, 171, 27);
			ivjSysProp_btn.setEnabled(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSysProp_btn;
}
/**
 * Return the SysPropTextArea property value.
 * @return javax.swing.JTextArea
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextArea getSysPropTextArea() {
	if (ivjSysPropTextArea == null) {
		try {
			ivjSysPropTextArea = new javax.swing.JTextArea();
			ivjSysPropTextArea.setName("SysPropTextArea");
			ivjSysPropTextArea.setBounds(0, 0, 160, 120);
			ivjSysPropTextArea.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSysPropTextArea;
}
/**
 * Return the tableDataModel property value.
 * @return com.zmc.datamodel.TableDataModel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TableDataModel getTableDataModel() {
	if (ivjTableDataModel == null) {
		try {
			ivjTableDataModel = new icbc.cmis.mgr.TableDataModel();
			// user code begin {1}
			String[] title = {"序号", "服务器名", "状态", "登录人数", "在线人数"};
			ivjTableDataModel.setNames(title);

			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjTableDataModel;
}
/**
 * Return the TotalMemLabel property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getTotalMemLabel() {
	if (ivjTotalMemLabel == null) {
		try {
			ivjTotalMemLabel = new javax.swing.JLabel();
			ivjTotalMemLabel.setName("TotalMemLabel");
			ivjTotalMemLabel.setBorder(new javax.swing.border.EtchedBorder());
			ivjTotalMemLabel.setText("服务处于关闭状态...");
			ivjTotalMemLabel.setBounds(120, 86, 153, 21);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjTotalMemLabel;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-22 9:37:10)
 * @return com.ecc.echannels.util.MonitorUserInfo
 * @param serverName java.lang.String
 */
public MonitorUserInfo getUserInfo(String serverName) {
	
	if(this.userInfo.containsKey(serverName)){
		return (MonitorUserInfo)this.userInfo.get(serverName);
	}
	return null;
	
}
/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	// exception.printStackTrace(System.out);
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-22 10:38:21)
 */
private void init() {
	this.getServerTable().addMouseListener(this);
	getJClearMenuItem().setEnabled(false);
	getRestartServerMenuItem().setEnabled(false);
	getReloadMenuItem().setEnabled(false);
	getSettingsMenuItem().setEnabled(false);
	getStopMenuItem().setEnabled(false);
	getStartServerMenuItem().setEnabled(false);
	getExitMenuItem().setEnabled(true);
	getJMonitMemoryMenuItem().setEnabled(false);
	getJMonitSysPropMenuItem().setEnabled(false);
	getJGCMenuItem().setEnabled(false);
	getJMgrLogonMenuItem().setEnabled(false);
	getJUserSignOffMenuItem().setEnabled(false);
	getJMonitStartMenuItem().setEnabled(true);
	getMemFresh_btn().setEnabled(false);
	getMemNoTime_btn().setEnabled(false);
	getMemTime_btn().setEnabled(false);
	getSysProp_btn().setEnabled(false);
	ButtonGroup bg = new ButtonGroup();
	bg.add(getMemNoTime_btn());
	bg.add(getMemTime_btn());	
}
/**
 * Initializes connections
 * @exception java.lang.Exception The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() throws java.lang.Exception {
	// user code begin {1}
	this.addWindowListener(this);
	// user code end
	getJClearMenuItem().addActionListener(ivjEventHandler);
	getRestartServerMenuItem().addActionListener(ivjEventHandler);
	getReloadMenuItem().addActionListener(ivjEventHandler);
	getSettingsMenuItem().addActionListener(ivjEventHandler);
	getStopMenuItem().addActionListener(ivjEventHandler);
	getStartServerMenuItem().addActionListener(ivjEventHandler);
	getExitMenuItem().addActionListener(ivjEventHandler);
	getJMonitMemoryMenuItem().addActionListener(ivjEventHandler);
	getJMonitSysPropMenuItem().addActionListener(ivjEventHandler);
	getJGCMenuItem().addActionListener(ivjEventHandler);
	getJMgrLogonMenuItem().addActionListener(ivjEventHandler);
	getJUserSignOffMenuItem().addActionListener(ivjEventHandler);
	getJMonitStartMenuItem().addActionListener(ivjEventHandler);
	getMemTime_btn().addActionListener(ivjEventHandler);
	getMemNoTime_btn().addActionListener(ivjEventHandler);
	getMemFresh_btn().addActionListener(ivjEventHandler);
	getSysProp_btn().addActionListener(ivjEventHandler);
	getJButton1().addActionListener(ivjEventHandler);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		//init look and feel
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
		}

		// user code end
		setName("INBSMonitor");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("CMIS EBank Monitor");
		setSize(914, 537);
		setJMenuBar(getINBSMonitorJMenuBar());
		setContentPane(getJFrameContentPane());
		initConnections();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	init();
	
	java.awt.Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	this.setBounds(0,0,(int)d.getWidth(),(int)d.getHeight()-60);
	
	// user code end
}
/**
 * Comment
 */
public void intervalMenuItem_ActionPerformed() {
	return;
}
/**
 * Comment
 */
public void jButton1_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	getSysPropTextArea().setText("");
	return;
}
/**
 * Comment
 */
public void jGCMenuItem_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	String srvName = getSellectedServer();
	if(srvName == null || srvName.trim().length() == 0) return;
	showMessage(srvName,"资源回收");
	sendreloadpara("2009",srvName);
	return;
}
/**
 * Comment
 */
public void jMgrLogonMenuItem_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	MgrLogonDlg mgrDlg = new MgrLogonDlg(this);
	mgrDlg.setModal(true);
	mgrDlg.setVisible(true);
	mgrDlg.show();
	return;
}
/**
 * Comment
 */
public void jMonitMemoryMenuItem_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	String srvName = getSellectedServer();
	if(srvName == null || srvName.trim().length() == 0) return;
	MemoryMonitorSetting dlg = new MemoryMonitorSetting(this);
	dlg.setVisible(true);
	dlg.setModal(true);
	dlg.show();
	return;
}
/**
 * Comment
 */
public void jMonitStartMenuItem_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	ListenerPortDlg dlg = new ListenerPortDlg(this);
	dlg.setVisible(true);
	dlg.show();
	return;
}
/**
 * Comment
 */
public void jMonitSysPropMenuItem_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	String serverName = getSellectedServer();
	if(serverName == null || serverName.trim().length() == 0)return;
	showMessage(serverName,"取系统属性参数配置");
	sendreloadpara("2012|"+serverName,serverName);
	return;
}
/**
 * Comment
 */
public void jUserSignOffMenuItem_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	String srvName = getSellectedServer();
	if(srvName == null || srvName.trim().length() == 0) return;
	
	showMessage("服务器["+srvName+"]用户签退!");
	removeUserInfo(srvName);

	getTotalMemLabel().setText("服务处于关闭状态...");
	getFreeMemLabel().setText("服务处于关闭状态...");
	getAvailableMemLabel().setText("服务处于关闭状态...");
	getSysPropTextArea().setText("");
	userSignoff();
	showMessage("服务器["+srvName+"]用户签退成功!");
	return;
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		INBSMonitor aINBSMonitor;
		aINBSMonitor = new INBSMonitor();
			aINBSMonitor.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		aINBSMonitor.show();
		java.awt.Insets insets = aINBSMonitor.getInsets();
		aINBSMonitor.setSize(aINBSMonitor.getWidth() + insets.left + insets.right, aINBSMonitor.getHeight() + insets.top + insets.bottom);
		aINBSMonitor.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of javax.swing.JFrame");
		exception.printStackTrace(System.out);
	}
}
/**
 * Comment
 */
public void memFresh_btn_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	String srvName = getSellectedServer();
	String oSrvName = getMem_srvNameLabel().getText();
	if(srvName == null 
		|| srvName.trim().length() == 0
		|| oSrvName == null
		|| oSrvName.trim().length() == 0
		|| !oSrvName.trim().equals(srvName.trim())){
			
			getMemNoTime_btn().setEnabled(true);
			getMemTime_btn().setEnabled(true);
			getMemFresh_btn().setEnabled(false);
			return;
		}
	MonitorUserInfo  info1 = (MonitorUserInfo)getUserInfo(oSrvName);
	if(info1 != null && !info1.getIsMemTimeMonit()){
		sendreloadpara("2011",oSrvName);
	}
	return;
}
/**
 * Comment
 */
public void memNoTime_btn_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	String srvName = getSellectedServer();
	if(srvName == null || srvName.trim().length() == 0){
		getMemNoTime_btn().setEnabled(false);
		getMemTime_btn().setEnabled(false);
		getMemFresh_btn().setEnabled(false);
		return;
	}
	getMem_srvNameLabel().setText(srvName);
	MonitorUserInfo info = getUserInfo(srvName);
	if(info == null) return;
	info.setIsMemTimeMonit(false);
	if(!getMemFresh_btn().isEnabled()){
		getMemFresh_btn().setEnabled(true);
	}
}
/**
 * Comment
 */
public void memTime_btn_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	String srvName = getSellectedServer();
	if(srvName == null || srvName.trim().length() == 0){
		getMemNoTime_btn().setEnabled(false);
		getMemTime_btn().setEnabled(false);
		getMemFresh_btn().setEnabled(false);
		return;
	}
	getMem_srvNameLabel().setText(srvName);
	MonitorUserInfo info = getUserInfo(srvName);
	if(info == null) return;
	info.setIsMemTimeMonit(true);
	if(getMemFresh_btn().isEnabled()){
		getMemFresh_btn().setEnabled(false);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-20 14:38:09)
 */
public void mgrSignin() {
	getJMonitStartMenuItem().setEnabled(false);	
	getJClearMenuItem().setEnabled(true);
	getRestartServerMenuItem().setEnabled(true);
	getReloadMenuItem().setEnabled(true);
	getSettingsMenuItem().setEnabled(true);
	getStopMenuItem().setEnabled(true);
	getStartServerMenuItem().setEnabled(true);
	getExitMenuItem().setEnabled(true);
	getJMonitMemoryMenuItem().setEnabled(true);
	getJMonitSysPropMenuItem().setEnabled(true);
	getJGCMenuItem().setEnabled(true);
	getJMgrLogonMenuItem().setEnabled(false);
	getJUserSignOffMenuItem().setEnabled(true);
	getMemFresh_btn().setEnabled(true);
	getMemNoTime_btn().setEnabled(true);
	getMemTime_btn().setEnabled(true);
	getSysProp_btn().setEnabled(true);
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-20 14:38:09)
 */
public void mgrSignin1() {
	getJMonitStartMenuItem().setEnabled(false);	
	getJClearMenuItem().setEnabled(true);
	getRestartServerMenuItem().setEnabled(true);
	getReloadMenuItem().setEnabled(true);
	getSettingsMenuItem().setEnabled(true);
	getStopMenuItem().setEnabled(true);
	getStartServerMenuItem().setEnabled(true);
	getExitMenuItem().setEnabled(true);
	getJMonitMemoryMenuItem().setEnabled(true);
	getJMonitSysPropMenuItem().setEnabled(true);
	getJGCMenuItem().setEnabled(true);
	getJMgrLogonMenuItem().setEnabled(false);
	getJUserSignOffMenuItem().setEnabled(true);
	getSysProp_btn().setEnabled(true);
	getMemTime_btn().setSelected(true);
	getMemNoTime_btn().setSelected(true);
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 16:44:13)
 * @param port int
 */
public void monitorStart(int port) {
	this.listenPort = port;
	try
	{

	//start the server listen thread 
		this.listenThread = new ServerListenThread(this, this.listenPort);
		showMessage("Ready to service the client...");
	}
	catch (Exception e)
	{
		System.out.println("Failed to initialize the Monitor!\nException: " + e.getMessage());
	}

	//start the monitor thread
	this.monitorThread = new Thread(this);
	monitorThread.start();
	getStartServerMenuItem().setEnabled(true);
	getJMonitStartMenuItem().setEnabled(false);
		
	}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:21:14)
 */
public void mouseClicked(MouseEvent e) {}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:21:14)
 */
public void mouseEntered(MouseEvent e) {}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:21:14)
 */
public void mouseExited(MouseEvent e) {}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:21:14)
 */
public void mousePressed(MouseEvent e) {}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:21:14)
 */
public void mouseReleased(MouseEvent e) {
	String cSrvName = getSellectedServer();
	String oSrvName = getMem_srvNameLabel().getText();
	if(cSrvName == null || cSrvName.trim().length() == 0){
		userSignoff();
		return;
	}
	MonitorUserInfo info = getUserInfo(cSrvName);
	if(info == null ){
		userSignoff();
		return;
	}
	if(getUserInfo(cSrvName).getIsMemTimeMonit()){
		getMemTime_btn().setSelected(true);
		getMemNoTime_btn().setSelected(false);
		getMemFresh_btn().setEnabled(false);
		if(oSrvName == null || !oSrvName.trim().equals(cSrvName.trim())){
			getMem_srvNameLabel().setText(cSrvName);
			getTotalMemLabel().setText("请稍后...");
			getFreeMemLabel().setText("请稍后...");
			getAvailableMemLabel().setText("请稍后...");
		
		}
	}else{
		getMemTime_btn().setSelected(true);
		getMemNoTime_btn().setSelected(true);
		getMemFresh_btn().setEnabled(true);
		if(oSrvName == null || !oSrvName.trim().equals(cSrvName.trim())){
			getMem_srvNameLabel().setText(cSrvName);
			getTotalMemLabel().setText("请点击刷新按钮...");
			getFreeMemLabel().setText("请点击刷新按钮...");
			getAvailableMemLabel().setText("请点击刷新按钮...");
		
		}
		
	}
	mgrSignin1();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-21 23:21:14)
 */
public void newMethod() {}
public synchronized void  newPackageReceived(int sessionId, String pkg, java.io.DataOutputStream outputStream){
	
	if( pkg.startsWith("1000") ) //session logoff
	{
		sessionRemoved(sessionId, pkg );
	}
	else if( pkg.startsWith("1001") ) //reflash the server information
	{
		this.reflashServer(sessionId,  pkg);
		
	}
	else if(pkg.startsWith("20010")){
		String srvN = pkg.substring(5,pkg.indexOf("}"));
		String s = pkg.substring(pkg.indexOf("}")+1,pkg.length());
	  	MonitorUserInfo info = getUserInfo(srvN);
	  	if(info != null && info.getMonitorReloadSettingDlg() != null){
		  	info.getMonitorReloadSettingDlg().setPkg(s);
	  	}
	  	showMessage("取重置信息成功");
   }else if(pkg.startsWith("20011")){
	   showMessage("error...取重置信息失败");
   }
   else if(pkg.startsWith("20020")){
	   pkg = pkg.substring(6,pkg.length());
	   String sn = pkg.substring(0,pkg.indexOf("|"));
	   String desc = pkg.substring(pkg.indexOf("|"),pkg.length());
	   showMessage("服务器["+sn+"]"+desc+"重置成功");
	   MonitorUserInfo info = getUserInfo(sn);
	   if(info != null && info.getMonitorReloadSettingDlg() != null){
		   ReloadSettingsDlg dlg = info.getMonitorReloadSettingDlg();
		   dlg.showMessage("服务器["+sn+"]"+desc+"重置成功");
	   }
   }else if(pkg.startsWith("20021")){
	    pkg = pkg.substring(6,pkg.length());
	   	String sn = pkg.substring(0,pkg.indexOf("|"));
	   	String desc = pkg.substring(pkg.indexOf("|"),pkg.length());
	   	showMessage("error...服务器["+sn+"]"+desc+"重置失败");
	  	MonitorUserInfo info = getUserInfo(sn);
	   	if(info != null && info.getMonitorReloadSettingDlg() != null){
		   ReloadSettingsDlg dlg = info.getMonitorReloadSettingDlg();
		   dlg.showMessage("error...服务器["+sn+"]"+desc+"重置失败");
	   }
   }
   
   //管理员登录;0-succ,1-sever no start,2-no define mgr,3-mgr invalid,4-fail
   else if(pkg.startsWith("20040")){
	   showMessage("管理员登录成功");
	   addUserInfo(getCurUserInfo());
	   setCurUserInfo(null);
	   mgrSignin();
   }else if(pkg.startsWith("20041")){
	    setCurUserInfo(null);
	   showMessage("error...应用服务未启动，请启动服务！");
	   getJMgrLogonMenuItem().setEnabled(false);
	   getStartServerMenuItem().setEnabled(true);
	 
   }else if(pkg.startsWith("20042")){
	    setCurUserInfo(null);
	   showMessage("error...该管理员不存在");
   }else if(pkg.startsWith("20043")){
	    setCurUserInfo(null);
	   showMessage("error...管理员名称或密码不正确");
   }else if(pkg.startsWith("20044")){
	    setCurUserInfo(null);
	   showMessage("error...系统管理员登录失败");
   }
  
	//资源回收
   else if(pkg.startsWith("20090")){
	   showMessage("资源回收成功,当前内存使用情况如下："+pkg.substring(5,pkg.length()));
   }else if(pkg.startsWith("20091")){
	   showMessage("资源回收失败");
   }
   //系统内存查询
   else if(pkg.startsWith("20110")){
	   pkg = pkg.substring(pkg.indexOf("|")+1,pkg.length());
	   String total = pkg.substring(0,pkg.indexOf("|"));
	   pkg = pkg.substring(pkg.indexOf("|")+1,pkg.length());
	   String free = pkg.substring(0,pkg.indexOf("|"));
	   String av = pkg.substring(pkg.indexOf("|")+1,pkg.length());
	   String srvName1 = getMem_srvNameLabel().getText();
	   String servName = getSellectedServer();
	   if(srvName1 != null && srvName1.trim().length()>0 && servName != null && srvName1.trim().equals(servName.trim())){
		   getTotalMemLabel().setText(total);
		   getFreeMemLabel().setText(free);
		   getAvailableMemLabel().setText(av);
	   }
	      
   }else if(pkg.startsWith("20111")){
		   showMessage("取系统内存资源失败");
   }
   //取系统属性参数配置
   else if(pkg.startsWith("20120")){
		showMessage("取系统属性配置成功");
		pkg = pkg.substring(6,pkg.length());
		int index = pkg.indexOf("|");
		String srvName = pkg.substring(0,index);
		getSysPropTextArea().append("服务器["+srvName+"]的系统属性参数配置如下：\n"+pkg.substring(index+1,pkg.length()));
	} else if(pkg.startsWith("20121")){
		 showMessage("取系统属性参数配置失败");
	}
	else  if(pkg.startsWith("30010")|| pkg.startsWith("30020")|| pkg.startsWith("44440")){
		 pkg = pkg.substring(6,pkg.length());
		 String srvName = pkg.substring(0,pkg.indexOf("|"));
		 String desc = pkg.substring(pkg.indexOf("|")+1,pkg.length());
		 showMessage("重置"+desc+"成功");
		 MonitorUserInfo info = getUserInfo(srvName.trim());
		 if(info != null && info.getMonitorReloadSettingDlg() != null){
			 info.getMonitorReloadSettingDlg().showMessage("重置"+desc+"成功");
		 }
	} else if(pkg.startsWith("30011")|| pkg.startsWith("30021")|| pkg.startsWith("44441")){
		 pkg = pkg.substring(6,pkg.length());
		 String srvName = pkg.substring(0,pkg.indexOf("|"));
		 String desc = pkg.substring(pkg.indexOf("|")+1,pkg.length());
		 showMessage("重置"+desc+"失败");
		 MonitorUserInfo info = getUserInfo(srvName.trim());
		 if(info != null && info.getMonitorReloadSettingDlg() != null){
			 info.getMonitorReloadSettingDlg().showMessage("重置"+desc+"失败");
		 }
	}
	else{
		showMessage(pkg);
	}
	
	
}
public void newSocketAccepted(java.net.Socket socket)
{
	try
	{

		ServerReadThread readThread = new ServerReadThread(socket, this);		

		
		Thread aThread = new Thread(readThread);
		readThread.runningThread = aThread;
		aThread.setName("Server read thread");
		aThread.start();
		
		serverProcessors.addElement( readThread );

		String session = String.valueOf( readThread.sessionID );
		this.readThreads.put(session, readThread);
		
		this.addServer(readThread.sessionID, readThread.getClientHostName() );
	}
	catch (Exception e)
	{
		System.out.println("Exception from ProjectManagerServer.newSocketAccepted(Socket): " + e);
		showMessage(e.toString());
	}
	
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-1 22:27:42)
 * @param serverName java.lang.String
 * @param msg java.lang.String
 */
private void reflashServer(int sessionId, String msg)
{
	TableDataModel dataModel = getTableDataModel();
	for( int i=0; i<dataModel.getRowCount(); i++)
	{
		String[] rowData =(String[]) dataModel.getRowObject(i);
		if( rowData[0].equals(String.valueOf(sessionId)) )
		{
			rowData[2] = "NORMAL";			
			int idx = msg.indexOf("|");
			rowData[3] = msg.substring(4, idx);
			rowData[4] = msg.substring(idx+1);
			
			dataModel.updateRow(rowData);
			break;
		}
	}
}
/**
 * Comment
 */
public void reloadMenuItem_ActionPerformed() {

	String serverName = getSellectedServer();
	
	if( serverName != null)
	{
		ReloadSettingsDlg dlg = null;
		MonitorUserInfo info = getUserInfo(serverName);
		if(info == null) return;
		//dlg = info.getMonitorReloadSettingDlg();
	//	if(info.getMonitorReloadSettingDlg() == null){
			dlg = new ReloadSettingsDlg(this);
	//	}else{
	//		dlg.iniSettings();
	//	}
		dlg.setVisible(true);
		dlg.setModal(true);
		dlg.show();
	}
	
	return;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-10 13:11:52)
 */
private void removeAllUserInfo() {
	if(this.userInfo != null){
		Enumeration e = this.userInfo.keys();
		
		for(;e.hasMoreElements();){
			removeUserInfo((String)e.nextElement());
		}
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-1 22:27:42)
 * @param serverName java.lang.String
 * @param msg java.lang.String
 */
private void removeServer(int sessionId)
{
	TableDataModel dataModel = getTableDataModel();
	for( int i=0; i<dataModel.getRowCount(); i++)
	{
		String[] rowData =(String[]) dataModel.getRowObject(i);
		if( rowData[0].equals(String.valueOf(sessionId)) )
		{
			dataModel.deleteRow(i);
			break;
		}
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-22 10:22:12)
 * @param serverName java.lang.String
 */
private synchronized void removeUserInfo(String serverName) {
	MonitorUserInfo info = getUserInfo(serverName);
	if(info != null){
		if(info.getMonitorReloadSettingDlg() != null){
			info.getMonitorReloadSettingDlg().dispose();
			info.setMonitorReloadSettingDlg(null);
		}
		info.setIsCtxTimeMonit(false);
		info.setIsMemTimeMonit(false);
	}
	if(this.userInfo.containsKey(serverName)){
		this.userInfo.remove(serverName);
	}
}
/**
 * Comment
 */
public void restartServerMenuItem_ActionPerformed() {

	String serverName = getSellectedServer();
	if( serverName != null)
	{
		RestartServerDlg dlg = new RestartServerDlg(this);
		dlg.setServerName(serverName);
		dlg.show();
	}
	return;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-1 22:38:19)
 */
public void run()
{
	while (!isStop)
	{
		try
		{
			Thread.currentThread().sleep(checkInterval);

			getServerMessage();
			String servName = getSellectedServer();
			if(servName != null && servName.trim().length() != 0){
				MonitorUserInfo  info1 = (MonitorUserInfo)getUserInfo(servName);
				if(info1 != null && info1.getIsMemTimeMonit()){
					 sendreloadpara("2011",servName);
				}
			}
			
		}
		catch (Exception e)
		{
			showMessage("Exception in INBSMonitor.run():"+e.getMessage());
			
		}

	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-7-16 15:41:38)
 */
public void sendreloadpara(String arg1,String arg2)
{
	
	if (arg1==null) return;
	if (arg2==null) return;
	int sid = getSessionId();
	Vector processors = (Vector)this.serverProcessors.clone();
	//int i = Integer.parseInt(arg2);
	for (int i = 0; i < processors.size(); i++)
	{
		ServerReadThread readThread = (ServerReadThread) processors.elementAt(i);
		try
		{
			//if (readThread.getClientHostName()==arg2)
			if (readThread.sessionID==sid)
			readThread.sentdata(arg1); //reload para
		}
		catch (Exception e)
		{
		}
	}
	return;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-1 22:20:48)
 * @param msg java.lang.String
 */
private void sessionRemoved(int sessionID, String msg)
{
	//alarm every server that this session(in msg)was log off!!!
	for (int i = 0; i < this.serverProcessors.size(); i++)
	{
		ServerReadThread readThread = (ServerReadThread) serverProcessors.elementAt(i);
		try
		{
			if (readThread.sessionID != sessionID)
				readThread.sentdata(msg);
		}
		catch (Exception e)
		{
		}
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-22 10:11:33)
 * @param info com.ecc.echannels.util.MonitorUserInfo
 */
public void setCurUserInfo(MonitorUserInfo info) {
	this.curUserInfo = info;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-29 11:43:51)
 * @param tf boolean
 */
public void setMemFreshType(boolean tf) {
	if(tf){
		if(getMemFresh_btn().isEnabled()) getMemFresh_btn().setEnabled(false);
		if(getMemNoTime_btn().isSelected()){
			getMemNoTime_btn().setSelected(false);
			getMemTime_btn().setSelected(true);
		}
	}else{
		if(getMemTime_btn().isSelected()){
			getMemTime_btn().setSelected(false);
			getMemNoTime_btn().setSelected(true);
		}
		if(!getMemFresh_btn().isEnabled()) getMemFresh_btn().setEnabled(true);
	}
	
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-22 11:26:59)
 * @param tf boolean
 */
public void setStartServerEnable(boolean tf) {
	getStartServerMenuItem().setEnabled(tf);
}
/**
 * Comment
 */
public void settingsMenuItem_ActionPerformed() {
	SettingsDlg dlg = new SettingsDlg(this);
	dlg.setInterval(this.checkInterval / 1000);
	dlg.setMaxLine(this.maxLines);
	dlg.setModal(true);
	dlg.show();
	if (dlg.isOK) {
		checkInterval = Integer.parseInt(dlg.getInterval()) * 1000;
		maxLines = Integer.parseInt(dlg.getMaxLine());
	}
	return;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-1 21:16:31)
 * @param message java.lang.String
 */
public synchronized void showMessage(String message)
{
	lines++;

	if( lines > maxLines)
	{
		lines = 0;
		getMessageArea().setText("");
	}

	String tmp = getCurrentDateTime("yyyy-MM-dd HH:mm:ss") + "\tMonitor\t\t" + message + "\n";
	getMessageArea().append(tmp);
//	String
	
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-1 21:16:31)
 * @param message java.lang.String
 */
private synchronized void showMessage(String owner, String message)
{
	lines++;

	if( lines > maxLines)
	{
		lines = 0;
		getMessageArea().setText("");
	}

	String tmp = getCurrentDateTime("yyyy-MM-dd HH:mm:ss\t") + owner + "\t" + message + "\n";
	getMessageArea().append(tmp);

}	
/**
 * Comment
 */
public void startServerMenuItem_ActionPerformed() {
		StartServerDlg dlg = new StartServerDlg(this);
//		dlg.setServerName(serverName);
		dlg.show();
	
	return;
}
/**
 * Comment
 */
public int getSessionId() {
	int idx = getServerTable().getSelectedRow();
	if( idx == -1)
		return -1;

	String[] row = (String[])getTableDataModel().getRowObject(idx);

	return Integer.parseInt(row[0]);
}

public void stopMenuItem_ActionPerformed() {
	int idx = getServerTable().getSelectedRow();
	if( idx == -1)
		return;

	String[] row = (String[])getTableDataModel().getRowObject(idx);

	String sessionId = row[0];
	String serverName = row[1];
	if( serverName != null)
	{
//		if( JOptionPane.showConfirmDialog(this, "Are you sure to restart server[ " + serverName + " ]'s session manager thread?", "Restart session manager thread", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION )
		{
			ServerReadThread readThread = (ServerReadThread)this.readThreads.get(sessionId );
			if(readThread != null )
			{
				readThread.close();
				
//				readThread.isStop = true;
				
				removeServer(Integer.parseInt(sessionId));
				readThreads.remove(sessionId);
				serverProcessors.remove(readThread);

			}
		}
	}
	return;
}
/**
 * Comment
 */
public void sysProp_btn_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	String serverName = getSellectedServer();
	if(serverName == null || serverName.trim().length() == 0)return;
	showMessage(serverName,"取系统属性参数配置");
	sendreloadpara("2012"+serverName,serverName);
	return;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-20 14:36:33)
 */
public void userSignoff() {
	getJMonitStartMenuItem().setEnabled(false);	
	getJClearMenuItem().setEnabled(false);
	getRestartServerMenuItem().setEnabled(false);
	getReloadMenuItem().setEnabled(false);
	getSettingsMenuItem().setEnabled(false);
	getStopMenuItem().setEnabled(false);
	getStartServerMenuItem().setEnabled(true);
	getExitMenuItem().setEnabled(true);
	getJMonitMemoryMenuItem().setEnabled(false);
	getJMonitSysPropMenuItem().setEnabled(false);
	getJGCMenuItem().setEnabled(false);
	getJMgrLogonMenuItem().setEnabled(true);
	getJUserSignOffMenuItem().setEnabled(false);
	getMemFresh_btn().setEnabled(false);
	getMemNoTime_btn().setEnabled(false);
	getMemTime_btn().setEnabled(false);
	getSysProp_btn().setEnabled(false);
	
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-10 13:30:46)
 * @param e java.awt.event.WindowEvent
 */
public void windowActivated(WindowEvent e) {}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-10 13:31:11)
 * @param e java.awt.event.WindowEvent
 */
public void windowClosed(WindowEvent e) {}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-10 13:27:16)
 * @param e java.awt.event.WindowEvent
 */
public void windowClosing(WindowEvent e) {
	removeAllUserInfo();
	dispose();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-10 13:30:18)
 * @param e java.awt.event.WindowEvent
 */
public void windowDeactivated(WindowEvent e) {}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-10 13:28:05)
 * @param e java.awt.event.WindowEvent
 */
public void windowDeiconified(WindowEvent e) {}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-10 13:29:44)
 * @param e java.awt.event.WindowEvent
 */
public void windowIconified(WindowEvent e) {}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-10 13:31:42)
 * @param e java.awt.event.WindowEvent
 */
public void windowOpened(WindowEvent e) {}
}
