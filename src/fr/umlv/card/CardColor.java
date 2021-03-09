package fr.umlv.card;

import java.awt.Color;
import java.util.Objects;

/**
 * @author mohamed
 * class of the colors of the card : White, Blue, Yellow, Red, Green
 */
public enum CardColor {
	White, Blue, Yellow, Red, Green;

	
	/**
	 * method that converts the CardColor color to a usable java.awt.Color 
	 * used for graphic mode
	 * @param color color of the card in {enum CardColor} 
	 * @return the same color in java.awt.Color value 
	 */
	public static Color conversion(CardColor color) {
		Objects.requireNonNull(color);
		switch(color) {
		case White: return Color.white;
		case Blue:return Color.blue;
		case Yellow: return Color.yellow;
		case Red: return Color.red;
		case Green: return Color.GREEN;
		default: return null;
		}
	}

}


