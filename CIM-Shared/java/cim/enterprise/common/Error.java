package cim.enterprise.common;

public class Error {

	private String error;
	
	private String errorMessage;
	
	public Error()
	{
	}
	
	public Error(String error, String errorMessage)
	{
		this.error = error;
		this.errorMessage = errorMessage;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
