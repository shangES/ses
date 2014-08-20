package icbc.cmis.mgr;

/**
 * Insert the type's description here.
 * Creation date: (2001-4-1 23:53:26)
 * @author: Administrator
 */
public class MonitorServer{

	private static INBSMonitor monitor = null;
/**
 * Insert the method's description here.
 * Creation date: (2002-4-22 16:32:25)
 * @param checkInterva1l int
 * @param listenPort1 int
 */
public MonitorServer(int checkInterva1l, int listenPort1) {
	try{
		if(this.monitor == null){
			INBSMonitor monitor = new INBSMonitor(checkInterva1l, listenPort1);
			this.monitor = monitor;
		}
	}catch(Exception e){
		System.out.println("start MonitorServer failure,Exception:\n"+e.getMessage());
	}
}
}
