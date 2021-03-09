package fr.umlv.hanabi;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import fr.umlv.card.Card;
import fr.umlv.card.CardColor;
import fr.umlv.clue.ClueTypes;
import fr.umlv.player.Hand;
import fr.umlv.player.Player;
import fr.umlv.zen5.ApplicationContext;

public class SimpleGameViewGraphique implements SimpleGameView {
	private final SimpleGameData data;
	private final float windowWidth;
	private final float windowLength;
	private final ApplicationContext context;

	
	
	
	public SimpleGameViewGraphique(SimpleGameData data, ApplicationContext context, float windowWidth, float windowLength) {
		Objects.requireNonNull(data);
		Objects.requireNonNull(context);
		this.context = context;
		this.data = data;
		this.windowWidth = windowWidth;
		this.windowLength = windowLength;
		
	}

	/**
	 * draw the card in graphic mode
	 * @param graphics Graphics2D of the context
	 * @param card card to draw
	 * @param index index of card to draw 
	 * @param posX position x of the card
	 */
	private static void drawCard(Graphics2D graphics, Card card, int index, float posX) {
		Objects.requireNonNull(graphics);
		Objects.requireNonNull(card);
		var cardX = posX + index*(Card.cardWidthX()+5);
		if( posX == 0) {
			cardX = card.cardX();
		}
		graphics.setColor(CardColor.conversion(card.getColor()));
		graphics.fill(new Rectangle2D.Float(cardX, card.cardY(), Card.cardWidthX(), Card.cardLengthY()));
		graphics.setColor(Color.BLACK);
		graphics.drawString(Integer.toString(card.getValue()),
				cardX + 5, card.cardY()+ 13);
		graphics.drawString(Integer.toString(card.getValue()),
				cardX + Card.cardWidthX()-10, card.cardY()+Card.cardLengthY()-10);
		graphics.drawRect((int)card.cardX(), (int)card.cardY(), 
				Card.cardWidthX(), Card.cardLengthY());
	}
	
	/**
	 * hide card in graphic mode 
	 * @param graphics Graphics2D of the context	 
	 * @param card card to hide
	 * @param index index of card to hide
	 * @param player position x of the card
	 */
	private static void hideCard(Graphics2D graphics, Card card, int index, Player player) {
		Objects.requireNonNull(graphics);
		graphics.setColor(Color.DARK_GRAY);
		if( player != null) {
			var cardX = player.getCoordinates().getX() + index*(Card.cardWidthX()+5);
			graphics.fill(new Rectangle2D.Float(cardX, card.cardY(), Card.cardWidthX(), Card.cardLengthY()));
			drawClue(graphics, card, player, cardX);
		} else {
			graphics.fill(new Rectangle2D.Float(card.cardX(), card.cardY(), Card.cardWidthX(), Card.cardLengthY()));
		}
		graphics.setColor(Color.BLACK);
		graphics.drawRect((int)card.cardX(), (int)card.cardY(), 
				Card.cardWidthX(), Card.cardLengthY());
		
	}
	
	/**
	 * draw clue on card in graphic mode 
	 * @param graphics Graphics2D of the context	 
	 * @param card card to draw on
	 * @param index index of card to draw on
	 * @param player position x of the card
	 */
	private static void drawClue(Graphics2D graphics, Card card, Player player, float cardX) {
		Objects.requireNonNull(graphics);
		Objects.requireNonNull(card);
		 for (var clueType : ClueTypes.values()) {
				if( player.getPlayerClues().hasClueOnCard(card, clueType)) {
					if( clueType.equals(ClueTypes.Color)) {
						graphics.setColor(CardColor.conversion(card.getColor()));
						graphics.fill(new Rectangle2D.Float(cardX, card.cardY(), Card.cardWidthX(), Card.cardLengthY()));
					}else {
						graphics.setColor(Color.BLACK);
						graphics.drawString(Integer.toString(card.getValue()),
								cardX + 5, card.cardY()+ 13);
						graphics.drawString(Integer.toString(card.getValue()),
								cardX + Card.cardWidthX()-10, card.cardY()+Card.cardLengthY()-10);
					}
				}
			}
	}
	

	public float getWindowLength() {
		return windowLength;
	}
	
