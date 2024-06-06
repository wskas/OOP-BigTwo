/**
 * This class checks validity of Full House hands.
 * @author Winson Sutanto
 *
 */
public class FullHouse extends Hand {
	
	/**
	 * Constructor for FullHouse class which is inherited from the Hand parent class.
	 * @param player is the current active player in the game.
	 * @param cards is the hand the active player played.
	 */
	public FullHouse(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	
	/**
	 * Checks the validity of Full House hand.
	 * @return a boolean value. Returns true is hand is a valid Full House, otherwise return false.
	 */
	public boolean isValid() {
		if (this.size()!=5) {
			return false;
		}else {
			if (this.getCard(1).getRank() != this.getCard(2).getRank()) {
				if(this.getCard(0).getRank() == this.getCard(1).getRank() && this.getCard(2).getRank() == this.getCard(3).getRank() && this.getCard(2).getRank() == this.getCard(4).getRank()) {
					return true;
				}else {
					return false;
				}
			}else if( this.getCard(2).getRank() != this.getCard(3).getRank()){
				if(this.getCard(0).getRank() == this.getCard(1).getRank() && this.getCard(0).getRank() == this.getCard(2).getRank() && this.getCard(3).getRank() == this.getCard(4).getRank()) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
	}
	
	/**
	 * @return a String that represent the type of the hand. Which is "FullHouse" in this case.
	 */
	public String getType() {
		return "FullHouse";
	}
}
