package main.enums;

import main.exception.GameRuntimeException;

/**
 * This Enum encapsulates everything related to the Game Status - which stage of
 * the game we are on (still playing or finished)
 * 
 * @author marumjr
 */
public enum GameStatus {

	/** Status that represents the game is still going on */
	NOT_ENDED(false),

	/** Status that represents that the game ended in a draw */
	DRAW(true),

	/** Status that represents that Player A has won */
	PLAYER_A_WINS(true),

	/** Status that represents that Player B has won */
	PLAYER_B_WINS(true),

	/** Status that represents the triumph of the machine */
	CPU_WINS(true);

	// Registers if this Status triggers the end of the game
	private boolean endGameFlag;

	// Singleton instance that keeps track of the game status
	private static GameStatus gameStatus;

	private GameStatus(boolean endGameFlag) {
		this.endGameFlag = endGameFlag;
	}

	/**
	 * Has the game come to an end?
	 * 
	 * @return <code>true</code> if the game has reached its end,
	 *         <code>false</code> otherwise
	 */
	public static boolean hasGameEnded() {
		return gameStatus.endGameFlag;
	}

	/**
	 * Initializes the game status
	 * 
	 * @return the current game status
	 */
	public static GameStatus initialize() {
		// The initial game status should be NOT_ENDED
		gameStatus = GameStatus.NOT_ENDED;
		return gameStatus;
	}

	/**
	 * Returns the singleton instance of game status
	 * 
	 * @return the current game status
	 */
	public static GameStatus getGameStatus() {
		return gameStatus;
	}

	/**
	 * Declare the match as a draw
	 * 
	 * @return The current game status
	 */
	public static GameStatus declareDraw() {
		gameStatus = DRAW;
		return DRAW;
	}

	/**
	 * Declare a player as the winner
	 * 
	 * @param player
	 *            The player that will be designated as the winner of the match
	 * @return The current game status
	 */
	public static GameStatus declareWinner(Player player) {
		if (Player.PLAYER_A.equals(player)) {
			gameStatus = PLAYER_A_WINS;
			return PLAYER_A_WINS;
		} else if (Player.PLAYER_B.equals(player)) {
			gameStatus = PLAYER_B_WINS;
			return PLAYER_B_WINS;
		} else if (Player.CPU.equals(player)) {
			gameStatus = CPU_WINS;
			return CPU_WINS;
		}

		throw new GameRuntimeException("Unexpected error: declareWinner() called with an improper argument");
	}

}
