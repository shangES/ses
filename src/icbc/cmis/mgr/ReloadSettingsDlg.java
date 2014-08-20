package icbc.cmis.mgr;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

public class ReloadSettingsDlg extends JDialog {
	private JButton ivjCancelButton = null;
	private JPanel ivjJDialogContentPane = null;
	private JButton ivjSubmitButton = null;
	private String serverName = null;
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private int lines = 0;
	private int maxLines = 12;
	public INBSMonitor monitor = null;
	private JTextArea ivjMessageArea = null;
	private JScrollPane ivjJScrollPane1 = null;
	private JPanel ivjCheckboxPannel = null;
	private JPanel ivjJPanel1 = null;
	private JPanel ivjJPanel2 = null;
	private String pkg = null;
	private java.util.Vector checkboxList = null;
	private JScrollPane ivjJScrollPane2 = null;
	private JPanel ivjJPanel3 = null;
	private JCheckBox ivjDBUserMapBox = null;
	private JCheckBox ivjErrorMapBox = null;

     private javax.swing.JCheckBox ivjJCheckBox = null;
class IvjEventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == ReloadSettingsDlg.this.getCancelButton()) 
				connEtoM1(e);
			if (e.getSource() == ReloadSettingsDlg.this.getSubmitButton()) 
				connEtoC1(e);
		};
	};
/**
 * ReloadSettingsDlg constructor comment.
 */
public ReloadSettingsDlg() {
	super();
	initialize();
}
/**
 * ReloadSettingsDlg constructor comment.
 * @param owner java.awt.Dialog
 */
public ReloadSettingsDlg(java.awt.Dialog owner) {
	
	super(owner);
}
/**
 * ReloadSettingsDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 */
public ReloadSettingsDlg(java.awt.Dialog owner, String title) {
	super(owner, title);
}
/**
 * ReloadSettingsDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 * @param modal boolean
 */
public ReloadSettingsDlg(java.awt.Dialog owner, String title, boolean modal) {
	super(owner, title, modal);
}
/**
 * ReloadSettingsDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param modal boolean
 */
public ReloadSettingsDlg(java.awt.Dialog owner, boolean modal) {
	super(owner, modal);
}
/**
 * ReloadSettingsDlg constructor comment.
 * @param owner java.awt.Frame
 */
public ReloadSettingsDlg(java.awt.Frame owner) {
	super(owner);
	monitor = (INBSMonitor)owner;
	serverName = monitor.getSellectedServer();
	monitor.getUserInfo(serverName).setMonitorReloadSettingDlg(this);
	initialize();
}
/**
 * ReloadSettingsDlg constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 */
public ReloadSettingsDlg(java.awt.Frame owner, String title) {
	super(owner, title);
}
/**
 * ReloadSettingsDlg constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 * @param modal boolean
 */
public ReloadSettingsDlg(java.awt.Frame owner, String title, boolean modal) {
	super(owner, title, modal);
}
/**
 * ReloadSettingsDlg constructor comment.
 * @param owner java.awt.Frame
 * @param modal boolean
 */
