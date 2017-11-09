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

/**
 * Class that represents the Game Board itself, with its field and player's
 * marks
 * 
 * @author marumjr
 */
public class GameBoard {

	private static final char BLANK = '#';

	// Variables set during the configuration stage
	private int size;
	private Map<Player, Character> playersChars = new HashMap<Player, Character>();

	// Stores the grid displayed to the players
	private char[][] field;
	// Stores the number of available moves still on play
	private int availableMoves;

	public GameBoard(int size, char playerA, char playerB, char cpu) throws GameException {
		super();

		// First, set and check the parameters configured by the user
		this.size = size;
		this.playersChars.put(PLAYER_A, playerA);
		this.playersChars.put(PLAYER_B, playerB);
		this.playersChars.put(CPU, cpu);
		assertParameters();

		// Then, initializes the other variables
		this.field = new char[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.field[i][j] = BLANK;
			}
		}
		
		this.availableMoves = size * size;
	}


	/**
	 * Register a player's move, by marking it on the grid. But not without
	 * validating if the move is legal, i.e. it doesn't extrapolate the limits
	 * of the game board and neither is made over an already chosen spot on the
	 * board.
	 * 
	 * @param i
	 *            Row for the player's move
	 * @param j
	 *            Column for the player's move
	 * @throws GameException
	 *             If the player tries to make an invalid move, like marking
	 *             outside the board's range or claiming a spot already chosen
	 *             by someone
	 */
	public void registerPlay(int i, int j) throws GameException {
		if (i >= size || j >= size) {
			// Tried to play out of the board's range
			throw new GameException("Invalid move: [%d %d] is out of the board's range", i, j);
		} else if (this.field[i][j] != BLANK) {
			// Tried to play over someone's already claimed spot
			throw new GameException("Invalid move: [%d %d] was already chosen before", i, j);
		}

		this.field[i][j] = playersChars.get(Player.getCurrentPlayer());
		this.availableMoves--;
	}

	/**
	 * Register the CPU's move.
	 * <p>
	 * It tries 10 times to play at random over the game board - trying to make
	 * a valid move. If at the end it couldn't make said move, it just chooses
	 * the first available space in the grid (from left to right, top to bottom)
	 * 
	 * @throws GameException
	 *             If the player tries to make an invalid move, like marking
	 *             outside the board's range or claiming a spot already chosen
	 *             by someone
	 */
	public void registerCPUPlay() throws GameException {
		// 10 tries to make a move randomly
		Random rn = new Random();
		for (int x = 0; x < 10; x++) {
			int i = rn.nextInt(size);
			int j = rn.nextInt(size);

			if (this.field[i][j] == BLANK) {
				registerPlay(i, j);
				validateEndGame(i, j);
				return;
			}
		}

		// If it couldn't make a random move, then we choose the first available
		// blank space
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (this.field[i][j] == BLANK) {
					registerPlay(i, j);
					validateEndGame(i, j);
					return;
				}
			}
		}
	}

	/**
	 * Render the game board for the user
	 */
	public void renderGameBoard() {
		// Header: printing the column numbers
		System.out.print("  ");
		for (int j = 0; j < size; j++) {
			System.out.print(" " + j);
		}
		System.out.println("");

		// The field itself
		for (int i = 0; i < size; i++) {
			// Printing the row numbers
			System.out.print(" " + i);

			for (int j = 0; j < size; j++) {
				System.out.print(" " + this.field[i][j]);
			}

			System.out.println("");
		}
	}

	/**
	 * Check if the game has reached its end. One of two things can trigger the
	 * game ending:
	 * <ul>
	 * <li>One player could claim all spaces inside a single column, or row or
	 * diagonal;</li>
	 * <li>There are no more available spaces to play.</li>
	 * </ul>
	 * Otherwise, the game just keeps going.
	 * 
	 * @param i
	 *            The row in which the move was made
	 * @param j
	 *            The column in which the move was made
	 * @return the game status, as it may still be playable or it could've
	 *         reached an end
	 */
	public GameStatus validateEndGame(int i, int j) {
		if (availableMoves == 0) {
			// There are no more available moves => DRAW
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

	/**
	 * Check if all characters in an array of chars are the same
	 * @param chars
	 * 		Array of chars to be checked
	 * @return <code>true</code> when ALL of the characters are the same, <code>false</code> otherwise
	 */
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

	/**
	 * Check if the parameters are according to expected
	 * 
	 * @throws GameException
	 */
	private void assertParameters() throws GameException {
		if (size < 3 || size > 10) {
			throw new GameException("Field size is out of bounds: " + size + " This value should be between 3 and 10.");
		}

		// A player can't have the same mark (e.g. X or O) as another player
		if (playersChars.get(PLAYER_A) == playersChars.get(PLAYER_B)) {
			throw new GameException("Duplicate use of characters for player A and B: %c", playersChars.get(PLAYER_A));
		} else if (playersChars.get(PLAYER_A) == playersChars.get(CPU)) {
			throw new GameException("Duplicate use of characters for player A and CPU: %c", playersChars.get(PLAYER_A));
		} else if (playersChars.get(PLAYER_B) == playersChars.get(CPU)) {
			throw new GameException("Duplicate use of characters for player B and CPU: %c", playersChars.get(PLAYER_B));
		}
	}

}
