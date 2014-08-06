/**
 * SmsPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package com.mk.framework.note;

import com.mk.framework.note.holders.ReplyArrayHolder;

public interface SmsPortType extends java.rmi.Remote {
    public java.lang.String sms(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4, java.lang.String in5, java.lang.String in6, java.lang.String in7, java.lang.String in8, java.lang.String in9, java.lang.String in10) throws java.rmi.RemoteException;
    public java.lang.String report(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException;
    public void reply(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, javax.xml.rpc.holders.StringHolder result, javax.xml.rpc.holders.StringHolder confirm_time, javax.xml.rpc.holders.StringHolder id, ReplyArrayHolder replys) throws java.rmi.RemoteException;
    public java.lang.String replyConfirm(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4) throws java.rmi.RemoteException;
    public void searchSmsNum(java.lang.String in0, java.lang.String in1, java.lang.String in2, javax.xml.rpc.holders.StringHolder result, javax.xml.rpc.holders.StringHolder number) throws java.rmi.RemoteException;
    public java.lang.String auditing(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3) throws java.rmi.RemoteException;
}
