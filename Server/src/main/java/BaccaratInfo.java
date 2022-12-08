import java.io.Serializable;
import java.util.ArrayList;

// class for sending info back and forth between client and server
public class BaccaratInfo implements Serializable {

	private static final long serialVersionUID = 8438887217790120277L;
	String Bet;  // who the client bet on
	double currentBet;  // how much they betted
	double totalWinnings;  // how much they won
	ArrayList<Card> playerHand;  // players hand
	ArrayList<Card> bankerHand;  // bankers hand
	int playerTotal;  // value of cards of player
	int bankerTotal;  // value of cards of banker
	String whoWon;  // who won the game(Player, Banker, or Draw)
	
	// constructor, initializes everything to default values
	BaccaratInfo() {
		Bet = "";
		currentBet = 0.0;
		totalWinnings = 0.0;
		playerTotal = 0;
		bankerTotal = 0;
		playerHand = new ArrayList<>();
		bankerHand = new ArrayList<>();
		whoWon = "";
	}
	
	// used by the Client program to set up the Bet and currentBet
	public void setBaccaratInfoClient(String Bet, double currentBet) {
		this.Bet = Bet;
		this.currentBet = currentBet;
	}
	
	// used by the Server program to set up all the info required by Client 
	public void setBaccaratInfoServer(String winner, double totalWinnings, ArrayList<Card> playerHand, ArrayList<Card> bankerHand,
			int playerTotal, int bankerTotal) {
		this.whoWon = winner;
		this.totalWinnings = totalWinnings;
		this.playerHand = playerHand;
		this.bankerHand = bankerHand;
		this.playerTotal = playerTotal;
		this.bankerTotal = bankerTotal;
	}
}
