package main.enums;

public enum Player {
	
	PLAYER_A("Player A", true),
	
	PLAYER_B("Player B", true),
	
	CPU("CPU", false);
	
	private static Player currentPlayer;
	
	private String name;
	
	private boolean humanPlayer;
	
	private Player(String name, boolean humanPlayer) {
		this.name = name;
		this.humanPlayer = humanPlayer;
	}
	
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
	
	public static Player getCurrentPlayer() {
		if (currentPlayer == null) {
			currentPlayer = Player.PLAYER_A;
		}
		
		return currentPlayer;
	}

	public String getName() {
		return name;
	}
	
	public boolean isHumanPlayer() {
		return humanPlayer;
	}

}
