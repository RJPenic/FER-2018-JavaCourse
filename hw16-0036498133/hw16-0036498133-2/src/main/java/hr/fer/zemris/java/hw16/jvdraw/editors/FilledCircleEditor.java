package hr.fer.zemris.java.hw16.jvdraw.editors;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geometry.FilledCircle;

/**
 * Editor that can edit filled circle objects
 * 
 * @author Rafael Josip Penić
 *
 */
public class FilledCircleEditor extends CircleEditor {

	private static final long serialVersionUID = 1L;

	/**
	 * filled circle that is edited
	 */
	private FilledCircle filledCircle;

	/**
	 * Color area object that receives input about circles fill color
	 */
	private JColorArea bgColorArea;

	/**
	 * Constructor
	 * 
	 * @param filledCircle
	 *            filled circle that is edited
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		super(filledCircle);

		this.filledCircle = filledCircle;

		add(new JLabel("Circle area color : "));
		bgColorArea = new JColorArea(filledCircle.getBackgroundColor());
		add(bgColorArea);
	}

	@Override
	public void acceptEditing() {
		super.acceptEditing();
		filledCircle.setBackgroundColor(bgColorArea.getCurrentColor());
	}

}
