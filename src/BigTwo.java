import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;
/**
 * This class is used to start the BigTwo card game.
 * @author Winson Sutanto
 *
 */
public class BigTwo implements CardGame{
	
	private static int numOfPlayers = 4;//the number of players in a BigTwo game
	private static Deck deck;//a deck of cards.
	private ArrayList<CardGamePlayer> playerList = new ArrayList<CardGamePlayer>();//ArrayList of players that will be playing the BigTwo game.
	private ArrayList<Hand> handsOnTable = new ArrayList<Hand>();//ArrayList of the valid hands played on the board.
	private int currentPlayerIdx;//an integer specifying the index of the current player.
	private BigTwoGUI gui;// a BigTwoUI object for providing the user interface.
	private BigTwoClient bigTwoClient;//client for bigTwo

	
	/**
	 * main program. Creates an BigTwo object and calls start() to start the game. Prints the players and how many cards they have left when game ends.
	 * @param args is not used.
	 */
	public static void main(String[] args) {
		BigTwo bigTwoGame = new BigTwo();//create object of BigTwo
		

	}
	/**
	 * Checks the validity of the hand the active player played.
	 * @param player is the current active player in the game.
	 * @param cards is the hand the active player played.
	 * @return null if the hand is not valid. Returns Hand if the hand played is valid.
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards) {
		//full house
		FullHouse fullHouse = new FullHouse(player,cards);
		if (fullHouse.isValid()) {
			return fullHouse;
		}
		//straight flush
		StraightFlush straightFlush = new StraightFlush(player,cards);
			if (straightFlush.isValid()) {
				return straightFlush;
			}
		//flush
		Flush flush = new Flush(player,cards);
		if (flush.isValid()) {
			return flush;
		}
		//straight
		Straight straight = new Straight(player,cards);
		if (straight.isValid()) {
			return straight;
		}
		//Quad
		Quad quad = new Quad(player,cards);
		if (quad.isValid()) {
			return quad;
		}
		//triple
		Triple triple = new Triple(player,cards);
		if (triple.isValid()) {
			return triple;
		}
		//pair
		Pair pair = new Pair(player,cards);
		if (pair.isValid()) {
			return pair;
		}
		//single
		Single single = new Single(player,cards);
		if (single.isValid()) {
			return single;
		}
		return null;
	}
	/**
	 * getter function for bigTwoClient
	 * 
	 */
	public BigTwoClient getBigTwoClient() {
		return this.bigTwoClient;
	}
	
	/**
	 * Creates an instance of BigTwo class.
	 */
	public BigTwo() {
		this.gui =  new BigTwoGUI(this);
		for (int i = 0 ; i < numOfPlayers ; i++) {
			CardGamePlayer player = new CardGamePlayer();
			this.getPlayerList().add(player);
		}
		this.bigTwoClient = new BigTwoClient(this,this.gui);
		String name = JOptionPane.showInputDialog("Name: ");
		this.bigTwoClient.setPlayerName(name);
		this.bigTwoClient.connect();
	}
	
	/**
	 * Getter method for the number of players in the BigTwo game.
	 * @return an integer value specifying the number of players in the BigTwo game.
	 */
	public int getNumOfPlayers() {
		return playerList.size();
	}

	/**
	 * Getter method for the deck class.
	 * @return deck.
	 */
	public Deck getDeck() {
		return this.deck;
	}
	
	/**
	 * Getter method for the list of player in the game.
	 * @return list of players in the BigTwo game.
	 */
	public ArrayList<CardGamePlayer> getPlayerList(){
		return this.playerList;
	}
	
	/**
	 * Getter method for the list of valid hands played on the table.
	 * @return list of valid hands played on the table in the BigTwo game.
	 */
	public ArrayList<Hand> getHandsOnTable(){
		return this.handsOnTable;
	}
	/**
	 * Getter method for finding the index of the active player in the player list.
	 * @return index of the active player in the player list in the BigTwo game.
	 */
	public int getCurrentPlayerIdx() {
		return this.currentPlayerIdx;
	}
	
