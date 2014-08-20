package icbc.cmis.base;

/**
 * Insert the type's description here.
 * Creation date: (2001-12-26 9:10:52)
 * @author: Administrator
 */
public class CTEObjectNotFoundException extends CTEException {
/**
 * EchannelsObjectNotFoundException constructor comment.
 */
public CTEObjectNotFoundException() {
	super();
}
/**
 * EchannelsObjectNotFoundException constructor comment.
 * @param s java.lang.String
 */
public CTEObjectNotFoundException(String s) {
	super(s);
}
/**
 * EchannelsObjectNotFoundException constructor comment.
 * @param title java.lang.String
 * @param errLocal java.lang.String
 * @param errMsgDesc1 java.lang.String
 */
public CTEObjectNotFoundException(String title, String errLocal, String errMsgDesc1) {
	super(title, errLocal, errMsgDesc1);
}
}
