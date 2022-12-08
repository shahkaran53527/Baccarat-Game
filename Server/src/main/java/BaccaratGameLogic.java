import java.util.ArrayList;
 // handles all the Logic for BaccaratGame
public class BaccaratGameLogic {
	
	// returns who won the game using player and banker hands
	public static String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		int playerHand = handTotal(hand1);  // gets the player hand value
		int bankerHand = handTotal(hand2);  // gets the banker hand value
		// draw
		if (playerHand >= 8 && bankerHand >= 8) {
			return "Draw";
		} else if (playerHand >= 8) {  // player natural win
			return "Player";
		} else if (bankerHand >= 8) {  // banker natural win
			return "Banker";
		} else if(playerHand == bankerHand) {  // draw
            return "Draw";
        } else if(bankerHand > playerHand) {  // return the highest value then
            return "Banker";
        } else {
            return "Player";
        }
	}
	
	// returns the handTotal, 10 and face cards are worth 0, hands with value > 10 are modded by 10
	public static int handTotal(ArrayList<Card> hand) {
		int value = 0;
		for (Card c : hand) {
			if (c.value < 10) {  // ignore face cards
				value += c.value;
			}
		}
		return value % 10;
	}
	
	// checks if player should get another card
	public static boolean evaluatePlayerDraw(ArrayList<Card> hand) {
		if (handTotal(hand) < 7) {
			return true;
		}
		return false;
	}
	
	// checks if banker should get another card
	public static boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
		int bankerHand = handTotal(hand);
		int cardValue = 0;
		if (playerCard == null) {
			cardValue = 5;
		} else if (playerCard.value < 10){
			cardValue = playerCard.value;
		}
		if (cardValue == 8 && bankerHand <= 2) {
			return true;
		} else if ((cardValue == 0 || cardValue == 1 || cardValue == 9) && bankerHand <= 3) {
			return true;
		} else if ((cardValue == 2 || cardValue == 3) && bankerHand <= 4) {
			return true;
		} else if ((cardValue == -1 || cardValue == 4 || cardValue == 5) && bankerHand <= 5) {
			return true;
		} else if ((cardValue == 6 || cardValue == 7) && bankerHand <= 6) {
			return true;
		}
		return false;
	}
}
