
/**
 * This class checks the validity of Quad hands
 * @author Winson Sutanto
 *
 */
public class Quad extends Hand{

	/**
	 * Constructor for Quad class which is inherited from the Hand parent class.
	 * @param player is the current active player in the game.
	 * @param cards is the hand the active player played.
	 */
	public Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}

	/**
	 * checks the validity of Quad hand.
	 * @return a boolean value. Returns true if the hand is a valid Quad, otherwise return false.
	 */
	public boolean isValid() {
		this.sort();
		if(this.size() != 5) {
			return false;
		}else {
			if(this.getCard(0).getRank() == this.getCard(1).getRank()) {
				if( this.getCard(0).getRank() == this.getCard(2).getRank() && this.getCard(0).getRank() == this.getCard(3).getRank()) {
					return true;
				}else {
					return false;
				}
			}else {
				if (this.getCard(1).getRank() == this.getCard(2).getRank() && this.getCard(1).getRank() == this.getCard(3).getRank() && this.getCard(1).getRank() == this.getCard(4).getRank()) {
					return true;
				}else {
					return false;
				}
			}
		}
	}
	/**
	 * @return a String that represent the type of the hand. Which is "Quad" in this case.
	 */
	
	public String getType() {
		return "Quad";
	}
}
