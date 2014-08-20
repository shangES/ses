package icbc.cmis.mgr;

import javax.swing.*;
/**
 * Insert the type's description here.
 * Creation date: (2001-11-19 1:24:56)
 * @author: Administrator
 */
public class MgrLogonDlg extends JDialog {
	private javax.swing.JPanel ivjJDialogContentPane = null;
	private UserNameAndPassPanel ivjUserNameAndPassPanel = null;
	private javax.swing.JButton ivjLogon_cancel = null;
	private javax.swing.JButton ivjLogon_ok = null;
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private INBSMonitor monitor = null;

class IvjEventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == MgrLogonDlg.this.getLogon_ok()) 
				connEtoC1(e);
			if (e.getSource() == MgrLogonDlg.this.getLogon_cancel()) 
				connEtoM1(e);
		};
	};
/**
 * MgrLogonDlg constructor comment.
 */
public MgrLogonDlg() {
	super();
	initialize();
}
/**
 * MgrLogonDlg constructor comment.
 * @param owner java.awt.Dialog
 */
public MgrLogonDlg(java.awt.Dialog owner) {
	super(owner);
}
/**
 * MgrLogonDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 */
public MgrLogonDlg(java.awt.Dialog owner, String title) {
	super(owner, title);
}
/**
 * MgrLogonDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 * @param modal boolean
 */
public MgrLogonDlg(java.awt.Dialog owner, String title, boolean modal) {
	super(owner, title, modal);
}
/**
 * MgrLogonDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param modal boolean
 */
public MgrLogonDlg(java.awt.Dialog owner, boolean modal) {
	super(owner, modal);
}
/**
 * MgrLogonDlg constructor comment.
 * @param owner java.awt.Frame
 */
public MgrLogonDlg(java.awt.Frame owner) {
	super(owner);
	this.monitor = (INBSMonitor)owner;
	initialize();
}
/**
 * MgrLogonDlg constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 */
public MgrLogonDlg(java.awt.Frame owner, String title) {
	super(owner, title);
}
/**
 * MgrLogonDlg constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 * @param modal boolean
 */
public MgrLogonDlg(java.awt.Frame owner, String title, boolean modal) {
	super(owner, title, modal);
}
/**
 * MgrLogonDlg constructor comment.
 * @param owner java.awt.Frame
 * @param modal boolean
 */
public MgrLogonDlg(java.awt.Frame owner, boolean modal) {
	super(owner, modal);
}
/**
 * connEtoC1:  (Logon_ok.action.actionPerformed(java.awt.event.ActionEvent) --> MgrLogonDlg.logon_ok_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.logon_ok_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoM1:  (Logon_cancel.action.actionPerformed(java.awt.event.ActionEvent) --> MgrLogonDlg.dispose()V)
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
 * Return the JDialogContentPane property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getJDialogContentPane() {
	if (ivjJDialogContentPane == null) {
		try {
			ivjJDialogContentPane = new javax.swing.JPanel();
			ivjJDialogContentPane.setName("JDialogContentPane");
			ivjJDialogContentPane.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
			ivjJDialogContentPane.setLayout(null);
			ivjJDialogContentPane.setAlignmentY(java.awt.Component.CENTER_ALIGNMENT);
			getJDialogContentPane().add(getUserNameAndPassPanel(), getUserNameAndPassPanel().getName());
			getJDialogContentPane().add(getLogon_ok(), getLogon_ok().getName());
			getJDialogContentPane().add(getLogon_cancel(), getLogon_cancel().getName());
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
 * Return the Logon_cancel property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getLogon_cancel() {
	if (ivjLogon_cancel == null) {
		try {
			ivjLogon_cancel = new javax.swing.JButton();
			ivjLogon_cancel.setName("Logon_cancel");
			ivjLogon_cancel.setText("取  消");
			ivjLogon_cancel.setBounds(170, 138, 85, 27);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjLogon_cancel;
}
/**
 * Return the Logon_ok property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getLogon_ok() {
	if (ivjLogon_ok == null) {
		try {
			ivjLogon_ok = new javax.swing.JButton();
			ivjLogon_ok.setName("Logon_ok");
			ivjLogon_ok.setText("登  录");
			ivjLogon_ok.setBounds(45, 138, 85, 27);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjLogon_ok;
}
/**
 * Return the UserNameAndPassPanel property value.
 * @return com.ecc.echannels.util.UserNameAndPassPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private UserNameAndPassPanel getUserNameAndPassPanel() {
	if (ivjUserNameAndPassPanel == null) {
		try {
			ivjUserNameAndPassPanel = new UserNameAndPassPanel();
			ivjUserNameAndPassPanel.setName("UserNameAndPassPanel");
			ivjUserNameAndPassPanel.setBorder(new javax.swing.border.EtchedBorder());
			ivjUserNameAndPassPanel.setBounds(0, 0, 295, 125);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjUserNameAndPassPanel;
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
 * Initializes connections
 * @exception java.lang.Exception The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() throws java.lang.Exception {
	// user code begin {1}
	// user code end
	getLogon_ok().addActionListener(ivjEventHandler);
	getLogon_cancel().addActionListener(ivjEventHandler);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("MgrLogonDlg");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setModal(true);
		setSize(293, 192);
		setTitle("系统管理员登录");
		setContentPane(getJDialogContentPane());
		initConnections();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	java.awt.Dimension dm = this.getSize();
	dm.height = dm.height+20;
	java.awt.Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	this.setBounds((d.width-dm.width)/2, (d.height-dm.height)/3, dm.width, dm.height);
	
	// user code end
}
/**
 * Comment
 */
public void logon_ok_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	String srvName = monitor.getSellectedServer();
	if(srvName == null || srvName.trim().length() == 0) return;
	
	monitor.showMessage("服务器["+srvName+"]管理员登录!");
	String mgrName = getUserNameAndPassPanel().getUserName();
	String mgrPass = getUserNameAndPassPanel().getUserPass();
	if(mgrName == null || mgrName.trim().length() == 0){
	    monitor.showMessage("请输入管理员名称");
	    return;
	}
	if(mgrPass == null || mgrPass.trim().length() == 0){
	    monitor.showMessage("请输入管理员密码");
	    return;
	}
	MonitorUserInfo info = new MonitorUserInfo();
	info.setServerName(srvName);
	info.setUserName(mgrName);
	info.setUserPass(mgrPass);
	info.setUserType(true);
	info.setIsCtxTimeMonit(false);
	info.setIsMemTimeMonit(false);
	monitor.setCurUserInfo(info);
	monitor.sendreloadpara("2004|"+mgrName+"|"+mgrPass,srvName.trim());
	this.dispose();
	return;
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		MgrLogonDlg aMgrLogonDlg;
		aMgrLogonDlg = new MgrLogonDlg();
		aMgrLogonDlg.setModal(true);
		aMgrLogonDlg.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		aMgrLogonDlg.show();
		java.awt.Insets insets = aMgrLogonDlg.getInsets();
		aMgrLogonDlg.setSize(aMgrLogonDlg.getWidth() + insets.left + insets.right, aMgrLogonDlg.getHeight() + insets.top + insets.bottom);
		aMgrLogonDlg.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of javax.swing.JDialog");
		exception.printStackTrace(System.out);
	}
}
}
