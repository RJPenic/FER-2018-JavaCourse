package hr.fer.zemris.java.hw06.shell.commands;

/**
 * Name builder which appends certain groups of regex to a String Builder
 * 
 * @author Rafael Josip Penić
 *
 */
public class NameBuilderGroup implements NameBuilder {

	/**
	 * Index of group that will be appended
	 */
	private int groupIndex;

	/**
	 * Width that the appended string will take
	 */
	private int size;

	/**
	 * Determines if the appended string will be prefixed by zeros or white spaces
	 */
	private boolean zeros;

	/**
	 * Constructor for NameBuilderGroup objects
	 * 
	 * @param s
	 *            string that contains needed arguments for constructing the name
	 *            builder
	 */
	public NameBuilderGroup(String s) {
		String[] arr = s.split(",");
		if (arr.length == 1 || arr.length == 2) {
			try {
				groupIndex = Integer.parseInt(arr[0].trim());
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException(arr[0].trim() + " cannot be parsed into integer.");
			}
			if (groupIndex < 0)
				throw new IllegalArgumentException("Group index cannot be negative.");

			if (arr.length == 2) {
				try {
					size = Integer.parseInt(arr[1].trim());
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException(arr[1].trim() + " cannot be parsed into integer.");
				}
				
				if (size <= 0)
					throw new IllegalArgumentException("Given size must be positive.");
				
				if (arr[1].trim().startsWith("0")) {
					zeros = true;
				}
			}
		}
	}

	@Override
	public void execute(NameBuilderInfo info) {
		String s = info.getGroup(groupIndex);

		if (zeros) {
			if (s.length() < size) {
				for (int i = 0; i < size - s.length(); i++) {
					info.getStringBuilder().append("0");
				}
			}
		} else {
			if (size > s.length()) {
				s = String.format("%" + (size - s.length()) + "s", " ") + s;
			}
		}

		info.getStringBuilder().append(s);
	}

}
