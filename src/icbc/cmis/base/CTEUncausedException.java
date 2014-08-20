package icbc.cmis.base;

/**
 * Insert the type's description here.
 * Creation date: (2001-12-26 9:46:27)
 * @author: Administrator
 */
public class CTEUncausedException extends CTEException {
/**
 * EchannelsUncausedException constructor comment.
 */
public CTEUncausedException() {
	super();
}
/**
 * EchannelsUncausedException constructor comment.
 * @param s java.lang.String
 */
public CTEUncausedException(String s) {
	super(s);
}
/**
 * EchannelsUncausedException constructor comment.
 * @param title java.lang.String
 * @param errLocal java.lang.String
 * @param errMsgDesc1 java.lang.String
 */
public CTEUncausedException(String title, String errLocal, String errMsgDesc1) {
	super(title, errLocal, errMsgDesc1);
}
}
