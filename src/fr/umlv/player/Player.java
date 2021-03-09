package fr.umlv.player;


import java.util.Objects;
import fr.umlv.card.Card;
import fr.umlv.card.Deck;
import fr.umlv.clue.Clues;
import fr.umlv.hanabi.Coordinates;

public class Player{
	private final String name;
	private final Hand hand;
	private final Clues playerClues;
	private final Coordinates coordinates;

	
	public Player(int numberOfCardHand, String name, int indexPlayer, float windowLength, float windowWidth){
		Objects.requireNonNull(name);
		this.name = name;
		this.hand = new Hand(numberOfCardHand);
		this.playerClues = new Clues();
		switch(indexPlayer) {
		case 0: this.coordinates = new Coordinates(windowWidth/10, windowLength/2);
				break;
		case 1: this.coordinates = new Coordinates(windowWidth/10, 3*windowLength/4);
				break;
		case 2: this.coordinates = new Coordinates(7*windowWidth/10, 3*windowLength/4);
				break;
		case 3: this.coordinates = new Coordinates(7*windowWidth/10, windowLength/2);
				break;
		case 4: this.coordinates = new Coordinates(2*windowWidth/5, windowLength/10);
				break;
		default : this.coordinates = null;
				break;
		}
	}
	public Player(int numberOfCardHand, int indexPlayer) {
		this(numberOfCardHand, "", indexPlayer, 0, 0);
	}
	
	
	/**
	 * takes deck, and adds the upper card to player hand
	 * throws IllegalArgumentException deck is empty
	 * @param deck game's deck
	 */
	public void drawCard(Deck deck) {
		Objects.requireNonNull(deck, "deck must not be null");
		System.out.println("Player "+name+" has drawn a card");
		if(!deck.isEmpty()) {
			try {
				hand.addCardToHand(deck.drawCard(), coordinates);
				
			}catch(Exception e) {
				throw new IllegalArgumentException("deck is empty !");
			}
		}
	}
	
	/**
	 * choose card from player's hand
	 * @param cardNumber index of card
	 * @return the Card chosen from the player hand 
	 */
	public Card chooseCard(int cardNumber) {	
		Card card = null;
		try {
			card = hand.throwCard(cardNumber); 
			playerClues.removeClue(card);
		}catch(IllegalArgumentException e) {
			System.out.println("Card index is out of bound");
		}
		return card;
		
	}
	
	/**
	 * @return string name of player
	 */
	public String name() {
		return name;
	}

	/**
	 * getter for the hand
	 * @return Hand
	 */
	public Hand getHand() {
		return hand;
	}
	/**
	 * getter for player clues
	 * @return Clues player Clues
	 */
	public Clues getPlayerClues() {
		return playerClues;
	}

	/**
	 * getter for coordinates of player 
	 * @return player coordinates
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( obj == null || !(obj instanceof Player)) {
			return false;
		}
		var player = (Player) obj;
		return this.name.equals(player.name) && 
				this.hand.equals(player.hand);
	}
	
	
	@Override
	public int hashCode() {
		return hand.hashCode() ^ name.hashCode()^playerClues.hashCode();
	}
	
	
}
