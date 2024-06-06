/**
 * This class checks the validity of Straight hands.
 * 
 * @author Winson Sutanto
 *
 */
public class Straight extends Hand{
	/**
	 * Constructor for Straight class which is inherited from the Hand parent class.
	 * @param player is the current active player in the game.
	 * @param cards is the hand the active player played.
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	/**
	 * checks validity of Straight hand.
	 * @return a boolean value. Returns true if the hand is a valid Straight, otherwise return false.
	 */
	public boolean isValid() {
		if (this.size() != 5) {
			return false;
		}else {
			int[] straightRanks = new int[5];
			for (int i = 0 ; i < 5 ; i++) {
				if (this.getCard(i).getRank() == 0 || this.getCard(i).getRank() == 1) {
					straightRanks[i] = this.getCard(i).getRank() + 13;
				}else {
					straightRanks[i] = this.getCard(i).getRank();
				}
			}
			for(int i = 0 ; i < 4; i++){
				if (straightRanks[i+1]-straightRanks[i] != 1) {
					return false;
				}
			}
			return true;
		}
	}
	/**
	 * @return a String that represent the type of the hand. Which is "Straight" in this case.
	 */
	public String getType() {
		return "Straight";
	}
	
}
