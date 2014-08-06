/**
 * Sms_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.mk.framework.note;

public interface Sms_Service extends javax.xml.rpc.Service {
    public java.lang.String getSmsHttpPortAddress();

    public SmsPortType getSmsHttpPort() throws javax.xml.rpc.ServiceException;

    public SmsPortType getSmsHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
