package main.exception;

public class InvalidMoveException extends GameException {

	private static final long serialVersionUID = -4966697564662102129L;

	public InvalidMoveException(String message) {
		super(message);
	}
	
	public InvalidMoveException(String message, Object... args) {
		super(message, args);
	}

}
