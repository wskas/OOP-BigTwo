import java.util.ArrayList;


/**
 * This class is a child class of CardList class.
 * This abstract class is a blueprint for more specific types of hands.
 * Contains public methods getPlayer(),getTopCard(),beats().
 * Contains public abstract method isValid(),getType() to be overriden in the child class.
 * @author Winson Sutanto
 *
 */
public abstract class Hand extends CardList{

	private final CardGamePlayer player;//The current active player in the game.
	
	/**
	 * Constructor for Hand class. Creates an instance of Hand class.
	 * @param player is the current active player in the game.
	 * @param cards is the hand the active player played.
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		this.player = player;
		for ( int i = 0 ; i < cards.size() ; i++) {
			this.addCard(cards.getCard(i));
		}
		this.sort();//sorts the hands when the hand is created. No need to sort in isValid() anymore.
	}
	
	/**
	 * Getter method for accessing the private variable player.
	 * @return the current active player
	 */
	public CardGamePlayer getPlayer() {
		return this.player;
	}
	
	/**
	 * Getter method for the hand played by the active player.
	 * @return a CardList of the hand the active player played.
	 */
	public Card getTopCard(){
		if (!this.isEmpty()) {
			return this.getCard(this.size()-1);//get the last card of sorted hand
		}else {
			return null;
		}
	}
	
	/**
	 * Checks if the hand beats the specified hand.
	 * @param hand is the combination of cards that is compared to the current hand.
	 * @return a boolean value. Returns true if the current hand beats the specified hand, otherwise return false.
	 */
	public boolean beats(Hand hand) {
		if (hand == null || this == null || !this.isValid() || !hand.isValid() || hand.size() != this.size()) {
			return false;
		}else {
			if (this.size() == 5 ) {
				ArrayList<String> handOrder = new ArrayList<String>();
				handOrder.add("Straight");//added according to strength of combination
				handOrder.add("Flush");
				handOrder.add("FullHouse");
				handOrder.add("Quad");
				handOrder.add("StraightFlush");
				//order from weakest: straight, flush , full house, straight flush
				if (this.getType() !=  hand.getType()) {
					return(handOrder.indexOf(this.getType()) > handOrder.indexOf(hand.getType()));//checks which types of combination is stronger.
				}else {
					return(this.getTopCard().compareTo(hand.getTopCard()) > 0 );//compare top card if the types are equal.
				}
			}else {
				return(this.getTopCard().compareTo(hand.getTopCard()) > 0 );//compare top card if the types are equal.
			}
		}
	}
	
	public abstract boolean isValid();//abstract class isValid(). To be overriden.
	
	public abstract String getType();//abstract class getType(). To be overriden.
	

}
