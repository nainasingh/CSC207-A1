package mazegame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.Random;

/**
 * A class that represents the basic functionality of the maze game. This class
 * is responsible for performing the following operations: 1. At creation, it
 * initializes the instance variables used to store the current state of the
 * game. 2. When a move is specified, it checks if it is a legal move and makes
 * the move if it is legal. 3. It reports information about the current state of
 * the game when asked.
 */
/**
 * @author nainasingh
 *
 */
/**
 * @author nainasingh
 *
 */
public class MazeGame {

	/** A random number generator to move the MobileBananas. */
	private Random random;

	/** The maze grid. */
	private Grid<Sprite> maze;

	/** The first player. */
	private Monkey player1;

	/** The second player. */
	private Monkey player2;

	/** The banana to eat. */
	private List<Banana> bananas;

	/**
	 * Creates a new MazeGame that corresponds to the maze in the file named
	 * layoutFileName.
	 * 
	 * @param layoutFileName
	 *            the path to the input maze file
	 * @throws IOException
	 */
	public MazeGame(String layoutFileName) throws IOException {
		random = new Random();

		int[] dimensions = getDimensions(layoutFileName);
		maze = new ArrayGrid<Sprite>(dimensions[0], dimensions[1]);

		Scanner sc = new Scanner(new File(layoutFileName));

		/* INITIALIZE THE GRID HERE */

		/**
		 * Reads data from Scanner.Sets the Maze Constants in the correct row
		 * and column in the grid.
		 */

		String line;
		int row = 0;

		while ((line = sc.nextLine()) != null) {
			for (int col = 0; col < line.length(); col++) {
				if (line.charAt(row) == MazeConstants.WALL) {
					maze.setCell(row, col, new Wall(MazeConstants.WALL, row,
							col));
				} else if (line.charAt(col) == MazeConstants.BANANA) {
					maze.setCell(row, col, new Banana(MazeConstants.BANANA,
							row, col, MazeConstants.BANANA_SCORE));
					bananas.add(new Banana(MazeConstants.BANANA, row, col,
							MazeConstants.BANANA_SCORE));
				} else if (line.charAt(col) == MazeConstants.MOBILE_BANANA) {
					maze.setCell(row, col, new Banana(
							MazeConstants.MOBILE_BANANA, row, col,
							MazeConstants.MOBILE_BANANA_SCORE));
					bananas.add(new Banana(MazeConstants.MOBILE_BANANA, row,
							col, MazeConstants.MOBILE_BANANA_SCORE));
				} else if (line.charAt(col) == MazeConstants.P1) {
					maze.setCell(row, col, new Monkey(MazeConstants.P1, row,
							col));
				} else if (line.charAt(col) == MazeConstants.P2) {
					maze.setCell(row, col, new Monkey(MazeConstants.P2, row,
							col));
				} else if (line.charAt(col) == MazeConstants.VACANT) {
					maze.setCell(row, col, new UnvisitedHallway(
							MazeConstants.VACANT, row, col));
				} else if (line.charAt(col) == MazeConstants.VISITED) {
					maze.setCell(row, col, new VisitedHallway(
							MazeConstants.VISITED, row, col));
				}
			}
			row++;
		}
		sc.close();
	}

	/**
	 * 
	 * @return the Sprite maze
	 */

	public Grid<Sprite> getMaze() {
		return maze;
	}

	/**
	 * 
	 * @return Player 1 set as Monkey
	 */

	public Monkey getPlayerOne() {
		return player1;
	}

	/**
	 * 
	 * @return Player 2 set as Monkey
	 */

	public Monkey getPlayerTwo() {
		return player2;
	}

	/**
	 * 
	 * @return Number of rows in grid
	 */

	public int getNumRows() {
		return maze.getNumRows();
	}

	/**
	 * 
	 * @return Number of columns in grid
	 */

	public int getNumCols() {
		return maze.getNumCols();
	}

	/**
	 * Takes coordinates of object
	 * 
	 * @param row
	 *            Row in grid
	 * @param col
	 *            Column in grid
	 * @return Sprite
	 */

	public Sprite get(int row, int col) {
		return maze.getCell(row, col);
	}

	/**
	 * Creates the new coordinates based on the directions given by players
	 * 
	 * @param row
	 *            Row of object in grif
	 * @param col
	 *            Column of object in grid
	 * @param direction
	 *            The direction player wants to move
	 * @return Sprite
	 */

