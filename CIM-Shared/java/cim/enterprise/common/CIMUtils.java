package cim.enterprise.common;

import cim.enterprise.service.CimTxnResult;

public class CIMUtils {

	 public static  CimTxnResult createTxnResult(int errorCode, String errorMessage, String notification)
	  {
		  CimTxnResult result = new CimTxnResult();
		  result.setErrorCode(errorCode);
		  result.setErrorMessage(errorMessage);
		  result.setNotification(notification);
		  
		  return result;
	  }
}
