package controller;

import view.MainView;
import javax.swing.*;
import java.awt.event.*;

public class GameGUI implements ActionListener { 
	private MainView mainView;

	public GameGUI() {
		mainView = new MainView(this);
		//mainView.setButtonListener(this);
		mainView.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GameGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		if(command.equals("play")) {
			
			mainView.playerOneNameForm();
		}
		if(command.equals("submitPlayerOneName")) {
			String name = ((JTextField) e.getSource()).getText();
			System.out.println(name);
		}
	}
}
