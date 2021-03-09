package fr.umlv.hanabi;

public class Hanabi extends InitGame{
	
	/**
	 * the main methode of the game, allows to choose number of players, 
	 * and the game mode, and runs the game accordingly
	 * @param args list of String input 
	 */
	public static void main(String[] args) {
		var numberOfPlayer = choosePlayerNumber();

		if( chooseGameMode().equals("c")){
			runOnConsole(numberOfPlayer);
		}else {
			runOnGraphique(numberOfPlayer);
		};
		
	}
}
