package fr.umlv.clue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import fr.umlv.card.Card;
import fr.umlv.card.CardColor;
import fr.umlv.player.Hand;

abstract class AskClues {
	
	/**
	 * Asks the player helping the color of the cards to give clues about, only accepts valid entry
	 * and determines the specific cards to register as clues for the player to help
	 * uses streams to filler and collects clues
	 * @param playerHand the hand of the player to help
	 * @param color to get
	 * @return list of cards concerned about the clue given
	 */
	public List<Card> getColorClues(Hand playerHand, CardColor color){
		while(true) {	
			try{
				var list = Arrays.stream(playerHand.getHand()).filter(s->s.sameColor(color))
															.collect(Collectors.toList());
				if(!list.isEmpty()) {
					return list;
				}
			}catch(Exception e) {
				System.out.println("clue color is not valid, retry!");	
			};
		}	
	}
	

	
	
	
	
	/**
	 * Asks the player helping the value of the cards to give clues about, only accepts valid entry
	 * and determines the specific cards to register as clues for the player to help
	 * uses streams to filter and collect clues
	 * @param playerHand the hand of the player to help
	 * @param number number to get
	 * @return list of cards concerned about the clue given
	 */
	public List<Card> getNumberClues(Hand playerHand, int number) {
		Objects.requireNonNull(playerHand);
		while(true) {	
			try {
				var list = Arrays.stream(playerHand.getHand()).filter(s->s.sameValue(number))
											.collect(Collectors.toList());
				if(!list.isEmpty()) {
					return list;
				}
			}catch(Exception e) {
				System.out.println("clue number is not valid, retry !");
			}
		}	
	}
	
	
	
}
