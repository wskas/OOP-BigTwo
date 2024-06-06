
/**
 * This class is used to check validity of Single hands.
 * 
 * @author Winson Sutanto
 *
 */
public class Single extends Hand {

	/**
	 * Constructor for Single class inherited from Hand parent class.
	 * @param player is the current active player in the game.
	 * @param cards is the hand the active player played.
	 */
	public Single(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}

	/**
	 * checks the validity of Single hands
	 * @return boolean value. Returns true if the hand is a valid Single, otherwise return false.
	 */
	public boolean isValid() {
		return(this.size() == 1);
	}

	/**
	 * @return a String that represent the type of the hand. Which is "Single" in this case.
	 */
	public String getType() {
		return "Single";
	}
	
}
