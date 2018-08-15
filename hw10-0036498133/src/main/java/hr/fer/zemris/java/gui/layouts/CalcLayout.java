package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

/**
 * Layout manager that provides basic calculator design when designing GUI
 * 
 * @author Rafael Josip Penić
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Number of rows in the layout
	 */
	private static final int MAX_ROW = 5;

	/**
	 * Number of columns in the layout
	 */
	private static final int MAX_COLUMN = 7;

	/**
	 * gap(gap) between components(both vertically and horizontally)
	 */
	private int gap;

	/**
	 * Map used as storage for components added in the container
	 */
	private Map<Component, RCPosition> components = new HashMap<>();

	/**
	 * Default constructor for CalcLayout which constructs CalcLayout with the gap
	 * equal to zero
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor for CalcLayout which constructs CalcLayout layout with the given
	 * gap between the components of the container
	 * 
	 * @param gap
	 *            gap between components
	 */
	public CalcLayout(int gap) {
		if (gap < 0)
			throw new IllegalArgumentException("Gap between components cannot be negative.");

		this.gap = gap;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		Objects.requireNonNull(name, "Given string reference is null.");
		Objects.requireNonNull(comp, "Given component reference is null.");

		String[] tempArray = name.split(",");

		if (tempArray.length != 2)
			throw new CalcLayoutException("Given string has too many arguments.");

		try {
			addLayoutComponent(comp, new RCPosition(Integer.parseInt(tempArray[0]), Integer.parseInt(tempArray[1])));
		} catch (NumberFormatException ex) {
			throw new CalcLayoutException("Given string arguments cannot be parsed into integer.");
		}

	}

	@Override
	public void layoutContainer(Container parent) {
		int numberOfComps = parent.getComponentCount();
		Insets insets = parent.getInsets();

		Dimension dim = parent.getSize();

		Dimension realDim = new Dimension(dim.width - (insets.right + insets.right),
				dim.height - (insets.bottom + insets.top));

		int xStep = (realDim.width - (MAX_COLUMN - 1) * gap) / MAX_COLUMN;
		int yStep = (realDim.height - (MAX_ROW - 1) * gap) / MAX_ROW;

		for (int i = 0; i < numberOfComps; i++) {
			Component comp = parent.getComponent(i);

			RCPosition pos = components.get(comp);

			if (pos != null) {
				int x = insets.left + (pos.getColumn() - 1) * xStep + (pos.getColumn() - 1) * gap;
				int y = insets.top + (pos.getRow() - 1) * yStep + (pos.getRow() - 1) * gap;
				int compHeight = yStep;

				int compWidth;
				if (pos.getRow() == 1 && pos.getColumn() == 1) {
					compWidth = xStep * (MAX_COLUMN - 2) + gap * (MAX_COLUMN - 3);
				} else {
					compWidth = xStep;
				}

				comp.setBounds(x, y, compWidth, compHeight);
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calculateLayoutSize(parent, (comp) -> comp.getMinimumSize());
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calculateLayoutSize(parent, (comp) -> comp.getPreferredSize());
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints instanceof RCPosition) {
			RCPosition position = (RCPosition) constraints;

			int row = position.getRow();
			int column = position.getColumn();

			if (row < 1 || row > MAX_ROW)
				throw new CalcLayoutException("Given row constraint is not in allowed interval([1,5])");

			if (column < 1 || column > MAX_COLUMN)
				throw new CalcLayoutException("Given column constraint is not in allowed interval([1,7])");

			if (row == 1 && (column > 1 && column < MAX_COLUMN - 1))
				throw new CalcLayoutException("Given row, column combination is not allowed.");

			if (components.containsValue(position))
				throw new CalcLayoutException("Given position is already taken.");

			components.put(comp, position);
		} else if (constraints instanceof String) {
			addLayoutComponent((String) constraints, comp);
		} else {
			throw new CalcLayoutException("Given constraints object must be instance of RCPosition class.");
		}
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0.5f;
	}

	@Override
	public void invalidateLayout(Container target) {
		// do nothing
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return calculateLayoutSize(target, (comp) -> comp.getMaximumSize());
	}

	/**
	 * Method that calculates needed layout size depending on what is
	 * needed(maximum, minimum and etc.)
	 * 
	 * @param cont
	 *            Containers which size will be calculated
	 * @param func
	 *            function that determines what will be calculated(e.g. for maximum
	 *            it is (comp) -> comp.getMaximumSize())
	 * @return calculated dimensions
	 */
	private Dimension calculateLayoutSize(Container cont, Function<Component, Dimension> func) {
		Insets insets = cont.getInsets();

		int horizontalInset = insets.left + insets.right;
		int verticalInset = insets.bottom + insets.top;

		int width = horizontalInset;
		int height = verticalInset;

		for (Entry<Component, RCPosition> pair : components.entrySet()) {
			Component comp = pair.getKey();
			RCPosition pos = pair.getValue();

			Dimension dim = func.apply(comp);

			if (pos.getRow() == 1 && pos.getColumn() == 1) {
				width = Math.max(width, (horizontalInset + ((dim.width - 4 * gap)/5)*MAX_COLUMN + (MAX_COLUMN - 1) * gap));
			} else {
				width = Math.max(width, horizontalInset + MAX_COLUMN * dim.width + (MAX_COLUMN - 1) * gap);
			}

			height = Math.max(height, verticalInset + MAX_ROW * dim.height + (MAX_ROW - 1) * gap);
		}

		return new Dimension(width, height);
	}
}
