package fr.umlv.player;

import java.util.Arrays;
import java.util.Objects;
import fr.umlv.card.Card;
import fr.umlv.card.CardColor;
import fr.umlv.card.Deck;
import fr.umlv.hanabi.Coordinates;

public class Hand{
	private final Card []hand;
	private int indexOfCard = 0; // index of last card in array

		
	/**
	 * constructor of the object hand using number of card in hand
	 * @param numberOfCardHand number of cards in hand
	 */
	public Hand(int numberOfCardHand) {
		if(numberOfCardHand <1 || numberOfCardHand >5   ) {
			throw new IllegalArgumentException("hand length must be between 1 and 5");
		}
		 this.hand = new Card[numberOfCardHand];
		 this.indexOfCard = 0;
	}
	
	
	/**
	 * initial fill hand method at the start of the g
	 * @param deck iterator of deck,
	 * @param coordPlayer coordinates of player
	 */
	public void dealHand(Deck deck, Coordinates coordPlayer){
		Objects.requireNonNull(deck);
		if( indexOfCard != 0)
			throw new IllegalCallerException("hand is not empty");
		for (int i = 0; i < hand.length; i++) {
			if(!deck.isEmpty()) {
				try {
					var newCard = deck.drawCard();
					newCard.changeCoord(
							new Coordinates(coordPlayer.getX() + i*(Card.cardWidthX()+5), coordPlayer.getY()));
					hand[i] = newCard;
				}catch(Exception e){
					throw new IllegalCallerException("hand is full !");
				};
					
			}
		}
		indexOfCard =hand.length-1; 
	}
	

	/**
	 * adds a new card to hand of player
	 * @param newCard new card to be added to hand, if the hand is full, 	
	 * @param coordPlayer coordinates of player
	 * @throws IllegalAccessException if hand is full
	 */
	public void addCardToHand(Card newCard, Coordinates coordPlayer) throws IllegalAccessException  {
		Objects.requireNonNull(newCard, "new Card must not be null");
		if( indexOfCard < hand.length-1 ) { 
			if( indexOfCard > 0) {
				newCard.changeCoord(
						new Coordinates(coordPlayer.getX() + (indexOfCard+1)*(Card.cardWidthX()+5), coordPlayer.getY()));
				hand[indexOfCard+1] = newCard;
			}else {
				newCard.changeCoord(
						new Coordinates(coordPlayer.getX(), coordPlayer.getY()));
				hand[0] = newCard;
			}
			indexOfCard++; 
		}else {
			throw new IllegalAccessException("hand is full !");
		}
	}
	
	/**
	 * remove a card from the hand of player and returns it 
	 * @param cardNumber index of card to throw between 1 and number of cards in hand
	 * @throws IllegalArgumentException if the card to throw is not valid
	 * @return card thrown from the hand of the player
	 */
	public Card throwCard(int cardNumber) throws IllegalArgumentException {
		if( cardNumber < 1 || cardNumber >indexOfCard+1) {
			throw new IllegalArgumentException("index of card is invalid !");
		}
		Card cardToThrow = hand[cardNumber-1];
		for (int i = cardNumber-1; i < indexOfCard; i++) {
			hand[i+1].changeCoord(hand[i].getCoordinates());
			hand[i] = hand[i+1];
		}
		hand[indexOfCard] = null;
		indexOfCard--;
		return cardToThrow;	
	}
	
	/**
	 * getter for number of cards in hand
	 * @return number of cards in hand 
	 */
	public int getNumberOfCards() {
		return indexOfCard+1;
	}
	
	
	/**
	 * select card with index card Number
	 * @param cardNumber index of card to chose 
	 * @return Card selected
	 */
	public Card selectCard(int cardNumber) {
		if( cardNumber <0 || cardNumber >indexOfCard) {
			throw new IllegalArgumentException("Card number in not valid");
		}
		return hand[cardNumber];
	}
	
	
	/**
	 * checks if a certain color is in hand
	 * @param color to check 
	 * @return boolean true if color in hand, false else 
	 */
	public Boolean ColorInHand(CardColor color) {
		Objects.requireNonNull(color);
		for (Card card : hand) {
			if(card.getColor().equals(color)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * checks if a certain value is in hand
	 * @param value to check 
	 * @return boolean true if value in hand, false else 
	 */
	public Boolean valueInHand(int value) {
		if(value <1 || value >5) {
			throw new IllegalArgumentException("Card value in not valid");
		}
		for (Card card : hand) {
			if(card.getValue() == value) {
				return true;
			}
		}
		return false;
	}


	/**
	 * getter for object hand
	 * @return list of card forming hand
	 */
	public Card[] getHand() {
		return hand;
	}
	

	@Override
	public boolean equals(Object obj) {
		if( obj == null || !(obj instanceof Hand)) {
			return false;
		}
		var hand = (Hand) obj;
		return this.indexOfCard == hand.indexOfCard 
				&& Arrays.equals(this.hand, hand.hand);
	}
	
	@Override
	public int hashCode() {
		return indexOfCard ^ hand.hashCode();
	}
	
	
}
