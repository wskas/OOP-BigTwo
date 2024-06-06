
/**
 * This class checks the validity of Triple hands.
 * @author Winson Sutanto
 *
 */
public class Triple extends Hand{
	/**
	 * Constructor for Triple class which is inherited from Hand parent class.
	 * @param player is the current active player in the game.
	 * @param cards is the hand the active player played.
	 */
	public Triple(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	/**
	 * checks the validity of Triple hand.
	 * @return a boolean value. Returns true if the hand is a valid triple, otherwise return false.
	 */
	public boolean isValid() {
		return (this.size() == 3 && (this.getCard(0).getRank() == this.getCard(1).getRank()) && (this.getCard(0).getRank() == this.getCard(2).getRank()));
	}
	
	/**
	 * @return a String that represent the type of the hand. Which is "Triple" in this case.
	 */
	public String getType() {
		return "Triple";
	}
}
