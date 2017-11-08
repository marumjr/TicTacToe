package main;

import java.util.Scanner;

import main.enums.GameStatus;
import main.enums.Player;
import main.exception.GameException;

public class Controller {

	private static final String BAR = "====================================================";

	public static void main(String[] args) throws GameException {
		Scanner in = new Scanner(System.in);

		// System.out.println("Please, enter the name of the file with the
		// configurations:");
		// String filename = in.nextLine();

		System.out.println("What are the parameters n p1 p2 cpu?");
		int size = in.nextInt();
		char p1 = in.next().charAt(0);
		char p2 = in.next().charAt(0);
		char cpu = in.next().charAt(0);

		GameBoard field = new GameBoard(size, p1, p2, cpu);
		Controller controller = new Controller();

		GameStatus.initialize();
		while (!GameStatus.hasGameEnded()) {
			controller.run(in, field);
		}
		field.renderGameBoard();
		GameStatus.printEndGame();

		in.close();
	}

	public void run(Scanner in, GameBoard board) {
		board.renderGameBoard();

		Player currentPlayer = Player.getCurrentPlayer();
		System.out.println(currentPlayer.getName() + "'s turn");

		if (currentPlayer.isHumanPlayer()) {
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
			try {
				board.registerCPUPlay();
				Player.nextPlayer();
			} catch (GameException e) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println(BAR);
	}

}
