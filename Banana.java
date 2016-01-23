/**
 * 
 */
package mazegame;

/**
 * @author nainasingh Initializes Banana and values, and returns values.
 */
public class Banana extends Sprite {
	public int value;

	/**
	 * @param symbol
	 *            Banana's symbol
	 * @param row
	 *            Row in grid
	 * @param column
	 *            Column in grid
	 */
	public Banana(char symbol, int row, int column, int value) {
		super(symbol, row, column);
		this.value = value;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns the value of Banana
	 */

	public int getValue() {
		return value;
	}

}
