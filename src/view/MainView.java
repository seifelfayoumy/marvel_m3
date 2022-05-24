package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.event.*;

import javax.swing.*;

public class MainView extends JFrame {
	private JLabel title;
	private JButton playButton;
	private JPanel menuPanel;
	private ActionListener buttonListener;
	
	public void setButtonListener(ActionListener buttonListener) {
		this.buttonListener = buttonListener;
	}
	public MainView(ActionListener al) {
		this.buttonListener = al;
		
		setTitle("Marvel: Ultimate War");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(50, 50, 800, 600);
		//setResizable(false);
		menuPanel = new JPanel();

		title = new JLabel("Welcome to Marvel: Ultimate War", JLabel.CENTER);
		title.setAlignmentX(menuPanel.CENTER_ALIGNMENT);
		
		playButton = new JButton("Play");
		playButton.setAlignmentX(menuPanel.CENTER_ALIGNMENT);
		playButton.setActionCommand("play");
		playButton.addActionListener(this.buttonListener);
		
	
		playButton.setPreferredSize(new Dimension(40,40));
		
		
		//menuPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.Y_AXIS));
		menuPanel.add(title);
		menuPanel.add(playButton);
		
		//menuPanel.validate();
		//menuPanel.repaint();
		
		add(menuPanel);
	}
	public void playerOneNameForm() {
		this.menuPanel.setVisible(false);
		
		JPanel playerNamePanel = new JPanel();
		JLabel question = new JLabel("Player 1: Please Enter Your Name.", JLabel.CENTER);
		JButton submitButton = new JButton("submit");
		question.setAlignmentX(playerNamePanel.CENTER_ALIGNMENT);
		JTextField answer = new JTextField();
		answer.setAlignmentX(playerNamePanel.CENTER_ALIGNMENT);
		answer.setPreferredSize(new Dimension(150,20));
		submitButton.setActionCommand("submitPlayerOneName");
		submitButton.addActionListener(this.buttonListener);
		
		//playerNamePanel.setLayout(new BoxLayout(playerNamePanel,BoxLayout.Y_AXIS));
		playerNamePanel.add(question);
		playerNamePanel.add(answer);
		playerNamePanel.add(submitButton);
		playerNamePanel.setVisible(true);
		this.add(playerNamePanel);
		
		
	}
}