	public float getWindowWidth() {
		return windowWidth;
	}
	
	
	/**
	 * draws an empty card in position coordinates
	 * @param graphics Graphics2D of the context	 
	 * @param coord coordinates to draw in 
	 */
	private void emptyPosition(Graphics2D graphics, Coordinates coord) {
		Objects.requireNonNull(graphics);
		Objects.requireNonNull(coord);
		graphics.setColor(Color.BLACK);
		graphics.drawRect((int)coord.getX(), (int)coord.getY(), 
				Card.cardWidthX(), Card.cardLengthY());
		graphics.drawString("Empty", coord.getX()+ 10, coord.getY()+ Card.cardLengthY()/2);
	}
	
	
	
	/**
	 * draw the bin with last card in the bin, or empty position if there is no cards
	 * @param graphicsGraphics2D of the context
	 */
	private void showBinGraphique(Graphics2D graphics) {
		Objects.requireNonNull(graphics);
		var cardBin =  data.board().lastCardBin();	
		if(cardBin != null) {
			drawCard(graphics, cardBin, 0, 0);
		}else {
			graphics.drawString("Bin", data.board().getCoordBin().getX()+10, data.board().getCoordBin().getY()-5);
			emptyPosition(graphics, data.board().getCoordBin());
		}
	}
	
	/**
	 * draws the deck of the game, or empty position if there is no cards left in the deck 
	 * @param graphics Graphics2D of the context
	 */
	private void showDeckGraphique(Graphics2D graphics){
		Objects.requireNonNull(graphics);
		graphics.drawString("Deck", data.deck().getCoordDeck().getX()+10, data.deck().getCoordDeck().getY()-5);
		if(data.deck().isEmpty()){
			emptyPosition(graphics, data.deck().getCoordDeck());
		}else {
			hideCard(graphics, data.deck().specificCard(0), 0, null);
		}
	}
	
	/**
	 * draw the board of the game, with the placed cards in order
	 * @param graphics Graphics2D of the context
	 */
	private void drawBoardGraphique(Graphics2D graphics){
		Objects.requireNonNull(graphics);
		var i = 0;
		for (int j = 0; j < 5; j++) {
			emptyPosition(graphics, data.board().getCoordGameBoard()[j] );
		}
		for(var set:data.board().getGameBoard().entrySet()) {
			emptyPosition(graphics, data.board().getCoordGameBoard()[i] );
			var card = new Card( set.getValue(), set.getKey(), data.board().getCoordGameBoard()[i] );
			drawCard(graphics, card, 0, 0);
			i++;
		}
	}
	/**
	 * draw the players hands with the cards either hidden or drawn 
	 * @param graphics Graphics2D of the context
	 */
	private void showPlayersHands(Graphics2D graphics) {
		for(var player : data.players()) {
			if(player.equals(data.currentPlayer())){
				graphics.setColor(Color.red);
				graphics.drawString("Player "+ player.name(), player.getCoordinates().getX()+50, player.getCoordinates().getY()-10);
				for(int i=0; i < player.getHand().getNumberOfCards(); i++) {
					hideCard(graphics, player.getHand().getHand()[i],i, player );}
			}else {
				graphics.setColor(Color.BLACK);
				graphics.drawString("Player "+ player.name(), player.getCoordinates().getX()+50, player.getCoordinates().getY()-10);
				for(int i=0; i < player.getHand().getNumberOfCards(); i++) {
					drawCard(graphics,  player.getHand().getHand()[i], i, player.getCoordinates().getX());}
			}
		}
	}
	
	
	/**
	 * draw the conole game, by calling specific methods to draw each part
	 */
	public void drawConsole() {
		context.renderFrame(graphics->{
			clearZone(0, 0, windowWidth, windowLength);
			drawBoardGraphique(graphics);
			showBinGraphique(graphics);	
			showDeckGraphique(graphics);
			showPlayersHands(graphics);
			clearZone(6*windowWidth/7 - 15, windowLength/10 -15, windowWidth/7 +15, windowLength/10);
			Font font = new Font("SANS_SERIF", Font.CENTER_BASELINE, 20);
			graphics.setFont(font);
			graphics.setColor(Color.BLUE);
			graphics.drawString("Blue tokens: "+ data.board().getBlueTokens(), 6*windowWidth/7, windowLength/10  );
			graphics.setColor(Color.RED);
			graphics.drawString("Red tokens: "+ data.board().getRedTokens(), 6*windowWidth/7, windowLength/10 + 20  );		
			}
		);
	}

	
	/**
	 * clear a specific zone of the interface by drawing rectangle with back ground color
	 * @param zoneX coordinate x of the rectangle
	 * @param zoneY coordinate y of the rectangle
	 * @param width width of the rectangle
	 * @param height height of the rectangle
	 */
	private void clearZone(float zoneX, float zoneY, float width, float height ) {
		var rectangle = new Rectangle2D.Float(zoneX, zoneY ,width, height);
	    context.renderFrame(graphics -> {   
	    	graphics.setColor(Color.lightGray);
	    	graphics.fill(rectangle);
	});
	}


