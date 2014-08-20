package icbc.cmis.util;

/**
 * @author xgl
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DSRPackMsg {

	/**
	 * Constructor for DSRPackMsg.
	 */
	byte[] bMsg = null;
	int  len = 0;
	public DSRPackMsg(int msgLen,byte[] bm) {
		super();
		bMsg = bm;
		len = msgLen;
	}
	

	/**
	 * Returns the bMsg.
	 * @return byte[]
	 */
	public byte[] getBMsg() {
		return bMsg;
	}

	/**
	 * Returns the len.
	 * @return int
	 */
	public int getLen() {
		return len;
	}

	/**
	 * Sets the bMsg.
	 * @param bMsg The bMsg to set
	 */
	public void setBMsg(byte[] bMsg) {
		this.bMsg = bMsg;
	}

	/**
	 * Sets the len.
	 * @param len The len to set
	 */
	public void setLen(int len) {
		this.len = len;
	}

}
