/**
 * 
 */
package mazegame;

/**
 * @author nainasingh This interface generates grid and items. It also stores
 *         items, gets numbers of rows and columns and checks to see if 2 grids
 *         are the same.
 */
public interface Grid<T> {
	public void setCell(int row, int column, T item);

	/**
	 * @param row
	 *            The row in the grid
	 * @param column
	 *            The column in the grid
	 */
	public T getCell(int row, int column);

	public int getNumRows();

	public int getNumCols();

	public boolean equals(Grid<T> other);

	public String toString();

}
