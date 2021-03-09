package fr.umlv.hanabi;

import java.util.Objects;

/**
 * The Coordinates class defines a couple of integers.
 * @author this class is final, it must not be changed
 */
public final class Coordinates {
	private final float x;
	private final float y;

	/**
	 * Constructs and initializes a couple with the specified coordinates.
	 * @param x  the first coordinate
	 * @param y the second coordinate
	 */
	public Coordinates(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * The first coordinate of this couple.
	 * @return the first coordinate of this couple.
	 */
	public float getX() {
		return x;
	}

	/**
	 * The second coordinate of this couple.
	 * @return the second coordinate of this couple.
	 */
	public float getY() {
		return y;
	}

	/**
	 *the equals method of the game, 
	 * @return boolean true if 2 coordinates have the same x and y, else false
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coordinates)) {
			return false;
		}
		Coordinates cc = (Coordinates) o;
		return x == cc.x && y == cc.y;
	}

	/**
	 *method hash code of the class Coordinates
	 * @return Integer hash code of the object Coordinates
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
