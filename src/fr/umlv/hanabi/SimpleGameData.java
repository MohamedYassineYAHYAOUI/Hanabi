package fr.umlv.hanabi;

import fr.umlv.board.Board;
import fr.umlv.card.Deck;
import fr.umlv.player.Player;

class SimpleGameData {

	private final int numberOfPlayers;
	private final int numberOfCardHand;
	private final Deck deck;
	private final Board board;
	private final Player []players;
	private int indexCurrentPlayer;

	
	SimpleGameData(int numberOfPlayer, float width, float hight){
		this.numberOfPlayers = numberOfPlayer;
		if( numberOfPlayer < 4) {
			this.numberOfCardHand = 5;
		}else {
			this.numberOfCardHand = 4;
		}
		this.deck = new Deck();
		this.board = new Board(width, hight);
		this.players = new Player[numberOfPlayer];
		this.indexCurrentPlayer = 0;
	}
	
	SimpleGameData(int numberOfPlayer) {
		this(numberOfPlayer, 0, 0);
	}
	
	/**
	 * determines the current player
	 * @return the current player playing
	 */
	Player currentPlayer() {
		return players[indexCurrentPlayer];
	}
	
	/**
	 * updates the player index, pointing to the current player
	 */
	void nextPlayer() {
		indexCurrentPlayer++;
		if( indexCurrentPlayer >= numberOfPlayers)
			indexCurrentPlayer =0;
		
	}
	
	/**
	 * @return list of players
	 */
	Player[] players() {
		return players;
	}
	

	
	/**
	 * the game deck
	 * @return  deck of card
	 */
	Deck deck(){
		return deck;
	}

	/**
	 * @return the board of the game
	 */
	Board board() {
		return board;
	}


	 /**
	  * initilize the start of the game 
	 * @param windowLength window length
	 * @param windowWidth window width
	 */
	void initStartGame(float windowLength, float windowWidth) {
		System.out.println("number of player chosen :"+numberOfPlayers);
		deck.fillDeck();
		deck.shuffle();
		 for (int i = 0; i < numberOfPlayers; i++) {
			// players[i] = new Player(numberOfCardHand, PlayerInfo::askPlayerName(i));
			 players[i] = new Player(numberOfCardHand, InitGame.askPlayerName(i+1), i ,
					 windowLength, windowWidth);
			 players[i].getHand().dealHand(deck, players[i].getCoordinates());
			 
		 }
	 }
	 
	void initStartGame() {
		 this.initStartGame(0, 0);
	}
	 
}