package application.model;

import java.util.ArrayList;

public class Player implements Comparable<Player> {
    public static final int HAND_SIZE = 5;
    
    private String playerName; // This is the ID
    private final ArrayList<Card> cards = new ArrayList<>();
    private HandType handType;
    public Integer wins = 0;
    
    public Player(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
    
    public HandType getHand() {
    	return this.handType;
    }
    
    public void addCard(Card card) {
        if (cards.size() < HAND_SIZE) cards.add(card);
    }
    
    public void discardHand() {
        cards.clear();
        handType = null;
    }
    
    public int getNumCards() {
        return cards.size();
    }
    
    public int getHandOrdinal() {
    	return this.handType.ordinal();
    }

    /**
     * If the hand has not been evaluated, but does have all cards, 
     * then evaluate it.
     */
    public HandType evaluateHand() {
        if (handType == null && cards.size() == HAND_SIZE) {
            handType = HandType.evaluateHand(cards);
        }
        return handType;
    }
    
    public HandType getHandType() {
    	return this.handType;
    }
    
    public void addWin() {
    	this.wins += 1;
    }
    public int getWins() {
    	return this.wins;
    }
    public void setName(String name) {
    	this.playerName = name;
    }

    /**
     * Hands are compared, based on the evaluation they have.
     */
    @Override
    public int compareTo(Player otherPlayer) {
       return otherPlayer.getWins() - this.wins;
    }
}
