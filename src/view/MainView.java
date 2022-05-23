package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;

public class MainView extends JFrame {
	private JTextArea title;
	
	public MainView() {
		
		setTitle("Marvel: Ultimate War");

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setBounds(50, 50, 800, 600);

		title = new JTextArea();
	
		title.setPreferredSize(new Dimension(200, getHeight()));
		
		//title.insert("Welcome", DISPOSE_ON_CLOSE);
		
		title.setEditable(false);
		
		title.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		
		add(title, BorderLayout.EAST);

	}
}
