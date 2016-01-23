/**
 * 
 */
package mazegame;

/**
 * Initialize ArrayGrid<T> to create basic grid
 *
 * @param numRows
 *            number of rows of grid
 * @param numCols
 *            number of columns of grid
 * 
 */
public class ArrayGrid<T> implements Grid<T> {
	private int numRows;
	private int numCols;
	private T[][] temp;

	@SuppressWarnings("unchecked")
	public ArrayGrid(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		this.temp = (T[][]) new Object[this.numRows][this.numCols];

	}

	/**
	 * Stores item on grid at row and column
	 */

	public void setCell(int row, int column, T item) {
		item = temp[row][column];
	}

	/**
	 * Returns item on grid at row and colum
	 */

	public T getCell(int row, int column) {
		return temp[row][column];
	}

	/**
	 * Returns the number of rows
	 */

	public int getNumRows() {
		return this.numRows;

	}

	/**
	 * Returns the number of columns
	 */

	public int getNumCols() {
		return this.numCols;

	}

	/**
	 * Returns True if grids are the same
	 */

	@Override
	public boolean equals(Grid<T> other) {
		return (this.toString() == other.toString());

	}

	/**
	 * Creates the grid in grid form
	 */

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < this.getNumRows(); i++) {
			for (int j = 0; j < this.getNumCols(); j++) {
				result += temp[i][j];
			}
			result += "\n";

		}
		return result;
	}

}
