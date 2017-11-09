package main.exception;

/**
 * RuntimeException thrown by the classes in the project
 * 
 * @author marumjr
 */
public class GameRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 7946987100262758755L;

	protected String message;
	
	/**
	 * @param message
	 * 		Message carried out by the Exception
	 */
	public GameRuntimeException(String message) {
		super();
		this.message = message;
	}

	/**
	 * Format the message with the arguments given
	 * 
	 * @param message
	 * 		Message carried out by the Exception
	 * @param args
	 * 		Arguments to be set in the message
	 */
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
