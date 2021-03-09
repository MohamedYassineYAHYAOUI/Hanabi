package fr.umlv.hanabi;

import fr.umlv.card.CardColor;
import fr.umlv.clue.ClueTypes;
import fr.umlv.player.Hand;
import fr.umlv.player.Player;
import fr.umlv.zen5.ApplicationContext;

/**
 * @author mohamed
 *
 */
interface SimpleGameView {

	
	
	/**
	 * draw the game in graphic or terminal mode 
	 */
	void drawConsole();
	
	/**
	 * draw the allowed actions 
	 * @param data {@link SimpleGameData} data of the game
	 */
	void drawActions(SimpleGameData data);
		
	/**
	 * draws the choices of players
	 * @return Player in mode terminal, and null in graphic mode
	 */
	public Player decidePlayer() ;
	
	/**
	 * draws the choices of clues
	 * @return ClueTypes in mode terminal, and null in graphic mode
	 */
	ClueTypes decideClueType();
	
	/**
	 * draw the score at the end of the game
	 */
	public void showScore();
	
	/**
	 * draw the choices of color from hand
	 * @param hand hand to draw from 
	 * @return CardColor in terminal mode, null in graphic mode
	 */
	public CardColor askForColor(Hand hand);
	
	/**
	 * draw the value choices from the hand
	 * @param hand hand to draw from 
	 * @return CardColor in terminal mode, 0 in graphic mode
	 */
	public int askForValue(Hand hand);

	
	/**
	 * @return window length in graphic mode
	 */
	float getWindowLength();
	/**
	 * @return window width in graphic mode
	 */
	float getWindowWidth();
	
	/**
	 * @return {@link ApplicationContext} Application context of the game
	 */
	 public ApplicationContext context();

}
