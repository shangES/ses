package icbc.cmis.base;

/**
 * Insert the type's description here.
 * Creation date: (2001-12-26 11:08:36)
 * @author: Administrator
 */
public class CTEObjectExistingException extends CTEException {
/**
 * CTEObjectexistingException constructor comment.
 */
public CTEObjectExistingException() {
	super();
}
/**
 * CTEObjectexistingException constructor comment.
 * @param s java.lang.String
 */
public CTEObjectExistingException(String s) {
	super(s);
}
/**
 * CTEObjectexistingException constructor comment.
 * @param title java.lang.String
 * @param errLocal java.lang.String
 * @param errMsgDesc1 java.lang.String
 */
public CTEObjectExistingException(String title, String errLocal, String errMsgDesc1) {
	super(title, errLocal, errMsgDesc1);
}
}
