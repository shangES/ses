package icbc.cmis.mgr;

import javax.swing.*;
import javax.swing.event.*;
/**
 * Insert the type's description here.
 * Creation date: (2001-4-4 15:44:25)
 * @author: Administrator
 */
public class StartServerDlg extends JDialog {
	private JButton ivjCancelButton = null;
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private JPanel ivjJDialogContentPane = null;
	private JLabel ivjJLabel1 = null;
	private JLabel ivjJLabel2 = null;
	private JLabel ivjJLabel3 = null;
	private JLabel ivjJLabel5 = null;
	private JPasswordField ivjPasswordField = null;
	private JButton ivjSubmitButton = null;
	private JTextField ivjUserNameField = null;
	private String serverName = null;
	private JSeparator ivjJSeparator1 = null;
	private JTextArea ivjMessageArea = null;
	private JScrollPane ivjJScrollPane1 = null;
	private JLabel ivjJLabel4 = null;
	private JTextField ivjServletField = null;
	private JTextField ivjServerNameField = null;
	private INBSMonitor monitor = null;

class IvjEventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == StartServerDlg.this.getCancelButton()) 
				connEtoM1(e);
			if (e.getSource() == StartServerDlg.this.getSubmitButton()) 
				connEtoC1(e);
		};
	};
/**
 * RestartServerDlg constructor comment.
 */
public StartServerDlg() {
	super();
	initialize();
}
/**
 * RestartServerDlg constructor comment.
 * @param owner java.awt.Dialog
 */
public StartServerDlg(java.awt.Dialog owner) {
	super(owner);
}
/**
 * RestartServerDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 */
public StartServerDlg(java.awt.Dialog owner, String title) {
	super(owner, title);
}
/**
 * RestartServerDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 * @param modal boolean
 */
public StartServerDlg(java.awt.Dialog owner, String title, boolean modal) {
	super(owner, title, modal);
}
/**
 * RestartServerDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param modal boolean
 */
public StartServerDlg(java.awt.Dialog owner, boolean modal) {
	super(owner, modal);
}
/**
 * RestartServerDlg constructor comment.
 * @param owner java.awt.Frame
 */
public StartServerDlg(java.awt.Frame owner) {
	super(owner);
	this.monitor = (INBSMonitor)owner;
	initialize();
}
/**
 * RestartServerDlg constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 */
public StartServerDlg(java.awt.Frame owner, String title) {
	super(owner, title);
}
/**
 * RestartServerDlg constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 * @param modal boolean
 */
public StartServerDlg(java.awt.Frame owner, String title, boolean modal) {
	super(owner, title, modal);
}
/**
 * RestartServerDlg constructor comment.
 * @param owner java.awt.Frame
 * @param modal boolean
 */
