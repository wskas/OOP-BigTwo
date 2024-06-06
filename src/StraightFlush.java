/**
 * This class checks the validity of Straight Flush hands.
 * @author Winson Sutanto
 *
 */

public class StraightFlush extends Hand{

	/**
	 * Constructor for StraightFlush class which is inherited from Hand parent class.
	 * @param player is the current active player in the game.
	 * @param cards is the hand the active player played.
	 */
	public StraightFlush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	/**
	 * checks the validity of Straight Flush hand
	 * @return a boolean value. Returns true if the hand is a valid Straight Flush, otherwise return false.
	 */
	public boolean isValid() {
		if (this.size() != 5 ) {
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
			
			for (int i = 0; i < 4 ; i++) {
				if (this.getCard(i+1).getSuit()!=this.getCard(i).getSuit()) {
					return false;
				}
			}
			return true;
		}
	}
	/**
	 * @return a String that represent the type of the hand. Which is "StraightFlush" in this case.
	 */
	public String getType() {
		return "StraightFlush";
	}
}
