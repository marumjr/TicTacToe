package main.exception;

public class GameException extends Exception {

	private static final long serialVersionUID = -766642687213075448L;

	protected String message;
	
	public GameException(String message) {
		super();
		this.message = message;
	}
	
	public GameException(String message, Object... args) {
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
