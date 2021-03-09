package fr.umlv.hanabi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;


class SimpleGameControllerConsole extends AbstractGameController implements SimpleGameController{

	 SimpleGameControllerConsole(SimpleGameData data, SimpleGameView view) {
		 super(data, view);
	}

	 /**
	 *method to collect the specific choice of the player, and execute the specific action
	 * @param buffer buffer to read action
	 * @return Action chosen
	 */
	private static Actions regesterAction(BufferedReader buffer){
		 Objects.requireNonNull(buffer);
		 try {
			 var choice = buffer.readLine();
			 if( choice.equals("d")) {
				 return Actions.discard;
			 }
			 if(choice.equals("p")) {
				 return Actions.play;
			 }
			 if(choice.equals("c")) {
				 return Actions.giveclue;
			 }
		 }catch(Exception e) {
			 System.out.println("Choice invalide, Please try again!");	
		 }
	 return Actions.none; 
	}	 
	
	
	
	
	/**
	 * @return Action choice taken by the player
	 */
	private Actions regesterChoice(){
		BufferedReader  buffer  = new BufferedReader(new InputStreamReader(System.in));
		view().drawActions(data());
		var choice = Actions.none;
		while(choice.equals(Actions.none)) {
			choice = regesterAction(buffer);

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