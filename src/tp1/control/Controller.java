package tp1.control;

import tp1.control.commands.Command;
import tp1.control.commands.CommandGenerator;
import tp1.exceptions.CommandException;
import tp1.logic.GameInterfaces.GameModel;
import tp1.view.GameView;

public class Controller {

	private GameModel game;
	private GameView view;

	public Controller(GameModel game, GameView view) {
		this.game = game;
		this.view = view;
	}

	public void run() {

		view.showWelcome();

		view.showGame();
		
		while (!game.isFinished()) {
			
			String[] words = view.getPrompt();//Stores user's input
			
			try {
				
				Command command = CommandGenerator.parse(words); 
					command.execute(game, view); 
				
			} catch (CommandException e) { 
				view.showError(e.getMessage()); 
				Throwable cause = e.getCause(); 
				while(cause != null) { 
					
					view.showError(cause.getMessage()); 
					cause = cause.getCause();
				}
			}

		}
		view.showEndMessage();
	}
	
}