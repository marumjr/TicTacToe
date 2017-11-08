package main.exception;

public class GameRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 7946987100262758755L;

	protected String message;
	
	public GameRuntimeException(String message) {
		super();
		this.message = message;
	}
	
	public GameRuntimeException(String message, Object... args) {
		super();
		this.message = String.format(message, args);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