	/**
	 * Removes the cards in the players' hands. Initializes BigTwoDeck, shuffles the deck and distribute the cards evenly among the players.
	 * @param deck is the deck to be used in the BigTwo game.
	 */
	public void start(Deck deck) {
		//remove all cards from players
		for (int i = 0 ; i < BigTwo.numOfPlayers ; i++) {
			this.playerList.get(i).removeAllCards();
		}
		//distribute cards to from deck to players
		while (!deck.isEmpty()) {
			for (int i = 0 ; i < BigTwo.numOfPlayers ; i++) {
				this.playerList.get(i).addCard(deck.getCard(0));
				if (deck.getCard(0).getSuit() == 0 && deck.getCard(0).getRank() == 2){
					this.currentPlayerIdx = i;//setting index to player who got 3 of diamonds
					this.gui.setActivePlayer(i);//setting active player
				}
				deck.removeCard(0);
			}
		}
		//sort all the cards of the players
		for (int i = 0 ; i < BigTwo.numOfPlayers ; i++) {
			this.playerList.get(i).sortCardsInHand();
		}
		this.handsOnTable.removeAll(handsOnTable);//empty the handsOnTable
		BigTwo.numOfPasses = 0;
		this.gui.reset();
		this.gui.repaint();//repaint UI
		this.gui.promptActivePlayer();//prompt the active player
	}
	/**
	 * a method for setting the index of the currentPlayerIndex (i.e., the player having control of the GUI)
	 */
	public void setCurrentPlayerIdx(int activePlayer) {
		if (activePlayer < 0 || activePlayer >= this.getPlayerList().size()) {
			this.currentPlayerIdx = 0;
		} else {
			this.currentPlayerIdx = activePlayer;
		}
	}
	/**
	 * calls checkMove()
	 * @param playerIdx is the index of the active player in the player list.
	 * @param cardIdx is the chosen cards by the active player through the UI.
	 */
	public void makeMove(int playerIdx,int[] cardIdx) {
		bigTwoClient.sendMessage(new CardGameMessage(CardGameMessage.MOVE,-1,cardIdx));
	}
		
	public static int numOfPasses = 0;//to check the number of passes after the active player moves.
	
