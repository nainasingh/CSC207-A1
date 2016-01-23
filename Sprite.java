package mazegame;

/**
 * 
 * @author nainasingh This class allows us to create Sprite objects and utilize
 *         them. It assigns a symbol, row and position to each object.
 */

public abstract class Sprite {

	protected char symbol;
	protected int row;
	protected int column;

	/**
	 * 
	 * @param symbol
	 *            Symbol for object
	 * @param row
	 *            Row object is located at in grid
	 * @param column
	 *            Column object is located at in grid
	 */

	public Sprite(char symbol, int row, int column) {

		this.symbol = symbol;
		this.row = row;
		this.column = column;

	}

	/**
	 * 
	 * @return symbol of object
	 */

	public char getSymbol() {
		return symbol;
	}

	/**
	 * 
	 * @return row of object
	 */

	public int getRow() {
		return row;
	}

	/**
	 * 
	 * @return symbol of object
	 */

	public int getColumn() {
		return column;
	}

	/**
	 * Returns object's symbol as String
	 */

	@Override
	public String toString() {
		return Character.toString(this.symbol);

	}

}
