package main.enums;

import main.exception.GameRuntimeException;

public enum GameStatus {
	
	NOT_ENDED(false),

	DRAW(true),
	
	PLAYER_A_WINS(true),
	
	PLAYER_B_WINS(true),
	
	CPU_WINS(true);
	
	private boolean endGameFlag;
	
	private static GameStatus gameStatus;
	
	private GameStatus(boolean endGameFlag) {
		this.endGameFlag = endGameFlag;
	}
	
	public static boolean hasGameEnded() {
		return gameStatus.endGameFlag;
	}

	public static GameStatus initialize() {
		gameStatus = GameStatus.NOT_ENDED;
		return gameStatus;
	}
	
	public static GameStatus getGameStatus() {
		return gameStatus;
	}
	
	public static GameStatus declareDraw() {
		gameStatus = DRAW;
		return DRAW;
	}
	
	/**
	 * Declare a player as the winner
	 * 
	 * @param player
	 * 		The player that will be designated as the winner of the match
	 * @return
	 * 		The current game status
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
