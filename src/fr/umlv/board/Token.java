package fr.umlv.board;

/**
 * @author mohamed
 *class that defines the tokens values and the specific actions on the tokens
 */
class Token {
	private int redTokens;
	private int blueTokens;
	
	
	/**
	 * Contractor for the class token, 
	 * Initialize blue tokens to 8 and red tokens to 3
	 */
	Token(){
		this.blueTokens = 8;
		this.redTokens = 3;
		
	}

	/**
	 * @return number of blue tokens remaining 
	 */
	 int blueTokens() {
		return blueTokens;
	}
	
	/**
	 * @return number of red tokens remaining
	 */
	int redTokens() {
		return redTokens;
	}

	/**
	 * method to lose 1 red token
	 * @return true if there is no red tokens left, else false
	 */
	boolean loseRedToken() {
		redTokens--;
		return redTokens <1;
	}
	
	/**
	 * method to lose 1 blue token
	 */
	void loseBlueToken() {
		if( blueTokens >=1)
			blueTokens--;
	}
	
	/**
	 * adds 1 blue token, to maximum of 5 chips
	 */
	void addBlueToken() {
		if(blueTokens < 8)
			blueTokens++;
	}
	
}


