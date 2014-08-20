package icbc.cmis.mgr;

import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
/**
 * Insert the type's description here.
 * Creation date: (2001-11-22 17:48:28)
 * @author: Administrator
 */
public class MemoryMonitorSetting extends javax.swing.JDialog {
	private javax.swing.JPanel ivjJDialogContentPane = null;
	private javax.swing.JRadioButton ivjMemNoTimeRadioButton = null;
	private javax.swing.JRadioButton ivjMemTimeMonitorRadioButton = null;
	private javax.swing.JButton ivjMemMonitor_btn = null;
	private INBSMonitor monitor = null;

class IvjEventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == MemoryMonitorSetting.this.getMemMonitor_btn()) 
				connEtoC1(e);
		};
	};
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
/**
 * MemoryMonitorSetting constructor comment.
 */
public MemoryMonitorSetting() {
	super();
	initialize();
}
/**
 * MemoryMonitorSetting constructor comment.
 * @param owner java.awt.Dialog
 */
public MemoryMonitorSetting(java.awt.Dialog owner) {
	super(owner);
}
/**
 * MemoryMonitorSetting constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 */
public MemoryMonitorSetting(java.awt.Dialog owner, String title) {
	super(owner, title);
}
/**
 * MemoryMonitorSetting constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 * @param modal boolean
 */
public MemoryMonitorSetting(java.awt.Dialog owner, String title, boolean modal) {
	super(owner, title, modal);
}
/**
 * MemoryMonitorSetting constructor comment.
 * @param owner java.awt.Dialog
 * @param modal boolean
 */
public MemoryMonitorSetting(java.awt.Dialog owner, boolean modal) {
	super(owner, modal);
}
/**
 * MemoryMonitorSetting constructor comment.
 * @param owner java.awt.Frame
 */
public MemoryMonitorSetting(java.awt.Frame owner) {
	super(owner);
	this.monitor = (INBSMonitor)owner;
	initialize();
}
/**
 * MemoryMonitorSetting constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 */
public MemoryMonitorSetting(java.awt.Frame owner, String title) {
	super(owner, title);
}
/**
 * MemoryMonitorSetting constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 * @param modal boolean
 */
public MemoryMonitorSetting(java.awt.Frame owner, String title, boolean modal) {
	super(owner, title, modal);
}
/**
 * MemoryMonitorSetting constructor comment.
 * @param owner java.awt.Frame
 * @param modal boolean
 */
public MemoryMonitorSetting(java.awt.Frame owner, boolean modal) {
	super(owner, modal);
}
/**
 * connEtoC1:  (MemMonitor_btn.action.actionPerformed(java.awt.event.ActionEvent) --> MemoryMonitorSetting.memMonitor_btn_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.memMonitor_btn_ActionPerformed(arg1);
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
			ivjJDialogContentPane.setLayout(null);
			getJDialogContentPane().add(getMemTimeMonitorRadioButton(), getMemTimeMonitorRadioButton().getName());
			getJDialogContentPane().add(getMemNoTimeRadioButton(), getMemNoTimeRadioButton().getName());
			getJDialogContentPane().add(getMemMonitor_btn(), getMemMonitor_btn().getName());
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
 * Return the MemMonitor_btn property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getMemMonitor_btn() {
	if (ivjMemMonitor_btn == null) {
		try {
			ivjMemMonitor_btn = new javax.swing.JButton();
			ivjMemMonitor_btn.setName("MemMonitor_btn");
			ivjMemMonitor_btn.setText("确  定");
			ivjMemMonitor_btn.setBounds(101, 68, 85, 27);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMemMonitor_btn;
}
/**
 * Return the MemNoTimeRadioButton property value.
 * @return javax.swing.JRadioButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JRadioButton getMemNoTimeRadioButton() {
	if (ivjMemNoTimeRadioButton == null) {
		try {
			ivjMemNoTimeRadioButton = new javax.swing.JRadioButton();
			ivjMemNoTimeRadioButton.setName("MemNoTimeRadioButton");
			ivjMemNoTimeRadioButton.setText("非实时监听");
			ivjMemNoTimeRadioButton.setBounds(173, 17, 108, 24);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMemNoTimeRadioButton;
}
/**
 * Return the MemTimeMonitorRadioButton property value.
 * @return javax.swing.JRadioButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JRadioButton getMemTimeMonitorRadioButton() {
	if (ivjMemTimeMonitorRadioButton == null) {
		try {
			ivjMemTimeMonitorRadioButton = new javax.swing.JRadioButton();
			ivjMemTimeMonitorRadioButton.setName("MemTimeMonitorRadioButton");
			ivjMemTimeMonitorRadioButton.setSelected(true);
			ivjMemTimeMonitorRadioButton.setText("实时监听");
			ivjMemTimeMonitorRadioButton.setBounds(36, 17, 108, 24);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMemTimeMonitorRadioButton;
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
	getMemMonitor_btn().addActionListener(ivjEventHandler);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("MemoryMonitorSetting");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("内存监听设置");
		setSize(309, 126);
		setResizable(false);
		setContentPane(getJDialogContentPane());
		initConnections();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	javax.swing.ButtonGroup grop = new javax.swing.ButtonGroup();
	grop.add(getMemTimeMonitorRadioButton());
	grop.add(getMemNoTimeRadioButton());
	java.awt.Dimension dm = this.getSize();
	dm.height = dm.height+20;
	java.awt.Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	this.setBounds((d.width-dm.width)/2, (d.height-dm.height)/3, dm.width, dm.height);
	
	// user code end
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		MemoryMonitorSetting aMemoryMonitorSetting;
		aMemoryMonitorSetting = new MemoryMonitorSetting();
		aMemoryMonitorSetting.setModal(true);
		aMemoryMonitorSetting.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		aMemoryMonitorSetting.show();
		java.awt.Insets insets = aMemoryMonitorSetting.getInsets();
		aMemoryMonitorSetting.setSize(aMemoryMonitorSetting.getWidth() + insets.left + insets.right, aMemoryMonitorSetting.getHeight() + insets.top + insets.bottom);
		aMemoryMonitorSetting.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of javax.swing.JDialog");
		exception.printStackTrace(System.out);
	}
}
/**
 * Comment
 */
public void memMonitor_btn_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	String serverName = this.monitor.getSellectedServer();
	MonitorUserInfo info = this.monitor.getUserInfo(serverName);
	if(info == null){
		this.dispose();
		return;
	}
	
	if(getMemNoTimeRadioButton().isSelected()){
		if(info.getIsMemTimeMonit()) info.setIsMemTimeMonit(false);
		this.monitor.setMemFreshType(false);
	}
	if(getMemTimeMonitorRadioButton().isSelected()){
		if(!info.getIsMemTimeMonit()) info.setIsMemTimeMonit(true);
		this.monitor.setMemFreshType(true);
	}
	this.dispose();
	return;
}
}
