package icbc.cmis.mgr;

/**
 * Insert the type's description here.
 * Creation date: (2001-11-19 1:27:15)
 * @author: Administrator
 */
public class UserNameAndPassPanel extends javax.swing.JPanel {
	private javax.swing.JLabel ivjJLabel1 = null;
	private javax.swing.JLabel ivjJLabel2 = null;
	private javax.swing.JTextField ivjJUserNameTextField = null;
	private javax.swing.JPasswordField ivjJUserPasswordField = null;
/**
 * UserNameAndPassPanel constructor comment.
 */
public UserNameAndPassPanel() {
	super();
	initialize();
}
/**
 * UserNameAndPassPanel constructor comment.
 * @param layout java.awt.LayoutManager
 */
public UserNameAndPassPanel(java.awt.LayoutManager layout) {
	super(layout);
}
/**
 * UserNameAndPassPanel constructor comment.
 * @param layout java.awt.LayoutManager
 * @param isDoubleBuffered boolean
 */
public UserNameAndPassPanel(java.awt.LayoutManager layout, boolean isDoubleBuffered) {
	super(layout, isDoubleBuffered);
}
/**
 * UserNameAndPassPanel constructor comment.
 * @param isDoubleBuffered boolean
 */
public UserNameAndPassPanel(boolean isDoubleBuffered) {
	super(isDoubleBuffered);
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
			ivjJLabel1.setText("用户名称");
			ivjJLabel1.setBounds(21, 20, 69, 16);
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
			ivjJLabel2.setText("用户密码");
			ivjJLabel2.setBounds(21, 72, 69, 16);
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
 * Return the JUserNameTextField property value.
 * @return javax.swing.JTextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextField getJUserNameTextField() {
	if (ivjJUserNameTextField == null) {
		try {
			ivjJUserNameTextField = new javax.swing.JTextField();
			ivjJUserNameTextField.setName("JUserNameTextField");
			ivjJUserNameTextField.setBounds(92, 20, 154, 20);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJUserNameTextField;
}
/**
 * Return the JPasswordField property value.
 * @return javax.swing.JPasswordField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPasswordField getJUserPasswordField() {
	if (ivjJUserPasswordField == null) {
		try {
			ivjJUserPasswordField = new javax.swing.JPasswordField();
			ivjJUserPasswordField.setName("JUserPasswordField");
			ivjJUserPasswordField.setBounds(92, 67, 154, 20);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJUserPasswordField;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-19 15:26:20)
 * @return java.lang.String
 */
public String getUserName() {
	return getJUserNameTextField().getText();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-11-19 15:27:22)
 * @return java.lang.String
 */
public String getUserPass() {
	return String.valueOf(getJUserPasswordField().getPassword());
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
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("UserNameAndPassPanel");
		setLayout(null);
		setSize(294, 120);
		add(getJLabel1(), getJLabel1().getName());
		add(getJLabel2(), getJLabel2().getName());
		add(getJUserNameTextField(), getJUserNameTextField().getName());
		add(getJUserPasswordField(), getJUserPasswordField().getName());
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
	// user code begin {2}
	// user code end
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		javax.swing.JFrame frame = new javax.swing.JFrame();
		UserNameAndPassPanel aUserNameAndPassPanel;
		aUserNameAndPassPanel = new UserNameAndPassPanel();
		frame.setContentPane(aUserNameAndPassPanel);
		frame.setSize(aUserNameAndPassPanel.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		frame.show();
		java.awt.Insets insets = frame.getInsets();
		frame.setSize(frame.getWidth() + insets.left + insets.right, frame.getHeight() + insets.top + insets.bottom);
		frame.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of javax.swing.JPanel");
		exception.printStackTrace(System.out);
	}
}
}
