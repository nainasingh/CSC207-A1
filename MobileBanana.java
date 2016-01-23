/**
 * 
 */
package mazegame;

/**
 * @author nainasingh This class handles the movement of the MobileBanana as
 *         well as its value
 */
public class MobileBanana extends Banana implements Moveable {

	/**
	 * @param symbol
	 *            Symbol for MobileBanana
	 * @param row
	 *            Row in grid
	 * @param column
	 *            Columb in grid
	 * @param value
	 *            Value of MobileBanana
	 */
	public MobileBanana(char symbol, int row, int column, int value) {
		super(symbol, row, column, value);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Move Mobile Banana
	 */

	@Override
	public void move(int row, int column) {

		this.row += row;
		this.column += column;
	}

	/**
	 * Return MobileBanana value
	 */

	public int getValue() {
		return MazeConstants.MOBILE_BANANA_SCORE;
	}

}
