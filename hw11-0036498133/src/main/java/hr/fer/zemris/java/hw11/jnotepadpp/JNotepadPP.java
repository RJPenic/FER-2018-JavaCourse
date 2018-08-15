package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import java.util.function.*;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Program which realizes multitabbed Notepad with various options for modifying
 * documents text.
 * 
 * @author Rafael Josip Penić
 *
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Notepads tabbed panel which allows creation of new tabs
	 */
	private DefaultMultipleDocumentModel tabbedPane;

	/**
	 * Localization provider used for translation of notepad in various languages
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

	/**
	 * Label containing information about documents length
	 */
	private JLabel length = new JLabel();

	/**
	 * Label containing information about carets current line
	 */
	private JLabel ln = new JLabel();

	/**
	 * Label containing information about carets current column
	 */
	private JLabel col = new JLabel();

	/**
	 * Label containing information about length of selected text
	 */
	private JLabel sel = new JLabel();

	/**
	 * Label containing current timestamp
	 */
	private JLabel time = new JLabel();

	/**
	 * Document listener which updates length label
	 */
	private DocumentListener docL = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			changedUpdate(e);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			changedUpdate(e);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			length.setText(Integer.toString(tabbedPane.getCurrentDocument().getTextComponent().getText().length()));
		}
	};

	/**
	 * Boolean flag used for stopping program when exiting(needed because of the
	 * clock)
	 */
	private volatile boolean stopped = false;

	/**
	 * Constructor for JNotepad frame
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitMethod();
			}
		});

		setLocation(0, 0);
		setSize(800, 600);
		setTitle("JNotepad++");

		initGUI();
	}

	/**
	 * Method that initializes GUI of the notepad and registers initial listeners
	 */
	private void initGUI() {
		tabbedPane = new DefaultMultipleDocumentModel();

		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				int index = tabbedPane.getSelectedIndex();

				if (index == -1) {
					tabbedPane.setCurrentDocument(null);
				} else {
					tabbedPane.setCurrentDocument(tabbedPane.getDocument(index));
				}

				if (tabbedPane.getCurrentDocument() == null) {
					setTitle("JNotepad++");
				} else {
					String name;

					if (tabbedPane.getCurrentDocument().getFilePath() == null) {
						name = "New document";
					} else {
						name = tabbedPane.getCurrentDocument().getFilePath().getFileName().toString();
					}

					setTitle(name + " - JNotepad++");
				}
			}
		});

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addMultipleDocumentListener(new MultipleDocumentListener() {

			private int serializer = 1;

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				int index = tabbedPane.getIndexOfSingleDocumentModel(model);
				tabbedPane.removeTabAt(index);
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				if (model.getFilePath() == null) {
					tabbedPane.addTab("New_Document_" + (serializer++), new JScrollPane(model.getTextComponent()));
				} else {
					tabbedPane.addTab(model.getFilePath().getFileName().toString(),
							new JScrollPane(model.getTextComponent()));
				}
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (currentModel != null) {
					if (previousModel != null) {
						previousModel.getTextComponent().removeCaretListener(l);
						previousModel.getTextComponent().getDocument().removeDocumentListener(docL);
					}

					currentModel.getTextComponent().addCaretListener(l);

					currentModel.getTextComponent().getDocument().addDocumentListener(docL);

					l.caretUpdate(null);
					docL.changedUpdate(null);
					tabbedPane.setSelectedIndex(tabbedPane.getIndexOfSingleDocumentModel(currentModel));
				}
			}
		});

		tabbedPane.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				if (tabbedPane.getNumberOfDocuments() == 1) {
					saveAsDocumentAction.setEnabled(false);
					saveDocumentAction.setEnabled(false);
					closeCurrentTabAction.setEnabled(false);
					showStatisticsAction.setEnabled(false);
				}
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				if (tabbedPane.getNumberOfDocuments() == 1) {
					saveAsDocumentAction.setEnabled(true);
					saveDocumentAction.setEnabled(true);
					closeCurrentTabAction.setEnabled(true);
					showStatisticsAction.setEnabled(true);
				}
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				// nothing
			}
		});

		saveAsDocumentAction.setEnabled(false);
		saveDocumentAction.setEnabled(false);
		closeCurrentTabAction.setEnabled(false);
		showStatisticsAction.setEnabled(false);
		toUpperAction.setEnabled(false);
		toLowerAction.setEnabled(false);
		convertLettersAction.setEnabled(false);
		sortAscendingAction.setEnabled(false);
		sortDescendingAction.setEnabled(false);
		filterUniqueLinesAction.setEnabled(false);

		addLocalizationListenersForActions();
		setKeysForActions();
		createMenus();
		createToolbar();
		createStatusbar();
		flp.fire();
	}

	/**
	 * Method that constructs GUI status bar and inserts it into the frame
	 */
	private void createStatusbar() {
		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new GridLayout(1, 0));

		JPanel statusBar1 = new JPanel();
		JPanel statusBar2 = new JPanel();
		JPanel statusBar3 = new JPanel();

		statusBar1.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusBar2.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusBar3.setBorder(new BevelBorder(BevelBorder.LOWERED));

		statusBar1.setLayout(new FlowLayout(FlowLayout.LEFT));
		statusBar2.setLayout(new FlowLayout(FlowLayout.LEFT));
		statusBar3.setLayout(new FlowLayout(FlowLayout.LEFT));

		statusBar1.add(new LJLabel("length", flp));// "length : "
		statusBar1.add(length);

		statusBar2.add(new LJLabel("ln", flp));
		statusBar2.add(ln);
		statusBar2.add(new LJLabel("col", flp));
		statusBar2.add(col);
		statusBar2.add(new LJLabel("sel", flp));
		statusBar2.add(sel);

		setupTime();
		statusBar3.add(time);

		statusPanel.add(statusBar1);
		statusPanel.add(statusBar2);
		statusPanel.add(statusBar3);

		this.add(statusPanel, BorderLayout.SOUTH);

	}

	/**
	 * Sets up the clock and date in bottom right corner of the frame
	 */
	private void setupTime() {
		Thread t = new Thread(() -> {
			DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");

			while (true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					SwingUtilities.invokeLater(() -> {
						JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("error"),
								flp.getString("clock_error"), JOptionPane.ERROR_MESSAGE);
						exitMethod();
					});
				}

				if (stopped)
					break;

				SwingUtilities.invokeLater(() -> {
					updateTime(formatterTime, formatterDate);
				});
			}
		});
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Updates time label content
	 * 
	 * @param formatterTime
	 *            time format
	 * @param formatterDate
	 *            date format
	 */
	private void updateTime(DateTimeFormatter formatterTime, DateTimeFormatter formatterDate) {
		time.setText(formatterDate.format(LocalDate.now()) + "   " + formatterTime.format(LocalTime.now()));
	}

	/**
	 * Creates GUI tool bar and inserts it into the frame
	 */
	private void createToolbar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);

		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.addSeparator();

		toolBar.add(new JButton(createBlankDocumentAction));
		toolBar.add(new JButton(closeCurrentTabAction));
		toolBar.addSeparator();

		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.addSeparator();

		toolBar.add(new JButton(showStatisticsAction));
		toolBar.addSeparator();

		toolBar.add(new JButton(exitAction));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Creates menu bar and its submenus and adds it in the frame
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		LJMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(createBlankDocumentAction));
		fileMenu.add(new JMenuItem(closeCurrentTabAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));

		LJMenu editMenu = new LJMenu("edit", flp);
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(pasteAction));

		LJMenu infoMenu = new LJMenu("info", flp);
		menuBar.add(infoMenu);

		infoMenu.add(new JMenuItem(showStatisticsAction));

		LJMenu langMenu = new LJMenu("lang", flp);
		menuBar.add(langMenu);

		ButtonGroup languageGroup = new ButtonGroup();

		JRadioButton hr = new JRadioButton("Hrvatski");
		hr.addActionListener(l -> {
			LocalizationProvider.getInstance().setLanguage("hr");
		});

		JRadioButton eng = new JRadioButton("English");
		eng.addActionListener(l -> {
			LocalizationProvider.getInstance().setLanguage("eng");
		});

		JRadioButton de = new JRadioButton("Deutsch");
		de.addActionListener(l -> {
			LocalizationProvider.getInstance().setLanguage("de");
		});

		languageGroup.add(hr);
		languageGroup.add(eng);
		languageGroup.add(de);
		eng.setSelected(true);

		langMenu.add(hr);
		langMenu.add(eng);
		langMenu.add(de);

		LJMenu tools = new LJMenu("tools", flp);
		menuBar.add(tools);

		LJMenu changeCase = new LJMenu("change_case", flp);
		tools.add(changeCase);

		changeCase.add(new JMenuItem(toUpperAction));
		changeCase.add(new JMenuItem(toLowerAction));
		changeCase.add(new JMenuItem(convertLettersAction));

		LJMenu sort = new LJMenu("sort", flp);
		menuBar.add(sort);

		sort.add(new JMenuItem(sortAscendingAction));
		sort.add(new JMenuItem(sortDescendingAction));

		menuBar.add(new JMenuItem(filterUniqueLinesAction));

		this.setJMenuBar(menuBar);
	}

	private void addLocalizationListenersForActions() {
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				createActions();
			}
		});
	}

	/**
	 * Sets accelerator key combinations and mnemonic keys for some actions
	 */
	private void setKeysForActions() {
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F1"));
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F2"));
		createBlankDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));

		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		closeCurrentTabAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F5"));
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F6"));
		showStatisticsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F7"));

		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		closeCurrentTabAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F8"));
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F9"));
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F10"));

		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F11"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		toUpperAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toLowerAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
	}

	/**
	 * Creates actions names and descriptions(based on current notepad language)
	 */
	private void createActions() {
		saveDocumentAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("save"));
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION,
				LocalizationProvider.getInstance().getString("save_desc"));

		saveAsDocumentAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("saveas"));
		saveAsDocumentAction.putValue(Action.SHORT_DESCRIPTION,
				LocalizationProvider.getInstance().getString("saveas_desc"));

		createBlankDocumentAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("create"));
		createBlankDocumentAction.putValue(Action.SHORT_DESCRIPTION,
				LocalizationProvider.getInstance().getString("create_desc"));

		openDocumentAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("open"));
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION,
				LocalizationProvider.getInstance().getString("open_desc"));

		closeCurrentTabAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("close"));
		closeCurrentTabAction.putValue(Action.SHORT_DESCRIPTION,
				LocalizationProvider.getInstance().getString("close_desc"));

		exitAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("exit"));
		exitAction.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("exit_desc"));

		showStatisticsAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("stats"));
		showStatisticsAction.putValue(Action.SHORT_DESCRIPTION,
				LocalizationProvider.getInstance().getString("stats_desc"));

		copyAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("copy"));
		copyAction.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("copy_desc"));

		pasteAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("paste"));
		pasteAction.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("paste_desc"));

		cutAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("cut"));
		cutAction.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("cut_desc"));

		toUpperAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("toUpper"));
		toUpperAction.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("toUpper_desc"));

		toLowerAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("toLower"));
		toLowerAction.putValue(Action.SHORT_DESCRIPTION, LocalizationProvider.getInstance().getString("toLower_desc"));

		convertLettersAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("convert"));
		convertLettersAction.putValue(Action.SHORT_DESCRIPTION,
				LocalizationProvider.getInstance().getString("convert_desc"));

		sortAscendingAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("sortAsc"));
		sortAscendingAction.putValue(Action.SHORT_DESCRIPTION,
				LocalizationProvider.getInstance().getString("sortAsc_desc"));

		sortDescendingAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("sortDesc"));
		sortDescendingAction.putValue(Action.SHORT_DESCRIPTION,
				LocalizationProvider.getInstance().getString("sortDesc_desc"));

		filterUniqueLinesAction.putValue(Action.NAME, LocalizationProvider.getInstance().getString("unique"));
		filterUniqueLinesAction.putValue(Action.SHORT_DESCRIPTION,
				LocalizationProvider.getInstance().getString("unique_desc"));
	}

	/**
	 * Action initiated when the save document option is chosen
	 */
	private final Action saveDocumentAction = new LocalizableAction("save", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			saveDocument(false);
		}
	};

	/**
	 * Action initiated when save as document option is chosen
	 */
	private final Action saveAsDocumentAction = new LocalizableAction("saveas", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			saveDocument(true);
		}

	};

	/**
	 * If the given boolean is true saving is done as saveAs operation and as save
	 * operation otherwise. Difference is that saveAs will always ask for saving
	 * file path while save doesn't in case document has know file path.
	 * 
	 * @param isSaveAs
	 *            determines if saveAs or save option is chosen
	 */
	private void saveDocument(boolean isSaveAs) {
		Path p;
		if (isSaveAs || tabbedPane.getCurrentDocument().getFilePath() == null) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle(isSaveAs ? LocalizationProvider.getInstance().getString("saveas")
					: LocalizationProvider.getInstance().getString("save"));

			if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						LocalizationProvider.getInstance().getString("nothing_saved_warning"),
						LocalizationProvider.getInstance().getString("warning"), JOptionPane.WARNING_MESSAGE);
				return;
			}

			p = jfc.getSelectedFile().toPath();
		} else {
			p = null;
		}

		try {
			tabbedPane.saveDocument(tabbedPane.getCurrentDocument(), p);
			JNotepadPP.this.setTitle(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()) + " - JNotepad++");
		} catch (RuntimeException ex) {
			JOptionPane.showMessageDialog(JNotepadPP.this,
					LocalizationProvider.getInstance().getString("saving_err").replace("#$1#",
							p.toAbsolutePath().toString()),
					LocalizationProvider.getInstance().getString("error"), JOptionPane.ERROR);
			return;
		}

		JOptionPane.showMessageDialog(JNotepadPP.this, LocalizationProvider.getInstance().getString("doc_saved"),
				LocalizationProvider.getInstance().getString("info"), JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Action initiated when the create document option is chosen
	 */
	private final Action createBlankDocumentAction = new LocalizableAction("create", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			tabbedPane.createNewDocument();
		}
	};

	/**
	 * Action initiated when open document option is chosen
	 */
	private final Action openDocumentAction = new LocalizableAction("open", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(LocalizationProvider.getInstance().getString("open_file"));

			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();

			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						LocalizationProvider.getInstance().getString("file_not_exist").replace("#$1#",
								filePath.toAbsolutePath().toString()),
						LocalizationProvider.getInstance().getString("error"), JOptionPane.ERROR);
				return;
			}

			SingleDocumentModel doc;
			try {
				doc = tabbedPane.loadDocument(filePath);
			} catch (RuntimeException ex) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						LocalizationProvider.getInstance().getString("reading_err").replace("#$1#",
								filePath.toAbsolutePath().toString()),
						LocalizationProvider.getInstance().getString("error"), JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (tabbedPane.getIndexOfDocumentWithPath(filePath) == -1) {
				tabbedPane.addTab(doc.getFilePath().getFileName().toString(), doc.getTextComponent());
			}
		}
	};

	/**
	 * Action initiated when close current tab option is chosen
	 */
	private final Action closeCurrentTabAction = new LocalizableAction("close", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!saveClosingTab(tabbedPane.getSelectedIndex())) {
				tabbedPane.closeDocument(tabbedPane.getCurrentDocument());
			}
		}
	};

	/**
	 * Action initiated when exit option is chosen
	 */
	private final Action exitAction = new LocalizableAction("exit", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			exitMethod();
		}

	};

	/**
	 * Checks if there are any documents that are not stored and exits the
	 * application.
	 */
	private void exitMethod() {
		boolean cancelFlag = false;

		for (int i = 0; i < tabbedPane.getNumberOfDocuments(); i++) {
			tabbedPane.setSelectedIndex(i);
			cancelFlag = saveClosingTab(i);
		}

		if (!cancelFlag) {
			dispose();
			stopped = true;
		}
	}

	/**
	 * Gives option to save document on the given index. Called before closing a
	 * document.
	 * 
	 * @param i
	 *            index of the document
	 * @return true if the cancel option has been chosen on the file chooser window
	 *         and false otherwise
	 */
	private boolean saveClosingTab(int i) {
		if (tabbedPane.getDocument(i).isModified()) {
			int resultOption = JOptionPane.showConfirmDialog(JNotepadPP.this,
					LocalizationProvider.getInstance().getString("not_saved_msg").replace("#$1#",
							tabbedPane.getTitleAt(i)),
					LocalizationProvider.getInstance().getString("save"), JOptionPane.YES_NO_CANCEL_OPTION);

			if (resultOption == JOptionPane.YES_OPTION) {
				saveDocument(false);
			} else if (resultOption != JOptionPane.NO_OPTION) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Action initiated when the calculate and show statistics option is chosen
	 */
	private final Action showStatisticsAction = new LocalizableAction("stats", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String docText = tabbedPane.getCurrentDocument().getTextComponent().getText();
			int nonBlankChars = docText.replaceAll("\\s+", "").length();
			int lines = docText.length() - docText.replaceAll("\n", "").length();

			JOptionPane.showMessageDialog(JNotepadPP.this, LocalizationProvider.getInstance().getString("info_msg")
					.replace("#$1#", Integer.toString(docText.length()))
					.replace("#$2#", Integer.toString(nonBlankChars)).replace("#$3#", Integer.toString(lines + 1)),
					LocalizationProvider.getInstance().getString("stats"), JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * Action initiated when copying parts of texts
	 */
	private final Action copyAction = new LocalizableAction("copy", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (tabbedPane.getCurrentDocument() == null)
				return;

			JTextArea editor = tabbedPane.getCurrentDocument().getTextComponent();
			editor.copy();
		}
	};

	/**
	 * Action initiated when pasting previously cut/copied text
	 */
	private final Action pasteAction = new LocalizableAction("paste", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (tabbedPane.getCurrentDocument() == null)
				return;
			JTextArea editor = tabbedPane.getCurrentDocument().getTextComponent();
			editor.paste();
		}
	};

	/**
	 * Action initiated when cutting text
	 */
	private final Action cutAction = new LocalizableAction("cut", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (tabbedPane.getCurrentDocument() == null)
				return;
			JTextArea editor = tabbedPane.getCurrentDocument().getTextComponent();
			editor.cut();
		}
	};

	/**
	 * Action initiated when transforming selected text to upper case
	 */
	private final Action toUpperAction = new LocalizableAction("toUpper", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			transformText(c -> Character.toUpperCase(c));
		}
	};

	/**
	 * Action initiated when transforming selected text to lower case
	 */
	private final Action toLowerAction = new LocalizableAction("toLower", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			transformText(c -> Character.toLowerCase(c));
		}
	};

	/**
	 * Action initiated when transforming selected text to opposite case(lower case
	 * to upper and vice versa)
	 */
	private final Action convertLettersAction = new LocalizableAction("convert", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			transformText(c -> Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c));
		}
	};

	/**
	 * Action initiated when sorting selected lines in ascending order
	 */
	private final Action sortAscendingAction = new LocalizableAction("sortAsc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			sortLines(true);
		}
	};

	/**
	 * Action initiated when sorting selected lines in descending order
	 */
	private final Action sortDescendingAction = new LocalizableAction("sortDesc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			sortLines(false);
		}
	};

	/**
	 * Action initiated when filtering duplicate lines from selected set of lines
	 */
	private final Action filterUniqueLinesAction = new LocalizableAction("unique", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JTextArea editor = tabbedPane.getCurrentDocument().getTextComponent();
			Document doc = editor.getDocument();

			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

			int offset = 0;
			if (len != 0) {
				offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			} else {
				len = doc.getLength();
			}

			try {
				int startLine = editor.getLineOfOffset(offset);
				int endLine = editor.getLineOfOffset(offset + len);

				int realOffsetStart = editor.getLineStartOffset(startLine);
				int realOffsetEnd = editor.getLineEndOffset(endLine);
				
				boolean endLineIncluded = false;
				if(realOffsetEnd == editor.getLineEndOffset(editor.getLineCount() - 1)){
					endLineIncluded = true;
				}

				String[] lines = doc.getText(realOffsetStart, realOffsetEnd - realOffsetStart).split("\n");

				Set<String> linesSet = new LinkedHashSet<>(Arrays.asList(lines));

				StringBuilder sb = new StringBuilder();

				int i = 0;
				for (String line : linesSet) {
					sb.append(line);
					if(!(endLineIncluded && i == linesSet.size() - 1)) {
						sb.append("\n");
					}
						
					i++;
				}

				doc.remove(realOffsetStart, realOffsetEnd - realOffsetStart);
				editor.insert(sb.toString(), realOffsetStart);
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}
	};

	/**
	 * Sorts selected lines in ascending or descending order, depending on a given
	 * boolean value
	 * 
	 * @param ascending
	 *            true if ascending sorting is wanted and false for descending
	 *            sorting
	 */
	private void sortLines(boolean ascending) {
		JTextArea editor = tabbedPane.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();

		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

		int offset = 0;
		if (len != 0) {
			offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		} else {
			len = doc.getLength();
		}

		try {
			int startLine = editor.getLineOfOffset(offset);
			int endLine = editor.getLineOfOffset(offset + len);

			int realOffsetStart = editor.getLineStartOffset(startLine);
			int realOffsetEnd = editor.getLineEndOffset(endLine);
			
			boolean endLineIncluded = false;
			if(realOffsetEnd == editor.getLineEndOffset(editor.getLineCount() - 1)){
				endLineIncluded = true;
			}

			String[] lines = doc.getText(realOffsetStart, realOffsetEnd - realOffsetStart).split("\n");

			Locale lang = new Locale(LocalizationProvider.getInstance().getLanguage());
			Collator coll = Collator.getInstance(lang);

			List<String> linesList = Arrays.asList(lines);

			linesList.sort((l1, l2) -> ascending ? coll.compare(l1, l2) : coll.compare(l2, l1));

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < linesList.size(); i++) {
				sb.append(linesList.get(i));
				if(!(endLineIncluded && i == linesList.size() - 1)) {
					sb.append("\n");
				}
			}

			doc.remove(realOffsetStart, realOffsetEnd - realOffsetStart);
			editor.insert(sb.toString(), realOffsetStart);

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Transforms selected text depending on a given function
	 * 
	 * @param func
	 *            determines how selected text will be transformed
	 */
	private void transformText(Function<Character, Character> func) {
		JTextArea editor = tabbedPane.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();

		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

		int offset = 0;
		if (len != 0) {
			offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		} else {
			len = doc.getLength();
		}

		try {
			String text = doc.getText(offset, len);
			text = changeCase(text, func);

			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Changes given text depending on a given function
	 * 
	 * @param text
	 *            text to be modified
	 * @param func
	 *            function used for changing the text
	 * @return transformed text
	 */
	private String changeCase(String text, Function<Character, Character> func) {
		char[] znakovi = text.toCharArray();

		for (int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			znakovi[i] = func.apply(c);
		}

		return new String(znakovi);
	}

	/**
	 * Caret listener which monitors current position of the caret and enables and
	 * disables certain components depending on the situation.
	 */
	private final CaretListener l = e -> {
		JTextArea editor = tabbedPane.getCurrentDocument().getTextComponent();
		Caret c = editor.getCaret();
		int len = Math.abs(c.getDot() - c.getMark());
		sel.setText(Integer.toString(len));

		try {
			ln.setText(Integer.toString(1 + editor.getLineOfOffset(c.getDot())));
			int column = c.getDot() - editor.getLineStartOffset(editor.getLineOfOffset(c.getDot())) + 1;
			col.setText(Integer.toString(column));
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		if (c.getDot() == c.getMark()) {
			toUpperAction.setEnabled(false);
			toLowerAction.setEnabled(false);
			convertLettersAction.setEnabled(false);
			sortAscendingAction.setEnabled(false);
			sortDescendingAction.setEnabled(false);
			filterUniqueLinesAction.setEnabled(false);
		} else {
			toUpperAction.setEnabled(true);
			toLowerAction.setEnabled(true);
			convertLettersAction.setEnabled(true);
			sortAscendingAction.setEnabled(true);
			sortDescendingAction.setEnabled(true);
			filterUniqueLinesAction.setEnabled(true);
		}
	};

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

}
