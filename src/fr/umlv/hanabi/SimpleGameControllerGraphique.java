package fr.umlv.hanabi;


class SimpleGameControllerGraphique extends AbstractGameController implements SimpleGameController {

	SimpleGameControllerGraphique(SimpleGameData data, SimpleGameView view) {
		super(data, view);
	}
	
	
	/**
	 * determens the choice chosen by the player from the menu 
	 * @param x width click
	 * @param y length click
	 * @return Action chosen
	 */
	private Actions menuChoice(float x, float y) {
		var width = view().getWindowWidth();
		var length = view().getWindowLength();
		if(x> (width/3 +20) && x< (20+ 6*width/15) && y > (3*length/4 +50) && y< (31*length/40 +50 )  ) {
			return Actions.play;
		}
		if(x> (6*width/15 + 60) && x< (7*width/15 + 60) && y >(3*length/4  + 50 ) && y< (31*length/40 +50) ) {
			if(data().board().getBlueTokens() >0) {
				return Actions.giveclue;
			}	
		}
		if(x> (7*width/15 +100) && x< (8*width/15 +100) && y > (3*length/4  +50) && y< (31*length/40 +50) ) {
			if(data().board().getBlueTokens() < 8){
				return Actions.discard;
			}
		}
		return Actions.none;
	}



	
	/**
	 * @return the action chosen by the player
	 */
	private Actions regesterChoice(){
		view().drawActions(data());
		var choice = Actions.none;
		while(choice.equals(Actions.none)) {
			Coordinates position = clickPosition(view().context());
			if( position !=null) {
				choice = menuChoice(position.getX(), position.getY());
			}
		}
		return choice;
	}



	/**
	 * choose the action to executer, related to the choice of the player,
	 *  accept only valid choices 
	 */
	public void manageActions() {
		 var acceptAction =false;
		 while(!acceptAction) {
			 switch (regesterChoice()) {
			 	case discard: acceptAction= discardCard(data(), view());
			 					break;
			 	case play: acceptAction= playCard(data(), view());
								break; 
			 	case giveclue: acceptAction= giveClue(data(), view());
			 					break;
				default : System.out.println("Action is not possible, or not valid please choose again"); 
							break;
			 }
		 }
	}


	

}
