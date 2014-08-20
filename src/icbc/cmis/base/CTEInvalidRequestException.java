package icbc.cmis.base;

/**
 * Insert the type's description here.
 * Creation date: (2001-12-26 9:10:09)
 * @author: Administrator
 */
public class CTEInvalidRequestException extends CTEException {
/**
 * EchannelsInvalidRequestException constructor comment.
 */
public CTEInvalidRequestException() {
	super();
}
/**
 * EchannelsInvalidRequestException constructor comment.
 * @param s java.lang.String
 */
public CTEInvalidRequestException(String s) {
	super(s);
}
/**
 * EchannelsInvalidRequestException constructor comment.
 * @param title java.lang.String
 * @param errLocal java.lang.String
 * @param errMsgDesc1 java.lang.String
 */
public CTEInvalidRequestException(String title, String errLocal, String errMsgDesc1) {
	super(title, errLocal, errMsgDesc1);
}
}
