package fr.umlv.hanabi;


interface SimpleGameController {

	
	/**
	 * Initialize the start of the game, in graphic mode or console mode
	 */
	public void startGame();
	
	/**
	 * checks if the game still valid
	 * @return true of game is valid, else false 
	 */
	public boolean gameIsValid();
	
	/**
	 * update the player turn 
	 */
	public void updateGame();
	
	
	/**
	 * manage the choices of the player, and selects the specific actions to play 
	 */
	public void manageActions();
		
}
