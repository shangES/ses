package icbc.cmis.base;

import java.awt.*;
import java.awt.event.*;
/**
 * 
 *   @(#) AwtConsoleFrame.java	1.0 05/13/2000
 *   Copyright (c) 1999 EChannel R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/13/2000
 *   @author  ZhongMingChang
 *   
 */
public class AwtConsoleFrame extends Frame implements ActionListener, WindowListener {
	private Button ivjClearButton = null;
	private Button ivjCloseButton = null;
	private Panel ivjContentsPane = null;
	private Panel ivjPanel1 = null;
	private TextArea ivjTextArea1 = null;

	private Image image;
/**
 * Constructor
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public AwtConsoleFrame() {
	super();
	initialize();
}
/**
 * AwtConsoleFrame constructor comment.
 * @param title java.lang.String
 */
public AwtConsoleFrame(String title) {
	super(title);
}
/**
 * Method to handle events for the ActionListener interface.
 * @param e java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void actionPerformed(ActionEvent e) {
	// user code begin {1}
	// user code end
	if ((e.getSource() == getClearButton()) ) {
		connEtoM1(e);
	}
	if ((e.getSource() == getCloseButton()) ) {
		connEtoM2(e);
	}
	// user code begin {2}
	// user code end
}
/**
 * connEtoC1:  (AwtConsoleFrame.window.windowClosing(java.awt.event.WindowEvent) --> AwtConsoleFrame.dispose()V)
 * @param arg1 java.awt.event.WindowEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC1(WindowEvent arg1) {
	try {
		// user code begin {1}
		Trace.enableTraces = false;
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
 * connEtoM1:  (ClearButton.action.actionPerformed(java.awt.event.ActionEvent) --> TextArea1.text)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoM1(ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		getTextArea1().setText(new java.lang.String());
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * connEtoM2:  (CloseButton.action.actionPerformed(java.awt.event.ActionEvent) --> AwtConsoleFrame.dispose()V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoM2(ActionEvent arg1) {
	try {
		// user code begin {1}
		Trace.enableTraces = false;
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
 * Return the ClearButton property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Button getClearButton() {
	if (ivjClearButton == null) {
		try {
			ivjClearButton = new java.awt.Button();
			ivjClearButton.setName("ClearButton");
			ivjClearButton.setLabel("Clear");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjClearButton;
}
/**
 * Return the CloseButton property value.
 * @return java.awt.Button
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Button getCloseButton() {
	if (ivjCloseButton == null) {
		try {
			ivjCloseButton = new java.awt.Button();
			ivjCloseButton.setName("CloseButton");
			ivjCloseButton.setLabel("Close");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjCloseButton;
}
/**
 * Return the ContentsPane property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Panel getContentsPane() {
	if (ivjContentsPane == null) {
		try {
			ivjContentsPane = new java.awt.Panel();
			ivjContentsPane.setName("ContentsPane");
			ivjContentsPane.setLayout(new java.awt.BorderLayout());
			getContentsPane().add(getPanel1(), "South");
			getContentsPane().add(getTextArea1(), "Center");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjContentsPane;
}
/**
 * Return the Panel1 property value.
 * @return java.awt.Panel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private Panel getPanel1() {
	if (ivjPanel1 == null) {
		try {
			ivjPanel1 = new java.awt.Panel();
			ivjPanel1.setName("Panel1");
			ivjPanel1.setLayout(new java.awt.BorderLayout());
			ivjPanel1.setBackground(java.awt.Color.lightGray);
			getPanel1().add(getClearButton(), "West");
			getPanel1().add(getCloseButton(), "East");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjPanel1;
}
/**
 * Return the TextArea1 property value.
 * @return java.awt.TextArea
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private TextArea getTextArea1() {
	if (ivjTextArea1 == null) {
		try {
			ivjTextArea1 = new java.awt.TextArea();
			ivjTextArea1.setName("TextArea1");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	};
	return ivjTextArea1;
}
/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	// exception.printStackTrace(System.out);
}
public boolean imageUpdate(java.awt.Image img, int flags, int x, int y, int w, int h)
{

	boolean ret = true;
	if (img == this.image)
	{
		if ((flags & java.awt.image.ImageObserver.PROPERTIES) == java.awt.image.ImageObserver.PROPERTIES)
		{
			ret = true;
		}
		if ((flags & java.awt.image.ImageObserver.ALLBITS) == java.awt.image.ImageObserver.ALLBITS)
		{
			try
			{
				this.setIconImage(img);
			}
			catch (Exception e)
			{
			}
			ret = false;
		}
		return ret;
	}
	else
		return super.imageUpdate(img, flags, x, y, w, h);
}
/**
 * Initializes connections
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() {
	// user code begin {1}
	// user code end
	this.addWindowListener(this);
	getClearButton().addActionListener(this);
	getCloseButton().addActionListener(this);
}
/**
 * Initialize the class.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initialize() {
	// user code begin {1}
	// user code end
	setName("AwtConsoleFrame");
	setLayout(new java.awt.BorderLayout());
	setSize(426, 240);
	setTitle("Editor Console");
	add(getContentsPane(), "Center");
	initConnections();
	// user code begin {2}
	try{
		image = loadImage("/logo.jpg"); //= this.getToolkit().createImage("logo.jpg");
	}catch(Exception e){}

//	setIconImage(getToolkit().createImage("logo.jpg"));
	// user code end
}
private Image loadImage(String name)
{
	java.net.URL url = null;
	try
	{

		url = getClass().getResource(name);
		java.awt.Toolkit toolkit = getToolkit();
		Image img = toolkit.getImage(url);
		toolkit.prepareImage(img, -1, -1, this);
		return img;
	}
	catch (Exception e)
	{
		//System.err.println("ImageButton.loadImage() : Couldn't load the image " + name + ".");
		return null;
	}
}
/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		AwtConsoleFrame aAwtConsoleFrame;
		aAwtConsoleFrame = new AwtConsoleFrame();
		try {
			Class aCloserClass = Class.forName("com.ibm.uvm.abt.edit.WindowCloser");
			Class parmTypes[] = { java.awt.Window.class };
			Object parms[] = { aAwtConsoleFrame };
			java.lang.reflect.Constructor aCtor = aCloserClass.getConstructor(parmTypes);
			aCtor.newInstance(parms);
		} catch (java.lang.Throwable exc) {};
		aAwtConsoleFrame.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of AwtConsoleFrame:");
		exception.printStackTrace(System.out);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-17 10:17:16)
 */
public void reset()
{
		getTextArea1().setText("");
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param msg java.lang.String
 */
public void showMessage(String msg) {
	getTextArea1().append(msg + "\n");
}
/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void windowActivated(WindowEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void windowClosed(WindowEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void windowClosing(WindowEvent e) {
	// user code begin {1}
	// user code end
	if ((e.getSource() == this) ) {
		connEtoC1(e);
	}
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void windowDeactivated(WindowEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void windowDeiconified(WindowEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void windowIconified(WindowEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
public void windowOpened(WindowEvent e) {
	// user code begin {1}
	// user code end
	// user code begin {2}
	// user code end
}
}
