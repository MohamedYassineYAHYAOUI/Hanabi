package fr.umlv.hanabi;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

import fr.umlv.card.Card;
import fr.umlv.card.CardColor;
import fr.umlv.clue.ClueTypes;
import fr.umlv.player.Hand;
import fr.umlv.player.Player;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;


/**
 * @author mohamed
 * abstract class that assembles the common methods between {@link SimpleGameControllerConsole}
 * and {@link SimpleGameControllerGraphique}, also needed method for both classes to function
 *  => certain methods can be presented on two versions 
 */
abstract class AbstractGameController {
	
	
	private final SimpleGameData data;
	private final SimpleGameView view ;
	
	
	/** 
	 * constructor for the object {@link AbstractGameController}, uses data and view
	 * @param data {@link SimpleGameData} the games data
	 * @param view {@link SimpleGameView} the games console/graphic interface 
	 */
	AbstractGameController(SimpleGameData data, SimpleGameView view ) {
		Objects.requireNonNull(data);
		Objects.requireNonNull(view);
		this.data = data;
		this.view = view;
	}
	
	/**
	 * determens the click position, this method is not in SimpleGameControllerGraphique 
	 * because its needed in the methods decideCard, choosePlayer, chooseClueType... which are called in
	 * SimpleGameControllerGraphique,  
	 * @param context ApplicationContext of the game
	 * @return Coordinates of the position, or null if no event accured
	 */
	static Coordinates clickPosition(ApplicationContext context){
		Objects.requireNonNull(context);
		Point2D.Float location;
		Event event = context.pollOrWaitEvent(10);
		if (event == null ) {  // no event
			return null;
	    }
		Action action = event.getAction();
	    if (action == Action.POINTER_DOWN) {
	    	 location = event.getLocation();
	    	 return new Coordinates(location.x, location.y);
	    }
	    return null;
	}
	
	
	/**
	 * lets the player decide the card from his hand, accept valid entry
	 * @param data information of the game
	 * @param scr choices of the player
 	 * @return number of card chosen 
	 */
	private static int decideCard(int numberOfCards) {
		System.out.println("Select card number between 1 and "+ numberOfCards+" : ");
		BufferedReader  buffer  = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try {
				var choice = Integer.parseInt(buffer.readLine());
				if(choice >= 1 && choice <= numberOfCards) {
					return choice;
				}
			}catch(Exception e) {
				System.out.println("Choice is not valid , retry !");
			}
		}
	}	
	
	
	/**
	 * lets the player decide the card from his hand, accept valid entry
	 * @param context {@link ApplicationContext} application context of the game
	 * @param data information of the game
	 * @param view represtation of the context
 	 * @return number of card chosen 
	 */
	private static int decideCard(ApplicationContext context, SimpleGameData data, SimpleGameView view){
		Objects.requireNonNull(context);
		context.renderFrame(graphics->{
			graphics.drawString("Choose card from hand",view.getWindowWidth()/3 , 3*view.getWindowLength()/4 +90);
		});
		while(true) {
			var position = clickPosition(context);
			if(position != null){
				for (int i = 0; i < data.currentPlayer().getHand().getHand().length; i++) {
				var card = data.currentPlayer().getHand().getHand()[i];
				var cardX = data.currentPlayer().getCoordinates().getX() + i*(Card.cardWidthX()+5);
					if((position.getX() > cardX) && (position.getX() <( cardX + Card.cardWidthX()))
							&& (position.getY()> card.cardY()) && (position.getY() <( card.cardY() + Card.cardLengthY()))){
						return i+1;}
				}
			}
		}
	}
		
	/**
	 * choose a player form possible players in graphic mode, accepts only valid entry
	 * @param context {@link ApplicationContext} application context of the game
	 * @param data {@link SimpleGameData} data of the game
	 * @param view {@link SimpleGameView} drawing interface of the game
	 * @return player chosen 
	 */
	private static Player choosePlayer(ApplicationContext context, SimpleGameData data, SimpleGameView view) {
		Objects.requireNonNull(context);
		var width = view.getWindowWidth();
		var length = view.getWindowLength();
		while(true){
			var position = clickPosition(context);
			if(position != null){
				for (int i = 0; i < data.players().length; i++) {
					if(!data.currentPlayer().equals(data.players()[i])){
						if((position.getX() > width/3 +i*(width/15)) && (position.getX() <( width/3 +i*(width/15) + width/15))
								&& (position.getY()>  3*length/4 +50) && (position.getY() <(  3*length/4 +50 + length/40 ))){
							return  data.players()[i];}
					}
				}
			}
		}	
	}

	/**
	 * choose clue type in graphic interface, accepts only valid entry
	 * @param context {@link ApplicationContext} appliction context of the game
	 * @param data {@link SimpleGameData} data of the game 
	 * @param view {@link SimpleGameView} drawing interface of the game
	 * @return Clue type chosen by the player
	 */
	private static ClueTypes chooseClueType(ApplicationContext context, SimpleGameData data, SimpleGameView view) {
		Objects.requireNonNull(context);
		var width = view.getWindowWidth();
		var length = view.getWindowLength();
		while(true){
			var position = clickPosition(context);
			if(position != null){
				if((position.getX() > (width/3 +20) && (position.getX() <( width/3 +20+width/15))
						&& (position.getY()>  3*length/4 +50) && (position.getY() <(  3*length/4 +50 + length/40 )))){
					return ClueTypes.Color;
				}else if((position.getX() > 7*width/15 +100) && (position.getX() <(  7*width/15 +100 + width/15))
						&& (position.getY()>  3*length/4 +50) && (position.getY() <(  3*length/4 +50 + length/40 ))) {
					return ClueTypes.Number;
				}
			}
		}
	}
	
	/**
	 *defines the action and the specific steps of discarding a card
	 *@param data information of the game 
	 *@param view view interaction with the player to precise choices 
	 *@param scr scanner to collect choices 
	 *@return boolean true of the action is possible, else false
	 */
	public static boolean discardCard(SimpleGameData data, SimpleGameView view){
		Objects.requireNonNull(data);
		Objects.requireNonNull(view);
		if( data.board().getBlueTokens() <8 ){
			int cardNumber;
			if( view instanceof SimpleGameViewConsole) {
				cardNumber = decideCard(data.currentPlayer().getHand().getNumberOfCards());
			} else {
				cardNumber = decideCard(view.context(), data , view);
			}
			data.board().placeCardToBin(data.currentPlayer().chooseCard(cardNumber));
			data.currentPlayer().drawCard(data.deck());
			data.board().addBlueToken();			
			return true;
		}
		return false;
	}
	/**
	 *defines the action and the specific steps of playing a card
	 *@param data information of the game 
	 *@param view view interaction with the player to precise choices 
	 *@param scr scanner to collect choices 
	 *@return boolean true of the action is possible, else false
	 */
	public static boolean playCard(SimpleGameData data, SimpleGameView view){
		Objects.requireNonNull(data);
		Objects.requireNonNull(view);
		if( data.currentPlayer().getHand().getNumberOfCards() >0) {
			int cardNumber;
			if( view instanceof SimpleGameViewConsole) {
				cardNumber = decideCard(data.currentPlayer().getHand().getNumberOfCards());
			} else {
				cardNumber = decideCard(view.context(), data, view );
			}
			var card = data.currentPlayer().chooseCard(cardNumber);
			try {
				data.board().placeCardToBoard(card);
			}catch(IllegalAccessException e) {
				data.board().placeCardToBin(card);
				data.board().loseRedToken();
			}
			data.currentPlayer().drawCard(data.deck());	
			return true;
			
		}		
		return false;
	}

	
	
	
	/**
	 * allows to choose player either in mode graphic or console
	 * @param view {@link SimpleGameView} drawing interface of the game 
	 * @param data {@link SimpleGameDate} data of the game 
	 * @param context {@link ApplicationContext} Application context of the game
	 * @return the player chosen
	 */
	private static Player askForPlayer(SimpleGameView view, SimpleGameData data, ApplicationContext context) {
		Objects.requireNonNull(view);
		Objects.requireNonNull(data);
		if( view instanceof SimpleGameViewConsole) {
			return view.decidePlayer(); 
		}else {			
			view.decidePlayer();
			return choosePlayer(context, data, view);
		} 
	}
	
	/**
	 * allows to ask for a clue in graphic mode or in console mode
	 * @param view {@link SimpleGameView}drawing interface of the game 
	 * @param data {@link SimpleGameData} data of the game
	 * @param context {@link ApplicationContext} application context of the game
	 * @return Clue type chosen
	 */
	private static ClueTypes askForClueType(SimpleGameView view, SimpleGameData data, ApplicationContext context) {
		Objects.requireNonNull(view);
		Objects.requireNonNull(data);
		if( view instanceof SimpleGameViewConsole) {
			return view.decideClueType();
		}else {			
			view.decideClueType();
			return chooseClueType(context, data, view);
		} 
	}
	
	
	/**
	 * choose clue color to give from the hand of the player to help 
	 * @param view {@link SimpleGameView}drawing interface of the game 
	 * @param data {@link SimpleGameData} data of the game
	 * @param context {@link ApplicationContext} application context of the game
	 * @param hand player hand 
	 * @return a Color that was chosen to give clue about
	 */
	private static CardColor chooseClueColor(ApplicationContext context, SimpleGameData data, SimpleGameView view, Hand hand ) {
		Objects.requireNonNull(context);
		context.renderFrame(graphics->{
			graphics.drawString("Choose color",view.getWindowWidth()/3 , 3*view.getWindowLength()/4 +90);
		});
		var width = view.getWindowWidth();
		var length = view.getWindowLength();
		while(true) {
			var position = clickPosition(context);
			if(position != null){
				var index =0;
				for (var color : CardColor.values()) {
					if(hand.ColorInHand(color)) {
						if(((position.getX() > width/3 +index*(width/15)) && (position.getX() <( width/3 +index*(width/15 ) + width/15))
								&& (position.getY()>  3*length/4 +50) && (position.getY() <(  3*length/4 +50 + length/40 )))){
							return CardColor.values()[index];}
					}
					index++;
				}
			}
		}	
	}
	
	/**
	 * allows to chose for color clue i graphic mode or in console mode 
	 * @param playerHand player hand to give clue to
	 * @param view {@link SimpleGameView}drawing interface of the game 
	 * @param data {@link SimpleGameData} data of the game
	 * @param context {@link ApplicationContext} application context of the game
	 * @return a Color that was chosen to give clue about
	 */
	private static CardColor askForColor(Hand playerHand, SimpleGameView view, SimpleGameData data, ApplicationContext context){
		Objects.requireNonNull(playerHand);
		Objects.requireNonNull(view);
		Objects.requireNonNull(data);
		
		if(view instanceof SimpleGameViewConsole) {
			return view.askForColor(playerHand);
		}else {
			view.askForColor(playerHand);
			return chooseClueColor(context, data, view, playerHand);
		}
	}
	
	/**
	 * choose clue value to give from the hand of the player to help 
	 * @param view {@link SimpleGameView}drawing interface of the game 
	 * @param data {@link SimpleGameData} data of the game
	 * @param context {@link ApplicationContext} application context of the game
	 * @param hand player hand 
	 * @return a card value that was chosen to give clue about
	 */
	private static int chooseClueValue(ApplicationContext context, SimpleGameData data, SimpleGameView view, Hand hand ) {
		Objects.requireNonNull(context);
		context.renderFrame(graphics->{
			graphics.drawString("Choose value",view.getWindowWidth()/3 , 3*view.getWindowLength()/4 +90);
		});
		var width = view.getWindowWidth();
		var length = view.getWindowLength();
		while(true) {
			var position = clickPosition(context);
			if(position != null){
				for (int i = 1; i < 6; i++) {
					if(hand.valueInHand(i)) {
						if((position.getX() > width/3 +(i-1)*(width/15) && (position.getX() <( width/3 +(i-1)*(width/15) + width/15))
								&& (position.getY()>  3*length/4 +50) && (position.getY() <(  3*length/4 +50 + length/40 )))){
							return i;}
					}
				}
			}
		}	
	}
	
	/**
	 * allows to chose for value clue in graphic mode or in console mode 
	 * @param playerHand player hand to give clue to
	 * @param view {@link SimpleGameView}drawing interface of the game 
	 * @param data {@link SimpleGameData} data of the game
	 * @param context {@link ApplicationContext} application context of the game
	 * @return a card value that was chosen to give clue about
	 */
	private static int askForValue(Hand playerHand, SimpleGameView view, SimpleGameData data, ApplicationContext context){
		Objects.requireNonNull(playerHand);
		Objects.requireNonNull(view);
		Objects.requireNonNull(data);
		
		if(view instanceof SimpleGameViewConsole) {
			return view.askForValue(playerHand);
		}else {
			view.askForValue(playerHand);
			return chooseClueValue(context, data, view, playerHand);
		}
	}
	
	
	/**
	 *defines the action and the specific steps of giving a clue
	 *@param data information of the game 
	 *@param view view interaction with the player to precise choices 
	 *@return boolean true of the action is possible, else false
	 */
	public static boolean giveClue(SimpleGameData data, SimpleGameView view) {
		Objects.requireNonNull(data);
		Objects.requireNonNull(view);
		if( data.board().getBlueTokens() >0 ) {
			Player playerToHelp = askForPlayer(view, data, view.context());
			ClueTypes clueType = askForClueType(view, data, view.context() );
			if(clueType.equals(ClueTypes.Color)) {
				var cardColor = askForColor(playerToHelp.getHand(), view, data, view.context());
				playerToHelp.getPlayerClues().addClue(playerToHelp.getPlayerClues()
						.getColorClues(playerToHelp.getHand(), cardColor), ClueTypes.Color);
			}else if(clueType.equals(ClueTypes.Number)) {
				var cardValue = askForValue(playerToHelp.getHand(), view, data, view.context());
				playerToHelp.getPlayerClues().addClue(playerToHelp.getPlayerClues()
						.getNumberClues(playerToHelp.getHand(), cardValue), ClueTypes.Number);
			}
			data.board().loseBlueToken();
			return true;
		}
		return false;
	}
	

	/**
	 * Initialize the start of the game, in graphic mode or console mode
	 */
	public void startGame(){
		if(view instanceof SimpleGameViewConsole) {
			data.initStartGame();
		}else {
			data.initStartGame(view.getWindowLength(), view.getWindowWidth());
		}
		view.drawConsole();
	}
	
	

	 
	 /**
	 * update the player and the console of the game
	 */
	public void updateGame() {
		data.nextPlayer();
		view.drawConsole();

	}
		 
	/**
	 * check if the game is still valid
	 * @return boolean true if game still valid , false else 
	 */
	public boolean gameIsValid() {
		return  data.board().redTokensValid() && !data.board().boardIsFull() 
				&& !data.deck().isEmpty();
	}
	
	/**
	 * @return data {@link SimpleGameData} of the game
	 */
	SimpleGameData data() {
		return data;
	}
	/**
	 * @return view {@link SimpleGameView} of the game
	 */
	SimpleGameView view() {
		return view;
	}
	
}