public ReloadSettingsDlg(java.awt.Frame owner, boolean modal) {
	super(owner, modal);
}
/**
 * connEtoC1:  (SubmitButton.action.actionPerformed(java.awt.event.ActionEvent) --> ReloadSettingsDlg.submitButton_ActionPerformed()V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.submitButton_ActionPerformed();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoM1:  (CancelButton.action.actionPerformed(java.awt.event.ActionEvent) --> ReloadSettingsDlg.dispose()V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoM1(java.awt.event.ActionEvent arg1) {
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
 * Return the CancelButton property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getCancelButton() {
	if (ivjCancelButton == null) {
		try {
			ivjCancelButton = new javax.swing.JButton();
			ivjCancelButton.setName("CancelButton");
			ivjCancelButton.setText("关闭");
			ivjCancelButton.setBounds(379, 5, 85, 27);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjCancelButton;
}
/**
 * Return the CheckboxPannel property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getCheckboxPannel() {
	if (ivjCheckboxPannel == null) {
		try {
			ivjCheckboxPannel = new javax.swing.JPanel();
			ivjCheckboxPannel.setName("CheckboxPannel");
			ivjCheckboxPannel.setPreferredSize(new java.awt.Dimension(790, 0));
			ivjCheckboxPannel.setBorder(new javax.swing.border.EtchedBorder());
			ivjCheckboxPannel.setLayout(null);
			ivjCheckboxPannel.setBounds(0, 0, 723, 256);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjCheckboxPannel;
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
 * Return the JCheckBox2 property value.
 * @return javax.swing.JCheckBox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JCheckBox getDBUserMapBox() {
	if (ivjDBUserMapBox == null) {
		try {
			ivjDBUserMapBox = new javax.swing.JCheckBox();
			ivjDBUserMapBox.setName("DBUserMapBox");
			ivjDBUserMapBox.setPreferredSize(new java.awt.Dimension(300, 24));
			ivjDBUserMapBox.setText("数据库[用户-密码校验串]映射表");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjDBUserMapBox;
}
/**
 * Return the JCheckBox1 property value.
 * @return javax.swing.JCheckBox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JCheckBox getErrorMapBox() {
	if (ivjErrorMapBox == null) {
		try {
			ivjErrorMapBox = new javax.swing.JCheckBox();
			ivjErrorMapBox.setName("ErrorMapBox");
			ivjErrorMapBox.setPreferredSize(new java.awt.Dimension(250,24));
			ivjErrorMapBox.setText("错误[代码-信息]映射表");
			ivjErrorMapBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjErrorMapBox;
}
/**
 * Return the JDialogContentPane property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getJDialogContentPane() {
	if (ivjJDialogContentPane == null) {
		try {
			ivjJDialogContentPane = new javax.swing.JPanel();
			ivjJDialogContentPane.setName("JDialogContentPane");
			ivjJDialogContentPane.setLayout(new java.awt.BorderLayout());
			getJDialogContentPane().add(getJPanel1(), "South");
			getJDialogContentPane().add(getJScrollPane2(), "Center");
			getJDialogContentPane().add(getJPanel3(), java.awt.BorderLayout.NORTH);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJDialogContentPane;
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
			ivjJPanel1.setPreferredSize(new java.awt.Dimension(3, 120));
			ivjJPanel1.setBorder(new javax.swing.border.CompoundBorder());
			ivjJPanel1.setLayout(new java.awt.BorderLayout());
			getJPanel1().add(getJScrollPane1(), "Center");
			getJPanel1().add(getJPanel2(), "South");
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
			ivjJPanel2.setPreferredSize(new java.awt.Dimension(0, 40));
			ivjJPanel2.setBorder(new javax.swing.border.EtchedBorder());
			ivjJPanel2.setLayout(null);
			getJPanel2().add(getSubmitButton(), getSubmitButton().getName());
			getJPanel2().add(getCancelButton(), getCancelButton().getName());
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
			ivjJPanel3.setPreferredSize(new java.awt.Dimension(0, 30));
			ivjJPanel3.setBorder(new javax.swing.border.CompoundBorder());
			ivjJPanel3.setLayout(new java.awt.BorderLayout());
			getJPanel3().add(getErrorMapBox(), "West");
			getJPanel3().add(getDBUserMapBox(), "East");
			ivjJPanel3.add(getIvjJCheckBox(), java.awt.BorderLayout.CENTER);
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
			ivjJScrollPane2.setPreferredSize(new java.awt.Dimension(516, 82));
			getJScrollPane2().setViewportView(getCheckboxPannel());
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
 * Return the JTextArea1 property value.
 * @return javax.swing.JTextArea
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextArea getMessageArea() {
	if (ivjMessageArea == null) {
		try {
			ivjMessageArea = new javax.swing.JTextArea();
			ivjMessageArea.setName("MessageArea");
			ivjMessageArea.setLineWrap(false);
			ivjMessageArea.setBorder(new javax.swing.border.CompoundBorder());
			ivjMessageArea.setBounds(0, 0, 492, 78);
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
 * Return the SubmitButton property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getSubmitButton() {
	if (ivjSubmitButton == null) {
		try {
			ivjSubmitButton = new javax.swing.JButton();
			ivjSubmitButton.setName("SubmitButton");
			ivjSubmitButton.setText("确定");
			ivjSubmitButton.setBounds(216, 5, 85, 27);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjSubmitButton;
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
 * Creation date: (2001-11-28 14:26:16)
 */
