/**
 * Reply.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.mk.framework.note;

public class Reply  implements java.io.Serializable {
    private java.lang.String callMdn;

    private java.lang.String mdn;

    private java.lang.String content;

    private java.lang.String reply_time;

    public Reply() {
    }

    public Reply(
           java.lang.String callMdn,
           java.lang.String mdn,
           java.lang.String content,
           java.lang.String reply_time) {
           this.callMdn = callMdn;
           this.mdn = mdn;
           this.content = content;
           this.reply_time = reply_time;
    }


    /**
     * Gets the callMdn value for this Reply.
     * 
     * @return callMdn
     */
    public java.lang.String getCallMdn() {
        return callMdn;
    }


    /**
     * Sets the callMdn value for this Reply.
     * 
     * @param callMdn
     */
    public void setCallMdn(java.lang.String callMdn) {
        this.callMdn = callMdn;
    }


    /**
     * Gets the mdn value for this Reply.
     * 
     * @return mdn
     */
    public java.lang.String getMdn() {
        return mdn;
    }


    /**
     * Sets the mdn value for this Reply.
     * 
     * @param mdn
     */
    public void setMdn(java.lang.String mdn) {
        this.mdn = mdn;
    }


    /**
     * Gets the content value for this Reply.
     * 
     * @return content
     */
    public java.lang.String getContent() {
        return content;
    }


    /**
     * Sets the content value for this Reply.
     * 
     * @param content
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }


    /**
     * Gets the reply_time value for this Reply.
     * 
     * @return reply_time
     */
    public java.lang.String getReply_time() {
        return reply_time;
    }


    /**
     * Sets the reply_time value for this Reply.
     * 
     * @param reply_time
     */
    public void setReply_time(java.lang.String reply_time) {
        this.reply_time = reply_time;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reply)) return false;
        Reply other = (Reply) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.callMdn==null && other.getCallMdn()==null) || 
             (this.callMdn!=null &&
              this.callMdn.equals(other.getCallMdn()))) &&
            ((this.mdn==null && other.getMdn()==null) || 
             (this.mdn!=null &&
              this.mdn.equals(other.getMdn()))) &&
            ((this.content==null && other.getContent()==null) || 
             (this.content!=null &&
              this.content.equals(other.getContent()))) &&
            ((this.reply_time==null && other.getReply_time()==null) || 
             (this.reply_time!=null &&
              this.reply_time.equals(other.getReply_time())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCallMdn() != null) {
            _hashCode += getCallMdn().hashCode();
        }
        if (getMdn() != null) {
            _hashCode += getMdn().hashCode();
        }
        if (getContent() != null) {
            _hashCode += getContent().hashCode();
        }
        if (getReply_time() != null) {
            _hashCode += getReply_time().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reply.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.flaginfo.com.cn", "Reply"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("callMdn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.flaginfo.com.cn", "callMdn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mdn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.flaginfo.com.cn", "mdn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("content");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.flaginfo.com.cn", "content"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reply_time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.flaginfo.com.cn", "reply_time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
