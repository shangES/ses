package icbc.cmis.mgr;

/**
 * This type was created in VisualAge.
 */
public interface ServerProcessor {
/**
 * This method was created in VisualAge.
 */
void closeSocket(int sessinId, java.net.Socket socket );
/**
 * This method was created in VisualAge.
 */
void newPackageReceived(int sessionId, String pkg, java.io.DataOutputStream outputStream);
/**
 * This method was created in VisualAge.
 */
void showMessage(String msg);
}
