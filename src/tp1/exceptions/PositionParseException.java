package tp1.exceptions;

public class PositionParseException extends GameParseException{
	public PositionParseException() { 
		super(); 
	}
	
	public PositionParseException(String message){ 
		super(message); 
	}
	
	public PositionParseException(String message, Throwable cause){
		super(message, cause);
	}
	
	public PositionParseException(Throwable cause){ 
		super(cause); 
	}
	
	public PositionParseException(String message, Throwable cause, boolean enableSuppression, boolean writeableStackTrace){
		super(message, cause, enableSuppression, writeableStackTrace);
	}
}
