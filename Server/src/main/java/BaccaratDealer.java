import java.util.ArrayList;

// class for managing creation and distribution of cards
public class BaccaratDealer {
	ArrayList<Card> deck;  // deck of 52 cards
	
	// constructor, used to initialize the ArrayList
	public BaccaratDealer() {
		deck = new ArrayList<Card>();
	}
	
	// clears the deck and adds 52 cards to the deck
	public void generateDeck() {
		deck.clear();
		for (int i = 0; i < 13; i++) {
			deck.add(new Card("Spade", i + 1));
			deck.add(new Card("Heart", i + 1));
			deck.add(new Card("Club", i + 1));
			deck.add(new Card("Diamond", i + 1));
		}
	}
	
	// returns two cards randomly from the deck
	public ArrayList<Card> dealHand() {
		ArrayList<Card> dealHand = new ArrayList<>();
		int index = (int)(Math.random() * deckSize());
		dealHand.add(deck.remove(index));
		index = (int)(Math.random() * deckSize());
		dealHand.add(deck.remove(index));
		return dealHand;
	}
	
	// returns one card randomly from the deck
	public Card drawOne() {
		int index = (int)(Math.random() * deckSize());
		return deck.remove(index);
	}
	
	// generates a new deck of 52 cards and randomizes it
	public void shuffleDeck() {
		generateDeck();
		ArrayList<Card> shuffledDeck = new ArrayList<Card>();
		while (deck.size() > 0) {
			int index = (int)(Math.random() * deckSize());
			shuffledDeck.add(deck.remove(index));
		}
		this.deck = shuffledDeck;
	}
	
	// returns the size of the deck
	public int deckSize() {
		return deck.size();
	}
}
