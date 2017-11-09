package main.enums;

/**
 * Enum that encapsulates everything related to the players and turn order
 * 
 * @author marumjr
 */
public enum Player {

	PLAYER_A("Player A", true),

	PLAYER_B("Player B", true),

	CPU("CPU", false);

	// Singleton instance that stores the current player, whose turn it is
	private static Player currentPlayer;

	// Printable name of the enum element
	private String name;

	// Flag that stores whether a player is controlled by a human or not
	private boolean humanPlayer;

	private Player(String name, boolean humanPlayer) {
		this.name = name;
		this.humanPlayer = humanPlayer;
	}

	public String getName() {
		return name;
	}

	public boolean isHumanPlayer() {
		return humanPlayer;
	}

	/**
	 * Move to the next player's turn, in the following order:
	 * <ul>
	 * <li>Player A</li>
	 * <li>Player B</li>
	 * <li>CPU</li>
	 * <ul>
	 * 
	 * @return the new current player
	 */
	public static Player nextPlayer() {
		if (currentPlayer == null || currentPlayer == CPU) {
			currentPlayer = Player.PLAYER_A;
		} else if (currentPlayer == PLAYER_A) {
			currentPlayer = Player.PLAYER_B;
		} else { // currentPlayer == PLAYER_B
			currentPlayer = Player.CPU;
		}

		return currentPlayer;
	}

	/**
	 * Retrieves the current player, whose turn is going on
	 * 
	 * @return the current player
	 */
	public static Player getCurrentPlayer() {
		if (currentPlayer == null) {
			currentPlayer = Player.PLAYER_A;
		}

		return currentPlayer;
	}

}
