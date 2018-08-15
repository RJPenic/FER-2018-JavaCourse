package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program that generates GUI program which shows 2 lists that implements the
 * same model and in which the prime numbers are generated
 * 
 * @author Rafael Josip Penić
 *
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for PrimDemo objects.
	 */
	public PrimDemo() {
		setLocation(50, 50);
		setSize(300, 250);
		setTitle("Primes");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	/**
	 * Method that initializes Graphical User Interface
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();

		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton nextButton = new JButton("Sljedeći");
		southPanel.add(nextButton);

		nextButton.addActionListener(e -> {
			model.next();
		});

		JPanel centralPanel = new JPanel(new GridLayout(1, 0));
		centralPanel.add(new JScrollPane(list1));
		centralPanel.add(new JScrollPane(list2));

		cp.add(centralPanel, BorderLayout.CENTER);
		cp.add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
