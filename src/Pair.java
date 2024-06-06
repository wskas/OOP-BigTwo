
/**
 * This class checks the validity of Pair hands
 * @author Winson Sutanto
 *
 */
public class Pair extends Hand {
	/**
	 * Constructor for the Pair class which is inherited from the Hand parent class.
	 * @param player is the current active player in the game.
	 * @param cards is the hand the active player played.
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}

	/**
	 * checks the validity of the Pair hand
	 * @return a boolean value. Returns true if the hand is a valid pair, otherwise return false.
	 */
	public boolean isValid() {
		return (this.size() == 2 && this.getCard(0).getRank() == this.getCard(1).getRank());
	}

	/**
	 * @return a String that represent the type of the hand. Which is "Pair" in this case.
	 */
	public String getType() {
		return "Pair";
	}
}
