package controller;

import view.MainView;
import view.ViewListener;

import javax.swing.*;

import engine.Game;
import engine.Player;
import model.effects.Effect;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;
import model.world.Hero;
import model.world.Villain;

import java.awt.*;

import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.awt.BorderLayout;

public class GameGUI implements ActionListener, ViewListener { 
	private MainView mainView;
	private Player playerOne;
	private Player playerTwo;
	private Game game;

	public GameGUI() {
		mainView = new MainView(this,this);
		//mainView.setLayout(new BoxLayout(mainView,BoxLayout.Y_AXIS));
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
	}

	@Override
	public void onPlayerOneName(String name) {
		// TODO Auto-generated method stub
		playerOne = new Player(name);
		
	}

	@Override
	public void onPlayerTwoName(String name) {
		// TODO Auto-generated method stub
		playerTwo = new Player(name);
		setupGame();
	}
	
	public void setupGame() {
		try {
			Game.loadAbilities("Abilities.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Game.loadChampions("Champions.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playerOneSelection();
	
	}
	public void playerOneSelection() {
		JLabel question = new JLabel("Player 1: Please select a champion");
		JPanel selection = new JPanel();
		question.setAlignmentX(selection.CENTER_ALIGNMENT);
		selection.setLayout(new GridLayout(5, 3));
		
		for(Champion c : Game.getAvailableChampions()) {
			boolean validChampion = true;
			for(Champion c1: playerOne.getTeam()) {
				if (c1.getName().equals(c.getName())){
					validChampion = false;
				}
			}
			for(Champion c1: playerTwo.getTeam()) {
				if (c1.getName().equals(c.getName())){
					validChampion = false;
				}
			}
			if(playerOne.getTeam().size() == 3) {
				playerOneSelectionLeader();
				return;
			}
			if(validChampion) {
				JPanel champion = new JPanel();
				JLabel name = new JLabel(c.getName());
				JButton addChampion = new JButton("add");
				addChampion.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	
		            	playerOne.getTeam().add(c);
		            	mainView.remove(question);
		        		mainView.revalidate();
		        		mainView.repaint();
		            	mainView.remove(selection);
		        		mainView.revalidate();
		        		mainView.repaint();
		            	playerOneSelection();
		            }
		        });
				champion.add(addChampion);
				champion.add(name);
				selection.add(champion);
			}

		}
		mainView.add(question);
		mainView.revalidate();
		mainView.repaint();
		mainView.add(selection);
		mainView.revalidate();
		mainView.repaint();
	}
	
	public void playerTwoSelection() {
		JLabel question = new JLabel("Player 2: Please select a champion");
		JPanel selection = new JPanel();
		question.setAlignmentX(selection.CENTER_ALIGNMENT);
		selection.setLayout(new GridLayout(5, 3));
		
		for(Champion c : Game.getAvailableChampions()) {
			boolean validChampion = true;
			for(Champion c1: playerOne.getTeam()) {
				if (c1.getName().equals(c.getName())){
					validChampion = false;
				}
			}
			for(Champion c1: playerTwo.getTeam()) {
				if (c1.getName().equals(c.getName())){
					validChampion = false;
				}
			}
			if(playerTwo.getTeam().size() == 3) {
	
				playerTwoSelectionLeader();
				return;
			}
			if(validChampion) {
				JPanel champion = new JPanel();
				JLabel name = new JLabel(c.getName());
				JButton addChampion = new JButton("add");
				addChampion.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		      
		            	playerTwo.getTeam().add(c);
		            	mainView.remove(question);
		        		mainView.revalidate();
		        		mainView.repaint();
		            	mainView.remove(selection);
		        		mainView.revalidate();
		        		mainView.repaint();
		            	playerTwoSelection();
		            }
		        });
				champion.add(addChampion);
				champion.add(name);
				selection.add(champion);
			}

		}
		mainView.add(question);
		mainView.revalidate();
		mainView.repaint();
		mainView.add(selection);
		mainView.revalidate();
		mainView.repaint();
	}
	
	public void playerOneSelectionLeader() {
		JLabel question = new JLabel("Player 1: Please select your leader");
		JPanel selection = new JPanel();
		question.setAlignmentX(selection.CENTER_ALIGNMENT);
		selection.setLayout(new GridLayout(5, 3));
		
		for(Champion c : playerOne.getTeam()) {
			
				JPanel champion = new JPanel();
				JLabel name = new JLabel(c.getName());
				JButton addChampion = new JButton("make leader");
				addChampion.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		      
		            	playerOne.setLeader(c);
		            	mainView.remove(question);
		        		mainView.revalidate();
		        		mainView.repaint();
		            	mainView.remove(selection);
		        		mainView.revalidate();
		        		mainView.repaint();
		            	playerTwoSelection();
		            }
		        });
				champion.add(addChampion);
				champion.add(name);
				selection.add(champion);
			

		}
		mainView.add(question);
		mainView.revalidate();
		mainView.repaint();
		mainView.add(selection);
		mainView.revalidate();
		mainView.repaint();
	}
	
	public void playerTwoSelectionLeader() {
		JLabel question = new JLabel("Player 2: Please select your leader");
		JPanel selection = new JPanel();
		question.setAlignmentX(selection.CENTER_ALIGNMENT);
		selection.setLayout(new GridLayout(5, 3));
		
		for(Champion c : playerTwo.getTeam()) {
			
				JPanel champion = new JPanel();
				JLabel name = new JLabel(c.getName());
				JButton addChampion = new JButton("make leader");
				addChampion.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		      
		            	playerTwo.setLeader(c);
		            	mainView.remove(question);
		        		mainView.revalidate();
		        		mainView.repaint();
		            	mainView.remove(selection);
		        		mainView.revalidate();
		        		mainView.repaint();
		        		startGame();
		            	return;
		            }
		        });
				champion.add(addChampion);
				champion.add(name);
				selection.add(champion);
			

		}
		mainView.add(question);
		mainView.revalidate();
		mainView.repaint();
		mainView.add(selection);
		mainView.revalidate();
		mainView.repaint();
	}
	
	public JPanel generateRemainingChamp(Champion c) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel name = new JLabel("Name: " + c.getName());
		panel.add(name);
		JLabel hp = new JLabel("Current HP: " + c.getCurrentHP());
		panel.add(hp);
		JLabel mana = new JLabel("Mana: " + c.getMana());
		panel.add(mana);
		JLabel speed = new JLabel("Speed: " + c.getSpeed());
		panel.add(speed);
		JLabel actions= new JLabel("Max Actions Per Turn: " + c.getMaxActionPointsPerTurn());
		panel.add(actions);
		JLabel attackDamage= new JLabel("Attack Damage: " + c.getAttackDamage());
		panel.add(attackDamage);
		JLabel attackRange = new JLabel("Attack Range: " + c.getAttackRange());
		panel.add(attackRange);
		String type = "";
		if(c instanceof Hero) {
			type = "Hero";
		}
		else if(c instanceof Villain) {
			type = "Villain";
		}
		else if(c instanceof AntiHero) {
			type = "AntiHero";
		}
		JLabel typeName = new JLabel("Type: " + type);
		panel.add(typeName);
		
		for(Champion c1 : playerOne.getTeam()) {
			if (c1 == playerOne.getLeader() && c1 == c){
				JLabel leader = new JLabel("Leader");
				panel.add(leader);
			}
		}
		for(Champion c1 : playerTwo.getTeam() ) {
			if (c1 == playerTwo.getLeader() && c1 == c){
				JLabel leader = new JLabel("Leader");
				panel.add(leader);
			}
		}
		if(!c.getAppliedEffects().isEmpty()) {
			JLabel effectsList = new JLabel("Effects;");
			panel.add(effectsList);
			
			for(Effect e: c.getAppliedEffects()) {
				JLabel effectDetails = new JLabel(e.getName() + ", " + e.getDuration());
				panel.add(effectDetails);
				
			}
		}
		return panel;

	}
	
	public JPanel board() {
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(5,5,5,5));
		for(int i = 0;i<5;i++) {
			for(int j =0; j<5;j++) {
				JPanel p = new JPanel();
				p.setBorder(BorderFactory.createLineBorder(Color.black));
				if(game.getBoard()[i][j] != null) {
					if(game.getBoard()[i][j] instanceof Champion) {
						Champion c = (Champion) game.getBoard()[i][j];
						JLabel name = new JLabel(c.getName());
						p.add(name);
					}
					if(game.getBoard()[i][j] instanceof Cover) {
						Cover c = (Cover) game.getBoard()[i][j];
						JLabel name = new JLabel(c.getCurrentHP()+"");
						p.add(name);
					}
				}
				board.add(p);
				board.revalidate();
				board.repaint();
			}
		}
		return board;
	}
	
	public JPanel currentTurn() {
		JPanel currentTurn = new JPanel();
		currentTurn.setLayout(new BoxLayout(currentTurn,BoxLayout.Y_AXIS));
		boolean firstPlayer = false;
		for(Champion c: game.getFirstPlayer().getTeam()) {
			if(c == game.getCurrentChampion()) {
				firstPlayer = true;
			}
		}
		String name;
		if(firstPlayer) {
			name = game.getFirstPlayer().getName();
		}
		else {
			name = game.getSecondPlayer().getName();
		}
		JLabel info = new JLabel("Current Turn: " + name);
		currentTurn.add(info);
		return currentTurn;
	}
	
	public void startGame() {
		this.game = new Game(playerOne,playerTwo);
		
		mainView.setLayout(new BorderLayout());
		
		JPanel playerOnePanel = new JPanel();
		JLabel playerOneName = new JLabel("Player 1:" + playerOne.getName());
		playerOnePanel.add(playerOneName);
		playerOnePanel.revalidate();
		playerOnePanel.repaint();
		
		for(Champion c : playerOne.getTeam()) {
			JPanel p = generateRemainingChamp(c);
			playerOnePanel.add(p);
			playerOnePanel.revalidate();
			playerOnePanel.repaint();
		}
		
		mainView.add(playerOnePanel, BorderLayout.PAGE_START);
		mainView.revalidate();
		mainView.repaint();
		
		JPanel playerTwoPanel = new JPanel();
		JLabel playerTwoName = new JLabel("Player 2:" + playerTwo.getName());
		playerTwoPanel.add(playerTwoName);
		playerTwoPanel.revalidate();
		playerTwoPanel.repaint();
		
		for(Champion c : playerTwo.getTeam()) {
			JPanel p = generateRemainingChamp(c);
			playerTwoPanel.add(p);
			playerTwoPanel.revalidate();
			playerTwoPanel.repaint();
		}
		
		mainView.add(playerTwoPanel, BorderLayout.PAGE_END);
		mainView.revalidate();
		mainView.repaint();
		
		JPanel board = board();
		
		mainView.add(board, BorderLayout.CENTER);
		mainView.revalidate();
		mainView.repaint();
		
		JPanel turnOrder = new JPanel();
		turnOrder.setLayout(new BoxLayout(turnOrder,BoxLayout.Y_AXIS));
		JLabel current = new JLabel("Current: " + game.getCurrentChampion().getName());
		turnOrder.add(current);
		if(game.getTurnOrder().size()>1) {
			Champion c = game.getCurrentChampion();
			game.getTurnOrder().remove();
			JLabel next = new JLabel("Next: " + game.getCurrentChampion().getName());
			turnOrder.add(next);
			game.getTurnOrder().insert(c);
		}
		
		mainView.add(turnOrder, BorderLayout.LINE_END);
		mainView.revalidate();
		mainView.repaint();
		
		JPanel currentTurn = currentTurn();
		
		mainView.add(currentTurn, BorderLayout.LINE_START);
		mainView.revalidate();
		mainView.repaint();
		
	}
}