public boolean iniSettings(){
	try{
		if(checkboxList != null){
			for(int i = 0;i<checkboxList.size();i++){
				JCheckBox b = (JCheckBox)checkboxList.elementAt(i);
				try{
					getCheckboxPannel().remove(b);
				}catch(Exception ee){}
			}
		}
		showMessage("取服务器重置信息");
		int count = 0;
		this.monitor.sendreloadpara("2001|"+this.serverName,this.serverName);
		try{
			while(this.pkg == null){
			   if(count >=14) throw new Exception("网络超时");
				count++;
				Thread.currentThread().sleep(1000);
			}
		}catch(Exception e){
			if(this.monitor.getUserInfo(serverName) != null)
				this.monitor.getUserInfo(serverName).setMonitorReloadSettingDlg(null);
				dispose();
				showMessage("取服务器重置信息失败");
				return false;
			}
		int ind = this.pkg.indexOf("|");
		int lines = (Integer.valueOf(pkg.substring(0,ind)).intValue())/2+1;
		String pkg1 = pkg.substring(ind+1,pkg.length());
		this.pkg = null;
		checkboxList = new java.util.Vector();
		int x = (int)getCheckboxPannel().getLocation().getX()+100;
		int y = (int)getCheckboxPannel().getLocation().getY()+10;
		StringTokenizer toke = new StringTokenizer(pkg1,"|");

		int tmpX = 0;
		int tmpY = 0;
		while(toke.hasMoreElements()){
			if(tmpX <= x) tmpX = x;
			tmpY = y;
			String s1 = (String) toke.nextElement();
			int index = s1.indexOf("=");
			String name = s1.substring(0,index);
			String desc = s1.substring(index+1,s1.length());
			JCheckBox box = new JCheckBox();
			box.setVisible(true);
			box.setName(name);
			box.setText(desc);
			box.setSize(250,24);
			box.setLocation(x,y);
			getCheckboxPannel().add(box);
			checkboxList.add(box);
			x = x+300;
			if(((int)getCheckboxPannel().getLocation().getX()+getCheckboxPannel().getWidth() - x) < 250){
				x = (int)getCheckboxPannel().getLocation().getX()+100;
				y = y+20;
			}
		}
		getCheckboxPannel().setPreferredSize(new java.awt.Dimension(tmpX+50,tmpY+50));
		getCheckboxPannel().setSize(tmpX+50,tmpY+50);
		showMessage("取服务器重置信息成功");
		return true;
	}catch(Exception e){
		e.printStackTrace();
		dispose();
		return false;
	}
}
/**
 * Initializes connections
 * @exception java.lang.Exception The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() throws java.lang.Exception {
	// user code begin {1}
	// user code end
	getCancelButton().addActionListener(ivjEventHandler);
	getSubmitButton().addActionListener(ivjEventHandler);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("ReloadSettingsDlg");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setSize(726, 442);
		setTitle("重载服务器配置");
		setContentPane(getJDialogContentPane());
		initConnections();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	setTitle("重载服务器[ "+this.serverName+" ]配置");
	java.awt.Dimension dx = getJScrollPane2().getPreferredSize();
	getCheckboxPannel().setPreferredSize(new java.awt.Dimension((int)dx.getWidth(),(int)dx.getHeight()-30));
	java.awt.Dimension dm = this.getSize();
	dm.height = dm.height+20;
	java.awt.Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	this.setBounds((d.width-dm.width)/2, (d.height-dm.height)/3, dm.width, dm.height);
	iniSettings();
	setResizable(false);
	// user code end
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		ReloadSettingsDlg aReloadSettingsDlg;
		aReloadSettingsDlg = new ReloadSettingsDlg();
		aReloadSettingsDlg.setModal(true);
		aReloadSettingsDlg.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		aReloadSettingsDlg.show();
		java.awt.Insets insets = aReloadSettingsDlg.getInsets();
		aReloadSettingsDlg.setSize(aReloadSettingsDlg.getWidth() + insets.left + insets.right, aReloadSettingsDlg.getHeight() + insets.top + insets.bottom);
		aReloadSettingsDlg.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of javax.swing.JDialog");
		exception.printStackTrace(System.out);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-28 16:08:25)
 * @param s java.lang.String
 */
public void setPkg(String s) {
	pkg = s;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-1 21:16:31)
 * @param message java.lang.String
 */
public void showMessage(String message)
{
	lines++;

	if( lines > maxLines)
	{
		lines = 0;
		getMessageArea().setText("");
	}

	String tmp = getCurrentDateTime("yyyy-MM-dd HH:mm:ss") + ":  " + message + "\n";
	getMessageArea().append(tmp);

	
}
/**
 * Comment
 */
public void submitButton_ActionPerformed() {
	if(this.checkboxList != null){
		for(int i = 0;i<checkboxList.size();i++){
			JCheckBox box = (JCheckBox)checkboxList.elementAt(i);
			String id = box.getName();
			String desc = box.getText();
			if(box.isSelected()){
				showMessage("服务器["+this.serverName+"]"+desc+"重置!");
				monitor.showMessage("服务器["+this.serverName+"]"+desc+"重置!");
				monitor.sendreloadpara("2002|"+this.serverName+"|"+id+"|"+desc,this.serverName);
			}	
		}
	}
	if(getErrorMapBox().isSelected()){
		showMessage("服务器["+this.serverName+"]"+getErrorMapBox().getText()+"重置!");
		monitor.showMessage("服务器["+this.serverName+"]"+getErrorMapBox().getText()+"重置!");
		monitor.sendreloadpara("3001|"+this.serverName+"|"+getErrorMapBox().getText(),this.serverName);
	}
	if(getDBUserMapBox().isSelected()){
		showMessage("服务器["+this.serverName+"]"+getDBUserMapBox().getText()+"重置!");
		monitor.showMessage("服务器["+this.serverName+"]"+getDBUserMapBox().getText()+"重置!");
		monitor.sendreloadpara("3002|"+this.serverName+"|"+getDBUserMapBox().getText(),this.serverName);
	}
	if(getIvjJCheckBox().isSelected()){
		showMessage("服务器["+this.serverName+"]"+getIvjJCheckBox().getText()+"重置!");
		monitor.showMessage("服务器["+this.serverName+"]"+getIvjJCheckBox().getText()+"重置!");
		monitor.sendreloadpara("4444|"+this.serverName+"|"+getIvjJCheckBox().getText(),this.serverName);
	}
	return;
}
	/**
	 * This method initializes ivjJCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private javax.swing.JCheckBox getIvjJCheckBox() {
		if(ivjJCheckBox == null) {
			ivjJCheckBox = new javax.swing.JCheckBox();
			ivjJCheckBox.setText("交易限制列表");
			ivjJCheckBox.setName("operationLimitBtn");
		}
		return ivjJCheckBox;
	}
}
