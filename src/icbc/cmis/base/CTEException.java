package icbc.cmis.base;

/**
 * Insert the type's description here.
 * Creation date: (2001-12-26 8:59:22)
 * @author: Administrator
 */
public abstract class CTEException extends Exception {
	private String errMsgDesc = null;
	private String errLocation = null;
	
/**
 * EchannelsException constructor comment.
 */
public CTEException() {
	super();
}
/**
 * EchannelsException constructor comment.
 * @param s java.lang.String
 */
public CTEException(String s) {
	super(s);
}
/**
 * EchannelsException constructor comment.
 * @param s java.lang.String
 */
public CTEException(String title,String errLocal,String errMsgDesc1) {
	super(title);
	errLocation = errLocal;
	errMsgDesc = errMsgDesc1;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-26 9:04:01)
 */
public String getErrorLocation() {
	if(errLocation != null)
		return errLocation;
		else
			return "";
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-26 9:04:01)
 */
public String getErrorMsgDesc() {
	if(errMsgDesc != null)
		return errMsgDesc;
		else
			return "";
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-26 9:04:01)
 */
public void setErrorLocation(String errLocation1) {
	errLocation = errLocation1;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-26 9:04:01)
 */
public void setErrorMsgDesc(String errMsgDesc1) {
	errMsgDesc = errMsgDesc1;
}
}
