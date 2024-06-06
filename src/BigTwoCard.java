
/**
 * This class is a child class of Card parent class.
 * @author Winson Sutanto
 */
public class BigTwoCard extends Card{
	/**
	 * Creates an instance of the BigTwoCard class.
	 * @param suit is the symbol of the card.
	 * @param rank is the number of th2 card.
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
	}

	/**
	 * Overriden the compareTo(Card card) method in Card parent class for the game to work.
	 * To be used when card.sort() is called.
	 * @return an integer. Returns 1 if card is larger than the specified card. Returns  negative 1 if card is smaller than the specified card.
	 */
	public int compareTo(Card card) {
		int[] bigTwoRank = new int[2];
		if (this.rank == 0 || this.rank == 1) {
			bigTwoRank[0] = this.rank + 13; 
		}else {
			bigTwoRank[0] = this.rank;
		}
		if (card.rank == 0 || card.rank == 1) {
			bigTwoRank[1] = card.rank + 13;
		}else {
			bigTwoRank[1] = card.rank;
		}
		
		if(bigTwoRank[0] > bigTwoRank[1]) {
			return 1;
		}else if(bigTwoRank[0] < bigTwoRank[1]) {
			return -1;
		}else {
			if (this.suit > card.suit) {
				return 1;
			}else {
				return -1;
			}
		}
		
	}
	

}
