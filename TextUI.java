package ui;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import mazegame.MazeGame;
import mazegame.MazeConstants;

/**
 * 
 * @author nainasingh This class allows play of game and implents UI interfrace
 */
@SuppressWarnings("serial")
public class TextUI extends JFrame implements UI {

	private MazeGame game;

	public TextUI(MazeGame game) {
		this.game = game;
	}

	public MazeGame getGame() {
		return game;
	}

	public void setGame(MazeGame game) {
		this.game = game;
	}

	/**
	 * Launches game to allow play. Takes the keyboard input and turns it into
	 * commands for the movement of the monkey.
	 */

	@Override
	public void launchGame() {
		// TODO Auto-generated method stub
		// Take Scanner info to generate grid

		Scanner sc = new Scanner(new InputStreamReader(System.in));
		String input = "";
		char move;
		boolean exit = false;

		while (!exit) {
			do {
				System.out.print("Your Move");
				input = sc.nextLine();
			} while (input.length() != 1);
			move = input.charAt(0);
			if (move == MazeConstants.P1_UP) {
				game.move(MazeConstants.P1_UP);
			} else if (move == MazeConstants.P1_LEFT) {
				game.move(move);
			} else if (move == MazeConstants.P1_DOWN) {
				game.move(move);
			} else if (move == MazeConstants.P1_RIGHT) {
				game.move(move);
			} else if (move == MazeConstants.P2_UP) {
				game.move(move);
			} else if (move == MazeConstants.P2_LEFT) {
				game.move(move);
			} else if (move == MazeConstants.P2_DOWN) {
				game.move(move);
			} else if (move == MazeConstants.P2_RIGHT) {
				game.move(move);
			}

			System.out.println(game.getMaze().toString());

			if (game.isBlocked() || game.hasWon() != 0) {
				exit = true;
			}

		}
	}

	/**
	 * Returns the name of the winner if there is one.
	 */

	@Override
	public void displayWinner() {
		// TODO Auto-generated method stub
		int winner = game.hasWon();
		String message;
		if (game.isBlocked()) {
			message = "Player 1 and Player 2 are stuck." + " Game over!!!";
		}

		else {
			if (winner == 0) { // Game is still on
			} else if (winner == 1) {
				message = "Player 1 Wins!!!";
				System.out.println(message);
			} else if (winner == 2) {
				message = "Player 2 Wins!!!";
				System.out.println(message);
			} else if (winner == 3) {
				message = "It's a tie!!!";
				System.out.println(message);
			}
		}
	}

}
