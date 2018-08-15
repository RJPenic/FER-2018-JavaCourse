package hr.fer.zemris.java.gui.calc;

/**
 * Listeners which notify their callers that calculators value changed
 * 
 * @author Rafael Josip Penić
 *
 */
public interface CalcValueListener {

	/**
	 * Invoked when the calculators value changes
	 * 
	 * @param model
	 *            calculator which value changed
	 */
	void valueChanged(CalcModel model);
}