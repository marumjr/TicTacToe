package main.exception;

/**
 * Exception thrown by the classes in the project
 * 
 * @author marumjr
 */
public class GameException extends Exception {

	private static final long serialVersionUID = -766642687213075448L;

	protected String message;
	
	/**
	 * @param message
	 * 		Message carried out by the Exception
	 */
	public GameException(String message) {
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
