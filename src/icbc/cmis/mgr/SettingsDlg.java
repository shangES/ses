package icbc.cmis.mgr;

import javax.swing.*;
import javax.swing.event.*;
/**
 * Insert the type's description here.
 * Creation date: (2001-5-17 9:54:06)
 * @author: Administrator
 */
public class SettingsDlg extends JDialog {
	private JButton ivjCancelButton = null;
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private JTextField ivjIntervalTextField = null;
	private JPanel ivjJDialogContentPane = null;
	private JLabel ivjJLabel1 = null;
	private JLabel ivjJLabel2 = null;
	private JTextField ivjMaxLineTextField = null;
	private JButton ivjOKButton = null;
	public boolean isOK = false;

class IvjEventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == SettingsDlg.this.getCancelButton()) 
				connEtoM1(e);
			if (e.getSource() == SettingsDlg.this.getOKButton()) 
				connEtoC1(e);
		};
	};
/**
 * SettingsDlg constructor comment.
 */
public SettingsDlg() {
	super();
	initialize();
}
/**
 * SettingsDlg constructor comment.
 * @param owner java.awt.Dialog
 */
public SettingsDlg(java.awt.Dialog owner) {
	super(owner);
}
/**
 * SettingsDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 */
public SettingsDlg(java.awt.Dialog owner, String title) {
	super(owner, title);
}
/**
 * SettingsDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 * @param modal boolean
 */
public SettingsDlg(java.awt.Dialog owner, String title, boolean modal) {
	super(owner, title, modal);
}
/**
 * SettingsDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param modal boolean
 */
public SettingsDlg(java.awt.Dialog owner, boolean modal) {
	super(owner, modal);
}
/**
 * SettingsDlg constructor comment.
 * @param owner java.awt.Frame
 */
public SettingsDlg(java.awt.Frame owner) {
	super(owner);
	initialize();
}
/**
 * SettingsDlg constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 */
public SettingsDlg(java.awt.Frame owner, String title) {
	super(owner, title);
}
/**
 * SettingsDlg constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 * @param modal boolean
 */
public SettingsDlg(java.awt.Frame owner, String title, boolean modal) {
	super(owner, title, modal);
}
/**
 * SettingsDlg constructor comment.
 * @param owner java.awt.Frame
 * @param modal boolean
 */
public SettingsDlg(java.awt.Frame owner, boolean modal) {
	super(owner, modal);
}
/**
 * connEtoC1:  (OKButton.action.actionPerformed(java.awt.event.ActionEvent) --> SettingsDlg.oKButton_ActionPerformed()V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.oKButton_ActionPerformed();
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoM1:  (CancelButton.action.actionPerformed(java.awt.event.ActionEvent) --> SettingsDlg.dispose()V)
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
			ivjCancelButton.setText("取消");
			ivjCancelButton.setBounds(215, 134, 85, 27);
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
 * Insert the method's description here.
 * Creation date: (2001-5-17 10:01:41)
 * @return java.lang.String
 */
public String getInterval() {
	return getIntervalTextField().getText();
}
/**
 * Return the IntervalTextField property value.
 * @return javax.swing.JTextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextField getIntervalTextField() {
	if (ivjIntervalTextField == null) {
		try {
			ivjIntervalTextField = new javax.swing.JTextField();
			ivjIntervalTextField.setName("IntervalTextField");
			ivjIntervalTextField.setBounds(171, 39, 126, 20);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjIntervalTextField;
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
			getJDialogContentPane().add(getIntervalTextField(), getIntervalTextField().getName());
			getJDialogContentPane().add(getMaxLineTextField(), getMaxLineTextField().getName());
			getJDialogContentPane().add(getOKButton(), getOKButton().getName());
			getJDialogContentPane().add(getCancelButton(), getCancelButton().getName());
			getJDialogContentPane().add(getJLabel1(), getJLabel1().getName());
			getJDialogContentPane().add(getJLabel2(), getJLabel2().getName());
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
 * Return the JLabel1 property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getJLabel1() {
	if (ivjJLabel1 == null) {
		try {
			ivjJLabel1 = new javax.swing.JLabel();
			ivjJLabel1.setName("JLabel1");
			ivjJLabel1.setText("刷新间隔(秒):");
			ivjJLabel1.setBounds(58, 44, 95, 16);
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
			ivjJLabel2.setText("窗口显示最大行数：");
			ivjJLabel2.setBounds(22, 78, 152, 17);
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
 * Insert the method's description here.
 * Creation date: (2001-5-17 10:01:41)
 * @return java.lang.String
 */
public String getMaxLine() {
	return getMaxLineTextField().getText();
}
/**
 * Return the MaxLineTextField property value.
 * @return javax.swing.JTextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextField getMaxLineTextField() {
	if (ivjMaxLineTextField == null) {
		try {
			ivjMaxLineTextField = new javax.swing.JTextField();
			ivjMaxLineTextField.setName("MaxLineTextField");
			ivjMaxLineTextField.setBounds(171, 77, 126, 20);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjMaxLineTextField;
}
/**
 * Return the OKButton property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getOKButton() {
	if (ivjOKButton == null) {
		try {
			ivjOKButton = new javax.swing.JButton();
			ivjOKButton.setName("OKButton");
			ivjOKButton.setText("确定");
			ivjOKButton.setBounds(111, 134, 85, 27);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjOKButton;
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
	getCancelButton().addActionListener(ivjEventHandler);
	getOKButton().addActionListener(ivjEventHandler);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("SettingsDlg");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("设置");
		setVisible(true);
		setModal(true);
		setSize(370, 179);
		setResizable(false);
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
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		SettingsDlg aSettingsDlg;
		aSettingsDlg = new SettingsDlg();
		aSettingsDlg.setModal(true);
		aSettingsDlg.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		aSettingsDlg.show();
		java.awt.Insets insets = aSettingsDlg.getInsets();
		aSettingsDlg.setSize(aSettingsDlg.getWidth() + insets.left + insets.right, aSettingsDlg.getHeight() + insets.top + insets.bottom);
		aSettingsDlg.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of javax.swing.JDialog");
		exception.printStackTrace(System.out);
	}
}
/**
 * Comment
 */
public void oKButton_ActionPerformed() {
	if( getInterval().length()>0 && getMaxLine().length()>0)
	{
		isOK = true;
		dispose();
	}
	return;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-5-17 10:04:00)
 * @param interval int
 */
public void setInterval(int interval) {

	getIntervalTextField().setText(String.valueOf(interval));
	}
/**
 * Insert the method's description here.
 * Creation date: (2001-5-17 10:04:00)
 * @param interval int
 */
public void setMaxLine(int maxLine) {

	getMaxLineTextField().setText(String.valueOf(maxLine));
	}
}
