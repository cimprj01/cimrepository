package cim.enterprise.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Transient;

import cim.enterprise.data.CimObject;

public class CimTxnResult {
	
	@Transient
	private String txnId;
	
	@Transient
	private String txnDescription;
	
	@Transient
	private int errorCode;
	
	@Transient
	private String errorMessage;
	
	@Transient
	private String notification;
	
	@Transient
	private Object cimObject;
	

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}
	
	public void setCimObject(Object cimObject) {
		this.cimObject = cimObject;
	}	

	public Object getCimObject() {
		return cimObject;
	}


	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getTxnDescription() {
		return txnDescription;
	}

	public void setTxnDescription(String txnDescription) {
		this.txnDescription = txnDescription;
	}
	
	
	public void setTxnResult(int errorCode, String errorMessage, String notification)
	{
		 setErrorCode(errorCode);
		 setErrorMessage(errorMessage);
		 setNotification(notification);
	}	

}
