package icbc.cmis.mgr;

/**
 * Insert the type's description here.
 * Creation date: (2001-11-21 16:32:10)
 * @author: Administrator
 */
public class ListenerPortDlg extends javax.swing.JDialog {
	private javax.swing.JPanel ivjJDialogContentPane = null;
	private javax.swing.JLabel ivjJLabel1 = null;
	private javax.swing.JTextField ivjListenerPortField = null;
	private javax.swing.JButton ivjOk_btn = null;
	private javax.swing.JTextArea ivjMessageArea = null;
	private javax.swing.JScrollPane ivjJScrollPane1 = null;
	private INBSMonitor monitor = null;
	IvjEventHandler ivjEventHandler = new IvjEventHandler();

class IvjEventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == ListenerPortDlg.this.getOk_btn()) 
				connEtoC1(e);
		};
	};
/**
 * ListenerPortDlg constructor comment.
 */
public ListenerPortDlg() {
	super();
	initialize();
}
/**
 * ListenerPortDlg constructor comment.
 * @param owner java.awt.Dialog
 */
public ListenerPortDlg(java.awt.Dialog owner) {
	super(owner);
}
/**
 * ListenerPortDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 */
public ListenerPortDlg(java.awt.Dialog owner, String title) {
	super(owner, title);
}
/**
 * ListenerPortDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param title java.lang.String
 * @param modal boolean
 */
public ListenerPortDlg(java.awt.Dialog owner, String title, boolean modal) {
	super(owner, title, modal);
}
/**
 * ListenerPortDlg constructor comment.
 * @param owner java.awt.Dialog
 * @param modal boolean
 */
public ListenerPortDlg(java.awt.Dialog owner, boolean modal) {
	super(owner, modal);
}
/**
 * ListenerPortDlg constructor comment.
 * @param owner java.awt.Frame
 */
public ListenerPortDlg(java.awt.Frame owner) {
	super(owner);
	this.monitor = (INBSMonitor)owner;
	initialize();
}
/**
 * ListenerPortDlg constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 */
public ListenerPortDlg(java.awt.Frame owner, String title) {
	super(owner, title);
}
/**
 * ListenerPortDlg constructor comment.
 * @param owner java.awt.Frame
 * @param title java.lang.String
 * @param modal boolean
 */
public ListenerPortDlg(java.awt.Frame owner, String title, boolean modal) {
	super(owner, title, modal);
}
/**
 * ListenerPortDlg constructor comment.
 * @param owner java.awt.Frame
 * @param modal boolean
 */
public ListenerPortDlg(java.awt.Frame owner, boolean modal) {
	super(owner, modal);
}
/**
 * connEtoC1:  (Ok_btn.action.actionPerformed(java.awt.event.ActionEvent) --> ListenerPortDlg.ok_btn_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.ok_btn_ActionPerformed(arg1);
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
			getJDialogContentPane().add(getJLabel1(), getJLabel1().getName());
			getJDialogContentPane().add(getListenerPortField(), getListenerPortField().getName());
			getJDialogContentPane().add(getOk_btn(), getOk_btn().getName());
			getJDialogContentPane().add(getJScrollPane1(), getJScrollPane1().getName());
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
			ivjJLabel1.setText("INBSMonitor监听端口：");
			ivjJLabel1.setBounds(9, 12, 143, 16);
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
 * Return the JScrollPane1 property value.
 * @return javax.swing.JScrollPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JScrollPane getJScrollPane1() {
	if (ivjJScrollPane1 == null) {
		try {
			ivjJScrollPane1 = new javax.swing.JScrollPane();
			ivjJScrollPane1.setName("JScrollPane1");
			ivjJScrollPane1.setBounds(2, 36, 231, 67);
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
 * Return the ListenerPortField property value.
 * @return javax.swing.JTextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextField getListenerPortField() {
	if (ivjListenerPortField == null) {
		try {
			ivjListenerPortField = new javax.swing.JTextField();
			ivjListenerPortField.setName("ListenerPortField");
			ivjListenerPortField.setBounds(151, 11, 35, 20);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjListenerPortField;
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
			ivjMessageArea.setBorder(new javax.swing.border.EtchedBorder());
			ivjMessageArea.setBounds(0, 0, 231, 59);
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
 * Return the Ok_btn property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getOk_btn() {
	if (ivjOk_btn == null) {
		try {
			ivjOk_btn = new javax.swing.JButton();
			ivjOk_btn.setName("Ok_btn");
			ivjOk_btn.setText("确定");
			ivjOk_btn.setBounds(78, 108, 64, 20);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjOk_btn;
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
	getOk_btn().addActionListener(ivjEventHandler);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("ListenerPortDlg");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("INBSMonitor start");
		setSize(235, 168);
		setModal(true);
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
		ListenerPortDlg aListenerPortDlg;
		aListenerPortDlg = new ListenerPortDlg();
		aListenerPortDlg.setModal(true);
		aListenerPortDlg.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		aListenerPortDlg.show();
		java.awt.Insets insets = aListenerPortDlg.getInsets();
		aListenerPortDlg.setSize(aListenerPortDlg.getWidth() + insets.left + insets.right, aListenerPortDlg.getHeight() + insets.top + insets.bottom);
		aListenerPortDlg.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of javax.swing.JDialog");
		exception.printStackTrace(System.out);
	}
}
/**
 * Comment
 */
public void ok_btn_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	String port  = getListenerPortField().getText();
	if(port == null|| port.trim().length() == 0){
		getMessageArea().append("请输入端口号！\n");
		return;
	}
	int iPort = 1800;
	try{
		iPort = Integer.valueOf(port).intValue();
	}catch(Exception e){
		getMessageArea().append("请输入一个有效的端口号！\n");
		getListenerPortField().setText("");
		return;
	}
	this.setVisible(false);
	this.monitor.monitorStart(iPort);
	this.dispose();
	return;
}
}
