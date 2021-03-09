package fr.umlv.card;

import java.util.Objects;


import fr.umlv.hanabi.Coordinates;

/**
 * @author mohamed
 * class that defines the actions on the object Card such as its value, color
 * in case of graphic mode its coordinates, the length and width
 */

public final class Card {
	private final int value;
	private final CardColor color;
	private Coordinates coordinates;
	private final static int widthX = 50;
	private final static int lengthY = 150;
	
	
	/**
	 * Constructs the object Card using card value, card Color, 
	 * and the coordinates of the card
	 * @param value Integer value of the card
	 * @param color Color color of the card
	 * @param coordinates Coordinates of the card in graphic mode, can be null in 
	 * 					console mode
	 */
	public Card(int value, CardColor color, Coordinates coordinates){
		Objects.requireNonNull(color);
		if(value <1 || value > 5) {
			throw new IllegalArgumentException("Card number is not valid");
		}
		this.coordinates = coordinates;
		this.color = color;
		this.value = value; 

	}
	
	 /**
	 * Constructor for the object Card in console mode, 
	 * calls the first constructor with value, color and null as coordina
	 * @param value Integer value among {1,1,1,2,2,3,3,4,4,5}
	 * @param color a color among White, Blue, Yellow, Red, Green
	 */
	Card(int value, CardColor color) {
		this(value, color, null);
	}
	

	/**
	 *method write card of the class Card
	 *@return string of the object card, its color and value
	 */
	public String writeCard() {
		return "Color : "+color+ "| number :"+value; 
	}
	

	
	/**
	 * getter of the color for the object card 
	 * @return enum CardColor the color of the Card
	 */
	public CardColor getColor () {
		return color;
	}
	/**
	 * getter of the value the object card 
	 * @return integer of the value of the Card
	 */
	public int getValue() {
		return value;
	}
	

	/**
	 * Card length in graphic mode
	 * @return Integer of the card length 
 	 */
	public static int cardLengthY() {
		return lengthY;
	}
	/**
	 * Card width in graphic mode
	 * @return Integer of the card width 
 	 */
	public static int cardWidthX() {
		return widthX;
	}
	
	/**
	 * determines if two object card has the same color
	 * @param color of the card to check
	 * @return boolean, true if both objects have the same color, else false
	 */
	public boolean sameColor(CardColor color) {
		Objects.requireNonNull(color);
		return this.color.equals(color);
	}
	/**
	 * determines if two object card have the same value
	 * @param value of the card to check
	 * @return boolean, true if both objects have the same value, else false
	 */
	public boolean sameValue(int value) {
		if(value <1 || value > 5){
			throw new IllegalArgumentException("value must be between 1 and 5");
		}
		return this.value == value;
	}


	
	/**
	 *equals method of the class Card
	 *@return boolean if 2 cards have the same value and color
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Card))
			return false;
		var c = (Card)obj;
		return c.value == this.value && c.color.equals(this.color);
	}
	
	/**
	 * hashCode method of the class Card
	 * @return Integer Hash code value of the object
	 */
	@Override
	public int hashCode() {
		return value ^ color.hashCode();
	}


	/**
	 * change coordinates of the card to newCord
	 * @param newCord Coordinates  the new coordinates of the card
	 */
	public void changeCoord(Coordinates newCord) {
		Objects.requireNonNull(newCord);
		this.coordinates = newCord;
	}
	
	/**
	 * getter of the coordinates of the card 
	 * @return Coordinates of the card
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	/** 
	 * width coordinate of the card
	 * @return float of width coordinate of the card
	 */
	public float cardX() {
		return coordinates.getX();
	}
	/** 
	 * length coordinate of the card
	 * @return float of length coordinate of the card
	 */
	public float cardY() {
		return coordinates.getY();
	}

}