	/**
	 * This method is responsible for checking if the hands played by the players is valid. Asks the player to re input if their move is invalid.
	 * Controls the flow of the moves by the player.
	 * @param playerIdx is the index of the active player in the player list.
	 * @param cardIdx is the chosen cards by the active player through the UI.
	 */
	public void checkMove(int playerIdx,int[] cardIdx) {
		//CHECKS IF CARDIDX IS NULL (PASS)
		CardList cardsPlayed = new CardList();
		CardGamePlayer player = this.playerList.get(playerIdx);
		//when the table is empty
		if (currentPlayerIdx != playerIdx ) {
			this.gui.printMsg("Not a legal move!!!");
			this.gui.promptActivePlayer();
		}else {
			if (this.handsOnTable.isEmpty()) {
				if (cardIdx == null) {
					this.gui.printMsg("Not a legal move!!!");
					this.gui.promptActivePlayer();
				}else {
					cardsPlayed = player.play(cardIdx);//getting the cards played to cardsPlayed CardList
					//checks if the move contains 3 of diamonds and is valid.
					if (composeHand(player,cardsPlayed) == null || !cardsPlayed.contains(this.getPlayerList().get(playerIdx).getCardsInHand().getCard(0))) {
						this.gui.printMsg("Not a legal move!!!");
						this.gui.promptActivePlayer();
					}else {
						this.handsOnTable.add(composeHand(player,cardsPlayed));
						this.gui.printMsg("{"+composeHand(player,cardsPlayed).getType()+"} " + composeHand(player,cardsPlayed).toString());
						this.gui.setActivePlayer(playerIdx + 1);
						this.setCurrentPlayerIdx(playerIdx + 1);
						this.gui.repaint();
						player.removeCards(cardsPlayed);//remove cards played
						this.gui.promptActivePlayer();
					}
				}
			}else {
				if(BigTwo.numOfPasses == 3) {//when 3 players pass and turn goes back to the player who played the hand on the table
					if (cardIdx == null) {
						this.gui.printMsg("Not a legal move!!!");
						this.gui.promptActivePlayer();
					}else {
						cardsPlayed = player.play(cardIdx);//getting the cards played to cardsPlayed CardList
						if (composeHand(player,cardsPlayed) == null) {
							this.gui.printMsg("Not a legal move!!!");
							this.gui.promptActivePlayer();
						}else {
							this.handsOnTable.add(composeHand(player,cardsPlayed));
							this.gui.printMsg("{"+composeHand(player,cardsPlayed).getType()+"} " + composeHand(player,cardsPlayed).toString());
							player.removeCards(cardsPlayed);//delete used cards from player's hands
							this.gui.setActivePlayer(playerIdx + 1);
							this.setCurrentPlayerIdx(playerIdx + 1);
							this.gui.repaint();
							if(this.endOfGame()) {
								String finalMsg = "";
								this.gui.disable();
								this.gui.printMsg("");
								for (int i = 0 ; i < this.getPlayerList().size(); i++) {
									if(this.getPlayerList().get(i).getCardsInHand().size() == 0) {
										finalMsg += (this.getPlayerList().get(i).getName()+ " wins the game.\n");
									}else {
										Integer numOfCards =  this.getPlayerList().get(i).getCardsInHand().size();
										finalMsg += (this.getPlayerList().get(i).getName()+ " has " + numOfCards.toString() + " cards in hand.\n");
									}
								}
								JOptionPane.showMessageDialog(gui.getFrame(), finalMsg);
								bigTwoClient.sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
							}else {
								this.gui.promptActivePlayer();
							}
							BigTwo.numOfPasses = 0;
						}
					}
				}else {
					if (cardIdx == null) {
						this.gui.printMsg("{pass}");
						this.gui.setActivePlayer(playerIdx + 1);
						this.setCurrentPlayerIdx(playerIdx + 1);
						this.gui.repaint();
						this.gui.promptActivePlayer();
						BigTwo.numOfPasses++;
					}else {
						cardsPlayed = player.play(cardIdx);//getting the cards played to cardsPlayed CardList
						if (composeHand(player,cardsPlayed) == null) {
							this.gui.printMsg("Not a legal move!!!");
							this.gui.promptActivePlayer();
						}else {
							if(composeHand(player, cardsPlayed).beats(this.handsOnTable.get(this.handsOnTable.size()-1))) {
								this.handsOnTable.add(composeHand(player,cardsPlayed));
								BigTwo.numOfPasses = 0;
								this.gui.printMsg("{"+composeHand(player,cardsPlayed).getType()+"} " + composeHand(player,cardsPlayed).toString());
								player.removeCards(cardsPlayed);
								this.gui.setActivePlayer(playerIdx + 1);
								this.setCurrentPlayerIdx(playerIdx + 1);
								this.gui.repaint();
								if(this.endOfGame()) {
									String finalMsg = "";
									this.gui.disable();
									this.gui.printMsg("");
									for (int i = 0 ; i < this.getPlayerList().size(); i++) {
										if(this.getPlayerList().get(i).getCardsInHand().size() == 0) {
											finalMsg += (this.getPlayerList().get(i).getName()+ " wins the game.\n");
										}else {
											Integer numOfCards =  this.getPlayerList().get(i).getCardsInHand().size();
											finalMsg += (this.getPlayerList().get(i).getName()+ " has " + numOfCards.toString() + " cards in hand.\n");
										}
									}
									JOptionPane.showMessageDialog(gui.getFrame(), finalMsg);
									bigTwoClient.sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
								}else {
									this.gui.promptActivePlayer();
								}
							}else {
								this.gui.printMsg("Not a legal move!!!");
								this.gui.promptActivePlayer();
							}
						}
					}
				}
			}
		}
		
		
	}
		
	/**
	 * Checks if the BigTwo game has ended. (Which is when a player has no more cards left)
	 * @return a boolean value. Returns true if the game has ended, otherwise return false.
	 */
	public boolean endOfGame() {
		if (this.handsOnTable.size() == 0) {
			return false;
		}
		for (int i = 0 ; i < BigTwo.numOfPlayers ; i++) {
			if (this.playerList.get(i).getCardsInHand().size() == 0 ) {
				return true;
			}
		}
		return false;
	}

}
