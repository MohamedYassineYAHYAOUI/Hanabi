package fr.umlv.hanabi;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;


/**
 * @author mohamed
 * abstract class to initilize the game
 *
 */
abstract class InitGame {
	
	
	/**
	 * allows to run the game on console
	 * @param numberOfPlayer number of player in the game
	 */
	static void runOnConsole(int numberOfPlayer){
		SimpleGameData data = new SimpleGameData(numberOfPlayer);
		SimpleGameView view = new SimpleGameViewConsole(data);
		SimpleGameController controller = new SimpleGameControllerConsole(data, view);
			playGame( controller);
			lastRound(controller, data, numberOfPlayer);
			endGame( view);
	}
	
	/**
	 * allows to run the game on graphic mode
	 * @param numberOfPlayer number of player in the game
	 */
	static void runOnGraphique(int numberOfPlayer){
		Application.run(Color.lightGray, context->{
			
			ScreenInfo screenInfo = context.getScreenInfo();
			SimpleGameViewGraphique.redirectUserToTerminal(context,screenInfo.getWidth(), screenInfo.getHeight() );
			SimpleGameData data = new SimpleGameData(numberOfPlayer, screenInfo.getWidth(),screenInfo.getHeight());
			SimpleGameView view = new SimpleGameViewGraphique(data,context , screenInfo.getWidth(), screenInfo.getHeight());
			SimpleGameController controller = new SimpleGameControllerGraphique(data, view);
			playGame( controller);
			lastRound(controller, data, numberOfPlayer);
			endGame( view);
			while(true) {
				Event event  = context.pollEvent();
				if ( event != null && event.getAction() == Action.POINTER_DOWN) {  // exit on pointer_down (to avoid exit on pointer_up of the last event ) 
					context.exit(0);											  // need to to this to see the win/lose text and score text
					break;
				}	
			}												  
		}); 
		}
	
	
	
	/**
	 * Determines the number of players participating , accepts only valid entry
	 * @return integer of the number of player
	 */
	static public int choosePlayerNumber() {
		System.out.println("Enter number of players :");
		BufferedReader  buffer  = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try {
				var playerNumber = Integer.parseInt(buffer.readLine());
				if( playerNumber >=2 && playerNumber<= 5) {
					return playerNumber;
				}else {
					System.out.println("player number must be between 2 and 5");
				}
			}catch(Exception e){
				System.out.println("Choice is invalid, try again !");
			}
		}
	}
	
	/**
	 * asks the player number playerIndex thier name
	 * @param playerIndex index of player to ask
	 * @return String name of player
	 */
	static String askPlayerName(int playerIndex) {
		System.out.println("Please enter player "+playerIndex+" name :");
		BufferedReader  buffer  = new BufferedReader(new InputStreamReader(System.in));
		var name ="";
		try{
			name =buffer.readLine();
		}catch(Exception e) {
			System.out.println("Invalid name, replaced with number");
			name = Integer.toString(playerIndex);
		}
		return name;
		
	}

	
	/**
	 * allows to continually play the game
	 * @param controller SimpleGameController controller of the game
	 */
	static void playGame( SimpleGameController controller) {
		Objects.requireNonNull(controller);
		controller.startGame();	
		while(controller.gameIsValid()) {
			controller.manageActions();
			controller.updateGame();
		}
	}

	/**
	 * allows to play the last round of the game in case no card left in deck 
	 * @param controller SimpleGameController controller of the game
	 * @param data  {@link SimpleGameData} data of the game
	 * @param numberOfPlayer number of player in the game
	 */
	static void lastRound(SimpleGameController controller, SimpleGameData data, int numberOfPlayer) {
		Objects.requireNonNull(controller);
		Objects.requireNonNull(data);
		if( numberOfPlayer <2 || numberOfPlayer> 5) {
			throw new IllegalArgumentException("number of player must be between 2 and 5");
		}
		for(int i=0;data.deck().isEmpty() && data.board().redTokensValid() 
				&& !data.board().boardIsFull()&& i<numberOfPlayer; i++) {
			controller.manageActions();
			controller.updateGame();
		}
	}
	
	/**
	 * show the score at the end of the game
	 * @param view {@link SimpleGameView} view of the game
	 */
	static void endGame( SimpleGameView view) {
		Objects.requireNonNull(view);
		view.showScore();
	}
	
	/**
	 * allows to select the game mode 
	 * @return String of the game mode
	 */
	static String chooseGameMode(){
		BufferedReader  buffer  = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Choose game mode, Graphique[g] or Consol[c] :");
		while(true) {
			try {	
				var choice = buffer.readLine();
				if( choice.equals("g") || choice.equals("c")) {
					return choice;
				}
			}catch(Exception e) {
				System.out.println("game mode not valid, please try again !");
			}
		}
	}

	/**
	 * @param data {@link SimpleGameData} data of the game
	 * @param view {@link SimpleGameView} view of the game
	 * @return a specific contorller of the game
	 */
	static SimpleGameController controllerMode(SimpleGameData data, SimpleGameView view){
		Objects.requireNonNull(data);
		Objects.requireNonNull(view);
		if( view instanceof SimpleGameViewConsole) {
			return new SimpleGameControllerConsole(data, view);
		}else {
			return new SimpleGameControllerGraphique(data, view);
		}
	}
	

	
}
