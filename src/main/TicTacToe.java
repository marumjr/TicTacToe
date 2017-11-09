package main;

import static main.enums.GameStatus.CPU_WINS;
import static main.enums.GameStatus.DRAW;
import static main.enums.GameStatus.PLAYER_A_WINS;
import static main.enums.GameStatus.PLAYER_B_WINS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import main.enums.GameStatus;
import main.enums.Player;
import main.exception.GameException;
import main.exception.GameRuntimeException;

/**
 * The main class, responsible for running the game
 * 
 * @author marumjr
 */
public class TicTacToe {

	private static final String BAR = "====================================================";

	public static void main(String[] args) {
		try {
			File file = new File(args[0]);
			Scanner scanFile = new Scanner(file);
			int size = scanFile.nextInt();
			char p1 = scanFile.next().charAt(0);
			char p2 = scanFile.next().charAt(0);
			char cpu = scanFile.next().charAt(0);
			scanFile.close();

			TicTacToe controller = new TicTacToe();
			Scanner in = new Scanner(System.in);
			
			GameStatus.initialize();
			GameBoard field = new GameBoard(size, p1, p2, cpu);
			while (!GameStatus.hasGameEnded()) {
				controller.run(in, field);
			}
			field.renderGameBoard();
			controller.printEndGame();

			in.close();
			
		} catch (FileNotFoundException e) {
			throw new GameRuntimeException("File not found");
		}
	}

	/**
	 * Routine called during the game. Responsible for rendering the board,
	 * keeping track of whose turn it is, request the player for his/her next
	 * play, validate if said play is valid and finally validate if the game has
	 * come to an end
	 * 
	 * @param in
	 *            Input reader to read the data submitted by the player
	 * @param board
	 *            The game board
	 */
	public void run(Scanner in, GameBoard board) {
		board.renderGameBoard();

		Player currentPlayer = Player.getCurrentPlayer();
		System.out.println(currentPlayer.getName() + "'s turn"); // Prints whose turn it is

		if (currentPlayer.isHumanPlayer()) {
			// Human players should choose their next move...
			System.out.println("What is your next play (enter 'i j' -> i: row; j: column)?");
			int i = in.nextInt();
			int j = in.nextInt();

			try {
				board.registerPlay(i, j);
				board.validateEndGame(i, j);
				Player.nextPlayer();
			} catch (GameException e) {
				System.out.println(e.getMessage());
			}

		} else {
			// ... while the CPU make a scripted move
			try {
				board.registerCPUPlay();
				Player.nextPlayer();
			} catch (GameException e) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println(BAR);
	}

	/**
	 * Prints the end game results
	 */
	public void printEndGame() {
		System.out.println(BAR);

		if (DRAW.equals(GameStatus.getGameStatus())) {
			System.out.println("No more legal moves. The game has ended in a draw.");
		} else if (CPU_WINS.equals(GameStatus.getGameStatus())) {
			System.out.println("The game has ended. The CPU wins.");
		} else if (PLAYER_A_WINS.equals(GameStatus.getGameStatus())) {
			System.out.println("The game has ended. Player A wins!!!");
		} else if (PLAYER_B_WINS.equals(GameStatus.getGameStatus())) {
			System.out.println("The game has ended. Player B wins!!!");
		} else {
			throw new GameRuntimeException("Unexpected error: printEndGame() called and the game has not ended yet.");
		}

		System.out.println(BAR);
		System.out.println(BAR);
	}

}
