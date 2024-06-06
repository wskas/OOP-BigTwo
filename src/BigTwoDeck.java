
/**
 * This class is the child class of parent class Deck.
 * Initializes deck of cards of datatype BigTwoCard for the game to function.
 * @author Winson Sutanto
 *
 */
public class BigTwoDeck extends Deck{

	/**
	 * creates an instance of BigTwoDeck class.
	 */
	
	public BigTwoDeck() {
		super();
	}


	//overriden initialize() in Deck parent class by changing data type of the card from Card to BigTwoCard for the game to work.
	public void initialize() {
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);//uses BigTwoCard instead of Card to use the correct sort()
				addCard(card);
			}
		}
	}
}
