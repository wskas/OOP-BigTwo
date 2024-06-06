/**
 * This class checks the validity of Flush hands
 * @author Winson Sutanto
 *
 */
public class Flush extends Hand{

	/**
	 * Constructor for the Flush class which is inherited from Hand parent class.
	 * @param player is the current active player in the game.
	 * @param cards is the hand the active player played.
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * Checks the validity of Flush hand
	 * @return a boolean value. Returns true if the hand is a valid Flush, otherwise return false.
	 */
	public boolean isValid() {
		if (this.size() != 5) {
			return false;
		}else {
			for(int i = 0 ; i < 4 ; i++) {
				if(this.getCard(i).getSuit() != this.getCard(i+1).getSuit()) {
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * @return a String that represent the type of the hand. Which is "Flush" in this case.
	 */
	public String getType() {
		return "Flush";
	}

}
