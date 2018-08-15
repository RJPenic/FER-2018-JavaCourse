package hr.fer.zemris.java.gui.layouts;

/**
 * Class representing a position in layout with which the location of the
 * certain component is determined.
 * 
 * @author Rafael Josip Penić
 *
 */
public class RCPosition {

	/**
	 * Row index of the position
	 */
	private final int row;

	/**
	 * Column index of position
	 */
	private final int column;

	/**
	 * Constructor for RCPosition objects
	 * 
	 * @param row
	 *            row of the position
	 * @param column
	 *            column of the position
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter for row of the position
	 * 
	 * @return positions row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter for column of the position
	 * 
	 * @return positions column
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

}
