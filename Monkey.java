/**
 * 
 */
package mazegame;

/**
 * @author nainasingh This class initializes the Monkey and its position and
 *         allows it move. Also generates player's score and number of moves.
 *         Also allows Monkey to ear Banana.
 */
public class Monkey extends Sprite implements Moveable {
	public int score;
	public int numMoves;

	/**
	 * @param symbol
	 *            Symbol for monkey
	 * @param row
	 *            Row of grid
	 * @param column
	 *            Column of grid
	 */
	public Monkey(char symbol, int row, int column) {
		super(symbol, row, column);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param score
	 *            increments the score from eating Banana
	 */

	public void eatBanana(int score) {
		this.score += score;
	}

	/**
	 * 
	 * @return the player's score
	 */

	public int getScore() {
		return this.score;
	}

	/**
	 * 
	 * @return the number of moves by the player
	 */

	public int getNumMoves() {
		return this.numMoves;
	}

	/**
	 * Allows monkey to move position and tracks number of moves
	 */

	@Override
	public void move(int row, int column) {
		this.row += row;
		this.column += column;
		this.numMoves += 1;

	}

}
