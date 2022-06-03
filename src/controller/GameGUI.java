package controller;

import view.MainView;
import view.ViewListener;

import javax.swing.*;

import engine.Game;
import engine.Player;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Effect;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;
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
//		mainView.setExtendedState(JFrame.MAXIMIZED_BOTH); 
//		mainView.setUndecorated(true);
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
			JOptionPane.showMessageDialog(mainView,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
		}
		try {
			Game.loadChampions("Champions.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(mainView,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
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
		for(int i = 4;i>=0;i--) {
			for(int j =0; j<5;j++) {
				JPanel p = new JPanel();
				p.setBorder(BorderFactory.createLineBorder(Color.black));
				//p.add(new JLabel("" + i + " "+ j));			
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
	
	public JPanel generateEffect(Effect e) {
		JPanel effect = new JPanel();
		effect.setLayout(new BoxLayout(effect,BoxLayout.Y_AXIS));
		effect.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel name = new JLabel("Effect Name: " + e.getName());
		effect.add(name);
		JLabel duration = new JLabel("Duration: " + e.getDuration());
		effect.add(duration);
		
		return effect;
	}
	
	public JPanel currentTurn() {
		JPanel currentTurn = new JPanel();
		JPanel allTurn = new JPanel();
		currentTurn.setLayout(new BoxLayout(currentTurn,BoxLayout.Y_AXIS));
		allTurn.setLayout(new BorderLayout());
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
		
		JPanel currentChamp = generateCurrentChamp(game.getCurrentChampion());
		currentTurn.add(currentChamp);
		
		
		JLabel abilities = new JLabel("Abilities:");
		currentTurn.add(abilities);
		for(Ability a: game.getCurrentChampion().getAbilities()) {
			JPanel ability = generateAbility(a);
			currentTurn.add(ability);
		}
		allTurn.add(currentTurn,BorderLayout.LINE_START);
		
		JPanel effects = new JPanel();
		effects.setLayout(new BoxLayout(effects,BoxLayout.Y_AXIS));
		
		JLabel effectsLabel = new JLabel("Effects(if any):");
		effects.add(effectsLabel);
		for(Effect e: game.getCurrentChampion().getAppliedEffects()) {
			JPanel effect = generateEffect(e);
			effects.add(effect);
		}
		allTurn.add(effects,BorderLayout.LINE_END);
		
		
		return allTurn;
	}
	
	public JPanel generateMoveButtons() {
		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls,BoxLayout.Y_AXIS));
		
		JButton up = new JButton("Move Up");
		controls.add(up);
		up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					game.move(Direction.UP);
					mainView.getContentPane().removeAll();
					mainView.revalidate();
					mainView.repaint();
					generateGameView();
				} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);  
				}
            }
        });
		JButton down = new JButton("Move Down");
		controls.add(down);
		down.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					game.move(Direction.DOWN);
					mainView.getContentPane().removeAll();
					mainView.revalidate();
					mainView.repaint();
					generateGameView();
				} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
				}
            }
        });
		JButton right = new JButton("Move Right");
		controls.add(right);
		right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					game.move(Direction.RIGHT);
					mainView.getContentPane().removeAll();
					mainView.revalidate();
					mainView.repaint();
					generateGameView();
				} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
				}
            }
        });
		JButton left = new JButton("Move Left");
		controls.add(left);
		left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					game.move(Direction.LEFT);
					mainView.getContentPane().removeAll();
					mainView.revalidate();
					mainView.repaint();
					generateGameView();
				} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
				}
            }
        });
		return controls;
	}
	
	public JPanel generateAttackButtons() {
		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls,BoxLayout.Y_AXIS));
		
		JButton up = new JButton("Attack Up");
		controls.add(up);
		up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
					try {
						game.attack(Direction.UP);
						mainView.getContentPane().removeAll();
						mainView.revalidate();
						mainView.repaint();
						generateGameView();
					} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
					}
            }
        });
		JButton down = new JButton("Attack Down");
		controls.add(down);
		down.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				try {
					game.attack(Direction.DOWN);
					mainView.getContentPane().removeAll();
					mainView.revalidate();
					mainView.repaint();
					generateGameView();
				} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
				}
            }
        });
		JButton right = new JButton("Attack Right");
		controls.add(right);
		right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				try {
					game.attack(Direction.RIGHT);
					mainView.getContentPane().removeAll();
					mainView.revalidate();
					mainView.repaint();
					generateGameView();
				} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
				}
            }
        });
		JButton left = new JButton("Attack Left");
		controls.add(left);
		left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				try {
					game.attack(Direction.LEFT);
					mainView.getContentPane().removeAll();
					mainView.revalidate();
					mainView.repaint();
					generateGameView();
				} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
				}
            }
        });
		return controls;
	}
	
	public JPanel generateCurrentChamp(Champion c) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel name = new JLabel("Name: " + c.getName());
		panel.add(name);
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
		JLabel hp = new JLabel("Current HP: " + c.getCurrentHP());
		panel.add(hp);
		JLabel mana = new JLabel("Mana: " + c.getMana());
		panel.add(mana);
		JLabel actions= new JLabel("Action Points: " + c.getCurrentActionPoints());
		panel.add(actions);
		JLabel attackDamage= new JLabel("Attack Damage: " + c.getAttackDamage());
		panel.add(attackDamage);
		JLabel attackRange = new JLabel("Attack Range: " + c.getAttackRange());
		panel.add(attackRange);
		


		
		return panel;

	}
	
	public JPanel generateAbility(Ability c) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel name = new JLabel("Name: " + c.getName());
		panel.add(name);
		String type = "";
		JLabel amount = new JLabel();
		if(c instanceof DamagingAbility) {
			type = "Damaging Ability";
			amount = new JLabel("Damage Amount: " + ((DamagingAbility) c).getDamageAmount());
		}
		else if(c instanceof HealingAbility) {
			type = "Healing Ability";
			amount = new JLabel("Heal Amount: " + ((HealingAbility) c).getHealAmount());
		}
		else if(c instanceof CrowdControlAbility) {
			type = "Crowd Control Ability";
			amount = new JLabel("Effect: " + ((CrowdControlAbility) c).getEffect().getName());
		}
		JLabel typeName = new JLabel("Type: " + type);
		panel.add(typeName);
		JLabel hp = new JLabel("Area of Effect: " + c.getCastArea().toString());
		panel.add(hp);
		JLabel mana = new JLabel("Range: " + c.getCastRange());
		panel.add(mana);
		JLabel actions= new JLabel("Mana Cost: " + c.getManaCost());
		panel.add(actions);
		JLabel attackDamage= new JLabel("Current Cooldown: " + c.getCurrentCooldown());
		panel.add(attackDamage);
		JLabel attackRange = new JLabel("Base Cooldown: " + c.getBaseCooldown());
		panel.add(attackRange);
		

		panel.add(amount);
		
		if(c.getCastArea() == AreaOfEffect.SELFTARGET || c.getCastArea() == AreaOfEffect.TEAMTARGET || c.getCastArea() == AreaOfEffect.SURROUND) {
			JButton cast = new JButton("Cast Ability");
			panel.add(cast);
			cast.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	try {
						game.castAbility(c);
						mainView.getContentPane().removeAll();
						mainView.revalidate();
						mainView.repaint();
						generateGameView();
					} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
					}
	            }
	        });
		}
		if(c.getCastArea() == AreaOfEffect.DIRECTIONAL) {
			JButton cast = new JButton("Cast Ability Up");
			panel.add(cast);
			cast.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	try {
						game.castAbility(c,Direction.UP);
						mainView.getContentPane().removeAll();
						mainView.revalidate();
						mainView.repaint();
						generateGameView();
					} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
					}
	            }
	        });
			JButton castDown = new JButton("Cast Ability Down");
			panel.add(castDown);
			castDown.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	try {
						game.castAbility(c,Direction.DOWN);
						mainView.getContentPane().removeAll();
						mainView.revalidate();
						mainView.repaint();
						generateGameView();
					} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
					}
	            }
	        });
			JButton castRight = new JButton("Cast Ability Right");
			panel.add(castRight);
			castRight.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	try {
						game.castAbility(c,Direction.RIGHT);
						mainView.getContentPane().removeAll();
						mainView.revalidate();
						mainView.repaint();
						generateGameView();
					} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
					}
	            }
	        });
			JButton castLeft = new JButton("Cast Ability Left");
			panel.add(castLeft);
			castLeft.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	try {
						game.castAbility(c,Direction.LEFT);
						mainView.getContentPane().removeAll();
						mainView.revalidate();
						mainView.repaint();
						generateGameView();
					} catch (NotEnoughResourcesException | AbilityUseException | CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
					}
	            }
	        });
		}
		if(c.getCastArea() == AreaOfEffect.SINGLETARGET) {
			JButton cast = new JButton("Cast Ability");
			panel.add(cast);
			cast.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	String location = JOptionPane.showInputDialog(mainView,"Enter the location of the target(ex: if the target is located in the top right corner, right 44. And if its located in the top left corner, right 40)");  
	            	if (location == null || !Character.isDigit(location.charAt(0)) || !Character.isDigit(location.charAt(1))){
	            		JOptionPane.showMessageDialog(mainView,"please enter a valid location","Invalid",JOptionPane.ERROR_MESSAGE); 
	            		return;
	            	}
	            	
	            	int locationH = Integer.parseInt(location.charAt(0)+"");
	              	int locationW = Integer.parseInt(location.charAt(1)+"");
	              	boolean hValid = locationH >=0 && locationH <= 4;
	              	boolean wValid = locationW >=0 && locationW <= 4;
	              	if(location.length() != 2 || !hValid || !wValid) {
	              		JOptionPane.showMessageDialog(mainView,"please enter a valid location","Invalid",JOptionPane.ERROR_MESSAGE); 
	              		return;
	              	}
	            	
						try {
							game.castAbility(c,locationH,locationW);
							mainView.getContentPane().removeAll();
							mainView.revalidate();
							mainView.repaint();
							generateGameView();
						} catch (NotEnoughResourcesException | AbilityUseException | InvalidTargetException
								| CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
						}
	            }
	        });
		}

		
		return panel;

	}
	
	public void checkGameOver() {
		if(game.checkGameOver()!= null) {
			Player winner = game.checkGameOver();
			JOptionPane.showMessageDialog(mainView,winner.getName()+ " Won!  (click OK to quit the game)","GAME OVER!",JOptionPane.OK_OPTION);
			System.exit(0);
		}
	}
	
	public void startGame() {
		this.game = new Game(playerOne,playerTwo);
		
		generateGameView();
	}
	public void generateGameView() {
		checkGameOver();
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
		turnOrder.add(generateMoveButtons());
		turnOrder.add(generateAttackButtons());
		JButton useLeader = new JButton("Use Leader Ability");
		turnOrder.add(useLeader);
		useLeader.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
					try {
						game.useLeaderAbility();
						mainView.getContentPane().removeAll();
						mainView.revalidate();
						mainView.repaint();
						generateGameView();
					} catch (LeaderNotCurrentException | LeaderAbilityAlreadyUsedException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(mainView,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE); 
					}
            }
        });
		JButton endTurn = new JButton("EndTurn");
		turnOrder.add(endTurn);
		endTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	game.endTurn();
				mainView.getContentPane().removeAll();
				mainView.revalidate();
				mainView.repaint();
				generateGameView();
            }
        });
		
		JScrollPane paneOrder = new JScrollPane(turnOrder);
		mainView.add(paneOrder, BorderLayout.LINE_END); 
		mainView.revalidate();
		mainView.repaint();
		
		JPanel currentTurn = currentTurn();

		JScrollPane pane = new JScrollPane(currentTurn);
		mainView.add(pane, BorderLayout.LINE_START);
		mainView.revalidate();
		mainView.repaint();
		
	}
}