	private Sprite location(int row, int col, int direction) {
		Sprite next = null;
		if (direction == MazeConstants.LEFT) {
			next = maze.getCell((row + MazeConstants.LEFT), col);
		} else if (direction == MazeConstants.RIGHT) {
			next = maze.getCell((row + MazeConstants.RIGHT), col);
		} else if (direction == MazeConstants.UP) {
			next = maze.getCell(row, (col + MazeConstants.UP));
		} else if (direction == MazeConstants.DOWN) {
			next = maze.getCell(row, (col + MazeConstants.DOWN));
		}
		return next;
	}

	/**
	 * Moves the Monkey/Player if they can move
	 * 
	 * @param nextMove
	 *            Move the Player wished to make
	 */

	public void move(char nextMove) {
		Sprite nextObj = null;
		// If player wants to move Monkey down
		if (nextMove == MazeConstants.P1_DOWN) { // Calculates new coordinates
			nextObj = location(getPlayerOne().row, getPlayerOne().column,
					MazeConstants.DOWN);

			if (nextObj instanceof Wall) {// Doesn't move
				getPlayerOne().move(0, 0);
			} else if (nextObj instanceof VisitedHallway) {
				getPlayerOne().move(0, 0);// Doesn't move
			} else if (nextObj instanceof UnvisitedHallway) {
				maze.setCell(getPlayerOne().row, getPlayerOne().column,
						new VisitedHallway('.', getPlayerOne().row,
								getPlayerOne().column));
				// Moves player, sets old coordinates
				getPlayerOne().move(0, MazeConstants.DOWN);
				maze.setCell(getPlayerOne().getRow(), getPlayerOne()
						.getColumn(), player1);
				// Sets new coordinates
				moveMobileBanana(); // Moves Mobile Banana

			} else if (nextObj instanceof Banana) {
				maze.setCell(getPlayerOne().row, getPlayerOne().column,
						new VisitedHallway('.', getPlayerOne().row,
								getPlayerOne().column));
				getPlayerOne().move(0, MazeConstants.DOWN);
				getPlayerOne().eatBanana(MazeConstants.BANANA_SCORE);
				maze.setCell(getPlayerOne().getRow(), getPlayerOne()
						.getColumn(), player1);
				bananas.remove(0); // Remove the banana from maze
				moveMobileBanana();
			} else if (nextObj instanceof MobileBanana) {
				maze.setCell(getPlayerOne().row, getPlayerOne().column,
						new VisitedHallway('.', getPlayerOne().row,
								getPlayerOne().column));
				getPlayerOne().move(0, MazeConstants.DOWN);
				getPlayerOne().eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				maze.setCell(getPlayerOne().getRow(), getPlayerOne()
						.getColumn(), player1);
				bananas.remove(0);
				moveMobileBanana();
			} else if (nextObj instanceof Monkey) {
				getPlayerOne().move(0, 0);
			}
		} else if (nextMove == MazeConstants.P1_LEFT) {
			nextObj = location(getPlayerOne().row, getPlayerOne().column,
					MazeConstants.LEFT);
			if (nextObj instanceof Wall) {
				getPlayerOne().move(0, 0);
			} else if (nextObj instanceof VisitedHallway) {
				getPlayerOne().move(0, 0);
			} else if (nextObj instanceof UnvisitedHallway) {
				maze.setCell(getPlayerOne().row, getPlayerOne().column,
						new VisitedHallway('.', getPlayerOne().row,
								getPlayerOne().column));
				getPlayerOne().move(0, MazeConstants.LEFT);
				maze.setCell(getPlayerOne().getRow(), getPlayerOne()
						.getColumn(), player1);
				moveMobileBanana();
			} else if (nextObj instanceof Banana) {
				maze.setCell(getPlayerOne().row, getPlayerOne().column,
						new VisitedHallway('.', getPlayerOne().row,
								getPlayerOne().column));
				getPlayerOne().move(0, MazeConstants.LEFT);
				getPlayerOne().eatBanana(MazeConstants.BANANA_SCORE);
				maze.setCell(getPlayerOne().getRow(), getPlayerOne()
						.getColumn(), player1);
				bananas.remove(0);
				moveMobileBanana();
			} else if (nextObj instanceof MobileBanana) {
				maze.setCell(getPlayerOne().row, getPlayerOne().column,
						new VisitedHallway('.', getPlayerOne().row,
								getPlayerOne().column));
				getPlayerOne().move(0, MazeConstants.LEFT);
				getPlayerOne().eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				maze.setCell(getPlayerOne().getRow(), getPlayerOne()
						.getColumn(), player1);
				bananas.remove(0);
				moveMobileBanana();
			} else if (nextObj instanceof Monkey) {
				getPlayerOne().move(0, 0);
			}
		} else if (nextMove == MazeConstants.P1_RIGHT) {
			nextObj = location(getPlayerOne().row, getPlayerOne().column,
					MazeConstants.RIGHT);
			if (nextObj instanceof Wall) {
				getPlayerOne().move(0, 0);
			} else if (nextObj instanceof VisitedHallway) {
				getPlayerOne().move(0, 0);
			} else if (nextObj instanceof UnvisitedHallway) {
				maze.setCell(getPlayerOne().row, getPlayerOne().column,
						new VisitedHallway('.', getPlayerOne().row,
								getPlayerOne().column));
				getPlayerOne().move(0, MazeConstants.RIGHT);
				maze.setCell(getPlayerOne().getRow(), getPlayerOne()
						.getColumn(), player1);
				moveMobileBanana();
			} else if (nextObj instanceof Banana) {
				maze.setCell(getPlayerOne().row, getPlayerOne().column,
						new VisitedHallway('.', getPlayerOne().row,
								getPlayerOne().column));
				getPlayerOne().move(0, MazeConstants.RIGHT);
				getPlayerOne().eatBanana(MazeConstants.BANANA_SCORE);
				maze.setCell(getPlayerOne().getRow(), getPlayerOne()
						.getColumn(), player1);
				bananas.remove(0);
				moveMobileBanana();
			} else if (nextObj instanceof MobileBanana) {
				maze.setCell(getPlayerOne().row, getPlayerOne().column,
						new VisitedHallway('.', getPlayerOne().row,
								getPlayerOne().column));
				getPlayerOne().move(0, MazeConstants.RIGHT);
				getPlayerOne().eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				maze.setCell(getPlayerOne().getRow(), getPlayerOne()
						.getColumn(), player1);
				bananas.remove(0);
				moveMobileBanana();
			} else if (nextObj instanceof Monkey) {
				getPlayerOne().move(0, 0);
			}
		} else if (nextMove == MazeConstants.P1_UP) {
			nextObj = location(getPlayerOne().row, getPlayerOne().column,
					MazeConstants.UP);
			if (nextObj instanceof Wall) {
				getPlayerOne().move(0, 0);
			} else if (nextObj instanceof VisitedHallway) {
				getPlayerOne().move(0, 0);
			} else if (nextObj instanceof UnvisitedHallway) {
				maze.setCell(getPlayerOne().row, getPlayerOne().column,
						new VisitedHallway('.', getPlayerOne().row,
								getPlayerOne().column));
				getPlayerOne().move(0, MazeConstants.UP);
				maze.setCell(getPlayerOne().getRow(), getPlayerOne()
						.getColumn(), player1);
				moveMobileBanana();
			} else if (nextObj instanceof Banana) {
				maze.setCell(getPlayerOne().row, getPlayerOne().column,
						new VisitedHallway('.', getPlayerOne().row,
								getPlayerOne().column));
				getPlayerOne().move(0, MazeConstants.UP);
				getPlayerOne().eatBanana(MazeConstants.BANANA_SCORE);
				maze.setCell(getPlayerOne().getRow(), getPlayerOne()
						.getColumn(), player1);
				bananas.remove(0);
				moveMobileBanana();
			} else if (nextObj instanceof MobileBanana) {
				maze.setCell(getPlayerOne().row, getPlayerOne().column,
						new VisitedHallway('.', getPlayerOne().row,
								getPlayerOne().column));
				getPlayerOne().move(0, MazeConstants.UP);
				getPlayerOne().eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				maze.setCell(getPlayerOne().getRow(), getPlayerOne()
						.getColumn(), player1);
				bananas.remove(0);
				moveMobileBanana();
			} else if (nextObj instanceof Monkey) {
				getPlayerOne().move(0, 0);
			} // Moves Player 2 if directed to
		} else if (nextMove == MazeConstants.P2_DOWN) {
			nextObj = location(getPlayerTwo().row, getPlayerTwo().column,
					MazeConstants.DOWN);
			if (nextObj instanceof Wall) {
				getPlayerTwo().move(0, 0);
			} else if (nextObj instanceof VisitedHallway) {
				getPlayerTwo().move(0, 0);
			} else if (nextObj instanceof UnvisitedHallway) {
				maze.setCell(getPlayerTwo().row, getPlayerTwo().column,
						new VisitedHallway('.', getPlayerTwo().row,
								getPlayerTwo().column));
				getPlayerTwo().move(0, MazeConstants.DOWN);
				maze.setCell(getPlayerTwo().getRow(), getPlayerTwo()
						.getColumn(), player2);
				moveMobileBanana();
			} else if (nextObj instanceof Banana) {
				maze.setCell(getPlayerTwo().row, getPlayerTwo().column,
						new VisitedHallway('.', getPlayerTwo().row,
								getPlayerTwo().column));
				getPlayerTwo().move(0, MazeConstants.DOWN);
				getPlayerTwo().eatBanana(MazeConstants.BANANA_SCORE);
				maze.setCell(getPlayerTwo().getRow(), getPlayerTwo()
						.getColumn(), player2);
				bananas.remove(0);
				moveMobileBanana();
			} else if (nextObj instanceof MobileBanana) {
				maze.setCell(getPlayerTwo().row, getPlayerTwo().column,
						new VisitedHallway('.', getPlayerTwo().row,
								getPlayerTwo().column));
				getPlayerTwo().move(0, MazeConstants.DOWN);
				getPlayerTwo().eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				maze.setCell(getPlayerTwo().getRow(), getPlayerTwo()
						.getColumn(), player2);
				bananas.remove(0);
				moveMobileBanana();
			} else if (nextObj instanceof Monkey) {
				getPlayerTwo().move(0, 0);
			}
		} else if (nextMove == MazeConstants.P2_LEFT) {
			nextObj = location(getPlayerTwo().row, getPlayerTwo().column,
					MazeConstants.LEFT);
			if (nextObj instanceof Wall) {
				getPlayerTwo().move(0, 0);
			} else if (nextObj instanceof VisitedHallway) {
				getPlayerTwo().move(0, 0);
			} else if (nextObj instanceof UnvisitedHallway) {
				maze.setCell(getPlayerTwo().row, getPlayerTwo().column,
						new VisitedHallway('.', getPlayerTwo().row,
								getPlayerTwo().column));
				getPlayerTwo().move(0, MazeConstants.LEFT);
				maze.setCell(getPlayerTwo().getRow(), getPlayerTwo()
						.getColumn(), player2);
				moveMobileBanana();
			} else if (nextObj instanceof Banana) {
				maze.setCell(getPlayerTwo().row, getPlayerTwo().column,
						new VisitedHallway('.', getPlayerTwo().row,
								getPlayerTwo().column));
				getPlayerTwo().move(0, MazeConstants.LEFT);
				getPlayerTwo().eatBanana(MazeConstants.BANANA_SCORE);
				maze.setCell(getPlayerTwo().getRow(), getPlayerTwo()
						.getColumn(), player2);
				bananas.remove(0);
				moveMobileBanana();
			} else if (nextObj instanceof MobileBanana) {
				maze.setCell(getPlayerTwo().row, getPlayerTwo().column,
						new VisitedHallway('.', getPlayerTwo().row,
								getPlayerTwo().column));
				getPlayerTwo().move(0, MazeConstants.LEFT);
				getPlayerTwo().eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				maze.setCell(getPlayerTwo().getRow(), getPlayerTwo()
						.getColumn(), player2);
				bananas.remove(0);
				moveMobileBanana();
			} else if (nextObj instanceof Monkey) {
				getPlayerTwo().move(0, 0);
			}
		} else if (nextMove == MazeConstants.P2_RIGHT) {
			nextObj = location(getPlayerTwo().row, getPlayerTwo().column,
					MazeConstants.RIGHT);
			if (nextObj instanceof Wall) {
				getPlayerTwo().move(0, 0);
			} else if (nextObj instanceof VisitedHallway) {
				getPlayerTwo().move(0, 0);
			} else if (nextObj instanceof UnvisitedHallway) {
				maze.setCell(getPlayerTwo().row, getPlayerTwo().column,
						new VisitedHallway('.', getPlayerTwo().row,
								getPlayerTwo().column));
				getPlayerTwo().move(0, MazeConstants.RIGHT);
				maze.setCell(getPlayerTwo().getRow(), getPlayerTwo()
						.getColumn(), player2);
				moveMobileBanana();
			} else if (nextObj instanceof Banana) {
				maze.setCell(getPlayerTwo().row, getPlayerTwo().column,
						new VisitedHallway('.', getPlayerTwo().row,
								getPlayerTwo().column));
				getPlayerTwo().move(0, MazeConstants.RIGHT);
				getPlayerTwo().eatBanana(MazeConstants.BANANA_SCORE);
				maze.setCell(getPlayerTwo().getRow(), getPlayerTwo()
						.getColumn(), player2);
				bananas.remove(0);
				moveMobileBanana();
			} else if (nextObj instanceof MobileBanana) {
				maze.setCell(getPlayerTwo().row, getPlayerTwo().column,
						new VisitedHallway('.', getPlayerTwo().row,
								getPlayerTwo().column));
				getPlayerTwo().move(0, MazeConstants.RIGHT);
				getPlayerTwo().eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				maze.setCell(getPlayerTwo().getRow(), getPlayerTwo()
						.getColumn(), player2);
				bananas.remove(0);
				moveMobileBanana();
			} else if (nextObj instanceof Monkey) {
				getPlayerTwo().move(0, 0);
			}
		} else if (nextMove == MazeConstants.P2_UP) {
			nextObj = location(getPlayerTwo().row, getPlayerTwo().column,
					MazeConstants.UP);
			if (nextObj instanceof Wall) {
				getPlayerTwo().move(0, 0);
			} else if (nextObj instanceof VisitedHallway) {
				getPlayerTwo().move(0, 0);
			} else if (nextObj instanceof UnvisitedHallway) {
				maze.setCell(getPlayerTwo().row, getPlayerTwo().column,
						new VisitedHallway('.', getPlayerTwo().row,
								getPlayerTwo().column));
				getPlayerTwo().move(0, MazeConstants.UP);
				maze.setCell(getPlayerTwo().getRow(), getPlayerTwo()
						.getColumn(), player2);
				moveMobileBanana();
			} else if (nextObj instanceof Banana) {
				maze.setCell(getPlayerTwo().row, getPlayerTwo().column,
						new VisitedHallway('.', getPlayerTwo().row,
								getPlayerTwo().column));
				getPlayerTwo().move(0, MazeConstants.UP);
				getPlayerTwo().eatBanana(MazeConstants.BANANA_SCORE);
				maze.setCell(getPlayerTwo().getRow(), getPlayerTwo()
						.getColumn(), player2);
				bananas.remove(0);
				moveMobileBanana();

			} else if (nextObj instanceof MobileBanana) {
				maze.setCell(getPlayerTwo().row, getPlayerTwo().column,
						new VisitedHallway('.', getPlayerTwo().row,
								getPlayerTwo().column));
				getPlayerTwo().move(0, MazeConstants.UP);
				getPlayerTwo().eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				maze.setCell(getPlayerTwo().getRow(), getPlayerTwo()
						.getColumn(), player2);
				bananas.remove(0);
				moveMobileBanana();

			} else if (nextObj instanceof Monkey) {
				getPlayerTwo().move(0, 0);
				moveMobileBanana();
			}
		}
	}

