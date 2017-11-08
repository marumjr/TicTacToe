package main.enums;

import main.exception.GameRuntimeException;

public enum GameStatus {
	
	NOT_ENDED(false),

	DRAW(true),
	
	PLAYER_A_WINS(true),
	
	PLAYER_B_WINS(true),
	
	CPU_WINS(true);
	
	private static final String BAR = "====================================================";
	
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
	
	public static void printEndGame() {
		System.out.println(BAR);
		if (DRAW.equals(gameStatus)) {
			System.out.println("No more legal moves. The game has ended in a draw.");
		} else if (CPU_WINS.equals(gameStatus)) {
			System.out.println("The game has ended. The CPU wins.");
		} else if (PLAYER_A_WINS.equals(gameStatus)) {
			System.out.println("The game has ended. The Player A wins!!!");
		} else if (PLAYER_B_WINS.equals(gameStatus)) {
			System.out.println("The game has ended. The Player B wins!!!");
		} else {
			throw new GameRuntimeException("Unexpected error: printEndGame() called and the game has not ended yet.");
		}
		System.out.println(BAR);
		System.out.println(BAR);
	}
	
	public static GameStatus getGameStatus() {
		return gameStatus;
	}
	
	public static GameStatus declareDraw() {
		gameStatus = DRAW;
		return DRAW;
	}
	
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
