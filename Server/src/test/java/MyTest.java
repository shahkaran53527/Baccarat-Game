import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	// This tests the constructor for Card class.
	@Test
	void CardConstructor() {
		Card c1 = new Card("King", 13);
		assertEquals(c1.suite, "King");
		assertEquals(c1.value, 13);
	}
	
	// This tests the constructor of BaccaratDealer class.
	@Test
	void BaccaratDealerConstructor() {
		BaccaratDealer b1 = new BaccaratDealer();
		assertEquals(b1.deckSize(), 0);
	}
	
	// This tests generateDeck method in BaccaratDealer class.
	@Test
	void BaccaratDealerGenerateDeck1() {
		BaccaratDealer b1 = new BaccaratDealer();
		b1.generateDeck();
		assertEquals(b1.deckSize(), 52);
	}
	
	// This tests generateDeck method in BaccaratDealer class.
	@Test
	void BaccaratDealerGenerateDeck2() {
		BaccaratDealer b1 = new BaccaratDealer();
		b1.generateDeck();
		int sizeOfSpade = 0;
		int sizeOfHeart = 0;
		int sizeOfClub = 0;
		int sizeOfDiamond = 0;
		for (int i = 0; i < 52; i++) {
			if (b1.deck.get(i).suite == "Spade") {
				sizeOfSpade++;
			}
			else if (b1.deck.get(i).suite == "Heart") {
				sizeOfHeart++;
			}
			else if (b1.deck.get(i).suite == "Club") {
				sizeOfClub++;
			}
			else {
				sizeOfDiamond++;
			}
		}
		assertEquals(sizeOfSpade, 13);
		assertEquals(sizeOfHeart, 13);
		assertEquals(sizeOfClub, 13);
		assertEquals(sizeOfDiamond, 13);
	}
	
	// This tests DealHand method in BaccaratDealer class.
	@Test
	void BaccaratDealerDealHand1() {
		BaccaratDealer b1 = new BaccaratDealer();
		b1.generateDeck();
		ArrayList<Card> dealHand = new ArrayList<>();
		dealHand = b1.dealHand();
		assertEquals(dealHand.size(), 2);
		assertEquals(b1.deckSize(), 50);
	}
	
	// This tests DealHand method in BaccaratDealer class.
	@Test
	void BaccaratDealerDealHand2() {
		BaccaratDealer b1 = new BaccaratDealer();
		b1.generateDeck();
		ArrayList<Card> dealHand = new ArrayList<>();
		dealHand = b1.dealHand();
		boolean flag = false;
		for (int i = 0; i < 50; i++) {
			if ((b1.deck.get(i).suite == dealHand.get(0).suite) && (b1.deck.get(i).value == dealHand.get(0).value)
				|| (b1.deck.get(i).suite == dealHand.get(1).suite) && (b1.deck.get(i).value == dealHand.get(1).value)) {
				flag = true;
			}
		}
		assertEquals(flag, false);
	}
	
	// This tests DrawOne method in BaccaratDealer class.
	@Test
	void BaccaratDealerDrawOne1() {
		BaccaratDealer b1 = new BaccaratDealer();
		b1.generateDeck();
		Card drawOne = b1.drawOne();
		assertEquals(b1.deckSize(), 51);
	}
	
	// This tests DrawOne method in BaccaratDealer class.
	@Test
	void BaccaratDealerDrawOne2() {
		BaccaratDealer b1 = new BaccaratDealer();
		b1.generateDeck();
		Card drawOne = b1.drawOne();
		boolean flag = false;
		for (int i = 0; i < 50; i++) {
			if ((b1.deck.get(i).suite == drawOne.suite) && (b1.deck.get(i).value == drawOne.value)) {
				flag = true;
			}
		}
		assertEquals(flag, false);
	}
	
	// This tests ShuffleDeck method in BaccaratDealer class.
	@Test
	void BaccaratDealerShuffleDeck1() {
		BaccaratDealer b1 = new BaccaratDealer();
		b1.generateDeck();
		b1.shuffleDeck();
		assertEquals(b1.deckSize(), 52);
	}
	
	// This tests ShuffleDeck method in BaccaratDealer class.
	@Test
	void BaccaratDealerShuffleDeck2() {
		BaccaratDealer b1 = new BaccaratDealer();
		b1.generateDeck();
		Card drawOne = b1.drawOne();
		b1.shuffleDeck();
		assertEquals(b1.deckSize(), 52);
	}
	
	// This tests DeckSize method in BaccaratDealer class.
	@Test
	void BaccaratDealerDeckSize1() {
		BaccaratDealer b1 = new BaccaratDealer();
		b1.generateDeck();
		assertEquals(b1.deckSize(), 52);
	}
	
	// This tests DeckSize method in BaccaratDealer class.
	@Test
	void BaccaratDealerDeckSize2() {
		BaccaratDealer b1 = new BaccaratDealer();
		assertEquals(b1.deckSize(), 0);
	}
	
	// This tests WhoWon method in BaccaratGameLogic class.
	@Test
	void BaccaratGameLogicWhoWon1() {
		ArrayList<Card> hand1 = new ArrayList<>();
		hand1.add(new Card("Spade", 8));
		hand1.add(new Card("Club", 9));
		
		ArrayList<Card> hand2 = new ArrayList<>();
		hand2.add(new Card("Heart", 6));
		hand2.add(new Card("Diamond", 9));
		
		String winner = BaccaratGameLogic.whoWon(hand1, hand2);
		assertEquals(winner, "Player");
	}

	// This tests WhoWon method in BaccaratGameLogic class.
	@Test
	void BaccaratGameLogicWhoWon2() {
		ArrayList<Card> hand1 = new ArrayList<>();
		hand1.add(new Card("Spade", 4));
		hand1.add(new Card("Club", 2));
		hand1.add(new Card("Club", 4));
		
		ArrayList<Card> hand2 = new ArrayList<>();
		hand2.add(new Card("Heart", 3));
		hand2.add(new Card("Diamond", 2));
		hand2.add(new Card("Diamond", 5));
		
		String winner = BaccaratGameLogic.whoWon(hand1, hand2);
		assertEquals(winner, "Draw");
	}
	
	// This tests handTotal method in BaccaratGameLogic class.
	@Test
	void BaccaratGameLogicHandTotal1() {
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("Spade", 10));
		hand.add(new Card("Club", 2));
		
		int handTotal = BaccaratGameLogic.handTotal(hand);
		assertEquals(handTotal, 2);
	}
	
	// This tests handTotal method in BaccaratGameLogic class.
	@Test
	void BaccaratGameLogicHandTotal2() {
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("Spade", 7));
		hand.add(new Card("Club", 2));
		
		int handTotal = BaccaratGameLogic.handTotal(hand);
		assertEquals(handTotal, 9);
	}
	
	// This tests evaluatePlayerDraw method in BaccaratGameLogic class.
	@Test
	void EvaluatePlayerDraw1() {
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("Spade", 2));
		hand.add(new Card("Club", 2));
		
		boolean evaluateDraw = BaccaratGameLogic.evaluatePlayerDraw(hand);
		assertEquals(evaluateDraw, true);
	}
	
	// This tests evaluatePlayerDraw method in BaccaratGameLogic class.
	@Test
	void EvaluatePlayerDraw2() {
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("Spade", 5));
		hand.add(new Card("Club", 2));
		
		boolean evaluateDraw = BaccaratGameLogic.evaluatePlayerDraw(hand);
		assertEquals(evaluateDraw, false);
	}
	
	// This tests evaluatePlayerDraw method in BaccaratGameLogic class.
	@Test
	void EvaluatePlayerDraw3() {
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("Spade", 5));
		hand.add(new Card("Club", 4));
		
		boolean evaluateDraw = BaccaratGameLogic.evaluatePlayerDraw(hand);
		assertEquals(evaluateDraw, false);
	}
	
	// This tests evaluateBankerDraw method in BaccaratGameLogic class.
	@Test
	void EvaluateBankerDraw1() {
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("Spade", 5));
		hand.add(new Card("Club", 4));
		
		Card playerCard = new Card("Spade", 9);
		
		boolean evaluateDraw = BaccaratGameLogic.evaluateBankerDraw(hand, playerCard);
		assertEquals(evaluateDraw, false);
	}
	
	// This tests evaluateBankerDraw method in BaccaratGameLogic class.
	@Test
	void EvaluateBankerDraw2() {
		ArrayList<Card> hand = new ArrayList<>();
		hand.add(new Card("Spade", 2));
		hand.add(new Card("Club", 4));
		
		Card playerCard = new Card("Spade", 6);
		
		boolean evaluateDraw = BaccaratGameLogic.evaluateBankerDraw(hand, playerCard);
		assertEquals(evaluateDraw, true);
	}
	
	// This tests BaccaratGame constructor in BaccaratGame class.
	@Test
	void BaccaratGameConstructor() {
		BaccaratGame b1 = new BaccaratGame();
		assertEquals(b1.Bet, "");
		assertEquals(b1.winner, "");
		assertEquals(b1.totalWinnings, 0.0);
		assertEquals(b1.playerHand.size(), 0);
		assertEquals(b1.bankerHand.size(), 0);
		assertEquals(b1.theDealer.deckSize(), 0);
	}
	
	// This tests BaccaratGameResetGame method in BaccaratGame class.
	@Test
	void BaccaratGameResetGame1() {
		BaccaratGame b1 = new BaccaratGame();
		b1.Bet = "Player";
		b1.winner = "Banker";
		ArrayList<Card> playerHand = new ArrayList<>();
		playerHand.add(new Card("Spade", 1));
		playerHand.add(new Card("Club", 4));
		
		ArrayList<Card> bankerHand = new ArrayList<>();
		bankerHand.add(new Card("Heart", 1));
		bankerHand.add(new Card("Club", 8));
		
		b1.resetGame();
		assertEquals(b1.Bet, "");
		assertEquals(b1.winner, "");
		assertEquals(b1.playerHand.size(), 0);
		assertEquals(b1.bankerHand.size(), 0);
	}
	
	// This tests BaccaratGameResetGame method in BaccaratGame class.
	@Test
	void BaccaratGameResetGame2() {
		BaccaratGame b1 = new BaccaratGame();
		b1.Bet = "Player";
		b1.winner = "Draw";
		ArrayList<Card> playerHand = new ArrayList<>();
		playerHand.add(new Card("Spade", 2));
		playerHand.add(new Card("Club", 7));
		
		ArrayList<Card> bankerHand = new ArrayList<>();
		bankerHand.add(new Card("Heart", 7));
		bankerHand.add(new Card("Club", 2));
		
		b1.resetGame();
		assertEquals(b1.Bet, "");
		assertEquals(b1.winner, "");
		assertEquals(b1.playerHand.size(), 0);
		assertEquals(b1.bankerHand.size(), 0);
	}
	
	// This tests BaccaratGameEvaluateWinnings method in BaccaratGame class.
	@Test
	void BaccaratGameEvaluateWinnings1() {
		BaccaratGame b1 = new BaccaratGame();
		b1.currentBet = 500.0;
		b1.Bet = "Banker";
		double howMuchWin = b1.evaluateWinnings();
		if (b1.winner.equals("Player")) {
			assertEquals(howMuchWin, -500.0);
		}
		else if (b1.winner.equals("Banker")) {
			assertEquals(howMuchWin, 475.0);
		}
		else {
			
			assertEquals(howMuchWin, -500.0);
		}
	}
	
	// This tests BaccaratGameEvaluateWinnings method in BaccaratGame class.
	@Test
	void BaccaratGameEvaluateWinnings2() {
		BaccaratGame b1 = new BaccaratGame();
		b1.currentBet = 100.0;
		b1.Bet = "Draw";
		double howMuchWin = b1.evaluateWinnings();
		if (b1.winner.equals("Player")) {
			assertEquals(howMuchWin, -100.0);
		}
		else if (b1.winner.equals("Banker")) {
			assertEquals(howMuchWin, -100.0);
		}
		else {
			
			assertEquals(howMuchWin, 800.0);
		}
	}
	
}
