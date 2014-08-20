package icbc.cmis.base;

/**
 * Insert the type's description here.
 * Creation date: (2002-1-11 8:44:45)
 * @author: Administrator
 */
public class SignOnException extends TranFailException {
/**
 * SignOnException constructor comment.
 * @param errorCode java.lang.String
 * @param classMethodName java.lang.String
 * @param errorMessage java.lang.String
 */
public SignOnException(String errorCode, String classMethodName, String errorMessage) {
	super(errorCode, classMethodName, errorMessage);
}
/**
 * SignOnException constructor comment.
 * @param errorCode java.lang.String
 * @param classMethodName java.lang.String
 * @param errorMessage java.lang.String
 * @param errorDispMsg java.lang.String
 */
public SignOnException(String errorCode, String classMethodName, String errorMessage, String errorDispMsg) {
	super(errorCode, classMethodName, errorMessage, errorDispMsg);
}
}
