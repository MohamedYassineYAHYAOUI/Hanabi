package fr.umlv.card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import fr.umlv.hanabi.Coordinates;

/**
 * @author mohamed
 * Class for the object Deck of the card, defines the arrayList of the cards,
 * and coordinates of the deck
 */
public class Deck {
	private final ArrayList<Card> deck = new ArrayList<Card>();
	private final Coordinates coordDeck = new Coordinates(90, 20);
	
	
	/**
	 * fill the arraylist of the deck with all the cards 
	 */
	public void fillDeck() {
		int numbers[] = {1,1,1,2,2,3,3,4,4,5};
		for(CardColor col: CardColor.values()) {
			for (int i: numbers) {
				deck.add(new Card(i, col, coordDeck));
			}
		}
	}
	/**
	 * method shuffle of the deck, it mix the different cards in the deck
	 */
	public void shuffle() {				
		Collections.shuffle(deck);
	}
	
	/**
	 * method to determine if the deck is empty
	 * @return return boolean true if the deck is empty, else false
	 **/
	public boolean isEmpty() {
		return deck.isEmpty() ;
	}
	
	/**
	 * getter for the coordinates of the Deck
	 * @return Coordinates of the deck
	 */
	public Coordinates getCoordDeck() {
		return coordDeck;
	}
	
	/**
	 *  ask for a specific card from the deck
	 * @param cardIndex the index of the card in the deck
	 * @return Card asked for, or null if the card doesn't exist
	 */
	public Card specificCard(int cardIndex) {
		if( cardIndex >= deck.size()) {
			return null;
		}
		return deck.get(cardIndex);
	}
	
	/**
	 * method that draw the top card from the deck,
	 * must verify that the deck is not empty first
	 * @return the card drawn from the top
	 * @throws IOException if the deck is empty
	 */
	public Card drawCard() throws IOException {
		if(deck.isEmpty()) {
			throw new IOException("the deck is empty");
		}
		var card = deck.get(0);
		deck.remove(0);
		return card;
	}
	
	public int cardsLeft() {
		return deck.size();
	}
	
}