	/**
	 * draw a button in specific location in the window
	 */
	private void drawButton(Graphics2D graphics, String label, float x, float y, float width, float height, Color color ) {
		Font font = new Font("SANS_SERIF", Font.ROMAN_BASELINE, 15);
		graphics.setFont(font);
		graphics.setColor(color);
		graphics.fill(new Rectangle2D.Float(x, y, width, height) );
		graphics.setColor(Color.black);
		graphics.drawString(label,x+10, y+(height/2)+5);
		graphics.drawRect((int)x,(int)y , (int)width, (int)height);	
	}
	
	
	/**
	 * draw the different actions of the game, only allowed action
	 * @param data {@link SimpleGameData} data of the game 
	 */
	public void drawActions(SimpleGameData data) {
		Objects.requireNonNull(data);
		clearZone(windowWidth/3, 3*windowLength/4 ,windowWidth/3 , windowLength/4);
		context.renderFrame(graphics ->{
			Font font = new Font("SANS_SERIF", Font.ROMAN_BASELINE, 20);
			graphics.setFont(font);
			graphics.setColor(Color.black);
			graphics.drawString("Player "+data.currentPlayer().name()+" turn, Select Action", 6*windowWidth/15, 3*windowLength/4 + 20 );
			drawButton(graphics, "Play card",windowWidth/3 +20, 3*windowLength/4 +50,windowWidth/15, windowLength/40, Color.white);
			if(data.board().getBlueTokens() >0) {
				drawButton(graphics, "Give Clue",6*windowWidth/15 + 60,3*windowLength/4  + 50 ,windowWidth/15 , windowLength/40, Color.white);
			}
			if(data.board().getBlueTokens() < 8) {
				drawButton(graphics, "Discard", 7*windowWidth/15 +100,3*windowLength/4  +50 ,windowWidth/15 , windowLength/40, Color.white);			
			}
		});
	}
	
	
	/**
	 *draw the player choices, current player is in red rectangle
	 */
	public Player decidePlayer() {
		clearZone(windowWidth/3, 3*windowLength/4 ,windowWidth/3 , windowLength/4);
		context.renderFrame(graphics ->{
			Font font = new Font("SANS_SERIF", Font.ROMAN_BASELINE, 20);
			graphics.setFont(font);
			graphics.setColor(Color.black);
			graphics.drawString("Player "+data.currentPlayer().name()+" turn, Select Player to help", 6*windowWidth/15, 3*windowLength/4 + 20 );
			var index =0;
			for (var player :data.players()){
				if(player.equals(data.currentPlayer())) {
					drawButton(graphics, player.name(),windowWidth/3 +index*(windowWidth/15), 3*windowLength/4 +50,windowWidth/15, windowLength/40, Color.red);
				}else {
					drawButton(graphics, player.name(),windowWidth/3 + index*(windowWidth/15), 3*windowLength/4 +50,windowWidth/15, windowLength/40, Color.white);					
				}
				index++;
			}
		});
		return null;
	}



