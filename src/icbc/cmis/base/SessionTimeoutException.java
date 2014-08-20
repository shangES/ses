package icbc.cmis.base;

/**
 * Insert the type's description here.
 * Creation date: (2002-6-26 21:49:05)
 * @author: Administrator
 */
public class SessionTimeoutException extends TranFailException {
/**
 * SessionTimeoutException constructor comment.
 * @param errCode java.lang.String
 * @param classMethodName java.lang.String
 * @param errorMessage java.lang.String
 */
public SessionTimeoutException(String errCode, String classMethodName, String errorMessage) {
	super(errCode, classMethodName, errorMessage);
}
/**
 * SessionTimeoutException constructor comment.
 * @param errorCode java.lang.String
 * @param classMethodName java.lang.String
 * @param errorMessage java.lang.String
 * @param errorDispMsg java.lang.String
 */
public SessionTimeoutException(String errorCode, String classMethodName, String errorMessage, String errorDispMsg) {
	super(errorCode, classMethodName, errorMessage, errorDispMsg);
}
}