	/**
	 * Moves MobileBanana every time player moves
	 */

	public void moveMobileBanana() {
		// Chooses a random direction
		int rand = random.nextInt(4);
		for (Banana b : bananas) {
			Sprite nextObj = null;
			// Makes sure its MobileBanana
			if (b instanceof MobileBanana) {
				int row = b.getRow();
				int col = b.getColumn();
				if (rand == 0) { // Moves MobileBanana down
					// Generates new coordinates
					nextObj = location(row, col, MazeConstants.DOWN);
					// Sets new coordinates
					maze.setCell(row, col, new UnvisitedHallway(
							MazeConstants.VACANT, row, col));
					// Sets old coordinates
					maze.setCell(row, col - MazeConstants.DOWN, b);
				} else if (rand == 1) {
					nextObj = location(row, col, MazeConstants.UP);
					maze.setCell(row, col, new UnvisitedHallway(
							MazeConstants.VACANT, row, col));
					maze.setCell(row, col - MazeConstants.UP, b);
				} else if (rand == 2) {
					nextObj = location(row, col, MazeConstants.LEFT);
					maze.setCell(row, col, new UnvisitedHallway(
							MazeConstants.VACANT, row, col));
					maze.setCell(row - MazeConstants.LEFT, col, b);
				} else {
					nextObj = location(row, col, MazeConstants.RIGHT);
					maze.setCell(row, col, new UnvisitedHallway(
							MazeConstants.VACANT, row, col));
					maze.setCell(row - MazeConstants.RIGHT, col, b);
				}
			}

		}
	}

