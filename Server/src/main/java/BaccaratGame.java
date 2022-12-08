import java.util.ArrayList;

// BaccaratGame class used for checkingwin
public class BaccaratGame {
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	BaccaratDealer theDealer;
	double currentBet;
	double totalWinnings;
	int playerTotal;
	int bankerTotal;
	String Bet;  // whom the player betted on
	String winner;  // who Won
	
	// constructor
	public BaccaratGame() {
		Bet = "";
		winner = "";
		totalWinnings = 0.0;
		playerHand = new ArrayList<>();
		bankerHand = new ArrayList<>();
		theDealer = new BaccaratDealer();
	}
	
	// clears everything
	public void resetGame() {
		Bet = "";
		winner = "";
		playerHand.clear();
		bankerHand.clear();
		theDealer = new BaccaratDealer();
	}
	
	// returns how the client won, updates totalWinninggs, whoWon, playerTotal and bankerTotal
	public double evaluateWinnings() {
		theDealer.shuffleDeck();
		playerHand = theDealer.dealHand();
		bankerHand = theDealer.dealHand();
		Card playerCard = null;
		// if draw, then get another card
		if (BaccaratGameLogic.evaluatePlayerDraw(playerHand)) {
			playerCard = theDealer.drawOne();
			playerHand.add(playerCard);
		}
		if (BaccaratGameLogic.evaluateBankerDraw(bankerHand, playerCard)) {
			bankerHand.add(theDealer.drawOne());
		}
		winner = BaccaratGameLogic.whoWon(playerHand, bankerHand);
		if (Bet.equals("Player")) {
			if (winner == "Player") {
			} else {
				currentBet = -1 * currentBet;
			}
		} else if (Bet.equals("Banker")) {
			if (winner == "Banker") {
				currentBet = .95 * currentBet;  // evens out the odds
			} else {
				currentBet = -1 * currentBet;
			}
		} else if (Bet.equals("Draw")) {
			if (winner == "Draw") {
				currentBet = 8 * currentBet;
			} else {
				currentBet = -1 * currentBet;
			}
		}
		totalWinnings += currentBet;
		playerTotal = BaccaratGameLogic.handTotal(playerHand);
		bankerTotal = BaccaratGameLogic.handTotal(bankerHand);
		return currentBet;
	}
}