public StartServerDlg(java.awt.Frame owner, boolean modal) {
	super(owner, modal);
}
/**
 * connEtoC1:  (SubmitButton.action.actionPerformed(java.awt.event.ActionEvent) --> RestartServerDlg.submitButton_ActionPerformed()V)
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
 * connEtoM1:  (CancelButton.action.actionPerformed(java.awt.event.ActionEvent) --> RestartServerDlg.dispose()V)
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
			ivjCancelButton.setBounds(391, 64, 85, 27);
			ivjCancelButton.setActionCommand("CancelButton");
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
			getJDialogContentPane().add(getJLabel2(), getJLabel2().getName());
			getJDialogContentPane().add(getJLabel3(), getJLabel3().getName());
			getJDialogContentPane().add(getServerNameField(), getServerNameField().getName());
			getJDialogContentPane().add(getServletField(), getServletField().getName());
			getJDialogContentPane().add(getUserNameField(), getUserNameField().getName());
			getJDialogContentPane().add(getPasswordField(), getPasswordField().getName());
			getJDialogContentPane().add(getSubmitButton(), getSubmitButton().getName());
			getJDialogContentPane().add(getCancelButton(), getCancelButton().getName());
			getJDialogContentPane().add(getJLabel5(), getJLabel5().getName());
			getJDialogContentPane().add(getJSeparator1(), getJSeparator1().getName());
			getJDialogContentPane().add(getJScrollPane1(), getJScrollPane1().getName());
			getJDialogContentPane().add(getJLabel4(), getJLabel4().getName());
			getJDialogContentPane().add(getJLabel1(), getJLabel1().getName());
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
			ivjJLabel1.setText("服务器(Web):");
			ivjJLabel1.setBounds(26, 31, 92, 22);
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
			ivjJLabel2.setText("用户名:");
			ivjJLabel2.setBounds(47, 140, 45, 16);
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
			ivjJLabel3.setText("密码:");
			ivjJLabel3.setBounds(58, 172, 45, 16);
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
			ivjJLabel4.setText("Servlet:");
			ivjJLabel4.setBounds(51, 63, 66, 16);
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
 * Return the JLabel5 property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getJLabel5() {
	if (ivjJLabel5 == null) {
		try {
			ivjJLabel5 = new javax.swing.JLabel();
			ivjJLabel5.setName("JLabel5");
			ivjJLabel5.setText("请输入管理员用户和口令");
			ivjJLabel5.setBounds(19, 108, 231, 23);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJLabel5;
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
			ivjJScrollPane1.setBounds(11, 218, 472, 82);
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
 * Return the JSeparator1 property value.
 * @return javax.swing.JSeparator
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JSeparator getJSeparator1() {
	if (ivjJSeparator1 == null) {
		try {
			ivjJSeparator1 = new javax.swing.JSeparator();
			ivjJSeparator1.setName("JSeparator1");
			ivjJSeparator1.setBounds(6, 204, 474, 9);
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
 * Return the MessageArea property value.
 * @return javax.swing.JTextArea
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextArea getMessageArea() {
	if (ivjMessageArea == null) {
		try {
			ivjMessageArea = new javax.swing.JTextArea();
			ivjMessageArea.setName("MessageArea");
			ivjMessageArea.setBounds(0, 0, 464, 81);
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
 * Return the PasswordField property value.
 * @return javax.swing.JPasswordField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPasswordField getPasswordField() {
	if (ivjPasswordField == null) {
		try {
			ivjPasswordField = new javax.swing.JPasswordField();
			ivjPasswordField.setName("PasswordField");
			ivjPasswordField.setBounds(137, 167, 202, 24);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPasswordField;
}
/**
 * Return the ServerNameField property value.
 * @return javax.swing.JTextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextField getServerNameField() {
	if (ivjServerNameField == null) {
		try {
			ivjServerNameField = new javax.swing.JTextField();
			ivjServerNameField.setName("ServerNameField");
			ivjServerNameField.setBounds(137, 30, 200, 25);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjServerNameField;
}
/**
 * Return the ServletField property value.
 * @return javax.swing.JTextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextField getServletField() {
	if (ivjServletField == null) {
		try {
			ivjServletField = new javax.swing.JTextField();
			ivjServletField.setName("ServletField");
			ivjServletField.setText("icbccmisStartup");
			ivjServletField.setBounds(138, 66, 200, 25);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjServletField;
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
			ivjSubmitButton.setBounds(391, 22, 85, 27);
			ivjSubmitButton.setActionCommand("SubmitButton");
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
 * Return the UserNameField property value.
 * @return javax.swing.JTextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextField getUserNameField() {
	if (ivjUserNameField == null) {
		try {
			ivjUserNameField = new javax.swing.JTextField();
			ivjUserNameField.setName("UserNameField");
			ivjUserNameField.setBounds(138, 133, 202, 24);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjUserNameField;
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
		setName("RestartServerDlg");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(497, 307);
		setTitle("Start Cmis Server");
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
		RestartServerDlg aRestartServerDlg;
		aRestartServerDlg = new RestartServerDlg();
		aRestartServerDlg.setModal(true);
		aRestartServerDlg.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		aRestartServerDlg.show();
		java.awt.Insets insets = aRestartServerDlg.getInsets();
		aRestartServerDlg.setSize(aRestartServerDlg.getWidth() + insets.left + insets.right, aRestartServerDlg.getHeight() + insets.top + insets.bottom);
		aRestartServerDlg.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of javax.swing.JDialog");
		exception.printStackTrace(System.out);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-4 21:12:43)
 * @param msg java.lang.String
 */
private void showMessage(String msg)
{
	getMessageArea().append(msg + "\n");
}
/**
 * Comment
 */
public void submitButton_ActionPerformed() {
	String userName = getUserNameField().getText().trim();
	String password = new String(getPasswordField().getPassword());

	String serverName = getServerNameField().getText();
	String servletName = getServletField().getText();
	if( serverName == null || serverName.length() == 0 )
	{
		showMessage("请输入WEB地址[IP:port]!");
		return;
	}
	if( userName.length() == 0 )
	{
		showMessage("请输入用户ID!");
		return;
	}
	else if( password.length() == 0)
	{
		showMessage("请输入用户口令!");
		return;
	}
	try{
		String url ="http://" +  serverName + "/servlet/" + servletName;
		java.net.URL aURL = new java.net.URL( url );

		String postData  = "userName=" + userName +"&password=" + password+"&statusCheck=true";
				

		java.net.HttpURLConnection urlConnection = (java.net.HttpURLConnection) (aURL.openConnection());

		urlConnection.setRequestMethod("POST");
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(false);

		this.monitor.showMessage("Connecting with the server...");

		urlConnection.setRequestProperty("Content-Length", String.valueOf(postData.length()));
		urlConnection.connect();

		java.io.OutputStream out = urlConnection.getOutputStream();
		
		out.write( postData.getBytes());

		out.close();
		
		java.io.DataInputStream in = new java.io.DataInputStream(urlConnection.getInputStream());
		int resultLength = in.readInt();

		byte responseBuffer[] = new byte[resultLength];
		in.readFully(responseBuffer);
		String result = new String(responseBuffer, "UTF8");
		in.close();

		this.monitor.showMessage(result);
		this.monitor.setStartServerEnable(false);
		
//		this.getAppletContext().showDocument(aURL);
	}catch(Exception e)
	{
		showMessage("内部错误:" + e);				
	}
	return;

}
}
