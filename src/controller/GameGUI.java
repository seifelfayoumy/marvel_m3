package controller;

import view.MainView;

public class GameGUI {
	private MainView mainView;

	public GameGUI() {
		mainView = new MainView();
		mainView.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GameGUI();
	}
}
