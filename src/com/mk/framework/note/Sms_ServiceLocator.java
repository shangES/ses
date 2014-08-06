/**
 * Sms_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.mk.framework.note;

public class Sms_ServiceLocator extends org.apache.axis.client.Service implements Sms_Service {

    public Sms_ServiceLocator() {
    }


    public Sms_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Sms_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SmsHttpPort
    private java.lang.String SmsHttpPort_address = "http://112.65.228.88:8888/sms_hb/services/Sms/";

    public java.lang.String getSmsHttpPortAddress() {
        return SmsHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SmsHttpPortWSDDServiceName = "SmsHttpPort";

    public java.lang.String getSmsHttpPortWSDDServiceName() {
        return SmsHttpPortWSDDServiceName;
    }

    public void setSmsHttpPortWSDDServiceName(java.lang.String name) {
        SmsHttpPortWSDDServiceName = name;
    }

    public SmsPortType getSmsHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SmsHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSmsHttpPort(endpoint);
    }

    public SmsPortType getSmsHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SmsHttpBindingStub _stub = new SmsHttpBindingStub(portAddress, this);
            _stub.setPortName(getSmsHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSmsHttpPortEndpointAddress(java.lang.String address) {
        SmsHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (SmsPortType.class.isAssignableFrom(serviceEndpointInterface)) {
               SmsHttpBindingStub _stub = new SmsHttpBindingStub(new java.net.URL(SmsHttpPort_address), this);
                _stub.setPortName(getSmsHttpPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SmsHttpPort".equals(inputPortName)) {
            return getSmsHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.flaginfo.com.cn", "Sms");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.flaginfo.com.cn", "SmsHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SmsHttpPort".equals(portName)) {
            setSmsHttpPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
