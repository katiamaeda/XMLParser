package com.katiamaeda;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Window extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static private final String newline = "\n";

	private JTextArea log;
	private JFileChooser fc;
	private JButton openButton;
	private JPanel panel;
	private JTextField txtOutput;
	private JButton btnNewButton;
	private File[] files;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE); 
				createAndShowGUI();
			}
		});
	}

	public Window() {
		super(new BorderLayout());

		log = new JTextArea(5,20);
		log.setMargin(new Insets(5,5,5,5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		//Create a file chooser
		fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);

		panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		openButton = new JButton("Select files");
		openButton.addActionListener(this);

		txtOutput = new JTextField();
		txtOutput.setText("OutputFile.xls");
		txtOutput.setColumns(10);

		btnNewButton = new JButton("Create");
		btnNewButton.addActionListener(this);

		panel.add(openButton);
		panel.add(txtOutput);
		panel.add(btnNewButton);
		
		add(logScrollPane, BorderLayout.CENTER);
	}

	private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame("FileChooserDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Add content to the window.
		frame.getContentPane().add(new Window());

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(Window.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				files = fc.getSelectedFiles();
			} 

			log.setCaretPosition(log.getDocument().getLength());
		} else if (e.getSource() == btnNewButton) {
			if (files == null) return;
			
			Parser parser = new Parser();
			String message = parser.parseXmlFile(files, txtOutput.getText());

			log.append(message + newline);
		}
	}
}
