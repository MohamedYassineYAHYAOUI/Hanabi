package fr.umlv.clue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.umlv.card.Card;


/**
 * @author mohamed
 * class for object clue of a player, extends AskClues
 * defines Arraylist of Arraylist of clues
 */
public class Clues extends AskClues{
	private final ArrayList<ArrayList<Card>> cluesList;
	
	
	 /**
	 * constructs an object Cluee, cluelist is arraylist of two arayliste type of clues
	 *each contains cards that was given as clues  
	 */
	public Clues() {
		 this.cluesList =  new ArrayList<ArrayList<Card>>();
		 for (int i = 0; i < ClueTypes.values().length; i++) {
			 cluesList.add(new ArrayList<Card>());
		}
	 }
		
	 
	/**
	 * method to add clue to the specific list of clues
	 * @param cards list of card clues to add
	 * @param clueType enum type of clue to add card in (context of the clue for the card )
	 */
	public void addClue( List<Card> cards, ClueTypes clueType) {
		 Objects.requireNonNull(cards);
		 Objects.requireNonNull(clueType);
		 for (Card card : cards) {
			 cluesList.get(clueType.ordinal()).add(card);
		}
	}
	 
	 /**
	 * remove all the clues about a specific card from the clues list
	 * @param card card to remove from the list of clues
	 */
	public void removeClue(Card card) {
		 Objects.requireNonNull(card);
		 for (int i = 0; i < ClueTypes.values().length; i++) {
			 cluesList.get(i).remove(card);
		}
	 }
	
	
	/**
	 * determens if cluesList has a specific clueType on card
	 * @param card card to check if we have clue for
	 * @param clueType specific clue type to check 
	 * @return boolean  true if has a clue on card, else false
	 */
	public boolean hasClueOnCard(Card card, ClueTypes clueType) {
		Objects.requireNonNull(card);
		return cluesList.get(clueType.ordinal()).contains(card);
	}

	 
	 

	
}
