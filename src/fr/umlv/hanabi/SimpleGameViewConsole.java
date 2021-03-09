package fr.umlv.hanabi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;


import fr.umlv.card.Card;
import fr.umlv.card.CardColor;
import fr.umlv.clue.ClueTypes;
import fr.umlv.player.Hand;
import fr.umlv.player.Player;
import fr.umlv.zen5.ApplicationContext;


public class SimpleGameViewConsole implements SimpleGameView{
	private final SimpleGameData data;
	
	public SimpleGameViewConsole(SimpleGameData data) {
		Objects.requireNonNull(data);
		this.data = data;
	}
	
	
	/**
	 * show the hand of player
	 * @param hand hand to show
	 * @return string of the hand to show
	 */
	static private String showHand(Hand hand) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < hand.getNumberOfCards(); i++) {
			str.append("card number "+(i+1)+" ").append(hand.getHand()[i].writeCard()).append("\n");
		}
		return str.toString();
	}
	
	 /**
	  * finds all the clues about card 
	 * @param card card to show clue about
	 * @param data of the game
	 * @return String of the clue about the card
	 */
	private static String clueOnCard(Card card, SimpleGameData data) {
		 Objects.requireNonNull(card);
		 var builder = new StringBuilder();
		 for (var clueType : ClueTypes.values()) {
			if( data.currentPlayer().getPlayerClues().hasClueOnCard(card, clueType)) {
				if( clueType.equals(ClueTypes.Color)) {
					builder.append(" "+clueType + " : "+card.getColor());	
				}else {
					builder.append(" "+clueType + " : "+card.getValue());	
				}
				builder.append("|");
			}
		}
		 return builder.toString();
	 }
	
	/**
	 * hide curent player hand
	 * @param data of the game
	 * @return hand to hide
	 * @return string of the hand to hide
	 */
	static private String hideHand(Hand hand, SimpleGameData data) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < hand.getNumberOfCards(); i++){
			str.append("card number "+(i+1)).
			append(clueOnCard(hand.getHand()[i], data)).
			append("\n");		
		}
		return str.toString();
	}
	
	
	/**
	 * Shows the cards present on the board following color and number order 
	 * and the number of red and blue tokens left
	 * color order : first placed / number order : normal order
	 * @return string of the fireworks cards and tokens values
	 */
	
	private String drawBoardConsole(){
		StringBuilder str = new StringBuilder();
		
		for (var set :data.board().getGameBoard().entrySet() ) {
			str.append("Set color :").append(set.getKey()).append("\n");
			for (int i = 0; i < set.getValue(); i++) {
				str.append(i+1).append(" | ");
			}
			str.append("\n");
		}
		str.append("Blue tokens :").append(data.board().getBlueTokens()).append("\n");
		str.append("Red tokens : ").append(data.board().getRedTokens()).append("\n");
		return str.toString();
	}
	
		/**
		 * Show the top card in the bin if there is one, else shows Empty!
		 * @return string of the card on top of the bin, or indication of empty bin 
		 */
	 private String showBinConsole() {
			StringBuilder str = new StringBuilder();
			if( data.board().lastCardBin() != null) {
				str.append("Latest card in the bin:\n").append(data.board().lastCardBin().writeCard());
			}else {
				str.append("Bin is empty !");
			}
			return str.toString();
		}
	
	
	
	/**
	 * draw the hands of the players, state of the hand depends on current player
	 */
	public void drawConsole(){
		System.out.println("Game Board : \n");
		System.out.println(drawBoardConsole());
		System.out.println(showBinConsole()+"\n");
		for(var player: data.players()){
			System.out.println("player "+player.name()+" hand :");
			if(player.equals(data.currentPlayer())){
				System.out.println(hideHand(player.getHand(), data));
			}else {
				System.out.println(showHand(player.getHand()));
			}
		}
	}
	
	
	/**
	 * create a string of the possible actions of the round
	 * @param data information of the game
	 */
	public void drawActions(SimpleGameData data) {
		Objects.requireNonNull(data);
		System.out.println("Player "+data.currentPlayer().name()+" trun, select action :");
		if(data.board().getBlueTokens() < 8) {
			System.out.println("Discard card (enter d)");
		}
		if(data.board().getBlueTokens() >0) {
			System.out.println("Give clue (enter c)");
		}
		System.out.println("Play card (enter p)");
	}

	
	/**
	 * lets user decide which player to give clue to, accept only valid entry
	 * @return player chosen
	 */
	public Player decidePlayer() {
		System.out.println("Enter player name to give clue to : ");
		BufferedReader  buffer  = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try {
				var chosen = buffer.readLine();
				for (int i = 0; i < data.players().length; i++) {
					if(!data.currentPlayer().name().equals(chosen) &&
							data.players()[i].name().equals(chosen)	) {
						return data.players()[i];}
				}
			}catch(Exception e) {
				System.out.println("Player name is not valid, retry !");}
		}
	}
	
	/**
	 * lets user decide the type of clue to give, accpet only valid entry
	 * @return string of the type of clue giving 
	 */
	public ClueTypes decideClueType(){
		BufferedReader  buffer  = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			System.out.println("Which Clue type do you want to give \"Color\" or \"Number\" ? ");
			try {	
				var chosen = buffer.readLine();				
				if( chosen.equals("Color") ||  chosen.equals("Number") ) {
					return ClueTypes.valueOf(chosen);
				}
			}catch(Exception e) {
				System.out.println("Entry invalide, please try again !");
			}
		}
	}
	
	/**
	 * show score at end of game, in terminal mode
	 */
	public void showScore() {
		if( data.board().boardIsFull()) {
			System.out.println("congratulations you have won !");
		}
		 System.out.println("your final score is :"+data.board().numberOfCardsPlaced());
	}
	
	
	
	/**
	 *ask for card color from hand
	 *@param hand to ask colors about
	 *@return CardColor chosen
	 */

	public CardColor askForColor(Hand hand) {
		Objects.requireNonNull(hand);
		BufferedReader  buffer  = new BufferedReader(new InputStreamReader(System.in));
		while(true) {	
			System.out.println("write clue color :");
			try{
				var color = CardColor.valueOf(buffer.readLine());
				if(hand.ColorInHand(color)) {
					return color;
				}
			}catch(Exception e) {
				System.out.println("clue color is not valid, retry!");	
			};
		}	
	}
	/**
	 *ask for card value from hand
	 *@param hand to ask values about
	 *@return Integer value chosen
	 */

	public int askForValue(Hand hand) {
		Objects.requireNonNull(hand);
		BufferedReader  buffer  = new BufferedReader(new InputStreamReader(System.in));
		while(true) {	
			System.out.println("write clue number :");
			try {
				var number = Integer.parseInt(buffer.readLine());
				if(hand.valueInHand(number)) {
					return number;
				}else {
					System.out.println("clue number is not player hand, retry !");
				}
			}catch(Exception e) {
				System.out.println("clue number is not valid, retry !");
			}
		}
	}


	@Override
	public float getWindowLength() {
		return 0;
	}
	@Override
	public float getWindowWidth() {
		return 0;
	}
	@Override
	public ApplicationContext context(){
		return null;
	}
}
