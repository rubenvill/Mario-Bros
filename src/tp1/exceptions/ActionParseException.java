package tp1.exceptions;

public class ActionParseException extends GameParseException{

	public ActionParseException() { 
		super(); 
	}
	
	public ActionParseException(String message){ 
		super(message); 
	}
	
	public ActionParseException(String message, Throwable cause){
		super(message, cause);
	}
	
	public ActionParseException(Throwable cause){ 
		super(cause); 
	}
	
	public ActionParseException(String message, Throwable cause, boolean enableSuppression, boolean writeableStackTrace){
		super(message, cause, enableSuppression, writeableStackTrace);
	}
}
