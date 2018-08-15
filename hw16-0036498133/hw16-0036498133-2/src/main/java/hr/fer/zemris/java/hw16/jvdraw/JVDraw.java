package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.components.ColorInfoLabel;
import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.geometry.Line;
import hr.fer.zemris.java.hw16.jvdraw.list.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

/**
 * Program that offers simple drawing mechanics and provides saving and
 * exporting options.
 * 
 * @author Rafael Josip Penić
 *
 */
public class JVDraw extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Drawing tool currently used for drawing geometry objects
	 */
	private Tool currentDrawingTool;

	/**
	 * Color provider that determines foreground color when drawing
	 */
	private JColorArea fgColorArea = new JColorArea(Color.BLUE);

	/**
	 * Color provider that determines background color when drawing
	 */
	private JColorArea bgColorArea = new JColorArea(Color.WHITE);

	/**
	 * Drawing model which "stores" drawn objects
	 */
	private DrawingModel dModel = new DrawingModelImpl();

	/**
	 * Drawing canvas on which the objects are drawn
	 */
	private JDrawingCanvas canvas;

	/**
	 * JList which shows current state of the drawing model
	 */
	private JList<GeometricalObject> list;

	/**
	 * Current save path
	 */
	private Path path = null;

	/**
	 * Modification flag. If the painting has been modified, this flag is true
	 */
	private boolean modified = false;

	/**
	 * Action which provides ability to open appropriate jvd files.
	 */
	private final Action openFileAction = new AbstractAction("Open") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");

			FileNameExtensionFilter filter = new FileNameExtensionFilter("jvd", "jvd");
			jfc.setFileFilter(filter);

			if (jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File fileName = jfc.getSelectedFile();
			Path filePath = fileName.toPath();

			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JVDraw.this, "Error while processing given file!", "Error",
						JOptionPane.ERROR);
				return;
			}

			DrawingModel tempDModel = new DrawingModelImpl();

			try {
				List<String> lines = Files.readAllLines(filePath);

				for (String line : lines) {
					line = line.trim();
					if (line.isEmpty())
						continue;

					String[] lineArr = line.split("\\s+");

					switch (lineArr[0].toUpperCase()) {
					case ("LINE"):
						Line dLine = new Line(new Point(Integer.parseInt(lineArr[1]), Integer.parseInt(lineArr[2])),
								(new Point(Integer.parseInt(lineArr[3]), Integer.parseInt(lineArr[4]))),
								new Color(Integer.parseInt(lineArr[5]), Integer.parseInt(lineArr[6]),
										Integer.parseInt(lineArr[7])));
						tempDModel.add(dLine);
						break;
					case ("CIRCLE"):
						Circle dCircle = new Circle(
								new Point(Integer.parseInt(lineArr[1]), Integer.parseInt(lineArr[2])),
								Integer.parseInt(lineArr[3]), new Color(Integer.parseInt(lineArr[4]),
										Integer.parseInt(lineArr[5]), Integer.parseInt(lineArr[6])));
						tempDModel.add(dCircle);
						break;
					case ("FCIRCLE"):
						FilledCircle dFCircle = new FilledCircle(
								new Point(Integer.parseInt(lineArr[1]), Integer.parseInt(lineArr[2])),
								Integer.parseInt(lineArr[3]),
								new Color(Integer.parseInt(lineArr[4]), Integer.parseInt(lineArr[5]),
										Integer.parseInt(lineArr[6])),
								new Color(Integer.parseInt(lineArr[7]), Integer.parseInt(lineArr[8]),
										Integer.parseInt(lineArr[9])));
						tempDModel.add(dFCircle);
						break;
					}
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(JVDraw.this, "Error while reading given file!", "Error",
						JOptionPane.ERROR);
				return;
			} catch (IndexOutOfBoundsException | IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(JVDraw.this, "Given file is not formatted correctly!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			path = filePath;

			JVDraw.this.setTitle("JVDraw - " + path.toAbsolutePath().normalize().toString());

			int size = dModel.getSize();
			for (int i = 0; i < size; i++) {
				dModel.remove(dModel.getObject(0));
			}

			for (int i = 0; i < tempDModel.getSize(); i++) {
				dModel.add(tempDModel.getObject(i));
			}

			canvas.repaint();
		}
	};

	/**
	 * Action which provides ability to save paintings in jvd form
	 */
	private final Action saveFileAction = new AbstractAction("Save") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			save(false);
		}
	};

	/**
	 * Action very similar to saveAction, but it does not depend on current path of
	 * the painting
	 */
	private final Action saveAsFileAction = new AbstractAction("Save As") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			save(true);
		}
	};

	/**
	 * Action which provides ability to export paintings in form of png, jpg or gif
	 * images
	 */
	private final Action exportAction = new AbstractAction("Export") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
			for (int i = 0; i < dModel.getSize(); i++) {
				dModel.getObject(i).accept(bbcalc);
			}

			Rectangle box = bbcalc.getBoundingBox();
			if (box.height == 0) {// in case of an line that is parallel with x-axis
				box.height = 1;
			}

			if (box.width == 0) {// in case of an line that is parallel with y-axis
				box.width = 1;
			}

			BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);

			Graphics2D g = image.createGraphics();

			g.translate(-box.x, -box.y);
			GeometricalObjectPainter painter = new GeometricalObjectPainter(g, dModel, fgColorArea, bgColorArea,
					canvas);
			for (int i = 0; i < dModel.getSize(); i++) {
				dModel.getObject(i).accept(painter);
			}
			g.dispose();

			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Extract");

			initJFCFilters(jfc);
			jfc.setAcceptAllFileFilterUsed(false);

			if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File f = new File(jfc.getSelectedFile() + jfc.getFileFilter().getDescription());
			try {
				ImageIO.write(image, extractExtension(f), f);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(JVDraw.this, "Error while exporting file!", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			JOptionPane.showMessageDialog(JVDraw.this, "Image has been exported", "Info",
					JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * Initializes filters of a file chooser that is responsible for saving an image
	 * when exporting
	 * 
	 * @param jfc
	 *            file chooser on which the filters will be set
	 */
	private static void initJFCFilters(JFileChooser jfc) {
		jfc.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return ".png";
			}

			@Override
			public boolean accept(File f) {
				String fname = f.getName().toLowerCase();
				return fname.endsWith(".png");
			}
		});

		jfc.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return ".gif";
			}

			@Override
			public boolean accept(File f) {
				String fname = f.getName().toLowerCase();
				return fname.endsWith(".gif");
			}
		});

		jfc.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return ".jpg";
			}

			@Override
			public boolean accept(File f) {
				String fname = f.getName().toLowerCase();
				return fname.endsWith(".jpg");
			}
		});

	}

	/**
	 * Action called when exiting the program
	 */
	private final Action exitAction = new AbstractAction("Exit") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			exitMethod();
		}
	};

	/**
	 * Extracts extension from the given file
	 * 
	 * @param f
	 *            File object from which the extension will be extracted
	 * @return files extension
	 */
	private static String extractExtension(File f) {
		int index = f.getName().lastIndexOf('.');
		if (index < 0)
			return "";

		return f.getName().substring(index + 1);
	}

	/**
	 * Method called when saving a painting in jvd form
	 * 
	 * @param isSaveAs
	 *            determines if it is called by SaveAs or Save option
	 */
	private void save(boolean isSaveAs) {
		if (path == null || isSaveAs) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle(isSaveAs ? "Save As" : "Save");
			jfc.setFileFilter(new FileNameExtensionFilter(".jvd", ".jvd"));
			jfc.setAcceptAllFileFilterUsed(false);
			if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File fileName = new File(jfc.getSelectedFile().toString() + jfc.getFileFilter().getDescription());
			Path filePath = fileName.toPath();

			saveOnPath(filePath);
			path = filePath;
			JVDraw.this.setTitle("JVDraw - " + path.toAbsolutePath().normalize().toString());
		} else {
			saveOnPath(path);
		}
	}

	/**
	 * Saves current painting as jvd file on the given path
	 * 
	 * @param p
	 *            path on which the drawing will be saved
	 */
	private void saveOnPath(Path p) {
		try {
			BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(p, StandardOpenOption.CREATE));

			for (int i = 0; i < dModel.getSize(); i++) {
				GeometricalObject ob = dModel.getObject(i);

				bos.write((ob.constructStringForFile() + "\n").getBytes());
				bos.flush();
			}
			bos.close();
			modified = false;
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(JVDraw.this, "Error while saving file!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

	}

	/**
	 * Default constructor which set default close operation, size and initializes
	 * GUI
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitMethod();
			}
		});

		setLocation(50, 50);
		setSize(800, 600);
		setTitle("JVDraw");

		initGUI();
	}

	/**
	 * Initializes programs Graphical User Interface(Initializes canvas, tool bar
	 * and etc.)
	 */
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());

		initCanvas();
		initToolBar();
		initBottomBar();
		initList();
		initMenu();

		initDModelListener();
	}

	/**
	 * Method called when exiting the program
	 */
	private void exitMethod() {
		boolean cancelFlag = false;
		if (modified) {
			int res = JOptionPane.showConfirmDialog(JVDraw.this, "Painting hasn't been save. Do you want to save it?",
					"Exit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (res == JOptionPane.YES_OPTION) {
				save(false);
			} else if (res != JOptionPane.NO_OPTION) {
				cancelFlag = true;
			}
		}

		if (!cancelFlag) {
			dispose();
		}
	}

	/**
	 * Initializes JVDraw's drawing model listeners
	 */
	private void initDModelListener() {
		dModel.addDrawingModelListener(new DrawingModelListener() {

			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
			}

			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				modified = true;
			}

			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				modified = true;
			}
		});
	}

	/**
	 * Initializes menu which offers options like opening or saving a file
	 */
	private void initMenu() {
		JMenuBar menu = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menu.add(fileMenu);

		fileMenu.add(new JMenuItem(openFileAction));
		fileMenu.add(new JMenuItem(saveFileAction));
		fileMenu.add(new JMenuItem(saveAsFileAction));

		this.setJMenuBar(menu);
	}

	/**
	 * Initializes JVDraw's side list
	 */
	private void initList() {
		list = new JList<>(new DrawingObjectListModel(dModel));

		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = list.locationToIndex(e.getPoint());
					if (index >= 0) {
						GeometricalObject ob = list.getModel().getElementAt(index);
						GeometricalObjectEditor editor = ob.createGeometricalObjectEditor();

						int res = JOptionPane.showConfirmDialog(JVDraw.this, editor, ob.toString(),
								JOptionPane.OK_CANCEL_OPTION);

						if (res == JOptionPane.OK_OPTION) {
							try {
								editor.checkEditing();
								editor.acceptEditing();
								canvas.repaint();
							} catch (RuntimeException ex) {
								JOptionPane.showMessageDialog(JVDraw.this, "Input is not valid!", "Warning!",
										JOptionPane.WARNING_MESSAGE);

								mouseClicked(e);
							}
						}
					}
				}
			}
		});

		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int index = list.getSelectedIndex();
				if (index < 0)
					return;
				GeometricalObject ob = list.getModel().getElementAt(index);

				switch (e.getKeyCode()) {
				case (KeyEvent.VK_DELETE):
					dModel.remove(ob);
					canvas.repaint();
					break;
				case (KeyEvent.VK_ADD):
					dModel.changeOrder(ob, -1);
					list.setSelectedIndex(index - 1);
					canvas.repaint();
					break;
				case (KeyEvent.VK_SUBTRACT):
					dModel.changeOrder(ob, +1);
					list.setSelectedIndex(index + 1);
					canvas.repaint();
					break;
				}
			}
		});

		add(new JScrollPane(list), BorderLayout.EAST);
	}

	/**
	 * Initializes canvas and it adds appropriate listeners to it
	 */
	private void initCanvas() {
		canvas = new JDrawingCanvas(dModel, bgColorArea, bgColorArea);

		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentDrawingTool.mouseClicked(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				currentDrawingTool.mousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				currentDrawingTool.mouseReleased(e);
			}

		});

		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				currentDrawingTool.mouseDragged(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				currentDrawingTool.mouseMoved(e);
			}

		});

		add(canvas, BorderLayout.CENTER);
	}

	/**
	 * Initializes JVDraw's tool bar
	 */
	private void initBottomBar() {
		ColorInfoLabel infoLabel = new ColorInfoLabel(fgColorArea, bgColorArea);

		fgColorArea.addColorChangeListener(infoLabel);
		bgColorArea.addColorChangeListener(infoLabel);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 0));

		bottomPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		bottomPanel.add(infoLabel);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	/**
	 * Initializes JVDraw's tool bar
	 */
	private void initToolBar() {
		JToolBar toolBar = new JToolBar();

		fgColorArea.setMaximumSize(new Dimension(25, 25));
		bgColorArea.setMaximumSize(new Dimension(25, 25));

		toolBar.add(fgColorArea);
		toolBar.addSeparator();
		toolBar.add(bgColorArea);
		toolBar.addSeparator();

		ButtonGroup group = new ButtonGroup();

		JToggleButton lineDrawToggle = new JToggleButton("Line");
		group.add(lineDrawToggle);
		lineDrawToggle.addActionListener(l -> {
			currentDrawingTool = new LineTool(fgColorArea, dModel, canvas);
		});
		toolBar.add(lineDrawToggle);
		lineDrawToggle.setSelected(true);
		toolBar.addSeparator();

		JToggleButton circleDrawToggle = new JToggleButton("Circle");
		group.add(circleDrawToggle);
		circleDrawToggle.addActionListener(l -> {
			currentDrawingTool = new CircleTool(dModel, fgColorArea, canvas);
		});
		toolBar.add(circleDrawToggle);
		toolBar.addSeparator();

		JToggleButton filledCircleDrawToggle = new JToggleButton("Filled circle");
		group.add(filledCircleDrawToggle);
		filledCircleDrawToggle.addActionListener(l -> {
			currentDrawingTool = new FilledCircleTool(dModel, fgColorArea, bgColorArea, canvas);
		});
		toolBar.add(filledCircleDrawToggle);
		toolBar.addSeparator();
		toolBar.addSeparator();
		toolBar.addSeparator();

		currentDrawingTool = new LineTool(fgColorArea, dModel, canvas);

		toolBar.add(new JButton(exportAction));
		toolBar.add(new JButton(exitAction));

		this.add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * Main program
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}
}
