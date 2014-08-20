package icbc.cmis.util;

/**
 * @author xgl
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class DsrDataElementType implements Cloneable {
	private String dataName = null;
	private String dataValue = null;
	private String defaultValue = null;
	private int IOType = -1;
	private String format = null;
	private String mapField = null;

	public void setDataName(String id) {
		dataName = id;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Cone class["+this.getClass().toString()+"Failed,Exception:" + e.toString());
			return null;
		}

	}
	
	public void setDataValue(String value) {
		dataValue = value;
	}
	public void setDefaultValue(String value) {
		defaultValue = value;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public String format(String src) {
		return src;
	}

	public String unformat(String src) {
		return src;
	}
	public String getDataValue() {
		if(dataValue != null){
			return dataValue;
		}else{
			return getDefaultValue();
		}
	}
	public String getDataName() {
		return dataName;
	}
	public void setIOType(int type){
		IOType = type;
	}
	public int getIOType(){
		return IOType;
	}
	public void setFormat(String fmt)throws Exception{
		format = fmt;
	}
	public String getFormat(){
		return format;
	}

	/**
	 * Returns the mapField.
	 * @return String
	 */
	public String getMapField() {
		return mapField;
	}

	/**
	 * Sets the mapField.
	 * @param mapField The mapField to set
	 */
	public void setMapField(String mapField) {
		this.mapField = mapField;
	}

}
