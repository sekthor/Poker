package application.model;

import java.util.ArrayList;

public class CustomTest {

	public static void main(String[] args) {
		String[] h1 = { "QS", "QC", "3H", "3D", "7H" };
		String[] h2 ={ "QS", "QC", "TH", "3D", "TH" };
		ArrayList<Card> hand1 = makeHand(h1);
		ArrayList<Card> hand2 = makeHand(h2);
		Player p1 = new Player("Player 1");
		Player p2 = new Player("Player 2");
		
		for (Card i : hand1) {
			p1.addCard(i);
		}
		p1.evaluateHand();
		for (Card i : hand2) {
			p2.addCard(i);
		}
		p2.evaluateHand();
		
		Player winner = Winner.tiebreak(p1,p2);
		System.out.println(winner.getPlayerName());

	}
	
	private static ArrayList<Card> makeHand(String[] hand){
		ArrayList<Card> finalHand = new ArrayList<Card>();
		for (String cardString : hand) {
			finalHand.add(makeCard(cardString));
		}
		return finalHand;
	}
	
	private static Card makeCard(String in) {
		char r = in.charAt(0);
		Card.Rank rank = null;
		if (r <= '9') rank = Card.Rank.values()[r-'0' - 2];
		else if (r == 'T') rank = Card.Rank.Ten;
		else if (r == 'J') rank = Card.Rank.Jack;
		else if (r == 'Q') rank = Card.Rank.Queen;
		else if (r == 'K') rank = Card.Rank.King;
		else if (r == 'A') rank = Card.Rank.Ace;
		
		char s = in.charAt(1);
		Card.Suit suit = null;
		if (s == 'C') suit = Card.Suit.Clubs;
		if (s == 'D') suit = Card.Suit.Diamonds;
		if (s == 'H') suit = Card.Suit.Hearts;
		if (s == 'S') suit = Card.Suit.Spades;

		return new Card(suit, rank);
	}

}
