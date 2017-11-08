package main;

import static main.enums.Player.CPU;
import static main.enums.Player.PLAYER_A;
import static main.enums.Player.PLAYER_B;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import main.enums.GameStatus;
import main.enums.Player;
import main.exception.GameException;

public class GameBoard {

	private static final char BLANK = '#';

	// Variables set during the configuration stage
	private int size;
	private Map<Player, Character> playersChars = new HashMap<Player, Character>();

	private char[][] field;
	private int availableMoves;

	public GameBoard(int size, char playerA, char playerB, char cpu) throws GameException {
		super();

		this.size = size;
		this.playersChars.put(PLAYER_A, playerA);
		this.playersChars.put(PLAYER_B, playerB);
		this.playersChars.put(CPU, cpu);
		assertParameters();

		initializeField();
		this.availableMoves = size * size;
	}

	public void registerPlay(int i, int j) throws GameException {
		if (i >= size || j >= size) {
			throw new GameException("Invalid move: [%d %d] is out of the board's range", i, j);
		} else if (this.field[i][j] != BLANK) {
			throw new GameException("Invalid move: [%d %d] was already chosen before", i, j);
		}

		this.field[i][j] = playersChars.get(Player.getCurrentPlayer());
		this.availableMoves--;
	}
	
	public void registerCPUPlay() throws GameException {
		// 10 tries to make a move randomly
		Random rn = new Random();
		for (int x=0; x<10; x++) {
			int i = rn.nextInt(size);
			int j = rn.nextInt(size);
			
			if (this.field[i][j] == BLANK) {
				registerPlay(i, j);
				validateEndGame(i, j);
				return;
			}
		}
		
		// If it couldn't make a random move, then we choose the first available blank space
		for (int i=0; i<this.size; i++) {
			for (int j=0; j<this.size; j++) {
				if (this.field[i][j] == BLANK) {
					registerPlay(i, j);
					validateEndGame(i, j);
					return;
				}
			}
		}
	}

	public void renderGameBoard() {
		// Header: printing the columns
		System.out.print("  ");
		for (int j = 0; j < size; j++) {
			System.out.print(" " + j);
		}
		System.out.println("");

		// The field itself
		for (int i = 0; i < size; i++) {
			// Printing the rows
			System.out.print(" " + i);

			for (int j = 0; j < size; j++) {
				System.out.print(" " + this.field[i][j]);
			}

			System.out.println("");
		}
	}

	public GameStatus validateEndGame(int i, int j) {
		if (availableMoves == 0) {
			return GameStatus.declareDraw();
		}
		
		Player currentPlayer = Player.getCurrentPlayer();
		
		// Validate the column
		char[] sequence = new char[this.size];
		for (int x = 0; x < this.size; x++) {
			sequence[x] = this.field[x][j];
		}
		if (areAllTheSameCharacters(sequence)) {
			return GameStatus.declareWinner(currentPlayer);
		}

		// Validate the row
		for (int x = 0; x < this.size; x++) {
			sequence[x] = this.field[i][x];
		}
		if (areAllTheSameCharacters(sequence)) {
			return GameStatus.declareWinner(currentPlayer);
		}

		// Validate diagonal
		if (i == j) {
			for (int x = 0; x < this.size; x++) {
				sequence[x] = this.field[x][x];
			}
			if (areAllTheSameCharacters(sequence)) {
				return GameStatus.declareWinner(currentPlayer);
			}
		}

		return GameStatus.NOT_ENDED;
	}

	private boolean areAllTheSameCharacters(char[] chars) {
		char c = chars[0];
		for (int i = 1; i < chars.length; i++) {
			if (c != chars[i]) {
				return false;
			}
			c = chars[i];
		}

		return true;
	}

	private void initializeField() {
		this.field = new char[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.field[i][j] = BLANK;
			}
		}
	}

	private void assertParameters() throws GameException {
		if (size < 3 || size > 10) {
			throw new GameException("Field size is out of bounds: " + size + " This value should be between 3 and 10.");
		}

		if (playersChars.get(PLAYER_A) == playersChars.get(PLAYER_B)) {
			throw new GameException("Duplicate use of characters for player A and B: %c", playersChars.get(PLAYER_A));
		} else if (playersChars.get(PLAYER_A) == playersChars.get(CPU)) {
			throw new GameException("Duplicate use of characters for player A and CPU: %c", playersChars.get(PLAYER_A));
		} else if (playersChars.get(PLAYER_B) == playersChars.get(CPU)) {
			throw new GameException("Duplicate use of characters for player B and CPU: %c", playersChars.get(PLAYER_B));
		}
	}

}
