package fr.umlv.board;

import java.util.HashMap;
import java.util.Objects;
import fr.umlv.card.Card;
import fr.umlv.card.CardColor;
import fr.umlv.hanabi.Coordinates;

/**
 * @author mohamed
 * board class, to manipulate all objects on the board such as the game board for the places cartes
 * coordinates of the board, the bin and its coordinates, and tokens 
 */
public class Board{

		private final HashMap<CardColor, Integer> gameBoard;
		private final Coordinates coordGameBoard[];
		private Card cardBin ;
		private final Coordinates coordBin;
		private final Token tokens;
		
	
		 
		 /**
		 * Constructs an object Board using window length and window width
		 * @param windowWidth width of the context window 
		 * @param WindowLength length of the context window 
		 */
		public Board(float windowWidth, float WindowLength){
			this.gameBoard = new HashMap<>();
			this.coordGameBoard = new Coordinates[5];
			var x = windowWidth/3;
			var y = WindowLength/2;
			for (int i = 0; i < CardColor.values().length; i++) {
				this.coordGameBoard[i] = 
						new Coordinates(x+(i*(Card.cardWidthX()+30)),y);
			}
			this.cardBin = null; /* empty bin */
			this.coordBin = new Coordinates(20, 20);
			
			this.tokens = new Token();
		}
		
		
		/**
		 * second constructor with no arguments, calls the first constructor
		 * with window length and window width at 0  
		 */
		public Board(){
			 this(0, 0);
		}
		
	
		
		/**
		 * place the card in specific position on the play board
		 * @param card place card on the board
		 * @throws IllegalAccessException exception in case the card exists on the board
		 */
		public void placeCardToBoard(Card card) throws IllegalAccessException {
			Objects.requireNonNull(card);
			if( !gameBoard.containsKey(card.getColor())) {
				if( card.getValue() == 1) {
					gameBoard.put(card.getColor(), 1);
					return;
				}
				throw new IllegalAccessException("Card cant be placed in boad");
			}else {
				if(card.getValue() - gameBoard.get(card.getColor()) == 1) {
					gameBoard.put(card.getColor(), card.getValue());
					return;
				}
				throw new IllegalAccessException("Card cant be placed in boad");
			}
		}
		
		
		/**
		 * adds card to the top (next empty position) in bin, also updates coordinates of the card
		 * @param card adds card to bin
		 */
		public void placeCardToBin(Card card) {
			Objects.requireNonNull(card);
			card.changeCoord(coordBin);
			cardBin = card;	
		}
		
		/**
		 * @return the last card on the bin
		 */
		public Card lastCardBin() {
			return cardBin;
		}
		
		/**
		 * getter for coordinates bin
		 * @return the coordinates of the bin
		 */
		public Coordinates getCoordBin() {
			return coordBin;
		}
		
		/**
		 * getter for placement positions of the cards on the board 
		 * @return list of Coordinates of the placements on the board
		 */
		public Coordinates[] getCoordGameBoard() {
			return coordGameBoard;
		}
		
		
		/**
		 * determines if the game board is full or not
		 * @return boolean true if the game board is full, false if not
		 */
		public boolean boardIsFull() {
			for (var set: gameBoard.entrySet()) {
				if(set.getValue() != 5) {
					return false;
				}
			}
			return gameBoard.entrySet().size() == 5;
		}
		
		/**
		 * count the number of cards on the game board
		 * @return integer of the number of cards placed correctly
		 */
		public int numberOfCardsPlaced() {
			var numberCard =0;
			for (var set : gameBoard.entrySet()) {
				numberCard+= set.getValue();
			}
			return numberCard;
		}
		
		/**
		 * getter for blue tokens 
		 * @return integer number of blue tokens
		 */
		public int getBlueTokens() {
			return tokens.blueTokens();
		}
		
		/**
		 * getter for red tokens
		 * @return  integer number of red tokens
		 */
		public int getRedTokens(){
			return tokens.redTokens();
		}
		
		/**
		 * check if the number of red tokens is still valid
		 * @return boolean true if red tokens is bigger then 0, false else
		 */
		public boolean redTokensValid() {
			return tokens.redTokens() != 0;
		}
		
		/**
		 * adds a blue token to number of tokens
		 */
		public void addBlueToken() {
			tokens.addBlueToken();
		}
		
		/**
		 * take away a blue token from number of tokens 
		 */
		public void loseBlueToken() {
			tokens.loseBlueToken();
		}
		
		/**
		 * take away a red token from number of tokens 
		 */
		public void loseRedToken(){
			tokens.loseRedToken();
		}

		/**
		 * getter of game board, representing the cards correctly placed
		 * @return HashMap of the card colors and values of the cards
		 */
		public HashMap<CardColor, Integer> getGameBoard() {
			return gameBoard;
		}


}


