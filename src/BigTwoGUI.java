import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;


public class BigTwoGUI implements CardGameUI{
	private BigTwo game;//a Big Two card game associates with this GUI.
	private boolean[] selected; //a boolean array indicating which cards are being selected.
	private int activePlayer; // an integer specifying the index of the active player.
	private JFrame frame; //the main window of the application.
	private JPanel bigTwoPanel; // a panel for showing the cards of each player and the cards played on the table
	private JPanel msgNChatPanel;;//a panel for showing msgArea and chatArea
	private JButton playButton; //a Play button for the active player to play the selected cards
	private JButton passButton; // a Pass button for the active player to pass his/her turn to the next player.
	private JTextArea msgArea; // a text area for showing the current game status as well as end of game messages.
	private JTextArea chatArea; //a text area for showing chat messages sent by the players.
	private JTextField chatInput; //a text field for players to input chat messages.
	private JMenuBar menuBar;//a menu bar for players to connect or quit game
	private JMenu gameMenu;//a menu for players to connect or quit game
	private JMenuItem connectMenuItem;//a menu item for players to connect game
	private JMenuItem quitMenuItem;//a menu for players to quit game
	private JScrollPane msgAreaScroll;//enable scrolling of msgArea
	private JScrollPane chatAreaScroll;//enable scrolling of chatArea
	private JPanel bottomPanel;//a bottom panel for user to play/pass/message
	private JLabel chatLabel;//label for text field
	
	
	
	/**
	 * a constructor for creating a BigTwoGUI
	 * @param game is a reference to a Big Two card game associates with this GUI.
	 */
	public BigTwoGUI(BigTwo game) {
		this.game = game;
		
		//----------------------------------------Setting up JMenuBar--------------------------------------------------------
		
		menuBar = new JMenuBar();//setting menu bar on top of frame
		
		gameMenu = new JMenu("Game");
		
		connectMenuItem = new JMenuItem("Connect");//setting up connectMenuItem to be added to gameMenu
		quitMenuItem = new JMenuItem("Quit");//setting up quitMenuItem to be added to gameMenu
		connectMenuItem.addActionListener(new ConnectMenuItemListener());//register connectMenuItem with ActionListener ConnectMenuItemListener()
		quitMenuItem.addActionListener(new QuitMenuItemListener());//register quitMenuItem with ActionListener QuitMenuItemListener()
		
		gameMenu.setMnemonic(KeyEvent.VK_G);//press Alt + G to open "Game" menu
		
		connectMenuItem.setMnemonic(KeyEvent.VK_R);//press R to connect game
		quitMenuItem.setMnemonic(KeyEvent.VK_Q);//press Q to quit game
		
		gameMenu.add(connectMenuItem);//adding connectMenuItem under gameMenu
		gameMenu.add(quitMenuItem);//adding quitMenuItem under gameMenu

		menuBar.add(gameMenu);//add gameMenu to menuBar
		
		//----------------------------------------Setting up msgNChatPanel--------------------------------------------------------
		
		msgNChatPanel = new JPanel();
		msgNChatPanel.setLayout(new GridLayout(2,1));//changing layout of panel from FlowLayout() to GridLayout()
		
		msgArea = new JTextArea();
		chatArea = new JTextArea();
		
		msgArea.setSize(new Dimension(300,250));
		chatArea.setSize(new Dimension(300,250));
		
		msgArea.setEditable(false); // set textArea non-editable
		chatArea.setEditable(false); // set textArea non-editable
		msgArea.setLineWrap(true);
		chatArea.setLineWrap(true);
		
		msgAreaScroll = new JScrollPane(msgArea);//enable scroll
		chatAreaScroll = new JScrollPane(chatArea);//enable scroll
		
		msgAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		msgAreaScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		chatAreaScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		msgNChatPanel.add(msgAreaScroll);
		msgNChatPanel.add(chatAreaScroll);
		
		//----------------------------------------Setting up bottomPanel---------------------------------------------------------
		selected = new boolean[13];
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		
		playButton = new JButton("Play");
		playButton.setFocusable(false);
		
		passButton = new JButton("Pass");
		passButton.setFocusable(false);
		
		chatLabel = new JLabel("Message:");
		
		chatInput = new JTextField();
		chatInput.setPreferredSize(new Dimension(300,20));
		chatInput.addKeyListener(new chatInputListener());//press enter to add message to chatArea
		
		playButton.addActionListener(new PlayButtonListener());
		passButton.addActionListener(new PassButtonListener());
		
		bottomPanel.add(playButton);
		bottomPanel.add(passButton);
		bottomPanel.add(Box.createRigidArea(new Dimension(250,0)));//create spacing between chatInput and passButton and playButton
		bottomPanel.add(chatLabel);
		bottomPanel.add(chatInput);
		
		//----------------------------------------Setting up bigTwoPanel---------------------------------------------------------
		
		bigTwoPanel = new BigTwoPanel();
		bigTwoPanel.setBackground(new Color(1,96,41));//new Color(17,59,8)
		bigTwoPanel.setLayout(null);
		bigTwoPanel.addMouseListener(new BigTwoPanel());

		//----------------------------------------Creating JFrame---------------------------------------------------------
		frame = new JFrame("Big Two");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);//adding menuBar to frame
		frame.add(msgNChatPanel, BorderLayout.EAST);
		frame.add(bottomPanel,BorderLayout.SOUTH);//adding bottomPanel to the bottom of the frame
		frame.add(bigTwoPanel);
		