	/**
	 * Returns the dimensions of the maze in the file named layoutFileName.
	 * 
	 * @param layoutFileName
	 *            the path of the input maze file
	 * @return an array [numRows, numCols], where numRows is the number of rows
	 *         and numCols is the number of columns in the maze that corresponds
	 *         to the given input maze file
	 * @throws IOException
	 */
	private int[] getDimensions(String layoutFileName) throws IOException {

		Scanner sc = new Scanner(new File(layoutFileName));

		// find the number of columns
		String nextLine = sc.nextLine();
		int numCols = nextLine.length();

		int numRows = 1;

		// find the number of rows
		while (sc.hasNext()) {
			numRows++;
			nextLine = sc.nextLine();
		}

		sc.close();
		return new int[] { numRows, numCols };
	}

	/**
	 * 
	 * @return The identity of the winning player or reveal if its a tie
	 */

	public int hasWon() {
		int result = 0;
		if (bananas.isEmpty()) {
			if (player1.getScore() > player2.getScore()) {
				result = 1;
			} else if (player1.getScore() < player2.getScore()) {
				result = 2;
			} else {// 3 is a tie
				result = 3;
			}
		}
		return result;
	}

	/**
	 * 
	 * @return If a player is stuck
	 */

	public boolean isBlocked() {
		boolean block = false;
		if (((location(getPlayerOne().row, getPlayerOne().column,
				MazeConstants.LEFT) instanceof Wall)
				|| (location(getPlayerOne().row, getPlayerOne().column,
						MazeConstants.LEFT) instanceof VisitedHallway) || (location(
					getPlayerOne().row, getPlayerOne().column,
					MazeConstants.LEFT) instanceof Monkey))
				&&

				((location(getPlayerOne().row, getPlayerOne().column,
						MazeConstants.RIGHT) instanceof Wall)
						|| (location(getPlayerOne().row, getPlayerOne().column,
								MazeConstants.RIGHT) instanceof VisitedHallway) || (location(
							getPlayerOne().row, getPlayerOne().column,
							MazeConstants.RIGHT) instanceof Monkey))
				&&

				((location(getPlayerOne().row, getPlayerOne().column,
						MazeConstants.UP) instanceof Wall)
						|| (location(getPlayerOne().row, getPlayerOne().column,
								MazeConstants.UP) instanceof VisitedHallway) || (location(
							getPlayerOne().row, getPlayerOne().column,
							MazeConstants.UP) instanceof Monkey))
				&&

				((location(getPlayerOne().row, getPlayerOne().column,
						MazeConstants.DOWN) instanceof Wall)
						|| (location(getPlayerOne().row, getPlayerOne().column,
								MazeConstants.DOWN) instanceof VisitedHallway) || (location(
							getPlayerOne().row, getPlayerOne().column,
							MazeConstants.DOWN) instanceof Monkey))
				&& ((location(getPlayerTwo().row, getPlayerTwo().column,
						MazeConstants.LEFT) instanceof Wall)
						|| (location(getPlayerTwo().row, getPlayerTwo().column,
								MazeConstants.LEFT) instanceof VisitedHallway) || (location(
							getPlayerTwo().row, getPlayerTwo().column,
							MazeConstants.LEFT) instanceof Monkey))
				&&

				((location(getPlayerTwo().row, getPlayerTwo().column,
						MazeConstants.RIGHT) instanceof Wall)
						|| (location(getPlayerTwo().row, getPlayerTwo().column,
								MazeConstants.RIGHT) instanceof VisitedHallway) || (location(
							getPlayerTwo().row, getPlayerTwo().column,
							MazeConstants.RIGHT) instanceof Monkey))
				&&

				((location(getPlayerTwo().row, getPlayerTwo().column,
						MazeConstants.UP) instanceof Wall)
						|| (location(getPlayerTwo().row, getPlayerTwo().column,
								MazeConstants.UP) instanceof VisitedHallway) || (location(
							getPlayerTwo().row, getPlayerTwo().column,
							MazeConstants.UP) instanceof Monkey))
				&&

				((location(getPlayerTwo().row, getPlayerTwo().column,
						MazeConstants.DOWN) instanceof Wall)
						|| (location(getPlayerTwo().row, getPlayerTwo().column,
								MazeConstants.DOWN) instanceof VisitedHallway) || (location(
							getPlayerTwo().row, getPlayerTwo().column,
							MazeConstants.DOWN) instanceof Monkey))) {
			block = true;
		}
		return block;
	}
}