	/**
	 *draw the two clue types menu to chose from 
	 */
	public ClueTypes decideClueType() {
		clearZone(windowWidth/3, 3*windowLength/4 ,windowWidth/3 , windowLength/4);
		context.renderFrame(graphics ->{
			Font font = new Font("SANS_SERIF", Font.ROMAN_BASELINE, 20);
			graphics.setFont(font);
			graphics.setColor(Color.black);
			graphics.drawString("Player "+data.currentPlayer().name()+" turn, Select Clue type", 6*windowWidth/15, 3*windowLength/4 + 20 );
			drawButton(graphics, "Color",windowWidth/3 +20, 3*windowLength/4 +50,windowWidth/15, windowLength/40, Color.white);
			drawButton(graphics, "Number", 7*windowWidth/15 +100,3*windowLength/4  +50 ,windowWidth/15 , windowLength/40, Color.white);				
		});	
		return null;
	}

	
	/**
	 *draw the card colors in the hand menu to chose from 
	 *@param hand to draw colors from
	 */
	public CardColor askForColor(Hand hand) {
		clearZone(windowWidth/3, 3*windowLength/4 ,windowWidth/3 , windowLength/4);
		context.renderFrame(graphics ->{
			Font font = new Font("SANS_SERIF", Font.ROMAN_BASELINE, 20);
			graphics.setFont(font);
			graphics.setColor(Color.black);
			graphics.drawString("Player "+data.currentPlayer().name()+" turn, Select clue color to give", 5*windowWidth/15, 3*windowLength/4 + 20 );
			var index =0;
			for (var color :CardColor.values()){
				if(hand.ColorInHand(color)){
					drawButton(graphics, color.toString(),windowWidth/3 +index*(windowWidth/15), 3*windowLength/4 +50,windowWidth/15, windowLength/40, CardColor.conversion(color));
				}
				index++;
			}
		});
		return null;
	}
	
	/**
	 *draw the card values in the hand menu to chose from 
	 *@param hand to draw values from
	 */
	@Override
	public int askForValue(Hand hand) {
		clearZone(windowWidth/3, 3*windowLength/4 ,windowWidth/3 , windowLength/4);
		context.renderFrame(graphics ->{
			Font font = new Font("SANS_SERIF", Font.ROMAN_BASELINE, 20);
			graphics.setFont(font);
			graphics.setColor(Color.black);
			graphics.drawString("Player "+data.currentPlayer().name()+" turn, Select clue number to give", 5*windowWidth/15, 3*windowLength/4 + 20 );
			var index =0;
			for (int i = 1; i < 6; i++) {
				if(hand.valueInHand(i)){
					drawButton(graphics, Integer.toString(i),windowWidth/3 +index*(windowWidth/15), 3*windowLength/4 +50,windowWidth/15, windowLength/40, Color.white);
				}
				index++;	
			}
		});
		
		return 0;
	}
	
	/**
	 * show score and win/lose messages at the end of the game 
	 */
	@Override
	public void showScore() {
		context.renderFrame(graphics->{
			Font font = new Font("SANS_SERIF", Font.ROMAN_BASELINE, 20);
			graphics.setFont(font);
			if( data.board().boardIsFull()) {
				graphics.setColor(Color.MAGENTA);
				graphics.drawString("congratulations you won !", 5*windowWidth/7, 3*windowLength/10);
			}else {
				graphics.setColor(Color.WHITE);
				graphics.drawString("You lost! good luck next time.", 5*windowWidth/7, 3*windowLength/10 );
			}
			graphics.setColor(Color.BLACK);
			graphics.drawString("your final score is :"+data.board().numberOfCardsPlaced(), 5*windowWidth/7 +10, 3*windowLength/10 + 30 );	
			graphics.drawString("click to exit !", 5*windowWidth/7 + 10, 3*windowLength/10 + 50 );		
		}
		);

	}
	

	public ApplicationContext context() {
		return context;
	}


	 /**
	  * draw the initile message to let user know to enter names of the players in the terminal
	 * @param context ApplicationContext of the game
	 * @param width window width
	 * @param length window length
	 */
	public static void redirectUserToTerminal(ApplicationContext context, float width, float length) {
		 Objects.requireNonNull(context);
			context.renderFrame(graphics->{
				Font font = new Font("SANS_SERIF", Font.CENTER_BASELINE, 20);
				graphics.setFont(font);
				graphics.setColor(Color.BLACK);
				graphics.drawString("Please write players names on the terminal ", width/3, length/2  );
				}
			);
	 }






}