		ImageIcon image = new ImageIcon("src/cards/j.gif");
		frame.setIconImage(image.getImage());
		
		frame.setMinimumSize(new Dimension(1024,768));//1024x768
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	/**
	 * inner class that implements KeyListener for the JTextField chatInput
	 * @author Winson Sutanto
	 *
	 */
	class chatInputListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ENTER){
				if ( !chatInput.getText().isEmpty()) {//only prints chat if not empty
					//send message of type MSG to server
					game.getBigTwoClient().sendMessage(new CardGameMessage(CardGameMessage.MSG,-1,chatInput.getText()));
					chatInput.setText("");
				}
	        }
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	/**
	 * prints string at the chatArea
	 * @param msg is the string to be printed on chatArea
	 */
	public void printChat(String msg) {
		chatArea.append(msg+"\n");
		
		JScrollBar chatAreaScrollBar = chatAreaScroll.getVerticalScrollBar();
		chatAreaScrollBar.setValue( chatAreaScrollBar.getMaximum() );
	}
	
	/**
	 * a method for setting the index of the active player (i.e., the player having control of the GUI)
	 */
	public void setActivePlayer(int activePlayer) {
		if (activePlayer < 0 || activePlayer >= game.getPlayerList().size()) {
			this.activePlayer = 0;
		} else {
			this.activePlayer = activePlayer;
		}
	}
	
	
	
	/**
	 * a method for repainting the GUI. 
	 */
	public void repaint() {
		bigTwoPanel.repaint();
	}
	
	
	/**
	 * a method for printing the specified string to the message area of the GUI. 
	 * @param msg is the string to be printed
	 */
	public void printMsg(String msg) {
		msgArea.append(msg+"\n");
		JScrollBar msgAreaScrollBar = msgAreaScroll.getVerticalScrollBar();
		msgAreaScrollBar.setValue( msgAreaScrollBar.getMaximum() );
	}
	
	
	/**
	 *  a method for clearing the message area of the GUI
	 */
	public void clearMsgArea() {
		msgArea.setText("");
	}
	
	/**
	 *  a method for clearing the chat area of the GUI
	 */
	public void clearChatArea() {
		chatArea.setText("");
	}
	
	/**
	 * a method for resetting the GUI.(i) reset the list of selected cards; (ii) clear the message and chat area; and (iii) enable user interactions.
	 */
	public void reset() {
		for (int j = 0; j < selected.length; j++) {
			selected[j] = false;
		}
		this.clearMsgArea();
		this.clearChatArea();
		this.enable();
	}
	
	
	/**
	 * a method for enabling user interactions with the GUI. 
	 */
	public void enable() {
		playButton.setEnabled(true);//enable the Play button
		passButton.setEnabled(true);//enable the Pass button
		chatInput.setEditable(true);//enable the chat input;
	}
	
	/**
	 * a method for disabling user interactions with the GUI. 
	 */
	
	public void disable() {
		playButton.setEnabled(false);//disable the Play button
		passButton.setEnabled(false);//disable the Pass button 
		chatInput.setEditable(false);//disable the chat input;
	}
	
	
	/**
	 *  a method for prompting the active player to select cards and make his/her move. A message should be displayed in the message area showing it
	 *  is the active player’s turn.
	 */
	public void promptActivePlayer() {
		printMsg(game.getPlayerList().get(activePlayer).getName() + "'s turn: ");
	}
	
	
	private int[] getSelected() {

		int[] cardIdx = null;
		int count = 0;
		for (int j = 0; j < selected.length; j++) {
			if (selected[j]) {
				count++;
			}
		}

		if (count != 0) {
			cardIdx = new int[count];
			count = 0;
			for (int j = 0; j < selected.length; j++) {
				if (selected[j]) {
					cardIdx[count] = j;
					count++;
				}
			}
		}
		return cardIdx;
	}
	
	
	private void resetSelected() {
		for (int j = 0; j < selected.length; j++) {
			selected[j] = false;
		}
	}
	
	
	/**
	 * an inner class that extends the JPanel class and implements the MouseListener interface.
	 * Overrides the paintComponent() method inherited from the JPanel class to draw the card game table. 
	 * Implements the mouseReleased() method from the MouseListener interface to handle mouse click events. 
	 */
	class BigTwoPanel extends JPanel implements MouseListener{
//		public BigTwoPanel() {
//			this.setBackground(Color.green);//new Color(17,59,8)
//			this.setLayout(new GridLayout(5,1));
//		}
		
		public void paintComponent(Graphics g) {
			boolean flag = false;
			
			//create the cards in hand of players and on the table
			//first divides into the panel into 5 individual grid
			super.paintComponent(g);
			int panelWidth = this.getWidth();
			int panelHeight = this.getHeight();
			//make playerIcons
			Image[] playerIcons = new Image[4];
			playerIcons[0] = new ImageIcon("src/icons8-bmo-48.png").getImage();
			playerIcons[1] = new ImageIcon("src/icons8-finn-48.png").getImage();
			playerIcons[2] = new ImageIcon("src/icons8-ice-king-48.png").getImage();
			playerIcons[3] = new ImageIcon("src/icons8-jake-48.png").getImage();
			
			//make images for the cards
			Image cardBack = new ImageIcon("src/cards/b.gif").getImage();
			Image[][] cards = new Image[4][13];
			String fileLocation = "";
			for (int i= 0 ; i < 4 ; i++) {
				for (int j = 0 ; j < 13 ; j++) {
					if ( i == 0 ) {
						fileLocation = "d";
					}else if ( i == 1 ) {
						fileLocation = "c";
					}else if ( i == 2 ) {
						fileLocation = "h";
					}else if ( i == 3 ) {
						fileLocation = "s";
					}
					if (j == 0) {
						fileLocation = "a" + fileLocation;
					}else if(j == 9) {
						fileLocation = "t"+ fileLocation;
					}else if(j == 10) {
						fileLocation = "j"+ fileLocation;
					}else if(j == 11) {
						fileLocation = "q"+ fileLocation;
					}else if(j ==12) {
						fileLocation = "k"+ fileLocation;
					}else {
						fileLocation = Integer.toString(j+1)+ fileLocation;
					}
					fileLocation = "src/cards/" +fileLocation + ".gif";
					cards[i][j] = new ImageIcon(fileLocation).getImage();
					fileLocation = "";
				}
			}
			g.setFont(new Font( "SansSerif", Font.PLAIN, 18 ));
			// draw lines along the panel separating players
			for (int i = 1 ; i < 5 ; i++) {
				g.drawLine(0, i * panelHeight/5, panelWidth, i * panelHeight/5);
			}
			for ( int i = 0 ; i < 4 ; i++) {
				if (game.getPlayerList().get(i).getName() == null) {
					flag = true;
				}
			}
			if (flag == true){
				//make player name and playersIcon
				for (int i = 0 ; i < game.getNumOfPlayers() ; i++) {
					if (game.getPlayerList().get(i).getName() != null ) {
						g.drawString(game.getPlayerList().get(i).getName(), panelWidth/100 , panelHeight/35 + i*panelHeight/5);
						g.drawImage(playerIcons[i],panelWidth/100, panelHeight/20 + i*panelHeight/5, this);
					}
				}
			}else {
			//make player name and playersIcon
				for (int i = 0 ; i < 5 ; i++) {
					if (i < 4 ) {
						g.drawString(game.getPlayerList().get(i).getName(), panelWidth/100 , panelHeight/35 + i*panelHeight/5);
						g.drawImage(playerIcons[i],panelWidth/100, panelHeight/20 + i*panelHeight/5, this);
					}else {
						if (game.getHandsOnTable().isEmpty()) {
							g.drawString("Empty",  panelWidth/100 , panelHeight/35 + i * panelHeight/5 );
						}else {
							g.drawString("Played by " + game.getHandsOnTable().get(game.getHandsOnTable().size()-1).getPlayer().getName(), panelWidth/100,  panelHeight/35 + i * panelHeight/5);
						}
					}
				}
				
				//draw the cards
				for (int i = 0 ; i < 5; i++) {
					if (i < 4 && !(i == game.getBigTwoClient().getPlayerID())){
						CardList currentPlayerHand = game.getPlayerList().get(i).getCardsInHand();
						for (int j = 0 ; j < currentPlayerHand.size() ; j++) {
							g.drawImage(cardBack, (j+3)* panelWidth/30, panelHeight/20 + i * panelHeight/5,this);
						}
					}else if(i == game.getBigTwoClient().getPlayerID()) {
						CardList currentPlayerHand = game.getPlayerList().get(i).getCardsInHand();
						for (int j = 0 ; j < currentPlayerHand.size() ; j++) {
							Image card = cards[currentPlayerHand.getCard(j).getSuit()][currentPlayerHand.getCard(j).getRank()];
							if (selected[j] == true) {
								g.drawImage(card,  (j+3)* panelWidth/30, panelHeight/20 + i * panelHeight/5 - 10,this);
							}else {
								g.drawImage(card,  (j+3)* panelWidth/30, panelHeight/20 + i * panelHeight/5,this);
							}
						}
					}else {
						if (game.getHandsOnTable().isEmpty()) {
							break;
						}else {
							CardList handOnTable = game.getHandsOnTable().get(game.getHandsOnTable().size()-1);
							for (int j = 0 ; j < handOnTable.size() ; j++) {
								Image card = cards[handOnTable.getCard(j).getSuit()][handOnTable.getCard(j).getRank()];
								g.drawImage(card, (j+1)*panelWidth/30, panelHeight/20 + i* panelHeight/5,this);
							}
						}
					}
				}
			}
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if(!game.endOfGame()) {
				int x = e.getX();
				int y = e.getY();
				int panelWidth = bigTwoPanel.getWidth();
				int panelHeight = bigTwoPanel.getHeight();
				game.getPlayerList().get(activePlayer);
				CardList activeHand = game.getPlayerList().get(activePlayer).getCardsInHand();
				for (int i = 0 ; i < activeHand.size() ; i++) {
					if (selected[i]) {
						if (i != activeHand.size()-1) {
							if (y > panelHeight/20 + activePlayer*panelHeight/5 - 10  && y < panelHeight/20 + activePlayer*panelHeight/5 + 86) {
								if ( x < (i+3)* panelWidth/30 + panelWidth/30 && x > (i+3)* panelWidth/30) {
									selected[i] = !selected[i];
								}
							}
						}else {
							if (y > panelHeight/20 + activePlayer*panelHeight/5 - 10  && y < panelHeight/20 + activePlayer*panelHeight/5 + 86) {
								if ( x < (i+3)* panelWidth/30 + 72 && x > (i+3)* panelWidth/30) {
									selected[i] = !selected[i];
								}
							}
						}
					}else {
						if (i != activeHand.size()-1) {
							if (y > panelHeight/20 + activePlayer*panelHeight/5 && y < panelHeight/20 + activePlayer*panelHeight/5 + 96) {
								if (x < (i+3)* panelWidth/30 + panelWidth/30 && x > (i+3)* panelWidth/30) {
									selected[i] = !selected[i];
								}
							}
						}else {
							if (y > panelHeight/20 + activePlayer*panelHeight/5 && y < panelHeight/20 + activePlayer*panelHeight/5 + 96) {
								if (x < (i+3)* panelWidth/30 + 72 && x > (i+3)* panelWidth/30) {
									selected[i] = !selected[i];
								}
							}
						}
					}
				}
			}
			bigTwoPanel.repaint();
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	/**
	 * an inner class that implements the ActionListener interface
	 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the Play button.
	 */
	class PlayButtonListener implements ActionListener{ 
		public void actionPerformed(ActionEvent event) {
			if (getSelected() != null) {
				game.makeMove(activePlayer, getSelected());
				resetSelected();
				bigTwoPanel.repaint();
			}
			
		}
	}
	
	
	/**
	 * an inner class that implements the ActionListener interface.
	 * Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the Pass button.
	 */
	class PassButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			game.makeMove(activePlayer, null);
			bigTwoPanel.repaint();
		}
	}
	
	
	/**
	 * an inner class that implements the ActionListener interface.
	 *  Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the Connect menu item.
	 */
	class ConnectMenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			game.getBigTwoClient().connect();
		}
	}
	
	
	/**
	 * an inner class that implements the ActionListener interface.
	 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the Quit menu item.
	 */
	class QuitMenuItemListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}
}
