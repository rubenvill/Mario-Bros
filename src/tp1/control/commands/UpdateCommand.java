package tp1.control.commands;

import tp1.exceptions.CommandParseException;
import tp1.logic.GameInterfaces.GameModel;
import tp1.view.Messages;
import tp1.view.GameView;


public class UpdateCommand extends NoParamsCommand {
	
		private static final String NAME = Messages.COMMAND_UPDATE_NAME;
		private static final String SHORTCUT = Messages.COMMAND_UPDATE_SHORTCUT;
		private static final String DETAILS = Messages.COMMAND_UPDATE_DETAILS;
		private static final String HELP = Messages.COMMAND_UPDATE_HELP;
		
	public UpdateCommand () {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	@Override
	public void execute(GameModel game, GameView view){
		
		 game.update();
		view.showGame();
	
	}
	
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(commandWords[0] == "" || matchCommandName(commandWords[0])) {
			if(commandWords.length == 1) {
				return this;
			}
			else {
				throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			}
		}
		return null;
	}
}